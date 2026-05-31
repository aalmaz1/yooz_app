package cn.yoozworld.watch.utils.notifi;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class CustomNotification<T> implements Parcelable {
    public static final Parcelable.Creator<CustomNotification> CREATOR = new Parcelable.Creator<CustomNotification>() { // from class: cn.yoozworld.watch.utils.notifi.CustomNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomNotification createFromParcel(Parcel parcel) {
            return new CustomNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomNotification[] newArray(int i) {
            return new CustomNotification[i];
        }
    };
    private static final int TYPE_UNKNOWN = -1;
    T mData;
    boolean mIsCollapsible;
    boolean mIsOverride;
    boolean mIsPin;
    int mPriority;
    int mTimeout;
    int mType;
    NotificationView<T> mView;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CustomNotification() {
        this.mTimeout = 0;
        this.mPriority = 0;
        this.mIsOverride = true;
        this.mType = -1;
    }

    protected CustomNotification(Parcel parcel) {
        this.mTimeout = 0;
        this.mPriority = 0;
        this.mIsOverride = true;
        this.mType = -1;
        this.mTimeout = parcel.readInt();
        this.mPriority = parcel.readInt();
        this.mIsPin = parcel.readByte() != 0;
        this.mIsCollapsible = parcel.readByte() != 0;
        this.mIsOverride = parcel.readByte() != 0;
        this.mType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTimeout);
        parcel.writeInt(this.mPriority);
        parcel.writeByte(this.mIsPin ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mIsCollapsible ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mIsOverride ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mType);
    }

    public CustomNotification<T> setTimeOut(int i) {
        this.mTimeout = i;
        return this;
    }

    public CustomNotification<T> setPin(boolean z) {
        this.mIsPin = z;
        return this;
    }

    public CustomNotification<T> setPriority(int i) {
        this.mPriority = i;
        return this;
    }

    public CustomNotification<T> setType(int i) {
        this.mType = i;
        return this;
    }

    public CustomNotification<T> setCollapsible(boolean z) {
        this.mIsCollapsible = z;
        return this;
    }

    public CustomNotification<T> setOverride(boolean z) {
        this.mIsOverride = z;
        return this;
    }

    public CustomNotification<T> setNotificationView(NotificationView<T> notificationView) {
        this.mView = notificationView;
        return this;
    }

    public CustomNotification<T> setData(T t) {
        this.mData = t;
        return this;
    }

    public CustomNotification<T> setData(T t, boolean z) {
        this.mData = t;
        if (z) {
            getNotificationView().bindNotification(this);
        }
        return this;
    }

    public NotificationView<T> getNotificationView() {
        return this.mView;
    }

    public int getTimeout() {
        return this.mTimeout;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public boolean isPin() {
        return this.mIsPin;
    }

    public boolean isCollapsible() {
        return this.mIsCollapsible;
    }

    public boolean isOverride() {
        return this.mIsOverride;
    }

    public Activity getActivity() {
        NotificationView<T> notificationView = this.mView;
        if (notificationView == null) {
            return null;
        }
        return notificationView.getActivity();
    }

    public T getData() {
        return this.mData;
    }

    public void show() {
        checkArgument();
        NotificationView<T> notificationView = this.mView;
        if (notificationView != null) {
            notificationView.bindNotification(this);
        }
        NotificationManager.getInstance().notify(this);
    }

    private void checkArgument() {
        if (this.mType == -1) {
            throw new IllegalArgumentException("type should be set");
        }
    }

    public static void cancel(int i) {
        NotificationManager.getInstance().cancel(i);
    }
}
