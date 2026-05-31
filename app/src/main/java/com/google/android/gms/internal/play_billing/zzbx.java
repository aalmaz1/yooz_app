package com.google.android.gms.internal.play_billing;

import com.google.common.flogger.backend.google.GooglePlatform;
import com.google.common.flogger.backend.system.DefaultPlatform;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzbx {
    public static zzbw zza() {
        try {
            try {
                try {
                    return (zzbw) zzcb.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (IllegalAccessException | InstantiationException | NoClassDefFoundError | NoSuchMethodException | InvocationTargetException unused) {
                    return null;
                }
            } catch (IllegalAccessException | InstantiationException | NoClassDefFoundError | NoSuchMethodException | InvocationTargetException unused2) {
                return (zzbw) DefaultPlatform.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
        } catch (IllegalAccessException | InstantiationException | NoClassDefFoundError | NoSuchMethodException | InvocationTargetException unused3) {
            return (zzbw) GooglePlatform.class.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
    }
}
