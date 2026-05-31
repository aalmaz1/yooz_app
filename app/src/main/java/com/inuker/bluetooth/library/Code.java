package com.inuker.bluetooth.library;

/* JADX INFO: loaded from: classes2.dex */
public class Code {
    public static final int BLE_NOT_SUPPORTED = -4;
    public static final int BLUETOOTH_DISABLED = -5;
    public static final int ILLEGAL_ARGUMENT = -3;
    public static final int REQUEST_CANCELED = -2;
    public static final int REQUEST_DENIED = -9;
    public static final int REQUEST_EXCEPTION = -10;
    public static final int REQUEST_FAILED = -1;
    public static final int REQUEST_OVERFLOW = -8;
    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_TIMEDOUT = -7;
    public static final int REQUEST_UNKNOWN = -11;
    public static final int SERVICE_UNREADY = -6;

    public static String toString(int i) {
        return i != -9 ? i != -7 ? i != -6 ? i != -5 ? i != -4 ? i != -3 ? i != -1 ? i != 0 ? "unknown code: " + i : "REQUEST_SUCCESS" : "REQUEST_FAILED" : "ILLEGAL_ARGUMENT" : "BLE_NOT_SUPPORTED" : "BLUETOOTH_DISABLED" : "SERVICE_UNREADY" : "REQUEST_TIMEDOUT" : "REQUEST_DENIED";
    }
}
