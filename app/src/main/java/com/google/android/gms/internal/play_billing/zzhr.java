package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzhr extends IllegalArgumentException {
    zzhr(int i, int i2) {
        super("Unpaired surrogate at index " + i + " of " + i2);
    }
}
