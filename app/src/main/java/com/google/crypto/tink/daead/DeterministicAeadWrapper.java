package com.google.crypto.tink.daead;

import com.google.crypto.tink.DeterministicAead;
import com.google.crypto.tink.Key;
import com.google.crypto.tink.daead.internal.LegacyFullDeterministicAead;
import com.google.crypto.tink.internal.LegacyProtoKey;
import com.google.crypto.tink.internal.MonitoringClient;
import com.google.crypto.tink.internal.MonitoringKeysetInfo;
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
public class DeterministicAeadWrapper implements PrimitiveWrapper<DeterministicAead, DeterministicAead> {
    private static final DeterministicAeadWrapper WRAPPER = new DeterministicAeadWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, DeterministicAead> LEGACY_FULL_DAEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.daead.DeterministicAeadWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullDeterministicAead.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, DeterministicAead.class);

    private static class WrappedDeterministicAead implements DeterministicAead {
        private final MonitoringClient.Logger decLogger;
        private final MonitoringClient.Logger encLogger;
        private final PrimitiveSet<DeterministicAead> primitives;

        public WrappedDeterministicAead(PrimitiveSet<DeterministicAead> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                MonitoringClient monitoringClient = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
                MonitoringKeysetInfo monitoringKeysetInfo = MonitoringUtil.getMonitoringKeysetInfo(primitives);
                this.encLogger = monitoringClient.createLogger(monitoringKeysetInfo, "daead", "encrypt");
                this.decLogger = monitoringClient.createLogger(monitoringKeysetInfo, "daead", "decrypt");
                return;
            }
            this.encLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            this.decLogger = MonitoringUtil.DO_NOTHING_LOGGER;
        }

        @Override // com.google.crypto.tink.DeterministicAead
        public byte[] encryptDeterministically(final byte[] plaintext, final byte[] associatedData) throws GeneralSecurityException {
            try {
                byte[] bArrEncryptDeterministically = this.primitives.getPrimary().getFullPrimitive().encryptDeterministically(plaintext, associatedData);
                this.encLogger.log(this.primitives.getPrimary().getKeyId(), plaintext.length);
                return bArrEncryptDeterministically;
            } catch (GeneralSecurityException e) {
                this.encLogger.logFailure();
                throw e;
            }
        }

        @Override // com.google.crypto.tink.DeterministicAead
        public byte[] decryptDeterministically(final byte[] ciphertext, final byte[] associatedData) throws GeneralSecurityException {
            if (ciphertext.length > 5) {
                for (PrimitiveSet.Entry<DeterministicAead> entry : this.primitives.getPrimitive(Arrays.copyOf(ciphertext, 5))) {
                    try {
                        byte[] bArrDecryptDeterministically = entry.getFullPrimitive().decryptDeterministically(ciphertext, associatedData);
                        this.decLogger.log(entry.getKeyId(), ciphertext.length);
                        return bArrDecryptDeterministically;
                    } catch (GeneralSecurityException unused) {
                    }
                }
            }
            for (PrimitiveSet.Entry<DeterministicAead> entry2 : this.primitives.getRawPrimitives()) {
                try {
                    byte[] bArrDecryptDeterministically2 = entry2.getFullPrimitive().decryptDeterministically(ciphertext, associatedData);
                    this.decLogger.log(entry2.getKeyId(), ciphertext.length);
                    return bArrDecryptDeterministically2;
                } catch (GeneralSecurityException unused2) {
                }
            }
            this.decLogger.logFailure();
            throw new GeneralSecurityException("decryption failed");
        }
    }

    DeterministicAeadWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public DeterministicAead wrap(final PrimitiveSet<DeterministicAead> primitives) {
        return new WrappedDeterministicAead(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<DeterministicAead> getPrimitiveClass() {
        return DeterministicAead.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<DeterministicAead> getInputPrimitiveClass() {
        return DeterministicAead.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_FULL_DAEAD_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
