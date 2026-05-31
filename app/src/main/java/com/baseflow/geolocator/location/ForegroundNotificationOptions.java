package com.baseflow.geolocator.location;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ForegroundNotificationOptions {
    private final boolean enableWakeLock;
    private final boolean enableWifiLock;
    private final AndroidIconResource notificationIcon;
    private final String notificationText;
    private final String notificationTitle;
    private final boolean setOngoing;

    public static ForegroundNotificationOptions parseArguments(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        AndroidIconResource arguments = AndroidIconResource.parseArguments((Map) map.get("notificationIcon"));
        return new ForegroundNotificationOptions((String) map.get("notificationTitle"), (String) map.get("notificationText"), arguments, ((Boolean) map.get("enableWifiLock")).booleanValue(), ((Boolean) map.get("enableWakeLock")).booleanValue(), ((Boolean) map.get("setOngoing")).booleanValue());
    }

    private ForegroundNotificationOptions(String str, String str2, AndroidIconResource androidIconResource, boolean z, boolean z2, boolean z3) {
        this.notificationTitle = str;
        this.notificationText = str2;
        this.notificationIcon = androidIconResource;
        this.enableWifiLock = z;
        this.enableWakeLock = z2;
        this.setOngoing = z3;
    }

    public String getNotificationTitle() {
        return this.notificationTitle;
    }

    public String getNotificationText() {
        return this.notificationText;
    }

    public AndroidIconResource getNotificationIcon() {
        return this.notificationIcon;
    }

    public boolean isEnableWifiLock() {
        return this.enableWifiLock;
    }

    public boolean isEnableWakeLock() {
        return this.enableWakeLock;
    }

    public boolean isSetOngoing() {
        return this.setOngoing;
    }
}
