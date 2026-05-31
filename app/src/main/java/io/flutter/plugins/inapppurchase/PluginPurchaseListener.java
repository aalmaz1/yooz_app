package io.flutter.plugins.inapppurchase;

import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import io.flutter.Log;
import io.flutter.plugins.inapppurchase.Messages;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
class PluginPurchaseListener implements PurchasesUpdatedListener {
    private final Messages.InAppPurchaseCallbackApi callbackApi;

    PluginPurchaseListener(Messages.InAppPurchaseCallbackApi inAppPurchaseCallbackApi) {
        this.callbackApi = inAppPurchaseCallbackApi;
    }

    @Override // com.android.billingclient.api.PurchasesUpdatedListener
    public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> list) {
        this.callbackApi.onPurchasesUpdated(new Messages.PlatformPurchasesResponse.Builder().setBillingResult(Translator.fromBillingResult(billingResult)).setPurchases(Translator.fromPurchasesList(list)).build(), new Messages.VoidResult() { // from class: io.flutter.plugins.inapppurchase.PluginPurchaseListener.1
            @Override // io.flutter.plugins.inapppurchase.Messages.VoidResult
            public void success() {
            }

            @Override // io.flutter.plugins.inapppurchase.Messages.VoidResult
            public void error(Throwable th) {
                Log.e("IN_APP_PURCHASE", "onPurchaseUpdated handler error: " + th);
            }
        });
    }
}
