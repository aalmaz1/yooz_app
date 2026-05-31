package com.google.android.gms.internal.play_billing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzfm extends zzfq {
    private static final Class zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzfm() {
        super(null);
    }

    /* synthetic */ zzfm(zzfl zzflVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.play_billing.zzfq
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzhn.zzf(obj, j);
        if (list instanceof zzfk) {
            objUnmodifiableList = ((zzfk) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzgj) && (list instanceof zzfc)) {
                zzfc zzfcVar = (zzfc) list;
                if (zzfcVar.zzc()) {
                    zzfcVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzhn.zzs(obj, j, objUnmodifiableList);
    }

    @Override // com.google.android.gms.internal.play_billing.zzfq
    final void zzb(Object obj, Object obj2, long j) {
        List list;
        List list2;
        List list3 = (List) zzhn.zzf(obj2, j);
        int size = list3.size();
        List list4 = (List) zzhn.zzf(obj, j);
        if (list4.isEmpty()) {
            List zzfjVar = list4 instanceof zzfk ? new zzfj(size) : ((list4 instanceof zzgj) && (list4 instanceof zzfc)) ? ((zzfc) list4).zzd(size) : new ArrayList(size);
            zzhn.zzs(obj, j, zzfjVar);
            list2 = zzfjVar;
        } else {
            if (zza.isAssignableFrom(list4.getClass())) {
                ArrayList arrayList = new ArrayList(list4.size() + size);
                arrayList.addAll(list4);
                zzhn.zzs(obj, j, arrayList);
                list = arrayList;
            } else if (list4 instanceof zzhi) {
                zzfj zzfjVar2 = new zzfj(list4.size() + size);
                zzfjVar2.addAll(zzfjVar2.size(), (zzhi) list4);
                zzhn.zzs(obj, j, zzfjVar2);
                list = zzfjVar2;
            } else {
                boolean z = list4 instanceof zzgj;
                list2 = list4;
                if (z) {
                    boolean z2 = list4 instanceof zzfc;
                    list2 = list4;
                    if (z2) {
                        zzfc zzfcVar = (zzfc) list4;
                        list2 = list4;
                        if (!zzfcVar.zzc()) {
                            zzfc zzfcVarZzd = zzfcVar.zzd(list4.size() + size);
                            zzhn.zzs(obj, j, zzfcVarZzd);
                            list2 = zzfcVarZzd;
                        }
                    }
                }
            }
            list2 = list;
        }
        int size2 = list2.size();
        int size3 = list3.size();
        if (size2 > 0 && size3 > 0) {
            list2.addAll(list3);
        }
        if (size2 > 0) {
            list3 = list2;
        }
        zzhn.zzs(obj, j, list3);
    }
}
