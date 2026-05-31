package com.sun.mail.imap;

import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.Protocol;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.IMAPReferralException;
import com.sun.mail.imap.protocol.ListInfo;
import com.sun.mail.imap.protocol.Namespaces;
import com.sun.mail.util.MailConnectException;
import com.sun.mail.util.MailLogger;
import com.sun.mail.util.PropUtil;
import com.sun.mail.util.SocketConnectException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import javax.mail.AuthenticationFailedException;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Quota;
import javax.mail.QuotaAwareStore;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.URLName;
import kotlin.jvm.internal.CharCompanionObject;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: loaded from: classes2.dex */
public class IMAPStore extends Store implements QuotaAwareStore, ResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ID_ADDRESS = "address";
    public static final String ID_ARGUMENTS = "arguments";
    public static final String ID_COMMAND = "command";
    public static final String ID_DATE = "date";
    public static final String ID_ENVIRONMENT = "environment";
    public static final String ID_NAME = "name";
    public static final String ID_OS = "os";
    public static final String ID_OS_VERSION = "os-version";
    public static final String ID_SUPPORT_URL = "support-url";
    public static final String ID_VENDOR = "vendor";
    public static final String ID_VERSION = "version";
    public static final int RESPONSE = 1000;
    private final int appendBufferSize;
    protected String authorizationID;
    private final int blksize;
    private boolean closeFoldersOnStoreFailure;
    private volatile boolean connectionFailed;
    private final Object connectionFailedLock;
    private boolean debugpassword;
    private boolean debugusername;
    protected final int defaultPort;
    private boolean enableCompress;
    private boolean enableImapEvents;
    private boolean enableResponseEvents;
    private boolean enableSASL;
    private boolean enableStartTLS;
    private boolean finalizeCleanClose;
    private volatile Constructor<?> folderConstructor;
    private volatile Constructor<?> folderConstructorLI;
    private volatile boolean forceClose;
    private boolean forcePasswordRefresh;
    private String guid;
    protected String host;
    private boolean ignoreSize;
    protected final boolean isSSL;
    protected MailLogger logger;
    private boolean messageCacheDebug;
    private final int minIdleTime;
    protected final String name;
    private Namespaces namespaces;
    private ResponseHandler nonStoreResponseHandler;
    protected String password;
    private boolean peek;
    private final ConnectionPool pool;
    private volatile int port;
    protected String proxyAuthUser;
    private boolean requireStartTLS;
    private String[] saslMechanisms;
    protected String saslRealm;
    private final int statusCacheTimeout;
    private boolean throwSearchException;
    protected String user;
    private boolean usingSSL;

    protected void preLogin(IMAPProtocol iMAPProtocol) throws ProtocolException {
    }

    static class ConnectionPool {
        private static final int ABORTING = 2;
        private static final int IDLE = 1;
        private static final int RUNNING = 0;
        private final long clientTimeoutInterval;
        private Vector<IMAPFolder> folders;
        private IMAPProtocol idleProtocol;
        private final MailLogger logger;
        private final int poolSize;
        private final long pruningInterval;
        private final boolean separateStoreConnection;
        private final long serverTimeoutInterval;
        private Vector<IMAPProtocol> authenticatedConnections = new Vector<>();
        private boolean storeConnectionInUse = false;
        private int idleState = 0;
        private long lastTimePruned = System.currentTimeMillis();

        ConnectionPool(String str, MailLogger mailLogger, Session session) {
            Properties properties = session.getProperties();
            MailLogger subLogger = mailLogger.getSubLogger("connectionpool", "DEBUG IMAP CP", PropUtil.getBooleanProperty(properties, "mail." + str + ".connectionpool.debug", false));
            this.logger = subLogger;
            int intProperty = PropUtil.getIntProperty(properties, "mail." + str + ".connectionpoolsize", -1);
            if (intProperty > 0) {
                this.poolSize = intProperty;
                if (subLogger.isLoggable(Level.CONFIG)) {
                    subLogger.config("mail.imap.connectionpoolsize: " + intProperty);
                }
            } else {
                this.poolSize = 1;
            }
            int intProperty2 = PropUtil.getIntProperty(properties, "mail." + str + ".connectionpooltimeout", -1);
            if (intProperty2 > 0) {
                long j = intProperty2;
                this.clientTimeoutInterval = j;
                if (subLogger.isLoggable(Level.CONFIG)) {
                    subLogger.config("mail.imap.connectionpooltimeout: " + j);
                }
            } else {
                this.clientTimeoutInterval = 45000L;
            }
            int intProperty3 = PropUtil.getIntProperty(properties, "mail." + str + ".servertimeout", -1);
            if (intProperty3 > 0) {
                long j2 = intProperty3;
                this.serverTimeoutInterval = j2;
                if (subLogger.isLoggable(Level.CONFIG)) {
                    subLogger.config("mail.imap.servertimeout: " + j2);
                }
            } else {
                this.serverTimeoutInterval = 1800000L;
            }
            int intProperty4 = PropUtil.getIntProperty(properties, "mail." + str + ".pruninginterval", -1);
            if (intProperty4 > 0) {
                long j3 = intProperty4;
                this.pruningInterval = j3;
                if (subLogger.isLoggable(Level.CONFIG)) {
                    subLogger.config("mail.imap.pruninginterval: " + j3);
                }
            } else {
                this.pruningInterval = DateUtils.MILLIS_PER_MINUTE;
            }
            boolean booleanProperty = PropUtil.getBooleanProperty(properties, "mail." + str + ".separatestoreconnection", false);
            this.separateStoreConnection = booleanProperty;
            if (booleanProperty) {
                subLogger.config("dedicate a store connection");
            }
        }
    }

    public IMAPStore(Session session, URLName uRLName) {
        this(session, uRLName, "imap", false);
    }

    protected IMAPStore(Session session, URLName uRLName, String str, boolean z) {
        Class<?> cls;
        String property;
        super(session, uRLName);
        this.port = -1;
        this.enableStartTLS = false;
        this.requireStartTLS = false;
        this.usingSSL = false;
        this.enableSASL = false;
        this.forcePasswordRefresh = false;
        this.enableResponseEvents = false;
        this.enableImapEvents = false;
        this.throwSearchException = false;
        this.peek = false;
        this.closeFoldersOnStoreFailure = true;
        this.enableCompress = false;
        this.finalizeCleanClose = false;
        this.connectionFailed = false;
        this.forceClose = false;
        this.connectionFailedLock = new Object();
        this.folderConstructor = null;
        this.folderConstructorLI = null;
        this.nonStoreResponseHandler = new ResponseHandler() { // from class: com.sun.mail.imap.IMAPStore.1
            @Override // com.sun.mail.iap.ResponseHandler
            public void handleResponse(Response response) {
                if (response.isOK() || response.isNO() || response.isBAD() || response.isBYE()) {
                    IMAPStore.this.handleResponseCode(response);
                }
                if (response.isBYE()) {
                    IMAPStore.this.logger.fine("IMAPStore non-store connection dead");
                }
            }
        };
        Properties properties = session.getProperties();
        str = uRLName != null ? uRLName.getProtocol() : str;
        this.name = str;
        z = z ? z : PropUtil.getBooleanProperty(properties, "mail." + str + ".ssl.enable", false);
        if (z) {
            this.defaultPort = 993;
        } else {
            this.defaultPort = 143;
        }
        this.isSSL = z;
        this.debug = session.getDebug();
        this.debugusername = PropUtil.getBooleanProperty(properties, "mail.debug.auth.username", true);
        this.debugpassword = PropUtil.getBooleanProperty(properties, "mail.debug.auth.password", false);
        this.logger = new MailLogger(getClass(), "DEBUG " + str.toUpperCase(Locale.ENGLISH), session.getDebug(), session.getDebugOut());
        if (!PropUtil.getBooleanProperty(properties, "mail." + str + ".partialfetch", true)) {
            this.blksize = -1;
            this.logger.config("mail.imap.partialfetch: false");
        } else {
            int intProperty = PropUtil.getIntProperty(properties, "mail." + str + ".fetchsize", 16384);
            this.blksize = intProperty;
            if (this.logger.isLoggable(Level.CONFIG)) {
                this.logger.config("mail.imap.fetchsize: " + intProperty);
            }
        }
        this.ignoreSize = PropUtil.getBooleanProperty(properties, "mail." + str + ".ignorebodystructuresize", false);
        if (this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config("mail.imap.ignorebodystructuresize: " + this.ignoreSize);
        }
        int intProperty2 = PropUtil.getIntProperty(properties, "mail." + str + ".statuscachetimeout", 1000);
        this.statusCacheTimeout = intProperty2;
        if (this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config("mail.imap.statuscachetimeout: " + intProperty2);
        }
        int intProperty3 = PropUtil.getIntProperty(properties, "mail." + str + ".appendbuffersize", -1);
        this.appendBufferSize = intProperty3;
        if (this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config("mail.imap.appendbuffersize: " + intProperty3);
        }
        int intProperty4 = PropUtil.getIntProperty(properties, "mail." + str + ".minidletime", 10);
        this.minIdleTime = intProperty4;
        if (this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config("mail.imap.minidletime: " + intProperty4);
        }
        String property2 = session.getProperty("mail." + str + ".proxyauth.user");
        if (property2 != null) {
            this.proxyAuthUser = property2;
            if (this.logger.isLoggable(Level.CONFIG)) {
                this.logger.config("mail.imap.proxyauth.user: " + this.proxyAuthUser);
            }
        }
        boolean booleanProperty = PropUtil.getBooleanProperty(properties, "mail." + str + ".starttls.enable", false);
        this.enableStartTLS = booleanProperty;
        if (booleanProperty) {
            this.logger.config("enable STARTTLS");
        }
        boolean booleanProperty2 = PropUtil.getBooleanProperty(properties, "mail." + str + ".starttls.required", false);
        this.requireStartTLS = booleanProperty2;
        if (booleanProperty2) {
            this.logger.config("require STARTTLS");
        }
        boolean booleanProperty3 = PropUtil.getBooleanProperty(properties, "mail." + str + ".sasl.enable", false);
        this.enableSASL = booleanProperty3;
        if (booleanProperty3) {
            this.logger.config("enable SASL");
        }
        if (this.enableSASL && (property = session.getProperty("mail." + str + ".sasl.mechanisms")) != null && property.length() > 0) {
            if (this.logger.isLoggable(Level.CONFIG)) {
                this.logger.config("SASL mechanisms allowed: " + property);
            }
            ArrayList arrayList = new ArrayList(5);
            StringTokenizer stringTokenizer = new StringTokenizer(property, " ,");
            while (stringTokenizer.hasMoreTokens()) {
                String strNextToken = stringTokenizer.nextToken();
                if (strNextToken.length() > 0) {
                    arrayList.add(strNextToken);
                }
            }
            String[] strArr = new String[arrayList.size()];
            this.saslMechanisms = strArr;
            arrayList.toArray(strArr);
        }
        String property3 = session.getProperty("mail." + str + ".sasl.authorizationid");
        if (property3 != null) {
            this.authorizationID = property3;
            this.logger.log(Level.CONFIG, "mail.imap.sasl.authorizationid: {0}", this.authorizationID);
        }
        String property4 = session.getProperty("mail." + str + ".sasl.realm");
        if (property4 != null) {
            this.saslRealm = property4;
            this.logger.log(Level.CONFIG, "mail.imap.sasl.realm: {0}", this.saslRealm);
        }
        boolean booleanProperty4 = PropUtil.getBooleanProperty(properties, "mail." + str + ".forcepasswordrefresh", false);
        this.forcePasswordRefresh = booleanProperty4;
        if (booleanProperty4) {
            this.logger.config("enable forcePasswordRefresh");
        }
        boolean booleanProperty5 = PropUtil.getBooleanProperty(properties, "mail." + str + ".enableresponseevents", false);
        this.enableResponseEvents = booleanProperty5;
        if (booleanProperty5) {
            this.logger.config("enable IMAP response events");
        }
        boolean booleanProperty6 = PropUtil.getBooleanProperty(properties, "mail." + str + ".enableimapevents", false);
        this.enableImapEvents = booleanProperty6;
        if (booleanProperty6) {
            this.logger.config("enable IMAP IDLE events");
        }
        this.messageCacheDebug = PropUtil.getBooleanProperty(properties, "mail." + str + ".messagecache.debug", false);
        String property5 = session.getProperty("mail." + str + ".yahoo.guid");
        this.guid = property5;
        if (property5 != null) {
            this.logger.log(Level.CONFIG, "mail.imap.yahoo.guid: {0}", this.guid);
        }
        boolean booleanProperty7 = PropUtil.getBooleanProperty(properties, "mail." + str + ".throwsearchexception", false);
        this.throwSearchException = booleanProperty7;
        if (booleanProperty7) {
            this.logger.config("throw SearchException");
        }
        boolean booleanProperty8 = PropUtil.getBooleanProperty(properties, "mail." + str + ".peek", false);
        this.peek = booleanProperty8;
        if (booleanProperty8) {
            this.logger.config("peek");
        }
        boolean booleanProperty9 = PropUtil.getBooleanProperty(properties, "mail." + str + ".closefoldersonstorefailure", true);
        this.closeFoldersOnStoreFailure = booleanProperty9;
        if (booleanProperty9) {
            this.logger.config("closeFoldersOnStoreFailure");
        }
        boolean booleanProperty10 = PropUtil.getBooleanProperty(properties, "mail." + str + ".compress.enable", false);
        this.enableCompress = booleanProperty10;
        if (booleanProperty10) {
            this.logger.config("enable COMPRESS");
        }
        boolean booleanProperty11 = PropUtil.getBooleanProperty(properties, "mail." + str + ".finalizecleanclose", false);
        this.finalizeCleanClose = booleanProperty11;
        if (booleanProperty11) {
            this.logger.config("close connection cleanly in finalize");
        }
        String property6 = session.getProperty("mail." + str + ".folder.class");
        if (property6 != null) {
            this.logger.log(Level.CONFIG, "IMAP: folder class: {0}", property6);
            try {
                try {
                    cls = Class.forName(property6, false, getClass().getClassLoader());
                } catch (ClassNotFoundException unused) {
                    cls = Class.forName(property6);
                }
                this.folderConstructor = cls.getConstructor(String.class, Character.TYPE, IMAPStore.class, Boolean.class);
                this.folderConstructorLI = cls.getConstructor(ListInfo.class, IMAPStore.class);
            } catch (Exception e) {
                this.logger.log(Level.CONFIG, "IMAP: failed to load folder class", (Throwable) e);
            }
        }
        this.pool = new ConnectionPool(str, this.logger, session);
    }

    @Override // javax.mail.Service
    protected synchronized boolean protocolConnect(String str, int i, String str2, String str3) throws MessagingException {
        boolean zIsEmpty;
        if (str == null || str3 == null || str2 == null) {
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.fine("protocolConnect returning false, host=" + str + ", user=" + traceUser(str2) + ", password=" + tracePassword(str3));
            }
            return false;
        }
        if (i != -1) {
            this.port = i;
        } else {
            this.port = PropUtil.getIntProperty(this.session.getProperties(), "mail." + this.name + ".port", this.port);
        }
        if (this.port == -1) {
            this.port = this.defaultPort;
        }
        Protocol protocol = null;
        try {
            try {
                try {
                    synchronized (this.pool) {
                        zIsEmpty = this.pool.authenticatedConnections.isEmpty();
                    }
                    if (zIsEmpty) {
                        if (this.logger.isLoggable(Level.FINE)) {
                            this.logger.fine("trying to connect to host \"" + str + "\", port " + this.port + ", isSSL " + this.isSSL);
                        }
                        IMAPProtocol iMAPProtocolNewIMAPProtocol = newIMAPProtocol(str, this.port);
                        if (this.logger.isLoggable(Level.FINE)) {
                            this.logger.fine("protocolConnect login, host=" + str + ", user=" + traceUser(str2) + ", password=" + tracePassword(str3));
                        }
                        iMAPProtocolNewIMAPProtocol.addResponseHandler(this.nonStoreResponseHandler);
                        login(iMAPProtocolNewIMAPProtocol, str2, str3);
                        iMAPProtocolNewIMAPProtocol.removeResponseHandler(this.nonStoreResponseHandler);
                        iMAPProtocolNewIMAPProtocol.addResponseHandler(this);
                        this.usingSSL = iMAPProtocolNewIMAPProtocol.isSSL();
                        this.host = str;
                        this.user = str2;
                        this.password = str3;
                        synchronized (this.pool) {
                            this.pool.authenticatedConnections.addElement(iMAPProtocolNewIMAPProtocol);
                        }
                    }
                    return true;
                } catch (CommandFailedException e) {
                    if (0 != 0) {
                        protocol.disconnect();
                    }
                    Response response = e.getResponse();
                    throw new AuthenticationFailedException(response != null ? response.getRest() : e.getMessage());
                } catch (IOException e2) {
                    throw new MessagingException(e2.getMessage(), e2);
                }
            } catch (IMAPReferralException e3) {
                if (0 != 0) {
                    protocol.disconnect();
                }
                throw new ReferralException(e3.getUrl(), e3.getMessage());
            } catch (ProtocolException e4) {
                if (0 != 0) {
                    protocol.disconnect();
                }
                throw new MessagingException(e4.getMessage(), e4);
            }
        } catch (SocketConnectException e5) {
            throw new MailConnectException(e5);
        }
    }

    protected IMAPProtocol newIMAPProtocol(String str, int i) throws ProtocolException, IOException {
        return new IMAPProtocol(this.name, str, i, this.session.getProperties(), this.isSSL, this.logger);
    }

    private void login(IMAPProtocol iMAPProtocol, String str, String str2) throws ProtocolException {
        if ((this.enableStartTLS || this.requireStartTLS) && !iMAPProtocol.isSSL()) {
            if (iMAPProtocol.hasCapability("STARTTLS")) {
                iMAPProtocol.startTLS();
                iMAPProtocol.capability();
            } else if (this.requireStartTLS) {
                this.logger.fine("STARTTLS required but not supported by server");
                throw new ProtocolException("STARTTLS required but not supported by server");
            }
        }
        if (iMAPProtocol.isAuthenticated()) {
            return;
        }
        preLogin(iMAPProtocol);
        if (this.guid != null) {
            HashMap map = new HashMap();
            map.put("GUID", this.guid);
            iMAPProtocol.id(map);
        }
        iMAPProtocol.getCapabilities().put("__PRELOGIN__", "");
        String str3 = this.authorizationID;
        if (str3 == null && (str3 = this.proxyAuthUser) == null) {
            str3 = null;
        }
        if (this.enableSASL) {
            try {
                iMAPProtocol.sasllogin(this.saslMechanisms, this.saslRealm, str3, str, str2);
                if (!iMAPProtocol.isAuthenticated()) {
                    throw new CommandFailedException("SASL authentication failed");
                }
            } catch (UnsupportedOperationException unused) {
            }
        }
        if (!iMAPProtocol.isAuthenticated()) {
            authenticate(iMAPProtocol, str3, str, str2);
        }
        String str4 = this.proxyAuthUser;
        if (str4 != null) {
            iMAPProtocol.proxyauth(str4);
        }
        if (iMAPProtocol.hasCapability("__PRELOGIN__")) {
            try {
                iMAPProtocol.capability();
            } catch (ConnectionException e) {
                throw e;
            } catch (ProtocolException unused2) {
            }
        }
        if (this.enableCompress && iMAPProtocol.hasCapability("COMPRESS=DEFLATE")) {
            iMAPProtocol.compress();
        }
        if (iMAPProtocol.hasCapability("UTF8=ACCEPT") || iMAPProtocol.hasCapability("UTF8=ONLY")) {
            iMAPProtocol.enable("UTF8=ACCEPT");
        }
    }

    private void authenticate(IMAPProtocol iMAPProtocol, String str, String str2, String str3) throws ProtocolException {
        String property = this.session.getProperty("mail." + this.name + ".auth.mechanisms");
        if (property == null) {
            property = "PLAIN LOGIN NTLM XOAUTH2";
        }
        StringTokenizer stringTokenizer = new StringTokenizer(property);
        while (stringTokenizer.hasMoreTokens()) {
            String upperCase = stringTokenizer.nextToken().toUpperCase(Locale.ENGLISH);
            if (property == "PLAIN LOGIN NTLM XOAUTH2") {
                String str4 = "mail." + this.name + ".auth." + upperCase.toLowerCase(Locale.ENGLISH) + ".disable";
                if (PropUtil.getBooleanProperty(this.session.getProperties(), str4, upperCase.equals("XOAUTH2"))) {
                    if (this.logger.isLoggable(Level.FINE)) {
                        this.logger.fine("mechanism " + upperCase + " disabled by property: " + str4);
                    }
                }
            }
            if (!iMAPProtocol.hasCapability("AUTH=" + upperCase) && (!upperCase.equals("LOGIN") || !iMAPProtocol.hasCapability("AUTH-LOGIN"))) {
                this.logger.log(Level.FINE, "mechanism {0} not supported by server", upperCase);
            } else {
                if (upperCase.equals("PLAIN")) {
                    iMAPProtocol.authplain(str, str2, str3);
                    return;
                }
                if (upperCase.equals("LOGIN")) {
                    iMAPProtocol.authlogin(str2, str3);
                    return;
                } else if (upperCase.equals("NTLM")) {
                    iMAPProtocol.authntlm(str, str2, str3);
                    return;
                } else {
                    if (upperCase.equals("XOAUTH2")) {
                        iMAPProtocol.authoauth2(str2, str3);
                        return;
                    }
                    this.logger.log(Level.FINE, "no authenticator for mechanism {0}", upperCase);
                }
            }
        }
        if (!iMAPProtocol.hasCapability("LOGINDISABLED")) {
            iMAPProtocol.login(str2, str3);
            return;
        }
        throw new ProtocolException("No login methods supported!");
    }

    public synchronized boolean isSSL() {
        return this.usingSSL;
    }

    public synchronized void setUsername(String str) {
        this.user = str;
    }

    public synchronized void setPassword(String str) {
        this.password = str;
    }

    IMAPProtocol getProtocol(IMAPFolder iMAPFolder) throws MessagingException {
        while (true) {
            IMAPProtocol iMAPProtocolNewIMAPProtocol = null;
            while (iMAPProtocolNewIMAPProtocol == null) {
                synchronized (this.pool) {
                    if (this.pool.authenticatedConnections.isEmpty() || (this.pool.authenticatedConnections.size() == 1 && (this.pool.separateStoreConnection || this.pool.storeConnectionInUse))) {
                        this.logger.fine("no connections in the pool, creating a new one");
                        try {
                            if (this.forcePasswordRefresh) {
                                refreshPassword();
                            }
                            iMAPProtocolNewIMAPProtocol = newIMAPProtocol(this.host, this.port);
                            iMAPProtocolNewIMAPProtocol.addResponseHandler(this.nonStoreResponseHandler);
                            login(iMAPProtocolNewIMAPProtocol, this.user, this.password);
                            iMAPProtocolNewIMAPProtocol.removeResponseHandler(this.nonStoreResponseHandler);
                        } catch (Exception unused) {
                            if (iMAPProtocolNewIMAPProtocol != null) {
                                try {
                                    iMAPProtocolNewIMAPProtocol.disconnect();
                                } catch (Exception unused2) {
                                }
                            }
                            iMAPProtocolNewIMAPProtocol = null;
                        }
                        if (iMAPProtocolNewIMAPProtocol == null) {
                            throw new MessagingException("connection failure");
                        }
                    } else {
                        if (this.logger.isLoggable(Level.FINE)) {
                            this.logger.fine("connection available -- size: " + this.pool.authenticatedConnections.size());
                        }
                        iMAPProtocolNewIMAPProtocol = (IMAPProtocol) this.pool.authenticatedConnections.lastElement();
                        this.pool.authenticatedConnections.removeElement(iMAPProtocolNewIMAPProtocol);
                        if (System.currentTimeMillis() - iMAPProtocolNewIMAPProtocol.getTimestamp() > this.pool.serverTimeoutInterval) {
                            try {
                                try {
                                    iMAPProtocolNewIMAPProtocol.removeResponseHandler(this);
                                    iMAPProtocolNewIMAPProtocol.addResponseHandler(this.nonStoreResponseHandler);
                                    iMAPProtocolNewIMAPProtocol.noop();
                                    iMAPProtocolNewIMAPProtocol.removeResponseHandler(this.nonStoreResponseHandler);
                                    iMAPProtocolNewIMAPProtocol.addResponseHandler(this);
                                } catch (RuntimeException unused3) {
                                }
                            } catch (ProtocolException unused4) {
                                iMAPProtocolNewIMAPProtocol.removeResponseHandler(this.nonStoreResponseHandler);
                                iMAPProtocolNewIMAPProtocol.disconnect();
                            }
                        }
                        String str = this.proxyAuthUser;
                        if (str != null && !str.equals(iMAPProtocolNewIMAPProtocol.getProxyAuthUser()) && iMAPProtocolNewIMAPProtocol.hasCapability("X-UNAUTHENTICATE")) {
                            try {
                                try {
                                    iMAPProtocolNewIMAPProtocol.removeResponseHandler(this);
                                    iMAPProtocolNewIMAPProtocol.addResponseHandler(this.nonStoreResponseHandler);
                                    iMAPProtocolNewIMAPProtocol.unauthenticate();
                                    login(iMAPProtocolNewIMAPProtocol, this.user, this.password);
                                    iMAPProtocolNewIMAPProtocol.removeResponseHandler(this.nonStoreResponseHandler);
                                    iMAPProtocolNewIMAPProtocol.addResponseHandler(this);
                                } catch (ProtocolException unused5) {
                                    iMAPProtocolNewIMAPProtocol.removeResponseHandler(this.nonStoreResponseHandler);
                                    iMAPProtocolNewIMAPProtocol.disconnect();
                                }
                            } catch (RuntimeException unused6) {
                            }
                        }
                        iMAPProtocolNewIMAPProtocol.removeResponseHandler(this);
                    }
                    timeoutConnections();
                    if (iMAPFolder != null) {
                        if (this.pool.folders == null) {
                            this.pool.folders = new Vector();
                        }
                        this.pool.folders.addElement(iMAPFolder);
                    }
                }
            }
            return iMAPProtocolNewIMAPProtocol;
        }
    }

    private IMAPProtocol getStoreProtocol() throws ProtocolException {
        IMAPProtocol iMAPProtocolNewIMAPProtocol = null;
        while (iMAPProtocolNewIMAPProtocol == null) {
            synchronized (this.pool) {
                waitIfIdle();
                if (this.pool.authenticatedConnections.isEmpty()) {
                    this.pool.logger.fine("getStoreProtocol() - no connections in the pool, creating a new one");
                    try {
                        if (this.forcePasswordRefresh) {
                            refreshPassword();
                        }
                        iMAPProtocolNewIMAPProtocol = newIMAPProtocol(this.host, this.port);
                        login(iMAPProtocolNewIMAPProtocol, this.user, this.password);
                    } catch (Exception unused) {
                        if (iMAPProtocolNewIMAPProtocol != null) {
                            try {
                                iMAPProtocolNewIMAPProtocol.logout();
                            } catch (Exception unused2) {
                            }
                        }
                        iMAPProtocolNewIMAPProtocol = null;
                    }
                    if (iMAPProtocolNewIMAPProtocol == null) {
                        throw new ConnectionException("failed to create new store connection");
                    }
                    iMAPProtocolNewIMAPProtocol.addResponseHandler(this);
                    this.pool.authenticatedConnections.addElement(iMAPProtocolNewIMAPProtocol);
                } else {
                    if (this.pool.logger.isLoggable(Level.FINE)) {
                        this.pool.logger.fine("getStoreProtocol() - connection available -- size: " + this.pool.authenticatedConnections.size());
                    }
                    iMAPProtocolNewIMAPProtocol = (IMAPProtocol) this.pool.authenticatedConnections.firstElement();
                    String str = this.proxyAuthUser;
                    if (str != null && !str.equals(iMAPProtocolNewIMAPProtocol.getProxyAuthUser()) && iMAPProtocolNewIMAPProtocol.hasCapability("X-UNAUTHENTICATE")) {
                        iMAPProtocolNewIMAPProtocol.unauthenticate();
                        login(iMAPProtocolNewIMAPProtocol, this.user, this.password);
                    }
                }
                if (!this.pool.storeConnectionInUse) {
                    this.pool.storeConnectionInUse = true;
                    this.pool.logger.fine("getStoreProtocol() -- storeConnectionInUse");
                } else {
                    try {
                        this.pool.wait();
                        iMAPProtocolNewIMAPProtocol = null;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new ProtocolException("Interrupted getStoreProtocol", e);
                    }
                }
                timeoutConnections();
            }
        }
        return iMAPProtocolNewIMAPProtocol;
    }

    IMAPProtocol getFolderStoreProtocol() throws ProtocolException {
        IMAPProtocol storeProtocol = getStoreProtocol();
        storeProtocol.removeResponseHandler(this);
        storeProtocol.addResponseHandler(this.nonStoreResponseHandler);
        return storeProtocol;
    }

    private void refreshPassword() {
        InetAddress byName;
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("refresh password, user: " + traceUser(this.user));
        }
        try {
            byName = InetAddress.getByName(this.host);
        } catch (UnknownHostException unused) {
            byName = null;
        }
        PasswordAuthentication passwordAuthenticationRequestPasswordAuthentication = this.session.requestPasswordAuthentication(byName, this.port, this.name, null, this.user);
        if (passwordAuthenticationRequestPasswordAuthentication != null) {
            this.user = passwordAuthenticationRequestPasswordAuthentication.getUserName();
            this.password = passwordAuthenticationRequestPasswordAuthentication.getPassword();
        }
    }

    boolean allowReadOnlySelect() {
        return PropUtil.getBooleanProperty(this.session.getProperties(), "mail." + this.name + ".allowreadonlyselect", false);
    }

    boolean hasSeparateStoreConnection() {
        return this.pool.separateStoreConnection;
    }

    MailLogger getConnectionPoolLogger() {
        return this.pool.logger;
    }

    boolean getMessageCacheDebug() {
        return this.messageCacheDebug;
    }

    boolean isConnectionPoolFull() {
        boolean z;
        synchronized (this.pool) {
            if (this.pool.logger.isLoggable(Level.FINE)) {
                this.pool.logger.fine("connection pool current size: " + this.pool.authenticatedConnections.size() + "   pool size: " + this.pool.poolSize);
            }
            z = this.pool.authenticatedConnections.size() >= this.pool.poolSize;
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0052 A[Catch: all -> 0x0060, TryCatch #0 {, blocks: (B:5:0x0007, B:7:0x000d, B:9:0x0023, B:10:0x0040, B:11:0x0047, B:12:0x004a, B:14:0x0052, B:15:0x005b, B:16:0x005e), top: B:22:0x0007 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void releaseProtocol(com.sun.mail.imap.IMAPFolder r4, com.sun.mail.imap.protocol.IMAPProtocol r5) {
        /*
            r3 = this;
            java.lang.String r0 = "added an Authenticated connection -- size: "
            com.sun.mail.imap.IMAPStore$ConnectionPool r1 = r3.pool
            monitor-enter(r1)
            if (r5 == 0) goto L4a
            boolean r2 = r3.isConnectionPoolFull()     // Catch: java.lang.Throwable -> L60
            if (r2 != 0) goto L40
            r5.addResponseHandler(r3)     // Catch: java.lang.Throwable -> L60
            com.sun.mail.imap.IMAPStore$ConnectionPool r2 = r3.pool     // Catch: java.lang.Throwable -> L60
            java.util.Vector r2 = com.sun.mail.imap.IMAPStore.ConnectionPool.access$000(r2)     // Catch: java.lang.Throwable -> L60
            r2.addElement(r5)     // Catch: java.lang.Throwable -> L60
            com.sun.mail.util.MailLogger r5 = r3.logger     // Catch: java.lang.Throwable -> L60
            java.util.logging.Level r2 = java.util.logging.Level.FINE     // Catch: java.lang.Throwable -> L60
            boolean r5 = r5.isLoggable(r2)     // Catch: java.lang.Throwable -> L60
            if (r5 == 0) goto L4a
            com.sun.mail.util.MailLogger r5 = r3.logger     // Catch: java.lang.Throwable -> L60
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L60
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L60
            com.sun.mail.imap.IMAPStore$ConnectionPool r0 = r3.pool     // Catch: java.lang.Throwable -> L60
            java.util.Vector r0 = com.sun.mail.imap.IMAPStore.ConnectionPool.access$000(r0)     // Catch: java.lang.Throwable -> L60
            int r0 = r0.size()     // Catch: java.lang.Throwable -> L60
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch: java.lang.Throwable -> L60
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L60
            r5.fine(r0)     // Catch: java.lang.Throwable -> L60
            goto L4a
        L40:
            com.sun.mail.util.MailLogger r0 = r3.logger     // Catch: java.lang.Throwable -> L60
            java.lang.String r2 = "pool is full, not adding an Authenticated connection"
            r0.fine(r2)     // Catch: java.lang.Throwable -> L60
            r5.logout()     // Catch: com.sun.mail.iap.ProtocolException -> L4a java.lang.Throwable -> L60
        L4a:
            com.sun.mail.imap.IMAPStore$ConnectionPool r5 = r3.pool     // Catch: java.lang.Throwable -> L60
            java.util.Vector r5 = com.sun.mail.imap.IMAPStore.ConnectionPool.access$400(r5)     // Catch: java.lang.Throwable -> L60
            if (r5 == 0) goto L5b
            com.sun.mail.imap.IMAPStore$ConnectionPool r5 = r3.pool     // Catch: java.lang.Throwable -> L60
            java.util.Vector r5 = com.sun.mail.imap.IMAPStore.ConnectionPool.access$400(r5)     // Catch: java.lang.Throwable -> L60
            r5.removeElement(r4)     // Catch: java.lang.Throwable -> L60
        L5b:
            r3.timeoutConnections()     // Catch: java.lang.Throwable -> L60
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L60
            return
        L60:
            r4 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L60
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.imap.IMAPStore.releaseProtocol(com.sun.mail.imap.IMAPFolder, com.sun.mail.imap.protocol.IMAPProtocol):void");
    }

    private void releaseStoreProtocol(IMAPProtocol iMAPProtocol) {
        boolean z;
        if (iMAPProtocol == null) {
            cleanup();
            return;
        }
        synchronized (this.connectionFailedLock) {
            z = this.connectionFailed;
            this.connectionFailed = false;
        }
        synchronized (this.pool) {
            this.pool.storeConnectionInUse = false;
            this.pool.notifyAll();
            this.pool.logger.fine("releaseStoreProtocol()");
            timeoutConnections();
        }
        if (z) {
            cleanup();
        }
    }

    void releaseFolderStoreProtocol(IMAPProtocol iMAPProtocol) {
        if (iMAPProtocol == null) {
            return;
        }
        iMAPProtocol.removeResponseHandler(this.nonStoreResponseHandler);
        iMAPProtocol.addResponseHandler(this);
        synchronized (this.pool) {
            this.pool.storeConnectionInUse = false;
            this.pool.notifyAll();
            this.pool.logger.fine("releaseFolderStoreProtocol()");
            timeoutConnections();
        }
    }

    private void emptyConnectionPool(boolean z) {
        synchronized (this.pool) {
            for (int size = this.pool.authenticatedConnections.size() - 1; size >= 0; size--) {
                try {
                    IMAPProtocol iMAPProtocol = (IMAPProtocol) this.pool.authenticatedConnections.elementAt(size);
                    iMAPProtocol.removeResponseHandler(this);
                    if (z) {
                        iMAPProtocol.disconnect();
                    } else {
                        iMAPProtocol.logout();
                    }
                } catch (ProtocolException unused) {
                }
            }
            this.pool.authenticatedConnections.removeAllElements();
        }
        this.pool.logger.fine("removed all authenticated connections from pool");
    }

    private void timeoutConnections() {
        synchronized (this.pool) {
            if (System.currentTimeMillis() - this.pool.lastTimePruned > this.pool.pruningInterval && this.pool.authenticatedConnections.size() > 1) {
                if (this.pool.logger.isLoggable(Level.FINE)) {
                    this.pool.logger.fine("checking for connections to prune: " + (System.currentTimeMillis() - this.pool.lastTimePruned));
                    this.pool.logger.fine("clientTimeoutInterval: " + this.pool.clientTimeoutInterval);
                }
                for (int size = this.pool.authenticatedConnections.size() - 1; size > 0; size--) {
                    IMAPProtocol iMAPProtocol = (IMAPProtocol) this.pool.authenticatedConnections.elementAt(size);
                    if (this.pool.logger.isLoggable(Level.FINE)) {
                        this.pool.logger.fine("protocol last used: " + (System.currentTimeMillis() - iMAPProtocol.getTimestamp()));
                    }
                    if (System.currentTimeMillis() - iMAPProtocol.getTimestamp() > this.pool.clientTimeoutInterval) {
                        this.pool.logger.fine("authenticated connection timed out, logging out the connection");
                        iMAPProtocol.removeResponseHandler(this);
                        this.pool.authenticatedConnections.removeElementAt(size);
                        try {
                            iMAPProtocol.logout();
                        } catch (ProtocolException unused) {
                        }
                    }
                }
                this.pool.lastTimePruned = System.currentTimeMillis();
            }
        }
    }

    int getFetchBlockSize() {
        return this.blksize;
    }

    boolean ignoreBodyStructureSize() {
        return this.ignoreSize;
    }

    Session getSession() {
        return this.session;
    }

    int getStatusCacheTimeout() {
        return this.statusCacheTimeout;
    }

    int getAppendBufferSize() {
        return this.appendBufferSize;
    }

    int getMinIdleTime() {
        return this.minIdleTime;
    }

    boolean throwSearchException() {
        return this.throwSearchException;
    }

    boolean getPeek() {
        return this.peek;
    }

    public synchronized boolean hasCapability(String str) throws MessagingException {
        IMAPProtocol storeProtocol;
        storeProtocol = null;
        try {
            try {
                storeProtocol = getStoreProtocol();
            } catch (ProtocolException e) {
                throw new MessagingException(e.getMessage(), e);
            }
        } finally {
            releaseStoreProtocol(storeProtocol);
        }
        return storeProtocol.hasCapability(str);
    }

    public void setProxyAuthUser(String str) {
        this.proxyAuthUser = str;
    }

    public String getProxyAuthUser() {
        return this.proxyAuthUser;
    }

    @Override // javax.mail.Service
    public synchronized boolean isConnected() {
        if (!super.isConnected()) {
            return false;
        }
        IMAPProtocol storeProtocol = null;
        try {
            storeProtocol = getStoreProtocol();
            storeProtocol.noop();
        } catch (ProtocolException unused) {
        } catch (Throwable th) {
            releaseStoreProtocol(storeProtocol);
            throw th;
        }
        releaseStoreProtocol(storeProtocol);
        return super.isConnected();
    }

    @Override // javax.mail.Service, java.lang.AutoCloseable
    public synchronized void close() throws MessagingException {
        cleanup();
        closeAllFolders(false);
        emptyConnectionPool(false);
    }

    @Override // javax.mail.Service
    protected void finalize() throws Throwable {
        if (!this.finalizeCleanClose) {
            synchronized (this.connectionFailedLock) {
                this.connectionFailed = true;
                this.forceClose = true;
            }
            this.closeFoldersOnStoreFailure = true;
        }
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    private synchronized void cleanup() {
        boolean z;
        if (!super.isConnected()) {
            this.logger.fine("IMAPStore cleanup, not connected");
            return;
        }
        synchronized (this.connectionFailedLock) {
            z = this.forceClose;
            this.forceClose = false;
            this.connectionFailed = false;
        }
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.fine("IMAPStore cleanup, force " + z);
        }
        if (!z || this.closeFoldersOnStoreFailure) {
            closeAllFolders(z);
        }
        emptyConnectionPool(z);
        try {
            super.close();
        } catch (MessagingException unused) {
        }
        this.logger.fine("IMAPStore cleanup done");
    }

    private void closeAllFolders(boolean z) {
        boolean z2;
        Vector vector = null;
        while (true) {
            synchronized (this.pool) {
                if (this.pool.folders != null) {
                    vector = this.pool.folders;
                    this.pool.folders = null;
                    z2 = false;
                } else {
                    z2 = true;
                }
            }
            if (z2) {
                return;
            }
            int size = vector.size();
            for (int i = 0; i < size; i++) {
                IMAPFolder iMAPFolder = (IMAPFolder) vector.get(i);
                if (z) {
                    try {
                        this.logger.fine("force folder to close");
                        iMAPFolder.forceClose();
                    } catch (IllegalStateException | MessagingException unused) {
                    }
                } else {
                    this.logger.fine("close folder");
                    iMAPFolder.close(false);
                }
            }
        }
    }

    @Override // javax.mail.Store
    public synchronized Folder getDefaultFolder() throws MessagingException {
        checkConnected();
        return new DefaultFolder(this);
    }

    @Override // javax.mail.Store
    public synchronized Folder getFolder(String str) throws MessagingException {
        checkConnected();
        return newIMAPFolder(str, CharCompanionObject.MAX_VALUE);
    }

    @Override // javax.mail.Store
    public synchronized Folder getFolder(URLName uRLName) throws MessagingException {
        checkConnected();
        return newIMAPFolder(uRLName.getFile(), CharCompanionObject.MAX_VALUE);
    }

    protected IMAPFolder newIMAPFolder(String str, char c, Boolean bool) {
        IMAPFolder iMAPFolder;
        if (this.folderConstructor != null) {
            try {
                iMAPFolder = (IMAPFolder) this.folderConstructor.newInstance(str, Character.valueOf(c), this, bool);
            } catch (Exception e) {
                this.logger.log(Level.FINE, "exception creating IMAPFolder class", (Throwable) e);
                iMAPFolder = null;
            }
        } else {
            iMAPFolder = null;
        }
        return iMAPFolder == null ? new IMAPFolder(str, c, this, bool) : iMAPFolder;
    }

    protected IMAPFolder newIMAPFolder(String str, char c) {
        return newIMAPFolder(str, c, null);
    }

    protected IMAPFolder newIMAPFolder(ListInfo listInfo) {
        IMAPFolder iMAPFolder;
        if (this.folderConstructorLI != null) {
            try {
                iMAPFolder = (IMAPFolder) this.folderConstructorLI.newInstance(listInfo, this);
            } catch (Exception e) {
                this.logger.log(Level.FINE, "exception creating IMAPFolder class LI", (Throwable) e);
                iMAPFolder = null;
            }
        } else {
            iMAPFolder = null;
        }
        return iMAPFolder == null ? new IMAPFolder(listInfo, this) : iMAPFolder;
    }

    @Override // javax.mail.Store
    public Folder[] getPersonalNamespaces() throws MessagingException {
        Namespaces namespaces = getNamespaces();
        if (namespaces == null || namespaces.personal == null) {
            return super.getPersonalNamespaces();
        }
        return namespaceToFolders(namespaces.personal, null);
    }

    @Override // javax.mail.Store
    public Folder[] getUserNamespaces(String str) throws MessagingException {
        Namespaces namespaces = getNamespaces();
        if (namespaces == null || namespaces.otherUsers == null) {
            return super.getUserNamespaces(str);
        }
        return namespaceToFolders(namespaces.otherUsers, str);
    }

    @Override // javax.mail.Store
    public Folder[] getSharedNamespaces() throws MessagingException {
        Namespaces namespaces = getNamespaces();
        if (namespaces == null || namespaces.shared == null) {
            return super.getSharedNamespaces();
        }
        return namespaceToFolders(namespaces.shared, null);
    }

    private synchronized Namespaces getNamespaces() throws MessagingException {
        checkConnected();
        if (this.namespaces == null) {
            IMAPProtocol storeProtocol = null;
            try {
                try {
                    storeProtocol = getStoreProtocol();
                    this.namespaces = storeProtocol.namespace();
                } catch (BadCommandException unused) {
                } catch (ConnectionException e) {
                    throw new StoreClosedException(this, e.getMessage());
                } catch (ProtocolException e2) {
                    throw new MessagingException(e2.getMessage(), e2);
                }
            } finally {
                releaseStoreProtocol(storeProtocol);
            }
        }
        return this.namespaces;
    }

    private Folder[] namespaceToFolders(Namespaces.Namespace[] namespaceArr, String str) {
        int length = namespaceArr.length;
        Folder[] folderArr = new Folder[length];
        for (int i = 0; i < length; i++) {
            String strSubstring = namespaceArr[i].prefix;
            if (str == null) {
                int length2 = strSubstring.length();
                if (length2 > 0) {
                    int i2 = length2 - 1;
                    if (strSubstring.charAt(i2) == namespaceArr[i].delimiter) {
                        strSubstring = strSubstring.substring(0, i2);
                    }
                }
            } else {
                strSubstring = strSubstring + str;
            }
            folderArr[i] = newIMAPFolder(strSubstring, namespaceArr[i].delimiter, Boolean.valueOf(str == null));
        }
        return folderArr;
    }

    @Override // javax.mail.QuotaAwareStore
    public synchronized Quota[] getQuota(String str) throws MessagingException {
        IMAPProtocol storeProtocol;
        checkConnected();
        storeProtocol = null;
        try {
            try {
                try {
                    try {
                        storeProtocol = getStoreProtocol();
                    } catch (ProtocolException e) {
                        throw new MessagingException(e.getMessage(), e);
                    }
                } catch (ConnectionException e2) {
                    throw new StoreClosedException(this, e2.getMessage());
                }
            } catch (BadCommandException e3) {
                throw new MessagingException("QUOTA not supported", e3);
            }
        } finally {
            releaseStoreProtocol(storeProtocol);
        }
        return storeProtocol.getQuotaRoot(str);
    }

    @Override // javax.mail.QuotaAwareStore
    public synchronized void setQuota(Quota quota) throws MessagingException {
        checkConnected();
        IMAPProtocol storeProtocol = null;
        try {
            try {
                try {
                    try {
                        storeProtocol = getStoreProtocol();
                        storeProtocol.setQuota(quota);
                    } catch (ProtocolException e) {
                        throw new MessagingException(e.getMessage(), e);
                    }
                } catch (ConnectionException e2) {
                    throw new StoreClosedException(this, e2.getMessage());
                }
            } catch (BadCommandException e3) {
                throw new MessagingException("QUOTA not supported", e3);
            }
        } finally {
            releaseStoreProtocol(storeProtocol);
        }
    }

    private void checkConnected() {
        if (!super.isConnected()) {
            throw new IllegalStateException("Not connected");
        }
    }

    @Override // com.sun.mail.iap.ResponseHandler
    public void handleResponse(Response response) {
        if (response.isOK() || response.isNO() || response.isBAD() || response.isBYE()) {
            handleResponseCode(response);
        }
        if (response.isBYE()) {
            this.logger.fine("IMAPStore connection dead");
            synchronized (this.connectionFailedLock) {
                this.connectionFailed = true;
                if (response.isSynthetic()) {
                    this.forceClose = true;
                }
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:55:0x009b
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public void idle() throws javax.mail.MessagingException {
        /*
            Method dump skipped, instruction units count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.imap.IMAPStore.idle():void");
    }

    private void waitIfIdle() throws ProtocolException {
        while (this.pool.idleState != 0) {
            if (this.pool.idleState == 1) {
                this.pool.idleProtocol.idleAbort();
                this.pool.idleState = 2;
            }
            try {
                this.pool.wait();
            } catch (InterruptedException e) {
                throw new ProtocolException("Interrupted waitIfIdle", e);
            }
        }
    }

    public synchronized Map<String, String> id(Map<String, String> map) throws MessagingException {
        IMAPProtocol storeProtocol;
        checkConnected();
        storeProtocol = null;
        try {
            try {
                try {
                    try {
                        storeProtocol = getStoreProtocol();
                    } catch (ProtocolException e) {
                        throw new MessagingException(e.getMessage(), e);
                    }
                } catch (ConnectionException e2) {
                    throw new StoreClosedException(this, e2.getMessage());
                }
            } catch (BadCommandException e3) {
                throw new MessagingException("ID not supported", e3);
            }
        } finally {
            releaseStoreProtocol(storeProtocol);
        }
        return storeProtocol.id(map);
    }

    void handleResponseCode(Response response) {
        if (this.enableResponseEvents) {
            notifyStoreListeners(1000, response.toString());
        }
        String rest = response.getRest();
        boolean z = false;
        if (rest.startsWith("[")) {
            int iIndexOf = rest.indexOf(93);
            if (iIndexOf > 0 && rest.substring(0, iIndexOf + 1).equalsIgnoreCase("[ALERT]")) {
                z = true;
            }
            rest = rest.substring(iIndexOf + 1).trim();
        }
        if (z) {
            notifyStoreListeners(1, rest);
        } else {
            if (!response.isUnTagged() || rest.length() <= 0) {
                return;
            }
            notifyStoreListeners(2, rest);
        }
    }

    private String traceUser(String str) {
        return this.debugusername ? str : "<user name suppressed>";
    }

    private String tracePassword(String str) {
        return this.debugpassword ? str : str == null ? "<null>" : "<non-null>";
    }
}
