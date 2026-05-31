package com.google.crypto.tink.keyderivation.internal;

import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.internal.PrimitiveWrapper;
import com.google.crypto.tink.keyderivation.KeysetDeriver;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
public final class KeysetDeriverWrapper implements PrimitiveWrapper<KeyDeriver, KeysetDeriver> {
    private static final KeysetDeriverWrapper WRAPPER = new KeysetDeriverWrapper();

    private static void validate(PrimitiveSet<KeyDeriver> primitiveSet) throws GeneralSecurityException {
        if (primitiveSet.getPrimary() == null) {
            throw new GeneralSecurityException("Primitive set has no primary.");
        }
    }

    @Immutable
    private static class WrappedKeysetDeriver implements KeysetDeriver {
        private final PrimitiveSet<KeyDeriver> primitiveSet;

        private WrappedKeysetDeriver(PrimitiveSet<KeyDeriver> primitiveSet) {
            this.primitiveSet = primitiveSet;
        }

        private static KeysetHandle.Builder.Entry deriveAndGetEntry(byte[] salt, PrimitiveSet.Entry<KeyDeriver> entry, int primaryKeyId) throws GeneralSecurityException {
            KeyDeriver fullPrimitive = entry.getFullPrimitive();
            if (fullPrimitive == null) {
                throw new GeneralSecurityException("Primitive set has non-full primitives -- this is probably a bug");
            }
            KeysetHandle.Builder.Entry entryImportKey = KeysetHandle.importKey(fullPrimitive.deriveKey(salt));
            entryImportKey.withFixedId(entry.getKeyId());
            if (entry.getKeyId() == primaryKeyId) {
                entryImportKey.makePrimary();
            }
            return entryImportKey;
        }

        @Override // com.google.crypto.tink.keyderivation.KeysetDeriver
        public KeysetHandle deriveKeyset(byte[] salt) throws GeneralSecurityException {
            KeysetHandle.Builder builderNewBuilder = KeysetHandle.newBuilder();
            Iterator<PrimitiveSet.Entry<KeyDeriver>> it = this.primitiveSet.getAllInKeysetOrder().iterator();
            while (it.hasNext()) {
                builderNewBuilder.addEntry(deriveAndGetEntry(salt, it.next(), this.primitiveSet.getPrimary().getKeyId()));
            }
            return builderNewBuilder.build();
        }
    }

    KeysetDeriverWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public KeysetDeriver wrap(final PrimitiveSet<KeyDeriver> primitiveSet) throws GeneralSecurityException {
        validate(primitiveSet);
        return new WrappedKeysetDeriver(primitiveSet);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<KeysetDeriver> getPrimitiveClass() {
        return KeysetDeriver.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<KeyDeriver> getInputPrimitiveClass() {
        return KeyDeriver.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
    }
}
