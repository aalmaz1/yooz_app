package com.sun.mail.smtp;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.sun.mail.auth.Ntlm;
import com.sun.mail.util.ASCIIUtility;
import com.sun.mail.util.BASE64EncoderStream;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.MailConnectException;
import com.sun.mail.util.MailLogger;
import com.sun.mail.util.PropUtil;
import com.sun.mail.util.SocketConnectException;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.ParseException;
import javax.net.ssl.SSLSocket;
import kotlin.UByte;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SMTPTransport extends Transport {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String UNKNOWN = "UNKNOWN";
    private Address[] addresses;
    private boolean allowutf8;
    private Map<String, Authenticator> authenticators;
    private String authorizationID;
    private int chunkSize;
    private SMTPOutputStream dataStream;
    private boolean debugpassword;
    private boolean debugusername;
    private String defaultAuthenticationMechanisms;
    private int defaultPort;
    private boolean enableSASL;
    private MessagingException exception;
    private Hashtable<String, String> extMap;
    private String host;
    private Address[] invalidAddr;
    private boolean isSSL;
    private int lastReturnCode;
    private String lastServerResponse;
    private LineInputStream lineInputStream;
    private String localHostName;
    private MailLogger logger;
    private MimeMessage message;
    private String name;
    private boolean noauthdebug;
    private boolean noopStrict;
    private boolean notificationDone;
    private String ntlmDomain;
    private boolean quitWait;
    private boolean reportSuccess;
    private boolean requireStartTLS;
    private SaslAuthenticator saslAuthenticator;
    private String[] saslMechanisms;
    private String saslRealm;
    private boolean sendPartiallyFailed;
    private BufferedInputStream serverInput;
    private OutputStream serverOutput;
    private Socket serverSocket;
    private TraceInputStream traceInput;
    private MailLogger traceLogger;
    private TraceOutputStream traceOutput;
    private boolean useCanonicalHostName;
    private boolean useRset;
    private boolean useStartTLS;
    private Address[] validSentAddr;
    private Address[] validUnsentAddr;
    private static final String[] ignoreList = {"Bcc", "Content-Length"};
    private static final byte[] CRLF = {13, 10};
    private static final String[] UNKNOWN_SA = new String[0];
    private static char[] hexchar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private void sendMessageEnd() {
    }

    private void sendMessageStart(String str) {
    }

    public SMTPTransport(Session session, URLName uRLName) {
        this(session, uRLName, "smtp", false);
    }

    protected SMTPTransport(Session session, URLName uRLName, String str, boolean z) {
        super(session, uRLName);
        this.name = "smtp";
        this.defaultPort = 25;
        this.isSSL = false;
        this.sendPartiallyFailed = false;
        this.authenticators = new HashMap();
        this.quitWait = false;
        this.saslRealm = UNKNOWN;
        this.authorizationID = UNKNOWN;
        this.enableSASL = false;
        this.useCanonicalHostName = false;
        this.saslMechanisms = UNKNOWN_SA;
        this.ntlmDomain = UNKNOWN;
        this.noopStrict = true;
        this.noauthdebug = true;
        Properties properties = session.getProperties();
        MailLogger mailLogger = new MailLogger(getClass(), "DEBUG SMTP", session.getDebug(), session.getDebugOut());
        this.logger = mailLogger;
        this.traceLogger = mailLogger.getSubLogger("protocol", null);
        this.noauthdebug = !PropUtil.getBooleanProperty(properties, "mail.debug.auth", false);
        this.debugusername = PropUtil.getBooleanProperty(properties, "mail.debug.auth.username", true);
        this.debugpassword = PropUtil.getBooleanProperty(properties, "mail.debug.auth.password", false);
        str = uRLName != null ? uRLName.getProtocol() : str;
        this.name = str;
        z = z ? z : PropUtil.getBooleanProperty(properties, "mail." + str + ".ssl.enable", false);
        if (z) {
            this.defaultPort = 465;
        } else {
            this.defaultPort = 25;
        }
        this.isSSL = z;
        this.quitWait = PropUtil.getBooleanProperty(properties, "mail." + str + ".quitwait", true);
        this.reportSuccess = PropUtil.getBooleanProperty(properties, "mail." + str + ".reportsuccess", false);
        this.useStartTLS = PropUtil.getBooleanProperty(properties, "mail." + str + ".starttls.enable", false);
        this.requireStartTLS = PropUtil.getBooleanProperty(properties, "mail." + str + ".starttls.required", false);
        this.useRset = PropUtil.getBooleanProperty(properties, "mail." + str + ".userset", false);
        this.noopStrict = PropUtil.getBooleanProperty(properties, "mail." + str + ".noop.strict", true);
        boolean booleanProperty = PropUtil.getBooleanProperty(properties, "mail." + str + ".sasl.enable", false);
        this.enableSASL = booleanProperty;
        if (booleanProperty) {
            this.logger.config("enable SASL");
        }
        boolean booleanProperty2 = PropUtil.getBooleanProperty(properties, "mail." + str + ".sasl.usecanonicalhostname", false);
        this.useCanonicalHostName = booleanProperty2;
        if (booleanProperty2) {
            this.logger.config("use canonical host name");
        }
        boolean booleanProperty3 = PropUtil.getBooleanProperty(properties, "mail.mime.allowutf8", false);
        this.allowutf8 = booleanProperty3;
        if (booleanProperty3) {
            this.logger.config("allow UTF-8");
        }
        int intProperty = PropUtil.getIntProperty(properties, "mail." + str + ".chunksize", -1);
        this.chunkSize = intProperty;
        if (intProperty > 0 && this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config("chunk size " + this.chunkSize);
        }
        Authenticator[] authenticatorArr = {new LoginAuthenticator(), new PlainAuthenticator(), new DigestMD5Authenticator(), new NtlmAuthenticator(), new OAuth2Authenticator()};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            this.authenticators.put(authenticatorArr[i].getMechanism(), authenticatorArr[i]);
            sb.append(authenticatorArr[i].getMechanism()).append(' ');
        }
        this.defaultAuthenticationMechanisms = sb.toString();
    }

    public synchronized String getLocalHost() {
        Socket socket;
        String str = this.localHostName;
        if (str == null || str.length() <= 0) {
            this.localHostName = this.session.getProperty("mail." + this.name + ".localhost");
        }
        String str2 = this.localHostName;
        if (str2 == null || str2.length() <= 0) {
            this.localHostName = this.session.getProperty("mail." + this.name + ".localaddress");
        }
        try {
            String str3 = this.localHostName;
            if (str3 == null || str3.length() <= 0) {
                InetAddress localHost = InetAddress.getLocalHost();
                String canonicalHostName = localHost.getCanonicalHostName();
                this.localHostName = canonicalHostName;
                if (canonicalHostName == null) {
                    this.localHostName = "[" + localHost.getHostAddress() + "]";
                }
            }
        } catch (UnknownHostException unused) {
        }
        String str4 = this.localHostName;
        if ((str4 == null || str4.length() <= 0) && (socket = this.serverSocket) != null && socket.isBound()) {
            InetAddress localAddress = this.serverSocket.getLocalAddress();
            String canonicalHostName2 = localAddress.getCanonicalHostName();
            this.localHostName = canonicalHostName2;
            if (canonicalHostName2 == null) {
                this.localHostName = "[" + localAddress.getHostAddress() + "]";
            }
        }
        return this.localHostName;
    }

    public synchronized void setLocalHost(String str) {
        this.localHostName = str;
    }

    public synchronized void connect(Socket socket) throws MessagingException {
        this.serverSocket = socket;
        super.connect();
    }

    public synchronized String getAuthorizationId() {
        if (this.authorizationID == UNKNOWN) {
            this.authorizationID = this.session.getProperty("mail." + this.name + ".sasl.authorizationid");
        }
        return this.authorizationID;
    }

    public synchronized void setAuthorizationID(String str) {
        this.authorizationID = str;
    }

    public synchronized boolean getSASLEnabled() {
        return this.enableSASL;
    }

    public synchronized void setSASLEnabled(boolean z) {
        this.enableSASL = z;
    }

    public synchronized String getSASLRealm() {
        if (this.saslRealm == UNKNOWN) {
            String property = this.session.getProperty("mail." + this.name + ".sasl.realm");
            this.saslRealm = property;
            if (property == null) {
                this.saslRealm = this.session.getProperty("mail." + this.name + ".saslrealm");
            }
        }
        return this.saslRealm;
    }

    public synchronized void setSASLRealm(String str) {
        this.saslRealm = str;
    }

    public synchronized boolean getUseCanonicalHostName() {
        return this.useCanonicalHostName;
    }

    public synchronized void setUseCanonicalHostName(boolean z) {
        this.useCanonicalHostName = z;
    }

    public synchronized String[] getSASLMechanisms() {
        if (this.saslMechanisms == UNKNOWN_SA) {
            ArrayList arrayList = new ArrayList(5);
            String property = this.session.getProperty("mail." + this.name + ".sasl.mechanisms");
            if (property != null && property.length() > 0) {
                if (this.logger.isLoggable(Level.FINE)) {
                    this.logger.fine("SASL mechanisms allowed: " + property);
                }
                StringTokenizer stringTokenizer = new StringTokenizer(property, " ,");
                while (stringTokenizer.hasMoreTokens()) {
                    String strNextToken = stringTokenizer.nextToken();
                    if (strNextToken.length() > 0) {
                        arrayList.add(strNextToken);
                    }
                }
            }
            String[] strArr = new String[arrayList.size()];
            this.saslMechanisms = strArr;
            arrayList.toArray(strArr);
        }
        String[] strArr2 = this.saslMechanisms;
        if (strArr2 == null) {
            return null;
        }
        return (String[]) strArr2.clone();
    }

    public synchronized void setSASLMechanisms(String[] strArr) {
        if (strArr != null) {
            strArr = (String[]) strArr.clone();
            this.saslMechanisms = strArr;
        } else {
            this.saslMechanisms = strArr;
        }
    }

    public synchronized String getNTLMDomain() {
        if (this.ntlmDomain == UNKNOWN) {
            this.ntlmDomain = this.session.getProperty("mail." + this.name + ".auth.ntlm.domain");
        }
        return this.ntlmDomain;
    }

    public synchronized void setNTLMDomain(String str) {
        this.ntlmDomain = str;
    }

    public synchronized boolean getReportSuccess() {
        return this.reportSuccess;
    }

    public synchronized void setReportSuccess(boolean z) {
        this.reportSuccess = z;
    }

    public synchronized boolean getStartTLS() {
        return this.useStartTLS;
    }

    public synchronized void setStartTLS(boolean z) {
        this.useStartTLS = z;
    }

    public synchronized boolean getRequireStartTLS() {
        return this.requireStartTLS;
    }

    public synchronized void setRequireStartTLS(boolean z) {
        this.requireStartTLS = z;
    }

    public synchronized boolean isSSL() {
        return this.serverSocket instanceof SSLSocket;
    }

    public synchronized boolean getUseRset() {
        return this.useRset;
    }

    public synchronized void setUseRset(boolean z) {
        this.useRset = z;
    }

    public synchronized boolean getNoopStrict() {
        return this.noopStrict;
    }

    public synchronized void setNoopStrict(boolean z) {
        this.noopStrict = z;
    }

    public synchronized String getLastServerResponse() {
        return this.lastServerResponse;
    }

    public synchronized int getLastReturnCode() {
        return this.lastReturnCode;
    }

    @Override // javax.mail.Service
    protected synchronized boolean protocolConnect(String str, int i, String str2, String str3) throws MessagingException {
        Properties properties = this.session.getProperties();
        boolean booleanProperty = PropUtil.getBooleanProperty(properties, "mail." + this.name + ".auth", false);
        if (booleanProperty && (str2 == null || str3 == null)) {
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("need username and password for authentication");
                this.logger.fine("protocolConnect returning false, host=" + str + ", user=" + traceUser(str2) + ", password=" + tracePassword(str3));
            }
            return false;
        }
        boolean booleanProperty2 = PropUtil.getBooleanProperty(properties, "mail." + this.name + ".ehlo", true);
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("useEhlo " + booleanProperty2 + ", useAuth " + booleanProperty);
        }
        if (i == -1) {
            i = PropUtil.getIntProperty(properties, "mail." + this.name + ".port", -1);
        }
        if (i == -1) {
            i = this.defaultPort;
        }
        if (str == null || str.length() == 0) {
            str = "localhost";
        }
        try {
            if (this.serverSocket != null) {
                openServer();
            } else {
                openServer(str, i);
            }
            if (!(booleanProperty2 ? ehlo(getLocalHost()) : false)) {
                helo(getLocalHost());
            }
            if (this.useStartTLS || this.requireStartTLS) {
                if (this.serverSocket instanceof SSLSocket) {
                    this.logger.fine("STARTTLS requested but already using SSL");
                } else if (supportsExtension("STARTTLS")) {
                    startTLS();
                    ehlo(getLocalHost());
                } else if (this.requireStartTLS) {
                    this.logger.fine("STARTTLS required but not supported");
                    throw new MessagingException("STARTTLS is required but host does not support STARTTLS");
                }
            }
            if (this.allowutf8 && !supportsExtension("SMTPUTF8")) {
                this.logger.log(Level.INFO, "mail.mime.allowutf8 set but server doesn't advertise SMTPUTF8 support");
            }
            if ((!booleanProperty && (str2 == null || str3 == null)) || (!supportsExtension("AUTH") && !supportsExtension("AUTH=LOGIN"))) {
                return true;
            }
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("protocolConnect login, host=" + str + ", user=" + traceUser(str2) + ", password=" + tracePassword(str3));
            }
            boolean zAuthenticate = authenticate(str2, str3);
            if (!zAuthenticate) {
            }
            return zAuthenticate;
        } finally {
            try {
                closeConnection();
            } catch (MessagingException unused) {
            }
        }
    }

    private boolean authenticate(String str, String str2) throws MessagingException {
        String property = this.session.getProperty("mail." + this.name + ".auth.mechanisms");
        if (property == null) {
            property = this.defaultAuthenticationMechanisms;
        }
        String authorizationId = getAuthorizationId();
        if (authorizationId == null) {
            authorizationId = str;
        }
        if (this.enableSASL) {
            this.logger.fine("Authenticate with SASL");
            try {
                if (sasllogin(getSASLMechanisms(), getSASLRealm(), authorizationId, str, str2)) {
                    return true;
                }
                this.logger.fine("SASL authentication failed");
                return false;
            } catch (UnsupportedOperationException e) {
                this.logger.log(Level.FINE, "SASL support failed", (Throwable) e);
            }
        }
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("Attempt to authenticate using mechanisms: " + property);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(property);
        while (stringTokenizer.hasMoreTokens()) {
            String upperCase = stringTokenizer.nextToken().toUpperCase(Locale.ENGLISH);
            Authenticator authenticator = this.authenticators.get(upperCase);
            if (authenticator == null) {
                this.logger.log(Level.FINE, "no authenticator for mechanism {0}", upperCase);
            } else if (!supportsAuthentication(upperCase)) {
                this.logger.log(Level.FINE, "mechanism {0} not supported by server", upperCase);
            } else {
                if (property == this.defaultAuthenticationMechanisms) {
                    String str3 = "mail." + this.name + ".auth." + upperCase.toLowerCase(Locale.ENGLISH) + ".disable";
                    if (PropUtil.getBooleanProperty(this.session.getProperties(), str3, !authenticator.enabled())) {
                        if (this.logger.isLoggable(Level.FINE)) {
                            this.logger.fine("mechanism " + upperCase + " disabled by property: " + str3);
                        }
                    }
                }
                this.logger.log(Level.FINE, "Using mechanism {0}", upperCase);
                return authenticator.authenticate(this.host, authorizationId, str, str2);
            }
        }
        throw new AuthenticationFailedException("No authentication mechanisms supported by both server and client");
    }

    private abstract class Authenticator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final boolean enabled;
        private final String mech;
        protected int resp;

        abstract void doAuth(String str, String str2, String str3, String str4) throws MessagingException, IOException;

        String getInitialResponse(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            return null;
        }

        Authenticator(SMTPTransport sMTPTransport, String str) {
            this(str, true);
        }

        Authenticator(String str, boolean z) {
            this.mech = str.toUpperCase(Locale.ENGLISH);
            this.enabled = z;
        }

        String getMechanism() {
            return this.mech;
        }

        boolean enabled() {
            return this.enabled;
        }

        boolean authenticate(String str, String str2, String str3, String str4) throws MessagingException {
            String str5;
            try {
                try {
                    String initialResponse = getInitialResponse(str, str2, str3, str4);
                    if (SMTPTransport.this.noauthdebug && SMTPTransport.this.isTracing()) {
                        SMTPTransport.this.logger.fine("AUTH " + this.mech + " command trace suppressed");
                        SMTPTransport.this.suspendTracing();
                    }
                    if (initialResponse != null) {
                        this.resp = SMTPTransport.this.simpleCommand("AUTH " + this.mech + StringUtils.SPACE + (initialResponse.length() == 0 ? "=" : initialResponse));
                    } else {
                        this.resp = SMTPTransport.this.simpleCommand("AUTH " + this.mech);
                    }
                    if (this.resp == 530) {
                        SMTPTransport.this.startTLS();
                        if (initialResponse != null) {
                            this.resp = SMTPTransport.this.simpleCommand("AUTH " + this.mech + StringUtils.SPACE + initialResponse);
                        } else {
                            this.resp = SMTPTransport.this.simpleCommand("AUTH " + this.mech);
                        }
                    }
                    if (this.resp == 334) {
                        doAuth(str, str2, str3, str4);
                    }
                    if (SMTPTransport.this.noauthdebug && SMTPTransport.this.isTracing()) {
                        SMTPTransport.this.logger.fine("AUTH " + this.mech + StringUtils.SPACE + (this.resp != 235 ? "failed" : "succeeded"));
                    }
                    SMTPTransport.this.resumeTracing();
                    if (this.resp == 235) {
                        return true;
                    }
                    SMTPTransport.this.closeConnection();
                    throw new AuthenticationFailedException(SMTPTransport.this.getLastServerResponse());
                } catch (IOException e) {
                    SMTPTransport.this.logger.log(Level.FINE, "AUTH " + this.mech + " failed", (Throwable) e);
                    if (SMTPTransport.this.noauthdebug && SMTPTransport.this.isTracing()) {
                        MailLogger mailLogger = SMTPTransport.this.logger;
                        StringBuilder sbAppend = new StringBuilder("AUTH ").append(this.mech).append(StringUtils.SPACE);
                        str5 = this.resp != 235 ? "failed" : "succeeded";
                        mailLogger.fine(sbAppend.append(str5).toString());
                    }
                    SMTPTransport.this.resumeTracing();
                    if (this.resp != 235) {
                        SMTPTransport.this.closeConnection();
                        throw new AuthenticationFailedException(SMTPTransport.this.getLastServerResponse());
                    }
                    return true;
                } catch (Throwable th) {
                    SMTPTransport.this.logger.log(Level.FINE, "AUTH " + this.mech + " failed", th);
                    if (SMTPTransport.this.noauthdebug && SMTPTransport.this.isTracing()) {
                        MailLogger mailLogger2 = SMTPTransport.this.logger;
                        StringBuilder sbAppend2 = new StringBuilder("AUTH ").append(this.mech).append(StringUtils.SPACE);
                        str5 = this.resp != 235 ? "failed" : "succeeded";
                        mailLogger2.fine(sbAppend2.append(str5).toString());
                    }
                    SMTPTransport.this.resumeTracing();
                    if (this.resp != 235) {
                        SMTPTransport.this.closeConnection();
                        if (th instanceof Error) {
                            throw ((Error) th);
                        }
                        if (th instanceof Exception) {
                            throw new AuthenticationFailedException(SMTPTransport.this.getLastServerResponse(), (Exception) th);
                        }
                        throw new AuthenticationFailedException(SMTPTransport.this.getLastServerResponse());
                    }
                    return true;
                }
            } catch (Throwable th2) {
                if (SMTPTransport.this.noauthdebug && SMTPTransport.this.isTracing()) {
                    MailLogger mailLogger3 = SMTPTransport.this.logger;
                    StringBuilder sbAppend3 = new StringBuilder("AUTH ").append(this.mech).append(StringUtils.SPACE);
                    if (this.resp != 235) {
                        str5 = "failed";
                    }
                    mailLogger3.fine(sbAppend3.append(str5).toString());
                }
                SMTPTransport.this.resumeTracing();
                if (this.resp == 235) {
                    throw th2;
                }
                SMTPTransport.this.closeConnection();
                throw new AuthenticationFailedException(SMTPTransport.this.getLastServerResponse());
            }
        }
    }

    private class LoginAuthenticator extends Authenticator {
        LoginAuthenticator() {
            super(SMTPTransport.this, "LOGIN");
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        void doAuth(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            this.resp = SMTPTransport.this.simpleCommand(BASE64EncoderStream.encode(str3.getBytes(StandardCharsets.UTF_8)));
            if (this.resp == 334) {
                this.resp = SMTPTransport.this.simpleCommand(BASE64EncoderStream.encode(str4.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }

    private class PlainAuthenticator extends Authenticator {
        PlainAuthenticator() {
            super(SMTPTransport.this, "PLAIN");
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        String getInitialResponse(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BASE64EncoderStream bASE64EncoderStream = new BASE64EncoderStream(byteArrayOutputStream, Integer.MAX_VALUE);
            if (str2 != null) {
                bASE64EncoderStream.write(str2.getBytes(StandardCharsets.UTF_8));
            }
            bASE64EncoderStream.write(0);
            bASE64EncoderStream.write(str3.getBytes(StandardCharsets.UTF_8));
            bASE64EncoderStream.write(0);
            bASE64EncoderStream.write(str4.getBytes(StandardCharsets.UTF_8));
            bASE64EncoderStream.flush();
            return ASCIIUtility.toString(byteArrayOutputStream.toByteArray());
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        void doAuth(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            throw new AuthenticationFailedException("PLAIN asked for more");
        }
    }

    private class DigestMD5Authenticator extends Authenticator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private DigestMD5 md5support;

        DigestMD5Authenticator() {
            super(SMTPTransport.this, "DIGEST-MD5");
        }

        private synchronized DigestMD5 getMD5() {
            if (this.md5support == null) {
                this.md5support = new DigestMD5(SMTPTransport.this.logger);
            }
            return this.md5support;
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        void doAuth(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            DigestMD5 md5 = getMD5();
            this.resp = SMTPTransport.this.simpleCommand(md5.authClient(str, str3, str4, SMTPTransport.this.getSASLRealm(), SMTPTransport.this.getLastServerResponse()));
            if (this.resp == 334) {
                if (!md5.authServer(SMTPTransport.this.getLastServerResponse())) {
                    this.resp = -1;
                } else {
                    this.resp = SMTPTransport.this.simpleCommand(new byte[0]);
                }
            }
        }
    }

    private class NtlmAuthenticator extends Authenticator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int flags;
        private Ntlm ntlm;

        NtlmAuthenticator() {
            super(SMTPTransport.this, "NTLM");
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        String getInitialResponse(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            this.ntlm = new Ntlm(SMTPTransport.this.getNTLMDomain(), SMTPTransport.this.getLocalHost(), str3, str4, SMTPTransport.this.logger);
            int intProperty = PropUtil.getIntProperty(SMTPTransport.this.session.getProperties(), "mail." + SMTPTransport.this.name + ".auth.ntlm.flags", 0);
            this.flags = intProperty;
            return this.ntlm.generateType1Msg(intProperty);
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        void doAuth(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            this.resp = SMTPTransport.this.simpleCommand(this.ntlm.generateType3Msg(SMTPTransport.this.getLastServerResponse().substring(4).trim()));
        }
    }

    private class OAuth2Authenticator extends Authenticator {
        OAuth2Authenticator() {
            super("XOAUTH2", false);
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        String getInitialResponse(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            return ASCIIUtility.toString(BASE64EncoderStream.encode(("user=" + str3 + "\u0001auth=Bearer " + str4 + "\u0001\u0001").getBytes(StandardCharsets.UTF_8)));
        }

        @Override // com.sun.mail.smtp.SMTPTransport.Authenticator
        void doAuth(String str, String str2, String str3, String str4) throws MessagingException, IOException {
            throw new AuthenticationFailedException("OAUTH2 asked for more");
        }
    }

    private boolean sasllogin(String[] strArr, String str, String str2, String str3, String str4) throws MessagingException {
        String canonicalHostName;
        ArrayList arrayList;
        String str5;
        if (this.useCanonicalHostName) {
            canonicalHostName = this.serverSocket.getInetAddress().getCanonicalHostName();
        } else {
            canonicalHostName = this.host;
        }
        if (this.saslAuthenticator == null) {
            try {
                this.saslAuthenticator = (SaslAuthenticator) Class.forName("com.sun.mail.smtp.SMTPSaslAuthenticator").getConstructor(SMTPTransport.class, String.class, Properties.class, MailLogger.class, String.class).newInstance(this, this.name, this.session.getProperties(), this.logger, canonicalHostName);
            } catch (Exception e) {
                this.logger.log(Level.FINE, "Can't load SASL authenticator", (Throwable) e);
                return false;
            }
        }
        if (strArr != null && strArr.length > 0) {
            arrayList = new ArrayList(strArr.length);
            for (int i = 0; i < strArr.length; i++) {
                if (supportsAuthentication(strArr[i])) {
                    arrayList.add(strArr[i]);
                }
            }
        } else {
            arrayList = new ArrayList();
            Hashtable<String, String> hashtable = this.extMap;
            if (hashtable != null && (str5 = hashtable.get("AUTH")) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(str5);
                while (stringTokenizer.hasMoreTokens()) {
                    arrayList.add(stringTokenizer.nextToken());
                }
            }
        }
        String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        try {
            if (this.noauthdebug && isTracing()) {
                this.logger.fine("SASL AUTH command trace suppressed");
                suspendTracing();
            }
            return this.saslAuthenticator.authenticate(strArr2, str, str2, str3, str4);
        } finally {
            resumeTracing();
        }
    }

    @Override // javax.mail.Transport
    public synchronized void sendMessage(Message message, Address[] addressArr) throws MessagingException {
        sendMessageStart(message != null ? message.getSubject() : "");
        checkConnected();
        if (!(message instanceof MimeMessage)) {
            this.logger.fine("Can only send RFC822 msgs");
            throw new MessagingException("SMTP can only send RFC822 messages");
        }
        for (int i = 0; i < addressArr.length; i++) {
            if (!(addressArr[i] instanceof InternetAddress)) {
                throw new MessagingException(addressArr[i] + " is not an InternetAddress");
            }
        }
        if (addressArr.length == 0) {
            throw new SendFailedException("No recipient addresses");
        }
        this.message = (MimeMessage) message;
        this.addresses = addressArr;
        this.validUnsentAddr = addressArr;
        expandGroups();
        boolean allow8bitMIME = message instanceof SMTPMessage ? ((SMTPMessage) message).getAllow8bitMIME() : false;
        if (!allow8bitMIME) {
            allow8bitMIME = PropUtil.getBooleanProperty(this.session.getProperties(), "mail." + this.name + ".allow8bitmime", false);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("use8bit " + allow8bitMIME);
        }
        if (allow8bitMIME && supportsExtension("8BITMIME") && convertTo8Bit(this.message)) {
            try {
                this.message.saveChanges();
            } catch (MessagingException unused) {
            }
        }
        try {
            try {
                try {
                    mailFrom();
                    rcptTo();
                    if (this.chunkSize > 0 && supportsExtension("CHUNKING")) {
                        this.message.writeTo(bdat(), ignoreList);
                        finishBdat();
                    } else {
                        this.message.writeTo(data(), ignoreList);
                        finishData();
                    }
                    if (this.sendPartiallyFailed) {
                        this.logger.fine("Sending partially failed because of invalid destination addresses");
                        notifyTransportListeners(3, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
                        throw new SMTPSendFailedException(".", this.lastReturnCode, this.lastServerResponse, this.exception, this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
                    }
                    this.logger.fine("message successfully delivered to mail server");
                    notifyTransportListeners(1, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
                    this.invalidAddr = null;
                    this.validUnsentAddr = null;
                    this.validSentAddr = null;
                    this.addresses = null;
                    this.message = null;
                    this.exception = null;
                    this.sendPartiallyFailed = false;
                    this.notificationDone = false;
                    sendMessageEnd();
                } catch (IOException e) {
                    this.logger.log(Level.FINE, "IOException while sending, closing", (Throwable) e);
                    try {
                        closeConnection();
                    } catch (MessagingException unused2) {
                    }
                    addressesFailed();
                    notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
                    throw new MessagingException("IOException while sending message", e);
                }
            } catch (MessagingException e2) {
                this.logger.log(Level.FINE, "MessagingException while sending", (Throwable) e2);
                if (e2.getNextException() instanceof IOException) {
                    this.logger.fine("nested IOException, closing");
                    try {
                        closeConnection();
                    } catch (MessagingException unused3) {
                    }
                }
                addressesFailed();
                notifyTransportListeners(2, this.validSentAddr, this.validUnsentAddr, this.invalidAddr, this.message);
                throw e2;
            }
        } catch (Throwable th) {
            this.invalidAddr = null;
            this.validUnsentAddr = null;
            this.validSentAddr = null;
            this.addresses = null;
            this.message = null;
            this.exception = null;
            this.sendPartiallyFailed = false;
            this.notificationDone = false;
            throw th;
        }
    }

    private void addressesFailed() {
        Address[] addressArr = this.validSentAddr;
        if (addressArr != null) {
            Address[] addressArr2 = this.validUnsentAddr;
            if (addressArr2 != null) {
                Address[] addressArr3 = new Address[addressArr.length + addressArr2.length];
                System.arraycopy(addressArr, 0, addressArr3, 0, addressArr.length);
                Address[] addressArr4 = this.validUnsentAddr;
                System.arraycopy(addressArr4, 0, addressArr3, this.validSentAddr.length, addressArr4.length);
                this.validSentAddr = null;
                this.validUnsentAddr = addressArr3;
                return;
            }
            this.validUnsentAddr = addressArr;
            this.validSentAddr = null;
        }
    }

    @Override // javax.mail.Service, java.lang.AutoCloseable
    public synchronized void close() throws MessagingException {
        int serverResponse;
        if (super.isConnected()) {
            try {
                if (this.serverSocket != null) {
                    sendCommand("QUIT");
                    if (this.quitWait && (serverResponse = readServerResponse()) != 221 && serverResponse != -1 && this.logger.isLoggable(Level.FINE)) {
                        this.logger.fine("QUIT failed with " + serverResponse);
                    }
                }
            } finally {
                closeConnection();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeConnection() throws MessagingException {
        try {
            try {
                Socket socket = this.serverSocket;
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                throw new MessagingException("Server Close Failed", e);
            }
        } finally {
            this.serverSocket = null;
            this.serverOutput = null;
            this.serverInput = null;
            this.lineInputStream = null;
            if (super.isConnected()) {
                super.close();
            }
        }
    }

    @Override // javax.mail.Service
    public synchronized boolean isConnected() {
        if (!super.isConnected()) {
            return false;
        }
        try {
            try {
                if (this.useRset) {
                    sendCommand("RSET");
                } else {
                    sendCommand("NOOP");
                }
                int serverResponse = readServerResponse();
                if (serverResponse >= 0 && (!this.noopStrict ? serverResponse == 421 : serverResponse != 250)) {
                    return true;
                }
                try {
                    closeConnection();
                } catch (MessagingException unused) {
                }
                return false;
            } catch (MessagingException unused2) {
            }
        } catch (Exception unused3) {
            closeConnection();
        }
        return false;
    }

    @Override // javax.mail.Transport
    protected void notifyTransportListeners(int i, Address[] addressArr, Address[] addressArr2, Address[] addressArr3, Message message) {
        if (this.notificationDone) {
            return;
        }
        super.notifyTransportListeners(i, addressArr, addressArr2, addressArr3, message);
        this.notificationDone = true;
    }

    private void expandGroups() {
        ArrayList arrayList = null;
        int i = 0;
        while (true) {
            Address[] addressArr = this.addresses;
            if (i >= addressArr.length) {
                break;
            }
            InternetAddress internetAddress = (InternetAddress) addressArr[i];
            if (internetAddress.isGroup()) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    for (int i2 = 0; i2 < i; i2++) {
                        arrayList.add(this.addresses[i2]);
                    }
                }
                try {
                    InternetAddress[] group = internetAddress.getGroup(true);
                    if (group != null) {
                        for (InternetAddress internetAddress2 : group) {
                            arrayList.add(internetAddress2);
                        }
                    } else {
                        arrayList.add(internetAddress);
                    }
                } catch (ParseException unused) {
                    arrayList.add(internetAddress);
                }
            } else if (arrayList != null) {
                arrayList.add(internetAddress);
            }
            i++;
        }
        if (arrayList != null) {
            InternetAddress[] internetAddressArr = new InternetAddress[arrayList.size()];
            arrayList.toArray(internetAddressArr);
            this.addresses = internetAddressArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean convertTo8Bit(MimePart mimePart) {
        InputStream inputStream;
        boolean z = false;
        try {
            if (mimePart.isMimeType("text/*")) {
                String encoding = mimePart.getEncoding();
                if (encoding == null) {
                    return false;
                }
                if (!encoding.equalsIgnoreCase("quoted-printable") && !encoding.equalsIgnoreCase("base64")) {
                    return false;
                }
                try {
                    inputStream = mimePart.getInputStream();
                } catch (Throwable th) {
                    th = th;
                    inputStream = null;
                }
                try {
                    if (is8Bit(inputStream)) {
                        mimePart.setContent(mimePart.getContent(), mimePart.getContentType());
                        mimePart.setHeader("Content-Transfer-Encoding", "8bit");
                        z = true;
                    }
                    if (inputStream == null) {
                        return z;
                    }
                    inputStream.close();
                    return z;
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            }
            if (!mimePart.isMimeType("multipart/*")) {
                return false;
            }
            MimeMultipart mimeMultipart = (MimeMultipart) mimePart.getContent();
            int count = mimeMultipart.getCount();
            boolean z2 = false;
            for (int i = 0; i < count; i++) {
                try {
                    if (convertTo8Bit((MimePart) mimeMultipart.getBodyPart(i))) {
                        z2 = true;
                    }
                } catch (IOException | MessagingException unused2) {
                }
            }
            return z2;
        } catch (IOException | MessagingException unused3) {
            return false;
        }
    }

    private boolean is8Bit(InputStream inputStream) {
        boolean z = false;
        int i = 0;
        while (true) {
            try {
                int i2 = inputStream.read();
                if (i2 < 0) {
                    if (z) {
                        this.logger.fine("found an 8bit part");
                    }
                    return z;
                }
                int i3 = i2 & 255;
                if (i3 == 13 || i3 == 10) {
                    i = 0;
                } else if (i3 == 0 || (i = i + 1) > 998) {
                    return false;
                }
                if (i3 > 127) {
                    z = true;
                }
            } catch (IOException unused) {
                return false;
            }
        }
    }

    @Override // javax.mail.Service
    protected void finalize() throws Throwable {
        try {
            closeConnection();
        } catch (MessagingException unused) {
        } catch (Throwable th) {
            super.finalize();
            throw th;
        }
        super.finalize();
    }

    protected void helo(String str) throws MessagingException {
        if (str != null) {
            issueCommand("HELO " + str, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        } else {
            issueCommand("HELO", ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        }
    }

    protected boolean ehlo(String str) throws MessagingException {
        sendCommand(str != null ? "EHLO " + str : "EHLO");
        int serverResponse = readServerResponse();
        if (serverResponse == 250) {
            BufferedReader bufferedReader = new BufferedReader(new StringReader(this.lastServerResponse));
            this.extMap = new Hashtable<>();
            boolean z = true;
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    if (z) {
                        z = false;
                    } else if (line.length() >= 5) {
                        String strSubstring = line.substring(4);
                        int iIndexOf = strSubstring.indexOf(32);
                        String strSubstring2 = "";
                        if (iIndexOf > 0) {
                            strSubstring2 = strSubstring.substring(iIndexOf + 1);
                            strSubstring = strSubstring.substring(0, iIndexOf);
                        }
                        if (this.logger.isLoggable(Level.FINE)) {
                            this.logger.fine("Found extension \"" + strSubstring + "\", arg \"" + strSubstring2 + "\"");
                        }
                        this.extMap.put(strSubstring.toUpperCase(Locale.ENGLISH), strSubstring2);
                    }
                } catch (IOException unused) {
                }
            }
        }
        return serverResponse == 250;
    }

    protected void mailFrom() throws MessagingException {
        Address localAddress;
        Address[] from;
        MimeMessage mimeMessage = this.message;
        String envelopeFrom = mimeMessage instanceof SMTPMessage ? ((SMTPMessage) mimeMessage).getEnvelopeFrom() : null;
        if (envelopeFrom == null || envelopeFrom.length() <= 0) {
            envelopeFrom = this.session.getProperty("mail." + this.name + ".from");
        }
        boolean z = false;
        if (envelopeFrom == null || envelopeFrom.length() <= 0) {
            MimeMessage mimeMessage2 = this.message;
            if (mimeMessage2 != null && (from = mimeMessage2.getFrom()) != null && from.length > 0) {
                localAddress = from[0];
            } else {
                localAddress = InternetAddress.getLocalAddress(this.session);
            }
            if (localAddress != null) {
                envelopeFrom = ((InternetAddress) localAddress).getAddress();
            } else {
                throw new MessagingException("can't determine local email address");
            }
        }
        String str = "MAIL FROM:" + normalizeAddress(envelopeFrom);
        if (this.allowutf8 && supportsExtension("SMTPUTF8")) {
            str = str + " SMTPUTF8";
        }
        if (supportsExtension("DSN")) {
            MimeMessage mimeMessage3 = this.message;
            String dSNRet = mimeMessage3 instanceof SMTPMessage ? ((SMTPMessage) mimeMessage3).getDSNRet() : null;
            if (dSNRet == null) {
                dSNRet = this.session.getProperty("mail." + this.name + ".dsn.ret");
            }
            if (dSNRet != null) {
                str = str + " RET=" + dSNRet;
            }
        }
        if (supportsExtension("AUTH")) {
            MimeMessage mimeMessage4 = this.message;
            String submitter = mimeMessage4 instanceof SMTPMessage ? ((SMTPMessage) mimeMessage4).getSubmitter() : null;
            if (submitter == null) {
                submitter = this.session.getProperty("mail." + this.name + ".submitter");
            }
            if (submitter != null) {
                try {
                    if (this.allowutf8 && supportsExtension("SMTPUTF8")) {
                        z = true;
                    }
                    str = str + " AUTH=" + xtext(submitter, z);
                } catch (IllegalArgumentException e) {
                    if (this.logger.isLoggable(Level.FINE)) {
                        this.logger.log(Level.FINE, "ignoring invalid submitter: " + submitter, (Throwable) e);
                    }
                }
            }
        }
        MimeMessage mimeMessage5 = this.message;
        String mailExtension = mimeMessage5 instanceof SMTPMessage ? ((SMTPMessage) mimeMessage5).getMailExtension() : null;
        if (mailExtension == null) {
            mailExtension = this.session.getProperty("mail." + this.name + ".mailextension");
        }
        if (mailExtension != null && mailExtension.length() > 0) {
            str = str + StringUtils.SPACE + mailExtension;
        }
        try {
            issueSendCommand(str, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        } catch (SMTPSendFailedException e2) {
            int returnCode = e2.getReturnCode();
            if (returnCode == 501 || returnCode == 503 || returnCode == 553 || returnCode == 550 || returnCode == 551) {
                try {
                    e2.setNextException(new SMTPSenderFailedException(new InternetAddress(envelopeFrom), str, returnCode, e2.getMessage()));
                } catch (AddressException unused) {
                }
            }
            throw e2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:155:0x0194 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x018a A[PHI: r9 r10
      0x018a: PHI (r9v3 java.lang.Exception) = (r9v2 java.lang.Exception), (r9v6 java.lang.Exception), (r9v8 java.lang.Exception), (r9v12 java.lang.Exception) binds: [B:79:0x0188, B:72:0x0172, B:66:0x015e, B:52:0x0109] A[DONT_GENERATE, DONT_INLINE]
      0x018a: PHI (r10v5 boolean) = (r10v1 boolean), (r10v7 boolean), (r10v9 boolean), (r10v11 boolean) binds: [B:79:0x0188, B:72:0x0172, B:66:0x015e, B:52:0x0109] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void rcptTo() throws javax.mail.MessagingException {
        /*
            Method dump skipped, instruction units count: 796
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.smtp.SMTPTransport.rcptTo():void");
    }

    protected OutputStream data() throws MessagingException {
        issueSendCommand("DATA", 354);
        SMTPOutputStream sMTPOutputStream = new SMTPOutputStream(this.serverOutput);
        this.dataStream = sMTPOutputStream;
        return sMTPOutputStream;
    }

    protected void finishData() throws MessagingException, IOException {
        this.dataStream.ensureAtBOL();
        issueSendCommand(".", ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
    }

    protected OutputStream bdat() throws MessagingException {
        BDATOutputStream bDATOutputStream = new BDATOutputStream(this.serverOutput, this.chunkSize);
        this.dataStream = bDATOutputStream;
        return bDATOutputStream;
    }

    protected void finishBdat() throws MessagingException, IOException {
        this.dataStream.ensureAtBOL();
        this.dataStream.close();
    }

    protected void startTLS() throws MessagingException {
        issueCommand("STARTTLS", 220);
        try {
            this.serverSocket = SocketFetcher.startTLS(this.serverSocket, this.host, this.session.getProperties(), "mail." + this.name);
            initStreams();
        } catch (IOException e) {
            closeConnection();
            throw new MessagingException("Could not convert socket to TLS", e);
        }
    }

    private void openServer(String str, int i) throws MessagingException {
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("trying to connect to host \"" + str + "\", port " + i + ", isSSL " + this.isSSL);
        }
        try {
            Socket socket = SocketFetcher.getSocket(str, i, this.session.getProperties(), "mail." + this.name, this.isSSL);
            this.serverSocket = socket;
            int port = socket.getPort();
            this.host = str;
            initStreams();
            int serverResponse = readServerResponse();
            if (serverResponse != 220) {
                this.serverSocket.close();
                this.serverSocket = null;
                this.serverOutput = null;
                this.serverInput = null;
                this.lineInputStream = null;
                if (this.logger.isLoggable(Level.FINE)) {
                    this.logger.fine("could not connect to host \"" + str + "\", port: " + port + ", response: " + serverResponse);
                }
                throw new MessagingException("Could not connect to SMTP host: " + str + ", port: " + port + ", response: " + serverResponse);
            }
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("connected to host \"" + str + "\", port: " + port);
            }
        } catch (SocketConnectException e) {
            throw new MailConnectException(e);
        } catch (UnknownHostException e2) {
            throw new MessagingException("Unknown SMTP host: " + str, e2);
        } catch (IOException e3) {
            throw new MessagingException("Could not connect to SMTP host: " + str + ", port: " + i, e3);
        }
    }

    private void openServer() throws MessagingException {
        this.host = UNKNOWN;
        try {
            int port = this.serverSocket.getPort();
            this.host = this.serverSocket.getInetAddress().getHostName();
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("starting protocol to host \"" + this.host + "\", port " + port);
            }
            initStreams();
            int serverResponse = readServerResponse();
            if (serverResponse != 220) {
                this.serverSocket.close();
                this.serverSocket = null;
                this.serverOutput = null;
                this.serverInput = null;
                this.lineInputStream = null;
                if (this.logger.isLoggable(Level.FINE)) {
                    this.logger.fine("got bad greeting from host \"" + this.host + "\", port: " + port + ", response: " + serverResponse);
                }
                throw new MessagingException("Got bad greeting from SMTP host: " + this.host + ", port: " + port + ", response: " + serverResponse);
            }
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("protocol started to host \"" + this.host + "\", port: " + port);
            }
        } catch (IOException e) {
            throw new MessagingException("Could not start protocol to SMTP host: " + this.host + ", port: -1", e);
        }
    }

    private void initStreams() throws IOException {
        boolean booleanProperty = PropUtil.getBooleanProperty(this.session.getProperties(), "mail.debug.quote", false);
        TraceInputStream traceInputStream = new TraceInputStream(this.serverSocket.getInputStream(), this.traceLogger);
        this.traceInput = traceInputStream;
        traceInputStream.setQuote(booleanProperty);
        TraceOutputStream traceOutputStream = new TraceOutputStream(this.serverSocket.getOutputStream(), this.traceLogger);
        this.traceOutput = traceOutputStream;
        traceOutputStream.setQuote(booleanProperty);
        this.serverOutput = new BufferedOutputStream(this.traceOutput);
        this.serverInput = new BufferedInputStream(this.traceInput);
        this.lineInputStream = new LineInputStream(this.serverInput);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTracing() {
        return this.traceLogger.isLoggable(Level.FINEST);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void suspendTracing() {
        if (this.traceLogger.isLoggable(Level.FINEST)) {
            this.traceInput.setTrace(false);
            this.traceOutput.setTrace(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resumeTracing() {
        if (this.traceLogger.isLoggable(Level.FINEST)) {
            this.traceInput.setTrace(true);
            this.traceOutput.setTrace(true);
        }
    }

    public synchronized void issueCommand(String str, int i) throws MessagingException {
        sendCommand(str);
        int serverResponse = readServerResponse();
        if (i != -1 && serverResponse != i) {
            throw new MessagingException(this.lastServerResponse);
        }
    }

    private void issueSendCommand(String str, int i) throws MessagingException {
        sendCommand(str);
        int serverResponse = readServerResponse();
        if (serverResponse != i) {
            Address[] addressArr = this.validSentAddr;
            int length = addressArr == null ? 0 : addressArr.length;
            Address[] addressArr2 = this.validUnsentAddr;
            int length2 = addressArr2 == null ? 0 : addressArr2.length;
            Address[] addressArr3 = new Address[length + length2];
            if (length > 0) {
                System.arraycopy(addressArr, 0, addressArr3, 0, length);
            }
            if (length2 > 0) {
                System.arraycopy(this.validUnsentAddr, 0, addressArr3, length, length2);
            }
            this.validSentAddr = null;
            this.validUnsentAddr = addressArr3;
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("got response code " + serverResponse + ", with response: " + this.lastServerResponse);
            }
            String str2 = this.lastServerResponse;
            int i2 = this.lastReturnCode;
            if (this.serverSocket != null) {
                issueCommand("RSET", -1);
            }
            this.lastServerResponse = str2;
            this.lastReturnCode = i2;
            throw new SMTPSendFailedException(str, serverResponse, this.lastServerResponse, this.exception, this.validSentAddr, this.validUnsentAddr, this.invalidAddr);
        }
    }

    public synchronized int simpleCommand(String str) throws MessagingException {
        sendCommand(str);
        return readServerResponse();
    }

    protected int simpleCommand(byte[] bArr) throws MessagingException {
        sendCommand(bArr);
        return readServerResponse();
    }

    protected void sendCommand(String str) throws MessagingException {
        sendCommand(toBytes(str));
    }

    private void sendCommand(byte[] bArr) throws MessagingException {
        try {
            this.serverOutput.write(bArr);
            this.serverOutput.write(CRLF);
            this.serverOutput.flush();
        } catch (IOException e) {
            throw new MessagingException("Can't send command to SMTP host", e);
        }
    }

    protected int readServerResponse() throws MessagingException {
        String line;
        int i;
        StringBuilder sb = new StringBuilder(100);
        do {
            try {
                line = this.lineInputStream.readLine();
                if (line == null) {
                    String string = sb.toString();
                    if (string.length() == 0) {
                        string = "[EOF]";
                    }
                    this.lastServerResponse = string;
                    this.lastReturnCode = -1;
                    this.logger.log(Level.FINE, "EOF: {0}", string);
                    return -1;
                }
                sb.append(line);
                sb.append("\n");
            } catch (IOException e) {
                this.logger.log(Level.FINE, "exception reading response", (Throwable) e);
                this.lastServerResponse = "";
                this.lastReturnCode = 0;
                throw new MessagingException("Exception reading response", e);
            }
        } while (isNotLastLine(line));
        String string2 = sb.toString();
        if (string2.length() >= 3) {
            try {
                try {
                    try {
                        i = Integer.parseInt(string2.substring(0, 3));
                    } catch (NumberFormatException unused) {
                        close();
                    } catch (StringIndexOutOfBoundsException unused2) {
                        close();
                    }
                } catch (MessagingException e2) {
                    this.logger.log(Level.FINE, "close failed", (Throwable) e2);
                }
            } catch (MessagingException e3) {
                this.logger.log(Level.FINE, "close failed", (Throwable) e3);
            }
        } else {
            i = -1;
        }
        if (i == -1) {
            this.logger.log(Level.FINE, "bad server response: {0}", string2);
        }
        this.lastServerResponse = string2;
        this.lastReturnCode = i;
        return i;
    }

    protected void checkConnected() {
        if (!super.isConnected()) {
            throw new IllegalStateException("Not connected");
        }
    }

    private boolean isNotLastLine(String str) {
        return str != null && str.length() >= 4 && str.charAt(3) == '-';
    }

    private String normalizeAddress(String str) {
        return (str.startsWith("<") || str.endsWith(">")) ? str : "<" + str + ">";
    }

    public boolean supportsExtension(String str) {
        Hashtable<String, String> hashtable = this.extMap;
        return (hashtable == null || hashtable.get(str.toUpperCase(Locale.ENGLISH)) == null) ? false : true;
    }

    public String getExtensionParameter(String str) {
        Hashtable<String, String> hashtable = this.extMap;
        if (hashtable == null) {
            return null;
        }
        return hashtable.get(str.toUpperCase(Locale.ENGLISH));
    }

    protected boolean supportsAuthentication(String str) {
        String str2;
        Hashtable<String, String> hashtable = this.extMap;
        if (hashtable == null || (str2 = hashtable.get("AUTH")) == null) {
            return false;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str2);
        while (stringTokenizer.hasMoreTokens()) {
            if (stringTokenizer.nextToken().equalsIgnoreCase(str)) {
                return true;
            }
        }
        if (!str.equalsIgnoreCase("LOGIN") || !supportsExtension("AUTH=LOGIN")) {
            return false;
        }
        this.logger.fine("use AUTH=LOGIN hack");
        return true;
    }

    protected static String xtext(String str) {
        return xtext(str, false);
    }

    protected static String xtext(String str, boolean z) {
        byte[] bytes;
        if (z) {
            bytes = str.getBytes(StandardCharsets.UTF_8);
        } else {
            bytes = ASCIIUtility.getBytes(str);
        }
        StringBuilder sb = null;
        for (int i = 0; i < bytes.length; i++) {
            char c = (char) (bytes[i] & UByte.MAX_VALUE);
            if (!z && c >= 128) {
                throw new IllegalArgumentException("Non-ASCII character in SMTP submitter: " + str);
            }
            if (c < '!' || c > '~' || c == '+' || c == '=') {
                if (sb == null) {
                    sb = new StringBuilder(str.length() + 4);
                    sb.append(str.substring(0, i));
                }
                sb.append('+');
                sb.append(hexchar[(c & 240) >> 4]);
                sb.append(hexchar[c & 15]);
            } else if (sb != null) {
                sb.append(c);
            }
        }
        return sb != null ? sb.toString() : str;
    }

    private String traceUser(String str) {
        return this.debugusername ? str : "<user name suppressed>";
    }

    private String tracePassword(String str) {
        return this.debugpassword ? str : str == null ? "<null>" : "<non-null>";
    }

    private byte[] toBytes(String str) {
        if (this.allowutf8) {
            return str.getBytes(StandardCharsets.UTF_8);
        }
        return ASCIIUtility.getBytes(str);
    }

    private class BDATOutputStream extends SMTPOutputStream {
        public BDATOutputStream(OutputStream outputStream, int i) {
            super(SMTPTransport.this.new ChunkedOutputStream(outputStream, i));
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.out.close();
        }
    }

    private class ChunkedOutputStream extends OutputStream {
        private final byte[] buf;
        private int count = 0;
        private final OutputStream out;

        public ChunkedOutputStream(OutputStream outputStream, int i) {
            this.out = outputStream;
            this.buf = new byte[i];
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            byte[] bArr = this.buf;
            int i2 = this.count;
            int i3 = i2 + 1;
            this.count = i3;
            bArr[i2] = (byte) i;
            if (i3 >= bArr.length) {
                flush();
            }
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            while (i2 > 0) {
                int iMin = Math.min(this.buf.length - this.count, i2);
                byte[] bArr2 = this.buf;
                if (iMin == bArr2.length) {
                    bdat(bArr, i, iMin, false);
                } else {
                    System.arraycopy(bArr, i, bArr2, this.count, iMin);
                    this.count += iMin;
                }
                i += iMin;
                i2 -= iMin;
                if (this.count >= this.buf.length) {
                    flush();
                }
            }
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() throws IOException {
            bdat(this.buf, 0, this.count, false);
            this.count = 0;
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            bdat(this.buf, 0, this.count, true);
            this.count = 0;
        }

        private void bdat(byte[] bArr, int i, int i2, boolean z) throws IOException {
            if (i2 > 0 || z) {
                try {
                    if (z) {
                        SMTPTransport.this.sendCommand("BDAT " + i2 + " LAST");
                    } else {
                        SMTPTransport.this.sendCommand("BDAT " + i2);
                    }
                    this.out.write(bArr, i, i2);
                    this.out.flush();
                    if (SMTPTransport.this.readServerResponse() == 250) {
                    } else {
                        throw new IOException(SMTPTransport.this.lastServerResponse);
                    }
                } catch (MessagingException e) {
                    throw new IOException("BDAT write exception", e);
                }
            }
        }
    }
}
