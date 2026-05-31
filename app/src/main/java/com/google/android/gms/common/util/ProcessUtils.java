package com.google.android.gms.common.util;

import android.app.Application;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
/* JADX INFO: loaded from: classes2.dex */
public class ProcessUtils {

    @Nullable
    private static String zza;
    private static int zzb;

    private ProcessUtils() {
    }

    public static String getMyProcessName() {
        if (zza == null) {
            zza = Application.getProcessName();
        }
        return zza;
    }
}
