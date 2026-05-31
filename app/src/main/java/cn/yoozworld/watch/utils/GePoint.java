package cn.yoozworld.watch.utils;

/* JADX INFO: loaded from: classes.dex */
public class GePoint {
    public double latitude;
    public double longtitude;

    public GePoint(double d, double d2) {
        this.latitude = d;
        this.longtitude = d2;
    }

    public double getLatitude() {
        return ((this.latitude * 2.0d) * 3.141592653589793d) / 360.0d;
    }

    public double getLongtitude() {
        return ((this.longtitude * 2.0d) * 3.141592653589793d) / 360.0d;
    }
}
