package com.google.crypto.tink.shaded.protobuf;

import com.google.crypto.tink.shaded.protobuf.ArrayDecoders;
import com.google.crypto.tink.shaded.protobuf.ByteString;
import com.google.crypto.tink.shaded.protobuf.Internal;
import com.google.crypto.tink.shaded.protobuf.MapEntryLite;
import com.google.crypto.tink.shaded.protobuf.WireFormat;
import com.google.crypto.tink.shaded.protobuf.Writer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes2.dex */
@CheckReturnValue
final class MessageSchema<T> implements Schema<T> {
    private static final int CHECK_INITIALIZED_BIT = 1024;
    private static final int ENFORCE_UTF8_MASK = 536870912;
    private static final int FIELD_TYPE_MASK = 267386880;
    private static final int HAS_HAS_BIT = 4096;
    private static final int INTS_PER_FIELD = 3;
    private static final int LEGACY_ENUM_IS_CLOSED_BIT = 2048;
    private static final int LEGACY_ENUM_IS_CLOSED_MASK = Integer.MIN_VALUE;
    private static final int NO_PRESENCE_SENTINEL = 1048575;
    private static final int OFFSET_BITS = 20;
    private static final int OFFSET_MASK = 1048575;
    static final int ONEOF_TYPE_OFFSET = 51;
    private static final int REQUIRED_BIT = 256;
    private static final int REQUIRED_MASK = 268435456;
    private static final int UTF8_CHECK_BIT = 512;
    private final int[] buffer;
    private final int checkInitializedCount;
    private final MessageLite defaultInstance;
    private final ExtensionSchema<?> extensionSchema;
    private final boolean hasExtensions;
    private final int[] intArray;
    private final ListFieldSchema listFieldSchema;
    private final boolean lite;
    private final MapFieldSchema mapFieldSchema;
    private final int maxFieldNumber;
    private final int minFieldNumber;
    private final NewInstanceSchema newInstanceSchema;
    private final Object[] objects;
    private final int repeatedFieldOffsetStart;
    private final ProtoSyntax syntax;
    private final UnknownFieldSchema<?, ?> unknownFieldSchema;
    private final boolean useCachedSizeField;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final Unsafe UNSAFE = UnsafeUtil.getUnsafe();

    private static boolean isEnforceUtf8(int value) {
        return (value & ENFORCE_UTF8_MASK) != 0;
    }

    private static boolean isLegacyEnumIsClosed(int value) {
        return (value & Integer.MIN_VALUE) != 0;
    }

    private static boolean isRequired(int value) {
        return (value & REQUIRED_MASK) != 0;
    }

    private static long offset(int value) {
        return value & 1048575;
    }

    private static int type(int value) {
        return (value & FIELD_TYPE_MASK) >>> 20;
    }

    private MessageSchema(int[] buffer, Object[] objects, int minFieldNumber, int maxFieldNumber, MessageLite defaultInstance, ProtoSyntax syntax, boolean useCachedSizeField, int[] intArray, int checkInitialized, int mapFieldPositions, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
        this.buffer = buffer;
        this.objects = objects;
        this.minFieldNumber = minFieldNumber;
        this.maxFieldNumber = maxFieldNumber;
        this.lite = defaultInstance instanceof GeneratedMessageLite;
        this.syntax = syntax;
        this.hasExtensions = extensionSchema != null && extensionSchema.hasExtensions(defaultInstance);
        this.useCachedSizeField = useCachedSizeField;
        this.intArray = intArray;
        this.checkInitializedCount = checkInitialized;
        this.repeatedFieldOffsetStart = mapFieldPositions;
        this.newInstanceSchema = newInstanceSchema;
        this.listFieldSchema = listFieldSchema;
        this.unknownFieldSchema = unknownFieldSchema;
        this.extensionSchema = extensionSchema;
        this.defaultInstance = defaultInstance;
        this.mapFieldSchema = mapFieldSchema;
    }

