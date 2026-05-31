package com.baseflow.geolocator.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Bundle;
import android.os.Handler;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class NmeaClient {
    public static final String NMEA_ALTITUDE_EXTRA = "geolocator_mslAltitude";
    private final Context context;
    private String lastNmeaMessage;
    private Calendar lastNmeaMessageTime;
    private final LocationManager locationManager;
    private final LocationOptions locationOptions;
    private boolean listenerAdded = false;
    private OnNmeaMessageListener nmeaMessageListener = new OnNmeaMessageListener() { // from class: com.baseflow.geolocator.location.NmeaClient$$ExternalSyntheticLambda0
        @Override // android.location.OnNmeaMessageListener
        public final void onNmeaMessage(String str, long j) {
            this.f$0.m481lambda$new$0$combaseflowgeolocatorlocationNmeaClient(str, j);
        }
    };

    public NmeaClient(Context context, LocationOptions locationOptions) {
        this.context = context;
        this.locationOptions = locationOptions;
        this.locationManager = (LocationManager) context.getSystemService("location");
    }

    /* JADX INFO: renamed from: lambda$new$0$com-baseflow-geolocator-location-NmeaClient, reason: not valid java name */
    /* synthetic */ void m481lambda$new$0$combaseflowgeolocatorlocationNmeaClient(String str, long j) {
        if (str.startsWith("$GPGGA")) {
            this.lastNmeaMessage = str;
            this.lastNmeaMessageTime = Calendar.getInstance();
        }
    }

    public void start() {
        LocationOptions locationOptions;
        LocationManager locationManager;
        if (this.listenerAdded || (locationOptions = this.locationOptions) == null || !locationOptions.isUseMSLAltitude() || (locationManager = this.locationManager) == null) {
            return;
        }
        locationManager.addNmeaListener(this.nmeaMessageListener, (Handler) null);
        this.listenerAdded = true;
    }

    public void stop() {
        LocationManager locationManager;
        LocationOptions locationOptions = this.locationOptions;
        if (locationOptions == null || !locationOptions.isUseMSLAltitude() || (locationManager = this.locationManager) == null) {
            return;
        }
        locationManager.removeNmeaListener(this.nmeaMessageListener);
        this.listenerAdded = false;
    }

    public void enrichExtrasWithNmea(Location location) {
        if (location == null || this.lastNmeaMessage == null || this.locationOptions == null || !this.listenerAdded) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(13, -5);
        Calendar calendar2 = this.lastNmeaMessageTime;
        if ((calendar2 == null || !calendar2.before(calendar)) && this.locationOptions.isUseMSLAltitude()) {
            String[] strArrSplit = this.lastNmeaMessage.split(",");
            if (!strArrSplit[0].startsWith("$GPGGA") || strArrSplit.length <= 9 || strArrSplit[9].isEmpty()) {
                return;
            }
            double d = Double.parseDouble(strArrSplit[9]);
            if (location.getExtras() == null) {
                location.setExtras(Bundle.EMPTY);
            }
            location.getExtras().putDouble(NMEA_ALTITUDE_EXTRA, d);
        }
    }
}
