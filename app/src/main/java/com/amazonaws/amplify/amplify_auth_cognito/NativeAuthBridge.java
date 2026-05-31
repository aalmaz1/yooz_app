package com.amazonaws.amplify.amplify_auth_cognito;

import com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge;
import com.google.android.gms.common.internal.ImagesContract;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dJ\"\u0010\u0002\u001a\u00020\u00032\u0018\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\u0004\u0012\u00020\u00030\u0005H&J2\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0018\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\u0004\u0012\u00020\u00030\u0005H&J4\u0010\u000b\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u001a\u0010\u0004\u001a\u0016\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006\u0012\u0004\u0012\u00020\u00030\u0005H&J\b\u0010\r\u001a\u00020\tH&J\b\u0010\u000e\u001a\u00020\u000fH&J6\u0010\u0010\u001a\u00020\u00032\b\u0010\u0011\u001a\u0004\u0018\u00010\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0018\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0006\u0012\u0004\u0012\u00020\u00030\u0005H&J\u0014\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u0015H&JP\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\t2$\u0010\u0004\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\u00150\u0006\u0012\u0004\u0012\u00020\u00030\u0005H&JD\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\t2\u0018\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\u0004\u0012\u00020\u00030\u0005H&¨\u0006\u001e"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthBridge;", "", "clearLegacyCredentials", "", "callback", "Lkotlin/Function1;", "Lkotlin/Result;", "deleteLegacyDeviceSecrets", "username", "", "userPoolId", "fetchLegacyDeviceSecrets", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsSecret;", "getBundleId", "getContextData", "Lcom/amazonaws/amplify/amplify_auth_cognito/NativeUserContextData;", "getLegacyCredentials", "identityPoolId", "appClientId", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData;", "getValidationData", "", "signInWithUrl", ImagesContract.URL, "callbackUrlScheme", "preferPrivateSession", "", "browserPackageName", "signOutWithUrl", "Companion", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface NativeAuthBridge {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void clearLegacyCredentials(Function1<? super Result<Unit>, Unit> callback);

    void deleteLegacyDeviceSecrets(String username, String userPoolId, Function1<? super Result<Unit>, Unit> callback);

    void fetchLegacyDeviceSecrets(String username, String userPoolId, Function1<? super Result<LegacyDeviceDetailsSecret>, Unit> callback);

    String getBundleId();

    NativeUserContextData getContextData();

    void getLegacyCredentials(String identityPoolId, String appClientId, Function1<? super Result<LegacyCredentialStoreData>, Unit> callback);

    Map<String, String> getValidationData();

    void signInWithUrl(String url, String callbackUrlScheme, boolean preferPrivateSession, String browserPackageName, Function1<? super Result<? extends Map<String, String>>, Unit> callback);

    void signOutWithUrl(String url, String callbackUrlScheme, boolean preferPrivateSession, String browserPackageName, Function1<? super Result<Unit>, Unit> callback);

    /* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eR#\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthBridge$Companion;", "", "()V", "codec", "Lio/flutter/plugin/common/MessageCodec;", "getCodec", "()Lio/flutter/plugin/common/MessageCodec;", "codec$delegate", "Lkotlin/Lazy;", "setUp", "", "binaryMessenger", "Lio/flutter/plugin/common/BinaryMessenger;", "api", "Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthBridge;", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* JADX INFO: renamed from: codec$delegate, reason: from kotlin metadata */
        private static final Lazy<NativeAuthBridgeCodec> codec = LazyKt.lazy(new Function0<NativeAuthBridgeCodec>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$codec$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final NativeAuthBridgeCodec invoke() {
                return NativeAuthBridgeCodec.INSTANCE;
            }
        });

        private Companion() {
        }

        public final MessageCodec<Object> getCodec() {
            return codec.getValue();
        }

        public final void setUp(BinaryMessenger binaryMessenger, final NativeAuthBridge api) {
            Intrinsics.checkNotNullParameter(binaryMessenger, "binaryMessenger");
            BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.signInWithUrl", getCodec());
            if (api != null) {
                basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda0
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$1$lambda$0(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.signOutWithUrl", getCodec());
            if (api != null) {
                basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$3$lambda$2(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel2.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.getValidationData", getCodec());
            if (api != null) {
                basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda2
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$5$lambda$4(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel3.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.getContextData", getCodec());
            if (api != null) {
                basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda3
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$7$lambda$6(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel4.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel5 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.getBundleId", getCodec());
            if (api != null) {
                basicMessageChannel5.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda4
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$9$lambda$8(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel5.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel6 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.getLegacyCredentials", getCodec());
            if (api != null) {
                basicMessageChannel6.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda5
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$11$lambda$10(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel6.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel7 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.clearLegacyCredentials", getCodec());
            if (api != null) {
                basicMessageChannel7.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda6
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$13$lambda$12(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel7.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel8 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.fetchLegacyDeviceSecrets", getCodec());
            if (api != null) {
                basicMessageChannel8.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda7
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$15$lambda$14(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel8.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel9 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_auth_cognito.NativeAuthBridge.deleteLegacyDeviceSecrets", getCodec());
            if (api != null) {
                basicMessageChannel9.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$$ExternalSyntheticLambda8
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        NativeAuthBridge.Companion.setUp$lambda$17$lambda$16(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel9.setMessageHandler(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$1$lambda$0(NativeAuthBridge nativeAuthBridge, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            String str = (String) obj2;
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
            String str2 = (String) obj3;
            Object obj4 = list.get(2);
            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Boolean");
            nativeAuthBridge.signInWithUrl(str, str2, ((Boolean) obj4).booleanValue(), (String) list.get(3), new Function1<Result<? extends Map<String, ? extends String>>, Unit>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$setUp$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends Map<String, ? extends String>> result) {
                    m466invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m466invoke(Object obj5) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj5);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapError(thM687exceptionOrNullimpl));
                        return;
                    }
                    if (Result.m690isFailureimpl(obj5)) {
                        obj5 = null;
                    }
                    reply.reply(NativeAuthPluginBindingsPigeonKt.wrapResult((Map) obj5));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$3$lambda$2(NativeAuthBridge nativeAuthBridge, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            String str = (String) obj2;
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
            String str2 = (String) obj3;
            Object obj4 = list.get(2);
            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Boolean");
            nativeAuthBridge.signOutWithUrl(str, str2, ((Boolean) obj4).booleanValue(), (String) list.get(3), new Function1<Result<? extends Unit>, Unit>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$setUp$2$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends Unit> result) {
                    m467invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m467invoke(Object obj5) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj5);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapError(thM687exceptionOrNullimpl));
                    } else {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapResult(null));
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$5$lambda$4(NativeAuthBridge nativeAuthBridge, Object obj, BasicMessageChannel.Reply reply) {
            List listWrapError;
            Intrinsics.checkNotNullParameter(reply, "reply");
            try {
                listWrapError = CollectionsKt.listOf(nativeAuthBridge.getValidationData());
            } catch (Throwable th) {
                listWrapError = NativeAuthPluginBindingsPigeonKt.wrapError(th);
            }
            reply.reply(listWrapError);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$7$lambda$6(NativeAuthBridge nativeAuthBridge, Object obj, BasicMessageChannel.Reply reply) {
            List listWrapError;
            Intrinsics.checkNotNullParameter(reply, "reply");
            try {
                listWrapError = CollectionsKt.listOf(nativeAuthBridge.getContextData());
            } catch (Throwable th) {
                listWrapError = NativeAuthPluginBindingsPigeonKt.wrapError(th);
            }
            reply.reply(listWrapError);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$9$lambda$8(NativeAuthBridge nativeAuthBridge, Object obj, BasicMessageChannel.Reply reply) {
            List listWrapError;
            Intrinsics.checkNotNullParameter(reply, "reply");
            try {
                listWrapError = CollectionsKt.listOf(nativeAuthBridge.getBundleId());
            } catch (Throwable th) {
                listWrapError = NativeAuthPluginBindingsPigeonKt.wrapError(th);
            }
            reply.reply(listWrapError);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$11$lambda$10(NativeAuthBridge nativeAuthBridge, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            nativeAuthBridge.getLegacyCredentials((String) list.get(0), (String) list.get(1), new Function1<Result<? extends LegacyCredentialStoreData>, Unit>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$setUp$6$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends LegacyCredentialStoreData> result) {
                    m468invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m468invoke(Object obj2) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj2);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapError(thM687exceptionOrNullimpl));
                        return;
                    }
                    if (Result.m690isFailureimpl(obj2)) {
                        obj2 = null;
                    }
                    reply.reply(NativeAuthPluginBindingsPigeonKt.wrapResult((LegacyCredentialStoreData) obj2));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$13$lambda$12(NativeAuthBridge nativeAuthBridge, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            nativeAuthBridge.clearLegacyCredentials(new Function1<Result<? extends Unit>, Unit>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$setUp$7$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends Unit> result) {
                    m469invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m469invoke(Object obj2) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj2);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapError(thM687exceptionOrNullimpl));
                    } else {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapResult(null));
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$15$lambda$14(NativeAuthBridge nativeAuthBridge, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
            nativeAuthBridge.fetchLegacyDeviceSecrets((String) obj2, (String) obj3, new Function1<Result<? extends LegacyDeviceDetailsSecret>, Unit>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$setUp$8$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends LegacyDeviceDetailsSecret> result) {
                    m470invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m470invoke(Object obj4) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj4);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapError(thM687exceptionOrNullimpl));
                        return;
                    }
                    if (Result.m690isFailureimpl(obj4)) {
                        obj4 = null;
                    }
                    reply.reply(NativeAuthPluginBindingsPigeonKt.wrapResult((LegacyDeviceDetailsSecret) obj4));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$17$lambda$16(NativeAuthBridge nativeAuthBridge, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
            nativeAuthBridge.deleteLegacyDeviceSecrets((String) obj2, (String) obj3, new Function1<Result<? extends Unit>, Unit>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge$Companion$setUp$9$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends Unit> result) {
                    m471invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m471invoke(Object obj4) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj4);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapError(thM687exceptionOrNullimpl));
                    } else {
                        reply.reply(NativeAuthPluginBindingsPigeonKt.wrapResult(null));
                    }
                }
            });
        }
    }
}
