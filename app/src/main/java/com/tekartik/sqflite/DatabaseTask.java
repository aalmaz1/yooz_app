package com.tekartik.sqflite;

/* JADX INFO: loaded from: classes2.dex */
final class DatabaseTask {
    private final DatabaseDelegate database;
    final Runnable runnable;

    DatabaseTask(DatabaseDelegate databaseDelegate, Runnable runnable) {
        this.database = databaseDelegate;
        this.runnable = runnable;
    }

    public boolean isInTransaction() {
        DatabaseDelegate databaseDelegate = this.database;
        return databaseDelegate != null && databaseDelegate.isInTransaction();
    }

    public Integer getDatabaseId() {
        DatabaseDelegate databaseDelegate = this.database;
        if (databaseDelegate != null) {
            return Integer.valueOf(databaseDelegate.getDatabaseId());
        }
        return null;
    }
}
