package javax.activation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/* JADX INFO: compiled from: DataHandler.java */
/* JADX INFO: loaded from: classes3.dex */
class ObjectDataContentHandler implements DataContentHandler {
    private DataContentHandler dch;
    private String mimeType;
    private Object obj;
    private ActivationDataFlavor[] transferFlavors = null;

    public ObjectDataContentHandler(DataContentHandler dataContentHandler, Object obj, String str) {
        this.obj = obj;
        this.mimeType = str;
        this.dch = dataContentHandler;
    }

    public DataContentHandler getDCH() {
        return this.dch;
    }

    @Override // javax.activation.DataContentHandler
    public synchronized ActivationDataFlavor[] getTransferDataFlavors() {
        if (this.transferFlavors == null) {
            DataContentHandler dataContentHandler = this.dch;
            if (dataContentHandler != null) {
                this.transferFlavors = dataContentHandler.getTransferDataFlavors();
            } else {
                this.transferFlavors = new ActivationDataFlavor[]{new ActivationDataFlavor(cls, str, str)};
                Class<?> cls = this.obj.getClass();
                String str = this.mimeType;
            }
        }
        return this.transferFlavors;
    }

    @Override // javax.activation.DataContentHandler
    public Object getTransferData(ActivationDataFlavor activationDataFlavor, DataSource dataSource) throws IOException {
        DataContentHandler dataContentHandler = this.dch;
        if (dataContentHandler != null) {
            return dataContentHandler.getTransferData(activationDataFlavor, dataSource);
        }
        if (activationDataFlavor.equals(getTransferDataFlavors()[0])) {
            return this.obj;
        }
        throw new IOException("Unsupported DataFlavor: " + activationDataFlavor);
    }

    @Override // javax.activation.DataContentHandler
    public Object getContent(DataSource dataSource) {
        return this.obj;
    }

    @Override // javax.activation.DataContentHandler
    public void writeTo(Object obj, String str, OutputStream outputStream) throws IOException {
        DataContentHandler dataContentHandler = this.dch;
        if (dataContentHandler != null) {
            dataContentHandler.writeTo(obj, str, outputStream);
            return;
        }
        if (obj instanceof byte[]) {
            outputStream.write((byte[]) obj);
        } else {
            if (obj instanceof String) {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write((String) obj);
                outputStreamWriter.flush();
                return;
            }
            throw new UnsupportedDataTypeException("no object DCH for MIME type " + this.mimeType);
        }
    }
}
