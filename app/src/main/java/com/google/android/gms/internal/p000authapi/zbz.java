package com.google.android.gms.internal.p000authapi;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zbz extends zba implements IInterface {
    zbz(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.identity.internal.ICredentialSavingService");
    }

    public final void zbc(zbaf zbafVar, SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequest) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbafVar);
        zbc.zbc(parcelZba, saveAccountLinkingTokenRequest);
        zbb(1, parcelZba);
    }

    public final void zbd(zbah zbahVar, SavePasswordRequest savePasswordRequest) throws RemoteException {
        Parcel parcelZba = zba();
        zbc.zbd(parcelZba, zbahVar);
        zbc.zbc(parcelZba, savePasswordRequest);
        zbb(2, parcelZba);
    }
}
