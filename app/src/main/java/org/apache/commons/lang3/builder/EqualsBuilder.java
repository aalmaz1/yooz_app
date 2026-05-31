package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.tuple.Pair;

/* JADX INFO: loaded from: classes3.dex */
public class EqualsBuilder implements Builder<Boolean> {
    private static final ThreadLocal<Set<Pair<IDKey, IDKey>>> REGISTRY = new ThreadLocal<>();
    private boolean isEquals = true;
    private boolean testTransients = false;
    private boolean testRecursive = false;
    private Class<?> reflectUpToClass = null;
    private String[] excludeFields = null;

    static Set<Pair<IDKey, IDKey>> getRegistry() {
        return REGISTRY.get();
    }

    static Pair<IDKey, IDKey> getRegisterPair(Object obj, Object obj2) {
        return Pair.of(new IDKey(obj), new IDKey(obj2));
    }

    static boolean isRegistered(Object obj, Object obj2) {
        Set<Pair<IDKey, IDKey>> registry = getRegistry();
        Pair<IDKey, IDKey> registerPair = getRegisterPair(obj, obj2);
        return registry != null && (registry.contains(registerPair) || registry.contains(Pair.of(registerPair.getRight(), registerPair.getLeft())));
    }

    private static void register(Object obj, Object obj2) {
        Set<Pair<IDKey, IDKey>> registry = getRegistry();
        if (registry == null) {
            registry = new HashSet<>();
            REGISTRY.set(registry);
        }
        registry.add(getRegisterPair(obj, obj2));
    }

    private static void unregister(Object obj, Object obj2) {
        Set<Pair<IDKey, IDKey>> registry = getRegistry();
        if (registry != null) {
            registry.remove(getRegisterPair(obj, obj2));
            if (registry.isEmpty()) {
                REGISTRY.remove();
            }
        }
    }

    public EqualsBuilder setTestTransients(boolean z) {
        this.testTransients = z;
        return this;
    }

    public EqualsBuilder setTestRecursive(boolean z) {
        this.testRecursive = z;
        return this;
    }

    public EqualsBuilder setReflectUpToClass(Class<?> cls) {
        this.reflectUpToClass = cls;
        return this;
    }

    public EqualsBuilder setExcludeFields(String... strArr) {
        this.excludeFields = strArr;
        return this;
    }

    public static boolean reflectionEquals(Object obj, Object obj2, Collection<String> collection) {
        return reflectionEquals(obj, obj2, ReflectionToStringBuilder.toNoNullStringArray(collection));
    }

