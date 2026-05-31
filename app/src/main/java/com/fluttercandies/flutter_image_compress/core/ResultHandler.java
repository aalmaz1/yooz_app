package com.fluttercandies.flutter_image_compress.core;

import android.os.Handler;
import android.os.Looper;
import com.tekartik.sqflite.Constant;
import io.flutter.plugin.common.MethodChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResultHandler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b&\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\u0001J&\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0001R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/core/ResultHandler;", "", Constant.PARAM_RESULT, "Lio/flutter/plugin/common/MethodChannel$Result;", "(Lio/flutter/plugin/common/MethodChannel$Result;)V", "isReply", "", "reply", "", "any", "replyError", Constant.PARAM_ERROR_CODE, "", Constant.PARAM_ERROR_MESSAGE, "obj", "Companion", "flutter_image_compress_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResultHandler {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static final ExecutorService threadPool;
    private boolean isReply;
    private MethodChannel.Result result;

    public static final ExecutorService getThreadPool() {
        return INSTANCE.getThreadPool();
    }

    /* JADX INFO: compiled from: ResultHandler.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u001c\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/core/ResultHandler$Companion;", "", "()V", "handler", "Landroid/os/Handler;", "getHandler$annotations", "threadPool", "Ljava/util/concurrent/ExecutorService;", "getThreadPool$annotations", "getThreadPool", "()Ljava/util/concurrent/ExecutorService;", "flutter_image_compress_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        private static /* synthetic */ void getHandler$annotations() {
        }

        @JvmStatic
        public static /* synthetic */ void getThreadPool$annotations() {
        }

        private Companion() {
        }

        public final ExecutorService getThreadPool() {
            return ResultHandler.threadPool;
        }
    }

    public ResultHandler(MethodChannel.Result result) {
        this.result = result;
    }

    static {
        ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(8);
        Intrinsics.checkNotNullExpressionValue(executorServiceNewFixedThreadPool, "newFixedThreadPool(...)");
        threadPool = executorServiceNewFixedThreadPool;
    }

    public final void reply(final Object any) {
        if (this.isReply) {
            return;
        }
        this.isReply = true;
        final MethodChannel.Result result = this.result;
        this.result = null;
        handler.post(new Runnable() { // from class: com.fluttercandies.flutter_image_compress.core.ResultHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.reply$lambda$0(result, any);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reply$lambda$0(MethodChannel.Result result, Object obj) {
        if (result != null) {
            result.success(obj);
        }
    }

    public static /* synthetic */ void replyError$default(ResultHandler resultHandler, String str, String str2, Object obj, int i, Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: replyError");
        }
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            obj = null;
        }
        resultHandler.replyError(str, str2, obj);
    }

    public final void replyError(final String code, final String message, final Object obj) {
        Intrinsics.checkNotNullParameter(code, "code");
        if (this.isReply) {
            return;
        }
        this.isReply = true;
        final MethodChannel.Result result = this.result;
        this.result = null;
        handler.post(new Runnable() { // from class: com.fluttercandies.flutter_image_compress.core.ResultHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.replyError$lambda$1(result, code, message, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void replyError$lambda$1(MethodChannel.Result result, String code, String str, Object obj) {
        Intrinsics.checkNotNullParameter(code, "$code");
        if (result != null) {
            result.error(code, str, obj);
        }
    }
}
