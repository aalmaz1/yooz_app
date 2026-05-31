package cn.yoozworld.watch.utils;

import androidx.camera.video.AudioStats;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Util {
    public static final double ABS_ALTITUDE = 228.0d;
    public static final double COORD_SCALE = 10000.0d;

    public static double det(double[][] dArr) {
        int length = dArr.length;
        double[] dArr2 = dArr[0];
        if (length != dArr2.length) {
            throw new ArithmeticException("determinant of non-square matrix");
        }
        if (dArr.length == 1) {
            return dArr2[0];
        }
        double dDet = AudioStats.AUDIO_AMPLITUDE_NONE;
        for (int i = 0; i < dArr.length; i++) {
            dDet += ((double) (i % 2 == 0 ? 1 : -1)) * dArr[i][0] * det(minor(dArr, i, 0));
        }
        return dDet;
    }

    public static double[][] minor(double[][] dArr, int i, int i2) {
        if (i < dArr.length) {
            double[] dArr2 = dArr[0];
            if (i2 < dArr2.length) {
                double[][] dArr3 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, dArr.length - 1, dArr2.length - 1);
                for (int i3 = 0; i3 < dArr.length; i3++) {
                    for (int i4 = 0; i4 < dArr.length; i4++) {
                        if (i3 < i && i4 < i2) {
                            dArr3[i3][i4] = dArr[i3][i4];
                        }
                        if (i3 < i && i4 > i2) {
                            dArr3[i3][i4 - 1] = dArr[i3][i4];
                        }
                        if (i3 > i && i4 < i2) {
                            dArr3[i3 - 1][i4] = dArr[i3][i4];
                        }
                        if (i3 > i && i4 > i2) {
                            dArr3[i3 - 1][i4 - 1] = dArr[i3][i4];
                        }
                    }
                }
                return dArr3;
            }
        }
        return dArr;
    }

    public static boolean within(double d, double d2, double d3, double d4) {
        return d3 - d4 < Math.max(d, d2) && d3 + d4 >= Math.min(d, d2);
    }

    public static boolean within(double d, double d2, double d3) {
        return d3 <= Math.max(d, d2) && d3 >= Math.min(d, d2);
    }

    public static boolean approx(double d, double d2, double d3) {
        return Math.abs(d - d2) < d3;
    }

    public static <I extends Comparable> I max(I... iArr) {
        I i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (iArr[i2].compareTo(i) > 0) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public static <I extends Comparable> I min(I... iArr) {
        I i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (iArr[i2].compareTo(i) < 0) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public static double constrain(double d, double d2, double d3) {
        if (d > ((Double) max(Double.valueOf(d2), Double.valueOf(d3))).doubleValue()) {
            d = ((Double) max(Double.valueOf(d2), Double.valueOf(d3))).doubleValue();
        }
        return d < ((Double) min(Double.valueOf(d2), Double.valueOf(d3))).doubleValue() ? ((Double) min(Double.valueOf(d2), Double.valueOf(d3))).doubleValue() : d;
    }

    public static <I> List<Point> adjacent(I[][] iArr, Point point, int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = -i; i2 < i; i2++) {
            safeAdd(arrayList, iArr, point.ix() + i2, point.iy() + i);
            safeAdd(arrayList, iArr, point.ix() + i2 + 1, point.iy() - i);
            safeAdd(arrayList, iArr, point.ix() + i, point.iy() + i2 + 1);
            safeAdd(arrayList, iArr, point.ix() - i, point.iy() + i2);
        }
        return arrayList;
    }

    private static <I> void safeAdd(List<Point> list, I[][] iArr, int i, int i2) {
        if (i < 0 || i2 < 0 || i > iArr.length || i2 > iArr[0].length) {
            return;
        }
        list.add(new Point(i, i2));
    }

    public static <I> List<I> removeNull(List<I> list) {
        while (list.contains(null)) {
            list.remove((Object) null);
        }
        return list;
    }

    public static int factorial(int i) {
        if (i <= 1) {
            return 1;
        }
        return factorial(i - 1) * i;
    }

    public static <E> List<E> occurencesOf(E e, List<E> list) {
        ArrayList arrayList = new ArrayList();
        while (list.contains(e)) {
            arrayList.add(list.remove(list.lastIndexOf(e)));
        }
        return arrayList;
    }

    public static String toKML(List<Point> list) {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n  <Document>\n    <name>Coverage path</name>\n    <description>Automatically generated.</description>\n    <Style id=\"thickRedLine\">\n          <LineStyle>\n            <color>ff0000ff</color>\n            <width>10</width>\n          </LineStyle>\n        </Style>\n    <Placemark>\n      <name>Absolute Extruded</name>\n      <description></description>\n      <styleUrl>#thickRedLine</styleUrl>\n      <LineString>\n        <tessellate>1</tessellate>\n        <altitudeMode>relativeToGround</altitudeMode>\n        <coordinates>\n");
        for (Point point : list) {
            sb.append("            ").append((-point.x()) / 10000.0d).append(",").append(point.y() / 10000.0d).append(",").append(point.z() + 228.0d).append("\n");
        }
        sb.append("        </coordinates>\n      </LineString>\n    </Placemark>\n  </Document>\n</kml>");
        return sb.toString();
    }
}
