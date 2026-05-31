package cn.baos.watch.sdk.util;

import cn.baos.watch.sdk.database.gps.GpslocEntity;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CoordinateUtils {
    public static double deg2rad(double d) {
        return (d * 3.141592653589793d) / 180.0d;
    }

    public static double rad2deg(double d) {
        return (d * 180.0d) / 3.141592653589793d;
    }

    public static double getDistance(double d, double d2, double d3, double d4) {
        return rad2deg(Math.acos((Math.sin(deg2rad(d)) * Math.sin(deg2rad(d3))) + (Math.cos(deg2rad(d)) * Math.cos(deg2rad(d3)) * Math.cos(deg2rad(d2 - d4))))) * 60.0d * 1.1515d * 1609.344d;
    }

    public static ArrayList<GpslocEntity> removeNearbyDuplicates(List<GpslocEntity> list, double d) {
        ArrayList<GpslocEntity> arrayList = new ArrayList<>();
        int i = 0;
        while (i < list.size()) {
            GpslocEntity gpslocEntity = list.get(i);
            i++;
            for (int i2 = i; i2 < list.size(); i2++) {
                list.get(i2);
            }
            arrayList.add(gpslocEntity);
        }
        return arrayList;
    }
}
