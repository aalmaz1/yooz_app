package com.google.crypto.tink.hybrid.internal;

import com.google.crypto.tink.subtle.X25519;
import com.google.crypto.tink.util.Bytes;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
final class X25519HpkeKemPrivateKey implements HpkeKemPrivateKey {
    private final Bytes privateKey;
    private final Bytes publicKey;

    static X25519HpkeKemPrivateKey fromBytes(byte[] privateKey) throws GeneralSecurityException {
        return new X25519HpkeKemPrivateKey(privateKey, X25519.publicFromPrivate(privateKey));
    }

    private X25519HpkeKemPrivateKey(byte[] privateKey, byte[] publicKey) {
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
