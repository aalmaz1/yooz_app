package org.msgpack.core.buffer;

import com.sun.mail.imap.IMAPStore;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes3.dex */
class DirectBufferAccess {
    static Constructor byteBufferConstructor;
    static DirectBufferConstructorType directBufferConstructorType;
    static Class<?> directByteBufferClass;
    static Method mClean;
    static Method mCleaner;
    static Method mGetAddress;
    static Method memoryBlockWrapFromJni;

    enum DirectBufferConstructorType {
        ARGS_LONG_INT_REF,
        ARGS_LONG_INT,
        ARGS_INT_INT,
        ARGS_MB_INT_INT
    }

    private DirectBufferAccess() {
    }

    static {
        Constructor<?> declaredConstructor;
        DirectBufferConstructorType directBufferConstructorType2;
        try {
            Class<?> clsLoadClass = ClassLoader.getSystemClassLoader().loadClass("java.nio.DirectByteBuffer");
            directByteBufferClass = clsLoadClass;
            Method declaredMethod = null;
            try {
                try {
                    try {
                        declaredConstructor = clsLoadClass.getDeclaredConstructor(Long.TYPE, Integer.TYPE, Object.class);
                        directBufferConstructorType2 = DirectBufferConstructorType.ARGS_LONG_INT_REF;
                    } catch (NoSuchMethodException unused) {
                        declaredConstructor = directByteBufferClass.getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                        directBufferConstructorType2 = DirectBufferConstructorType.ARGS_LONG_INT;
                    }
                } catch (NoSuchMethodException unused2) {
                    Class<?> cls = Class.forName("java.nio.MemoryBlock");
                    declaredMethod = cls.getDeclaredMethod("wrapFromJni", Integer.TYPE, Long.TYPE);
                    declaredMethod.setAccessible(true);
                    declaredConstructor = directByteBufferClass.getDeclaredConstructor(cls, Integer.TYPE, Integer.TYPE);
                    directBufferConstructorType2 = DirectBufferConstructorType.ARGS_MB_INT_INT;
                }
            } catch (NoSuchMethodException unused3) {
                declaredConstructor = directByteBufferClass.getDeclaredConstructor(Integer.TYPE, Integer.TYPE);
                directBufferConstructorType2 = DirectBufferConstructorType.ARGS_INT_INT;
            }
            byteBufferConstructor = declaredConstructor;
            directBufferConstructorType = directBufferConstructorType2;
            memoryBlockWrapFromJni = declaredMethod;
            if (declaredConstructor == null) {
                throw new RuntimeException("Constructor of DirectByteBuffer is not found");
            }
            declaredConstructor.setAccessible(true);
            Method declaredMethod2 = directByteBufferClass.getDeclaredMethod(IMAPStore.ID_ADDRESS, new Class[0]);
            mGetAddress = declaredMethod2;
            declaredMethod2.setAccessible(true);
            Method declaredMethod3 = directByteBufferClass.getDeclaredMethod("cleaner", new Class[0]);
            mCleaner = declaredMethod3;
            declaredMethod3.setAccessible(true);
            Method declaredMethod4 = mCleaner.getReturnType().getDeclaredMethod("clean", new Class[0]);
            mClean = declaredMethod4;
            declaredMethod4.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static long getAddress(Object obj) {
        try {
            return ((Long) mGetAddress.invoke(obj, new Object[0])).longValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    static void clean(Object obj) {
        try {
            mClean.invoke(mCleaner.invoke(obj, new Object[0]), new Object[0]);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    static boolean isDirectByteBufferInstance(Object obj) {
        return directByteBufferClass.isInstance(obj);
    }

    /* JADX INFO: renamed from: org.msgpack.core.buffer.DirectBufferAccess$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$msgpack$core$buffer$DirectBufferAccess$DirectBufferConstructorType;

        static {
            int[] iArr = new int[DirectBufferConstructorType.values().length];
            $SwitchMap$org$msgpack$core$buffer$DirectBufferAccess$DirectBufferConstructorType = iArr;
            try {
                iArr[DirectBufferConstructorType.ARGS_LONG_INT_REF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$msgpack$core$buffer$DirectBufferAccess$DirectBufferConstructorType[DirectBufferConstructorType.ARGS_LONG_INT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$msgpack$core$buffer$DirectBufferAccess$DirectBufferConstructorType[DirectBufferConstructorType.ARGS_INT_INT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$msgpack$core$buffer$DirectBufferAccess$DirectBufferConstructorType[DirectBufferConstructorType.ARGS_MB_INT_INT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static ByteBuffer newByteBuffer(long j, int i, int i2, ByteBuffer byteBuffer) {
        try {
            int i3 = AnonymousClass1.$SwitchMap$org$msgpack$core$buffer$DirectBufferAccess$DirectBufferConstructorType[directBufferConstructorType.ordinal()];
            if (i3 == 1) {
                return (ByteBuffer) byteBufferConstructor.newInstance(Long.valueOf(j + ((long) i)), Integer.valueOf(i2), byteBuffer);
            }
            if (i3 == 2) {
                return (ByteBuffer) byteBufferConstructor.newInstance(Long.valueOf(j + ((long) i)), Integer.valueOf(i2));
            }
            if (i3 == 3) {
                return (ByteBuffer) byteBufferConstructor.newInstance(Integer.valueOf(((int) j) + i), Integer.valueOf(i2));
            }
            if (i3 == 4) {
                return (ByteBuffer) byteBufferConstructor.newInstance(memoryBlockWrapFromJni.invoke(null, Long.valueOf(j + ((long) i)), Integer.valueOf(i2)), Integer.valueOf(i2), 0);
            }
            throw new IllegalStateException("Unexpected value");
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
