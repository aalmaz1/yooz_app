package com.google.android.gms.common.data;

import io.reactivex.internal.operators.flowable.FlowableReplay;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class FreezableUtils {
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> arrayList) {
        FlowableReplay.UnboundedReplayBuffer unboundedReplayBuffer = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            unboundedReplayBuffer.add(arrayList.get(i).freeze());
        }
        return unboundedReplayBuffer;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> iterable) {
        FlowableReplay.UnboundedReplayBuffer unboundedReplayBuffer = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            unboundedReplayBuffer.add(it.next().freeze());
        }
        return unboundedReplayBuffer;
    }

    public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] eArr) {
        FlowableReplay.UnboundedReplayBuffer unboundedReplayBuffer = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e : eArr) {
            unboundedReplayBuffer.add(e.freeze());
        }
        return unboundedReplayBuffer;
    }
}
