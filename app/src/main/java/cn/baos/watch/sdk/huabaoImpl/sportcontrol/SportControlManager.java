package cn.baos.watch.sdk.huabaoImpl.sportcontrol;

import android.content.Context;
import cn.baos.watch.sdk.manager.gps.GpsManager;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.Action_sync;
import cn.baos.watch.w100.messages.MessageBase;
import cn.baos.watch.w100.messages.Request_get_data;
import cn.baos.watch.w100.messages.Response_msg;
import cn.baos.watch.w100.messages.Sensor_data_gps;
import com.google.gson.Gson;

/* JADX INFO: loaded from: classes.dex */
public class SportControlManager {
    public void responseActionSync(Action_sync action_sync) {
        Response_msg response_msg = new Response_msg();
        response_msg.act_catagory = action_sync.catagory;
        response_msg.act_sequence_id = action_sync.action_type;
        LogUtil.d("新协议消息回复,运动控制,相机状态回复:" + new Gson().toJson(response_msg));
        MessageManager.getInstance().sendMessage(response_msg);
    }

    public void handleGpsRequestFromWatch(Context context, Request_get_data request_get_data) {
        if (request_get_data.last_data_timestamp == 1) {
            Sensor_data_gps sensor_data_gps = new Sensor_data_gps();
            sensor_data_gps.satellite_count = GpsManager.getInstance().gpsCount;
            MessageManager.getInstance().sendMessage((MessageBase) sensor_data_gps);
            return;
        }
        int i = request_get_data.last_data_timestamp;
    }
}
