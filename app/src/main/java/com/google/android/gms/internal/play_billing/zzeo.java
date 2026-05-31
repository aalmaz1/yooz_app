package com.google.android.gms.internal.play_billing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzeo {
    private static final zzeo zzb = new zzeo(true);
    final zzgz zza = new zzgp(16);
    private boolean zzc;
    private boolean zzd;

    private zzeo() {
    }

    public static zzeo zza() {
        throw null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static final void zzd(com.google.android.gms.internal.play_billing.zzen r4, java.lang.Object r5) {
        /*
            com.google.android.gms.internal.play_billing.zzht r0 = r4.zzb()
            byte[] r1 = com.google.android.gms.internal.play_billing.zzfd.zzd
            r5.getClass()
            com.google.android.gms.internal.play_billing.zzht r1 = com.google.android.gms.internal.play_billing.zzht.DOUBLE
            com.google.android.gms.internal.play_billing.zzhu r1 = com.google.android.gms.internal.play_billing.zzhu.INT
            com.google.android.gms.internal.play_billing.zzhu r0 = r0.zza()
            int r0 = r0.ordinal()
            switch(r0) {
                case 0: goto L43;
                case 1: goto L40;
                case 2: goto L3d;
                case 3: goto L3a;
                case 4: goto L37;
                case 5: goto L34;
                case 6: goto L2b;
                case 7: goto L22;
                case 8: goto L19;
                default: goto L18;
            }
        L18:
            goto L48
        L19:
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzgc
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzfh
            if (r0 == 0) goto L48
            goto L47
        L22:
            boolean r0 = r5 instanceof java.lang.Integer
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzez
            if (r0 == 0) goto L48
            goto L47
        L2b:
            boolean r0 = r5 instanceof com.google.android.gms.internal.play_billing.zzdw
            if (r0 != 0) goto L47
            boolean r0 = r5 instanceof byte[]
            if (r0 == 0) goto L48
            goto L47
        L34:
            boolean r0 = r5 instanceof java.lang.String
            goto L45
        L37:
            boolean r0 = r5 instanceof java.lang.Boolean
            goto L45
        L3a:
            boolean r0 = r5 instanceof java.lang.Double
            goto L45
        L3d:
            boolean r0 = r5 instanceof java.lang.Float
            goto L45
        L40:
            boolean r0 = r5 instanceof java.lang.Long
            goto L45
        L43:
            boolean r0 = r5 instanceof java.lang.Integer
        L45:
            if (r0 == 0) goto L48
        L47:
            return
        L48:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            int r2 = r4.zza()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r3 = 0
            r1[r3] = r2
            com.google.android.gms.internal.play_billing.zzht r4 = r4.zzb()
            com.google.android.gms.internal.play_billing.zzhu r4 = r4.zza()
            r2 = 1
            r1[r2] = r4
            java.lang.Class r4 = r5.getClass()
            java.lang.String r4 = r4.getName()
            r5 = 2
            r1[r5] = r4
            java.lang.String r4 = "Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n"
            java.lang.String r4 = java.lang.String.format(r4, r1)
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzeo.zzd(com.google.android.gms.internal.play_billing.zzen, java.lang.Object):void");
    }

    public final /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzeo zzeoVar = new zzeo();
        for (int i = 0; i < this.zza.zzb(); i++) {
            Map.Entry entryZzg = this.zza.zzg(i);
            zzeoVar.zzc((zzen) entryZzg.getKey(), entryZzg.getValue());
        }
        for (Map.Entry entry : this.zza.zzc()) {
            zzeoVar.zzc((zzen) entry.getKey(), entry.getValue());
        }
        zzeoVar.zzd = this.zzd;
        return zzeoVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzeo) {
            return this.zza.equals(((zzeo) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final void zzb() {
        if (this.zzc) {
            return;
        }
        for (int i = 0; i < this.zza.zzb(); i++) {
            Map.Entry entryZzg = this.zza.zzg(i);
            if (entryZzg.getValue() instanceof zzex) {
                ((zzex) entryZzg.getValue()).zzn();
            }
        }
        this.zza.zza();
        this.zzc = true;
    }

    public final void zzc(zzen zzenVar, Object obj) {
        if (!zzenVar.zzc()) {
            zzd(zzenVar, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                zzd(zzenVar, arrayList.get(i));
            }
            obj = arrayList;
        }
        if (obj instanceof zzfh) {
            this.zzd = true;
        }
        this.zza.put(zzenVar, obj);
    }

    private zzeo(boolean z) {
        zzb();
        zzb();
    }
}
