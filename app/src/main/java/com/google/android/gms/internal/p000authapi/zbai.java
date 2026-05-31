package com.google.android.gms.internal.p000authapi;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zbai extends zba implements IInterface {
    zbai(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.identity.internal.ISignInService");
    }

    public final void zbc(zby zbyVar, BeginSignInRequest beginSignInRequest) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbyVar);
        zbc.zbc(parcelZba, beginSignInRequest);
        zbb(1, parcelZba);
    }

    public final void zbd(zbab zbabVar, GetPhoneNumberHintIntentRequest getPhoneNumberHintIntentRequest, String str) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbabVar);
        zbc.zbc(parcelZba, getPhoneNumberHintIntentRequest);
        parcelZba.writeString(str);
        zbb(4, parcelZba);
    }

    public final void zbe(zbad zbadVar, GetSignInIntentRequest getSignInIntentRequest) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbadVar);
        zbc.zbc(parcelZba, getSignInIntentRequest);
        zbb(3, parcelZba);
    }

    public final void zbf(IStatusCallback iStatusCallback, String str) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, iStatusCallback);
        parcelZba.writeString(str);
        zbb(2, parcelZba);
    }
}
