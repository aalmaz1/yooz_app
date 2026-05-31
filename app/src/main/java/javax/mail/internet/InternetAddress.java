package javax.mail.internet;

import com.sun.mail.util.PropUtil;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import javax.mail.Address;
import javax.mail.Session;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes3.dex */
public class InternetAddress extends Address implements Cloneable {
    private static final long serialVersionUID = -7507595530758302903L;
    private static final String specialsNoDot = "()<>,;:\\\"[]@";
    private static final String specialsNoDotNoAt = "()<>,;:\\\"[]";
    protected String address;
    protected String encodedPersonal;
    protected String personal;
    private static final boolean ignoreBogusGroupName = PropUtil.getBooleanSystemProperty("mail.mime.address.ignorebogusgroupname", true);
    private static final boolean useCanonicalHostName = PropUtil.getBooleanSystemProperty("mail.mime.address.usecanonicalhostname", true);
    private static final boolean allowUtf8 = PropUtil.getBooleanSystemProperty("mail.mime.allowutf8", false);
    private static final String rfc822phrase = HeaderTokenizer.RFC822.replace(' ', (char) 0).replace('\t', (char) 0);

    @Override // javax.mail.Address
    public String getType() {
        return "rfc822";
    }

    public InternetAddress() {
    }

    public InternetAddress(String str) throws AddressException {
        InternetAddress[] internetAddressArr = parse(str, true);
        if (internetAddressArr.length != 1) {
            throw new AddressException("Illegal address", str);
        }
        InternetAddress internetAddress = internetAddressArr[0];
        this.address = internetAddress.address;
        this.personal = internetAddress.personal;
        this.encodedPersonal = internetAddress.encodedPersonal;
    }

    public InternetAddress(String str, boolean z) throws AddressException {
        this(str);
        if (z) {
            if (isGroup()) {
                getGroup(true);
            } else {
                checkAddress(this.address, true, true);
            }
        }
    }

    public InternetAddress(String str, String str2) throws UnsupportedEncodingException {
        this(str, str2, null);
    }

    public InternetAddress(String str, String str2, String str3) throws UnsupportedEncodingException {
        this.address = str;
        setPersonal(str2, str3);
    }

