package io.reactivex.parallel;

/* JADX INFO: loaded from: classes3.dex */
public interface ParallelTransformer<Upstream, Downstream> {
    ParallelFlowable<Downstream> apply(ParallelFlowable<Upstream> parallelFlowable);
}
