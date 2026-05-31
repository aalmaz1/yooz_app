package androidx.work.impl.utils;

import androidx.work.WorkerParameters;
import androidx.work.impl.StartStopToken;
import androidx.work.impl.WorkManagerImpl;

/* JADX INFO: loaded from: classes.dex */
public class StartWorkRunnable implements Runnable {
    private WorkerParameters.RuntimeExtras mRuntimeExtras;
    private WorkManagerImpl mWorkManagerImpl;
    private StartStopToken mWorkSpecId;

    public StartWorkRunnable(WorkManagerImpl workManagerImpl, StartStopToken workSpecId, WorkerParameters.RuntimeExtras runtimeExtras) {
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkSpecId = workSpecId;
        this.mRuntimeExtras = runtimeExtras;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.mWorkManagerImpl.getProcessor().startWork(this.mWorkSpecId, this.mRuntimeExtras);
    }
}
