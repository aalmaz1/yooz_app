package com.google.android.play.core.appupdate.internal;

import android.os.IBinder;
import android.os.IInterface;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zze extends zzb implements zzf {
    public static zzf zzb(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.appupdate.protocol.IAppUpdateService");
        return iInterfaceQueryLocalInterface instanceof zzf ? (zzf) iInterfaceQueryLocalInterface : new zzd(iBinder);
    }
}
