package cn.yoozworld.watch.ui;

/* JADX INFO: loaded from: classes.dex */
public class HbBst {

    public interface BoostLifecycleListener {
        void beforeCreateEngine();

        void onEngineCreated();

        void onEngineDestroy();
    }
}
