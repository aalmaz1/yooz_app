package com.google.crypto.tink.mac.internal;

import com.google.crypto.tink.InsecureSecretKeyAccess;
import com.google.crypto.tink.mac.AesCmacKey;
import com.google.crypto.tink.mac.AesCmacParameters;
import com.google.crypto.tink.mac.ChunkedMacComputation;
import com.google.crypto.tink.subtle.Bytes;
import com.google.crypto.tink.subtle.EngineFactory;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes2.dex */
final class ChunkedAesCmacComputation implements ChunkedMacComputation {
    private static final byte[] FORMAT_VERSION = {0};
    private final Cipher aes;
    private boolean finalized = false;
    private final AesCmacKey key;
    private final ByteBuffer localStash;
    private final byte[] subKey1;
    private final byte[] subKey2;
    private final ByteBuffer x;
    private final ByteBuffer y;

    ChunkedAesCmacComputation(AesCmacKey key) throws GeneralSecurityException {
        this.key = key;
        Cipher engineFactory = EngineFactory.CIPHER.getInstance("AES/ECB/NoPadding");
        this.aes = engineFactory;
        engineFactory.init(1, new SecretKeySpec(key.getAesKey().toByteArray(InsecureSecretKeyAccess.get()), "AES"));
        byte[] bArrDbl = AesUtil.dbl(engineFactory.doFinal(new byte[16]));
        this.subKey1 = bArrDbl;
        this.subKey2 = AesUtil.dbl(bArrDbl);
        this.localStash = ByteBuffer.allocate(16);
        this.x = ByteBuffer.allocate(16);
        this.y = ByteBuffer.allocate(16);
    }

    private void munch(ByteBuffer data) throws GeneralSecurityException {
        this.y.rewind();
        this.x.rewind();
        Bytes.xor(this.y, this.x, data, 16);
        this.y.rewind();
        this.x.rewind();
        this.aes.doFinal(this.y, this.x);
    }

    @Override // com.google.crypto.tink.mac.ChunkedMacComputation
    public void update(ByteBuffer data) throws GeneralSecurityException {
        if (this.finalized) {
            throw new IllegalStateException("Can not update after computing the MAC tag. Please create a new object.");
        }
        if (this.localStash.remaining() != 16) {
            int iMin = Math.min(this.localStash.remaining(), data.remaining());
            for (int i = 0; i < iMin; i++) {
                this.localStash.put(data.get());
            }
        }
        if (this.localStash.remaining() == 0 && data.remaining() > 0) {
            this.localStash.rewind();
            munch(this.localStash);
            this.localStash.rewind();
        }
        while (data.remaining() > 16) {
            munch(data);
        }
        this.localStash.put(data);
    }

    @Override // com.google.crypto.tink.mac.ChunkedMacComputation
    public byte[] computeMac() throws GeneralSecurityException {
        byte[] bArrXor;
        if (this.finalized) {
            throw new IllegalStateException("Can not compute after computing the MAC tag. Please create a new object.");
        }
        if (this.key.getParameters().getVariant() == AesCmacParameters.Variant.LEGACY) {
            update(ByteBuffer.wrap(FORMAT_VERSION));
        }
        this.finalized = true;
        if (this.localStash.remaining() > 0) {
            bArrXor = Bytes.xor(AesUtil.cmacPad(Arrays.copyOf(this.localStash.array(), this.localStash.position())), this.subKey2);
        } else {
            bArrXor = Bytes.xor(this.localStash.array(), 0, this.subKey1, 0, 16);
        }
        return Bytes.concat(this.key.getOutputPrefix().toByteArray(), Arrays.copyOf(this.aes.doFinal(Bytes.xor(bArrXor, this.x.array())), this.key.getParameters().getCryptographicTagSizeBytes()));
    }
}
