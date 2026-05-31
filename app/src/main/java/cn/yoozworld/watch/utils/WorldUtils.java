package cn.yoozworld.watch.utils;

import android.content.Context;
import android.location.LocationManager;
import cn.baos.watch.sdk.entitiy.WorldEntity;
import cn.baos.watch.sdk.util.LogUtil;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.commons.lang3.time.TimeZones;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class WorldUtils {
    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.io.BufferedReader] */
    public static String getWorldTime(Context context) throws Throwable {
        InputStreamReader inputStreamReader;
        Throwable th;
        Exception e;
        BufferedReader bufferedReader;
        try {
            try {
                inputStreamReader = new InputStreamReader(context.getAssets().open("wroldTimeData.json"), "UTF-8");
            } catch (Exception e2) {
                inputStreamReader = null;
                e = e2;
                bufferedReader = null;
            } catch (Throwable th2) {
                inputStreamReader = null;
                th = th2;
                context = 0;
            }
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
            } catch (Exception e3) {
                e = e3;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                context = 0;
                try {
                    context.close();
                } catch (Exception unused) {
                }
                try {
                    inputStreamReader.close();
                    throw th;
                } catch (Exception unused2) {
                    throw th;
                }
            }
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                JSONArray jSONArray = new JSONArray(sb.toString());
                LogUtil.e(">>>>>>banks object is->" + jSONArray.toString());
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    WorldEntity worldEntity = new WorldEntity();
                    worldEntity.contryCn = jSONObject.getString("contryCn");
                    worldEntity.cityEn = jSONObject.getString("cityEn");
                    worldEntity.cityCn = jSONObject.getString("cityCn");
                    worldEntity.contryEn = jSONObject.getString("contryEn");
                    worldEntity.secondsFromGMT = Integer.valueOf(jSONObject.getInt("secondsFromGMT"));
                    worldEntity.gMT = jSONObject.getString(TimeZones.GMT_ID);
                    worldEntity.continentsCn = jSONObject.getString("continentsCn");
                    worldEntity.continentsEn = jSONObject.getString("continentsEn");
                    arrayList.add(worldEntity);
                }
                String json = new Gson().toJson(arrayList);
                try {
                    bufferedReader.close();
                } catch (Exception unused3) {
                }
                try {
                    inputStreamReader.close();
                    return json;
                } catch (Exception unused4) {
                    return json;
                }
            } catch (Exception e4) {
                e = e4;
                LogUtil.e(">>>>>>read json error->" + e.getMessage());
                e.printStackTrace();
                try {
                    bufferedReader.close();
                } catch (Exception unused5) {
                }
                try {
                    inputStreamReader.close();
                } catch (Exception unused6) {
                }
                return "";
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:0|2|52|3|50|4|(2:39|5)|(7:6|(1:8)(1:54)|43|14|41|15|29)|9|(3:12|13|10)|43|14|41|15|29|(2:(1:49)|(0))) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<cn.baos.watch.sdk.entitiy.WorldEntity> getWorldList(android.content.Context r8) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.yoozworld.watch.utils.WorldUtils.getWorldList(android.content.Context):java.util.List");
    }
}
