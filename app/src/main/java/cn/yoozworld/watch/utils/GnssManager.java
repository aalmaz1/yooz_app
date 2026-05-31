package cn.yoozworld.watch.utils;

import android.content.Context;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import androidx.core.location.GnssStatusCompat;
import androidx.core.location.LocationManagerCompat;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class GnssManager {
    private static GnssManager instance;
    public int gpsCount = 0;
    LocationManager locationManager = null;
    private Context mContext;

    public static GnssManager getInstance() {
        if (instance == null) {
            instance = new GnssManager();
        }
        return instance;
    }

    private GnssManager() {
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public boolean checkGPSOpen() {
        if (this.locationManager == null) {
            this.locationManager = (LocationManager) this.mContext.getSystemService("location");
        }
        return this.locationManager.isProviderEnabled("gps");
    }

    public void startListenSatellites() {
        if (this.locationManager == null) {
            this.locationManager = (LocationManager) this.mContext.getSystemService("location");
        }
        if (checkGPSOpen()) {
            LocationManagerCompat.registerGnssStatusCallback(this.locationManager, new GnssStatusCompat.Callback() { // from class: cn.yoozworld.watch.utils.GnssManager.1
                @Override // androidx.core.location.GnssStatusCompat.Callback
                public void onStarted() {
                    super.onStarted();
                }

                @Override // androidx.core.location.GnssStatusCompat.Callback
                public void onStopped() {
                    super.onStopped();
                }

                @Override // androidx.core.location.GnssStatusCompat.Callback
                public void onFirstFix(int i) {
                    super.onFirstFix(i);
                }

                @Override // androidx.core.location.GnssStatusCompat.Callback
                public void onSatelliteStatusChanged(GnssStatusCompat gnssStatusCompat) {
                    super.onSatelliteStatusChanged(gnssStatusCompat);
                    GnssManager.this.gpsCount = gnssStatusCompat.getSatelliteCount();
                }
            }, new Handler(Looper.myLooper()));
            LogUtil.d("开始卫星个数获取");
            return;
        }
        LogUtil.d("定位服务未打开，不可以获取卫星个数");
    }
}
