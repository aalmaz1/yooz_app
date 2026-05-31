package com.sun.mail.iap;

import androidx.exifinterface.media.ExifInterface;
import com.sun.mail.util.MailLogger;
import com.sun.mail.util.PropUtil;
import com.sun.mail.util.SocketFetcher;
import com.sun.mail.util.TraceInputStream;
import com.sun.mail.util.TraceOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.net.ssl.SSLSocket;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class Protocol {
    private final List<ResponseHandler> handlers;
    protected String host;
    private volatile ResponseInputStream input;
    private String localHostName;
    protected MailLogger logger;
    private volatile DataOutputStream output;
    protected String prefix;
    protected Properties props;
    protected boolean quote;
    private Socket socket;
    private int tagCounter;
    private final String tagPrefix;
    private volatile long timestamp;
    private TraceInputStream traceInput;
    protected MailLogger traceLogger;
    private TraceOutputStream traceOutput;
    static final AtomicInteger tagNum = new AtomicInteger();
    private static final byte[] CRLF = {13, 10};

    private void commandEnd() {
    }

    private void commandStart(String str) {
    }

    protected ByteArray getResponseBuffer() {
        return null;
    }

    public boolean supportsUtf8() {
        return false;
    }

    public Protocol(String str, int i, Properties properties, String str2, boolean z, MailLogger mailLogger) throws ProtocolException, IOException {
        this.tagCounter = 0;
        this.handlers = new CopyOnWriteArrayList();
        this.tagPrefix = computePrefix(properties, str2);
        try {
            this.host = str;
            this.props = properties;
            this.prefix = str2;
            this.logger = mailLogger;
            this.traceLogger = mailLogger.getSubLogger("protocol", null);
            this.socket = SocketFetcher.getSocket(str, i, properties, str2, z);
            this.quote = PropUtil.getBooleanProperty(properties, "mail.debug.quote", false);
            initStreams();
            processGreeting(readResponse());
            this.timestamp = System.currentTimeMillis();
        } catch (Throwable th) {
            disconnect();
            throw th;
        }
    }

    private void initStreams() throws IOException {
        TraceInputStream traceInputStream = new TraceInputStream(this.socket.getInputStream(), this.traceLogger);
        this.traceInput = traceInputStream;
        traceInputStream.setQuote(this.quote);
        this.input = new ResponseInputStream(this.traceInput);
        TraceOutputStream traceOutputStream = new TraceOutputStream(this.socket.getOutputStream(), this.traceLogger);
        this.traceOutput = traceOutputStream;
        traceOutputStream.setQuote(this.quote);
        this.output = new DataOutputStream(new BufferedOutputStream(this.traceOutput));
    }

    private String computePrefix(Properties properties, String str) {
        String str2;
        if (PropUtil.getBooleanProperty(properties, str + ".reusetagprefix", false)) {
            return ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
        }
        int andIncrement = tagNum.getAndIncrement() % 18278;
        if (andIncrement < 26) {
            return new String(new char[]{(char) (andIncrement + 65)});
        }
        if (andIncrement < 702) {
            int i = andIncrement - 26;
            str2 = new String(new char[]{(char) ((i / 26) + 65), (char) ((i % 26) + 65)});
        } else {
            int i2 = andIncrement - 702;
            str2 = new String(new char[]{(char) ((i2 / 676) + 65), (char) (((i2 % 676) / 26) + 65), (char) ((i2 % 26) + 65)});
        }
        return str2;
    }

    public Protocol(InputStream inputStream, PrintStream printStream, Properties properties, boolean z) throws IOException {
        this.tagCounter = 0;
        this.handlers = new CopyOnWriteArrayList();
        this.host = "localhost";
        this.props = properties;
        this.quote = false;
        this.tagPrefix = computePrefix(properties, "mail.imap");
        MailLogger mailLogger = new MailLogger(getClass(), "DEBUG", z, System.out);
        this.logger = mailLogger;
        this.traceLogger = mailLogger.getSubLogger("protocol", null);
        TraceInputStream traceInputStream = new TraceInputStream(inputStream, this.traceLogger);
        this.traceInput = traceInputStream;
        traceInputStream.setQuote(this.quote);
        this.input = new ResponseInputStream(this.traceInput);
        TraceOutputStream traceOutputStream = new TraceOutputStream(printStream, this.traceLogger);
        this.traceOutput = traceOutputStream;
        traceOutputStream.setQuote(this.quote);
        this.output = new DataOutputStream(new BufferedOutputStream(this.traceOutput));
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void addResponseHandler(ResponseHandler responseHandler) {
        this.handlers.add(responseHandler);
    }

    public void removeResponseHandler(ResponseHandler responseHandler) {
        this.handlers.remove(responseHandler);
    }

    public void notifyResponseHandlers(Response[] responseArr) {
        if (this.handlers.isEmpty()) {
            return;
        }
        for (Response response : responseArr) {
            if (response != null) {
                for (ResponseHandler responseHandler : this.handlers) {
                    if (responseHandler != null) {
                        responseHandler.handleResponse(response);
                    }
                }
            }
        }
    }

    protected void processGreeting(Response response) throws ProtocolException {
        if (response.isBYE()) {
            throw new ConnectionException(this, response);
        }
    }

    protected ResponseInputStream getInputStream() {
        return this.input;
    }

    protected OutputStream getOutputStream() {
        return this.output;
    }

    protected synchronized boolean supportsNonSyncLiterals() {
        return false;
    }

    public Response readResponse() throws ProtocolException, IOException {
        return new Response(this);
    }

    public boolean hasResponse() {
        try {
            return this.input.available() > 0;
        } catch (IOException unused) {
            return false;
        }
    }

    public String writeCommand(String str, Argument argument) throws ProtocolException, IOException {
        StringBuilder sbAppend = new StringBuilder().append(this.tagPrefix);
        int i = this.tagCounter;
        this.tagCounter = i + 1;
        String string = sbAppend.append(Integer.toString(i)).toString();
        this.output.writeBytes(string + StringUtils.SPACE + str);
        if (argument != null) {
            this.output.write(32);
            argument.write(this);
        }
        this.output.write(CRLF);
        this.output.flush();
        return string;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x005b A[Catch: all -> 0x0072, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x000b, B:14:0x0026, B:15:0x002a, B:18:0x0032, B:20:0x003b, B:29:0x005b, B:30:0x005e, B:24:0x0047, B:27:0x0054, B:8:0x0012, B:10:0x001b), top: B:36:0x0001, inners: #1, #2, #3, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0026 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized com.sun.mail.iap.Response[] command(java.lang.String r8, com.sun.mail.iap.Argument r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            r7.commandStart(r8)     // Catch: java.lang.Throwable -> L72
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L72
            r0.<init>()     // Catch: java.lang.Throwable -> L72
            r1 = 0
            r2 = 1
            java.lang.String r8 = r7.writeCommand(r8, r9)     // Catch: java.lang.Exception -> L11 com.sun.mail.iap.LiteralException -> L1a java.lang.Throwable -> L72
            r9 = 0
            goto L24
        L11:
            r8 = move-exception
            com.sun.mail.iap.Response r8 = com.sun.mail.iap.Response.byeResponse(r8)     // Catch: java.lang.Throwable -> L72
            r0.add(r8)     // Catch: java.lang.Throwable -> L72
            goto L22
        L1a:
            r8 = move-exception
            com.sun.mail.iap.Response r8 = r8.getResponse()     // Catch: java.lang.Throwable -> L72
            r0.add(r8)     // Catch: java.lang.Throwable -> L72
        L22:
            r8 = r1
        L23:
            r9 = r2
        L24:
            if (r9 != 0) goto L59
            com.sun.mail.iap.Response r3 = r7.readResponse()     // Catch: com.sun.mail.iap.ProtocolException -> L46 java.io.IOException -> L51 java.lang.Throwable -> L72
            boolean r4 = r3.isBYE()     // Catch: java.lang.Throwable -> L72
            if (r4 == 0) goto L32
            r1 = r3
            goto L24
        L32:
            r0.add(r3)     // Catch: java.lang.Throwable -> L72
            boolean r4 = r3.isTagged()     // Catch: java.lang.Throwable -> L72
            if (r4 == 0) goto L24
            java.lang.String r3 = r3.getTag()     // Catch: java.lang.Throwable -> L72
            boolean r3 = r3.equals(r8)     // Catch: java.lang.Throwable -> L72
            if (r3 == 0) goto L24
            goto L23
        L46:
            r3 = move-exception
            com.sun.mail.util.MailLogger r4 = r7.logger     // Catch: java.lang.Throwable -> L72
            java.util.logging.Level r5 = java.util.logging.Level.FINE     // Catch: java.lang.Throwable -> L72
            java.lang.String r6 = "ignoring bad response"
            r4.log(r5, r6, r3)     // Catch: java.lang.Throwable -> L72
            goto L24
        L51:
            r8 = move-exception
            if (r1 != 0) goto L59
            com.sun.mail.iap.Response r8 = com.sun.mail.iap.Response.byeResponse(r8)     // Catch: java.lang.Throwable -> L72
            r1 = r8
        L59:
            if (r1 == 0) goto L5e
            r0.add(r1)     // Catch: java.lang.Throwable -> L72
        L5e:
            int r8 = r0.size()     // Catch: java.lang.Throwable -> L72
            com.sun.mail.iap.Response[] r8 = new com.sun.mail.iap.Response[r8]     // Catch: java.lang.Throwable -> L72
            r0.toArray(r8)     // Catch: java.lang.Throwable -> L72
            long r0 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L72
            r7.timestamp = r0     // Catch: java.lang.Throwable -> L72
            r7.commandEnd()     // Catch: java.lang.Throwable -> L72
            monitor-exit(r7)
            return r8
        L72:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.iap.Protocol.command(java.lang.String, com.sun.mail.iap.Argument):com.sun.mail.iap.Response[]");
    }

    public void handleResult(Response response) throws ProtocolException {
        if (response.isOK()) {
            return;
        }
        if (response.isNO()) {
            throw new CommandFailedException(response);
        }
        if (response.isBAD()) {
            throw new BadCommandException(response);
        }
        if (response.isBYE()) {
            disconnect();
            throw new ConnectionException(this, response);
        }
    }

    public void simpleCommand(String str, Argument argument) throws ProtocolException {
        Response[] responseArrCommand = command(str, argument);
        notifyResponseHandlers(responseArrCommand);
        handleResult(responseArrCommand[responseArrCommand.length - 1]);
    }

    public synchronized void startTLS(String str) throws ProtocolException, IOException {
        if (this.socket instanceof SSLSocket) {
            return;
        }
        simpleCommand(str, null);
        this.socket = SocketFetcher.startTLS(this.socket, this.host, this.props, this.prefix);
        initStreams();
    }

    public synchronized void startCompression(String str) throws ProtocolException, IOException {
        simpleCommand(str, null);
        TraceInputStream traceInputStream = new TraceInputStream(new InflaterInputStream(this.socket.getInputStream(), new Inflater(true)), this.traceLogger);
        this.traceInput = traceInputStream;
        traceInputStream.setQuote(this.quote);
        this.input = new ResponseInputStream(this.traceInput);
        int intProperty = PropUtil.getIntProperty(this.props, this.prefix + ".compress.level", -1);
        int intProperty2 = PropUtil.getIntProperty(this.props, this.prefix + ".compress.strategy", 0);
        if (this.logger.isLoggable(Level.FINE)) {
            this.logger.log(Level.FINE, "Creating Deflater with compression level {0} and strategy {1}", Integer.valueOf(intProperty), Integer.valueOf(intProperty2));
        }
        Deflater deflater = new Deflater(-1, true);
        try {
            deflater.setLevel(intProperty);
        } catch (IllegalArgumentException e) {
            this.logger.log(Level.FINE, "Ignoring bad compression level", (Throwable) e);
        }
        try {
            deflater.setStrategy(intProperty2);
        } catch (IllegalArgumentException e2) {
            this.logger.log(Level.FINE, "Ignoring bad compression strategy", (Throwable) e2);
        }
        TraceOutputStream traceOutputStream = new TraceOutputStream(new DeflaterOutputStream(this.socket.getOutputStream(), deflater, true), this.traceLogger);
        this.traceOutput = traceOutputStream;
        traceOutputStream.setQuote(this.quote);
        this.output = new DataOutputStream(new BufferedOutputStream(this.traceOutput));
    }

    public boolean isSSL() {
        return this.socket instanceof SSLSocket;
    }

    public InetAddress getInetAddress() {
        return this.socket.getInetAddress();
    }

    public SocketChannel getChannel() {
        SocketChannel channel = this.socket.getChannel();
        if (channel != null) {
            return channel;
        }
        Socket socket = this.socket;
        if (!(socket instanceof SSLSocket)) {
            return channel;
        }
        try {
            Field declaredField = socket.getClass().getDeclaredField("socket");
            declaredField.setAccessible(true);
            return ((Socket) declaredField.get(this.socket)).getChannel();
        } catch (Exception unused) {
            return channel;
        }
    }

    protected synchronized void disconnect() {
        Socket socket = this.socket;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException unused) {
            }
            this.socket = null;
        }
    }

    protected synchronized String getLocalHost() {
        Socket socket;
        String str = this.localHostName;
        if (str == null || str.length() <= 0) {
            this.localHostName = this.props.getProperty(this.prefix + ".localhost");
        }
        String str2 = this.localHostName;
        if (str2 == null || str2.length() <= 0) {
            this.localHostName = this.props.getProperty(this.prefix + ".localaddress");
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
        if ((str4 == null || str4.length() <= 0) && (socket = this.socket) != null && socket.isBound()) {
            InetAddress localAddress = this.socket.getLocalAddress();
            String canonicalHostName2 = localAddress.getCanonicalHostName();
            this.localHostName = canonicalHostName2;
            if (canonicalHostName2 == null) {
                this.localHostName = "[" + localAddress.getHostAddress() + "]";
            }
        }
        return this.localHostName;
    }

    protected boolean isTracing() {
        return this.traceLogger.isLoggable(Level.FINEST);
    }

    protected void suspendTracing() {
        if (this.traceLogger.isLoggable(Level.FINEST)) {
            this.traceInput.setTrace(false);
            this.traceOutput.setTrace(false);
        }
    }

    protected void resumeTracing() {
        if (this.traceLogger.isLoggable(Level.FINEST)) {
            this.traceInput.setTrace(true);
            this.traceOutput.setTrace(true);
        }
    }

    protected void finalize() throws Throwable {
        try {
            disconnect();
        } finally {
            super.finalize();
        }
    }
}
