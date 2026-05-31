package com.google.crypto.tink.hybrid.internal;

import com.google.crypto.tink.InsecureSecretKeyAccess;
import com.google.crypto.tink.hybrid.HpkeParameters;
import com.google.crypto.tink.hybrid.HpkePrivateKey;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
public final class HpkeKemKeyFactory {
    public static HpkeKemPrivateKey createPrivate(HpkePrivateKey privateKey) throws GeneralSecurityException {
        HpkeParameters.KemId kemId = privateKey.getParameters().getKemId();
        if (kemId == HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256) {
            return X25519HpkeKemPrivateKey.fromBytes(privateKey.getPrivateKeyBytes().toByteArray(InsecureSecretKeyAccess.get()));
        }
        if (kemId == HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256 || kemId == HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384 || kemId == HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512) {
            return NistCurvesHpkeKemPrivateKey.fromBytes(privateKey.getPrivateKeyBytes().toByteArray(InsecureSecretKeyAccess.get()), privateKey.getPublicKey().getPublicKeyBytes().toByteArray(), HpkeUtil.nistHpkeKemToCurve(kemId));
        }
        throw new GeneralSecurityException("Unrecognized HPKE KEM identifier");
    }

    private HpkeKemKeyFactory() {
    }
}
