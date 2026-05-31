package androidx.camera.camera2.internal.compat.quirk;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.util.Size;
import androidx.camera.core.impl.Quirk;
import com.king.camera.scan.config.ResolutionCameraConfig;
import com.king.zxing.util.CodeUtils;
import io.flutter.plugin.platform.PlatformPlugin;

/* JADX INFO: loaded from: classes.dex */
public class ExtraSupportedOutputSizeQuirk implements Quirk {
    static boolean load() {
        return isMotoE5Play();
    }

    private static boolean isMotoE5Play() {
        return "motorola".equalsIgnoreCase(Build.BRAND) && "moto e5 play".equalsIgnoreCase(Build.MODEL);
    }

    public Size[] getExtraSupportedResolutions(int i) {
        return (i == 34 && isMotoE5Play()) ? getMotoE5PlayExtraSupportedResolutions() : new Size[0];
    }

    public <T> Size[] getExtraSupportedResolutions(Class<T> cls) {
        return (StreamConfigurationMap.isOutputSupportedFor(cls) && isMotoE5Play()) ? getMotoE5PlayExtraSupportedResolutions() : new Size[0];
    }

    private Size[] getMotoE5PlayExtraSupportedResolutions() {
        return new Size[]{new Size(1920, 1080), new Size(1440, 1080), new Size(PlatformPlugin.DEFAULT_SYSTEM_UI, ResolutionCameraConfig.IMAGE_QUALITY_720P), new Size(960, ResolutionCameraConfig.IMAGE_QUALITY_720P), new Size(864, CodeUtils.DEFAULT_REQ_WIDTH), new Size(ResolutionCameraConfig.IMAGE_QUALITY_720P, CodeUtils.DEFAULT_REQ_WIDTH)};
    }
}
