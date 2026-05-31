package com.amazonaws.amplify.amplify_auth_cognito;

import android.security.keystore.KeyGenParameterSpec;
import java.security.Key;
import java.security.KeyStore;
import javax.crypto.KeyGenerator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LegacyKeyProvider.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\n"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyKeyProvider;", "", "()V", "deleteKey", "", "keyAlias", "", "generateKey", "Ljava/security/Key;", "retrieveKey", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LegacyKeyProvider {
    public final synchronized Key retrieveKey(String keyAlias) {
        KeyStore keyStore;
        Intrinsics.checkNotNullParameter(keyAlias, "keyAlias");
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            Intrinsics.checkNotNullExpressionValue(keyStore, "getInstance(...)");
            keyStore.load(null);
            if (keyStore.containsAlias(keyAlias)) {
            } else {
                throw new Exception("AndroidKeyStore does not contain the keyAlias: " + keyAlias);
            }
        } catch (Exception e) {
            throw new Exception("Error occurred while accessing AndroidKeyStore to retrieve the key for keyAlias: " + keyAlias, e);
        }
        return keyStore.getKey(keyAlias, null);
    }

    public final synchronized Key generateKey(String keyAlias) {
        KeyGenerator keyGenerator;
        Intrinsics.checkNotNullParameter(keyAlias, "keyAlias");
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            Intrinsics.checkNotNullExpressionValue(keyStore, "getInstance(...)");
            keyStore.load(null);
            if (!keyStore.containsAlias(keyAlias)) {
                keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
                Intrinsics.checkNotNullExpressionValue(keyGenerator, "getInstance(...)");
                keyGenerator.init(new KeyGenParameterSpec.Builder(keyAlias, 3).setBlockModes("GCM").setEncryptionPaddings("NoPadding").setKeySize(256).setRandomizedEncryptionRequired(false).build());
            } else {
                throw new Exception("Key already exists for the keyAlias: " + keyAlias + " in AndroidKeyStore");
            }
        } catch (Exception e) {
            throw new Exception("Cannot generate a key for alias: " + keyAlias + " in AndroidKeyStore", e);
        }
        return keyGenerator.generateKey();
    }

    public final synchronized void deleteKey(String keyAlias) {
        Intrinsics.checkNotNullParameter(keyAlias, "keyAlias");
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            Intrinsics.checkNotNullExpressionValue(keyStore, "getInstance(...)");
            keyStore.load(null);
            keyStore.deleteEntry(keyAlias);
        } catch (Exception unused) {
        }
    }
}
