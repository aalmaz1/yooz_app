package com.google.crypto.tink.aead;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.Key;
import com.google.crypto.tink.aead.internal.LegacyFullAead;
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
public class AeadWrapper implements PrimitiveWrapper<Aead, Aead> {
    private static final AeadWrapper WRAPPER = new AeadWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, Aead> LEGACY_FULL_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.aead.AeadWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullAead.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, Aead.class);

    private static class WrappedAead implements Aead {
        private final MonitoringClient.Logger decLogger;
        private final MonitoringClient.Logger encLogger;
        private final PrimitiveSet<Aead> pSet;

        private WrappedAead(PrimitiveSet<Aead> pSet) {
            this.pSet = pSet;
            if (pSet.hasAnnotations()) {
                MonitoringClient monitoringClient = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
                MonitoringKeysetInfo monitoringKeysetInfo = MonitoringUtil.getMonitoringKeysetInfo(pSet);
                this.encLogger = monitoringClient.createLogger(monitoringKeysetInfo, "aead", "encrypt");
                this.decLogger = monitoringClient.createLogger(monitoringKeysetInfo, "aead", "decrypt");
                return;
            }
            this.encLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            this.decLogger = MonitoringUtil.DO_NOTHING_LOGGER;
        }

        @Override // com.google.crypto.tink.Aead
        public byte[] encrypt(final byte[] plaintext, final byte[] associatedData) throws GeneralSecurityException {
            try {
                byte[] bArrEncrypt = this.pSet.getPrimary().getFullPrimitive().encrypt(plaintext, associatedData);
                this.encLogger.log(this.pSet.getPrimary().getKeyId(), plaintext.length);
                return bArrEncrypt;
            } catch (GeneralSecurityException e) {
                this.encLogger.logFailure();
                throw e;
            }
        }

        @Override // com.google.crypto.tink.Aead
        public byte[] decrypt(final byte[] ciphertext, final byte[] associatedData) throws GeneralSecurityException {
            if (ciphertext.length > 5) {
                for (PrimitiveSet.Entry<Aead> entry : this.pSet.getPrimitive(Arrays.copyOf(ciphertext, 5))) {
                    try {
                        byte[] bArrDecrypt = entry.getFullPrimitive().decrypt(ciphertext, associatedData);
                        this.decLogger.log(entry.getKeyId(), ciphertext.length);
                        return bArrDecrypt;
                    } catch (GeneralSecurityException unused) {
                    }
                }
            }
            for (PrimitiveSet.Entry<Aead> entry2 : this.pSet.getRawPrimitives()) {
                try {
                    byte[] bArrDecrypt2 = entry2.getFullPrimitive().decrypt(ciphertext, associatedData);
                    this.decLogger.log(entry2.getKeyId(), ciphertext.length);
                    return bArrDecrypt2;
                } catch (GeneralSecurityException unused2) {
                }
            }
            this.decLogger.logFailure();
            throw new GeneralSecurityException("decryption failed");
        }
    }

    AeadWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Aead wrap(final PrimitiveSet<Aead> pset) throws GeneralSecurityException {
        return new WrappedAead(pset);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<Aead> getPrimitiveClass() {
        return Aead.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<Aead> getInputPrimitiveClass() {
        return Aead.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_FULL_AEAD_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
