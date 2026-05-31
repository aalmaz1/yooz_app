package com.baseflow.geolocator.errors;

/* JADX INFO: loaded from: classes.dex */
@FunctionalInterface
public interface ErrorCallback {
    void onError(ErrorCodes errorCodes);
}
