package com.fluttercandies.photo_manager.util;

import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.tekartik.sqflite.Constant;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ResultHandler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001J&\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00152\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lcom/fluttercandies/photo_manager/util/ResultHandler;", "", Constant.PARAM_RESULT, "Lio/flutter/plugin/common/MethodChannel$Result;", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "(Lio/flutter/plugin/common/MethodChannel$Result;Lio/flutter/plugin/common/MethodCall;)V", "getCall", "()Lio/flutter/plugin/common/MethodCall;", "isReplied", "", "getResult", "()Lio/flutter/plugin/common/MethodChannel$Result;", "setResult", "(Lio/flutter/plugin/common/MethodChannel$Result;)V", "notImplemented", "", "reply", "any", "replyError", Constant.PARAM_ERROR_CODE, "", Constant.PARAM_ERROR_MESSAGE, "obj", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResultHandler {
    public static final Handler handler = new Handler(Looper.getMainLooper());
    private final MethodCall call;
    private boolean isReplied;
    private MethodChannel.Result result;

    public ResultHandler(MethodChannel.Result result, MethodCall call) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(call, "call");
        this.result = result;
        this.call = call;
        handler.hasMessages(0);
    }

    public final MethodCall getCall() {
        return this.call;
    }

    public final MethodChannel.Result getResult() {
        return this.result;
    }

    public final void setResult(MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(result, "<set-?>");
        this.result = result;
    }

    public final void reply(final Object any) {
        if (this.isReplied) {
            return;
        }
        this.isReplied = true;
        final MethodChannel.Result result = this.result;
        handler.post(new Runnable() { // from class: com.fluttercandies.photo_manager.util.ResultHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.reply$lambda$0(result, any);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void reply$lambda$0(MethodChannel.Result result, Object obj) {
        Intrinsics.checkNotNullParameter(result, "$result");
        try {
            result.success(obj);
        } catch (IllegalStateException unused) {
        }
    }

    public static /* synthetic */ void replyError$default(ResultHandler resultHandler, String str, String str2, Object obj, int i, Object obj2) {
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
        if (this.isReplied) {
            return;
        }
        this.isReplied = true;
        final MethodChannel.Result result = this.result;
        handler.post(new Runnable() { // from class: com.fluttercandies.photo_manager.util.ResultHandler$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.replyError$lambda$1(result, code, message, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void replyError$lambda$1(MethodChannel.Result result, String code, String str, Object obj) {
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(code, "$code");
        result.error(code, str, obj);
    }

    public final void notImplemented() {
        if (this.isReplied) {
            return;
        }
        this.isReplied = true;
        final MethodChannel.Result result = this.result;
        handler.post(new Runnable() { // from class: com.fluttercandies.photo_manager.util.ResultHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ResultHandler.notImplemented$lambda$2(result);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void notImplemented$lambda$2(MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(result, "$result");
        result.notImplemented();
    }

    /* JADX INFO: renamed from: isReplied, reason: from getter */
    public final boolean getIsReplied() {
        return this.isReplied;
    }
}
