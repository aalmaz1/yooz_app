package androidx.work.impl.model;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.WorkInfo;
import androidx.work.impl.model.WorkSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/* JADX INFO: loaded from: classes.dex */
public final class WorkSpecDao_Impl implements WorkSpecDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<WorkSpec> __insertionAdapterOfWorkSpec;
    private final SharedSQLiteStatement __preparedStmtOfDelete;
    private final SharedSQLiteStatement __preparedStmtOfIncrementGeneration;
    private final SharedSQLiteStatement __preparedStmtOfIncrementPeriodCount;
    private final SharedSQLiteStatement __preparedStmtOfIncrementWorkSpecRunAttemptCount;
    private final SharedSQLiteStatement __preparedStmtOfMarkWorkSpecScheduled;
    private final SharedSQLiteStatement __preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast;
    private final SharedSQLiteStatement __preparedStmtOfResetScheduledState;
    private final SharedSQLiteStatement __preparedStmtOfResetWorkSpecRunAttemptCount;
    private final SharedSQLiteStatement __preparedStmtOfSetLastEnqueuedTime;
    private final SharedSQLiteStatement __preparedStmtOfSetOutput;
    private final SharedSQLiteStatement __preparedStmtOfSetState;
    private final EntityDeletionOrUpdateAdapter<WorkSpec> __updateAdapterOfWorkSpec;

    public WorkSpecDao_Impl(RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfWorkSpec = new EntityInsertionAdapter<WorkSpec>(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR IGNORE INTO `WorkSpec` (`id`,`state`,`worker_class_name`,`input_merger_class_name`,`input`,`output`,`initial_delay`,`interval_duration`,`flex_duration`,`run_attempt_count`,`backoff_policy`,`backoff_delay_duration`,`last_enqueue_time`,`minimum_retention_duration`,`schedule_requested_at`,`run_in_foreground`,`out_of_quota_policy`,`period_count`,`generation`,`required_network_type`,`requires_charging`,`requires_device_idle`,`requires_battery_not_low`,`requires_storage_not_low`,`trigger_content_update_delay`,`trigger_max_content_delay`,`content_uri_triggers`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, WorkSpec workSpec) throws Throwable {
                if (workSpec.id == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, workSpec.id);
                }
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                supportSQLiteStatement.bindLong(2, WorkTypeConverters.stateToInt(workSpec.state));
                if (workSpec.workerClassName == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, workSpec.workerClassName);
                }
                if (workSpec.inputMergerClassName == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, workSpec.inputMergerClassName);
                }
                byte[] byteArrayInternal = Data.toByteArrayInternal(workSpec.input);
                if (byteArrayInternal == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindBlob(5, byteArrayInternal);
                }
                byte[] byteArrayInternal2 = Data.toByteArrayInternal(workSpec.output);
                if (byteArrayInternal2 == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindBlob(6, byteArrayInternal2);
                }
                supportSQLiteStatement.bindLong(7, workSpec.initialDelay);
                supportSQLiteStatement.bindLong(8, workSpec.intervalDuration);
                supportSQLiteStatement.bindLong(9, workSpec.flexDuration);
                supportSQLiteStatement.bindLong(10, workSpec.runAttemptCount);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                supportSQLiteStatement.bindLong(11, WorkTypeConverters.backoffPolicyToInt(workSpec.backoffPolicy));
                supportSQLiteStatement.bindLong(12, workSpec.backoffDelayDuration);
                supportSQLiteStatement.bindLong(13, workSpec.lastEnqueueTime);
                supportSQLiteStatement.bindLong(14, workSpec.minimumRetentionDuration);
                supportSQLiteStatement.bindLong(15, workSpec.scheduleRequestedAt);
                supportSQLiteStatement.bindLong(16, workSpec.expedited ? 1L : 0L);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                supportSQLiteStatement.bindLong(17, WorkTypeConverters.outOfQuotaPolicyToInt(workSpec.outOfQuotaPolicy));
                supportSQLiteStatement.bindLong(18, workSpec.getPeriodCount());
                supportSQLiteStatement.bindLong(19, workSpec.getGeneration());
                Constraints constraints = workSpec.constraints;
                if (constraints != null) {
                    WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                    supportSQLiteStatement.bindLong(20, WorkTypeConverters.networkTypeToInt(constraints.getRequiredNetworkType()));
                    supportSQLiteStatement.bindLong(21, constraints.getRequiresCharging() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(22, constraints.getRequiresDeviceIdle() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(23, constraints.getRequiresBatteryNotLow() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(24, constraints.getRequiresStorageNotLow() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(25, constraints.getContentTriggerUpdateDelayMillis());
                    supportSQLiteStatement.bindLong(26, constraints.getContentTriggerMaxDelayMillis());
                    WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                    byte[] ofTriggersToByteArray = WorkTypeConverters.setOfTriggersToByteArray(constraints.getContentUriTriggers());
                    if (ofTriggersToByteArray == null) {
                        supportSQLiteStatement.bindNull(27);
                        return;
                    } else {
                        supportSQLiteStatement.bindBlob(27, ofTriggersToByteArray);
                        return;
                    }
                }
                supportSQLiteStatement.bindNull(20);
                supportSQLiteStatement.bindNull(21);
                supportSQLiteStatement.bindNull(22);
                supportSQLiteStatement.bindNull(23);
                supportSQLiteStatement.bindNull(24);
                supportSQLiteStatement.bindNull(25);
                supportSQLiteStatement.bindNull(26);
                supportSQLiteStatement.bindNull(27);
            }
        };
        this.__updateAdapterOfWorkSpec = new EntityDeletionOrUpdateAdapter<WorkSpec>(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE OR ABORT `WorkSpec` SET `id` = ?,`state` = ?,`worker_class_name` = ?,`input_merger_class_name` = ?,`input` = ?,`output` = ?,`initial_delay` = ?,`interval_duration` = ?,`flex_duration` = ?,`run_attempt_count` = ?,`backoff_policy` = ?,`backoff_delay_duration` = ?,`last_enqueue_time` = ?,`minimum_retention_duration` = ?,`schedule_requested_at` = ?,`run_in_foreground` = ?,`out_of_quota_policy` = ?,`period_count` = ?,`generation` = ?,`required_network_type` = ?,`requires_charging` = ?,`requires_device_idle` = ?,`requires_battery_not_low` = ?,`requires_storage_not_low` = ?,`trigger_content_update_delay` = ?,`trigger_max_content_delay` = ?,`content_uri_triggers` = ? WHERE `id` = ?";
            }

            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, WorkSpec workSpec) throws Throwable {
                if (workSpec.id == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, workSpec.id);
                }
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                supportSQLiteStatement.bindLong(2, WorkTypeConverters.stateToInt(workSpec.state));
                if (workSpec.workerClassName == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, workSpec.workerClassName);
                }
                if (workSpec.inputMergerClassName == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, workSpec.inputMergerClassName);
                }
                byte[] byteArrayInternal = Data.toByteArrayInternal(workSpec.input);
                if (byteArrayInternal == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindBlob(5, byteArrayInternal);
                }
                byte[] byteArrayInternal2 = Data.toByteArrayInternal(workSpec.output);
                if (byteArrayInternal2 == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindBlob(6, byteArrayInternal2);
                }
                supportSQLiteStatement.bindLong(7, workSpec.initialDelay);
                supportSQLiteStatement.bindLong(8, workSpec.intervalDuration);
                supportSQLiteStatement.bindLong(9, workSpec.flexDuration);
                supportSQLiteStatement.bindLong(10, workSpec.runAttemptCount);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                supportSQLiteStatement.bindLong(11, WorkTypeConverters.backoffPolicyToInt(workSpec.backoffPolicy));
                supportSQLiteStatement.bindLong(12, workSpec.backoffDelayDuration);
                supportSQLiteStatement.bindLong(13, workSpec.lastEnqueueTime);
                supportSQLiteStatement.bindLong(14, workSpec.minimumRetentionDuration);
                supportSQLiteStatement.bindLong(15, workSpec.scheduleRequestedAt);
                supportSQLiteStatement.bindLong(16, workSpec.expedited ? 1L : 0L);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                supportSQLiteStatement.bindLong(17, WorkTypeConverters.outOfQuotaPolicyToInt(workSpec.outOfQuotaPolicy));
                supportSQLiteStatement.bindLong(18, workSpec.getPeriodCount());
                supportSQLiteStatement.bindLong(19, workSpec.getGeneration());
                Constraints constraints = workSpec.constraints;
                if (constraints != null) {
                    WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                    supportSQLiteStatement.bindLong(20, WorkTypeConverters.networkTypeToInt(constraints.getRequiredNetworkType()));
                    supportSQLiteStatement.bindLong(21, constraints.getRequiresCharging() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(22, constraints.getRequiresDeviceIdle() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(23, constraints.getRequiresBatteryNotLow() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(24, constraints.getRequiresStorageNotLow() ? 1L : 0L);
                    supportSQLiteStatement.bindLong(25, constraints.getContentTriggerUpdateDelayMillis());
                    supportSQLiteStatement.bindLong(26, constraints.getContentTriggerMaxDelayMillis());
                    WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                    byte[] ofTriggersToByteArray = WorkTypeConverters.setOfTriggersToByteArray(constraints.getContentUriTriggers());
                    if (ofTriggersToByteArray == null) {
                        supportSQLiteStatement.bindNull(27);
                    } else {
                        supportSQLiteStatement.bindBlob(27, ofTriggersToByteArray);
                    }
                } else {
                    supportSQLiteStatement.bindNull(20);
                    supportSQLiteStatement.bindNull(21);
                    supportSQLiteStatement.bindNull(22);
                    supportSQLiteStatement.bindNull(23);
                    supportSQLiteStatement.bindNull(24);
                    supportSQLiteStatement.bindNull(25);
                    supportSQLiteStatement.bindNull(26);
                    supportSQLiteStatement.bindNull(27);
                }
                if (workSpec.id == null) {
                    supportSQLiteStatement.bindNull(28);
                } else {
                    supportSQLiteStatement.bindString(28, workSpec.id);
                }
            }
        };
        this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM workspec WHERE id=?";
            }
        };
        this.__preparedStmtOfSetState = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.4
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET state=? WHERE id=?";
            }
        };
        this.__preparedStmtOfIncrementPeriodCount = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.5
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET period_count=period_count+1 WHERE id=?";
            }
        };
        this.__preparedStmtOfSetOutput = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.6
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET output=? WHERE id=?";
            }
        };
        this.__preparedStmtOfSetLastEnqueuedTime = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.7
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET last_enqueue_time=? WHERE id=?";
            }
        };
        this.__preparedStmtOfIncrementWorkSpecRunAttemptCount = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.8
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET run_attempt_count=run_attempt_count+1 WHERE id=?";
            }
        };
        this.__preparedStmtOfResetWorkSpecRunAttemptCount = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.9
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET run_attempt_count=0 WHERE id=?";
            }
        };
        this.__preparedStmtOfMarkWorkSpecScheduled = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.10
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET schedule_requested_at=? WHERE id=?";
            }
        };
        this.__preparedStmtOfResetScheduledState = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.11
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET schedule_requested_at=-1 WHERE state NOT IN (2, 3, 5)";
            }
        };
        this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.12
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM workspec WHERE state IN (2, 3, 5) AND (SELECT COUNT(*)=0 FROM dependency WHERE     prerequisite_id=id AND     work_spec_id NOT IN         (SELECT id FROM workspec WHERE state IN (2, 3, 5)))";
            }
        };
        this.__preparedStmtOfIncrementGeneration = new SharedSQLiteStatement(__db) { // from class: androidx.work.impl.model.WorkSpecDao_Impl.13
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "UPDATE workspec SET generation=generation+1 WHERE id=?";
            }
        };
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void insertWorkSpec(final WorkSpec workSpec) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfWorkSpec.insert(workSpec);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void updateWorkSpec(final WorkSpec workSpec) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfWorkSpec.handle(workSpec);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void delete(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDelete.acquire();
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(1);
        } else {
            supportSQLiteStatementAcquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDelete.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int setState(final WorkInfo.State state, final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfSetState.acquire();
        WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
        supportSQLiteStatementAcquire.bindLong(1, WorkTypeConverters.stateToInt(state));
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(2);
        } else {
            supportSQLiteStatementAcquire.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            int iExecuteUpdateDelete = supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return iExecuteUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetState.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void incrementPeriodCount(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfIncrementPeriodCount.acquire();
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(1);
        } else {
            supportSQLiteStatementAcquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfIncrementPeriodCount.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void setOutput(final String id, final Data output) throws Throwable {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfSetOutput.acquire();
        byte[] byteArrayInternal = Data.toByteArrayInternal(output);
        if (byteArrayInternal == null) {
            supportSQLiteStatementAcquire.bindNull(1);
        } else {
            supportSQLiteStatementAcquire.bindBlob(1, byteArrayInternal);
        }
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(2);
        } else {
            supportSQLiteStatementAcquire.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetOutput.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void setLastEnqueuedTime(final String id, final long enqueueTime) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfSetLastEnqueuedTime.acquire();
        supportSQLiteStatementAcquire.bindLong(1, enqueueTime);
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(2);
        } else {
            supportSQLiteStatementAcquire.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfSetLastEnqueuedTime.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int incrementWorkSpecRunAttemptCount(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.acquire();
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(1);
        } else {
            supportSQLiteStatementAcquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            int iExecuteUpdateDelete = supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return iExecuteUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfIncrementWorkSpecRunAttemptCount.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int resetWorkSpecRunAttemptCount(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfResetWorkSpecRunAttemptCount.acquire();
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(1);
        } else {
            supportSQLiteStatementAcquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            int iExecuteUpdateDelete = supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return iExecuteUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfResetWorkSpecRunAttemptCount.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int markWorkSpecScheduled(final String id, final long startTime) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfMarkWorkSpecScheduled.acquire();
        supportSQLiteStatementAcquire.bindLong(1, startTime);
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(2);
        } else {
            supportSQLiteStatementAcquire.bindString(2, id);
        }
        this.__db.beginTransaction();
        try {
            int iExecuteUpdateDelete = supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return iExecuteUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfMarkWorkSpecScheduled.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public int resetScheduledState() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfResetScheduledState.acquire();
        this.__db.beginTransaction();
        try {
            int iExecuteUpdateDelete = supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
            return iExecuteUpdateDelete;
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfResetScheduledState.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void pruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfPruneFinishedWorkWithZeroDependentsIgnoringKeepForAtLeast.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public void incrementGeneration(final String id) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfIncrementGeneration.acquire();
        if (id == null) {
            supportSQLiteStatementAcquire.bindNull(1);
        } else {
            supportSQLiteStatementAcquire.bindString(1, id);
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfIncrementGeneration.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public WorkSpec getWorkSpec(final String id) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        WorkSpec workSpec;
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM workspec WHERE id=?", 1);
        if (id == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "state");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "worker_class_name");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input_merger_class_name");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "output");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "initial_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "interval_duration");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "flex_duration");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_attempt_count");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_policy");
            columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_delay_duration");
            columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "last_enqueue_time");
            columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "minimum_retention_duration");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "schedule_requested_at");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_in_foreground");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "out_of_quota_policy");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "period_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "generation");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "required_network_type");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_charging");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_device_idle");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_battery_not_low");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_storage_not_low");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_content_update_delay");
            int columnIndexOrThrow26 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_max_content_delay");
            int columnIndexOrThrow27 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "content_uri_triggers");
            if (cursorQuery.moveToFirst()) {
                String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                int i6 = cursorQuery.getInt(columnIndexOrThrow2);
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i6);
                String string2 = cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3);
                String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getBlob(columnIndexOrThrow5));
                Data dataFromByteArray2 = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getBlob(columnIndexOrThrow6));
                long j = cursorQuery.getLong(columnIndexOrThrow7);
                long j2 = cursorQuery.getLong(columnIndexOrThrow8);
                long j3 = cursorQuery.getLong(columnIndexOrThrow9);
                int i7 = cursorQuery.getInt(columnIndexOrThrow10);
                int i8 = cursorQuery.getInt(columnIndexOrThrow11);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                BackoffPolicy backoffPolicyIntToBackoffPolicy = WorkTypeConverters.intToBackoffPolicy(i8);
                long j4 = cursorQuery.getLong(columnIndexOrThrow12);
                long j5 = cursorQuery.getLong(columnIndexOrThrow13);
                long j6 = cursorQuery.getLong(columnIndexOrThrow14);
                long j7 = cursorQuery.getLong(columnIndexOrThrow15);
                if (cursorQuery.getInt(columnIndexOrThrow16) != 0) {
                    i = columnIndexOrThrow17;
                    z = true;
                } else {
                    i = columnIndexOrThrow17;
                    z = false;
                }
                int i9 = cursorQuery.getInt(i);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                OutOfQuotaPolicy outOfQuotaPolicyIntToOutOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(i9);
                int i10 = cursorQuery.getInt(columnIndexOrThrow18);
                int i11 = cursorQuery.getInt(columnIndexOrThrow19);
                int i12 = cursorQuery.getInt(columnIndexOrThrow20);
                WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                NetworkType networkTypeIntToNetworkType = WorkTypeConverters.intToNetworkType(i12);
                if (cursorQuery.getInt(columnIndexOrThrow21) != 0) {
                    i2 = columnIndexOrThrow22;
                    z2 = true;
                } else {
                    i2 = columnIndexOrThrow22;
                    z2 = false;
                }
                if (cursorQuery.getInt(i2) != 0) {
                    i3 = columnIndexOrThrow23;
                    z3 = true;
                } else {
                    i3 = columnIndexOrThrow23;
                    z3 = false;
                }
                if (cursorQuery.getInt(i3) != 0) {
                    i4 = columnIndexOrThrow24;
                    z4 = true;
                } else {
                    i4 = columnIndexOrThrow24;
                    z4 = false;
                }
                if (cursorQuery.getInt(i4) != 0) {
                    i5 = columnIndexOrThrow25;
                    z5 = true;
                } else {
                    i5 = columnIndexOrThrow25;
                    z5 = false;
                }
                long j8 = cursorQuery.getLong(i5);
                long j9 = cursorQuery.getLong(columnIndexOrThrow26);
                byte[] blob = cursorQuery.isNull(columnIndexOrThrow27) ? null : cursorQuery.getBlob(columnIndexOrThrow27);
                WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                workSpec = new WorkSpec(string, stateIntToState, string2, string3, dataFromByteArray, dataFromByteArray2, j, j2, j3, new Constraints(networkTypeIntToNetworkType, z2, z3, z4, z5, j8, j9, WorkTypeConverters.byteArrayToSetOfTriggers(blob)), i7, backoffPolicyIntToBackoffPolicy, j4, j5, j6, j7, z, outOfQuotaPolicyIntToOutOfQuotaPolicy, i10, i11);
            } else {
                workSpec = null;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return workSpec;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.IdAndState> getWorkSpecIdAndStatesForName(final String name) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id, state FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                int i = cursorQuery.getInt(1);
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                arrayList.add(new WorkSpec.IdAndState(string, WorkTypeConverters.intToState(i)));
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getAllWorkSpecIds() {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                arrayList.add(cursorQuery.isNull(0) ? null : cursorQuery.getString(0));
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<String>> getAllWorkSpecIdsLiveData() {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec", 0);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"workspec"}, true, new Callable<List<String>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.14
            @Override // java.util.concurrent.Callable
            public List<String> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor cursorQuery = DBUtil.query(WorkSpecDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                    try {
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        while (cursorQuery.moveToNext()) {
                            arrayList.add(cursorQuery.isNull(0) ? null : cursorQuery.getString(0));
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        cursorQuery.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public WorkInfo.State getState(final String id) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT state FROM workspec WHERE id=?", 1);
        if (id == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        WorkInfo.State stateIntToState = null;
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            if (cursorQuery.moveToFirst()) {
                Integer numValueOf = cursorQuery.isNull(0) ? null : Integer.valueOf(cursorQuery.getInt(0));
                if (numValueOf != null) {
                    WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                    stateIntToState = WorkTypeConverters.intToState(numValueOf.intValue());
                }
            }
            return stateIntToState;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public WorkSpec.WorkInfoPojo getWorkStatusPojoForId(String str) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count, generation FROM workspec WHERE id=?", 1);
        if (str == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            WorkSpec.WorkInfoPojo workInfoPojo = null;
            byte[] blob = null;
            Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, true, null);
            try {
                ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.getString(0);
                    if (arrayMap.get(string) == null) {
                        arrayMap.put(string, new ArrayList<>());
                    }
                    String string2 = cursorQuery.getString(0);
                    if (arrayMap2.get(string2) == null) {
                        arrayMap2.put(string2, new ArrayList<>());
                    }
                }
                cursorQuery.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                if (cursorQuery.moveToFirst()) {
                    String string3 = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                    int i = cursorQuery.getInt(1);
                    WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                    WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i);
                    if (!cursorQuery.isNull(2)) {
                        blob = cursorQuery.getBlob(2);
                    }
                    Data dataFromByteArray = Data.fromByteArray(blob);
                    int i2 = cursorQuery.getInt(3);
                    int i3 = cursorQuery.getInt(4);
                    ArrayList<String> arrayList = arrayMap.get(cursorQuery.getString(0));
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    ArrayList<String> arrayList2 = arrayList;
                    ArrayList<Data> arrayList3 = arrayMap2.get(cursorQuery.getString(0));
                    if (arrayList3 == null) {
                        arrayList3 = new ArrayList<>();
                    }
                    workInfoPojo = new WorkSpec.WorkInfoPojo(string3, stateIntToState, dataFromByteArray, i2, i3, arrayList2, arrayList3);
                }
                this.__db.setTransactionSuccessful();
                return workInfoPojo;
            } finally {
                cursorQuery.close();
                roomSQLiteQueryAcquire.release();
            }
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForIds(final List<String> ids) {
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("SELECT id, state, output, run_attempt_count, generation FROM workspec WHERE id IN (");
        int size = ids.size();
        StringUtil.appendPlaceholders(sbNewStringBuilder, size);
        sbNewStringBuilder.append(")");
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire(sbNewStringBuilder.toString(), size + 0);
        int i = 1;
        for (String str : ids) {
            if (str == null) {
                roomSQLiteQueryAcquire.bindNull(i);
            } else {
                roomSQLiteQueryAcquire.bindString(i, str);
            }
            i++;
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, true, null);
            try {
                ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.getString(0);
                    if (arrayMap.get(string) == null) {
                        arrayMap.put(string, new ArrayList<>());
                    }
                    String string2 = cursorQuery.getString(0);
                    if (arrayMap2.get(string2) == null) {
                        arrayMap2.put(string2, new ArrayList<>());
                    }
                }
                cursorQuery.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    String string3 = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                    int i2 = cursorQuery.getInt(1);
                    WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                    WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i2);
                    Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(2) ? null : cursorQuery.getBlob(2));
                    int i3 = cursorQuery.getInt(3);
                    int i4 = cursorQuery.getInt(4);
                    ArrayList<String> arrayList2 = arrayMap.get(cursorQuery.getString(0));
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>();
                    }
                    ArrayList<String> arrayList3 = arrayList2;
                    ArrayList<Data> arrayList4 = arrayMap2.get(cursorQuery.getString(0));
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList<>();
                    }
                    arrayList.add(new WorkSpec.WorkInfoPojo(string3, stateIntToState, dataFromByteArray, i3, i4, arrayList3, arrayList4));
                }
                this.__db.setTransactionSuccessful();
                return arrayList;
            } finally {
                cursorQuery.close();
                roomSQLiteQueryAcquire.release();
            }
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForIds(final List<String> ids) {
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("SELECT id, state, output, run_attempt_count, generation FROM workspec WHERE id IN (");
        int size = ids.size();
        StringUtil.appendPlaceholders(sbNewStringBuilder, size);
        sbNewStringBuilder.append(")");
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire(sbNewStringBuilder.toString(), size + 0);
        int i = 1;
        for (String str : ids) {
            if (str == null) {
                roomSQLiteQueryAcquire.bindNull(i);
            } else {
                roomSQLiteQueryAcquire.bindString(i, str);
            }
            i++;
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.15
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public List<WorkSpec.WorkInfoPojo> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor cursorQuery = DBUtil.query(WorkSpecDao_Impl.this.__db, roomSQLiteQueryAcquire, true, null);
                    try {
                        ArrayMap arrayMap = new ArrayMap();
                        ArrayMap arrayMap2 = new ArrayMap();
                        while (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(0);
                            if (((ArrayList) arrayMap.get(string)) == null) {
                                arrayMap.put(string, new ArrayList());
                            }
                            String string2 = cursorQuery.getString(0);
                            if (((ArrayList) arrayMap2.get(string2)) == null) {
                                arrayMap2.put(string2, new ArrayList());
                            }
                        }
                        cursorQuery.moveToPosition(-1);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        while (cursorQuery.moveToNext()) {
                            String string3 = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                            int i2 = cursorQuery.getInt(1);
                            WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                            WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i2);
                            Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(2) ? null : cursorQuery.getBlob(2));
                            int i3 = cursorQuery.getInt(3);
                            int i4 = cursorQuery.getInt(4);
                            ArrayList arrayList2 = (ArrayList) arrayMap.get(cursorQuery.getString(0));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            ArrayList arrayList3 = arrayList2;
                            ArrayList arrayList4 = (ArrayList) arrayMap2.get(cursorQuery.getString(0));
                            if (arrayList4 == null) {
                                arrayList4 = new ArrayList();
                            }
                            arrayList.add(new WorkSpec.WorkInfoPojo(string3, stateIntToState, dataFromByteArray, i3, i4, arrayList3, arrayList4));
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        cursorQuery.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForTag(final String tag) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count, generation FROM workspec WHERE id IN\n            (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, tag);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, true, null);
            try {
                ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.getString(0);
                    if (arrayMap.get(string) == null) {
                        arrayMap.put(string, new ArrayList<>());
                    }
                    String string2 = cursorQuery.getString(0);
                    if (arrayMap2.get(string2) == null) {
                        arrayMap2.put(string2, new ArrayList<>());
                    }
                }
                cursorQuery.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    String string3 = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                    int i = cursorQuery.getInt(1);
                    WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                    WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i);
                    Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(2) ? null : cursorQuery.getBlob(2));
                    int i2 = cursorQuery.getInt(3);
                    int i3 = cursorQuery.getInt(4);
                    ArrayList<String> arrayList2 = arrayMap.get(cursorQuery.getString(0));
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>();
                    }
                    ArrayList<String> arrayList3 = arrayList2;
                    ArrayList<Data> arrayList4 = arrayMap2.get(cursorQuery.getString(0));
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList<>();
                    }
                    arrayList.add(new WorkSpec.WorkInfoPojo(string3, stateIntToState, dataFromByteArray, i2, i3, arrayList3, arrayList4));
                }
                this.__db.setTransactionSuccessful();
                return arrayList;
            } finally {
                cursorQuery.close();
                roomSQLiteQueryAcquire.release();
            }
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForTag(final String tag) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count, generation FROM workspec WHERE id IN\n            (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, tag);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec", "worktag"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.16
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public List<WorkSpec.WorkInfoPojo> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor cursorQuery = DBUtil.query(WorkSpecDao_Impl.this.__db, roomSQLiteQueryAcquire, true, null);
                    try {
                        ArrayMap arrayMap = new ArrayMap();
                        ArrayMap arrayMap2 = new ArrayMap();
                        while (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(0);
                            if (((ArrayList) arrayMap.get(string)) == null) {
                                arrayMap.put(string, new ArrayList());
                            }
                            String string2 = cursorQuery.getString(0);
                            if (((ArrayList) arrayMap2.get(string2)) == null) {
                                arrayMap2.put(string2, new ArrayList());
                            }
                        }
                        cursorQuery.moveToPosition(-1);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        while (cursorQuery.moveToNext()) {
                            String string3 = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                            int i = cursorQuery.getInt(1);
                            WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                            WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i);
                            Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(2) ? null : cursorQuery.getBlob(2));
                            int i2 = cursorQuery.getInt(3);
                            int i3 = cursorQuery.getInt(4);
                            ArrayList arrayList2 = (ArrayList) arrayMap.get(cursorQuery.getString(0));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            ArrayList arrayList3 = arrayList2;
                            ArrayList arrayList4 = (ArrayList) arrayMap2.get(cursorQuery.getString(0));
                            if (arrayList4 == null) {
                                arrayList4 = new ArrayList();
                            }
                            arrayList.add(new WorkSpec.WorkInfoPojo(string3, stateIntToState, dataFromByteArray, i2, i3, arrayList3, arrayList4));
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        cursorQuery.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec.WorkInfoPojo> getWorkStatusPojoForName(final String name) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count, generation FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, true, null);
            try {
                ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>();
                ArrayMap<String, ArrayList<Data>> arrayMap2 = new ArrayMap<>();
                while (cursorQuery.moveToNext()) {
                    String string = cursorQuery.getString(0);
                    if (arrayMap.get(string) == null) {
                        arrayMap.put(string, new ArrayList<>());
                    }
                    String string2 = cursorQuery.getString(0);
                    if (arrayMap2.get(string2) == null) {
                        arrayMap2.put(string2, new ArrayList<>());
                    }
                }
                cursorQuery.moveToPosition(-1);
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                while (cursorQuery.moveToNext()) {
                    String string3 = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                    int i = cursorQuery.getInt(1);
                    WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                    WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i);
                    Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(2) ? null : cursorQuery.getBlob(2));
                    int i2 = cursorQuery.getInt(3);
                    int i3 = cursorQuery.getInt(4);
                    ArrayList<String> arrayList2 = arrayMap.get(cursorQuery.getString(0));
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>();
                    }
                    ArrayList<String> arrayList3 = arrayList2;
                    ArrayList<Data> arrayList4 = arrayMap2.get(cursorQuery.getString(0));
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList<>();
                    }
                    arrayList.add(new WorkSpec.WorkInfoPojo(string3, stateIntToState, dataFromByteArray, i2, i3, arrayList3, arrayList4));
                }
                this.__db.setTransactionSuccessful();
                return arrayList;
            } finally {
                cursorQuery.close();
                roomSQLiteQueryAcquire.release();
            }
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<List<WorkSpec.WorkInfoPojo>> getWorkStatusPojoLiveDataForName(final String name) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id, state, output, run_attempt_count, generation FROM workspec WHERE id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, name);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"WorkTag", "WorkProgress", "workspec", "workname"}, true, new Callable<List<WorkSpec.WorkInfoPojo>>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.17
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public List<WorkSpec.WorkInfoPojo> call() throws Exception {
                WorkSpecDao_Impl.this.__db.beginTransaction();
                try {
                    Cursor cursorQuery = DBUtil.query(WorkSpecDao_Impl.this.__db, roomSQLiteQueryAcquire, true, null);
                    try {
                        ArrayMap arrayMap = new ArrayMap();
                        ArrayMap arrayMap2 = new ArrayMap();
                        while (cursorQuery.moveToNext()) {
                            String string = cursorQuery.getString(0);
                            if (((ArrayList) arrayMap.get(string)) == null) {
                                arrayMap.put(string, new ArrayList());
                            }
                            String string2 = cursorQuery.getString(0);
                            if (((ArrayList) arrayMap2.get(string2)) == null) {
                                arrayMap2.put(string2, new ArrayList());
                            }
                        }
                        cursorQuery.moveToPosition(-1);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                        WorkSpecDao_Impl.this.__fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap2);
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        while (cursorQuery.moveToNext()) {
                            String string3 = cursorQuery.isNull(0) ? null : cursorQuery.getString(0);
                            int i = cursorQuery.getInt(1);
                            WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                            WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i);
                            Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(2) ? null : cursorQuery.getBlob(2));
                            int i2 = cursorQuery.getInt(3);
                            int i3 = cursorQuery.getInt(4);
                            ArrayList arrayList2 = (ArrayList) arrayMap.get(cursorQuery.getString(0));
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            ArrayList arrayList3 = arrayList2;
                            ArrayList arrayList4 = (ArrayList) arrayMap2.get(cursorQuery.getString(0));
                            if (arrayList4 == null) {
                                arrayList4 = new ArrayList();
                            }
                            arrayList.add(new WorkSpec.WorkInfoPojo(string3, stateIntToState, dataFromByteArray, i2, i3, arrayList3, arrayList4));
                        }
                        WorkSpecDao_Impl.this.__db.setTransactionSuccessful();
                        return arrayList;
                    } finally {
                        cursorQuery.close();
                    }
                } finally {
                    WorkSpecDao_Impl.this.__db.endTransaction();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<Data> getInputsFromPrerequisites(final String id) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT output FROM workspec WHERE id IN\n             (SELECT prerequisite_id FROM dependency WHERE work_spec_id=?)", 1);
        if (id == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, id);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                arrayList.add(Data.fromByteArray(cursorQuery.isNull(0) ? null : cursorQuery.getBlob(0)));
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getUnfinishedWorkWithTag(final String tag) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM worktag WHERE tag=?)", 1);
        if (tag == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, tag);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                arrayList.add(cursorQuery.isNull(0) ? null : cursorQuery.getString(0));
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getUnfinishedWorkWithName(final String name) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5) AND id IN (SELECT work_spec_id FROM workname WHERE name=?)", 1);
        if (name == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, name);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                arrayList.add(cursorQuery.isNull(0) ? null : cursorQuery.getString(0));
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<String> getAllUnfinishedWork() {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT id FROM workspec WHERE state NOT IN (2, 3, 5)", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                arrayList.add(cursorQuery.isNull(0) ? null : cursorQuery.getString(0));
            }
            return arrayList;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public boolean hasUnfinishedWork() {
        boolean z = false;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT COUNT(*) > 0 FROM workspec WHERE state NOT IN (2, 3, 5) LIMIT 1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            if (cursorQuery.moveToFirst()) {
                if (cursorQuery.getInt(0) != 0) {
                    z = true;
                }
            }
            return z;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public LiveData<Long> getScheduleRequestedAtLiveData(final String id) {
        final RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT schedule_requested_at FROM workspec WHERE id=?", 1);
        if (id == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, id);
        }
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"workspec"}, false, new Callable<Long>() { // from class: androidx.work.impl.model.WorkSpecDao_Impl.18
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Long call() throws Exception {
                Cursor cursorQuery = DBUtil.query(WorkSpecDao_Impl.this.__db, roomSQLiteQueryAcquire, false, null);
                try {
                    return Long.valueOf(cursorQuery.moveToFirst() ? cursorQuery.getLong(0) : 0L);
                } finally {
                    cursorQuery.close();
                }
            }

            protected void finalize() {
                roomSQLiteQueryAcquire.release();
            }
        });
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getEligibleWorkForScheduling(final int schedulerLimit) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM workspec WHERE state=0 AND schedule_requested_at=-1 ORDER BY last_enqueue_time LIMIT (SELECT MAX(?-COUNT(*), 0) FROM workspec WHERE schedule_requested_at<>-1 AND state NOT IN (2, 3, 5))", 1);
        roomSQLiteQueryAcquire.bindLong(1, schedulerLimit);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "state");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "worker_class_name");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input_merger_class_name");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "output");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "initial_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "interval_duration");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "flex_duration");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_attempt_count");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_policy");
            columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_delay_duration");
            columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "last_enqueue_time");
            columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "minimum_retention_duration");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "schedule_requested_at");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_in_foreground");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "out_of_quota_policy");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "period_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "generation");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "required_network_type");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_charging");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_device_idle");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_battery_not_low");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_storage_not_low");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_content_update_delay");
            int columnIndexOrThrow26 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_max_content_delay");
            int columnIndexOrThrow27 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "content_uri_triggers");
            int i6 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                int i7 = cursorQuery.getInt(columnIndexOrThrow2);
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i7);
                String string2 = cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3);
                String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getBlob(columnIndexOrThrow5));
                Data dataFromByteArray2 = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getBlob(columnIndexOrThrow6));
                long j = cursorQuery.getLong(columnIndexOrThrow7);
                long j2 = cursorQuery.getLong(columnIndexOrThrow8);
                long j3 = cursorQuery.getLong(columnIndexOrThrow9);
                int i8 = cursorQuery.getInt(columnIndexOrThrow10);
                int i9 = cursorQuery.getInt(columnIndexOrThrow11);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                BackoffPolicy backoffPolicyIntToBackoffPolicy = WorkTypeConverters.intToBackoffPolicy(i9);
                long j4 = cursorQuery.getLong(columnIndexOrThrow12);
                long j5 = cursorQuery.getLong(columnIndexOrThrow13);
                int i10 = i6;
                long j6 = cursorQuery.getLong(i10);
                int i11 = columnIndexOrThrow;
                int i12 = columnIndexOrThrow15;
                long j7 = cursorQuery.getLong(i12);
                columnIndexOrThrow15 = i12;
                int i13 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i13) != 0) {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = true;
                } else {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = false;
                }
                int i14 = cursorQuery.getInt(i);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                OutOfQuotaPolicy outOfQuotaPolicyIntToOutOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(i14);
                columnIndexOrThrow17 = i;
                int i15 = columnIndexOrThrow18;
                int i16 = cursorQuery.getInt(i15);
                columnIndexOrThrow18 = i15;
                int i17 = columnIndexOrThrow19;
                int i18 = cursorQuery.getInt(i17);
                columnIndexOrThrow19 = i17;
                int i19 = columnIndexOrThrow20;
                int i20 = cursorQuery.getInt(i19);
                WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                NetworkType networkTypeIntToNetworkType = WorkTypeConverters.intToNetworkType(i20);
                columnIndexOrThrow20 = i19;
                int i21 = columnIndexOrThrow21;
                if (cursorQuery.getInt(i21) != 0) {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = true;
                } else {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = false;
                }
                if (cursorQuery.getInt(i2) != 0) {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = true;
                } else {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = false;
                }
                if (cursorQuery.getInt(i3) != 0) {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = true;
                } else {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = false;
                }
                if (cursorQuery.getInt(i4) != 0) {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = true;
                } else {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = false;
                }
                long j8 = cursorQuery.getLong(i5);
                columnIndexOrThrow25 = i5;
                int i22 = columnIndexOrThrow26;
                long j9 = cursorQuery.getLong(i22);
                columnIndexOrThrow26 = i22;
                int i23 = columnIndexOrThrow27;
                byte[] blob = cursorQuery.isNull(i23) ? null : cursorQuery.getBlob(i23);
                WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                Constraints constraints = new Constraints(networkTypeIntToNetworkType, z2, z3, z4, z5, j8, j9, WorkTypeConverters.byteArrayToSetOfTriggers(blob));
                columnIndexOrThrow27 = i23;
                arrayList.add(new WorkSpec(string, stateIntToState, string2, string3, dataFromByteArray, dataFromByteArray2, j, j2, j3, constraints, i8, backoffPolicyIntToBackoffPolicy, j4, j5, j6, j7, z, outOfQuotaPolicyIntToOutOfQuotaPolicy, i16, i18));
                columnIndexOrThrow = i11;
                i6 = i10;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getAllEligibleWorkSpecsForScheduling(final int maxLimit) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM workspec WHERE state=0 ORDER BY last_enqueue_time LIMIT ?", 1);
        roomSQLiteQueryAcquire.bindLong(1, maxLimit);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "state");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "worker_class_name");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input_merger_class_name");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "output");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "initial_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "interval_duration");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "flex_duration");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_attempt_count");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_policy");
            columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_delay_duration");
            columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "last_enqueue_time");
            columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "minimum_retention_duration");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "schedule_requested_at");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_in_foreground");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "out_of_quota_policy");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "period_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "generation");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "required_network_type");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_charging");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_device_idle");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_battery_not_low");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_storage_not_low");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_content_update_delay");
            int columnIndexOrThrow26 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_max_content_delay");
            int columnIndexOrThrow27 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "content_uri_triggers");
            int i6 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                int i7 = cursorQuery.getInt(columnIndexOrThrow2);
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i7);
                String string2 = cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3);
                String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getBlob(columnIndexOrThrow5));
                Data dataFromByteArray2 = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getBlob(columnIndexOrThrow6));
                long j = cursorQuery.getLong(columnIndexOrThrow7);
                long j2 = cursorQuery.getLong(columnIndexOrThrow8);
                long j3 = cursorQuery.getLong(columnIndexOrThrow9);
                int i8 = cursorQuery.getInt(columnIndexOrThrow10);
                int i9 = cursorQuery.getInt(columnIndexOrThrow11);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                BackoffPolicy backoffPolicyIntToBackoffPolicy = WorkTypeConverters.intToBackoffPolicy(i9);
                long j4 = cursorQuery.getLong(columnIndexOrThrow12);
                long j5 = cursorQuery.getLong(columnIndexOrThrow13);
                int i10 = i6;
                long j6 = cursorQuery.getLong(i10);
                int i11 = columnIndexOrThrow;
                int i12 = columnIndexOrThrow15;
                long j7 = cursorQuery.getLong(i12);
                columnIndexOrThrow15 = i12;
                int i13 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i13) != 0) {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = true;
                } else {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = false;
                }
                int i14 = cursorQuery.getInt(i);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                OutOfQuotaPolicy outOfQuotaPolicyIntToOutOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(i14);
                columnIndexOrThrow17 = i;
                int i15 = columnIndexOrThrow18;
                int i16 = cursorQuery.getInt(i15);
                columnIndexOrThrow18 = i15;
                int i17 = columnIndexOrThrow19;
                int i18 = cursorQuery.getInt(i17);
                columnIndexOrThrow19 = i17;
                int i19 = columnIndexOrThrow20;
                int i20 = cursorQuery.getInt(i19);
                WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                NetworkType networkTypeIntToNetworkType = WorkTypeConverters.intToNetworkType(i20);
                columnIndexOrThrow20 = i19;
                int i21 = columnIndexOrThrow21;
                if (cursorQuery.getInt(i21) != 0) {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = true;
                } else {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = false;
                }
                if (cursorQuery.getInt(i2) != 0) {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = true;
                } else {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = false;
                }
                if (cursorQuery.getInt(i3) != 0) {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = true;
                } else {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = false;
                }
                if (cursorQuery.getInt(i4) != 0) {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = true;
                } else {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = false;
                }
                long j8 = cursorQuery.getLong(i5);
                columnIndexOrThrow25 = i5;
                int i22 = columnIndexOrThrow26;
                long j9 = cursorQuery.getLong(i22);
                columnIndexOrThrow26 = i22;
                int i23 = columnIndexOrThrow27;
                byte[] blob = cursorQuery.isNull(i23) ? null : cursorQuery.getBlob(i23);
                WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                Constraints constraints = new Constraints(networkTypeIntToNetworkType, z2, z3, z4, z5, j8, j9, WorkTypeConverters.byteArrayToSetOfTriggers(blob));
                columnIndexOrThrow27 = i23;
                arrayList.add(new WorkSpec(string, stateIntToState, string2, string3, dataFromByteArray, dataFromByteArray2, j, j2, j3, constraints, i8, backoffPolicyIntToBackoffPolicy, j4, j5, j6, j7, z, outOfQuotaPolicyIntToOutOfQuotaPolicy, i16, i18));
                columnIndexOrThrow = i11;
                i6 = i10;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getScheduledWork() throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM workspec WHERE state=0 AND schedule_requested_at<>-1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "state");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "worker_class_name");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input_merger_class_name");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "output");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "initial_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "interval_duration");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "flex_duration");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_attempt_count");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_policy");
            columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_delay_duration");
            columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "last_enqueue_time");
            columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "minimum_retention_duration");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "schedule_requested_at");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_in_foreground");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "out_of_quota_policy");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "period_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "generation");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "required_network_type");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_charging");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_device_idle");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_battery_not_low");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_storage_not_low");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_content_update_delay");
            int columnIndexOrThrow26 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_max_content_delay");
            int columnIndexOrThrow27 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "content_uri_triggers");
            int i6 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                int i7 = cursorQuery.getInt(columnIndexOrThrow2);
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i7);
                String string2 = cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3);
                String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getBlob(columnIndexOrThrow5));
                Data dataFromByteArray2 = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getBlob(columnIndexOrThrow6));
                long j = cursorQuery.getLong(columnIndexOrThrow7);
                long j2 = cursorQuery.getLong(columnIndexOrThrow8);
                long j3 = cursorQuery.getLong(columnIndexOrThrow9);
                int i8 = cursorQuery.getInt(columnIndexOrThrow10);
                int i9 = cursorQuery.getInt(columnIndexOrThrow11);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                BackoffPolicy backoffPolicyIntToBackoffPolicy = WorkTypeConverters.intToBackoffPolicy(i9);
                long j4 = cursorQuery.getLong(columnIndexOrThrow12);
                long j5 = cursorQuery.getLong(columnIndexOrThrow13);
                int i10 = i6;
                long j6 = cursorQuery.getLong(i10);
                int i11 = columnIndexOrThrow;
                int i12 = columnIndexOrThrow15;
                long j7 = cursorQuery.getLong(i12);
                columnIndexOrThrow15 = i12;
                int i13 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i13) != 0) {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = true;
                } else {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = false;
                }
                int i14 = cursorQuery.getInt(i);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                OutOfQuotaPolicy outOfQuotaPolicyIntToOutOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(i14);
                columnIndexOrThrow17 = i;
                int i15 = columnIndexOrThrow18;
                int i16 = cursorQuery.getInt(i15);
                columnIndexOrThrow18 = i15;
                int i17 = columnIndexOrThrow19;
                int i18 = cursorQuery.getInt(i17);
                columnIndexOrThrow19 = i17;
                int i19 = columnIndexOrThrow20;
                int i20 = cursorQuery.getInt(i19);
                WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                NetworkType networkTypeIntToNetworkType = WorkTypeConverters.intToNetworkType(i20);
                columnIndexOrThrow20 = i19;
                int i21 = columnIndexOrThrow21;
                if (cursorQuery.getInt(i21) != 0) {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = true;
                } else {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = false;
                }
                if (cursorQuery.getInt(i2) != 0) {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = true;
                } else {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = false;
                }
                if (cursorQuery.getInt(i3) != 0) {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = true;
                } else {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = false;
                }
                if (cursorQuery.getInt(i4) != 0) {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = true;
                } else {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = false;
                }
                long j8 = cursorQuery.getLong(i5);
                columnIndexOrThrow25 = i5;
                int i22 = columnIndexOrThrow26;
                long j9 = cursorQuery.getLong(i22);
                columnIndexOrThrow26 = i22;
                int i23 = columnIndexOrThrow27;
                byte[] blob = cursorQuery.isNull(i23) ? null : cursorQuery.getBlob(i23);
                WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                Constraints constraints = new Constraints(networkTypeIntToNetworkType, z2, z3, z4, z5, j8, j9, WorkTypeConverters.byteArrayToSetOfTriggers(blob));
                columnIndexOrThrow27 = i23;
                arrayList.add(new WorkSpec(string, stateIntToState, string2, string3, dataFromByteArray, dataFromByteArray2, j, j2, j3, constraints, i8, backoffPolicyIntToBackoffPolicy, j4, j5, j6, j7, z, outOfQuotaPolicyIntToOutOfQuotaPolicy, i16, i18));
                columnIndexOrThrow = i11;
                i6 = i10;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getRunningWork() throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM workspec WHERE state=1", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "state");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "worker_class_name");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input_merger_class_name");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "output");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "initial_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "interval_duration");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "flex_duration");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_attempt_count");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_policy");
            columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_delay_duration");
            columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "last_enqueue_time");
            columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "minimum_retention_duration");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "schedule_requested_at");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_in_foreground");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "out_of_quota_policy");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "period_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "generation");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "required_network_type");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_charging");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_device_idle");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_battery_not_low");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_storage_not_low");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_content_update_delay");
            int columnIndexOrThrow26 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_max_content_delay");
            int columnIndexOrThrow27 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "content_uri_triggers");
            int i6 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                int i7 = cursorQuery.getInt(columnIndexOrThrow2);
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i7);
                String string2 = cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3);
                String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getBlob(columnIndexOrThrow5));
                Data dataFromByteArray2 = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getBlob(columnIndexOrThrow6));
                long j = cursorQuery.getLong(columnIndexOrThrow7);
                long j2 = cursorQuery.getLong(columnIndexOrThrow8);
                long j3 = cursorQuery.getLong(columnIndexOrThrow9);
                int i8 = cursorQuery.getInt(columnIndexOrThrow10);
                int i9 = cursorQuery.getInt(columnIndexOrThrow11);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                BackoffPolicy backoffPolicyIntToBackoffPolicy = WorkTypeConverters.intToBackoffPolicy(i9);
                long j4 = cursorQuery.getLong(columnIndexOrThrow12);
                long j5 = cursorQuery.getLong(columnIndexOrThrow13);
                int i10 = i6;
                long j6 = cursorQuery.getLong(i10);
                int i11 = columnIndexOrThrow;
                int i12 = columnIndexOrThrow15;
                long j7 = cursorQuery.getLong(i12);
                columnIndexOrThrow15 = i12;
                int i13 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i13) != 0) {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = true;
                } else {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = false;
                }
                int i14 = cursorQuery.getInt(i);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                OutOfQuotaPolicy outOfQuotaPolicyIntToOutOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(i14);
                columnIndexOrThrow17 = i;
                int i15 = columnIndexOrThrow18;
                int i16 = cursorQuery.getInt(i15);
                columnIndexOrThrow18 = i15;
                int i17 = columnIndexOrThrow19;
                int i18 = cursorQuery.getInt(i17);
                columnIndexOrThrow19 = i17;
                int i19 = columnIndexOrThrow20;
                int i20 = cursorQuery.getInt(i19);
                WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                NetworkType networkTypeIntToNetworkType = WorkTypeConverters.intToNetworkType(i20);
                columnIndexOrThrow20 = i19;
                int i21 = columnIndexOrThrow21;
                if (cursorQuery.getInt(i21) != 0) {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = true;
                } else {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = false;
                }
                if (cursorQuery.getInt(i2) != 0) {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = true;
                } else {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = false;
                }
                if (cursorQuery.getInt(i3) != 0) {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = true;
                } else {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = false;
                }
                if (cursorQuery.getInt(i4) != 0) {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = true;
                } else {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = false;
                }
                long j8 = cursorQuery.getLong(i5);
                columnIndexOrThrow25 = i5;
                int i22 = columnIndexOrThrow26;
                long j9 = cursorQuery.getLong(i22);
                columnIndexOrThrow26 = i22;
                int i23 = columnIndexOrThrow27;
                byte[] blob = cursorQuery.isNull(i23) ? null : cursorQuery.getBlob(i23);
                WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                Constraints constraints = new Constraints(networkTypeIntToNetworkType, z2, z3, z4, z5, j8, j9, WorkTypeConverters.byteArrayToSetOfTriggers(blob));
                columnIndexOrThrow27 = i23;
                arrayList.add(new WorkSpec(string, stateIntToState, string2, string3, dataFromByteArray, dataFromByteArray2, j, j2, j3, constraints, i8, backoffPolicyIntToBackoffPolicy, j4, j5, j6, j7, z, outOfQuotaPolicyIntToOutOfQuotaPolicy, i16, i18));
                columnIndexOrThrow = i11;
                i6 = i10;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    @Override // androidx.work.impl.model.WorkSpecDao
    public List<WorkSpec> getRecentlyCompletedWork(final long startingAt) throws Throwable {
        RoomSQLiteQuery roomSQLiteQuery;
        int columnIndexOrThrow;
        int columnIndexOrThrow2;
        int columnIndexOrThrow3;
        int columnIndexOrThrow4;
        int columnIndexOrThrow5;
        int columnIndexOrThrow6;
        int columnIndexOrThrow7;
        int columnIndexOrThrow8;
        int columnIndexOrThrow9;
        int columnIndexOrThrow10;
        int columnIndexOrThrow11;
        int columnIndexOrThrow12;
        int columnIndexOrThrow13;
        int columnIndexOrThrow14;
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        int i4;
        boolean z4;
        int i5;
        boolean z5;
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM workspec WHERE last_enqueue_time >= ? AND state IN (2, 3, 5) ORDER BY last_enqueue_time DESC", 1);
        roomSQLiteQueryAcquire.bindLong(1, startingAt);
        this.__db.assertNotSuspendingTransaction();
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "state");
            columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "worker_class_name");
            columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input_merger_class_name");
            columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "input");
            columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "output");
            columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "initial_delay");
            columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "interval_duration");
            columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "flex_duration");
            columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_attempt_count");
            columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_policy");
            columnIndexOrThrow12 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "backoff_delay_duration");
            columnIndexOrThrow13 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "last_enqueue_time");
            columnIndexOrThrow14 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "minimum_retention_duration");
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        } catch (Throwable th) {
            th = th;
            roomSQLiteQuery = roomSQLiteQueryAcquire;
        }
        try {
            int columnIndexOrThrow15 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "schedule_requested_at");
            int columnIndexOrThrow16 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "run_in_foreground");
            int columnIndexOrThrow17 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "out_of_quota_policy");
            int columnIndexOrThrow18 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "period_count");
            int columnIndexOrThrow19 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "generation");
            int columnIndexOrThrow20 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "required_network_type");
            int columnIndexOrThrow21 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_charging");
            int columnIndexOrThrow22 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_device_idle");
            int columnIndexOrThrow23 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_battery_not_low");
            int columnIndexOrThrow24 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "requires_storage_not_low");
            int columnIndexOrThrow25 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_content_update_delay");
            int columnIndexOrThrow26 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "trigger_max_content_delay");
            int columnIndexOrThrow27 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "content_uri_triggers");
            int i6 = columnIndexOrThrow14;
            ArrayList arrayList = new ArrayList(cursorQuery.getCount());
            while (cursorQuery.moveToNext()) {
                String string = cursorQuery.isNull(columnIndexOrThrow) ? null : cursorQuery.getString(columnIndexOrThrow);
                int i7 = cursorQuery.getInt(columnIndexOrThrow2);
                WorkTypeConverters workTypeConverters = WorkTypeConverters.INSTANCE;
                WorkInfo.State stateIntToState = WorkTypeConverters.intToState(i7);
                String string2 = cursorQuery.isNull(columnIndexOrThrow3) ? null : cursorQuery.getString(columnIndexOrThrow3);
                String string3 = cursorQuery.isNull(columnIndexOrThrow4) ? null : cursorQuery.getString(columnIndexOrThrow4);
                Data dataFromByteArray = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow5) ? null : cursorQuery.getBlob(columnIndexOrThrow5));
                Data dataFromByteArray2 = Data.fromByteArray(cursorQuery.isNull(columnIndexOrThrow6) ? null : cursorQuery.getBlob(columnIndexOrThrow6));
                long j = cursorQuery.getLong(columnIndexOrThrow7);
                long j2 = cursorQuery.getLong(columnIndexOrThrow8);
                long j3 = cursorQuery.getLong(columnIndexOrThrow9);
                int i8 = cursorQuery.getInt(columnIndexOrThrow10);
                int i9 = cursorQuery.getInt(columnIndexOrThrow11);
                WorkTypeConverters workTypeConverters2 = WorkTypeConverters.INSTANCE;
                BackoffPolicy backoffPolicyIntToBackoffPolicy = WorkTypeConverters.intToBackoffPolicy(i9);
                long j4 = cursorQuery.getLong(columnIndexOrThrow12);
                long j5 = cursorQuery.getLong(columnIndexOrThrow13);
                int i10 = i6;
                long j6 = cursorQuery.getLong(i10);
                int i11 = columnIndexOrThrow;
                int i12 = columnIndexOrThrow15;
                long j7 = cursorQuery.getLong(i12);
                columnIndexOrThrow15 = i12;
                int i13 = columnIndexOrThrow16;
                if (cursorQuery.getInt(i13) != 0) {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = true;
                } else {
                    columnIndexOrThrow16 = i13;
                    i = columnIndexOrThrow17;
                    z = false;
                }
                int i14 = cursorQuery.getInt(i);
                WorkTypeConverters workTypeConverters3 = WorkTypeConverters.INSTANCE;
                OutOfQuotaPolicy outOfQuotaPolicyIntToOutOfQuotaPolicy = WorkTypeConverters.intToOutOfQuotaPolicy(i14);
                columnIndexOrThrow17 = i;
                int i15 = columnIndexOrThrow18;
                int i16 = cursorQuery.getInt(i15);
                columnIndexOrThrow18 = i15;
                int i17 = columnIndexOrThrow19;
                int i18 = cursorQuery.getInt(i17);
                columnIndexOrThrow19 = i17;
                int i19 = columnIndexOrThrow20;
                int i20 = cursorQuery.getInt(i19);
                WorkTypeConverters workTypeConverters4 = WorkTypeConverters.INSTANCE;
                NetworkType networkTypeIntToNetworkType = WorkTypeConverters.intToNetworkType(i20);
                columnIndexOrThrow20 = i19;
                int i21 = columnIndexOrThrow21;
                if (cursorQuery.getInt(i21) != 0) {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = true;
                } else {
                    columnIndexOrThrow21 = i21;
                    i2 = columnIndexOrThrow22;
                    z2 = false;
                }
                if (cursorQuery.getInt(i2) != 0) {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = true;
                } else {
                    columnIndexOrThrow22 = i2;
                    i3 = columnIndexOrThrow23;
                    z3 = false;
                }
                if (cursorQuery.getInt(i3) != 0) {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = true;
                } else {
                    columnIndexOrThrow23 = i3;
                    i4 = columnIndexOrThrow24;
                    z4 = false;
                }
                if (cursorQuery.getInt(i4) != 0) {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = true;
                } else {
                    columnIndexOrThrow24 = i4;
                    i5 = columnIndexOrThrow25;
                    z5 = false;
                }
                long j8 = cursorQuery.getLong(i5);
                columnIndexOrThrow25 = i5;
                int i22 = columnIndexOrThrow26;
                long j9 = cursorQuery.getLong(i22);
                columnIndexOrThrow26 = i22;
                int i23 = columnIndexOrThrow27;
                byte[] blob = cursorQuery.isNull(i23) ? null : cursorQuery.getBlob(i23);
                WorkTypeConverters workTypeConverters5 = WorkTypeConverters.INSTANCE;
                Constraints constraints = new Constraints(networkTypeIntToNetworkType, z2, z3, z4, z5, j8, j9, WorkTypeConverters.byteArrayToSetOfTriggers(blob));
                columnIndexOrThrow27 = i23;
                arrayList.add(new WorkSpec(string, stateIntToState, string2, string3, dataFromByteArray, dataFromByteArray2, j, j2, j3, constraints, i8, backoffPolicyIntToBackoffPolicy, j4, j5, j6, j7, z, outOfQuotaPolicyIntToOutOfQuotaPolicy, i16, i18));
                columnIndexOrThrow = i11;
                i6 = i10;
            }
            cursorQuery.close();
            roomSQLiteQuery.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            cursorQuery.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void __fetchRelationshipWorkTagAsjavaLangString(final ArrayMap<String, ArrayList<String>> _map) {
        Set<String> setKeySet = _map.keySet();
        if (setKeySet.isEmpty()) {
            return;
        }
        if (_map.size() > 999) {
            ArrayMap<String, ArrayList<String>> arrayMap = new ArrayMap<>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
            int size = _map.size();
            int i = 0;
            int i2 = 0;
            while (i < size) {
                arrayMap.put(_map.keyAt(i), _map.valueAt(i));
                i++;
                i2++;
                if (i2 == 999) {
                    __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                    arrayMap = new ArrayMap<>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
                    i2 = 0;
                }
            }
            if (i2 > 0) {
                __fetchRelationshipWorkTagAsjavaLangString(arrayMap);
                return;
            }
            return;
        }
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("SELECT `tag`,`work_spec_id` FROM `WorkTag` WHERE `work_spec_id` IN (");
        int size2 = setKeySet.size();
        StringUtil.appendPlaceholders(sbNewStringBuilder, size2);
        sbNewStringBuilder.append(")");
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire(sbNewStringBuilder.toString(), size2 + 0);
        int i3 = 1;
        for (String str : setKeySet) {
            if (str == null) {
                roomSQLiteQueryAcquire.bindNull(i3);
            } else {
                roomSQLiteQueryAcquire.bindString(i3, str);
            }
            i3++;
        }
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndex = CursorUtil.getColumnIndex(cursorQuery, "work_spec_id");
            if (columnIndex == -1) {
                return;
            }
            while (cursorQuery.moveToNext()) {
                ArrayList<String> arrayList = _map.get(cursorQuery.getString(columnIndex));
                if (arrayList != null) {
                    arrayList.add(cursorQuery.isNull(0) ? null : cursorQuery.getString(0));
                }
            }
        } finally {
            cursorQuery.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void __fetchRelationshipWorkProgressAsandroidxWorkData(final ArrayMap<String, ArrayList<Data>> _map) {
        Set<String> setKeySet = _map.keySet();
        if (setKeySet.isEmpty()) {
            return;
        }
        if (_map.size() > 999) {
            ArrayMap<String, ArrayList<Data>> arrayMap = new ArrayMap<>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
            int size = _map.size();
            int i = 0;
            int i2 = 0;
            while (i < size) {
                arrayMap.put(_map.keyAt(i), _map.valueAt(i));
                i++;
                i2++;
                if (i2 == 999) {
                    __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap);
                    arrayMap = new ArrayMap<>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
                    i2 = 0;
                }
            }
            if (i2 > 0) {
                __fetchRelationshipWorkProgressAsandroidxWorkData(arrayMap);
                return;
            }
            return;
        }
        StringBuilder sbNewStringBuilder = StringUtil.newStringBuilder();
        sbNewStringBuilder.append("SELECT `progress`,`work_spec_id` FROM `WorkProgress` WHERE `work_spec_id` IN (");
        int size2 = setKeySet.size();
        StringUtil.appendPlaceholders(sbNewStringBuilder, size2);
        sbNewStringBuilder.append(")");
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire(sbNewStringBuilder.toString(), size2 + 0);
        int i3 = 1;
        for (String str : setKeySet) {
            if (str == null) {
                roomSQLiteQueryAcquire.bindNull(i3);
            } else {
                roomSQLiteQueryAcquire.bindString(i3, str);
            }
            i3++;
        }
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndex = CursorUtil.getColumnIndex(cursorQuery, "work_spec_id");
            if (columnIndex == -1) {
                return;
            }
            while (cursorQuery.moveToNext()) {
                ArrayList<Data> arrayList = _map.get(cursorQuery.getString(columnIndex));
                if (arrayList != null) {
                    arrayList.add(Data.fromByteArray(cursorQuery.isNull(0) ? null : cursorQuery.getBlob(0)));
                }
            }
        } finally {
            cursorQuery.close();
        }
    }
}
