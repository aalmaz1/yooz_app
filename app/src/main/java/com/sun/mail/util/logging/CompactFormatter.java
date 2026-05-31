package com.sun.mail.util.logging;

import java.util.Collections;
import java.util.Date;
import java.util.Formattable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class CompactFormatter extends Formatter {
    private final String fmt;

    static {
        loadDeclaredClasses();
    }

    private static Class<?>[] loadDeclaredClasses() {
        return new Class[]{Alternate.class};
    }

    public CompactFormatter() {
        this.fmt = initFormat(getClass().getName());
    }

    public CompactFormatter(String str) {
        this.fmt = str == null ? initFormat(getClass().getName()) : str;
    }

    @Override // java.util.logging.Formatter
    public String format(LogRecord logRecord) {
        ResourceBundle resourceBundle = logRecord.getResourceBundle();
        Locale locale = resourceBundle == null ? null : resourceBundle.getLocale();
        String message = formatMessage(logRecord);
        String thrown = formatThrown(logRecord);
        String error = formatError(logRecord);
        Object[] objArr = {formatZonedDateTime(logRecord), formatSource(logRecord), formatLoggerName(logRecord), formatLevel(logRecord), message, thrown, new Alternate(message, thrown), new Alternate(thrown, message), Long.valueOf(logRecord.getSequenceNumber()), formatThreadID(logRecord), error, new Alternate(message, error), new Alternate(error, message), formatBackTrace(logRecord), logRecord.getResourceBundleName(), logRecord.getMessage()};
        if (locale == null) {
            return String.format(this.fmt, objArr);
        }
        return String.format(locale, this.fmt, objArr);
    }

    @Override // java.util.logging.Formatter
    public String formatMessage(LogRecord logRecord) {
        return replaceClassName(replaceClassName(super.formatMessage(logRecord), logRecord.getThrown()), logRecord.getParameters());
    }

    public String formatMessage(Throwable th) {
        String strReplaceClassName;
        if (th == null) {
            return "";
        }
        Throwable thApply = apply(th);
        String localizedMessage = thApply.getLocalizedMessage();
        String string = thApply.toString();
        String strSimpleClassName = simpleClassName(thApply.getClass());
        if (!isNullOrSpaces(localizedMessage)) {
            if (string.contains(localizedMessage)) {
                if (string.startsWith(thApply.getClass().getName()) || string.startsWith(strSimpleClassName)) {
                    strReplaceClassName = replaceClassName(localizedMessage, th);
                } else {
                    strReplaceClassName = replaceClassName(simpleClassName(string), th);
                }
            } else {
                strReplaceClassName = replaceClassName(simpleClassName(string) + ": " + localizedMessage, th);
            }
        } else {
            strReplaceClassName = replaceClassName(simpleClassName(string), th);
        }
        return !strReplaceClassName.contains(strSimpleClassName) ? strSimpleClassName + ": " + strReplaceClassName : strReplaceClassName;
    }

    public String formatLevel(LogRecord logRecord) {
        return logRecord.getLevel().getLocalizedName();
    }

    public String formatSource(LogRecord logRecord) {
        String sourceClassName = logRecord.getSourceClassName();
        if (sourceClassName != null) {
            if (logRecord.getSourceMethodName() != null) {
                return simpleClassName(sourceClassName) + StringUtils.SPACE + logRecord.getSourceMethodName();
            }
            return simpleClassName(sourceClassName);
        }
        return simpleClassName(logRecord.getLoggerName());
    }

    public String formatLoggerName(LogRecord logRecord) {
        return simpleClassName(logRecord.getLoggerName());
    }

    public Number formatThreadID(LogRecord logRecord) {
        return Long.valueOf(((long) logRecord.getThreadID()) & 4294967295L);
    }

    public String formatThrown(LogRecord logRecord) {
        Throwable thrown = logRecord.getThrown();
        if (thrown == null) {
            return "";
        }
        String backTrace = formatBackTrace(logRecord);
        return formatMessage(thrown) + (isNullOrSpaces(backTrace) ? "" : StringUtils.SPACE + backTrace);
    }

    public String formatError(LogRecord logRecord) {
        return formatMessage(logRecord.getThrown());
    }

    public String formatBackTrace(LogRecord logRecord) {
        Throwable thrown = logRecord.getThrown();
        if (thrown == null) {
            return "";
        }
        StackTraceElement[] stackTrace = apply(thrown).getStackTrace();
        String strFindAndFormat = findAndFormat(stackTrace);
        if (!isNullOrSpaces(strFindAndFormat)) {
            return strFindAndFormat;
        }
        int i = 0;
        while (thrown != null) {
            StackTraceElement[] stackTrace2 = thrown.getStackTrace();
            String strFindAndFormat2 = findAndFormat(stackTrace2);
            if (isNullOrSpaces(strFindAndFormat2)) {
                if (stackTrace.length == 0) {
                    stackTrace = stackTrace2;
                }
                i++;
                if (i != 65536) {
                    thrown = thrown.getCause();
                    strFindAndFormat = strFindAndFormat2;
                }
            }
            strFindAndFormat = strFindAndFormat2;
            break;
        }
        return (!isNullOrSpaces(strFindAndFormat) || stackTrace.length == 0) ? strFindAndFormat : formatStackTraceElement(stackTrace[0]);
    }

    private String findAndFormat(StackTraceElement[] stackTraceElementArr) {
        String stackTraceElement;
        int length = stackTraceElementArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                stackTraceElement = "";
                break;
            }
            StackTraceElement stackTraceElement2 = stackTraceElementArr[i];
            if (!ignore(stackTraceElement2)) {
                stackTraceElement = formatStackTraceElement(stackTraceElement2);
                break;
            }
            i++;
        }
        if (!isNullOrSpaces(stackTraceElement)) {
            return stackTraceElement;
        }
        for (StackTraceElement stackTraceElement3 : stackTraceElementArr) {
            if (!defaultIgnore(stackTraceElement3)) {
                return formatStackTraceElement(stackTraceElement3);
            }
        }
        return stackTraceElement;
    }

    private String formatStackTraceElement(StackTraceElement stackTraceElement) {
        String string;
        String strSimpleClassName = simpleClassName(stackTraceElement.getClassName());
        if (strSimpleClassName != null) {
            string = stackTraceElement.toString().replace(stackTraceElement.getClassName(), strSimpleClassName);
        } else {
            string = stackTraceElement.toString();
        }
        String strSimpleFileName = simpleFileName(stackTraceElement.getFileName());
        return (strSimpleFileName == null || !string.startsWith(strSimpleFileName)) ? string : string.replace(stackTraceElement.getFileName(), "");
    }

    protected Throwable apply(Throwable th) {
        return SeverityComparator.getInstance().apply(th);
    }

    protected boolean ignore(StackTraceElement stackTraceElement) {
        return isUnknown(stackTraceElement) || defaultIgnore(stackTraceElement);
    }

    protected String toAlternate(String str) {
        if (str != null) {
            return str.replaceAll("[\\x00-\\x1F\\x7F]+", "");
        }
        return null;
    }

    private Comparable<?> formatZonedDateTime(LogRecord logRecord) {
        Comparable<?> zonedDateTime = LogManagerProperties.getZonedDateTime(logRecord);
        return zonedDateTime == null ? new Date(logRecord.getMillis()) : zonedDateTime;
    }

    private boolean defaultIgnore(StackTraceElement stackTraceElement) {
        return isSynthetic(stackTraceElement) || isStaticUtility(stackTraceElement) || isReflection(stackTraceElement);
    }

    private boolean isStaticUtility(StackTraceElement stackTraceElement) {
        try {
            return LogManagerProperties.isStaticUtilityClass(stackTraceElement.getClassName());
        } catch (RuntimeException | Exception | LinkageError unused) {
            String className = stackTraceElement.getClassName();
            return (className.endsWith("s") && !className.endsWith("es")) || className.contains("Util") || className.endsWith("Throwables");
        }
    }

    private boolean isSynthetic(StackTraceElement stackTraceElement) {
        return stackTraceElement.getMethodName().indexOf(36) > -1;
    }

    private boolean isUnknown(StackTraceElement stackTraceElement) {
        return stackTraceElement.getLineNumber() < 0;
    }

    private boolean isReflection(StackTraceElement stackTraceElement) {
        try {
            return LogManagerProperties.isReflectionClass(stackTraceElement.getClassName());
        } catch (RuntimeException | Exception | LinkageError unused) {
            return stackTraceElement.getClassName().startsWith("java.lang.reflect.") || stackTraceElement.getClassName().startsWith("sun.reflect.");
        }
    }

    private String initFormat(String str) {
        String strFromLogManager = LogManagerProperties.fromLogManager(str.concat(".format"));
        return isNullOrSpaces(strFromLogManager) ? "%7$#.160s%n" : strFromLogManager;
    }

    private static String replaceClassName(String str, Throwable th) {
        if (!isNullOrSpaces(str)) {
            int i = 0;
            while (th != null) {
                Class<?> cls = th.getClass();
                str = str.replace(cls.getName(), simpleClassName(cls));
                i++;
                if (i == 65536) {
                    break;
                }
                th = th.getCause();
            }
        }
        return str;
    }

    private static String replaceClassName(String str, Object[] objArr) {
        if (!isNullOrSpaces(str) && objArr != null) {
            for (Object obj : objArr) {
                if (obj != null) {
                    Class<?> cls = obj.getClass();
                    str = str.replace(cls.getName(), simpleClassName(cls));
                }
            }
        }
        return str;
    }

    private static String simpleClassName(Class<?> cls) {
        try {
            return cls.getSimpleName();
        } catch (InternalError unused) {
            return simpleClassName(cls.getName());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0036, code lost:
    
        if (r2 <= (-1)) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0038, code lost:
    
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x003a, code lost:
    
        if (r2 >= r1) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003c, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003e, code lost:
    
        if (r4 >= r1) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0040, code lost:
    
        if (r4 <= r2) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0042, code lost:
    
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0047, code lost:
    
        return r7.substring(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:?, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:?, code lost:
    
        return r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:?, code lost:
    
        return r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String simpleClassName(java.lang.String r7) {
        /*
            if (r7 == 0) goto L47
            r0 = -1
            r1 = 0
            r2 = r0
            r3 = r2
            r4 = r3
        L7:
            int r5 = r7.length()
            if (r1 >= r5) goto L36
            int r5 = r7.codePointAt(r1)
            boolean r6 = java.lang.Character.isJavaIdentifierPart(r5)
            if (r6 != 0) goto L2b
            r6 = 46
            if (r5 != r6) goto L25
            int r3 = r2 + 1
            if (r3 == r1) goto L24
            if (r3 == r4) goto L24
            r3 = r2
            r2 = r1
            goto L30
        L24:
            return r7
        L25:
            int r5 = r2 + 1
            if (r5 != r1) goto L36
            r2 = r3
            goto L36
        L2b:
            r6 = 36
            if (r5 != r6) goto L30
            r4 = r1
        L30:
            int r5 = java.lang.Character.charCount(r5)
            int r1 = r1 + r5
            goto L7
        L36:
            if (r2 <= r0) goto L47
            int r2 = r2 + 1
            if (r2 >= r1) goto L47
            int r4 = r4 + 1
            if (r4 >= r1) goto L47
            if (r4 <= r2) goto L43
            r2 = r4
        L43:
            java.lang.String r7 = r7.substring(r2)
        L47:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.logging.CompactFormatter.simpleClassName(java.lang.String):java.lang.String");
    }

    private static String simpleFileName(String str) {
        int iLastIndexOf;
        return (str == null || (iLastIndexOf = str.lastIndexOf(46)) <= -1) ? str : str.substring(0, iLastIndexOf);
    }

    private static boolean isNullOrSpaces(String str) {
        return str == null || str.trim().length() == 0;
    }

    private class Alternate implements Formattable {
        private final String left;
        private final String right;

        Alternate(String str, String str2) {
            this.left = String.valueOf(str);
            this.right = String.valueOf(str2);
        }

        @Override // java.util.Formattable
        public void formatTo(java.util.Formatter formatter, int i, int i2, int i3) {
            String strPad = this.left;
            String alternate = this.right;
            if ((i & 2) == 2) {
                strPad = strPad.toUpperCase(formatter.locale());
                alternate = alternate.toUpperCase(formatter.locale());
            }
            if ((i & 4) == 4) {
                strPad = CompactFormatter.this.toAlternate(strPad);
                alternate = CompactFormatter.this.toAlternate(alternate);
            }
            if (i3 <= 0) {
                i3 = Integer.MAX_VALUE;
            }
            int iMin = Math.min(strPad.length(), i3);
            if (iMin > (i3 >> 1)) {
                iMin = Math.max(iMin - alternate.length(), iMin >> 1);
            }
            if (iMin > 0) {
                if (iMin > strPad.length() && Character.isHighSurrogate(strPad.charAt(iMin - 1))) {
                    iMin--;
                }
                strPad = strPad.substring(0, iMin);
            }
            String strSubstring = alternate.substring(0, Math.min(i3 - iMin, alternate.length()));
            if (i2 > 0) {
                int i4 = i2 >> 1;
                if (strPad.length() < i4) {
                    strPad = pad(i, strPad, i4);
                }
                if (strSubstring.length() < i4) {
                    strSubstring = pad(i, strSubstring, i4);
                }
            }
            Object[] array = Collections.emptySet().toArray();
            formatter.format(strPad, array);
            if (strPad.length() != 0 && strSubstring.length() != 0) {
                formatter.format("|", array);
            }
            formatter.format(strSubstring, array);
        }

        private String pad(int i, String str, int i2) {
            int length = i2 - str.length();
            StringBuilder sb = new StringBuilder(i2);
            int i3 = 0;
            if ((i & 1) == 1) {
                while (i3 < length) {
                    sb.append(' ');
                    i3++;
                }
                sb.append(str);
            } else {
                sb.append(str);
                while (i3 < length) {
                    sb.append(' ');
                    i3++;
                }
            }
            return sb.toString();
        }
    }
}
