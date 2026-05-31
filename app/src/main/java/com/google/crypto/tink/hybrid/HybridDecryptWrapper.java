package com.google.crypto.tink.hybrid;

import com.google.crypto.tink.HybridDecrypt;
import com.google.crypto.tink.Key;
import com.google.crypto.tink.hybrid.internal.LegacyFullHybridDecrypt;
import com.google.crypto.tink.internal.LegacyProtoKey;
import com.google.crypto.tink.internal.MonitoringClient;
import com.google.crypto.tink.internal.MonitoringUtil;
import com.google.crypto.tink.internal.MutableMonitoringRegistry;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveConstructor;
import com.google.crypto.tink.internal.PrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.internal.PrimitiveWrapper;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class HybridDecryptWrapper implements PrimitiveWrapper<HybridDecrypt, HybridDecrypt> {
    private static final HybridDecryptWrapper WRAPPER = new HybridDecryptWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, HybridDecrypt> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.hybrid.HybridDecryptWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullHybridDecrypt.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, HybridDecrypt.class);

    private static class WrappedHybridDecrypt implements HybridDecrypt {
        private final MonitoringClient.Logger decLogger;
        private final PrimitiveSet<HybridDecrypt> primitives;

        public WrappedHybridDecrypt(final PrimitiveSet<HybridDecrypt> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                this.decLogger = MutableMonitoringRegistry.globalInstance().getMonitoringClient().createLogger(MonitoringUtil.getMonitoringKeysetInfo(primitives), "hybrid_decrypt", "decrypt");
            } else {
                this.decLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            }
        }

        @Override // com.google.crypto.tink.HybridDecrypt
        public byte[] decrypt(final byte[] ciphertext, final byte[] contextInfo) throws GeneralSecurityException {
            if (ciphertext.length > 5) {
                for (PrimitiveSet.Entry<HybridDecrypt> entry : this.primitives.getPrimitive(Arrays.copyOfRange(ciphertext, 0, 5))) {
                    try {
                        byte[] bArrDecrypt = entry.getFullPrimitive().decrypt(ciphertext, contextInfo);
                        this.decLogger.log(entry.getKeyId(), ciphertext.length);
                        return bArrDecrypt;
                    } catch (GeneralSecurityException unused) {
                    }
                }
            }
            for (PrimitiveSet.Entry<HybridDecrypt> entry2 : this.primitives.getRawPrimitives()) {
                try {
                    byte[] bArrDecrypt2 = entry2.getFullPrimitive().decrypt(ciphertext, contextInfo);
                    this.decLogger.log(entry2.getKeyId(), ciphertext.length);
                    return bArrDecrypt2;
                } catch (GeneralSecurityException unused2) {
                }
            }
            this.decLogger.logFailure();
            throw new GeneralSecurityException("decryption failed");
        }
    }

    HybridDecryptWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public HybridDecrypt wrap(final PrimitiveSet<HybridDecrypt> primitives) {
        return new WrappedHybridDecrypt(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<HybridDecrypt> getPrimitiveClass() {
        return HybridDecrypt.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<HybridDecrypt> getInputPrimitiveClass() {
        return HybridDecrypt.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
