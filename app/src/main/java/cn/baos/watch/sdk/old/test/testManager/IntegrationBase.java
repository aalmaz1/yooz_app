package cn.baos.watch.sdk.old.test.testManager;

import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public interface IntegrationBase {
    void testFail();

    void testResult(Message message);

    void testStart();
}
