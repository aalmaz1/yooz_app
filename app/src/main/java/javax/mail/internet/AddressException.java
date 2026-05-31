package javax.mail.internet;

/* JADX INFO: loaded from: classes3.dex */
public class AddressException extends ParseException {
    private static final long serialVersionUID = 9134583443539323120L;
    protected int pos;
    protected String ref;

    public AddressException() {
        this.ref = null;
        this.pos = -1;
    }

    public AddressException(String str) {
        super(str);
        this.ref = null;
        this.pos = -1;
    }

    public AddressException(String str, String str2) {
        super(str);
        this.pos = -1;
        this.ref = str2;
    }

    public AddressException(String str, String str2, int i) {
        super(str);
        this.ref = str2;
        this.pos = i;
    }

    public String getRef() {
        return this.ref;
    }

    public int getPos() {
        return this.pos;
    }

    @Override // javax.mail.MessagingException, java.lang.Throwable
    public String toString() {
        String string = super.toString();
        if (this.ref == null) {
            return string;
        }
        String str = string + " in string ``" + this.ref + "''";
        return this.pos < 0 ? str : str + " at position " + this.pos;
    }
}
