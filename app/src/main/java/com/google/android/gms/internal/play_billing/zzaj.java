package com.google.android.gms.internal.play_billing;

import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzaj extends zzac implements Set {

    @CheckForNull
    private transient zzaf zza;

    zzaj() {
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this || obj == this) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            try {
                if (size() == set.size()) {
                    if (containsAll(set)) {
                        return true;
                    }
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        return zzar.zza(this);
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    public zzaf zzd() {
        zzaf zzafVar = this.zza;
        if (zzafVar != null) {
            return zzafVar;
        }
        zzaf zzafVarZzh = zzh();
        this.zza = zzafVarZzh;
        return zzafVarZzh;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* JADX INFO: renamed from: zze */
    public abstract zzas iterator();

    zzaf zzh() {
        Object[] array = toArray();
        int i = zzaf.zzd;
        return zzaf.zzi(array, array.length);
    }
}
