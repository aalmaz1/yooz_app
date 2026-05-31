package com.google.android.gms.internal.auth;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzp extends zza implements IInterface {
    zzp(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.account.data.IGoogleAuthService");
    }

    public final void zzd(IStatusCallback iStatusCallback, zzbw zzbwVar) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zze(parcelZza, iStatusCallback);
        zzc.zzd(parcelZza, zzbwVar);
        zzc(2, parcelZza);
    }

    public final void zze(zzm zzmVar, AccountChangeEventsRequest accountChangeEventsRequest) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zze(parcelZza, zzmVar);
        zzc.zzd(parcelZza, accountChangeEventsRequest);
        zzc(4, parcelZza);
    }

    public final void zzf(zzo zzoVar, Account account, String str, Bundle bundle) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zze(parcelZza, zzoVar);
        zzc.zzd(parcelZza, account);
        parcelZza.writeString(str);
        zzc.zzd(parcelZza, bundle);
        zzc(1, parcelZza);
    }

    public final void zzg(zzk zzkVar, Account account) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zze(parcelZza, zzkVar);
        zzc.zzd(parcelZza, account);
        zzc(6, parcelZza);
    }

    public final void zzh(zzk zzkVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        zzc.zze(parcelZza, zzkVar);
        parcelZza.writeString(str);
        zzc(3, parcelZza);
    }
}
