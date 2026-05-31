package com.android.billingclient.api;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.ResultReceiver;
import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
public class ProxyBillingActivity extends ComponentActivity {
    static final String KEY_ALTERNATIVE_BILLING_ONLY_DIALOG_RESULT_RECEIVER = "alternative_billing_only_dialog_result_receiver";
    static final String KEY_IN_APP_MESSAGE_RESULT_RECEIVER = "in_app_message_result_receiver";
    static final String KEY_PRICE_CHANGE_RESULT_RECEIVER = "result_receiver";
    private static final String KEY_SEND_CANCELLED_BROADCAST_IF_FINISHED = "send_cancelled_broadcast_if_finished";
    private static final int REQUEST_CODE_FIRST_PARTY_PURCHASE_FLOW = 110;
    private static final int REQUEST_CODE_IN_APP_MESSAGE_FLOW = 101;
    private static final int REQUEST_CODE_LAUNCH_ACTIVITY = 100;
    private static final String TAG = "ProxyBillingActivity";
    private ActivityResultLauncher<IntentSenderRequest> alternativeBillingOnlyDialogLauncher;
    private ResultReceiver alternativeBillingOnlyDialogResultReceiver;
    private ResultReceiver inAppMessageResultReceiver;
    private boolean isFlowFromFirstPartyClient;
    private ResultReceiver priceChangeResultReceiver;
    private boolean sendCancelledBroadcastIfFinished;

    private Intent makeAlternativeBillingIntent(String str) {
        Intent intent = new Intent("com.android.vending.billing.ALTERNATIVE_BILLING");
        intent.setPackage(getApplicationContext().getPackageName());
        intent.putExtra("ALTERNATIVE_BILLING_USER_CHOICE_DATA", str);
        return intent;
    }

