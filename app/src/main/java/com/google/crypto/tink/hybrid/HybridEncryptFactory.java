package com.google.crypto.tink.hybrid;

import com.google.crypto.tink.HybridEncrypt;
import com.google.crypto.tink.KeysetHandle;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public final class HybridEncryptFactory {
    @Deprecated
    public static HybridEncrypt getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
        HybridEncryptWrapper.register();
        return (HybridEncrypt) keysetHandle.getPrimitive(HybridEncrypt.class);
    }

    private HybridEncryptFactory() {
    }
}
