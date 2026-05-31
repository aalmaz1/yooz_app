package javax.mail.internet;

import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.LineOutputStream;
import com.sun.mail.util.PropUtil;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.MessageAware;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.MultipartDataSource;

/* JADX INFO: loaded from: classes3.dex */
public class MimeMultipart extends Multipart {
    protected boolean allowEmpty;
    protected boolean complete;
    protected DataSource ds;
    protected boolean ignoreExistingBoundaryParameter;
    protected boolean ignoreMissingBoundaryParameter;
    protected boolean ignoreMissingEndBoundary;
    protected boolean parsed;
    protected String preamble;

    public MimeMultipart() {
        this("mixed");
    }

    public MimeMultipart(String str) {
        this.ds = null;
        this.parsed = true;
        this.complete = true;
        this.preamble = null;
        this.ignoreMissingEndBoundary = true;
        this.ignoreMissingBoundaryParameter = true;
        this.ignoreExistingBoundaryParameter = false;
        this.allowEmpty = false;
        String uniqueBoundaryValue = UniqueValue.getUniqueBoundaryValue();
        ContentType contentType = new ContentType("multipart", str, null);
        contentType.setParameter("boundary", uniqueBoundaryValue);
        this.contentType = contentType.toString();
        initializeProperties();
    }

    public MimeMultipart(BodyPart... bodyPartArr) throws MessagingException {
        this();
        for (BodyPart bodyPart : bodyPartArr) {
            super.addBodyPart(bodyPart);
        }
    }

    public MimeMultipart(String str, BodyPart... bodyPartArr) throws MessagingException {
        this(str);
        for (BodyPart bodyPart : bodyPartArr) {
            super.addBodyPart(bodyPart);
        }
    }

    public MimeMultipart(DataSource dataSource) throws MessagingException {
        this.ds = null;
        this.parsed = true;
        this.complete = true;
        this.preamble = null;
        this.ignoreMissingEndBoundary = true;
        this.ignoreMissingBoundaryParameter = true;
        this.ignoreExistingBoundaryParameter = false;
        this.allowEmpty = false;
        if (dataSource instanceof MessageAware) {
            setParent(((MessageAware) dataSource).getMessageContext().getPart());
        }
        if (dataSource instanceof MultipartDataSource) {
            setMultipartDataSource((MultipartDataSource) dataSource);
            return;
        }
        this.parsed = false;
        this.ds = dataSource;
        this.contentType = dataSource.getContentType();
    }

    protected void initializeProperties() {
        this.ignoreMissingEndBoundary = PropUtil.getBooleanSystemProperty("mail.mime.multipart.ignoremissingendboundary", true);
        this.ignoreMissingBoundaryParameter = PropUtil.getBooleanSystemProperty("mail.mime.multipart.ignoremissingboundaryparameter", true);
        this.ignoreExistingBoundaryParameter = PropUtil.getBooleanSystemProperty("mail.mime.multipart.ignoreexistingboundaryparameter", false);
        this.allowEmpty = PropUtil.getBooleanSystemProperty("mail.mime.multipart.allowempty", false);
    }

    public synchronized void setSubType(String str) throws MessagingException {
        ContentType contentType = new ContentType(this.contentType);
        contentType.setSubType(str);
        this.contentType = contentType.toString();
    }

    @Override // javax.mail.Multipart
    public synchronized int getCount() throws MessagingException {
        parse();
        return super.getCount();
    }

    @Override // javax.mail.Multipart
    public synchronized BodyPart getBodyPart(int i) throws MessagingException {
        parse();
        return super.getBodyPart(i);
    }

    public synchronized BodyPart getBodyPart(String str) throws MessagingException {
        parse();
        int count = getCount();
        for (int i = 0; i < count; i++) {
            MimeBodyPart mimeBodyPart = (MimeBodyPart) getBodyPart(i);
            String contentID = mimeBodyPart.getContentID();
            if (contentID != null && contentID.equals(str)) {
                return mimeBodyPart;
            }
        }
        return null;
    }

    @Override // javax.mail.Multipart
    public boolean removeBodyPart(BodyPart bodyPart) throws MessagingException {
        parse();
        return super.removeBodyPart(bodyPart);
    }

    @Override // javax.mail.Multipart
    public void removeBodyPart(int i) throws MessagingException {
        parse();
        super.removeBodyPart(i);
    }

    @Override // javax.mail.Multipart
    public synchronized void addBodyPart(BodyPart bodyPart) throws MessagingException {
        parse();
        super.addBodyPart(bodyPart);
    }

    @Override // javax.mail.Multipart
    public synchronized void addBodyPart(BodyPart bodyPart, int i) throws MessagingException {
        parse();
        super.addBodyPart(bodyPart, i);
    }

