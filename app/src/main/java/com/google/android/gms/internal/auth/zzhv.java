package com.google.android.gms.internal.auth;

import android.util.Base64;
import androidx.camera.video.AudioStats;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzhv implements zzht {
    public static final zzdc zza;
    public static final zzdc zzb;
    public static final zzdc zzc;
    public static final zzdc zzd;
    public static final zzdc zze;
    public static final zzdc zzf;
    public static final zzdc zzg;
    public static final zzdc zzh;
    public static final zzdc zzi;
    public static final zzdc zzj;
    public static final zzdc zzk;
    public static final zzdc zzl;
    public static final zzdc zzm;
    public static final zzdc zzn;

    static {
        zzcz zzczVarZza = new zzcz(zzcr.zza("com.google.android.gms.auth_account")).zzb().zza();
        zza = zzczVarZza.zzc("getTokenRefactor__account_data_service_sample_percentage", AudioStats.AUDIO_AMPLITUDE_NONE);
        zzb = zzczVarZza.zze("getTokenRefactor__account_data_service_tokenAPI_usable", true);
        zzc = zzczVarZza.zzd("getTokenRefactor__account_manager_timeout_seconds", 20L);
        zzd = zzczVarZza.zzd("getTokenRefactor__android_id_shift", 0L);
        zze = zzczVarZza.zze("getTokenRefactor__authenticator_logic_improved", false);
        try {
            zzf = zzczVarZza.zzf("getTokenRefactor__blocked_packages", zzhr.zzk(Base64.decode("ChNjb20uYW5kcm9pZC52ZW5kaW5nCiBjb20uZ29vZ2xlLmFuZHJvaWQuYXBwcy5tZWV0aW5ncwohY29tLmdvb2dsZS5hbmRyb2lkLmFwcHMubWVzc2FnaW5n", 3)), zzhu.zza);
            zzg = zzczVarZza.zze("getTokenRefactor__chimera_get_token_evolved", true);
            zzh = zzczVarZza.zzd("getTokenRefactor__clear_token_timeout_seconds", 20L);
            zzi = zzczVarZza.zzd("getTokenRefactor__default_task_timeout_seconds", 20L);
            zzj = zzczVarZza.zze("getTokenRefactor__gaul_accounts_api_evolved", false);
            zzk = zzczVarZza.zze("getTokenRefactor__gaul_token_api_evolved", false);
            zzl = zzczVarZza.zzd("getTokenRefactor__get_token_timeout_seconds", 120L);
            zzm = zzczVarZza.zze("getTokenRefactor__gms_account_authenticator_evolved", true);
            zzn = zzczVarZza.zzc("getTokenRefactor__gms_account_authenticator_sample_percentage", AudioStats.AUDIO_AMPLITUDE_NONE);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.google.android.gms.internal.auth.zzht
    public final zzhr zza() {
        return (zzhr) zzf.zzb();
    }

    @Override // com.google.android.gms.internal.auth.zzht
    public final boolean zzb() {
        return ((Boolean) zzj.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.auth.zzht
    public final boolean zzc() {
        return ((Boolean) zzk.zzb()).booleanValue();
    }
}
