package com.google.android.play.core.install;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zza extends InstallState {
    private final int zza;
    private final long zzb;
    private final long zzc;
    private final int zzd;
    private final String zze;

    zza(int i, long j, long j2, int i2, String str) {
        this.zza = i;
        this.zzb = j;
        this.zzc = j2;
        this.zzd = i2;
        if (str == null) {
            throw new NullPointerException("Null packageName");
        }
        this.zze = str;
    }

    @Override // com.google.android.play.core.install.InstallState
    public final long bytesDownloaded() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof InstallState) {
            InstallState installState = (InstallState) obj;
            if (this.zza == installState.installStatus() && this.zzb == installState.bytesDownloaded() && this.zzc == installState.totalBytesToDownload() && this.zzd == installState.installErrorCode() && this.zze.equals(installState.packageName())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zza ^ 1000003;
        long j = this.zzb;
        long j2 = this.zzc;
        return (((((((i * 1000003) ^ ((int) (j ^ (j >>> 32)))) * 1000003) ^ ((int) ((j2 >>> 32) ^ j2))) * 1000003) ^ this.zzd) * 1000003) ^ this.zze.hashCode();
    }

    @Override // com.google.android.play.core.install.InstallState
    public final int installErrorCode() {
        return this.zzd;
    }

    @Override // com.google.android.play.core.install.InstallState
    public final int installStatus() {
        return this.zza;
    }

    @Override // com.google.android.play.core.install.InstallState
    public final String packageName() {
        return this.zze;
    }

    public final String toString() {
        return "InstallState{installStatus=" + this.zza + ", bytesDownloaded=" + this.zzb + ", totalBytesToDownload=" + this.zzc + ", installErrorCode=" + this.zzd + ", packageName=" + this.zze + "}";
    }

    @Override // com.google.android.play.core.install.InstallState
    public final long totalBytesToDownload() {
        return this.zzc;
    }
}
