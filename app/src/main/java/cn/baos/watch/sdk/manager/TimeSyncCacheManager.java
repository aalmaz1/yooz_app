package cn.baos.watch.sdk.manager;

import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.w100.messages.CommandTimeSync;

/* JADX INFO: loaded from: classes.dex */
public class TimeSyncCacheManager {
    private static TimeSyncCacheManager instance;
    CommandTimeSync commandTimeSync;
    boolean hasCheckNeedForceOta = false;
    private boolean hasCacheTimeSync = false;

    private TimeSyncCacheManager() {
    }

    public static TimeSyncCacheManager getInstance() {
        if (instance == null) {
            synchronized (TimeSyncCacheManager.class) {
                if (instance == null) {
                    instance = new TimeSyncCacheManager();
                }
            }
        }
        return instance;
    }

    public void timeSyncToWatch(long j, int i) {
        CommandTimeSync commandTimeSync = this.commandTimeSync;
        MessageManager.getInstance().timeSyncToWatch(commandTimeSync == null ? 100 : commandTimeSync.sync_id, j, i);
    }
}
