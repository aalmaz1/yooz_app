package cn.baos.watch.sdk.old.syncDate;

import cn.baos.message.Serializable;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.old.LV;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.baos.watch.sdk.utils.W100Utils;

/* JADX INFO: loaded from: classes.dex */
public class SyncDataManager {
    public static final int SYNC_END = 0;
    public static final int SYNC_ING = 1;
    private static SyncDataManager instance;
    private int mSyncStatus = 0;

    public static SyncDataManager getInstance() {
        if (instance == null) {
            synchronized (SyncDataManager.class) {
                if (instance == null) {
                    instance = new SyncDataManager();
                }
            }
        }
        return instance;
    }

    public void sendSyncDataFromServerToWatch(byte[] bArr) {
        Serializable serializableUnpackMessage = MessageManager.unpackMessage(bArr);
        if (serializableUnpackMessage != null && serializableUnpackMessage.catagory != 100 && serializableUnpackMessage.catagory != 101) {
            LogUtil.d("数据同步->接收服务器对象并发送给手表:" + W100Utils.toString(serializableUnpackMessage));
            MessageManager.getInstance().sendMessage(serializableUnpackMessage);
        } else if (serializableUnpackMessage != null && (serializableUnpackMessage.catagory == 100 || serializableUnpackMessage.catagory == 101)) {
            LogUtil.d("数据同步->接收服务器对象:" + W100Utils.toString(serializableUnpackMessage));
            LogUtil.d("数据同步->接收服务器对象:服务器处理异常:" + serializableUnpackMessage.catagory);
        } else {
            LogUtil.d("数据同步->接收服务器对象为空");
            LV.logText("数据同步->接收服务器对象为空,unpack异常");
        }
    }
}
