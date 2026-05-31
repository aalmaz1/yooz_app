package com.amazonaws.amplify.amplify_secure_storage.pigeons;

import cn.baos.watch.sdk.entitiy.NotificationConstant;
import com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.StandardMessageCodec;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AmplifySecureStoragePigeon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ2\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0018\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u0004\u0012\u00020\u00030\bH&J4\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u001a\u0010\u0007\u001a\u0016\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\t\u0012\u0004\u0012\u00020\u00030\bH&J*\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0018\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u0004\u0012\u00020\u00030\bH&J<\u0010\f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\r\u001a\u0004\u0018\u00010\u00052\u0018\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\t\u0012\u0004\u0012\u00020\u00030\bH&¨\u0006\u000f"}, d2 = {"Lcom/amazonaws/amplify/amplify_secure_storage/pigeons/AmplifySecureStoragePigeon;", "", "delete", "", "namespace", "", NotificationConstant.EXTRA_KEY, "callback", "Lkotlin/Function1;", "Lkotlin/Result;", "read", "removeAll", "write", "value", "Companion", "amplify_secure_storage_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AmplifySecureStoragePigeon {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    void delete(String namespace, String key, Function1<? super Result<Unit>, Unit> callback);

    void read(String namespace, String key, Function1<? super Result<String>, Unit> callback);

    void removeAll(String namespace, Function1<? super Result<Unit>, Unit> callback);

    void write(String namespace, String key, String value, Function1<? super Result<Unit>, Unit> callback);

    /* JADX INFO: compiled from: AmplifySecureStoragePigeon.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eR#\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/amazonaws/amplify/amplify_secure_storage/pigeons/AmplifySecureStoragePigeon$Companion;", "", "()V", "codec", "Lio/flutter/plugin/common/MessageCodec;", "getCodec", "()Lio/flutter/plugin/common/MessageCodec;", "codec$delegate", "Lkotlin/Lazy;", "setUp", "", "binaryMessenger", "Lio/flutter/plugin/common/BinaryMessenger;", "api", "Lcom/amazonaws/amplify/amplify_secure_storage/pigeons/AmplifySecureStoragePigeon;", "amplify_secure_storage_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        /* JADX INFO: renamed from: codec$delegate, reason: from kotlin metadata */
        private static final Lazy<StandardMessageCodec> codec = LazyKt.lazy(new Function0<StandardMessageCodec>() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$codec$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final StandardMessageCodec invoke() {
                return new StandardMessageCodec();
            }
        });

        private Companion() {
        }

        public final MessageCodec<Object> getCodec() {
            return codec.getValue();
        }

        public final void setUp(BinaryMessenger binaryMessenger, final AmplifySecureStoragePigeon api) {
            Intrinsics.checkNotNullParameter(binaryMessenger, "binaryMessenger");
            BasicMessageChannel basicMessageChannel = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_secure_storage.AmplifySecureStoragePigeon.read", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (api != null) {
                basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$$ExternalSyntheticLambda0
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        AmplifySecureStoragePigeon.Companion.setUp$lambda$1$lambda$0(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel2 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_secure_storage.AmplifySecureStoragePigeon.write", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (api != null) {
                basicMessageChannel2.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$$ExternalSyntheticLambda1
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        AmplifySecureStoragePigeon.Companion.setUp$lambda$3$lambda$2(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel2.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel3 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_secure_storage.AmplifySecureStoragePigeon.delete", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (api != null) {
                basicMessageChannel3.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$$ExternalSyntheticLambda2
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        AmplifySecureStoragePigeon.Companion.setUp$lambda$5$lambda$4(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel3.setMessageHandler(null);
            }
            BasicMessageChannel basicMessageChannel4 = new BasicMessageChannel(binaryMessenger, "dev.flutter.pigeon.amplify_secure_storage.AmplifySecureStoragePigeon.removeAll", getCodec(), binaryMessenger.makeBackgroundTaskQueue());
            if (api != null) {
                basicMessageChannel4.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$$ExternalSyntheticLambda3
                    @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
                    public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                        AmplifySecureStoragePigeon.Companion.setUp$lambda$7$lambda$6(api, obj, reply);
                    }
                });
            } else {
                basicMessageChannel4.setMessageHandler(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$1$lambda$0(AmplifySecureStoragePigeon amplifySecureStoragePigeon, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
            amplifySecureStoragePigeon.read((String) obj2, (String) obj3, new Function1<Result<? extends String>, Unit>() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$setUp$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends String> result) {
                    m473invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m473invoke(Object obj4) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj4);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(AmplifySecureStoragePigeonKt.wrapError(thM687exceptionOrNullimpl));
                        return;
                    }
                    if (Result.m690isFailureimpl(obj4)) {
                        obj4 = null;
                    }
                    reply.reply(AmplifySecureStoragePigeonKt.wrapResult((String) obj4));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$3$lambda$2(AmplifySecureStoragePigeon amplifySecureStoragePigeon, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
            amplifySecureStoragePigeon.write((String) obj2, (String) obj3, (String) list.get(2), new Function1<Result<? extends Unit>, Unit>() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$setUp$2$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends Unit> result) {
                    m474invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m474invoke(Object obj4) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj4);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(AmplifySecureStoragePigeonKt.wrapError(thM687exceptionOrNullimpl));
                    } else {
                        reply.reply(AmplifySecureStoragePigeonKt.wrapResult(null));
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$5$lambda$4(AmplifySecureStoragePigeon amplifySecureStoragePigeon, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            List list = (List) obj;
            Object obj2 = list.get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            Object obj3 = list.get(1);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
            amplifySecureStoragePigeon.delete((String) obj2, (String) obj3, new Function1<Result<? extends Unit>, Unit>() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$setUp$3$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends Unit> result) {
                    m475invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m475invoke(Object obj4) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj4);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(AmplifySecureStoragePigeonKt.wrapError(thM687exceptionOrNullimpl));
                    } else {
                        reply.reply(AmplifySecureStoragePigeonKt.wrapResult(null));
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void setUp$lambda$7$lambda$6(AmplifySecureStoragePigeon amplifySecureStoragePigeon, Object obj, final BasicMessageChannel.Reply reply) {
            Intrinsics.checkNotNullParameter(reply, "reply");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<kotlin.Any?>");
            Object obj2 = ((List) obj).get(0);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
            amplifySecureStoragePigeon.removeAll((String) obj2, new Function1<Result<? extends Unit>, Unit>() { // from class: com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon$Companion$setUp$4$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Result<? extends Unit> result) {
                    m476invoke(result.getValue());
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m476invoke(Object obj3) {
                    Throwable thM687exceptionOrNullimpl = Result.m687exceptionOrNullimpl(obj3);
                    if (thM687exceptionOrNullimpl != null) {
                        reply.reply(AmplifySecureStoragePigeonKt.wrapError(thM687exceptionOrNullimpl));
                    } else {
                        reply.reply(AmplifySecureStoragePigeonKt.wrapResult(null));
                    }
                }
            });
        }
    }
}
