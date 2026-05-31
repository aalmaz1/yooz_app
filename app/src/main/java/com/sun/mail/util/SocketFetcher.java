package com.sun.mail.util;

import androidx.webkit.ProxyConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.SocketFactory;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SocketFetcher {
    private static MailLogger logger = new MailLogger(SocketFetcher.class, "socket", "DEBUG SocketFetcher", PropUtil.getBooleanSystemProperty("mail.socket.debug", false), System.out);

    private SocketFetcher() {
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x022a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.net.Socket getSocket(java.lang.String r22, int r23, java.util.Properties r24, java.lang.String r25, boolean r26) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 623
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.SocketFetcher.getSocket(java.lang.String, int, java.util.Properties, java.lang.String, boolean):java.net.Socket");
    }

    public static Socket getSocket(String str, int i, Properties properties, String str2) throws IOException {
        return getSocket(str, i, properties, str2, false);
    }

    private static Socket createSocket(InetAddress inetAddress, int i, String str, int i2, int i3, int i4, Properties properties, String str2, SocketFactory socketFactory, boolean z) throws IOException {
        String property;
        String str3;
        int intProperty;
        String str4;
        Socket socket;
        Socket socketCreateSocket;
        String str5;
        SSLSocketFactory sSLSocketFactory;
        String str6;
        SocketFactory socketFactory2 = socketFactory;
        if (logger.isLoggable(Level.FINEST)) {
            logger.finest("create socket: prefix " + str2 + ", localaddr " + inetAddress + ", localport " + i + ", host " + str + ", port " + i2 + ", connection timeout " + i3 + ", timeout " + i4 + ", socket factory " + socketFactory2 + ", useSSL " + z);
        }
        String property2 = properties.getProperty(str2 + ".proxy.host", null);
        String property3 = properties.getProperty(str2 + ".proxy.user", null);
        String property4 = properties.getProperty(str2 + ".proxy.password", null);
        int i5 = 80;
        int i6 = 1080;
        if (property2 != null) {
            int iIndexOf = property2.indexOf(58);
            if (iIndexOf >= 0) {
                try {
                    i5 = Integer.parseInt(property2.substring(iIndexOf + 1));
                } catch (NumberFormatException unused) {
                }
                property2 = property2.substring(0, iIndexOf);
            }
            int intProperty2 = PropUtil.getIntProperty(properties, str2 + ".proxy.port", i5);
            String str7 = "Using web proxy host, port: " + property2 + ", " + intProperty2;
            if (logger.isLoggable(Level.FINER)) {
                str6 = str7;
                logger.finer("web proxy host " + property2 + ", port " + intProperty2);
                if (property3 != null) {
                    logger.finer("web proxy user " + property3 + ", password " + (property4 == null ? "<null>" : "<non-null>"));
                }
            } else {
                str6 = str7;
            }
            i5 = intProperty2;
            property = null;
            String str8 = str6;
            str3 = property2;
            intProperty = 1080;
            str4 = str8;
        } else {
            property = properties.getProperty(str2 + ".socks.host", null);
            if (property != null) {
                int iIndexOf2 = property.indexOf(58);
                if (iIndexOf2 >= 0) {
                    try {
                        i6 = Integer.parseInt(property.substring(iIndexOf2 + 1));
                    } catch (NumberFormatException unused2) {
                    }
                    property = property.substring(0, iIndexOf2);
                }
                str3 = property2;
                intProperty = PropUtil.getIntProperty(properties, str2 + ".socks.port", i6);
                String str9 = "Using SOCKS host, port: " + property + ", " + intProperty;
                if (logger.isLoggable(Level.FINER)) {
                    str4 = str9;
                    logger.finer("socks host " + property + ", port " + intProperty);
                } else {
                    str4 = str9;
                }
            } else {
                str3 = property2;
                intProperty = 1080;
                str4 = null;
            }
        }
        Socket socketCreateSocket2 = (socketFactory2 == null || (socketFactory2 instanceof SSLSocketFactory)) ? null : socketFactory.createSocket();
        if (socketCreateSocket2 != null) {
            socket = socketCreateSocket2;
        } else if (property != null) {
            socket = new Socket(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(property, intProperty)));
        } else {
            if (PropUtil.getBooleanProperty(properties, str2 + ".usesocketchannels", false)) {
                logger.finer("using SocketChannels");
                socketCreateSocket2 = SocketChannel.open().socket();
            } else {
                socketCreateSocket2 = new Socket();
            }
            socket = socketCreateSocket2;
        }
        if (i4 >= 0) {
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest("set socket read timeout " + i4);
            }
            socket.setSoTimeout(i4);
        }
        int intProperty3 = PropUtil.getIntProperty(properties, str2 + ".writetimeout", -1);
        if (intProperty3 != -1) {
            if (logger.isLoggable(Level.FINEST)) {
                logger.finest("set socket write timeout " + intProperty3);
            }
            socketCreateSocket = new WriteTimeoutSocket(socket, intProperty3);
        } else {
            socketCreateSocket = socket;
        }
        if (inetAddress != null) {
            socketCreateSocket.bind(new InetSocketAddress(inetAddress, i));
        }
        try {
            logger.finest("connecting...");
            try {
                if (str3 != null) {
                    proxyConnect(socketCreateSocket, str3, i5, property3, property4, str, i2, i3);
                    str5 = str;
                } else if (i3 >= 0) {
                    str5 = str;
                    socketCreateSocket.connect(new InetSocketAddress(str5, i2), i3);
                } else {
                    str5 = str;
                    socketCreateSocket.connect(new InetSocketAddress(str5, i2));
                }
                logger.finest("success!");
                if ((z || (socketFactory2 instanceof SSLSocketFactory)) && !(socketCreateSocket instanceof SSLSocket)) {
                    String property5 = properties.getProperty(str2 + ".ssl.trust");
                    if (property5 != null) {
                        try {
                            MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
                            if (property5.equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
                                mailSSLSocketFactory.setTrustAllHosts(true);
                            } else {
                                mailSSLSocketFactory.setTrustedHosts(property5.split("\\s+"));
                            }
                            sSLSocketFactory = mailSSLSocketFactory;
                        } catch (GeneralSecurityException e) {
                            IOException iOException = new IOException("Can't create MailSSLSocketFactory");
                            iOException.initCause(e);
                            throw iOException;
                        }
                    } else if (socketFactory2 instanceof SSLSocketFactory) {
                        sSLSocketFactory = (SSLSocketFactory) socketFactory2;
                    } else {
                        sSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                    }
                    socketCreateSocket = sSLSocketFactory.createSocket(socketCreateSocket, str5, i2, true);
                    socketFactory2 = sSLSocketFactory;
                }
                configureSSLSocket(socketCreateSocket, str5, properties, str2, socketFactory2);
                return socketCreateSocket;
            } catch (IOException e2) {
                e = e2;
                IOException iOException2 = e;
                logger.log(Level.FINEST, "connection failed", (Throwable) iOException2);
                throw new SocketConnectException(str4, iOException2, str, i2, i3);
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    private static SocketFactory getSocketFactory(String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException {
        Class<?> cls = null;
        if (str == null || str.length() == 0) {
            return null;
        }
        ClassLoader contextClassLoader = getContextClassLoader();
        if (contextClassLoader != null) {
            try {
                cls = Class.forName(str, false, contextClassLoader);
            } catch (ClassNotFoundException unused) {
            }
        }
        if (cls == null) {
            cls = Class.forName(str);
        }
        return (SocketFactory) cls.getMethod("getDefault", new Class[0]).invoke(new Object(), new Object[0]);
    }

    @Deprecated
    public static Socket startTLS(Socket socket) throws IOException {
        return startTLS(socket, new Properties(), "socket");
    }

    @Deprecated
    public static Socket startTLS(Socket socket, Properties properties, String str) throws IOException {
        return startTLS(socket, socket.getInetAddress().getHostName(), properties, str);
    }

    public static Socket startTLS(Socket socket, String str, Properties properties, String str2) throws IOException {
        SocketFactory socketFactory;
        String str3;
        SSLSocketFactory sSLSocketFactory;
        int port = socket.getPort();
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("startTLS host " + str + ", port " + port);
        }
        String str4 = "unknown socket factory";
        try {
            Object obj = properties.get(str2 + ".ssl.socketFactory");
            SSLSocketFactory sSLSocketFactory2 = null;
            sSLSocketFactory2 = null;
            if (obj instanceof SocketFactory) {
                socketFactory = (SocketFactory) obj;
                str4 = "SSL socket factory instance " + socketFactory;
            } else {
                socketFactory = null;
            }
            if (socketFactory == null) {
                String property = properties.getProperty(str2 + ".ssl.socketFactory.class");
                socketFactory = getSocketFactory(property);
                str4 = "SSL socket factory class " + property;
            }
            if (socketFactory != null && (socketFactory instanceof SSLSocketFactory)) {
                sSLSocketFactory2 = (SSLSocketFactory) socketFactory;
            }
            if (sSLSocketFactory2 == null) {
                Object obj2 = properties.get(str2 + ".socketFactory");
                if (obj2 instanceof SocketFactory) {
                    socketFactory = (SocketFactory) obj2;
                    str4 = "socket factory instance " + socketFactory;
                }
                if (socketFactory == null) {
                    String property2 = properties.getProperty(str2 + ".socketFactory.class");
                    socketFactory = getSocketFactory(property2);
                    str4 = "socket factory class " + property2;
                }
                if (socketFactory != null && (socketFactory instanceof SSLSocketFactory)) {
                    sSLSocketFactory2 = (SSLSocketFactory) socketFactory;
                }
            }
            SSLSocketFactory sSLSocketFactory3 = sSLSocketFactory2;
            if (sSLSocketFactory2 == null) {
                String property3 = properties.getProperty(str2 + ".ssl.trust");
                if (property3 != null) {
                    try {
                        MailSSLSocketFactory mailSSLSocketFactory = new MailSSLSocketFactory();
                        if (property3.equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
                            mailSSLSocketFactory.setTrustAllHosts(true);
                        } else {
                            mailSSLSocketFactory.setTrustedHosts(property3.split("\\s+"));
                        }
                        str3 = "mail SSL socket factory";
                        sSLSocketFactory = mailSSLSocketFactory;
                    } catch (GeneralSecurityException e) {
                        IOException iOException = new IOException("Can't create MailSSLSocketFactory");
                        iOException.initCause(e);
                        throw iOException;
                    }
                } else {
                    str3 = "default SSL socket factory";
                    sSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                }
                sSLSocketFactory3 = sSLSocketFactory;
            }
            Socket socketCreateSocket = sSLSocketFactory3.createSocket(socket, str, port, true);
            configureSSLSocket(socketCreateSocket, str, properties, str2, sSLSocketFactory3);
            return socketCreateSocket;
        } catch (Exception e2) {
            e = e2;
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                if (targetException instanceof Exception) {
                    e = (Exception) targetException;
                }
            }
            if (e instanceof IOException) {
                throw ((IOException) e);
            }
            IOException iOException2 = new IOException("Exception in startTLS using " + str4 + ": host, port: " + str + ", " + port + "; Exception: " + e);
            iOException2.initCause(e);
            throw iOException2;
        }
    }

    private static void configureSSLSocket(Socket socket, String str, Properties properties, String str2, SocketFactory socketFactory) throws IOException {
        if (socket instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket) socket;
            String property = properties.getProperty(str2 + ".ssl.protocols", null);
            if (property != null) {
                sSLSocket.setEnabledProtocols(stringArray(property));
            } else {
                String[] enabledProtocols = sSLSocket.getEnabledProtocols();
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("SSL enabled protocols before " + Arrays.asList(enabledProtocols));
                }
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < enabledProtocols.length; i++) {
                    String str3 = enabledProtocols[i];
                    if (str3 != null && !str3.startsWith("SSL")) {
                        arrayList.add(enabledProtocols[i]);
                    }
                }
                sSLSocket.setEnabledProtocols((String[]) arrayList.toArray(new String[arrayList.size()]));
            }
            String property2 = properties.getProperty(str2 + ".ssl.ciphersuites", null);
            if (property2 != null) {
                sSLSocket.setEnabledCipherSuites(stringArray(property2));
            }
            if (logger.isLoggable(Level.FINER)) {
                logger.finer("SSL enabled protocols after " + Arrays.asList(sSLSocket.getEnabledProtocols()));
                logger.finer("SSL enabled ciphers after " + Arrays.asList(sSLSocket.getEnabledCipherSuites()));
            }
            sSLSocket.startHandshake();
            if (PropUtil.getBooleanProperty(properties, str2 + ".ssl.checkserveridentity", false)) {
                checkServerIdentity(str, sSLSocket);
            }
            if ((socketFactory instanceof MailSSLSocketFactory) && !((MailSSLSocketFactory) socketFactory).isServerTrusted(str, sSLSocket)) {
                throw cleanupAndThrow(sSLSocket, new IOException("Server is not trusted: " + str));
            }
        }
    }

    private static IOException cleanupAndThrow(Socket socket, IOException iOException) {
        try {
            socket.close();
        } catch (Throwable th) {
            if (isRecoverable(th)) {
                iOException.addSuppressed(th);
            } else {
                th.addSuppressed(iOException);
                if (th instanceof Error) {
                    throw ((Error) th);
                }
                if (th instanceof RuntimeException) {
                    throw ((RuntimeException) th);
                }
                throw new RuntimeException("unexpected exception", th);
            }
        }
        return iOException;
    }

    private static boolean isRecoverable(Throwable th) {
        return (th instanceof Exception) || (th instanceof LinkageError);
    }

    private static void checkServerIdentity(String str, SSLSocket sSLSocket) throws IOException {
        try {
            Certificate[] peerCertificates = sSLSocket.getSession().getPeerCertificates();
            if (peerCertificates != null && peerCertificates.length > 0) {
                Certificate certificate = peerCertificates[0];
                if (certificate instanceof X509Certificate) {
                    if (matchCert(str, (X509Certificate) certificate)) {
                        return;
                    }
                }
            }
            sSLSocket.close();
            throw new IOException("Can't verify identity of server: " + str);
        } catch (SSLPeerUnverifiedException e) {
            sSLSocket.close();
            IOException iOException = new IOException("Can't verify identity of server: " + str);
            iOException.initCause(e);
            throw iOException;
        }
    }

    private static boolean matchCert(String str, X509Certificate x509Certificate) {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("matchCert server " + str + ", cert " + x509Certificate);
        }
        try {
            Class<?> cls = Class.forName("sun.security.util.HostnameChecker");
            Object objInvoke = cls.getMethod("getInstance", Byte.TYPE).invoke(new Object(), (byte) 2);
            if (logger.isLoggable(Level.FINER)) {
                logger.finer("using sun.security.util.HostnameChecker");
            }
            try {
                cls.getMethod("match", String.class, X509Certificate.class).invoke(objInvoke, str, x509Certificate);
                return true;
            } catch (InvocationTargetException e) {
                logger.log(Level.FINER, "HostnameChecker FAIL", (Throwable) e);
                return false;
            }
        } catch (Exception e2) {
            logger.log(Level.FINER, "NO sun.security.util.HostnameChecker", (Throwable) e2);
            try {
                Collection<List<?>> subjectAlternativeNames = x509Certificate.getSubjectAlternativeNames();
                if (subjectAlternativeNames != null) {
                    boolean z = false;
                    for (List<?> list : subjectAlternativeNames) {
                        if (((Integer) list.get(0)).intValue() == 2) {
                            String str2 = (String) list.get(1);
                            if (logger.isLoggable(Level.FINER)) {
                                logger.finer("found name: " + str2);
                            }
                            if (matchServer(str, str2)) {
                                return true;
                            }
                            z = true;
                        }
                    }
                    if (z) {
                        return false;
                    }
                }
            } catch (CertificateParsingException unused) {
            }
            Matcher matcher = Pattern.compile("CN=([^,]*)").matcher(x509Certificate.getSubjectX500Principal().getName());
            return matcher.find() && matchServer(str, matcher.group(1).trim());
        }
    }

    private static boolean matchServer(String str, String str2) {
        int length;
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("match server " + str + " with " + str2);
        }
        if (str2.startsWith("*.")) {
            String strSubstring = str2.substring(2);
            return strSubstring.length() != 0 && (length = str.length() - strSubstring.length()) >= 1 && str.charAt(length + (-1)) == '.' && str.regionMatches(true, length, strSubstring, 0, strSubstring.length());
        }
        return str.equalsIgnoreCase(str2);
    }

    private static void proxyConnect(Socket socket, String str, int i, String str2, String str3, String str4, int i2, int i3) throws IOException {
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("connecting through proxy " + str + ":" + i + " to " + str4 + ":" + i2);
        }
        if (i3 >= 0) {
            socket.connect(new InetSocketAddress(str, i), i3);
        } else {
            socket.connect(new InetSocketAddress(str, i));
        }
        PrintStream printStream = new PrintStream(socket.getOutputStream(), false, StandardCharsets.UTF_8.name());
        StringBuilder sb = new StringBuilder("CONNECT ");
        sb.append(str4).append(":").append(i2).append(" HTTP/1.1\r\nHost: ");
        sb.append(str4).append(":").append(i2).append(IOUtils.LINE_SEPARATOR_WINDOWS);
        if (str2 != null && str3 != null) {
            sb.append("Proxy-Authorization: Basic ").append(new String(BASE64EncoderStream.encode((str2 + ':' + str3).getBytes(StandardCharsets.UTF_8)), StandardCharsets.US_ASCII)).append(IOUtils.LINE_SEPARATOR_WINDOWS);
        }
        sb.append("Proxy-Connection: keep-alive\r\n\r\n");
        printStream.print(sb.toString());
        printStream.flush();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        boolean z = true;
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null || line.length() == 0) {
                return;
            }
            logger.finest(line);
            if (z) {
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                stringTokenizer.nextToken();
                if (!stringTokenizer.nextToken().equals("200")) {
                    try {
                        socket.close();
                    } catch (IOException unused) {
                    }
                    ConnectException connectException = new ConnectException("connection through proxy " + str + ":" + i + " to " + str4 + ":" + i2 + " failed: " + line);
                    logger.log(Level.FINE, "connect failed", (Throwable) connectException);
                    throw connectException;
                }
                z = false;
            }
        }
    }

    private static String[] stringArray(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private static ClassLoader getContextClassLoader() {
        return (ClassLoader) AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() { // from class: com.sun.mail.util.SocketFetcher.1
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
}
