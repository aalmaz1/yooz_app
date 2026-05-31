package com.google.android.gms.internal.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzfd extends zzdq implements RandomAccess, zzfe {
    public static final zzfe zza;
    private static final zzfd zzb;
    private final List zzc;

    static {
        zzfd zzfdVar = new zzfd(10);
        zzb = zzfdVar;
        zzfdVar.zzb();
        zza = zzfdVar;
    }

    public zzfd() {
        this(10);
    }

    private static String zzh(Object obj) {
        return obj instanceof String ? (String) obj : obj instanceof zzee ? ((zzee) obj).zzm(zzez.zzb) : zzez.zzh((byte[]) obj);
    }

    @Override // com.google.android.gms.internal.auth.zzdq, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ void add(int i, Object obj) {
        zza();
        this.zzc.add(i, (String) obj);
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.auth.zzdq, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        zza();
        if (collection instanceof zzfe) {
            collection = ((zzfe) collection).zzg();
        }
        boolean zAddAll = this.zzc.addAll(i, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // com.google.android.gms.internal.auth.zzdq, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zza();
        this.zzc.clear();
        this.modCount++;
    }

    @Override // com.google.android.gms.internal.auth.zzdq, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object remove(int i) {
        zza();
        Object objRemove = this.zzc.remove(i);
        this.modCount++;
        return zzh(objRemove);
    }

    @Override // com.google.android.gms.internal.auth.zzdq, java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object set(int i, Object obj) {
        zza();
        return zzh(this.zzc.set(i, (String) obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzc.size();
    }

    @Override // com.google.android.gms.internal.auth.zzey
    public final /* bridge */ /* synthetic */ zzey zzd(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzc);
        return new zzfd(arrayList);
    }

    @Override // com.google.android.gms.internal.auth.zzfe
    public final zzfe zze() {
        return zzc() ? new zzhd(this) : this;
    }

    @Override // java.util.AbstractList, java.util.List
    /* JADX INFO: renamed from: zzf, reason: merged with bridge method [inline-methods] */
    public final String get(int i) {
        Object obj = this.zzc.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzee) {
            zzee zzeeVar = (zzee) obj;
            String strZzm = zzeeVar.zzm(zzez.zzb);
            if (zzeeVar.zzh()) {
                this.zzc.set(i, strZzm);
            }
            return strZzm;
        }
        byte[] bArr = (byte[]) obj;
        String strZzh = zzez.zzh(bArr);
        if (zzez.zzi(bArr)) {
            this.zzc.set(i, strZzh);
        }
        return strZzh;
    }

    @Override // com.google.android.gms.internal.auth.zzfe
    public final List zzg() {
        return Collections.unmodifiableList(this.zzc);
    }

    public zzfd(int i) {
        this.zzc = new ArrayList(i);
    }

    private zzfd(ArrayList arrayList) {
        this.zzc = arrayList;
    }

    @Override // com.google.android.gms.internal.auth.zzdq, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        return addAll(size(), collection);
    }
}