    public synchronized boolean isComplete() throws MessagingException {
        parse();
        return this.complete;
    }

    public synchronized String getPreamble() throws MessagingException {
        parse();
        return this.preamble;
    }

    public synchronized void setPreamble(String str) throws MessagingException {
        this.preamble = str;
    }

    protected synchronized void updateHeaders() throws MessagingException {
        parse();
        for (int i = 0; i < this.parts.size(); i++) {
            ((MimeBodyPart) this.parts.elementAt(i)).updateHeaders();
        }
    }

    @Override // javax.mail.Multipart
    public synchronized void writeTo(OutputStream outputStream) throws MessagingException, IOException {
        parse();
        String str = "--" + new ContentType(this.contentType).getParameter("boundary");
        LineOutputStream lineOutputStream = new LineOutputStream(outputStream);
        String str2 = this.preamble;
        if (str2 != null) {
            byte[] bytes = ASCIIUtility.getBytes(str2);
            lineOutputStream.write(bytes);
            if (bytes.length > 0 && bytes[bytes.length - 1] != 13 && bytes[bytes.length - 1] != 10) {
                lineOutputStream.writeln();
            }
        }
        if (this.parts.size() == 0) {
            if (this.allowEmpty) {
                lineOutputStream.writeln(str);
                lineOutputStream.writeln();
            } else {
                throw new MessagingException("Empty multipart: " + this.contentType);
            }
        } else {
            for (int i = 0; i < this.parts.size(); i++) {
                lineOutputStream.writeln(str);
                ((MimeBodyPart) this.parts.elementAt(i)).writeTo(outputStream);
                lineOutputStream.writeln();
            }
        }
        lineOutputStream.writeln(str + "--");
    }

