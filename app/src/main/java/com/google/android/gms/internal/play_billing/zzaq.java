package com.google.android.gms.internal.play_billing;

import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import java.util.Arrays;
import javax.annotation.CheckForNull;
import kotlin.UByte;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzaq extends zzai {
    static final zzai zza = new zzaq(null, new Object[0], 0);
    final transient Object[] zzb;

    @CheckForNull
    private final transient Object zzc;
    private final transient int zzd;

    private zzaq(@CheckForNull Object obj, Object[] objArr, int i) {
        this.zzc = obj;
        this.zzb = objArr;
        this.zzd = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v24 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v34 */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r5v4, types: [int[]] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r6v4 */
    static zzaq zzf(int i, Object[] objArr, zzah zzahVar) {
        int iHighestOneBit;
        short[] sArr;
        ?? r2;
        ?? r6;
        int i2 = i;
        Object[] objArrCopyOf = objArr;
        if (i2 == 0) {
            return (zzaq) zza;
        }
        zzag zzagVar = null;
        ?? r22 = 0;
        zzag zzagVar2 = null;
        zzag zzagVar3 = null;
        if (i2 == 1) {
            Object obj = objArrCopyOf[0];
            obj.getClass();
            Object obj2 = objArrCopyOf[1];
            obj2.getClass();
            zzaa.zza(obj, obj2);
            return new zzaq(null, objArrCopyOf, 1);
        }
        zzx.zzb(i2, objArrCopyOf.length >> 1, "index");
        char c = 2;
        int iMax = Math.max(i2, 2);
        if (iMax < 751619276) {
            iHighestOneBit = Integer.highestOneBit(iMax - 1);
            do {
                iHighestOneBit += iHighestOneBit;
            } while (((double) iHighestOneBit) * 0.7d < iMax);
        } else {
            iHighestOneBit = BasicMeasure.EXACTLY;
            if (iMax >= 1073741824) {
                throw new IllegalArgumentException("collection too large");
            }
        }
        if (i2 == 1) {
            Object obj3 = objArrCopyOf[0];
            obj3.getClass();
            Object obj4 = objArrCopyOf[1];
            obj4.getClass();
            zzaa.zza(obj3, obj4);
            i2 = 1;
        } else {
            int i3 = iHighestOneBit - 1;
            byte b = -1;
            if (iHighestOneBit <= 128) {
                byte[] bArr = new byte[iHighestOneBit];
                Arrays.fill(bArr, (byte) -1);
                int i4 = 0;
                for (int i5 = 0; i5 < i2; i5++) {
                    int i6 = i4 + i4;
                    int i7 = i5 + i5;
                    Object obj5 = objArrCopyOf[i7];
                    obj5.getClass();
                    Object obj6 = objArrCopyOf[i7 ^ 1];
                    obj6.getClass();
                    zzaa.zza(obj5, obj6);
                    int iZza = zzab.zza(obj5.hashCode());
                    while (true) {
                        int i8 = iZza & i3;
                        int i9 = bArr[i8] & UByte.MAX_VALUE;
                        if (i9 == 255) {
                            bArr[i8] = (byte) i6;
                            if (i4 < i5) {
                                objArrCopyOf[i6] = obj5;
                                objArrCopyOf[i6 ^ 1] = obj6;
                            }
                            i4++;
                        } else {
                            if (obj5.equals(objArrCopyOf[i9 == true ? 1 : 0])) {
                                int i10 = ~i9;
                                Object obj7 = objArrCopyOf[i10 == true ? 1 : 0];
                                obj7.getClass();
                                zzag zzagVar4 = new zzag(obj5, obj6, obj7);
                                objArrCopyOf[i10 == true ? 1 : 0] = obj6;
                                zzagVar2 = zzagVar4;
                                break;
                            }
                            iZza = i8 + 1;
                        }
                    }
                }
                if (i4 == i2) {
                    r2 = bArr;
                    c = 2;
                    r22 = r2;
                } else {
                    r22 = new Object[]{bArr, Integer.valueOf(i4), zzagVar2};
                    c = 2;
                }
            } else if (iHighestOneBit <= 32768) {
                sArr = new short[iHighestOneBit];
                Arrays.fill(sArr, (short) -1);
                int i11 = 0;
                for (int i12 = 0; i12 < i2; i12++) {
                    int i13 = i11 + i11;
                    int i14 = i12 + i12;
                    Object obj8 = objArrCopyOf[i14];
                    obj8.getClass();
                    Object obj9 = objArrCopyOf[i14 ^ 1];
                    obj9.getClass();
                    zzaa.zza(obj8, obj9);
                    int iZza2 = zzab.zza(obj8.hashCode());
                    while (true) {
                        int i15 = iZza2 & i3;
                        char c2 = (char) sArr[i15];
                        if (c2 == 65535) {
                            sArr[i15] = (short) i13;
                            if (i11 < i12) {
                                objArrCopyOf[i13] = obj8;
                                objArrCopyOf[i13 ^ 1] = obj9;
                            }
                            i11++;
                        } else {
                            if (obj8.equals(objArrCopyOf[c2])) {
                                int i16 = c2 ^ 1;
                                Object obj10 = objArrCopyOf[i16 == true ? 1 : 0];
                                obj10.getClass();
                                zzag zzagVar5 = new zzag(obj8, obj9, obj10);
                                objArrCopyOf[i16 == true ? 1 : 0] = obj9;
                                zzagVar3 = zzagVar5;
                                break;
                            }
                            iZza2 = i15 + 1;
                        }
                    }
                }
                if (i11 != i2) {
                    c = 2;
                    r6 = new Object[]{sArr, Integer.valueOf(i11), zzagVar3};
                    r22 = r6;
                }
                r2 = sArr;
                c = 2;
                r22 = r2;
            } else {
                sArr = new int[iHighestOneBit];
                Arrays.fill((int[]) sArr, -1);
                int i17 = 0;
                int i18 = 0;
                while (i17 < i2) {
                    int i19 = i18 + i18;
                    int i20 = i17 + i17;
                    Object obj11 = objArrCopyOf[i20];
                    obj11.getClass();
                    Object obj12 = objArrCopyOf[i20 ^ 1];
                    obj12.getClass();
                    zzaa.zza(obj11, obj12);
                    int iZza3 = zzab.zza(obj11.hashCode());
                    while (true) {
                        int i21 = iZza3 & i3;
                        ?? r15 = sArr[i21];
                        if (r15 == b) {
                            sArr[i21] = i19;
                            if (i18 < i17) {
                                objArrCopyOf[i19] = obj11;
                                objArrCopyOf[i19 ^ 1] = obj12;
                            }
                            i18++;
                        } else {
                            if (obj11.equals(objArrCopyOf[r15])) {
                                int i22 = r15 ^ 1;
                                Object obj13 = objArrCopyOf[i22 == true ? 1 : 0];
                                obj13.getClass();
                                zzag zzagVar6 = new zzag(obj11, obj12, obj13);
                                objArrCopyOf[i22 == true ? 1 : 0] = obj12;
                                zzagVar = zzagVar6;
                                break;
                            }
                            iZza3 = i21 + 1;
                            b = -1;
                        }
                    }
                    i17++;
                    b = -1;
                }
                if (i18 != i2) {
                    c = 2;
                    r6 = new Object[]{sArr, Integer.valueOf(i18), zzagVar};
                    r22 = r6;
                }
                r2 = sArr;
                c = 2;
                r22 = r2;
            }
        }
        boolean z = r22 instanceof Object[];
        ?? r23 = r22;
        if (z) {
            Object[] objArr2 = (Object[]) r22;
            zzahVar.zzc = (zzag) objArr2[c];
            Object obj14 = objArr2[0];
            int iIntValue = ((Integer) objArr2[1]).intValue();
            objArrCopyOf = Arrays.copyOf(objArrCopyOf, iIntValue + iIntValue);
            r23 = obj14;
            i2 = iIntValue;
        }
        return new zzaq(r23, objArrCopyOf, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0003  */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0003 A[EDGE_INSN: B:44:0x0003->B:4:0x0003 BREAK  A[LOOP:0: B:16:0x0038->B:22:0x004e], EDGE_INSN: B:46:0x0003->B:4:0x0003 BREAK  A[LOOP:1: B:26:0x0063->B:32:0x007a], EDGE_INSN: B:48:0x0003->B:4:0x0003 BREAK  A[LOOP:2: B:34:0x0089->B:43:0x00a0]] */
    @Override // com.google.android.gms.internal.play_billing.zzai, java.util.Map
    @javax.annotation.CheckForNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object get(@javax.annotation.CheckForNull java.lang.Object r10) {
        /*
            r9 = this;
            r0 = 0
            if (r10 != 0) goto L6
        L3:
            r10 = r0
            goto L9c
        L6:
            int r1 = r9.zzd
            java.lang.Object[] r2 = r9.zzb
            r3 = 1
            if (r1 != r3) goto L20
            r1 = 0
            r1 = r2[r1]
            r1.getClass()
            boolean r10 = r1.equals(r10)
            if (r10 == 0) goto L3
            r10 = r2[r3]
            r10.getClass()
            goto L9c
        L20:
            java.lang.Object r1 = r9.zzc
            if (r1 != 0) goto L25
            goto L3
        L25:
            boolean r4 = r1 instanceof byte[]
            r5 = -1
            if (r4 == 0) goto L51
            r4 = r1
            byte[] r4 = (byte[]) r4
            int r1 = r4.length
            int r6 = r1 + (-1)
            int r1 = r10.hashCode()
            int r1 = com.google.android.gms.internal.play_billing.zzab.zza(r1)
        L38:
            r1 = r1 & r6
            r5 = r4[r1]
            r7 = 255(0xff, float:3.57E-43)
            r5 = r5 & r7
            if (r5 != r7) goto L41
            goto L3
        L41:
            r7 = r2[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L4e
            r10 = r5 ^ 1
            r10 = r2[r10]
            goto L9c
        L4e:
            int r1 = r1 + 1
            goto L38
        L51:
            boolean r4 = r1 instanceof short[]
            if (r4 == 0) goto L7d
            r4 = r1
            short[] r4 = (short[]) r4
            int r1 = r4.length
            int r6 = r1 + (-1)
            int r1 = r10.hashCode()
            int r1 = com.google.android.gms.internal.play_billing.zzab.zza(r1)
        L63:
            r1 = r1 & r6
            short r5 = r4[r1]
            char r5 = (char) r5
            r7 = 65535(0xffff, float:9.1834E-41)
            if (r5 != r7) goto L6d
            goto L3
        L6d:
            r7 = r2[r5]
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L7a
            r10 = r5 ^ 1
            r10 = r2[r10]
            goto L9c
        L7a:
            int r1 = r1 + 1
            goto L63
        L7d:
            int[] r1 = (int[]) r1
            int r4 = r1.length
            int r4 = r4 + r5
            int r6 = r10.hashCode()
            int r6 = com.google.android.gms.internal.play_billing.zzab.zza(r6)
        L89:
            r6 = r6 & r4
            r7 = r1[r6]
            if (r7 != r5) goto L90
            goto L3
        L90:
            r8 = r2[r7]
            boolean r8 = r10.equals(r8)
            if (r8 == 0) goto La0
            r10 = r7 ^ 1
            r10 = r2[r10]
        L9c:
            if (r10 != 0) goto L9f
            return r0
        L9f:
            return r10
        La0:
            int r6 = r6 + 1
            goto L89
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzaq.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.play_billing.zzai
    final zzac zza() {
        return new zzap(this.zzb, 1, this.zzd);
    }

    @Override // com.google.android.gms.internal.play_billing.zzai
    final zzaj zzc() {
        return new zzan(this, this.zzb, 0, this.zzd);
    }

    @Override // com.google.android.gms.internal.play_billing.zzai
    final zzaj zzd() {
        return new zzao(this, new zzap(this.zzb, 0, this.zzd));
    }
}
