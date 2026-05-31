package com.tekartik.sqflite;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: compiled from: DatabaseWorkerPool.java */
/* JADX INFO: loaded from: classes2.dex */
class SingleDatabaseWorkerPoolImpl implements DatabaseWorkerPool {
    private Handler handler;
    private HandlerThread handlerThread;
    final String name;
    final int priority;

    SingleDatabaseWorkerPoolImpl(String str, int i) {
        this.name = str;
        this.priority = i;
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public void start() {
        HandlerThread handlerThread = new HandlerThread(this.name, this.priority);
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper());
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public void quit() {
        HandlerThread handlerThread = this.handlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.handlerThread = null;
            this.handler = null;
        }
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public void post(DatabaseTask databaseTask) {
        this.handler.post(databaseTask.runnable);
    }
}
