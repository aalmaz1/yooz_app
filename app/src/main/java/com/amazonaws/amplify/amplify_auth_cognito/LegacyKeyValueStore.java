package com.amazonaws.amplify.amplify_auth_cognito;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import cn.baos.watch.sdk.entitiy.NotificationConstant;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LegacyKeyValueStore.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fJ&\u0010\r\u001a\u0004\u0018\u00010\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0005H\u0002J\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0086\u0002J\u0010\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0014\u0010\u0018\u001a\u0004\u0018\u00010\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u0005H\u0002J\b\u0010\u001a\u001a\u00020\u0005H\u0002J\u0012\u0010\u001b\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u0002J\u000e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0005J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001f\u001a\u00020\u0005H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyKeyValueStore;", "", "context", "Landroid/content/Context;", "sharedPreferencesName", "", "(Landroid/content/Context;Ljava/lang/String;)V", "keyProvider", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyKeyProvider;", "sharedPreferencesForData", "Landroid/content/SharedPreferences;", "clear", "", "decrypt", "decryptionKey", "Ljava/security/Key;", "ivSpec", "Ljava/security/spec/AlgorithmParameterSpec;", "encryptedData", "get", "dataKey", "getAlgorithmParameterSpecForIV", "iv", "", "getDataKeyUsedInPersistentStore", NotificationConstant.EXTRA_KEY, "getEncryptionKeyAlias", "getInitializationVector", "keyOfDataInSharedPreferences", "remove", "retrieveEncryptionKey", "encryptionKeyAlias", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LegacyKeyValueStore {
    private LegacyKeyProvider keyProvider;
    private final SharedPreferences sharedPreferencesForData;
    private final String sharedPreferencesName;

    public LegacyKeyValueStore(Context context, String sharedPreferencesName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sharedPreferencesName, "sharedPreferencesName");
        this.sharedPreferencesName = sharedPreferencesName;
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesName, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        this.sharedPreferencesForData = sharedPreferences;
        this.keyProvider = new LegacyKeyProvider();
    }

    public final synchronized String get(String dataKey) {
        if (dataKey == null) {
            return null;
        }
        String dataKeyUsedInPersistentStore = getDataKeyUsedInPersistentStore(dataKey);
        Key keyRetrieveEncryptionKey = retrieveEncryptionKey(getEncryptionKeyAlias());
        if (keyRetrieveEncryptionKey == null) {
            return null;
        }
        if (!this.sharedPreferencesForData.contains(dataKeyUsedInPersistentStore)) {
            return null;
        }
        try {
            String string = this.sharedPreferencesForData.getString(dataKeyUsedInPersistentStore + ".keyvaluestoreversion", null);
            Intrinsics.checkNotNull(string);
            if (Integer.parseInt(string) != 1) {
                return null;
            }
            return decrypt(keyRetrieveEncryptionKey, getInitializationVector(dataKeyUsedInPersistentStore), this.sharedPreferencesForData.getString(dataKeyUsedInPersistentStore, null));
        } catch (Exception unused) {
            remove(dataKey);
            return null;
        }
    }

    public final synchronized void remove(String dataKey) {
        Intrinsics.checkNotNullParameter(dataKey, "dataKey");
        String dataKeyUsedInPersistentStore = getDataKeyUsedInPersistentStore(dataKey);
        this.sharedPreferencesForData.edit().remove(dataKeyUsedInPersistentStore).remove(dataKeyUsedInPersistentStore + ".iv").remove(dataKeyUsedInPersistentStore + ".keyvaluestoreversion").apply();
    }

    public final synchronized void clear() {
        this.sharedPreferencesForData.edit().clear().apply();
    }

    private final String decrypt(Key decryptionKey, AlgorithmParameterSpec ivSpec, String encryptedData) {
        try {
            byte[] bArrDecode = Base64.decode(encryptedData, 0);
            Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            Intrinsics.checkNotNullExpressionValue(cipher, "getInstance(...)");
            cipher.init(2, decryptionKey, ivSpec);
            byte[] bArrDoFinal = cipher.doFinal(bArrDecode);
            Intrinsics.checkNotNullExpressionValue(bArrDoFinal, "doFinal(...)");
            Charset charsetDefaultCharset = Charset.defaultCharset();
            Intrinsics.checkNotNullExpressionValue(charsetDefaultCharset, "defaultCharset(...)");
            return new String(bArrDoFinal, charsetDefaultCharset);
        } catch (Exception unused) {
            return null;
        }
    }

    private final AlgorithmParameterSpec getInitializationVector(String keyOfDataInSharedPreferences) throws Exception {
        String str = keyOfDataInSharedPreferences + ".iv";
        if (!this.sharedPreferencesForData.contains(str)) {
            throw new Exception("Initialization vector for " + keyOfDataInSharedPreferences + " is missing from the SharedPreferences.");
        }
        String string = this.sharedPreferencesForData.getString(str, null);
        if (string == null) {
            throw new Exception("Cannot read the initialization vector for " + keyOfDataInSharedPreferences + " from SharedPreferences.");
        }
        byte[] bArrDecode = Base64.decode(string, 0);
        Intrinsics.checkNotNullExpressionValue(bArrDecode, "decode(...)");
        if (bArrDecode.length == 0) {
            throw new Exception("Cannot base64 decode the initialization vector for " + keyOfDataInSharedPreferences + " read from SharedPreferences.");
        }
        return getAlgorithmParameterSpecForIV(bArrDecode);
    }

    private final AlgorithmParameterSpec getAlgorithmParameterSpecForIV(byte[] iv) {
        return new GCMParameterSpec(128, iv);
    }

    private final synchronized Key retrieveEncryptionKey(String encryptionKeyAlias) {
        Key keyRetrieveKey;
        try {
            keyRetrieveKey = this.keyProvider.retrieveKey(encryptionKeyAlias);
        } catch (Exception unused) {
            this.keyProvider.deleteKey(encryptionKeyAlias);
            keyRetrieveKey = null;
        }
        return keyRetrieveKey;
    }

    private final String getDataKeyUsedInPersistentStore(String key) {
        if (key == null) {
            return null;
        }
        return key + ".encrypted";
    }

    private final String getEncryptionKeyAlias() {
        return this.sharedPreferencesName + ".aesKeyStoreAlias";
    }
}
