package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzfu implements zzgn {
    private static final zzga zza = new zzfs();
    private final zzga zzb;

    public zzfu() {
        zzga zzgaVar;
        zzga[] zzgaVarArr = new zzga[2];
        zzgaVarArr[0] = zzes.zza();
        try {
            zzgaVar = (zzga) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzgaVar = zza;
        }
        zzgaVarArr[1] = zzgaVar;
        zzft zzftVar = new zzft(zzgaVarArr);
        byte[] bArr = zzfd.zzd;
        this.zzb = zzftVar;
    }

    private static boolean zzb(zzfz zzfzVar) {
        return zzfzVar.zzc() + (-1) != 1;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgn
    public final zzgm zza(Class cls) {
        zzgo.zzq(cls);
        zzfz zzfzVarZzb = this.zzb.zzb(cls);
        return zzfzVarZzb.zzb() ? zzex.class.isAssignableFrom(cls) ? zzgg.zzc(zzgo.zzn(), zzem.zzb(), zzfzVarZzb.zza()) : zzgg.zzc(zzgo.zzm(), zzem.zza(), zzfzVarZzb.zza()) : zzex.class.isAssignableFrom(cls) ? zzb(zzfzVarZzb) ? zzgf.zzl(cls, zzfzVarZzb, zzgi.zzb(), zzfq.zzd(), zzgo.zzn(), zzem.zzb(), zzfy.zzb()) : zzgf.zzl(cls, zzfzVarZzb, zzgi.zzb(), zzfq.zzd(), zzgo.zzn(), null, zzfy.zzb()) : zzb(zzfzVarZzb) ? zzgf.zzl(cls, zzfzVarZzb, zzgi.zza(), zzfq.zzc(), zzgo.zzm(), zzem.zza(), zzfy.zza()) : zzgf.zzl(cls, zzfzVarZzb, zzgi.zza(), zzfq.zzc(), zzgo.zzm(), null, zzfy.zza());
    }
}
