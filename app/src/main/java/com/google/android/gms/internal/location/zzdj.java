package com.google.android.gms.internal.location;

import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzdj {
    private static final SimpleDateFormat zza = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.ROOT);
    private static final SimpleDateFormat zzb = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.ROOT);
    private static final StringBuilder zzc = new StringBuilder(33);

    public static String zza(long j) {
        String string;
        StringBuilder sb = zzc;
        synchronized (sb) {
            sb.setLength(0);
            zzb(j, sb);
            string = sb.toString();
        }
        return string;
    }

    public static void zzb(long j, StringBuilder sb) {
        if (j == 0) {
            sb.append("0s");
            return;
        }
        sb.ensureCapacity(sb.length() + 27);
        boolean z = false;
        if (j < 0) {
            sb.append("-");
            if (j != Long.MIN_VALUE) {
                j = -j;
            } else {
                j = Long.MAX_VALUE;
                z = true;
            }
        }
        if (j >= DateUtils.MILLIS_PER_DAY) {
            sb.append(j / DateUtils.MILLIS_PER_DAY);
            sb.append("d");
            j %= DateUtils.MILLIS_PER_DAY;
        }
        if (true == z) {
            j = 25975808;
        }
        if (j >= DateUtils.MILLIS_PER_HOUR) {
            sb.append(j / DateUtils.MILLIS_PER_HOUR);
            sb.append("h");
            j %= DateUtils.MILLIS_PER_HOUR;
        }
        if (j >= DateUtils.MILLIS_PER_MINUTE) {
            sb.append(j / DateUtils.MILLIS_PER_MINUTE);
            sb.append("m");
            j %= DateUtils.MILLIS_PER_MINUTE;
        }
        if (j >= 1000) {
            sb.append(j / 1000);
            sb.append("s");
            j %= 1000;
        }
        if (j > 0) {
            sb.append(j);
            sb.append("ms");
        }
    }
}
