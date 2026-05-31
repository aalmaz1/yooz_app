package com.google.android.gms.internal.play_billing;

import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzhk extends zzhm {
    zzhk(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(this.zza.getLong(obj, j));
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(this.zza.getInt(obj, j));
    }

    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.play_billing.zzhn.zzi(java.lang.Object, long, boolean):void */
    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.play_billing.zzhn.zzj(java.lang.Object, long, boolean):void */
    /* JADX WARN: Method inline failed with exception
    java.lang.ArrayIndexOutOfBoundsException: Index -1 out of bounds for length 2
    	at java.base/java.util.ArrayList.shiftTailOverGap(ArrayList.java:831)
    	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1782)
    	at java.base/java.util.ArrayList.removeIf(ArrayList.java:1751)
    	at jadx.core.dex.instructions.args.SSAVar.removeUse(SSAVar.java:139)
    	at jadx.core.utils.InsnRemover.unbindArgUsage(InsnRemover.java:170)
    	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:137)
    	at jadx.core.dex.regions.conditions.IfCondition.replaceArg(IfCondition.java:270)
    	at jadx.core.dex.instructions.mods.TernaryInsn.replaceArg(TernaryInsn.java:67)
    	at jadx.core.dex.nodes.InsnNode.replaceArg(InsnNode.java:141)
    	at jadx.core.dex.visitors.InlineMethods.replaceRegs(InlineMethods.java:127)
    	at jadx.core.dex.visitors.InlineMethods.inlineMethod(InlineMethods.java:86)
    	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:78)
    	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:50)
     */
    /* JADX WARN: Unknown register number '(r5v0 boolean)' in method call: com.google.android.gms.internal.play_billing.zzhn.zzj(java.lang.Object, long, boolean):void */
    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zzc(Object obj, long j, boolean z) {
        if (zzhn.zzb) {
            zzhn.zzi(obj, j, z);
        } else {
            zzhn.zzj(obj, j, z);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zzd(Object obj, long j, byte b) {
        if (zzhn.zzb) {
            zzhn.zzD(obj, j, b);
        } else {
            zzhn.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zze(Object obj, long j, double d) {
        this.zza.putLong(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zzf(Object obj, long j, float f) {
        this.zza.putInt(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final boolean zzg(Object obj, long j) {
        return zzhn.zzb ? zzhn.zzt(obj, j) : zzhn.zzu(obj, j);
    }
}
