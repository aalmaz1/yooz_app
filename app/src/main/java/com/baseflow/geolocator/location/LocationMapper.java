package com.baseflow.geolocator.location;

import android.location.Location;
import android.os.Build;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LocationMapper {
    public static Map<String, Object> toHashMap(Location location) {
        if (location == null) {
            return null;
        }
        HashMap map = new HashMap();
        map.put("latitude", Double.valueOf(location.getLatitude()));
        map.put("longitude", Double.valueOf(location.getLongitude()));
        map.put("timestamp", Long.valueOf(location.getTime()));
        map.put("is_mocked", Boolean.valueOf(isMocked(location)));
        if (location.hasAltitude()) {
            map.put("altitude", Double.valueOf(location.getAltitude()));
        }
        if (location.hasVerticalAccuracy()) {
            map.put("altitude_accuracy", Float.valueOf(location.getVerticalAccuracyMeters()));
        }
        if (location.hasAccuracy()) {
            map.put("accuracy", Double.valueOf(location.getAccuracy()));
        }
        if (location.hasBearing()) {
            map.put("heading", Double.valueOf(location.getBearing()));
        }
        if (location.hasBearingAccuracy()) {
            map.put("heading_accuracy", Float.valueOf(location.getBearingAccuracyDegrees()));
        }
        if (location.hasSpeed()) {
            map.put("speed", Double.valueOf(location.getSpeed()));
        }
        if (location.hasSpeedAccuracy()) {
            map.put("speed_accuracy", Double.valueOf(location.getSpeedAccuracyMetersPerSecond()));
        }
        if (location.getExtras() != null && location.getExtras().containsKey(NmeaClient.NMEA_ALTITUDE_EXTRA)) {
            map.put("altitude", Double.valueOf(location.getExtras().getDouble(NmeaClient.NMEA_ALTITUDE_EXTRA)));
        }
        return map;
    }

    private static boolean isMocked(Location location) {
        if (Build.VERSION.SDK_INT >= 31) {
            return location.isMock();
        }
        return location.isFromMockProvider();
    }
}
