package com.sun.mail.pop3;

import com.sun.mail.util.LineInputStream;
import com.sun.mail.util.MailLogger;
import com.sun.mail.util.PropUtil;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import javax.net.ssl.SSLSocket;
import kotlin.UByte;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
class Protocol {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String CRLF = "\r\n";
    private static final int POP3_PORT = 110;
    private static final int SLOP = 128;
    private static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private String apopChallenge;
    private Map<String, String> capabilities = null;
    private String host;
    private BufferedReader input;
    private MailLogger logger;
    private boolean noauthdebug;
    private PrintWriter output;
    private boolean pipelining;
    private String prefix;
    private Properties props;
    private Socket socket;
    private TraceInputStream traceInput;
    private MailLogger traceLogger;
    private TraceOutputStream traceOutput;
    private boolean traceSuspended;

    private void batchCommandContinue(String str) {
    }

    private void batchCommandEnd() {
    }

    private void batchCommandStart(String str) {
    }

    private void multilineCommandEnd() {
    }

    private void multilineCommandStart(String str) {
    }

    private void simpleCommandEnd() {
    }

    private void simpleCommandStart(String str) {
    }

    Protocol(String str, int i, MailLogger mailLogger, Properties properties, String str2, boolean z) throws IOException {
        this.apopChallenge = null;
        boolean z2 = true;
        this.noauthdebug = true;
        this.host = str;
        this.props = properties;
        this.prefix = str2;
        this.logger = mailLogger;
        this.traceLogger = mailLogger.getSubLogger("protocol", null);
        this.noauthdebug = !PropUtil.getBooleanProperty(properties, "mail.debug.auth", false);
        boolean boolProp = getBoolProp(properties, str2 + ".apop.enable");
        boolean boolProp2 = getBoolProp(properties, str2 + ".disablecapa");
        i = i == -1 ? 110 : i;
        try {
            if (mailLogger.isLoggable(Level.FINE)) {
                mailLogger.fine("connecting to host \"" + str + "\", port " + i + ", isSSL " + z);
            }
            this.socket = SocketFetcher.getSocket(str, i, properties, str2, z);
            initStreams();
            Response responseSimpleCommand = simpleCommand(null);
            if (!responseSimpleCommand.ok) {
                throw cleanupAndThrow(this.socket, new IOException("Connect failed"));
            }
            if (boolProp && responseSimpleCommand.data != null) {
                int iIndexOf = responseSimpleCommand.data.indexOf(60);
                int iIndexOf2 = responseSimpleCommand.data.indexOf(62, iIndexOf);
                if (iIndexOf != -1 && iIndexOf2 != -1) {
                    this.apopChallenge = responseSimpleCommand.data.substring(iIndexOf, iIndexOf2 + 1);
                }
                mailLogger.log(Level.FINE, "APOP challenge: {0}", this.apopChallenge);
            }
            if (!boolProp2) {
                setCapabilities(capa());
            }
            if (!hasCapability("PIPELINING") && !PropUtil.getBooleanProperty(properties, str2 + ".pipelining", false)) {
                z2 = false;
            }
            this.pipelining = z2;
            if (z2) {
                mailLogger.config("PIPELINING enabled");
            }
        } catch (IOException e) {
            throw cleanupAndThrow(this.socket, e);
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

    private final synchronized boolean getBoolProp(Properties properties, String str) {
        boolean booleanProperty;
        booleanProperty = PropUtil.getBooleanProperty(properties, str, false);
        if (this.logger.isLoggable(Level.CONFIG)) {
            this.logger.config(str + ": " + booleanProperty);
        }
        return booleanProperty;
    }

    private void initStreams() throws IOException {
        boolean booleanProperty = PropUtil.getBooleanProperty(this.props, "mail.debug.quote", false);
        TraceInputStream traceInputStream = new TraceInputStream(this.socket.getInputStream(), this.traceLogger);
        this.traceInput = traceInputStream;
        traceInputStream.setQuote(booleanProperty);
        TraceOutputStream traceOutputStream = new TraceOutputStream(this.socket.getOutputStream(), this.traceLogger);
        this.traceOutput = traceOutputStream;
        traceOutputStream.setQuote(booleanProperty);
        this.input = new BufferedReader(new InputStreamReader(this.traceInput, "iso-8859-1"));
        this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.traceOutput, "iso-8859-1")));
    }

    protected void finalize() throws Throwable {
        try {
            if (this.socket != null) {
                quit();
            }
        } finally {
            super.finalize();
        }
    }

    synchronized void setCapabilities(InputStream inputStream) {
        BufferedReader bufferedReader = null;
        if (inputStream == null) {
            this.capabilities = null;
            return;
        }
        this.capabilities = new HashMap(10);
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "us-ascii"));
        } catch (UnsupportedEncodingException unused) {
        }
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    int iIndexOf = line.indexOf(32);
                    this.capabilities.put((iIndexOf > 0 ? line.substring(0, iIndexOf) : line).toUpperCase(Locale.ENGLISH), line);
                }
            } catch (IOException unused2) {
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException unused3) {
                }
                throw th;
            }
            try {
                break;
            } catch (IOException unused4) {
            }
        }
        inputStream.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    synchronized boolean hasCapability(java.lang.String r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.util.Map<java.lang.String, java.lang.String> r0 = r2.capabilities     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto L13
            java.util.Locale r1 = java.util.Locale.ENGLISH     // Catch: java.lang.Throwable -> L16
            java.lang.String r3 = r3.toUpperCase(r1)     // Catch: java.lang.Throwable -> L16
            boolean r3 = r0.containsKey(r3)     // Catch: java.lang.Throwable -> L16
            if (r3 == 0) goto L13
            r3 = 1
            goto L14
        L13:
            r3 = 0
        L14:
            monitor-exit(r2)
            return r3
        L16:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.pop3.Protocol.hasCapability(java.lang.String):boolean");
    }

    synchronized Map<String, String> getCapabilities() {
        return this.capabilities;
    }

    synchronized String login(String str, String str2) throws IOException {
        Response responseSimpleCommand;
        boolean z = this.pipelining && (this.socket instanceof SSLSocket);
        try {
            if (this.noauthdebug && isTracing()) {
                this.logger.fine("authentication command trace suppressed");
                suspendTracing();
            }
            String digest = this.apopChallenge != null ? getDigest(str2) : null;
            if (this.apopChallenge != null && digest != null) {
                responseSimpleCommand = simpleCommand("APOP " + str + StringUtils.SPACE + digest);
            } else if (z) {
                String str3 = "USER " + str;
                batchCommandStart(str3);
                issueCommand(str3);
                String str4 = "PASS " + str2;
                batchCommandContinue(str4);
                issueCommand(str4);
                Response response = readResponse();
                if (!response.ok) {
                    String str5 = response.data != null ? response.data : "USER command failed";
                    readResponse();
                    batchCommandEnd();
                    return str5;
                }
                responseSimpleCommand = readResponse();
                batchCommandEnd();
            } else {
                Response responseSimpleCommand2 = simpleCommand("USER " + str);
                if (!responseSimpleCommand2.ok) {
                    return responseSimpleCommand2.data != null ? responseSimpleCommand2.data : "USER command failed";
                }
                responseSimpleCommand = simpleCommand("PASS " + str2);
            }
            if (this.noauthdebug && isTracing()) {
                this.logger.log(Level.FINE, "authentication command {0}", responseSimpleCommand.ok ? "succeeded" : "failed");
            }
            if (responseSimpleCommand.ok) {
                return null;
            }
            return responseSimpleCommand.data != null ? responseSimpleCommand.data : "login failed";
        } finally {
            resumeTracing();
        }
    }

    private String getDigest(String str) {
        try {
            return toHex(MessageDigest.getInstance("MD5").digest((this.apopChallenge + str).getBytes("iso-8859-1")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            return null;
        }
    }

    private static String toHex(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = b & UByte.MAX_VALUE;
            int i3 = i + 1;
            char[] cArr2 = digits;
            cArr[i] = cArr2[i2 >> 4];
            i = i3 + 1;
            cArr[i3] = cArr2[i2 & 15];
        }
        return new String(cArr);
    }

    synchronized boolean quit() throws IOException {
        try {
        } finally {
            close();
        }
        return simpleCommand("QUIT").ok;
    }

    void close() {
        try {
            this.socket.close();
        } catch (IOException unused) {
        } catch (Throwable th) {
            this.socket = null;
            this.input = null;
            this.output = null;
            throw th;
        }
        this.socket = null;
        this.input = null;
        this.output = null;
    }

    synchronized Status stat() throws IOException {
        Status status;
        Response responseSimpleCommand = simpleCommand("STAT");
        status = new Status();
        if (!responseSimpleCommand.ok) {
            throw new IOException("STAT command failed: " + responseSimpleCommand.data);
        }
        if (responseSimpleCommand.data != null) {
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(responseSimpleCommand.data);
                status.total = Integer.parseInt(stringTokenizer.nextToken());
                status.size = Integer.parseInt(stringTokenizer.nextToken());
            } catch (RuntimeException unused) {
            }
        }
        return status;
    }

    synchronized int list(int i) throws IOException {
        int i2;
        Response responseSimpleCommand = simpleCommand("LIST " + i);
        if (!responseSimpleCommand.ok || responseSimpleCommand.data == null) {
            i2 = -1;
        } else {
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(responseSimpleCommand.data);
                stringTokenizer.nextToken();
                i2 = Integer.parseInt(stringTokenizer.nextToken());
            } catch (RuntimeException unused) {
                i2 = -1;
            }
        }
        return i2;
    }

    synchronized InputStream list() throws IOException {
        return multilineCommand("LIST", 128).bytes;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    synchronized java.io.InputStream retr(int r10, int r11) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 315
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.pop3.Protocol.retr(int, int):java.io.InputStream");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0040, code lost:
    
        r2 = r5.input.read();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    synchronized boolean retr(int r6, java.io.OutputStream r7) throws java.io.IOException {
        /*
            r5 = this;
            java.lang.String r0 = "RETR "
            monitor-enter(r5)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8c
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L8c
            java.lang.StringBuilder r6 = r1.append(r6)     // Catch: java.lang.Throwable -> L8c
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L8c
            r5.multilineCommandStart(r6)     // Catch: java.lang.Throwable -> L8c
            r5.issueCommand(r6)     // Catch: java.lang.Throwable -> L8c
            com.sun.mail.pop3.Response r6 = r5.readResponse()     // Catch: java.lang.Throwable -> L8c
            boolean r6 = r6.ok     // Catch: java.lang.Throwable -> L8c
            if (r6 != 0) goto L24
            r5.multilineCommandEnd()     // Catch: java.lang.Throwable -> L8c
            monitor-exit(r5)
            r6 = 0
            return r6
        L24:
            r6 = 10
            r0 = 0
            r1 = r6
        L28:
            java.io.BufferedReader r2 = r5.input     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            int r2 = r2.read()     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            if (r2 < 0) goto L64
            if (r1 != r6) goto L47
            r1 = 46
            if (r2 != r1) goto L47
            java.io.BufferedReader r1 = r5.input     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            int r1 = r1.read()     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            r2 = 13
            if (r1 != r2) goto L48
            java.io.BufferedReader r6 = r5.input     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            int r2 = r6.read()     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            goto L64
        L47:
            r1 = r2
        L48:
            if (r0 != 0) goto L28
            r7.write(r1)     // Catch: java.lang.RuntimeException -> L4e java.io.IOException -> L59 java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            goto L28
        L4e:
            r0 = move-exception
            com.sun.mail.util.MailLogger r2 = r5.logger     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            java.util.logging.Level r3 = java.util.logging.Level.FINE     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            java.lang.String r4 = "exception while streaming"
            r2.log(r3, r4, r0)     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            goto L28
        L59:
            r0 = move-exception
            com.sun.mail.util.MailLogger r2 = r5.logger     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            java.util.logging.Level r3 = java.util.logging.Level.FINE     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            java.lang.String r4 = "exception while streaming"
            r2.log(r3, r4, r0)     // Catch: java.io.InterruptedIOException -> L85 java.lang.Throwable -> L8c
            goto L28
        L64:
            if (r2 < 0) goto L7d
            if (r0 == 0) goto L77
            boolean r6 = r0 instanceof java.io.IOException     // Catch: java.lang.Throwable -> L8c
            if (r6 != 0) goto L74
            boolean r6 = r0 instanceof java.lang.RuntimeException     // Catch: java.lang.Throwable -> L8c
            if (r6 != 0) goto L71
            goto L77
        L71:
            java.lang.RuntimeException r0 = (java.lang.RuntimeException) r0     // Catch: java.lang.Throwable -> L8c
            throw r0     // Catch: java.lang.Throwable -> L8c
        L74:
            java.io.IOException r0 = (java.io.IOException) r0     // Catch: java.lang.Throwable -> L8c
            throw r0     // Catch: java.lang.Throwable -> L8c
        L77:
            r5.multilineCommandEnd()     // Catch: java.lang.Throwable -> L8c
            monitor-exit(r5)
            r6 = 1
            return r6
        L7d:
            java.io.EOFException r6 = new java.io.EOFException     // Catch: java.lang.Throwable -> L8c
            java.lang.String r7 = "EOF on socket"
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L8c
            throw r6     // Catch: java.lang.Throwable -> L8c
        L85:
            r6 = move-exception
            java.net.Socket r7 = r5.socket     // Catch: java.io.IOException -> L8b java.lang.Throwable -> L8c
            r7.close()     // Catch: java.io.IOException -> L8b java.lang.Throwable -> L8c
        L8b:
            throw r6     // Catch: java.lang.Throwable -> L8c
        L8c:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.pop3.Protocol.retr(int, java.io.OutputStream):boolean");
    }

    synchronized InputStream top(int i, int i2) throws IOException {
        return multilineCommand("TOP " + i + StringUtils.SPACE + i2, 0).bytes;
    }

    synchronized boolean dele(int i) throws IOException {
        return simpleCommand("DELE " + i).ok;
    }

    synchronized String uidl(int i) throws IOException {
        Response responseSimpleCommand = simpleCommand("UIDL " + i);
        if (!responseSimpleCommand.ok) {
            return null;
        }
        int iIndexOf = responseSimpleCommand.data.indexOf(32);
        if (iIndexOf <= 0) {
            return null;
        }
        return responseSimpleCommand.data.substring(iIndexOf + 1);
    }

    synchronized boolean uidl(String[] strArr) throws IOException {
        int i;
        Response responseMultilineCommand = multilineCommand("UIDL", strArr.length * 15);
        if (!responseMultilineCommand.ok) {
            return false;
        }
        LineInputStream lineInputStream = new LineInputStream(responseMultilineCommand.bytes);
        while (true) {
            String line = lineInputStream.readLine();
            if (line != null) {
                int iIndexOf = line.indexOf(32);
                if (iIndexOf >= 1 && iIndexOf < line.length() && (i = Integer.parseInt(line.substring(0, iIndexOf))) > 0 && i <= strArr.length) {
                    strArr[i - 1] = line.substring(iIndexOf + 1);
                }
            } else {
                try {
                    break;
                } catch (IOException unused) {
                }
            }
        }
        responseMultilineCommand.bytes.close();
        return true;
    }

    synchronized boolean noop() throws IOException {
        return simpleCommand("NOOP").ok;
    }

    synchronized boolean rset() throws IOException {
        return simpleCommand("RSET").ok;
    }

    synchronized boolean stls() throws IOException {
        if (this.socket instanceof SSLSocket) {
            return true;
        }
        Response responseSimpleCommand = simpleCommand("STLS");
        if (responseSimpleCommand.ok) {
            try {
                this.socket = SocketFetcher.startTLS(this.socket, this.host, this.props, this.prefix);
                initStreams();
            } catch (IOException e) {
                try {
                    this.socket.close();
                    this.socket = null;
                    this.input = null;
                    this.output = null;
                    IOException iOException = new IOException("Could not convert socket to TLS");
                    iOException.initCause(e);
                    throw iOException;
                } catch (Throwable th) {
                    this.socket = null;
                    this.input = null;
                    this.output = null;
                    throw th;
                }
            }
        }
        return responseSimpleCommand.ok;
    }

    synchronized boolean isSSL() {
        return this.socket instanceof SSLSocket;
    }

    synchronized InputStream capa() throws IOException {
        Response responseMultilineCommand = multilineCommand("CAPA", 128);
        if (!responseMultilineCommand.ok) {
            return null;
        }
        return responseMultilineCommand.bytes;
    }

    private Response simpleCommand(String str) throws IOException {
        simpleCommandStart(str);
        issueCommand(str);
        Response response = readResponse();
        simpleCommandEnd();
        return response;
    }

    private void issueCommand(String str) throws IOException {
        if (this.socket == null) {
            throw new IOException("Folder is closed");
        }
        if (str != null) {
            this.output.print(str + "\r\n");
            this.output.flush();
        }
    }

    private Response readResponse() throws IOException {
        try {
            String line = this.input.readLine();
            if (line == null) {
                this.traceLogger.finest("<EOF>");
                throw new EOFException("EOF on socket");
            }
            Response response = new Response();
            if (line.startsWith("+OK")) {
                response.ok = true;
            } else if (line.startsWith("-ERR")) {
                response.ok = false;
            } else {
                throw new IOException("Unexpected response: " + line);
            }
            int iIndexOf = line.indexOf(32);
            if (iIndexOf >= 0) {
                response.data = line.substring(iIndexOf + 1);
            }
            return response;
        } catch (InterruptedIOException e) {
            try {
                this.socket.close();
            } catch (IOException unused) {
            }
            throw new EOFException(e.getMessage());
        } catch (SocketException e2) {
            try {
                this.socket.close();
            } catch (IOException unused2) {
            }
            throw new EOFException(e2.getMessage());
        }
    }

    private Response multilineCommand(String str, int i) throws IOException {
        multilineCommandStart(str);
        issueCommand(str);
        Response response = readResponse();
        if (!response.ok) {
            multilineCommandEnd();
            return response;
        }
        response.bytes = readMultilineResponse(i);
        multilineCommandEnd();
        return response;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0020, code lost:
    
        r2 = r3.input.read();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.io.InputStream readMultilineResponse(int r4) throws java.io.IOException {
        /*
            r3 = this;
            com.sun.mail.util.SharedByteArrayOutputStream r0 = new com.sun.mail.util.SharedByteArrayOutputStream
            r0.<init>(r4)
            r4 = 10
            r1 = r4
        L8:
            java.io.BufferedReader r2 = r3.input     // Catch: java.io.InterruptedIOException -> L3b
            int r2 = r2.read()     // Catch: java.io.InterruptedIOException -> L3b
            if (r2 < 0) goto L2c
            if (r1 != r4) goto L27
            r1 = 46
            if (r2 != r1) goto L27
            java.io.BufferedReader r1 = r3.input     // Catch: java.io.InterruptedIOException -> L3b
            int r1 = r1.read()     // Catch: java.io.InterruptedIOException -> L3b
            r2 = 13
            if (r1 != r2) goto L28
            java.io.BufferedReader r4 = r3.input     // Catch: java.io.InterruptedIOException -> L3b
            int r2 = r4.read()     // Catch: java.io.InterruptedIOException -> L3b
            goto L2c
        L27:
            r1 = r2
        L28:
            r0.write(r1)     // Catch: java.io.InterruptedIOException -> L3b
            goto L8
        L2c:
            if (r2 < 0) goto L33
            java.io.InputStream r4 = r0.toStream()
            return r4
        L33:
            java.io.EOFException r4 = new java.io.EOFException
            java.lang.String r0 = "EOF on socket"
            r4.<init>(r0)
            throw r4
        L3b:
            r4 = move-exception
            java.net.Socket r0 = r3.socket     // Catch: java.io.IOException -> L41
            r0.close()     // Catch: java.io.IOException -> L41
        L41:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.pop3.Protocol.readMultilineResponse(int):java.io.InputStream");
    }

    protected boolean isTracing() {
        return this.traceLogger.isLoggable(Level.FINEST);
    }

    private void suspendTracing() {
        if (this.traceLogger.isLoggable(Level.FINEST)) {
            this.traceInput.setTrace(false);
            this.traceOutput.setTrace(false);
        }
    }

    private void resumeTracing() {
        if (this.traceLogger.isLoggable(Level.FINEST)) {
            this.traceInput.setTrace(true);
            this.traceOutput.setTrace(true);
        }
    }
}
