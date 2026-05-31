package com.sun.mail.smtp;

import javax.mail.Provider;

/* JADX INFO: loaded from: classes2.dex */
public class SMTPProvider extends Provider {
    public SMTPProvider() {
        super(Provider.Type.TRANSPORT, "smtp", SMTPTransport.class.getName(), "Oracle", null);
    }
}
