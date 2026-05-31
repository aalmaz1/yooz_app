package com.google.crypto.tink.prf;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.internal.LegacyProtoKey;
import com.google.crypto.tink.internal.MonitoringClient;
import com.google.crypto.tink.internal.MonitoringUtil;
import com.google.crypto.tink.internal.MutableMonitoringRegistry;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveConstructor;
import com.google.crypto.tink.internal.PrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.internal.PrimitiveWrapper;
import com.google.crypto.tink.prf.internal.LegacyFullPrf;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
public class PrfSetWrapper implements PrimitiveWrapper<Prf, PrfSet> {
    private static final PrfSetWrapper WRAPPER = new PrfSetWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, Prf> LEGACY_FULL_PRF_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.prf.PrfSetWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullPrf.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, Prf.class);

    private static class WrappedPrfSet extends PrfSet {
        private final Map<Integer, Prf> keyIdToPrfMap;
        private final int primaryKeyId;

        @Immutable
        private static class PrfWithMonitoring implements Prf {
            private final int keyId;
            private final MonitoringClient.Logger logger;
            private final Prf prf;

            @Override // com.google.crypto.tink.prf.Prf
            public byte[] compute(byte[] input, int outputLength) throws GeneralSecurityException {
                try {
                    byte[] bArrCompute = this.prf.compute(input, outputLength);
                    this.logger.log(this.keyId, input.length);
                    return bArrCompute;
                } catch (GeneralSecurityException e) {
                    this.logger.logFailure();
                    throw e;
                }
            }

            public PrfWithMonitoring(Prf prf, int keyId, MonitoringClient.Logger logger) {
                this.prf = prf;
                this.keyId = keyId;
                this.logger = logger;
            }
        }

        private WrappedPrfSet(PrimitiveSet<Prf> primitives) throws GeneralSecurityException {
            MonitoringClient.Logger loggerCreateLogger;
            if (primitives.getRawPrimitives().isEmpty()) {
                throw new GeneralSecurityException("No primitives provided.");
            }
            if (primitives.getPrimary() == null) {
                throw new GeneralSecurityException("Primary key not set.");
            }
            if (primitives.hasAnnotations()) {
                loggerCreateLogger = MutableMonitoringRegistry.globalInstance().getMonitoringClient().createLogger(MonitoringUtil.getMonitoringKeysetInfo(primitives), "prf", "compute");
            } else {
                loggerCreateLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            }
            this.primaryKeyId = primitives.getPrimary().getKeyId();
            List<PrimitiveSet.Entry<Prf>> rawPrimitives = primitives.getRawPrimitives();
            HashMap map = new HashMap();
            for (PrimitiveSet.Entry<Prf> entry : rawPrimitives) {
                map.put(Integer.valueOf(entry.getKeyId()), new PrfWithMonitoring(entry.getFullPrimitive(), entry.getKeyId(), loggerCreateLogger));
            }
            this.keyIdToPrfMap = Collections.unmodifiableMap(map);
        }

        @Override // com.google.crypto.tink.prf.PrfSet
        public int getPrimaryId() {
            return this.primaryKeyId;
        }

        @Override // com.google.crypto.tink.prf.PrfSet
        public Map<Integer, Prf> getPrfs() throws GeneralSecurityException {
            return this.keyIdToPrfMap;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public PrfSet wrap(PrimitiveSet<Prf> set) throws GeneralSecurityException {
        return new WrappedPrfSet(set);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<PrfSet> getPrimitiveClass() {
        return PrfSet.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<Prf> getInputPrimitiveClass() {
        return Prf.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_FULL_PRF_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
