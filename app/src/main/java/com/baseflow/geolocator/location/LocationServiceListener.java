package com.baseflow.geolocator.location;

import com.baseflow.geolocator.errors.ErrorCodes;

/* JADX INFO: loaded from: classes.dex */
public interface LocationServiceListener {
    void onLocationServiceError(ErrorCodes errorCodes);

    void onLocationServiceResult(boolean z);
}
