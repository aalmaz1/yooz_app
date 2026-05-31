package com.tekartik.sqflite;

/* JADX INFO: loaded from: classes2.dex */
public interface DatabaseWorkerPool {
    void post(DatabaseTask databaseTask);

    void quit();

    void start();

    default void post(final Database database, Runnable runnable) {
        post(new DatabaseTask(database == null ? null : new DatabaseDelegate() { // from class: com.tekartik.sqflite.DatabaseWorkerPool.1
            @Override // com.tekartik.sqflite.DatabaseDelegate
            public int getDatabaseId() {
                return database.id;
            }

            @Override // com.tekartik.sqflite.DatabaseDelegate
            public boolean isInTransaction() {
                return database.isInTransaction();
            }
        }, runnable));
    }

    static DatabaseWorkerPool create(String str, int i, int i2) {
        if (i == 1) {
            return new SingleDatabaseWorkerPoolImpl(str, i2);
        }
        return new DatabaseWorkerPoolImpl(str, i, i2);
    }
}
