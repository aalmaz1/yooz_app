package cn.yoozworld.watch.utils.track;

import androidx.camera.video.AudioStats;
import cn.baos.watch.sdk.database.gps.GpslocEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.gps.GpsModeManager;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.Sport_trace;
import cn.yoozworld.watch.utils.GPStoCord;
import cn.yoozworld.watch.utils.GePoint;
import cn.yoozworld.watch.utils.Point;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes.dex */
public class SportTraceUtils {
    public static void sendGpsToWatch(int i, int i2) {
        LogUtil.e("gps-查询开始--->" + i + "查询结束--->" + i2 + "");
        ArrayList<GpslocEntity> arrayListQueryGpsModeInInterval = GpsModeManager.getInstance().queryGpsModeInInterval(i, i2);
        if (arrayListQueryGpsModeInInterval != null && arrayListQueryGpsModeInInterval.size() > 0) {
            LogUtil.e("gps-查询返回-length:" + arrayListQueryGpsModeInInterval.size());
            LogUtil.e("gps-查询返回:" + new Gson().toJson(arrayListQueryGpsModeInInterval));
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < arrayListQueryGpsModeInInterval.size(); i3++) {
                arrayList.add(new GePoint(Double.parseDouble(arrayListQueryGpsModeInInterval.get(i3).lat), Double.parseDouble(arrayListQueryGpsModeInInterval.get(i3).lon)));
            }
            GePoint gePoint = (GePoint) arrayList.get(0);
            int size = arrayList.size();
            Point[] pointArr = new Point[size];
            for (int i4 = 0; i4 < size; i4++) {
                pointArr[i4] = GPSToCord((GePoint) arrayList.get(i4), gePoint);
            }
            ArrayList arrayList2 = new ArrayList();
            if (size > 0) {
                for (int i5 = 0; i5 < size; i5++) {
                    Point point = pointArr[i5];
                    HashMap map = new HashMap();
                    map.put("x", Integer.valueOf((int) point.x()));
                    map.put("y", Integer.valueOf((int) point.y()));
                    arrayList2.add(map);
                }
                LogUtil.e("---sendSportStrace> simplifyLine=" + arrayList2.size());
                LogUtil.e("---sendSportStrace>simplifyLine=" + new Gson().toJson(arrayList2));
                List<Map<String, Integer>> listSimplifyLine = simplifyLine(arrayList2);
                LogUtil.e("---sendSportStrace> originPoints=" + listSimplifyLine.size());
                LogUtil.e("---sendSportStrace>originPoints=" + new Gson().toJson(listSimplifyLine));
                if (listSimplifyLine.size() > 80) {
                    listSimplifyLine = getOriginPoints(listSimplifyLine, 80);
                }
                ArrayList<Map<String, Integer>> arrayListRemoveSamePoint = removeSamePoint(listSimplifyLine);
                LogUtil.e("---sendSportStrace> removeSamePoint=" + arrayListRemoveSamePoint.size());
                LogUtil.e("---sendSportStrace> removeSamePoint=" + new Gson().toJson(arrayListRemoveSamePoint));
                ArrayList<Map<String, Integer>> arrayListRemoveSamePoint2 = removeSamePoint(arrayListRemoveSamePoint);
                byte[] bArr = new byte[arrayListRemoveSamePoint2.size()];
                byte[] bArr2 = new byte[arrayListRemoveSamePoint2.size()];
                int i6 = 0;
                boolean z = true;
                for (int i7 = 0; i7 < arrayListRemoveSamePoint2.size(); i7++) {
                    bArr[i7] = (byte) (arrayListRemoveSamePoint2.get(i7).get("x").intValue() & 255);
                    bArr2[i7] = (byte) (arrayListRemoveSamePoint2.get(i7).get("y").intValue() & 255);
                    if (arrayListRemoveSamePoint2.get(i7).get("x").byteValue() != 0) {
                        z = false;
                    }
                    if (arrayListRemoveSamePoint2.get(i7).get("y").byteValue() != 0) {
                        z = false;
                    }
                    i6++;
                }
                if (z || i6 <= 1) {
                    return;
                }
                LogUtil.e("---sendSportStrace2>" + new Gson().toJson(arrayListRemoveSamePoint2));
                Sport_trace sport_trace = new Sport_trace();
                sport_trace.begin_timestamp = i;
                sport_trace.end_timestamp = i2;
                sport_trace.points_x = bArr;
                sport_trace.points_y = bArr2;
                MessageManager.getInstance().setMapTrace(sport_trace);
                return;
            }
            return;
        }
        LogUtil.e("gps-查询返回-null");
    }

    public static Point GPSToCord(GePoint gePoint, GePoint gePoint2) {
        double[] cord = GPStoCord.getCord(gePoint, gePoint2);
        return new Point(cord[1], cord[0]);
    }

    public static List<Map<String, Integer>> simplifyLine(List<Map<String, Integer>> list) {
        ArrayList arrayList = new ArrayList();
        if (list.size() > 0) {
            arrayList.add(list.get(0));
            Map<String, Integer> map = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                Map<String, Integer> map2 = list.get(i);
                if (((int) Math.hypot(map2.get("x").intValue() - map.get("x").intValue(), map2.get("y").intValue() - map.get("y").intValue())) > 3) {
                    arrayList.add(list.get(i));
                    map = map2;
                }
            }
            arrayList.add(list.get(list.size() - 1));
        }
        return arrayList;
    }

    public static List<Map<String, Integer>> getOriginPoints(List<Map<String, Integer>> list, int i) {
        if (list.size() <= i) {
            return list;
        }
        List<Map<String, Object>> listCalculateDistances = calculateDistances(list);
        listCalculateDistances.sort(Comparator.comparingDouble(new ToDoubleFunction() { // from class: cn.yoozworld.watch.utils.track.SportTraceUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                return ((Double) ((Map) obj).get("distance")).doubleValue();
            }
        }).reversed().thenComparingDouble(new ToDoubleFunction() { // from class: cn.yoozworld.watch.utils.track.SportTraceUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                return ((Double) ((Map) obj).get("area")).doubleValue();
            }
        }).reversed());
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            int iIntValue = ((Integer) listCalculateDistances.get(i2).get("index")).intValue();
            HashMap map = new HashMap(list.get(iIntValue));
            map.put("index", Integer.valueOf(iIntValue));
            arrayList.add(map);
        }
        arrayList.sort(Comparator.comparingInt(new ToIntFunction() { // from class: cn.yoozworld.watch.utils.track.SportTraceUtils$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) ((Map) obj).get("index")).intValue();
            }
        }));
        return arrayList;
    }

    public static List<Map<String, Object>> calculateDistances(List<Map<String, Integer>> list) {
        double dAbs;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < list.size() - 1) {
            Map<String, Integer> map = list.get(i);
            int i2 = i + 1;
            Map<String, Integer> map2 = list.get(i2);
            double dSqrt = Math.sqrt(Math.pow(map.get("x").intValue() - map2.get("x").intValue(), 2.0d) + Math.pow(map.get("y").intValue() - map2.get("y").intValue(), 2.0d));
            if (i < list.size() - 2) {
                Map<String, Integer> map3 = list.get(i + 2);
                dAbs = ((double) Math.abs(((map.get("x").intValue() * (map2.get("y").intValue() - map3.get("y").intValue())) + (map2.get("x").intValue() * (map3.get("y").intValue() - map.get("y").intValue()))) + (map3.get("x").intValue() * (map.get("y").intValue() - map2.get("y").intValue())))) / 2.0d;
            } else {
                dAbs = AudioStats.AUDIO_AMPLITUDE_NONE;
            }
            HashMap map4 = new HashMap();
            map4.put("index", Integer.valueOf(i2));
            map4.put("distance", Double.valueOf(dSqrt));
            map4.put("area", Double.valueOf(dAbs));
            arrayList.add(map4);
            i = i2;
        }
        return arrayList;
    }

    public static ArrayList<Map<String, Integer>> removeSamePoint(List<Map<String, Integer>> list) {
        LogUtil.e("----pointArr:" + new Gson().toJson(list));
        ArrayList<Map> arrayList = new ArrayList();
        float f = 0.0f;
        float f2 = -3.4028235E38f;
        float f3 = -3.4028235E38f;
        int i = 0;
        float f4 = Float.MAX_VALUE;
        float f5 = Float.MAX_VALUE;
        float f6 = 0.0f;
        for (Map<String, Integer> map : list) {
            float fIntValue = map.get("x").intValue();
            float fIntValue2 = map.get("y").intValue();
            if (fIntValue != f || fIntValue2 != f6 || i == 0) {
                float fMin = Math.min(f4, fIntValue);
                float fMin2 = Math.min(f5, fIntValue2);
                float fMax = Math.max(f2, fIntValue);
                float fMax2 = Math.max(f3, fIntValue2);
                HashMap map2 = new HashMap();
                map2.put("x", Integer.valueOf((int) fIntValue));
                map2.put("y", Integer.valueOf((int) fIntValue2));
                arrayList.add(map2);
                f2 = fMax;
                f3 = fMax2;
                f4 = fMin;
                f5 = fMin2;
                f6 = fIntValue2;
                f = fIntValue;
            }
            i++;
        }
        LogUtil.e("----pointArr -end:" + new Gson().toJson(list));
        LogUtil.e("minX:" + f4 + " minY:" + f5 + " maxX:" + f2 + " maxY:" + f3);
        ArrayList<Map<String, Integer>> arrayList2 = new ArrayList<>();
        float fMax3 = 100.0f / Math.max(Math.abs(f2 - f4), Math.abs(f3 - f5));
        LogUtil.e("----pointArr -Map - length:" + fMax3);
        for (Map map3 : arrayList) {
            int iIntValue = (int) ((((Integer) map3.get("x")).intValue() - f4) * fMax3);
            int iIntValue2 = (int) ((((Integer) map3.get("y")).intValue() - f5) * fMax3);
            LogUtil.e("----pointArr -Map - xNormalized:" + iIntValue);
            LogUtil.e("----pointArr -Map - yNormalized:" + iIntValue2);
            HashMap map4 = new HashMap();
            map4.put("x", Integer.valueOf(iIntValue));
            map4.put("y", Integer.valueOf(iIntValue2));
            arrayList2.add(map4);
        }
        LogUtil.e("----pointArr -result:" + new Gson().toJson(arrayList2));
        return arrayList2;
    }
}
