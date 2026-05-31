package cn.baos.watch.sdk.huabaoImpl.translate;

/* JADX INFO: loaded from: classes.dex */
public interface TranslateCallback {
    void onLoadFile(int i);

    void onLoadFileFail();

    void onTransferFail(int i);

    void onTransferFinish();

    void onTransferProgress(int i);

    void onTranslateStart();

    void onWaitWatchStartTranslate();
}
