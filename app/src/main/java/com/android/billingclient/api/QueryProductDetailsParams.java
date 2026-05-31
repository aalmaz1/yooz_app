package com.android.billingclient.api;

import java.util.HashSet;
import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
public final class QueryProductDetailsParams {
    private final com.google.android.gms.internal.play_billing.zzaf zza;

    /* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
    public static class Builder {
        private com.google.android.gms.internal.play_billing.zzaf zza;

        private Builder() {
        }

        /* synthetic */ Builder(zzch zzchVar) {
        }

        public QueryProductDetailsParams build() {
            return new QueryProductDetailsParams(this, null);
        }

        public Builder setProductList(List<Product> list) {
            if (list == null || list.isEmpty()) {
                throw new IllegalArgumentException("Product list cannot be empty.");
            }
            HashSet hashSet = new HashSet();
            for (Product product : list) {
                if (!"play_pass_subs".equals(product.zzb())) {
                    hashSet.add(product.zzb());
                }
            }
            if (hashSet.size() > 1) {
                throw new IllegalArgumentException("All products should be of the same product type.");
            }
            this.zza = com.google.android.gms.internal.play_billing.zzaf.zzj(list);
            return this;
        }
    }

    /* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
    public static class Product {
        private final String zza;
        private final String zzb;

        /* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
        public static class Builder {
            private String zza;
            private String zzb;

            private Builder() {
            }

            /* synthetic */ Builder(zzci zzciVar) {
            }

            public Product build() {
                if ("first_party".equals(this.zzb)) {
                    throw new IllegalArgumentException("Serialized doc id must be provided for first party products.");
                }
                if (this.zza == null) {
                    throw new IllegalArgumentException("Product id must be provided.");
                }
                if (this.zzb != null) {
                    return new Product(this, null);
                }
                throw new IllegalArgumentException("Product type must be provided.");
            }

            public Builder setProductId(String str) {
                this.zza = str;
                return this;
            }

            public Builder setProductType(String str) {
                this.zzb = str;
                return this;
            }
        }

        /* synthetic */ Product(Builder builder, zzcj zzcjVar) {
            this.zza = builder.zza;
            this.zzb = builder.zzb;
        }

        public static Builder newBuilder() {
            return new Builder(null);
        }

        public final String zza() {
            return this.zza;
        }

        public final String zzb() {
            return this.zzb;
        }
    }

    /* synthetic */ QueryProductDetailsParams(Builder builder, zzck zzckVar) {
        this.zza = builder.zza;
    }

    public static Builder newBuilder() {
        return new Builder(null);
    }

    public final com.google.android.gms.internal.play_billing.zzaf zza() {
        return this.zza;
    }

    public final String zzb() {
        return ((Product) this.zza.get(0)).zzb();
    }
}
