package com.google.crypto.tink.daead;

import com.google.crypto.tink.DeterministicAead;
import com.google.crypto.tink.KeysetHandle;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public final class DeterministicAeadFactory {
    @Deprecated
    public static DeterministicAead getPrimitive(KeysetHandle keysetHandle) throws GeneralSecurityException {
        DeterministicAeadWrapper.register();
        return (DeterministicAead) keysetHandle.getPrimitive(DeterministicAead.class);
    }

    private DeterministicAeadFactory() {
    }
}
