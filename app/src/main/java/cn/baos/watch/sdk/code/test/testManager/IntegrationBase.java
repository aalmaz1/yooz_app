package cn.baos.watch.sdk.code.test.testManager;

import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public interface IntegrationBase {
    void testFail();

    void testResult(Message message);

    void testStart();
}
