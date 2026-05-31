package com.google.crypto.tink.internal;

import com.google.android.gms.security.ProviderInstaller;
import java.lang.reflect.InvocationTargetException;
import java.security.Provider;
import java.security.Security;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public final class ConscryptUtil {
    private static final String[] CONSCRYPT_PROVIDER_NAMES = {ProviderInstaller.PROVIDER_NAME, "AndroidOpenSSL", "Conscrypt"};

    @Nullable
    public static Provider providerOrNull() {
        for (String str : CONSCRYPT_PROVIDER_NAMES) {
            Provider provider = Security.getProvider(str);
            if (provider != null) {
                return provider;
            }
        }
        return null;
    }

    @Nullable
    public static Provider providerWithReflectionOrNull() {
        try {
            return (Provider) Class.forName("org.conscrypt.Conscrypt").getMethod("newProvider", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException unused) {
            return null;
        }
    }

    public static final boolean isConscryptProvider(Provider provider) {
        String name = provider.getName();
        return name.equals(ProviderInstaller.PROVIDER_NAME) || name.equals("AndroidOpenSSL") || name.equals("Conscrypt");
    }

    private ConscryptUtil() {
    }
}
