package dev.fluttercommunity.plus.connectivity;

import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: classes2.dex */
public class Connectivity {
    static final String CONNECTIVITY_BLUETOOTH = "bluetooth";
    static final String CONNECTIVITY_ETHERNET = "ethernet";
    static final String CONNECTIVITY_MOBILE = "mobile";
    static final String CONNECTIVITY_NONE = "none";
    static final String CONNECTIVITY_VPN = "vpn";
    static final String CONNECTIVITY_WIFI = "wifi";
    private final ConnectivityManager connectivityManager;

    public Connectivity(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    String getNetworkType() {
        NetworkCapabilities networkCapabilities = this.connectivityManager.getNetworkCapabilities(this.connectivityManager.getActiveNetwork());
        return networkCapabilities == null ? "none" : networkCapabilities.hasTransport(1) ? CONNECTIVITY_WIFI : networkCapabilities.hasTransport(3) ? CONNECTIVITY_ETHERNET : networkCapabilities.hasTransport(4) ? CONNECTIVITY_VPN : networkCapabilities.hasTransport(0) ? CONNECTIVITY_MOBILE : networkCapabilities.hasTransport(2) ? CONNECTIVITY_BLUETOOTH : getNetworkTypeLegacy();
    }

    private String getNetworkTypeLegacy() {
        NetworkInfo activeNetworkInfo = this.connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return "none";
        }
        int type = activeNetworkInfo.getType();
        return type != 0 ? type != 1 ? (type == 4 || type == 5) ? CONNECTIVITY_MOBILE : type != 6 ? type != 7 ? type != 9 ? type != 17 ? "none" : CONNECTIVITY_VPN : CONNECTIVITY_ETHERNET : CONNECTIVITY_BLUETOOTH : CONNECTIVITY_WIFI : CONNECTIVITY_WIFI : CONNECTIVITY_MOBILE;
    }

    public ConnectivityManager getConnectivityManager() {
        return this.connectivityManager;
    }
}
