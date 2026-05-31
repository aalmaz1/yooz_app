package com.android.billingclient.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
public final class UserChoiceDetails {
    private final String zza;
    private final JSONObject zzb;
    private final List zzc;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
    public static class Product {
        private final String zza;
        private final String zzb;
        private final String zzc;

        /* synthetic */ Product(JSONObject jSONObject, zzcq zzcqVar) {
            this.zza = jSONObject.optString("productId");
            this.zzb = jSONObject.optString("productType");
            String strOptString = jSONObject.optString("offerToken");
            this.zzc = true == strOptString.isEmpty() ? null : strOptString;
        }

        public boolean equals(Object obj) {
            String str;
            String offerToken;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Product)) {
                return false;
            }
            Product product = (Product) obj;
            return this.zza.equals(product.getId()) && this.zzb.equals(product.getType()) && ((str = this.zzc) == (offerToken = product.getOfferToken()) || (str != null && str.equals(offerToken)));
        }

        public String getId() {
            return this.zza;
        }

        public String getOfferToken() {
            return this.zzc;
        }

        public String getType() {
            return this.zzb;
        }

        public int hashCode() {
            return Arrays.hashCode(new Object[]{this.zza, this.zzb, this.zzc});
        }

        public String toString() {
            return String.format("{id: %s, type: %s, offer token: %s}", this.zza, this.zzb, this.zzc);
        }
    }

    UserChoiceDetails(String str) throws JSONException {
        this.zza = str;
        JSONObject jSONObject = new JSONObject(str);
        this.zzb = jSONObject;
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("products");
        ArrayList arrayList = new ArrayList();
        if (jSONArrayOptJSONArray != null) {
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i);
                if (jSONObjectOptJSONObject != null) {
                    arrayList.add(new Product(jSONObjectOptJSONObject, null));
                }
            }
        }
        this.zzc = arrayList;
    }

    public String getExternalTransactionToken() {
        return this.zzb.optString("externalTransactionToken");
    }

    public String getOriginalExternalTransactionId() {
        String strOptString = this.zzb.optString("originalExternalTransactionId");
        if (strOptString.isEmpty()) {
            return null;
        }
        return strOptString;
    }

    public List<Product> getProducts() {
        return this.zzc;
    }
}
