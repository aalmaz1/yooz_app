package javax.mail;

/* JADX INFO: loaded from: classes3.dex */
public class Header {
    protected String name;
    protected String value;

    public Header(String str, String str2) {
        this.name = str;
        this.value = str2;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
