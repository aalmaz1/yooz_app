package javax.mail;

import java.util.EventListener;
import java.util.Vector;
import java.util.concurrent.Executor;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.MailEvent;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Service implements AutoCloseable {
    private boolean connected = false;
    private final Vector<ConnectionListener> connectionListeners = new Vector<>();
    protected boolean debug;
    private final EventQueue q;
    protected Session session;
    protected volatile URLName url;

    protected boolean protocolConnect(String str, int i, String str2, String str3) throws MessagingException {
        return false;
    }

    protected Service(Session session, URLName uRLName) {
        String property;
        String str;
        String file;
        String password;
        int i;
        String property2;
        String property3 = null;
        this.url = null;
        this.debug = false;
        this.session = session;
        this.debug = session.getDebug();
        this.url = uRLName;
        if (this.url != null) {
            String protocol = this.url.getProtocol();
            String host = this.url.getHost();
            int port = this.url.getPort();
            property = this.url.getUsername();
            i = port;
            password = this.url.getPassword();
            file = this.url.getFile();
            str = protocol;
            property3 = host;
        } else {
            property = null;
            str = null;
            file = null;
            password = null;
            i = -1;
        }
        if (str != null) {
            property3 = property3 == null ? session.getProperty("mail." + str + ".host") : property3;
            if (property == null) {
                property = session.getProperty("mail." + str + ".user");
            }
        }
        String property4 = property3 == null ? session.getProperty("mail.host") : property3;
        property = property == null ? session.getProperty("mail.user") : property;
        if (property == null) {
            try {
                property2 = System.getProperty("user.name");
            } catch (SecurityException unused) {
                property2 = property;
            }
        } else {
            property2 = property;
        }
        this.url = new URLName(str, property4, i, file, property2, password);
        String property5 = session.getProperties().getProperty("mail.event.scope", "folder");
        Executor executor = (Executor) session.getProperties().get("mail.event.executor");
        if (property5.equalsIgnoreCase("application")) {
            this.q = EventQueue.getApplicationEventQueue(executor);
        } else if (property5.equalsIgnoreCase("session")) {
            this.q = session.getEventQueue();
        } else {
            this.q = new EventQueue(executor);
        }
    }

    public void connect() throws MessagingException {
        connect(null, null, null);
    }

    public void connect(String str, String str2, String str3) throws MessagingException {
        connect(str, -1, str2, str3);
    }

    public void connect(String str, String str2) throws MessagingException {
        connect(null, str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0058 A[PHI: r0
      0x0058: PHI (r0v26 java.lang.String) = (r0v0 java.lang.String), (r0v0 java.lang.String), (r0v29 java.lang.String) binds: [B:20:0x0043, B:22:0x004f, B:18:0x003a] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void connect(java.lang.String r19, int r20, java.lang.String r21, java.lang.String r22) throws javax.mail.MessagingException {
        /*
            Method dump skipped, instruction units count: 394
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.mail.Service.connect(java.lang.String, int, java.lang.String, java.lang.String):void");
    }

    public synchronized boolean isConnected() {
        return this.connected;
    }

    protected synchronized void setConnected(boolean z) {
        this.connected = z;
    }

    @Override // java.lang.AutoCloseable
    public synchronized void close() throws MessagingException {
        setConnected(false);
        notifyConnectionListeners(3);
    }

    public URLName getURLName() {
        URLName uRLName = this.url;
        return (uRLName == null || (uRLName.getPassword() == null && uRLName.getFile() == null)) ? uRLName : new URLName(uRLName.getProtocol(), uRLName.getHost(), uRLName.getPort(), null, uRLName.getUsername(), null);
    }

    protected void setURLName(URLName uRLName) {
        this.url = uRLName;
    }

    public void addConnectionListener(ConnectionListener connectionListener) {
        this.connectionListeners.addElement(connectionListener);
    }

    public void removeConnectionListener(ConnectionListener connectionListener) {
        this.connectionListeners.removeElement(connectionListener);
    }

    protected void notifyConnectionListeners(int i) {
        if (this.connectionListeners.size() > 0) {
            queueEvent(new ConnectionEvent(this, i), this.connectionListeners);
        }
        if (i == 3) {
            this.q.terminateQueue();
        }
    }

    public String toString() {
        URLName uRLName = getURLName();
        if (uRLName != null) {
            return uRLName.toString();
        }
        return super.toString();
    }

    protected void queueEvent(MailEvent mailEvent, Vector<? extends EventListener> vector) {
        this.q.enqueue(mailEvent, (Vector) vector.clone());
    }

    protected void finalize() throws Throwable {
        try {
            this.q.terminateQueue();
        } finally {
            super.finalize();
        }
    }

    Session getSession() {
        return this.session;
    }

    EventQueue getEventQueue() {
        return this.q;
    }
}
