package androidx.work.impl.utils.taskexecutor;

import android.os.Handler;
import android.os.Looper;
import androidx.work.impl.utils.SerialExecutorImpl;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class WorkManagerTaskExecutor implements TaskExecutor {
    private final SerialExecutorImpl mBackgroundExecutor;
    final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private final Executor mMainThreadExecutor = new Executor() { // from class: androidx.work.impl.utils.taskexecutor.WorkManagerTaskExecutor.1
        @Override // java.util.concurrent.Executor
        public void execute(Runnable command) {
            WorkManagerTaskExecutor.this.mMainThreadHandler.post(command);
        }
    };

    public WorkManagerTaskExecutor(Executor backgroundExecutor) {
        this.mBackgroundExecutor = new SerialExecutorImpl(backgroundExecutor);
    }

    @Override // androidx.work.impl.utils.taskexecutor.TaskExecutor
    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }

    @Override // androidx.work.impl.utils.taskexecutor.TaskExecutor
    public SerialExecutorImpl getSerialTaskExecutor() {
        return this.mBackgroundExecutor;
    }
}
