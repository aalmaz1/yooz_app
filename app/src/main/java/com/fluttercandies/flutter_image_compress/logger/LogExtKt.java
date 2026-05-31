package com.fluttercandies.flutter_image_compress.logger;

import android.util.Log;
import com.fluttercandies.flutter_image_compress.ImageCompressPlugin;
import com.fluttercandies.photo_manager.constant.Methods;
import kotlin.Metadata;

/* JADX INFO: compiled from: LogExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¨\u0006\u0004"}, d2 = {Methods.log, "", "any", "", "flutter_image_compress_common_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LogExtKt {
    public static final void log(Object obj) {
        String string;
        if (ImageCompressPlugin.INSTANCE.getShowLog()) {
            if (obj == null || (string = obj.toString()) == null) {
                string = "null";
            }
            Log.i("flutter_image_compress", string);
        }
    }
}
