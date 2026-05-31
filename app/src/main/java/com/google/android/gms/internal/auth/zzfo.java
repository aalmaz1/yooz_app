package com.google.android.gms.internal.auth;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzfo implements zzgi {
    private static final zzfu zza = new zzfm();
    private final zzfu zzb;

    public zzfo() {
        zzfu zzfuVar;
        zzfu[] zzfuVarArr = new zzfu[2];
        zzfuVarArr[0] = zzer.zza();
        try {
            zzfuVar = (zzfu) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzfuVar = zza;
        }
        zzfuVarArr[1] = zzfuVar;
        zzfn zzfnVar = new zzfn(zzfuVarArr);
        zzez.zzf(zzfnVar, "messageInfoFactory");
        this.zzb = zzfnVar;
    }

    private static boolean zzb(zzft zzftVar) {
        return zzftVar.zzc() == 1;
    }

    @Override // com.google.android.gms.internal.auth.zzgi
    public final zzgh zza(Class cls) {
        zzgj.zzg(cls);
        zzft zzftVarZzb = this.zzb.zzb(cls);
        return zzftVarZzb.zzb() ? zzeu.class.isAssignableFrom(cls) ? zzga.zzb(zzgj.zzc(), zzen.zzb(), zzftVarZzb.zza()) : zzga.zzb(zzgj.zza(), zzen.zza(), zzftVarZzb.zza()) : zzeu.class.isAssignableFrom(cls) ? zzb(zzftVarZzb) ? zzfz.zzj(cls, zzftVarZzb, zzgc.zzb(), zzfk.zzd(), zzgj.zzc(), zzen.zzb(), zzfs.zzb()) : zzfz.zzj(cls, zzftVarZzb, zzgc.zzb(), zzfk.zzd(), zzgj.zzc(), null, zzfs.zzb()) : zzb(zzftVarZzb) ? zzfz.zzj(cls, zzftVarZzb, zzgc.zza(), zzfk.zzc(), zzgj.zza(), zzen.zza(), zzfs.zza()) : zzfz.zzj(cls, zzftVarZzb, zzgc.zza(), zzfk.zzc(), zzgj.zzb(), null, zzfs.zza());
    }
}
