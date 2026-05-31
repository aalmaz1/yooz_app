package androidx.room.migration;

import androidx.sqlite.db.SupportSQLiteDatabase;

/* JADX INFO: loaded from: classes.dex */
public interface AutoMigrationSpec {
    default void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
    }
}
