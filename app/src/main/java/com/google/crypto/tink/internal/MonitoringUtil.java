package com.google.crypto.tink.internal;

import com.google.crypto.tink.KeyStatus;
import com.google.crypto.tink.internal.MonitoringClient;
import com.google.crypto.tink.internal.MonitoringKeysetInfo;
import com.google.crypto.tink.internal.PrimitiveSet;
import com.google.crypto.tink.proto.KeyStatusType;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public final class MonitoringUtil {
    public static final MonitoringClient.Logger DO_NOTHING_LOGGER = new DoNothingLogger(null);
    private static final String TYPE_URL_PREFIX = "type.googleapis.com/google.crypto.";

    private static class DoNothingLogger implements MonitoringClient.Logger {
        @Override // com.google.crypto.tink.internal.MonitoringClient.Logger
        public void log(int keyId, long numBytesAsInput) {
        }

        @Override // com.google.crypto.tink.internal.MonitoringClient.Logger
        public void logFailure() {
        }

        private DoNothingLogger() {
        }

        /* synthetic */ DoNothingLogger(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.google.crypto.tink.internal.MonitoringUtil$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$crypto$tink$proto$KeyStatusType;

        static {
            int[] iArr = new int[KeyStatusType.values().length];
            $SwitchMap$com$google$crypto$tink$proto$KeyStatusType = iArr;
            try {
                iArr[KeyStatusType.ENABLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$crypto$tink$proto$KeyStatusType[KeyStatusType.DISABLED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$crypto$tink$proto$KeyStatusType[KeyStatusType.DESTROYED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static KeyStatus parseStatus(KeyStatusType in) {
        int i = AnonymousClass1.$SwitchMap$com$google$crypto$tink$proto$KeyStatusType[in.ordinal()];
        if (i == 1) {
            return KeyStatus.ENABLED;
        }
        if (i == 2) {
            return KeyStatus.DISABLED;
        }
        if (i == 3) {
            return KeyStatus.DESTROYED;
        }
        throw new IllegalStateException("Unknown key status");
    }

    private static String parseKeyTypeUrl(String keyTypeUrl) {
        return !keyTypeUrl.startsWith(TYPE_URL_PREFIX) ? keyTypeUrl : keyTypeUrl.substring(34);
    }

    public static <P> MonitoringKeysetInfo getMonitoringKeysetInfo(PrimitiveSet<P> primitiveSet) {
        MonitoringKeysetInfo.Builder builderNewBuilder = MonitoringKeysetInfo.newBuilder();
        builderNewBuilder.setAnnotations(primitiveSet.getAnnotations());
        Iterator<List<PrimitiveSet.Entry<P>>> it = primitiveSet.getAll().iterator();
        while (it.hasNext()) {
            for (PrimitiveSet.Entry<P> entry : it.next()) {
                builderNewBuilder.addEntry(parseStatus(entry.getStatus()), entry.getKeyId(), parseKeyTypeUrl(entry.getKeyTypeUrl()), entry.getOutputPrefixType().name());
            }
        }
        if (primitiveSet.getPrimary() != null) {
            builderNewBuilder.setPrimaryKeyId(primitiveSet.getPrimary().getKeyId());
        }
        try {
            return builderNewBuilder.build();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    private MonitoringUtil() {
    }
}
