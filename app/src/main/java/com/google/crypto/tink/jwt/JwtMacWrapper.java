package com.google.crypto.tink.jwt;

import com.google.crypto.tink.internal.MonitoringClient;
import com.google.crypto.tink.internal.MonitoringKeysetInfo;
import com.google.crypto.tink.internal.MonitoringUtil;
import com.google.crypto.tink.internal.MutableMonitoringRegistry;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.internal.PrimitiveWrapper;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
class JwtMacWrapper implements PrimitiveWrapper<JwtMac, JwtMac> {
    private static final JwtMacWrapper WRAPPER = new JwtMacWrapper();

    private static void validate(PrimitiveSet<JwtMac> primitiveSet) throws GeneralSecurityException {
        if (primitiveSet.getPrimary() == null) {
            throw new GeneralSecurityException("Primitive set has no primary.");
        }
    }

    @Immutable
    private static class WrappedJwtMac implements JwtMac {
        private final MonitoringClient.Logger computeLogger;
        private final PrimitiveSet<JwtMac> primitives;
        private final MonitoringClient.Logger verifyLogger;

        private WrappedJwtMac(PrimitiveSet<JwtMac> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                MonitoringClient monitoringClient = MutableMonitoringRegistry.globalInstance().getMonitoringClient();
                MonitoringKeysetInfo monitoringKeysetInfo = MonitoringUtil.getMonitoringKeysetInfo(primitives);
                this.computeLogger = monitoringClient.createLogger(monitoringKeysetInfo, "jwtmac", "compute");
                this.verifyLogger = monitoringClient.createLogger(monitoringKeysetInfo, "jwtmac", "verify");
                return;
            }
            this.computeLogger = MonitoringUtil.DO_NOTHING_LOGGER;
            this.verifyLogger = MonitoringUtil.DO_NOTHING_LOGGER;
        }

        @Override // com.google.crypto.tink.jwt.JwtMac
        public String computeMacAndEncode(RawJwt token) throws GeneralSecurityException {
            try {
                String strComputeMacAndEncode = this.primitives.getPrimary().getFullPrimitive().computeMacAndEncode(token);
                this.computeLogger.log(this.primitives.getPrimary().getKeyId(), 1L);
                return strComputeMacAndEncode;
            } catch (GeneralSecurityException e) {
                this.computeLogger.logFailure();
                throw e;
            }
        }

        @Override // com.google.crypto.tink.jwt.JwtMac
        public VerifiedJwt verifyMacAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException {
            Iterator<List<PrimitiveSet.Entry<JwtMac>>> it = this.primitives.getAll().iterator();
            GeneralSecurityException generalSecurityException = null;
            while (it.hasNext()) {
                for (PrimitiveSet.Entry<JwtMac> entry : it.next()) {
                    try {
                        VerifiedJwt verifiedJwtVerifyMacAndDecode = entry.getFullPrimitive().verifyMacAndDecode(compact, validator);
                        this.verifyLogger.log(entry.getKeyId(), 1L);
                        return verifiedJwtVerifyMacAndDecode;
                    } catch (GeneralSecurityException e) {
                        if (e instanceof JwtInvalidException) {
                            generalSecurityException = e;
                        }
                    }
                }
            }
            this.verifyLogger.logFailure();
            if (generalSecurityException != null) {
                throw generalSecurityException;
            }
            throw new GeneralSecurityException("invalid MAC");
        }
    }

    JwtMacWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public JwtMac wrap(final PrimitiveSet<JwtMac> primitives) throws GeneralSecurityException {
        validate(primitives);
        return new WrappedJwtMac(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<JwtMac> getPrimitiveClass() {
        return JwtMac.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<JwtMac> getInputPrimitiveClass() {
        return JwtMac.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
    }
}
