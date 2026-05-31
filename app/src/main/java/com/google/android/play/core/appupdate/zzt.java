package com.google.android.play.core.appupdate;

import android.content.Context;
import java.io.File;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzt {
    private final Context zza;

    zzt(Context context) {
        this.zza = context;
    }

    private static long zzb(File file) {
        if (!file.isDirectory()) {
            return file.length();
        }
        File[] fileArrListFiles = file.listFiles();
        long jZzb = 0;
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                jZzb += zzb(file2);
            }
        }
        return jZzb;
    }

    final long zza() {
        return zzb(new File(this.zza.getFilesDir(), "assetpacks"));
    }
}
