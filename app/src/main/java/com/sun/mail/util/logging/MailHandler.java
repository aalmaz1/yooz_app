package com.sun.mail.util.logging;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.webkit.internal.AssetHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import javax.activation.DataHandler;
import javax.activation.FileTypeMap;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessageContext;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Service;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

/* JADX INFO: loaded from: classes2.dex */
public class MailHandler extends Handler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MIN_HEADER_SIZE = 1024;
    private volatile Filter[] attachmentFilters;
    private Formatter[] attachmentFormatters;
    private Formatter[] attachmentNames;
    private Authenticator auth;
    private int capacity;
    private Comparator<? super LogRecord> comparator;
    private FileTypeMap contentTypes;
    private LogRecord[] data;
    private String encoding;
    private volatile Filter filter;
    private Formatter formatter;
    private boolean isWriting;
    private Properties mailProps;
    private int[] matched;
    private Filter pushFilter;
    private Level pushLevel;
    private volatile boolean sealed;
    private Session session;
    private int size;
    private Formatter subjectFormatter;
    private static final Filter[] EMPTY_FILTERS = new Filter[0];
    private static final Formatter[] EMPTY_FORMATTERS = new Formatter[0];
    private static final int offValue = Level.OFF.intValue();
    private static final PrivilegedAction<Object> MAILHANDLER_LOADER = new GetAndSetContext(MailHandler.class);
    private static final ThreadLocal<Integer> MUTEX = new ThreadLocal<>();
    private static final Integer MUTEX_PUBLISH = -2;
    private static final Integer MUTEX_REPORT = -4;
    private static final Integer MUTEX_LINKAGE = -8;
    private volatile Level logLevel = Level.ALL;
    private volatile ErrorManager errorManager = defaultErrorManager();

    public void postConstruct() {
    }

    public MailHandler() {
        init(null);
        this.sealed = true;
        checkAccess();
    }

    public MailHandler(int i) {
        init(null);
        this.sealed = true;
        setCapacity0(i);
    }

    public MailHandler(Properties properties) throws Throwable {
        properties.getClass();
        init(properties);
        this.sealed = true;
        setMailProperties0(properties);
    }

    @Override // java.util.logging.Handler
    public boolean isLoggable(LogRecord logRecord) {
        int iIntValue = getLevel().intValue();
        if (logRecord.getLevel().intValue() < iIntValue || iIntValue == offValue) {
            return false;
        }
        Filter filter = getFilter();
        if (filter == null || filter.isLoggable(logRecord)) {
            setMatchedPart(-1);
            return true;
        }
        return isAttachmentLoggable(logRecord);
    }

    @Override // java.util.logging.Handler
    public void publish(LogRecord logRecord) {
        try {
            if (tryMutex()) {
                try {
                    if (isLoggable(logRecord)) {
                        logRecord.getSourceMethodName();
                        publish0(logRecord);
                    }
                } catch (LinkageError e) {
                    reportLinkageError(e, 1);
                }
                return;
            }
            reportUnPublishedError(logRecord);
        } finally {
            releaseMutex();
        }
    }

    private void publish0(LogRecord logRecord) {
        Message messageWriteLogRecords;
        boolean zIsPushable;
        synchronized (this) {
            int i = this.size;
            if (i == this.data.length && i < this.capacity) {
                grow();
            }
            int i2 = this.size;
            messageWriteLogRecords = null;
            if (i2 < this.data.length) {
                this.matched[i2] = getMatchedPart();
                LogRecord[] logRecordArr = this.data;
                int i3 = this.size;
                logRecordArr[i3] = logRecord;
                this.size = i3 + 1;
                zIsPushable = isPushable(logRecord);
                if (zIsPushable || this.size >= this.capacity) {
                    messageWriteLogRecords = writeLogRecords(1);
                }
            } else {
                zIsPushable = false;
            }
        }
        if (messageWriteLogRecords != null) {
            send(messageWriteLogRecords, zIsPushable, 1);
        }
    }

    private void reportUnPublishedError(LogRecord logRecord) {
        String str;
        ThreadLocal<Integer> threadLocal = MUTEX;
        Integer num = threadLocal.get();
        if (num == null || num.intValue() > MUTEX_REPORT.intValue()) {
            threadLocal.set(MUTEX_REPORT);
            if (logRecord != null) {
                try {
                    Formatter formatterCreateSimpleFormatter = createSimpleFormatter();
                    str = "Log record " + logRecord.getSequenceNumber() + " was not published. " + head(formatterCreateSimpleFormatter) + format(formatterCreateSimpleFormatter, logRecord) + tail(formatterCreateSimpleFormatter, "");
                } catch (Throwable th) {
                    if (num != null) {
                        MUTEX.set(num);
                    } else {
                        MUTEX.remove();
                    }
                    throw th;
                }
            } else {
                str = null;
            }
            reportError(str, new IllegalStateException("Recursive publish detected by thread " + Thread.currentThread()), 1);
            if (num != null) {
                threadLocal.set(num);
            } else {
                threadLocal.remove();
            }
        }
    }

    private boolean tryMutex() {
        ThreadLocal<Integer> threadLocal = MUTEX;
        if (threadLocal.get() != null) {
            return false;
        }
        threadLocal.set(MUTEX_PUBLISH);
        return true;
    }

    private void releaseMutex() {
        MUTEX.remove();
    }

    private int getMatchedPart() {
        Integer num = MUTEX.get();
        if (num == null || num.intValue() >= readOnlyAttachmentFilters().length) {
            num = MUTEX_PUBLISH;
        }
        return num.intValue();
    }

    private void setMatchedPart(int i) {
        Integer num = MUTEX_PUBLISH;
        ThreadLocal<Integer> threadLocal = MUTEX;
        if (num.equals(threadLocal.get())) {
            threadLocal.set(Integer.valueOf(i));
        }
    }

    private void clearMatches(int i) {
        for (int i2 = 0; i2 < this.size; i2++) {
            int[] iArr = this.matched;
            if (iArr[i2] >= i) {
                iArr[i2] = MUTEX_PUBLISH.intValue();
            }
        }
    }

    public void preDestroy() {
        push(false, 3);
    }

    public void push() {
        push(true, 2);
    }

    @Override // java.util.logging.Handler
    public void flush() {
        push(false, 2);
    }

    @Override // java.util.logging.Handler
    public void close() {
        Message messageWriteLogRecords;
        try {
            checkAccess();
            synchronized (this) {
                try {
                    messageWriteLogRecords = writeLogRecords(3);
                    this.logLevel = Level.OFF;
                    int i = this.capacity;
                    if (i > 0) {
                        this.capacity = -i;
                    }
                    if (this.size == 0 && this.data.length != 1) {
                        LogRecord[] logRecordArr = new LogRecord[1];
                        this.data = logRecordArr;
                        this.matched = new int[logRecordArr.length];
                    }
                } catch (Throwable th) {
                    this.logLevel = Level.OFF;
                    if (this.capacity > 0) {
                        this.capacity = -this.capacity;
                    }
                    if (this.size == 0 && this.data.length != 1) {
                        LogRecord[] logRecordArr2 = new LogRecord[1];
                        this.data = logRecordArr2;
                        this.matched = new int[logRecordArr2.length];
                    }
                    throw th;
                }
            }
            if (messageWriteLogRecords != null) {
                send(messageWriteLogRecords, false, 3);
            }
        } catch (LinkageError e) {
            reportLinkageError(e, 3);
        }
    }

    @Override // java.util.logging.Handler
    public void setLevel(Level level) {
        level.getClass();
        checkAccess();
        synchronized (this) {
            if (this.capacity > 0) {
                this.logLevel = level;
            }
        }
    }

    @Override // java.util.logging.Handler
    public Level getLevel() {
        return this.logLevel;
    }

    @Override // java.util.logging.Handler
    public ErrorManager getErrorManager() {
        checkAccess();
        return this.errorManager;
    }

    @Override // java.util.logging.Handler
    public void setErrorManager(ErrorManager errorManager) {
        checkAccess();
        setErrorManager0(errorManager);
    }

    private void setErrorManager0(ErrorManager errorManager) {
        errorManager.getClass();
        try {
            synchronized (this) {
                this.errorManager = errorManager;
                super.setErrorManager(errorManager);
            }
        } catch (LinkageError | RuntimeException unused) {
        }
    }

    @Override // java.util.logging.Handler
    public Filter getFilter() {
        return this.filter;
    }

    @Override // java.util.logging.Handler
    public void setFilter(Filter filter) {
        checkAccess();
        synchronized (this) {
            if (filter != this.filter) {
                clearMatches(-1);
            }
            this.filter = filter;
        }
    }

    @Override // java.util.logging.Handler
    public synchronized String getEncoding() {
        return this.encoding;
    }

    @Override // java.util.logging.Handler
    public void setEncoding(String str) throws UnsupportedEncodingException {
        checkAccess();
        setEncoding0(str);
    }

    private void setEncoding0(String str) throws UnsupportedEncodingException {
        if (str != null) {
            try {
                if (!Charset.isSupported(str)) {
                    throw new UnsupportedEncodingException(str);
                }
            } catch (IllegalCharsetNameException unused) {
                throw new UnsupportedEncodingException(str);
            }
        }
        synchronized (this) {
            this.encoding = str;
        }
    }

    @Override // java.util.logging.Handler
    public synchronized Formatter getFormatter() {
        return this.formatter;
    }

    @Override // java.util.logging.Handler
    public synchronized void setFormatter(Formatter formatter) throws SecurityException {
        checkAccess();
        if (formatter == null) {
            throw new NullPointerException();
        }
        this.formatter = formatter;
    }

    public final synchronized Level getPushLevel() {
        return this.pushLevel;
    }

    public final synchronized void setPushLevel(Level level) {
        checkAccess();
        if (level == null) {
            throw new NullPointerException();
        }
        if (this.isWriting) {
            throw new IllegalStateException();
        }
        this.pushLevel = level;
    }

    public final synchronized Filter getPushFilter() {
        return this.pushFilter;
    }

    public final synchronized void setPushFilter(Filter filter) {
        checkAccess();
        if (this.isWriting) {
            throw new IllegalStateException();
        }
        this.pushFilter = filter;
    }

    public final synchronized Comparator<? super LogRecord> getComparator() {
        return this.comparator;
    }

    public final synchronized void setComparator(Comparator<? super LogRecord> comparator) {
        checkAccess();
        if (this.isWriting) {
            throw new IllegalStateException();
        }
        this.comparator = comparator;
    }

    public final synchronized int getCapacity() {
        return Math.abs(this.capacity);
    }

    public final synchronized Authenticator getAuthenticator() {
        checkAccess();
        return this.auth;
    }

    public final void setAuthenticator(Authenticator authenticator) throws Throwable {
        setAuthenticator0(authenticator);
    }

    public final void setAuthenticator(char... cArr) throws Throwable {
        if (cArr == null) {
            setAuthenticator0(null);
        } else {
            setAuthenticator0(DefaultAuthenticator.of(new String(cArr)));
        }
    }

    private void setAuthenticator0(Authenticator authenticator) throws Throwable {
        Session sessionUpdateSession;
        checkAccess();
        synchronized (this) {
            if (this.isWriting) {
                throw new IllegalStateException();
            }
            this.auth = authenticator;
            sessionUpdateSession = updateSession();
        }
        verifySettings(sessionUpdateSession);
    }

    public final void setMailProperties(Properties properties) throws Throwable {
        setMailProperties0(properties);
    }

    private void setMailProperties0(Properties properties) throws Throwable {
        Session sessionUpdateSession;
        checkAccess();
        Properties properties2 = (Properties) properties.clone();
        synchronized (this) {
            if (this.isWriting) {
                throw new IllegalStateException();
            }
            this.mailProps = properties2;
            sessionUpdateSession = updateSession();
        }
        verifySettings(sessionUpdateSession);
    }

    public final Properties getMailProperties() {
        Properties properties;
        checkAccess();
        synchronized (this) {
            properties = this.mailProps;
        }
        return (Properties) properties.clone();
    }

    public final Filter[] getAttachmentFilters() {
        return (Filter[]) readOnlyAttachmentFilters().clone();
    }

    public final void setAttachmentFilters(Filter... filterArr) {
        Filter[] filterArrEmptyFilterArray;
        checkAccess();
        if (filterArr.length == 0) {
            filterArrEmptyFilterArray = emptyFilterArray();
        } else {
            filterArrEmptyFilterArray = (Filter[]) Arrays.copyOf(filterArr, filterArr.length, Filter[].class);
        }
        synchronized (this) {
            Formatter[] formatterArr = this.attachmentFormatters;
            if (formatterArr.length != filterArrEmptyFilterArray.length) {
                throw attachmentMismatch(formatterArr.length, filterArrEmptyFilterArray.length);
            }
            if (this.isWriting) {
                throw new IllegalStateException();
            }
            if (this.size != 0) {
                int i = 0;
                while (true) {
                    if (i >= filterArrEmptyFilterArray.length) {
                        break;
                    }
                    if (filterArrEmptyFilterArray[i] != this.attachmentFilters[i]) {
                        clearMatches(i);
                        break;
                    }
                    i++;
                }
            }
            this.attachmentFilters = filterArrEmptyFilterArray;
        }
    }

    public final Formatter[] getAttachmentFormatters() {
        Formatter[] formatterArr;
        synchronized (this) {
            formatterArr = this.attachmentFormatters;
        }
        return (Formatter[]) formatterArr.clone();
    }

    public final void setAttachmentFormatters(Formatter... formatterArr) {
        Formatter[] formatterArrEmptyFormatterArray;
        checkAccess();
        if (formatterArr.length == 0) {
            formatterArrEmptyFormatterArray = emptyFormatterArray();
        } else {
            formatterArrEmptyFormatterArray = (Formatter[]) Arrays.copyOf(formatterArr, formatterArr.length, Formatter[].class);
            for (int i = 0; i < formatterArrEmptyFormatterArray.length; i++) {
                if (formatterArrEmptyFormatterArray[i] == null) {
                    throw new NullPointerException(atIndexMsg(i));
                }
            }
        }
        synchronized (this) {
            if (this.isWriting) {
                throw new IllegalStateException();
            }
            this.attachmentFormatters = formatterArrEmptyFormatterArray;
            alignAttachmentFilters();
            alignAttachmentNames();
        }
    }

    public final Formatter[] getAttachmentNames() {
        Formatter[] formatterArr;
        synchronized (this) {
            formatterArr = this.attachmentNames;
        }
        return (Formatter[]) formatterArr.clone();
    }

    public final void setAttachmentNames(String... strArr) {
        Formatter[] formatterArrEmptyFormatterArray;
        checkAccess();
        if (strArr.length == 0) {
            formatterArrEmptyFormatterArray = emptyFormatterArray();
        } else {
            formatterArrEmptyFormatterArray = new Formatter[strArr.length];
        }
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            if (str != null) {
                if (str.length() > 0) {
                    formatterArrEmptyFormatterArray[i] = TailNameFormatter.of(str);
                } else {
                    throw new IllegalArgumentException(atIndexMsg(i));
                }
            } else {
                throw new NullPointerException(atIndexMsg(i));
            }
        }
        synchronized (this) {
            Formatter[] formatterArr = this.attachmentFormatters;
            if (formatterArr.length != strArr.length) {
                throw attachmentMismatch(formatterArr.length, strArr.length);
            }
            if (this.isWriting) {
                throw new IllegalStateException();
            }
            this.attachmentNames = formatterArrEmptyFormatterArray;
        }
    }

    public final void setAttachmentNames(Formatter... formatterArr) {
        Formatter[] formatterArrEmptyFormatterArray;
        checkAccess();
        if (formatterArr.length == 0) {
            formatterArrEmptyFormatterArray = emptyFormatterArray();
        } else {
            formatterArrEmptyFormatterArray = (Formatter[]) Arrays.copyOf(formatterArr, formatterArr.length, Formatter[].class);
        }
        for (int i = 0; i < formatterArrEmptyFormatterArray.length; i++) {
            if (formatterArrEmptyFormatterArray[i] == null) {
                throw new NullPointerException(atIndexMsg(i));
            }
        }
        synchronized (this) {
            Formatter[] formatterArr2 = this.attachmentFormatters;
            if (formatterArr2.length != formatterArrEmptyFormatterArray.length) {
                throw attachmentMismatch(formatterArr2.length, formatterArrEmptyFormatterArray.length);
            }
            if (this.isWriting) {
                throw new IllegalStateException();
            }
            this.attachmentNames = formatterArrEmptyFormatterArray;
        }
    }

    public final synchronized Formatter getSubject() {
        return this.subjectFormatter;
    }

    public final void setSubject(String str) {
        if (str != null) {
            setSubject(TailNameFormatter.of(str));
        } else {
            checkAccess();
            throw null;
        }
    }

    public final void setSubject(Formatter formatter) {
        checkAccess();
        formatter.getClass();
        synchronized (this) {
            if (this.isWriting) {
                throw new IllegalStateException();
            }
            this.subjectFormatter = formatter;
        }
    }

    @Override // java.util.logging.Handler
    protected void reportError(String str, Exception exc, int i) {
        try {
            if (str != null) {
                this.errorManager.error(Level.SEVERE.getName().concat(": ").concat(str), exc, i);
            } else {
                this.errorManager.error(null, exc, i);
            }
        } catch (LinkageError | RuntimeException e) {
            reportLinkageError(e, i);
        }
    }

    private void checkAccess() {
        if (this.sealed) {
            LogManagerProperties.checkLogManagerAccess();
        }
    }

    final String contentTypeOf(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return null;
        }
        if (charSequence.length() > 25) {
            charSequence = charSequence.subSequence(0, 25);
        }
        try {
            return URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(charSequence.toString().getBytes(getEncodingName())));
        } catch (IOException e) {
            reportError(e.getMessage(), e, 5);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0062, code lost:
    
        r7 = r7.getSuperclass();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final java.lang.String contentTypeOf(java.util.logging.Formatter r7) {
        /*
            r6 = this;
            if (r7 == 0) goto L67
            java.lang.Class r0 = r7.getClass()
            java.lang.String r0 = r0.getName()
            java.lang.String r0 = r6.getContentType(r0)
            if (r0 == 0) goto L11
            return r0
        L11:
            java.lang.Class r7 = r7.getClass()
        L15:
            java.lang.Class<java.util.logging.Formatter> r0 = java.util.logging.Formatter.class
            if (r7 == r0) goto L67
            java.lang.String r0 = r7.getSimpleName()     // Catch: java.lang.InternalError -> L1e
            goto L22
        L1e:
            java.lang.String r0 = r7.getName()
        L22:
            java.util.Locale r1 = java.util.Locale.ENGLISH
            java.lang.String r0 = r0.toLowerCase(r1)
            r1 = 36
            int r1 = r0.indexOf(r1)
            r2 = 1
            int r1 = r1 + r2
        L30:
            java.lang.String r3 = "ml"
            int r1 = r0.indexOf(r3, r1)
            r3 = -1
            if (r1 <= r3) goto L62
            if (r1 <= 0) goto L5f
            int r3 = r1 + (-1)
            char r4 = r0.charAt(r3)
            r5 = 120(0x78, float:1.68E-43)
            if (r4 != r5) goto L48
            java.lang.String r7 = "application/xml"
            return r7
        L48:
            if (r1 <= r2) goto L5f
            int r4 = r1 + (-2)
            char r4 = r0.charAt(r4)
            r5 = 104(0x68, float:1.46E-43)
            if (r4 != r5) goto L5f
            char r3 = r0.charAt(r3)
            r4 = 116(0x74, float:1.63E-43)
            if (r3 != r4) goto L5f
            java.lang.String r7 = "text/html"
            return r7
        L5f:
            int r1 = r1 + 2
            goto L30
        L62:
            java.lang.Class r7 = r7.getSuperclass()
            goto L15
        L67:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.logging.MailHandler.contentTypeOf(java.util.logging.Formatter):java.lang.String");
    }

    final boolean isMissingContent(Message message, Throwable th) {
        Object andSetContextClassLoader = getAndSetContextClassLoader(MAILHANDLER_LOADER);
        try {
            try {
                message.writeTo(new ByteArrayOutputStream(1024));
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                String message2 = e2.getMessage();
                if (!isEmpty(message2)) {
                    int i = 0;
                    while (th != null) {
                        if (e2.getClass() != th.getClass() || !message2.equals(th.getMessage())) {
                            Throwable cause = th.getCause();
                            th = (cause == null && (th instanceof MessagingException)) ? ((MessagingException) th).getNextException() : cause;
                            i++;
                            if (i == 65536) {
                                break;
                            }
                        } else {
                            getAndSetContextClassLoader(andSetContextClassLoader);
                            return true;
                        }
                    }
                }
            }
            getAndSetContextClassLoader(andSetContextClassLoader);
            return false;
        } catch (Throwable th2) {
            getAndSetContextClassLoader(andSetContextClassLoader);
            throw th2;
        }
    }

    private void reportError(Message message, Exception exc, int i) {
        try {
            try {
                this.errorManager.error(toRawString(message), exc, i);
            } catch (RuntimeException e) {
                reportError(toMsgString(e), exc, i);
            } catch (Exception e2) {
                reportError(toMsgString(e2), exc, i);
            }
        } catch (LinkageError e3) {
            reportLinkageError(e3, i);
        }
    }

    private void reportLinkageError(Throwable th, int i) {
        if (th == null) {
            throw new NullPointerException(String.valueOf(i));
        }
        ThreadLocal<Integer> threadLocal = MUTEX;
        Integer num = threadLocal.get();
        if (num == null || num.intValue() > MUTEX_LINKAGE.intValue()) {
            threadLocal.set(MUTEX_LINKAGE);
            try {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), th);
                if (num != null) {
                    threadLocal.set(num);
                } else {
                    threadLocal.remove();
                }
            } catch (LinkageError | RuntimeException unused) {
                if (num != null) {
                    MUTEX.set(num);
                } else {
                    MUTEX.remove();
                }
            } catch (Throwable th2) {
                if (num != null) {
                    MUTEX.set(num);
                } else {
                    MUTEX.remove();
                }
                throw th2;
            }
        }
    }

    private String getContentType(String str) {
        String contentType = this.contentTypes.getContentType(str);
        if ("application/octet-stream".equalsIgnoreCase(contentType)) {
            return null;
        }
        return contentType;
    }

    private String getEncodingName() {
        String encoding = getEncoding();
        return encoding == null ? MimeUtility.getDefaultJavaCharset() : encoding;
    }

    private void setContent(MimePart mimePart, CharSequence charSequence, String str) throws MessagingException {
        String encodingName = getEncodingName();
        if (str != null && !AssetHelper.DEFAULT_MIME_TYPE.equalsIgnoreCase(str)) {
            try {
                mimePart.setDataHandler(new DataHandler(new ByteArrayDataSource(charSequence.toString(), contentWithEncoding(str, encodingName))));
                return;
            } catch (IOException e) {
                reportError(e.getMessage(), e, 5);
                mimePart.setText(charSequence.toString(), encodingName);
                return;
            }
        }
        mimePart.setText(charSequence.toString(), MimeUtility.mimeCharset(encodingName));
    }

    private String contentWithEncoding(String str, String str2) {
        try {
            ContentType contentType = new ContentType(str);
            contentType.setParameter("charset", MimeUtility.mimeCharset(str2));
            String string = contentType.toString();
            return !isEmpty(string) ? string : str;
        } catch (MessagingException e) {
            reportError(str, e, 5);
            return str;
        }
    }

    private synchronized void setCapacity0(int i) {
        checkAccess();
        if (i <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        if (this.isWriting) {
            throw new IllegalStateException();
        }
        if (this.capacity < 0) {
            this.capacity = -i;
        } else {
            this.capacity = i;
        }
    }

    private Filter[] readOnlyAttachmentFilters() {
        return this.attachmentFilters;
    }

    private static Formatter[] emptyFormatterArray() {
        return EMPTY_FORMATTERS;
    }

    private static Filter[] emptyFilterArray() {
        return EMPTY_FILTERS;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean alignAttachmentNames() {
        /*
            r5 = this;
            java.util.logging.Formatter[] r0 = r5.attachmentFormatters
            int r0 = r0.length
            java.util.logging.Formatter[] r1 = r5.attachmentNames
            int r2 = r1.length
            r3 = 0
            if (r2 == r0) goto L17
            java.lang.Class<java.util.logging.Formatter[]> r4 = java.util.logging.Formatter[].class
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r1, r0, r4)
            java.util.logging.Formatter[] r1 = (java.util.logging.Formatter[]) r1
            r5.attachmentNames = r1
            if (r2 == 0) goto L17
            r1 = 1
            goto L18
        L17:
            r1 = r3
        L18:
            if (r0 != 0) goto L21
            java.util.logging.Formatter[] r0 = emptyFormatterArray()
            r5.attachmentNames = r0
            goto L3a
        L21:
            if (r3 >= r0) goto L3a
            java.util.logging.Formatter[] r2 = r5.attachmentNames
            r4 = r2[r3]
            if (r4 != 0) goto L37
            java.util.logging.Formatter[] r4 = r5.attachmentFormatters
            r4 = r4[r3]
            java.lang.String r4 = r5.toString(r4)
            java.util.logging.Formatter r4 = com.sun.mail.util.logging.MailHandler.TailNameFormatter.of(r4)
            r2[r3] = r4
        L37:
            int r3 = r3 + 1
            goto L21
        L3a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.logging.MailHandler.alignAttachmentNames():boolean");
    }

    private boolean alignAttachmentFilters() {
        int length = this.attachmentFormatters.length;
        int length2 = this.attachmentFilters.length;
        if (length2 != length) {
            this.attachmentFilters = (Filter[]) Arrays.copyOf(this.attachmentFilters, length, Filter[].class);
            clearMatches(length2);
            z = length2 != 0;
            Filter filter = this.filter;
            if (filter != null) {
                while (length2 < length) {
                    this.attachmentFilters[length2] = filter;
                    length2++;
                }
            }
        }
        if (length == 0) {
            this.attachmentFilters = emptyFilterArray();
        }
        return z;
    }

    private void reset() {
        int i = this.size;
        LogRecord[] logRecordArr = this.data;
        if (i < logRecordArr.length) {
            Arrays.fill(logRecordArr, 0, i, (Object) null);
        } else {
            Arrays.fill(logRecordArr, (Object) null);
        }
        this.size = 0;
    }

    private void grow() {
        LogRecord[] logRecordArr = this.data;
        int length = logRecordArr.length;
        int i = (length >> 1) + length + 1;
        int i2 = this.capacity;
        if (i > i2 || i < length) {
            i = i2;
        }
        this.data = (LogRecord[]) Arrays.copyOf(logRecordArr, i, LogRecord[].class);
        this.matched = Arrays.copyOf(this.matched, i);
    }

    private synchronized void init(Properties properties) {
        String name = getClass().getName();
        this.mailProps = new Properties();
        Object andSetContextClassLoader = getAndSetContextClassLoader(MAILHANDLER_LOADER);
        try {
            this.contentTypes = FileTypeMap.getDefaultFileTypeMap();
            getAndSetContextClassLoader(andSetContextClassLoader);
            initErrorManager(name);
            initLevel(name);
            initFilter(name);
            initCapacity(name);
            initAuthenticator(name);
            initEncoding(name);
            initFormatter(name);
            initComparator(name);
            initPushLevel(name);
            initPushFilter(name);
            initSubject(name);
            initAttachmentFormaters(name);
            initAttachmentFilters(name);
            initAttachmentNames(name);
            if (properties == null && LogManagerProperties.fromLogManager(name.concat(".verify")) != null) {
                verifySettings(initSession());
            }
            intern();
        } catch (Throwable th) {
            getAndSetContextClassLoader(andSetContextClassLoader);
            throw th;
        }
    }

    private void intern() {
        try {
            Map<Object, Object> map = new HashMap<>();
            try {
                intern(map, this.errorManager);
            } catch (SecurityException e) {
                reportError(e.getMessage(), e, 4);
            }
            try {
                Object obj = this.filter;
                Object objIntern = intern(map, obj);
                if (objIntern != obj && (objIntern instanceof Filter)) {
                    this.filter = (Filter) objIntern;
                }
                Object obj2 = this.formatter;
                Object objIntern2 = intern(map, obj2);
                if (objIntern2 != obj2 && (objIntern2 instanceof Formatter)) {
                    this.formatter = (Formatter) objIntern2;
                }
            } catch (SecurityException e2) {
                reportError(e2.getMessage(), e2, 4);
            }
            Object obj3 = this.subjectFormatter;
            Object objIntern3 = intern(map, obj3);
            if (objIntern3 != obj3 && (objIntern3 instanceof Formatter)) {
                this.subjectFormatter = (Formatter) objIntern3;
            }
            Object obj4 = this.pushFilter;
            Object objIntern4 = intern(map, obj4);
            if (objIntern4 != obj4 && (objIntern4 instanceof Filter)) {
                this.pushFilter = (Filter) objIntern4;
            }
            int i = 0;
            while (true) {
                Object[] objArr = this.attachmentFormatters;
                if (i >= objArr.length) {
                    return;
                }
                Object obj5 = objArr[i];
                Object objIntern5 = intern(map, obj5);
                if (objIntern5 != obj5 && (objIntern5 instanceof Formatter)) {
                    this.attachmentFormatters[i] = (Formatter) objIntern5;
                }
                Object obj6 = this.attachmentFilters[i];
                Object objIntern6 = intern(map, obj6);
                if (objIntern6 != obj6 && (objIntern6 instanceof Filter)) {
                    this.attachmentFilters[i] = (Filter) objIntern6;
                }
                Object obj7 = this.attachmentNames[i];
                Object objIntern7 = intern(map, obj7);
                if (objIntern7 != obj7 && (objIntern7 instanceof Formatter)) {
                    this.attachmentNames[i] = (Formatter) objIntern7;
                }
                i++;
            }
        } catch (Exception e3) {
            reportError(e3.getMessage(), e3, 4);
        } catch (LinkageError e4) {
            reportError(e4.getMessage(), new InvocationTargetException(e4), 4);
        }
    }

    private Object intern(Map<Object, Object> map, Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Object objNewInstance = obj.getClass().getName().equals(TailNameFormatter.class.getName()) ? obj : obj.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
        if (objNewInstance.getClass() != obj.getClass()) {
            return obj;
        }
        Object obj2 = map.get(objNewInstance);
        if (obj2 == null) {
            boolean zEquals = objNewInstance.equals(obj);
            boolean zEquals2 = obj.equals(objNewInstance);
            if (!zEquals || !zEquals2) {
                if (zEquals == zEquals2) {
                    return obj;
                }
                reportNonSymmetric(obj, objNewInstance);
                return obj;
            }
            Object objPut = map.put(obj, obj);
            if (objPut == null) {
                return obj;
            }
            reportNonDiscriminating(objNewInstance, objPut);
            Object objRemove = map.remove(objNewInstance);
            if (objRemove == obj) {
                return obj;
            }
            reportNonDiscriminating(objNewInstance, objRemove);
            map.clear();
            return obj;
        }
        if (obj.getClass() == obj2.getClass()) {
            return obj2;
        }
        reportNonDiscriminating(obj, obj2);
        return obj;
    }

    private static Formatter createSimpleFormatter() {
        return (Formatter) Formatter.class.cast(new SimpleFormatter());
    }

    private static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    private static boolean hasValue(String str) {
        return (isEmpty(str) || "null".equalsIgnoreCase(str)) ? false : true;
    }

    private void initAttachmentFilters(String str) {
        String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".attachment.filters"));
        if (!isEmpty(strFromLogManager)) {
            String[] strArrSplit = strFromLogManager.split(",");
            int length = strArrSplit.length;
            Filter[] filterArr = new Filter[length];
            for (int i = 0; i < length; i++) {
                String strTrim = strArrSplit[i].trim();
                strArrSplit[i] = strTrim;
                if (!"null".equalsIgnoreCase(strTrim)) {
                    try {
                        filterArr[i] = LogManagerProperties.newFilter(strArrSplit[i]);
                    } catch (SecurityException e) {
                        throw e;
                    } catch (Exception e2) {
                        reportError(e2.getMessage(), e2, 4);
                    }
                }
            }
            this.attachmentFilters = filterArr;
            if (alignAttachmentFilters()) {
                reportError("Attachment filters.", attachmentMismatch("Length mismatch."), 4);
                return;
            }
            return;
        }
        this.attachmentFilters = emptyFilterArray();
        alignAttachmentFilters();
    }

    private void initAttachmentFormaters(String str) {
        Formatter[] formatterArrEmptyFormatterArray;
        String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".attachment.formatters"));
        if (!isEmpty(strFromLogManager)) {
            String[] strArrSplit = strFromLogManager.split(",");
            if (strArrSplit.length == 0) {
                formatterArrEmptyFormatterArray = emptyFormatterArray();
            } else {
                formatterArrEmptyFormatterArray = new Formatter[strArrSplit.length];
            }
            for (int i = 0; i < formatterArrEmptyFormatterArray.length; i++) {
                String strTrim = strArrSplit[i].trim();
                strArrSplit[i] = strTrim;
                if (!"null".equalsIgnoreCase(strTrim)) {
                    try {
                        Formatter formatterNewFormatter = LogManagerProperties.newFormatter(strArrSplit[i]);
                        formatterArrEmptyFormatterArray[i] = formatterNewFormatter;
                        if (formatterNewFormatter instanceof TailNameFormatter) {
                            reportError("Attachment formatter.", new ClassNotFoundException(formatterArrEmptyFormatterArray[i].toString()), 4);
                            formatterArrEmptyFormatterArray[i] = createSimpleFormatter();
                        }
                    } catch (SecurityException e) {
                        throw e;
                    } catch (Exception e2) {
                        reportError(e2.getMessage(), e2, 4);
                        formatterArrEmptyFormatterArray[i] = createSimpleFormatter();
                    }
                } else {
                    reportError("Attachment formatter.", new NullPointerException(atIndexMsg(i)), 4);
                    formatterArrEmptyFormatterArray[i] = createSimpleFormatter();
                }
            }
            this.attachmentFormatters = formatterArrEmptyFormatterArray;
            return;
        }
        this.attachmentFormatters = emptyFormatterArray();
    }

    private void initAttachmentNames(String str) {
        String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".attachment.names"));
        if (!isEmpty(strFromLogManager)) {
            String[] strArrSplit = strFromLogManager.split(",");
            int length = strArrSplit.length;
            Formatter[] formatterArr = new Formatter[length];
            for (int i = 0; i < length; i++) {
                String strTrim = strArrSplit[i].trim();
                strArrSplit[i] = strTrim;
                if (!"null".equalsIgnoreCase(strTrim)) {
                    try {
                        try {
                            formatterArr[i] = LogManagerProperties.newFormatter(strArrSplit[i]);
                        } catch (ClassCastException | ClassNotFoundException unused) {
                            formatterArr[i] = TailNameFormatter.of(strArrSplit[i]);
                        }
                    } catch (SecurityException e) {
                        throw e;
                    } catch (Exception e2) {
                        reportError(e2.getMessage(), e2, 4);
                    }
                } else {
                    reportError("Attachment names.", new NullPointerException(atIndexMsg(i)), 4);
                }
            }
            this.attachmentNames = formatterArr;
            if (alignAttachmentNames()) {
                reportError("Attachment names.", attachmentMismatch("Length mismatch."), 4);
                return;
            }
            return;
        }
        this.attachmentNames = emptyFormatterArray();
        alignAttachmentNames();
    }

    private void initAuthenticator(String str) {
        String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".authenticator"));
        if (strFromLogManager == null || "null".equalsIgnoreCase(strFromLogManager)) {
            return;
        }
        if (strFromLogManager.length() != 0) {
            try {
                this.auth = (Authenticator) LogManagerProperties.newObjectFrom(strFromLogManager, Authenticator.class);
                return;
            } catch (ClassCastException | ClassNotFoundException unused) {
                this.auth = DefaultAuthenticator.of(strFromLogManager);
                return;
            } catch (SecurityException e) {
                throw e;
            } catch (Exception e2) {
                reportError(e2.getMessage(), e2, 4);
                return;
            }
        }
        this.auth = DefaultAuthenticator.of(strFromLogManager);
    }

    private void initLevel(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".level"));
            if (strFromLogManager != null) {
                this.logLevel = Level.parse(strFromLogManager);
            } else {
                this.logLevel = Level.WARNING;
            }
        } catch (SecurityException e) {
            throw e;
        } catch (RuntimeException e2) {
            reportError(e2.getMessage(), e2, 4);
            this.logLevel = Level.WARNING;
        }
    }

    private void initFilter(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".filter"));
            if (hasValue(strFromLogManager)) {
                this.filter = LogManagerProperties.newFilter(strFromLogManager);
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            reportError(e2.getMessage(), e2, 4);
        }
    }

    private void initCapacity(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".capacity"));
            if (strFromLogManager != null) {
                setCapacity0(Integer.parseInt(strFromLogManager));
            } else {
                setCapacity0(1000);
            }
        } catch (SecurityException e) {
            throw e;
        } catch (RuntimeException e2) {
            reportError(e2.getMessage(), e2, 4);
        }
        if (this.capacity <= 0) {
            this.capacity = 1000;
        }
        LogRecord[] logRecordArr = new LogRecord[1];
        this.data = logRecordArr;
        this.matched = new int[logRecordArr.length];
    }

    private void initEncoding(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".encoding"));
            if (strFromLogManager != null) {
                setEncoding0(strFromLogManager);
            }
        } catch (UnsupportedEncodingException e) {
            e = e;
            reportError(e.getMessage(), e, 4);
        } catch (SecurityException e2) {
            throw e2;
        } catch (RuntimeException e3) {
            e = e3;
            reportError(e.getMessage(), e, 4);
        }
    }

    private ErrorManager defaultErrorManager() {
        ErrorManager errorManager;
        try {
            errorManager = super.getErrorManager();
        } catch (LinkageError | RuntimeException unused) {
            errorManager = null;
        }
        return errorManager == null ? new ErrorManager() : errorManager;
    }

    private void initErrorManager(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".errorManager"));
            if (strFromLogManager != null) {
                setErrorManager0(LogManagerProperties.newErrorManager(strFromLogManager));
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            reportError(e2.getMessage(), e2, 4);
        }
    }

    private void initFormatter(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".formatter"));
            if (hasValue(strFromLogManager)) {
                Formatter formatterNewFormatter = LogManagerProperties.newFormatter(strFromLogManager);
                if (!(formatterNewFormatter instanceof TailNameFormatter)) {
                    this.formatter = formatterNewFormatter;
                } else {
                    this.formatter = createSimpleFormatter();
                }
            } else {
                this.formatter = createSimpleFormatter();
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            reportError(e2.getMessage(), e2, 4);
            this.formatter = createSimpleFormatter();
        }
    }

    private void initComparator(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".comparator"));
            String strFromLogManager2 = LogManagerProperties.fromLogManager(str.concat(".comparator.reverse"));
            if (hasValue(strFromLogManager)) {
                this.comparator = LogManagerProperties.newComparator(strFromLogManager);
                if (Boolean.parseBoolean(strFromLogManager2)) {
                    this.comparator = LogManagerProperties.reverseOrder(this.comparator);
                    return;
                }
                return;
            }
            if (!isEmpty(strFromLogManager2)) {
                throw new IllegalArgumentException("No comparator to reverse.");
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            reportError(e2.getMessage(), e2, 4);
        }
    }

    private void initPushLevel(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".pushLevel"));
            if (strFromLogManager != null) {
                this.pushLevel = Level.parse(strFromLogManager);
            }
        } catch (RuntimeException e) {
            reportError(e.getMessage(), e, 4);
        }
        if (this.pushLevel == null) {
            this.pushLevel = Level.OFF;
        }
    }

    private void initPushFilter(String str) {
        try {
            String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".pushFilter"));
            if (hasValue(strFromLogManager)) {
                this.pushFilter = LogManagerProperties.newFilter(strFromLogManager);
            }
        } catch (SecurityException e) {
            throw e;
        } catch (Exception e2) {
            reportError(e2.getMessage(), e2, 4);
        }
    }

    private void initSubject(String str) {
        String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".subject"));
        if (strFromLogManager == null) {
            strFromLogManager = "com.sun.mail.util.logging.CollectorFormatter";
        }
        if (hasValue(strFromLogManager)) {
            try {
                this.subjectFormatter = LogManagerProperties.newFormatter(strFromLogManager);
                return;
            } catch (ClassCastException | ClassNotFoundException unused) {
                this.subjectFormatter = TailNameFormatter.of(strFromLogManager);
                return;
            } catch (SecurityException e) {
                throw e;
            } catch (Exception e2) {
                this.subjectFormatter = TailNameFormatter.of(strFromLogManager);
                reportError(e2.getMessage(), e2, 4);
                return;
            }
        }
        this.subjectFormatter = TailNameFormatter.of(strFromLogManager);
    }

    private boolean isAttachmentLoggable(LogRecord logRecord) {
        Filter[] onlyAttachmentFilters = readOnlyAttachmentFilters();
        for (int i = 0; i < onlyAttachmentFilters.length; i++) {
            Filter filter = onlyAttachmentFilters[i];
            if (filter == null || filter.isLoggable(logRecord)) {
                setMatchedPart(i);
                return true;
            }
        }
        return false;
    }

    private boolean isPushable(LogRecord logRecord) {
        int iIntValue = getPushLevel().intValue();
        if (iIntValue == offValue || logRecord.getLevel().intValue() < iIntValue) {
            return false;
        }
        Filter pushFilter = getPushFilter();
        if (pushFilter == null) {
            return true;
        }
        int matchedPart = getMatchedPart();
        if (!(matchedPart == -1 && getFilter() == pushFilter) && (matchedPart < 0 || this.attachmentFilters[matchedPart] != pushFilter)) {
            return pushFilter.isLoggable(logRecord);
        }
        return true;
    }

    private void push(boolean z, int i) {
        try {
            if (tryMutex()) {
                try {
                    Message messageWriteLogRecords = writeLogRecords(i);
                    if (messageWriteLogRecords != null) {
                        send(messageWriteLogRecords, z, i);
                    }
                } catch (LinkageError e) {
                    reportLinkageError(e, i);
                }
                return;
            }
            reportUnPublishedError(null);
        } finally {
            releaseMutex();
        }
    }

    private void send(Message message, boolean z, int i) {
        try {
            envelopeFor(message, z);
            Object andSetContextClassLoader = getAndSetContextClassLoader(MAILHANDLER_LOADER);
            try {
                Transport.send(message);
                getAndSetContextClassLoader(andSetContextClassLoader);
            } catch (Throwable th) {
                getAndSetContextClassLoader(andSetContextClassLoader);
                throw th;
            }
        } catch (RuntimeException e) {
            reportError(message, e, i);
        } catch (Exception e2) {
            reportError(message, e2, i);
        }
    }

    private void sort() {
        Comparator<? super LogRecord> comparator = this.comparator;
        if (comparator != null) {
            try {
                int i = this.size;
                if (i != 1) {
                    Arrays.sort(this.data, 0, i, comparator);
                    return;
                }
                LogRecord logRecord = this.data[0];
                if (comparator.compare(logRecord, logRecord) != 0) {
                    throw new IllegalArgumentException(this.comparator.getClass().getName());
                }
            } catch (RuntimeException e) {
                reportError(e.getMessage(), e, 5);
            }
        }
    }

    private Message writeLogRecords(int i) {
        try {
        } catch (RuntimeException e) {
            reportError(e.getMessage(), e, i);
            return null;
        } catch (Exception e2) {
            reportError(e2.getMessage(), e2, i);
            return null;
        }
        synchronized (this) {
            if (this.size <= 0 || this.isWriting) {
                return null;
            }
            this.isWriting = true;
            try {
                return writeLogRecords0();
            } finally {
                this.isWriting = false;
                if (this.size > 0) {
                    reset();
                }
            }
            reportError(e.getMessage(), e, i);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Message writeLogRecords0() throws Exception {
        MimePart mimePartCreateBodyPart;
        int i;
        StringBuilder sb;
        Filter filter;
        boolean z;
        sort();
        if (this.session == null) {
            initSession();
        }
        MimeMessage mimeMessage = new MimeMessage(this.session);
        int length = this.attachmentFormatters.length;
        MimeBodyPart[] mimeBodyPartArr = new MimeBodyPart[length];
        StringBuilder[] sbArr = new StringBuilder[length];
        if (length == 0) {
            mimeMessage.setDescription(descriptionFrom(getFormatter(), getFilter(), this.subjectFormatter));
            mimePartCreateBodyPart = mimeMessage;
        } else {
            mimeMessage.setDescription(descriptionFrom(this.comparator, this.pushLevel, this.pushFilter));
            mimePartCreateBodyPart = createBodyPart();
        }
        appendSubject(mimeMessage, head(this.subjectFormatter));
        Formatter formatter = getFormatter();
        Filter filter2 = getFilter();
        LogRecord logRecord = null;
        StringBuilder sb2 = null;
        Object obj = null;
        int i2 = 0;
        while (i2 < this.size) {
            int i3 = this.matched[i2];
            LogRecord[] logRecordArr = this.data;
            LogRecord logRecord2 = logRecordArr[i2];
            logRecordArr[i2] = logRecord;
            Locale localeLocaleFor = localeFor(logRecord2);
            appendSubject(mimeMessage, format(this.subjectFormatter, logRecord2));
            if (filter2 == null || i3 == -1 || length == 0 || (i3 < -1 && filter2.isLoggable(logRecord2))) {
                if (sb2 == null) {
                    sb2 = new StringBuilder();
                    sb2.append(head(formatter));
                }
                sb2.append(format(formatter, logRecord2));
                if (localeLocaleFor != null && !localeLocaleFor.equals(obj)) {
                    appendContentLang(mimePartCreateBodyPart, localeLocaleFor);
                }
                filter = filter2;
                z = true;
            } else {
                z = false;
                filter = null;
            }
            Filter filter3 = filter2;
            Filter filter4 = filter;
            int i4 = 0;
            while (i4 < length) {
                StringBuilder sb3 = sb2;
                Filter filter5 = this.attachmentFilters[i4];
                if (filter5 == null || filter4 == filter5 || i3 == i4 || (i3 < i4 && filter5.isLoggable(logRecord2))) {
                    if (filter4 == null && filter5 != null) {
                        filter4 = filter5;
                    }
                    if (mimeBodyPartArr[i4] == null) {
                        mimeBodyPartArr[i4] = createBodyPart(i4);
                        StringBuilder sb4 = new StringBuilder();
                        sbArr[i4] = sb4;
                        sb4.append(head(this.attachmentFormatters[i4]));
                        appendFileName(mimeBodyPartArr[i4], head(this.attachmentNames[i4]));
                    }
                    appendFileName(mimeBodyPartArr[i4], format(this.attachmentNames[i4], logRecord2));
                    sbArr[i4].append(format(this.attachmentFormatters[i4], logRecord2));
                    if (localeLocaleFor != null && !localeLocaleFor.equals(obj)) {
                        appendContentLang(mimeBodyPartArr[i4], localeLocaleFor);
                    }
                    z = true;
                }
                i4++;
                sb2 = sb3;
            }
            StringBuilder sb5 = sb2;
            if (z) {
                if (mimePartCreateBodyPart != mimeMessage && localeLocaleFor != null && !localeLocaleFor.equals(obj)) {
                    appendContentLang(mimeMessage, localeLocaleFor);
                }
            } else {
                reportFilterError(logRecord2);
            }
            i2++;
            obj = localeLocaleFor;
            filter2 = filter3;
            sb2 = sb5;
            logRecord = null;
        }
        this.size = 0;
        for (int i5 = length - 1; i5 >= 0; i5--) {
            MimeBodyPart mimeBodyPart = mimeBodyPartArr[i5];
            if (mimeBodyPart != null) {
                appendFileName(mimeBodyPart, tail(this.attachmentNames[i5], NotificationCompat.CATEGORY_ERROR));
                sbArr[i5].append(tail(this.attachmentFormatters[i5], ""));
                if (sbArr[i5].length() > 0) {
                    String fileName = mimeBodyPartArr[i5].getFileName();
                    if (isEmpty(fileName)) {
                        fileName = toString(this.attachmentFormatters[i5]);
                        mimeBodyPartArr[i5].setFileName(fileName);
                    }
                    setContent(mimeBodyPartArr[i5], sbArr[i5], getContentType(fileName));
                    sb = null;
                } else {
                    setIncompleteCopy(mimeMessage);
                    sb = null;
                    mimeBodyPartArr[i5] = null;
                }
                sbArr[i5] = sb;
            }
        }
        if (sb2 != null) {
            sb2.append(tail(formatter, ""));
            i = 0;
        } else {
            i = 0;
            sb2 = new StringBuilder(0);
        }
        appendSubject(mimeMessage, tail(this.subjectFormatter, ""));
        String strContentTypeOf = contentTypeOf(sb2);
        String strContentTypeOf2 = contentTypeOf(formatter);
        if (strContentTypeOf2 != null) {
            strContentTypeOf = strContentTypeOf2;
        }
        setContent(mimePartCreateBodyPart, sb2, strContentTypeOf);
        if (mimePartCreateBodyPart != mimeMessage) {
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart((BodyPart) mimePartCreateBodyPart);
            for (int i6 = i; i6 < length; i6++) {
                MimeBodyPart mimeBodyPart2 = mimeBodyPartArr[i6];
                if (mimeBodyPart2 != null) {
                    mimeMultipart.addBodyPart(mimeBodyPart2);
                }
            }
            mimeMessage.setContent(mimeMultipart);
        }
        return mimeMessage;
    }

    private void verifySettings(Session session) throws Throwable {
        if (session != null) {
            try {
                Object objPut = session.getProperties().put("verify", "");
                if (objPut instanceof String) {
                    String str = (String) objPut;
                    if (hasValue(str)) {
                        verifySettings0(session, str);
                    }
                } else if (objPut != null) {
                    verifySettings0(session, objPut.getClass().toString());
                }
            } catch (LinkageError e) {
                reportLinkageError(e, 4);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x0245 A[Catch: Exception -> 0x03a6, RuntimeException -> 0x03af, TryCatch #7 {RuntimeException -> 0x03af, blocks: (B:32:0x00d7, B:34:0x00dd, B:36:0x00e0, B:41:0x00eb, B:43:0x00ee, B:51:0x0114, B:53:0x011d, B:56:0x0127, B:58:0x0155, B:60:0x0161, B:62:0x0184, B:64:0x01be, B:126:0x0272, B:139:0x02a0, B:138:0x0295, B:76:0x01ea, B:63:0x01a2, B:59:0x015c, B:77:0x01f7, B:83:0x020a, B:87:0x0211, B:89:0x0219, B:124:0x026a, B:90:0x021d, B:109:0x023f, B:111:0x0245, B:112:0x024c, B:114:0x0252, B:116:0x0255, B:117:0x025b, B:119:0x0261, B:121:0x0264, B:100:0x0234, B:106:0x023c, B:44:0x00fa, B:45:0x0104, B:39:0x00e5, B:48:0x0107, B:50:0x0111), top: B:208:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x026a A[Catch: Exception -> 0x03a6, RuntimeException -> 0x03af, TryCatch #7 {RuntimeException -> 0x03af, blocks: (B:32:0x00d7, B:34:0x00dd, B:36:0x00e0, B:41:0x00eb, B:43:0x00ee, B:51:0x0114, B:53:0x011d, B:56:0x0127, B:58:0x0155, B:60:0x0161, B:62:0x0184, B:64:0x01be, B:126:0x0272, B:139:0x02a0, B:138:0x0295, B:76:0x01ea, B:63:0x01a2, B:59:0x015c, B:77:0x01f7, B:83:0x020a, B:87:0x0211, B:89:0x0219, B:124:0x026a, B:90:0x021d, B:109:0x023f, B:111:0x0245, B:112:0x024c, B:114:0x0252, B:116:0x0255, B:117:0x025b, B:119:0x0261, B:121:0x0264, B:100:0x0234, B:106:0x023c, B:44:0x00fa, B:45:0x0104, B:39:0x00e5, B:48:0x0107, B:50:0x0111), top: B:208:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x031f A[Catch: RuntimeException -> 0x03a3, Exception -> 0x03a6, TryCatch #18 {RuntimeException -> 0x03a3, blocks: (B:150:0x0304, B:159:0x0311, B:156:0x030c, B:157:0x030f, B:160:0x031c, B:162:0x031f, B:164:0x032e, B:165:0x0334, B:167:0x033e, B:169:0x0341, B:170:0x0345, B:172:0x0348, B:174:0x0350, B:175:0x0353, B:176:0x0376, B:178:0x0379, B:179:0x0381, B:180:0x038d, B:181:0x038e, B:182:0x0395, B:189:0x039f, B:190:0x03a2), top: B:222:0x00e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:181:0x038e A[Catch: RuntimeException -> 0x03a3, Exception -> 0x03a6, TryCatch #18 {RuntimeException -> 0x03a3, blocks: (B:150:0x0304, B:159:0x0311, B:156:0x030c, B:157:0x030f, B:160:0x031c, B:162:0x031f, B:164:0x032e, B:165:0x0334, B:167:0x033e, B:169:0x0341, B:170:0x0345, B:172:0x0348, B:174:0x0350, B:175:0x0353, B:176:0x0376, B:178:0x0379, B:179:0x0381, B:180:0x038d, B:181:0x038e, B:182:0x0395, B:189:0x039f, B:190:0x03a2), top: B:222:0x00e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x027a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void verifySettings0(javax.mail.Session r17, java.lang.String r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 955
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.logging.MailHandler.verifySettings0(javax.mail.Session, java.lang.String):void");
    }

    private void saveChangesNoContent(Message message, String str) {
        if (message != null) {
            try {
                try {
                    message.saveChanges();
                } catch (RuntimeException | MessagingException e) {
                    reportError(str, e, 5);
                }
            } catch (NullPointerException e2) {
                try {
                    if (message.getHeader("Content-Transfer-Encoding") == null) {
                        message.setHeader("Content-Transfer-Encoding", "base64");
                        message.saveChanges();
                        return;
                    }
                    throw e2;
                } catch (RuntimeException | MessagingException e3) {
                    if (e3 != e2) {
                        e3.addSuppressed(e2);
                    }
                    throw e3;
                }
            }
        }
    }

    private static void verifyProperties(Session session, String str) {
        session.getProperty("mail.from");
        session.getProperty("mail." + str + ".from");
        session.getProperty("mail.dsn.ret");
        session.getProperty("mail." + str + ".dsn.ret");
        session.getProperty("mail.dsn.notify");
        session.getProperty("mail." + str + ".dsn.notify");
        session.getProperty("mail." + str + ".port");
        session.getProperty("mail.user");
        session.getProperty("mail." + str + ".user");
        session.getProperty("mail." + str + ".localport");
    }

    private static InetAddress verifyHost(String str) throws IOException {
        InetAddress byName;
        if (isEmpty(str)) {
            byName = InetAddress.getLocalHost();
        } else {
            byName = InetAddress.getByName(str);
        }
        if (byName.getCanonicalHostName().length() != 0) {
            return byName;
        }
        throw new UnknownHostException();
    }

    private static void verifyAddresses(Address[] addressArr) throws AddressException {
        if (addressArr != null) {
            for (Address address : addressArr) {
                if (address instanceof InternetAddress) {
                    ((InternetAddress) address).validate();
                }
            }
        }
    }

    private void reportUnexpectedSend(MimeMessage mimeMessage, String str, Exception exc) {
        Exception messagingException = new MessagingException("An empty message was sent.", exc);
        setErrorContent(mimeMessage, str, messagingException);
        reportError(mimeMessage, messagingException, 4);
    }

    private void setErrorContent(MimeMessage mimeMessage, String str, Throwable th) {
        MimeBodyPart mimeBodyPartCreateBodyPart;
        String strDescriptionFrom;
        String classId;
        String name;
        try {
            synchronized (this) {
                mimeBodyPartCreateBodyPart = createBodyPart();
                strDescriptionFrom = descriptionFrom(this.comparator, this.pushLevel, this.pushFilter);
                classId = getClassId(this.subjectFormatter);
            }
            StringBuilder sb = new StringBuilder("Formatted using ");
            if (th == null) {
                name = Throwable.class.getName();
            } else {
                name = th.getClass().getName();
            }
            mimeBodyPartCreateBodyPart.setDescription(sb.append(name).append(", filtered with ").append(str).append(", and named by ").append(classId).append('.').toString());
            setContent(mimeBodyPartCreateBodyPart, toMsgString(th), AssetHelper.DEFAULT_MIME_TYPE);
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPartCreateBodyPart);
            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setDescription(strDescriptionFrom);
            setAcceptLang(mimeMessage);
            mimeMessage.saveChanges();
        } catch (RuntimeException | MessagingException e) {
            reportError("Unable to create body.", e, 4);
        }
    }

    private Session updateSession() {
        if (this.mailProps.getProperty("verify") != null) {
            return initSession();
        }
        this.session = null;
        return null;
    }

    private Session initSession() {
        Session session = Session.getInstance(new LogManagerProperties(this.mailProps, getClass().getName()), this.auth);
        this.session = session;
        return session;
    }

    private void envelopeFor(Message message, boolean z) {
        setAcceptLang(message);
        setFrom(message);
        if (!setRecipient(message, "mail.to", Message.RecipientType.TO)) {
            setDefaultRecipient(message, Message.RecipientType.TO);
        }
        setRecipient(message, "mail.cc", Message.RecipientType.CC);
        setRecipient(message, "mail.bcc", Message.RecipientType.BCC);
        setReplyTo(message);
        setSender(message);
        setMailer(message);
        setAutoSubmitted(message);
        if (z) {
            setPriority(message);
        }
        try {
            message.setSentDate(new Date());
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private MimeBodyPart createBodyPart() throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setDisposition(Part.INLINE);
        mimeBodyPart.setDescription(descriptionFrom(getFormatter(), getFilter(), this.subjectFormatter));
        setAcceptLang(mimeBodyPart);
        return mimeBodyPart;
    }

    private MimeBodyPart createBodyPart(int i) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setDisposition(Part.ATTACHMENT);
        mimeBodyPart.setDescription(descriptionFrom(this.attachmentFormatters[i], this.attachmentFilters[i], this.attachmentNames[i]));
        setAcceptLang(mimeBodyPart);
        return mimeBodyPart;
    }

    private String descriptionFrom(Comparator<?> comparator, Level level, Filter filter) {
        return "Sorted using " + (comparator == null ? "no comparator" : comparator.getClass().getName()) + ", pushed when " + level.getName() + ", and " + (filter == null ? "no push filter" : filter.getClass().getName()) + '.';
    }

    private String descriptionFrom(Formatter formatter, Filter filter, Formatter formatter2) {
        return "Formatted using " + getClassId(formatter) + ", filtered with " + (filter == null ? "no filter" : filter.getClass().getName()) + ", and named by " + getClassId(formatter2) + '.';
    }

    private String getClassId(Formatter formatter) {
        if (formatter instanceof TailNameFormatter) {
            return String.class.getName();
        }
        return formatter.getClass().getName();
    }

    private String toString(Formatter formatter) {
        String string = formatter.toString();
        return !isEmpty(string) ? string : getClassId(formatter);
    }

    private void appendFileName(Part part, String str) {
        if (str != null) {
            if (str.length() > 0) {
                appendFileName0(part, str);
                return;
            }
            return;
        }
        reportNullError(5);
    }

    private void appendFileName0(Part part, String str) {
        try {
            String strReplaceAll = str.replaceAll("[\\x00-\\x1F\\x7F]+", "");
            String fileName = part.getFileName();
            if (fileName != null) {
                strReplaceAll = fileName.concat(strReplaceAll);
            }
            part.setFileName(strReplaceAll);
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private void appendSubject(Message message, String str) {
        if (str != null) {
            if (str.length() > 0) {
                appendSubject0(message, str);
                return;
            }
            return;
        }
        reportNullError(5);
    }

    private void appendSubject0(Message message, String str) {
        try {
            String strReplaceAll = str.replaceAll("[\\x00-\\x1F\\x7F]+", "");
            String encodingName = getEncodingName();
            String subject = message.getSubject();
            MimeMessage mimeMessage = (MimeMessage) message;
            if (subject != null) {
                strReplaceAll = subject.concat(strReplaceAll);
            }
            mimeMessage.setSubject(strReplaceAll, MimeUtility.mimeCharset(encodingName));
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private Locale localeFor(LogRecord logRecord) {
        ResourceBundle resourceBundle = logRecord.getResourceBundle();
        if (resourceBundle == null) {
            return null;
        }
        Locale locale = resourceBundle.getLocale();
        return (locale == null || isEmpty(locale.getLanguage())) ? Locale.getDefault() : locale;
    }

    private void appendContentLang(MimePart mimePart, Locale locale) {
        int length;
        String strConcat;
        try {
            String languageTag = LogManagerProperties.toLanguageTag(locale);
            if (languageTag.length() != 0) {
                String header = mimePart.getHeader("Content-Language", null);
                if (isEmpty(header)) {
                    mimePart.setHeader("Content-Language", languageTag);
                    return;
                }
                if (header.equalsIgnoreCase(languageTag)) {
                    return;
                }
                String strConcat2 = ",".concat(languageTag);
                int iIndexOf = 0;
                do {
                    iIndexOf = header.indexOf(strConcat2, iIndexOf);
                    if (iIndexOf <= -1 || (iIndexOf = iIndexOf + strConcat2.length()) == header.length()) {
                        break;
                    }
                } while (header.charAt(iIndexOf) != ',');
                if (iIndexOf < 0) {
                    int iLastIndexOf = header.lastIndexOf("\r\n\t");
                    if (iLastIndexOf < 0) {
                        length = header.length() + 20;
                    } else {
                        length = (header.length() - iLastIndexOf) + 8;
                    }
                    if (length + strConcat2.length() > 76) {
                        strConcat = header.concat("\r\n\t".concat(strConcat2));
                    } else {
                        strConcat = header.concat(strConcat2);
                    }
                    mimePart.setHeader("Content-Language", strConcat);
                }
            }
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private void setAcceptLang(Part part) {
        try {
            String languageTag = LogManagerProperties.toLanguageTag(Locale.getDefault());
            if (languageTag.length() != 0) {
                part.setHeader("Accept-Language", languageTag);
            }
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private void reportFilterError(LogRecord logRecord) {
        Formatter formatterCreateSimpleFormatter = createSimpleFormatter();
        reportError("Log record " + logRecord.getSequenceNumber() + " was filtered from all message parts.  " + head(formatterCreateSimpleFormatter) + format(formatterCreateSimpleFormatter, logRecord) + tail(formatterCreateSimpleFormatter, ""), new IllegalArgumentException(getFilter() + ", " + Arrays.asList(readOnlyAttachmentFilters())), 5);
    }

    private void reportNonSymmetric(Object obj, Object obj2) {
        reportError("Non symmetric equals implementation.", new IllegalArgumentException(obj.getClass().getName() + " is not equal to " + obj2.getClass().getName()), 4);
    }

    private void reportNonDiscriminating(Object obj, Object obj2) {
        reportError("Non discriminating equals implementation.", new IllegalArgumentException(obj.getClass().getName() + " should not be equal to " + obj2.getClass().getName()), 4);
    }

    private void reportNullError(int i) {
        reportError("null", new NullPointerException(), i);
    }

    private String head(Formatter formatter) {
        try {
            return formatter.getHead(this);
        } catch (RuntimeException e) {
            reportError(e.getMessage(), e, 5);
            return "";
        }
    }

    private String format(Formatter formatter, LogRecord logRecord) {
        try {
            return formatter.format(logRecord);
        } catch (RuntimeException e) {
            reportError(e.getMessage(), e, 5);
            return "";
        }
    }

    private String tail(Formatter formatter, String str) {
        try {
            return formatter.getTail(this);
        } catch (RuntimeException e) {
            reportError(e.getMessage(), e, 5);
            return str;
        }
    }

    private void setMailer(Message message) {
        String strReplaceAll;
        String strFold;
        try {
            Class<?> cls = getClass();
            if (cls == MailHandler.class) {
                strFold = MailHandler.class.getName();
            } else {
                try {
                    strReplaceAll = MimeUtility.encodeText(cls.getName());
                } catch (UnsupportedEncodingException e) {
                    reportError(e.getMessage(), e, 5);
                    strReplaceAll = cls.getName().replaceAll("[^\\x00-\\x7F]", "\u001a");
                }
                strFold = MimeUtility.fold(10, MailHandler.class.getName() + " using the " + strReplaceAll + " extension.");
            }
            message.setHeader("X-Mailer", strFold);
        } catch (MessagingException e2) {
            reportError(e2.getMessage(), e2, 5);
        }
    }

    private void setPriority(Message message) {
        try {
            message.setHeader("Importance", "High");
            message.setHeader("Priority", "urgent");
            message.setHeader("X-Priority", ExifInterface.GPS_MEASUREMENT_2D);
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private void setIncompleteCopy(Message message) {
        try {
            message.setHeader("Incomplete-Copy", "");
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private void setAutoSubmitted(Message message) {
        if (allowRestrictedHeaders()) {
            try {
                message.setHeader("auto-submitted", "auto-generated");
            } catch (MessagingException e) {
                reportError(e.getMessage(), e, 5);
            }
        }
    }

    private void setFrom(Message message) {
        String property = getSession(message).getProperty("mail.from");
        if (property != null) {
            try {
                InternetAddress[] internetAddressArr = InternetAddress.parse(property, false);
                if (internetAddressArr.length > 0) {
                    if (internetAddressArr.length == 1) {
                        message.setFrom(internetAddressArr[0]);
                    } else {
                        message.addFrom(internetAddressArr);
                    }
                }
                return;
            } catch (MessagingException e) {
                reportError(e.getMessage(), e, 5);
                setDefaultFrom(message);
                return;
            }
        }
        setDefaultFrom(message);
    }

    private void setDefaultFrom(Message message) {
        try {
            message.setFrom();
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private void setDefaultRecipient(Message message, Message.RecipientType recipientType) {
        try {
            InternetAddress localAddress = InternetAddress.getLocalAddress(getSession(message));
            if (localAddress != null) {
                message.setRecipient(recipientType, localAddress);
                return;
            }
            MimeMessage mimeMessage = new MimeMessage(getSession(message));
            mimeMessage.setFrom();
            Address[] from = mimeMessage.getFrom();
            if (from.length > 0) {
                message.setRecipients(recipientType, from);
                return;
            }
            throw new MessagingException("No local address.");
        } catch (RuntimeException | MessagingException e) {
            reportError("Unable to compute a default recipient.", e, 5);
        }
    }

    private void setReplyTo(Message message) {
        String property = getSession(message).getProperty("mail.reply.to");
        if (isEmpty(property)) {
            return;
        }
        try {
            InternetAddress[] internetAddressArr = InternetAddress.parse(property, false);
            if (internetAddressArr.length > 0) {
                message.setReplyTo(internetAddressArr);
            }
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private void setSender(Message message) {
        String property = getSession(message).getProperty("mail.sender");
        if (isEmpty(property)) {
            return;
        }
        try {
            Address[] addressArr = InternetAddress.parse(property, false);
            if (addressArr.length > 0) {
                ((MimeMessage) message).setSender(addressArr[0]);
                if (addressArr.length > 1) {
                    reportError("Ignoring other senders.", tooManyAddresses(addressArr, 1), 5);
                }
            }
        } catch (MessagingException e) {
            reportError(e.getMessage(), e, 5);
        }
    }

    private AddressException tooManyAddresses(Address[] addressArr, int i) {
        return new AddressException(Arrays.asList(addressArr).subList(i, addressArr.length).toString());
    }

    private boolean setRecipient(Message message, String str, Message.RecipientType recipientType) {
        String property = getSession(message).getProperty(str);
        boolean z = property != null;
        if (!isEmpty(property)) {
            try {
                InternetAddress[] internetAddressArr = InternetAddress.parse(property, false);
                if (internetAddressArr.length > 0) {
                    message.setRecipients(recipientType, internetAddressArr);
                }
            } catch (MessagingException e) {
                reportError(e.getMessage(), e, 5);
            }
        }
        return z;
    }

    private String toRawString(Message message) throws MessagingException, IOException {
        if (message == null) {
            return null;
        }
        Object andSetContextClassLoader = getAndSetContextClassLoader(MAILHANDLER_LOADER);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(message.getSize() + 1024, 1024));
            message.writeTo(byteArrayOutputStream);
            return byteArrayOutputStream.toString("UTF-8");
        } finally {
            getAndSetContextClassLoader(andSetContextClassLoader);
        }
    }

    private String toMsgString(Throwable th) {
        if (th == null) {
            return "null";
        }
        String encodingName = getEncodingName();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, encodingName);
            try {
                PrintWriter printWriter = new PrintWriter(outputStreamWriter);
                try {
                    printWriter.println(th.getMessage());
                    th.printStackTrace(printWriter);
                    printWriter.flush();
                    printWriter.close();
                    outputStreamWriter.close();
                    return byteArrayOutputStream.toString(encodingName);
                } finally {
                }
            } finally {
            }
        } catch (RuntimeException e) {
            return th.toString() + ' ' + e.toString();
        } catch (Exception e2) {
            return th.toString() + ' ' + e2.toString();
        }
    }

    private Object getAndSetContextClassLoader(Object obj) {
        PrivilegedAction getAndSetContext;
        if (obj != GetAndSetContext.NOT_MODIFIED) {
            try {
                if (obj instanceof PrivilegedAction) {
                    getAndSetContext = (PrivilegedAction) obj;
                } else {
                    getAndSetContext = new GetAndSetContext(obj);
                }
                return AccessController.doPrivileged(getAndSetContext);
            } catch (SecurityException unused) {
            }
        }
        return GetAndSetContext.NOT_MODIFIED;
    }

    private static RuntimeException attachmentMismatch(String str) {
        return new IndexOutOfBoundsException(str);
    }

    private static RuntimeException attachmentMismatch(int i, int i2) {
        return attachmentMismatch("Attachments mismatched, expected " + i + " but given " + i2 + '.');
    }

    private static MessagingException attach(MessagingException messagingException, Exception exc) {
        if (exc != null && !messagingException.setNextException(exc)) {
            if (exc instanceof MessagingException) {
                MessagingException messagingException2 = (MessagingException) exc;
                if (messagingException2.setNextException(messagingException)) {
                    return messagingException2;
                }
            }
            if (exc != messagingException) {
                messagingException.addSuppressed(exc);
            }
        }
        return messagingException;
    }

    private String getLocalHost(Service service) {
        try {
            return LogManagerProperties.getLocalHost(service);
        } catch (Exception e) {
            reportError(service.toString(), e, 4);
            return null;
        } catch (LinkageError | NoSuchMethodException | SecurityException unused) {
            return null;
        }
    }

    private Session getSession(Message message) {
        message.getClass();
        return new MessageContext(message).getSession();
    }

    private boolean allowRestrictedHeaders() {
        return LogManagerProperties.hasLogManager();
    }

    private static String atIndexMsg(int i) {
        return "At index: " + i + '.';
    }

    private static final class DefaultAuthenticator extends Authenticator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final String pass;

        static Authenticator of(String str) {
            return new DefaultAuthenticator(str);
        }

        private DefaultAuthenticator(String str) {
            this.pass = str;
        }

        @Override // javax.mail.Authenticator
        protected final PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(getDefaultUserName(), this.pass);
        }
    }

    private static final class GetAndSetContext implements PrivilegedAction<Object> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public static final Object NOT_MODIFIED = GetAndSetContext.class;
        private final Object source;

        GetAndSetContext(Object obj) {
            this.source = obj;
        }

        @Override // java.security.PrivilegedAction
        public final Object run() {
            ClassLoader classLoader;
            Thread threadCurrentThread = Thread.currentThread();
            ClassLoader contextClassLoader = threadCurrentThread.getContextClassLoader();
            Object obj = this.source;
            if (obj == null) {
                classLoader = null;
            } else if (obj instanceof ClassLoader) {
                classLoader = (ClassLoader) obj;
            } else if (obj instanceof Class) {
                classLoader = ((Class) obj).getClassLoader();
            } else if (obj instanceof Thread) {
                classLoader = ((Thread) obj).getContextClassLoader();
            } else {
                classLoader = obj.getClass().getClassLoader();
            }
            if (contextClassLoader != classLoader) {
                threadCurrentThread.setContextClassLoader(classLoader);
                return contextClassLoader;
            }
            return NOT_MODIFIED;
        }
    }

    private static final class TailNameFormatter extends Formatter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final String name;

        @Override // java.util.logging.Formatter
        public final String format(LogRecord logRecord) {
            return "";
        }

        static Formatter of(String str) {
            return new TailNameFormatter(str);
        }

        private TailNameFormatter(String str) {
            this.name = str;
        }

        @Override // java.util.logging.Formatter
        public final String getTail(Handler handler) {
            return this.name;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof TailNameFormatter) {
                return this.name.equals(((TailNameFormatter) obj).name);
            }
            return false;
        }

        public final int hashCode() {
            return getClass().hashCode() + this.name.hashCode();
        }

        public final String toString() {
            return this.name;
        }
    }
}
