package com.google.android.gms.internal.auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzfq extends LinkedHashMap {
    private static final zzfq zza;
    private boolean zzb;

    static {
        zzfq zzfqVar = new zzfq();
        zza = zzfqVar;
        zzfqVar.zzb = false;
    }

    private zzfq() {
        this.zzb = true;
    }

    public static zzfq zza() {
        return zza;
    }

    private static int zzf(Object obj) {
        if (obj instanceof byte[]) {
            return zzez.zzb((byte[]) obj);
        }
        if (obj instanceof zzew) {
            throw new UnsupportedOperationException();
        }
        return obj.hashCode();
    }

    private final void zzg() {
        if (!this.zzb) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void clear() {
        zzg();
        super.clear();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        return isEmpty() ? Collections.emptySet() : super.entrySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (this == map) {
            return true;
        }
        if (size() != map.size()) {
            return false;
        }
        Iterator it = entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (!map.containsKey(entry.getKey())) {
                return false;
            }
            Object value = entry.getValue();
            Object obj2 = map.get(entry.getKey());
            if (!(((value instanceof byte[]) && (obj2 instanceof byte[])) ? Arrays.equals((byte[]) value, (byte[]) obj2) : value.equals(obj2))) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        Iterator it = entrySet().iterator();
        int iZzf = 0;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            iZzf += zzf(entry.getValue()) ^ zzf(entry.getKey());
        }
        return iZzf;
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        zzg();
        zzez.zze(obj);
        zzez.zze(obj2);
        return super.put(obj, obj2);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final void putAll(Map map) {
        zzg();
        for (Object obj : map.keySet()) {
            zzez.zze(obj);
            zzez.zze(map.get(obj));
        }
        super.putAll(map);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        zzg();
        return super.remove(obj);
    }

    public final zzfq zzb() {
        return isEmpty() ? new zzfq() : new zzfq(this);
    }

    public final void zzc() {
        this.zzb = false;
    }

    public final void zzd(zzfq zzfqVar) {
        zzg();
        if (zzfqVar.isEmpty()) {
            return;
        }
        putAll(zzfqVar);
    }

    public final boolean zze() {
        return this.zzb;
    }

    private zzfq(Map map) {
        super(map);
        this.zzb = true;
    }
}
