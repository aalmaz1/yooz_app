package com.google.crypto.tink.shaded.protobuf;

import com.google.crypto.tink.shaded.protobuf.Internal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
@CheckReturnValue
abstract class ListFieldSchema {
    private static final ListFieldSchema FULL_INSTANCE;
    private static final ListFieldSchema LITE_INSTANCE;

    abstract void makeImmutableListAt(Object msg, long offset);

    abstract <L> void mergeListsAt(Object msg, Object otherMsg, long offset);

    abstract <L> List<L> mutableListAt(Object msg, long offset);

    private ListFieldSchema() {
    }

    static {
        FULL_INSTANCE = new ListFieldSchemaFull();
        LITE_INSTANCE = new ListFieldSchemaLite();
    }

    static ListFieldSchema full() {
        return FULL_INSTANCE;
    }

    static ListFieldSchema lite() {
        return LITE_INSTANCE;
    }

    private static final class ListFieldSchemaFull extends ListFieldSchema {
        private static final Class<?> UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();

        private ListFieldSchemaFull() {
            super();
        }

        @Override // com.google.crypto.tink.shaded.protobuf.ListFieldSchema
        <L> List<L> mutableListAt(Object message, long offset) {
            return mutableListAt(message, offset, 10);
        }

        @Override // com.google.crypto.tink.shaded.protobuf.ListFieldSchema
        void makeImmutableListAt(Object message, long offset) {
            Object objUnmodifiableList;
            List list = (List) UnsafeUtil.getObject(message, offset);
            if (list instanceof LazyStringList) {
                objUnmodifiableList = ((LazyStringList) list).getUnmodifiableView();
            } else {
                if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                    return;
                }
                if ((list instanceof PrimitiveNonBoxingCollection) && (list instanceof Internal.ProtobufList)) {
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) list;
                    if (protobufList.isModifiable()) {
                        protobufList.makeImmutable();
                        return;
                    }
                    return;
                }
                objUnmodifiableList = Collections.unmodifiableList(list);
            }
            UnsafeUtil.putObject(message, offset, objUnmodifiableList);
        }

        private static <L> List<L> mutableListAt(Object obj, long j, int i) {
            Object obj2;
            List<L> arrayList;
            List<L> list = getList(obj, j);
            if (list.isEmpty()) {
                if (list instanceof LazyStringList) {
                    arrayList = new LazyStringArrayList(i);
                } else if ((list instanceof PrimitiveNonBoxingCollection) && (list instanceof Internal.ProtobufList)) {
                    arrayList = ((Internal.ProtobufList) list).mutableCopyWithCapacity2(i);
                } else {
                    arrayList = new ArrayList<>(i);
                }
                UnsafeUtil.putObject(obj, j, arrayList);
                return arrayList;
            }
            if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                ArrayList arrayList2 = new ArrayList(list.size() + i);
                arrayList2.addAll(list);
                UnsafeUtil.putObject(obj, j, arrayList2);
                obj2 = arrayList2;
            } else if (list instanceof UnmodifiableLazyStringList) {
                LazyStringArrayList lazyStringArrayList = new LazyStringArrayList(list.size() + i);
                lazyStringArrayList.addAll((UnmodifiableLazyStringList) list);
                UnsafeUtil.putObject(obj, j, lazyStringArrayList);
                obj2 = lazyStringArrayList;
            } else {
                if (!(list instanceof PrimitiveNonBoxingCollection) || !(list instanceof Internal.ProtobufList)) {
                    return list;
                }
                Internal.ProtobufList protobufList = (Internal.ProtobufList) list;
                if (protobufList.isModifiable()) {
                    return list;
                }
                Internal.ProtobufList protobufListMutableCopyWithCapacity2 = protobufList.mutableCopyWithCapacity2(list.size() + i);
                UnsafeUtil.putObject(obj, j, protobufListMutableCopyWithCapacity2);
                return protobufListMutableCopyWithCapacity2;
            }
            return (List<L>) obj2;
        }

        @Override // com.google.crypto.tink.shaded.protobuf.ListFieldSchema
        <E> void mergeListsAt(Object msg, Object otherMsg, long offset) {
            List list = getList(otherMsg, offset);
            List listMutableListAt = mutableListAt(msg, offset, list.size());
            int size = listMutableListAt.size();
            int size2 = list.size();
            if (size > 0 && size2 > 0) {
                listMutableListAt.addAll(list);
            }
            if (size > 0) {
                list = listMutableListAt;
            }
            UnsafeUtil.putObject(msg, offset, list);
        }

        static <E> List<E> getList(Object message, long offset) {
            return (List) UnsafeUtil.getObject(message, offset);
        }
    }

    private static final class ListFieldSchemaLite extends ListFieldSchema {
        private ListFieldSchemaLite() {
            super();
        }

        @Override // com.google.crypto.tink.shaded.protobuf.ListFieldSchema
        <L> List<L> mutableListAt(Object message, long offset) {
            Internal.ProtobufList protobufList = getProtobufList(message, offset);
            if (protobufList.isModifiable()) {
                return protobufList;
            }
            int size = protobufList.size();
            Internal.ProtobufList protobufListMutableCopyWithCapacity2 = protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
            UnsafeUtil.putObject(message, offset, protobufListMutableCopyWithCapacity2);
            return protobufListMutableCopyWithCapacity2;
        }

        @Override // com.google.crypto.tink.shaded.protobuf.ListFieldSchema
        void makeImmutableListAt(Object message, long offset) {
            getProtobufList(message, offset).makeImmutable();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1 */
        /* JADX WARN: Type inference failed for: r0v2, types: [com.google.crypto.tink.shaded.protobuf.Internal$ProtobufList] */
        /* JADX WARN: Type inference failed for: r0v4 */
        /* JADX WARN: Type inference failed for: r0v5 */
        /* JADX WARN: Type inference failed for: r0v6 */
        /* JADX WARN: Type inference failed for: r0v7 */
        /* JADX WARN: Type inference failed for: r0v8 */
        /* JADX WARN: Type inference failed for: r6v1, types: [com.google.crypto.tink.shaded.protobuf.Internal$ProtobufList, java.util.Collection] */
        /* JADX WARN: Type inference failed for: r6v2, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r6v3 */
        @Override // com.google.crypto.tink.shaded.protobuf.ListFieldSchema
        <E> void mergeListsAt(Object obj, Object obj2, long j) {
            Internal.ProtobufList protobufList = getProtobufList(obj, j);
            ?? protobufList2 = getProtobufList(obj2, j);
            int size = protobufList.size();
            int size2 = protobufList2.size();
            ?? r0 = protobufList;
            r0 = protobufList;
            if (size > 0 && size2 > 0) {
                boolean zIsModifiable = protobufList.isModifiable();
                ?? MutableCopyWithCapacity2 = protobufList;
                if (!zIsModifiable) {
                    MutableCopyWithCapacity2 = protobufList.mutableCopyWithCapacity2(size2 + size);
                }
                MutableCopyWithCapacity2.addAll(protobufList2);
                r0 = MutableCopyWithCapacity2;
            }
            if (size > 0) {
                protobufList2 = r0;
            }
            UnsafeUtil.putObject(obj, j, (Object) protobufList2);
        }

        static <E> Internal.ProtobufList<E> getProtobufList(Object message, long offset) {
            return (Internal.ProtobufList) UnsafeUtil.getObject(message, offset);
        }
    }
}
