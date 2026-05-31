package cn.baos.watch.sdk.manager.gps;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import cn.baos.watch.sdk.util.LogUtil;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class GpsManager implements GpsStatus.Listener {
    private static GpsManager instance;
    public int gpsCount = 0;
    LocationManager locationManager = null;
    private Context mContext;

    public static GpsManager getInstance() {
        if (instance == null) {
            instance = new GpsManager();
        }
        return instance;
    }

    private GpsManager() {
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
            this.locationManager.addGpsStatusListener(this);
            LogUtil.d("开始卫星个数获取");
        } else {
            LogUtil.d("定位服务未打开，不可以获取卫星个数");
        }
    }

    @Override // android.location.GpsStatus.Listener
    public void onGpsStatusChanged(int i) {
        this.gpsCount = 0;
        LogUtil.d("卫星个数onGpsStatusChanged:" + i);
        if (i != 3 && i == 4) {
            GpsStatus gpsStatus = this.locationManager.getGpsStatus(null);
            int maxSatellites = gpsStatus.getMaxSatellites();
            Iterator<GpsSatellite> it = gpsStatus.getSatellites().iterator();
            while (it.hasNext() && this.gpsCount <= maxSatellites) {
                if (it.next().usedInFix()) {
                    this.gpsCount++;
                }
            }
            LogUtil.d("总卫星个数:" + maxSatellites + " 当前可见卫星个数:" + this.gpsCount);
        }
    }
}
