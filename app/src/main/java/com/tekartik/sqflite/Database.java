package com.tekartik.sqflite;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Build;
import android.util.Log;
import com.tekartik.sqflite.operation.BatchOperation;
import com.tekartik.sqflite.operation.MethodCallOperation;
import com.tekartik.sqflite.operation.Operation;
import com.tekartik.sqflite.operation.QueuedOperation;
import com.tekartik.sqflite.operation.SqlErrorInfo;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
class Database {
    static final boolean WAL_ENABLED_BY_DEFAULT = false;
    private static final String WAL_ENABLED_META_NAME = "com.tekartik.sqflite.wal_enabled";
    private static Boolean walGloballyEnabled;
    final Context context;
    private Integer currentTransactionId;
    public DatabaseWorkerPool databaseWorkerPool;
    final int id;
    final int logLevel;
    final String path;
    final boolean singleInstance;
    SQLiteDatabase sqliteDatabase;
    final List<QueuedOperation> noTransactionOperationQueue = new ArrayList();
    final Map<Integer, SqfliteCursor> cursors = new HashMap();
    private int transactionDepth = 0;
    private int lastTransactionId = 0;
    private int lastCursorId = 0;

    Database(Context context, String str, int i, boolean z, int i2) {
        this.context = context;
        this.path = str;
        this.singleInstance = z;
        this.id = i;
        this.logLevel = i2;
    }

    protected static boolean checkWalEnabled(Context context) {
        return checkMetaBoolean(context, WAL_ENABLED_META_NAME, false);
    }

