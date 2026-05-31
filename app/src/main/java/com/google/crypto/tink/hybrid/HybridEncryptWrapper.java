package com.google.crypto.tink.hybrid;

import com.google.crypto.tink.HybridEncrypt;
import com.google.crypto.tink.Key;
import com.google.crypto.tink.hybrid.internal.LegacyFullHybridEncrypt;
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

/* JADX INFO: loaded from: classes2.dex */
public class HybridEncryptWrapper implements PrimitiveWrapper<HybridEncrypt, HybridEncrypt> {
    private static final HybridEncryptWrapper WRAPPER = new HybridEncryptWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, HybridEncrypt> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.hybrid.HybridEncryptWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullHybridEncrypt.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, HybridEncrypt.class);

    private static class WrappedHybridEncrypt implements HybridEncrypt {
        private final MonitoringClient.Logger encLogger;
        final PrimitiveSet<HybridEncrypt> primitives;

        public WrappedHybridEncrypt(final PrimitiveSet<HybridEncrypt> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                this.encLogger = MutableMonitoringRegistry.globalInstance().getMonitoringClient().createLogger(MonitoringUtil.getMonitoringKeysetInfo(primitives), "hybrid_encrypt", "encrypt");
            } else {
                this.encLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            }
        }

        @Override // com.google.crypto.tink.HybridEncrypt
        public byte[] encrypt(final byte[] plaintext, final byte[] contextInfo) throws GeneralSecurityException {
            if (this.primitives.getPrimary() == null) {
                this.encLogger.logFailure();
                throw new GeneralSecurityException("keyset without primary key");
            }
            try {
                byte[] bArrEncrypt = this.primitives.getPrimary().getFullPrimitive().encrypt(plaintext, contextInfo);
                this.encLogger.log(this.primitives.getPrimary().getKeyId(), plaintext.length);
                return bArrEncrypt;
            } catch (GeneralSecurityException e) {
                this.encLogger.logFailure();
                throw e;
            }
        }
    }

    HybridEncryptWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public HybridEncrypt wrap(final PrimitiveSet<HybridEncrypt> primitives) {
        return new WrappedHybridEncrypt(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<HybridEncrypt> getPrimitiveClass() {
        return HybridEncrypt.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<HybridEncrypt> getInputPrimitiveClass() {
        return HybridEncrypt.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
