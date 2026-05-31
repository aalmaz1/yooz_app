package org.msgpack.core;

import java.io.Closeable;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.UByte;
import kotlin.UShort;
import org.msgpack.core.MessagePack;
import org.msgpack.core.buffer.MessageBuffer;
import org.msgpack.core.buffer.MessageBufferInput;
import org.msgpack.value.ImmutableValue;
import org.msgpack.value.Value;
import org.msgpack.value.ValueFactory;
import org.msgpack.value.ValueType;
import org.msgpack.value.Variable;

/* JADX INFO: loaded from: classes3.dex */
public class MessageUnpacker implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final MessageBuffer EMPTY_BUFFER = MessageBuffer.wrap(new byte[0]);
    private static final String EMPTY_STRING = "";
    private final CodingErrorAction actionOnMalformedString;
    private final CodingErrorAction actionOnUnmappableString;
    private final boolean allowReadingBinaryAsString;
    private final boolean allowReadingStringAsBinary;
    private CharBuffer decodeBuffer;
    private StringBuilder decodeStringBuffer;
    private CharsetDecoder decoder;
    private MessageBufferInput in;
    private int nextReadPosition;
    private int position;
    private final int stringDecoderBufferSize;
    private final int stringSizeLimit;
    private long totalReadBytes;
    private MessageBuffer buffer = EMPTY_BUFFER;
    private final MessageBuffer numberBuffer = MessageBuffer.allocate(8);

    protected MessageUnpacker(MessageBufferInput messageBufferInput, MessagePack.UnpackerConfig unpackerConfig) {
        this.in = (MessageBufferInput) Preconditions.checkNotNull(messageBufferInput, "MessageBufferInput is null");
        this.allowReadingStringAsBinary = unpackerConfig.getAllowReadingStringAsBinary();
        this.allowReadingBinaryAsString = unpackerConfig.getAllowReadingBinaryAsString();
        this.actionOnMalformedString = unpackerConfig.getActionOnMalformedString();
        this.actionOnUnmappableString = unpackerConfig.getActionOnUnmappableString();
        this.stringSizeLimit = unpackerConfig.getStringSizeLimit();
        this.stringDecoderBufferSize = unpackerConfig.getStringDecoderBufferSize();
    }

    public MessageBufferInput reset(MessageBufferInput messageBufferInput) throws IOException {
        MessageBufferInput messageBufferInput2 = (MessageBufferInput) Preconditions.checkNotNull(messageBufferInput, "MessageBufferInput is null");
        MessageBufferInput messageBufferInput3 = this.in;
        this.in = messageBufferInput2;
        this.buffer = EMPTY_BUFFER;
        this.position = 0;
        this.totalReadBytes = 0L;
        return messageBufferInput3;
    }

    public long getTotalReadBytes() {
        return this.totalReadBytes + ((long) this.position);
    }

    private MessageBuffer getNextBuffer() throws IOException {
        MessageBuffer next = this.in.next();
        if (next == null) {
            throw new MessageInsufficientBufferException();
        }
        this.totalReadBytes += (long) this.buffer.size();
        return next;
    }

    private void nextBuffer() throws IOException {
        this.buffer = getNextBuffer();
        this.position = 0;
    }

    private MessageBuffer prepareNumberBuffer(int i) throws IOException {
        int i2;
        int size = this.buffer.size();
        int i3 = this.position;
        int i4 = size - i3;
        if (i4 >= i) {
            this.nextReadPosition = i3;
            this.position = i3 + i;
            return this.buffer;
        }
        if (i4 > 0) {
            this.numberBuffer.putMessageBuffer(0, this.buffer, i3, i4);
            i -= i4;
            i2 = i4 + 0;
        } else {
            i2 = 0;
        }
        while (true) {
            nextBuffer();
            int size2 = this.buffer.size();
            if (size2 >= i) {
                this.numberBuffer.putMessageBuffer(i2, this.buffer, 0, i);
                this.position = i;
                this.nextReadPosition = 0;
                return this.numberBuffer;
            }
            this.numberBuffer.putMessageBuffer(i2, this.buffer, 0, size2);
            i -= size2;
            i2 += size2;
        }
    }

    private static int utf8MultibyteCharacterSize(byte b) {
        return Integer.numberOfLeadingZeros((~(b & UByte.MAX_VALUE)) << 24);
    }

    public boolean hasNext() throws IOException {
        return ensureBuffer();
    }

    private boolean ensureBuffer() throws IOException {
        while (this.buffer.size() <= this.position) {
            MessageBuffer next = this.in.next();
            if (next == null) {
                return false;
            }
            this.totalReadBytes += (long) this.buffer.size();
            this.buffer = next;
            this.position = 0;
        }
        return true;
    }

    public MessageFormat getNextFormat() throws IOException {
        if (!ensureBuffer()) {
            throw new MessageInsufficientBufferException();
        }
        return MessageFormat.valueOf(this.buffer.getByte(this.position));
    }

    private byte readByte() throws IOException {
        int size = this.buffer.size();
        int i = this.position;
        if (size > i) {
            byte b = this.buffer.getByte(i);
            this.position++;
            return b;
        }
        nextBuffer();
        if (this.buffer.size() > 0) {
            byte b2 = this.buffer.getByte(0);
            this.position = 1;
            return b2;
        }
        return readByte();
    }

    private short readShort() throws IOException {
        return prepareNumberBuffer(2).getShort(this.nextReadPosition);
    }

    private int readInt() throws IOException {
        return prepareNumberBuffer(4).getInt(this.nextReadPosition);
    }

    private long readLong() throws IOException {
        return prepareNumberBuffer(8).getLong(this.nextReadPosition);
    }

    private float readFloat() throws IOException {
        return prepareNumberBuffer(4).getFloat(this.nextReadPosition);
    }

    private double readDouble() throws IOException {
        return prepareNumberBuffer(8).getDouble(this.nextReadPosition);
    }

    public void skipValue() throws IOException {
        skipValue(1);
    }

    public void skipValue(int i) throws IOException {
        int nextLength16;
        int nextLength162;
        while (i > 0) {
            byte b = readByte();
            switch (MessageFormat.valueOf(b)) {
                case FIXMAP:
                    nextLength16 = b & 15;
                    nextLength162 = nextLength16 * 2;
                    i += nextLength162;
                    i--;
                    break;
                case FIXARRAY:
                    nextLength162 = b & 15;
                    i += nextLength162;
                    i--;
                    break;
                case FIXSTR:
                    skipPayload(b & 31);
                    i--;
                    break;
                case INT8:
                case UINT8:
                    skipPayload(1);
                    i--;
                    break;
                case INT16:
                case UINT16:
                    skipPayload(2);
                    i--;
                    break;
                case INT32:
                case UINT32:
                case FLOAT32:
                    skipPayload(4);
                    i--;
                    break;
                case INT64:
                case UINT64:
                case FLOAT64:
                    skipPayload(8);
                    i--;
                    break;
                case BIN8:
                case STR8:
                    skipPayload(readNextLength8());
                    i--;
                    break;
                case BIN16:
                case STR16:
                    skipPayload(readNextLength16());
                    i--;
                    break;
                case BIN32:
                case STR32:
                    skipPayload(readNextLength32());
                    i--;
                    break;
                case FIXEXT1:
                    skipPayload(2);
                    i--;
                    break;
                case FIXEXT2:
                    skipPayload(3);
                    i--;
                    break;
                case FIXEXT4:
                    skipPayload(5);
                    i--;
                    break;
                case FIXEXT8:
                    skipPayload(9);
                    i--;
                    break;
                case FIXEXT16:
                    skipPayload(17);
                    i--;
                    break;
                case EXT8:
                    skipPayload(readNextLength8() + 1);
                    i--;
                    break;
                case EXT16:
                    skipPayload(readNextLength16() + 1);
                    i--;
                    break;
                case EXT32:
                    skipPayload(readNextLength32() + 1);
                    i--;
                    break;
                case ARRAY16:
                    nextLength162 = readNextLength16();
                    i += nextLength162;
                    i--;
                    break;
                case ARRAY32:
                    nextLength162 = readNextLength32();
                    i += nextLength162;
                    i--;
                    break;
                case MAP16:
                    nextLength16 = readNextLength16();
                    nextLength162 = nextLength16 * 2;
                    i += nextLength162;
                    i--;
                    break;
                case MAP32:
                    nextLength16 = readNextLength32();
                    nextLength162 = nextLength16 * 2;
                    i += nextLength162;
                    i--;
                    break;
                case NEVER_USED:
                    throw new MessageNeverUsedFormatException("Encountered 0xC1 \"NEVER_USED\" byte");
                default:
                    i--;
                    break;
            }
        }
    }

    private static MessagePackException unexpected(String str, byte b) {
        MessageFormat messageFormatValueOf = MessageFormat.valueOf(b);
        if (messageFormatValueOf == MessageFormat.NEVER_USED) {
            return new MessageNeverUsedFormatException(String.format("Expected %s, but encountered 0xC1 \"NEVER_USED\" byte", str));
        }
        String strName = messageFormatValueOf.getValueType().name();
        return new MessageTypeException(String.format("Expected %s, but got %s (%02x)", str, strName.substring(0, 1) + strName.substring(1).toLowerCase(), Byte.valueOf(b)));
    }

    /* JADX INFO: renamed from: org.msgpack.core.MessageUnpacker$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$msgpack$value$ValueType;

        static {
            int[] iArr = new int[ValueType.values().length];
            $SwitchMap$org$msgpack$value$ValueType = iArr;
            try {
                iArr[ValueType.NIL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.BOOLEAN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.INTEGER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.FLOAT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.STRING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.BINARY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.ARRAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.MAP.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$msgpack$value$ValueType[ValueType.EXTENSION.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            int[] iArr2 = new int[MessageFormat.values().length];
            $SwitchMap$org$msgpack$core$MessageFormat = iArr2;
            try {
                iArr2[MessageFormat.POSFIXINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.NEGFIXINT.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.NIL.ordinal()] = 4;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXMAP.ordinal()] = 5;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXARRAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXSTR.ordinal()] = 7;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.INT8.ordinal()] = 8;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.UINT8.ordinal()] = 9;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.INT16.ordinal()] = 10;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.UINT16.ordinal()] = 11;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.INT32.ordinal()] = 12;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.UINT32.ordinal()] = 13;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FLOAT32.ordinal()] = 14;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.INT64.ordinal()] = 15;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.UINT64.ordinal()] = 16;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FLOAT64.ordinal()] = 17;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.BIN8.ordinal()] = 18;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.STR8.ordinal()] = 19;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.BIN16.ordinal()] = 20;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.STR16.ordinal()] = 21;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.BIN32.ordinal()] = 22;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.STR32.ordinal()] = 23;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXEXT1.ordinal()] = 24;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXEXT2.ordinal()] = 25;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXEXT4.ordinal()] = 26;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXEXT8.ordinal()] = 27;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.FIXEXT16.ordinal()] = 28;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.EXT8.ordinal()] = 29;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.EXT16.ordinal()] = 30;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.EXT32.ordinal()] = 31;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.ARRAY16.ordinal()] = 32;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.ARRAY32.ordinal()] = 33;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.MAP16.ordinal()] = 34;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.MAP32.ordinal()] = 35;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                $SwitchMap$org$msgpack$core$MessageFormat[MessageFormat.NEVER_USED.ordinal()] = 36;
            } catch (NoSuchFieldError unused45) {
            }
        }
    }

    public ImmutableValue unpackValue() throws IOException {
        MessageFormat nextFormat = getNextFormat();
        int i = 0;
        switch (AnonymousClass1.$SwitchMap$org$msgpack$value$ValueType[nextFormat.getValueType().ordinal()]) {
            case 1:
                readByte();
                return ValueFactory.newNil();
            case 2:
                return ValueFactory.newBoolean(unpackBoolean());
            case 3:
                if (nextFormat == MessageFormat.UINT64) {
                    return ValueFactory.newInteger(unpackBigInteger());
                }
                return ValueFactory.newInteger(unpackLong());
            case 4:
                return ValueFactory.newFloat(unpackDouble());
            case 5:
                return ValueFactory.newString(readPayload(unpackRawStringHeader()), true);
            case 6:
                int iUnpackBinaryHeader = unpackBinaryHeader();
                if (iUnpackBinaryHeader > 0) {
                    return ValueFactory.newBinary(readPayload(iUnpackBinaryHeader), true);
                }
                return ValueFactory.newBinary(new byte[0], true);
            case 7:
                int iUnpackArrayHeader = unpackArrayHeader();
                Value[] valueArr = new Value[iUnpackArrayHeader];
                while (i < iUnpackArrayHeader) {
                    valueArr[i] = unpackValue();
                    i++;
                }
                return ValueFactory.newArray(valueArr, true);
            case 8:
                int iUnpackMapHeader = unpackMapHeader() * 2;
                Value[] valueArr2 = new Value[iUnpackMapHeader];
                while (i < iUnpackMapHeader) {
                    valueArr2[i] = unpackValue();
                    int i2 = i + 1;
                    valueArr2[i2] = unpackValue();
                    i = i2 + 1;
                }
                return ValueFactory.newMap(valueArr2, true);
            case 9:
                ExtensionTypeHeader extensionTypeHeaderUnpackExtensionTypeHeader = unpackExtensionTypeHeader();
                return ValueFactory.newExtension(extensionTypeHeaderUnpackExtensionTypeHeader.getType(), readPayload(extensionTypeHeaderUnpackExtensionTypeHeader.getLength()));
            default:
                throw new MessageNeverUsedFormatException("Unknown value type");
        }
    }

    public Variable unpackValue(Variable variable) throws IOException {
        MessageFormat nextFormat = getNextFormat();
        int i = 0;
        switch (AnonymousClass1.$SwitchMap$org$msgpack$value$ValueType[nextFormat.getValueType().ordinal()]) {
            case 1:
                readByte();
                variable.setNilValue();
                return variable;
            case 2:
                variable.setBooleanValue(unpackBoolean());
                return variable;
            case 3:
                if (AnonymousClass1.$SwitchMap$org$msgpack$core$MessageFormat[nextFormat.ordinal()] == 16) {
                    variable.setIntegerValue(unpackBigInteger());
                    return variable;
                }
                variable.setIntegerValue(unpackLong());
                return variable;
            case 4:
                variable.setFloatValue(unpackDouble());
                return variable;
            case 5:
                variable.setStringValue(readPayload(unpackRawStringHeader()));
                return variable;
            case 6:
                variable.setBinaryValue(readPayload(unpackBinaryHeader()));
                return variable;
            case 7:
                int iUnpackArrayHeader = unpackArrayHeader();
                ArrayList arrayList = new ArrayList(iUnpackArrayHeader);
                while (i < iUnpackArrayHeader) {
                    arrayList.add(unpackValue());
                    i++;
                }
                variable.setArrayValue(arrayList);
                return variable;
            case 8:
                int iUnpackMapHeader = unpackMapHeader();
                HashMap map = new HashMap();
                while (i < iUnpackMapHeader) {
                    map.put(unpackValue(), unpackValue());
                    i++;
                }
                variable.setMapValue(map);
                return variable;
            case 9:
                ExtensionTypeHeader extensionTypeHeaderUnpackExtensionTypeHeader = unpackExtensionTypeHeader();
                variable.setExtensionValue(extensionTypeHeaderUnpackExtensionTypeHeader.getType(), readPayload(extensionTypeHeaderUnpackExtensionTypeHeader.getLength()));
                return variable;
            default:
                throw new MessageFormatException("Unknown value type");
        }
    }

    public void unpackNil() throws IOException {
        byte b = readByte();
        if (b != -64) {
            throw unexpected("Nil", b);
        }
    }

    public boolean tryUnpackNil() throws IOException {
        if (!ensureBuffer()) {
            throw new MessageInsufficientBufferException();
        }
        if (this.buffer.getByte(this.position) != -64) {
            return false;
        }
        readByte();
        return true;
    }

    public boolean unpackBoolean() throws IOException {
        byte b = readByte();
        if (b == -62) {
            return false;
        }
        if (b == -61) {
            return true;
        }
        throw unexpected("boolean", b);
    }

    public byte unpackByte() throws IOException {
        long j;
        byte b = readByte();
        if (MessagePack.Code.isFixInt(b)) {
            return b;
        }
        switch (b) {
            case -52:
                byte b2 = readByte();
                if (b2 >= 0) {
                    return b2;
                }
                throw overflowU8(b2);
            case -51:
                short s = readShort();
                if (s < 0 || s > 127) {
                    throw overflowU16(s);
                }
                return (byte) s;
            case -50:
                int i = readInt();
                if (i < 0 || i > 127) {
                    throw overflowU32(i);
                }
                return (byte) i;
            case -49:
                j = readLong();
                if (j < 0 || j > 127) {
                    throw overflowU64(j);
                }
                break;
            case -48:
                return readByte();
            case -47:
                short s2 = readShort();
                if (s2 < -128 || s2 > 127) {
                    throw overflowI16(s2);
                }
                return (byte) s2;
            case -46:
                int i2 = readInt();
                if (i2 < -128 || i2 > 127) {
                    throw overflowI32(i2);
                }
                return (byte) i2;
            case -45:
                j = readLong();
                if (j < -128 || j > 127) {
                    throw overflowI64(j);
                }
                break;
            default:
                throw unexpected("Integer", b);
        }
        return (byte) j;
    }

    public short unpackShort() throws IOException {
        int i;
        long j;
        byte b = readByte();
        if (MessagePack.Code.isFixInt(b)) {
            return b;
        }
        switch (b) {
            case -52:
                i = readByte() & UByte.MAX_VALUE;
                return (short) i;
            case -51:
                short s = readShort();
                if (s >= 0) {
                    return s;
                }
                throw overflowU16(s);
            case -50:
                int i2 = readInt();
                if (i2 < 0 || i2 > 32767) {
                    throw overflowU32(i2);
                }
                return (short) i2;
            case -49:
                j = readLong();
                if (j < 0 || j > 32767) {
                    throw overflowU64(j);
                }
                i = (int) j;
                return (short) i;
            case -48:
                i = readByte();
                return (short) i;
            case -47:
                return readShort();
            case -46:
                int i3 = readInt();
                if (i3 < -32768 || i3 > 32767) {
                    throw overflowI32(i3);
                }
                return (short) i3;
            case -45:
                j = readLong();
                if (j < -32768 || j > 32767) {
                    throw overflowI64(j);
                }
                i = (int) j;
                return (short) i;
            default:
                throw unexpected("Integer", b);
        }
    }

    public int unpackInt() throws IOException {
        byte b = readByte();
        if (MessagePack.Code.isFixInt(b)) {
            return b;
        }
        switch (b) {
            case -52:
                return readByte() & UByte.MAX_VALUE;
            case -51:
                return readShort() & UShort.MAX_VALUE;
            case -50:
                int i = readInt();
                if (i >= 0) {
                    return i;
                }
                throw overflowU32(i);
            case -49:
                long j = readLong();
                if (j < 0 || j > 2147483647L) {
                    throw overflowU64(j);
                }
                return (int) j;
            case -48:
                return readByte();
            case -47:
                return readShort();
            case -46:
                return readInt();
            case -45:
                long j2 = readLong();
                if (j2 < -2147483648L || j2 > 2147483647L) {
                    throw overflowI64(j2);
                }
                return (int) j2;
            default:
                throw unexpected("Integer", b);
        }
    }

    public long unpackLong() throws IOException {
        byte b = readByte();
        if (MessagePack.Code.isFixInt(b)) {
            return b;
        }
        switch (b) {
            case -52:
                return readByte() & UByte.MAX_VALUE;
            case -51:
                return readShort() & UShort.MAX_VALUE;
            case -50:
                int i = readInt();
                return i < 0 ? ((long) (i & Integer.MAX_VALUE)) + 2147483648L : i;
            case -49:
                long j = readLong();
                if (j >= 0) {
                    return j;
                }
                throw overflowU64(j);
            case -48:
                return readByte();
            case -47:
                return readShort();
            case -46:
                return readInt();
            case -45:
                return readLong();
            default:
                throw unexpected("Integer", b);
        }
    }

    public BigInteger unpackBigInteger() throws IOException {
        byte b = readByte();
        if (MessagePack.Code.isFixInt(b)) {
            return BigInteger.valueOf(b);
        }
        switch (b) {
            case -52:
                return BigInteger.valueOf(readByte() & UByte.MAX_VALUE);
            case -51:
                return BigInteger.valueOf(readShort() & UShort.MAX_VALUE);
            case -50:
                int i = readInt();
                if (i < 0) {
                    return BigInteger.valueOf(((long) (i & Integer.MAX_VALUE)) + 2147483648L);
                }
                return BigInteger.valueOf(i);
            case -49:
                long j = readLong();
                if (j < 0) {
                    return BigInteger.valueOf(j + Long.MAX_VALUE + 1).setBit(63);
                }
                return BigInteger.valueOf(j);
            case -48:
                return BigInteger.valueOf(readByte());
            case -47:
                return BigInteger.valueOf(readShort());
            case -46:
                return BigInteger.valueOf(readInt());
            case -45:
                return BigInteger.valueOf(readLong());
            default:
                throw unexpected("Integer", b);
        }
    }

    public float unpackFloat() throws IOException {
        byte b = readByte();
        if (b == -54) {
            return readFloat();
        }
        if (b == -53) {
            return (float) readDouble();
        }
        throw unexpected("Float", b);
    }

    public double unpackDouble() throws IOException {
        byte b = readByte();
        if (b == -54) {
            return readFloat();
        }
        if (b == -53) {
            return readDouble();
        }
        throw unexpected("Float", b);
    }

    private void resetDecoder() {
        CharsetDecoder charsetDecoder = this.decoder;
        if (charsetDecoder == null) {
            this.decodeBuffer = CharBuffer.allocate(this.stringDecoderBufferSize);
            this.decoder = MessagePack.UTF8.newDecoder().onMalformedInput(this.actionOnMalformedString).onUnmappableCharacter(this.actionOnUnmappableString);
        } else {
            charsetDecoder.reset();
        }
        StringBuilder sb = this.decodeStringBuffer;
        if (sb == null) {
            this.decodeStringBuffer = new StringBuilder();
        } else {
            sb.setLength(0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x011c, code lost:
    
        return r8.decodeStringBuffer.toString();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String unpackString() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 323
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.msgpack.core.MessageUnpacker.unpackString():java.lang.String");
    }

    private void handleCoderError(CoderResult coderResult) throws CharacterCodingException {
        if ((coderResult.isMalformed() && this.actionOnMalformedString == CodingErrorAction.REPORT) || (coderResult.isUnmappable() && this.actionOnUnmappableString == CodingErrorAction.REPORT)) {
            coderResult.throwException();
        }
    }

    private String decodeStringFastPath(int i) {
        if (this.actionOnMalformedString == CodingErrorAction.REPLACE && this.actionOnUnmappableString == CodingErrorAction.REPLACE && this.buffer.hasArray()) {
            String str = new String(this.buffer.array(), this.buffer.arrayOffset() + this.position, i, MessagePack.UTF8);
            this.position += i;
            return str;
        }
        try {
            CharBuffer charBufferDecode = this.decoder.decode(this.buffer.sliceAsByteBuffer(this.position, i));
            this.position += i;
            return charBufferDecode.toString();
        } catch (CharacterCodingException e) {
            throw new MessageStringCodingException(e);
        }
    }

    public int unpackArrayHeader() throws IOException {
        byte b = readByte();
        if (MessagePack.Code.isFixedArray(b)) {
            return b & 15;
        }
        if (b == -36) {
            return readNextLength16();
        }
        if (b == -35) {
            return readNextLength32();
        }
        throw unexpected("Array", b);
    }

    public int unpackMapHeader() throws IOException {
        byte b = readByte();
        if (MessagePack.Code.isFixedMap(b)) {
            return b & 15;
        }
        if (b == -34) {
            return readNextLength16();
        }
        if (b == -33) {
            return readNextLength32();
        }
        throw unexpected("Map", b);
    }

    public ExtensionTypeHeader unpackExtensionTypeHeader() throws IOException {
        byte b = readByte();
        switch (b) {
            case -57:
                MessageBuffer messageBufferPrepareNumberBuffer = prepareNumberBuffer(2);
                return new ExtensionTypeHeader(messageBufferPrepareNumberBuffer.getByte(this.nextReadPosition + 1), messageBufferPrepareNumberBuffer.getByte(this.nextReadPosition) & UByte.MAX_VALUE);
            case -56:
                MessageBuffer messageBufferPrepareNumberBuffer2 = prepareNumberBuffer(3);
                return new ExtensionTypeHeader(messageBufferPrepareNumberBuffer2.getByte(this.nextReadPosition + 2), messageBufferPrepareNumberBuffer2.getShort(this.nextReadPosition) & UShort.MAX_VALUE);
            case -55:
                MessageBuffer messageBufferPrepareNumberBuffer3 = prepareNumberBuffer(5);
                int i = messageBufferPrepareNumberBuffer3.getInt(this.nextReadPosition);
                if (i < 0) {
                    throw overflowU32Size(i);
                }
                return new ExtensionTypeHeader(messageBufferPrepareNumberBuffer3.getByte(this.nextReadPosition + 4), i);
            default:
                switch (b) {
                    case -44:
                        return new ExtensionTypeHeader(readByte(), 1);
                    case -43:
                        return new ExtensionTypeHeader(readByte(), 2);
                    case -42:
                        return new ExtensionTypeHeader(readByte(), 4);
                    case -41:
                        return new ExtensionTypeHeader(readByte(), 8);
                    case -40:
                        return new ExtensionTypeHeader(readByte(), 16);
                    default:
                        throw unexpected("Ext", b);
                }
        }
    }

    private int tryReadStringHeader(byte b) throws IOException {
        switch (b) {
            case -39:
                return readNextLength8();
            case -38:
                return readNextLength16();
            case -37:
                return readNextLength32();
            default:
                return -1;
        }
    }

    private int tryReadBinaryHeader(byte b) throws IOException {
        switch (b) {
            case -60:
                return readNextLength8();
            case -59:
                return readNextLength16();
            case -58:
                return readNextLength32();
            default:
                return -1;
        }
    }

    public int unpackRawStringHeader() throws IOException {
        int iTryReadBinaryHeader;
        byte b = readByte();
        if (MessagePack.Code.isFixedRaw(b)) {
            return b & 31;
        }
        int iTryReadStringHeader = tryReadStringHeader(b);
        if (iTryReadStringHeader >= 0) {
            return iTryReadStringHeader;
        }
        if (!this.allowReadingBinaryAsString || (iTryReadBinaryHeader = tryReadBinaryHeader(b)) < 0) {
            throw unexpected("String", b);
        }
        return iTryReadBinaryHeader;
    }

    public int unpackBinaryHeader() throws IOException {
        int iTryReadStringHeader;
        byte b = readByte();
        if (MessagePack.Code.isFixedRaw(b)) {
            return b & 31;
        }
        int iTryReadBinaryHeader = tryReadBinaryHeader(b);
        if (iTryReadBinaryHeader >= 0) {
            return iTryReadBinaryHeader;
        }
        if (!this.allowReadingStringAsBinary || (iTryReadStringHeader = tryReadStringHeader(b)) < 0) {
            throw unexpected("Binary", b);
        }
        return iTryReadStringHeader;
    }

    private void skipPayload(int i) throws IOException {
        while (true) {
            int size = this.buffer.size();
            int i2 = this.position;
            int i3 = size - i2;
            if (i3 >= i) {
                this.position = i2 + i;
                return;
            } else {
                this.position = i2 + i3;
                i -= i3;
                nextBuffer();
            }
        }
    }

    public void readPayload(ByteBuffer byteBuffer) throws IOException {
        while (true) {
            int iRemaining = byteBuffer.remaining();
            int size = this.buffer.size();
            int i = this.position;
            int i2 = size - i;
            if (i2 >= iRemaining) {
                this.buffer.getBytes(i, iRemaining, byteBuffer);
                this.position += iRemaining;
                return;
            } else {
                this.buffer.getBytes(i, i2, byteBuffer);
                this.position += i2;
                nextBuffer();
            }
        }
    }

    public void readPayload(MessageBuffer messageBuffer, int i, int i2) throws IOException {
        while (true) {
            int size = this.buffer.size();
            int i3 = this.position;
            int i4 = size - i3;
            if (i4 >= i2) {
                messageBuffer.putMessageBuffer(i, this.buffer, i3, i2);
                this.position += i2;
                return;
            } else {
                messageBuffer.putMessageBuffer(i, this.buffer, i3, i4);
                i += i4;
                i2 -= i4;
                this.position += i4;
                nextBuffer();
            }
        }
    }

    public void readPayload(byte[] bArr) throws IOException {
        readPayload(bArr, 0, bArr.length);
    }

    public byte[] readPayload(int i) throws IOException {
        byte[] bArr = new byte[i];
        readPayload(bArr);
        return bArr;
    }

    public void readPayload(byte[] bArr, int i, int i2) throws IOException {
        while (true) {
            int size = this.buffer.size();
            int i3 = this.position;
            int i4 = size - i3;
            if (i4 >= i2) {
                this.buffer.getBytes(i3, bArr, i, i2);
                this.position += i2;
                return;
            } else {
                this.buffer.getBytes(i3, bArr, i, i4);
                i += i4;
                i2 -= i4;
                this.position += i4;
                nextBuffer();
            }
        }
    }

    public MessageBuffer readPayloadAsReference(int i) throws IOException {
        int size = this.buffer.size();
        int i2 = this.position;
        if (size - i2 >= i) {
            MessageBuffer messageBufferSlice = this.buffer.slice(i2, i);
            this.position += i;
            return messageBufferSlice;
        }
        MessageBuffer messageBufferAllocate = MessageBuffer.allocate(i);
        readPayload(messageBufferAllocate, 0, i);
        return messageBufferAllocate;
    }

    private int readNextLength8() throws IOException {
        return readByte() & UByte.MAX_VALUE;
    }

    private int readNextLength16() throws IOException {
        return readShort() & UShort.MAX_VALUE;
    }

    private int readNextLength32() throws IOException {
        int i = readInt();
        if (i >= 0) {
            return i;
        }
        throw overflowU32Size(i);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.buffer = EMPTY_BUFFER;
        this.position = 0;
        this.in.close();
    }

    private static MessageIntegerOverflowException overflowU8(byte b) {
        return new MessageIntegerOverflowException(BigInteger.valueOf(b & UByte.MAX_VALUE));
    }

    private static MessageIntegerOverflowException overflowU16(short s) {
        return new MessageIntegerOverflowException(BigInteger.valueOf(s & UShort.MAX_VALUE));
    }

    private static MessageIntegerOverflowException overflowU32(int i) {
        return new MessageIntegerOverflowException(BigInteger.valueOf(((long) (i & Integer.MAX_VALUE)) + 2147483648L));
    }

    private static MessageIntegerOverflowException overflowU64(long j) {
        return new MessageIntegerOverflowException(BigInteger.valueOf(j + Long.MAX_VALUE + 1).setBit(63));
    }

    private static MessageIntegerOverflowException overflowI16(short s) {
        return new MessageIntegerOverflowException(BigInteger.valueOf(s));
    }

    private static MessageIntegerOverflowException overflowI32(int i) {
        return new MessageIntegerOverflowException(BigInteger.valueOf(i));
    }

    private static MessageIntegerOverflowException overflowI64(long j) {
        return new MessageIntegerOverflowException(BigInteger.valueOf(j));
    }

    private static MessageSizeException overflowU32Size(int i) {
        return new MessageSizeException(((long) (i & Integer.MAX_VALUE)) + 2147483648L);
    }
}
