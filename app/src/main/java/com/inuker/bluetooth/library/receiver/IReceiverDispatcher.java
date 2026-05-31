package com.inuker.bluetooth.library.receiver;

import com.inuker.bluetooth.library.receiver.listener.BluetoothReceiverListener;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface IReceiverDispatcher {
    List<BluetoothReceiverListener> getListeners(Class<?> cls);
}
