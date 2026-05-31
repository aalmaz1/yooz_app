package com.fluttercandies.flutter_image_compress.exception;

import com.tekartik.sqflite.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompressError.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/exception/CompressError;", "Ljava/lang/Exception;", Constant.PARAM_ERROR_MESSAGE, "", "(Ljava/lang/String;)V", "flutter_image_compress_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompressError extends Exception {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompressError(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
    }
}
