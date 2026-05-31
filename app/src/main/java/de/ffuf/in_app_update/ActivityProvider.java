package de.ffuf.in_app_update;

import android.app.Activity;
import io.flutter.plugin.common.PluginRegistry;
import kotlin.Metadata;

/* JADX INFO: compiled from: InAppUpdatePlugin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lde/ffuf/in_app_update/ActivityProvider;", "", "activity", "Landroid/app/Activity;", "addActivityResultListener", "", "callback", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", "in_app_update_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ActivityProvider {
    Activity activity();

    void addActivityResultListener(PluginRegistry.ActivityResultListener callback);
}
