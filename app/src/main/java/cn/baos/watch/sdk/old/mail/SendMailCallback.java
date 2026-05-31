package cn.baos.watch.sdk.old.mail;

/* JADX INFO: loaded from: classes.dex */
public interface SendMailCallback {
    void onFail(Exception exc);

    void onSuccess();
}
