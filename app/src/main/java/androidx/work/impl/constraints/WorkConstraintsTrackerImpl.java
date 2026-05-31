package androidx.work.impl.constraints;

import androidx.work.Logger;
import androidx.work.impl.constraints.controllers.BatteryChargingController;
import androidx.work.impl.constraints.controllers.BatteryNotLowController;
import androidx.work.impl.constraints.controllers.ConstraintController;
import androidx.work.impl.constraints.controllers.NetworkConnectedController;
import androidx.work.impl.constraints.controllers.NetworkMeteredController;
import androidx.work.impl.constraints.controllers.NetworkNotRoamingController;
import androidx.work.impl.constraints.controllers.NetworkUnmeteredController;
import androidx.work.impl.constraints.controllers.StorageNotLowController;
import androidx.work.impl.constraints.trackers.Trackers;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WorkConstraintsTracker.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B#\b\u0001\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0010\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\t¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0016\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J\u0016\u0010\u0018\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J\u0016\u0010\u0019\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0014H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Landroidx/work/impl/constraints/WorkConstraintsTrackerImpl;", "Landroidx/work/impl/constraints/WorkConstraintsTracker;", "Landroidx/work/impl/constraints/controllers/ConstraintController$OnConstraintUpdatedCallback;", "trackers", "Landroidx/work/impl/constraints/trackers/Trackers;", "callback", "Landroidx/work/impl/constraints/WorkConstraintsCallback;", "(Landroidx/work/impl/constraints/trackers/Trackers;Landroidx/work/impl/constraints/WorkConstraintsCallback;)V", "constraintControllers", "", "Landroidx/work/impl/constraints/controllers/ConstraintController;", "(Landroidx/work/impl/constraints/WorkConstraintsCallback;[Landroidx/work/impl/constraints/controllers/ConstraintController;)V", "[Landroidx/work/impl/constraints/controllers/ConstraintController;", "lock", "", "areAllConstraintsMet", "", "workSpecId", "", "onConstraintMet", "", "workSpecs", "", "Landroidx/work/impl/model/WorkSpec;", "onConstraintNotMet", "replace", "", "reset", "work-runtime_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
public final class WorkConstraintsTrackerImpl implements WorkConstraintsTracker, ConstraintController.OnConstraintUpdatedCallback {
    private final WorkConstraintsCallback callback;
    private final ConstraintController<?>[] constraintControllers;
    private final Object lock;

    public WorkConstraintsTrackerImpl(WorkConstraintsCallback workConstraintsCallback, ConstraintController<?>[] constraintControllers) {
        Intrinsics.checkNotNullParameter(constraintControllers, "constraintControllers");
        this.callback = workConstraintsCallback;
        this.constraintControllers = constraintControllers;
        this.lock = new Object();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WorkConstraintsTrackerImpl(Trackers trackers, WorkConstraintsCallback workConstraintsCallback) {
        this(workConstraintsCallback, (ConstraintController<?>[]) new ConstraintController[]{new BatteryChargingController(trackers.getBatteryChargingTracker()), new BatteryNotLowController(trackers.getBatteryNotLowTracker()), new StorageNotLowController(trackers.getStorageNotLowTracker()), new NetworkConnectedController(trackers.getNetworkStateTracker()), new NetworkUnmeteredController(trackers.getNetworkStateTracker()), new NetworkNotRoamingController(trackers.getNetworkStateTracker()), new NetworkMeteredController(trackers.getNetworkStateTracker())});
        Intrinsics.checkNotNullParameter(trackers, "trackers");
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsTracker
    public void replace(Iterable<WorkSpec> workSpecs) {
        Intrinsics.checkNotNullParameter(workSpecs, "workSpecs");
        synchronized (this.lock) {
            for (ConstraintController<?> constraintController : this.constraintControllers) {
                constraintController.setCallback(null);
            }
            for (ConstraintController<?> constraintController2 : this.constraintControllers) {
                constraintController2.replace(workSpecs);
            }
            for (ConstraintController<?> constraintController3 : this.constraintControllers) {
                constraintController3.setCallback(this);
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // androidx.work.impl.constraints.WorkConstraintsTracker
    public void reset() {
        synchronized (this.lock) {
            for (ConstraintController<?> constraintController : this.constraintControllers) {
                constraintController.reset();
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final boolean areAllConstraintsMet(String workSpecId) {
        ConstraintController<?> constraintController;
        boolean z;
        Intrinsics.checkNotNullParameter(workSpecId, "workSpecId");
        synchronized (this.lock) {
            ConstraintController<?>[] constraintControllerArr = this.constraintControllers;
            int length = constraintControllerArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    constraintController = null;
                    break;
                }
                constraintController = constraintControllerArr[i];
                if (constraintController.isWorkSpecConstrained(workSpecId)) {
                    break;
                }
                i++;
            }
            if (constraintController != null) {
                Logger.get().debug(WorkConstraintsTrackerKt.TAG, "Work " + workSpecId + " constrained by " + constraintController.getClass().getSimpleName());
            }
            z = constraintController == null;
        }
        return z;
    }

    @Override // androidx.work.impl.constraints.controllers.ConstraintController.OnConstraintUpdatedCallback
    public void onConstraintMet(List<WorkSpec> workSpecs) {
        Intrinsics.checkNotNullParameter(workSpecs, "workSpecs");
        synchronized (this.lock) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : workSpecs) {
                if (areAllConstraintsMet(((WorkSpec) obj).id)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                Logger.get().debug(WorkConstraintsTrackerKt.TAG, "Constraints met for " + ((WorkSpec) it.next()));
            }
            WorkConstraintsCallback workConstraintsCallback = this.callback;
            if (workConstraintsCallback != null) {
                workConstraintsCallback.onAllConstraintsMet(arrayList2);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    @Override // androidx.work.impl.constraints.controllers.ConstraintController.OnConstraintUpdatedCallback
    public void onConstraintNotMet(List<WorkSpec> workSpecs) {
        Intrinsics.checkNotNullParameter(workSpecs, "workSpecs");
        synchronized (this.lock) {
            WorkConstraintsCallback workConstraintsCallback = this.callback;
            if (workConstraintsCallback != null) {
                workConstraintsCallback.onAllConstraintsNotMet(workSpecs);
                Unit unit = Unit.INSTANCE;
            }
        }
    }
}
