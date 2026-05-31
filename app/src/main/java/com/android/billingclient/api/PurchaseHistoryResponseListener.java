package com.android.billingclient.api;

import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
public interface PurchaseHistoryResponseListener {
    void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> list);
}
