package com.fluttercandies.photo_manager.core;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.RecoverableSecurityException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import com.fluttercandies.photo_manager.core.utils.IDBUtils;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.fluttercandies.photo_manager.util.ResultHandler;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PhotoManagerDeleteManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u00014B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005J\u0014\u0010\u001f\u001a\u00020\u001e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\r0!J \u0010\"\u001a\u00020\u001e2\u000e\u0010#\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100!2\u0006\u0010$\u001a\u00020\nH\u0007J8\u0010%\u001a\u00020\u001e2&\u0010#\u001a\"\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u00100&j\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u0010`'2\u0006\u0010$\u001a\u00020\nH\u0007J\u0012\u0010(\u001a\u0004\u0018\u00010\r2\u0006\u0010)\u001a\u00020\u0010H\u0002J\u0010\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\bH\u0002J \u0010,\u001a\u00020\u001e2\u000e\u0010#\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100!2\u0006\u0010$\u001a\u00020\nH\u0007J\"\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\b2\u0006\u0010+\u001a\u00020\b2\b\u00100\u001a\u0004\u0018\u000101H\u0016J\b\u00102\u001a\u00020\u001eH\u0002J\b\u00103\u001a\u00020\u001eH\u0003R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\b\u0018\u00010\u001aR\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u001b\u001a\f\u0012\b\u0012\u00060\u001aR\u00020\u00000\u001cX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lcom/fluttercandies/photo_manager/core/PhotoManagerDeleteManager;", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", "context", "Landroid/content/Context;", "activity", "Landroid/app/Activity;", "(Landroid/content/Context;Landroid/app/Activity;)V", "androidQDeleteRequestCode", "", "androidQHandler", "Lcom/fluttercandies/photo_manager/util/ResultHandler;", "androidQSuccessIds", "", "", "androidQUriMap", "", "Landroid/net/Uri;", "androidRDeleteRequestCode", "androidRHandler", "getContext", "()Landroid/content/Context;", "cr", "Landroid/content/ContentResolver;", "getCr", "()Landroid/content/ContentResolver;", "currentTask", "Lcom/fluttercandies/photo_manager/core/PhotoManagerDeleteManager$AndroidQDeleteTask;", "waitPermissionQueue", "Ljava/util/LinkedList;", "bindActivity", "", "deleteInApi28", "ids", "", "deleteInApi30", "uris", "resultHandler", "deleteJustInApi29", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "findIdByUriInApi29", "uri", "handleAndroidRDelete", "resultCode", "moveToTrashInApi30", "onActivityResult", "", "requestCode", "intent", "Landroid/content/Intent;", "replyAndroidQDeleteResult", "requestAndroidQNextPermission", "AndroidQDeleteTask", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhotoManagerDeleteManager implements PluginRegistry.ActivityResultListener {
    private Activity activity;
    private int androidQDeleteRequestCode;
    private ResultHandler androidQHandler;
    private final List<String> androidQSuccessIds;
    private final Map<String, Uri> androidQUriMap;
    private int androidRDeleteRequestCode;
    private ResultHandler androidRHandler;
    private final Context context;
    private AndroidQDeleteTask currentTask;
    private LinkedList<AndroidQDeleteTask> waitPermissionQueue;

    public PhotoManagerDeleteManager(Context context, Activity activity) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.activity = activity;
        this.androidQDeleteRequestCode = 40070;
        this.androidQUriMap = new LinkedHashMap();
        this.androidQSuccessIds = new ArrayList();
        this.waitPermissionQueue = new LinkedList<>();
        this.androidRDeleteRequestCode = 40069;
    }

    public final Context getContext() {
        return this.context;
    }

    public final void bindActivity(Activity activity) {
        this.activity = activity;
    }

    /* JADX INFO: compiled from: PhotoManagerDeleteManager.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u000eR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012"}, d2 = {"Lcom/fluttercandies/photo_manager/core/PhotoManagerDeleteManager$AndroidQDeleteTask;", "", "id", "", "uri", "Landroid/net/Uri;", "exception", "Landroid/app/RecoverableSecurityException;", "(Lcom/fluttercandies/photo_manager/core/PhotoManagerDeleteManager;Ljava/lang/String;Landroid/net/Uri;Landroid/app/RecoverableSecurityException;)V", "getId", "()Ljava/lang/String;", "getUri", "()Landroid/net/Uri;", "handleResult", "", "resultCode", "", "requestPermission", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class AndroidQDeleteTask {
        private final RecoverableSecurityException exception;
        private final String id;
        final /* synthetic */ PhotoManagerDeleteManager this$0;
        private final Uri uri;

        public AndroidQDeleteTask(PhotoManagerDeleteManager photoManagerDeleteManager, String id, Uri uri, RecoverableSecurityException exception) {
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(uri, "uri");
            Intrinsics.checkNotNullParameter(exception, "exception");
            this.this$0 = photoManagerDeleteManager;
            this.id = id;
            this.uri = uri;
            this.exception = exception;
        }

        public final String getId() {
            return this.id;
        }

        public final Uri getUri() {
            return this.uri;
        }

        public final void requestPermission() throws IntentSender.SendIntentException {
            Intent intent = new Intent();
            intent.setData(this.uri);
            Activity activity = this.this$0.activity;
            if (activity != null) {
                activity.startIntentSenderForResult(this.exception.getUserAction().getActionIntent().getIntentSender(), this.this$0.androidQDeleteRequestCode, intent, 0, 0, 0);
            }
        }

        public final void handleResult(int resultCode) throws IntentSender.SendIntentException {
            if (resultCode == -1) {
                this.this$0.androidQSuccessIds.add(this.id);
            }
            this.this$0.requestAndroidQNextPermission();
        }
    }

    private final ContentResolver getCr() {
        ContentResolver contentResolver = this.context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        return contentResolver;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, Intent intent) throws IntentSender.SendIntentException {
        AndroidQDeleteTask androidQDeleteTask;
        if (requestCode == this.androidRDeleteRequestCode) {
            handleAndroidRDelete(resultCode);
            return true;
        }
        if (requestCode != this.androidQDeleteRequestCode) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 29 && (androidQDeleteTask = this.currentTask) != null) {
            androidQDeleteTask.handleResult(resultCode);
        }
        return true;
    }

    private final void handleAndroidRDelete(int resultCode) {
        MethodCall call;
        List list;
        if (resultCode == -1) {
            ResultHandler resultHandler = this.androidRHandler;
            if (resultHandler == null || (call = resultHandler.getCall()) == null || (list = (List) call.argument("ids")) == null) {
                return;
            }
            Intrinsics.checkNotNull(list);
            ResultHandler resultHandler2 = this.androidRHandler;
            if (resultHandler2 != null) {
                resultHandler2.reply(list);
                return;
            }
            return;
        }
        ResultHandler resultHandler3 = this.androidRHandler;
        if (resultHandler3 != null) {
            resultHandler3.reply(CollectionsKt.emptyList());
        }
    }

    public final void deleteInApi28(List<String> ids) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        getCr().delete(IDBUtils.INSTANCE.getAllUri(), "_id in (" + CollectionsKt.joinToString$default(ids, ",", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.fluttercandies.photo_manager.core.PhotoManagerDeleteManager$deleteInApi28$where$1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return "?";
            }
        }, 30, null) + ")", (String[]) ids.toArray(new String[0]));
    }

    public final void deleteInApi30(List<? extends Uri> uris, ResultHandler resultHandler) throws IntentSender.SendIntentException {
        Intrinsics.checkNotNullParameter(uris, "uris");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        this.androidRHandler = resultHandler;
        ContentResolver cr = getCr();
        ArrayList arrayList = new ArrayList();
        for (Uri uri : uris) {
            if (uri != null) {
                arrayList.add(uri);
            }
        }
        PendingIntent pendingIntentCreateDeleteRequest = MediaStore.createDeleteRequest(cr, arrayList);
        Intrinsics.checkNotNullExpressionValue(pendingIntentCreateDeleteRequest, "createDeleteRequest(...)");
        Activity activity = this.activity;
        if (activity != null) {
            activity.startIntentSenderForResult(pendingIntentCreateDeleteRequest.getIntentSender(), this.androidRDeleteRequestCode, null, 0, 0, 0);
        }
    }

    private final String findIdByUriInApi29(Uri uri) {
        for (Map.Entry<String, Uri> entry : this.androidQUriMap.entrySet()) {
            if (Intrinsics.areEqual(entry.getValue(), uri)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestAndroidQNextPermission() throws IntentSender.SendIntentException {
        AndroidQDeleteTask androidQDeleteTaskPoll = this.waitPermissionQueue.poll();
        if (androidQDeleteTaskPoll == null) {
            replyAndroidQDeleteResult();
        } else {
            this.currentTask = androidQDeleteTaskPoll;
            androidQDeleteTaskPoll.requestPermission();
        }
    }

    public final void deleteJustInApi29(HashMap<String, Uri> uris, ResultHandler resultHandler) throws IntentSender.SendIntentException {
        Intrinsics.checkNotNullParameter(uris, "uris");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        this.androidQHandler = resultHandler;
        this.androidQUriMap.clear();
        HashMap<String, Uri> map = uris;
        this.androidQUriMap.putAll(map);
        this.androidQSuccessIds.clear();
        this.waitPermissionQueue.clear();
        for (Map.Entry<String, Uri> entry : map.entrySet()) {
            Uri value = entry.getValue();
            if (value != null) {
                String key = entry.getKey();
                try {
                    getCr().delete(value, null, null);
                } catch (Exception e) {
                    if (e instanceof RecoverableSecurityException) {
                        this.waitPermissionQueue.add(new AndroidQDeleteTask(this, key, value, (RecoverableSecurityException) e));
                    } else {
                        LogUtils.error("delete assets error in api 29", e);
                        replyAndroidQDeleteResult();
                        return;
                    }
                }
            }
        }
        requestAndroidQNextPermission();
    }

    private final void replyAndroidQDeleteResult() {
        if (!this.androidQSuccessIds.isEmpty()) {
            Iterator<String> it = this.androidQSuccessIds.iterator();
            while (it.hasNext()) {
                Uri uri = this.androidQUriMap.get(it.next());
                if (uri != null) {
                    getCr().delete(uri, null, null);
                }
            }
        }
        ResultHandler resultHandler = this.androidQHandler;
        if (resultHandler != null) {
            resultHandler.reply(CollectionsKt.toList(this.androidQSuccessIds));
        }
        this.androidQSuccessIds.clear();
        this.androidQHandler = null;
    }

    public final void moveToTrashInApi30(List<? extends Uri> uris, ResultHandler resultHandler) throws IntentSender.SendIntentException {
        Intrinsics.checkNotNullParameter(uris, "uris");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        this.androidRHandler = resultHandler;
        ContentResolver cr = getCr();
        ArrayList arrayList = new ArrayList();
        for (Uri uri : uris) {
            if (uri != null) {
                arrayList.add(uri);
            }
        }
        PendingIntent pendingIntentCreateTrashRequest = MediaStore.createTrashRequest(cr, arrayList, true);
        Intrinsics.checkNotNullExpressionValue(pendingIntentCreateTrashRequest, "createTrashRequest(...)");
        Activity activity = this.activity;
        if (activity != null) {
            activity.startIntentSenderForResult(pendingIntentCreateTrashRequest.getIntentSender(), this.androidRDeleteRequestCode, null, 0, 0, 0);
        }
    }
}
