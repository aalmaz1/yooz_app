package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MailcapFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class MailcapCommandMap extends CommandMap {
    private static final int PROG = 0;
    private static final String confDir;
    private MailcapFile[] DB;

    static {
        String str;
        try {
            str = (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.activation.MailcapCommandMap.1
                @Override // java.security.PrivilegedAction
                public Object run() {
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

    public MailcapCommandMap() throws Throwable {
        MailcapFile mailcapFileLoadFile;
        MailcapFile mailcapFileLoadFile2;
        ArrayList arrayList = new ArrayList(5);
        arrayList.add(null);
        LogSupport.log("MailcapCommandMap: load HOME");
        try {
            String property = System.getProperty("user.home");
            if (property != null && (mailcapFileLoadFile2 = loadFile(property + File.separator + ".mailcap")) != null) {
                arrayList.add(mailcapFileLoadFile2);
            }
        } catch (SecurityException unused) {
        }
        LogSupport.log("MailcapCommandMap: load SYS");
        try {
            String str = confDir;
            if (str != null && (mailcapFileLoadFile = loadFile(str + "mailcap")) != null) {
                arrayList.add(mailcapFileLoadFile);
            }
        } catch (SecurityException unused2) {
        }
        LogSupport.log("MailcapCommandMap: load JAR");
        loadAllResources(arrayList, "META-INF/mailcap");
        LogSupport.log("MailcapCommandMap: load DEF");
        MailcapFile mailcapFileLoadResource = loadResource("/META-INF/mailcap.default");
        if (mailcapFileLoadResource != null) {
            arrayList.add(mailcapFileLoadResource);
        }
        MailcapFile[] mailcapFileArr = new MailcapFile[arrayList.size()];
        this.DB = mailcapFileArr;
        this.DB = (MailcapFile[]) arrayList.toArray(mailcapFileArr);
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x008c: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:39:0x008c */
    /* JADX WARN: Removed duplicated region for block: B:46:0x004a A[EXC_TOP_SPLITTER, PHI: r4
      0x004a: PHI (r4v5 java.io.InputStream) = (r4v3 java.io.InputStream), (r4v4 java.io.InputStream), (r4v7 java.io.InputStream) binds: [B:29:0x006e, B:35:0x0087, B:14:0x0048] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.sun.activation.registries.MailcapFile loadResource(java.lang.String r7) throws java.lang.Throwable {
        /*
            r6 = this;
            java.lang.String r0 = "MailcapCommandMap: successfully loaded mailcap file: "
            java.lang.String r1 = "MailcapCommandMap: not loading mailcap file: "
            java.lang.String r2 = "MailcapCommandMap: can't load "
            r3 = 0
            java.lang.Class r4 = r6.getClass()     // Catch: java.lang.Throwable -> L52 java.lang.SecurityException -> L54 java.io.IOException -> L6f
            java.io.InputStream r4 = javax.activation.SecuritySupport.getResourceAsStream(r4, r7)     // Catch: java.lang.Throwable -> L52 java.lang.SecurityException -> L54 java.io.IOException -> L6f
            if (r4 == 0) goto L32
            com.sun.activation.registries.MailcapFile r1 = new com.sun.activation.registries.MailcapFile     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            r1.<init>(r4)     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            boolean r5 = com.sun.activation.registries.LogSupport.isLoggable()     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            if (r5 == 0) goto L2c
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            r5.<init>(r0)     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            java.lang.StringBuilder r0 = r5.append(r7)     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            java.lang.String r0 = r0.toString()     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            com.sun.activation.registries.LogSupport.log(r0)     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
        L2c:
            if (r4 == 0) goto L31
            r4.close()     // Catch: java.io.IOException -> L31
        L31:
            return r1
        L32:
            boolean r0 = com.sun.activation.registries.LogSupport.isLoggable()     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            if (r0 == 0) goto L48
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            r0.<init>(r1)     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            java.lang.String r0 = r0.toString()     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
            com.sun.activation.registries.LogSupport.log(r0)     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
        L48:
            if (r4 == 0) goto L8a
        L4a:
            r4.close()     // Catch: java.io.IOException -> L8a
            goto L8a
        L4e:
            r0 = move-exception
            goto L56
        L50:
            r0 = move-exception
            goto L71
        L52:
            r7 = move-exception
            goto L8d
        L54:
            r0 = move-exception
            r4 = r3
        L56:
            boolean r1 = com.sun.activation.registries.LogSupport.isLoggable()     // Catch: java.lang.Throwable -> L8b
            if (r1 == 0) goto L6c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8b
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L8b
            java.lang.StringBuilder r7 = r1.append(r7)     // Catch: java.lang.Throwable -> L8b
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L8b
            com.sun.activation.registries.LogSupport.log(r7, r0)     // Catch: java.lang.Throwable -> L8b
        L6c:
            if (r4 == 0) goto L8a
            goto L4a
        L6f:
            r0 = move-exception
            r4 = r3
        L71:
            boolean r1 = com.sun.activation.registries.LogSupport.isLoggable()     // Catch: java.lang.Throwable -> L8b
            if (r1 == 0) goto L87
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8b
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L8b
            java.lang.StringBuilder r7 = r1.append(r7)     // Catch: java.lang.Throwable -> L8b
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L8b
            com.sun.activation.registries.LogSupport.log(r7, r0)     // Catch: java.lang.Throwable -> L8b
        L87:
            if (r4 == 0) goto L8a
            goto L4a
        L8a:
            return r3
        L8b:
            r7 = move-exception
            r3 = r4
        L8d:
            if (r3 == 0) goto L92
            r3.close()     // Catch: java.io.IOException -> L92
        L92:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.activation.MailcapCommandMap.loadResource(java.lang.String):com.sun.activation.registries.MailcapFile");
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadAllResources(java.util.List r9, java.lang.String r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.activation.MailcapCommandMap.loadAllResources(java.util.List, java.lang.String):void");
    }

    private MailcapFile loadFile(String str) {
        try {
            return new MailcapFile(str);
        } catch (IOException unused) {
            return null;
        }
    }

    public MailcapCommandMap(String str) throws IOException {
        this();
        if (LogSupport.isLoggable()) {
            LogSupport.log("MailcapCommandMap: load PROG from " + str);
        }
        MailcapFile[] mailcapFileArr = this.DB;
        if (mailcapFileArr[0] == null) {
            mailcapFileArr[0] = new MailcapFile(str);
        }
    }

    public MailcapCommandMap(InputStream inputStream) {
        this();
        LogSupport.log("MailcapCommandMap: load PROG");
        MailcapFile[] mailcapFileArr = this.DB;
        if (mailcapFileArr[0] == null) {
            try {
                mailcapFileArr[0] = new MailcapFile(inputStream);
            } catch (IOException unused) {
            }
        }
    }

    @Override // javax.activation.CommandMap
    public synchronized CommandInfo[] getPreferredCommands(String str) {
        ArrayList arrayList;
        Map mailcapFallbackList;
        Map mailcapList;
        arrayList = new ArrayList();
        if (str != null) {
            str = str.toLowerCase(Locale.ENGLISH);
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            MailcapFile[] mailcapFileArr = this.DB;
            if (i2 >= mailcapFileArr.length) {
                break;
            }
            MailcapFile mailcapFile = mailcapFileArr[i2];
            if (mailcapFile != null && (mailcapList = mailcapFile.getMailcapList(str)) != null) {
                appendPrefCmdsToList(mailcapList, arrayList);
            }
            i2++;
        }
        while (true) {
            MailcapFile[] mailcapFileArr2 = this.DB;
            if (i < mailcapFileArr2.length) {
                MailcapFile mailcapFile2 = mailcapFileArr2[i];
                if (mailcapFile2 != null && (mailcapFallbackList = mailcapFile2.getMailcapFallbackList(str)) != null) {
                    appendPrefCmdsToList(mailcapFallbackList, arrayList);
                }
                i++;
            }
        }
        return (CommandInfo[]) arrayList.toArray(new CommandInfo[arrayList.size()]);
    }

    private void appendPrefCmdsToList(Map map, List list) {
        for (String str : map.keySet()) {
            if (!checkForVerb(list, str)) {
                list.add(new CommandInfo(str, (String) ((List) map.get(str)).get(0)));
            }
        }
    }

    private boolean checkForVerb(List list, String str) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((CommandInfo) it.next()).getCommandName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // javax.activation.CommandMap
    public synchronized CommandInfo[] getAllCommands(String str) {
        ArrayList arrayList;
        Map mailcapFallbackList;
        Map mailcapList;
        arrayList = new ArrayList();
        if (str != null) {
            str = str.toLowerCase(Locale.ENGLISH);
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            MailcapFile[] mailcapFileArr = this.DB;
            if (i2 >= mailcapFileArr.length) {
                break;
            }
            MailcapFile mailcapFile = mailcapFileArr[i2];
            if (mailcapFile != null && (mailcapList = mailcapFile.getMailcapList(str)) != null) {
                appendCmdsToList(mailcapList, arrayList);
            }
            i2++;
        }
        while (true) {
            MailcapFile[] mailcapFileArr2 = this.DB;
            if (i < mailcapFileArr2.length) {
                MailcapFile mailcapFile2 = mailcapFileArr2[i];
                if (mailcapFile2 != null && (mailcapFallbackList = mailcapFile2.getMailcapFallbackList(str)) != null) {
                    appendCmdsToList(mailcapFallbackList, arrayList);
                }
                i++;
            }
        }
        return (CommandInfo[]) arrayList.toArray(new CommandInfo[arrayList.size()]);
    }

    private void appendCmdsToList(Map map, List list) {
        for (String str : map.keySet()) {
            Iterator it = ((List) map.get(str)).iterator();
            while (it.hasNext()) {
                list.add(new CommandInfo(str, (String) it.next()));
            }
        }
    }

    @Override // javax.activation.CommandMap
    public synchronized CommandInfo getCommand(String str, String str2) {
        Map mailcapFallbackList;
        List list;
        String str3;
        Map mailcapList;
        List list2;
        String str4;
        if (str != null) {
            try {
                str = str.toLowerCase(Locale.ENGLISH);
            } catch (Throwable th) {
                throw th;
            }
        }
        int i = 0;
        while (true) {
            MailcapFile[] mailcapFileArr = this.DB;
            if (i < mailcapFileArr.length) {
                MailcapFile mailcapFile = mailcapFileArr[i];
                if (mailcapFile != null && (mailcapList = mailcapFile.getMailcapList(str)) != null && (list2 = (List) mailcapList.get(str2)) != null && (str4 = (String) list2.get(0)) != null) {
                    return new CommandInfo(str2, str4);
                }
                i++;
            } else {
                int i2 = 0;
                while (true) {
                    MailcapFile[] mailcapFileArr2 = this.DB;
                    if (i2 >= mailcapFileArr2.length) {
                        return null;
                    }
                    MailcapFile mailcapFile2 = mailcapFileArr2[i2];
                    if (mailcapFile2 != null && (mailcapFallbackList = mailcapFile2.getMailcapFallbackList(str)) != null && (list = (List) mailcapFallbackList.get(str2)) != null && (str3 = (String) list.get(0)) != null) {
                        return new CommandInfo(str2, str3);
                    }
                    i2++;
                }
            }
        }
    }

    public synchronized void addMailcap(String str) {
        LogSupport.log("MailcapCommandMap: add to PROG");
        MailcapFile[] mailcapFileArr = this.DB;
        if (mailcapFileArr[0] == null) {
            mailcapFileArr[0] = new MailcapFile();
        }
        this.DB[0].appendToMailcap(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x006e, code lost:
    
        r1 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x006f, code lost:
    
        r2 = r4.DB;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0072, code lost:
    
        if (r1 >= r2.length) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0076, code lost:
    
        if (r2[r1] != null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007d, code lost:
    
        if (com.sun.activation.registries.LogSupport.isLoggable() == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x007f, code lost:
    
        com.sun.activation.registries.LogSupport.log("  search fallback DB #" + r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0095, code lost:
    
        r2 = r4.DB[r1].getMailcapFallbackList(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009d, code lost:
    
        if (r2 == null) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x009f, code lost:
    
        r2 = (java.util.List) r2.get("content-handler");
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00a7, code lost:
    
        if (r2 == null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00a9, code lost:
    
        r2 = getDataContentHandler((java.lang.String) r2.get(0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b3, code lost:
    
        if (r2 == null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b6, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00b7, code lost:
    
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bb, code lost:
    
        return null;
     */
    @Override // javax.activation.CommandMap
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized javax.activation.DataContentHandler createDataContentHandler(java.lang.String r5) {
        /*
            r4 = this;
            java.lang.String r0 = "MailcapCommandMap: createDataContentHandler for "
            monitor-enter(r4)
            boolean r1 = com.sun.activation.registries.LogSupport.isLoggable()     // Catch: java.lang.Throwable -> Lbd
            if (r1 == 0) goto L19
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbd
            r1.<init>(r0)     // Catch: java.lang.Throwable -> Lbd
            java.lang.StringBuilder r0 = r1.append(r5)     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lbd
            com.sun.activation.registries.LogSupport.log(r0)     // Catch: java.lang.Throwable -> Lbd
        L19:
            if (r5 == 0) goto L21
            java.util.Locale r0 = java.util.Locale.ENGLISH     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r5 = r5.toLowerCase(r0)     // Catch: java.lang.Throwable -> Lbd
        L21:
            r0 = 0
            r1 = r0
        L23:
            com.sun.activation.registries.MailcapFile[] r2 = r4.DB     // Catch: java.lang.Throwable -> Lbd
            int r3 = r2.length     // Catch: java.lang.Throwable -> Lbd
            if (r1 >= r3) goto L6e
            r2 = r2[r1]     // Catch: java.lang.Throwable -> Lbd
            if (r2 != 0) goto L2d
            goto L6b
        L2d:
            boolean r2 = com.sun.activation.registries.LogSupport.isLoggable()     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto L49
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbd
            r2.<init>()     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r3 = "  search DB #"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbd
            java.lang.StringBuilder r2 = r2.append(r1)     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lbd
            com.sun.activation.registries.LogSupport.log(r2)     // Catch: java.lang.Throwable -> Lbd
        L49:
            com.sun.activation.registries.MailcapFile[] r2 = r4.DB     // Catch: java.lang.Throwable -> Lbd
            r2 = r2[r1]     // Catch: java.lang.Throwable -> Lbd
            java.util.Map r2 = r2.getMailcapList(r5)     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto L6b
            java.lang.String r3 = "content-handler"
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Throwable -> Lbd
            java.util.List r2 = (java.util.List) r2     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto L6b
            java.lang.Object r2 = r2.get(r0)     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> Lbd
            javax.activation.DataContentHandler r2 = r4.getDataContentHandler(r2)     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto L6b
            monitor-exit(r4)
            return r2
        L6b:
            int r1 = r1 + 1
            goto L23
        L6e:
            r1 = r0
        L6f:
            com.sun.activation.registries.MailcapFile[] r2 = r4.DB     // Catch: java.lang.Throwable -> Lbd
            int r3 = r2.length     // Catch: java.lang.Throwable -> Lbd
            if (r1 >= r3) goto Lba
            r2 = r2[r1]     // Catch: java.lang.Throwable -> Lbd
            if (r2 != 0) goto L79
            goto Lb7
        L79:
            boolean r2 = com.sun.activation.registries.LogSupport.isLoggable()     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto L95
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbd
            r2.<init>()     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r3 = "  search fallback DB #"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Throwable -> Lbd
            java.lang.StringBuilder r2 = r2.append(r1)     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> Lbd
            com.sun.activation.registries.LogSupport.log(r2)     // Catch: java.lang.Throwable -> Lbd
        L95:
            com.sun.activation.registries.MailcapFile[] r2 = r4.DB     // Catch: java.lang.Throwable -> Lbd
            r2 = r2[r1]     // Catch: java.lang.Throwable -> Lbd
            java.util.Map r2 = r2.getMailcapFallbackList(r5)     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto Lb7
            java.lang.String r3 = "content-handler"
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Throwable -> Lbd
            java.util.List r2 = (java.util.List) r2     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto Lb7
            java.lang.Object r2 = r2.get(r0)     // Catch: java.lang.Throwable -> Lbd
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> Lbd
            javax.activation.DataContentHandler r2 = r4.getDataContentHandler(r2)     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto Lb7
            monitor-exit(r4)
            return r2
        Lb7:
            int r1 = r1 + 1
            goto L6f
        Lba:
            monitor-exit(r4)
            r5 = 0
            return r5
        Lbd:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.activation.MailcapCommandMap.createDataContentHandler(java.lang.String):javax.activation.DataContentHandler");
    }

    private DataContentHandler getDataContentHandler(String str) {
        Class<?> cls;
        if (LogSupport.isLoggable()) {
            LogSupport.log("    got content-handler");
        }
        if (LogSupport.isLoggable()) {
            LogSupport.log("      class " + str);
        }
        try {
            ClassLoader contextClassLoader = SecuritySupport.getContextClassLoader();
            if (contextClassLoader == null) {
                contextClassLoader = getClass().getClassLoader();
            }
            try {
                cls = contextClassLoader.loadClass(str);
            } catch (Exception unused) {
                cls = Class.forName(str);
            }
            if (cls != null) {
                return (DataContentHandler) cls.newInstance();
            }
            return null;
        } catch (ClassNotFoundException e) {
            if (!LogSupport.isLoggable()) {
                return null;
            }
            LogSupport.log("Can't load DCH " + str, e);
            return null;
        } catch (IllegalAccessException e2) {
            if (!LogSupport.isLoggable()) {
                return null;
            }
            LogSupport.log("Can't load DCH " + str, e2);
            return null;
        } catch (InstantiationException e3) {
            if (!LogSupport.isLoggable()) {
                return null;
            }
            LogSupport.log("Can't load DCH " + str, e3);
            return null;
        }
    }

    @Override // javax.activation.CommandMap
    public synchronized String[] getMimeTypes() {
        ArrayList arrayList;
        String[] mimeTypes;
        arrayList = new ArrayList();
        int i = 0;
        while (true) {
            MailcapFile[] mailcapFileArr = this.DB;
            if (i < mailcapFileArr.length) {
                MailcapFile mailcapFile = mailcapFileArr[i];
                if (mailcapFile != null && (mimeTypes = mailcapFile.getMimeTypes()) != null) {
                    for (int i2 = 0; i2 < mimeTypes.length; i2++) {
                        if (!arrayList.contains(mimeTypes[i2])) {
                            arrayList.add(mimeTypes[i2]);
                        }
                    }
                }
                i++;
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public synchronized String[] getNativeCommands(String str) {
        ArrayList arrayList;
        String[] nativeCommands;
        arrayList = new ArrayList();
        if (str != null) {
            str = str.toLowerCase(Locale.ENGLISH);
        }
        int i = 0;
        while (true) {
            MailcapFile[] mailcapFileArr = this.DB;
            if (i < mailcapFileArr.length) {
                MailcapFile mailcapFile = mailcapFileArr[i];
                if (mailcapFile != null && (nativeCommands = mailcapFile.getNativeCommands(str)) != null) {
                    for (int i2 = 0; i2 < nativeCommands.length; i2++) {
                        if (!arrayList.contains(nativeCommands[i2])) {
                            arrayList.add(nativeCommands[i2]);
                        }
                    }
                }
                i++;
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
