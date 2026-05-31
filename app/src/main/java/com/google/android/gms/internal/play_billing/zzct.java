package com.google.android.gms.internal.play_billing;

import java.util.Comparator;
import java.util.Map;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzct implements Comparator {
    zzct() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((String) ((Map.Entry) obj).getKey()).compareTo((String) ((Map.Entry) obj2).getKey());
    }
}
