package com.google.android.gms.internal.auth;

import androidx.camera.video.AudioStats;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public enum zzho {
    INT(0),
    LONG(0L),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(AudioStats.AUDIO_AMPLITUDE_NONE)),
    BOOLEAN(false),
    STRING(""),
    BYTE_STRING(zzee.zzb),
    ENUM(null),
    MESSAGE(null);

    private final Object zzk;

    zzho(Object obj) {
        this.zzk = obj;
    }
}
