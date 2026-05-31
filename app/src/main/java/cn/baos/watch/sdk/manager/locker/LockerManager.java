package cn.baos.watch.sdk.manager.locker;

/* JADX INFO: loaded from: classes.dex */
public class LockerManager {
    private static LockerManager instace;
    Object nfcLocker = new Object();
    Object alexaLocker = new Object();
    Object payLocker = new Object();
    Object dataBaseLocker = new Object();
    Object getWatchInfoLocker = new Object();
    Object getContactInfoLocker = new Object();
    Object getFallLocker = new Object();
    Object getWorldLocaker = new Object();

    public static LockerManager getInstance() {
        synchronized (LockerManager.class) {
            if (instace == null) {
                instace = new LockerManager();
            }
        }
        return instace;
    }

    public Object getNfcLocker() {
        return this.nfcLocker;
    }

    public Object getAlexaLocker() {
        return this.alexaLocker;
    }

    public Object getPayLocker() {
        return this.payLocker;
    }

    public Object getDataBaseLocker() {
        return this.dataBaseLocker;
    }

    public Object getGetWatchInfoLocker() {
        return this.getWatchInfoLocker;
    }

    public Object getGetContactInfoLocker() {
        return this.getContactInfoLocker;
    }

    public Object getGetWorldLocaker() {
        return this.getWorldLocaker;
    }

    public Object getGetFallLocker() {
        return this.getFallLocker;
    }
}