    /* JADX WARN: Code restructure failed: missing block: B:173:0x025f, code lost:
    
        r26 = r6;
        r5 = r17;
        r8 = r25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0342, code lost:
    
        r2.close();
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002b A[Catch: all -> 0x0362, TryCatch #0 {, blocks: (B:4:0x0005, B:8:0x000b, B:9:0x000e, B:11:0x0018, B:13:0x001c, B:15:0x0020, B:17:0x0026, B:19:0x002b, B:21:0x0030, B:23:0x003b, B:25:0x0043, B:28:0x0054, B:30:0x0058, B:33:0x005d, B:34:0x0064, B:77:0x0100, B:209:0x0342, B:211:0x0346, B:219:0x0355, B:220:0x0358, B:222:0x035a, B:223:0x0361, B:35:0x0065, B:36:0x006b, B:38:0x0078, B:40:0x007f, B:44:0x0088, B:45:0x008b, B:47:0x0093, B:50:0x009a, B:52:0x00a5, B:54:0x00ab, B:67:0x00d3, B:70:0x00db, B:71:0x00e5, B:57:0x00b5, B:59:0x00bb, B:61:0x00c3, B:63:0x00ca, B:73:0x00f4, B:75:0x00fc, B:80:0x0105, B:81:0x010c, B:82:0x010d, B:84:0x0119, B:85:0x0122, B:87:0x0127, B:89:0x012b, B:91:0x0133, B:94:0x0143, B:93:0x013e, B:95:0x0148, B:98:0x0155, B:99:0x0159, B:101:0x015f, B:105:0x0168, B:107:0x016c, B:108:0x0170, B:109:0x0177, B:112:0x0181, B:115:0x0189, B:117:0x0197, B:118:0x019e, B:120:0x01ae, B:123:0x01b4, B:124:0x01b8, B:175:0x0267, B:185:0x0292, B:176:0x0270, B:178:0x0273, B:180:0x027c, B:183:0x0282, B:184:0x0288, B:125:0x01c7, B:126:0x01ce, B:129:0x01d3, B:137:0x01f1, B:143:0x01fe, B:190:0x02d1, B:195:0x02e8, B:196:0x02ee, B:198:0x02fa, B:199:0x0308, B:203:0x0315, B:205:0x031b, B:153:0x0219, B:155:0x0228, B:157:0x0230, B:159:0x0236, B:170:0x0252, B:172:0x025c, B:188:0x02b0, B:132:0x01de, B:116:0x0191, B:207:0x033a, B:208:0x0341, B:111:0x017b, B:217:0x034d, B:218:0x0354), top: B:230:0x0005, inners: #4, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x00d9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:242:0x00f2 A[EDGE_INSN: B:242:0x00f2->B:72:0x00f2 BREAK  A[LOOP:0: B:36:0x006b->B:246:0x006b], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:247:0x006b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0078 A[Catch: all -> 0x034a, IOException -> 0x034c, TryCatch #6 {IOException -> 0x034c, blocks: (B:35:0x0065, B:36:0x006b, B:38:0x0078, B:40:0x007f, B:44:0x0088, B:45:0x008b, B:47:0x0093, B:50:0x009a, B:52:0x00a5, B:54:0x00ab, B:67:0x00d3, B:70:0x00db, B:71:0x00e5, B:57:0x00b5, B:59:0x00bb, B:61:0x00c3, B:63:0x00ca, B:73:0x00f4, B:75:0x00fc, B:80:0x0105, B:81:0x010c, B:82:0x010d, B:84:0x0119, B:85:0x0122, B:87:0x0127, B:89:0x012b, B:91:0x0133, B:94:0x0143, B:93:0x013e, B:95:0x0148, B:98:0x0155, B:99:0x0159, B:101:0x015f, B:105:0x0168, B:107:0x016c, B:108:0x0170, B:109:0x0177, B:112:0x0181, B:115:0x0189, B:117:0x0197, B:118:0x019e, B:120:0x01ae, B:123:0x01b4, B:124:0x01b8, B:175:0x0267, B:185:0x0292, B:176:0x0270, B:178:0x0273, B:180:0x027c, B:183:0x0282, B:184:0x0288, B:125:0x01c7, B:126:0x01ce, B:129:0x01d3, B:137:0x01f1, B:143:0x01fe, B:190:0x02d1, B:195:0x02e8, B:196:0x02ee, B:198:0x02fa, B:199:0x0308, B:203:0x0315, B:205:0x031b, B:153:0x0219, B:155:0x0228, B:157:0x0230, B:159:0x0236, B:170:0x0252, B:172:0x025c, B:188:0x02b0, B:132:0x01de, B:116:0x0191, B:207:0x033a, B:208:0x0341, B:111:0x017b), top: B:239:0x0065, outer: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00f4 A[Catch: all -> 0x034a, IOException -> 0x034c, TryCatch #6 {IOException -> 0x034c, blocks: (B:35:0x0065, B:36:0x006b, B:38:0x0078, B:40:0x007f, B:44:0x0088, B:45:0x008b, B:47:0x0093, B:50:0x009a, B:52:0x00a5, B:54:0x00ab, B:67:0x00d3, B:70:0x00db, B:71:0x00e5, B:57:0x00b5, B:59:0x00bb, B:61:0x00c3, B:63:0x00ca, B:73:0x00f4, B:75:0x00fc, B:80:0x0105, B:81:0x010c, B:82:0x010d, B:84:0x0119, B:85:0x0122, B:87:0x0127, B:89:0x012b, B:91:0x0133, B:94:0x0143, B:93:0x013e, B:95:0x0148, B:98:0x0155, B:99:0x0159, B:101:0x015f, B:105:0x0168, B:107:0x016c, B:108:0x0170, B:109:0x0177, B:112:0x0181, B:115:0x0189, B:117:0x0197, B:118:0x019e, B:120:0x01ae, B:123:0x01b4, B:124:0x01b8, B:175:0x0267, B:185:0x0292, B:176:0x0270, B:178:0x0273, B:180:0x027c, B:183:0x0282, B:184:0x0288, B:125:0x01c7, B:126:0x01ce, B:129:0x01d3, B:137:0x01f1, B:143:0x01fe, B:190:0x02d1, B:195:0x02e8, B:196:0x02ee, B:198:0x02fa, B:199:0x0308, B:203:0x0315, B:205:0x031b, B:153:0x0219, B:155:0x0228, B:157:0x0230, B:159:0x0236, B:170:0x0252, B:172:0x025c, B:188:0x02b0, B:132:0x01de, B:116:0x0191, B:207:0x033a, B:208:0x0341, B:111:0x017b), top: B:239:0x0065, outer: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00fc A[Catch: all -> 0x034a, IOException -> 0x034c, TRY_LEAVE, TryCatch #6 {IOException -> 0x034c, blocks: (B:35:0x0065, B:36:0x006b, B:38:0x0078, B:40:0x007f, B:44:0x0088, B:45:0x008b, B:47:0x0093, B:50:0x009a, B:52:0x00a5, B:54:0x00ab, B:67:0x00d3, B:70:0x00db, B:71:0x00e5, B:57:0x00b5, B:59:0x00bb, B:61:0x00c3, B:63:0x00ca, B:73:0x00f4, B:75:0x00fc, B:80:0x0105, B:81:0x010c, B:82:0x010d, B:84:0x0119, B:85:0x0122, B:87:0x0127, B:89:0x012b, B:91:0x0133, B:94:0x0143, B:93:0x013e, B:95:0x0148, B:98:0x0155, B:99:0x0159, B:101:0x015f, B:105:0x0168, B:107:0x016c, B:108:0x0170, B:109:0x0177, B:112:0x0181, B:115:0x0189, B:117:0x0197, B:118:0x019e, B:120:0x01ae, B:123:0x01b4, B:124:0x01b8, B:175:0x0267, B:185:0x0292, B:176:0x0270, B:178:0x0273, B:180:0x027c, B:183:0x0282, B:184:0x0288, B:125:0x01c7, B:126:0x01ce, B:129:0x01d3, B:137:0x01f1, B:143:0x01fe, B:190:0x02d1, B:195:0x02e8, B:196:0x02ee, B:198:0x02fa, B:199:0x0308, B:203:0x0315, B:205:0x031b, B:153:0x0219, B:155:0x0228, B:157:0x0230, B:159:0x0236, B:170:0x0252, B:172:0x025c, B:188:0x02b0, B:132:0x01de, B:116:0x0191, B:207:0x033a, B:208:0x0341, B:111:0x017b), top: B:239:0x0065, outer: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x010d A[Catch: all -> 0x034a, IOException -> 0x034c, TryCatch #6 {IOException -> 0x034c, blocks: (B:35:0x0065, B:36:0x006b, B:38:0x0078, B:40:0x007f, B:44:0x0088, B:45:0x008b, B:47:0x0093, B:50:0x009a, B:52:0x00a5, B:54:0x00ab, B:67:0x00d3, B:70:0x00db, B:71:0x00e5, B:57:0x00b5, B:59:0x00bb, B:61:0x00c3, B:63:0x00ca, B:73:0x00f4, B:75:0x00fc, B:80:0x0105, B:81:0x010c, B:82:0x010d, B:84:0x0119, B:85:0x0122, B:87:0x0127, B:89:0x012b, B:91:0x0133, B:94:0x0143, B:93:0x013e, B:95:0x0148, B:98:0x0155, B:99:0x0159, B:101:0x015f, B:105:0x0168, B:107:0x016c, B:108:0x0170, B:109:0x0177, B:112:0x0181, B:115:0x0189, B:117:0x0197, B:118:0x019e, B:120:0x01ae, B:123:0x01b4, B:124:0x01b8, B:175:0x0267, B:185:0x0292, B:176:0x0270, B:178:0x0273, B:180:0x027c, B:183:0x0282, B:184:0x0288, B:125:0x01c7, B:126:0x01ce, B:129:0x01d3, B:137:0x01f1, B:143:0x01fe, B:190:0x02d1, B:195:0x02e8, B:196:0x02ee, B:198:0x02fa, B:199:0x0308, B:203:0x0315, B:205:0x031b, B:153:0x0219, B:155:0x0228, B:157:0x0230, B:159:0x0236, B:170:0x0252, B:172:0x025c, B:188:0x02b0, B:132:0x01de, B:116:0x0191, B:207:0x033a, B:208:0x0341, B:111:0x017b), top: B:239:0x0065, outer: #5 }] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v2, types: [int] */
    /* JADX WARN: Type inference failed for: r11v13 */
    /* JADX WARN: Type inference failed for: r11v15 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected synchronized void parse() throws javax.mail.MessagingException {
        /*
            Method dump skipped, instruction units count: 869
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.internet.MimeMultipart.parse():void");
    }

    private static boolean allDashes(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '-') {
                return false;
            }
        }
        return true;
    }

    private static int readFully(InputStream inputStream, byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        if (i2 == 0) {
            return 0;
        }
        while (i2 > 0) {
            int i4 = inputStream.read(bArr, i, i2);
            if (i4 <= 0) {
                break;
            }
            i += i4;
            i3 += i4;
            i2 -= i4;
        }
        if (i3 > 0) {
            return i3;
        }
        return -1;
    }

    private void skipFully(InputStream inputStream, long j) throws IOException {
        while (j > 0) {
            long jSkip = inputStream.skip(j);
            if (jSkip <= 0) {
                throw new EOFException("can't skip");
            }
            j -= jSkip;
        }
    }

    protected InternetHeaders createInternetHeaders(InputStream inputStream) throws MessagingException {
        return new InternetHeaders(inputStream);
    }

    protected MimeBodyPart createMimeBodyPart(InternetHeaders internetHeaders, byte[] bArr) throws MessagingException {
        return new MimeBodyPart(internetHeaders, bArr);
    }

    protected MimeBodyPart createMimeBodyPart(InputStream inputStream) throws MessagingException {
        return new MimeBodyPart(inputStream);
    }

    private MimeBodyPart createMimeBodyPartIs(InputStream inputStream) throws MessagingException {
        try {
            return createMimeBodyPart(inputStream);
        } finally {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
