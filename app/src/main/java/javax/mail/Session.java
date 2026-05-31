package javax.mail;

import androidx.core.app.NotificationCompat;
import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.MailLogger;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import javax.mail.Provider;

/* JADX INFO: loaded from: classes3.dex */
public final class Session {
    private static final String confDir;
    private static Session defaultSession;
    private final Authenticator authenticator;
    private boolean debug;
    private boolean loadedProviders;
    private MailLogger logger;
    private PrintStream out;
    private final Properties props;
    private List<Provider> providers;
    private final EventQueue q;
    private final Hashtable<URLName, PasswordAuthentication> authTable = new Hashtable<>();
    private final Map<String, Provider> providersByProtocol = new HashMap();
    private final Map<String, Provider> providersByClassName = new HashMap();
    private final Properties addressMap = new Properties();

    static {
        String str;
        try {
            str = (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: javax.mail.Session.1
                @Override // java.security.PrivilegedAction
                public String run() {
                    String property = System.getProperty("java.home");
                    String str2 = property + File.separator + "conf";
                    if (new File(str2).exists()) {
                        return str2 + File.separator;
                    }
                    return property + File.separator + "lib" + File.separator;
                }
            });
        } catch (Exception unused) {
            str = null;
        }
        confDir = str;
    }

    private Session(Properties properties, Authenticator authenticator) throws Throwable {
        Class<?> cls;
        this.debug = false;
        this.props = properties;
        this.authenticator = authenticator;
        if (Boolean.valueOf(properties.getProperty("mail.debug")).booleanValue()) {
            this.debug = true;
        }
        initLogger();
        this.logger.log(Level.CONFIG, "JavaMail version {0}", Version.version);
        if (authenticator != null) {
            cls = authenticator.getClass();
        } else {
            cls = getClass();
        }
        loadAddressMap(cls);
        this.q = new EventQueue((Executor) properties.get("mail.event.executor"));
    }

    private final synchronized void initLogger() {
        this.logger = new MailLogger(getClass(), "DEBUG", this.debug, getDebugOut());
    }

    public static Session getInstance(Properties properties, Authenticator authenticator) {
        return new Session(properties, authenticator);
    }

    public static Session getInstance(Properties properties) {
        return new Session(properties, null);
    }

    public static synchronized Session getDefaultInstance(Properties properties, Authenticator authenticator) {
        Session session = defaultSession;
        if (session == null) {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkSetFactory();
            }
            defaultSession = new Session(properties, authenticator);
        } else {
            Authenticator authenticator2 = session.authenticator;
            if (authenticator2 != authenticator && (authenticator2 == null || authenticator == null || authenticator2.getClass().getClassLoader() != authenticator.getClass().getClassLoader())) {
                throw new SecurityException("Access to default session denied");
            }
        }
        return defaultSession;
    }

    public static Session getDefaultInstance(Properties properties) {
        return getDefaultInstance(properties, null);
    }

    public synchronized void setDebug(boolean z) {
        this.debug = z;
        initLogger();
        this.logger.log(Level.CONFIG, "setDebug: JavaMail version {0}", Version.version);
    }

    public synchronized boolean getDebug() {
        return this.debug;
    }

    public synchronized void setDebugOut(PrintStream printStream) {
        this.out = printStream;
        initLogger();
    }

    public synchronized PrintStream getDebugOut() {
        PrintStream printStream = this.out;
        if (printStream != null) {
            return printStream;
        }
        return System.out;
    }

    public synchronized Provider[] getProviders() {
        Provider[] providerArr;
        ArrayList arrayList = new ArrayList();
        Iterator it = ServiceLoader.load(Provider.class).iterator();
        boolean z = true;
        while (it.hasNext()) {
            arrayList.add((Provider) it.next());
            z = false;
        }
        if (!this.loadedProviders) {
            loadProviders(z);
        }
        List<Provider> list = this.providers;
        if (list != null) {
            arrayList.addAll(list);
        }
        providerArr = new Provider[arrayList.size()];
        arrayList.toArray(providerArr);
        return providerArr;
    }

    public synchronized Provider getProvider(String str) throws NoSuchProviderException {
        Provider providerByProtocol;
        if (str != null) {
            if (str.length() > 0) {
                String property = this.props.getProperty("mail." + str + ".class");
                if (property != null) {
                    if (this.logger.isLoggable(Level.FINE)) {
                        this.logger.fine("mail." + str + ".class property exists and points to " + property);
                    }
                    providerByProtocol = getProviderByClassName(property);
                } else {
                    providerByProtocol = null;
                }
                if (providerByProtocol == null) {
                    providerByProtocol = getProviderByProtocol(str);
                }
                if (providerByProtocol == null) {
                    throw new NoSuchProviderException("No provider for " + str);
                }
                if (this.logger.isLoggable(Level.FINE)) {
                    this.logger.fine("getProvider() returning " + providerByProtocol.toString());
                }
            }
        }
        throw new NoSuchProviderException("Invalid protocol: null");
        return providerByProtocol;
    }

    public synchronized void setProvider(Provider provider) throws NoSuchProviderException {
        if (provider == null) {
            throw new NoSuchProviderException("Can't set null provider");
        }
        this.providersByProtocol.put(provider.getProtocol(), provider);
        this.providersByClassName.put(provider.getClassName(), provider);
        this.props.put("mail." + provider.getProtocol() + ".class", provider.getClassName());
    }

    public Store getStore() throws NoSuchProviderException {
        return getStore(getProperty("mail.store.protocol"));
    }

    public Store getStore(String str) throws NoSuchProviderException {
        return getStore(new URLName(str, null, -1, null, null, null));
    }

    public Store getStore(URLName uRLName) throws NoSuchProviderException {
        return getStore(getProvider(uRLName.getProtocol()), uRLName);
    }

    public Store getStore(Provider provider) throws NoSuchProviderException {
        return getStore(provider, null);
    }

    private Store getStore(Provider provider, URLName uRLName) throws NoSuchProviderException {
        if (provider == null || provider.getType() != Provider.Type.STORE) {
            throw new NoSuchProviderException("invalid provider");
        }
        return (Store) getService(provider, uRLName, Store.class);
    }

    public Folder getFolder(URLName uRLName) throws MessagingException {
        Store store = getStore(uRLName);
        store.connect();
        return store.getFolder(uRLName);
    }

    public Transport getTransport() throws NoSuchProviderException {
        String property = getProperty("mail.transport.protocol");
        if (property != null) {
            return getTransport(property);
        }
        String str = (String) this.addressMap.get("rfc822");
        if (str != null) {
            return getTransport(str);
        }
        return getTransport("smtp");
    }

    public Transport getTransport(String str) throws NoSuchProviderException {
        return getTransport(new URLName(str, null, -1, null, null, null));
    }

    public Transport getTransport(URLName uRLName) throws NoSuchProviderException {
        return getTransport(getProvider(uRLName.getProtocol()), uRLName);
    }

    public Transport getTransport(Provider provider) throws NoSuchProviderException {
        return getTransport(provider, null);
    }

    public Transport getTransport(Address address) throws NoSuchProviderException {
        String property = getProperty("mail.transport.protocol." + address.getType());
        if (property != null) {
            return getTransport(property);
        }
        String str = (String) this.addressMap.get(address.getType());
        if (str != null) {
            return getTransport(str);
        }
        throw new NoSuchProviderException("No provider for Address type: " + address.getType());
    }

    private Transport getTransport(Provider provider, URLName uRLName) throws NoSuchProviderException {
        if (provider == null || provider.getType() != Provider.Type.TRANSPORT) {
            throw new NoSuchProviderException("invalid provider");
        }
        return (Transport) getService(provider, uRLName, Transport.class);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0045 A[Catch: Exception -> 0x0077, TryCatch #1 {Exception -> 0x0077, blocks: (B:11:0x002d, B:13:0x0033, B:17:0x003f, B:20:0x004d, B:23:0x0054, B:24:0x0076, B:19:0x0045), top: B:42:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0054 A[Catch: Exception -> 0x0077, TryCatch #1 {Exception -> 0x0077, blocks: (B:11:0x002d, B:13:0x0033, B:17:0x003f, B:20:0x004d, B:23:0x0054, B:24:0x0076, B:19:0x0045), top: B:42:0x002d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <T extends javax.mail.Service> T getService(javax.mail.Provider r10, javax.mail.URLName r11, java.lang.Class<T> r12) throws javax.mail.NoSuchProviderException {
        /*
            Method dump skipped, instruction units count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.Session.getService(javax.mail.Provider, javax.mail.URLName, java.lang.Class):javax.mail.Service");
    }

    public void setPasswordAuthentication(URLName uRLName, PasswordAuthentication passwordAuthentication) {
        if (passwordAuthentication == null) {
            this.authTable.remove(uRLName);
        } else {
            this.authTable.put(uRLName, passwordAuthentication);
        }
    }

    public PasswordAuthentication getPasswordAuthentication(URLName uRLName) {
        return this.authTable.get(uRLName);
    }

    public PasswordAuthentication requestPasswordAuthentication(InetAddress inetAddress, int i, String str, String str2, String str3) {
        Authenticator authenticator = this.authenticator;
        if (authenticator != null) {
            return authenticator.requestPasswordAuthentication(inetAddress, i, str, str2, str3);
        }
        return null;
    }

    public Properties getProperties() {
        return this.props;
    }

    public String getProperty(String str) {
        return this.props.getProperty(str);
    }

    private Provider getProviderByClassName(String str) throws Throwable {
        Provider provider = this.providersByClassName.get(str);
        if (provider != null) {
            return provider;
        }
        for (Provider provider2 : ServiceLoader.load(Provider.class)) {
            if (str.equals(provider2.getClassName())) {
                return provider2;
            }
        }
        if (this.loadedProviders) {
            return provider;
        }
        loadProviders(true);
        return this.providersByClassName.get(str);
    }

    private Provider getProviderByProtocol(String str) throws Throwable {
        Provider provider = this.providersByProtocol.get(str);
        if (provider != null) {
            return provider;
        }
        for (Provider provider2 : ServiceLoader.load(Provider.class)) {
            if (str.equals(provider2.getProtocol())) {
                return provider2;
            }
        }
        if (this.loadedProviders) {
            return provider;
        }
        loadProviders(true);
        return this.providersByProtocol.get(str);
    }

    private void loadProviders(boolean z) throws Throwable {
        Class<?> cls;
        StreamLoader streamLoader = new StreamLoader() { // from class: javax.mail.Session.2
            @Override // javax.mail.StreamLoader
            public void load(InputStream inputStream) throws IOException {
                Session.this.loadProvidersFromStream(inputStream);
            }
        };
        try {
            String str = confDir;
            if (str != null) {
                loadFile(str + "javamail.providers", streamLoader);
            }
        } catch (SecurityException unused) {
        }
        Authenticator authenticator = this.authenticator;
        if (authenticator != null) {
            cls = authenticator.getClass();
        } else {
            cls = getClass();
        }
        loadAllResources("META-INF/javamail.providers", cls, streamLoader);
        loadResource("/META-INF/javamail.default.providers", cls, streamLoader, false);
        List<Provider> list = this.providers;
        if ((list == null || list.size() == 0) && z) {
            this.logger.config("failed to load any providers, using defaults");
            addProvider(new Provider(Provider.Type.STORE, "imap", "com.sun.mail.imap.IMAPStore", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.STORE, "imaps", "com.sun.mail.imap.IMAPSSLStore", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.STORE, "pop3", "com.sun.mail.pop3.POP3Store", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.STORE, "pop3s", "com.sun.mail.pop3.POP3SSLStore", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.TRANSPORT, "smtp", "com.sun.mail.smtp.SMTPTransport", "Oracle", Version.version));
            addProvider(new Provider(Provider.Type.TRANSPORT, "smtps", "com.sun.mail.smtp.SMTPSSLTransport", "Oracle", Version.version));
        }
        if (this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config("Tables of loaded providers from javamail.providers");
            this.logger.config("Providers Listed By Class Name: " + this.providersByClassName.toString());
            this.logger.config("Providers Listed By Protocol: " + this.providersByProtocol.toString());
        }
        this.loadedProviders = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadProvidersFromStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        LineInputStream lineInputStream = new LineInputStream(inputStream);
        while (true) {
            String line = lineInputStream.readLine();
            if (line == null) {
                return;
            }
            if (!line.startsWith("#") && line.trim().length() != 0) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, ";");
                Provider.Type type = null;
                String strSubstring = null;
                String strSubstring2 = null;
                String strSubstring3 = null;
                String strSubstring4 = null;
                while (stringTokenizer.hasMoreTokens()) {
                    String strTrim = stringTokenizer.nextToken().trim();
                    int iIndexOf = strTrim.indexOf("=");
                    if (strTrim.startsWith("protocol=")) {
                        strSubstring = strTrim.substring(iIndexOf + 1);
                    } else if (strTrim.startsWith("type=")) {
                        String strSubstring5 = strTrim.substring(iIndexOf + 1);
                        if (strSubstring5.equalsIgnoreCase("store")) {
                            type = Provider.Type.STORE;
                        } else if (strSubstring5.equalsIgnoreCase(NotificationCompat.CATEGORY_TRANSPORT)) {
                            type = Provider.Type.TRANSPORT;
                        }
                    } else if (strTrim.startsWith("class=")) {
                        strSubstring2 = strTrim.substring(iIndexOf + 1);
                    } else if (strTrim.startsWith("vendor=")) {
                        strSubstring3 = strTrim.substring(iIndexOf + 1);
                    } else if (strTrim.startsWith("version=")) {
                        strSubstring4 = strTrim.substring(iIndexOf + 1);
                    }
                }
                if (type == null || strSubstring == null || strSubstring2 == null || strSubstring.length() <= 0 || strSubstring2.length() <= 0) {
                    this.logger.log(Level.CONFIG, "Bad provider entry: {0}", line);
                } else {
                    addProvider(new Provider(type, strSubstring, strSubstring2, strSubstring3, strSubstring4));
                }
            }
        }
    }

    public synchronized void addProvider(Provider provider) {
        if (this.providers == null) {
            this.providers = new ArrayList();
        }
        this.providers.add(provider);
        this.providersByClassName.put(provider.getClassName(), provider);
        if (!this.providersByProtocol.containsKey(provider.getProtocol())) {
            this.providersByProtocol.put(provider.getProtocol(), provider);
        }
    }

    private void loadAddressMap(Class<?> cls) throws Throwable {
        StreamLoader streamLoader = new StreamLoader() { // from class: javax.mail.Session.3
            @Override // javax.mail.StreamLoader
            public void load(InputStream inputStream) throws IOException {
                Session.this.addressMap.load(inputStream);
            }
        };
        loadResource("/META-INF/javamail.default.address.map", cls, streamLoader, true);
        loadAllResources("META-INF/javamail.address.map", cls, streamLoader);
        try {
            String str = confDir;
            if (str != null) {
                loadFile(str + "javamail.address.map", streamLoader);
            }
        } catch (SecurityException unused) {
        }
        if (this.addressMap.isEmpty()) {
            this.logger.config("failed to load address map, using defaults");
            this.addressMap.put("rfc822", "smtp");
        }
    }

    public synchronized void setProtocolForAddress(String str, String str2) {
        if (str2 == null) {
            this.addressMap.remove(str);
        } else {
            this.addressMap.put(str, str2);
        }
    }

    private void loadFile(String str, StreamLoader streamLoader) throws Throwable {
        BufferedInputStream bufferedInputStream = null;
        try {
            try {
                try {
                    BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(str));
                    try {
                        streamLoader.load(bufferedInputStream2);
                        this.logger.log(Level.CONFIG, "successfully loaded file: {0}", str);
                        bufferedInputStream2.close();
                    } catch (FileNotFoundException unused) {
                        bufferedInputStream = bufferedInputStream2;
                        if (bufferedInputStream == null) {
                            return;
                        }
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e = e;
                        bufferedInputStream = bufferedInputStream2;
                        if (this.logger.isLoggable(Level.CONFIG)) {
                            this.logger.log(Level.CONFIG, "not loading file: " + str, (Throwable) e);
                        }
                        if (bufferedInputStream == null) {
                            return;
                        }
                        bufferedInputStream.close();
                    } catch (SecurityException e2) {
                        e = e2;
                        bufferedInputStream = bufferedInputStream2;
                        if (this.logger.isLoggable(Level.CONFIG)) {
                            this.logger.log(Level.CONFIG, "not loading file: " + str, (Throwable) e);
                        }
                        if (bufferedInputStream == null) {
                            return;
                        }
                        bufferedInputStream.close();
                    } catch (Throwable th) {
                        th = th;
                        bufferedInputStream = bufferedInputStream2;
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException unused2) {
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException unused3) {
                } catch (IOException e3) {
                    e = e3;
                } catch (SecurityException e4) {
                    e = e4;
                }
            } catch (IOException unused4) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private void loadResource(String str, Class<?> cls, StreamLoader streamLoader, boolean z) {
        InputStream resourceAsStream = null;
        try {
            try {
                resourceAsStream = getResourceAsStream(cls, str);
                if (resourceAsStream != null) {
                    streamLoader.load(resourceAsStream);
                    this.logger.log(Level.CONFIG, "successfully loaded resource: {0}", str);
                } else if (z) {
                    this.logger.log(Level.WARNING, "expected resource not found: {0}", str);
                }
                if (resourceAsStream == null) {
                    return;
                }
            } catch (IOException e) {
                this.logger.log(Level.CONFIG, "Exception loading resource", (Throwable) e);
                if (0 == 0) {
                    return;
                }
            } catch (SecurityException e2) {
                this.logger.log(Level.CONFIG, "Exception loading resource", (Throwable) e2);
                if (0 == 0) {
                    return;
                }
            }
            try {
                resourceAsStream.close();
            } catch (IOException unused) {
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    resourceAsStream.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadAllResources(java.lang.String r11, java.lang.Class<?> r12, javax.mail.StreamLoader r13) {
        /*
            r10 = this;
            java.lang.String r0 = "Exception loading resource"
            r1 = 0
            java.lang.ClassLoader r2 = getContextClassLoader()     // Catch: java.lang.Exception -> L76
            if (r2 != 0) goto Ld
            java.lang.ClassLoader r2 = r12.getClassLoader()     // Catch: java.lang.Exception -> L76
        Ld:
            if (r2 == 0) goto L14
            java.net.URL[] r2 = getResources(r2, r11)     // Catch: java.lang.Exception -> L76
            goto L18
        L14:
            java.net.URL[] r2 = getSystemResources(r11)     // Catch: java.lang.Exception -> L76
        L18:
            if (r2 == 0) goto L74
            r3 = r1
            r4 = r3
        L1c:
            int r5 = r2.length     // Catch: java.lang.Exception -> L72
            if (r3 >= r5) goto L7f
            r5 = r2[r3]     // Catch: java.lang.Exception -> L72
            com.sun.mail.util.MailLogger r6 = r10.logger     // Catch: java.lang.Exception -> L72
            java.util.logging.Level r7 = java.util.logging.Level.CONFIG     // Catch: java.lang.Exception -> L72
            java.lang.String r8 = "URL {0}"
            r6.log(r7, r8, r5)     // Catch: java.lang.Exception -> L72
            r6 = 0
            java.io.InputStream r6 = openStream(r5)     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
            if (r6 == 0) goto L3f
            r13.load(r6)     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
            r4 = 1
            com.sun.mail.util.MailLogger r7 = r10.logger     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
            java.lang.String r9 = "successfully loaded resource: {0}"
            r7.log(r8, r9, r5)     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
            goto L48
        L3f:
            com.sun.mail.util.MailLogger r7 = r10.logger     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
            java.lang.String r9 = "not loading resource: {0}"
            r7.log(r8, r9, r5)     // Catch: java.lang.Throwable -> L4e java.lang.SecurityException -> L50 java.io.IOException -> L5b java.io.FileNotFoundException -> L6c
        L48:
            if (r6 == 0) goto L6f
        L4a:
            r6.close()     // Catch: java.io.IOException -> L6f java.lang.Exception -> L72
            goto L6f
        L4e:
            r2 = move-exception
            goto L66
        L50:
            r5 = move-exception
            com.sun.mail.util.MailLogger r7 = r10.logger     // Catch: java.lang.Throwable -> L4e
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L4e
            r7.log(r8, r0, r5)     // Catch: java.lang.Throwable -> L4e
            if (r6 == 0) goto L6f
            goto L4a
        L5b:
            r5 = move-exception
            com.sun.mail.util.MailLogger r7 = r10.logger     // Catch: java.lang.Throwable -> L4e
            java.util.logging.Level r8 = java.util.logging.Level.CONFIG     // Catch: java.lang.Throwable -> L4e
            r7.log(r8, r0, r5)     // Catch: java.lang.Throwable -> L4e
            if (r6 == 0) goto L6f
            goto L4a
        L66:
            if (r6 == 0) goto L6b
            r6.close()     // Catch: java.io.IOException -> L6b java.lang.Exception -> L72
        L6b:
            throw r2     // Catch: java.lang.Exception -> L72
        L6c:
            if (r6 == 0) goto L6f
            goto L4a
        L6f:
            int r3 = r3 + 1
            goto L1c
        L72:
            r2 = move-exception
            goto L78
        L74:
            r4 = r1
            goto L7f
        L76:
            r2 = move-exception
            r4 = r1
        L78:
            com.sun.mail.util.MailLogger r3 = r10.logger
            java.util.logging.Level r5 = java.util.logging.Level.CONFIG
            r3.log(r5, r0, r2)
        L7f:
            if (r4 != 0) goto L93
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "/"
            r0.<init>(r2)
            java.lang.StringBuilder r11 = r0.append(r11)
            java.lang.String r11 = r11.toString()
            r10.loadResource(r11, r12, r13, r1)
        L93:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.Session.loadAllResources(java.lang.String, java.lang.Class, javax.mail.StreamLoader):void");
    }

    static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: javax.mail.Session.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.security.PrivilegedAction
            public ClassLoader run() {
                try {
                    return Thread.currentThread().getContextClassLoader();
                } catch (SecurityException unused) {
                    return null;
                }
            }
        });
    }

    private static InputStream getResourceAsStream(final Class<?> cls, final String str) throws IOException {
        try {
            return (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() { // from class: javax.mail.Session.5
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.security.PrivilegedExceptionAction
                public InputStream run() throws IOException {
                    try {
                        return cls.getResourceAsStream(str);
                    } catch (RuntimeException e) {
                        IOException iOException = new IOException("ClassLoader.getResourceAsStream failed");
                        iOException.initCause(e);
                        throw iOException;
                    }
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    private static URL[] getResources(final ClassLoader classLoader, final String str) {
        return (URL[]) AccessController.doPrivileged(new PrivilegedAction<URL[]>() { // from class: javax.mail.Session.6
            @Override // java.security.PrivilegedAction
            public URL[] run() {
                URL[] urlArr = null;
                try {
                    ArrayList list = Collections.list(classLoader.getResources(str));
                    if (list.isEmpty()) {
                        return null;
                    }
                    urlArr = new URL[list.size()];
                    list.toArray(urlArr);
                    return urlArr;
                } catch (IOException | SecurityException unused) {
                    return urlArr;
                }
            }
        });
    }

    private static URL[] getSystemResources(final String str) {
        return (URL[]) AccessController.doPrivileged(new PrivilegedAction<URL[]>() { // from class: javax.mail.Session.7
            @Override // java.security.PrivilegedAction
            public URL[] run() {
                URL[] urlArr = null;
                try {
                    ArrayList list = Collections.list(ClassLoader.getSystemResources(str));
                    if (list.isEmpty()) {
                        return null;
                    }
                    urlArr = new URL[list.size()];
                    list.toArray(urlArr);
                    return urlArr;
                } catch (IOException | SecurityException unused) {
                    return urlArr;
                }
            }
        });
    }

    private static InputStream openStream(final URL url) throws IOException {
        try {
            return (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>() { // from class: javax.mail.Session.8
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // java.security.PrivilegedExceptionAction
                public InputStream run() throws IOException {
                    return url.openStream();
                }
            });
        } catch (PrivilegedActionException e) {
            throw ((IOException) e.getException());
        }
    }

    EventQueue getEventQueue() {
        return this.q;
    }
}
