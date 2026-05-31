package com.google.android.gms.internal.play_billing;

import com.google.android.gms.internal.play_billing.zzet;
import com.google.android.gms.internal.play_billing.zzex;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public class zzet<MessageType extends zzex<MessageType, BuilderType>, BuilderType extends zzet<MessageType, BuilderType>> extends zzdf<MessageType, BuilderType> {
    protected zzex zza;
    private final zzex zzb;

    protected zzet(MessageType messagetype) {
        this.zzb = messagetype;
        if (messagetype.zzt()) {
            throw new IllegalArgumentException("Default instance must be immutable.");
        }
        this.zza = messagetype.zzi();
    }

    @Override // com.google.android.gms.internal.play_billing.zzdf
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzet clone() {
        zzet zzetVar = (zzet) this.zzb.zzu(5, null, null);
        zzetVar.zza = zze();
        return zzetVar;
    }

    public final MessageType zzc() {
        MessageType messagetype = (MessageType) zze();
        if (messagetype.zzs()) {
            return messagetype;
        }
        throw new zzhc(messagetype);
    }

    @Override // com.google.android.gms.internal.play_billing.zzgb
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public MessageType zze() {
        if (!this.zza.zzt()) {
            return (MessageType) this.zza;
        }
        this.zza.zzn();
        return (MessageType) this.zza;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgd
    public final /* bridge */ /* synthetic */ zzgc zzf() {
        throw null;
    }

    protected final void zzg() {
        if (this.zza.zzt()) {
            return;
        }
        zzh();
    }

    protected void zzh() {
        zzex zzexVarZzi = this.zzb.zzi();
        zzgk.zza().zzb(zzexVarZzi.getClass()).zzg(zzexVarZzi, this.zza);
        this.zza = zzexVarZzi;
    }
}
