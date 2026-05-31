package javax.mail;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: Session.java */
/* JADX INFO: loaded from: classes3.dex */
interface StreamLoader {
    void load(InputStream inputStream) throws IOException;
}
