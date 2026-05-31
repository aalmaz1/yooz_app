package com.google.android.gms.internal.location;

import android.app.PendingIntent;
import android.os.IInterface;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* JADX INFO: loaded from: classes2.dex */
public interface zzm extends IInterface {
    void zzb(int i, String[] strArr) throws RemoteException;

    void zzc(int i, PendingIntent pendingIntent) throws RemoteException;

    void zzd(int i, String[] strArr) throws RemoteException;
}
