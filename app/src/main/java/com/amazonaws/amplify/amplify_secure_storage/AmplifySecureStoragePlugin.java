package com.amazonaws.amplify.amplify_secure_storage;

import android.content.Context;
import cn.baos.watch.sdk.entitiy.NotificationConstant;
import com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AmplifySecureStoragePlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J2\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u0018\u0010\u000e\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u0010\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\bH\u0002J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J4\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\u001a\u0010\u000e\u001a\u0016\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0010\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016J*\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0018\u0010\u000e\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u0010\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016J<\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\b2\u0018\u0010\u000e\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u0010\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/amazonaws/amplify/amplify_secure_storage/AmplifySecureStoragePlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lcom/amazonaws/amplify/amplify_secure_storage/pigeons/AmplifySecureStoragePigeon;", "()V", "context", "Landroid/content/Context;", "repositories", "", "", "Lcom/amazonaws/amplify/amplify_secure_storage/EncryptedKeyValueRepository;", "delete", "", "namespace", NotificationConstant.EXTRA_KEY, "callback", "Lkotlin/Function1;", "Lkotlin/Result;", "getOrCreateRepository", "onAttachedToEngine", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "read", "removeAll", "write", "value", "amplify_secure_storage_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AmplifySecureStoragePlugin implements FlutterPlugin, AmplifySecureStoragePigeon {
    private Context context;
    private final Map<String, EncryptedKeyValueRepository> repositories = new LinkedHashMap();

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Context applicationContext = binding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.context = applicationContext;
        AmplifySecureStoragePigeon.Companion companion = AmplifySecureStoragePigeon.INSTANCE;
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        companion.setUp(binaryMessenger, this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        AmplifySecureStoragePigeon.Companion companion = AmplifySecureStoragePigeon.INSTANCE;
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        companion.setUp(binaryMessenger, null);
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon
    public void read(String namespace, String key, Function1<? super Result<String>, Unit> callback) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callback, "callback");
        try {
            String str = getOrCreateRepository(namespace).get(key);
            Result.Companion companion = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(str)));
        } catch (Exception e) {
            Result.Companion companion2 = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(ResultKt.createFailure(e))));
        }
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon
    public void write(String namespace, String key, String value, Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callback, "callback");
        try {
            getOrCreateRepository(namespace).put(key, value);
            Result.Companion companion = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(Unit.INSTANCE)));
        } catch (Exception e) {
            Result.Companion companion2 = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(ResultKt.createFailure(e))));
        }
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon
    public void delete(String namespace, String key, Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callback, "callback");
        try {
            getOrCreateRepository(namespace).remove(key);
            Result.Companion companion = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(Unit.INSTANCE)));
        } catch (Exception e) {
            Result.Companion companion2 = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(ResultKt.createFailure(e))));
        }
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.pigeons.AmplifySecureStoragePigeon
    public void removeAll(String namespace, Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(callback, "callback");
        try {
            getOrCreateRepository(namespace).removeAll();
            Result.Companion companion = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(Unit.INSTANCE)));
        } catch (Exception e) {
            Result.Companion companion2 = Result.INSTANCE;
            callback.invoke(Result.m683boximpl(Result.m684constructorimpl(ResultKt.createFailure(e))));
        }
    }

    private final EncryptedKeyValueRepository getOrCreateRepository(String namespace) {
        if (this.repositories.containsKey(namespace)) {
            EncryptedKeyValueRepository encryptedKeyValueRepository = this.repositories.get(namespace);
            Intrinsics.checkNotNull(encryptedKeyValueRepository);
            return encryptedKeyValueRepository;
        }
        Context context = this.context;
        if (context == null) {
            Intrinsics.throwUninitializedPropertyAccessException("context");
            context = null;
        }
        EncryptedKeyValueRepository encryptedKeyValueRepository2 = new EncryptedKeyValueRepository(context, namespace);
        this.repositories.put(namespace, encryptedKeyValueRepository2);
        return encryptedKeyValueRepository2;
    }
}
