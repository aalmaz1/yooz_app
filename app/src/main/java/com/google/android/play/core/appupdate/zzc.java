package com.google.android.play.core.appupdate;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.play.core.install.InstallState;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzc extends com.google.android.play.core.appupdate.internal.zzl {
    public zzc(Context context) {
        super(new com.google.android.play.core.appupdate.internal.zzm("AppUpdateListenerRegistry"), new IntentFilter("com.google.android.play.core.install.ACTION_INSTALL_STATUS"), context);
    }

    @Override // com.google.android.play.core.appupdate.internal.zzl
    protected final void zza(Context context, Intent intent) {
        if (!context.getPackageName().equals(intent.getStringExtra("package.name"))) {
            this.zza.zza("ListenerRegistryBroadcastReceiver received broadcast for third party app: %s", intent.getStringExtra("package.name"));
            return;
        }
        this.zza.zza("List of extras in received intent:", new Object[0]);
        for (String str : intent.getExtras().keySet()) {
            this.zza.zza("Key: %s; value: %s", str, intent.getExtras().get(str));
        }
        InstallState installStateZzb = InstallState.zzb(intent, this.zza);
        this.zza.zza("ListenerRegistryBroadcastReceiver.onReceive: %s", installStateZzb);
        zzd(installStateZzb);
    }
}
