package com.google.android.gms.internal.auth;

import com.google.android.gms.internal.auth.zzes;
import com.google.android.gms.internal.auth.zzeu;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzeu<MessageType extends zzeu<MessageType, BuilderType>, BuilderType extends zzes<MessageType, BuilderType>> extends zzdp<MessageType, BuilderType> {
    private static final Map zzb = new ConcurrentHashMap();
    protected zzgz zzc = zzgz.zza();

    static zzeu zza(Class cls) {
        Map map = zzb;
        zzeu zzeuVar = (zzeu) map.get(cls);
        if (zzeuVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzeuVar = (zzeu) map.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzeuVar == null) {
            zzeuVar = (zzeu) ((zzeu) zzhi.zze(cls)).zzi(6, null, null);
            if (zzeuVar == null) {
                throw new IllegalStateException();
            }
            map.put(cls, zzeuVar);
        }
        return zzeuVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
    
        if (r2 != false) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static com.google.android.gms.internal.auth.zzeu zzb(com.google.android.gms.internal.auth.zzeu r4, byte[] r5) throws com.google.android.gms.internal.auth.zzfa {
        /*
            int r0 = r5.length
            com.google.android.gms.internal.auth.zzek r1 = com.google.android.gms.internal.auth.zzek.zza
            r2 = 0
            com.google.android.gms.internal.auth.zzeu r4 = zzj(r4, r5, r2, r0, r1)
            if (r4 == 0) goto L4c
            java.lang.Boolean r5 = java.lang.Boolean.TRUE
            boolean r5 = r5.booleanValue()
            r0 = 1
            r1 = 0
            java.lang.Object r2 = r4.zzi(r0, r1, r1)
            java.lang.Byte r2 = (java.lang.Byte) r2
            byte r2 = r2.byteValue()
            if (r2 != r0) goto L1f
            goto L4c
        L1f:
            if (r2 == 0) goto L3f
            com.google.android.gms.internal.auth.zzge r2 = com.google.android.gms.internal.auth.zzge.zza()
            java.lang.Class r3 = r4.getClass()
            com.google.android.gms.internal.auth.zzgh r2 = r2.zzb(r3)
            boolean r2 = r2.zzi(r4)
            if (r5 == 0) goto L3c
            if (r0 == r2) goto L37
            r5 = r1
            goto L38
        L37:
            r5 = r4
        L38:
            r0 = 2
            r4.zzi(r0, r5, r1)
        L3c:
            if (r2 == 0) goto L3f
            goto L4c
        L3f:
            com.google.android.gms.internal.auth.zzgx r5 = new com.google.android.gms.internal.auth.zzgx
            r5.<init>(r4)
            com.google.android.gms.internal.auth.zzfa r5 = r5.zza()
            r5.zze(r4)
            throw r5
        L4c:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzeu.zzb(com.google.android.gms.internal.auth.zzeu, byte[]):com.google.android.gms.internal.auth.zzeu");
    }

    protected static zzey zzc() {
        return zzgf.zze();
    }

    static Object zze(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static Object zzf(zzfw zzfwVar, String str, Object[] objArr) {
        return new zzgg(zzfwVar, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001a", objArr);
    }

    protected static void zzg(Class cls, zzeu zzeuVar) {
        zzb.put(cls, zzeuVar);
    }

    private static zzeu zzj(zzeu zzeuVar, byte[] bArr, int i, int i2, zzek zzekVar) throws zzfa {
        zzeu zzeuVar2 = (zzeu) zzeuVar.zzi(4, null, null);
        try {
            zzgh zzghVarZzb = zzge.zza().zzb(zzeuVar2.getClass());
            zzghVarZzb.zzg(zzeuVar2, bArr, 0, i2, new zzds(zzekVar));
            zzghVarZzb.zze(zzeuVar2);
            if (zzeuVar2.zza == 0) {
                return zzeuVar2;
            }
            throw new RuntimeException();
        } catch (zzfa e) {
            e.zze(zzeuVar2);
            throw e;
        } catch (zzgx e2) {
            zzfa zzfaVarZza = e2.zza();
            zzfaVarZza.zze(zzeuVar2);
            throw zzfaVarZza;
        } catch (IOException e3) {
            if (e3.getCause() instanceof zzfa) {
                throw ((zzfa) e3.getCause());
            }
            zzfa zzfaVar = new zzfa(e3);
            zzfaVar.zze(zzeuVar2);
            throw zzfaVar;
        } catch (IndexOutOfBoundsException unused) {
            zzfa zzfaVarZzf = zzfa.zzf();
            zzfaVarZzf.zze(zzeuVar2);
            throw zzfaVarZzf;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzge.zza().zzb(getClass()).zzh(this, (zzeu) obj);
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zza;
        if (i != 0) {
            return i;
        }
        int iZza = zzge.zza().zzb(getClass()).zza(this);
        this.zza = iZza;
        return iZza;
    }

    public final String toString() {
        return zzfy.zza(this, super.toString());
    }

    @Override // com.google.android.gms.internal.auth.zzfw
    public final /* synthetic */ zzfv zzd() {
        zzes zzesVar = (zzes) zzi(5, null, null);
        zzesVar.zze(this);
        return zzesVar;
    }

    @Override // com.google.android.gms.internal.auth.zzfx
    public final /* synthetic */ zzfw zzh() {
        return (zzeu) zzi(6, null, null);
    }

    protected abstract Object zzi(int i, Object obj, Object obj2);
}
