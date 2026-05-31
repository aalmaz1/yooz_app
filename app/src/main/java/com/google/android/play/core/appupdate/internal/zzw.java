package com.google.android.play.core.appupdate.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzw implements ServiceConnection {
    final /* synthetic */ zzx zza;

    /* synthetic */ zzw(zzx zzxVar, zzv zzvVar) {
        this.zza = zzxVar;
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.zza.zzc.zzd("ServiceConnectionImpl.onServiceConnected(%s)", componentName);
        this.zza.zzc().post(new zzt(this, iBinder));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.zza.zzc.zzd("ServiceConnectionImpl.onServiceDisconnected(%s)", componentName);
        this.zza.zzc().post(new zzu(this));
    }
}
