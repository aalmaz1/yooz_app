package javax.mail.internet;

import com.sun.mail.util.FolderClosedIOException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownServiceException;
import javax.activation.DataSource;
import javax.mail.FolderClosedException;
import javax.mail.MessageAware;
import javax.mail.MessageContext;
import javax.mail.MessagingException;

/* JADX INFO: loaded from: classes3.dex */
public class MimePartDataSource implements DataSource, MessageAware {
    private MessageContext context;
    protected MimePart part;

    public MimePartDataSource(MimePart mimePart) {
        this.part = mimePart;
    }

    @Override // javax.activation.DataSource
    public InputStream getInputStream() throws IOException {
        InputStream contentStream;
        try {
            MimePart mimePart = this.part;
            if (mimePart instanceof MimeBodyPart) {
                contentStream = ((MimeBodyPart) mimePart).getContentStream();
            } else if (mimePart instanceof MimeMessage) {
                contentStream = ((MimeMessage) mimePart).getContentStream();
            } else {
                throw new MessagingException("Unknown part");
            }
            MimePart mimePart2 = this.part;
            String strRestrictEncoding = MimeBodyPart.restrictEncoding(mimePart2, mimePart2.getEncoding());
            return strRestrictEncoding != null ? MimeUtility.decode(contentStream, strRestrictEncoding) : contentStream;
        } catch (FolderClosedException e) {
            throw new FolderClosedIOException(e.getFolder(), e.getMessage());
        } catch (MessagingException e2) {
            IOException iOException = new IOException(e2.getMessage());
            iOException.initCause(e2);
            throw iOException;
        }
    }

    @Override // javax.activation.DataSource
    public OutputStream getOutputStream() throws IOException {
        throw new UnknownServiceException("Writing not supported");
    }

    @Override // javax.activation.DataSource
    public String getContentType() {
        try {
            return this.part.getContentType();
        } catch (MessagingException unused) {
            return "application/octet-stream";
        }
    }

    @Override // javax.activation.DataSource
    public String getName() {
        try {
            MimePart mimePart = this.part;
            return mimePart instanceof MimeBodyPart ? ((MimeBodyPart) mimePart).getFileName() : "";
        } catch (MessagingException unused) {
            return "";
        }
    }

    @Override // javax.mail.MessageAware
    public synchronized MessageContext getMessageContext() {
        if (this.context == null) {
            this.context = new MessageContext(this.part);
        }
        return this.context;
    }
}
