package com.tekartik.sqflite;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.tekartik.sqflite.dev.Debug;
import com.tekartik.sqflite.operation.MethodCallOperation;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SqflitePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private static DatabaseWorkerPool databaseWorkerPool;
    static String databasesPath;
    private Context context;
    private MethodChannel methodChannel;
    static final Map<String, Integer> _singleInstancesByPath = new HashMap();
    static final Map<Integer, Database> databaseMap = new HashMap();
    private static final Object databaseMapLocker = new Object();
    private static final Object openCloseLocker = new Object();
    static int logLevel = 0;
    private static int THREAD_PRIORITY = 0;
    private static int THREAD_COUNT = 1;
    private static int databaseId = 0;

    public SqflitePlugin() {
    }

    public SqflitePlugin(Context context) {
        this.context = context.getApplicationContext();
    }

    private static Map<String, Object> fixMap(Map<Object, Object> map) {
        Object string;
        HashMap map2 = new HashMap();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                string = fixMap((Map) value);
            } else {
                string = toString(value);
            }
            map2.put(toString(entry.getKey()), string);
        }
        return map2;
    }

    private static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            ArrayList arrayList = new ArrayList();
            for (byte b : (byte[]) obj) {
                arrayList.add(Integer.valueOf(b));
            }
            return arrayList.toString();
        }
        if (obj instanceof Map) {
            return fixMap((Map) obj).toString();
        }
        return obj.toString();
    }

    static boolean isInMemoryPath(String str) {
        return str == null || str.equals(":memory:");
    }

    static Map makeOpenResult(int i, boolean z, boolean z2) {
        HashMap map = new HashMap();
        map.put("id", Integer.valueOf(i));
        if (z) {
            map.put(Constant.PARAM_RECOVERED, true);
        }
        if (z2) {
            map.put(Constant.PARAM_RECOVERED_IN_TRANSACTION, true);
        }
        return map;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    }

    private void onAttachedToEngine(Context context, BinaryMessenger binaryMessenger) {
        this.context = context;
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "com.tekartik.sqflite", StandardMethodCodec.INSTANCE, binaryMessenger.makeBackgroundTaskQueue());
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.context = null;
        this.methodChannel.setMethodCallHandler(null);
        this.methodChannel = null;
    }

    private Context getContext() {
        return this.context;
    }

    private Database getDatabase(int i) {
        return databaseMap.get(Integer.valueOf(i));
    }

    private Database getDatabaseOrError(MethodCall methodCall, MethodChannel.Result result) {
        int iIntValue = ((Integer) methodCall.argument("id")).intValue();
        Database database = getDatabase(iIntValue);
        if (database != null) {
            return database;
        }
        result.error("sqlite_error", "database_closed " + iIntValue, null);
        return null;
    }

    private void onQueryCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                databaseOrError.query(new MethodCallOperation(methodCall, result));
            }
        });
    }

    private void onQueryCursorNextCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                databaseOrError.queryCursorNext(new MethodCallOperation(methodCall, result));
            }
        });
    }

    private void onBatchCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                databaseOrError.batch(methodCall, result);
            }
        });
    }

    private void onInsertCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                databaseOrError.insert(new MethodCallOperation(methodCall, result));
            }
        });
    }

    private void onExecuteCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                databaseOrError.execute(new MethodCallOperation(methodCall, result));
            }
        });
    }

    private void onSetLocaleCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SqflitePlugin.lambda$onSetLocaleCall$5(methodCall, databaseOrError, result);
            }
        });
    }

    static /* synthetic */ void lambda$onSetLocaleCall$5(MethodCall methodCall, Database database, MethodChannel.Result result) {
        try {
            database.sqliteDatabase.setLocale(Utils.localeForLanguateTag((String) methodCall.argument("locale")));
            result.success(null);
        } catch (Exception e) {
            result.error("sqlite_error", "Error calling setLocale: " + e.getMessage(), null);
        }
    }

    private void onUpdateCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                databaseOrError.update(new MethodCallOperation(methodCall, result));
            }
        });
    }

    private void onDebugCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = (String) methodCall.argument("cmd");
        HashMap map = new HashMap();
        if ("get".equals(str)) {
            int i = logLevel;
            if (i > 0) {
                map.put("logLevel", Integer.valueOf(i));
            }
            Map<Integer, Database> map2 = databaseMap;
            if (!map2.isEmpty()) {
                HashMap map3 = new HashMap();
                for (Map.Entry<Integer, Database> entry : map2.entrySet()) {
                    Database value = entry.getValue();
                    HashMap map4 = new HashMap();
                    map4.put("path", value.path);
                    map4.put("singleInstance", Boolean.valueOf(value.singleInstance));
                    if (value.logLevel > 0) {
                        map4.put("logLevel", Integer.valueOf(value.logLevel));
                    }
                    map3.put(entry.getKey().toString(), map4);
                }
                map.put("databases", map3);
            }
        }
        result.success(map);
    }

    private void onDebugModeCall(MethodCall methodCall, MethodChannel.Result result) {
        Debug.LOGV = Boolean.TRUE.equals(methodCall.arguments());
        Debug.EXTRA_LOGV = Debug._EXTRA_LOGV && Debug.LOGV;
        if (Debug.LOGV) {
            if (Debug.EXTRA_LOGV) {
                logLevel = 2;
            } else if (Debug.LOGV) {
                logLevel = 1;
            }
        } else {
            logLevel = 0;
        }
        result.success(null);
    }

    private void onOpenDatabaseCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final int i;
        Database database;
        final String str = (String) methodCall.argument("path");
        final Boolean bool = (Boolean) methodCall.argument("readOnly");
        final boolean zIsInMemoryPath = isInMemoryPath(str);
        boolean z = (Boolean.FALSE.equals(methodCall.argument("singleInstance")) || zIsInMemoryPath) ? false : true;
        if (z) {
            synchronized (databaseMapLocker) {
                if (LogLevel.hasVerboseLevel(logLevel)) {
                    Log.d(Constant.TAG, "Look for " + str + " in " + _singleInstancesByPath.keySet());
                }
                Integer num = _singleInstancesByPath.get(str);
                if (num != null && (database = databaseMap.get(num)) != null) {
                    if (!database.sqliteDatabase.isOpen()) {
                        if (LogLevel.hasVerboseLevel(logLevel)) {
                            Log.d(Constant.TAG, database.getThreadLogPrefix() + "single instance database of " + str + " not opened");
                        }
                    } else {
                        if (LogLevel.hasVerboseLevel(logLevel)) {
                            Log.d(Constant.TAG, database.getThreadLogPrefix() + "re-opened single instance " + (database.isInTransaction() ? "(in transaction) " : "") + num + StringUtils.SPACE + str);
                        }
                        result.success(makeOpenResult(num.intValue(), true, database.isInTransaction()));
                        return;
                    }
                }
            }
        }
        Object obj = databaseMapLocker;
        synchronized (obj) {
            i = databaseId + 1;
            databaseId = i;
        }
        final Database database2 = new Database(this.context, str, i, z, logLevel);
        synchronized (obj) {
            if (databaseWorkerPool == null) {
                DatabaseWorkerPool databaseWorkerPoolCreate = DatabaseWorkerPool.create(Constant.TAG, THREAD_COUNT, THREAD_PRIORITY);
                databaseWorkerPool = databaseWorkerPoolCreate;
                databaseWorkerPoolCreate.start();
                if (LogLevel.hasSqlLevel(database2.logLevel)) {
                    Log.d(Constant.TAG, database2.getThreadLogPrefix() + "starting worker pool with priority " + THREAD_PRIORITY);
                }
            }
            database2.databaseWorkerPool = databaseWorkerPool;
            if (LogLevel.hasSqlLevel(database2.logLevel)) {
                Log.d(Constant.TAG, database2.getThreadLogPrefix() + "opened " + i + StringUtils.SPACE + str);
            }
            final boolean z2 = z;
            databaseWorkerPool.post(database2, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    SqflitePlugin.lambda$onOpenDatabaseCall$7(zIsInMemoryPath, str, result, bool, database2, methodCall, z2, i);
                }
            });
        }
    }

    static /* synthetic */ void lambda$onOpenDatabaseCall$7(boolean z, String str, MethodChannel.Result result, Boolean bool, Database database, MethodCall methodCall, boolean z2, int i) {
        synchronized (openCloseLocker) {
            if (!z) {
                try {
                    File file = new File(new File(str).getParent());
                    if (!file.exists() && !file.mkdirs() && !file.exists()) {
                        result.error("sqlite_error", "open_failed " + str, null);
                        return;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            try {
                if (Boolean.TRUE.equals(bool)) {
                    database.openReadOnly();
                } else {
                    database.open();
                }
                synchronized (databaseMapLocker) {
                    if (z2) {
                        _singleInstancesByPath.put(str, Integer.valueOf(i));
                        databaseMap.put(Integer.valueOf(i), database);
                    } else {
                        databaseMap.put(Integer.valueOf(i), database);
                    }
                }
                if (LogLevel.hasSqlLevel(database.logLevel)) {
                    Log.d(Constant.TAG, database.getThreadLogPrefix() + "opened " + i + StringUtils.SPACE + str);
                }
                result.success(makeOpenResult(i, false, false));
            } catch (Exception e) {
                database.handleException(e, new MethodCallOperation(methodCall, result));
            }
        }
    }

    private void onCloseDatabaseCall(MethodCall methodCall, final MethodChannel.Result result) {
        int iIntValue = ((Integer) methodCall.argument("id")).intValue();
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        if (LogLevel.hasSqlLevel(databaseOrError.logLevel)) {
            Log.d(Constant.TAG, databaseOrError.getThreadLogPrefix() + "closing " + iIntValue + StringUtils.SPACE + databaseOrError.path);
        }
        String str = databaseOrError.path;
        synchronized (databaseMapLocker) {
            databaseMap.remove(Integer.valueOf(iIntValue));
            if (databaseOrError.singleInstance) {
                _singleInstancesByPath.remove(str);
            }
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SqflitePlugin.openCloseLocker) {
                    SqflitePlugin.this.closeDatabase(databaseOrError);
                }
                result.success(null);
            }
        });
    }

    private void onDeleteDatabaseCall(MethodCall methodCall, final MethodChannel.Result result) {
        final Database database;
        Map<Integer, Database> map;
        final String str = (String) methodCall.argument("path");
        synchronized (databaseMapLocker) {
            if (LogLevel.hasVerboseLevel(logLevel)) {
                Log.d(Constant.TAG, "Look for " + str + " in " + _singleInstancesByPath.keySet());
            }
            Map<String, Integer> map2 = _singleInstancesByPath;
            Integer num = map2.get(str);
            if (num == null || (database = (map = databaseMap).get(num)) == null || !database.sqliteDatabase.isOpen()) {
                database = null;
            } else {
                if (LogLevel.hasVerboseLevel(logLevel)) {
                    Log.d(Constant.TAG, database.getThreadLogPrefix() + "found single instance " + (database.isInTransaction() ? "(in transaction) " : "") + num + StringUtils.SPACE + str);
                }
                map.remove(num);
                map2.remove(str);
            }
        }
        Runnable runnable = new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SqflitePlugin.openCloseLocker) {
                    Database database2 = database;
                    if (database2 != null) {
                        SqflitePlugin.this.closeDatabase(database2);
                    }
                    try {
                        if (LogLevel.hasVerboseLevel(SqflitePlugin.logLevel)) {
                            Log.d(Constant.TAG, "delete database " + str);
                        }
                        Database.deleteDatabase(str);
                    } catch (Exception e) {
                        Log.e(Constant.TAG, "error " + e + " while closing database " + SqflitePlugin.databaseId);
                    }
                }
                result.success(null);
            }
        };
        DatabaseWorkerPool databaseWorkerPool2 = databaseWorkerPool;
        if (databaseWorkerPool2 != null) {
            databaseWorkerPool2.post(database, runnable);
        } else {
            runnable.run();
        }
    }

    private void onDatabaseExistsCall(MethodCall methodCall, MethodChannel.Result result) {
        result.success(Boolean.valueOf(Database.existsDatabase((String) methodCall.argument("path"))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeDatabase(Database database) {
        try {
            if (LogLevel.hasSqlLevel(database.logLevel)) {
                Log.d(Constant.TAG, database.getThreadLogPrefix() + "closing database ");
            }
            database.close();
        } catch (Exception e) {
            Log.e(Constant.TAG, "error " + e + " while closing database " + databaseId);
        }
        synchronized (databaseMapLocker) {
            if (databaseMap.isEmpty() && databaseWorkerPool != null) {
                if (LogLevel.hasSqlLevel(database.logLevel)) {
                    Log.d(Constant.TAG, database.getThreadLogPrefix() + "stopping thread");
                }
                databaseWorkerPool.quit();
                databaseWorkerPool = null;
            }
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "execute":
                onExecuteCall(methodCall, result);
                break;
            case "closeDatabase":
                onCloseDatabaseCall(methodCall, result);
                break;
            case "options":
                onOptionsCall(methodCall, result);
                break;
            case "insert":
                onInsertCall(methodCall, result);
                break;
            case "update":
                onUpdateCall(methodCall, result);
                break;
            case "androidSetLocale":
                onSetLocaleCall(methodCall, result);
                break;
            case "deleteDatabase":
                onDeleteDatabaseCall(methodCall, result);
                break;
            case "debugMode":
                onDebugModeCall(methodCall, result);
                break;
            case "openDatabase":
                onOpenDatabaseCall(methodCall, result);
                break;
            case "batch":
                onBatchCall(methodCall, result);
                break;
            case "debug":
                onDebugCall(methodCall, result);
                break;
            case "query":
                onQueryCall(methodCall, result);
                break;
            case "databaseExists":
                onDatabaseExistsCall(methodCall, result);
                break;
            case "queryCursorNext":
                onQueryCursorNextCall(methodCall, result);
                break;
            case "getPlatformVersion":
                result.success("Android " + Build.VERSION.RELEASE);
                break;
            case "getDatabasesPath":
                onGetDatabasesPathCall(methodCall, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    void onOptionsCall(MethodCall methodCall, MethodChannel.Result result) {
        Object objArgument = methodCall.argument("androidThreadPriority");
        if (objArgument != null) {
            THREAD_PRIORITY = ((Integer) objArgument).intValue();
        }
        Object objArgument2 = methodCall.argument("androidThreadCount");
        if (objArgument2 != null && !objArgument2.equals(Integer.valueOf(THREAD_COUNT))) {
            THREAD_COUNT = ((Integer) objArgument2).intValue();
            DatabaseWorkerPool databaseWorkerPool2 = databaseWorkerPool;
            if (databaseWorkerPool2 != null) {
                databaseWorkerPool2.quit();
                databaseWorkerPool = null;
            }
        }
        Integer logLevel2 = LogLevel.getLogLevel(methodCall);
        if (logLevel2 != null) {
            logLevel = logLevel2.intValue();
        }
        result.success(null);
    }

    void onGetDatabasesPathCall(MethodCall methodCall, MethodChannel.Result result) {
        if (databasesPath == null) {
            databasesPath = this.context.getDatabasePath("tekartik_sqflite.db").getParent();
        }
        result.success(databasesPath);
    }
}