    public static boolean reflectionEquals(Object obj, Object obj2, String... strArr) {
        return reflectionEquals(obj, obj2, false, null, strArr);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z) {
        return reflectionEquals(obj, obj2, z, null, new String[0]);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z, Class<?> cls, String... strArr) {
        return reflectionEquals(obj, obj2, z, cls, false, strArr);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z, Class<?> cls, boolean z2, String... strArr) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return new EqualsBuilder().setExcludeFields(strArr).setReflectUpToClass(cls).setTestTransients(z).setTestRecursive(z2).reflectionAppend(obj, obj2).isEquals();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.apache.commons.lang3.builder.EqualsBuilder reflectionAppend(java.lang.Object r5, java.lang.Object r6) {
        /*
            r4 = this;
            boolean r0 = r4.isEquals
            if (r0 != 0) goto L5
            return r4
        L5:
            if (r5 != r6) goto L8
            return r4
        L8:
            r0 = 0
            if (r5 == 0) goto L57
            if (r6 != 0) goto Le
            goto L57
        Le:
            java.lang.Class r1 = r5.getClass()
            java.lang.Class r2 = r6.getClass()
            boolean r3 = r1.isInstance(r6)
            if (r3 == 0) goto L23
            boolean r3 = r2.isInstance(r5)
            if (r3 != 0) goto L31
            goto L30
        L23:
            boolean r3 = r2.isInstance(r5)
            if (r3 == 0) goto L54
            boolean r3 = r1.isInstance(r6)
            if (r3 != 0) goto L30
            goto L31
        L30:
            r1 = r2
        L31:
            boolean r2 = r1.isArray()     // Catch: java.lang.IllegalArgumentException -> L51
            if (r2 == 0) goto L3b
            r4.append(r5, r6)     // Catch: java.lang.IllegalArgumentException -> L51
            goto L50
        L3b:
            r4.reflectionAppend(r5, r6, r1)     // Catch: java.lang.IllegalArgumentException -> L51
        L3e:
            java.lang.Class r2 = r1.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L51
            if (r2 == 0) goto L50
            java.lang.Class<?> r2 = r4.reflectUpToClass     // Catch: java.lang.IllegalArgumentException -> L51
            if (r1 == r2) goto L50
            java.lang.Class r1 = r1.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L51
            r4.reflectionAppend(r5, r6, r1)     // Catch: java.lang.IllegalArgumentException -> L51
            goto L3e
        L50:
            return r4
        L51:
            r4.isEquals = r0
            return r4
        L54:
            r4.isEquals = r0
            return r4
        L57:
            r4.isEquals = r0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.builder.EqualsBuilder.reflectionAppend(java.lang.Object, java.lang.Object):org.apache.commons.lang3.builder.EqualsBuilder");
    }

    private void reflectionAppend(Object obj, Object obj2, Class<?> cls) {
        if (isRegistered(obj, obj2)) {
            return;
        }
        try {
            register(obj, obj2);
            Field[] declaredFields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (int i = 0; i < declaredFields.length && this.isEquals; i++) {
                Field field = declaredFields[i];
                if (!ArrayUtils.contains(this.excludeFields, field.getName()) && !field.getName().contains("$") && ((this.testTransients || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(EqualsExclude.class))) {
                    try {
                        append(field.get(obj), field.get(obj2));
                    } catch (IllegalAccessException unused) {
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
                }
            }
        } finally {
            unregister(obj, obj2);
        }
    }

    public EqualsBuilder appendSuper(boolean z) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = z;
        return this;
    }

    public EqualsBuilder append(Object obj, Object obj2) {
        if (!this.isEquals || obj == obj2) {
            return this;
        }
        if (obj == null || obj2 == null) {
            setEquals(false);
            return this;
        }
        Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            if (this.testRecursive && !ClassUtils.isPrimitiveOrWrapper(cls)) {
                reflectionAppend(obj, obj2);
            } else {
                this.isEquals = obj.equals(obj2);
            }
        } else {
            appendArray(obj, obj2);
        }
        return this;
    }

    private void appendArray(Object obj, Object obj2) {
        if (obj.getClass() != obj2.getClass()) {
            setEquals(false);
            return;
        }
        if (obj instanceof long[]) {
            append((long[]) obj, (long[]) obj2);
            return;
        }
        if (obj instanceof int[]) {
            append((int[]) obj, (int[]) obj2);
            return;
        }
        if (obj instanceof short[]) {
            append((short[]) obj, (short[]) obj2);
            return;
        }
        if (obj instanceof char[]) {
            append((char[]) obj, (char[]) obj2);
            return;
        }
        if (obj instanceof byte[]) {
            append((byte[]) obj, (byte[]) obj2);
            return;
        }
        if (obj instanceof double[]) {
            append((double[]) obj, (double[]) obj2);
            return;
        }
        if (obj instanceof float[]) {
            append((float[]) obj, (float[]) obj2);
        } else if (obj instanceof boolean[]) {
            append((boolean[]) obj, (boolean[]) obj2);
        } else {
            append((Object[]) obj, (Object[]) obj2);
        }
    }

    public EqualsBuilder append(long j, long j2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = j == j2;
        return this;
    }

    public EqualsBuilder append(int i, int i2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = i == i2;
        return this;
    }

    public EqualsBuilder append(short s, short s2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = s == s2;
        return this;
    }

    public EqualsBuilder append(char c, char c2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = c == c2;
        return this;
    }

    public EqualsBuilder append(byte b, byte b2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = b == b2;
        return this;
    }

    public EqualsBuilder append(double d, double d2) {
        return !this.isEquals ? this : append(Double.doubleToLongBits(d), Double.doubleToLongBits(d2));
    }

    public EqualsBuilder append(float f, float f2) {
        return !this.isEquals ? this : append(Float.floatToIntBits(f), Float.floatToIntBits(f2));
    }

    public EqualsBuilder append(boolean z, boolean z2) {
        if (!this.isEquals) {
            return this;
        }
        this.isEquals = z == z2;
        return this;
    }

    public EqualsBuilder append(Object[] objArr, Object[] objArr2) {
        if (!this.isEquals || objArr == objArr2) {
            return this;
        }
        if (objArr == null || objArr2 == null) {
            setEquals(false);
            return this;
        }
        if (objArr.length != objArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < objArr.length && this.isEquals; i++) {
            append(objArr[i], objArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(long[] jArr, long[] jArr2) {
        if (!this.isEquals || jArr == jArr2) {
            return this;
        }
        if (jArr == null || jArr2 == null) {
            setEquals(false);
            return this;
        }
        if (jArr.length != jArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < jArr.length && this.isEquals; i++) {
            append(jArr[i], jArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(int[] iArr, int[] iArr2) {
        if (!this.isEquals || iArr == iArr2) {
            return this;
        }
        if (iArr == null || iArr2 == null) {
            setEquals(false);
            return this;
        }
        if (iArr.length != iArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < iArr.length && this.isEquals; i++) {
            append(iArr[i], iArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(short[] sArr, short[] sArr2) {
        if (!this.isEquals || sArr == sArr2) {
            return this;
        }
        if (sArr == null || sArr2 == null) {
            setEquals(false);
            return this;
        }
        if (sArr.length != sArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < sArr.length && this.isEquals; i++) {
            append(sArr[i], sArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(char[] cArr, char[] cArr2) {
        if (!this.isEquals || cArr == cArr2) {
            return this;
        }
        if (cArr == null || cArr2 == null) {
            setEquals(false);
            return this;
        }
        if (cArr.length != cArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < cArr.length && this.isEquals; i++) {
            append(cArr[i], cArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(byte[] bArr, byte[] bArr2) {
        if (!this.isEquals || bArr == bArr2) {
            return this;
        }
        if (bArr == null || bArr2 == null) {
            setEquals(false);
            return this;
        }
        if (bArr.length != bArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < bArr.length && this.isEquals; i++) {
            append(bArr[i], bArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(double[] dArr, double[] dArr2) {
        if (!this.isEquals || dArr == dArr2) {
            return this;
        }
        if (dArr == null || dArr2 == null) {
            setEquals(false);
            return this;
        }
        if (dArr.length != dArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < dArr.length && this.isEquals; i++) {
            append(dArr[i], dArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(float[] fArr, float[] fArr2) {
        if (!this.isEquals || fArr == fArr2) {
            return this;
        }
        if (fArr == null || fArr2 == null) {
            setEquals(false);
            return this;
        }
        if (fArr.length != fArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < fArr.length && this.isEquals; i++) {
            append(fArr[i], fArr2[i]);
        }
        return this;
    }

    public EqualsBuilder append(boolean[] zArr, boolean[] zArr2) {
        if (!this.isEquals || zArr == zArr2) {
            return this;
        }
        if (zArr == null || zArr2 == null) {
            setEquals(false);
            return this;
        }
        if (zArr.length != zArr2.length) {
            setEquals(false);
            return this;
        }
        for (int i = 0; i < zArr.length && this.isEquals; i++) {
            append(zArr[i], zArr2[i]);
        }
        return this;
    }

    public boolean isEquals() {
        return this.isEquals;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.lang3.builder.Builder
    public Boolean build() {
        return Boolean.valueOf(isEquals());
    }

    protected void setEquals(boolean z) {
        this.isEquals = z;
    }

    public void reset() {
        this.isEquals = true;
    }
}
