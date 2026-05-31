package com.baseflow.geolocator.location;

import android.location.Location;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface PositionChangedCallback {
    void onPositionChanged(Location location);
}
