package com.google.android.gms.internal.play_billing;

import android.os.Build;
import dalvik.system.VMStack;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzcb extends zzbw {
    private static final boolean zza = zza.zza();
    private static final boolean zzb;
    private static final zzbv zzc;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
    final class zza {
        zza() {
        }

        static boolean zza() {
            return zzcb.zzt();
        }
    }

    static {
        boolean z = true;
        if (Build.FINGERPRINT != null && !"robolectric".equals(Build.FINGERPRINT)) {
            z = false;
        }
        zzb = z;
        zzc = new zzbv() { // from class: com.google.android.gms.internal.play_billing.zzcb.1
            @Override // com.google.android.gms.internal.play_billing.zzbv
            public zzaz zza(Class<?> cls, int i) {
                return zzaz.zza;
            }

            @Override // com.google.android.gms.internal.play_billing.zzbv
            public String zzb(Class cls) {
                StackTraceElement stackTraceElementZza;
                if (zzcb.zza) {
                    try {
                        if (cls.equals(zzcb.zzp())) {
                            return VMStack.getStackClass2().getName();
                        }
                    } catch (Throwable unused) {
                    }
                }
                if (!zzcb.zzb || (stackTraceElementZza = zzcz.zza(cls, 1)) == null) {
                    return null;
                }
                return stackTraceElementZza.getClassName();
            }
        };
    }

    static Class<?> zzp() {
        return VMStack.getStackClass2();
    }

    static String zzq() {
        try {
            return VMStack.getStackClass2().getName();
        } catch (Throwable unused) {
            return null;
        }
    }

    static boolean zzt() {
        try {
            Class.forName("dalvik.system.VMStack").getMethod("getStackClass2", new Class[0]);
            return zza.class.getName().equals(zzq());
        } catch (Throwable unused) {
            return false;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzbw
    protected zzbf zze(String str) {
        return zzce.zzb(str);
    }

    @Override // com.google.android.gms.internal.play_billing.zzbw
    protected zzbv zzh() {
        return zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzbw
    protected zzcl zzj() {
        return zzcf.zzb();
    }

    @Override // com.google.android.gms.internal.play_billing.zzbw
    protected String zzm() {
        return "platform: Android";
    }
}
