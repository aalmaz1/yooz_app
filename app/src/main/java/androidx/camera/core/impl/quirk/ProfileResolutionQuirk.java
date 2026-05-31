package androidx.camera.core.impl.quirk;

import android.util.Size;
import androidx.camera.core.impl.Quirk;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface ProfileResolutionQuirk extends Quirk {
    List<Size> getSupportedResolutions();
}
