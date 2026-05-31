package cn.baos.watch.sdk.api;

import cn.baos.watch.w100.messages.Action_sync;
import cn.baos.watch.w100.messages.Device_resource_info;
import cn.baos.watch.w100.messages.Request_get_data;

/* JADX INFO: loaded from: classes.dex */
public interface DeviceCallBack {
    void onActionSync(Action_sync action_sync);

    void onCollectWatchLoggerRequest(byte[] bArr);

    void onDeviceResourceInfo(Device_resource_info device_resource_info);

    void onPhoneStatus(int i);

    void onRequestGetData(Request_get_data request_get_data);

    void onRequestTime();

    void onRequestWeather();
}
