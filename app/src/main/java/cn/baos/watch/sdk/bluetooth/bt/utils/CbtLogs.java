package cn.baos.watch.sdk.bluetooth.bt.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import cn.baos.watch.sdk.bluetooth.bt.CbtManager;
import com.fluttercandies.photo_manager.constant.Methods;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CbtLogs {
    public static final int A = 7;
    private static final String ARGS = "args";
    private static final String BOTTOM_BORDER = "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final String BOTTOM_CORNER = "└";
    public static final int D = 3;
    public static final int E = 6;
    private static final int FILE = 16;
    public static final int I = 4;
    private static final int JSON = 32;
    private static final String LEFT_BORDER = "│ ";
    private static final int MAX_LEN = 3000;
    private static final String MIDDLE_BORDER = "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String MIDDLE_CORNER = "├";
    private static final String MIDDLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String NOTHING = "log nothing";
    private static final String NULL = "null";
    private static final String PLACEHOLDER = " ";
    private static final String SIDE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String TOP_BORDER = "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────";
    private static final String TOP_CORNER = "┌";
    public static final int V = 2;
    public static final int W = 5;
    private static final int XML = 48;
    private static ExecutorService sExecutor;
    private static final char[] T = {'V', 'D', 'I', 'W', 'E', 'A'};
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");
    private static final Config CONFIG = new Config();

    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private CbtLogs() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Config getConfig() {
        return CONFIG;
    }

    public static void v(Object... objArr) {
        log(2, CONFIG.mGlobalTag, objArr);
    }

    public static void vTag(String str, Object... objArr) {
        log(2, str, objArr);
    }

    public static void d(Object... objArr) {
        log(3, CONFIG.mGlobalTag, objArr);
    }

    public static void dTag(String str, Object... objArr) {
        log(3, str, objArr);
    }

    public static void i(Object... objArr) {
        log(4, CONFIG.mGlobalTag, objArr);
    }

    public static void iTag(String str, Object... objArr) {
        log(4, str, objArr);
    }

    public static void w(Object... objArr) {
        log(5, CONFIG.mGlobalTag, objArr);
    }

    public static void wTag(String str, Object... objArr) {
        log(5, str, objArr);
    }

    public static void e(Object... objArr) {
        log(6, CONFIG.mGlobalTag, objArr);
    }

    public static void eTag(String str, Object... objArr) {
        log(6, str, objArr);
    }

    public static void a(Object... objArr) {
        log(7, CONFIG.mGlobalTag, objArr);
    }

    public static void aTag(String str, Object... objArr) {
        log(7, str, objArr);
    }

    public static void file(Object obj) {
        log(19, CONFIG.mGlobalTag, obj);
    }

    public static void file(int i, Object obj) {
        log(i | 16, CONFIG.mGlobalTag, obj);
    }

    public static void file(String str, Object obj) {
        log(19, str, obj);
    }

    public static void file(int i, String str, Object obj) {
        log(i | 16, str, obj);
    }

    public static void json(String str) {
        log(35, CONFIG.mGlobalTag, str);
    }

    public static void json(int i, String str) {
        log(i | 32, CONFIG.mGlobalTag, str);
    }

    public static void json(String str, String str2) {
        log(35, str, str2);
    }

    public static void json(int i, String str, String str2) {
        log(i | 32, str, str2);
    }

    public static void xml(String str) {
        log(51, CONFIG.mGlobalTag, str);
    }

    public static void xml(int i, String str) {
        log(i | 48, CONFIG.mGlobalTag, str);
    }

    public static void xml(String str, String str2) {
        log(51, str, str2);
    }

    public static void xml(int i, String str, String str2) {
        log(i | 48, str, str2);
    }

    public static void log(int i, String str, Object... objArr) {
        Config config = CONFIG;
        if (config.mLogSwitch) {
            if (config.mLog2ConsoleSwitch || config.mLog2FileSwitch) {
                int i2 = i & 15;
                int i3 = i & 240;
                if (i2 >= config.mConsoleFilter || i2 >= config.mFileFilter) {
                    TagHead tagHeadProcessTagAndHead = processTagAndHead(str);
                    String strProcessBody = processBody(i3, objArr);
                    if (config.mLog2ConsoleSwitch && i2 >= config.mConsoleFilter && i3 != 16) {
                        print2Console(i2, tagHeadProcessTagAndHead.tag, tagHeadProcessTagAndHead.consoleHead, strProcessBody);
                    }
                    if ((config.mLog2FileSwitch || i3 == 16) && i2 >= config.mFileFilter) {
                        print2File(i2, tagHeadProcessTagAndHead.tag, tagHeadProcessTagAndHead.fileHead + strProcessBody);
                    }
                }
            }
        }
    }

    private static TagHead processTagAndHead(String str) {
        String strSubstring;
        String str2;
        String strSubstring2;
        Config config = CONFIG;
        if (!config.mTagIsSpace && !config.mLogHeadSwitch) {
            str2 = config.mGlobalTag;
        } else {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            int i = config.mStackOffset + 3;
            if (i >= stackTrace.length) {
                String fileName = getFileName(stackTrace[3]);
                if (config.mTagIsSpace && isSpace(str)) {
                    int iIndexOf = fileName.indexOf(46);
                    strSubstring2 = iIndexOf == -1 ? fileName : fileName.substring(0, iIndexOf);
                } else {
                    strSubstring2 = str;
                }
                return new TagHead(strSubstring2, null, ": ");
            }
            StackTraceElement stackTraceElement = stackTrace[i];
            String fileName2 = getFileName(stackTraceElement);
            if (config.mTagIsSpace && isSpace(str)) {
                int iIndexOf2 = fileName2.indexOf(46);
                strSubstring = iIndexOf2 == -1 ? fileName2 : fileName2.substring(0, iIndexOf2);
            } else {
                strSubstring = str;
            }
            if (config.mLogHeadSwitch) {
                String name = Thread.currentThread().getName();
                String string = new Formatter().format("%s, %s.%s(%s:%d)", name, stackTraceElement.getClassName(), stackTraceElement.getMethodName(), fileName2, Integer.valueOf(stackTraceElement.getLineNumber())).toString();
                String str3 = " [" + string + "]: ";
                if (config.mStackDeep <= 1) {
                    return new TagHead(strSubstring, new String[]{string}, str3);
                }
                int iMin = Math.min(config.mStackDeep, stackTrace.length - i);
                String[] strArr = new String[iMin];
                strArr[0] = string;
                String string2 = new Formatter().format("%" + (name.length() + 2) + "s", "").toString();
                for (int i2 = 1; i2 < iMin; i2++) {
                    StackTraceElement stackTraceElement2 = stackTrace[i2 + i];
                    strArr[i2] = new Formatter().format("%s%s.%s(%s:%d)", string2, stackTraceElement2.getClassName(), stackTraceElement2.getMethodName(), getFileName(stackTraceElement2), Integer.valueOf(stackTraceElement2.getLineNumber())).toString();
                }
                return new TagHead(strSubstring, strArr, str3);
            }
            str2 = strSubstring;
        }
        return new TagHead(str2, null, ": ");
    }

    private static String getFileName(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        if (fileName != null) {
            return fileName;
        }
        String className = stackTraceElement.getClassName();
        String[] strArrSplit = className.split("\\.");
        if (strArrSplit.length > 0) {
            className = strArrSplit[strArrSplit.length - 1];
        }
        int iIndexOf = className.indexOf(36);
        if (iIndexOf != -1) {
            className = className.substring(0, iIndexOf);
        }
        return className + ".java";
    }

    private static String processBody(int i, Object... objArr) {
        String xml;
        String string = NULL;
        if (objArr != null) {
            if (objArr.length == 1) {
                Object obj = objArr[0];
                if (obj != null) {
                    string = obj.toString();
                }
                if (i == 32) {
                    xml = formatJson(string);
                } else if (i == 48) {
                    xml = formatXml(string);
                }
                string = xml;
            } else {
                StringBuilder sb = new StringBuilder();
                int length = objArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    Object obj2 = objArr[i2];
                    sb.append("args[").append(i2).append("] = ").append(obj2 == null ? NULL : obj2.toString()).append(LINE_SEP);
                }
                string = sb.toString();
            }
        }
        return string.length() == 0 ? NOTHING : string;
    }

    private static String formatJson(String str) {
        try {
            if (str.startsWith("{")) {
                str = new JSONObject(str).toString(4);
            } else if (str.startsWith("[")) {
                str = new JSONArray(str).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String formatXml(String str) {
        try {
            StreamSource streamSource = new StreamSource(new StringReader(str));
            StreamResult streamResult = new StreamResult(new StringWriter());
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            transformerNewTransformer.setOutputProperty("indent", "yes");
            transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformerNewTransformer.transform(streamSource, streamResult);
            return streamResult.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private static void print2Console(int i, String str, String[] strArr, String str2) {
        Config config = CONFIG;
        int i2 = 0;
        if (config.mSingleTagSwitch) {
            StringBuilder sb = new StringBuilder(" ");
            String str3 = LINE_SEP;
            sb.append(str3);
            if (config.mLogBorderSwitch) {
                sb.append(TOP_BORDER).append(str3);
                if (strArr != null) {
                    for (String str4 : strArr) {
                        sb.append(LEFT_BORDER).append(str4).append(LINE_SEP);
                    }
                    sb.append(MIDDLE_BORDER).append(LINE_SEP);
                }
                String[] strArrSplit = str2.split(LINE_SEP);
                int length = strArrSplit.length;
                while (i2 < length) {
                    sb.append(LEFT_BORDER).append(strArrSplit[i2]).append(LINE_SEP);
                    i2++;
                }
                sb.append(BOTTOM_BORDER);
            } else {
                if (strArr != null) {
                    int length2 = strArr.length;
                    while (i2 < length2) {
                        sb.append(strArr[i2]).append(LINE_SEP);
                        i2++;
                    }
                }
                sb.append(str2);
            }
            printMsgSingleTag(i, str, sb.toString());
            return;
        }
        printBorder(i, str, true);
        printHead(i, str, strArr);
        printMsg(i, str, str2);
        printBorder(i, str, false);
    }

    private static void printBorder(int i, String str, boolean z) {
        if (CONFIG.mLogBorderSwitch) {
            Log.println(i, str, z ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    private static void printHead(int i, String str, String[] strArr) {
        if (strArr != null) {
            for (String str2 : strArr) {
                if (CONFIG.mLogBorderSwitch) {
                    str2 = LEFT_BORDER + str2;
                }
                Log.println(i, str, str2);
            }
            if (CONFIG.mLogBorderSwitch) {
                Log.println(i, str, MIDDLE_BORDER);
            }
        }
    }

    private static void printMsg(int i, String str, String str2) {
        int length = str2.length();
        int i2 = length / 3000;
        if (i2 <= 0) {
            printSubMsg(i, str, str2);
            return;
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i4 + 3000;
            printSubMsg(i, str, str2.substring(i4, i5));
            i3++;
            i4 = i5;
        }
        if (i4 != length) {
            printSubMsg(i, str, str2.substring(i4, length));
        }
    }

    private static void printMsgSingleTag(int i, String str, String str2) {
        int length = str2.length();
        int i2 = length / 3000;
        if (i2 > 0) {
            int i3 = 0;
            if (!CONFIG.mLogBorderSwitch) {
                int i4 = 0;
                while (i3 < i2) {
                    int i5 = i4 + 3000;
                    Log.println(i, str, str2.substring(i4, i5));
                    i3++;
                    i4 = i5;
                }
                if (i4 != length) {
                    Log.println(i, str, str2.substring(i4, length));
                    return;
                }
                return;
            }
            int i6 = 3000;
            Log.println(i, str, str2.substring(0, 3000) + LINE_SEP + BOTTOM_BORDER);
            int i7 = 1;
            while (i7 < i2) {
                StringBuilder sb = new StringBuilder(" ");
                String str3 = LINE_SEP;
                int i8 = i6 + 3000;
                Log.println(i, str, sb.append(str3).append(TOP_BORDER).append(str3).append(LEFT_BORDER).append(str2.substring(i6, i8)).append(str3).append(BOTTOM_BORDER).toString());
                i7++;
                i6 = i8;
            }
            if (i6 != length) {
                StringBuilder sb2 = new StringBuilder(" ");
                String str4 = LINE_SEP;
                Log.println(i, str, sb2.append(str4).append(TOP_BORDER).append(str4).append(LEFT_BORDER).append(str2.substring(i6, length)).toString());
                return;
            }
            return;
        }
        Log.println(i, str, str2);
    }

    private static void printSubMsg(int i, String str, String str2) {
        if (!CONFIG.mLogBorderSwitch) {
            Log.println(i, str, str2);
            return;
        }
        for (String str3 : str2.split(LINE_SEP)) {
            Log.println(i, str, LEFT_BORDER + str3);
        }
    }

    private static void printSubMsg1(int i, String str, String str2) {
        if (CONFIG.mLogBorderSwitch) {
            for (String str3 : str2.split(LINE_SEP)) {
                Log.println(i, str, LEFT_BORDER + str3);
            }
        }
    }

    private static void print2File(int i, String str, String str2) {
        String str3 = FORMAT.format(new Date(System.currentTimeMillis()));
        String strSubstring = str3.substring(0, 5);
        String strSubstring2 = str3.substring(6);
        StringBuilder sb = new StringBuilder();
        Config config = CONFIG;
        String string = sb.append(config.mDir == null ? config.mDefaultDir : config.mDir).append(config.mFilePrefix).append("-").append(strSubstring).append(".txt").toString();
        if (!createOrExistsFile(string)) {
            Log.e("LogUtils", "create " + string + " failed!");
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(strSubstring2).append(T[i - 2]).append("/").append(str).append(str2).append(LINE_SEP);
        input2File(sb2.toString(), string);
    }

    private static boolean createOrExistsFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            boolean zCreateNewFile = file.createNewFile();
            if (zCreateNewFile) {
                printDeviceInfo(str);
            }
            return zCreateNewFile;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void printDeviceInfo(String str) {
        String str2 = "";
        int i = 0;
        try {
            PackageInfo packageInfo = CbtManager.getInstance().getContext().getPackageManager().getPackageInfo(CbtManager.getInstance().getContext().getPackageName(), 0);
            if (packageInfo != null) {
                str2 = packageInfo.versionName;
                i = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        input2File("************* Log Head ****************\nDate of Log        : " + str.substring(str.length() - 9, str.length() - 4) + "\nDevice Manufacturer: " + Build.MANUFACTURER + "\nDevice Model       : " + Build.MODEL + "\nAndroid Version    : " + Build.VERSION.RELEASE + "\nAndroid SDK        : " + Build.VERSION.SDK_INT + "\nApp VersionName    : " + str2 + "\nApp VersionCode    : " + i + "\n************* Log Head ****************\n\n", str);
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void input2File(final String str, final String str2) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        try {
            if (((Boolean) sExecutor.submit(new Callable<Boolean>() { // from class: cn.baos.watch.sdk.bluetooth.bt.utils.CbtLogs.1
                /* JADX WARN: Can't rename method to resolve collision */
                /* JADX WARN: Removed duplicated region for block: B:32:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
                @Override // java.util.concurrent.Callable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public java.lang.Boolean call() throws java.lang.Exception {
                    /*
                        r6 = this;
                        r0 = 0
                        java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch: java.lang.Throwable -> L22 java.io.IOException -> L27
                        java.io.FileWriter r2 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L22 java.io.IOException -> L27
                        java.lang.String r3 = r1     // Catch: java.lang.Throwable -> L22 java.io.IOException -> L27
                        r4 = 1
                        r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L22 java.io.IOException -> L27
                        r1.<init>(r2)     // Catch: java.lang.Throwable -> L22 java.io.IOException -> L27
                        java.lang.String r0 = r2     // Catch: java.io.IOException -> L20 java.lang.Throwable -> L3e
                        r1.write(r0)     // Catch: java.io.IOException -> L20 java.lang.Throwable -> L3e
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r4)     // Catch: java.io.IOException -> L20 java.lang.Throwable -> L3e
                        r1.close()     // Catch: java.io.IOException -> L1b
                        goto L1f
                    L1b:
                        r1 = move-exception
                        r1.printStackTrace()
                    L1f:
                        return r0
                    L20:
                        r0 = move-exception
                        goto L2b
                    L22:
                        r1 = move-exception
                        r5 = r1
                        r1 = r0
                        r0 = r5
                        goto L3f
                    L27:
                        r1 = move-exception
                        r5 = r1
                        r1 = r0
                        r0 = r5
                    L2b:
                        r0.printStackTrace()     // Catch: java.lang.Throwable -> L3e
                        r0 = 0
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch: java.lang.Throwable -> L3e
                        if (r1 == 0) goto L3d
                        r1.close()     // Catch: java.io.IOException -> L39
                        goto L3d
                    L39:
                        r1 = move-exception
                        r1.printStackTrace()
                    L3d:
                        return r0
                    L3e:
                        r0 = move-exception
                    L3f:
                        if (r1 == 0) goto L49
                        r1.close()     // Catch: java.io.IOException -> L45
                        goto L49
                    L45:
                        r1 = move-exception
                        r1.printStackTrace()
                    L49:
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: cn.baos.watch.sdk.bluetooth.bt.utils.CbtLogs.AnonymousClass1.call():java.lang.Boolean");
                }
            }).get()).booleanValue()) {
                return;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e2) {
            e2.printStackTrace();
        }
        Log.e("LogUtils", "log to " + str2 + " failed!");
    }

    public static class Config {
        private int mConsoleFilter;
        private String mDefaultDir;
        private String mDir;
        private int mFileFilter;
        private String mFilePrefix;
        private String mGlobalTag;
        private boolean mLog2ConsoleSwitch;
        private boolean mLog2FileSwitch;
        private boolean mLogBorderSwitch;
        private boolean mLogHeadSwitch;
        private boolean mLogSwitch;
        private boolean mSingleTagSwitch;
        private int mStackDeep;
        private int mStackOffset;
        private boolean mTagIsSpace;

        private Config() {
            this.mFilePrefix = "util";
            this.mLogSwitch = true;
            this.mLog2ConsoleSwitch = true;
            this.mGlobalTag = null;
            this.mTagIsSpace = true;
            this.mLogHeadSwitch = true;
            this.mLog2FileSwitch = false;
            this.mLogBorderSwitch = true;
            this.mSingleTagSwitch = true;
            this.mConsoleFilter = 2;
            this.mFileFilter = 2;
            this.mStackDeep = 1;
            this.mStackOffset = 0;
            if (this.mDefaultDir != null) {
                return;
            }
            if ("mounted".equals(Environment.getExternalStorageState()) && CbtManager.getInstance().getContext().getExternalCacheDir() != null) {
                this.mDefaultDir = CbtManager.getInstance().getContext().getExternalCacheDir() + CbtLogs.FILE_SEP + Methods.log + CbtLogs.FILE_SEP;
            } else {
                this.mDefaultDir = CbtManager.getInstance().getContext().getCacheDir() + CbtLogs.FILE_SEP + Methods.log + CbtLogs.FILE_SEP;
            }
        }

        public Config setLogSwitch(boolean z) {
            this.mLogSwitch = z;
            return this;
        }

        public Config setConsoleSwitch(boolean z) {
            this.mLog2ConsoleSwitch = z;
            return this;
        }

        public Config setGlobalTag(String str) {
            if (CbtLogs.isSpace(str)) {
                this.mGlobalTag = "";
                this.mTagIsSpace = true;
            } else {
                this.mGlobalTag = str;
                this.mTagIsSpace = false;
            }
            return this;
        }

        public Config setLogHeadSwitch(boolean z) {
            this.mLogHeadSwitch = z;
            return this;
        }

        public Config setLog2FileSwitch(boolean z) {
            this.mLog2FileSwitch = z;
            return this;
        }

        public Config setDir(String str) {
            if (CbtLogs.isSpace(str)) {
                this.mDir = null;
            } else {
                if (!str.endsWith(CbtLogs.FILE_SEP)) {
                    str = str + CbtLogs.FILE_SEP;
                }
                this.mDir = str;
            }
            return this;
        }

        public Config setDir(File file) {
            this.mDir = file == null ? null : file.getAbsolutePath() + CbtLogs.FILE_SEP;
            return this;
        }

        public Config setFilePrefix(String str) {
            if (CbtLogs.isSpace(str)) {
                this.mFilePrefix = "util";
            } else {
                this.mFilePrefix = str;
            }
            return this;
        }

        public Config setBorderSwitch(boolean z) {
            this.mLogBorderSwitch = z;
            return this;
        }

        public Config setSingleTagSwitch(boolean z) {
            this.mSingleTagSwitch = z;
            return this;
        }

        public Config setConsoleFilter(int i) {
            this.mConsoleFilter = i;
            return this;
        }

        public Config setFileFilter(int i) {
            this.mFileFilter = i;
            return this;
        }

        public Config setStackDeep(int i) {
            this.mStackDeep = i;
            return this;
        }

        public Config setStackOffset(int i) {
            this.mStackOffset = i;
            return this;
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder("switch: ").append(this.mLogSwitch).append(CbtLogs.LINE_SEP).append("console: ").append(this.mLog2ConsoleSwitch).append(CbtLogs.LINE_SEP).append("tag: ").append(this.mTagIsSpace ? CbtLogs.NULL : this.mGlobalTag).append(CbtLogs.LINE_SEP).append("head: ").append(this.mLogHeadSwitch).append(CbtLogs.LINE_SEP).append("file: ").append(this.mLog2FileSwitch).append(CbtLogs.LINE_SEP).append("dir: ");
            String str = this.mDir;
            if (str == null) {
                str = this.mDefaultDir;
            }
            return sbAppend.append(str).append(CbtLogs.LINE_SEP).append("filePrefix: ").append(this.mFilePrefix).append(CbtLogs.LINE_SEP).append("border: ").append(this.mLogBorderSwitch).append(CbtLogs.LINE_SEP).append("singleTag: ").append(this.mSingleTagSwitch).append(CbtLogs.LINE_SEP).append("consoleFilter: ").append(CbtLogs.T[this.mConsoleFilter - 2]).append(CbtLogs.LINE_SEP).append("fileFilter: ").append(CbtLogs.T[this.mFileFilter - 2]).append(CbtLogs.LINE_SEP).append("stackDeep: ").append(this.mStackDeep).append(CbtLogs.LINE_SEP).append("mStackOffset: ").append(this.mStackOffset).toString();
        }
    }

    private static class TagHead {
        String[] consoleHead;
        String fileHead;
        String tag;

        TagHead(String str, String[] strArr, String str2) {
            this.tag = str;
            this.consoleHead = strArr;
            this.fileHead = str2;
        }
    }
}
