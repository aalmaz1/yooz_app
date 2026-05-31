package io.flutter.plugins.inapppurchase;

import android.content.Context;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.UserChoiceBillingListener;
import com.android.billingclient.api.UserChoiceDetails;
import io.flutter.Log;
import io.flutter.plugins.inapppurchase.Messages;

/* JADX INFO: loaded from: classes2.dex */
final class BillingClientFactoryImpl implements BillingClientFactory {
    BillingClientFactoryImpl() {
    }

    /* JADX INFO: renamed from: io.flutter.plugins.inapppurchase.BillingClientFactoryImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformBillingChoiceMode;

        static {
            int[] iArr = new int[Messages.PlatformBillingChoiceMode.values().length];
            $SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformBillingChoiceMode = iArr;
            try {
                iArr[Messages.PlatformBillingChoiceMode.ALTERNATIVE_BILLING_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformBillingChoiceMode[Messages.PlatformBillingChoiceMode.USER_CHOICE_BILLING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformBillingChoiceMode[Messages.PlatformBillingChoiceMode.PLAY_BILLING_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // io.flutter.plugins.inapppurchase.BillingClientFactory
    public BillingClient createBillingClient(Context context, Messages.InAppPurchaseCallbackApi inAppPurchaseCallbackApi, Messages.PlatformBillingChoiceMode platformBillingChoiceMode) {
        BillingClient.Builder builderEnablePendingPurchases = BillingClient.newBuilder(context).enablePendingPurchases();
        int i = AnonymousClass2.$SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformBillingChoiceMode[platformBillingChoiceMode.ordinal()];
        if (i == 1) {
            builderEnablePendingPurchases.enableAlternativeBillingOnly();
        } else if (i == 2) {
            builderEnablePendingPurchases.enableUserChoiceBilling(createUserChoiceBillingListener(inAppPurchaseCallbackApi));
        } else if (i != 3) {
            Log.e("BillingClientFactoryImpl", "Unknown BillingChoiceMode " + platformBillingChoiceMode + ", Defaulting to PLAY_BILLING_ONLY");
        }
        return builderEnablePendingPurchases.setListener(new PluginPurchaseListener(inAppPurchaseCallbackApi)).build();
    }

    UserChoiceBillingListener createUserChoiceBillingListener(final Messages.InAppPurchaseCallbackApi inAppPurchaseCallbackApi) {
        return new UserChoiceBillingListener() { // from class: io.flutter.plugins.inapppurchase.BillingClientFactoryImpl$$ExternalSyntheticLambda0
            @Override // com.android.billingclient.api.UserChoiceBillingListener
            public final void userSelectedAlternativeBilling(UserChoiceDetails userChoiceDetails) {
                this.f$0.m676xf850cf4b(inAppPurchaseCallbackApi, userChoiceDetails);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$createUserChoiceBillingListener$0$io-flutter-plugins-inapppurchase-BillingClientFactoryImpl, reason: not valid java name */
    /* synthetic */ void m676xf850cf4b(Messages.InAppPurchaseCallbackApi inAppPurchaseCallbackApi, UserChoiceDetails userChoiceDetails) {
        inAppPurchaseCallbackApi.userSelectedalternativeBilling(Translator.fromUserChoiceDetails(userChoiceDetails), new Messages.VoidResult() { // from class: io.flutter.plugins.inapppurchase.BillingClientFactoryImpl.1
            @Override // io.flutter.plugins.inapppurchase.Messages.VoidResult
            public void success() {
            }

            @Override // io.flutter.plugins.inapppurchase.Messages.VoidResult
            public void error(Throwable th) {
                Log.e("IN_APP_PURCHASE", "userSelectedalternativeBilling handler error: " + th);
            }
        });
    }
}
