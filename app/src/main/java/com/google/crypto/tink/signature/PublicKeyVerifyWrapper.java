package com.google.crypto.tink.signature;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.PublicKeyVerify;
import com.google.crypto.tink.internal.LegacyProtoKey;
import com.google.crypto.tink.internal.MonitoringClient;
import com.google.crypto.tink.internal.MonitoringUtil;
import com.google.crypto.tink.internal.MutableMonitoringRegistry;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveConstructor;
import com.google.crypto.tink.internal.PrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.internal.PrimitiveWrapper;
import com.google.crypto.tink.signature.internal.LegacyFullVerify;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class PublicKeyVerifyWrapper implements PrimitiveWrapper<PublicKeyVerify, PublicKeyVerify> {
    private static final PublicKeyVerifyWrapper WRAPPER = new PublicKeyVerifyWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, PublicKeyVerify> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.signature.PublicKeyVerifyWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullVerify.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, PublicKeyVerify.class);

    private static class WrappedPublicKeyVerify implements PublicKeyVerify {
        private final MonitoringClient.Logger monitoringLogger;
        private final PrimitiveSet<PublicKeyVerify> primitives;

        public WrappedPublicKeyVerify(PrimitiveSet<PublicKeyVerify> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                this.monitoringLogger = MutableMonitoringRegistry.globalInstance().getMonitoringClient().createLogger(MonitoringUtil.getMonitoringKeysetInfo(primitives), "public_key_verify", "verify");
            } else {
                this.monitoringLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            }
        }

        @Override // com.google.crypto.tink.PublicKeyVerify
        public void verify(final byte[] signature, final byte[] data) throws GeneralSecurityException {
            if (signature.length <= 5) {
                this.monitoringLogger.logFailure();
                throw new GeneralSecurityException("signature too short");
            }
            for (PrimitiveSet.Entry<PublicKeyVerify> entry : this.primitives.getPrimitive(Arrays.copyOf(signature, 5))) {
                try {
                    entry.getFullPrimitive().verify(signature, data);
                    this.monitoringLogger.log(entry.getKeyId(), data.length);
                    return;
                } catch (GeneralSecurityException unused) {
                }
            }
            for (PrimitiveSet.Entry<PublicKeyVerify> entry2 : this.primitives.getRawPrimitives()) {
                try {
                    entry2.getFullPrimitive().verify(signature, data);
                    this.monitoringLogger.log(entry2.getKeyId(), data.length);
                    return;
                } catch (GeneralSecurityException unused2) {
                }
            }
            this.monitoringLogger.logFailure();
            throw new GeneralSecurityException("invalid signature");
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public PublicKeyVerify wrap(final PrimitiveSet<PublicKeyVerify> primitives) {
        return new WrappedPublicKeyVerify(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<PublicKeyVerify> getPrimitiveClass() {
        return PublicKeyVerify.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<PublicKeyVerify> getInputPrimitiveClass() {
        return PublicKeyVerify.class;
    }

    static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
