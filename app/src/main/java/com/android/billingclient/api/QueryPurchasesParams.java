package com.android.billingclient.api;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
public final class QueryPurchasesParams {
    private final String zza;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
    public static class Builder {
        private String zza;

        private Builder() {
        }

        /* synthetic */ Builder(zzcn zzcnVar) {
        }

        public QueryPurchasesParams build() {
            if (this.zza != null) {
                return new QueryPurchasesParams(this, null);
            }
            throw new IllegalArgumentException("Product type must be set");
        }

        public Builder setProductType(String str) {
            this.zza = str;
            return this;
        }
    }

    /* synthetic */ QueryPurchasesParams(Builder builder, zzco zzcoVar) {
        this.zza = builder.zza;
    }

    public static Builder newBuilder() {
        return new Builder(null);
    }

    public final String zza() {
        return this.zza;
    }
}
