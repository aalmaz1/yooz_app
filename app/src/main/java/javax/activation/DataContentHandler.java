package javax.activation;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes3.dex */
public interface DataContentHandler {
    Object getContent(DataSource dataSource) throws IOException;

    Object getTransferData(ActivationDataFlavor activationDataFlavor, DataSource dataSource) throws IOException;

    /* JADX INFO: renamed from: getTransferDataFlavors */
    ActivationDataFlavor[] mo640getTransferDataFlavors();

    void writeTo(Object obj, String str, OutputStream outputStream) throws IOException;
}
