package com.google.crypto.tink.streamingaead;

import com.google.crypto.tink.Key;
import com.google.crypto.tink.StreamingAead;
import com.google.crypto.tink.internal.LegacyProtoKey;
import com.google.crypto.tink.internal.MutablePrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveConstructor;
import com.google.crypto.tink.internal.PrimitiveRegistry;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.internal.PrimitiveWrapper;
import com.google.crypto.tink.streamingaead.internal.LegacyFullStreamingAead;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class StreamingAeadWrapper implements PrimitiveWrapper<StreamingAead, StreamingAead> {
    private static final StreamingAeadWrapper WRAPPER = new StreamingAeadWrapper();
    private static final PrimitiveConstructor<LegacyProtoKey, StreamingAead> LEGACY_FULL_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR = PrimitiveConstructor.create(new PrimitiveConstructor.PrimitiveConstructionFunction() { // from class: com.google.crypto.tink.streamingaead.StreamingAeadWrapper$$ExternalSyntheticLambda0
        @Override // com.google.crypto.tink.internal.PrimitiveConstructor.PrimitiveConstructionFunction
        public final Object constructPrimitive(Key key) {
            return LegacyFullStreamingAead.create((LegacyProtoKey) key);
        }
    }, LegacyProtoKey.class, StreamingAead.class);

    StreamingAeadWrapper() {
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public StreamingAead wrap(final PrimitiveSet<StreamingAead> primitives) throws GeneralSecurityException {
        ArrayList arrayList = new ArrayList();
        Iterator<List<PrimitiveSet.Entry<StreamingAead>>> it = primitives.getAll().iterator();
        while (it.hasNext()) {
            for (PrimitiveSet.Entry<StreamingAead> entry : it.next()) {
                if (entry.getFullPrimitive() == null) {
                    throw new GeneralSecurityException("No full primitive set for key id " + entry.getKeyId());
                }
                arrayList.add(entry.getFullPrimitive());
            }
        }
        PrimitiveSet.Entry<StreamingAead> primary = primitives.getPrimary();
        if (primary == null || primary.getFullPrimitive() == null) {
            throw new GeneralSecurityException("No primary set");
        }
        return new StreamingAeadHelper(arrayList, primary.getFullPrimitive());
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<StreamingAead> getPrimitiveClass() {
        return StreamingAead.class;
    }

    @Override // com.google.crypto.tink.internal.PrimitiveWrapper
    public Class<StreamingAead> getInputPrimitiveClass() {
        return StreamingAead.class;
    }

    public static void register() throws GeneralSecurityException {
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveWrapper(WRAPPER);
        MutablePrimitiveRegistry.globalInstance().registerPrimitiveConstructor(LEGACY_FULL_STREAMING_AEAD_PRIMITIVE_CONSTRUCTOR);
    }

    public static void registerToInternalPrimitiveRegistry(PrimitiveRegistry.Builder primitiveRegistryBuilder) throws GeneralSecurityException {
        primitiveRegistryBuilder.registerPrimitiveWrapper(WRAPPER);
    }
}
