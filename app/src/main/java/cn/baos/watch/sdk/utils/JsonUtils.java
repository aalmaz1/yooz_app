package cn.baos.watch.sdk.utils;

import android.content.Context;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.w100.messages.Device_base_info;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inuker.bluetooth.library.utils.StringUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class JsonUtils {
    public static void readDeviceJson(Context context) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("device_config.json"), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                } else {
                    bufferedReader.close();
                    inputStreamReader.close();
                    SharePreferenceUtils.saveStringByKey(context, Constant.DEVICE_CONFIG_ALL, new Gson().toJson((ArrayList) new Gson().fromJson(sb.toString(), new TypeToken<ArrayList<DeviceBean>>() { // from class: cn.baos.watch.sdk.utils.JsonUtils.1
                    }.getType())));
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DeviceBean getCurrentDeviceConfig(Context context) {
        String strQueryStringByKey;
        String strQueryStringByKey2;
        try {
            strQueryStringByKey = SharePreferenceUtils.queryStringByKey(context, Constant.DEVICE_CONFIG_ALL);
            strQueryStringByKey2 = SharePreferenceUtils.queryStringByKey(context, Constant.DEVICE_CONFIG_WATCH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!StringUtils.isBlank(strQueryStringByKey) && !StringUtils.isBlank(strQueryStringByKey2)) {
            Device_base_info device_base_info = (Device_base_info) new Gson().fromJson(strQueryStringByKey2, Device_base_info.class);
            ArrayList<DeviceBean> arrayList = (ArrayList) new Gson().fromJson(strQueryStringByKey, new TypeToken<ArrayList<DeviceBean>>() { // from class: cn.baos.watch.sdk.utils.JsonUtils.2
            }.getType());
            if (arrayList != null && arrayList.size() > 0 && device_base_info != null) {
                for (DeviceBean deviceBean : arrayList) {
                    if (deviceBean != null && StringUtils.isNotBlank(device_base_info.device_model) && StringUtils.isNotBlank(deviceBean.name) && device_base_info.device_model.equals(deviceBean.name)) {
                        return deviceBean;
                    }
                }
            }
            return null;
        }
        return null;
    }
}
