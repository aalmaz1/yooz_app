package com.baseflow.geolocator.location;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import com.baseflow.geolocator.errors.ErrorCallback;

/* JADX INFO: loaded from: classes.dex */
public interface LocationClient {
    void getLastKnownPosition(PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback);

    void isLocationServiceEnabled(LocationServiceListener locationServiceListener);

    boolean onActivityResult(int i, int i2);

    void startPositionUpdates(Activity activity, PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback);

    void stopPositionUpdates();

    default boolean checkLocationService(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
    }
}
