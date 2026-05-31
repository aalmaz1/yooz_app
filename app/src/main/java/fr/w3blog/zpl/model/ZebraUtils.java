package fr.w3blog.zpl.model;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class ZebraUtils {
    public static void printZpl(String str, String str2, int i) throws Throwable {
        Socket socket;
        Socket socket2 = null;
        try {
            try {
                socket = new Socket(str2, i);
            } catch (Throwable th) {
                th = th;
            }
            try {
                new DataOutputStream(socket.getOutputStream()).writeBytes(str);
                socket.close();
                socket.close();
            } catch (Throwable th2) {
                th = th2;
                socket2 = socket;
                if (socket2 != null) {
                    socket2.close();
                }
                throw th;
            }
        } catch (IOException e) {
            throw new ZebraPrintException("Cannot print label on this printer : " + str2 + ":" + i, e);
        }
    }

    public static void printZpl(ZebraLabel zebraLabel, String str, int i) throws Throwable {
        printZpl(zebraLabel.getZplCode(), str, i);
    }

    public static void printZpl(List<ZebraLabel> list, String str, int i) throws Throwable {
        StringBuilder sb = new StringBuilder();
        Iterator<ZebraLabel> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next().getZplCode());
        }
        printZpl(sb.toString(), str, i);
    }
}
