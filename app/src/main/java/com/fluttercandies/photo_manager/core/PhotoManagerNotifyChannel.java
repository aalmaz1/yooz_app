package com.fluttercandies.photo_manager.core;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.core.utils.IDBUtils;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.tekartik.sqflite.Constant;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* JADX INFO: compiled from: PhotoManagerNotifyChannel.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001,B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ9\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\b\u0010\"\u001a\u0004\u0018\u00010!2\u0006\u0010#\u001a\u00020$¢\u0006\u0002\u0010%J\u001c\u0010&\u001a\u00020\u001c2\n\u0010'\u001a\u00060\u000eR\u00020\u00002\u0006\u0010\u001d\u001a\u00020\nH\u0002J\u000e\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\u0018J\u0006\u0010*\u001a\u00020\u001cJ\u0006\u0010+\u001a\u00020\u001cR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00060\u000eR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n \u0010*\u0004\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u0012\u0010\u0013\u001a\u00060\u000eR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\n \u0010*\u0004\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0019\u001a\u00060\u000eR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\n \u0010*\u0004\u0018\u00010\n0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/fluttercandies/photo_manager/core/PhotoManagerNotifyChannel;", "", "applicationContext", "Landroid/content/Context;", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "handler", "Landroid/os/Handler;", "(Landroid/content/Context;Lio/flutter/plugin/common/BinaryMessenger;Landroid/os/Handler;)V", "allUri", "Landroid/net/Uri;", "getApplicationContext", "()Landroid/content/Context;", "audioObserver", "Lcom/fluttercandies/photo_manager/core/PhotoManagerNotifyChannel$MediaObserver;", "audioUri", "kotlin.jvm.PlatformType", "context", "getContext", "imageObserver", "imageUri", "methodChannel", "Lio/flutter/plugin/common/MethodChannel;", "notifying", "", "videoObserver", "videoUri", "onOuterChange", "", "uri", "changeType", "", "id", "", "galleryId", "observerType", "", "(Landroid/net/Uri;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;I)V", "registerObserver", "mediaObserver", "setAndroidQExperimental", AbstractCircuitBreaker.PROPERTY_NAME, "startNotify", "stopNotify", "MediaObserver", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhotoManagerNotifyChannel {
    private final Uri allUri;
    private final Context applicationContext;
    private final MediaObserver audioObserver;
    private final Uri audioUri;
    private final MediaObserver imageObserver;
    private final Uri imageUri;
    private final MethodChannel methodChannel;
    private boolean notifying;
    private final MediaObserver videoObserver;
    private final Uri videoUri;

    public PhotoManagerNotifyChannel(Context applicationContext, BinaryMessenger messenger, Handler handler) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        Intrinsics.checkNotNullParameter(messenger, "messenger");
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.applicationContext = applicationContext;
        this.videoObserver = new MediaObserver(this, 3, handler);
        this.imageObserver = new MediaObserver(this, 1, handler);
        this.audioObserver = new MediaObserver(this, 2, handler);
        this.allUri = IDBUtils.INSTANCE.getAllUri();
        this.imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        this.videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        this.audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        this.methodChannel = new MethodChannel(messenger, "com.fluttercandies/photo_manager/notify");
    }

    public final Context getApplicationContext() {
        return this.applicationContext;
    }

    /* JADX INFO: renamed from: getContext, reason: from getter */
    private final Context getApplicationContext() {
        return this.applicationContext;
    }

    public final void startNotify() {
        if (this.notifying) {
            return;
        }
        MediaObserver mediaObserver = this.imageObserver;
        Uri imageUri = this.imageUri;
        Intrinsics.checkNotNullExpressionValue(imageUri, "imageUri");
        registerObserver(mediaObserver, imageUri);
        MediaObserver mediaObserver2 = this.videoObserver;
        Uri videoUri = this.videoUri;
        Intrinsics.checkNotNullExpressionValue(videoUri, "videoUri");
        registerObserver(mediaObserver2, videoUri);
        MediaObserver mediaObserver3 = this.audioObserver;
        Uri audioUri = this.audioUri;
        Intrinsics.checkNotNullExpressionValue(audioUri, "audioUri");
        registerObserver(mediaObserver3, audioUri);
        this.notifying = true;
    }

    private final void registerObserver(MediaObserver mediaObserver, Uri uri) {
        getApplicationContext().getContentResolver().registerContentObserver(uri, true, mediaObserver);
        mediaObserver.setUri(uri);
    }

    public final void stopNotify() {
        if (this.notifying) {
            this.notifying = false;
            getApplicationContext().getContentResolver().unregisterContentObserver(this.imageObserver);
            getApplicationContext().getContentResolver().unregisterContentObserver(this.videoObserver);
            getApplicationContext().getContentResolver().unregisterContentObserver(this.audioObserver);
        }
    }

    public final void onOuterChange(Uri uri, String changeType, Long id, Long galleryId, int observerType) {
        Intrinsics.checkNotNullParameter(changeType, "changeType");
        HashMap mapHashMapOf = MapsKt.hashMapOf(TuplesKt.to("platform", "android"), TuplesKt.to("uri", String.valueOf(uri)), TuplesKt.to(NotificationManager.BUNDLE_TYPE, changeType), TuplesKt.to("mediaType", Integer.valueOf(observerType)));
        if (id != null) {
            mapHashMapOf.put("id", id);
        }
        if (galleryId != null) {
            mapHashMapOf.put("galleryId", galleryId);
        }
        LogUtils.debug(mapHashMapOf);
        this.methodChannel.invokeMethod("change", mapHashMapOf);
    }

    public final void setAndroidQExperimental(boolean open) {
        this.methodChannel.invokeMethod("setAndroidQExperimental", MapsKt.mapOf(TuplesKt.to(AbstractCircuitBreaker.PROPERTY_NAME, Boolean.valueOf(open))));
    }

    /* JADX INFO: compiled from: PhotoManagerNotifyChannel.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\u0017\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u00182\u0006\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u001a\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006 "}, d2 = {"Lcom/fluttercandies/photo_manager/core/PhotoManagerNotifyChannel$MediaObserver;", "Landroid/database/ContentObserver;", NotificationManager.BUNDLE_TYPE, "", "handler", "Landroid/os/Handler;", "(Lcom/fluttercandies/photo_manager/core/PhotoManagerNotifyChannel;ILandroid/os/Handler;)V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "cr", "Landroid/content/ContentResolver;", "getCr", "()Landroid/content/ContentResolver;", "getType", "()I", "uri", "Landroid/net/Uri;", "getUri", "()Landroid/net/Uri;", "setUri", "(Landroid/net/Uri;)V", "getGalleryIdAndName", "Lkotlin/Pair;", "", "", "id", "onChange", "", "selfChange", "", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class MediaObserver extends ContentObserver {
        final /* synthetic */ PhotoManagerNotifyChannel this$0;
        private final int type;
        private Uri uri;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaObserver(PhotoManagerNotifyChannel photoManagerNotifyChannel, int i, Handler handler) {
            super(handler);
            Intrinsics.checkNotNullParameter(handler, "handler");
            this.this$0 = photoManagerNotifyChannel;
            this.type = i;
            Uri uri = Uri.parse("content://media");
            Intrinsics.checkNotNullExpressionValue(uri, "parse(...)");
            this.uri = uri;
        }

        public final int getType() {
            return this.type;
        }

        public /* synthetic */ MediaObserver(PhotoManagerNotifyChannel photoManagerNotifyChannel, int i, Handler handler, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(photoManagerNotifyChannel, i, (i2 & 2) != 0 ? new Handler(Looper.getMainLooper()) : handler);
        }

        public final Uri getUri() {
            return this.uri;
        }

        public final void setUri(Uri uri) {
            Intrinsics.checkNotNullParameter(uri, "<set-?>");
            this.uri = uri;
        }

        public final Context getContext() {
            return this.this$0.getApplicationContext();
        }

        public final ContentResolver getCr() {
            ContentResolver contentResolver = getContext().getContentResolver();
            Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
            return contentResolver;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange, Uri uri) {
            if (uri == null) {
                return;
            }
            String lastPathSegment = uri.getLastPathSegment();
            Long longOrNull = lastPathSegment != null ? StringsKt.toLongOrNull(lastPathSegment) : null;
            if (longOrNull != null) {
                Cursor cursorQuery = getCr().query(this.this$0.allUri, new String[]{"date_added", "date_modified", "media_type"}, "_id = ?", new String[]{longOrNull.toString()}, null);
                if (cursorQuery != null) {
                    Cursor cursor = cursorQuery;
                    PhotoManagerNotifyChannel photoManagerNotifyChannel = this.this$0;
                    try {
                        if (!cursorQuery.moveToNext()) {
                            photoManagerNotifyChannel.onOuterChange(uri, "delete", longOrNull, null, this.type);
                            CloseableKt.closeFinally(cursor, null);
                            return;
                        }
                        String str = (System.currentTimeMillis() / ((long) 1000)) - cursorQuery.getLong(cursorQuery.getColumnIndex("date_added")) < 30 ? Constant.METHOD_INSERT : Constant.METHOD_UPDATE;
                        int i = cursorQuery.getInt(cursorQuery.getColumnIndex("media_type"));
                        Pair<Long, String> galleryIdAndName = getGalleryIdAndName(longOrNull.longValue(), i);
                        Long lComponent1 = galleryIdAndName.component1();
                        String strComponent2 = galleryIdAndName.component2();
                        if (lComponent1 != null && strComponent2 != null) {
                            photoManagerNotifyChannel.onOuterChange(uri, str, longOrNull, lComponent1, i);
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(cursor, null);
                            return;
                        }
                        CloseableKt.closeFinally(cursor, null);
                        return;
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(cursor, th);
                            throw th2;
                        }
                    }
                }
                return;
            }
            if (Build.VERSION.SDK_INT < 29 && Intrinsics.areEqual(uri, this.uri)) {
                this.this$0.onOuterChange(uri, Constant.METHOD_INSERT, null, null, this.type);
            } else {
                this.this$0.onOuterChange(uri, "delete", null, null, this.type);
            }
        }

        private final Pair<Long, String> getGalleryIdAndName(long id, int type) {
            Cursor cursor;
            if (Build.VERSION.SDK_INT >= 29) {
                Cursor cursorQuery = getCr().query(this.this$0.allUri, new String[]{"bucket_id", "bucket_display_name"}, "_id = ?", new String[]{String.valueOf(id)}, null);
                if (cursorQuery != null) {
                    cursor = cursorQuery;
                    try {
                        if (cursorQuery.moveToNext()) {
                            Pair<Long, String> pair = new Pair<>(Long.valueOf(cursorQuery.getLong(cursorQuery.getColumnIndex("bucket_id"))), cursorQuery.getString(cursorQuery.getColumnIndex("bucket_display_name")));
                            CloseableKt.closeFinally(cursor, null);
                            return pair;
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(cursor, null);
                    } finally {
                    }
                }
            } else if (type == 2) {
                Cursor cursorQuery2 = getCr().query(this.this$0.allUri, new String[]{"album_id", "album"}, "_id = ?", new String[]{String.valueOf(id)}, null);
                if (cursorQuery2 != null) {
                    cursor = cursorQuery2;
                    try {
                        if (cursorQuery2.moveToNext()) {
                            Pair<Long, String> pair2 = new Pair<>(Long.valueOf(cursorQuery2.getLong(cursorQuery2.getColumnIndex("album_id"))), cursorQuery2.getString(cursorQuery2.getColumnIndex("album")));
                            CloseableKt.closeFinally(cursor, null);
                            return pair2;
                        }
                        Unit unit2 = Unit.INSTANCE;
                        CloseableKt.closeFinally(cursor, null);
                    } finally {
                        try {
                            throw th;
                        } finally {
                        }
                    }
                }
            } else {
                Cursor cursorQuery3 = getCr().query(this.this$0.allUri, new String[]{"bucket_id", "bucket_display_name"}, "_id = ?", new String[]{String.valueOf(id)}, null);
                if (cursorQuery3 != null) {
                    cursor = cursorQuery3;
                    try {
                        if (cursorQuery3.moveToNext()) {
                            Pair<Long, String> pair3 = new Pair<>(Long.valueOf(cursorQuery3.getLong(cursorQuery3.getColumnIndex("bucket_id"))), cursorQuery3.getString(cursorQuery3.getColumnIndex("bucket_display_name")));
                            CloseableKt.closeFinally(cursor, null);
                            return pair3;
                        }
                        Unit unit3 = Unit.INSTANCE;
                        CloseableKt.closeFinally(cursor, null);
                    } finally {
                        try {
                            throw th;
                        } finally {
                        }
                    }
                }
            }
            return new Pair<>(null, null);
        }
    }
}
