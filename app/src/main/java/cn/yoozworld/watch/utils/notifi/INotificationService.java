package cn.yoozworld.watch.utils.notifi;

import android.animation.Animator;

/* JADX INFO: loaded from: classes.dex */
public interface INotificationService<T> {
    void cancel(T t, Animator.AnimatorListener animatorListener);

    void changeIsShowing(boolean z);

    boolean isShowing();

    void show(T t);
}
