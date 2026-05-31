package com.android.billingclient.api;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
public final class AlternativeBillingOnlyReportingDetails {
    private final String zza;

    AlternativeBillingOnlyReportingDetails(String str) throws JSONException {
        this.zza = new JSONObject(str).optString("externalTransactionToken");
    }

    public String getExternalTransactionToken() {
        return this.zza;
    }
}
