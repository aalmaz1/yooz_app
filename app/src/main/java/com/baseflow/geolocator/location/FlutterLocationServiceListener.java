package com.baseflow.geolocator.location;

import com.baseflow.geolocator.errors.ErrorCodes;
import io.flutter.plugin.common.MethodChannel;

/* JADX INFO: loaded from: classes.dex */
public class FlutterLocationServiceListener implements LocationServiceListener {
    private MethodChannel.Result result;

    public FlutterLocationServiceListener(MethodChannel.Result result) {
        this.result = result;
    }

    @Override // com.baseflow.geolocator.location.LocationServiceListener
    public void onLocationServiceResult(boolean z) {
        this.result.success(Boolean.valueOf(z));
    }

    @Override // com.baseflow.geolocator.location.LocationServiceListener
    public void onLocationServiceError(ErrorCodes errorCodes) {
        this.result.error(errorCodes.toString(), errorCodes.toDescription(), null);
    }
}
