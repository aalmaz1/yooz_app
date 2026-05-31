package com.google.crypto.tink.signature;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.PublicKeySign;
import com.google.crypto.tink.internal.LegacyProtoKey;
import com.google.crypto.tink.internal.MonitoringClient;
import com.google.crypto.tink.internal.MonitoringUtil;
import com.google.crypto.tink.internal.MutableMonitoringRegistry;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveConstructor;
import com.google.crypto.tink.internal.PrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.internal.PrimitiveWrapper;
import com.google.crypto.tink.signature.internal.LegacyFullSign;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
public class PublicKeySignWrapper implements PrimitiveWrapper<PublicKeySign, PublicKeySign> {
    private static final PublicKeySignWrapper WRAPPER = new PublicKeySignWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, PublicKeySign> LEGACY_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.signature.PublicKeySignWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullSign.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, PublicKeySign.class);

    private static class WrappedPublicKeySign implements PublicKeySign {
        private final MonitoringClient.Logger logger;
        private final PrimitiveSet<PublicKeySign> primitives;

        public WrappedPublicKeySign(final PrimitiveSet<PublicKeySign> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                this.logger = MutableMonitoringRegistry.globalInstance().getMonitoringClient().createLogger(MonitoringUtil.getMonitoringKeysetInfo(primitives), "public_key_sign", "sign");
            } else {
                this.logger = MonitoringUtil.DO_NOTHING_LOGGER;
            }
        }

        @Override // com.google.crypto.tink.PublicKeySign
        public byte[] sign(final byte[] data) throws GeneralSecurityException {
            try {
                byte[] bArrSign = this.primitives.getPrimary().getFullPrimitive().sign(data);
                this.logger.log(this.primitives.getPrimary().getKeyId(), data.length);
                return bArrSign;
            } catch (GeneralSecurityException e) {
                this.logger.logFailure();
                throw e;
            }
        }
    }

    PublicKeySignWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public PublicKeySign wrap(final PrimitiveSet<PublicKeySign> primitives) {
        return new WrappedPublicKeySign(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<PublicKeySign> getPrimitiveClass() {
        return PublicKeySign.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<PublicKeySign> getInputPrimitiveClass() {
        return PublicKeySign.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
