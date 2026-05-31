package cn.yoozworld.watch.utils;

import androidx.camera.video.AudioStats;

/* JADX INFO: loaded from: classes.dex */
public class Point {
    public static final int CROSS = 1;
    private static final int DOT = 0;
    private static final double GRANULARITY = 1.0d;
    private double[] coordinates;

    public Point(double d, double d2) {
        this.coordinates = new double[]{d, d2};
    }

    public Point(Point point, double d) {
        this.coordinates = new double[]{point.x(), 0.0d, 0.0d};
        this.coordinates[1] = point.y();
        this.coordinates[2] = d;
    }

    public Point(double... dArr) {
        this.coordinates = (double[]) dArr.clone();
    }

    public double[] toArray() {
        double[] dArr = this.coordinates;
        return new double[]{dArr[0], dArr[1]};
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        int i = 0;
        while (true) {
            double[] dArr = this.coordinates;
            if (i < dArr.length) {
                sb.append(String.format("%.2f", Double.valueOf(dArr[i])));
                if (i != this.coordinates.length - 1) {
                    sb.append(", ");
                }
                i++;
            } else {
                sb.append(")");
                return sb.toString();
            }
        }
    }

    public double x() {
        double[] dArr = this.coordinates;
        return dArr.length > 0 ? dArr[0] : AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    public double y() {
        double[] dArr = this.coordinates;
        return dArr.length > 1 ? dArr[1] : AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    public double z() {
        double[] dArr = this.coordinates;
        return dArr.length > 2 ? dArr[2] : AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    public double n(int i) {
        double[] dArr = this.coordinates;
        return dArr.length > i ? dArr[i] : AudioStats.AUDIO_AMPLITUDE_NONE;
    }

    public int ix() {
        double[] dArr = this.coordinates;
        return (int) Util.constrain(dArr.length > 0 ? dArr[0] : AudioStats.AUDIO_AMPLITUDE_NONE, -2.147483648E9d, 2.147483647E9d);
    }

    public int iy() {
        double[] dArr = this.coordinates;
        return (int) Util.constrain(dArr.length > 1 ? dArr[1] : AudioStats.AUDIO_AMPLITUDE_NONE, -2.147483648E9d, 2.147483647E9d);
    }

    public int iz() {
        double[] dArr = this.coordinates;
        return (int) Util.constrain(dArr.length > 2 ? dArr[2] : AudioStats.AUDIO_AMPLITUDE_NONE, -2.147483648E9d, 2.147483647E9d);
    }

    public int in(int i) {
        double[] dArr = this.coordinates;
        return (int) Util.constrain(dArr.length > i ? dArr[i] : AudioStats.AUDIO_AMPLITUDE_NONE, -2.147483648E9d, 2.147483647E9d);
    }

    public int dim() {
        return this.coordinates.length;
    }

    public boolean same(Point point) {
        return x() == point.x() && y() == point.y();
    }

    public double distance(Point point) {
        return Math.sqrt(sqDistance(point));
    }

    public Point rotate(double d) {
        return new Point((x() * Math.cos(d)) - (y() * Math.sin(d)), (x() * Math.sin(d)) + (y() * Math.cos(d)));
    }

    public double sqDistance(Point point) {
        int iN = 0;
        for (int i = 0; i < Math.max(dim(), point.dim()); i++) {
            iN = (int) (((double) iN) + ((point.n(i) - n(i)) * (point.n(i) - n(i))));
        }
        return iN;
    }

    public double bearing() {
        return bearing(new Point(AudioStats.AUDIO_AMPLITUDE_NONE, AudioStats.AUDIO_AMPLITUDE_NONE));
    }

    public double bearing(Point point) {
        if (point.x() == x()) {
            return (y() <= point.y() ? 0.0d : 3.141592653589793d) + 1.5707963267948966d;
        }
        return ((Math.atan((point.y() - y()) / (point.x() - x())) + (point.x() >= x() ? 0.0d : 3.141592653589793d)) + 6.283185307179586d) % 6.283185307179586d;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Point point = (Point) obj;
        return Util.approx(point.x(), x(), GRANULARITY) && Util.approx(point.y(), y(), GRANULARITY);
    }
}
