package com.google.android.gms.location;

import android.location.Location;
import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzs extends com.google.android.gms.internal.location.zza implements zzu {
    zzs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.location.ILocationListener");
    }

    @Override // com.google.android.gms.location.zzu
    public final void zzd(Location location) throws RemoteException {
        throw null;
    }

    @Override // com.google.android.gms.location.zzu
    public final void zze() throws RemoteException {
        throw null;
    }
}
