package org.msgpack.core.buffer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import org.apache.commons.lang3.StringUtils;
import org.msgpack.core.Preconditions;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes3.dex */
public class MessageBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int ARRAY_BYTE_BASE_OFFSET;
    private static final String BIGENDIAN_MESSAGE_BUFFER = "org.msgpack.core.buffer.MessageBufferBE";
    private static final String DEFAULT_MESSAGE_BUFFER = "org.msgpack.core.buffer.MessageBuffer";
    private static final String UNIVERSAL_MESSAGE_BUFFER = "org.msgpack.core.buffer.MessageBufferU";
    static final boolean isUniversalBuffer;
    private static final Constructor<?> mbArrConstructor;
    private static final Constructor<?> mbBBConstructor;
    static final Unsafe unsafe;
    protected final long address;
    protected final Object base;
    protected final ByteBuffer reference;
    protected final int size;

    /* JADX WARN: Can't wrap try/catch for region: R(23:0|2|123|3|4|(17:117|6|(1:13)|111|17|(1:19)(1:20)|21|(1:23)(1:24)|25|(1:33)(1:32)|(4:126|35|125|(6:37|128|38|(1:40)(2:41|42)|70|130)(2:47|48))(1:57)|58|(4:61|(1:63)(1:64)|(1:66)|67)|68|(3:113|71|131)|70|130)|12|111|17|(0)(0)|21|(0)(0)|25|(0)|33|(0)(0)|58|(0)|68|(0)|70|130|(1:(0))) */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0101 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01ac A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0153 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0192  */
    static {
        /*
            Method dump skipped, instruction units count: 481
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.msgpack.core.buffer.MessageBuffer.<clinit>():void");
    }

    public static MessageBuffer allocate(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("size must not be negative");
        }
        return wrap(new byte[i]);
    }

    public static MessageBuffer wrap(byte[] bArr) {
        return newMessageBuffer(bArr, 0, bArr.length);
    }

    public static MessageBuffer wrap(byte[] bArr, int i, int i2) {
        return newMessageBuffer(bArr, i, i2);
    }

    public static MessageBuffer wrap(ByteBuffer byteBuffer) {
        return newMessageBuffer(byteBuffer);
    }

    private static MessageBuffer newMessageBuffer(byte[] bArr, int i, int i2) {
        Preconditions.checkNotNull(bArr);
        Constructor<?> constructor = mbArrConstructor;
        return constructor != null ? newInstance(constructor, bArr, Integer.valueOf(i), Integer.valueOf(i2)) : new MessageBuffer(bArr, i, i2);
    }

    private static MessageBuffer newMessageBuffer(ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer);
        Constructor<?> constructor = mbBBConstructor;
        if (constructor != null) {
            return newInstance(constructor, byteBuffer);
        }
        return new MessageBuffer(byteBuffer);
    }

    private static MessageBuffer newInstance(Constructor<?> constructor, Object... objArr) {
        try {
            return (MessageBuffer) constructor.newInstance(objArr);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        } catch (InvocationTargetException e3) {
            if (e3.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e3.getCause());
            }
            if (e3.getCause() instanceof Error) {
                throw ((Error) e3.getCause());
            }
            throw new IllegalStateException(e3.getCause());
        }
    }

    public static void releaseBuffer(MessageBuffer messageBuffer) {
        if (isUniversalBuffer || messageBuffer.hasArray()) {
            return;
        }
        if (DirectBufferAccess.isDirectByteBufferInstance(messageBuffer.reference)) {
            DirectBufferAccess.clean(messageBuffer.reference);
        } else {
            unsafe.freeMemory(messageBuffer.address);
        }
    }

    MessageBuffer(byte[] bArr, int i, int i2) {
        this.base = bArr;
        this.address = ARRAY_BYTE_BASE_OFFSET + i;
        this.size = i2;
        this.reference = null;
    }

    MessageBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            if (isUniversalBuffer) {
                throw new UnsupportedOperationException("Cannot create MessageBuffer from a DirectBuffer on this platform");
            }
            this.base = null;
            this.address = DirectBufferAccess.getAddress(byteBuffer) + ((long) byteBuffer.position());
            this.size = byteBuffer.remaining();
            this.reference = byteBuffer;
            return;
        }
        if (byteBuffer.hasArray()) {
            this.base = byteBuffer.array();
            this.address = ARRAY_BYTE_BASE_OFFSET + byteBuffer.arrayOffset() + byteBuffer.position();
            this.size = byteBuffer.remaining();
            this.reference = null;
            return;
        }
        throw new IllegalArgumentException("Only the array-backed ByteBuffer or DirectBuffer is supported");
    }

    protected MessageBuffer(Object obj, long j, int i) {
        this.base = obj;
        this.address = j;
        this.size = i;
        this.reference = null;
    }

    public int size() {
        return this.size;
    }

    public MessageBuffer slice(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        Preconditions.checkArgument(i + i2 <= size());
        return new MessageBuffer(this.base, this.address + ((long) i), i2);
    }

    public byte getByte(int i) {
        return unsafe.getByte(this.base, this.address + ((long) i));
    }

    public boolean getBoolean(int i) {
        return unsafe.getBoolean(this.base, this.address + ((long) i));
    }

    public short getShort(int i) {
        return Short.reverseBytes(unsafe.getShort(this.base, this.address + ((long) i)));
    }

    public int getInt(int i) {
        return Integer.reverseBytes(unsafe.getInt(this.base, this.address + ((long) i)));
    }

    public float getFloat(int i) {
        return Float.intBitsToFloat(getInt(i));
    }

    public long getLong(int i) {
        return Long.reverseBytes(unsafe.getLong(this.base, this.address + ((long) i)));
    }

    public double getDouble(int i) {
        return Double.longBitsToDouble(getLong(i));
    }

    public void getBytes(int i, byte[] bArr, int i2, int i3) {
        unsafe.copyMemory(this.base, this.address + ((long) i), bArr, ARRAY_BYTE_BASE_OFFSET + i2, i3);
    }

    public void getBytes(int i, int i2, ByteBuffer byteBuffer) {
        if (byteBuffer.remaining() < i2) {
            throw new BufferOverflowException();
        }
        byteBuffer.put(sliceAsByteBuffer(i, i2));
    }

    public void putByte(int i, byte b) {
        unsafe.putByte(this.base, this.address + ((long) i), b);
    }

    public void putBoolean(int i, boolean z) {
        unsafe.putBoolean(this.base, this.address + ((long) i), z);
    }

    public void putShort(int i, short s) {
        unsafe.putShort(this.base, this.address + ((long) i), Short.reverseBytes(s));
    }

    public void putInt(int i, int i2) {
        unsafe.putInt(this.base, this.address + ((long) i), Integer.reverseBytes(i2));
    }

    public void putFloat(int i, float f) {
        putInt(i, Float.floatToRawIntBits(f));
    }

    public void putLong(int i, long j) {
        unsafe.putLong(this.base, ((long) i) + this.address, Long.reverseBytes(j));
    }

    public void putDouble(int i, double d) {
        putLong(i, Double.doubleToRawLongBits(d));
    }

    public void putBytes(int i, byte[] bArr, int i2, int i3) {
        unsafe.copyMemory(bArr, ARRAY_BYTE_BASE_OFFSET + i2, this.base, this.address + ((long) i), i3);
    }

    public void putByteBuffer(int i, ByteBuffer byteBuffer, int i2) {
        if (byteBuffer.isDirect()) {
            unsafe.copyMemory((Object) null, DirectBufferAccess.getAddress(byteBuffer) + ((long) byteBuffer.position()), this.base, this.address + ((long) i), i2);
            byteBuffer.position(byteBuffer.position() + i2);
            return;
        }
        if (byteBuffer.hasArray()) {
            unsafe.copyMemory(byteBuffer.array(), ARRAY_BYTE_BASE_OFFSET + byteBuffer.position(), this.base, this.address + ((long) i), i2);
            byteBuffer.position(byteBuffer.position() + i2);
        } else {
            if (hasArray()) {
                byteBuffer.get((byte[]) this.base, i, i2);
                return;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                unsafe.putByte(this.base, this.address + ((long) i), byteBuffer.get());
            }
        }
    }

    public void putMessageBuffer(int i, MessageBuffer messageBuffer, int i2, int i3) {
        unsafe.copyMemory(messageBuffer.base, messageBuffer.address + ((long) i2), this.base, ((long) i) + this.address, i3);
    }

    public ByteBuffer sliceAsByteBuffer(int i, int i2) {
        if (hasArray()) {
            return ByteBuffer.wrap((byte[]) this.base, (int) ((this.address - ((long) ARRAY_BYTE_BASE_OFFSET)) + ((long) i)), i2);
        }
        return DirectBufferAccess.newByteBuffer(this.address, i, i2, this.reference);
    }

    public ByteBuffer sliceAsByteBuffer() {
        return sliceAsByteBuffer(0, size());
    }

    public boolean hasArray() {
        return this.base != null;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[size()];
        unsafe.copyMemory(this.base, this.address, bArr, ARRAY_BYTE_BASE_OFFSET, size());
        return bArr;
    }

    public byte[] array() {
        return (byte[]) this.base;
    }

    public int arrayOffset() {
        return ((int) this.address) - ARRAY_BYTE_BASE_OFFSET;
    }

    public void copyTo(int i, MessageBuffer messageBuffer, int i2, int i3) {
        unsafe.copyMemory(this.base, this.address + ((long) i), messageBuffer.base, ((long) i2) + messageBuffer.address, i3);
    }

    public String toHexString(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        for (int i3 = i; i3 < i2; i3++) {
            if (i3 != i) {
                sb.append(StringUtils.SPACE);
            }
            sb.append(String.format("%02x", Byte.valueOf(getByte(i3))));
        }
        return sb.toString();
    }
}