    static <T> MessageSchema<T> newSchema(Class<T> messageClass, MessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
        if (messageInfo instanceof RawMessageInfo) {
            return newSchemaForRawMessageInfo((RawMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
        }
        return newSchemaForMessageInfo((StructuralMessageInfo) messageInfo, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x0248  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0265  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static <T> com.google.crypto.tink.shaded.protobuf.MessageSchema<T> newSchemaForRawMessageInfo(com.google.crypto.tink.shaded.protobuf.RawMessageInfo r32, com.google.crypto.tink.shaded.protobuf.NewInstanceSchema r33, com.google.crypto.tink.shaded.protobuf.ListFieldSchema r34, com.google.crypto.tink.shaded.protobuf.UnknownFieldSchema<?, ?> r35, com.google.crypto.tink.shaded.protobuf.ExtensionSchema<?> r36, com.google.crypto.tink.shaded.protobuf.MapFieldSchema r37) {
        /*
            Method dump skipped, instruction units count: 989
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.newSchemaForRawMessageInfo(com.google.crypto.tink.shaded.protobuf.RawMessageInfo, com.google.crypto.tink.shaded.protobuf.NewInstanceSchema, com.google.crypto.tink.shaded.protobuf.ListFieldSchema, com.google.crypto.tink.shaded.protobuf.UnknownFieldSchema, com.google.crypto.tink.shaded.protobuf.ExtensionSchema, com.google.crypto.tink.shaded.protobuf.MapFieldSchema):com.google.crypto.tink.shaded.protobuf.MessageSchema");
    }

    private static java.lang.reflect.Field reflectField(Class<?> messageClass, String fieldName) {
        try {
            return messageClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException unused) {
            java.lang.reflect.Field[] declaredFields = messageClass.getDeclaredFields();
            for (java.lang.reflect.Field field : declaredFields) {
                if (fieldName.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + fieldName + " for " + messageClass.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    static <T> MessageSchema<T> newSchemaForMessageInfo(StructuralMessageInfo messageInfo, NewInstanceSchema newInstanceSchema, ListFieldSchema listFieldSchema, UnknownFieldSchema<?, ?> unknownFieldSchema, ExtensionSchema<?> extensionSchema, MapFieldSchema mapFieldSchema) {
        int fieldNumber;
        int fieldNumber2;
        int i;
        FieldInfo[] fields = messageInfo.getFields();
        if (fields.length == 0) {
            fieldNumber = 0;
            fieldNumber2 = 0;
        } else {
            fieldNumber = fields[0].getFieldNumber();
            fieldNumber2 = fields[fields.length - 1].getFieldNumber();
        }
        int length = fields.length;
        int[] iArr = new int[length * 3];
        Object[] objArr = new Object[length * 2];
        int i2 = 0;
        int i3 = 0;
        for (FieldInfo fieldInfo : fields) {
            if (fieldInfo.getType() == FieldType.MAP) {
                i2++;
            } else if (fieldInfo.getType().id() >= 18 && fieldInfo.getType().id() <= 49) {
                i3++;
            }
        }
        int[] iArr2 = i2 > 0 ? new int[i2] : null;
        int[] iArr3 = i3 > 0 ? new int[i3] : null;
        int[] checkInitialized = messageInfo.getCheckInitialized();
        if (checkInitialized == null) {
            checkInitialized = EMPTY_INT_ARRAY;
        }
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (i4 < fields.length) {
            FieldInfo fieldInfo2 = fields[i4];
            int fieldNumber3 = fieldInfo2.getFieldNumber();
            storeFieldData(fieldInfo2, iArr, i5, objArr);
            if (i6 < checkInitialized.length && checkInitialized[i6] == fieldNumber3) {
                checkInitialized[i6] = i5;
                i6++;
            }
            if (fieldInfo2.getType() == FieldType.MAP) {
                iArr2[i7] = i5;
                i7++;
            } else {
                if (fieldInfo2.getType().id() >= 18 && fieldInfo2.getType().id() <= 49) {
                    i = i5;
                    iArr3[i8] = (int) UnsafeUtil.objectFieldOffset(fieldInfo2.getField());
                    i8++;
                }
                i4++;
                i5 = i + 3;
            }
            i = i5;
            i4++;
            i5 = i + 3;
        }
        if (iArr2 == null) {
            iArr2 = EMPTY_INT_ARRAY;
        }
        if (iArr3 == null) {
            iArr3 = EMPTY_INT_ARRAY;
        }
        int[] iArr4 = new int[checkInitialized.length + iArr2.length + iArr3.length];
        System.arraycopy(checkInitialized, 0, iArr4, 0, checkInitialized.length);
        System.arraycopy(iArr2, 0, iArr4, checkInitialized.length, iArr2.length);
        System.arraycopy(iArr3, 0, iArr4, checkInitialized.length + iArr2.length, iArr3.length);
        return new MessageSchema<>(iArr, objArr, fieldNumber, fieldNumber2, messageInfo.getDefaultInstance(), messageInfo.getSyntax(), true, iArr4, checkInitialized.length, checkInitialized.length + iArr2.length, newInstanceSchema, listFieldSchema, unknownFieldSchema, extensionSchema, mapFieldSchema);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void storeFieldData(com.google.crypto.tink.shaded.protobuf.FieldInfo r8, int[] r9, int r10, java.lang.Object[] r11) {
        /*
            Method dump skipped, instruction units count: 220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.storeFieldData(com.google.crypto.tink.shaded.protobuf.FieldInfo, int[], int, java.lang.Object[]):void");
    }

    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    public T newInstance() {
        return (T) this.newInstanceSchema.newInstance(this.defaultInstance);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    public boolean equals(T message, T other) {
        int length = this.buffer.length;
        for (int i = 0; i < length; i += 3) {
            if (!equals(message, other, i)) {
                return false;
            }
        }
        if (!this.unknownFieldSchema.getFromMessage(message).equals(this.unknownFieldSchema.getFromMessage(other))) {
            return false;
        }
        if (this.hasExtensions) {
            return this.extensionSchema.getExtensions(message).equals(this.extensionSchema.getExtensions(other));
        }
        return true;
    }

    private boolean equals(T message, T other, int pos) {
        int iTypeAndOffsetAt = typeAndOffsetAt(pos);
        long jOffset = offset(iTypeAndOffsetAt);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (arePresentForEquals(message, other, pos) && Double.doubleToLongBits(UnsafeUtil.getDouble(message, jOffset)) == Double.doubleToLongBits(UnsafeUtil.getDouble(other, jOffset))) {
                    break;
                }
                break;
            case 1:
                if (arePresentForEquals(message, other, pos) && Float.floatToIntBits(UnsafeUtil.getFloat(message, jOffset)) == Float.floatToIntBits(UnsafeUtil.getFloat(other, jOffset))) {
                    break;
                }
                break;
            case 2:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, jOffset) == UnsafeUtil.getLong(other, jOffset)) {
                    break;
                }
                break;
            case 3:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, jOffset) == UnsafeUtil.getLong(other, jOffset)) {
                    break;
                }
                break;
            case 4:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, jOffset) == UnsafeUtil.getInt(other, jOffset)) {
                    break;
                }
                break;
            case 5:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, jOffset) == UnsafeUtil.getLong(other, jOffset)) {
                    break;
                }
                break;
            case 6:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, jOffset) == UnsafeUtil.getInt(other, jOffset)) {
                    break;
                }
                break;
            case 7:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getBoolean(message, jOffset) == UnsafeUtil.getBoolean(other, jOffset)) {
                    break;
                }
                break;
            case 8:
                if (arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, jOffset), UnsafeUtil.getObject(other, jOffset))) {
                    break;
                }
                break;
            case 9:
                if (arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, jOffset), UnsafeUtil.getObject(other, jOffset))) {
                    break;
                }
                break;
            case 10:
                if (arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, jOffset), UnsafeUtil.getObject(other, jOffset))) {
                    break;
                }
                break;
            case 11:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, jOffset) == UnsafeUtil.getInt(other, jOffset)) {
                    break;
                }
                break;
            case 12:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, jOffset) == UnsafeUtil.getInt(other, jOffset)) {
                    break;
                }
                break;
            case 13:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, jOffset) == UnsafeUtil.getInt(other, jOffset)) {
                    break;
                }
                break;
            case 14:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, jOffset) == UnsafeUtil.getLong(other, jOffset)) {
                    break;
                }
                break;
            case 15:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getInt(message, jOffset) == UnsafeUtil.getInt(other, jOffset)) {
                    break;
                }
                break;
            case 16:
                if (arePresentForEquals(message, other, pos) && UnsafeUtil.getLong(message, jOffset) == UnsafeUtil.getLong(other, jOffset)) {
                    break;
                }
                break;
            case 17:
                if (arePresentForEquals(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, jOffset), UnsafeUtil.getObject(other, jOffset))) {
                    break;
                }
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                if (isOneofCaseEqual(message, other, pos) && SchemaUtil.safeEquals(UnsafeUtil.getObject(message, jOffset), UnsafeUtil.getObject(other, jOffset))) {
                    break;
                }
                break;
        }
        return true;
    }

    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    public int hashCode(T message) {
        int i;
        int iHashLong;
        int length = this.buffer.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3 += 3) {
            int iTypeAndOffsetAt = typeAndOffsetAt(i3);
            int iNumberAt = numberAt(i3);
            long jOffset = offset(iTypeAndOffsetAt);
            int iHashCode = 37;
            switch (type(iTypeAndOffsetAt)) {
                case 0:
                    i = i2 * 53;
                    iHashLong = Internal.hashLong(Double.doubleToLongBits(UnsafeUtil.getDouble(message, jOffset)));
                    i2 = i + iHashLong;
                    break;
                case 1:
                    i = i2 * 53;
                    iHashLong = Float.floatToIntBits(UnsafeUtil.getFloat(message, jOffset));
                    i2 = i + iHashLong;
                    break;
                case 2:
                    i = i2 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(message, jOffset));
                    i2 = i + iHashLong;
                    break;
                case 3:
                    i = i2 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(message, jOffset));
                    i2 = i + iHashLong;
                    break;
                case 4:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getInt(message, jOffset);
                    i2 = i + iHashLong;
                    break;
                case 5:
                    i = i2 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(message, jOffset));
                    i2 = i + iHashLong;
                    break;
                case 6:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getInt(message, jOffset);
                    i2 = i + iHashLong;
                    break;
                case 7:
                    i = i2 * 53;
                    iHashLong = Internal.hashBoolean(UnsafeUtil.getBoolean(message, jOffset));
                    i2 = i + iHashLong;
                    break;
                case 8:
                    i = i2 * 53;
                    iHashLong = ((String) UnsafeUtil.getObject(message, jOffset)).hashCode();
                    i2 = i + iHashLong;
                    break;
                case 9:
                    Object object = UnsafeUtil.getObject(message, jOffset);
                    if (object != null) {
                        iHashCode = object.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 10:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getObject(message, jOffset).hashCode();
                    i2 = i + iHashLong;
                    break;
                case 11:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getInt(message, jOffset);
                    i2 = i + iHashLong;
                    break;
                case 12:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getInt(message, jOffset);
                    i2 = i + iHashLong;
                    break;
                case 13:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getInt(message, jOffset);
                    i2 = i + iHashLong;
                    break;
                case 14:
                    i = i2 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(message, jOffset));
                    i2 = i + iHashLong;
                    break;
                case 15:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getInt(message, jOffset);
                    i2 = i + iHashLong;
                    break;
                case 16:
                    i = i2 * 53;
                    iHashLong = Internal.hashLong(UnsafeUtil.getLong(message, jOffset));
                    i2 = i + iHashLong;
                    break;
                case 17:
                    Object object2 = UnsafeUtil.getObject(message, jOffset);
                    if (object2 != null) {
                        iHashCode = object2.hashCode();
                    }
                    i2 = (i2 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getObject(message, jOffset).hashCode();
                    i2 = i + iHashLong;
                    break;
                case 50:
                    i = i2 * 53;
                    iHashLong = UnsafeUtil.getObject(message, jOffset).hashCode();
                    i2 = i + iHashLong;
                    break;
                case 51:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Internal.hashLong(Double.doubleToLongBits(oneofDoubleAt(message, jOffset)));
                        i2 = i + iHashLong;
                    }
                    break;
                case 52:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Float.floatToIntBits(oneofFloatAt(message, jOffset));
                        i2 = i + iHashLong;
                    }
                    break;
                case 53:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(message, jOffset));
                        i2 = i + iHashLong;
                    }
                    break;
                case 54:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(message, jOffset));
                        i2 = i + iHashLong;
                    }
                    break;
                case 55:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = oneofIntAt(message, jOffset);
                        i2 = i + iHashLong;
                    }
                    break;
                case 56:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(message, jOffset));
                        i2 = i + iHashLong;
                    }
                    break;
                case 57:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = oneofIntAt(message, jOffset);
                        i2 = i + iHashLong;
                    }
                    break;
                case 58:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Internal.hashBoolean(oneofBooleanAt(message, jOffset));
                        i2 = i + iHashLong;
                    }
                    break;
                case 59:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = ((String) UnsafeUtil.getObject(message, jOffset)).hashCode();
                        i2 = i + iHashLong;
                    }
                    break;
                case 60:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = UnsafeUtil.getObject(message, jOffset).hashCode();
                        i2 = i + iHashLong;
                    }
                    break;
                case 61:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = UnsafeUtil.getObject(message, jOffset).hashCode();
                        i2 = i + iHashLong;
                    }
                    break;
                case 62:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = oneofIntAt(message, jOffset);
                        i2 = i + iHashLong;
                    }
                    break;
                case 63:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = oneofIntAt(message, jOffset);
                        i2 = i + iHashLong;
                    }
                    break;
                case 64:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = oneofIntAt(message, jOffset);
                        i2 = i + iHashLong;
                    }
                    break;
                case 65:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(message, jOffset));
                        i2 = i + iHashLong;
                    }
                    break;
                case 66:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = oneofIntAt(message, jOffset);
                        i2 = i + iHashLong;
                    }
                    break;
                case 67:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = Internal.hashLong(oneofLongAt(message, jOffset));
                        i2 = i + iHashLong;
                    }
                    break;
                case 68:
                    if (isOneofPresent(message, iNumberAt, i3)) {
                        i = i2 * 53;
                        iHashLong = UnsafeUtil.getObject(message, jOffset).hashCode();
                        i2 = i + iHashLong;
                    }
                    break;
            }
        }
        int iHashCode2 = (i2 * 53) + this.unknownFieldSchema.getFromMessage(message).hashCode();
        return this.hasExtensions ? (iHashCode2 * 53) + this.extensionSchema.getExtensions(message).hashCode() : iHashCode2;
    }

    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    public void mergeFrom(T message, T other) {
        checkMutable(message);
        other.getClass();
        for (int i = 0; i < this.buffer.length; i += 3) {
            mergeSingleField(message, other, i);
        }
        SchemaUtil.mergeUnknownFields(this.unknownFieldSchema, message, other);
        if (this.hasExtensions) {
            SchemaUtil.mergeExtensions(this.extensionSchema, message, other);
        }
    }

    private void mergeSingleField(T message, T other, int pos) {
        int iTypeAndOffsetAt = typeAndOffsetAt(pos);
        long jOffset = offset(iTypeAndOffsetAt);
        int iNumberAt = numberAt(pos);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putDouble(message, jOffset, UnsafeUtil.getDouble(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 1:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putFloat(message, jOffset, UnsafeUtil.getFloat(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 2:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, jOffset, UnsafeUtil.getLong(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 3:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, jOffset, UnsafeUtil.getLong(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 4:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, jOffset, UnsafeUtil.getInt(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 5:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, jOffset, UnsafeUtil.getLong(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 6:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, jOffset, UnsafeUtil.getInt(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 7:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putBoolean(message, jOffset, UnsafeUtil.getBoolean(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 8:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject(message, jOffset, UnsafeUtil.getObject(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 9:
                mergeMessage(message, other, pos);
                break;
            case 10:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putObject(message, jOffset, UnsafeUtil.getObject(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 11:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, jOffset, UnsafeUtil.getInt(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 12:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, jOffset, UnsafeUtil.getInt(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 13:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, jOffset, UnsafeUtil.getInt(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 14:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, jOffset, UnsafeUtil.getLong(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 15:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putInt(message, jOffset, UnsafeUtil.getInt(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 16:
                if (isFieldPresent(other, pos)) {
                    UnsafeUtil.putLong(message, jOffset, UnsafeUtil.getLong(other, jOffset));
                    setFieldPresent(message, pos);
                }
                break;
            case 17:
                mergeMessage(message, other, pos);
                break;
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                this.listFieldSchema.mergeListsAt(message, other, jOffset);
                break;
            case 50:
                SchemaUtil.mergeMap(this.mapFieldSchema, message, other, jOffset);
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                if (isOneofPresent(other, iNumberAt, pos)) {
                    UnsafeUtil.putObject(message, jOffset, UnsafeUtil.getObject(other, jOffset));
                    setOneofPresent(message, iNumberAt, pos);
                }
                break;
            case 60:
                mergeOneofMessage(message, other, pos);
                break;
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
                if (isOneofPresent(other, iNumberAt, pos)) {
                    UnsafeUtil.putObject(message, jOffset, UnsafeUtil.getObject(other, jOffset));
                    setOneofPresent(message, iNumberAt, pos);
                }
                break;
            case 68:
                mergeOneofMessage(message, other, pos);
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void mergeMessage(T targetParent, T sourceParent, int pos) {
        if (isFieldPresent(sourceParent, pos)) {
            long jOffset = offset(typeAndOffsetAt(pos));
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(sourceParent, jOffset);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + numberAt(pos) + " is present but null: " + sourceParent);
            }
            Schema messageFieldSchema = getMessageFieldSchema(pos);
            if (!isFieldPresent(targetParent, pos)) {
                if (!isMutable(object)) {
                    unsafe.putObject(targetParent, jOffset, object);
                } else {
                    Object objNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(objNewInstance, object);
                    unsafe.putObject(targetParent, jOffset, objNewInstance);
                }
                setFieldPresent(targetParent, pos);
                return;
            }
            Object object2 = unsafe.getObject(targetParent, jOffset);
            if (!isMutable(object2)) {
                Object objNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(objNewInstance2, object2);
                unsafe.putObject(targetParent, jOffset, objNewInstance2);
                object2 = objNewInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void mergeOneofMessage(T targetParent, T sourceParent, int pos) {
        int iNumberAt = numberAt(pos);
        if (isOneofPresent(sourceParent, iNumberAt, pos)) {
            long jOffset = offset(typeAndOffsetAt(pos));
            Unsafe unsafe = UNSAFE;
            Object object = unsafe.getObject(sourceParent, jOffset);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + numberAt(pos) + " is present but null: " + sourceParent);
            }
            Schema messageFieldSchema = getMessageFieldSchema(pos);
            if (!isOneofPresent(targetParent, iNumberAt, pos)) {
                if (!isMutable(object)) {
                    unsafe.putObject(targetParent, jOffset, object);
                } else {
                    Object objNewInstance = messageFieldSchema.newInstance();
                    messageFieldSchema.mergeFrom(objNewInstance, object);
                    unsafe.putObject(targetParent, jOffset, objNewInstance);
                }
                setOneofPresent(targetParent, iNumberAt, pos);
                return;
            }
            Object object2 = unsafe.getObject(targetParent, jOffset);
            if (!isMutable(object2)) {
                Object objNewInstance2 = messageFieldSchema.newInstance();
                messageFieldSchema.mergeFrom(objNewInstance2, object2);
                unsafe.putObject(targetParent, jOffset, objNewInstance2);
                object2 = objNewInstance2;
            }
            messageFieldSchema.mergeFrom(object2, object);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005d  */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v215 */
    /* JADX WARN: Type inference failed for: r0v217 */
    /* JADX WARN: Type inference failed for: r0v219 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v14 */
    /* JADX WARN: Type inference failed for: r15v15 */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v17 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r16v0 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2 */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v23 */
    /* JADX WARN: Type inference failed for: r1v75 */
    /* JADX WARN: Type inference failed for: r1v76 */
    /* JADX WARN: Type inference failed for: r1v78 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v18 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v41 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v16 */
    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getSerializedSize(T r19) {
        /*
            Method dump skipped, instruction units count: 1708
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.getSerializedSize(java.lang.Object):int");
    }

    private <UT, UB> int getUnknownFieldsSerializedSize(UnknownFieldSchema<UT, UB> schema, T message) {
        return schema.getSerializedSize(schema.getFromMessage(message));
    }

    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    public void writeTo(T message, Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            writeFieldsInDescendingOrder(message, writer);
        } else {
            writeFieldsInAscendingOrder(message, writer);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInAscendingOrder(T r22, com.google.crypto.tink.shaded.protobuf.Writer r23) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1744
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.writeFieldsInAscendingOrder(java.lang.Object, com.google.crypto.tink.shaded.protobuf.Writer):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeFieldsInDescendingOrder(T r11, com.google.crypto.tink.shaded.protobuf.Writer r12) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 1586
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.writeFieldsInDescendingOrder(java.lang.Object, com.google.crypto.tink.shaded.protobuf.Writer):void");
    }

    private <K, V> void writeMapHelper(Writer writer, int number, Object mapField, int pos) throws IOException {
        if (mapField != null) {
            writer.writeMap(number, this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(pos)), this.mapFieldSchema.forMapData(mapField));
        }
    }

    private <UT, UB> void writeUnknownInMessageTo(UnknownFieldSchema<UT, UB> schema, T message, Writer writer) throws IOException {
        schema.writeTo(schema.getFromMessage(message), writer);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    public void mergeFrom(T message, Reader reader, ExtensionRegistryLite extensionRegistry) throws Throwable {
        extensionRegistry.getClass();
        checkMutable(message);
        mergeFromHelper(this.unknownFieldSchema, this.extensionSchema, message, reader, extensionRegistry);
    }

    /* JADX WARN: Code restructure failed: missing block: B:245:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x008c, code lost:
    
        r0 = r18.checkInitializedCount;
        r4 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0091, code lost:
    
        if (r0 >= r18.repeatedFieldOffsetStart) goto L242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0093, code lost:
    
        r4 = filterMapUnknownEnumValues(r21, r18.intArray[r0], r4, r19, r21);
        r0 = r0 + 1;
        r3 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a9, code lost:
    
        r10 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00aa, code lost:
    
        if (r4 == null) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ac, code lost:
    
        r7.setBuilderToMessage(r10, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00af, code lost:
    
        return;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0646 A[Catch: all -> 0x069e, TRY_LEAVE, TryCatch #13 {all -> 0x069e, blocks: (B:156:0x0617, B:167:0x0640, B:169:0x0646, B:179:0x066e, B:180:0x0673), top: B:208:0x0617 }] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x066c  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x06b0 A[LOOP:4: B:199:0x06ac->B:201:0x06b0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:203:0x06c5  */
    /* JADX WARN: Type inference failed for: r22v0, types: [com.google.crypto.tink.shaded.protobuf.Reader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private <UT, UB, ET extends com.google.crypto.tink.shaded.protobuf.FieldSet.FieldDescriptorLite<ET>> void mergeFromHelper(com.google.crypto.tink.shaded.protobuf.UnknownFieldSchema<UT, UB> r19, com.google.crypto.tink.shaded.protobuf.ExtensionSchema<ET> r20, T r21, com.google.crypto.tink.shaded.protobuf.Reader r22, com.google.crypto.tink.shaded.protobuf.ExtensionRegistryLite r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 1880
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.mergeFromHelper(com.google.crypto.tink.shaded.protobuf.UnknownFieldSchema, com.google.crypto.tink.shaded.protobuf.ExtensionSchema, java.lang.Object, com.google.crypto.tink.shaded.protobuf.Reader, com.google.crypto.tink.shaded.protobuf.ExtensionRegistryLite):void");
    }

    static UnknownFieldSetLite getMutableUnknownFields(Object message) {
        GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) message;
        UnknownFieldSetLite unknownFieldSetLite = generatedMessageLite.unknownFields;
        if (unknownFieldSetLite != UnknownFieldSetLite.getDefaultInstance()) {
            return unknownFieldSetLite;
        }
        UnknownFieldSetLite unknownFieldSetLiteNewInstance = UnknownFieldSetLite.newInstance();
        generatedMessageLite.unknownFields = unknownFieldSetLiteNewInstance;
        return unknownFieldSetLiteNewInstance;
    }

    /* JADX INFO: renamed from: com.google.crypto.tink.shaded.protobuf.MessageSchema$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$FieldType;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            $SwitchMap$com$google$protobuf$WireFormat$FieldType = iArr;
            try {
                iArr[WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED32.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SFIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.ENUM.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.INT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.UINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.MESSAGE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT32.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.SINT64.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$FieldType[WireFormat.FieldType.STRING.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    private int decodeMapEntryValue(byte[] data, int position, int limit, WireFormat.FieldType fieldType, Class<?> messageType, ArrayDecoders.Registers registers) throws IOException {
        switch (AnonymousClass1.$SwitchMap$com$google$protobuf$WireFormat$FieldType[fieldType.ordinal()]) {
            case 1:
                int iDecodeVarint64 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Boolean.valueOf(registers.long1 != 0);
                return iDecodeVarint64;
            case 2:
                return ArrayDecoders.decodeBytes(data, position, registers);
            case 3:
                registers.object1 = Double.valueOf(ArrayDecoders.decodeDouble(data, position));
                return position + 8;
            case 4:
            case 5:
                registers.object1 = Integer.valueOf(ArrayDecoders.decodeFixed32(data, position));
                return position + 4;
            case 6:
            case 7:
                registers.object1 = Long.valueOf(ArrayDecoders.decodeFixed64(data, position));
                return position + 8;
            case 8:
                registers.object1 = Float.valueOf(ArrayDecoders.decodeFloat(data, position));
                return position + 4;
            case 9:
            case 10:
            case 11:
                int iDecodeVarint32 = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = Integer.valueOf(registers.int1);
                return iDecodeVarint32;
            case 12:
            case 13:
                int iDecodeVarint642 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Long.valueOf(registers.long1);
                return iDecodeVarint642;
            case 14:
                return ArrayDecoders.decodeMessageField(Protobuf.getInstance().schemaFor((Class) messageType), data, position, limit, registers);
            case 15:
                int iDecodeVarint322 = ArrayDecoders.decodeVarint32(data, position, registers);
                registers.object1 = Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1));
                return iDecodeVarint322;
            case 16:
                int iDecodeVarint643 = ArrayDecoders.decodeVarint64(data, position, registers);
                registers.object1 = Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1));
                return iDecodeVarint643;
            case 17:
                return ArrayDecoders.decodeStringRequireUtf8(data, position, registers);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <K, V> int decodeMapEntry(byte[] data, int position, int limit, MapEntryLite.Metadata<K, V> metadata, Map<K, V> target, ArrayDecoders.Registers registers) throws IOException {
        int iDecodeVarint32;
        int iDecodeVarint322 = ArrayDecoders.decodeVarint32(data, position, registers);
        int i = registers.int1;
        if (i < 0 || i > limit - iDecodeVarint322) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        int i2 = iDecodeVarint322 + i;
        Object obj = metadata.defaultKey;
        Object obj2 = metadata.defaultValue;
        while (iDecodeVarint322 < i2) {
            int i3 = iDecodeVarint322 + 1;
            int i4 = data[iDecodeVarint322];
            if (i4 < 0) {
                iDecodeVarint32 = ArrayDecoders.decodeVarint32(i4, data, i3, registers);
                i4 = registers.int1;
            } else {
                iDecodeVarint32 = i3;
            }
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i5 == 1) {
                if (i6 == metadata.keyType.getWireType()) {
                    iDecodeVarint322 = decodeMapEntryValue(data, iDecodeVarint32, limit, metadata.keyType, null, registers);
                    obj = registers.object1;
                } else {
                    iDecodeVarint322 = ArrayDecoders.skipField(i4, data, iDecodeVarint32, limit, registers);
                }
            } else if (i5 == 2 && i6 == metadata.valueType.getWireType()) {
                iDecodeVarint322 = decodeMapEntryValue(data, iDecodeVarint32, limit, metadata.valueType, metadata.defaultValue.getClass(), registers);
                obj2 = registers.object1;
            } else {
                iDecodeVarint322 = ArrayDecoders.skipField(i4, data, iDecodeVarint32, limit, registers);
            }
        }
        if (iDecodeVarint322 != i2) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        target.put(obj, obj2);
        return i2;
    }

    private int parseRepeatedField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int bufferPosition, long typeAndOffset, int fieldType, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
        int iDecodeVarint32List;
        Unsafe unsafe = UNSAFE;
        Internal.ProtobufList protobufListMutableCopyWithCapacity2 = (Internal.ProtobufList) unsafe.getObject(message, fieldOffset);
        if (!protobufListMutableCopyWithCapacity2.isModifiable()) {
            int size = protobufListMutableCopyWithCapacity2.size();
            protobufListMutableCopyWithCapacity2 = protobufListMutableCopyWithCapacity2.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
            unsafe.putObject(message, fieldOffset, protobufListMutableCopyWithCapacity2);
        }
        switch (fieldType) {
            case 18:
            case 35:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedDoubleList(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 1 ? ArrayDecoders.decodeDoubleList(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 19:
            case 36:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedFloatList(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 5 ? ArrayDecoders.decodeFloatList(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 20:
            case 21:
            case 37:
            case 38:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedVarint64List(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 0 ? ArrayDecoders.decodeVarint64List(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 22:
            case 29:
            case 39:
            case 43:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedVarint32List(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 0 ? ArrayDecoders.decodeVarint32List(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 23:
            case 32:
            case 40:
            case 46:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedFixed64List(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 1 ? ArrayDecoders.decodeFixed64List(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 24:
            case 31:
            case 41:
            case 45:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedFixed32List(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 5 ? ArrayDecoders.decodeFixed32List(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 25:
            case 42:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedBoolList(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 0 ? ArrayDecoders.decodeBoolList(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 26:
                if (wireType != 2) {
                    return position;
                }
                if ((typeAndOffset & 536870912) == 0) {
                    return ArrayDecoders.decodeStringList(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers);
                }
                return ArrayDecoders.decodeStringListRequireUtf8(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers);
            case 27:
                return wireType == 2 ? ArrayDecoders.decodeMessageList(getMessageFieldSchema(bufferPosition), tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 28:
                return wireType == 2 ? ArrayDecoders.decodeBytesList(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 30:
            case 44:
                if (wireType == 2) {
                    iDecodeVarint32List = ArrayDecoders.decodePackedVarint32List(data, position, protobufListMutableCopyWithCapacity2, registers);
                } else {
                    if (wireType != 0) {
                        return position;
                    }
                    iDecodeVarint32List = ArrayDecoders.decodeVarint32List(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers);
                }
                SchemaUtil.filterUnknownEnumList((Object) message, number, (List<Integer>) protobufListMutableCopyWithCapacity2, getEnumFieldVerifier(bufferPosition), (Object) null, (UnknownFieldSchema<UT, Object>) this.unknownFieldSchema);
                return iDecodeVarint32List;
            case 33:
            case 47:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedSInt32List(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 0 ? ArrayDecoders.decodeSInt32List(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 34:
            case 48:
                if (wireType == 2) {
                    return ArrayDecoders.decodePackedSInt64List(data, position, protobufListMutableCopyWithCapacity2, registers);
                }
                return wireType == 0 ? ArrayDecoders.decodeSInt64List(tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            case 49:
                return wireType == 3 ? ArrayDecoders.decodeGroupList(getMessageFieldSchema(bufferPosition), tag, data, position, limit, protobufListMutableCopyWithCapacity2, registers) : position;
            default:
                return position;
        }
    }

    private <K, V> int parseMapField(T message, byte[] data, int position, int limit, int bufferPosition, long fieldOffset, ArrayDecoders.Registers registers) throws IOException {
        Unsafe unsafe = UNSAFE;
        Object mapFieldDefaultEntry = getMapFieldDefaultEntry(bufferPosition);
        Object object = unsafe.getObject(message, fieldOffset);
        if (this.mapFieldSchema.isImmutable(object)) {
            Object objNewMapField = this.mapFieldSchema.newMapField(mapFieldDefaultEntry);
            this.mapFieldSchema.mergeFrom(objNewMapField, object);
            unsafe.putObject(message, fieldOffset, objNewMapField);
            object = objNewMapField;
        }
        return decodeMapEntry(data, position, limit, this.mapFieldSchema.forMapMetadata(mapFieldDefaultEntry), this.mapFieldSchema.forMutableMapData(object), registers);
    }

    private int parseOneofField(T message, byte[] data, int position, int limit, int tag, int number, int wireType, int typeAndOffset, int fieldType, long fieldOffset, int bufferPosition, ArrayDecoders.Registers registers) throws IOException {
        Unsafe unsafe = UNSAFE;
        long j = this.buffer[bufferPosition + 2] & 1048575;
        switch (fieldType) {
            case 51:
                if (wireType != 1) {
                    return position;
                }
                unsafe.putObject(message, fieldOffset, Double.valueOf(ArrayDecoders.decodeDouble(data, position)));
                int i = position + 8;
                unsafe.putInt(message, j, number);
                return i;
            case 52:
                if (wireType != 5) {
                    return position;
                }
                unsafe.putObject(message, fieldOffset, Float.valueOf(ArrayDecoders.decodeFloat(data, position)));
                int i2 = position + 4;
                unsafe.putInt(message, j, number);
                return i2;
            case 53:
            case 54:
                if (wireType != 0) {
                    return position;
                }
                int iDecodeVarint64 = ArrayDecoders.decodeVarint64(data, position, registers);
                unsafe.putObject(message, fieldOffset, Long.valueOf(registers.long1));
                unsafe.putInt(message, j, number);
                return iDecodeVarint64;
            case 55:
            case 62:
                if (wireType != 0) {
                    return position;
                }
                int iDecodeVarint32 = ArrayDecoders.decodeVarint32(data, position, registers);
                unsafe.putObject(message, fieldOffset, Integer.valueOf(registers.int1));
                unsafe.putInt(message, j, number);
                return iDecodeVarint32;
            case 56:
            case 65:
                if (wireType != 1) {
                    return position;
                }
                unsafe.putObject(message, fieldOffset, Long.valueOf(ArrayDecoders.decodeFixed64(data, position)));
                int i3 = position + 8;
                unsafe.putInt(message, j, number);
                return i3;
            case 57:
            case 64:
                if (wireType != 5) {
                    return position;
                }
                unsafe.putObject(message, fieldOffset, Integer.valueOf(ArrayDecoders.decodeFixed32(data, position)));
                int i4 = position + 4;
                unsafe.putInt(message, j, number);
                return i4;
            case 58:
                if (wireType != 0) {
                    return position;
                }
                int iDecodeVarint642 = ArrayDecoders.decodeVarint64(data, position, registers);
                unsafe.putObject(message, fieldOffset, Boolean.valueOf(registers.long1 != 0));
                unsafe.putInt(message, j, number);
                return iDecodeVarint642;
            case 59:
                if (wireType != 2) {
                    return position;
                }
                int iDecodeVarint322 = ArrayDecoders.decodeVarint32(data, position, registers);
                int i5 = registers.int1;
                if (i5 == 0) {
                    unsafe.putObject(message, fieldOffset, "");
                } else {
                    if ((typeAndOffset & ENFORCE_UTF8_MASK) != 0 && !Utf8.isValidUtf8(data, iDecodeVarint322, iDecodeVarint322 + i5)) {
                        throw InvalidProtocolBufferException.invalidUtf8();
                    }
                    unsafe.putObject(message, fieldOffset, new String(data, iDecodeVarint322, i5, Internal.UTF_8));
                    iDecodeVarint322 += i5;
                }
                unsafe.putInt(message, j, number);
                return iDecodeVarint322;
            case 60:
                if (wireType != 2) {
                    return position;
                }
                Object objMutableOneofMessageFieldForMerge = mutableOneofMessageFieldForMerge(message, number, bufferPosition);
                int iMergeMessageField = ArrayDecoders.mergeMessageField(objMutableOneofMessageFieldForMerge, getMessageFieldSchema(bufferPosition), data, position, limit, registers);
                storeOneofMessageField(message, number, bufferPosition, objMutableOneofMessageFieldForMerge);
                return iMergeMessageField;
            case 61:
                if (wireType != 2) {
                    return position;
                }
                int iDecodeBytes = ArrayDecoders.decodeBytes(data, position, registers);
                unsafe.putObject(message, fieldOffset, registers.object1);
                unsafe.putInt(message, j, number);
                return iDecodeBytes;
            case 63:
                if (wireType != 0) {
                    return position;
                }
                int iDecodeVarint323 = ArrayDecoders.decodeVarint32(data, position, registers);
                int i6 = registers.int1;
                Internal.EnumVerifier enumFieldVerifier = getEnumFieldVerifier(bufferPosition);
                if (enumFieldVerifier == null || enumFieldVerifier.isInRange(i6)) {
                    unsafe.putObject(message, fieldOffset, Integer.valueOf(i6));
                    unsafe.putInt(message, j, number);
                } else {
                    getMutableUnknownFields(message).storeField(tag, Long.valueOf(i6));
                }
                return iDecodeVarint323;
            case 66:
                if (wireType != 0) {
                    return position;
                }
                int iDecodeVarint324 = ArrayDecoders.decodeVarint32(data, position, registers);
                unsafe.putObject(message, fieldOffset, Integer.valueOf(CodedInputStream.decodeZigZag32(registers.int1)));
                unsafe.putInt(message, j, number);
                return iDecodeVarint324;
            case 67:
                if (wireType != 0) {
                    return position;
                }
                int iDecodeVarint643 = ArrayDecoders.decodeVarint64(data, position, registers);
                unsafe.putObject(message, fieldOffset, Long.valueOf(CodedInputStream.decodeZigZag64(registers.long1)));
                unsafe.putInt(message, j, number);
                return iDecodeVarint643;
            case 68:
                if (wireType != 3) {
                    return position;
                }
                Object objMutableOneofMessageFieldForMerge2 = mutableOneofMessageFieldForMerge(message, number, bufferPosition);
                int iMergeGroupField = ArrayDecoders.mergeGroupField(objMutableOneofMessageFieldForMerge2, getMessageFieldSchema(bufferPosition), data, position, limit, (tag & (-8)) | 4, registers);
                storeOneofMessageField(message, number, bufferPosition, objMutableOneofMessageFieldForMerge2);
                return iMergeGroupField;
            default:
                return position;
        }
    }

    private Schema getMessageFieldSchema(int pos) {
        int i = (pos / 3) * 2;
        Schema schema = (Schema) this.objects[i];
        if (schema != null) {
            return schema;
        }
        Schema<T> schemaSchemaFor = Protobuf.getInstance().schemaFor((Class) this.objects[i + 1]);
        this.objects[i] = schemaSchemaFor;
        return schemaSchemaFor;
    }

    private Object getMapFieldDefaultEntry(int pos) {
        return this.objects[(pos / 3) * 2];
    }

    private Internal.EnumVerifier getEnumFieldVerifier(int pos) {
        return (Internal.EnumVerifier) this.objects[((pos / 3) * 2) + 1];
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:27:0x0099. Please report as an issue. */
    int parseMessage(T message, byte[] data, int position, int limit, int endDelimited, ArrayDecoders.Registers registers) throws IOException {
        Unsafe unsafe;
        int i;
        MessageSchema<T> messageSchema;
        int i2;
        int i3;
        int i4;
        int i5;
        T t;
        int i6;
        int iPositionForFieldNumber;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        byte[] bArr;
        int i13;
        int i14;
        byte b;
        byte[] bArr2;
        int iDecodeVarint64;
        int i15;
        int i16;
        MessageSchema<T> messageSchema2 = this;
        T t2 = message;
        byte[] bArr3 = data;
        int i17 = limit;
        int i18 = endDelimited;
        ArrayDecoders.Registers registers2 = registers;
        checkMutable(message);
        Unsafe unsafe2 = UNSAFE;
        int iDecodeUnknownField = position;
        int i19 = 0;
        int i20 = 0;
        int i21 = 0;
        int i22 = -1;
        int i23 = 1048575;
        while (true) {
            if (iDecodeUnknownField < i17) {
                int i24 = iDecodeUnknownField + 1;
                byte b2 = bArr3[iDecodeUnknownField];
                if (b2 < 0) {
                    int iDecodeVarint32 = ArrayDecoders.decodeVarint32(b2, bArr3, i24, registers2);
                    i6 = registers2.int1;
                    i24 = iDecodeVarint32;
                } else {
                    i6 = b2;
                }
                int i25 = i6 >>> 3;
                int i26 = i6 & 7;
                if (i25 > i22) {
                    iPositionForFieldNumber = messageSchema2.positionForFieldNumber(i25, i19 / 3);
                } else {
                    iPositionForFieldNumber = messageSchema2.positionForFieldNumber(i25);
                }
                int i27 = iPositionForFieldNumber;
                if (i27 == -1) {
                    i7 = i25;
                    i8 = i24;
                    i3 = i6;
                    i9 = i21;
                    i10 = i23;
                    unsafe = unsafe2;
                    i = i18;
                    i11 = 0;
                } else {
                    int i28 = messageSchema2.buffer[i27 + 1];
                    int iType = type(i28);
                    long jOffset = offset(i28);
                    int i29 = i6;
                    if (iType <= 17) {
                        int i30 = messageSchema2.buffer[i27 + 2];
                        int i31 = 1 << (i30 >>> 20);
                        int i32 = 1048575;
                        int i33 = i30 & 1048575;
                        if (i33 != i23) {
                            if (i23 != 1048575) {
                                unsafe2.putInt(t2, i23, i21);
                                i32 = 1048575;
                            }
                            i10 = i33;
                            i12 = i33 == i32 ? 0 : unsafe2.getInt(t2, i33);
                        } else {
                            i12 = i21;
                            i10 = i23;
                        }
                        switch (iType) {
                            case 0:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 1) {
                                    UnsafeUtil.putDouble(t2, jOffset, ArrayDecoders.decodeDouble(bArr, i24));
                                    iDecodeUnknownField = i24 + 8;
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 1:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 5) {
                                    UnsafeUtil.putFloat(t2, jOffset, ArrayDecoders.decodeFloat(bArr, i24));
                                    iDecodeUnknownField = i24 + 4;
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 2:
                            case 3:
                                bArr2 = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 0) {
                                    iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr2, i24, registers2);
                                    unsafe2.putLong(message, jOffset, registers2.long1);
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr2;
                                    iDecodeUnknownField = iDecodeVarint64;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 4:
                            case 11:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 0) {
                                    iDecodeUnknownField = ArrayDecoders.decodeVarint32(bArr, i24, registers2);
                                    unsafe2.putInt(t2, jOffset, registers2.int1);
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 5:
                            case 14:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 1) {
                                    unsafe2.putLong(message, jOffset, ArrayDecoders.decodeFixed64(bArr, i24));
                                    iDecodeUnknownField = i24 + 8;
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 6:
                            case 13:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 5) {
                                    unsafe2.putInt(t2, jOffset, ArrayDecoders.decodeFixed32(bArr, i24));
                                    iDecodeUnknownField = i24 + 4;
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 7:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 0) {
                                    iDecodeUnknownField = ArrayDecoders.decodeVarint64(bArr, i24, registers2);
                                    UnsafeUtil.putBoolean(t2, jOffset, registers2.long1 != 0);
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 8:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 2) {
                                    if (isEnforceUtf8(i28)) {
                                        iDecodeUnknownField = ArrayDecoders.decodeStringRequireUtf8(bArr, i24, registers2);
                                    } else {
                                        iDecodeUnknownField = ArrayDecoders.decodeString(bArr, i24, registers2);
                                    }
                                    unsafe2.putObject(t2, jOffset, registers2.object1);
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 9:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 2) {
                                    Object objMutableMessageFieldForMerge = messageSchema2.mutableMessageFieldForMerge(t2, i13);
                                    iDecodeUnknownField = ArrayDecoders.mergeMessageField(objMutableMessageFieldForMerge, messageSchema2.getMessageFieldSchema(i13), data, i24, limit, registers);
                                    messageSchema2.storeMessageField(t2, i13, objMutableMessageFieldForMerge);
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 10:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 2) {
                                    iDecodeUnknownField = ArrayDecoders.decodeBytes(bArr, i24, registers2);
                                    unsafe2.putObject(t2, jOffset, registers2.object1);
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 12:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 0) {
                                    iDecodeUnknownField = ArrayDecoders.decodeVarint32(bArr, i24, registers2);
                                    int i34 = registers2.int1;
                                    Internal.EnumVerifier enumFieldVerifier = messageSchema2.getEnumFieldVerifier(i13);
                                    if (!isLegacyEnumIsClosed(i28) || enumFieldVerifier == null || enumFieldVerifier.isInRange(i34)) {
                                        unsafe2.putInt(t2, jOffset, i34);
                                        i21 = i12 | i31;
                                        i18 = endDelimited;
                                        i19 = i13;
                                        bArr3 = bArr;
                                        i20 = i14;
                                        i23 = i10;
                                        i22 = i7;
                                        i17 = limit;
                                    } else {
                                        getMutableUnknownFields(message).storeField(i14, Long.valueOf(i34));
                                        i18 = endDelimited;
                                        i19 = i13;
                                        i21 = i12;
                                        i20 = i14;
                                        i23 = i10;
                                        i22 = i7;
                                        i17 = limit;
                                        bArr3 = bArr;
                                    }
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 15:
                                bArr = data;
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                if (i26 == 0) {
                                    iDecodeUnknownField = ArrayDecoders.decodeVarint32(bArr, i24, registers2);
                                    unsafe2.putInt(t2, jOffset, CodedInputStream.decodeZigZag32(registers2.int1));
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 16:
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                bArr2 = data;
                                if (i26 == 0) {
                                    iDecodeVarint64 = ArrayDecoders.decodeVarint64(bArr2, i24, registers2);
                                    unsafe2.putLong(message, jOffset, CodedInputStream.decodeZigZag64(registers2.long1));
                                    i21 = i12 | i31;
                                    i18 = endDelimited;
                                    i19 = i13;
                                    bArr3 = bArr2;
                                    iDecodeUnknownField = iDecodeVarint64;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            case 17:
                                if (i26 == 3) {
                                    Object objMutableMessageFieldForMerge2 = messageSchema2.mutableMessageFieldForMerge(t2, i27);
                                    i7 = i25;
                                    i14 = i29;
                                    iDecodeUnknownField = ArrayDecoders.mergeGroupField(objMutableMessageFieldForMerge2, messageSchema2.getMessageFieldSchema(i27), data, i24, limit, (i25 << 3) | 4, registers);
                                    messageSchema2.storeMessageField(t2, i27, objMutableMessageFieldForMerge2);
                                    i21 = i12 | i31;
                                    bArr3 = data;
                                    i18 = endDelimited;
                                    i19 = i27;
                                    i20 = i14;
                                    i23 = i10;
                                    i22 = i7;
                                    i17 = limit;
                                } else {
                                    i7 = i25;
                                    i14 = i29;
                                    b = -1;
                                    i13 = i27;
                                    i = endDelimited;
                                    i8 = i24;
                                    i11 = i13;
                                    unsafe = unsafe2;
                                    i9 = i12;
                                    i3 = i14;
                                }
                                break;
                            default:
                                i7 = i25;
                                i13 = i27;
                                i14 = i29;
                                b = -1;
                                i = endDelimited;
                                i8 = i24;
                                i11 = i13;
                                unsafe = unsafe2;
                                i9 = i12;
                                i3 = i14;
                                break;
                        }
                    } else {
                        i7 = i25;
                        int i35 = i23;
                        i9 = i21;
                        if (iType != 27) {
                            i10 = i35;
                            if (iType <= 49) {
                                int i36 = i24;
                                unsafe = unsafe2;
                                i11 = i27;
                                i16 = i29;
                                iDecodeUnknownField = parseRepeatedField(message, data, i24, limit, i29, i7, i26, i27, i28, iType, jOffset, registers);
                                if (iDecodeUnknownField != i36) {
                                    messageSchema2 = this;
                                    t2 = message;
                                    bArr3 = data;
                                    i17 = limit;
                                    i18 = endDelimited;
                                    registers2 = registers;
                                    i20 = i16;
                                    i21 = i9;
                                    i19 = i11;
                                    i23 = i10;
                                    i22 = i7;
                                    unsafe2 = unsafe;
                                } else {
                                    i = endDelimited;
                                    i8 = iDecodeUnknownField;
                                    i3 = i16;
                                }
                            } else {
                                i15 = i24;
                                unsafe = unsafe2;
                                i11 = i27;
                                i16 = i29;
                                if (iType != 50) {
                                    iDecodeUnknownField = parseOneofField(message, data, i15, limit, i16, i7, i26, i28, iType, jOffset, i11, registers);
                                    if (iDecodeUnknownField != i15) {
                                        messageSchema2 = this;
                                        t2 = message;
                                        bArr3 = data;
                                        i17 = limit;
                                        i18 = endDelimited;
                                        registers2 = registers;
                                        i20 = i16;
                                        i21 = i9;
                                        i19 = i11;
                                        i23 = i10;
                                        i22 = i7;
                                        unsafe2 = unsafe;
                                    } else {
                                        i = endDelimited;
                                        i8 = iDecodeUnknownField;
                                        i3 = i16;
                                    }
                                } else if (i26 == 2) {
                                    iDecodeUnknownField = parseMapField(message, data, i15, limit, i11, jOffset, registers);
                                    if (iDecodeUnknownField != i15) {
                                        messageSchema2 = this;
                                        t2 = message;
                                        bArr3 = data;
                                        i17 = limit;
                                        i18 = endDelimited;
                                        registers2 = registers;
                                        i20 = i16;
                                        i21 = i9;
                                        i19 = i11;
                                        i23 = i10;
                                        i22 = i7;
                                        unsafe2 = unsafe;
                                    } else {
                                        i = endDelimited;
                                        i8 = iDecodeUnknownField;
                                        i3 = i16;
                                    }
                                }
                            }
                        } else if (i26 == 2) {
                            Internal.ProtobufList protobufListMutableCopyWithCapacity2 = (Internal.ProtobufList) unsafe2.getObject(t2, jOffset);
                            if (!protobufListMutableCopyWithCapacity2.isModifiable()) {
                                int size = protobufListMutableCopyWithCapacity2.size();
                                protobufListMutableCopyWithCapacity2 = protobufListMutableCopyWithCapacity2.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
                                unsafe2.putObject(t2, jOffset, protobufListMutableCopyWithCapacity2);
                            }
                            iDecodeUnknownField = ArrayDecoders.decodeMessageList(messageSchema2.getMessageFieldSchema(i27), i29, data, i24, limit, protobufListMutableCopyWithCapacity2, registers);
                            i18 = endDelimited;
                            i19 = i27;
                            i20 = i29;
                            i21 = i9;
                            i23 = i35;
                            i22 = i7;
                            bArr3 = data;
                            i17 = limit;
                        } else {
                            i10 = i35;
                            i15 = i24;
                            unsafe = unsafe2;
                            i11 = i27;
                            i16 = i29;
                        }
                        i = endDelimited;
                        i8 = i15;
                        i3 = i16;
                    }
                }
                if (i3 != i || i == 0) {
                    if (this.hasExtensions && registers.extensionRegistry != ExtensionRegistryLite.getEmptyRegistry()) {
                        iDecodeUnknownField = ArrayDecoders.decodeExtensionOrUnknownField(i3, data, i8, limit, message, this.defaultInstance, this.unknownFieldSchema, registers);
                    } else {
                        iDecodeUnknownField = ArrayDecoders.decodeUnknownField(i3, data, i8, limit, getMutableUnknownFields(message), registers);
                    }
                    t2 = message;
                    bArr3 = data;
                    i17 = limit;
                    i20 = i3;
                    messageSchema2 = this;
                    registers2 = registers;
                    i21 = i9;
                    i19 = i11;
                    i23 = i10;
                    i22 = i7;
                    unsafe2 = unsafe;
                    i18 = i;
                } else {
                    i5 = 1048575;
                    messageSchema = this;
                    i2 = i8;
                    i21 = i9;
                    i4 = i10;
                }
            } else {
                int i37 = i23;
                unsafe = unsafe2;
                i = i18;
                messageSchema = messageSchema2;
                i2 = iDecodeUnknownField;
                i3 = i20;
                i4 = i37;
                i5 = 1048575;
            }
        }
        if (i4 != i5) {
            t = message;
            unsafe.putInt(t, i4, i21);
        } else {
            t = message;
        }
        UnknownFieldSetLite unknownFieldSetLite = null;
        for (int i38 = messageSchema.checkInitializedCount; i38 < messageSchema.repeatedFieldOffsetStart; i38++) {
            unknownFieldSetLite = (UnknownFieldSetLite) filterMapUnknownEnumValues(message, messageSchema.intArray[i38], unknownFieldSetLite, messageSchema.unknownFieldSchema, message);
        }
        if (unknownFieldSetLite != null) {
            messageSchema.unknownFieldSchema.setBuilderToMessage(t, unknownFieldSetLite);
        }
        if (i == 0) {
            if (i2 != limit) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        } else if (i2 > limit || i3 != i) {
            throw InvalidProtocolBufferException.parseFailure();
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Object mutableMessageFieldForMerge(T message, int pos) {
        Schema messageFieldSchema = getMessageFieldSchema(pos);
        long jOffset = offset(typeAndOffsetAt(pos));
        if (!isFieldPresent(message, pos)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(message, jOffset);
        if (isMutable(object)) {
            return object;
        }
        Object objNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(objNewInstance, object);
        }
        return objNewInstance;
    }

    private void storeMessageField(T message, int pos, Object field) {
        UNSAFE.putObject(message, offset(typeAndOffsetAt(pos)), field);
        setFieldPresent(message, pos);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Object mutableOneofMessageFieldForMerge(T message, int fieldNumber, int pos) {
        Schema messageFieldSchema = getMessageFieldSchema(pos);
        if (!isOneofPresent(message, fieldNumber, pos)) {
            return messageFieldSchema.newInstance();
        }
        Object object = UNSAFE.getObject(message, offset(typeAndOffsetAt(pos)));
        if (isMutable(object)) {
            return object;
        }
        Object objNewInstance = messageFieldSchema.newInstance();
        if (object != null) {
            messageFieldSchema.mergeFrom(objNewInstance, object);
        }
        return objNewInstance;
    }

    private void storeOneofMessageField(T message, int fieldNumber, int pos, Object field) {
        UNSAFE.putObject(message, offset(typeAndOffsetAt(pos)), field);
        setOneofPresent(message, fieldNumber, pos);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    public void mergeFrom(T message, byte[] data, int position, int limit, ArrayDecoders.Registers registers) throws IOException {
        parseMessage(message, data, position, limit, 0, registers);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0069  */
    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void makeImmutable(T r8) {
        /*
            Method dump skipped, instruction units count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.makeImmutable(java.lang.Object):void");
    }

    private final <K, V> void mergeMap(Object message, int pos, Object mapDefaultEntry, ExtensionRegistryLite extensionRegistry, Reader reader) throws IOException {
        long jOffset = offset(typeAndOffsetAt(pos));
        Object object = UnsafeUtil.getObject(message, jOffset);
        if (object == null) {
            object = this.mapFieldSchema.newMapField(mapDefaultEntry);
            UnsafeUtil.putObject(message, jOffset, object);
        } else if (this.mapFieldSchema.isImmutable(object)) {
            Object objNewMapField = this.mapFieldSchema.newMapField(mapDefaultEntry);
            this.mapFieldSchema.mergeFrom(objNewMapField, object);
            UnsafeUtil.putObject(message, jOffset, objNewMapField);
            object = objNewMapField;
        }
        reader.readMap(this.mapFieldSchema.forMutableMapData(object), this.mapFieldSchema.forMapMetadata(mapDefaultEntry), extensionRegistry);
    }

    private <UT, UB> UB filterMapUnknownEnumValues(Object obj, int i, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema, Object obj2) {
        Internal.EnumVerifier enumFieldVerifier;
        int iNumberAt = numberAt(i);
        Object object = UnsafeUtil.getObject(obj, offset(typeAndOffsetAt(i)));
        return (object == null || (enumFieldVerifier = getEnumFieldVerifier(i)) == null) ? ub : (UB) filterUnknownEnumMap(i, iNumberAt, this.mapFieldSchema.forMutableMapData(object), enumFieldVerifier, ub, unknownFieldSchema, obj2);
    }

    private <K, V, UT, UB> UB filterUnknownEnumMap(int i, int i2, Map<K, V> map, Internal.EnumVerifier enumVerifier, UB ub, UnknownFieldSchema<UT, UB> unknownFieldSchema, Object obj) {
        MapEntryLite.Metadata<?, ?> metadataForMapMetadata = this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            if (!enumVerifier.isInRange(((Integer) next.getValue()).intValue())) {
                if (ub == null) {
                    ub = unknownFieldSchema.getBuilderFromMessage(obj);
                }
                ByteString.CodedBuilder codedBuilderNewCodedBuilder = ByteString.newCodedBuilder(MapEntryLite.computeSerializedSize(metadataForMapMetadata, next.getKey(), next.getValue()));
                try {
                    MapEntryLite.writeTo(codedBuilderNewCodedBuilder.getCodedOutput(), metadataForMapMetadata, next.getKey(), next.getValue());
                    unknownFieldSchema.addLengthDelimited(ub, i2, codedBuilderNewCodedBuilder.build());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0089  */
    @Override // com.google.crypto.tink.shaded.protobuf.Schema
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isInitialized(T r18) {
        /*
            r17 = this;
            r6 = r17
            r7 = r18
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r9 = 0
            r0 = r8
            r1 = r9
            r10 = r1
        Lb:
            int r2 = r6.checkInitializedCount
            r3 = 1
            if (r10 >= r2) goto Lb1
            int[] r2 = r6.intArray
            r11 = r2[r10]
            int r12 = r6.numberAt(r11)
            int r13 = r6.typeAndOffsetAt(r11)
            int[] r2 = r6.buffer
            int r4 = r11 + 2
            r2 = r2[r4]
            r4 = r2 & r8
            int r2 = r2 >>> 20
            int r14 = r3 << r2
            if (r4 == r0) goto L37
            if (r4 == r8) goto L33
            sun.misc.Unsafe r0 = com.google.crypto.tink.shaded.protobuf.MessageSchema.UNSAFE
            long r1 = (long) r4
            int r1 = r0.getInt(r7, r1)
        L33:
            r16 = r1
            r15 = r4
            goto L3a
        L37:
            r15 = r0
            r16 = r1
        L3a:
            boolean r0 = isRequired(r13)
            if (r0 == 0) goto L50
            r0 = r17
            r1 = r18
            r2 = r11
            r3 = r15
            r4 = r16
            r5 = r14
            boolean r0 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L50
            return r9
        L50:
            int r0 = type(r13)
            r1 = 9
            if (r0 == r1) goto L90
            r1 = 17
            if (r0 == r1) goto L90
            r1 = 27
            if (r0 == r1) goto L89
            r1 = 60
            if (r0 == r1) goto L78
            r1 = 68
            if (r0 == r1) goto L78
            r1 = 49
            if (r0 == r1) goto L89
            r1 = 50
            if (r0 == r1) goto L71
            goto Laa
        L71:
            boolean r0 = r6.isMapInitialized(r7, r13, r11)
            if (r0 != 0) goto Laa
            return r9
        L78:
            boolean r0 = r6.isOneofPresent(r7, r12, r11)
            if (r0 == 0) goto Laa
            com.google.crypto.tink.shaded.protobuf.Schema r0 = r6.getMessageFieldSchema(r11)
            boolean r0 = isInitialized(r7, r13, r0)
            if (r0 != 0) goto Laa
            return r9
        L89:
            boolean r0 = r6.isListInitialized(r7, r13, r11)
            if (r0 != 0) goto Laa
            return r9
        L90:
            r0 = r17
            r1 = r18
            r2 = r11
            r3 = r15
            r4 = r16
            r5 = r14
            boolean r0 = r0.isFieldPresent(r1, r2, r3, r4, r5)
            if (r0 == 0) goto Laa
            com.google.crypto.tink.shaded.protobuf.Schema r0 = r6.getMessageFieldSchema(r11)
            boolean r0 = isInitialized(r7, r13, r0)
            if (r0 != 0) goto Laa
            return r9
        Laa:
            int r10 = r10 + 1
            r0 = r15
            r1 = r16
            goto Lb
        Lb1:
            boolean r0 = r6.hasExtensions
            if (r0 == 0) goto Lc2
            com.google.crypto.tink.shaded.protobuf.ExtensionSchema<?> r0 = r6.extensionSchema
            com.google.crypto.tink.shaded.protobuf.FieldSet r0 = r0.getExtensions(r7)
            boolean r0 = r0.isInitialized()
            if (r0 != 0) goto Lc2
            return r9
        Lc2:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.crypto.tink.shaded.protobuf.MessageSchema.isInitialized(java.lang.Object):boolean");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isInitialized(Object message, int typeAndOffset, Schema schema) {
        return schema.isInitialized(UnsafeUtil.getObject(message, offset(typeAndOffset)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N> boolean isListInitialized(Object message, int typeAndOffset, int pos) {
        List list = (List) UnsafeUtil.getObject(message, offset(typeAndOffset));
        if (list.isEmpty()) {
            return true;
        }
        Schema messageFieldSchema = getMessageFieldSchema(pos);
        for (int i = 0; i < list.size(); i++) {
            if (!messageFieldSchema.isInitialized(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [com.google.crypto.tink.shaded.protobuf.Schema] */
    private boolean isMapInitialized(T t, int i, int i2) {
        Map<?, ?> mapForMapData = this.mapFieldSchema.forMapData(UnsafeUtil.getObject(t, offset(i)));
        if (mapForMapData.isEmpty()) {
            return true;
        }
        if (this.mapFieldSchema.forMapMetadata(getMapFieldDefaultEntry(i2)).valueType.getJavaType() != WireFormat.JavaType.MESSAGE) {
            return true;
        }
        ?? SchemaFor = 0;
        for (Object obj : mapForMapData.values()) {
            SchemaFor = SchemaFor;
            if (SchemaFor == 0) {
                SchemaFor = Protobuf.getInstance().schemaFor((Class) obj.getClass());
            }
            if (!SchemaFor.isInitialized(obj)) {
                return false;
            }
        }
        return true;
    }

    private void writeString(int fieldNumber, Object value, Writer writer) throws IOException {
        if (value instanceof String) {
            writer.writeString(fieldNumber, (String) value);
        } else {
            writer.writeBytes(fieldNumber, (ByteString) value);
        }
    }

    private void readString(Object message, int typeAndOffset, Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readStringRequireUtf8());
        } else if (this.lite) {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readString());
        } else {
            UnsafeUtil.putObject(message, offset(typeAndOffset), reader.readBytes());
        }
    }

    private void readStringList(Object message, int typeAndOffset, Reader reader) throws IOException {
        if (isEnforceUtf8(typeAndOffset)) {
            reader.readStringListRequireUtf8(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        } else {
            reader.readStringList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)));
        }
    }

    private <E> void readMessageList(Object message, int typeAndOffset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        reader.readMessageList(this.listFieldSchema.mutableListAt(message, offset(typeAndOffset)), schema, extensionRegistry);
    }

    private <E> void readGroupList(Object message, long offset, Reader reader, Schema<E> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        reader.readGroupList(this.listFieldSchema.mutableListAt(message, offset), schema, extensionRegistry);
    }

    private int numberAt(int pos) {
        return this.buffer[pos];
    }

    private int typeAndOffsetAt(int pos) {
        return this.buffer[pos + 1];
    }

    private int presenceMaskAndOffsetAt(int pos) {
        return this.buffer[pos + 2];
    }

    private static boolean isMutable(Object message) {
        if (message == null) {
            return false;
        }
        if (message instanceof GeneratedMessageLite) {
            return ((GeneratedMessageLite) message).isMutable();
        }
        return true;
    }

    private static void checkMutable(Object message) {
        if (!isMutable(message)) {
            throw new IllegalArgumentException("Mutating immutable message: " + message);
        }
    }

    private static <T> double doubleAt(T message, long offset) {
        return UnsafeUtil.getDouble(message, offset);
    }

    private static <T> float floatAt(T message, long offset) {
        return UnsafeUtil.getFloat(message, offset);
    }

    private static <T> int intAt(T message, long offset) {
        return UnsafeUtil.getInt(message, offset);
    }

    private static <T> long longAt(T message, long offset) {
        return UnsafeUtil.getLong(message, offset);
    }

    private static <T> boolean booleanAt(T message, long offset) {
        return UnsafeUtil.getBoolean(message, offset);
    }

    private static <T> double oneofDoubleAt(T message, long offset) {
        return ((Double) UnsafeUtil.getObject(message, offset)).doubleValue();
    }

    private static <T> float oneofFloatAt(T message, long offset) {
        return ((Float) UnsafeUtil.getObject(message, offset)).floatValue();
    }

    private static <T> int oneofIntAt(T message, long offset) {
        return ((Integer) UnsafeUtil.getObject(message, offset)).intValue();
    }

    private static <T> long oneofLongAt(T message, long offset) {
        return ((Long) UnsafeUtil.getObject(message, offset)).longValue();
    }

    private static <T> boolean oneofBooleanAt(T message, long offset) {
        return ((Boolean) UnsafeUtil.getObject(message, offset)).booleanValue();
    }

    private boolean arePresentForEquals(T message, T other, int pos) {
        return isFieldPresent(message, pos) == isFieldPresent(other, pos);
    }

    private boolean isFieldPresent(T message, int pos, int presenceFieldOffset, int presenceField, int presenceMask) {
        if (presenceFieldOffset == 1048575) {
            return isFieldPresent(message, pos);
        }
        return (presenceField & presenceMask) != 0;
    }

    private boolean isFieldPresent(T message, int pos) {
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(pos);
        long j = 1048575 & iPresenceMaskAndOffsetAt;
        if (j != 1048575) {
            return (UnsafeUtil.getInt(message, j) & (1 << (iPresenceMaskAndOffsetAt >>> 20))) != 0;
        }
        int iTypeAndOffsetAt = typeAndOffsetAt(pos);
        long jOffset = offset(iTypeAndOffsetAt);
        switch (type(iTypeAndOffsetAt)) {
            case 0:
                return Double.doubleToRawLongBits(UnsafeUtil.getDouble(message, jOffset)) != 0;
            case 1:
                return Float.floatToRawIntBits(UnsafeUtil.getFloat(message, jOffset)) != 0;
            case 2:
                return UnsafeUtil.getLong(message, jOffset) != 0;
            case 3:
                return UnsafeUtil.getLong(message, jOffset) != 0;
            case 4:
                return UnsafeUtil.getInt(message, jOffset) != 0;
            case 5:
                return UnsafeUtil.getLong(message, jOffset) != 0;
            case 6:
                return UnsafeUtil.getInt(message, jOffset) != 0;
            case 7:
                return UnsafeUtil.getBoolean(message, jOffset);
            case 8:
                Object object = UnsafeUtil.getObject(message, jOffset);
                if (object instanceof String) {
                    return !((String) object).isEmpty();
                }
                if (object instanceof ByteString) {
                    return !ByteString.EMPTY.equals(object);
                }
                throw new IllegalArgumentException();
            case 9:
                return UnsafeUtil.getObject(message, jOffset) != null;
            case 10:
                return !ByteString.EMPTY.equals(UnsafeUtil.getObject(message, jOffset));
            case 11:
                return UnsafeUtil.getInt(message, jOffset) != 0;
            case 12:
                return UnsafeUtil.getInt(message, jOffset) != 0;
            case 13:
                return UnsafeUtil.getInt(message, jOffset) != 0;
            case 14:
                return UnsafeUtil.getLong(message, jOffset) != 0;
            case 15:
                return UnsafeUtil.getInt(message, jOffset) != 0;
            case 16:
                return UnsafeUtil.getLong(message, jOffset) != 0;
            case 17:
                return UnsafeUtil.getObject(message, jOffset) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void setFieldPresent(T message, int pos) {
        int iPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(pos);
        long j = 1048575 & iPresenceMaskAndOffsetAt;
        if (j == 1048575) {
            return;
        }
        UnsafeUtil.putInt(message, j, (1 << (iPresenceMaskAndOffsetAt >>> 20)) | UnsafeUtil.getInt(message, j));
    }

    private boolean isOneofPresent(T message, int fieldNumber, int pos) {
        return UnsafeUtil.getInt(message, (long) (presenceMaskAndOffsetAt(pos) & 1048575)) == fieldNumber;
    }

    private boolean isOneofCaseEqual(T message, T other, int pos) {
        long jPresenceMaskAndOffsetAt = presenceMaskAndOffsetAt(pos) & 1048575;
        return UnsafeUtil.getInt(message, jPresenceMaskAndOffsetAt) == UnsafeUtil.getInt(other, jPresenceMaskAndOffsetAt);
    }

    private void setOneofPresent(T message, int fieldNumber, int pos) {
        UnsafeUtil.putInt(message, presenceMaskAndOffsetAt(pos) & 1048575, fieldNumber);
    }

    private int positionForFieldNumber(final int number) {
        if (number < this.minFieldNumber || number > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(number, 0);
    }

    private int positionForFieldNumber(final int number, final int min) {
        if (number < this.minFieldNumber || number > this.maxFieldNumber) {
            return -1;
        }
        return slowPositionForFieldNumber(number, min);
    }

    private int slowPositionForFieldNumber(final int number, int min) {
        int length = (this.buffer.length / 3) - 1;
        while (min <= length) {
            int i = (length + min) >>> 1;
            int i2 = i * 3;
            int iNumberAt = numberAt(i2);
            if (number == iNumberAt) {
                return i2;
            }
            if (number < iNumberAt) {
                length = i - 1;
            } else {
                min = i + 1;
            }
        }
        return -1;
    }

    int getSchemaSize() {
        return this.buffer.length * 3;
    }
}
