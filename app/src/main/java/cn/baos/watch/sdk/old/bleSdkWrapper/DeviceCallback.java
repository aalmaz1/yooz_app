package cn.baos.watch.sdk.old.bleSdkWrapper;

import cn.baos.watch.sdk.old.entity.SportInfo;

/* JADX INFO: loaded from: classes.dex */
public interface DeviceCallback {
    void call(int i);

    void callDelay(int i);

    void findPhone(int i);

    void location(int i);

    void music(int i, int i2);

    void sos(int i, int i2);

    void sport(SportInfo sportInfo);

    void sportState(int i);

    void systemStataus(int i);
}
