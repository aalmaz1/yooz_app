package com.google.android.gms.internal.location;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.location.LocationSettingsResult;

/* JADX INFO: compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcr extends zzr {
    final /* synthetic */ BaseImplementation.ResultHolder zza;

    zzcr(BaseImplementation.ResultHolder resultHolder) {
        this.zza = resultHolder;
    }

    @Override // com.google.android.gms.internal.location.zzs
    public final void zzb(LocationSettingsResult locationSettingsResult) {
        this.zza.setResult(locationSettingsResult);
    }
}
