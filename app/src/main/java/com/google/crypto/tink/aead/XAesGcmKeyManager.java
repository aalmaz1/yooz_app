package com.google.crypto.tink.aead;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;
import com.google.crypto.tink.aead.internal.XAesGcm;
import com.google.crypto.tink.aead.internal.XAesGcmProtoSerialization;
import com.google.crypto.tink.internal.MutableKeyCreationRegistry;
import com.google.crypto.tink.internal.MutableParametersRegistry;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveConstructor;
import com.google.crypto.tink.util.SecretBytes;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public final class XAesGcmKeyManager {
    private static final MutableKeyCreationRegistry.KeyCreator<XAesGcmParameters> KEY_CREATOR = new MutableKeyCreationRegistry.KeyCreator() { // from class: com.google.crypto.tink.aead.XAesGcmKeyManager$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.MutableKeyCreationRegistry.KeyCreator
        public final Key createKey(Parameters parameters, Integer num) {
            return XAesGcmKeyManager.createXAesGcmKey((XAesGcmParameters) parameters, num);
        }
    };
    private static final PrimitiveConstructor<XAesGcmKey, Aead> X_AES_GCM_PRIMITVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.aead.XAesGcmKeyManager$$ExternalSyntheticLambda1
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return XAesGcm.create((XAesGcmKey) key);
        }
    }, XAesGcmKey.class, Aead.class);

    private static Map<String, Parameters> namedParameters() {
        HashMap map = new HashMap();
        map.put("X_AES_GCM_8_BYTE_SALT_NO_PREFIX", PredefinedAeadParameters.X_AES_GCM_8_BYTE_SALT_NO_PREFIX);
        return Collections.unmodifiableMap(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static XAesGcmKey createXAesGcmKey(XAesGcmParameters parameters, @Nullable Integer idRequirement) throws GeneralSecurityException {
        return XAesGcmKey.create(parameters, SecretBytes.randomBytes(32), idRequirement);
    }

    public static void register(boolean newKeyAllowed) throws GeneralSecurityException {
        XAesGcmProtoSerialization.register();
        MutableParametersRegistry.globalInstance().putAll(namedParameters());
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(X_AES_GCM_PRIMITVE_CONSTRUCTOR);
        MutableKeyCreationRegistry.globalInstance().add(KEY_CREATOR, XAesGcmParameters.class);
    }

    private XAesGcmKeyManager() {
    }
}
