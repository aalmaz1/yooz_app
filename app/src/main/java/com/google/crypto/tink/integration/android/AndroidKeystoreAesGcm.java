package com.google.crypto.tink.integration.android;

import android.util.Log;
import com.google.crypto.tink.Aead;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.ProviderException;
import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/* JADX INFO: loaded from: classes2.dex */
public final class AndroidKeystoreAesGcm implements Aead {
    private static final int IV_SIZE_IN_BYTES = 12;
    private static final int MAX_WAIT_TIME_MILLISECONDS_BEFORE_RETRY = 100;
    private static final String TAG = "AndroidKeystoreAesGcm";
    private static final int TAG_SIZE_IN_BYTES = 16;
    private final SecretKey key;

    public AndroidKeystoreAesGcm(String keyId) throws GeneralSecurityException, IOException {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        SecretKey secretKey = (SecretKey) keyStore.getKey(keyId, null);
        this.key = secretKey;
        if (secretKey == null) {
            throw new InvalidKeyException("Keystore cannot load the key with ID: " + keyId);
        }
    }

    @Override // com.google.crypto.tink.Aead
    public byte[] encrypt(final byte[] plaintext, final byte[] associatedData) throws GeneralSecurityException {
        try {
            return encryptInternal(plaintext, associatedData);
        } catch (GeneralSecurityException | ProviderException e) {
            Log.w(TAG, "encountered a potentially transient KeyStore error, will wait and retry", e);
            sleepRandomAmount();
            return encryptInternal(plaintext, associatedData);
        }
    }

    private byte[] encryptInternal(final byte[] plaintext, final byte[] associatedData) throws GeneralSecurityException {
        if (plaintext.length > 2147483619) {
            throw new GeneralSecurityException("plaintext too long");
        }
        byte[] bArr = new byte[plaintext.length + 12 + 16];
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(1, this.key);
        cipher.updateAAD(associatedData);
        cipher.doFinal(plaintext, 0, plaintext.length, bArr, 12);
        System.arraycopy(cipher.getIV(), 0, bArr, 0, 12);
        return bArr;
    }

    @Override // com.google.crypto.tink.Aead
    public byte[] decrypt(final byte[] ciphertext, final byte[] associatedData) throws GeneralSecurityException {
        if (ciphertext.length < 28) {
            throw new GeneralSecurityException("ciphertext too short");
        }
        try {
            return decryptInternal(ciphertext, associatedData);
        } catch (ProviderException e) {
            e = e;
            Log.w(TAG, "encountered a potentially transient KeyStore error, will wait and retry", e);
            sleepRandomAmount();
            return decryptInternal(ciphertext, associatedData);
        } catch (AEADBadTagException e2) {
            throw e2;
        } catch (GeneralSecurityException e3) {
            e = e3;
            Log.w(TAG, "encountered a potentially transient KeyStore error, will wait and retry", e);
            sleepRandomAmount();
            return decryptInternal(ciphertext, associatedData);
        }
    }

    private byte[] decryptInternal(final byte[] ciphertext, final byte[] associatedData) throws GeneralSecurityException {
        GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(128, ciphertext, 0, 12);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, this.key, gCMParameterSpec);
        cipher.updateAAD(associatedData);
        return cipher.doFinal(ciphertext, 12, ciphertext.length - 12);
    }

    private static void sleepRandomAmount() {
        try {
            Thread.sleep((int) (Math.random() * 100.0d));
        } catch (InterruptedException unused) {
        }
    }
}
