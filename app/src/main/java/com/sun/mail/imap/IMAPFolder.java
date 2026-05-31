package com.sun.mail.imap;

import com.sun.mail.iap.BadCommandException;
import com.sun.mail.iap.CommandFailedException;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.iap.ResponseHandler;
import com.sun.mail.imap.IMAPMessage;
import com.sun.mail.imap.protocol.FLAGS;
import com.sun.mail.imap.protocol.FetchItem;
import com.sun.mail.imap.protocol.FetchResponse;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.IMAPResponse;
import com.sun.mail.imap.protocol.Item;
import com.sun.mail.imap.protocol.ListInfo;
import com.sun.mail.imap.protocol.MODSEQ;
import com.sun.mail.imap.protocol.MailboxInfo;
import com.sun.mail.imap.protocol.MessageSet;
import com.sun.mail.imap.protocol.Status;
import com.sun.mail.imap.protocol.UID;
import com.sun.mail.imap.protocol.UIDSet;
import com.sun.mail.util.MailLogger;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.ReadOnlyFolderException;
import javax.mail.StoreClosedException;
import javax.mail.UIDFolder;
import javax.mail.event.MailEvent;
import javax.mail.event.MessageChangedEvent;
import javax.mail.event.MessageCountListener;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class IMAPFolder extends Folder implements UIDFolder, ResponseHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ABORTING = 2;
    private static final int IDLE = 1;
    private static final int RUNNING = 0;
    protected static final char UNKNOWN_SEPARATOR = 65535;
    protected volatile String[] attributes;
    protected Flags availableFlags;
    private Status cachedStatus;
    private long cachedStatusTime;
    private MailLogger connectionPoolLogger;
    private boolean doExpungeNotification;
    protected volatile boolean exists;
    protected volatile String fullName;
    private boolean hasMessageCountListener;
    private volatile long highestmodseq;
    private IdleManager idleManager;
    private int idleState;
    protected boolean isNamespace;
    protected MailLogger logger;
    protected MessageCache messageCache;
    protected final Object messageCacheLock;
    protected String name;
    private volatile boolean opened;
    protected Flags permanentFlags;
    protected volatile IMAPProtocol protocol;
    private int realTotal;
    private boolean reallyClosed;
    private volatile int recent;
    protected char separator;
    private volatile int total;
    protected int type;
    private boolean uidNotSticky;
    protected Hashtable<Long, IMAPMessage> uidTable;
    private long uidnext;
    private long uidvalidity;

    public interface ProtocolCommand {
        Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException;
    }

    protected String getEnvelopeCommand() {
        return "ENVELOPE INTERNALDATE RFC822.SIZE";
    }

    public static class FetchProfileItem extends FetchProfile.Item {
        public static final FetchProfileItem HEADERS = new FetchProfileItem("HEADERS");

        @Deprecated
        public static final FetchProfileItem SIZE = new FetchProfileItem("SIZE");
        public static final FetchProfileItem MESSAGE = new FetchProfileItem("MESSAGE");
        public static final FetchProfileItem INTERNALDATE = new FetchProfileItem("INTERNALDATE");

        protected FetchProfileItem(String str) {
            super(str);
        }
    }

    protected IMAPFolder(String str, char c, IMAPStore iMAPStore, Boolean bool) {
        int iIndexOf;
        super(iMAPStore);
        this.isNamespace = false;
        this.messageCacheLock = new Object();
        this.opened = false;
        this.reallyClosed = true;
        this.idleState = 0;
        this.total = -1;
        this.recent = -1;
        this.realTotal = -1;
        this.uidvalidity = -1L;
        this.uidnext = -1L;
        this.uidNotSticky = false;
        this.highestmodseq = -1L;
        this.doExpungeNotification = true;
        this.cachedStatus = null;
        this.cachedStatusTime = 0L;
        this.hasMessageCountListener = false;
        if (str == null) {
            throw new NullPointerException("Folder name is null");
        }
        this.fullName = str;
        this.separator = c;
        this.logger = new MailLogger(getClass(), "DEBUG IMAP", iMAPStore.getSession().getDebug(), iMAPStore.getSession().getDebugOut());
        this.connectionPoolLogger = iMAPStore.getConnectionPoolLogger();
        this.isNamespace = false;
        if (c != 65535 && c != 0 && (iIndexOf = this.fullName.indexOf(c)) > 0 && iIndexOf == this.fullName.length() - 1) {
            this.fullName = this.fullName.substring(0, iIndexOf);
            this.isNamespace = true;
        }
        if (bool != null) {
            this.isNamespace = bool.booleanValue();
        }
    }

    protected IMAPFolder(ListInfo listInfo, IMAPStore iMAPStore) {
        this(listInfo.name, listInfo.separator, iMAPStore, null);
        if (listInfo.hasInferiors) {
            this.type |= 2;
        }
        if (listInfo.canOpen) {
            this.type |= 1;
        }
        this.exists = true;
        this.attributes = listInfo.attrs;
    }

    protected void checkExists() throws MessagingException {
        if (!this.exists && !exists()) {
            throw new FolderNotFoundException(this, this.fullName + " not found");
        }
    }

    protected void checkClosed() {
        if (this.opened) {
            throw new IllegalStateException("This operation is not allowed on an open folder");
        }
    }

    protected void checkOpened() throws FolderClosedException {
        if (this.opened) {
            return;
        }
        if (this.reallyClosed) {
            throw new IllegalStateException("This operation is not allowed on a closed folder");
        }
        throw new FolderClosedException(this, "Lost folder connection to server");
    }

    protected void checkRange(int i) throws MessagingException {
        if (i < 1) {
            throw new IndexOutOfBoundsException("message number < 1");
        }
        if (i <= this.total) {
            return;
        }
        synchronized (this.messageCacheLock) {
            try {
                try {
                    keepConnectionAlive(false);
                } catch (ProtocolException e) {
                    throw new MessagingException(e.getMessage(), e);
                }
            } catch (ConnectionException e2) {
                throw new FolderClosedException(this, e2.getMessage());
            }
        }
        if (i > this.total) {
            throw new IndexOutOfBoundsException(i + " > " + this.total);
        }
    }

    private void checkFlags(Flags flags) throws MessagingException {
        if (this.mode != 2) {
            throw new IllegalStateException("Cannot change flags on READ_ONLY folder: " + this.fullName);
        }
    }

    @Override // javax.mail.Folder
    public synchronized String getName() {
        if (this.name == null) {
            try {
                this.name = this.fullName.substring(this.fullName.lastIndexOf(getSeparator()) + 1);
            } catch (MessagingException unused) {
            }
        }
        return this.name;
    }

    @Override // javax.mail.Folder
    public String getFullName() {
        return this.fullName;
    }

    @Override // javax.mail.Folder
    public synchronized Folder getParent() throws MessagingException {
        char separator = getSeparator();
        int iLastIndexOf = this.fullName.lastIndexOf(separator);
        if (iLastIndexOf != -1) {
            return ((IMAPStore) this.store).newIMAPFolder(this.fullName.substring(0, iLastIndexOf), separator);
        }
        return new DefaultFolder((IMAPStore) this.store);
    }

    @Override // javax.mail.Folder
    public synchronized boolean exists() throws MessagingException {
        final String str;
        if (this.isNamespace && this.separator != 0) {
            str = this.fullName + this.separator;
        } else {
            str = this.fullName;
        }
        ListInfo[] listInfoArr = (ListInfo[]) doCommand(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.1
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.list("", str);
            }
        });
        if (listInfoArr != null) {
            int iFindName = findName(listInfoArr, str);
            this.fullName = listInfoArr[iFindName].name;
            this.separator = listInfoArr[iFindName].separator;
            int length = this.fullName.length();
            if (this.separator != 0 && length > 0) {
                int i = length - 1;
                if (this.fullName.charAt(i) == this.separator) {
                    this.fullName = this.fullName.substring(0, i);
                }
            }
            this.type = 0;
            if (listInfoArr[iFindName].hasInferiors) {
                this.type |= 2;
            }
            if (listInfoArr[iFindName].canOpen) {
                this.type |= 1;
            }
            this.exists = true;
            this.attributes = listInfoArr[iFindName].attrs;
        } else {
            this.exists = this.opened;
            this.attributes = null;
        }
        return this.exists;
    }

    private int findName(ListInfo[] listInfoArr, String str) {
        int i = 0;
        while (i < listInfoArr.length && !listInfoArr[i].name.equals(str)) {
            i++;
        }
        if (i >= listInfoArr.length) {
            return 0;
        }
        return i;
    }

    @Override // javax.mail.Folder
    public Folder[] list(String str) throws MessagingException {
        return doList(str, false);
    }

    @Override // javax.mail.Folder
    public Folder[] listSubscribed(String str) throws MessagingException {
        return doList(str, true);
    }

    private synchronized Folder[] doList(final String str, final boolean z) throws MessagingException {
        checkExists();
        int i = 0;
        if (this.attributes != null && !isDirectory()) {
            return new Folder[0];
        }
        final char separator = getSeparator();
        ListInfo[] listInfoArr = (ListInfo[]) doCommandIgnoreFailure(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.2
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                if (z) {
                    return iMAPProtocol.lsub("", IMAPFolder.this.fullName + separator + str);
                }
                return iMAPProtocol.list("", IMAPFolder.this.fullName + separator + str);
            }
        });
        if (listInfoArr == null) {
            return new Folder[0];
        }
        if (listInfoArr.length > 0 && listInfoArr[0].name.equals(this.fullName + separator)) {
            i = 1;
        }
        IMAPFolder[] iMAPFolderArr = new IMAPFolder[listInfoArr.length - i];
        IMAPStore iMAPStore = (IMAPStore) this.store;
        for (int i2 = i; i2 < listInfoArr.length; i2++) {
            iMAPFolderArr[i2 - i] = iMAPStore.newIMAPFolder(listInfoArr[i2]);
        }
        return iMAPFolderArr;
    }

    @Override // javax.mail.Folder
    public synchronized char getSeparator() throws MessagingException {
        if (this.separator == 65535) {
            ListInfo[] listInfoArr = (ListInfo[]) doCommand(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.3
                @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    if (iMAPProtocol.isREV1()) {
                        return iMAPProtocol.list(IMAPFolder.this.fullName, "");
                    }
                    return iMAPProtocol.list("", IMAPFolder.this.fullName);
                }
            });
            if (listInfoArr != null) {
                this.separator = listInfoArr[0].separator;
            } else {
                this.separator = IOUtils.DIR_SEPARATOR_UNIX;
            }
        }
        return this.separator;
    }

    @Override // javax.mail.Folder
    public synchronized int getType() throws MessagingException {
        if (this.opened) {
            if (this.attributes == null) {
                exists();
            }
        } else {
            checkExists();
        }
        return this.type;
    }

    @Override // javax.mail.Folder
    public synchronized boolean isSubscribed() {
        final String str;
        ListInfo[] listInfoArr;
        if (this.isNamespace && this.separator != 0) {
            str = this.fullName + this.separator;
        } else {
            str = this.fullName;
        }
        try {
            listInfoArr = (ListInfo[]) doProtocolCommand(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.4
                @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    return iMAPProtocol.lsub("", str);
                }
            });
        } catch (ProtocolException unused) {
            listInfoArr = null;
        }
        if (listInfoArr == null) {
            return false;
        }
        return listInfoArr[findName(listInfoArr, str)].canOpen;
    }

    @Override // javax.mail.Folder
    public synchronized void setSubscribed(final boolean z) throws MessagingException {
        doCommandIgnoreFailure(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.5
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                if (z) {
                    iMAPProtocol.subscribe(IMAPFolder.this.fullName);
                    return null;
                }
                iMAPProtocol.unsubscribe(IMAPFolder.this.fullName);
                return null;
            }
        });
    }

    @Override // javax.mail.Folder
    public synchronized boolean create(final int i) throws MessagingException {
        final char separator = (i & 1) == 0 ? getSeparator() : (char) 0;
        if (doCommandIgnoreFailure(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.6
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                ListInfo[] list;
                if ((i & 1) == 0) {
                    iMAPProtocol.create(IMAPFolder.this.fullName + separator);
                } else {
                    iMAPProtocol.create(IMAPFolder.this.fullName);
                    if ((i & 2) != 0 && (list = iMAPProtocol.list("", IMAPFolder.this.fullName)) != null && !list[0].hasInferiors) {
                        iMAPProtocol.delete(IMAPFolder.this.fullName);
                        throw new ProtocolException("Unsupported type");
                    }
                }
                return Boolean.TRUE;
            }
        }) == null) {
            return false;
        }
        boolean zExists = exists();
        if (zExists) {
            notifyFolderListeners(1);
        }
        return zExists;
    }

    @Override // javax.mail.Folder
    public synchronized boolean hasNewMessages() throws MessagingException {
        final String str;
        synchronized (this.messageCacheLock) {
            if (this.opened) {
                try {
                    try {
                        keepConnectionAlive(true);
                        return this.recent > 0;
                    } catch (ProtocolException e) {
                        throw new MessagingException(e.getMessage(), e);
                    }
                } catch (ConnectionException e2) {
                    throw new FolderClosedException(this, e2.getMessage());
                }
            }
            if (this.isNamespace && this.separator != 0) {
                str = this.fullName + this.separator;
            } else {
                str = this.fullName;
            }
            ListInfo[] listInfoArr = (ListInfo[]) doCommandIgnoreFailure(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.7
                @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    return iMAPProtocol.list("", str);
                }
            });
            if (listInfoArr == null) {
                throw new FolderNotFoundException(this, this.fullName + " not found");
            }
            int iFindName = findName(listInfoArr, str);
            if (listInfoArr[iFindName].changeState == 1) {
                return true;
            }
            if (listInfoArr[iFindName].changeState == 2) {
                return false;
            }
            try {
                return getStatus().recent > 0;
            } catch (BadCommandException unused) {
                return false;
            } catch (ConnectionException e3) {
                throw new StoreClosedException(this.store, e3.getMessage());
            } catch (ProtocolException e4) {
                throw new MessagingException(e4.getMessage(), e4);
            }
        }
    }

    @Override // javax.mail.Folder
    public synchronized Folder getFolder(String str) throws MessagingException {
        char separator;
        if (this.attributes != null && !isDirectory()) {
            throw new MessagingException("Cannot contain subfolders");
        }
        separator = getSeparator();
        return ((IMAPStore) this.store).newIMAPFolder(this.fullName + separator + str, separator);
    }

    @Override // javax.mail.Folder
    public synchronized boolean delete(boolean z) throws MessagingException {
        checkClosed();
        if (z) {
            for (Folder folder : list()) {
                folder.delete(z);
            }
        }
        if (doCommandIgnoreFailure(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.8
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.delete(IMAPFolder.this.fullName);
                return Boolean.TRUE;
            }
        }) == null) {
            return false;
        }
        this.exists = false;
        this.attributes = null;
        notifyFolderListeners(2);
        return true;
    }

    @Override // javax.mail.Folder
    public synchronized boolean renameTo(final Folder folder) throws MessagingException {
        checkClosed();
        checkExists();
        if (folder.getStore() != this.store) {
            throw new MessagingException("Can't rename across Stores");
        }
        if (doCommandIgnoreFailure(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.9
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.rename(IMAPFolder.this.fullName, folder.getFullName());
                return Boolean.TRUE;
            }
        }) == null) {
            return false;
        }
        this.exists = false;
        this.attributes = null;
        notifyFolderRenamedListeners(folder);
        return true;
    }

    @Override // javax.mail.Folder
    public synchronized void open(int i) throws MessagingException {
        open(i, null);
    }

    public synchronized List<MailEvent> open(int i, ResyncData resyncData) throws MessagingException {
        MailboxInfo mailboxInfoSelect;
        ArrayList arrayList;
        long[] array;
        Message messageProcessFetchResponse;
        checkClosed();
        this.protocol = ((IMAPStore) this.store).getProtocol(this);
        synchronized (this.messageCacheLock) {
            this.protocol.addResponseHandler(this);
            if (resyncData != null) {
                try {
                    if (resyncData == ResyncData.CONDSTORE) {
                        if (!this.protocol.isEnabled("CONDSTORE") && !this.protocol.isEnabled("QRESYNC")) {
                            if (this.protocol.hasCapability("CONDSTORE")) {
                                this.protocol.enable("CONDSTORE");
                            } else {
                                this.protocol.enable("QRESYNC");
                            }
                        }
                    } else if (!this.protocol.isEnabled("QRESYNC")) {
                        this.protocol.enable("QRESYNC");
                    }
                } catch (CommandFailedException e) {
                    try {
                        checkExists();
                        if ((this.type & 1) == 0) {
                            throw new MessagingException("folder cannot contain messages");
                        }
                        throw new MessagingException(e.getMessage(), e);
                    } catch (Throwable th) {
                        this.exists = false;
                        this.attributes = null;
                        this.type = 0;
                        releaseProtocol(true);
                        throw th;
                    }
                } catch (ProtocolException e2) {
                    try {
                        throw logoutAndThrow(e2.getMessage(), e2);
                    } catch (Throwable th2) {
                        releaseProtocol(false);
                        throw th2;
                    }
                }
            }
            if (i == 1) {
                mailboxInfoSelect = this.protocol.examine(this.fullName, resyncData);
            } else {
                mailboxInfoSelect = this.protocol.select(this.fullName, resyncData);
            }
            if (mailboxInfoSelect.mode != i && (i != 2 || mailboxInfoSelect.mode != 1 || !((IMAPStore) this.store).allowReadOnlySelect())) {
                throw cleanupAndThrow(new ReadOnlyFolderException(this, "Cannot open in desired mode"));
            }
            this.opened = true;
            this.reallyClosed = false;
            this.mode = mailboxInfoSelect.mode;
            this.availableFlags = mailboxInfoSelect.availableFlags;
            this.permanentFlags = mailboxInfoSelect.permanentFlags;
            int i2 = mailboxInfoSelect.total;
            this.realTotal = i2;
            this.total = i2;
            this.recent = mailboxInfoSelect.recent;
            this.uidvalidity = mailboxInfoSelect.uidvalidity;
            this.uidnext = mailboxInfoSelect.uidnext;
            this.uidNotSticky = mailboxInfoSelect.uidNotSticky;
            this.highestmodseq = mailboxInfoSelect.highestmodseq;
            this.messageCache = new MessageCache(this, (IMAPStore) this.store, this.total);
            if (mailboxInfoSelect.responses != null) {
                arrayList = new ArrayList();
                for (IMAPResponse iMAPResponse : mailboxInfoSelect.responses) {
                    if (iMAPResponse.keyEquals("VANISHED")) {
                        String[] atomStringList = iMAPResponse.readAtomStringList();
                        if (atomStringList != null && atomStringList.length == 1 && atomStringList[0].equalsIgnoreCase("EARLIER") && (array = UIDSet.toArray(UIDSet.parseUIDSets(iMAPResponse.readAtom()), this.uidnext)) != null && array.length > 0) {
                            arrayList.add(new MessageVanishedEvent(this, array));
                        }
                    } else if (iMAPResponse.keyEquals("FETCH") && (messageProcessFetchResponse = processFetchResponse((FetchResponse) iMAPResponse)) != null) {
                        arrayList.add(new MessageChangedEvent(this, 1, messageProcessFetchResponse));
                    }
                }
            } else {
                arrayList = null;
            }
        }
        this.exists = true;
        this.attributes = null;
        this.type = 1;
        notifyConnectionListeners(1);
        return arrayList;
    }

    private MessagingException cleanupAndThrow(MessagingException messagingException) {
        try {
            try {
                this.protocol.close();
                releaseProtocol(true);
            } catch (ProtocolException e) {
                try {
                    addSuppressed(messagingException, logoutAndThrow(e.getMessage(), e));
                    releaseProtocol(false);
                } catch (Throwable th) {
                    releaseProtocol(false);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            addSuppressed(messagingException, th2);
        }
        return messagingException;
    }

    private MessagingException logoutAndThrow(String str, ProtocolException protocolException) {
        MessagingException messagingException = new MessagingException(str, protocolException);
        try {
            this.protocol.logout();
        } catch (Throwable th) {
            addSuppressed(messagingException, th);
        }
        return messagingException;
    }

    private void addSuppressed(Throwable th, Throwable th2) {
        if (isRecoverable(th2)) {
            th.addSuppressed(th2);
            return;
        }
        th2.addSuppressed(th);
        if (th2 instanceof Error) {
            throw ((Error) th2);
        }
        if (th2 instanceof RuntimeException) {
            throw ((RuntimeException) th2);
        }
        throw new RuntimeException("unexpected exception", th2);
    }

    private boolean isRecoverable(Throwable th) {
        return (th instanceof Exception) || (th instanceof LinkageError);
    }

    @Override // javax.mail.Folder
    public synchronized void fetch(Message[] messageArr, FetchProfile fetchProfile) throws MessagingException {
        boolean zIsREV1;
        FetchItem[] fetchItems;
        boolean z;
        boolean z2;
        String[] headerNames;
        synchronized (this.messageCacheLock) {
            checkOpened();
            zIsREV1 = this.protocol.isREV1();
            fetchItems = this.protocol.getFetchItems();
        }
        StringBuilder sb = new StringBuilder();
        if (fetchProfile.contains(FetchProfile.Item.ENVELOPE)) {
            sb.append(getEnvelopeCommand());
            z = false;
        } else {
            z = true;
        }
        if (fetchProfile.contains(FetchProfile.Item.FLAGS)) {
            sb.append(z ? "FLAGS" : " FLAGS");
            z = false;
        }
        if (fetchProfile.contains(FetchProfile.Item.CONTENT_INFO)) {
            sb.append(z ? "BODYSTRUCTURE" : " BODYSTRUCTURE");
            z = false;
        }
        if (fetchProfile.contains(UIDFolder.FetchProfileItem.UID)) {
            sb.append(z ? "UID" : " UID");
            z = false;
        }
        if (fetchProfile.contains(FetchProfileItem.HEADERS)) {
            if (zIsREV1) {
                sb.append(z ? "BODY.PEEK[HEADER]" : " BODY.PEEK[HEADER]");
            } else {
                sb.append(z ? "RFC822.HEADER" : " RFC822.HEADER");
            }
            z = false;
            z2 = true;
        } else {
            z2 = false;
        }
        if (fetchProfile.contains(FetchProfileItem.MESSAGE)) {
            if (zIsREV1) {
                sb.append(z ? "BODY.PEEK[]" : " BODY.PEEK[]");
            } else {
                sb.append(z ? "RFC822" : " RFC822");
            }
            z = false;
            z2 = true;
        }
        if (fetchProfile.contains(FetchProfile.Item.SIZE) || fetchProfile.contains(FetchProfileItem.SIZE)) {
            sb.append(z ? "RFC822.SIZE" : " RFC822.SIZE");
            z = false;
        }
        if (fetchProfile.contains(FetchProfileItem.INTERNALDATE)) {
            sb.append(z ? "INTERNALDATE" : " INTERNALDATE");
            z = false;
        }
        Response[] responseArrFetch = null;
        if (z2) {
            headerNames = null;
        } else {
            headerNames = fetchProfile.getHeaderNames();
            if (headerNames.length > 0) {
                if (!z) {
                    sb.append(StringUtils.SPACE);
                }
                sb.append(createHeaderCommand(headerNames, zIsREV1));
            }
        }
        for (int i = 0; i < fetchItems.length; i++) {
            if (fetchProfile.contains(fetchItems[i].getFetchProfileItem())) {
                if (sb.length() != 0) {
                    sb.append(StringUtils.SPACE);
                }
                sb.append(fetchItems[i].getName());
            }
        }
        IMAPMessage.FetchProfileCondition fetchProfileCondition = new IMAPMessage.FetchProfileCondition(fetchProfile, fetchItems);
        synchronized (this.messageCacheLock) {
            checkOpened();
            MessageSet[] messageSetSorted = Utility.toMessageSetSorted(messageArr, fetchProfileCondition);
            if (messageSetSorted == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            try {
                try {
                    responseArrFetch = getProtocol().fetch(messageSetSorted, sb.toString());
                } catch (CommandFailedException unused) {
                } catch (ProtocolException e) {
                    throw new MessagingException(e.getMessage(), e);
                }
                if (responseArrFetch == null) {
                    return;
                }
                for (Response response : responseArrFetch) {
                    if (response != null) {
                        if (!(response instanceof FetchResponse)) {
                            arrayList.add(response);
                        } else {
                            FetchResponse fetchResponse = (FetchResponse) response;
                            IMAPMessage messageBySeqNumber = getMessageBySeqNumber(fetchResponse.getNumber());
                            int itemCount = fetchResponse.getItemCount();
                            boolean z3 = false;
                            for (int i2 = 0; i2 < itemCount; i2++) {
                                Item item = fetchResponse.getItem(i2);
                                if ((item instanceof Flags) && (!fetchProfile.contains(FetchProfile.Item.FLAGS) || messageBySeqNumber == null)) {
                                    z3 = true;
                                } else if (messageBySeqNumber != null) {
                                    messageBySeqNumber.handleFetchItem(item, headerNames, z2);
                                }
                            }
                            if (messageBySeqNumber != null) {
                                messageBySeqNumber.handleExtensionFetchItems(fetchResponse.getExtensionItems());
                            }
                            if (z3) {
                                arrayList.add(fetchResponse);
                            }
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    Response[] responseArr = new Response[arrayList.size()];
                    arrayList.toArray(responseArr);
                    handleResponses(responseArr);
                }
            } catch (ConnectionException e2) {
                throw new FolderClosedException(this, e2.getMessage());
            }
        }
    }

    protected IMAPMessage newIMAPMessage(int i) {
        return new IMAPMessage(this, i);
    }

    private String createHeaderCommand(String[] strArr, boolean z) {
        StringBuilder sb;
        if (z) {
            sb = new StringBuilder("BODY.PEEK[HEADER.FIELDS (");
        } else {
            sb = new StringBuilder("RFC822.HEADER.LINES (");
        }
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                sb.append(StringUtils.SPACE);
            }
            sb.append(strArr[i]);
        }
        if (z) {
            sb.append(")]");
        } else {
            sb.append(")");
        }
        return sb.toString();
    }

    @Override // javax.mail.Folder
    public synchronized void setFlags(Message[] messageArr, Flags flags, boolean z) throws MessagingException {
        checkOpened();
        checkFlags(flags);
        if (messageArr.length == 0) {
            return;
        }
        synchronized (this.messageCacheLock) {
            try {
                IMAPProtocol protocol = getProtocol();
                MessageSet[] messageSetSorted = Utility.toMessageSetSorted(messageArr, null);
                if (messageSetSorted == null) {
                    throw new MessageRemovedException("Messages have been removed");
                }
                protocol.storeFlags(messageSetSorted, flags, z);
            } catch (ConnectionException e) {
                throw new FolderClosedException(this, e.getMessage());
            } catch (ProtocolException e2) {
                throw new MessagingException(e2.getMessage(), e2);
            }
        }
    }

    @Override // javax.mail.Folder
    public synchronized void setFlags(int i, int i2, Flags flags, boolean z) throws MessagingException {
        checkOpened();
        Message[] messageArr = new Message[(i2 - i) + 1];
        int i3 = 0;
        while (i <= i2) {
            messageArr[i3] = getMessage(i);
            i++;
            i3++;
        }
        setFlags(messageArr, flags, z);
    }

    @Override // javax.mail.Folder
    public synchronized void setFlags(int[] iArr, Flags flags, boolean z) throws MessagingException {
        checkOpened();
        Message[] messageArr = new Message[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            messageArr[i] = getMessage(iArr[i]);
        }
        setFlags(messageArr, flags, z);
    }

    @Override // javax.mail.Folder
    public synchronized void close(boolean z) throws MessagingException {
        close(z, false);
    }

    public synchronized void forceClose() throws MessagingException {
        close(false, true);
    }

    private void close(boolean z, boolean z2) throws MessagingException {
        boolean z3;
        synchronized (this.messageCacheLock) {
            if (!this.opened && this.reallyClosed) {
                throw new IllegalStateException("This operation is not allowed on a closed folder");
            }
            boolean z4 = true;
            this.reallyClosed = true;
            try {
                if (this.opened) {
                    try {
                        waitIfIdle();
                        if (z2) {
                            this.logger.log(Level.FINE, "forcing folder {0} to close", this.fullName);
                            if (this.protocol != null) {
                                this.protocol.disconnect();
                            }
                        } else if (((IMAPStore) this.store).isConnectionPoolFull()) {
                            this.logger.fine("pool is full, not adding an Authenticated connection");
                            if (z && this.protocol != null) {
                                this.protocol.close();
                            }
                            if (this.protocol != null) {
                                this.protocol.logout();
                            }
                        } else if (!z && this.mode == 2) {
                            try {
                                if (this.protocol != null && this.protocol.hasCapability("UNSELECT")) {
                                    this.protocol.unselect();
                                } else if (this.protocol != null) {
                                    try {
                                        this.protocol.examine(this.fullName);
                                        z3 = true;
                                    } catch (CommandFailedException unused) {
                                        z3 = false;
                                    }
                                    if (z3 && this.protocol != null) {
                                        this.protocol.close();
                                    }
                                }
                            } catch (ProtocolException unused2) {
                                z4 = false;
                            }
                        } else if (this.protocol != null) {
                            this.protocol.close();
                        }
                    } catch (ProtocolException e) {
                        throw new MessagingException(e.getMessage(), e);
                    }
                }
            } finally {
                if (this.opened) {
                    cleanup(true);
                }
            }
        }
    }

    private void cleanup(boolean z) {
        releaseProtocol(z);
        this.messageCache = null;
        this.uidTable = null;
        this.exists = false;
        this.attributes = null;
        this.opened = false;
        this.idleState = 0;
        this.messageCacheLock.notifyAll();
        notifyConnectionListeners(3);
    }

    @Override // javax.mail.Folder
    public synchronized boolean isOpen() {
        synchronized (this.messageCacheLock) {
            if (this.opened) {
                try {
                    keepConnectionAlive(false);
                } catch (ProtocolException unused) {
                }
            }
        }
        return this.opened;
    }

    @Override // javax.mail.Folder
    public synchronized Flags getPermanentFlags() {
        Flags flags = this.permanentFlags;
        if (flags == null) {
            return null;
        }
        Flags flags2 = (Flags) flags.clone();
        return flags2;
    }

    @Override // javax.mail.Folder
    public synchronized int getMessageCount() throws MessagingException {
        synchronized (this.messageCacheLock) {
            if (this.opened) {
                try {
                    try {
                        keepConnectionAlive(true);
                        return this.total;
                    } catch (ConnectionException e) {
                        throw new FolderClosedException(this, e.getMessage());
                    }
                } catch (ProtocolException e2) {
                    throw new MessagingException(e2.getMessage(), e2);
                }
            }
            checkExists();
            try {
                try {
                    try {
                        return getStatus().total;
                    } catch (ProtocolException e3) {
                        throw new MessagingException(e3.getMessage(), e3);
                    }
                } catch (ConnectionException e4) {
                    throw new StoreClosedException(this.store, e4.getMessage());
                }
            } catch (BadCommandException unused) {
                IMAPProtocol storeProtocol = null;
                try {
                    try {
                        storeProtocol = getStoreProtocol();
                        MailboxInfo mailboxInfoExamine = storeProtocol.examine(this.fullName);
                        storeProtocol.close();
                        return mailboxInfoExamine.total;
                    } catch (ProtocolException e5) {
                        throw new MessagingException(e5.getMessage(), e5);
                    }
                } finally {
                    releaseStoreProtocol(storeProtocol);
                }
            }
        }
    }

    @Override // javax.mail.Folder
    public synchronized int getNewMessageCount() throws MessagingException {
        synchronized (this.messageCacheLock) {
            if (this.opened) {
                try {
                    try {
                        keepConnectionAlive(true);
                        return this.recent;
                    } catch (ConnectionException e) {
                        throw new FolderClosedException(this, e.getMessage());
                    }
                } catch (ProtocolException e2) {
                    throw new MessagingException(e2.getMessage(), e2);
                }
            }
            checkExists();
            try {
                try {
                    try {
                        return getStatus().recent;
                    } catch (ProtocolException e3) {
                        throw new MessagingException(e3.getMessage(), e3);
                    }
                } catch (ConnectionException e4) {
                    throw new StoreClosedException(this.store, e4.getMessage());
                }
            } catch (BadCommandException unused) {
                IMAPProtocol storeProtocol = null;
                try {
                    try {
                        storeProtocol = getStoreProtocol();
                        MailboxInfo mailboxInfoExamine = storeProtocol.examine(this.fullName);
                        storeProtocol.close();
                        return mailboxInfoExamine.recent;
                    } catch (ProtocolException e5) {
                        throw new MessagingException(e5.getMessage(), e5);
                    }
                } finally {
                    releaseStoreProtocol(storeProtocol);
                }
            }
        }
    }

    @Override // javax.mail.Folder
    public synchronized int getUnreadMessageCount() throws MessagingException {
        int length;
        if (!this.opened) {
            checkExists();
            try {
                try {
                    return getStatus().unseen;
                } catch (BadCommandException unused) {
                    return -1;
                } catch (ProtocolException e) {
                    throw new MessagingException(e.getMessage(), e);
                }
            } catch (ConnectionException e2) {
                throw new StoreClosedException(this.store, e2.getMessage());
            }
        }
        Flags flags = new Flags();
        flags.add(Flags.Flag.SEEN);
        try {
            synchronized (this.messageCacheLock) {
                length = getProtocol().search(new FlagTerm(flags, false)).length;
            }
            return length;
        } catch (ConnectionException e3) {
            throw new FolderClosedException(this, e3.getMessage());
        } catch (ProtocolException e4) {
            throw new MessagingException(e4.getMessage(), e4);
        }
    }

    @Override // javax.mail.Folder
    public synchronized int getDeletedMessageCount() throws MessagingException {
        int length;
        if (!this.opened) {
            checkExists();
            return -1;
        }
        Flags flags = new Flags();
        flags.add(Flags.Flag.DELETED);
        try {
            synchronized (this.messageCacheLock) {
                length = getProtocol().search(new FlagTerm(flags, true)).length;
            }
            return length;
        } catch (ConnectionException e) {
            throw new FolderClosedException(this, e.getMessage());
        } catch (ProtocolException e2) {
            throw new MessagingException(e2.getMessage(), e2);
        }
    }

    private Status getStatus() throws Throwable {
        IMAPProtocol storeProtocol;
        int statusCacheTimeout = ((IMAPStore) this.store).getStatusCacheTimeout();
        if (statusCacheTimeout > 0 && this.cachedStatus != null && System.currentTimeMillis() - this.cachedStatusTime < statusCacheTimeout) {
            return this.cachedStatus;
        }
        IMAPProtocol iMAPProtocol = null;
        try {
            storeProtocol = getStoreProtocol();
        } catch (Throwable th) {
            th = th;
        }
        try {
            Status status = storeProtocol.status(this.fullName, null);
            if (statusCacheTimeout > 0) {
                this.cachedStatus = status;
                this.cachedStatusTime = System.currentTimeMillis();
            }
            releaseStoreProtocol(storeProtocol);
            return status;
        } catch (Throwable th2) {
            th = th2;
            iMAPProtocol = storeProtocol;
            releaseStoreProtocol(iMAPProtocol);
            throw th;
        }
    }

    @Override // javax.mail.Folder
    public synchronized Message getMessage(int i) throws MessagingException {
        checkOpened();
        checkRange(i);
        return this.messageCache.getMessage(i);
    }

    @Override // javax.mail.Folder
    public synchronized Message[] getMessages() throws MessagingException {
        Message[] messageArr;
        checkOpened();
        int messageCount = getMessageCount();
        messageArr = new Message[messageCount];
        for (int i = 1; i <= messageCount; i++) {
            messageArr[i - 1] = this.messageCache.getMessage(i);
        }
        return messageArr;
    }

    @Override // javax.mail.Folder
    public synchronized void appendMessages(Message[] messageArr) throws MessagingException {
        checkExists();
        int appendBufferSize = ((IMAPStore) this.store).getAppendBufferSize();
        for (Message message : messageArr) {
            final Date receivedDate = message.getReceivedDate();
            if (receivedDate == null) {
                receivedDate = message.getSentDate();
            }
            final Flags flags = message.getFlags();
            try {
                final MessageLiteral messageLiteral = new MessageLiteral(message, message.getSize() > appendBufferSize ? 0 : appendBufferSize);
                doCommand(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.10
                    @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        iMAPProtocol.append(IMAPFolder.this.fullName, flags, receivedDate, messageLiteral);
                        return null;
                    }
                });
            } catch (IOException e) {
                throw new MessagingException("IOException while appending messages", e);
            } catch (MessageRemovedException unused) {
            }
        }
    }

    public synchronized AppendUID[] appendUIDMessages(Message[] messageArr) throws MessagingException {
        AppendUID[] appendUIDArr;
        checkExists();
        int appendBufferSize = ((IMAPStore) this.store).getAppendBufferSize();
        appendUIDArr = new AppendUID[messageArr.length];
        for (int i = 0; i < messageArr.length; i++) {
            Message message = messageArr[i];
            try {
                final MessageLiteral messageLiteral = new MessageLiteral(message, message.getSize() > appendBufferSize ? 0 : appendBufferSize);
                final Date receivedDate = message.getReceivedDate();
                if (receivedDate == null) {
                    receivedDate = message.getSentDate();
                }
                final Flags flags = message.getFlags();
                appendUIDArr[i] = (AppendUID) doCommand(new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.11
                    @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
                    public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                        return iMAPProtocol.appenduid(IMAPFolder.this.fullName, flags, receivedDate, messageLiteral);
                    }
                });
            } catch (IOException e) {
                throw new MessagingException("IOException while appending messages", e);
            } catch (MessageRemovedException unused) {
            }
        }
        return appendUIDArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public synchronized Message[] addMessages(Message[] messageArr) throws MessagingException {
        MimeMessage[] mimeMessageArr;
        checkOpened();
        mimeMessageArr = new MimeMessage[messageArr.length];
        AppendUID[] appendUIDArrAppendUIDMessages = appendUIDMessages(messageArr);
        for (int i = 0; i < appendUIDArrAppendUIDMessages.length; i++) {
            AppendUID appendUID = appendUIDArrAppendUIDMessages[i];
            if (appendUID != null && appendUID.uidvalidity == this.uidvalidity) {
                try {
                    mimeMessageArr[i] = getMessageByUID(appendUID.uid);
                } catch (MessagingException unused) {
                }
            }
        }
        return mimeMessageArr;
    }

    @Override // javax.mail.Folder
    public synchronized void copyMessages(Message[] messageArr, Folder folder) throws MessagingException {
        copymoveMessages(messageArr, folder, false);
    }

    public synchronized AppendUID[] copyUIDMessages(Message[] messageArr, Folder folder) throws MessagingException {
        return copymoveUIDMessages(messageArr, folder, false);
    }

    public synchronized void moveMessages(Message[] messageArr, Folder folder) throws MessagingException {
        copymoveMessages(messageArr, folder, true);
    }

    public synchronized AppendUID[] moveUIDMessages(Message[] messageArr, Folder folder) throws MessagingException {
        return copymoveUIDMessages(messageArr, folder, true);
    }

    private synchronized void copymoveMessages(Message[] messageArr, Folder folder, boolean z) throws MessagingException {
        checkOpened();
        if (messageArr.length == 0) {
            return;
        }
        if (folder.getStore() == this.store) {
            synchronized (this.messageCacheLock) {
                try {
                    try {
                        IMAPProtocol protocol = getProtocol();
                        MessageSet[] messageSet = Utility.toMessageSet(messageArr, null);
                        if (messageSet == null) {
                            throw new MessageRemovedException("Messages have been removed");
                        }
                        if (z) {
                            protocol.move(messageSet, folder.getFullName());
                        } else {
                            protocol.copy(messageSet, folder.getFullName());
                        }
                    } catch (ConnectionException e) {
                        throw new FolderClosedException(this, e.getMessage());
                    } catch (ProtocolException e2) {
                        throw new MessagingException(e2.getMessage(), e2);
                    }
                } catch (CommandFailedException e3) {
                    if (e3.getMessage().indexOf("TRYCREATE") != -1) {
                        throw new FolderNotFoundException(folder, folder.getFullName() + " does not exist");
                    }
                    throw new MessagingException(e3.getMessage(), e3);
                }
            }
        } else {
            if (z) {
                throw new MessagingException("Move between stores not supported");
            }
            super.copyMessages(messageArr, folder);
        }
    }

    private synchronized AppendUID[] copymoveUIDMessages(Message[] messageArr, Folder folder, boolean z) throws MessagingException {
        CopyUID copyuid;
        AppendUID[] appendUIDArr;
        checkOpened();
        if (messageArr.length == 0) {
            return null;
        }
        if (folder.getStore() != this.store) {
            throw new MessagingException(z ? "can't moveUIDMessages to a different store" : "can't copyUIDMessages to a different store");
        }
        FetchProfile fetchProfile = new FetchProfile();
        fetchProfile.add(UIDFolder.FetchProfileItem.UID);
        fetch(messageArr, fetchProfile);
        synchronized (this.messageCacheLock) {
            try {
                try {
                    IMAPProtocol protocol = getProtocol();
                    MessageSet[] messageSet = Utility.toMessageSet(messageArr, null);
                    if (messageSet == null) {
                        throw new MessageRemovedException("Messages have been removed");
                    }
                    if (z) {
                        copyuid = protocol.moveuid(messageSet, folder.getFullName());
                    } else {
                        copyuid = protocol.copyuid(messageSet, folder.getFullName());
                    }
                    long[] array = UIDSet.toArray(copyuid.src);
                    long[] array2 = UIDSet.toArray(copyuid.dst);
                    Message[] messagesByUID = getMessagesByUID(array);
                    appendUIDArr = new AppendUID[messageArr.length];
                    for (int i = 0; i < messageArr.length; i++) {
                        int i2 = i;
                        while (true) {
                            if (messageArr[i] == messagesByUID[i2]) {
                                appendUIDArr[i] = new AppendUID(copyuid.uidvalidity, array2[i2]);
                                break;
                            }
                            i2++;
                            if (i2 >= messagesByUID.length) {
                                i2 = 0;
                            }
                            if (i2 == i) {
                                break;
                            }
                        }
                    }
                } catch (CommandFailedException e) {
                    if (e.getMessage().indexOf("TRYCREATE") != -1) {
                        throw new FolderNotFoundException(folder, folder.getFullName() + " does not exist");
                    }
                    throw new MessagingException(e.getMessage(), e);
                } catch (ProtocolException e2) {
                    throw new MessagingException(e2.getMessage(), e2);
                }
            } catch (ConnectionException e3) {
                throw new FolderClosedException(this, e3.getMessage());
            }
        }
        return appendUIDArr;
    }

    @Override // javax.mail.Folder
    public synchronized Message[] expunge() throws MessagingException {
        return expunge(null);
    }

    public synchronized Message[] expunge(Message[] messageArr) throws MessagingException {
        IMAPMessage[] iMAPMessageArrRemoveExpungedMessages;
        checkOpened();
        if (messageArr != null) {
            FetchProfile fetchProfile = new FetchProfile();
            fetchProfile.add(UIDFolder.FetchProfileItem.UID);
            fetch(messageArr, fetchProfile);
        }
        synchronized (this.messageCacheLock) {
            this.doExpungeNotification = false;
            try {
                try {
                    IMAPProtocol protocol = getProtocol();
                    if (messageArr != null) {
                        protocol.uidexpunge(Utility.toUIDSet(messageArr));
                    } else {
                        protocol.expunge();
                    }
                    if (messageArr != null) {
                        iMAPMessageArrRemoveExpungedMessages = this.messageCache.removeExpungedMessages(messageArr);
                    } else {
                        iMAPMessageArrRemoveExpungedMessages = this.messageCache.removeExpungedMessages();
                    }
                    if (this.uidTable != null) {
                        for (IMAPMessage iMAPMessage : iMAPMessageArrRemoveExpungedMessages) {
                            long uid = iMAPMessage.getUID();
                            if (uid != -1) {
                                this.uidTable.remove(Long.valueOf(uid));
                            }
                        }
                    }
                    this.total = this.messageCache.size();
                } catch (CommandFailedException e) {
                    if (this.mode != 2) {
                        throw new IllegalStateException("Cannot expunge READ_ONLY folder: " + this.fullName);
                    }
                    throw new MessagingException(e.getMessage(), e);
                } catch (ConnectionException e2) {
                    throw new FolderClosedException(this, e2.getMessage());
                } catch (ProtocolException e3) {
                    throw new MessagingException(e3.getMessage(), e3);
                }
            } finally {
                this.doExpungeNotification = true;
            }
        }
        if (iMAPMessageArrRemoveExpungedMessages.length > 0) {
            notifyMessageRemovedListeners(true, iMAPMessageArrRemoveExpungedMessages);
        }
        return iMAPMessageArrRemoveExpungedMessages;
    }

    @Override // javax.mail.Folder
    public synchronized Message[] search(SearchTerm searchTerm) throws MessagingException {
        IMAPMessage[] messagesBySeqNumbers;
        checkOpened();
        try {
            try {
                synchronized (this.messageCacheLock) {
                    int[] iArrSearch = getProtocol().search(searchTerm);
                    messagesBySeqNumbers = iArrSearch != null ? getMessagesBySeqNumbers(iArrSearch) : null;
                }
            } catch (ProtocolException e) {
                throw new MessagingException(e.getMessage(), e);
            } catch (SearchException e2) {
                if (((IMAPStore) this.store).throwSearchException()) {
                    throw e2;
                }
                return super.search(searchTerm);
            }
        } catch (CommandFailedException unused) {
            return super.search(searchTerm);
        } catch (ConnectionException e3) {
            throw new FolderClosedException(this, e3.getMessage());
        }
        return messagesBySeqNumbers;
    }

    @Override // javax.mail.Folder
    public synchronized Message[] search(SearchTerm searchTerm, Message[] messageArr) throws MessagingException {
        IMAPMessage[] messagesBySeqNumbers;
        checkOpened();
        if (messageArr.length == 0) {
            return messageArr;
        }
        try {
            try {
                try {
                    try {
                        synchronized (this.messageCacheLock) {
                            IMAPProtocol protocol = getProtocol();
                            MessageSet[] messageSetSorted = Utility.toMessageSetSorted(messageArr, null);
                            if (messageSetSorted == null) {
                                throw new MessageRemovedException("Messages have been removed");
                            }
                            int[] iArrSearch = protocol.search(messageSetSorted, searchTerm);
                            messagesBySeqNumbers = iArrSearch != null ? getMessagesBySeqNumbers(iArrSearch) : null;
                        }
                        return messagesBySeqNumbers;
                    } catch (ConnectionException e) {
                        throw new FolderClosedException(this, e.getMessage());
                    }
                } catch (SearchException unused) {
                    return super.search(searchTerm, messageArr);
                }
            } catch (ProtocolException e2) {
                throw new MessagingException(e2.getMessage(), e2);
            }
        } catch (CommandFailedException unused2) {
            return super.search(searchTerm, messageArr);
        }
    }

    public synchronized Message[] getSortedMessages(SortTerm[] sortTermArr) throws MessagingException {
        return getSortedMessages(sortTermArr, null);
    }

    public synchronized Message[] getSortedMessages(SortTerm[] sortTermArr, SearchTerm searchTerm) throws MessagingException {
        IMAPMessage[] messagesBySeqNumbers;
        checkOpened();
        try {
            try {
                try {
                    synchronized (this.messageCacheLock) {
                        int[] iArrSort = getProtocol().sort(sortTermArr, searchTerm);
                        messagesBySeqNumbers = iArrSort != null ? getMessagesBySeqNumbers(iArrSort) : null;
                    }
                } catch (ConnectionException e) {
                    throw new FolderClosedException(this, e.getMessage());
                } catch (ProtocolException e2) {
                    throw new MessagingException(e2.getMessage(), e2);
                }
            } catch (SearchException e3) {
                throw new MessagingException(e3.getMessage(), e3);
            }
        } catch (CommandFailedException e4) {
            throw new MessagingException(e4.getMessage(), e4);
        }
        return messagesBySeqNumbers;
    }

    @Override // javax.mail.Folder
    public synchronized void addMessageCountListener(MessageCountListener messageCountListener) {
        super.addMessageCountListener(messageCountListener);
        this.hasMessageCountListener = true;
    }

    @Override // javax.mail.UIDFolder
    public synchronized long getUIDValidity() throws MessagingException {
        Throwable th;
        ProtocolException e;
        IMAPProtocol storeProtocol;
        BadCommandException e2;
        if (this.opened) {
            return this.uidvalidity;
        }
        Status status = null;
        try {
            try {
                storeProtocol = getStoreProtocol();
            } catch (BadCommandException e3) {
                e2 = e3;
            } catch (ConnectionException e4) {
                e = e4;
                storeProtocol = null;
            } catch (ProtocolException e5) {
                e = e5;
            } catch (Throwable th2) {
                th = th2;
                releaseStoreProtocol(null);
                throw th;
            }
            try {
                status = storeProtocol.status(this.fullName, new String[]{"UIDVALIDITY"});
            } catch (BadCommandException e6) {
                e2 = e6;
                throw new MessagingException("Cannot obtain UIDValidity", e2);
            } catch (ConnectionException e7) {
                e = e7;
                throwClosedException(e);
            } catch (ProtocolException e8) {
                e = e8;
                throw new MessagingException(e.getMessage(), e);
            }
            releaseStoreProtocol(storeProtocol);
            if (status == null) {
                throw new MessagingException("Cannot obtain UIDValidity");
            }
            return status.uidvalidity;
        } catch (Throwable th3) {
            th = th3;
            releaseStoreProtocol(null);
            throw th;
        }
    }

    @Override // javax.mail.UIDFolder
    public synchronized long getUIDNext() throws MessagingException {
        Throwable th;
        ProtocolException e;
        IMAPProtocol storeProtocol;
        BadCommandException e2;
        if (this.opened) {
            return this.uidnext;
        }
        Status status = null;
        try {
            try {
                storeProtocol = getStoreProtocol();
            } catch (BadCommandException e3) {
                e2 = e3;
            } catch (ConnectionException e4) {
                e = e4;
                storeProtocol = null;
            } catch (ProtocolException e5) {
                e = e5;
            } catch (Throwable th2) {
                th = th2;
                releaseStoreProtocol(null);
                throw th;
            }
            try {
                status = storeProtocol.status(this.fullName, new String[]{"UIDNEXT"});
            } catch (BadCommandException e6) {
                e2 = e6;
                throw new MessagingException("Cannot obtain UIDNext", e2);
            } catch (ConnectionException e7) {
                e = e7;
                throwClosedException(e);
            } catch (ProtocolException e8) {
                e = e8;
                throw new MessagingException(e.getMessage(), e);
            }
            releaseStoreProtocol(storeProtocol);
            if (status == null) {
                throw new MessagingException("Cannot obtain UIDNext");
            }
            return status.uidnext;
        } catch (Throwable th3) {
            th = th3;
            releaseStoreProtocol(null);
            throw th;
        }
    }

    @Override // javax.mail.UIDFolder
    public synchronized Message getMessageByUID(long j) throws MessagingException {
        IMAPMessage iMAPMessage;
        checkOpened();
        try {
            try {
                synchronized (this.messageCacheLock) {
                    Long lValueOf = Long.valueOf(j);
                    Hashtable<Long, IMAPMessage> hashtable = this.uidTable;
                    if (hashtable != null) {
                        iMAPMessage = hashtable.get(lValueOf);
                        if (iMAPMessage != null) {
                            return iMAPMessage;
                        }
                    } else {
                        this.uidTable = new Hashtable<>();
                        iMAPMessage = null;
                    }
                    getProtocol().fetchSequenceNumber(j);
                    Hashtable<Long, IMAPMessage> hashtable2 = this.uidTable;
                    return (hashtable2 == null || (iMAPMessage = hashtable2.get(lValueOf)) == null) ? iMAPMessage : iMAPMessage;
                }
            } catch (ConnectionException e) {
                throw new FolderClosedException(this, e.getMessage());
            }
        } catch (ProtocolException e2) {
            throw new MessagingException(e2.getMessage(), e2);
        }
    }

    @Override // javax.mail.UIDFolder
    public synchronized Message[] getMessagesByUID(long j, long j2) throws MessagingException {
        Message[] messageArr;
        checkOpened();
        try {
            try {
                synchronized (this.messageCacheLock) {
                    if (this.uidTable == null) {
                        this.uidTable = new Hashtable<>();
                    }
                    long[] jArrFetchSequenceNumbers = getProtocol().fetchSequenceNumbers(j, j2);
                    ArrayList arrayList = new ArrayList();
                    for (long j3 : jArrFetchSequenceNumbers) {
                        IMAPMessage iMAPMessage = this.uidTable.get(Long.valueOf(j3));
                        if (iMAPMessage != null) {
                            arrayList.add(iMAPMessage);
                        }
                    }
                    messageArr = (Message[]) arrayList.toArray(new Message[arrayList.size()]);
                }
            } catch (ProtocolException e) {
                throw new MessagingException(e.getMessage(), e);
            }
        } catch (ConnectionException e2) {
            throw new FolderClosedException(this, e2.getMessage());
        }
        return messageArr;
    }

    @Override // javax.mail.UIDFolder
    public synchronized Message[] getMessagesByUID(long[] jArr) throws MessagingException {
        long[] jArr2;
        Message[] messageArr;
        checkOpened();
        try {
            synchronized (this.messageCacheLock) {
                if (this.uidTable != null) {
                    ArrayList arrayList = new ArrayList();
                    for (long j : jArr) {
                        if (!this.uidTable.containsKey(Long.valueOf(j))) {
                            arrayList.add(Long.valueOf(j));
                        }
                    }
                    int size = arrayList.size();
                    jArr2 = new long[size];
                    for (int i = 0; i < size; i++) {
                        jArr2[i] = ((Long) arrayList.get(i)).longValue();
                    }
                } else {
                    this.uidTable = new Hashtable<>();
                    jArr2 = jArr;
                }
                if (jArr2.length > 0) {
                    getProtocol().fetchSequenceNumbers(jArr2);
                }
                messageArr = new Message[jArr.length];
                for (int i2 = 0; i2 < jArr.length; i2++) {
                    messageArr[i2] = this.uidTable.get(Long.valueOf(jArr[i2]));
                }
            }
        } catch (ConnectionException e) {
            throw new FolderClosedException(this, e.getMessage());
        } catch (ProtocolException e2) {
            throw new MessagingException(e2.getMessage(), e2);
        }
        return messageArr;
    }

    @Override // javax.mail.UIDFolder
    public synchronized long getUID(Message message) throws MessagingException {
        if (message.getFolder() != this) {
            throw new NoSuchElementException("Message does not belong to this folder");
        }
        checkOpened();
        if (!(message instanceof IMAPMessage)) {
            throw new MessagingException("message is not an IMAPMessage");
        }
        IMAPMessage iMAPMessage = (IMAPMessage) message;
        long uid = iMAPMessage.getUID();
        if (uid != -1) {
            return uid;
        }
        synchronized (this.messageCacheLock) {
            try {
                IMAPProtocol protocol = getProtocol();
                iMAPMessage.checkExpunged();
                UID uidFetchUID = protocol.fetchUID(iMAPMessage.getSequenceNumber());
                if (uidFetchUID != null) {
                    uid = uidFetchUID.uid;
                    iMAPMessage.setUID(uid);
                    if (this.uidTable == null) {
                        this.uidTable = new Hashtable<>();
                    }
                    this.uidTable.put(Long.valueOf(uid), iMAPMessage);
                }
            } catch (ConnectionException e) {
                throw new FolderClosedException(this, e.getMessage());
            } catch (ProtocolException e2) {
                throw new MessagingException(e2.getMessage(), e2);
            }
        }
        return uid;
    }

    public synchronized boolean getUIDNotSticky() throws MessagingException {
        checkOpened();
        return this.uidNotSticky;
    }

    private Message[] createMessagesForUIDs(long[] jArr) {
        IMAPMessage[] iMAPMessageArr = new IMAPMessage[jArr.length];
        for (int i = 0; i < jArr.length; i = i + 1 + 1) {
            Hashtable<Long, IMAPMessage> hashtable = this.uidTable;
            IMAPMessage iMAPMessageNewIMAPMessage = hashtable != null ? hashtable.get(Long.valueOf(jArr[i])) : null;
            if (iMAPMessageNewIMAPMessage == null) {
                iMAPMessageNewIMAPMessage = newIMAPMessage(-1);
                iMAPMessageNewIMAPMessage.setUID(jArr[i]);
                iMAPMessageNewIMAPMessage.setExpunged(true);
            }
            iMAPMessageArr[i] = iMAPMessageNewIMAPMessage;
        }
        return iMAPMessageArr;
    }

    public synchronized long getHighestModSeq() throws MessagingException {
        Throwable th;
        ProtocolException e;
        IMAPProtocol storeProtocol;
        BadCommandException e2;
        if (this.opened) {
            return this.highestmodseq;
        }
        Status status = null;
        try {
            try {
                storeProtocol = getStoreProtocol();
            } catch (BadCommandException e3) {
                e2 = e3;
            } catch (ConnectionException e4) {
                e = e4;
                storeProtocol = null;
            } catch (ProtocolException e5) {
                e = e5;
            } catch (Throwable th2) {
                th = th2;
                releaseStoreProtocol(null);
                throw th;
            }
            try {
            } catch (BadCommandException e6) {
                e2 = e6;
                throw new MessagingException("Cannot obtain HIGHESTMODSEQ", e2);
            } catch (ConnectionException e7) {
                e = e7;
                throwClosedException(e);
            } catch (ProtocolException e8) {
                e = e8;
                throw new MessagingException(e.getMessage(), e);
            }
            if (!storeProtocol.hasCapability("CONDSTORE")) {
                throw new BadCommandException("CONDSTORE not supported");
            }
            status = storeProtocol.status(this.fullName, new String[]{"HIGHESTMODSEQ"});
            releaseStoreProtocol(storeProtocol);
            if (status == null) {
                throw new MessagingException("Cannot obtain HIGHESTMODSEQ");
            }
            return status.highestmodseq;
        } catch (Throwable th3) {
            th = th3;
            releaseStoreProtocol(null);
            throw th;
        }
    }

    public synchronized Message[] getMessagesByUIDChangedSince(long j, long j2, long j3) throws MessagingException {
        IMAPMessage[] messagesBySeqNumbers;
        checkOpened();
        try {
            synchronized (this.messageCacheLock) {
                IMAPProtocol protocol = getProtocol();
                if (!protocol.hasCapability("CONDSTORE")) {
                    throw new BadCommandException("CONDSTORE not supported");
                }
                messagesBySeqNumbers = getMessagesBySeqNumbers(protocol.uidfetchChangedSince(j, j2, j3));
            }
        } catch (ConnectionException e) {
            throw new FolderClosedException(this, e.getMessage());
        } catch (ProtocolException e2) {
            throw new MessagingException(e2.getMessage(), e2);
        }
        return messagesBySeqNumbers;
    }

    public Quota[] getQuota() throws MessagingException {
        return (Quota[]) doOptionalCommand("QUOTA not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.12
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.getQuotaRoot(IMAPFolder.this.fullName);
            }
        });
    }

    public void setQuota(final Quota quota) throws MessagingException {
        doOptionalCommand("QUOTA not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.13
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.setQuota(quota);
                return null;
            }
        });
    }

    public ACL[] getACL() throws MessagingException {
        return (ACL[]) doOptionalCommand("ACL not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.14
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.getACL(IMAPFolder.this.fullName);
            }
        });
    }

    public void addACL(ACL acl) throws MessagingException {
        setACL(acl, (char) 0);
    }

    public void removeACL(final String str) throws MessagingException {
        doOptionalCommand("ACL not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.15
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.deleteACL(IMAPFolder.this.fullName, str);
                return null;
            }
        });
    }

    public void addRights(ACL acl) throws MessagingException {
        setACL(acl, '+');
    }

    public void removeRights(ACL acl) throws MessagingException {
        setACL(acl, '-');
    }

    public Rights[] listRights(final String str) throws MessagingException {
        return (Rights[]) doOptionalCommand("ACL not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.16
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.listRights(IMAPFolder.this.fullName, str);
            }
        });
    }

    public Rights myRights() throws MessagingException {
        return (Rights) doOptionalCommand("ACL not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.17
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.myRights(IMAPFolder.this.fullName);
            }
        });
    }

    private void setACL(final ACL acl, final char c) throws MessagingException {
        doOptionalCommand("ACL not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.18
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                iMAPProtocol.setACL(IMAPFolder.this.fullName, c, acl);
                return null;
            }
        });
    }

    public synchronized String[] getAttributes() throws MessagingException {
        checkExists();
        if (this.attributes == null) {
            exists();
        }
        return this.attributes == null ? new String[0] : (String[]) this.attributes.clone();
    }

    public void idle() throws MessagingException {
        idle(false);
    }

    public void idle(boolean z) throws MessagingException {
        synchronized (this) {
            if (this.protocol != null && this.protocol.getChannel() != null) {
                throw new MessagingException("idle method not supported with SocketChannels");
            }
        }
        if (startIdle(null)) {
            while (handleIdle(z)) {
            }
            int minIdleTime = ((IMAPStore) this.store).getMinIdleTime();
            if (minIdleTime > 0) {
                try {
                    Thread.sleep(minIdleTime);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    boolean startIdle(final IdleManager idleManager) throws MessagingException {
        boolean zBooleanValue;
        IdleManager idleManager2;
        synchronized (this) {
            checkOpened();
            if (idleManager != null && (idleManager2 = this.idleManager) != null && idleManager != idleManager2) {
                throw new MessagingException("Folder already being watched by another IdleManager");
            }
            Boolean bool = (Boolean) doOptionalCommand("IDLE not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.19
                @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
                public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                    IdleManager idleManager3;
                    if (IMAPFolder.this.idleState != 1 || (idleManager3 = idleManager) == null || idleManager3 != IMAPFolder.this.idleManager) {
                        if (IMAPFolder.this.idleState == 0) {
                            iMAPProtocol.idleStart();
                            IMAPFolder.this.logger.finest("startIdle: set to IDLE");
                            IMAPFolder.this.idleState = 1;
                            IMAPFolder.this.idleManager = idleManager;
                            return Boolean.TRUE;
                        }
                        try {
                            IMAPFolder.this.messageCacheLock.wait();
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                        return Boolean.FALSE;
                    }
                    return Boolean.TRUE;
                }
            });
            this.logger.log(Level.FINEST, "startIdle: return {0}", bool);
            zBooleanValue = bool.booleanValue();
        }
        return zBooleanValue;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x0096, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    boolean handleIdle(boolean r8) throws javax.mail.MessagingException {
        /*
            r7 = this;
        L0:
            com.sun.mail.imap.protocol.IMAPProtocol r0 = r7.protocol
            com.sun.mail.iap.Response r0 = r0.readIdleResponse()
            java.lang.Object r1 = r7.messageCacheLock     // Catch: com.sun.mail.iap.ProtocolException -> Lac com.sun.mail.iap.ConnectionException -> Lb7
            monitor-enter(r1)     // Catch: com.sun.mail.iap.ProtocolException -> Lac com.sun.mail.iap.ConnectionException -> Lb7
            boolean r2 = r0.isBYE()     // Catch: java.lang.Throwable -> La9
            r3 = 2
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L5e
            boolean r2 = r0.isSynthetic()     // Catch: java.lang.Throwable -> La9
            if (r2 == 0) goto L5e
            int r2 = r7.idleState     // Catch: java.lang.Throwable -> La9
            if (r2 != r5) goto L5e
            java.lang.Exception r2 = r0.getException()     // Catch: java.lang.Throwable -> La9
            boolean r6 = r2 instanceof java.io.InterruptedIOException     // Catch: java.lang.Throwable -> La9
            if (r6 == 0) goto L5e
            r6 = r2
            java.io.InterruptedIOException r6 = (java.io.InterruptedIOException) r6     // Catch: java.lang.Throwable -> La9
            int r6 = r6.bytesTransferred     // Catch: java.lang.Throwable -> La9
            if (r6 != 0) goto L5e
            boolean r2 = r2 instanceof java.net.SocketTimeoutException     // Catch: java.lang.Throwable -> La9
            if (r2 == 0) goto L38
            com.sun.mail.util.MailLogger r0 = r7.logger     // Catch: java.lang.Throwable -> La9
            java.lang.String r2 = "handleIdle: ignoring socket timeout"
            r0.finest(r2)     // Catch: java.lang.Throwable -> La9
            r0 = r4
            goto L5c
        L38:
            com.sun.mail.util.MailLogger r2 = r7.logger     // Catch: java.lang.Throwable -> La9
            java.lang.String r4 = "handleIdle: interrupting IDLE"
            r2.finest(r4)     // Catch: java.lang.Throwable -> La9
            com.sun.mail.imap.IdleManager r2 = r7.idleManager     // Catch: java.lang.Throwable -> La9
            if (r2 == 0) goto L4e
            com.sun.mail.util.MailLogger r3 = r7.logger     // Catch: java.lang.Throwable -> La9
            java.lang.String r4 = "handleIdle: request IdleManager to abort"
            r3.finest(r4)     // Catch: java.lang.Throwable -> La9
            r2.requestAbort(r7)     // Catch: java.lang.Throwable -> La9
            goto L5c
        L4e:
            com.sun.mail.util.MailLogger r2 = r7.logger     // Catch: java.lang.Throwable -> La9
            java.lang.String r4 = "handleIdle: abort IDLE"
            r2.finest(r4)     // Catch: java.lang.Throwable -> La9
            com.sun.mail.imap.protocol.IMAPProtocol r2 = r7.protocol     // Catch: java.lang.Throwable -> La9
            r2.idleAbort()     // Catch: java.lang.Throwable -> La9
            r7.idleState = r3     // Catch: java.lang.Throwable -> La9
        L5c:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> La9
            goto L7a
        L5e:
            r2 = 0
            com.sun.mail.imap.protocol.IMAPProtocol r6 = r7.protocol     // Catch: java.lang.Throwable -> L97
            if (r6 == 0) goto L85
            com.sun.mail.imap.protocol.IMAPProtocol r6 = r7.protocol     // Catch: java.lang.Throwable -> L97
            boolean r6 = r6.processIdleResponse(r0)     // Catch: java.lang.Throwable -> L97
            if (r6 != 0) goto L6c
            goto L85
        L6c:
            if (r8 == 0) goto L79
            int r2 = r7.idleState     // Catch: java.lang.Throwable -> La9
            if (r2 != r5) goto L79
            com.sun.mail.imap.protocol.IMAPProtocol r2 = r7.protocol     // Catch: java.lang.Exception -> L77 java.lang.Throwable -> La9
            r2.idleAbort()     // Catch: java.lang.Exception -> L77 java.lang.Throwable -> La9
        L77:
            r7.idleState = r3     // Catch: java.lang.Throwable -> La9
        L79:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> La9
        L7a:
            if (r0 == 0) goto L0
            com.sun.mail.imap.protocol.IMAPProtocol r0 = r7.protocol
            boolean r0 = r0.hasResponse()
            if (r0 != 0) goto L0
            return r5
        L85:
            com.sun.mail.util.MailLogger r8 = r7.logger     // Catch: java.lang.Throwable -> La9
            java.lang.String r0 = "handleIdle: set to RUNNING"
            r8.finest(r0)     // Catch: java.lang.Throwable -> La9
            r7.idleState = r2     // Catch: java.lang.Throwable -> La9
            r7.idleManager = r4     // Catch: java.lang.Throwable -> La9
            java.lang.Object r8 = r7.messageCacheLock     // Catch: java.lang.Throwable -> La9
            r8.notifyAll()     // Catch: java.lang.Throwable -> La9
            monitor-exit(r1)     // Catch: java.lang.Throwable -> La9
            return r2
        L97:
            r8 = move-exception
            com.sun.mail.util.MailLogger r0 = r7.logger     // Catch: java.lang.Throwable -> La9
            java.lang.String r3 = "handleIdle: set to RUNNING"
            r0.finest(r3)     // Catch: java.lang.Throwable -> La9
            r7.idleState = r2     // Catch: java.lang.Throwable -> La9
            r7.idleManager = r4     // Catch: java.lang.Throwable -> La9
            java.lang.Object r0 = r7.messageCacheLock     // Catch: java.lang.Throwable -> La9
            r0.notifyAll()     // Catch: java.lang.Throwable -> La9
            throw r8     // Catch: java.lang.Throwable -> La9
        La9:
            r8 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> La9
            throw r8     // Catch: com.sun.mail.iap.ProtocolException -> Lac com.sun.mail.iap.ConnectionException -> Lb7
        Lac:
            r8 = move-exception
            javax.mail.MessagingException r0 = new javax.mail.MessagingException
            java.lang.String r1 = r8.getMessage()
            r0.<init>(r1, r8)
            throw r0
        Lb7:
            r8 = move-exception
            javax.mail.FolderClosedException r0 = new javax.mail.FolderClosedException
            java.lang.String r8 = r8.getMessage()
            r0.<init>(r7, r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.imap.IMAPFolder.handleIdle(boolean):boolean");
    }

    void waitIfIdle() throws ProtocolException {
        while (true) {
            int i = this.idleState;
            if (i == 0) {
                return;
            }
            if (i == 1) {
                IdleManager idleManager = this.idleManager;
                if (idleManager != null) {
                    this.logger.finest("waitIfIdle: request IdleManager to abort");
                    idleManager.requestAbort(this);
                } else {
                    this.logger.finest("waitIfIdle: abort IDLE");
                    this.protocol.idleAbort();
                    this.idleState = 2;
                }
            } else {
                this.logger.log(Level.FINEST, "waitIfIdle: idleState {0}", Integer.valueOf(this.idleState));
            }
            try {
                if (this.logger.isLoggable(Level.FINEST)) {
                    this.logger.finest("waitIfIdle: wait to be not idle: " + Thread.currentThread());
                }
                this.messageCacheLock.wait();
                if (this.logger.isLoggable(Level.FINEST)) {
                    this.logger.finest("waitIfIdle: wait done, idleState " + this.idleState + ": " + Thread.currentThread());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ProtocolException("Interrupted waitIfIdle", e);
            }
        }
    }

    void idleAbort() {
        synchronized (this.messageCacheLock) {
            if (this.idleState == 1 && this.protocol != null) {
                this.protocol.idleAbort();
                this.idleState = 2;
            }
        }
    }

    void idleAbortWait() {
        synchronized (this.messageCacheLock) {
            if (this.idleState == 1 && this.protocol != null) {
                this.protocol.idleAbort();
                this.idleState = 2;
                do {
                    try {
                    } catch (Exception e) {
                        this.logger.log(Level.FINEST, "Exception in idleAbortWait", (Throwable) e);
                    }
                } while (handleIdle(false));
                this.logger.finest("IDLE aborted");
            }
        }
    }

    SocketChannel getChannel() {
        if (this.protocol != null) {
            return this.protocol.getChannel();
        }
        return null;
    }

    public Map<String, String> id(final Map<String, String> map) throws MessagingException {
        checkOpened();
        return (Map) doOptionalCommand("ID not supported", new ProtocolCommand() { // from class: com.sun.mail.imap.IMAPFolder.20
            @Override // com.sun.mail.imap.IMAPFolder.ProtocolCommand
            public Object doCommand(IMAPProtocol iMAPProtocol) throws ProtocolException {
                return iMAPProtocol.id(map);
            }
        });
    }

    public synchronized long getStatusItem(String str) throws MessagingException {
        if (this.opened) {
            return -1L;
        }
        checkExists();
        IMAPProtocol storeProtocol = null;
        try {
            try {
                try {
                    storeProtocol = getStoreProtocol();
                    Status status = storeProtocol.status(this.fullName, new String[]{str});
                    return status != null ? status.getItem(str) : -1L;
                } catch (ConnectionException e) {
                    throw new StoreClosedException(this.store, e.getMessage());
                } catch (ProtocolException e2) {
                    throw new MessagingException(e2.getMessage(), e2);
                }
            } catch (BadCommandException unused) {
                return -1L;
            }
        } finally {
            releaseStoreProtocol(storeProtocol);
        }
    }

    @Override // com.sun.mail.iap.ResponseHandler
    public void handleResponse(Response response) {
        if (response.isOK() || response.isNO() || response.isBAD() || response.isBYE()) {
            ((IMAPStore) this.store).handleResponseCode(response);
        }
        int i = 0;
        if (response.isBYE()) {
            if (this.opened) {
                cleanup(false);
                return;
            }
            return;
        }
        if (response.isOK()) {
            response.skipSpaces();
            if (response.readByte() == 91 && response.readAtom().equalsIgnoreCase("HIGHESTMODSEQ")) {
                this.highestmodseq = response.readLong();
            }
            response.reset();
            return;
        }
        if (response.isUnTagged()) {
            if (!(response instanceof IMAPResponse)) {
                this.logger.fine("UNEXPECTED RESPONSE : " + response.toString());
                return;
            }
            IMAPResponse iMAPResponse = (IMAPResponse) response;
            if (iMAPResponse.keyEquals("EXISTS")) {
                int number = iMAPResponse.getNumber();
                int i2 = this.realTotal;
                if (number <= i2) {
                    return;
                }
                int i3 = number - i2;
                Message[] messageArr = new Message[i3];
                this.messageCache.addMessages(i3, i2 + 1);
                int i4 = this.total;
                this.realTotal += i3;
                this.total += i3;
                if (this.hasMessageCountListener) {
                    while (i < i3) {
                        i4++;
                        messageArr[i] = this.messageCache.getMessage(i4);
                        i++;
                    }
                    notifyMessageAddedListeners(messageArr);
                    return;
                }
                return;
            }
            if (iMAPResponse.keyEquals("EXPUNGE")) {
                int number2 = iMAPResponse.getNumber();
                if (number2 > this.realTotal) {
                    return;
                }
                Message[] messageArr2 = null;
                if (this.doExpungeNotification && this.hasMessageCountListener) {
                    Message[] messageArr3 = {getMessageBySeqNumber(number2)};
                    if (messageArr3[0] != null) {
                        messageArr2 = messageArr3;
                    }
                }
                this.messageCache.expungeMessage(number2);
                this.realTotal--;
                if (messageArr2 != null) {
                    notifyMessageRemovedListeners(false, messageArr2);
                    return;
                }
                return;
            }
            if (iMAPResponse.keyEquals("VANISHED")) {
                if (iMAPResponse.readAtomStringList() == null) {
                    UIDSet[] uIDSets = UIDSet.parseUIDSets(iMAPResponse.readAtom());
                    this.realTotal = (int) (((long) this.realTotal) - UIDSet.size(uIDSets));
                    Message[] messageArrCreateMessagesForUIDs = createMessagesForUIDs(UIDSet.toArray(uIDSets));
                    int length = messageArrCreateMessagesForUIDs.length;
                    while (i < length) {
                        Message message = messageArrCreateMessagesForUIDs[i];
                        if (message.getMessageNumber() > 0) {
                            this.messageCache.expungeMessage(message.getMessageNumber());
                        }
                        i++;
                    }
                    if (this.doExpungeNotification && this.hasMessageCountListener) {
                        notifyMessageRemovedListeners(true, messageArrCreateMessagesForUIDs);
                        return;
                    }
                    return;
                }
                return;
            }
            if (iMAPResponse.keyEquals("FETCH")) {
                Message messageProcessFetchResponse = processFetchResponse((FetchResponse) iMAPResponse);
                if (messageProcessFetchResponse != null) {
                    notifyMessageChangedListeners(1, messageProcessFetchResponse);
                    return;
                }
                return;
            }
            if (iMAPResponse.keyEquals("RECENT")) {
                this.recent = iMAPResponse.getNumber();
            }
        }
    }

    private Message processFetchResponse(FetchResponse fetchResponse) {
        boolean z;
        IMAPMessage messageBySeqNumber = getMessageBySeqNumber(fetchResponse.getNumber());
        if (messageBySeqNumber == null) {
            return messageBySeqNumber;
        }
        UID uid = (UID) fetchResponse.getItem(UID.class);
        boolean z2 = true;
        if (uid == null || messageBySeqNumber.getUID() == uid.uid) {
            z = false;
        } else {
            messageBySeqNumber.setUID(uid.uid);
            if (this.uidTable == null) {
                this.uidTable = new Hashtable<>();
            }
            this.uidTable.put(Long.valueOf(uid.uid), messageBySeqNumber);
            z = true;
        }
        MODSEQ modseq = (MODSEQ) fetchResponse.getItem(MODSEQ.class);
        if (modseq != null && messageBySeqNumber._getModSeq() != modseq.modseq) {
            messageBySeqNumber.setModSeq(modseq.modseq);
            z = true;
        }
        FLAGS flags = (FLAGS) fetchResponse.getItem(FLAGS.class);
        if (flags != null) {
            messageBySeqNumber._setFlags(flags);
        } else {
            z2 = z;
        }
        messageBySeqNumber.handleExtensionFetchItems(fetchResponse.getExtensionItems());
        if (z2) {
            return messageBySeqNumber;
        }
        return null;
    }

    void handleResponses(Response[] responseArr) {
        for (Response response : responseArr) {
            if (response != null) {
                handleResponse(response);
            }
        }
    }

    protected synchronized IMAPProtocol getStoreProtocol() throws ProtocolException {
        this.connectionPoolLogger.fine("getStoreProtocol() borrowing a connection");
        return ((IMAPStore) this.store).getFolderStoreProtocol();
    }

    protected synchronized void throwClosedException(ConnectionException connectionException) throws StoreClosedException, FolderClosedException {
        if ((this.protocol != null && connectionException.getProtocol() == this.protocol) || (this.protocol == null && !this.reallyClosed)) {
            throw new FolderClosedException(this, connectionException.getMessage());
        }
        throw new StoreClosedException(this.store, connectionException.getMessage());
    }

    protected IMAPProtocol getProtocol() throws ProtocolException {
        waitIfIdle();
        if (this.protocol == null) {
            throw new ConnectionException("Connection closed");
        }
        return this.protocol;
    }

    public Object doCommand(ProtocolCommand protocolCommand) throws MessagingException {
        try {
            return doProtocolCommand(protocolCommand);
        } catch (ConnectionException e) {
            throwClosedException(e);
            return null;
        } catch (ProtocolException e2) {
            throw new MessagingException(e2.getMessage(), e2);
        }
    }

    public Object doOptionalCommand(String str, ProtocolCommand protocolCommand) throws MessagingException {
        try {
            return doProtocolCommand(protocolCommand);
        } catch (BadCommandException e) {
            throw new MessagingException(str, e);
        } catch (ConnectionException e2) {
            throwClosedException(e2);
            return null;
        } catch (ProtocolException e3) {
            throw new MessagingException(e3.getMessage(), e3);
        }
    }

    public Object doCommandIgnoreFailure(ProtocolCommand protocolCommand) throws MessagingException {
        try {
            return doProtocolCommand(protocolCommand);
        } catch (CommandFailedException unused) {
            return null;
        } catch (ConnectionException e) {
            throwClosedException(e);
            return null;
        } catch (ProtocolException e2) {
            throw new MessagingException(e2.getMessage(), e2);
        }
    }

    protected synchronized Object doProtocolCommand(ProtocolCommand protocolCommand) throws ProtocolException {
        IMAPProtocol storeProtocol;
        Object objDoCommand;
        if (this.protocol != null) {
            synchronized (this.messageCacheLock) {
                objDoCommand = protocolCommand.doCommand(getProtocol());
            }
            return objDoCommand;
        }
        try {
            storeProtocol = getStoreProtocol();
        } catch (Throwable th) {
            th = th;
            storeProtocol = null;
        }
        try {
            Object objDoCommand2 = protocolCommand.doCommand(storeProtocol);
            releaseStoreProtocol(storeProtocol);
            return objDoCommand2;
        } catch (Throwable th2) {
            th = th2;
            releaseStoreProtocol(storeProtocol);
            throw th;
        }
    }

    protected synchronized void releaseStoreProtocol(IMAPProtocol iMAPProtocol) {
        if (iMAPProtocol != this.protocol) {
            ((IMAPStore) this.store).releaseFolderStoreProtocol(iMAPProtocol);
        } else {
            this.logger.fine("releasing our protocol as store protocol?");
        }
    }

    protected void releaseProtocol(boolean z) {
        if (this.protocol != null) {
            this.protocol.removeResponseHandler(this);
            if (z) {
                ((IMAPStore) this.store).releaseProtocol(this, this.protocol);
            } else {
                this.protocol.disconnect();
                ((IMAPStore) this.store).releaseProtocol(this, null);
            }
            this.protocol = null;
        }
    }

    protected void keepConnectionAlive(boolean z) throws ProtocolException {
        if (this.protocol == null) {
            return;
        }
        if (System.currentTimeMillis() - this.protocol.getTimestamp() > 1000) {
            waitIfIdle();
            if (this.protocol != null) {
                this.protocol.noop();
            }
        }
        if (z && ((IMAPStore) this.store).hasSeparateStoreConnection()) {
            IMAPProtocol folderStoreProtocol = null;
            try {
                folderStoreProtocol = ((IMAPStore) this.store).getFolderStoreProtocol();
                if (System.currentTimeMillis() - folderStoreProtocol.getTimestamp() > 1000) {
                    folderStoreProtocol.noop();
                }
            } finally {
                ((IMAPStore) this.store).releaseFolderStoreProtocol(folderStoreProtocol);
            }
        }
    }

    protected IMAPMessage getMessageBySeqNumber(int i) {
        if (i > this.messageCache.size()) {
            if (!this.logger.isLoggable(Level.FINE)) {
                return null;
            }
            this.logger.fine("ignoring message number " + i + " outside range " + this.messageCache.size());
            return null;
        }
        return this.messageCache.getMessageBySeqnum(i);
    }

    protected IMAPMessage[] getMessagesBySeqNumbers(int[] iArr) {
        int length = iArr.length;
        IMAPMessage[] iMAPMessageArr = new IMAPMessage[length];
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            IMAPMessage messageBySeqNumber = getMessageBySeqNumber(iArr[i2]);
            iMAPMessageArr[i2] = messageBySeqNumber;
            if (messageBySeqNumber == null) {
                i++;
            }
        }
        if (i <= 0) {
            return iMAPMessageArr;
        }
        IMAPMessage[] iMAPMessageArr2 = new IMAPMessage[iArr.length - i];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            IMAPMessage iMAPMessage = iMAPMessageArr[i4];
            if (iMAPMessage != null) {
                iMAPMessageArr2[i3] = iMAPMessage;
                i3++;
            }
        }
        return iMAPMessageArr2;
    }

    private boolean isDirectory() {
        return (this.type & 2) != 0;
    }
}
