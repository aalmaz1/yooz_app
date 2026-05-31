package com.amazonaws.amplify.amplify_auth_cognito;

import cn.yoozworld.watch.utils.notifi.NotificationManager;
import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0014¨\u0006\u000e"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthBridgeCodec;", "Lio/flutter/plugin/common/StandardMessageCodec;", "()V", "readValueOfType", "", NotificationManager.BUNDLE_TYPE, "", "buffer", "Ljava/nio/ByteBuffer;", "writeValue", "", "stream", "Ljava/io/ByteArrayOutputStream;", "value", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class NativeAuthBridgeCodec extends StandardMessageCodec {
    public static final NativeAuthBridgeCodec INSTANCE = new NativeAuthBridgeCodec();

    private NativeAuthBridgeCodec() {
    }

    @Override // io.flutter.plugin.common.StandardMessageCodec
    protected Object readValueOfType(byte type, ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        if (type == -128) {
            Object value = readValue(buffer);
            List<? extends Object> list = value instanceof List ? (List) value : null;
            if (list != null) {
                return LegacyCredentialStoreData.INSTANCE.fromList(list);
            }
            return null;
        }
        if (type == -127) {
            Object value2 = readValue(buffer);
            List<? extends Object> list2 = value2 instanceof List ? (List) value2 : null;
            if (list2 != null) {
                return LegacyDeviceDetailsSecret.INSTANCE.fromList(list2);
            }
            return null;
        }
        if (type == -126) {
            Object value3 = readValue(buffer);
            List<? extends Object> list3 = value3 instanceof List ? (List) value3 : null;
            if (list3 != null) {
                return NativeUserContextData.INSTANCE.fromList(list3);
            }
            return null;
        }
        return super.readValueOfType(type, buffer);
    }

    @Override // io.flutter.plugin.common.StandardMessageCodec
    protected void writeValue(ByteArrayOutputStream stream, Object value) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        if (value instanceof LegacyCredentialStoreData) {
            stream.write(128);
            writeValue(stream, ((LegacyCredentialStoreData) value).toList());
        } else if (value instanceof LegacyDeviceDetailsSecret) {
            stream.write(129);
            writeValue(stream, ((LegacyDeviceDetailsSecret) value).toList());
        } else if (value instanceof NativeUserContextData) {
            stream.write(130);
            writeValue(stream, ((NativeUserContextData) value).toList());
        } else {
            super.writeValue(stream, value);
        }
    }
}