    public Object clone() {
        try {
            return (InternetAddress) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public void setPersonal(String str, String str2) throws UnsupportedEncodingException {
        this.personal = str;
        if (str != null) {
            this.encodedPersonal = MimeUtility.encodeWord(str, str2, null);
        } else {
            this.encodedPersonal = null;
        }
    }

    public void setPersonal(String str) throws UnsupportedEncodingException {
        this.personal = str;
        if (str != null) {
            this.encodedPersonal = MimeUtility.encodeWord(str);
        } else {
            this.encodedPersonal = null;
        }
    }

    public String getAddress() {
        return this.address;
    }

    public String getPersonal() {
        String str = this.personal;
        if (str != null) {
            return str;
        }
        String str2 = this.encodedPersonal;
        if (str2 == null) {
            return null;
        }
        try {
            String strDecodeText = MimeUtility.decodeText(str2);
            this.personal = strDecodeText;
            return strDecodeText;
        } catch (Exception unused) {
            return this.encodedPersonal;
        }
    }

    @Override // javax.mail.Address
    public String toString() {
        String str;
        String str2 = this.address;
        if (str2 == null) {
            str2 = "";
        }
        if (this.encodedPersonal == null && (str = this.personal) != null) {
            try {
                this.encodedPersonal = MimeUtility.encodeWord(str);
            } catch (UnsupportedEncodingException unused) {
            }
        }
        if (this.encodedPersonal != null) {
            return quotePhrase(this.encodedPersonal) + " <" + str2 + ">";
        }
        return (isGroup() || isSimple()) ? str2 : "<" + str2 + ">";
    }

    public String toUnicodeString() {
        String personal = getPersonal();
        if (personal != null) {
            return quotePhrase(personal) + " <" + this.address + ">";
        }
        if (isGroup() || isSimple()) {
            return this.address;
        }
        return "<" + this.address + ">";
    }

    private static String quotePhrase(String str) {
        int length = str.length();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"' || cCharAt == '\\') {
                StringBuilder sb = new StringBuilder(length + 3);
                sb.append(Typography.quote);
                for (int i2 = 0; i2 < length; i2++) {
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 == '\"' || cCharAt2 == '\\') {
                        sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                    }
                    sb.append(cCharAt2);
                }
                sb.append(Typography.quote);
                return sb.toString();
            }
            if ((cCharAt < ' ' && cCharAt != '\r' && cCharAt != '\n' && cCharAt != '\t') || ((cCharAt >= 127 && !allowUtf8) || rfc822phrase.indexOf(cCharAt) >= 0)) {
                z = true;
            }
        }
        if (!z) {
            return str;
        }
        StringBuilder sb2 = new StringBuilder(length + 2);
        sb2.append(Typography.quote).append(str).append(Typography.quote);
        return sb2.toString();
    }

    private static String unquote(String str) {
        if (!str.startsWith("\"") || !str.endsWith("\"") || str.length() <= 1) {
            return str;
        }
        String strSubstring = str.substring(1, str.length() - 1);
        if (strSubstring.indexOf(92) < 0) {
            return strSubstring;
        }
        StringBuilder sb = new StringBuilder(strSubstring.length());
        int i = 0;
        while (i < strSubstring.length()) {
            char cCharAt = strSubstring.charAt(i);
            if (cCharAt == '\\' && i < strSubstring.length() - 1) {
                i++;
                cCharAt = strSubstring.charAt(i);
            }
            sb.append(cCharAt);
            i++;
        }
        return sb.toString();
    }

    @Override // javax.mail.Address
    public boolean equals(Object obj) {
        if (!(obj instanceof InternetAddress)) {
            return false;
        }
        String address = ((InternetAddress) obj).getAddress();
        String str = this.address;
        if (address == str) {
            return true;
        }
        return str != null && str.equalsIgnoreCase(address);
    }

    public int hashCode() {
        String str = this.address;
        if (str == null) {
            return 0;
        }
        return str.toLowerCase(Locale.ENGLISH).hashCode();
    }

    public static String toString(Address[] addressArr) {
        return toString(addressArr, 0);
    }

    public static String toUnicodeString(Address[] addressArr) {
        return toUnicodeString(addressArr, 0);
    }

    public static String toString(Address[] addressArr, int i) {
        if (addressArr == null || addressArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < addressArr.length; i2++) {
            if (i2 != 0) {
                sb.append(", ");
                i += 2;
            }
            String strFold = MimeUtility.fold(0, addressArr[i2].toString());
            if (lengthOfFirstSegment(strFold) + i > 76) {
                int length = sb.length();
                if (length > 0) {
                    int i3 = length - 1;
                    if (sb.charAt(i3) == ' ') {
                        sb.setLength(i3);
                    }
                }
                sb.append("\r\n\t");
                i = 8;
            }
            sb.append(strFold);
            i = lengthOfLastSegment(strFold, i);
        }
        return sb.toString();
    }

    public static String toUnicodeString(Address[] addressArr, int i) {
        if (addressArr == null || addressArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (int i2 = 0; i2 < addressArr.length; i2++) {
            if (i2 != 0) {
                sb.append(", ");
                i += 2;
            }
            String unicodeString = ((InternetAddress) addressArr[i2]).toUnicodeString();
            if (MimeUtility.checkAscii(unicodeString) != 1) {
                unicodeString = new String(unicodeString.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                z = true;
            }
            String strFold = MimeUtility.fold(0, unicodeString);
            if (lengthOfFirstSegment(strFold) + i > 76) {
                int length = sb.length();
                if (length > 0) {
                    int i3 = length - 1;
                    if (sb.charAt(i3) == ' ') {
                        sb.setLength(i3);
                    }
                }
                sb.append("\r\n\t");
                i = 8;
            }
            sb.append(strFold);
            i = lengthOfLastSegment(strFold, i);
        }
        String string = sb.toString();
        return z ? new String(string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : string;
    }

    private static int lengthOfFirstSegment(String str) {
        int iIndexOf = str.indexOf(IOUtils.LINE_SEPARATOR_WINDOWS);
        return iIndexOf != -1 ? iIndexOf : str.length();
    }

    private static int lengthOfLastSegment(String str, int i) {
        if (str.lastIndexOf(IOUtils.LINE_SEPARATOR_WINDOWS) != -1) {
            return (str.length() - r0) - 2;
        }
        return str.length() + i;
    }

    public static InternetAddress getLocalAddress(Session session) {
        try {
            return _getLocalAddress(session);
        } catch (SecurityException | UnknownHostException | AddressException unused) {
            return null;
        }
    }

    static InternetAddress _getLocalAddress(Session session) throws AddressException, SecurityException, UnknownHostException {
        String property;
        String property2;
        String localHostName;
        if (session == null) {
            property2 = System.getProperty("user.name");
            localHostName = getLocalHostName();
            property = null;
        } else {
            property = session.getProperty("mail.from");
            if (property == null) {
                String property3 = session.getProperty("mail.user");
                if (property3 == null || property3.length() == 0) {
                    property3 = session.getProperty("user.name");
                }
                String property4 = (property3 == null || property3.length() == 0) ? System.getProperty("user.name") : property3;
                String property5 = session.getProperty("mail.host");
                if (property5 == null || property5.length() == 0) {
                    property5 = getLocalHostName();
                }
                String str = property4;
                localHostName = property5;
                property2 = str;
            } else {
                property2 = null;
                localHostName = null;
            }
        }
        if (property == null && property2 != null && property2.length() != 0 && localHostName != null && localHostName.length() != 0) {
            property = MimeUtility.quote(property2.trim(), "()<>,;:\\\"[]@\t ") + "@" + localHostName;
        }
        if (property == null) {
            return null;
        }
        return new InternetAddress(property);
    }

    private static String getLocalHostName() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        if (localHost == null) {
            return null;
        }
        String canonicalHostName = useCanonicalHostName ? localHost.getCanonicalHostName() : null;
        if (canonicalHostName == null) {
            canonicalHostName = localHost.getHostName();
        }
        if (canonicalHostName == null) {
            canonicalHostName = localHost.getHostAddress();
        }
        return (canonicalHostName == null || canonicalHostName.length() <= 0 || !isInetAddressLiteral(canonicalHostName)) ? canonicalHostName : "[" + canonicalHostName + ']';
    }

    private static boolean isInetAddressLiteral(String str) {
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if ((cCharAt < '0' || cCharAt > '9') && cCharAt != '.') {
                if ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z')) {
                    z = true;
                } else {
                    if (cCharAt != ':') {
                        return false;
                    }
                    z2 = true;
                }
            }
        }
        return !z || z2;
    }

    public static InternetAddress[] parse(String str) throws AddressException {
        return parse(str, true);
    }

    public static InternetAddress[] parse(String str, boolean z) throws AddressException {
        return parse(str, z, false);
    }

    public static InternetAddress[] parseHeader(String str, boolean z) throws AddressException {
        return parse(MimeUtility.unfold(str), z, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:141:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0261 A[PHI: r2 r8 r9 r13
      0x0261: PHI (r2v38 int) = (r2v34 int), (r2v42 int) binds: [B:203:0x0253, B:189:0x0231] A[DONT_GENERATE, DONT_INLINE]
      0x0261: PHI (r8v18 int) = (r8v16 int), (r8v1 int) binds: [B:203:0x0253, B:189:0x0231] A[DONT_GENERATE, DONT_INLINE]
      0x0261: PHI (r9v12 int) = (r9v1 int), (r9v13 int) binds: [B:203:0x0253, B:189:0x0231] A[DONT_GENERATE, DONT_INLINE]
      0x0261: PHI (r13v7 int) = (r13v1 int), (r13v8 int) binds: [B:203:0x0253, B:189:0x0231] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0333  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x0364  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x00c3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0102  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static javax.mail.internet.InternetAddress[] parse(java.lang.String r19, boolean r20, boolean r21) throws javax.mail.internet.AddressException {
        /*
            Method dump skipped, instruction units count: 982
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.internet.InternetAddress.parse(java.lang.String, boolean, boolean):javax.mail.internet.InternetAddress[]");
    }

    public void validate() throws AddressException {
        if (isGroup()) {
            getGroup(true);
        } else {
            checkAddress(getAddress(), true, true);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0121, code lost:
    
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0122, code lost:
    
        if (r8 >= r1) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0128, code lost:
    
        if (r16.charAt(r8) == '.') goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x012a, code lost:
    
        r5 = false;
        r2 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x012c, code lost:
    
        if (r2 >= r1) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x012e, code lost:
    
        r6 = r16.charAt(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0134, code lost:
    
        if (r6 != '[') goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x0136, code lost:
    
        if (r2 != r8) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0138, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0141, code lost:
    
        throw new javax.mail.internet.AddressException("Domain literal not at start of domain", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0144, code lost:
    
        if (r6 != ']') goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0148, code lost:
    
        if (r2 != (r1 - 1)) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x014a, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0153, code lost:
    
        throw new javax.mail.internet.AddressException("Domain literal end not at end of domain", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0154, code lost:
    
        if (r6 <= ' ') goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0156, code lost:
    
        if (r6 == 127) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0158, code lost:
    
        if (r5 != false) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x015e, code lost:
    
        if (java.lang.Character.isLetterOrDigit(r6) != false) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0162, code lost:
    
        if (r6 == '-') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0164, code lost:
    
        if (r6 != '.') goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x016e, code lost:
    
        throw new javax.mail.internet.AddressException("Domain contains illegal character", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x016f, code lost:
    
        if (r6 != '.') goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0171, code lost:
    
        if (r7 == '.') goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x017b, code lost:
    
        throw new javax.mail.internet.AddressException("Domain contains dot-dot", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x017c, code lost:
    
        r2 = r2 + 1;
        r7 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0187, code lost:
    
        throw new javax.mail.internet.AddressException("Domain contains control or whitespace", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0188, code lost:
    
        if (r7 == '.') goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x018a, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0192, code lost:
    
        throw new javax.mail.internet.AddressException("Domain ends with dot", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x019a, code lost:
    
        throw new javax.mail.internet.AddressException("Domain starts with dot", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x01a2, code lost:
    
        throw new javax.mail.internet.AddressException("Missing domain", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x01aa, code lost:
    
        throw new javax.mail.internet.AddressException("Unterminated quote", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x010a, code lost:
    
        throw new javax.mail.internet.AddressException("Local address contains control or whitespace", r16);
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0112, code lost:
    
        if (r9 != false) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0114, code lost:
    
        if (r6 == '@') goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0116, code lost:
    
        if (r18 != false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0118, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0120, code lost:
    
        throw new javax.mail.internet.AddressException("Missing final '@domain'", r16);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void checkAddress(java.lang.String r16, boolean r17, boolean r18) throws javax.mail.internet.AddressException {
        /*
            Method dump skipped, instruction units count: 443
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.internet.InternetAddress.checkAddress(java.lang.String, boolean, boolean):void");
    }

    private boolean isSimple() {
        String str = this.address;
        return str == null || indexOfAny(str, specialsNoDotNoAt) < 0;
    }

    public boolean isGroup() {
        String str = this.address;
        return str != null && str.endsWith(";") && this.address.indexOf(58) > 0;
    }

    public InternetAddress[] getGroup(boolean z) throws AddressException {
        int iIndexOf;
        String address = getAddress();
        if (address != null && address.endsWith(";") && (iIndexOf = address.indexOf(58)) >= 0) {
            return parseHeader(address.substring(iIndexOf + 1, address.length() - 1), z);
        }
        return null;
    }

    private static int indexOfAny(String str, String str2) {
        return indexOfAny(str, str2, 0);
    }

    private static int indexOfAny(String str, String str2, int i) {
        try {
            int length = str.length();
            while (i < length) {
                if (str2.indexOf(str.charAt(i)) >= 0) {
                    return i;
                }
                i++;
            }
        } catch (StringIndexOutOfBoundsException unused) {
        }
        return -1;
    }
}
