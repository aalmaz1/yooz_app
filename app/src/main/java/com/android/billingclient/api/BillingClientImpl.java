package com.android.billingclient.api;

import android.R;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.View;
import androidx.core.app.BundleCompat;
import androidx.work.WorkRequest;
import com.android.billingclient.BuildConfig;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.gms.internal.play_billing.zzhx;
import com.google.android.gms.internal.play_billing.zzhy;
import com.google.android.gms.internal.play_billing.zzib;
import com.google.android.gms.internal.play_billing.zzic;
import com.google.android.gms.internal.play_billing.zzie;
import com.google.android.gms.internal.play_billing.zzii;
import com.google.android.gms.internal.play_billing.zzin;
import com.google.android.gms.internal.play_billing.zzio;
import com.google.android.gms.internal.play_billing.zziq;
import com.google.android.gms.internal.play_billing.zzis;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.json.JSONException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
class BillingClientImpl extends BillingClient {
    private ExecutorService zzA;
    private volatile int zza;
    private final String zzb;
    private final Handler zzc;
    private volatile zzh zzd;
    private Context zze;
    private zzbi zzf;
    private volatile com.google.android.gms.internal.play_billing.zzm zzg;
    private volatile zzao zzh;
    private boolean zzi;
    private boolean zzj;
    private int zzk;
    private boolean zzl;
    private boolean zzm;
    private boolean zzn;
    private boolean zzo;
    private boolean zzp;
    private boolean zzq;
    private boolean zzr;
    private boolean zzs;
    private boolean zzt;
    private boolean zzu;
    private boolean zzv;
    private boolean zzw;
    private boolean zzx;
    private zzbx zzy;
    private boolean zzz;

    private BillingClientImpl(Activity activity, zzbx zzbxVar, String str) {
        this(activity.getApplicationContext(), zzbxVar, new zzba(), str, null, null, null, null);
    }

