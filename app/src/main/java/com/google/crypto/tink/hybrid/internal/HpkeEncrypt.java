package com.google.crypto.tink.hybrid.internal;

import com.google.crypto.tink.HybridEncrypt;
import com.google.crypto.tink.hybrid.HpkeParameters;
import com.google.crypto.tink.hybrid.HpkePublicKey;
import com.google.crypto.tink.subtle.EllipticCurves;
import com.google.crypto.tink.util.Bytes;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
public final class HpkeEncrypt implements HybridEncrypt {
    private static final byte[] EMPTY_ASSOCIATED_DATA = new byte[0];
    private final HpkeAead aead;
    private final HpkeKdf kdf;
    private final HpkeKem kem;
    private final byte[] outputPrefix;
    private final byte[] recipientPublicKey;

    private HpkeEncrypt(Bytes recipientPublicKey, HpkeKem kem, HpkeKdf kdf, HpkeAead aead, Bytes outputPrefix) {
        this.recipientPublicKey = recipientPublicKey.toByteArray();
        this.kem = kem;
        this.kdf = kdf;
        this.aead = aead;
        this.outputPrefix = outputPrefix.toByteArray();
    }

    public static HybridEncrypt create(HpkePublicKey key) throws GeneralSecurityException {
        HpkeParameters parameters = key.getParameters();
        return new HpkeEncrypt(key.getPublicKeyBytes(), createKem(parameters.getKemId()), createKdf(parameters.getKdfId()), createAead(parameters.getAeadId()), key.getOutputPrefix());
    }

    static HpkeKem createKem(HpkeParameters.KemId kemId) throws GeneralSecurityException {
        if (kemId.equals(HpkeParameters.KemId.DHKEM_X25519_HKDF_SHA256)) {
            return new X25519HpkeKem(new HkdfHpkeKdf("HmacSha256"));
        }
        if (kemId.equals(HpkeParameters.KemId.DHKEM_P256_HKDF_SHA256)) {
            return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P256);
        }
        if (kemId.equals(HpkeParameters.KemId.DHKEM_P384_HKDF_SHA384)) {
            return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P384);
        }
        if (kemId.equals(HpkeParameters.KemId.DHKEM_P521_HKDF_SHA512)) {
            return NistCurvesHpkeKem.fromCurve(EllipticCurves.CurveType.NIST_P521);
        }
        throw new GeneralSecurityException("Unrecognized HPKE KEM identifier");
    }

    static HpkeKdf createKdf(HpkeParameters.KdfId kdfId) throws GeneralSecurityException {
        if (kdfId.equals(HpkeParameters.KdfId.HKDF_SHA256)) {
            return new HkdfHpkeKdf("HmacSha256");
        }
        if (kdfId.equals(HpkeParameters.KdfId.HKDF_SHA384)) {
            return new HkdfHpkeKdf("HmacSha384");
        }
        if (kdfId.equals(HpkeParameters.KdfId.HKDF_SHA512)) {
            return new HkdfHpkeKdf("HmacSha512");
        }
        throw new GeneralSecurityException("Unrecognized HPKE KDF identifier");
    }

    static HpkeAead createAead(HpkeParameters.AeadId aeadId) throws GeneralSecurityException {
        if (aeadId.equals(HpkeParameters.AeadId.AES_128_GCM)) {
            return new AesGcmHpkeAead(16);
        }
        if (aeadId.equals(HpkeParameters.AeadId.AES_256_GCM)) {
            return new AesGcmHpkeAead(32);
        }
        if (aeadId.equals(HpkeParameters.AeadId.CHACHA20_POLY1305)) {
            return new ChaCha20Poly1305HpkeAead();
        }
        throw new GeneralSecurityException("Unrecognized HPKE AEAD identifier");
    }

    @Override // com.google.crypto.tink.HybridEncrypt
    public byte[] encrypt(final byte[] plaintext, final byte[] contextInfo) throws GeneralSecurityException {
        if (contextInfo == null) {
            contextInfo = new byte[0];
        }
        HpkeContext hpkeContextCreateSenderContext = HpkeContext.createSenderContext(this.recipientPublicKey, this.kem, this.kdf, this.aead, contextInfo);
        byte[] encapsulatedKey = hpkeContextCreateSenderContext.getEncapsulatedKey();
        byte[] bArrSeal = hpkeContextCreateSenderContext.seal(plaintext, this.outputPrefix.length + encapsulatedKey.length, EMPTY_ASSOCIATED_DATA);
        byte[] bArr = this.outputPrefix;
        System.arraycopy(bArr, 0, bArrSeal, 0, bArr.length);
        System.arraycopy(encapsulatedKey, 0, bArrSeal, this.outputPrefix.length, encapsulatedKey.length);
        return bArrSeal;
    }
}
