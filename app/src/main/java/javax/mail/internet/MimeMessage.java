package javax.mail.internet;

import androidx.webkit.internal.AssetHelper;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.LineOutputStream;
import com.sun.mail.util.MessageRemovedIOException;
import com.sun.mail.util.MimeUtil;
import com.sun.mail.util.PropUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.SharedByteArrayInputStream;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes3.dex */
public class MimeMessage extends Message implements MimePart {
    private boolean allowutf8;
    protected Object cachedContent;
    protected byte[] content;
    protected InputStream contentStream;
    protected DataHandler dh;
    protected Flags flags;
    protected InternetHeaders headers;
    protected boolean modified;
    protected boolean saved;
    private boolean strict;
    private static final MailDateFormat mailDateFormat = new MailDateFormat();
    private static final Flags answeredFlag = new Flags(Flags.Flag.ANSWERED);

    @Override // javax.mail.Part
    public int getLineCount() throws MessagingException {
        return -1;
    }

    @Override // javax.mail.Message
    public Date getReceivedDate() throws MessagingException {
        return null;
    }

    public MimeMessage(Session session) {
        super(session);
        this.saved = false;
        this.strict = true;
        this.allowutf8 = false;
        this.modified = true;
        this.headers = new InternetHeaders();
        this.flags = new Flags();
        initStrict();
    }

    public MimeMessage(Session session, InputStream inputStream) throws MessagingException {
        super(session);
        this.modified = false;
        this.saved = false;
        this.strict = true;
        this.allowutf8 = false;
        this.flags = new Flags();
        initStrict();
        parse(inputStream);
        this.saved = true;
    }