    private void initialize(Context context, PurchasesUpdatedListener purchasesUpdatedListener, zzbx zzbxVar, AlternativeBillingListener alternativeBillingListener, String str, zzbi zzbiVar) {
        this.zze = context.getApplicationContext();
        zzin zzinVarZzv = zzio.zzv();
        zzinVarZzv.zzj(str);
        zzinVarZzv.zzi(this.zze.getPackageName());
        if (zzbiVar != null) {
            this.zzf = zzbiVar;
        } else {
            this.zzf = new zzbn(this.zze, (zzio) zzinVarZzv.zzc());
        }
        if (purchasesUpdatedListener == null) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Billing client should have a valid listener but the provided is null.");
        }
        this.zzd = new zzh(this.zze, purchasesUpdatedListener, alternativeBillingListener, this.zzf);
        this.zzy = zzbxVar;
        this.zzz = alternativeBillingListener != null;
        this.zze.getPackageName();
    }

    private int launchBillingFlowCpp(Activity activity, BillingFlowParams billingFlowParams) {
        return launchBillingFlow(activity, billingFlowParams).getResponseCode();
    }

    private void startConnection(long j) {
        zzba zzbaVar = new zzba(j);
        if (isReady()) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Service connection is valid. No need to re-initialize.");
            this.zzf.zzb(zzbh.zzb(6));
            zzbaVar.onBillingSetupFinished(zzbk.zzl);
            return;
        }
        int i = 1;
        if (this.zza == 1) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Client is already in the process of connecting to billing service.");
            this.zzf.zza(zzbh.zza(37, 6, zzbk.zzd));
            zzbaVar.onBillingSetupFinished(zzbk.zzd);
            return;
        }
        if (this.zza == 3) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            this.zzf.zza(zzbh.zza(38, 6, zzbk.zzm));
            zzbaVar.onBillingSetupFinished(zzbk.zzm);
            return;
        }
        this.zza = 1;
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Starting in-app billing setup.");
        this.zzh = new zzao(this, zzbaVar, null);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> listQueryIntentServices = this.zze.getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            i = 41;
        } else {
            ResolveInfo resolveInfo = listQueryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str = resolveInfo.serviceInfo.packageName;
                String str2 = resolveInfo.serviceInfo.name;
                if (!"com.android.vending".equals(str) || str2 == null) {
                    com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "The device doesn't have valid Play Store.");
                    i = 40;
                } else {
                    ComponentName componentName = new ComponentName(str, str2);
                    Intent intent2 = new Intent(intent);
                    intent2.setComponent(componentName);
                    intent2.putExtra("playBillingLibraryVersion", this.zzb);
                    if (this.zze.bindService(intent2, this.zzh, 1)) {
                        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Service was bonded successfully.");
                        return;
                    } else {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Connection to Billing service is blocked.");
                        i = 39;
                    }
                }
            }
        }
        this.zza = 0;
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Billing service unavailable on device.");
        this.zzf.zza(zzbh.zza(i, 6, zzbk.zzc));
        zzbaVar.onBillingSetupFinished(zzbk.zzc);
    }

    static /* synthetic */ zzce zzX(BillingClientImpl billingClientImpl, String str, int i) {
        Bundle bundleZzi;
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Querying owned items, item type: ".concat(String.valueOf(str)));
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        int i2 = 0;
        Bundle bundleZzd = com.google.android.gms.internal.play_billing.zzb.zzd(billingClientImpl.zzn, billingClientImpl.zzv, true, false, billingClientImpl.zzb);
        List list = null;
        String string = null;
        while (true) {
            try {
                if (billingClientImpl.zzn) {
                    bundleZzi = billingClientImpl.zzg.zzj(z != billingClientImpl.zzv ? 9 : 19, billingClientImpl.zze.getPackageName(), str, string, bundleZzd);
                } else {
                    bundleZzi = billingClientImpl.zzg.zzi(3, billingClientImpl.zze.getPackageName(), str, string);
                }
                zzcf zzcfVarZza = zzcg.zza(bundleZzi, "BillingClient", "getPurchase()");
                BillingResult billingResultZza = zzcfVarZza.zza();
                if (billingResultZza != zzbk.zzl) {
                    billingClientImpl.zzf.zza(zzbh.zza(zzcfVarZza.zzb(), 9, billingResultZza));
                    return new zzce(billingResultZza, list);
                }
                ArrayList<String> stringArrayList = bundleZzi.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = bundleZzi.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = bundleZzi.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                int i3 = i2;
                int i4 = i3;
                while (i3 < stringArrayList2.size()) {
                    String str2 = stringArrayList2.get(i3);
                    String str3 = stringArrayList3.get(i3);
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Sku is owned: ".concat(String.valueOf(stringArrayList.get(i3))));
                    try {
                        Purchase purchase = new Purchase(str2, str3);
                        if (TextUtils.isEmpty(purchase.getPurchaseToken())) {
                            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "BUG: empty/null token!");
                            i4 = 1;
                        }
                        arrayList.add(purchase);
                        i3++;
                    } catch (JSONException e) {
                        com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Got an exception trying to decode the purchase!", e);
                        billingClientImpl.zzf.zza(zzbh.zza(51, 9, zzbk.zzj));
                        return new zzce(zzbk.zzj, null);
                    }
                }
                if (i4 != 0) {
                    billingClientImpl.zzf.zza(zzbh.zza(26, 9, zzbk.zzj));
                }
                string = bundleZzi.getString("INAPP_CONTINUATION_TOKEN");
                com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Continuation token: ".concat(String.valueOf(string)));
                if (TextUtils.isEmpty(string)) {
                    return new zzce(zzbk.zzl, arrayList);
                }
                list = null;
                z = true;
                i2 = 0;
            } catch (Exception e2) {
                billingClientImpl.zzf.zza(zzbh.zza(52, 9, zzbk.zzm));
                com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Got exception trying to get purchasesm try to reconnect", e2);
                return new zzce(zzbk.zzm, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Handler zzY() {
        return Looper.myLooper() == null ? this.zzc : new Handler(Looper.myLooper());
    }

    private final BillingResult zzZ(final BillingResult billingResult) {
        if (Thread.interrupted()) {
            return billingResult;
        }
        this.zzc.post(new Runnable() { // from class: com.android.billingclient.api.zzj
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzN(billingResult);
            }
        });
        return billingResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BillingResult zzaa() {
        return (this.zza == 0 || this.zza == 3) ? zzbk.zzm : zzbk.zzj;
    }

    private static String zzab() {
        try {
            return (String) Class.forName("com.android.billingclient.ktx.BuildConfig").getField("VERSION_NAME").get(null);
        } catch (Exception unused) {
            return BuildConfig.VERSION_NAME;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Future zzac(Callable callable, long j, final Runnable runnable, Handler handler) {
        if (this.zzA == null) {
            this.zzA = Executors.newFixedThreadPool(com.google.android.gms.internal.play_billing.zzb.zza, new zzag(this));
        }
        try {
            final Future futureSubmit = this.zzA.submit(callable);
            handler.postDelayed(new Runnable() { // from class: com.android.billingclient.api.zzn
                @Override // java.lang.Runnable
                public final void run() {
                    Future future = futureSubmit;
                    if (future.isDone() || future.isCancelled()) {
                        return;
                    }
                    Runnable runnable2 = runnable;
                    future.cancel(true);
                    com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Async task is taking too long, cancel it!");
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            }, (long) (j * 0.95d));
            return futureSubmit;
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Async task throws exception!", e);
            return null;
        }
    }

    private final void zzad(String str, final PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 11, zzbk.zzm));
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzbk.zzm, null);
        } else if (zzac(new zzai(this, str, purchaseHistoryResponseListener), WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzy
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzT(purchaseHistoryResponseListener);
            }
        }, zzY()) == null) {
            BillingResult billingResultZzaa = zzaa();
            this.zzf.zza(zzbh.zza(25, 11, billingResultZzaa));
            purchaseHistoryResponseListener.onPurchaseHistoryResponse(billingResultZzaa, null);
        }
    }

    private final void zzae(String str, final PurchasesResponseListener purchasesResponseListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 9, zzbk.zzm));
            purchasesResponseListener.onQueryPurchasesResponse(zzbk.zzm, com.google.android.gms.internal.play_billing.zzaf.zzk());
        } else if (TextUtils.isEmpty(str)) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Please provide a valid product type.");
            this.zzf.zza(zzbh.zza(50, 9, zzbk.zzg));
            purchasesResponseListener.onQueryPurchasesResponse(zzbk.zzg, com.google.android.gms.internal.play_billing.zzaf.zzk());
        } else if (zzac(new zzah(this, str, purchasesResponseListener), WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzs
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzU(purchasesResponseListener);
            }
        }, zzY()) == null) {
            BillingResult billingResultZzaa = zzaa();
            this.zzf.zza(zzbh.zza(25, 9, billingResultZzaa));
            purchasesResponseListener.onQueryPurchasesResponse(billingResultZzaa, com.google.android.gms.internal.play_billing.zzaf.zzk());
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final void zzaf(BillingResult billingResult, int i, int i2) {
        zzic zzicVar = null;
        zzhy zzhyVar = null;
        if (billingResult.getResponseCode() == 0) {
            zzbi zzbiVar = this.zzf;
            try {
                zzib zzibVarZzv = zzic.zzv();
                zzibVarZzv.zzj(5);
                zziq zziqVarZzv = zzis.zzv();
                zziqVarZzv.zzi(i2);
                zzibVarZzv.zzi((zzis) zziqVarZzv.zzc());
                zzicVar = (zzic) zzibVarZzv.zzc();
            } catch (Exception e) {
                com.google.android.gms.internal.play_billing.zzb.zzl("BillingLogger", "Unable to create logging payload", e);
            }
            zzbiVar.zzb(zzicVar);
            return;
        }
        zzbi zzbiVar2 = this.zzf;
        try {
            zzhx zzhxVarZzv = zzhy.zzv();
            zzie zzieVarZzv = zzii.zzv();
            zzieVarZzv.zzk(billingResult.getResponseCode());
            zzieVarZzv.zzj(billingResult.getDebugMessage());
            zzieVarZzv.zzl(i);
            zzhxVarZzv.zzi(zzieVarZzv);
            zzhxVarZzv.zzk(5);
            zziq zziqVarZzv2 = zzis.zzv();
            zziqVarZzv2.zzi(i2);
            zzhxVarZzv.zzj((zzis) zziqVarZzv2.zzc());
            zzhyVar = (zzhy) zzhxVarZzv.zzc();
        } catch (Exception e2) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingLogger", "Unable to create logging payload", e2);
        }
        zzbiVar2.zza(zzhyVar);
    }

    static /* synthetic */ zzaz zzg(BillingClientImpl billingClientImpl, String str) {
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Querying purchase history, item type: ".concat(String.valueOf(str)));
        ArrayList arrayList = new ArrayList();
        int i = 0;
        Bundle bundleZzd = com.google.android.gms.internal.play_billing.zzb.zzd(billingClientImpl.zzn, billingClientImpl.zzv, true, false, billingClientImpl.zzb);
        String string = null;
        while (billingClientImpl.zzl) {
            try {
                Bundle bundleZzh = billingClientImpl.zzg.zzh(6, billingClientImpl.zze.getPackageName(), str, string, bundleZzd);
                zzcf zzcfVarZza = zzcg.zza(bundleZzh, "BillingClient", "getPurchaseHistory()");
                BillingResult billingResultZza = zzcfVarZza.zza();
                if (billingResultZza != zzbk.zzl) {
                    billingClientImpl.zzf.zza(zzbh.zza(zzcfVarZza.zzb(), 11, billingResultZza));
                    return new zzaz(billingResultZza, null);
                }
                ArrayList<String> stringArrayList = bundleZzh.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = bundleZzh.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = bundleZzh.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                int i2 = i;
                int i3 = i2;
                while (i2 < stringArrayList2.size()) {
                    String str2 = stringArrayList2.get(i2);
                    String str3 = stringArrayList3.get(i2);
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Purchase record found for sku : ".concat(String.valueOf(stringArrayList.get(i2))));
                    try {
                        PurchaseHistoryRecord purchaseHistoryRecord = new PurchaseHistoryRecord(str2, str3);
                        if (TextUtils.isEmpty(purchaseHistoryRecord.getPurchaseToken())) {
                            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "BUG: empty/null token!");
                            i3 = 1;
                        }
                        arrayList.add(purchaseHistoryRecord);
                        i2++;
                    } catch (JSONException e) {
                        com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Got an exception trying to decode the purchase!", e);
                        billingClientImpl.zzf.zza(zzbh.zza(51, 11, zzbk.zzj));
                        return new zzaz(zzbk.zzj, null);
                    }
                }
                if (i3 != 0) {
                    billingClientImpl.zzf.zza(zzbh.zza(26, 11, zzbk.zzj));
                }
                string = bundleZzh.getString("INAPP_CONTINUATION_TOKEN");
                com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Continuation token: ".concat(String.valueOf(string)));
                if (TextUtils.isEmpty(string)) {
                    return new zzaz(zzbk.zzl, arrayList);
                }
                i = 0;
            } catch (RemoteException e2) {
                com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Got exception trying to get purchase history, try to reconnect", e2);
                billingClientImpl.zzf.zza(zzbh.zza(59, 11, zzbk.zzm));
                return new zzaz(zzbk.zzm, null);
            }
        }
        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "getPurchaseHistory is not supported on current device");
        return new zzaz(zzbk.zzq, null);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void acknowledgePurchase(final AcknowledgePurchaseParams acknowledgePurchaseParams, final AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 3, zzbk.zzm));
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbk.zzm);
            return;
        }
        if (TextUtils.isEmpty(acknowledgePurchaseParams.getPurchaseToken())) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Please provide a valid purchase token.");
            this.zzf.zza(zzbh.zza(26, 3, zzbk.zzi));
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbk.zzi);
        } else if (!this.zzn) {
            this.zzf.zza(zzbh.zza(27, 3, zzbk.zzb));
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbk.zzb);
        } else if (zzac(new Callable() { // from class: com.android.billingclient.api.zzaf
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zzk(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzk
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzM(acknowledgePurchaseResponseListener);
            }
        }, zzY()) == null) {
            BillingResult billingResultZzaa = zzaa();
            this.zzf.zza(zzbh.zza(25, 3, billingResultZzaa));
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(billingResultZzaa);
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void consumeAsync(final ConsumeParams consumeParams, final ConsumeResponseListener consumeResponseListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 4, zzbk.zzm));
            consumeResponseListener.onConsumeResponse(zzbk.zzm, consumeParams.getPurchaseToken());
        } else if (zzac(new Callable() { // from class: com.android.billingclient.api.zzw
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zzl(consumeParams, consumeResponseListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzx
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzO(consumeResponseListener, consumeParams);
            }
        }, zzY()) == null) {
            BillingResult billingResultZzaa = zzaa();
            this.zzf.zza(zzbh.zza(25, 4, billingResultZzaa));
            consumeResponseListener.onConsumeResponse(billingResultZzaa, consumeParams.getPurchaseToken());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void createAlternativeBillingOnlyReportingDetailsAsync(final AlternativeBillingOnlyReportingDetailsListener alternativeBillingOnlyReportingDetailsListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 15, zzbk.zzm));
            alternativeBillingOnlyReportingDetailsListener.onAlternativeBillingOnlyTokenResponse(zzbk.zzm, null);
        } else if (!this.zzx) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Current client doesn't support alternative billing only.");
            this.zzf.zza(zzbh.zza(66, 15, zzbk.zzD));
            alternativeBillingOnlyReportingDetailsListener.onAlternativeBillingOnlyTokenResponse(zzbk.zzD, null);
        } else if (zzac(new Callable() { // from class: com.android.billingclient.api.zzo
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zzq(alternativeBillingOnlyReportingDetailsListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzp
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzP(alternativeBillingOnlyReportingDetailsListener);
            }
        }, zzY()) == null) {
            BillingResult billingResultZzaa = zzaa();
            this.zzf.zza(zzbh.zza(25, 15, billingResultZzaa));
            alternativeBillingOnlyReportingDetailsListener.onAlternativeBillingOnlyTokenResponse(billingResultZzaa, null);
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void endConnection() {
        this.zzf.zzb(zzbh.zzb(12));
        try {
            try {
                if (this.zzd != null) {
                    this.zzd.zze();
                }
                if (this.zzh != null) {
                    this.zzh.zzc();
                }
                if (this.zzh != null && this.zzg != null) {
                    com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Unbinding from service.");
                    this.zze.unbindService(this.zzh);
                    this.zzh = null;
                }
                this.zzg = null;
                ExecutorService executorService = this.zzA;
                if (executorService != null) {
                    executorService.shutdownNow();
                    this.zzA = null;
                }
            } catch (Exception e) {
                com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "There was an exception while ending connection!", e);
            }
        } finally {
            this.zza = 3;
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void getBillingConfigAsync(GetBillingConfigParams getBillingConfigParams, final BillingConfigResponseListener billingConfigResponseListener) {
        if (!isReady()) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Service disconnected.");
            this.zzf.zza(zzbh.zza(2, 13, zzbk.zzm));
            billingConfigResponseListener.onBillingConfigResponse(zzbk.zzm, null);
        } else {
            if (!this.zzu) {
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Current client doesn't support get billing config.");
                this.zzf.zza(zzbh.zza(32, 13, zzbk.zzz));
                billingConfigResponseListener.onBillingConfigResponse(zzbk.zzz, null);
                return;
            }
            String str = this.zzb;
            final Bundle bundle = new Bundle();
            bundle.putString("playBillingLibraryVersion", str);
            if (zzac(new Callable() { // from class: com.android.billingclient.api.zzl
                @Override // java.util.concurrent.Callable
                public final Object call() throws Exception {
                    this.zza.zzm(bundle, billingConfigResponseListener);
                    return null;
                }
            }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzm
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zzQ(billingConfigResponseListener);
                }
            }, zzY()) == null) {
                BillingResult billingResultZzaa = zzaa();
                this.zzf.zza(zzbh.zza(25, 13, billingResultZzaa));
                billingConfigResponseListener.onBillingConfigResponse(billingResultZzaa, null);
            }
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final int getConnectionState() {
        return this.zza;
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void isAlternativeBillingOnlyAvailableAsync(final AlternativeBillingOnlyAvailabilityListener alternativeBillingOnlyAvailabilityListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 14, zzbk.zzm));
            alternativeBillingOnlyAvailabilityListener.onAlternativeBillingOnlyAvailabilityResponse(zzbk.zzm);
        } else if (!this.zzx) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Current client doesn't support alternative billing only.");
            this.zzf.zza(zzbh.zza(66, 14, zzbk.zzD));
            alternativeBillingOnlyAvailabilityListener.onAlternativeBillingOnlyAvailabilityResponse(zzbk.zzD);
        } else if (zzac(new Callable() { // from class: com.android.billingclient.api.zzt
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zzr(alternativeBillingOnlyAvailabilityListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzv
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzR(alternativeBillingOnlyAvailabilityListener);
            }
        }, zzY()) == null) {
            BillingResult billingResultZzaa = zzaa();
            this.zzf.zza(zzbh.zza(25, 14, billingResultZzaa));
            alternativeBillingOnlyAvailabilityListener.onAlternativeBillingOnlyAvailabilityResponse(billingResultZzaa);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c6  */
    @Override // com.android.billingclient.api.BillingClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.billingclient.api.BillingResult isFeatureSupported(java.lang.String r14) {
        /*
            Method dump skipped, instruction units count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.isFeatureSupported(java.lang.String):com.android.billingclient.api.BillingResult");
    }

    @Override // com.android.billingclient.api.BillingClient
    public final boolean isReady() {
        return (this.zza != 2 || this.zzg == null || this.zzh == null) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:152:0x03e2  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03ed  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0441  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0444  */
    @Override // com.android.billingclient.api.BillingClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.billingclient.api.BillingResult launchBillingFlow(android.app.Activity r32, final com.android.billingclient.api.BillingFlowParams r33) {
        /*
            Method dump skipped, instruction units count: 1328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.BillingClientImpl.launchBillingFlow(android.app.Activity, com.android.billingclient.api.BillingFlowParams):com.android.billingclient.api.BillingResult");
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void queryProductDetailsAsync(final QueryProductDetailsParams queryProductDetailsParams, final ProductDetailsResponseListener productDetailsResponseListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 7, zzbk.zzm));
            productDetailsResponseListener.onProductDetailsResponse(zzbk.zzm, new ArrayList());
        } else if (!this.zzt) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Querying product details is not supported.");
            this.zzf.zza(zzbh.zza(20, 7, zzbk.zzv));
            productDetailsResponseListener.onProductDetailsResponse(zzbk.zzv, new ArrayList());
        } else if (zzac(new Callable() { // from class: com.android.billingclient.api.zzu
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zzn(queryProductDetailsParams, productDetailsResponseListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzz
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzS(productDetailsResponseListener);
            }
        }, zzY()) == null) {
            BillingResult billingResultZzaa = zzaa();
            this.zzf.zza(zzbh.zza(25, 7, billingResultZzaa));
            productDetailsResponseListener.onProductDetailsResponse(billingResultZzaa, new ArrayList());
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void queryPurchaseHistoryAsync(QueryPurchaseHistoryParams queryPurchaseHistoryParams, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        zzad(queryPurchaseHistoryParams.zza(), purchaseHistoryResponseListener);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void queryPurchasesAsync(QueryPurchasesParams queryPurchasesParams, PurchasesResponseListener purchasesResponseListener) {
        zzae(queryPurchasesParams.zza(), purchasesResponseListener);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void querySkuDetailsAsync(SkuDetailsParams skuDetailsParams, final SkuDetailsResponseListener skuDetailsResponseListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 8, zzbk.zzm));
            skuDetailsResponseListener.onSkuDetailsResponse(zzbk.zzm, null);
            return;
        }
        final String skuType = skuDetailsParams.getSkuType();
        final List<String> skusList = skuDetailsParams.getSkusList();
        if (TextUtils.isEmpty(skuType)) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Please fix the input params. SKU type can't be empty.");
            this.zzf.zza(zzbh.zza(49, 8, zzbk.zzf));
            skuDetailsResponseListener.onSkuDetailsResponse(zzbk.zzf, null);
        } else if (skusList == null) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Please fix the input params. The list of SKUs can't be empty.");
            this.zzf.zza(zzbh.zza(48, 8, zzbk.zze));
            skuDetailsResponseListener.onSkuDetailsResponse(zzbk.zze, null);
        } else {
            final String str = null;
            if (zzac(new Callable(skuType, skusList, str, skuDetailsResponseListener) { // from class: com.android.billingclient.api.zzq
                public final /* synthetic */ String zzb;
                public final /* synthetic */ List zzc;
                public final /* synthetic */ SkuDetailsResponseListener zzd;

                {
                    this.zzd = skuDetailsResponseListener;
                }

                @Override // java.util.concurrent.Callable
                public final Object call() throws Exception {
                    this.zza.zzo(this.zzb, this.zzc, null, this.zzd);
                    return null;
                }
            }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzr
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zzV(skuDetailsResponseListener);
                }
            }, zzY()) == null) {
                BillingResult billingResultZzaa = zzaa();
                this.zzf.zza(zzbh.zza(25, 8, billingResultZzaa));
                skuDetailsResponseListener.onSkuDetailsResponse(billingResultZzaa, null);
            }
        }
    }

    @Override // com.android.billingclient.api.BillingClient
    public final BillingResult showAlternativeBillingOnlyInformationDialog(final Activity activity, final AlternativeBillingOnlyInformationDialogListener alternativeBillingOnlyInformationDialogListener) {
        if (!isReady()) {
            this.zzf.zza(zzbh.zza(2, 16, zzbk.zzm));
            return zzbk.zzm;
        }
        if (!this.zzx) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Current Play Store version doesn't support alternative billing only.");
            this.zzf.zza(zzbh.zza(66, 16, zzbk.zzD));
            return zzbk.zzD;
        }
        final zzak zzakVar = new zzak(this, this.zzc, alternativeBillingOnlyInformationDialogListener);
        if (zzac(new Callable() { // from class: com.android.billingclient.api.zzad
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zzs(activity, zzakVar, alternativeBillingOnlyInformationDialogListener);
                return null;
            }
        }, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, new Runnable() { // from class: com.android.billingclient.api.zzae
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzW(alternativeBillingOnlyInformationDialogListener);
            }
        }, this.zzc) != null) {
            return zzbk.zzl;
        }
        BillingResult billingResultZzaa = zzaa();
        this.zzf.zza(zzbh.zza(25, 16, billingResultZzaa));
        return billingResultZzaa;
    }

    @Override // com.android.billingclient.api.BillingClient
    public final BillingResult showInAppMessages(final Activity activity, InAppMessageParams inAppMessageParams, InAppMessageResponseListener inAppMessageResponseListener) {
        if (!isReady()) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Service disconnected.");
            return zzbk.zzm;
        }
        if (!this.zzp) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Current client doesn't support showing in-app messages.");
            return zzbk.zzw;
        }
        View viewFindViewById = activity.findViewById(R.id.content);
        IBinder windowToken = viewFindViewById.getWindowToken();
        Rect rect = new Rect();
        viewFindViewById.getGlobalVisibleRect(rect);
        final Bundle bundle = new Bundle();
        BundleCompat.putBinder(bundle, "KEY_WINDOW_TOKEN", windowToken);
        bundle.putInt("KEY_DIMEN_LEFT", rect.left);
        bundle.putInt("KEY_DIMEN_TOP", rect.top);
        bundle.putInt("KEY_DIMEN_RIGHT", rect.right);
        bundle.putInt("KEY_DIMEN_BOTTOM", rect.bottom);
        bundle.putString("playBillingLibraryVersion", this.zzb);
        bundle.putIntegerArrayList("KEY_CATEGORY_IDS", inAppMessageParams.zza());
        final zzaj zzajVar = new zzaj(this, this.zzc, inAppMessageResponseListener);
        zzac(new Callable() { // from class: com.android.billingclient.api.zzaa
            @Override // java.util.concurrent.Callable
            public final Object call() throws Exception {
                this.zza.zzp(bundle, activity, zzajVar);
                return null;
            }
        }, 5000L, null, this.zzc);
        return zzbk.zzl;
    }

    final /* synthetic */ void zzM(AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) {
        this.zzf.zza(zzbh.zza(24, 3, zzbk.zzn));
        acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbk.zzn);
    }

    final /* synthetic */ void zzN(BillingResult billingResult) {
        if (this.zzd.zzd() != null) {
            this.zzd.zzd().onPurchasesUpdated(billingResult, null);
        } else {
            this.zzd.zzc();
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "No valid listener is set in BroadcastManager");
        }
    }

    final /* synthetic */ void zzO(ConsumeResponseListener consumeResponseListener, ConsumeParams consumeParams) {
        this.zzf.zza(zzbh.zza(24, 4, zzbk.zzn));
        consumeResponseListener.onConsumeResponse(zzbk.zzn, consumeParams.getPurchaseToken());
    }

    final /* synthetic */ void zzP(AlternativeBillingOnlyReportingDetailsListener alternativeBillingOnlyReportingDetailsListener) {
        this.zzf.zza(zzbh.zza(24, 15, zzbk.zzn));
        alternativeBillingOnlyReportingDetailsListener.onAlternativeBillingOnlyTokenResponse(zzbk.zzn, null);
    }

    final /* synthetic */ void zzQ(BillingConfigResponseListener billingConfigResponseListener) {
        this.zzf.zza(zzbh.zza(24, 13, zzbk.zzn));
        billingConfigResponseListener.onBillingConfigResponse(zzbk.zzn, null);
    }

    final /* synthetic */ void zzR(AlternativeBillingOnlyAvailabilityListener alternativeBillingOnlyAvailabilityListener) {
        this.zzf.zza(zzbh.zza(24, 14, zzbk.zzn));
        alternativeBillingOnlyAvailabilityListener.onAlternativeBillingOnlyAvailabilityResponse(zzbk.zzn);
    }

    final /* synthetic */ void zzS(ProductDetailsResponseListener productDetailsResponseListener) {
        this.zzf.zza(zzbh.zza(24, 7, zzbk.zzn));
        productDetailsResponseListener.onProductDetailsResponse(zzbk.zzn, new ArrayList());
    }

    final /* synthetic */ void zzT(PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        this.zzf.zza(zzbh.zza(24, 11, zzbk.zzn));
        purchaseHistoryResponseListener.onPurchaseHistoryResponse(zzbk.zzn, null);
    }

    final /* synthetic */ void zzU(PurchasesResponseListener purchasesResponseListener) {
        this.zzf.zza(zzbh.zza(24, 9, zzbk.zzn));
        purchasesResponseListener.onQueryPurchasesResponse(zzbk.zzn, com.google.android.gms.internal.play_billing.zzaf.zzk());
    }

    final /* synthetic */ void zzV(SkuDetailsResponseListener skuDetailsResponseListener) {
        this.zzf.zza(zzbh.zza(24, 8, zzbk.zzn));
        skuDetailsResponseListener.onSkuDetailsResponse(zzbk.zzn, null);
    }

    final /* synthetic */ void zzW(AlternativeBillingOnlyInformationDialogListener alternativeBillingOnlyInformationDialogListener) {
        this.zzf.zza(zzbh.zza(24, 16, zzbk.zzn));
        alternativeBillingOnlyInformationDialogListener.onAlternativeBillingOnlyInformationDialogResponse(zzbk.zzn);
    }

    final /* synthetic */ Bundle zzc(int i, String str, String str2, BillingFlowParams billingFlowParams, Bundle bundle) throws Exception {
        return this.zzg.zzg(i, this.zze.getPackageName(), str, str2, null, bundle);
    }

    final /* synthetic */ Bundle zzd(String str, String str2) throws Exception {
        return this.zzg.zzf(3, this.zze.getPackageName(), str, str2, null);
    }

    final /* synthetic */ Object zzk(AcknowledgePurchaseParams acknowledgePurchaseParams, AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener) throws Exception {
        try {
            com.google.android.gms.internal.play_billing.zzm zzmVar = this.zzg;
            String packageName = this.zze.getPackageName();
            String purchaseToken = acknowledgePurchaseParams.getPurchaseToken();
            String str = this.zzb;
            Bundle bundle = new Bundle();
            bundle.putString("playBillingLibraryVersion", str);
            Bundle bundleZzd = zzmVar.zzd(9, packageName, purchaseToken, bundle);
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbk.zza(com.google.android.gms.internal.play_billing.zzb.zzb(bundleZzd, "BillingClient"), com.google.android.gms.internal.play_billing.zzb.zzg(bundleZzd, "BillingClient")));
            return null;
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Error acknowledge purchase!", e);
            this.zzf.zza(zzbh.zza(28, 3, zzbk.zzm));
            acknowledgePurchaseResponseListener.onAcknowledgePurchaseResponse(zzbk.zzm);
            return null;
        }
    }

    final /* synthetic */ Object zzl(ConsumeParams consumeParams, ConsumeResponseListener consumeResponseListener) throws Exception {
        int iZza;
        String strZzg;
        String purchaseToken = consumeParams.getPurchaseToken();
        try {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Consuming purchase with token: " + purchaseToken);
            if (this.zzn) {
                com.google.android.gms.internal.play_billing.zzm zzmVar = this.zzg;
                String packageName = this.zze.getPackageName();
                boolean z = this.zzn;
                String str = this.zzb;
                Bundle bundle = new Bundle();
                if (z) {
                    bundle.putString("playBillingLibraryVersion", str);
                }
                Bundle bundleZze = zzmVar.zze(9, packageName, purchaseToken, bundle);
                iZza = bundleZze.getInt("RESPONSE_CODE");
                strZzg = com.google.android.gms.internal.play_billing.zzb.zzg(bundleZze, "BillingClient");
            } else {
                iZza = this.zzg.zza(3, this.zze.getPackageName(), purchaseToken);
                strZzg = "";
            }
            BillingResult billingResultZza = zzbk.zza(iZza, strZzg);
            if (iZza == 0) {
                com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Successfully consumed purchase.");
                consumeResponseListener.onConsumeResponse(billingResultZza, purchaseToken);
                return null;
            }
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Error consuming purchase with token. Response code: " + iZza);
            this.zzf.zza(zzbh.zza(23, 4, billingResultZza));
            consumeResponseListener.onConsumeResponse(billingResultZza, purchaseToken);
            return null;
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Error consuming purchase!", e);
            this.zzf.zza(zzbh.zza(29, 4, zzbk.zzm));
            consumeResponseListener.onConsumeResponse(zzbk.zzm, purchaseToken);
            return null;
        }
    }

    final /* synthetic */ Object zzm(Bundle bundle, BillingConfigResponseListener billingConfigResponseListener) throws Exception {
        try {
            this.zzg.zzo(18, this.zze.getPackageName(), bundle, new zzau(billingConfigResponseListener, this.zzf, null));
        } catch (DeadObjectException e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "getBillingConfig got a dead object exception (try to reconnect).", e);
            this.zzf.zza(zzbh.zza(62, 13, zzbk.zzm));
            billingConfigResponseListener.onBillingConfigResponse(zzbk.zzm, null);
        } catch (Exception e2) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "getBillingConfig got an exception.", e2);
            this.zzf.zza(zzbh.zza(62, 13, zzbk.zzj));
            billingConfigResponseListener.onBillingConfigResponse(zzbk.zzj, null);
        }
        return null;
    }

    final /* synthetic */ Object zzn(QueryProductDetailsParams queryProductDetailsParams, ProductDetailsResponseListener productDetailsResponseListener) throws Exception {
        String strZzg;
        int iZzb;
        int i;
        int i2;
        com.google.android.gms.internal.play_billing.zzm zzmVar;
        int i3;
        String packageName;
        Bundle bundle;
        com.google.android.gms.internal.play_billing.zzaf zzafVar;
        ArrayList arrayList = new ArrayList();
        String strZzb = queryProductDetailsParams.zzb();
        com.google.android.gms.internal.play_billing.zzaf zzafVarZza = queryProductDetailsParams.zza();
        int size = zzafVarZza.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size) {
                strZzg = "";
                iZzb = 0;
                break;
            }
            int i5 = i4 + 20;
            ArrayList arrayList2 = new ArrayList(zzafVarZza.subList(i4, i5 > size ? size : i5));
            ArrayList<String> arrayList3 = new ArrayList<>();
            int size2 = arrayList2.size();
            for (int i6 = 0; i6 < size2; i6++) {
                arrayList3.add(((QueryProductDetailsParams.Product) arrayList2.get(i6)).zza());
            }
            Bundle bundle2 = new Bundle();
            bundle2.putStringArrayList("ITEM_ID_LIST", arrayList3);
            bundle2.putString("playBillingLibraryVersion", this.zzb);
            try {
                zzmVar = this.zzg;
                i3 = true != this.zzw ? 17 : 20;
                packageName = this.zze.getPackageName();
                String str = this.zzb;
                if (TextUtils.isEmpty(null)) {
                    this.zze.getPackageName();
                }
                bundle = new Bundle();
                bundle.putString("playBillingLibraryVersion", str);
                bundle.putBoolean("enablePendingPurchases", true);
                bundle.putString("SKU_DETAILS_RESPONSE_FORMAT", "PRODUCT_DETAILS");
                ArrayList<String> arrayList4 = new ArrayList<>();
                ArrayList<String> arrayList5 = new ArrayList<>();
                int size3 = arrayList2.size();
                zzafVar = zzafVarZza;
                int i7 = 0;
                boolean z = false;
                boolean z2 = false;
                while (i7 < size3) {
                    QueryProductDetailsParams.Product product = (QueryProductDetailsParams.Product) arrayList2.get(i7);
                    ArrayList arrayList6 = arrayList2;
                    arrayList4.add(null);
                    z |= !TextUtils.isEmpty(null);
                    String strZzb2 = product.zzb();
                    int i8 = size3;
                    if (strZzb2.equals("first_party")) {
                        com.google.android.gms.internal.play_billing.zzx.zzc(null, "Serialized DocId is required for constructing ExtraParams to query ProductDetails for all first party products.");
                        arrayList5.add(null);
                        z2 = true;
                    }
                    i7++;
                    size3 = i8;
                    arrayList2 = arrayList6;
                }
                if (z) {
                    bundle.putStringArrayList("SKU_OFFER_ID_TOKEN_LIST", arrayList4);
                }
                if (!arrayList5.isEmpty()) {
                    bundle.putStringArrayList("SKU_SERIALIZED_DOCID_LIST", arrayList5);
                }
                if (z2 && !TextUtils.isEmpty(null)) {
                    bundle.putString("accountName", null);
                }
                i2 = 7;
            } catch (Exception e) {
                e = e;
                i = 6;
                i2 = 7;
            }
            try {
                Bundle bundleZzl = zzmVar.zzl(i3, packageName, strZzb, bundle2, bundle);
                strZzg = "Item is unavailable for purchase.";
                if (bundleZzl == null) {
                    com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "queryProductDetailsAsync got empty product details response.");
                    this.zzf.zza(zzbh.zza(44, 7, zzbk.zzB));
                    break;
                }
                if (bundleZzl.containsKey("DETAILS_LIST")) {
                    ArrayList<String> stringArrayList = bundleZzl.getStringArrayList("DETAILS_LIST");
                    if (stringArrayList == null) {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "queryProductDetailsAsync got null response list");
                        this.zzf.zza(zzbh.zza(46, 7, zzbk.zzB));
                        break;
                    }
                    for (int i9 = 0; i9 < stringArrayList.size(); i9++) {
                        try {
                            ProductDetails productDetails = new ProductDetails(stringArrayList.get(i9));
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Got product details: ".concat(productDetails.toString()));
                            arrayList.add(productDetails);
                        } catch (JSONException e2) {
                            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Got a JSON exception trying to decode ProductDetails. \n Exception: ", e2);
                            strZzg = "Error trying to decode SkuDetails.";
                            i = 6;
                            this.zzf.zza(zzbh.zza(47, 7, zzbk.zza(6, "Error trying to decode SkuDetails.")));
                            iZzb = i;
                            productDetailsResponseListener.onProductDetailsResponse(zzbk.zza(iZzb, strZzg), arrayList);
                            return null;
                        }
                    }
                    i4 = i5;
                    zzafVarZza = zzafVar;
                } else {
                    iZzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundleZzl, "BillingClient");
                    strZzg = com.google.android.gms.internal.play_billing.zzb.zzg(bundleZzl, "BillingClient");
                    if (iZzb != 0) {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "getSkuDetails() failed for queryProductDetailsAsync. Response code: " + iZzb);
                        this.zzf.zza(zzbh.zza(23, 7, zzbk.zza(iZzb, strZzg)));
                    } else {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "getSkuDetails() returned a bundle with neither an error nor a product detail list for queryProductDetailsAsync.");
                        this.zzf.zza(zzbh.zza(45, 7, zzbk.zza(6, strZzg)));
                        iZzb = 6;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                i = 6;
                com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "queryProductDetailsAsync got a remote exception (try to reconnect).", e);
                this.zzf.zza(zzbh.zza(43, i2, zzbk.zzj));
                strZzg = "An internal error occurred.";
                iZzb = i;
                productDetailsResponseListener.onProductDetailsResponse(zzbk.zza(iZzb, strZzg), arrayList);
                return null;
            }
        }
        iZzb = 4;
        productDetailsResponseListener.onProductDetailsResponse(zzbk.zza(iZzb, strZzg), arrayList);
        return null;
    }

    final /* synthetic */ Object zzo(String str, List list, String str2, SkuDetailsResponseListener skuDetailsResponseListener) throws Exception {
        String strZzg;
        int i;
        Bundle bundleZzk;
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                strZzg = "";
                i = 0;
                break;
            }
            int i3 = i2 + 20;
            ArrayList<String> arrayList2 = new ArrayList<>(list.subList(i2, i3 > size ? size : i3));
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("ITEM_ID_LIST", arrayList2);
            bundle.putString("playBillingLibraryVersion", this.zzb);
            try {
                if (this.zzo) {
                    com.google.android.gms.internal.play_billing.zzm zzmVar = this.zzg;
                    String packageName = this.zze.getPackageName();
                    int i4 = this.zzk;
                    String str3 = this.zzb;
                    Bundle bundle2 = new Bundle();
                    if (i4 >= 9) {
                        bundle2.putString("playBillingLibraryVersion", str3);
                    }
                    if (i4 >= 9) {
                        bundle2.putBoolean("enablePendingPurchases", true);
                    }
                    bundleZzk = zzmVar.zzl(10, packageName, str, bundle, bundle2);
                } else {
                    bundleZzk = this.zzg.zzk(3, this.zze.getPackageName(), str, bundle);
                }
                strZzg = "Item is unavailable for purchase.";
                if (bundleZzk == null) {
                    com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "querySkuDetailsAsync got null sku details list");
                    this.zzf.zza(zzbh.zza(44, 8, zzbk.zzB));
                    break;
                }
                if (bundleZzk.containsKey("DETAILS_LIST")) {
                    ArrayList<String> stringArrayList = bundleZzk.getStringArrayList("DETAILS_LIST");
                    if (stringArrayList == null) {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "querySkuDetailsAsync got null response list");
                        this.zzf.zza(zzbh.zza(46, 8, zzbk.zzB));
                        break;
                    }
                    for (int i5 = 0; i5 < stringArrayList.size(); i5++) {
                        try {
                            SkuDetails skuDetails = new SkuDetails(stringArrayList.get(i5));
                            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Got sku details: ".concat(skuDetails.toString()));
                            arrayList.add(skuDetails);
                        } catch (JSONException e) {
                            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Got a JSON exception trying to decode SkuDetails.", e);
                            strZzg = "Error trying to decode SkuDetails.";
                            this.zzf.zza(zzbh.zza(47, 8, zzbk.zza(6, "Error trying to decode SkuDetails.")));
                            arrayList = null;
                            i = 6;
                            skuDetailsResponseListener.onSkuDetailsResponse(zzbk.zza(i, strZzg), arrayList);
                            return null;
                        }
                    }
                    i2 = i3;
                } else {
                    int iZzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundleZzk, "BillingClient");
                    strZzg = com.google.android.gms.internal.play_billing.zzb.zzg(bundleZzk, "BillingClient");
                    if (iZzb != 0) {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "getSkuDetails() failed. Response code: " + iZzb);
                        this.zzf.zza(zzbh.zza(23, 8, zzbk.zza(iZzb, strZzg)));
                        i = iZzb;
                    } else {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "getSkuDetails() returned a bundle with neither an error nor a detail list.");
                        this.zzf.zza(zzbh.zza(45, 8, zzbk.zza(6, strZzg)));
                    }
                }
            } catch (Exception e2) {
                com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "querySkuDetailsAsync got a remote exception (try to reconnect).", e2);
                this.zzf.zza(zzbh.zza(43, 8, zzbk.zzm));
                strZzg = "Service connection is disconnected.";
                i = -1;
                arrayList = null;
            }
        }
        arrayList = null;
        i = 4;
        skuDetailsResponseListener.onSkuDetailsResponse(zzbk.zza(i, strZzg), arrayList);
        return null;
    }

    final /* synthetic */ Object zzp(Bundle bundle, Activity activity, ResultReceiver resultReceiver) throws Exception {
        this.zzg.zzq(12, this.zze.getPackageName(), bundle, new zzay(new WeakReference(activity), resultReceiver, null));
        return null;
    }

    final /* synthetic */ Void zzq(AlternativeBillingOnlyReportingDetailsListener alternativeBillingOnlyReportingDetailsListener) throws Exception {
        try {
            this.zzg.zzm(21, this.zze.getPackageName(), new Bundle(), new zzaq(alternativeBillingOnlyReportingDetailsListener, this.zzf, null));
        } catch (Exception unused) {
            this.zzf.zza(zzbh.zza(70, 15, zzbk.zzj));
            alternativeBillingOnlyReportingDetailsListener.onAlternativeBillingOnlyTokenResponse(zzbk.zzj, null);
        }
        return null;
    }

    final /* synthetic */ Void zzr(AlternativeBillingOnlyAvailabilityListener alternativeBillingOnlyAvailabilityListener) throws Exception {
        try {
            this.zzg.zzp(21, this.zze.getPackageName(), new Bundle(), new zzaw(alternativeBillingOnlyAvailabilityListener, this.zzf, null));
        } catch (Exception unused) {
            this.zzf.zza(zzbh.zza(69, 14, zzbk.zzj));
            alternativeBillingOnlyAvailabilityListener.onAlternativeBillingOnlyAvailabilityResponse(zzbk.zzj);
        }
        return null;
    }

    final /* synthetic */ Void zzs(Activity activity, ResultReceiver resultReceiver, AlternativeBillingOnlyInformationDialogListener alternativeBillingOnlyInformationDialogListener) throws Exception {
        try {
            this.zzg.zzn(21, this.zze.getPackageName(), new Bundle(), new zzas(new WeakReference(activity), resultReceiver, this.zzf, null));
        } catch (Exception unused) {
            this.zzf.zza(zzbh.zza(74, 16, zzbk.zzj));
            alternativeBillingOnlyInformationDialogListener.onAlternativeBillingOnlyInformationDialogResponse(zzbk.zzj);
        }
        return null;
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void queryPurchaseHistoryAsync(String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        zzad(str, purchaseHistoryResponseListener);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void queryPurchasesAsync(String str, PurchasesResponseListener purchasesResponseListener) {
        zzae(str, purchasesResponseListener);
    }

    private BillingClientImpl(Context context, zzbx zzbxVar, PurchasesUpdatedListener purchasesUpdatedListener, String str, String str2, UserChoiceBillingListener userChoiceBillingListener, zzbi zzbiVar, ExecutorService executorService) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = str;
        initialize(context, purchasesUpdatedListener, zzbxVar, userChoiceBillingListener, str, (zzbi) null);
    }

    private BillingClientImpl(String str) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = str;
    }

    BillingClientImpl(String str, Context context, zzbi zzbiVar, ExecutorService executorService) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        String strZzab = zzab();
        this.zzb = strZzab;
        this.zze = context.getApplicationContext();
        zzin zzinVarZzv = zzio.zzv();
        zzinVarZzv.zzj(strZzab);
        zzinVarZzv.zzi(this.zze.getPackageName());
        this.zzf = new zzbn(this.zze, (zzio) zzinVarZzv.zzc());
        this.zze.getPackageName();
    }

    private void initialize(Context context, PurchasesUpdatedListener purchasesUpdatedListener, zzbx zzbxVar, UserChoiceBillingListener userChoiceBillingListener, String str, zzbi zzbiVar) {
        this.zze = context.getApplicationContext();
        zzin zzinVarZzv = zzio.zzv();
        zzinVarZzv.zzj(str);
        zzinVarZzv.zzi(this.zze.getPackageName());
        if (zzbiVar != null) {
            this.zzf = zzbiVar;
        } else {
            this.zzf = new zzbn(this.zze, (zzio) zzinVarZzv.zzc());
        }
        if (purchasesUpdatedListener == null) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Billing client should have a valid listener but the provided is null.");
        }
        this.zzd = new zzh(this.zze, purchasesUpdatedListener, userChoiceBillingListener, this.zzf);
        this.zzy = zzbxVar;
        this.zzz = userChoiceBillingListener != null;
    }

    BillingClientImpl(String str, zzbx zzbxVar, Context context, zzbq zzbqVar, zzbi zzbiVar, ExecutorService executorService) {
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = zzab();
        this.zze = context.getApplicationContext();
        zzin zzinVarZzv = zzio.zzv();
        zzinVarZzv.zzj(zzab());
        zzinVarZzv.zzi(this.zze.getPackageName());
        this.zzf = new zzbn(this.zze, (zzio) zzinVarZzv.zzc());
        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Billing client should have a valid listener but the provided is null.");
        this.zzd = new zzh(this.zze, null, this.zzf);
        this.zzy = zzbxVar;
        this.zze.getPackageName();
    }

    BillingClientImpl(String str, zzbx zzbxVar, Context context, PurchasesUpdatedListener purchasesUpdatedListener, AlternativeBillingListener alternativeBillingListener, zzbi zzbiVar, ExecutorService executorService) {
        String strZzab = zzab();
        this.zza = 0;
        this.zzc = new Handler(Looper.getMainLooper());
        this.zzk = 0;
        this.zzb = strZzab;
        initialize(context, purchasesUpdatedListener, zzbxVar, alternativeBillingListener, strZzab, (zzbi) null);
    }

    BillingClientImpl(String str, zzbx zzbxVar, Context context, PurchasesUpdatedListener purchasesUpdatedListener, UserChoiceBillingListener userChoiceBillingListener, zzbi zzbiVar, ExecutorService executorService) {
        this(context, zzbxVar, purchasesUpdatedListener, zzab(), null, userChoiceBillingListener, null, null);
    }

    @Override // com.android.billingclient.api.BillingClient
    public final void startConnection(BillingClientStateListener billingClientStateListener) {
        if (isReady()) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Service connection is valid. No need to re-initialize.");
            this.zzf.zzb(zzbh.zzb(6));
            billingClientStateListener.onBillingSetupFinished(zzbk.zzl);
            return;
        }
        int i = 1;
        if (this.zza == 1) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Client is already in the process of connecting to billing service.");
            this.zzf.zza(zzbh.zza(37, 6, zzbk.zzd));
            billingClientStateListener.onBillingSetupFinished(zzbk.zzd);
            return;
        }
        if (this.zza == 3) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Client was already closed and can't be reused. Please create another instance.");
            this.zzf.zza(zzbh.zza(38, 6, zzbk.zzm));
            billingClientStateListener.onBillingSetupFinished(zzbk.zzm);
            return;
        }
        this.zza = 1;
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Starting in-app billing setup.");
        this.zzh = new zzao(this, billingClientStateListener, null);
        Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        intent.setPackage("com.android.vending");
        List<ResolveInfo> listQueryIntentServices = this.zze.getPackageManager().queryIntentServices(intent, 0);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            i = 41;
        } else {
            ResolveInfo resolveInfo = listQueryIntentServices.get(0);
            if (resolveInfo.serviceInfo != null) {
                String str = resolveInfo.serviceInfo.packageName;
                String str2 = resolveInfo.serviceInfo.name;
                if (!"com.android.vending".equals(str) || str2 == null) {
                    com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "The device doesn't have valid Play Store.");
                    i = 40;
                } else {
                    ComponentName componentName = new ComponentName(str, str2);
                    Intent intent2 = new Intent(intent);
                    intent2.setComponent(componentName);
                    intent2.putExtra("playBillingLibraryVersion", this.zzb);
                    if (this.zze.bindService(intent2, this.zzh, 1)) {
                        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Service was bonded successfully.");
                        return;
                    } else {
                        com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Connection to Billing service is blocked.");
                        i = 39;
                    }
                }
            }
        }
        this.zza = 0;
        com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "Billing service unavailable on device.");
        this.zzf.zza(zzbh.zza(i, 6, zzbk.zzc));
        billingClientStateListener.onBillingSetupFinished(zzbk.zzc);
    }
}
