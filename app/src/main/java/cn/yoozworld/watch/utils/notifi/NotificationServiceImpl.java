package cn.yoozworld.watch.utils.notifi;

import android.animation.Animator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.core.os.EnvironmentCompat;

/* JADX INFO: loaded from: classes.dex */
public class NotificationServiceImpl implements INotificationService<CustomNotification> {
    public boolean mIsShowing;
    private WindowManager.LayoutParams mLayoutParams;
    private NotifyContainerView mNotificationContainerView;
    private final NotificationManager mNotificationManager;
    private WindowManager mWindowManager;

    public NotificationServiceImpl(NotificationManager notificationManager) {
        this.mNotificationManager = notificationManager;
    }

    @Override // cn.yoozworld.watch.utils.notifi.INotificationService
    public void show(final CustomNotification customNotification) {
        if (customNotification != null) {
            try {
                if (customNotification.getActivity() != null) {
                    initNotificationView(customNotification);
                    NotifyContainerView notifyContainerView = this.mNotificationContainerView;
                    if (notifyContainerView != null && notifyContainerView.getParent() == null && this.mWindowManager != null && this.mLayoutParams != null) {
                        if (NotificationUtils.isActivityNotAlive(this.mNotificationContainerView.getActivity())) {
                            LoggerUtils.log("handleShow returned: activity is finishing or destroyed!");
                            return;
                        }
                        LoggerUtils.log("handleShow before addView: mLayoutParams.token" + this.mNotificationContainerView.getWindowToken());
                        this.mLayoutParams.token = this.mNotificationContainerView.getWindowToken();
                        this.mWindowManager.addView(this.mNotificationContainerView, this.mLayoutParams);
                        LoggerUtils.log("handleShow after addView");
                        changeIsShowing(true);
                        this.mNotificationContainerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: cn.yoozworld.watch.utils.notifi.NotificationServiceImpl.1
                            @Override // android.view.View.OnLayoutChangeListener
                            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                                if (NotificationServiceImpl.this.mNotificationContainerView == null) {
                                    LoggerUtils.log("handleShow animation: mNotificationContainerView == null");
                                    return;
                                }
                                if (NotificationUtils.isActivityNotAlive(NotificationServiceImpl.this.mNotificationContainerView.getActivity())) {
                                    LoggerUtils.log("handleShow animation: mNotificationContainerView.getActivity() is not alive : " + NotificationServiceImpl.this.mNotificationContainerView.getActivity());
                                    return;
                                }
                                if (customNotification == null) {
                                    LoggerUtils.log("handleShow animation: mNotification == null");
                                    return;
                                }
                                NotificationServiceImpl notificationServiceImpl = NotificationServiceImpl.this;
                                notificationServiceImpl.resetAnimation(notificationServiceImpl.mNotificationContainerView);
                                NotificationServiceImpl.this.mNotificationContainerView.animate().translationY(0.0f).setDuration(200L).start();
                                NotificationServiceImpl.this.mNotificationManager.startTimeout(customNotification.mType, customNotification.getTimeout());
                                NotificationServiceImpl.this.mNotificationContainerView.removeOnLayoutChangeListener(this);
                            }
                        });
                        return;
                    }
                    String str = EnvironmentCompat.MEDIA_UNKNOWN;
                    NotifyContainerView notifyContainerView2 = this.mNotificationContainerView;
                    if (notifyContainerView2 == null) {
                        str = "mNotificationContainerView == null";
                    } else if (notifyContainerView2.getParent() != null) {
                        str = "mNotificationContainerView.getParent() != null";
                    } else if (this.mWindowManager == null) {
                        str = "mWindowManager == null";
                    } else if (this.mLayoutParams == null) {
                        str = "mLayoutParams == null";
                    }
                    LoggerUtils.log("handleShow returned: ".concat(str));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        LoggerUtils.log("handleShow returned: mNotification == null || mNotification.getActivity() == null");
    }

