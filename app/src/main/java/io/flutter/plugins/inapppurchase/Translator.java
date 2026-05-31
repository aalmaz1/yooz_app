package io.flutter.plugins.inapppurchase;

import com.android.billingclient.api.AccountIdentifiers;
import com.android.billingclient.api.AlternativeBillingOnlyReportingDetails;
import com.android.billingclient.api.BillingConfig;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.UserChoiceDetails;
import io.flutter.plugins.inapppurchase.Messages;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
class Translator {
    Translator() {
    }

    static Messages.PlatformProductDetails fromProductDetail(ProductDetails productDetails) {
        return new Messages.PlatformProductDetails.Builder().setTitle(productDetails.getTitle()).setDescription(productDetails.getDescription()).setProductId(productDetails.getProductId()).setProductType(toPlatformProductType(productDetails.getProductType())).setName(productDetails.getName()).setOneTimePurchaseOfferDetails(fromOneTimePurchaseOfferDetails(productDetails.getOneTimePurchaseOfferDetails())).setSubscriptionOfferDetails(fromSubscriptionOfferDetailsList(productDetails.getSubscriptionOfferDetails())).build();
    }

    static List<QueryProductDetailsParams.Product> toProductList(List<Messages.PlatformQueryProduct> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<Messages.PlatformQueryProduct> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(toProduct(it.next()));
        }
        return arrayList;
    }

    static QueryProductDetailsParams.Product toProduct(Messages.PlatformQueryProduct platformQueryProduct) {
        return QueryProductDetailsParams.Product.newBuilder().setProductId(platformQueryProduct.getProductId()).setProductType(toProductTypeString(platformQueryProduct.getProductType())).build();
    }

    /* JADX INFO: renamed from: io.flutter.plugins.inapppurchase.Translator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformProductType;

        static {
            int[] iArr = new int[Messages.PlatformProductType.values().length];
            $SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformProductType = iArr;
            try {
                iArr[Messages.PlatformProductType.INAPP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformProductType[Messages.PlatformProductType.SUBS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    static String toProductTypeString(Messages.PlatformProductType platformProductType) {
        int i = AnonymousClass1.$SwitchMap$io$flutter$plugins$inapppurchase$Messages$PlatformProductType[platformProductType.ordinal()];
        if (i == 1) {
            return "inapp";
        }
        if (i == 2) {
            return "subs";
        }
        throw new Messages.FlutterError("UNKNOWN_TYPE", "Unknown product type: " + platformProductType, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static io.flutter.plugins.inapppurchase.Messages.PlatformProductType toPlatformProductType(java.lang.String r3) {
        /*
            int r0 = r3.hashCode()
            r1 = 3541555(0x360a33, float:4.962776E-39)
            r2 = 2
            if (r0 == r1) goto L1a
            r1 = 100343516(0x5fb1edc, float:2.3615263E-35)
            if (r0 == r1) goto L10
            goto L24
        L10:
            java.lang.String r0 = "inapp"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L24
            r3 = 0
            goto L25
        L1a:
            java.lang.String r0 = "subs"
            boolean r3 = r3.equals(r0)
            if (r3 == 0) goto L24
            r3 = r2
            goto L25
        L24:
            r3 = -1
        L25:
            if (r3 == r2) goto L2a
            io.flutter.plugins.inapppurchase.Messages$PlatformProductType r3 = io.flutter.plugins.inapppurchase.Messages.PlatformProductType.INAPP
            return r3
        L2a:
            io.flutter.plugins.inapppurchase.Messages$PlatformProductType r3 = io.flutter.plugins.inapppurchase.Messages.PlatformProductType.SUBS
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugins.inapppurchase.Translator.toPlatformProductType(java.lang.String):io.flutter.plugins.inapppurchase.Messages$PlatformProductType");
    }

    static List<Messages.PlatformProductDetails> fromProductDetailsList(List<ProductDetails> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<ProductDetails> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(fromProductDetail(it.next()));
        }
        return arrayList;
    }

    static Messages.PlatformOneTimePurchaseOfferDetails fromOneTimePurchaseOfferDetails(ProductDetails.OneTimePurchaseOfferDetails oneTimePurchaseOfferDetails) {
        if (oneTimePurchaseOfferDetails == null) {
            return null;
        }
        return new Messages.PlatformOneTimePurchaseOfferDetails.Builder().setPriceAmountMicros(Long.valueOf(oneTimePurchaseOfferDetails.getPriceAmountMicros())).setPriceCurrencyCode(oneTimePurchaseOfferDetails.getPriceCurrencyCode()).setFormattedPrice(oneTimePurchaseOfferDetails.getFormattedPrice()).build();
    }

    static List<Messages.PlatformSubscriptionOfferDetails> fromSubscriptionOfferDetailsList(List<ProductDetails.SubscriptionOfferDetails> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<ProductDetails.SubscriptionOfferDetails> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(fromSubscriptionOfferDetails(it.next()));
        }
        return arrayList;
    }

    static Messages.PlatformSubscriptionOfferDetails fromSubscriptionOfferDetails(ProductDetails.SubscriptionOfferDetails subscriptionOfferDetails) {
        return new Messages.PlatformSubscriptionOfferDetails.Builder().setOfferId(subscriptionOfferDetails.getOfferId()).setBasePlanId(subscriptionOfferDetails.getBasePlanId()).setOfferTags(subscriptionOfferDetails.getOfferTags()).setOfferToken(subscriptionOfferDetails.getOfferToken()).setPricingPhases(fromPricingPhases(subscriptionOfferDetails.getPricingPhases())).build();
    }

    static List<Messages.PlatformPricingPhase> fromPricingPhases(ProductDetails.PricingPhases pricingPhases) {
        ArrayList arrayList = new ArrayList();
        Iterator<ProductDetails.PricingPhase> it = pricingPhases.getPricingPhaseList().iterator();
        while (it.hasNext()) {
            arrayList.add(fromPricingPhase(it.next()));
        }
        return arrayList;
    }

    static Messages.PlatformPricingPhase fromPricingPhase(ProductDetails.PricingPhase pricingPhase) {
        return new Messages.PlatformPricingPhase.Builder().setFormattedPrice(pricingPhase.getFormattedPrice()).setPriceCurrencyCode(pricingPhase.getPriceCurrencyCode()).setPriceAmountMicros(Long.valueOf(pricingPhase.getPriceAmountMicros())).setBillingCycleCount(Long.valueOf(pricingPhase.getBillingCycleCount())).setBillingPeriod(pricingPhase.getBillingPeriod()).setRecurrenceMode(toPlatformRecurrenceMode(pricingPhase.getRecurrenceMode())).build();
    }

    static Messages.PlatformRecurrenceMode toPlatformRecurrenceMode(int i) {
        if (i == 1) {
            return Messages.PlatformRecurrenceMode.INFINITE_RECURRING;
        }
        if (i == 2) {
            return Messages.PlatformRecurrenceMode.FINITE_RECURRING;
        }
        if (i == 3) {
            return Messages.PlatformRecurrenceMode.NON_RECURRING;
        }
        return Messages.PlatformRecurrenceMode.NON_RECURRING;
    }

    static Messages.PlatformPurchaseState toPlatformPurchaseState(int i) {
        if (i == 0) {
            return Messages.PlatformPurchaseState.UNSPECIFIED;
        }
        if (i == 1) {
            return Messages.PlatformPurchaseState.PURCHASED;
        }
        if (i == 2) {
            return Messages.PlatformPurchaseState.PENDING;
        }
        return Messages.PlatformPurchaseState.UNSPECIFIED;
    }

    static Messages.PlatformPurchase fromPurchase(Purchase purchase) {
        Messages.PlatformPurchase.Builder quantity = new Messages.PlatformPurchase.Builder().setOrderId(purchase.getOrderId()).setPackageName(purchase.getPackageName()).setPurchaseTime(Long.valueOf(purchase.getPurchaseTime())).setPurchaseToken(purchase.getPurchaseToken()).setSignature(purchase.getSignature()).setProducts(purchase.getProducts()).setIsAutoRenewing(Boolean.valueOf(purchase.isAutoRenewing())).setOriginalJson(purchase.getOriginalJson()).setDeveloperPayload(purchase.getDeveloperPayload()).setIsAcknowledged(Boolean.valueOf(purchase.isAcknowledged())).setPurchaseState(toPlatformPurchaseState(purchase.getPurchaseState())).setQuantity(Long.valueOf(purchase.getQuantity()));
        AccountIdentifiers accountIdentifiers = purchase.getAccountIdentifiers();
        if (accountIdentifiers != null) {
            quantity.setAccountIdentifiers(new Messages.PlatformAccountIdentifiers.Builder().setObfuscatedAccountId(accountIdentifiers.getObfuscatedAccountId()).setObfuscatedProfileId(accountIdentifiers.getObfuscatedProfileId()).build());
        }
        return quantity.build();
    }

    static Messages.PlatformPurchaseHistoryRecord fromPurchaseHistoryRecord(PurchaseHistoryRecord purchaseHistoryRecord) {
        return new Messages.PlatformPurchaseHistoryRecord.Builder().setPurchaseTime(Long.valueOf(purchaseHistoryRecord.getPurchaseTime())).setPurchaseToken(purchaseHistoryRecord.getPurchaseToken()).setSignature(purchaseHistoryRecord.getSignature()).setProducts(purchaseHistoryRecord.getProducts()).setDeveloperPayload(purchaseHistoryRecord.getDeveloperPayload()).setOriginalJson(purchaseHistoryRecord.getOriginalJson()).setQuantity(Long.valueOf(purchaseHistoryRecord.getQuantity())).build();
    }

    static List<Messages.PlatformPurchase> fromPurchasesList(List<Purchase> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Purchase> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(fromPurchase(it.next()));
        }
        return arrayList;
    }

    static List<Messages.PlatformPurchaseHistoryRecord> fromPurchaseHistoryRecordList(List<PurchaseHistoryRecord> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<PurchaseHistoryRecord> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(fromPurchaseHistoryRecord(it.next()));
        }
        return arrayList;
    }

    static Messages.PlatformBillingResult fromBillingResult(BillingResult billingResult) {
        return new Messages.PlatformBillingResult.Builder().setResponseCode(Long.valueOf(billingResult.getResponseCode())).setDebugMessage(billingResult.getDebugMessage()).build();
    }

    static Messages.PlatformUserChoiceDetails fromUserChoiceDetails(UserChoiceDetails userChoiceDetails) {
        return new Messages.PlatformUserChoiceDetails.Builder().setExternalTransactionToken(userChoiceDetails.getExternalTransactionToken()).setOriginalExternalTransactionId(userChoiceDetails.getOriginalExternalTransactionId()).setProducts(fromUserChoiceProductsList(userChoiceDetails.getProducts())).build();
    }

    static List<Messages.PlatformUserChoiceProduct> fromUserChoiceProductsList(List<UserChoiceDetails.Product> list) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator<UserChoiceDetails.Product> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(fromUserChoiceProduct(it.next()));
        }
        return arrayList;
    }

    static Messages.PlatformUserChoiceProduct fromUserChoiceProduct(UserChoiceDetails.Product product) {
        return new Messages.PlatformUserChoiceProduct.Builder().setId(product.getId()).setOfferToken(product.getOfferToken()).setType(toPlatformProductType(product.getType())).build();
    }

    static Messages.PlatformBillingConfigResponse fromBillingConfig(BillingResult billingResult, BillingConfig billingConfig) {
        return new Messages.PlatformBillingConfigResponse.Builder().setBillingResult(fromBillingResult(billingResult)).setCountryCode(billingConfig == null ? "" : billingConfig.getCountryCode()).build();
    }

    static Messages.PlatformAlternativeBillingOnlyReportingDetailsResponse fromAlternativeBillingOnlyReportingDetails(BillingResult billingResult, AlternativeBillingOnlyReportingDetails alternativeBillingOnlyReportingDetails) {
        return new Messages.PlatformAlternativeBillingOnlyReportingDetailsResponse.Builder().setBillingResult(fromBillingResult(billingResult)).setExternalTransactionToken(alternativeBillingOnlyReportingDetails == null ? "" : alternativeBillingOnlyReportingDetails.getExternalTransactionToken()).build();
    }

    static String currencySymbolFromCode(String str) {
        return Currency.getInstance(str).getSymbol();
    }
}
