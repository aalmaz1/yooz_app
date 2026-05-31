package io.flutter.embedding.engine.plugins.broadcastreceiver;

import android.content.BroadcastReceiver;
import androidx.lifecycle.Lifecycle;

/* JADX INFO: loaded from: classes2.dex */
public interface BroadcastReceiverControlSurface {
    void attachToBroadcastReceiver(BroadcastReceiver broadcastReceiver, Lifecycle lifecycle);

    void detachFromBroadcastReceiver();
}
