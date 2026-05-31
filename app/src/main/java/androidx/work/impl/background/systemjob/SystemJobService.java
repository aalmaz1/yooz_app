package androidx.work.impl.background.systemjob;

import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Network;
import android.net.Uri;
import android.os.PersistableBundle;
import androidx.work.Logger;
import androidx.work.WorkerParameters;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.StartStopToken;
import androidx.work.impl.StartStopTokens;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkGenerationalId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SystemJobService extends JobService implements ExecutionListener {
    private static final String TAG = Logger.tagWithPrefix("SystemJobService");
    private final Map<WorkGenerationalId, JobParameters> mJobParameters = new HashMap();
    private final StartStopTokens mStartStopTokens = new StartStopTokens();
    private WorkManagerImpl mWorkManagerImpl;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        try {
            WorkManagerImpl workManagerImpl = WorkManagerImpl.getInstance(getApplicationContext());
            this.mWorkManagerImpl = workManagerImpl;
            workManagerImpl.getProcessor().addExecutionListener(this);
        } catch (IllegalStateException unused) {
            if (!Application.class.equals(getApplication().getClass())) {
                throw new IllegalStateException("WorkManager needs to be initialized via a ContentProvider#onCreate() or an Application#onCreate().");
            }
            Logger.get().warning(TAG, "Could not find WorkManager instance; this may be because an auto-backup is in progress. Ignoring JobScheduler commands for now. Please make sure that you are initializing WorkManager if you have manually disabled WorkManagerInitializer.");
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        WorkManagerImpl workManagerImpl = this.mWorkManagerImpl;
        if (workManagerImpl != null) {
            workManagerImpl.getProcessor().removeExecutionListener(this);
        }
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters params) {
        if (this.mWorkManagerImpl == null) {
            Logger.get().debug(TAG, "WorkManager is not initialized; requesting retry.");
            jobFinished(params, true);
            return false;
        }
        WorkGenerationalId workGenerationalIdWorkGenerationalIdFromJobParameters = workGenerationalIdFromJobParameters(params);
        if (workGenerationalIdWorkGenerationalIdFromJobParameters == null) {
            Logger.get().error(TAG, "WorkSpec id not found!");
            return false;
        }
        synchronized (this.mJobParameters) {
            if (this.mJobParameters.containsKey(workGenerationalIdWorkGenerationalIdFromJobParameters)) {
                Logger.get().debug(TAG, "Job is already being executed by SystemJobService: " + workGenerationalIdWorkGenerationalIdFromJobParameters);
                return false;
            }
            Logger.get().debug(TAG, "onStartJob for " + workGenerationalIdWorkGenerationalIdFromJobParameters);
            this.mJobParameters.put(workGenerationalIdWorkGenerationalIdFromJobParameters, params);
            WorkerParameters.RuntimeExtras runtimeExtras = new WorkerParameters.RuntimeExtras();
            if (Api24Impl.getTriggeredContentUris(params) != null) {
                runtimeExtras.triggeredContentUris = Arrays.asList(Api24Impl.getTriggeredContentUris(params));
            }
            if (Api24Impl.getTriggeredContentAuthorities(params) != null) {
                runtimeExtras.triggeredContentAuthorities = Arrays.asList(Api24Impl.getTriggeredContentAuthorities(params));
            }
            runtimeExtras.network = Api28Impl.getNetwork(params);
            this.mWorkManagerImpl.startWork(this.mStartStopTokens.tokenFor(workGenerationalIdWorkGenerationalIdFromJobParameters), runtimeExtras);
            return true;
        }
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters params) {
        if (this.mWorkManagerImpl == null) {
            Logger.get().debug(TAG, "WorkManager is not initialized; requesting retry.");
            return true;
        }
        WorkGenerationalId workGenerationalIdWorkGenerationalIdFromJobParameters = workGenerationalIdFromJobParameters(params);
        if (workGenerationalIdWorkGenerationalIdFromJobParameters == null) {
            Logger.get().error(TAG, "WorkSpec id not found!");
            return false;
        }
        Logger.get().debug(TAG, "onStopJob for " + workGenerationalIdWorkGenerationalIdFromJobParameters);
        synchronized (this.mJobParameters) {
            this.mJobParameters.remove(workGenerationalIdWorkGenerationalIdFromJobParameters);
        }
        StartStopToken startStopTokenRemove = this.mStartStopTokens.remove(workGenerationalIdWorkGenerationalIdFromJobParameters);
        if (startStopTokenRemove != null) {
            this.mWorkManagerImpl.stopWork(startStopTokenRemove);
        }
        return !this.mWorkManagerImpl.getProcessor().isCancelled(workGenerationalIdWorkGenerationalIdFromJobParameters.getWorkSpecId());
    }

    @Override // androidx.work.impl.ExecutionListener
    /* JADX INFO: renamed from: onExecuted */
    public void m336lambda$runOnExecuted$1$androidxworkimplProcessor(WorkGenerationalId id, boolean needsReschedule) {
        JobParameters jobParametersRemove;
        Logger.get().debug(TAG, id.getWorkSpecId() + " executed on JobScheduler");
        synchronized (this.mJobParameters) {
            jobParametersRemove = this.mJobParameters.remove(id);
        }
        this.mStartStopTokens.remove(id);
        if (jobParametersRemove != null) {
            jobFinished(jobParametersRemove, needsReschedule);
        }
    }

    private static WorkGenerationalId workGenerationalIdFromJobParameters(JobParameters parameters) {
        try {
            PersistableBundle extras = parameters.getExtras();
            if (extras == null || !extras.containsKey("EXTRA_WORK_SPEC_ID")) {
                return null;
            }
            return new WorkGenerationalId(extras.getString("EXTRA_WORK_SPEC_ID"), extras.getInt("EXTRA_WORK_SPEC_GENERATION"));
        } catch (NullPointerException unused) {
            return null;
        }
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static Uri[] getTriggeredContentUris(JobParameters jobParameters) {
            return jobParameters.getTriggeredContentUris();
        }

        static String[] getTriggeredContentAuthorities(JobParameters jobParameters) {
            return jobParameters.getTriggeredContentAuthorities();
        }
    }

    static class Api28Impl {
        private Api28Impl() {
        }

        static Network getNetwork(JobParameters jobParameters) {
            return jobParameters.getNetwork();
        }
    }
}
