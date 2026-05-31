package cn.yoozworld.watch.utils.notifi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Message;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
public class NotificationManager {
    public static final String BUNDLE_NOTIFICATION = "notification";
    public static final String BUNDLE_TYPE = "type";
    public static final int MSG_HIDE = 2;
    public static final int MSG_SHOW = 1;
    private static volatile NotificationManager sInstance;
    private final LinkedList<NotificationNode> mNodeLinkedList = new LinkedList<>();
    private final MyHandler mHandler = new MyHandler(this);

    private NotificationManager() {
    }

    public static NotificationManager getInstance() {
        if (sInstance == null) {
            synchronized (NotificationManager.class) {
                if (sInstance == null) {
                    sInstance = new NotificationManager();
                }
            }
        }
        return sInstance;
    }

    void notify(CustomNotification customNotification) {
        sendMessageShow(customNotification);
    }

    public void cancel(int i) {
        sendMessageHide(i);
    }

    protected void hideNotification() {
        if (this.mNodeLinkedList.isEmpty()) {
            return;
        }
        hideNotification(this.mNodeLinkedList.getFirst());
    }

    private void hideNotification(NotificationNode notificationNode) {
        if (this.mNodeLinkedList.isEmpty() || notificationNode == null) {
            return;
        }
        boolean z = notificationNode == this.mNodeLinkedList.getFirst();
        removeNotificationNode(notificationNode);
        notificationNode.changeIsShowing(false);
        if (z) {
            notificationNode.handleHide(new AnimatorListenerAdapter() { // from class: cn.yoozworld.watch.utils.notifi.NotificationManager.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (NotificationManager.this.mNodeLinkedList.isEmpty()) {
                        return;
                    }
                    NotificationManager notificationManager = NotificationManager.this;
                    notificationManager.showNotification(((NotificationNode) notificationManager.mNodeLinkedList.getFirst()).mNotification);
                }
            });
        }
    }

    protected void hideNotification(int i) {
        NotificationNode notificationNodeFindNodeByType = findNodeByType(i);
        if (notificationNodeFindNodeByType != null) {
            hideNotification(notificationNodeFindNodeByType);
        }
    }

    private NotificationNode findNodeByType(int i) {
        for (NotificationNode notificationNode : this.mNodeLinkedList) {
            if (notificationNode.mNotification != null && i == notificationNode.mNotification.mType) {
                return notificationNode;
            }
        }
        return null;
    }

    protected void startTimeout(int i, int i2) {
        MyHandler myHandler = this.mHandler;
        if (myHandler == null || i2 == 0) {
            return;
        }
        int i3 = i + 2;
        myHandler.removeMessages(i3);
        this.mHandler.sendEmptyMessageDelayed(i3, i2);
    }

    protected void showNotification(CustomNotification customNotification) {
        try {
            if (this.mNodeLinkedList.isEmpty()) {
                insertNotificationLocked(customNotification);
                this.mNodeLinkedList.getFirst().handleShow();
            } else if (customNotification.mType == this.mNodeLinkedList.getFirst().mNotification.mType) {
                insertNotificationLocked(customNotification);
                NotificationNode first = this.mNodeLinkedList.getFirst();
                if (!first.isShowing()) {
                    first.handleShow();
                }
            } else if (isHigherPriority(customNotification)) {
                final NotificationNode first2 = this.mNodeLinkedList.getFirst();
                insertNotificationLocked(customNotification);
                this.mHandler.post(new Runnable() { // from class: cn.yoozworld.watch.utils.notifi.NotificationManager.2
                    @Override // java.lang.Runnable
                    public void run() {
                        first2.handleHide(new AnimatorListenerAdapter() { // from class: cn.yoozworld.watch.utils.notifi.NotificationManager.2.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                if (NotificationManager.this.mNodeLinkedList.isEmpty()) {
                                    return;
                                }
                                ((NotificationNode) NotificationManager.this.mNodeLinkedList.getFirst()).handleShow();
                            }
                        });
                    }
                });
            } else {
                insertNotificationLocked(customNotification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isHigherPriority(CustomNotification customNotification) {
        return this.mNodeLinkedList.isEmpty() || this.mNodeLinkedList.getFirst().mNotification == null || this.mNodeLinkedList.getFirst().getPriority() < customNotification.getPriority();
    }

    private void insertNotificationLocked(CustomNotification customNotification) {
        NotificationNode notificationNode = new NotificationNode(customNotification, this);
        if (this.mNodeLinkedList.isEmpty()) {
            this.mNodeLinkedList.offerFirst(notificationNode);
            return;
        }
        if (this.mNodeLinkedList.contains(notificationNode)) {
            LinkedList<NotificationNode> linkedList = this.mNodeLinkedList;
            linkedList.get(linkedList.indexOf(notificationNode)).getNotification().setData(customNotification.getData(), true);
            return;
        }
        if (isHigherPriority(customNotification)) {
            this.mNodeLinkedList.offerFirst(notificationNode);
            return;
        }
        Iterator<NotificationNode> it = this.mNodeLinkedList.iterator();
        NotificationNode next = it.next();
        while (next != null) {
            if (notificationNode.compareTo(next) == 1) {
                break;
            } else if (!it.hasNext()) {
                break;
            } else {
                next = it.next();
            }
        }
        next = null;
        if (next == null) {
            this.mNodeLinkedList.offerLast(notificationNode);
        } else {
            LinkedList<NotificationNode> linkedList2 = this.mNodeLinkedList;
            linkedList2.add(linkedList2.indexOf(next), notificationNode);
        }
    }

    private void removeNotificationNode(NotificationNode notificationNode) {
        if (notificationNode == null) {
            return;
        }
        this.mNodeLinkedList.remove(notificationNode);
    }

    private void sendMessageShow(CustomNotification customNotification) {
        MyHandler myHandler = this.mHandler;
        if (myHandler == null) {
            return;
        }
        Message messageObtainMessage = myHandler.obtainMessage(1);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_NOTIFICATION, customNotification);
        messageObtainMessage.setData(bundle);
        messageObtainMessage.sendToTarget();
    }

    private void sendMessageHide(int i) {
        MyHandler myHandler = this.mHandler;
        if (myHandler == null) {
            return;
        }
        Message messageObtainMessage = myHandler.obtainMessage(i + 2);
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, i);
        messageObtainMessage.setData(bundle);
        messageObtainMessage.sendToTarget();
    }
}
