package com.inuker.bluetooth.library.connect.listener;

import com.inuker.bluetooth.library.model.BleGattProfile;

/* JADX INFO: loaded from: classes2.dex */
public interface ServiceDiscoverListener extends GattResponseListener {
    void onServicesDiscovered(int i, BleGattProfile bleGattProfile);
}
