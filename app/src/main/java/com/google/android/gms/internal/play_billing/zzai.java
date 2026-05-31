package com.google.android.gms.internal.play_billing;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
import org.apache.commons.io.FileUtils;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzai implements Map, Serializable {

    @CheckForNull
    private transient zzaj zza;

    @CheckForNull
    private transient zzaj zzb;

    @CheckForNull
    private transient zzac zzc;

    zzai() {
    }

    @Override // java.util.Map
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean containsKey(@CheckForNull Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public final boolean containsValue(@CheckForNull Object obj) {
        return values().contains(obj);
    }

    @Override // java.util.Map
    public final boolean equals(@CheckForNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    @CheckForNull
    public abstract Object get(@CheckForNull Object obj);

    @Override // java.util.Map
    @CheckForNull
    public final Object getOrDefault(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Object obj3 = get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return zzar.zza(entrySet());
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Set keySet() {
        zzaj zzajVar = this.zzb;
        if (zzajVar != null) {
            return zzajVar;
        }
        zzaj zzajVarZzd = zzd();
        this.zzb = zzajVarZzd;
        return zzajVarZzd;
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final Object remove(@CheckForNull Object obj) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        int size = size();
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be negative but was: " + size);
        }
        StringBuilder sb = new StringBuilder((int) Math.min(((long) size) * 8, FileUtils.ONE_GB));
        sb.append('{');
        boolean z = true;
        for (Map.Entry entry : entrySet()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            z = false;
        }
        sb.append('}');
        return sb.toString();
    }

    abstract zzac zza();

    @Override // java.util.Map
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzac values() {
        zzac zzacVar = this.zzc;
        if (zzacVar != null) {
            return zzacVar;
        }
        zzac zzacVarZza = zza();
        this.zzc = zzacVarZza;
        return zzacVarZza;
    }

    abstract zzaj zzc();

    abstract zzaj zzd();

    @Override // java.util.Map
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final zzaj entrySet() {
        zzaj zzajVar = this.zza;
        if (zzajVar != null) {
            return zzajVar;
        }
        zzaj zzajVarZzc = zzc();
        this.zza = zzajVarZzc;
        return zzajVarZzc;
    }
}
