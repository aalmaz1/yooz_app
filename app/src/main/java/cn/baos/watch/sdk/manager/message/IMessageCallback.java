package cn.baos.watch.sdk.manager.message;

import cn.baos.watch.w100.messages.Action_sync;
import cn.baos.watch.w100.messages.Device_resource_info;
import cn.baos.watch.w100.messages.Request_get_data;
import cn.baos.watch.w100.messages.User_info_config;

/* JADX INFO: loaded from: classes.dex */
public interface IMessageCallback {
    void onActionSync(Action_sync action_sync);

    void onBindRequestByPhone();

    void onCollectWatchLoggerRequest(byte[] bArr);

    void onCollectWatchLoggerRequestData(String str);

    void onDeviceResourceInfo(Device_resource_info device_resource_info);

    void onPhoneStatus(int i);

    void onRequestGetData(Request_get_data request_get_data);

    void onRequestGpsData();

    void onRequestMeteorologicalData();

    void onRequestWeather();

    void onSyncMessageRequest(byte[] bArr);

    void onUserInfoConfig(User_info_config user_info_config);

    void requestGetTime();
}
