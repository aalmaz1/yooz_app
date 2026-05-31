package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzc extends com.google.android.gms.internal.auth.zza implements zze {
    zzc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.account.IWorkAccountService");
    }

    @Override // com.google.android.gms.auth.account.zze
    public final void zzd(zzb zzbVar, String str) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.auth.zzc.zze(parcelZza, zzbVar);
        parcelZza.writeString(str);
        zzc(2, parcelZza);
    }

    @Override // com.google.android.gms.auth.account.zze
    public final void zze(zzb zzbVar, Account account) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.auth.zzc.zze(parcelZza, zzbVar);
        com.google.android.gms.internal.auth.zzc.zzd(parcelZza, account);
        zzc(3, parcelZza);
    }

    @Override // com.google.android.gms.auth.account.zze
    public final void zzf(boolean z) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.auth.zzc.zzc(parcelZza, z);
        zzc(1, parcelZza);
    }
}
