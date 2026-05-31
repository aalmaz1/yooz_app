package com.google.crypto.tink.hybrid.internal;

import com.google.crypto.tink.subtle.EllipticCurves;
import com.google.crypto.tink.util.Bytes;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
final class NistCurvesHpkeKemPrivateKey implements HpkeKemPrivateKey {
    private final Bytes privateKey;
    private final Bytes publicKey;

    static NistCurvesHpkeKemPrivateKey fromBytes(byte[] privateKey, byte[] publicKey, EllipticCurves.CurveType curveType) throws GeneralSecurityException {
        EllipticCurves.validatePublicKey(EllipticCurves.getEcPublicKey(curveType, EllipticCurves.PointFormatType.UNCOMPRESSED, publicKey), EllipticCurves.getEcPrivateKey(curveType, privateKey));
        return new NistCurvesHpkeKemPrivateKey(privateKey, publicKey);
    }

    private NistCurvesHpkeKemPrivateKey(byte[] privateKey, byte[] publicKey) {
        this.privateKey = Bytes.copyFrom(privateKey);
        this.publicKey = Bytes.copyFrom(publicKey);
    }

    @Override // com.google.crypto.tink.hybrid.internal.HpkeKemPrivateKey
    public Bytes getSerializedPrivate() {
        return this.privateKey;
    }

    @Override // com.google.crypto.tink.hybrid.internal.HpkeKemPrivateKey
    public Bytes getSerializedPublic() {
        return this.publicKey;
    }
}
