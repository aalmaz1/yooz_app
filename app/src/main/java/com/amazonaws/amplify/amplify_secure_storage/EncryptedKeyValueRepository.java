package com.amazonaws.amplify.amplify_secure_storage;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EncryptedKeyValueRepository.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001a\u001a\u00020\u0005H\u0016J\u001a\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0016J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001a\u001a\u00020\u0005H\u0016J\b\u0010\u001f\u001a\u00020\u001cH\u0016J\b\u0010 \u001a\u00020\u001cH\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR!\u0010\r\u001a\u00020\u000e8FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\u0013\u0010\f\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\f\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/amazonaws/amplify/amplify_secure_storage/EncryptedKeyValueRepository;", "Lcom/amazonaws/amplify/amplify_secure_storage/KeyValueRepository;", "context", "Landroid/content/Context;", "sharedPreferencesName", "", "(Landroid/content/Context;Ljava/lang/String;)V", "editor", "Landroid/content/SharedPreferences$Editor;", "getEditor", "()Landroid/content/SharedPreferences$Editor;", "editor$delegate", "Lkotlin/Lazy;", "initializationFlagFile", "Ljava/io/File;", "getInitializationFlagFile$annotations", "()V", "getInitializationFlagFile", "()Ljava/io/File;", "initializationFlagFile$delegate", "sharedPreferences", "Landroid/content/SharedPreferences;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "sharedPreferences$delegate", "get", "dataKey", "put", "", "value", "remove", "removeAll", "removeSharedPreferencesFile", "amplify_secure_storage_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class EncryptedKeyValueRepository implements KeyValueRepository {
    private final Context context;

    /* JADX INFO: renamed from: editor$delegate, reason: from kotlin metadata */
    private final Lazy editor;

    /* JADX INFO: renamed from: initializationFlagFile$delegate, reason: from kotlin metadata */
    private final Lazy initializationFlagFile;

    /* JADX INFO: renamed from: sharedPreferences$delegate, reason: from kotlin metadata */
    private final Lazy sharedPreferences;
    private final String sharedPreferencesName;

    public static /* synthetic */ void getInitializationFlagFile$annotations() {
    }

    public EncryptedKeyValueRepository(Context context, String sharedPreferencesName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sharedPreferencesName, "sharedPreferencesName");
        this.context = context;
        this.sharedPreferencesName = sharedPreferencesName;
        this.sharedPreferences = LazyKt.lazy(new Function0<SharedPreferences>() { // from class: com.amazonaws.amplify.amplify_secure_storage.EncryptedKeyValueRepository$sharedPreferences$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SharedPreferences invoke() throws GeneralSecurityException, IOException {
                if (!this.this$0.getInitializationFlagFile().exists()) {
                    this.this$0.removeSharedPreferencesFile();
                    this.this$0.getInitializationFlagFile().createNewFile();
                }
                SharedPreferences sharedPreferencesCreate = EncryptedSharedPreferences.create(this.this$0.sharedPreferencesName, MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), this.this$0.context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                Intrinsics.checkNotNullExpressionValue(sharedPreferencesCreate, "create(...)");
                return sharedPreferencesCreate;
            }
        });
        this.editor = LazyKt.lazy(new Function0<SharedPreferences.Editor>() { // from class: com.amazonaws.amplify.amplify_secure_storage.EncryptedKeyValueRepository$editor$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SharedPreferences.Editor invoke() {
                return this.this$0.getSharedPreferences().edit();
            }
        });
        this.initializationFlagFile = LazyKt.lazy(new Function0<File>() { // from class: com.amazonaws.amplify.amplify_secure_storage.EncryptedKeyValueRepository$initializationFlagFile$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                return new File(this.this$0.context.getNoBackupFilesDir(), "amplify_EncryptedSharedPreferences_" + this.this$0.sharedPreferencesName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SharedPreferences getSharedPreferences() {
        return (SharedPreferences) this.sharedPreferences.getValue();
    }

    private final SharedPreferences.Editor getEditor() {
        Object value = this.editor.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (SharedPreferences.Editor) value;
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.KeyValueRepository
    public void put(String dataKey, String value) {
        Intrinsics.checkNotNullParameter(dataKey, "dataKey");
        SharedPreferences.Editor editor = getEditor();
        editor.putString(dataKey, value);
        editor.apply();
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.KeyValueRepository
    public String get(String dataKey) {
        Intrinsics.checkNotNullParameter(dataKey, "dataKey");
        return getSharedPreferences().getString(dataKey, null);
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.KeyValueRepository
    public void remove(String dataKey) {
        Intrinsics.checkNotNullParameter(dataKey, "dataKey");
        SharedPreferences.Editor editor = getEditor();
        editor.remove(dataKey);
        editor.apply();
    }

    @Override // com.amazonaws.amplify.amplify_secure_storage.KeyValueRepository
    public void removeAll() {
        SharedPreferences.Editor editor = getEditor();
        editor.clear();
        editor.apply();
    }

    public void removeSharedPreferencesFile() {
        new File(this.context.getFilesDir().getParent() + "/shared_prefs/" + this.sharedPreferencesName + ".xml").delete();
    }

    public final File getInitializationFlagFile() {
        return (File) this.initializationFlagFile.getValue();
    }
}
