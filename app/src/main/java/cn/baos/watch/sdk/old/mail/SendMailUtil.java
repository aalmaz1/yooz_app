package cn.baos.watch.sdk.old.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SendMailUtil {
    private static final String FROM_MAIL_ADDR = "gary19890215@163.com";
    private static final String FROM_MAIL_AUTH_CODE = "SGJHQBAAKECZAZGJ";
    private static final String FROM_MAIL_PORT = "994";
    private static final String FROM_MAIL_SERVER = "smtp.163.com";
    private static List<String> toAddrList;

    static {
        ArrayList arrayList = new ArrayList();
        toAddrList = arrayList;
        arrayList.add("changjingpei@baos.cn");
        toAddrList.add("zhoulihong@baos.cn");
    }

    public static void send(final File file, final String str, final String str2, final SendMailCallback sendMailCallback) {
        new Thread(new Runnable() { // from class: cn.baos.watch.sdk.old.mail.SendMailUtil$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SendMailUtil.lambda$send$0(str, str2, file, sendMailCallback);
            }
        }).start();
    }

    static /* synthetic */ void lambda$send$0(String str, String str2, File file, SendMailCallback sendMailCallback) {
        Iterator<String> it = toAddrList.iterator();
        while (it.hasNext()) {
            try {
                new MailSender().sendFileMail(creatMail(it.next(), str, str2), file);
                if (sendMailCallback != null) {
                    sendMailCallback.onSuccess();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (sendMailCallback != null) {
                    sendMailCallback.onFail(e);
                    return;
                }
                return;
            }
        }
    }

    public static void send(final String str, final String str2, final SendMailCallback sendMailCallback) {
        new Thread(new Runnable() { // from class: cn.baos.watch.sdk.old.mail.SendMailUtil$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SendMailUtil.lambda$send$1(str, str2, sendMailCallback);
            }
        }).start();
    }

    static /* synthetic */ void lambda$send$1(String str, String str2, SendMailCallback sendMailCallback) {
        Iterator<String> it = toAddrList.iterator();
        while (it.hasNext()) {
            try {
                new MailSender().sendTextMail(creatMail(it.next(), str, str2));
                if (sendMailCallback != null) {
                    sendMailCallback.onSuccess();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (sendMailCallback != null) {
                    sendMailCallback.onFail(e);
                    return;
                }
                return;
            }
        }
    }

    private static MailInfo creatMail(String str, String str2, String str3) {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(FROM_MAIL_SERVER);
        mailInfo.setMailServerPort(FROM_MAIL_PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(FROM_MAIL_ADDR);
        mailInfo.setPassword(FROM_MAIL_AUTH_CODE);
        mailInfo.setFromAddress(FROM_MAIL_ADDR);
        mailInfo.setToAddress(str);
        mailInfo.setSubject(str2);
        mailInfo.setContent(str3);
        return mailInfo;
    }
}
