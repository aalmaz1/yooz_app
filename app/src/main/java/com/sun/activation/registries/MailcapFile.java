package com.sun.activation.registries;

import androidx.webkit.ProxyConfig;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class MailcapFile {
    private static boolean addReverse = false;
    private Map type_hash = new HashMap();
    private Map fallback_hash = new HashMap();
    private Map native_commands = new HashMap();

    static {
        try {
            addReverse = Boolean.getBoolean("javax.activation.addreverse");
        } catch (Throwable unused) {
        }
    }

    public MailcapFile(String str) throws Throwable {
        FileReader fileReader;
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: file " + str);
        }
        FileReader fileReader2 = null;
        try {
            fileReader = new FileReader(str);
        } catch (Throwable th) {
            th = th;
        }
        try {
            parse(new BufferedReader(fileReader));
            try {
                fileReader.close();
            } catch (IOException unused) {
            }
        } catch (Throwable th2) {
            th = th2;
            fileReader2 = fileReader;
            if (fileReader2 != null) {
                try {
                    fileReader2.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
    }

    public MailcapFile(InputStream inputStream) throws IOException {
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: InputStream");
        }
        parse(new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1")));
    }

    public MailcapFile() {
        if (LogSupport.isLoggable()) {
            LogSupport.log("new MailcapFile: default");
        }
    }

    public Map getMailcapList(String str) {
        Map map = (Map) this.type_hash.get(str);
        int iIndexOf = str.indexOf(47) + 1;
        if (str.substring(iIndexOf).equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
            return map;
        }
        Map map2 = (Map) this.type_hash.get(str.substring(0, iIndexOf) + ProxyConfig.MATCH_ALL_SCHEMES);
        return map2 != null ? map != null ? mergeResults(map, map2) : map2 : map;
    }

    public Map getMailcapFallbackList(String str) {
        Map map = (Map) this.fallback_hash.get(str);
        int iIndexOf = str.indexOf(47) + 1;
        if (str.substring(iIndexOf).equals(ProxyConfig.MATCH_ALL_SCHEMES)) {
            return map;
        }
        Map map2 = (Map) this.fallback_hash.get(str.substring(0, iIndexOf) + ProxyConfig.MATCH_ALL_SCHEMES);
        return map2 != null ? map != null ? mergeResults(map, map2) : map2 : map;
    }

    public String[] getMimeTypes() {
        HashSet hashSet = new HashSet(this.type_hash.keySet());
        hashSet.addAll(this.fallback_hash.keySet());
        hashSet.addAll(this.native_commands.keySet());
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    public String[] getNativeCommands(String str) {
        List list = (List) this.native_commands.get(str.toLowerCase(Locale.ENGLISH));
        if (list != null) {
            return (String[]) list.toArray(new String[list.size()]);
        }
        return null;
    }

    private Map mergeResults(Map map, Map map2) {
        HashMap map3 = new HashMap(map);
        for (String str : map2.keySet()) {
            List list = (List) map3.get(str);
            if (list == null) {
                map3.put(str, map2.get(str));
            } else {
                List list2 = (List) map2.get(str);
                ArrayList arrayList = new ArrayList(list);
                arrayList.addAll(list2);
                map3.put(str, arrayList);
            }
        }
        return map3;
    }

    public void appendToMailcap(String str) {
        if (LogSupport.isLoggable()) {
            LogSupport.log("appendToMailcap: " + str);
        }
        try {
            parse(new StringReader(str));
        } catch (IOException unused) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0065, code lost:
    
        parseLine(r1 + r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parse(java.io.Reader r7) throws java.io.IOException {
        /*
            r6 = this;
            java.io.BufferedReader r0 = new java.io.BufferedReader
            r0.<init>(r7)
            r7 = 0
        L6:
            r1 = r7
        L7:
            java.lang.String r2 = r0.readLine()
            if (r2 == 0) goto L6d
            java.lang.String r2 = r2.trim()
            r3 = 0
            char r4 = r2.charAt(r3)     // Catch: java.lang.Throwable -> L7
            r5 = 35
            if (r4 != r5) goto L1b
            goto L7
        L1b:
            int r4 = r2.length()     // Catch: java.lang.Throwable -> L7
            int r4 = r4 + (-1)
            char r4 = r2.charAt(r4)     // Catch: java.lang.Throwable -> L7
            r5 = 92
            if (r4 != r5) goto L52
            if (r1 == 0) goto L47
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7
            r4.<init>()     // Catch: java.lang.Throwable -> L7
            java.lang.StringBuilder r4 = r4.append(r1)     // Catch: java.lang.Throwable -> L7
            int r5 = r2.length()     // Catch: java.lang.Throwable -> L7
            int r5 = r5 + (-1)
            java.lang.String r2 = r2.substring(r3, r5)     // Catch: java.lang.Throwable -> L7
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch: java.lang.Throwable -> L7
            java.lang.String r1 = r2.toString()     // Catch: java.lang.Throwable -> L7
            goto L7
        L47:
            int r4 = r2.length()     // Catch: java.lang.Throwable -> L7
            int r4 = r4 + (-1)
            java.lang.String r1 = r2.substring(r3, r4)     // Catch: java.lang.Throwable -> L7
            goto L7
        L52:
            if (r1 == 0) goto L69
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7
            r3.<init>()     // Catch: java.lang.Throwable -> L7
            java.lang.StringBuilder r3 = r3.append(r1)     // Catch: java.lang.Throwable -> L7
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch: java.lang.Throwable -> L7
            java.lang.String r1 = r2.toString()     // Catch: java.lang.Throwable -> L7
            r6.parseLine(r1)     // Catch: com.sun.activation.registries.MailcapParseException -> L6 java.lang.Throwable -> L7
            goto L6
        L69:
            r6.parseLine(r2)     // Catch: java.lang.Throwable -> L7
            goto L7
        L6d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.activation.registries.MailcapFile.parse(java.io.Reader):void");
    }

    protected void parseLine(String str) throws MailcapParseException, IOException {
        int iNextToken;
        String lowerCase;
        int iNextToken2;
        MailcapTokenizer mailcapTokenizer = new MailcapTokenizer(str);
        mailcapTokenizer.setIsAutoquoting(false);
        if (LogSupport.isLoggable()) {
            LogSupport.log("parse: " + str);
        }
        int iNextToken3 = mailcapTokenizer.nextToken();
        if (iNextToken3 != 2) {
            reportParseError(2, iNextToken3, mailcapTokenizer.getCurrentTokenValue());
        }
        String lowerCase2 = mailcapTokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
        int iNextToken4 = mailcapTokenizer.nextToken();
        if (iNextToken4 != 47 && iNextToken4 != 59) {
            reportParseError(47, 59, iNextToken4, mailcapTokenizer.getCurrentTokenValue());
        }
        if (iNextToken4 == 47) {
            int iNextToken5 = mailcapTokenizer.nextToken();
            if (iNextToken5 != 2) {
                reportParseError(2, iNextToken5, mailcapTokenizer.getCurrentTokenValue());
            }
            lowerCase = mailcapTokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            iNextToken = mailcapTokenizer.nextToken();
        } else {
            iNextToken = iNextToken4;
            lowerCase = ProxyConfig.MATCH_ALL_SCHEMES;
        }
        String str2 = lowerCase2 + "/" + lowerCase;
        if (LogSupport.isLoggable()) {
            LogSupport.log("  Type: " + str2);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (iNextToken != 59) {
            reportParseError(59, iNextToken, mailcapTokenizer.getCurrentTokenValue());
        }
        mailcapTokenizer.setIsAutoquoting(true);
        int iNextToken6 = mailcapTokenizer.nextToken();
        mailcapTokenizer.setIsAutoquoting(false);
        if (iNextToken6 != 2 && iNextToken6 != 59) {
            reportParseError(2, 59, iNextToken6, mailcapTokenizer.getCurrentTokenValue());
        }
        if (iNextToken6 == 2) {
            List list = (List) this.native_commands.get(str2);
            if (list == null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                this.native_commands.put(str2, arrayList);
            } else {
                list.add(str);
            }
        }
        if (iNextToken6 != 59) {
            iNextToken6 = mailcapTokenizer.nextToken();
        }
        if (iNextToken6 != 59) {
            if (iNextToken6 != 5) {
                reportParseError(5, 59, iNextToken6, mailcapTokenizer.getCurrentTokenValue());
                return;
            }
            return;
        }
        boolean z = false;
        do {
            int iNextToken7 = mailcapTokenizer.nextToken();
            if (iNextToken7 != 2) {
                reportParseError(2, iNextToken7, mailcapTokenizer.getCurrentTokenValue());
            }
            String lowerCase3 = mailcapTokenizer.getCurrentTokenValue().toLowerCase(Locale.ENGLISH);
            iNextToken2 = mailcapTokenizer.nextToken();
            if (iNextToken2 != 61 && iNextToken2 != 59 && iNextToken2 != 5) {
                reportParseError(61, 59, 5, iNextToken2, mailcapTokenizer.getCurrentTokenValue());
            }
            if (iNextToken2 == 61) {
                mailcapTokenizer.setIsAutoquoting(true);
                int iNextToken8 = mailcapTokenizer.nextToken();
                mailcapTokenizer.setIsAutoquoting(false);
                if (iNextToken8 != 2) {
                    reportParseError(2, iNextToken8, mailcapTokenizer.getCurrentTokenValue());
                }
                String currentTokenValue = mailcapTokenizer.getCurrentTokenValue();
                if (lowerCase3.startsWith("x-java-")) {
                    String strSubstring = lowerCase3.substring(7);
                    if (strSubstring.equals("fallback-entry") && currentTokenValue.equalsIgnoreCase("true")) {
                        z = true;
                    } else {
                        if (LogSupport.isLoggable()) {
                            LogSupport.log("    Command: " + strSubstring + ", Class: " + currentTokenValue);
                        }
                        List arrayList2 = (List) linkedHashMap.get(strSubstring);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            linkedHashMap.put(strSubstring, arrayList2);
                        }
                        if (addReverse) {
                            arrayList2.add(0, currentTokenValue);
                        } else {
                            arrayList2.add(currentTokenValue);
                        }
                    }
                }
                iNextToken2 = mailcapTokenizer.nextToken();
            }
        } while (iNextToken2 == 59);
        Map map = z ? this.fallback_hash : this.type_hash;
        Map map2 = (Map) map.get(str2);
        if (map2 == null) {
            map.put(str2, linkedHashMap);
            return;
        }
        if (LogSupport.isLoggable()) {
            LogSupport.log("Merging commands for type " + str2);
        }
        for (String str3 : map2.keySet()) {
            List list2 = (List) map2.get(str3);
            List<String> list3 = (List) linkedHashMap.get(str3);
            if (list3 != null) {
                for (String str4 : list3) {
                    if (!list2.contains(str4)) {
                        if (addReverse) {
                            list2.add(0, str4);
                        } else {
                            list2.add(str4);
                        }
                    }
                }
            }
        }
        for (String str5 : linkedHashMap.keySet()) {
            if (!map2.containsKey(str5)) {
                map2.put(str5, (List) linkedHashMap.get(str5));
            }
        }
    }

    protected static void reportParseError(int i, int i2, String str) throws MailcapParseException {
        throw new MailcapParseException("Encountered a " + MailcapTokenizer.nameForToken(i2) + " token (" + str + ") while expecting a " + MailcapTokenizer.nameForToken(i) + " token.");
    }

    protected static void reportParseError(int i, int i2, int i3, String str) throws MailcapParseException {
        throw new MailcapParseException("Encountered a " + MailcapTokenizer.nameForToken(i3) + " token (" + str + ") while expecting a " + MailcapTokenizer.nameForToken(i) + " or a " + MailcapTokenizer.nameForToken(i2) + " token.");
    }

    protected static void reportParseError(int i, int i2, int i3, int i4, String str) throws MailcapParseException {
        if (LogSupport.isLoggable()) {
            LogSupport.log("PARSE ERROR: Encountered a " + MailcapTokenizer.nameForToken(i4) + " token (" + str + ") while expecting a " + MailcapTokenizer.nameForToken(i) + ", a " + MailcapTokenizer.nameForToken(i2) + ", or a " + MailcapTokenizer.nameForToken(i3) + " token.");
        }
        throw new MailcapParseException("Encountered a " + MailcapTokenizer.nameForToken(i4) + " token (" + str + ") while expecting a " + MailcapTokenizer.nameForToken(i) + ", a " + MailcapTokenizer.nameForToken(i2) + ", or a " + MailcapTokenizer.nameForToken(i3) + " token.");
    }
}
