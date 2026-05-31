package com.sun.mail.imap;

import com.sun.mail.imap.protocol.BODYSTRUCTURE;
import java.util.ArrayList;
import java.util.List;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.MultipartDataSource;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;

/* JADX INFO: loaded from: classes2.dex */
public class IMAPMultipartDataSource extends MimePartDataSource implements MultipartDataSource {
    private List<IMAPBodyPart> parts;

    protected IMAPMultipartDataSource(MimePart mimePart, BODYSTRUCTURE[] bodystructureArr, String str, IMAPMessage iMAPMessage) {
        String string;
        super(mimePart);
        this.parts = new ArrayList(bodystructureArr.length);
        for (int i = 0; i < bodystructureArr.length; i++) {
            List<IMAPBodyPart> list = this.parts;
            BODYSTRUCTURE bodystructure = bodystructureArr[i];
            if (str == null) {
                string = Integer.toString(i + 1);
            } else {
                string = str + "." + Integer.toString(i + 1);
            }
            list.add(new IMAPBodyPart(bodystructure, string, iMAPMessage));
        }
    }

    @Override // javax.mail.MultipartDataSource
    public int getCount() {
        return this.parts.size();
    }

    @Override // javax.mail.MultipartDataSource
    public BodyPart getBodyPart(int i) throws MessagingException {
        return this.parts.get(i);
    }
}
