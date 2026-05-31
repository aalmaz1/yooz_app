package com.fluttercandies.photo_manager.core.utils;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0005¨\u0006\u0006"}, d2 = {"checkDirs", "", "", "getOrientationDegrees", "", "Ljava/io/InputStream;", "photo_manager_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonExtKt {
    public static final void checkDirs(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        File file = new File(str);
        File parentFile = file.getParentFile();
        Intrinsics.checkNotNull(parentFile);
        if (parentFile.exists()) {
            return;
        }
        File parentFile2 = file.getParentFile();
        Intrinsics.checkNotNull(parentFile2);
        parentFile2.mkdirs();
    }

    public static final int getOrientationDegrees(InputStream inputStream) {
        Intrinsics.checkNotNullParameter(inputStream, "<this>");
        try {
            InputStream inputStream2 = inputStream;
            try {
                int rotationDegrees = new ExifInterface(inputStream2).getRotationDegrees();
                CloseableKt.closeFinally(inputStream2, null);
                return rotationDegrees;
            } finally {
            }
        } catch (Throwable unused) {
            return 0;
        }
    }
}
