package com.google.crypto.tink.internal;

import com.google.crypto.tink.CryptoFormat;
import com.google.crypto.tink.Key;
import com.google.crypto.tink.Parameters;
import com.google.crypto.tink.proto.KeyStatusType;
import com.google.crypto.tink.proto.Keyset;
import com.google.crypto.tink.proto.OutputPrefixType;
import com.google.crypto.tink.util.Bytes;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
public final class PrimitiveSet<P> {
    private final MonitoringAnnotations annotations;
    private final Map<Bytes, List<Entry<P>>> entries;
    private final List<Entry<P>> entriesInKeysetOrder;
    private final Entry<P> primary;
    private final Class<P> primitiveClass;

    public static final class Entry<P> {
        private final P fullPrimitive;
        private final Key key;
        private final int keyId;
        private final String keyTypeUrl;
        private final Bytes outputPrefix;
        private final OutputPrefixType outputPrefixType;
        private final KeyStatusType status;

        private Entry(P fullPrimitive, final Bytes outputPrefix, KeyStatusType status, OutputPrefixType outputPrefixType, int keyId, String keyTypeUrl, Key key) {
            this.fullPrimitive = fullPrimitive;
            this.outputPrefix = outputPrefix;
            this.status = status;
            this.outputPrefixType = outputPrefixType;
            this.keyId = keyId;
            this.keyTypeUrl = keyTypeUrl;
            this.key = key;
        }

        public P getFullPrimitive() {
            return this.fullPrimitive;
        }

        public KeyStatusType getStatus() {
            return this.status;
        }

        public OutputPrefixType getOutputPrefixType() {
            return this.outputPrefixType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bytes getOutputPrefix() {
            return this.outputPrefix;
        }

        public int getKeyId() {
            return this.keyId;
        }

        public String getKeyTypeUrl() {
            return this.keyTypeUrl;
        }

        public Key getKey() {
            return this.key;
        }

        @Nullable
        public Parameters getParameters() {
            Key key = this.key;
            if (key == null) {
                return null;
            }
            return key.getParameters();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <P> void storeEntryInPrimitiveSet(Entry<P> entry, Map<Bytes, List<Entry<P>>> entries, List<Entry<P>> entriesInKeysetOrder) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(entry);
        List<Entry<P>> listPut = entries.put(entry.getOutputPrefix(), Collections.unmodifiableList(arrayList));
        if (listPut != null) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(listPut);
            arrayList2.add(entry);
            entries.put(entry.getOutputPrefix(), Collections.unmodifiableList(arrayList2));
        }
        entriesInKeysetOrder.add(entry);
    }

    @Nullable
    public Entry<P> getPrimary() {
        return this.primary;
    }

    public boolean hasAnnotations() {
        return !this.annotations.toMap().isEmpty();
    }

    public MonitoringAnnotations getAnnotations() {
        return this.annotations;
    }

    public List<Entry<P>> getRawPrimitives() {
        return getPrimitive(CryptoFormat.RAW_PREFIX);
    }

    public List<Entry<P>> getPrimitive(final byte[] identifier) {
        List<Entry<P>> list = this.entries.get(Bytes.copyFrom(identifier));
        return list != null ? list : Collections.emptyList();
    }

    public Collection<List<Entry<P>>> getAll() {
        return this.entries.values();
    }

    public List<Entry<P>> getAllInKeysetOrder() {
        return Collections.unmodifiableList(this.entriesInKeysetOrder);
    }

    private PrimitiveSet(Map<Bytes, List<Entry<P>>> entries, List<Entry<P>> entriesInKeysetOrder, Entry<P> primary, MonitoringAnnotations annotations, Class<P> primitiveClass) {
        this.entries = entries;
        this.entriesInKeysetOrder = entriesInKeysetOrder;
        this.primary = primary;
        this.primitiveClass = primitiveClass;
        this.annotations = annotations;
    }

    public Class<P> getPrimitiveClass() {
        return this.primitiveClass;
    }

    public static class Builder<P> {
        private MonitoringAnnotations annotations;
        private Map<Bytes, List<Entry<P>>> entries;
        private final List<Entry<P>> entriesInKeysetOrder;
        private Entry<P> primary;
        private final Class<P> primitiveClass;

        private Builder<P> addEntry(final P fullPrimitive, Key key, Keyset.Key protoKey, boolean asPrimary) throws GeneralSecurityException {
            if (this.entries == null) {
                throw new IllegalStateException("addEntry cannot be called after build");
            }
            if (fullPrimitive == null) {
                throw new NullPointerException("`fullPrimitive` must not be null");
            }
            if (protoKey.getStatus() != KeyStatusType.ENABLED) {
                throw new GeneralSecurityException("only ENABLED key is allowed");
            }
            Entry<P> entry = new Entry<>(fullPrimitive, Bytes.copyFrom(CryptoFormat.getOutputPrefix(protoKey)), protoKey.getStatus(), protoKey.getOutputPrefixType(), protoKey.getKeyId(), protoKey.getKeyData().getTypeUrl(), key);
            PrimitiveSet.storeEntryInPrimitiveSet(entry, this.entries, this.entriesInKeysetOrder);
            if (asPrimary) {
                if (this.primary != null) {
                    throw new IllegalStateException("you cannot set two primary primitives");
                }
                this.primary = entry;
            }
            return this;
        }

        public Builder<P> addFullPrimitive(final P fullPrimitive, Key key, Keyset.Key protoKey) throws GeneralSecurityException {
            return addEntry(fullPrimitive, key, protoKey, false);
        }

        public Builder<P> addPrimaryFullPrimitive(final P fullPrimitive, Key key, Keyset.Key protoKey) throws GeneralSecurityException {
            return addEntry(fullPrimitive, key, protoKey, true);
        }

        public Builder<P> setAnnotations(MonitoringAnnotations annotations) {
            if (this.entries == null) {
                throw new IllegalStateException("setAnnotations cannot be called after build");
            }
            this.annotations = annotations;
            return this;
        }

        public PrimitiveSet<P> build() throws GeneralSecurityException {
            if (this.entries == null) {
                throw new IllegalStateException("build cannot be called twice");
            }
            PrimitiveSet<P> primitiveSet = new PrimitiveSet<>(this.entries, this.entriesInKeysetOrder, this.primary, this.annotations, this.primitiveClass);
            this.entries = null;
            return primitiveSet;
        }

        private Builder(Class<P> primitiveClass) {
            this.entries = new HashMap();
            this.entriesInKeysetOrder = new ArrayList();
            this.primitiveClass = primitiveClass;
            this.annotations = MonitoringAnnotations.EMPTY;
        }
    }

    public static <P> Builder<P> newBuilder(Class<P> primitiveClass) {
        return new Builder<>(primitiveClass);
    }
}
