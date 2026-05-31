package androidx.work.impl.utils;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.impl.Scheduler;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import androidx.work.impl.workers.ConstraintTrackingWorkerKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EnqueueUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0000\u001a\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u001e\u0010\n\u001a\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0002\u001a\u00020\u0001H\u0000¨\u0006\u000b"}, d2 = {"tryDelegateConstrainedWorkSpec", "Landroidx/work/impl/model/WorkSpec;", "workSpec", "usesScheduler", "", "schedulers", "", "Landroidx/work/impl/Scheduler;", "className", "", "wrapInConstraintTrackingWorkerIfNeeded", "work-runtime_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
public final class EnqueueUtilsKt {
    public static final WorkSpec wrapInConstraintTrackingWorkerIfNeeded(List<? extends Scheduler> schedulers, WorkSpec workSpec) {
        Intrinsics.checkNotNullParameter(schedulers, "schedulers");
        Intrinsics.checkNotNullParameter(workSpec, "workSpec");
        return workSpec;
    }

    public static final WorkSpec tryDelegateConstrainedWorkSpec(WorkSpec workSpec) throws Throwable {
        Intrinsics.checkNotNullParameter(workSpec, "workSpec");
        Constraints constraints = workSpec.constraints;
        String str = workSpec.workerClassName;
        if (Intrinsics.areEqual(str, ConstraintTrackingWorker.class.getName())) {
            return workSpec;
        }
        if (!constraints.getRequiresBatteryNotLow() && !constraints.getRequiresStorageNotLow()) {
            return workSpec;
        }
        Data dataBuild = new Data.Builder().putAll(workSpec.input).putString(ConstraintTrackingWorkerKt.ARGUMENT_CLASS_NAME, str).build();
        Intrinsics.checkNotNullExpressionValue(dataBuild, "Builder().putAll(workSpe…ame)\n            .build()");
        String name = ConstraintTrackingWorker.class.getName();
        Intrinsics.checkNotNullExpressionValue(name, "name");
        return workSpec.copy((1048574 & 1) != 0 ? workSpec.id : null, (1048574 & 2) != 0 ? workSpec.state : null, (1048574 & 4) != 0 ? workSpec.workerClassName : name, (1048574 & 8) != 0 ? workSpec.inputMergerClassName : null, (1048574 & 16) != 0 ? workSpec.input : dataBuild, (1048574 & 32) != 0 ? workSpec.output : null, (1048574 & 64) != 0 ? workSpec.initialDelay : 0L, (1048574 & 128) != 0 ? workSpec.intervalDuration : 0L, (1048574 & 256) != 0 ? workSpec.flexDuration : 0L, (1048574 & 512) != 0 ? workSpec.constraints : null, (1048574 & 1024) != 0 ? workSpec.runAttemptCount : 0, (1048574 & 2048) != 0 ? workSpec.backoffPolicy : null, (1048574 & 4096) != 0 ? workSpec.backoffDelayDuration : 0L, (1048574 & 8192) != 0 ? workSpec.lastEnqueueTime : 0L, (1048574 & 16384) != 0 ? workSpec.minimumRetentionDuration : 0L, (1048574 & 32768) != 0 ? workSpec.scheduleRequestedAt : 0L, (1048574 & 65536) != 0 ? workSpec.expedited : false, (131072 & 1048574) != 0 ? workSpec.outOfQuotaPolicy : null, (1048574 & 262144) != 0 ? workSpec.periodCount : 0, (1048574 & 524288) != 0 ? workSpec.generation : 0);
    }

    private static final boolean usesScheduler(List<? extends Scheduler> list, String str) {
        try {
            Class<?> cls = Class.forName(str);
            List<? extends Scheduler> list2 = list;
            if ((list2 instanceof Collection) && list2.isEmpty()) {
                return false;
            }
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (cls.isAssignableFrom(((Scheduler) it.next()).getClass())) {
                    return true;
                }
            }
            return false;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }
}
