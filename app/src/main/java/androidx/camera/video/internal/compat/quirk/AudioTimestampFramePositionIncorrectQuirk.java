package androidx.camera.video.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;

/* JADX INFO: loaded from: classes.dex */
public class AudioTimestampFramePositionIncorrectQuirk implements Quirk {
    static boolean load() {
        return isRedmi6A();
    }

    private static boolean isRedmi6A() {
        return "Xiaomi".equalsIgnoreCase(Build.BRAND) && "Redmi 6A".equalsIgnoreCase(Build.MODEL);
    }
}
