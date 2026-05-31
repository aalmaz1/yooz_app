package com.google.android.play.core.appupdate;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AppUpdateOptions {

    /* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
    public static abstract class Builder {
        public abstract AppUpdateOptions build();

        public abstract Builder setAllowAssetPackDeletion(boolean z);

        public abstract Builder setAppUpdateType(int i);
    }

    public static AppUpdateOptions defaultOptions(int i) {
        return newBuilder(i).build();
    }

    public static Builder newBuilder(int i) {
        zzv zzvVar = new zzv();
        zzvVar.setAppUpdateType(i);
        zzvVar.setAllowAssetPackDeletion(false);
        return zzvVar;
    }

    public abstract boolean allowAssetPackDeletion();

    public abstract int appUpdateType();
}
