package com.sun.mail.smtp;

import javax.mail.Provider;

/* JADX INFO: loaded from: classes2.dex */
public class SMTPSSLProvider extends Provider {
    public SMTPSSLProvider() {
        super(Provider.Type.TRANSPORT, "smtps", SMTPSSLTransport.class.getName(), "Oracle", null);
    }
}
