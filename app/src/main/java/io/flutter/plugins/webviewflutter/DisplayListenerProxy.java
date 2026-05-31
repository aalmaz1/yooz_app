package io.flutter.plugins.webviewflutter;

import android.hardware.display.DisplayManager;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
class DisplayListenerProxy {
    private static final String TAG = "DisplayListenerProxy";
    private ArrayList<DisplayManager.DisplayListener> listenersBeforeWebView;

    DisplayListenerProxy() {
    }

    void onPreWebViewInitialization(DisplayManager displayManager) {
        this.listenersBeforeWebView = yoinkDisplayListeners(displayManager);
    }

    void onPostWebViewInitialization(final DisplayManager displayManager) {
        final ArrayList<DisplayManager.DisplayListener> arrayListYoinkDisplayListeners = yoinkDisplayListeners(displayManager);
        arrayListYoinkDisplayListeners.removeAll(this.listenersBeforeWebView);
        if (arrayListYoinkDisplayListeners.isEmpty()) {
            return;
        }
        Iterator<DisplayManager.DisplayListener> it = arrayListYoinkDisplayListeners.iterator();
        while (it.hasNext()) {
            displayManager.unregisterDisplayListener(it.next());
            displayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: io.flutter.plugins.webviewflutter.DisplayListenerProxy.1
                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayAdded(int i) {
                    Iterator it2 = arrayListYoinkDisplayListeners.iterator();
                    while (it2.hasNext()) {
                        ((DisplayManager.DisplayListener) it2.next()).onDisplayAdded(i);
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayRemoved(int i) {
                    Iterator it2 = arrayListYoinkDisplayListeners.iterator();
                    while (it2.hasNext()) {
                        ((DisplayManager.DisplayListener) it2.next()).onDisplayRemoved(i);
                    }
                }

                @Override // android.hardware.display.DisplayManager.DisplayListener
                public void onDisplayChanged(int i) {
                    if (displayManager.getDisplay(i) == null) {
                        return;
                    }
                    Iterator it2 = arrayListYoinkDisplayListeners.iterator();
                    while (it2.hasNext()) {
                        ((DisplayManager.DisplayListener) it2.next()).onDisplayChanged(i);
                    }
                }
            }, null);
        }
    }

    private static ArrayList<DisplayManager.DisplayListener> yoinkDisplayListeners(DisplayManager displayManager) {
        return new ArrayList<>();
    }
}
