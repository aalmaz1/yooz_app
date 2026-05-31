package com.google.crypto.tink.internal;

import com.google.crypto.tink.KeyStatus;
import com.google.errorprone.annotations.Immutable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
public final class MonitoringKeysetInfo {
    private final MonitoringAnnotations annotations;
    private final List<Entry> entries;

    @Nullable
    private final Integer primaryKeyId;

    @Immutable
    public static final class Entry {
        private final int keyId;
        private final String keyPrefix;
        private final String keyType;
        private final KeyStatus status;

        public KeyStatus getStatus() {
            return this.status;
        }

        public int getKeyId() {
            return this.keyId;
        }

        public String getKeyType() {
            return this.keyType;
        }

        public String getKeyPrefix() {
            return this.keyPrefix;
        }

        private Entry(KeyStatus status, int keyId, String keyType, String keyPrefix) {
            this.status = status;
            this.keyId = keyId;
            this.keyType = keyType;
            this.keyPrefix = keyPrefix;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.status == entry.status && this.keyId == entry.keyId && this.keyType.equals(entry.keyType) && this.keyPrefix.equals(entry.keyPrefix);
        }

        public int hashCode() {
            return Objects.hash(this.status, Integer.valueOf(this.keyId), this.keyType, this.keyPrefix);
        }

        public String toString() {
            return String.format("(status=%s, keyId=%s, keyType='%s', keyPrefix='%s')", this.status, Integer.valueOf(this.keyId), this.keyType, this.keyPrefix);
        }
    }

    public static final class Builder {

        @Nullable
        private ArrayList<Entry> builderEntries = new ArrayList<>();
        private MonitoringAnnotations builderAnnotations = MonitoringAnnotations.EMPTY;

        @Nullable
        private Integer builderPrimaryKeyId = null;

        public Builder setAnnotations(MonitoringAnnotations annotations) {
            if (this.builderEntries == null) {
                throw new IllegalStateException("setAnnotations cannot be called after build()");
            }
            this.builderAnnotations = annotations;
            return this;
        }

        public Builder addEntry(KeyStatus status, int keyId, String keyType, String keyPrefix) {
            ArrayList<Entry> arrayList = this.builderEntries;
            if (arrayList == null) {
                throw new IllegalStateException("addEntry cannot be called after build()");
            }
            arrayList.add(new Entry(status, keyId, keyType, keyPrefix));
            return this;
        }

        public Builder setPrimaryKeyId(int primaryKeyId) {
            if (this.builderEntries == null) {
                throw new IllegalStateException("setPrimaryKeyId cannot be called after build()");
            }
            this.builderPrimaryKeyId = Integer.valueOf(primaryKeyId);
            return this;
        }

        private boolean isKeyIdInEntries(int keyId) {
            Iterator<Entry> it = this.builderEntries.iterator();
            while (it.hasNext()) {
                if (it.next().getKeyId() == keyId) {
                    return true;
                }
            }
            return false;
        }

        public MonitoringKeysetInfo build() throws GeneralSecurityException {
            if (this.builderEntries == null) {
                throw new IllegalStateException("cannot call build() twice");
            }
            Integer num = this.builderPrimaryKeyId;
            if (num != null && !isKeyIdInEntries(num.intValue())) {
                throw new GeneralSecurityException("primary key ID is not present in entries");
            }
            MonitoringKeysetInfo monitoringKeysetInfo = new MonitoringKeysetInfo(this.builderAnnotations, Collections.unmodifiableList(this.builderEntries), this.builderPrimaryKeyId);
            this.builderEntries = null;
            return monitoringKeysetInfo;
        }
    }

    private MonitoringKeysetInfo(MonitoringAnnotations annotations, List<Entry> entries, Integer primaryKeyId) {
        this.annotations = annotations;
        this.entries = entries;
        this.primaryKeyId = primaryKeyId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public MonitoringAnnotations getAnnotations() {
        return this.annotations;
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    @Nullable
    public Integer getPrimaryKeyId() {
        return this.primaryKeyId;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MonitoringKeysetInfo)) {
            return false;
        }
        MonitoringKeysetInfo monitoringKeysetInfo = (MonitoringKeysetInfo) obj;
        return this.annotations.equals(monitoringKeysetInfo.annotations) && this.entries.equals(monitoringKeysetInfo.entries) && Objects.equals(this.primaryKeyId, monitoringKeysetInfo.primaryKeyId);
    }

    public int hashCode() {
        return Objects.hash(this.annotations, this.entries);
    }

    public String toString() {
        return String.format("(annotations=%s, entries=%s, primaryKeyId=%s)", this.annotations, this.entries, this.primaryKeyId);
    }
}
