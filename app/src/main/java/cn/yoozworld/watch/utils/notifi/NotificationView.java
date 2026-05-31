package cn.yoozworld.watch.utils.notifi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes.dex */
public abstract class NotificationView<T> {
    private final Activity mActivity;
    private CustomNotification<T> mNotification;
    private View mView;

    private void setView(View view) {
    }

    protected boolean onClick(View view, int i) {
        return false;
    }

    public abstract int[] provideClickableViewArray();

    public abstract int provideLayoutResourceId();

    public NotificationView(Activity activity) {
        this.mActivity = activity;
        initView();
    }

    private void initView() {
        int iProvideLayoutResourceId = provideLayoutResourceId();
        if (iProvideLayoutResourceId == 0) {
            throw new IllegalArgumentException("layout res is illegal!");
        }
        View viewInflate = LayoutInflater.from(this.mActivity).inflate(iProvideLayoutResourceId, (ViewGroup) null);
        this.mView = viewInflate;
        setView(viewInflate);
        setClickableViewListener(this.mView);
    }

    public View getView() {
        return this.mView;
    }

    public void bindNotification(CustomNotification<T> customNotification) {
        this.mNotification = customNotification;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    /* JADX WARN: Incorrect return type in method signature: <T:Landroid/view/View;>(I)TT; */
    protected View findViewById(int i) {
        View view = this.mView;
        if (view == null) {
            throw new NullPointerException("View is not created!");
        }
        return view.findViewById(i);
    }

    private void setClickableViewListener(View view) {
        if (view == null) {
            return;
        }
        for (int i : provideClickableViewArray()) {
            if (i != 0) {
                setClickListener(view.findViewById(i));
            }
        }
    }

    private void setClickListener(View view) {
        if (view == null) {
            return;
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: cn.yoozworld.watch.utils.notifi.NotificationView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (NotificationView.this.onClick(view2, view2.getId())) {
                    NotificationView.this.hide();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hide() {
        NotificationManager.getInstance().cancel(this.mNotification.mType);
    }
}
