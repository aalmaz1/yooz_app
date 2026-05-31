package io.flutter.plugins.inapppurchase;

import android.content.Context;
import com.android.billingclient.api.BillingClient;
import io.flutter.plugins.inapppurchase.Messages;

/* JADX INFO: loaded from: classes2.dex */
interface BillingClientFactory {
    BillingClient createBillingClient(Context context, Messages.InAppPurchaseCallbackApi inAppPurchaseCallbackApi, Messages.PlatformBillingChoiceMode platformBillingChoiceMode);
}
