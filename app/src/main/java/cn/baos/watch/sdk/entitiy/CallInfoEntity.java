package cn.baos.watch.sdk.entitiy;

/* JADX INFO: loaded from: classes.dex */
public class CallInfoEntity {
    private String getmIncomingNumName;
    private String mIncomingNum;
    private int phoneState;

    public CallInfoEntity() {
    }

    public CallInfoEntity(String str, String str2) {
        this.mIncomingNum = str;
        this.getmIncomingNumName = str2;
    }

    public int getPhoneState() {
        return this.phoneState;
    }

    public void setPhoneState(int i) {
        this.phoneState = i;
    }

    public String getIncomingNum() {
        return this.mIncomingNum;
    }

    public void setIncomingNum(String str) {
        this.mIncomingNum = str;
    }

    public String getGetIncomingNumName() {
        return this.getmIncomingNumName;
    }

    public void setGetIncomingNumName(String str) {
        this.getmIncomingNumName = str;
    }

    public String toString() {
        return "CallInfoEntity{mIncomingNum='" + this.mIncomingNum + "', getmIncomingNumName='" + this.getmIncomingNumName + "', phoneState=" + this.phoneState + '}';
    }
}
