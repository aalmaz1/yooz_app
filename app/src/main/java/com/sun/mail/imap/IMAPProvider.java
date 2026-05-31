package com.sun.mail.imap;

import javax.mail.Provider;

/* JADX INFO: loaded from: classes2.dex */
public class IMAPProvider extends Provider {
    public IMAPProvider() {
        super(Provider.Type.STORE, "imap", IMAPStore.class.getName(), "Oracle", null);
    }
}