    static ApplicationInfo getApplicationInfoWithMeta32(Context context, String str, int i) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(str, i);
    }

    protected static boolean checkMetaBoolean(Context context, String str, boolean z) {
        ApplicationInfo applicationInfoWithMeta32;
        try {
            String packageName = context.getPackageName();
            if (Build.VERSION.SDK_INT >= 33) {
                applicationInfoWithMeta32 = context.getPackageManager().getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(128L));
            } else {
                applicationInfoWithMeta32 = getApplicationInfoWithMeta32(context, packageName, 128);
            }
            return applicationInfoWithMeta32.metaData.getBoolean(str, z);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static void deleteDatabase(String str) {
        SQLiteDatabase.deleteDatabase(new File(str));
    }

    public static boolean existsDatabase(String str) {
        try {
            return new File(str).exists();
        } catch (Exception unused) {
            return false;
        }
    }

    public void open() {
        if (walGloballyEnabled == null) {
            Boolean boolValueOf = Boolean.valueOf(checkWalEnabled(this.context));
            walGloballyEnabled = boolValueOf;
            if (boolValueOf.booleanValue() && LogLevel.hasVerboseLevel(this.logLevel)) {
                Log.d(Constant.TAG, getThreadLogPrefix() + "[sqflite] WAL enabled");
            }
        }
        this.sqliteDatabase = SQLiteDatabase.openDatabase(this.path, null, walGloballyEnabled.booleanValue() ? 805306368 : 268435456);
    }

    public void openReadOnly() {
        this.sqliteDatabase = SQLiteDatabase.openDatabase(this.path, null, 1, new DatabaseErrorHandler() { // from class: com.tekartik.sqflite.Database.1
            @Override // android.database.DatabaseErrorHandler
            public void onCorruption(SQLiteDatabase sQLiteDatabase) {
            }
        });
    }

    public void close() {
        if (!this.cursors.isEmpty() && LogLevel.hasSqlLevel(this.logLevel)) {
            Log.d(Constant.TAG, getThreadLogPrefix() + this.cursors.size() + " cursor(s) are left opened");
        }
        this.sqliteDatabase.close();
    }

    public SQLiteDatabase getWritableDatabase() {
        return this.sqliteDatabase;
    }

    public SQLiteDatabase getReadableDatabase() {
        return this.sqliteDatabase;
    }

    public boolean enableWriteAheadLogging() {
        try {
            return this.sqliteDatabase.enableWriteAheadLogging();
        } catch (Exception e) {
            Log.e(Constant.TAG, getThreadLogPrefix() + "enable WAL error: " + e);
            return false;
        }
    }

    String getThreadLogTag() {
        Thread threadCurrentThread = Thread.currentThread();
        return this.id + "," + threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
    }

    String getThreadLogPrefix() {
        return "[" + getThreadLogTag() + "] ";
    }

    private Map<String, Object> cursorToResults(Cursor cursor, Integer num) {
        HashMap map = null;
        int columnCount = 0;
        ArrayList arrayList = null;
        while (cursor.moveToNext()) {
            if (map == null) {
                ArrayList arrayList2 = new ArrayList();
                HashMap map2 = new HashMap();
                columnCount = cursor.getColumnCount();
                map2.put(Constant.PARAM_COLUMNS, Arrays.asList(cursor.getColumnNames()));
                map2.put(Constant.PARAM_ROWS, arrayList2);
                arrayList = arrayList2;
                map = map2;
            }
            arrayList.add(Utils.cursorRowToList(cursor, columnCount));
            if (num != null && arrayList.size() >= num.intValue()) {
                break;
            }
        }
        return map == null ? new HashMap() : map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runQueuedOperations() {
        while (!this.noTransactionOperationQueue.isEmpty() && this.currentTransactionId == null) {
            this.noTransactionOperationQueue.get(0).run();
            this.noTransactionOperationQueue.remove(0);
        }
    }

    private void wrapSqlOperationHandler(Operation operation, Runnable runnable) {
        Integer transactionId = operation.getTransactionId();
        Integer num = this.currentTransactionId;
        if (num == null) {
            runnable.run();
            return;
        }
        if (transactionId != null && (transactionId.equals(num) || transactionId.intValue() == -1)) {
            runnable.run();
            if (this.currentTransactionId != null || this.noTransactionOperationQueue.isEmpty()) {
                return;
            }
            this.databaseWorkerPool.post(this, new Runnable() { // from class: com.tekartik.sqflite.Database$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.runQueuedOperations();
                }
            });
            return;
        }
        this.noTransactionOperationQueue.add(new QueuedOperation(operation, runnable));
    }

    public void query(final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.Database$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.m644lambda$query$0$comtekartiksqfliteDatabase(operation);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doQuery, reason: merged with bridge method [inline-methods] */
    public boolean m644lambda$query$0$comtekartiksqfliteDatabase(Operation operation) throws Throwable {
        Cursor cursorRawQueryWithFactory;
        Integer num = (Integer) operation.getArgument(Constant.PARAM_CURSOR_PAGE_SIZE);
        final SqlCommand sqlCommand = operation.getSqlCommand();
        if (LogLevel.hasSqlLevel(this.logLevel)) {
            Log.d(Constant.TAG, getThreadLogPrefix() + sqlCommand);
        }
        SqfliteCursor sqfliteCursor = null;
        try {
            cursorRawQueryWithFactory = getReadableDatabase().rawQueryWithFactory(new SQLiteDatabase.CursorFactory() { // from class: com.tekartik.sqflite.Database$$ExternalSyntheticLambda5
                @Override // android.database.sqlite.SQLiteDatabase.CursorFactory
                public final Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
                    return Database.lambda$doQuery$1(sqlCommand, sQLiteDatabase, sQLiteCursorDriver, str, sQLiteQuery);
                }
            }, sqlCommand.getSql(), Constant.EMPTY_STRING_ARRAY, null);
            try {
                try {
                    Map<String, Object> mapCursorToResults = cursorToResults(cursorRawQueryWithFactory, num);
                    if ((num == null || cursorRawQueryWithFactory.isLast() || cursorRawQueryWithFactory.isAfterLast()) ? false : true) {
                        int i = this.lastCursorId + 1;
                        this.lastCursorId = i;
                        mapCursorToResults.put(Constant.PARAM_CURSOR_ID, Integer.valueOf(i));
                        SqfliteCursor sqfliteCursor2 = new SqfliteCursor(i, num.intValue(), cursorRawQueryWithFactory);
                        try {
                            this.cursors.put(Integer.valueOf(i), sqfliteCursor2);
                            sqfliteCursor = sqfliteCursor2;
                        } catch (Exception e) {
                            e = e;
                            sqfliteCursor = sqfliteCursor2;
                            handleException(e, operation);
                            if (sqfliteCursor != null) {
                                closeCursor(sqfliteCursor);
                            }
                            if (sqfliteCursor == null && cursorRawQueryWithFactory != null) {
                                cursorRawQueryWithFactory.close();
                            }
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            sqfliteCursor = sqfliteCursor2;
                            if (sqfliteCursor == null && cursorRawQueryWithFactory != null) {
                                cursorRawQueryWithFactory.close();
                            }
                            throw th;
                        }
                    }
                    operation.success(mapCursorToResults);
                    if (sqfliteCursor == null && cursorRawQueryWithFactory != null) {
                        cursorRawQueryWithFactory.close();
                    }
                    return true;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            cursorRawQueryWithFactory = null;
        } catch (Throwable th3) {
            th = th3;
            cursorRawQueryWithFactory = null;
        }
    }

    static /* synthetic */ Cursor lambda$doQuery$1(SqlCommand sqlCommand, SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
        sqlCommand.bindTo(sQLiteQuery);
        return new SQLiteCursor(sQLiteCursorDriver, str, sQLiteQuery);
    }

    public void queryCursorNext(final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.Database$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.m645lambda$queryCursorNext$2$comtekartiksqfliteDatabase(operation);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doQueryCursorNext, reason: merged with bridge method [inline-methods] */
    public boolean m645lambda$queryCursorNext$2$comtekartiksqfliteDatabase(Operation operation) throws Throwable {
        boolean z;
        int iIntValue = ((Integer) operation.getArgument(Constant.PARAM_CURSOR_ID)).intValue();
        boolean zEquals = Boolean.TRUE.equals(operation.getArgument(Constant.PARAM_CANCEL));
        if (LogLevel.hasVerboseLevel(this.logLevel)) {
            Log.d(Constant.TAG, getThreadLogPrefix() + "cursor " + iIntValue + (zEquals ? " cancel" : " next"));
        }
        SqfliteCursor sqfliteCursor = null;
        if (zEquals) {
            closeCursor(iIntValue);
            operation.success(null);
            return true;
        }
        SqfliteCursor sqfliteCursor2 = this.cursors.get(Integer.valueOf(iIntValue));
        boolean z2 = false;
        try {
            if (sqfliteCursor2 == null) {
                throw new IllegalStateException("Cursor " + iIntValue + " not found");
            }
            Cursor cursor = sqfliteCursor2.cursor;
            Map<String, Object> mapCursorToResults = cursorToResults(cursor, Integer.valueOf(sqfliteCursor2.pageSize));
            boolean z3 = (cursor.isLast() || cursor.isAfterLast()) ? false : true;
            if (z3) {
                try {
                    mapCursorToResults.put(Constant.PARAM_CURSOR_ID, Integer.valueOf(iIntValue));
                } catch (Exception e) {
                    z = z3;
                    e = e;
                    try {
                        handleException(e, operation);
                        if (sqfliteCursor2 != null) {
                            closeCursor(sqfliteCursor2);
                        } else {
                            sqfliteCursor = sqfliteCursor2;
                        }
                        if (!z && sqfliteCursor != null) {
                            closeCursor(sqfliteCursor);
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        z2 = z;
                        if (!z2 && sqfliteCursor2 != null) {
                            closeCursor(sqfliteCursor2);
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z2 = z3;
                    if (!z2) {
                        closeCursor(sqfliteCursor2);
                    }
                    throw th;
                }
            }
            operation.success(mapCursorToResults);
            if (!z3 && sqfliteCursor2 != null) {
                closeCursor(sqfliteCursor2);
            }
            return true;
        } catch (Exception e2) {
            e = e2;
            z = false;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private void closeCursor(SqfliteCursor sqfliteCursor) {
        try {
            int i = sqfliteCursor.cursorId;
            if (LogLevel.hasVerboseLevel(this.logLevel)) {
                Log.d(Constant.TAG, getThreadLogPrefix() + "closing cursor " + i);
            }
            this.cursors.remove(Integer.valueOf(i));
            sqfliteCursor.cursor.close();
        } catch (Exception unused) {
        }
    }

    private void closeCursor(int i) {
        SqfliteCursor sqfliteCursor = this.cursors.get(Integer.valueOf(i));
        if (sqfliteCursor != null) {
            closeCursor(sqfliteCursor);
        }
    }

    void handleException(Exception exc, Operation operation) {
        if (exc instanceof SQLiteCantOpenDatabaseException) {
            operation.error("sqlite_error", "open_failed " + this.path, null);
        } else if (exc instanceof SQLException) {
            operation.error("sqlite_error", exc.getMessage(), SqlErrorInfo.getMap(operation));
        } else {
            operation.error("sqlite_error", exc.getMessage(), SqlErrorInfo.getMap(operation));
        }
    }

    private boolean executeOrError(Operation operation) {
        SqlCommand sqlCommand = operation.getSqlCommand();
        if (LogLevel.hasSqlLevel(this.logLevel)) {
            Log.d(Constant.TAG, getThreadLogPrefix() + sqlCommand);
        }
        Boolean inTransactionChange = operation.getInTransactionChange();
        try {
            getWritableDatabase().execSQL(sqlCommand.getSql(), sqlCommand.getSqlArguments());
            enterOrLeaveInTransaction(inTransactionChange);
            return true;
        } catch (Exception e) {
            handleException(e, operation);
            return false;
        }
    }

    public void execute(final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.Database$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m642lambda$execute$3$comtekartiksqfliteDatabase(operation);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$execute$3$com-tekartik-sqflite-Database, reason: not valid java name */
    /* synthetic */ void m642lambda$execute$3$comtekartiksqfliteDatabase(Operation operation) {
        Boolean inTransactionChange = operation.getInTransactionChange();
        boolean z = Boolean.TRUE.equals(inTransactionChange) && operation.hasNullTransactionId();
        if (z) {
            int i = this.lastTransactionId + 1;
            this.lastTransactionId = i;
            this.currentTransactionId = Integer.valueOf(i);
        }
        if (!executeOrError(operation)) {
            if (z) {
                this.currentTransactionId = null;
            }
        } else if (z) {
            HashMap map = new HashMap();
            map.put(Constant.PARAM_TRANSACTION_ID, this.currentTransactionId);
            operation.success(map);
        } else {
            if (Boolean.FALSE.equals(inTransactionChange)) {
                this.currentTransactionId = null;
            }
            operation.success(null);
        }
    }

    private boolean doExecute(Operation operation) {
        if (!executeOrError(operation)) {
            return false;
        }
        operation.success(null);
        return true;
    }

    public void insert(final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.Database$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.m643lambda$insert$4$comtekartiksqfliteDatabase(operation);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00dc  */
    /* JADX INFO: renamed from: doInsert, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean m643lambda$insert$4$comtekartiksqfliteDatabase(com.tekartik.sqflite.operation.Operation r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tekartik.sqflite.Database.m643lambda$insert$4$comtekartiksqfliteDatabase(com.tekartik.sqflite.operation.Operation):boolean");
    }

    public void update(final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.Database$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.m646lambda$update$5$comtekartiksqfliteDatabase(operation);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doUpdate, reason: merged with bridge method [inline-methods] */
    public boolean m646lambda$update$5$comtekartiksqfliteDatabase(Operation operation) throws Throwable {
        if (!executeOrError(operation)) {
            return false;
        }
        Cursor cursor = null;
        if (operation.getNoResult()) {
            operation.success(null);
            return true;
        }
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery("SELECT changes()", null);
                if (cursorRawQuery != null) {
                    try {
                        if (cursorRawQuery.getCount() > 0 && cursorRawQuery.moveToFirst()) {
                            int i = cursorRawQuery.getInt(0);
                            if (LogLevel.hasSqlLevel(this.logLevel)) {
                                Log.d(Constant.TAG, getThreadLogPrefix() + "changed " + i);
                            }
                            operation.success(Integer.valueOf(i));
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                            return true;
                        }
                    } catch (Exception e) {
                        e = e;
                        cursor = cursorRawQuery;
                        handleException(e, operation);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorRawQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                Log.e(Constant.TAG, getThreadLogPrefix() + "fail to read changes for Update/Delete");
                operation.success(null);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    void batch(MethodCall methodCall, MethodChannel.Result result) {
        BatchOperation batchOperation;
        String method;
        MethodCallOperation methodCallOperation = new MethodCallOperation(methodCall, result);
        boolean noResult = methodCallOperation.getNoResult();
        boolean continueOnError = methodCallOperation.getContinueOnError();
        List list = (List) methodCallOperation.getArgument("operations");
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            batchOperation = new BatchOperation((Map) it.next(), noResult);
            method = batchOperation.getMethod();
            method.hashCode();
            switch (method) {
                case "execute":
                    if (!doExecute(batchOperation)) {
                        if (continueOnError) {
                            batchOperation.handleErrorContinue(arrayList);
                        } else {
                            batchOperation.handleError(result);
                            return;
                        }
                        break;
                    } else {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    }
                    break;
                case "insert":
                    if (!m643lambda$insert$4$comtekartiksqfliteDatabase(batchOperation)) {
                        if (continueOnError) {
                            batchOperation.handleErrorContinue(arrayList);
                        } else {
                            batchOperation.handleError(result);
                            return;
                        }
                        break;
                    } else {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    }
                    break;
                case "update":
                    if (!m646lambda$update$5$comtekartiksqfliteDatabase(batchOperation)) {
                        if (continueOnError) {
                            batchOperation.handleErrorContinue(arrayList);
                        } else {
                            batchOperation.handleError(result);
                            return;
                        }
                        break;
                    } else {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    }
                    break;
                case "query":
                    if (!m644lambda$query$0$comtekartiksqfliteDatabase(batchOperation)) {
                        if (continueOnError) {
                            batchOperation.handleErrorContinue(arrayList);
                        } else {
                            batchOperation.handleError(result);
                            return;
                        }
                        break;
                    } else {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    }
                    break;
                default:
                    result.error("bad_param", "Batch method '" + method + "' not supported", null);
                    return;
            }
        }
        if (noResult) {
            result.success(null);
        } else {
            result.success(arrayList);
        }
    }

    synchronized boolean isInTransaction() {
        return this.transactionDepth > 0;
    }

    synchronized void enterOrLeaveInTransaction(Boolean bool) {
        if (Boolean.TRUE.equals(bool)) {
            this.transactionDepth++;
        } else if (Boolean.FALSE.equals(bool)) {
            this.transactionDepth--;
        }
    }
}
