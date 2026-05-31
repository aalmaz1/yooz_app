package com.google.android.gms.internal.play_billing;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzk extends zzp implements zzm {
    zzk(IBinder iBinder) {
        super(iBinder, "com.android.vending.billing.IInAppBillingService");
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final int zza(int i, String str, String str2) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(3);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        Parcel parcelZzs = zzs(5, parcelZzr);
        int i2 = parcelZzs.readInt();
        parcelZzs.recycle();
        return i2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final int zzc(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(i);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        zzr.zzc(parcelZzr, bundle);
        Parcel parcelZzs = zzs(10, parcelZzr);
        int i2 = parcelZzs.readInt();
        parcelZzs.recycle();
        return i2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzd(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(9);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        zzr.zzc(parcelZzr, bundle);
        Parcel parcelZzs = zzs(902, parcelZzr);
        Bundle bundle2 = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zze(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(9);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        zzr.zzc(parcelZzr, bundle);
        Parcel parcelZzs = zzs(12, parcelZzr);
        Bundle bundle2 = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzf(int i, String str, String str2, String str3, String str4) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(3);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        parcelZzr.writeString(str3);
        parcelZzr.writeString(null);
        Parcel parcelZzs = zzs(3, parcelZzr);
        Bundle bundle = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzg(int i, String str, String str2, String str3, String str4, Bundle bundle) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(i);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        parcelZzr.writeString(str3);
        parcelZzr.writeString(null);
        zzr.zzc(parcelZzr, bundle);
        Parcel parcelZzs = zzs(8, parcelZzr);
        Bundle bundle2 = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzh(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(6);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        parcelZzr.writeString(str3);
        zzr.zzc(parcelZzr, bundle);
        Parcel parcelZzs = zzs(9, parcelZzr);
        Bundle bundle2 = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzi(int i, String str, String str2, String str3) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(3);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        parcelZzr.writeString(str3);
        Parcel parcelZzs = zzs(4, parcelZzr);
        Bundle bundle = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzj(int i, String str, String str2, String str3, Bundle bundle) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(i);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        parcelZzr.writeString(str3);
        zzr.zzc(parcelZzr, bundle);
        Parcel parcelZzs = zzs(11, parcelZzr);
        Bundle bundle2 = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzk(int i, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(3);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        zzr.zzc(parcelZzr, bundle);
        Parcel parcelZzs = zzs(2, parcelZzr);
        Bundle bundle2 = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final Bundle zzl(int i, String str, String str2, Bundle bundle, Bundle bundle2) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(i);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        zzr.zzc(parcelZzr, bundle);
        zzr.zzc(parcelZzr, bundle2);
        Parcel parcelZzs = zzs(901, parcelZzr);
        Bundle bundle3 = (Bundle) zzr.zza(parcelZzs, Bundle.CREATOR);
        parcelZzs.recycle();
        return bundle3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.play_billing.zzm
    public final void zzm(int i, String str, Bundle bundle, zzd zzdVar) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(21);
        parcelZzr.writeString(str);
        zzr.zzc(parcelZzr, bundle);
        parcelZzr.writeStrongBinder(zzdVar);
        zzu(1501, parcelZzr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.play_billing.zzm
    public final void zzn(int i, String str, Bundle bundle, zzf zzfVar) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(21);
        parcelZzr.writeString(str);
        zzr.zzc(parcelZzr, bundle);
        parcelZzr.writeStrongBinder(zzfVar);
        zzu(1601, parcelZzr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.play_billing.zzm
    public final void zzo(int i, String str, Bundle bundle, zzh zzhVar) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(18);
        parcelZzr.writeString(str);
        zzr.zzc(parcelZzr, bundle);
        parcelZzr.writeStrongBinder(zzhVar);
        zzt(1301, parcelZzr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.play_billing.zzm
    public final void zzp(int i, String str, Bundle bundle, zzj zzjVar) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(21);
        parcelZzr.writeString(str);
        zzr.zzc(parcelZzr, bundle);
        parcelZzr.writeStrongBinder(zzjVar);
        zzu(1401, parcelZzr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.play_billing.zzm
    public final void zzq(int i, String str, Bundle bundle, zzo zzoVar) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(12);
        parcelZzr.writeString(str);
        zzr.zzc(parcelZzr, bundle);
        parcelZzr.writeStrongBinder(zzoVar);
        zzt(1201, parcelZzr);
    }

    @Override // com.google.android.gms.internal.play_billing.zzm
    public final int zzv(int i, String str, String str2) throws RemoteException {
        Parcel parcelZzr = zzr();
        parcelZzr.writeInt(i);
        parcelZzr.writeString(str);
        parcelZzr.writeString(str2);
        Parcel parcelZzs = zzs(1, parcelZzr);
        int i2 = parcelZzs.readInt();
        parcelZzs.recycle();
        return i2;
    }
}
