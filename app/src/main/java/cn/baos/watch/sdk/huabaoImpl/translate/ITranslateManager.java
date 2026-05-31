package cn.baos.watch.sdk.huabaoImpl.translate;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public interface ITranslateManager {
    boolean isInTransmission();

    void stopTransmission();

    void transferFile(File file, int i, TranslateCallback translateCallback);
}
