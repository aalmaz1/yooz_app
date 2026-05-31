package cn.baos.watch.sdk.entitiy;

import cn.baos.message.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class MessageAndTargetId {
    private Serializable serializable;
    private int targetId;
    private long timeStamp;

    public MessageAndTargetId(int i, long j, Serializable serializable) {
        this.targetId = i;
        this.timeStamp = j;
        this.serializable = serializable;
    }

    public int getTargetId() {
        return this.targetId;
    }

    public void setTargetId(int i) {
        this.targetId = i;
    }

    public Serializable getSerializable() {
        return this.serializable;
    }

    public void setSerializable(Serializable serializable) {
        this.serializable = serializable;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long j) {
        this.timeStamp = j;
    }

    public String toString() {
        return "MessageAndTargetId{targetId=" + this.targetId + ", timeStamp=" + this.timeStamp + ", serializable=" + this.serializable + '}';
    }
}
