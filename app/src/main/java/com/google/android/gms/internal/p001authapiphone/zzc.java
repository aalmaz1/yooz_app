package com.google.android.gms.internal.p001authapiphone;

import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-api-phone@@17.5.1 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzc {
    private static final ClassLoader zza = zzc.class.getClassLoader();

    private zzc() {
    }

    public static <T extends Parcelable> T zza(Parcel parcel, Parcelable.Creator<T> creator) {
        if (parcel.readInt() == 0) {
            return null;
        }
        return creator.createFromParcel(parcel);
    }

    public static void zzb(Parcel parcel, IInterface iInterface) {
        if (iInterface == null) {
            parcel.writeStrongBinder(null);
        } else {
            parcel.writeStrongBinder(iInterface.asBinder());
        }
    }

    public static boolean zzc(Parcel parcel) {
        return parcel.readInt() != 0;
    }
}
