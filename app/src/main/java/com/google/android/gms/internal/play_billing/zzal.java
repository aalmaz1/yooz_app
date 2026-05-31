package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzal extends zzaf {
    static final zzaf zza = new zzal(new Object[0], 0);
    final transient Object[] zzb;
    private final transient int zzc;

    zzal(Object[] objArr, int i) {
        this.zzb = objArr;
        this.zzc = i;
    }

    @Override // java.util.List
    public final Object get(int i) {
        zzx.zza(i, this.zzc, "index");
        Object obj = this.zzb[i];
        obj.getClass();
        return obj;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzaf, com.google.android.gms.internal.play_billing.zzac
    final int zza(Object[] objArr, int i) {
        System.arraycopy(this.zzb, 0, objArr, 0, this.zzc);
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final int zzb() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final boolean zzf() {
        return false;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final Object[] zzg() {
        return this.zzb;
    }
}
