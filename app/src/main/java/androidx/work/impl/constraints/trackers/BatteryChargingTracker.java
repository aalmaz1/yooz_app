package androidx.work.impl.constraints.trackers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.core.app.NotificationCompat;
import androidx.work.Logger;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BatteryChargingTracker.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\b\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Landroidx/work/impl/constraints/trackers/BatteryChargingTracker;", "Landroidx/work/impl/constraints/trackers/BroadcastReceiverConstraintTracker;", "", "context", "Landroid/content/Context;", "taskExecutor", "Landroidx/work/impl/utils/taskexecutor/TaskExecutor;", "(Landroid/content/Context;Landroidx/work/impl/utils/taskexecutor/TaskExecutor;)V", "initialState", "getInitialState", "()Ljava/lang/Boolean;", "intentFilter", "Landroid/content/IntentFilter;", "getIntentFilter", "()Landroid/content/IntentFilter;", "isBatteryChangedIntentCharging", "intent", "Landroid/content/Intent;", "onBroadcastReceive", "", "work-runtime_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
public final class BatteryChargingTracker extends BroadcastReceiverConstraintTracker<Boolean> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BatteryChargingTracker(Context context, TaskExecutor taskExecutor) {
        super(context, taskExecutor);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(taskExecutor, "taskExecutor");
    }

    @Override // androidx.work.impl.constraints.trackers.ConstraintTracker
    public Boolean getInitialState() {
        Intent intentRegisterReceiver = getAppContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (intentRegisterReceiver == null) {
            Logger.get().error(BatteryChargingTrackerKt.TAG, "getInitialState - null intent received");
            return false;
        }
        return Boolean.valueOf(isBatteryChangedIntentCharging(intentRegisterReceiver));
    }

    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    public IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.CHARGING");
        intentFilter.addAction("android.os.action.DISCHARGING");
        return intentFilter;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // androidx.work.impl.constraints.trackers.BroadcastReceiverConstraintTracker
    public void onBroadcastReceive(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        String action = intent.getAction();
        if (action == null) {
        }
        Logger.get().debug(BatteryChargingTrackerKt.TAG, "Received " + action);
        switch (action.hashCode()) {
            case -1886648615:
                if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                    setState(false);
                    break;
                }
                break;
            case -54942926:
                if (action.equals("android.os.action.DISCHARGING")) {
                    setState(false);
                    break;
                }
                break;
            case 948344062:
                if (action.equals("android.os.action.CHARGING")) {
                    setState(true);
                    break;
                }
                break;
            case 1019184907:
                if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                    setState(true);
                    break;
                }
                break;
        }
    }

    private final boolean isBatteryChangedIntentCharging(Intent intent) {
        int intExtra = intent.getIntExtra(NotificationCompat.CATEGORY_STATUS, -1);
        return intExtra == 2 || intExtra == 5;
    }
}
