package io.flutter.util;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes2.dex */
public final class HandlerCompat {
    public static Handler createAsyncHandler(Looper looper) {
        return Handler.createAsync(looper);
    }
}
