package cn.yoozworld.watch.utils.notifi;

import android.animation.Animator;

/* JADX INFO: loaded from: classes.dex */
public class NotificationNode {
    public static final int ANIM_DURATION = 200;
    static final int EQUALS = 0;
    static final int ERROR = -1;
    static final int GREATER = 1;
    static final int SMALLER = 2;
    protected CustomNotification mNotification;
    private final INotificationService<CustomNotification> notificationService;

    public NotificationNode(CustomNotification customNotification, NotificationManager notificationManager) {
        this.notificationService = new NotificationServiceImpl(notificationManager);
        this.mNotification = customNotification;
    }

    int getPriority() {
        CustomNotification customNotification = this.mNotification;
        if (customNotification == null) {
            return -1;
        }
        return customNotification.mPriority;
    }

    boolean isShowing() {
        return this.notificationService.isShowing();
    }

    void changeIsShowing(boolean z) {
        this.notificationService.changeIsShowing(z);
    }

    CustomNotification getNotification() {
        return this.mNotification;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof NotificationNode)) {
            return false;
        }
        CustomNotification customNotification = ((NotificationNode) obj).mNotification;
        return !(customNotification == null || getNotification() == null || customNotification.mType != getNotification().mType) || super.equals(obj);
    }

    public int hashCode() {
        CustomNotification customNotification = this.mNotification;
        if (customNotification == null) {
            return -1;
        }
        return customNotification.mType;
    }

    int compareTo(NotificationNode notificationNode) {
        if (getNotification() == null || notificationNode.getNotification() == null) {
            return -1;
        }
        int i = getNotification().mPriority - notificationNode.getNotification().mPriority;
        if (i > 0) {
            return 1;
        }
        return i < 0 ? 2 : 0;
    }

    protected void handleShow() {
        this.notificationService.show(this.mNotification);
    }

    protected void handleHide(Animator.AnimatorListener animatorListener) {
        this.notificationService.cancel(this.mNotification, animatorListener);
    }
}
