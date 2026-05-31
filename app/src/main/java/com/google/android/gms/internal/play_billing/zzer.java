package com.google.android.gms.internal.play_billing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
abstract class zzer {
    private static final Logger zza = Logger.getLogger(zzee.class.getName());
    private static final String zzb = "com.google.protobuf.BlazeGeneratedExtensionRegistryLiteLoader";

    zzer() {
    }

    static zzej zzb(Class cls) {
        String str;
        ClassLoader classLoader = zzer.class.getClassLoader();
        if (cls.equals(zzej.class)) {
            str = zzb;
        } else {
            if (!cls.getPackage().equals(zzer.class.getPackage())) {
                throw new IllegalArgumentException(cls.getName());
            }
            str = String.format("%s.BlazeGenerated%sLoader", cls.getPackage().getName(), cls.getSimpleName());
        }
        try {
            try {
                try {
                    try {
                        return (zzej) cls.cast(((zzer) Class.forName(str, true, classLoader).getConstructor(new Class[0]).newInstance(new Object[0])).zza());
                    } catch (InstantiationException e) {
                        throw new IllegalStateException(e);
                    } catch (NoSuchMethodException e2) {
                        throw new IllegalStateException(e2);
                    }
                } catch (InvocationTargetException e3) {
                    throw new IllegalStateException(e3);
                }
            } catch (IllegalAccessException e4) {
                throw new IllegalStateException(e4);
            }
        } catch (ClassNotFoundException unused) {
            Iterator it = ServiceLoader.load(zzer.class, classLoader).iterator();
            ArrayList arrayList = new ArrayList();
            while (it.hasNext()) {
                try {
                    arrayList.add((zzej) cls.cast(((zzer) it.next()).zza()));
                } catch (ServiceConfigurationError e5) {
                    zza.logp(Level.SEVERE, "com.google.protobuf.GeneratedExtensionRegistryLoader", "load", "Unable to load ".concat(String.valueOf(cls.getSimpleName())), (Throwable) e5);
                }
            }
            if (arrayList.size() == 1) {
                return (zzej) arrayList.get(0);
            }
            if (arrayList.size() == 0) {
                return null;
            }
            try {
                return (zzej) cls.getMethod("combine", Collection.class).invoke(null, arrayList);
            } catch (IllegalAccessException e6) {
                throw new IllegalStateException(e6);
            } catch (NoSuchMethodException e7) {
                throw new IllegalStateException(e7);
            } catch (InvocationTargetException e8) {
                throw new IllegalStateException(e8);
            }
        }
    }

    protected abstract zzej zza();
}
