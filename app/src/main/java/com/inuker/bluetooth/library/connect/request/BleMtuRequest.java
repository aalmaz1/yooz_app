package com.inuker.bluetooth.library.connect.request;

import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.connect.listener.RequestMtuListener;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;

/* JADX INFO: loaded from: classes2.dex */
public class BleMtuRequest extends BleRequest implements RequestMtuListener {
    private int mMtu;

    public BleMtuRequest(int i, BleGeneralResponse bleGeneralResponse) {
        super(bleGeneralResponse);
        this.mMtu = i;
    }

    @Override // com.inuker.bluetooth.library.connect.request.BleRequest
    public void processRequest() {
        int currentStatus = getCurrentStatus();
        if (currentStatus == 0) {
            onRequestCompleted(-1);
            return;
        }
        if (currentStatus == 2) {
            requestMtu();
        } else if (currentStatus == 19) {
            requestMtu();
        } else {
            onRequestCompleted(-1);
        }
    }

    private void requestMtu() {
        if (!requestMtu(this.mMtu)) {
            onRequestCompleted(-1);
        } else {
            startRequestTiming();
        }
    }

    @Override // com.inuker.bluetooth.library.connect.listener.RequestMtuListener
    public void onMtuChanged(int i, int i2) {
        stopRequestTiming();
        if (i2 == 0) {
            putIntExtra(Constants.EXTRA_MTU, i);
            onRequestCompleted(0);
        } else {
            onRequestCompleted(-1);
        }
    }
}
