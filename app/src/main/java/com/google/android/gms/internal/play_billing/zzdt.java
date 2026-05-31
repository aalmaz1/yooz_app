package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
class zzdt extends zzds {
    protected final byte[] zza;

    zzdt(byte[] bArr) {
        bArr.getClass();
        this.zza = bArr;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdw) || zzd() != ((zzdw) obj).zzd()) {
            return false;
        }
        if (zzd() == 0) {
            return true;
        }
        if (!(obj instanceof zzdt)) {
            return obj.equals(this);
        }
        zzdt zzdtVar = (zzdt) obj;
        int iZzk = zzk();
        int iZzk2 = zzdtVar.zzk();
        if (iZzk != 0 && iZzk2 != 0 && iZzk != iZzk2) {
            return false;
        }
        int iZzd = zzd();
        if (iZzd > zzdtVar.zzd()) {
            throw new IllegalArgumentException("Length too large: " + iZzd + zzd());
        }
        if (iZzd > zzdtVar.zzd()) {
            throw new IllegalArgumentException("Ran off end of other: 0, " + iZzd + ", " + zzdtVar.zzd());
        }
        if (!(zzdtVar instanceof zzdt)) {
            return zzdtVar.zzf(0, iZzd).equals(zzf(0, iZzd));
        }
        byte[] bArr = this.zza;
        byte[] bArr2 = zzdtVar.zza;
        zzdtVar.zzc();
        int i = 0;
        int i2 = 0;
        while (i < iZzd) {
            if (bArr[i] != bArr2[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    public byte zza(int i) {
        return this.zza[i];
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    byte zzb(int i) {
        return this.zza[i];
    }

    protected int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    public int zzd() {
        return this.zza.length;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    protected final int zze(int i, int i2, int i3) {
        return zzfd.zzb(i, this.zza, 0, i3);
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    public final zzdw zzf(int i, int i2) {
        int iZzj = zzj(0, i2, zzd());
        return iZzj == 0 ? zzdw.zzb : new zzdq(this.zza, 0, iZzj);
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    protected final String zzg(Charset charset) {
        return new String(this.zza, 0, zzd(), charset);
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    final void zzh(zzdm zzdmVar) throws IOException {
        ((zzeb) zzdmVar).zzc(this.zza, 0, zzd());
    }

    @Override // com.google.android.gms.internal.play_billing.zzdw
    public final boolean zzi() {
        return zzhs.zze(this.zza, 0, zzd());
    }
}
