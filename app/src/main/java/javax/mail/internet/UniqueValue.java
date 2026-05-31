package javax.mail.internet;

import java.util.concurrent.atomic.AtomicInteger;
import javax.mail.Session;

/* JADX INFO: loaded from: classes3.dex */
class UniqueValue {
    private static AtomicInteger id = new AtomicInteger();

    UniqueValue() {
    }

    public static String getUniqueBoundaryValue() {
        StringBuilder sb = new StringBuilder();
        sb.append("----=_Part_").append(id.getAndIncrement()).append("_").append(sb.hashCode()).append('.').append(System.currentTimeMillis());
        return sb.toString();
    }

    public static String getUniqueMessageIDValue(Session session) {
        InternetAddress localAddress = InternetAddress.getLocalAddress(session);
        String address = localAddress != null ? localAddress.getAddress() : "javamailuser@localhost";
        int iLastIndexOf = address.lastIndexOf(64);
        if (iLastIndexOf >= 0) {
            address = address.substring(iLastIndexOf);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(sb.hashCode()).append('.').append(id.getAndIncrement()).append('.').append(System.currentTimeMillis()).append(address);
        return sb.toString();
    }
}
