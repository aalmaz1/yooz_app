package com.android.billingclient.api;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import androidx.work.WorkRequest;
import com.google.android.gms.internal.play_billing.zziz;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzao implements ServiceConnection {
    final /* synthetic */ BillingClientImpl zza;
    private final Object zzb = new Object();
    private boolean zzc = false;
    private BillingClientStateListener zzd;

    /* synthetic */ zzao(BillingClientImpl billingClientImpl, BillingClientStateListener billingClientStateListener, zzan zzanVar) {
        this.zza = billingClientImpl;
        this.zzd = billingClientStateListener;
    }

    private final void zzd(BillingResult billingResult) {
        synchronized (this.zzb) {
            BillingClientStateListener billingClientStateListener = this.zzd;
            if (billingClientStateListener != null) {
                billingClientStateListener.onBillingSetupFinished(billingResult);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Billing service connected.");
        this.zza.zzg = com.google.android.gms.internal.play_billing.zzl.zzr(iBinder);
        Callable callable = new Callable() { // from class: com.android.billingclient.api.zzal
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zza();
                return null;
            }
        };
        Runnable runnable = new Runnable() { // from class: com.android.billingclient.api.zzam
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzb();
            }
        };
        BillingClientImpl billingClientImpl = this.zza;
        if (billingClientImpl.zzac(callable, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, runnable, billingClientImpl.zzY()) == null) {
            BillingClientImpl billingClientImpl2 = this.zza;
            BillingResult billingResultZzaa = billingClientImpl2.zzaa();
            billingClientImpl2.zzf.zza(zzbh.zza(25, 6, billingResultZzaa));
            zzd(billingResultZzaa);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Billing service disconnected.");
        this.zza.zzf.zzc(zziz.zzw());
        this.zza.zzg = null;
        this.zza.zza = 0;
        synchronized (this.zzb) {
            BillingClientStateListener billingClientStateListener = this.zzd;
            if (billingClientStateListener != null) {
                billingClientStateListener.onBillingServiceDisconnected();
            }
        }
    }

    final /* synthetic */ Object zza() throws Exception {
        Bundle bundle;
        int i;
        int iZzv;
        synchronized (this.zzb) {
            if (!this.zzc) {
                if (TextUtils.isEmpty(null)) {
                    bundle = null;
                } else {
                    bundle = new Bundle();
                    bundle.putString("accountName", null);
                }
                int i2 = 3;
                try {
                    String packageName = this.zza.zze.getPackageName();
                    iZzv = 3;
                    int i3 = 21;
                    while (true) {
                        if (i3 < 3) {
                            i3 = 0;
                            break;
                        }
                        if (bundle == null) {
                            try {
                                iZzv = this.zza.zzg.zzv(i3, packageName, "subs");
                            } catch (Exception e) {
                                e = e;
                                i2 = iZzv;
                                com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Exception while checking if billing is supported; try to reconnect", e);
                                this.zza.zza = 0;
                                this.zza.zzg = null;
                                i = 42;
                                iZzv = i2;
                            }
                        } else {
                            iZzv = this.zza.zzg.zzc(i3, packageName, "subs", bundle);
                        }
                        if (iZzv == 0) {
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "highestLevelSupportedForSubs: " + i3);
                            break;
                        }
                        i3--;
                    }
                    boolean z = true;
                    this.zza.zzj = i3 >= 5;
                    this.zza.zzi = i3 >= 3;
                    if (i3 < 3) {
                        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "In-app billing API does not support subscription on this device.");
                        i = 9;
                    } else {
                        i = 1;
                    }
                    int i4 = 21;
                    while (true) {
                        if (i4 < 3) {
                            break;
                        }
                        iZzv = bundle == null ? this.zza.zzg.zzv(i4, packageName, "inapp") : this.zza.zzg.zzc(i4, packageName, "inapp", bundle);
                        if (iZzv == 0) {
                            this.zza.zzk = i4;
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "mHighestLevelSupportedForInApp: " + this.zza.zzk);
                            break;
                        }
                        i4--;
                    }
                    BillingClientImpl billingClientImpl = this.zza;
                    billingClientImpl.zzx = billingClientImpl.zzk >= 21;
                    BillingClientImpl billingClientImpl2 = this.zza;
                    billingClientImpl2.zzw = billingClientImpl2.zzk >= 20;
                    BillingClientImpl billingClientImpl3 = this.zza;
                    billingClientImpl3.zzv = billingClientImpl3.zzk >= 19;
                    BillingClientImpl billingClientImpl4 = this.zza;
                    billingClientImpl4.zzu = billingClientImpl4.zzk >= 18;
                    BillingClientImpl billingClientImpl5 = this.zza;
                    billingClientImpl5.zzt = billingClientImpl5.zzk >= 17;
                    BillingClientImpl billingClientImpl6 = this.zza;
                    billingClientImpl6.zzs = billingClientImpl6.zzk >= 16;
                    BillingClientImpl billingClientImpl7 = this.zza;
                    billingClientImpl7.zzr = billingClientImpl7.zzk >= 15;
                    BillingClientImpl billingClientImpl8 = this.zza;
                    billingClientImpl8.zzq = billingClientImpl8.zzk >= 14;
                    BillingClientImpl billingClientImpl9 = this.zza;
                    billingClientImpl9.zzp = billingClientImpl9.zzk >= 12;
                    BillingClientImpl billingClientImpl10 = this.zza;
                    billingClientImpl10.zzo = billingClientImpl10.zzk >= 10;
                    BillingClientImpl billingClientImpl11 = this.zza;
                    billingClientImpl11.zzn = billingClientImpl11.zzk >= 9;
                    BillingClientImpl billingClientImpl12 = this.zza;
                    billingClientImpl12.zzm = billingClientImpl12.zzk >= 8;
                    BillingClientImpl billingClientImpl13 = this.zza;
                    if (billingClientImpl13.zzk < 6) {
                        z = false;
                    }
                    billingClientImpl13.zzl = z;
                    if (this.zza.zzk < 3) {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "In-app billing API version 3 is not supported on this device.");
                        i = 36;
                    }
                    if (iZzv == 0) {
                        this.zza.zza = 2;
                        if (this.zza.zzd != null) {
                            zzh zzhVar = this.zza.zzd;
                            this.zza.zze.getPackageName();
                            zzhVar.zzf(false);
                        }
                    } else {
                        this.zza.zza = 0;
                        this.zza.zzg = null;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
                if (iZzv == 0) {
                    this.zza.zzf.zzb(zzbh.zzb(6));
                    zzd(zzbk.zzl);
                } else {
                    this.zza.zzf.zza(zzbh.zza(i, 6, zzbk.zza));
                    zzd(zzbk.zza);
                }
            }
        }
        return null;
    }

    final /* synthetic */ void zzb() {
        this.zza.zza = 0;
        this.zza.zzg = null;
        this.zza.zzf.zza(zzbh.zza(24, 6, zzbk.zzn));
        zzd(zzbk.zzn);
    }

    final void zzc() {
        synchronized (this.zzb) {
            this.zzd = null;
            this.zzc = true;
        }
    }
}
