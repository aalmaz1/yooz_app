package com.google.android.gms.internal.play_billing;

import com.google.android.gms.internal.play_billing.zzdf;
import com.google.android.gms.internal.play_billing.zzdg;
import java.io.IOException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzdg<MessageType extends zzdg<MessageType, BuilderType>, BuilderType extends zzdf<MessageType, BuilderType>> implements zzgc {
    protected int zza = 0;

    int zza(zzgm zzgmVar) {
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgc
    public final zzdw zzb() {
        try {
            int iZze = zze();
            zzdw zzdwVar = zzdw.zzb;
            byte[] bArr = new byte[iZze];
            zzee zzeeVarZzz = zzee.zzz(bArr, 0, iZze);
            zzr(zzeeVarZzz);
            zzeeVarZzz.zzA();
            return new zzdt(bArr);
        } catch (IOException e) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a ByteString threw an IOException (should never happen).", e);
        }
    }

    public final byte[] zzc() {
        try {
            int iZze = zze();
            byte[] bArr = new byte[iZze];
            zzee zzeeVarZzz = zzee.zzz(bArr, 0, iZze);
            zzr(zzeeVarZzz);
            zzeeVarZzz.zzA();
            return bArr;
        } catch (IOException e) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a byte array threw an IOException (should never happen).", e);
        }
    }
}
