package com.tekartik.sqflite;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: classes2.dex */
class DatabaseWorker {
    private Handler handler;
    private HandlerThread handlerThread;
    private DatabaseTask lastTask;
    private final String name;
    protected Runnable onIdle;
    private final int priority;

    DatabaseWorker(String str, int i) {
        this.name = str;
        this.priority = i;
    }

    synchronized void start(Runnable runnable) {
        HandlerThread handlerThread = new HandlerThread(this.name, this.priority);
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper());
        this.onIdle = runnable;
    }

    synchronized void quit() {
        HandlerThread handlerThread = this.handlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.handlerThread = null;
            this.handler = null;
        }
    }

    boolean isLastTaskInTransaction() {
        DatabaseTask databaseTask = this.lastTask;
        return databaseTask != null && databaseTask.isInTransaction();
    }

    Integer lastTaskDatabaseId() {
        DatabaseTask databaseTask = this.lastTask;
        if (databaseTask != null) {
            return databaseTask.getDatabaseId();
        }
        return null;
    }

    void postTask(final DatabaseTask databaseTask) {
        this.handler.post(new Runnable() { // from class: com.tekartik.sqflite.DatabaseWorker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m647lambda$postTask$0$comtekartiksqfliteDatabaseWorker(databaseTask);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: work, reason: merged with bridge method [inline-methods] */
    public void m647lambda$postTask$0$comtekartiksqfliteDatabaseWorker(DatabaseTask databaseTask) {
        databaseTask.runnable.run();
        this.lastTask = databaseTask;
        this.onIdle.run();
    }
}
