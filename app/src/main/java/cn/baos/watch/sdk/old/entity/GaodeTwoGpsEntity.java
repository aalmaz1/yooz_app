package cn.baos.watch.sdk.old.entity;

/* JADX INFO: loaded from: classes.dex */
public class GaodeTwoGpsEntity {
    private double latitude1;
    private double latitude2;
    private double longitude1;
    private double longitude2;

    public double getLatitude1() {
        return this.latitude1;
    }

    public void setLatitude1(double d) {
        this.latitude1 = d;
    }

    public double getLongitude1() {
        return this.longitude1;
    }

    public void setLongitude1(double d) {
        this.longitude1 = d;
    }

    public double getLatitude2() {
        return this.latitude2;
    }

    public void setLatitude2(double d) {
        this.latitude2 = d;
    }

    public double getLongitude2() {
        return this.longitude2;
    }

    public void setLongitude2(double d) {
        this.longitude2 = d;
    }

    public String toString() {
        return "GaodeTwoGpsEntity{latitude1=" + this.latitude1 + ", longitude1=" + this.longitude1 + ", latitude2=" + this.latitude2 + ", longitude2=" + this.longitude2 + '}';
    }
}
