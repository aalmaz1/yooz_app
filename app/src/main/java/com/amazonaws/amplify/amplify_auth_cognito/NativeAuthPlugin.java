package com.amazonaws.amplify.amplify_auth_cognito;

import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthPlugin;", "", "binaryMessenger", "Lio/flutter/plugin/common/BinaryMessenger;", "(Lio/flutter/plugin/common/BinaryMessenger;)V", "exchange", "", "paramsArg", "", "", "callback", "Lkotlin/Function0;", "Companion", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeAuthPlugin {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<StandardMessageCodec> codec$delegate = LazyKt.lazy(new Function0<StandardMessageCodec>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthPlugin$Companion$codec$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final StandardMessageCodec invoke() {
            return new StandardMessageCodec();
        }
    });
    private final BinaryMessenger binaryMessenger;

    public NativeAuthPlugin(BinaryMessenger binaryMessenger) {
        Intrinsics.checkNotNullParameter(binaryMessenger, "binaryMessenger");
        this.binaryMessenger = binaryMessenger;
    }

    /* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R#\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthPlugin$Companion;", "", "()V", "codec", "Lio/flutter/plugin/common/MessageCodec;", "getCodec", "()Lio/flutter/plugin/common/MessageCodec;", "codec$delegate", "Lkotlin/Lazy;", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MessageCodec<Object> getCodec() {
            return (MessageCodec) NativeAuthPlugin.codec$delegate.getValue();
        }
    }

    public final void exchange(Map<String, String> paramsArg, final Function0<Unit> callback) {
        Intrinsics.checkNotNullParameter(paramsArg, "paramsArg");
        Intrinsics.checkNotNullParameter(callback, "callback");
        new BasicMessageChannel(this.binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthPlugin.exchange", INSTANCE.getCodec()).send(CollectionsKt.listOf(paramsArg), new BasicMessageChannel.Reply() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthPlugin$$ExternalSyntheticLambda0
            @Override // io.flutter.plugin.common.BasicMessageChannel.Reply
            public final void reply(Object obj) {
                NativeAuthPlugin.exchange$lambda$0(callback, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void exchange$lambda$0(Function0 callback, Object obj) {
        Intrinsics.checkNotNullParameter(callback, "$callback");
        callback.invoke();
    }
}
