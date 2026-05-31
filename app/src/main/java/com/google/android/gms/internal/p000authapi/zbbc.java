package com.google.android.gms.internal.p000authapi;

import android.os.Build;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zbbc {
    public static final int zba;

    static {
        zba = Build.VERSION.SDK_INT >= 31 ? 33554432 : 0;
    }
}