    @Override // cn.yoozworld.watch.utils.notifi.INotificationService
    public void cancel(CustomNotification customNotification, final Animator.AnimatorListener animatorListener) {
        NotifyContainerView notifyContainerView = this.mNotificationContainerView;
        if (notifyContainerView == null) {
            return;
        }
        resetAnimation(notifyContainerView);
        this.mNotificationContainerView.animate().translationY(-this.mNotificationContainerView.getHeight()).setDuration(200L);
        this.mNotificationContainerView.animate().setListener(new Animator.AnimatorListener() { // from class: cn.yoozworld.watch.utils.notifi.NotificationServiceImpl.2
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                Animator.AnimatorListener animatorListener2 = animatorListener;
                if (animatorListener2 != null) {
                    animatorListener2.onAnimationStart(animator);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (NotificationServiceImpl.this.mWindowManager != null && NotificationServiceImpl.this.mNotificationContainerView != null && NotificationServiceImpl.this.mNotificationContainerView.getParent() != null) {
                    NotificationServiceImpl.this.mWindowManager.removeViewImmediate(NotificationServiceImpl.this.mNotificationContainerView);
                }
                Animator.AnimatorListener animatorListener2 = animatorListener;
                if (animatorListener2 != null) {
                    animatorListener2.onAnimationEnd(animator);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                Animator.AnimatorListener animatorListener2 = animatorListener;
                if (animatorListener2 != null) {
                    animatorListener2.onAnimationCancel(animator);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                Animator.AnimatorListener animatorListener2 = animatorListener;
                if (animatorListener2 != null) {
                    animatorListener2.onAnimationRepeat(animator);
                }
            }
        });
        this.mNotificationContainerView.animate().start();
        changeIsShowing(false);
    }

    @Override // cn.yoozworld.watch.utils.notifi.INotificationService
    public boolean isShowing() {
        return this.mIsShowing;
    }

    @Override // cn.yoozworld.watch.utils.notifi.INotificationService
    public void changeIsShowing(boolean z) {
        this.mIsShowing = z;
    }

    private void initNotificationView(CustomNotification customNotification) {
        Activity activity = customNotification.getActivity();
        if (customNotification.getNotificationView().getView() == null || customNotification.getNotificationView().getView().getParent() != null || activity == null) {
            return;
        }
        this.mWindowManager = (WindowManager) activity.getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mLayoutParams = layoutParams;
        layoutParams.height = -2;
        this.mLayoutParams.width = -1;
        this.mLayoutParams.format = -3;
        this.mLayoutParams.type = 1000;
        this.mLayoutParams.flags = 136;
        this.mLayoutParams.gravity = 48;
        this.mLayoutParams.x = 0;
        this.mLayoutParams.y = NotificationUtils.getNotificationLocationY(activity);
        this.mNotificationContainerView = new NotifyContainerView(activity);
        this.mNotificationContainerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
        this.mNotificationContainerView.setOnDismissListener(new OnDismissListener() { // from class: cn.yoozworld.watch.utils.notifi.NotificationServiceImpl.3
            @Override // cn.yoozworld.watch.utils.notifi.OnDismissListener
            public void onDismiss() {
                NotificationServiceImpl.this.mNotificationManager.hideNotification();
            }
        });
        this.mNotificationContainerView.addView(customNotification.getNotificationView().getView());
        this.mNotificationContainerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: cn.yoozworld.watch.utils.notifi.NotificationServiceImpl.4
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                NotificationServiceImpl.this.mNotificationContainerView.setTranslationY(-NotificationServiceImpl.this.mNotificationContainerView.getHeight());
                NotificationServiceImpl.this.mNotificationContainerView.removeOnLayoutChangeListener(this);
            }
        });
        this.mNotificationContainerView.setCollapsible(customNotification.mIsCollapsible);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetAnimation(View view) {
        if (view == null) {
            return;
        }
        view.animate().cancel();
        view.animate().setListener(null);
    }
}
