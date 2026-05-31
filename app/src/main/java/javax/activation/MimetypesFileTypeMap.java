package javax.activation;

import com.sun.activation.registries.LogSupport;
import com.sun.activation.registries.MimeTypeFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Vector;

/* JADX INFO: loaded from: classes3.dex */
public class MimetypesFileTypeMap extends FileTypeMap {
    private static final int PROG = 0;
    private static final String confDir;
    private static final String defaultType = "application/octet-stream";
    private MimeTypeFile[] DB;

    static {
        String str;
        try {
            str = (String) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.activation.MimetypesFileTypeMap.1
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

    public MimetypesFileTypeMap() throws Throwable {
        MimeTypeFile mimeTypeFileLoadFile;
        MimeTypeFile mimeTypeFileLoadFile2;
        Vector vector = new Vector(5);
        vector.addElement(null);
        LogSupport.log("MimetypesFileTypeMap: load HOME");
        try {
            String property = System.getProperty("user.home");
            if (property != null && (mimeTypeFileLoadFile2 = loadFile(property + File.separator + ".mime.types")) != null) {
                vector.addElement(mimeTypeFileLoadFile2);
            }
        } catch (SecurityException unused) {
        }
        LogSupport.log("MimetypesFileTypeMap: load SYS");
        try {
            String str = confDir;
            if (str != null && (mimeTypeFileLoadFile = loadFile(str + "mime.types")) != null) {
                vector.addElement(mimeTypeFileLoadFile);
            }
        } catch (SecurityException unused2) {
        }
        LogSupport.log("MimetypesFileTypeMap: load JAR");
        loadAllResources(vector, "META-INF/mime.types");
        LogSupport.log("MimetypesFileTypeMap: load DEF");
        MimeTypeFile mimeTypeFileLoadResource = loadResource("/META-INF/mimetypes.default");
        if (mimeTypeFileLoadResource != null) {
            vector.addElement(mimeTypeFileLoadResource);
        }
        MimeTypeFile[] mimeTypeFileArr = new MimeTypeFile[vector.size()];
        this.DB = mimeTypeFileArr;
        vector.copyInto(mimeTypeFileArr);
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x008c: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:39:0x008c */
    /* JADX WARN: Removed duplicated region for block: B:46:0x004a A[EXC_TOP_SPLITTER, PHI: r4
      0x004a: PHI (r4v5 java.io.InputStream) = (r4v3 java.io.InputStream), (r4v4 java.io.InputStream), (r4v7 java.io.InputStream) binds: [B:29:0x006e, B:35:0x0087, B:14:0x0048] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x008f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.sun.activation.registries.MimeTypeFile loadResource(java.lang.String r7) throws java.lang.Throwable {
        /*
            r6 = this;
            java.lang.String r0 = "MimetypesFileTypeMap: successfully loaded mime types file: "
            java.lang.String r1 = "MimetypesFileTypeMap: not loading mime types file: "
            java.lang.String r2 = "MimetypesFileTypeMap: can't load "
            r3 = 0
            java.lang.Class r4 = r6.getClass()     // Catch: java.lang.Throwable -> L52 java.lang.SecurityException -> L54 java.io.IOException -> L6f
            java.io.InputStream r4 = javax.activation.SecuritySupport.getResourceAsStream(r4, r7)     // Catch: java.lang.Throwable -> L52 java.lang.SecurityException -> L54 java.io.IOException -> L6f
            if (r4 == 0) goto L32
            com.sun.activation.registries.MimeTypeFile r1 = new com.sun.activation.registries.MimeTypeFile     // Catch: java.lang.SecurityException -> L4e java.io.IOException -> L50 java.lang.Throwable -> L8b
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
        throw new UnsupportedOperationException("Method not decompiled: javax.activation.MimetypesFileTypeMap.loadResource(java.lang.String):com.sun.activation.registries.MimeTypeFile");
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadAllResources(java.util.Vector r9, java.lang.String r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 286
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: javax.activation.MimetypesFileTypeMap.loadAllResources(java.util.Vector, java.lang.String):void");
    }

    private MimeTypeFile loadFile(String str) {
        try {
            return new MimeTypeFile(str);
        } catch (IOException unused) {
            return null;
        }
    }

    public MimetypesFileTypeMap(String str) throws IOException {
        this();
        this.DB[0] = new MimeTypeFile(str);
    }

    public MimetypesFileTypeMap(InputStream inputStream) {
        this();
        try {
            this.DB[0] = new MimeTypeFile(inputStream);
        } catch (IOException unused) {
        }
    }

    public synchronized void addMimeTypes(String str) {
        MimeTypeFile[] mimeTypeFileArr = this.DB;
        if (mimeTypeFileArr[0] == null) {
            mimeTypeFileArr[0] = new MimeTypeFile();
        }
        this.DB[0].appendToRegistry(str);
    }

    @Override // javax.activation.FileTypeMap
    public String getContentType(File file) {
        return getContentType(file.getName());
    }

    @Override // javax.activation.FileTypeMap
    public synchronized String getContentType(String str) {
        String mIMETypeString;
        int iLastIndexOf = str.lastIndexOf(".");
        if (iLastIndexOf < 0) {
            return defaultType;
        }
        String strSubstring = str.substring(iLastIndexOf + 1);
        if (strSubstring.length() == 0) {
            return defaultType;
        }
        int i = 0;
        while (true) {
            MimeTypeFile[] mimeTypeFileArr = this.DB;
            if (i >= mimeTypeFileArr.length) {
                return defaultType;
            }
            MimeTypeFile mimeTypeFile = mimeTypeFileArr[i];
            if (mimeTypeFile != null && (mIMETypeString = mimeTypeFile.getMIMETypeString(strSubstring)) != null) {
                return mIMETypeString;
            }
            i++;
        }
    }
}
