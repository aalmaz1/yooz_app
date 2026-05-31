package org.apache.commons.lang3.concurrent;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LazyInitializer<T> implements ConcurrentInitializer<T> {
    private static final Object NO_INIT = new Object();
    private volatile T object = (T) NO_INIT;

    protected abstract T initialize() throws ConcurrentException;

    @Override // org.apache.commons.lang3.concurrent.ConcurrentInitializer
    public T get() throws ConcurrentException {
        T tInitialize = this.object;
        Object obj = NO_INIT;
        if (tInitialize == obj) {
            synchronized (this) {
                tInitialize = this.object;
                if (tInitialize == obj) {
                    tInitialize = initialize();
                    this.object = tInitialize;
                }
            }
        }
        return tInitialize;
    }
}
