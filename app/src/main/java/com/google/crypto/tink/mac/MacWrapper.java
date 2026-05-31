package com.google.crypto.tink.mac;

import cn.baos.watch.sdk.database.DatabaseHelper;
import com.google.crypto.tink.Key;
import com.google.crypto.tink.Mac;
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
import com.google.crypto.tink.mac.internal.LegacyFullMac;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class MacWrapper implements PrimitiveWrapper<Mac, Mac> {
    private static final MacWrapper WRAPPER = new MacWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, Mac> LEGACY_FULL_MAC_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.mac.MacWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullMac.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, Mac.class);

    private static class WrappedMac implements Mac {
        private final MonitoringClient.Logger computeLogger;
        private final PrimitiveSet<Mac> primitives;
        private final MonitoringClient.Logger verifyLogger;

        private WrappedMac(PrimitiveSet<Mac> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                MonitoringClient monitoringClient = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
                MonitoringKeysetInfo monitoringKeysetInfo = MonitoringUtil.getMonitoringKeysetInfo(primitives);
                this.computeLogger = monitoringClient.createLogger(monitoringKeysetInfo, DatabaseHelper.COLUME_MAC, "compute");
                this.verifyLogger = monitoringClient.createLogger(monitoringKeysetInfo, DatabaseHelper.COLUME_MAC, "verify");
                return;
            }
            this.computeLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            this.verifyLogger = MonitoringUtil.DO_NOTHING_LOGGER;
        }

        @Override // com.google.crypto.tink.Mac
        public byte[] computeMac(final byte[] data) throws GeneralSecurityException {
            try {
                byte[] bArrComputeMac = this.primitives.getPrimary().getFullPrimitive().computeMac(data);
                this.computeLogger.log(this.primitives.getPrimary().getKeyId(), data.length);
                return bArrComputeMac;
            } catch (GeneralSecurityException e) {
                this.computeLogger.logFailure();
                throw e;
            }
        }

        @Override // com.google.crypto.tink.Mac
        public void verifyMac(final byte[] mac, final byte[] data) throws GeneralSecurityException {
            if (mac.length <= 5) {
                this.verifyLogger.logFailure();
                throw new GeneralSecurityException("tag too short");
            }
            for (PrimitiveSet.Entry<Mac> entry : this.primitives.getPrimitive(Arrays.copyOf(mac, 5))) {
                try {
                    entry.getFullPrimitive().verifyMac(mac, data);
                    this.verifyLogger.log(entry.getKeyId(), data.length);
                    return;
                } catch (GeneralSecurityException unused) {
                }
            }
            for (PrimitiveSet.Entry<Mac> entry2 : this.primitives.getRawPrimitives()) {
                try {
                    entry2.getFullPrimitive().verifyMac(mac, data);
                    this.verifyLogger.log(entry2.getKeyId(), data.length);
                    return;
                } catch (GeneralSecurityException unused2) {
                }
            }
            this.verifyLogger.logFailure();
            throw new GeneralSecurityException("invalid MAC");
        }
    }

    MacWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Mac wrap(final PrimitiveSet<Mac> primitives) throws GeneralSecurityException {
        return new WrappedMac(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<Mac> getPrimitiveClass() {
        return Mac.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<Mac> getInputPrimitiveClass() {
        return Mac.class;
    }

    static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_FULL_MAC_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