    public MimeMessage(MimeMessage mimeMessage) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        super(mimeMessage.session);
        this.modified = false;
        this.saved = false;
        this.strict = true;
        this.allowutf8 = false;
        Flags flags = mimeMessage.getFlags();
        this.flags = flags;
        if (flags == null) {
            this.flags = new Flags();
        }
        int size = mimeMessage.getSize();
        if (size > 0) {
            byteArrayOutputStream = new ByteArrayOutputStream(size);
        } else {
            byteArrayOutputStream = new ByteArrayOutputStream();
        }
        try {
            this.strict = mimeMessage.strict;
            mimeMessage.writeTo(byteArrayOutputStream);
            byteArrayOutputStream.close();
            SharedByteArrayInputStream sharedByteArrayInputStream = new SharedByteArrayInputStream(byteArrayOutputStream.toByteArray());
            parse(sharedByteArrayInputStream);
            sharedByteArrayInputStream.close();
            this.saved = true;
        } catch (IOException e) {
            throw new MessagingException("IOException while copying message", e);
        }
    }

    protected MimeMessage(Folder folder, int i) {
        super(folder, i);
        this.modified = false;
        this.saved = false;
        this.strict = true;
        this.allowutf8 = false;
        this.flags = new Flags();
        this.saved = true;
        initStrict();
    }

    protected MimeMessage(Folder folder, InputStream inputStream, int i) throws MessagingException {
        this(folder, i);
        initStrict();
        parse(inputStream);
    }

    protected MimeMessage(Folder folder, InternetHeaders internetHeaders, byte[] bArr, int i) throws MessagingException {
        this(folder, i);
        this.headers = internetHeaders;
        this.content = bArr;
        initStrict();
    }

    private void initStrict() {
        if (this.session != null) {
            Properties properties = this.session.getProperties();
            this.strict = PropUtil.getBooleanProperty(properties, "mail.mime.address.strict", true);
            this.allowutf8 = PropUtil.getBooleanProperty(properties, "mail.mime.allowutf8", false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void parse(InputStream inputStream) throws MessagingException {
        boolean z = inputStream instanceof ByteArrayInputStream;
        InputStream bufferedInputStream = inputStream;
        if (!z) {
            boolean z2 = inputStream instanceof BufferedInputStream;
            bufferedInputStream = inputStream;
            if (!z2) {
                boolean z3 = inputStream instanceof SharedInputStream;
                bufferedInputStream = inputStream;
                if (!z3) {
                    bufferedInputStream = new BufferedInputStream(inputStream);
                }
            }
        }
        this.headers = createInternetHeaders(bufferedInputStream);
        if (bufferedInputStream instanceof SharedInputStream) {
            SharedInputStream sharedInputStream = (SharedInputStream) bufferedInputStream;
            this.contentStream = sharedInputStream.newStream(sharedInputStream.getPosition(), -1L);
        } else {
            try {
                this.content = ASCIIUtility.getBytes(bufferedInputStream);
            } catch (IOException e) {
                throw new MessagingException("IOException", e);
            }
        }
        this.modified = false;
    }

    @Override // javax.mail.Message
    public Address[] getFrom() throws MessagingException {
        Address[] addressHeader = getAddressHeader("From");
        return addressHeader == null ? getAddressHeader("Sender") : addressHeader;
    }

    @Override // javax.mail.Message
    public void setFrom(Address address) throws MessagingException {
        if (address == null) {
            removeHeader("From");
        } else {
            setHeader("From", MimeUtility.fold(6, address.toString()));
        }
    }

    public void setFrom(String str) throws MessagingException {
        if (str == null) {
            removeHeader("From");
        } else {
            setAddressHeader("From", InternetAddress.parse(str));
        }
    }

    @Override // javax.mail.Message
    public void setFrom() throws MessagingException {
        try {
            InternetAddress internetAddress_getLocalAddress = InternetAddress._getLocalAddress(this.session);
            if (internetAddress_getLocalAddress != null) {
                setFrom(internetAddress_getLocalAddress);
                return;
            }
            throw new MessagingException("No From address");
        } catch (Exception e) {
            throw new MessagingException("No From address", e);
        }
    }

    @Override // javax.mail.Message
    public void addFrom(Address[] addressArr) throws MessagingException {
        addAddressHeader("From", addressArr);
    }

    public Address getSender() throws MessagingException {
        Address[] addressHeader = getAddressHeader("Sender");
        if (addressHeader == null || addressHeader.length == 0) {
            return null;
        }
        return addressHeader[0];
    }

    public void setSender(Address address) throws MessagingException {
        if (address == null) {
            removeHeader("Sender");
        } else {
            setHeader("Sender", MimeUtility.fold(8, address.toString()));
        }
    }

    public static class RecipientType extends Message.RecipientType {
        public static final RecipientType NEWSGROUPS = new RecipientType("Newsgroups");
        private static final long serialVersionUID = -5468290701714395543L;

        protected RecipientType(String str) {
            super(str);
        }

        @Override // javax.mail.Message.RecipientType
        protected Object readResolve() throws ObjectStreamException {
            if (this.type.equals("Newsgroups")) {
                return NEWSGROUPS;
            }
            return super.readResolve();
        }
    }

    @Override // javax.mail.Message
    public Address[] getRecipients(Message.RecipientType recipientType) throws MessagingException {
        if (recipientType == RecipientType.NEWSGROUPS) {
            String header = getHeader("Newsgroups", ",");
            if (header == null) {
                return null;
            }
            return NewsAddress.parse(header);
        }
        return getAddressHeader(getHeaderName(recipientType));
    }

    @Override // javax.mail.Message
    public Address[] getAllRecipients() throws MessagingException {
        Address[] allRecipients = super.getAllRecipients();
        Address[] recipients = getRecipients(RecipientType.NEWSGROUPS);
        if (recipients == null) {
            return allRecipients;
        }
        if (allRecipients == null) {
            return recipients;
        }
        Address[] addressArr = new Address[allRecipients.length + recipients.length];
        System.arraycopy(allRecipients, 0, addressArr, 0, allRecipients.length);
        System.arraycopy(recipients, 0, addressArr, allRecipients.length, recipients.length);
        return addressArr;
    }

    @Override // javax.mail.Message
    public void setRecipients(Message.RecipientType recipientType, Address[] addressArr) throws MessagingException {
        if (recipientType == RecipientType.NEWSGROUPS) {
            if (addressArr == null || addressArr.length == 0) {
                removeHeader("Newsgroups");
                return;
            } else {
                setHeader("Newsgroups", NewsAddress.toString(addressArr));
                return;
            }
        }
        setAddressHeader(getHeaderName(recipientType), addressArr);
    }

    public void setRecipients(Message.RecipientType recipientType, String str) throws MessagingException {
        if (recipientType == RecipientType.NEWSGROUPS) {
            if (str == null || str.length() == 0) {
                removeHeader("Newsgroups");
                return;
            } else {
                setHeader("Newsgroups", str);
                return;
            }
        }
        setAddressHeader(getHeaderName(recipientType), str == null ? null : InternetAddress.parse(str));
    }

    @Override // javax.mail.Message
    public void addRecipients(Message.RecipientType recipientType, Address[] addressArr) throws MessagingException {
        if (recipientType == RecipientType.NEWSGROUPS) {
            String string = NewsAddress.toString(addressArr);
            if (string != null) {
                addHeader("Newsgroups", string);
                return;
            }
            return;
        }
        addAddressHeader(getHeaderName(recipientType), addressArr);
    }

    public void addRecipients(Message.RecipientType recipientType, String str) throws MessagingException {
        if (recipientType == RecipientType.NEWSGROUPS) {
            if (str == null || str.length() == 0) {
                return;
            }
            addHeader("Newsgroups", str);
            return;
        }
        addAddressHeader(getHeaderName(recipientType), InternetAddress.parse(str));
    }

    @Override // javax.mail.Message
    public Address[] getReplyTo() throws MessagingException {
        Address[] addressHeader = getAddressHeader("Reply-To");
        return (addressHeader == null || addressHeader.length == 0) ? getFrom() : addressHeader;
    }

    @Override // javax.mail.Message
    public void setReplyTo(Address[] addressArr) throws MessagingException {
        setAddressHeader("Reply-To", addressArr);
    }

    private Address[] getAddressHeader(String str) throws MessagingException {
        String header = getHeader(str, ",");
        if (header == null) {
            return null;
        }
        return InternetAddress.parseHeader(header, this.strict);
    }

    private void setAddressHeader(String str, Address[] addressArr) throws MessagingException {
        String string;
        if (this.allowutf8) {
            string = InternetAddress.toUnicodeString(addressArr, str.length() + 2);
        } else {
            string = InternetAddress.toString(addressArr, str.length() + 2);
        }
        if (string == null) {
            removeHeader(str);
        } else {
            setHeader(str, string);
        }
    }

    private void addAddressHeader(String str, Address[] addressArr) throws MessagingException {
        String string;
        if (addressArr == null || addressArr.length == 0) {
            return;
        }
        Address[] addressHeader = getAddressHeader(str);
        if (addressHeader != null && addressHeader.length != 0) {
            Address[] addressArr2 = new Address[addressHeader.length + addressArr.length];
            System.arraycopy(addressHeader, 0, addressArr2, 0, addressHeader.length);
            System.arraycopy(addressArr, 0, addressArr2, addressHeader.length, addressArr.length);
            addressArr = addressArr2;
        }
        if (this.allowutf8) {
            string = InternetAddress.toUnicodeString(addressArr, str.length() + 2);
        } else {
            string = InternetAddress.toString(addressArr, str.length() + 2);
        }
        if (string == null) {
            return;
        }
        setHeader(str, string);
    }

    @Override // javax.mail.Message
    public String getSubject() throws MessagingException {
        String header = getHeader("Subject", null);
        if (header == null) {
            return null;
        }
        try {
            return MimeUtility.decodeText(MimeUtility.unfold(header));
        } catch (UnsupportedEncodingException unused) {
            return header;
        }
    }

    @Override // javax.mail.Message
    public void setSubject(String str) throws MessagingException {
        setSubject(str, null);
    }

    public void setSubject(String str, String str2) throws MessagingException {
        if (str == null) {
            removeHeader("Subject");
            return;
        }
        try {
            setHeader("Subject", MimeUtility.fold(9, MimeUtility.encodeText(str, str2, null)));
        } catch (UnsupportedEncodingException e) {
            throw new MessagingException("Encoding error", e);
        }
    }

    @Override // javax.mail.Message
    public Date getSentDate() throws MessagingException {
        Date date;
        String header = getHeader("Date", null);
        if (header != null) {
            try {
                MailDateFormat mailDateFormat2 = mailDateFormat;
                synchronized (mailDateFormat2) {
                    date = mailDateFormat2.parse(header);
                }
                return date;
            } catch (java.text.ParseException unused) {
            }
        }
        return null;
    }

    @Override // javax.mail.Message
    public void setSentDate(Date date) throws MessagingException {
        if (date == null) {
            removeHeader("Date");
            return;
        }
        MailDateFormat mailDateFormat2 = mailDateFormat;
        synchronized (mailDateFormat2) {
            setHeader("Date", mailDateFormat2.format(date));
        }
    }

    @Override // javax.mail.Part
    public int getSize() throws MessagingException {
        byte[] bArr = this.content;
        if (bArr != null) {
            return bArr.length;
        }
        InputStream inputStream = this.contentStream;
        if (inputStream == null) {
            return -1;
        }
        try {
            int iAvailable = inputStream.available();
            if (iAvailable > 0) {
                return iAvailable;
            }
            return -1;
        } catch (IOException unused) {
            return -1;
        }
    }

    @Override // javax.mail.Part
    public String getContentType() throws MessagingException {
        String strCleanContentType = MimeUtil.cleanContentType(this, getHeader("Content-Type", null));
        return strCleanContentType == null ? AssetHelper.DEFAULT_MIME_TYPE : strCleanContentType;
    }

    @Override // javax.mail.Part
    public boolean isMimeType(String str) throws MessagingException {
        return MimeBodyPart.isMimeType(this, str);
    }

    @Override // javax.mail.Part
    public String getDisposition() throws MessagingException {
        return MimeBodyPart.getDisposition(this);
    }

    @Override // javax.mail.Part
    public void setDisposition(String str) throws MessagingException {
        MimeBodyPart.setDisposition(this, str);
    }

    public String getEncoding() throws MessagingException {
        return MimeBodyPart.getEncoding(this);
    }

    public String getContentID() throws MessagingException {
        return getHeader("Content-Id", null);
    }

    public void setContentID(String str) throws MessagingException {
        if (str == null) {
            removeHeader("Content-ID");
        } else {
            setHeader("Content-ID", str);
        }
    }

    public String getContentMD5() throws MessagingException {
        return getHeader("Content-MD5", null);
    }

    public void setContentMD5(String str) throws MessagingException {
        setHeader("Content-MD5", str);
    }

    @Override // javax.mail.Part
    public String getDescription() throws MessagingException {
        return MimeBodyPart.getDescription(this);
    }

    @Override // javax.mail.Part
    public void setDescription(String str) throws MessagingException {
        setDescription(str, null);
    }

    public void setDescription(String str, String str2) throws MessagingException {
        MimeBodyPart.setDescription(this, str, str2);
    }

    public String[] getContentLanguage() throws MessagingException {
        return MimeBodyPart.getContentLanguage(this);
    }

    public void setContentLanguage(String[] strArr) throws MessagingException {
        MimeBodyPart.setContentLanguage(this, strArr);
    }

    public String getMessageID() throws MessagingException {
        return getHeader("Message-ID", null);
    }

    @Override // javax.mail.Part
    public String getFileName() throws MessagingException {
        return MimeBodyPart.getFileName(this);
    }

    @Override // javax.mail.Part
    public void setFileName(String str) throws MessagingException {
        MimeBodyPart.setFileName(this, str);
    }

    private String getHeaderName(Message.RecipientType recipientType) throws MessagingException {
        if (recipientType == Message.RecipientType.TO) {
            return "To";
        }
        if (recipientType == Message.RecipientType.CC) {
            return "Cc";
        }
        if (recipientType == Message.RecipientType.BCC) {
            return "Bcc";
        }
        if (recipientType == RecipientType.NEWSGROUPS) {
            return "Newsgroups";
        }
        throw new MessagingException("Invalid Recipient Type");
    }

    @Override // javax.mail.Part
    public InputStream getInputStream() throws MessagingException, IOException {
        return getDataHandler().getInputStream();
    }

    protected InputStream getContentStream() throws MessagingException {
        Closeable closeable = this.contentStream;
        if (closeable != null) {
            return ((SharedInputStream) closeable).newStream(0L, -1L);
        }
        if (this.content != null) {
            return new SharedByteArrayInputStream(this.content);
        }
        throw new MessagingException("No MimeMessage content");
    }

    public InputStream getRawInputStream() throws MessagingException {
        return getContentStream();
    }

    @Override // javax.mail.Part
    public synchronized DataHandler getDataHandler() throws MessagingException {
        if (this.dh == null) {
            this.dh = new MimeBodyPart.MimePartDataHandler(this);
        }
        return this.dh;
    }

    @Override // javax.mail.Part
    public Object getContent() throws MessagingException, IOException {
        Object obj = this.cachedContent;
        if (obj != null) {
            return obj;
        }
        try {
            Object content = getDataHandler().getContent();
            if (MimeBodyPart.cacheMultipart && (((content instanceof Multipart) || (content instanceof Message)) && (this.content != null || this.contentStream != null))) {
                this.cachedContent = content;
                if (content instanceof MimeMultipart) {
                    ((MimeMultipart) content).parse();
                }
            }
            return content;
        } catch (FolderClosedIOException e) {
            throw new FolderClosedException(e.getFolder(), e.getMessage());
        } catch (MessageRemovedIOException e2) {
            throw new MessageRemovedException(e2.getMessage());
        }
    }

    @Override // javax.mail.Part
    public synchronized void setDataHandler(DataHandler dataHandler) throws MessagingException {
        this.dh = dataHandler;
        this.cachedContent = null;
        MimeBodyPart.invalidateContentHeaders(this);
    }

    @Override // javax.mail.Part
    public void setContent(Object obj, String str) throws MessagingException {
        if (obj instanceof Multipart) {
            setContent((Multipart) obj);
        } else {
            setDataHandler(new DataHandler(obj, str));
        }
    }

    @Override // javax.mail.Part, javax.mail.internet.MimePart
    public void setText(String str) throws MessagingException {
        setText(str, null);
    }

    @Override // javax.mail.internet.MimePart
    public void setText(String str, String str2) throws MessagingException {
        MimeBodyPart.setText(this, str, str2, "plain");
    }

    @Override // javax.mail.internet.MimePart
    public void setText(String str, String str2, String str3) throws MessagingException {
        MimeBodyPart.setText(this, str, str2, str3);
    }

    @Override // javax.mail.Part
    public void setContent(Multipart multipart) throws MessagingException {
        setDataHandler(new DataHandler(multipart, multipart.getContentType()));
        multipart.setParent(this);
    }

    @Override // javax.mail.Message
    public Message reply(boolean z) throws MessagingException {
        return reply(z, true);
    }

    public Message reply(boolean z, boolean z2) throws MessagingException {
        MimeMessage mimeMessageCreateMimeMessage = createMimeMessage(this.session);
        String header = getHeader("Subject", null);
        if (header != null) {
            if (!header.regionMatches(true, 0, "Re: ", 0, 4)) {
                header = "Re: " + header;
            }
            mimeMessageCreateMimeMessage.setHeader("Subject", header);
        }
        Address[] replyTo = getReplyTo();
        mimeMessageCreateMimeMessage.setRecipients(Message.RecipientType.TO, replyTo);
        if (z) {
            ArrayList arrayList = new ArrayList();
            InternetAddress localAddress = InternetAddress.getLocalAddress(this.session);
            if (localAddress != null) {
                arrayList.add(localAddress);
            }
            String property = this.session != null ? this.session.getProperty("mail.alternates") : null;
            if (property != null) {
                eliminateDuplicates(arrayList, InternetAddress.parse(property, false));
            }
            boolean booleanProperty = this.session != null ? PropUtil.getBooleanProperty(this.session.getProperties(), "mail.replyallcc", false) : false;
            eliminateDuplicates(arrayList, replyTo);
            Address[] addressArrEliminateDuplicates = eliminateDuplicates(arrayList, getRecipients(Message.RecipientType.TO));
            if (addressArrEliminateDuplicates != null && addressArrEliminateDuplicates.length > 0) {
                if (booleanProperty) {
                    mimeMessageCreateMimeMessage.addRecipients(Message.RecipientType.CC, addressArrEliminateDuplicates);
                } else {
                    mimeMessageCreateMimeMessage.addRecipients(Message.RecipientType.TO, addressArrEliminateDuplicates);
                }
            }
            Address[] addressArrEliminateDuplicates2 = eliminateDuplicates(arrayList, getRecipients(Message.RecipientType.CC));
            if (addressArrEliminateDuplicates2 != null && addressArrEliminateDuplicates2.length > 0) {
                mimeMessageCreateMimeMessage.addRecipients(Message.RecipientType.CC, addressArrEliminateDuplicates2);
            }
            Address[] recipients = getRecipients(RecipientType.NEWSGROUPS);
            if (recipients != null && recipients.length > 0) {
                mimeMessageCreateMimeMessage.setRecipients(RecipientType.NEWSGROUPS, recipients);
            }
        }
        String header2 = getHeader("Message-Id", null);
        if (header2 != null) {
            mimeMessageCreateMimeMessage.setHeader("In-Reply-To", header2);
        }
        String header3 = getHeader("References", StringUtils.SPACE);
        if (header3 == null) {
            header3 = getHeader("In-Reply-To", StringUtils.SPACE);
        }
        if (header2 == null) {
            header2 = header3;
        } else if (header3 != null) {
            header2 = MimeUtility.unfold(header3) + StringUtils.SPACE + header2;
        }
        if (header2 != null) {
            mimeMessageCreateMimeMessage.setHeader("References", MimeUtility.fold(12, header2));
        }
        if (z2) {
            try {
                setFlags(answeredFlag, true);
            } catch (MessagingException unused) {
            }
        }
        return mimeMessageCreateMimeMessage;
    }

    private Address[] eliminateDuplicates(List<Address> list, Address[] addressArr) {
        Address[] addressArr2;
        boolean z;
        if (addressArr == null) {
            return null;
        }
        int i = 0;
        for (int i2 = 0; i2 < addressArr.length; i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= list.size()) {
                    z = false;
                    break;
                }
                if (((InternetAddress) list.get(i3)).equals(addressArr[i2])) {
                    i++;
                    addressArr[i2] = null;
                    z = true;
                    break;
                }
                i3++;
            }
            if (!z) {
                list.add(addressArr[i2]);
            }
        }
        if (i == 0) {
            return addressArr;
        }
        if (addressArr instanceof InternetAddress[]) {
            addressArr2 = new InternetAddress[addressArr.length - i];
        } else {
            addressArr2 = new Address[addressArr.length - i];
        }
        int i4 = 0;
        for (Address address : addressArr) {
            if (address != null) {
                addressArr2[i4] = address;
                i4++;
            }
        }
        return addressArr2;
    }

    @Override // javax.mail.Part
    public void writeTo(OutputStream outputStream) throws Throwable {
        writeTo(outputStream, null);
    }

    public void writeTo(OutputStream outputStream, String[] strArr) throws Throwable {
        InputStream contentStream;
        if (!this.saved) {
            saveChanges();
        }
        if (this.modified) {
            MimeBodyPart.writeTo(this, outputStream, strArr);
            return;
        }
        Enumeration<String> nonMatchingHeaderLines = getNonMatchingHeaderLines(strArr);
        LineOutputStream lineOutputStream = new LineOutputStream(outputStream, this.allowutf8);
        while (nonMatchingHeaderLines.hasMoreElements()) {
            lineOutputStream.writeln(nonMatchingHeaderLines.nextElement());
        }
        lineOutputStream.writeln();
        byte[] bArr = this.content;
        if (bArr == null) {
            byte[] bArr2 = new byte[8192];
            try {
                contentStream = getContentStream();
                while (true) {
                    try {
                        int i = contentStream.read(bArr2);
                        if (i <= 0) {
                            break;
                        } else {
                            outputStream.write(bArr2, 0, i);
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (contentStream != null) {
                            contentStream.close();
                        }
                        throw th;
                    }
                }
                if (contentStream != null) {
                    contentStream.close();
                }
            } catch (Throwable th2) {
                th = th2;
                contentStream = null;
            }
        } else {
            outputStream.write(bArr);
        }
        outputStream.flush();
    }

    @Override // javax.mail.Part
    public String[] getHeader(String str) throws MessagingException {
        return this.headers.getHeader(str);
    }

    public String getHeader(String str, String str2) throws MessagingException {
        return this.headers.getHeader(str, str2);
    }

    @Override // javax.mail.Part
    public void setHeader(String str, String str2) throws MessagingException {
        this.headers.setHeader(str, str2);
    }

    @Override // javax.mail.Part
    public void addHeader(String str, String str2) throws MessagingException {
        this.headers.addHeader(str, str2);
    }

    @Override // javax.mail.Part
    public void removeHeader(String str) throws MessagingException {
        this.headers.removeHeader(str);
    }

    @Override // javax.mail.Part
    public Enumeration<Header> getAllHeaders() throws MessagingException {
        return this.headers.getAllHeaders();
    }

    @Override // javax.mail.Part
    public Enumeration<Header> getMatchingHeaders(String[] strArr) throws MessagingException {
        return this.headers.getMatchingHeaders(strArr);
    }

    @Override // javax.mail.Part
    public Enumeration<Header> getNonMatchingHeaders(String[] strArr) throws MessagingException {
        return this.headers.getNonMatchingHeaders(strArr);
    }

    public void addHeaderLine(String str) throws MessagingException {
        this.headers.addHeaderLine(str);
    }

    public Enumeration<String> getAllHeaderLines() throws MessagingException {
        return this.headers.getAllHeaderLines();
    }

    public Enumeration<String> getMatchingHeaderLines(String[] strArr) throws MessagingException {
        return this.headers.getMatchingHeaderLines(strArr);
    }

    public Enumeration<String> getNonMatchingHeaderLines(String[] strArr) throws MessagingException {
        return this.headers.getNonMatchingHeaderLines(strArr);
    }

    @Override // javax.mail.Message
    public synchronized Flags getFlags() throws MessagingException {
        return (Flags) this.flags.clone();
    }

    @Override // javax.mail.Message
    public synchronized boolean isSet(Flags.Flag flag) throws MessagingException {
        return this.flags.contains(flag);
    }

    @Override // javax.mail.Message
    public synchronized void setFlags(Flags flags, boolean z) throws MessagingException {
        if (z) {
            this.flags.add(flags);
        } else {
            this.flags.remove(flags);
        }
    }

    @Override // javax.mail.Message
    public void saveChanges() throws MessagingException {
        this.modified = true;
        this.saved = true;
        updateHeaders();
    }

    protected void updateMessageID() throws MessagingException {
        setHeader("Message-ID", "<" + UniqueValue.getUniqueMessageIDValue(this.session) + ">");
    }

    protected synchronized void updateHeaders() throws MessagingException {
        MimeBodyPart.updateHeaders(this);
        setHeader("MIME-Version", "1.0");
        if (getHeader("Date") == null) {
            setSentDate(new Date());
        }
        updateMessageID();
        if (this.cachedContent != null) {
            this.dh = new DataHandler(this.cachedContent, getContentType());
            this.cachedContent = null;
            this.content = null;
            InputStream inputStream = this.contentStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException unused) {
                }
            }
            this.contentStream = null;
        }
    }

    protected InternetHeaders createInternetHeaders(InputStream inputStream) throws MessagingException {
        return new InternetHeaders(inputStream, this.allowutf8);
    }

    protected MimeMessage createMimeMessage(Session session) throws MessagingException {
        return new MimeMessage(session);
    }
}
