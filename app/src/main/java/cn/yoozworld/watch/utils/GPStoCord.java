package cn.yoozworld.watch.utils;

/* JADX INFO: loaded from: classes.dex */
public class GPStoCord {
    private static final double MACRO_AXIS = 6378137.0d;
    private static final double MINOR_AXIS = 6356752.0d;

    public static double[] getCord(GePoint gePoint, GePoint gePoint2) {
        return new double[]{turnY(gePoint, gePoint2), turnX(gePoint, gePoint2)};
    }

    private static double turnY(GePoint gePoint, GePoint gePoint2) {
        double dPow = Math.pow(MACRO_AXIS, 2.0d);
        double dPow2 = Math.pow(MINOR_AXIS, 2.0d);
        double dPow3 = Math.pow(Math.tan(gePoint.getLatitude()), 2.0d);
        double dPow4 = Math.pow(1.0d / Math.tan(gePoint.getLatitude()), 2.0d);
        double dSqrt = dPow / Math.sqrt((dPow3 * dPow2) + dPow);
        double dSqrt2 = dPow2 / Math.sqrt((dPow4 * dPow) + dPow2);
        double dPow5 = Math.pow(Math.tan(gePoint2.getLatitude()), 2.0d);
        double dPow6 = Math.pow(1.0d / Math.tan(gePoint2.getLatitude()), 2.0d);
        double dSqrt3 = dPow / Math.sqrt((dPow5 * dPow2) + dPow);
        double dSqrt4 = dPow2 / Math.sqrt((dPow * dPow6) + dPow2);
        if (gePoint.getLatitude() > gePoint2.getLatitude()) {
            return -new Point(dSqrt, dSqrt2).distance(new Point(dSqrt3, dSqrt4));
        }
        return new Point(dSqrt, dSqrt2).distance(new Point(dSqrt3, dSqrt4));
    }

    private static double turnX(GePoint gePoint, GePoint gePoint2) {
        double dPow = Math.pow(MACRO_AXIS, 2.0d);
        return (dPow / Math.sqrt((Math.pow(MINOR_AXIS, 2.0d) * Math.pow(Math.tan(gePoint.getLatitude()), 2.0d)) + dPow)) * ((-gePoint2.getLongtitude()) + gePoint.getLongtitude());
    }
}