    private Intent makePurchasesUpdatedIntent() {
        getApplicationContext().getPackageName();
        Intent intent = new Intent("com.android.vending.billing.PURCHASES_UPDATED");
        intent.setPackage(getApplicationContext().getPackageName());
        return intent;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0076  */
    @Override // androidx.activity.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onActivityResult(int r8, int r9, android.content.Intent r10) {
        /*
            Method dump skipped, instruction units count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.billingclient.api.ProxyBillingActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    void onAlternativeBillingOnlyDialogResult(ActivityResult activityResult) {
        Intent data = activityResult.getData();
        int iZzc = com.google.android.gms.internal.play_billing.zzb.zzc(data, TAG);
        ResultReceiver resultReceiver = this.alternativeBillingOnlyDialogResultReceiver;
        if (resultReceiver != null) {
            resultReceiver.send(iZzc, data == null ? null : data.getExtras());
        }
        if (activityResult.getResultCode() != -1 || iZzc != 0) {
            com.google.android.gms.internal.play_billing.zzb.zzk(TAG, "Alternative billing only dialog finished with resultCode " + activityResult.getResultCode() + " and billing's responseCode: " + iZzc);
        }
        finish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        int i;
        PendingIntent pendingIntent;
        int i2;
        super.onCreate(bundle);
        this.alternativeBillingOnlyDialogLauncher = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback() { // from class: com.android.billingclient.api.zzcd
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.zza.onAlternativeBillingOnlyDialogResult((ActivityResult) obj);
            }
        });
        if (bundle != null) {
            com.google.android.gms.internal.play_billing.zzb.zzj(TAG, "Launching Play Store billing flow from savedInstanceState");
            this.sendCancelledBroadcastIfFinished = bundle.getBoolean(KEY_SEND_CANCELLED_BROADCAST_IF_FINISHED, false);
            if (bundle.containsKey(KEY_PRICE_CHANGE_RESULT_RECEIVER)) {
                this.priceChangeResultReceiver = (ResultReceiver) bundle.getParcelable(KEY_PRICE_CHANGE_RESULT_RECEIVER);
            } else if (bundle.containsKey(KEY_IN_APP_MESSAGE_RESULT_RECEIVER)) {
                this.inAppMessageResultReceiver = (ResultReceiver) bundle.getParcelable(KEY_IN_APP_MESSAGE_RESULT_RECEIVER);
            } else if (bundle.containsKey(KEY_ALTERNATIVE_BILLING_ONLY_DIALOG_RESULT_RECEIVER)) {
                this.alternativeBillingOnlyDialogResultReceiver = (ResultReceiver) bundle.getParcelable(KEY_ALTERNATIVE_BILLING_ONLY_DIALOG_RESULT_RECEIVER);
            }
            this.isFlowFromFirstPartyClient = bundle.getBoolean("IS_FLOW_FROM_FIRST_PARTY_CLIENT", false);
            return;
        }
        com.google.android.gms.internal.play_billing.zzb.zzj(TAG, "Launching Play Store billing flow");
        if (getIntent().hasExtra("ALTERNATIVE_BILLING_ONLY_DIALOG_INTENT")) {
            PendingIntent pendingIntent2 = (PendingIntent) getIntent().getParcelableExtra("ALTERNATIVE_BILLING_ONLY_DIALOG_INTENT");
            this.alternativeBillingOnlyDialogResultReceiver = (ResultReceiver) getIntent().getParcelableExtra(KEY_ALTERNATIVE_BILLING_ONLY_DIALOG_RESULT_RECEIVER);
            this.alternativeBillingOnlyDialogLauncher.launch(new IntentSenderRequest.Builder(pendingIntent2).build());
            return;
        }
        if (getIntent().hasExtra("BUY_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("BUY_INTENT");
            if (getIntent().hasExtra("IS_FLOW_FROM_FIRST_PARTY_CLIENT") && getIntent().getBooleanExtra("IS_FLOW_FROM_FIRST_PARTY_CLIENT", false)) {
                this.isFlowFromFirstPartyClient = true;
                i2 = 110;
                i = i2;
            }
            i = 100;
        } else if (getIntent().hasExtra("SUBS_MANAGEMENT_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("SUBS_MANAGEMENT_INTENT");
            this.priceChangeResultReceiver = (ResultReceiver) getIntent().getParcelableExtra(KEY_PRICE_CHANGE_RESULT_RECEIVER);
            i = 100;
        } else if (getIntent().hasExtra("IN_APP_MESSAGE_INTENT")) {
            pendingIntent = (PendingIntent) getIntent().getParcelableExtra("IN_APP_MESSAGE_INTENT");
            this.inAppMessageResultReceiver = (ResultReceiver) getIntent().getParcelableExtra(KEY_IN_APP_MESSAGE_RESULT_RECEIVER);
            i2 = 101;
            i = i2;
        } else {
            i = 100;
            pendingIntent = null;
        }
        try {
            this.sendCancelledBroadcastIfFinished = true;
            startIntentSenderForResult(pendingIntent.getIntentSender(), i, new Intent(), 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            com.google.android.gms.internal.play_billing.zzb.zzl(TAG, "Got exception while trying to start a purchase flow.", e);
            ResultReceiver resultReceiver = this.priceChangeResultReceiver;
            if (resultReceiver != null) {
                resultReceiver.send(6, null);
            } else {
                ResultReceiver resultReceiver2 = this.inAppMessageResultReceiver;
                if (resultReceiver2 != null) {
                    resultReceiver2.send(0, null);
                } else {
                    Intent intentMakePurchasesUpdatedIntent = makePurchasesUpdatedIntent();
                    if (this.isFlowFromFirstPartyClient) {
                        intentMakePurchasesUpdatedIntent.putExtra("IS_FIRST_PARTY_PURCHASE", true);
                    }
                    intentMakePurchasesUpdatedIntent.putExtra("RESPONSE_CODE", 6);
                    intentMakePurchasesUpdatedIntent.putExtra("DEBUG_MESSAGE", "An internal error occurred.");
                    sendBroadcast(intentMakePurchasesUpdatedIntent);
                }
            }
            this.sendCancelledBroadcastIfFinished = false;
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() && this.sendCancelledBroadcastIfFinished) {
            Intent intentMakePurchasesUpdatedIntent = makePurchasesUpdatedIntent();
            intentMakePurchasesUpdatedIntent.putExtra("RESPONSE_CODE", 1);
            intentMakePurchasesUpdatedIntent.putExtra("DEBUG_MESSAGE", "Billing dialog closed.");
            sendBroadcast(intentMakePurchasesUpdatedIntent);
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        ResultReceiver resultReceiver = this.priceChangeResultReceiver;
        if (resultReceiver != null) {
            bundle.putParcelable(KEY_PRICE_CHANGE_RESULT_RECEIVER, resultReceiver);
        }
        ResultReceiver resultReceiver2 = this.inAppMessageResultReceiver;
        if (resultReceiver2 != null) {
            bundle.putParcelable(KEY_IN_APP_MESSAGE_RESULT_RECEIVER, resultReceiver2);
        }
        ResultReceiver resultReceiver3 = this.alternativeBillingOnlyDialogResultReceiver;
        if (resultReceiver3 != null) {
            bundle.putParcelable(KEY_ALTERNATIVE_BILLING_ONLY_DIALOG_RESULT_RECEIVER, resultReceiver3);
        }
        bundle.putBoolean(KEY_SEND_CANCELLED_BROADCAST_IF_FINISHED, this.sendCancelledBroadcastIfFinished);
        bundle.putBoolean("IS_FLOW_FROM_FIRST_PARTY_CLIENT", this.isFlowFromFirstPartyClient);
    }
}
