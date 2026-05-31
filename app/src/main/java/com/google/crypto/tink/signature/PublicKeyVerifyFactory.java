package com.google.crypto.tink.signature;

import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.PublicKeyVerify;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public final class PublicKeyVerifyFactory {
    @Deprecated
    public static PublicKeyVerify getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
        PublicKeyVerifyWrapper.register();
        return (PublicKeyVerify) keysetHandle.getPrimitive(PublicKeyVerify.class);
    }

    private PublicKeyVerifyFactory() {
    }
}
