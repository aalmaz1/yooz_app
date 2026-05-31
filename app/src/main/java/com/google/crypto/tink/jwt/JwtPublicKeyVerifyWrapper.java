package com.google.crypto.tink.jwt;

import com.google.crypto.tink.internal.MonitoringClient;
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
class JwtPublicKeyVerifyWrapper implements PrimitiveWrapper<JwtPublicKeyVerify, JwtPublicKeyVerify> {
    private static final JwtPublicKeyVerifyWrapper WRAPPER = new JwtPublicKeyVerifyWrapper();

    JwtPublicKeyVerifyWrapper() {
    }

    @Immutable
    private static class WrappedJwtPublicKeyVerify implements JwtPublicKeyVerify {
        private final MonitoringClient.Logger logger;
        private final PrimitiveSet<JwtPublicKeyVerify> primitives;

        public WrappedJwtPublicKeyVerify(PrimitiveSet<JwtPublicKeyVerify> primitives) {
            this.primitives = primitives;
            if (primitives.hasAnnotations()) {
                this.logger = MutableMonitoringRegistry.globalInstance().getMonitoringClient().createLogger(MonitoringUtil.getMonitoringKeysetInfo(primitives), "jwtverify", "verify");
            } else {
                this.logger = MonitoringUtil.DO_NOTHING_LOGGER;
            }
        }

        @Override // com.google.crypto.tink.jwt.JwtPublicKeyVerify
        public VerifiedJwt verifyAndDecode(String compact, JwtValidator validator) throws GeneralSecurityException {
            Iterator<List<PrimitiveSet.Entry<JwtPublicKeyVerify>>> it = this.primitives.getAll().iterator();
            GeneralSecurityException generalSecurityException = null;
            while (it.hasNext()) {
                for (PrimitiveSet.Entry<JwtPublicKeyVerify> entry : it.next()) {
                    try {
                        VerifiedJwt verifiedJwtVerifyAndDecode = entry.getFullPrimitive().verifyAndDecode(compact, validator);
                        this.logger.log(entry.getKeyId(), 1L);
                        return verifiedJwtVerifyAndDecode;
                    } catch (GeneralSecurityException e) {
                        if (e instanceof JwtInvalidException) {
                            generalSecurityException = e;
                        }
                    }
                }
            }
            this.logger.logFailure();
            if (generalSecurityException != null) {
                throw generalSecurityException;
            }
            throw new GeneralSecurityException("invalid JWT");
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public JwtPublicKeyVerify wrap(final PrimitiveSet<JwtPublicKeyVerify> primitives) throws GeneralSecurityException {
        return new WrappedJwtPublicKeyVerify(primitives);
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<JwtPublicKeyVerify> getPrimitiveClass() {
        return JwtPublicKeyVerify.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<JwtPublicKeyVerify> getInputPrimitiveClass() {
        return JwtPublicKeyVerify.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
    }
}
