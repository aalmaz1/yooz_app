package com.google.android.gms.internal.play_billing;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzc extends zzq implements zzd {
    public zzc() {
        super("com.android.vending.billing.IInAppBillingCreateAlternativeBillingOnlyTokenCallback");
    }

    @Override // com.google.android.gms.internal.play_billing.zzq
    protected final boolean zzb(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (i != 1) {
            return false;
        }
        Bundle bundle = (Bundle) zzr.zza(parcel, Bundle.CREATOR);
        zzr.zzb(parcel);
        zza(bundle);
        return true;
    }
}
