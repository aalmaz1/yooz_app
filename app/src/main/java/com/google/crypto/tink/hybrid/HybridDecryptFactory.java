package com.google.crypto.tink.hybrid;

import com.google.crypto.tink.HybridDecrypt;
import com.google.crypto.tink.KeysetHandle;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public final class HybridDecryptFactory {
    @Deprecated
    public static HybridDecrypt getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
        HybridDecryptWrapper.register();
        return (HybridDecrypt) keysetHandle.getPrimitive(HybridDecrypt.class);
    }

    private HybridDecryptFactory() {
    }
}
