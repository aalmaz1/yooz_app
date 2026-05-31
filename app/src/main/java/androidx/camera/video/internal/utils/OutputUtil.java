package androidx.camera.video.internal.utils;

import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class OutputUtil {
    private static final String TAG = "OutputUtil";

    private OutputUtil() {
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getAbsolutePathFromUri(android.content.ContentResolver r9, android.net.Uri r10, java.lang.String r11) throws java.lang.Throwable {
        /*
            r0 = 0
            r1 = 1
            r2 = 0
            java.lang.String[] r5 = new java.lang.String[r1]     // Catch: java.lang.Throwable -> L2b java.lang.RuntimeException -> L2d
            r5[r0] = r11     // Catch: java.lang.Throwable -> L2b java.lang.RuntimeException -> L2d
            r6 = 0
            r7 = 0
            r8 = 0
            r3 = r9
            r4 = r10
            android.database.Cursor r9 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> L2b java.lang.RuntimeException -> L2d
            if (r9 != 0) goto L18
            if (r9 == 0) goto L17
            r9.close()
        L17:
            return r2
        L18:
            int r11 = r9.getColumnIndexOrThrow(r11)     // Catch: java.lang.RuntimeException -> L29 java.lang.Throwable -> L4f
            r9.moveToFirst()     // Catch: java.lang.RuntimeException -> L29 java.lang.Throwable -> L4f
            java.lang.String r10 = r9.getString(r11)     // Catch: java.lang.RuntimeException -> L29 java.lang.Throwable -> L4f
            if (r9 == 0) goto L28
            r9.close()
        L28:
            return r10
        L29:
            r11 = move-exception
            goto L2f
        L2b:
            r10 = move-exception
            goto L51
        L2d:
            r11 = move-exception
            r9 = r2
        L2f:
            java.lang.String r3 = "OutputUtil"
            java.lang.String r4 = "Failed in getting absolute path for Uri %s with Exception %s"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L4f
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L4f
            r5[r0] = r10     // Catch: java.lang.Throwable -> L4f
            java.lang.String r10 = r11.toString()     // Catch: java.lang.Throwable -> L4f
            r5[r1] = r10     // Catch: java.lang.Throwable -> L4f
            java.lang.String r10 = java.lang.String.format(r4, r5)     // Catch: java.lang.Throwable -> L4f
            androidx.camera.core.Logger.e(r3, r10)     // Catch: java.lang.Throwable -> L4f
            if (r9 == 0) goto L4e
            r9.close()
        L4e:
            return r2
        L4f:
            r10 = move-exception
            r2 = r9
        L51:
            if (r2 == 0) goto L56
            r2.close()
        L56:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.video.internal.utils.OutputUtil.getAbsolutePathFromUri(android.content.ContentResolver, android.net.Uri, java.lang.String):java.lang.String");
    }

    public static boolean createParentFolder(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return false;
        }
        return parentFile.exists() ? parentFile.isDirectory() : parentFile.mkdirs();
    }
}
