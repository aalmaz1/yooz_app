package androidx.work.impl.background.greedy;

import android.content.Context;
import android.text.TextUtils;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Scheduler;
import androidx.work.impl.StartStopToken;
import androidx.work.impl.StartStopTokens;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.constraints.WorkConstraintsTrackerImpl;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkGenerationalId;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecKt;
import androidx.work.impl.utils.ProcessUtils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class GreedyScheduler implements Scheduler, WorkConstraintsCallback, ExecutionListener {
    private static final String TAG = Logger.tagWithPrefix("GreedyScheduler");
    private final Context mContext;
    private DelayedWorkTracker mDelayedWorkTracker;
    Boolean mInDefaultProcess;
    private boolean mRegisteredExecutionListener;
    private final WorkConstraintsTracker mWorkConstraintsTracker;
    private final WorkManagerImpl mWorkManagerImpl;
    private final Set<WorkSpec> mConstrainedWorkSpecs = new HashSet();
    private final StartStopTokens mStartStopTokens = new StartStopTokens();
    private final Object mLock = new Object();

    @Override // androidx.work.impl.Scheduler
    public boolean hasLimitedSchedulingSlots() {
        return false;
    }

    public GreedyScheduler(Context context, Configuration configuration, Trackers trackers, WorkManagerImpl workManagerImpl) {
        this.mContext = context;
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkConstraintsTracker = new WorkConstraintsTrackerImpl(trackers, this);
        this.mDelayedWorkTracker = new DelayedWorkTracker(this, configuration.getRunnableScheduler());
    }

    public GreedyScheduler(Context context, WorkManagerImpl workManagerImpl, WorkConstraintsTracker workConstraintsTracker) {
        this.mContext = context;
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkConstraintsTracker = workConstraintsTracker;
    }

    public void setDelayedWorkTracker(DelayedWorkTracker delayedWorkTracker) {
        this.mDelayedWorkTracker = delayedWorkTracker;
    }

    @Override // androidx.work.impl.Scheduler
    public void schedule(WorkSpec... workSpecs) {
        if (this.mInDefaultProcess == null) {
            checkDefaultProcess();
        }
        if (!this.mInDefaultProcess.booleanValue()) {
            Logger.get().info(TAG, "Ignoring schedule request in a secondary process");
            return;
        }
        registerExecutionListenerIfNeeded();
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        for (WorkSpec workSpec : workSpecs) {
            if (!this.mStartStopTokens.contains(WorkSpecKt.generationalId(workSpec))) {
                long jCalculateNextRunTime = workSpec.calculateNextRunTime();
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (workSpec.state == WorkInfo.State.ENQUEUED) {
                    if (jCurrentTimeMillis < jCalculateNextRunTime) {
                        DelayedWorkTracker delayedWorkTracker = this.mDelayedWorkTracker;
                        if (delayedWorkTracker != null) {
                            delayedWorkTracker.schedule(workSpec);
                        }
                    } else if (workSpec.hasConstraints()) {
                        if (workSpec.constraints.getRequiresDeviceIdle()) {
                            Logger.get().debug(TAG, "Ignoring " + workSpec + ". Requires device idle.");
                        } else if (workSpec.constraints.hasContentUriTriggers()) {
                            Logger.get().debug(TAG, "Ignoring " + workSpec + ". Requires ContentUri triggers.");
                        } else {
                            hashSet.add(workSpec);
                            hashSet2.add(workSpec.id);
                        }
                    } else if (!this.mStartStopTokens.contains(WorkSpecKt.generationalId(workSpec))) {
                        Logger.get().debug(TAG, "Starting work for " + workSpec.id);
                        this.mWorkManagerImpl.startWork(this.mStartStopTokens.tokenFor(workSpec));
                    }
                }
            }
        }
        synchronized (this.mLock) {
            if (!hashSet.isEmpty()) {
                Logger.get().debug(TAG, "Starting tracking for " + TextUtils.join(",", hashSet2));
                this.mConstrainedWorkSpecs.addAll(hashSet);
                this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
            }
        }
    }

    private void checkDefaultProcess() {
        this.mInDefaultProcess = Boolean.valueOf(ProcessUtils.isDefaultProcess(this.mContext, this.mWorkManagerImpl.getConfiguration()));
    }

    @Override // androidx.work.impl.Scheduler
    public void cancel(String workSpecId) {
        if (this.mInDefaultProcess == null) {
            checkDefaultProcess();
        }
        if (!this.mInDefaultProcess.booleanValue()) {
            Logger.get().info(TAG, "Ignoring schedule request in non-main process");
            return;
        }
        registerExecutionListenerIfNeeded();
        Logger.get().debug(TAG, "Cancelling work ID " + workSpecId);
        DelayedWorkTracker delayedWorkTracker = this.mDelayedWorkTracker;
        if (delayedWorkTracker != null) {
            delayedWorkTracker.unschedule(workSpecId);
        }
        Iterator<StartStopToken> it = this.mStartStopTokens.remove(workSpecId).iterator();
        while (it.hasNext()) {
            this.mWorkManagerImpl.stopWork(it.next());
        }
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsMet(List<WorkSpec> workSpecs) {
        Iterator<WorkSpec> it = workSpecs.iterator();
        while (it.hasNext()) {
            WorkGenerationalId workGenerationalIdGenerationalId = WorkSpecKt.generationalId(it.next());
            if (!this.mStartStopTokens.contains(workGenerationalIdGenerationalId)) {
                Logger.get().debug(TAG, "Constraints met: Scheduling work ID " + workGenerationalIdGenerationalId);
                this.mWorkManagerImpl.startWork(this.mStartStopTokens.tokenFor(workGenerationalIdGenerationalId));
            }
        }
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsCallback
    public void onAllConstraintsNotMet(List<WorkSpec> workSpecs) {
        Iterator<WorkSpec> it = workSpecs.iterator();
        while (it.hasNext()) {
            WorkGenerationalId workGenerationalIdGenerationalId = WorkSpecKt.generationalId(it.next());
            Logger.get().debug(TAG, "Constraints not met: Cancelling work ID " + workGenerationalIdGenerationalId);
            StartStopToken startStopTokenRemove = this.mStartStopTokens.remove(workGenerationalIdGenerationalId);
            if (startStopTokenRemove != null) {
                this.mWorkManagerImpl.stopWork(startStopTokenRemove);
            }
        }
    }

    @Override // androidx.work.impl.ExecutionListener
    /* JADX INFO: renamed from: onExecuted */
    public void m336lambda$runOnExecuted$1$androidxworkimplProcessor(WorkGenerationalId id, boolean needsReschedule) {
        this.mStartStopTokens.remove(id);
        removeConstraintTrackingFor(id);
    }

    private void removeConstraintTrackingFor(WorkGenerationalId id) {
        synchronized (this.mLock) {
            Iterator<WorkSpec> it = this.mConstrainedWorkSpecs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WorkSpec next = it.next();
                if (WorkSpecKt.generationalId(next).equals(id)) {
                    Logger.get().debug(TAG, "Stopping tracking for " + id);
                    this.mConstrainedWorkSpecs.remove(next);
                    this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
                    break;
                }
            }
        }
    }

    private void registerExecutionListenerIfNeeded() {
        if (this.mRegisteredExecutionListener) {
            return;
        }
        this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
        this.mRegisteredExecutionListener = true;
    }
}
