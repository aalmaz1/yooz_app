package cn.yoozworld.watch.utils.notifi;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes.dex */
public class NotifyContainerView extends FrameLayout {
    private static final int SLOP = 10;
    private boolean mIsCollapsible;
    private boolean mIsConsumeTouchEvent;
    private float mLastY;
    private OnDismissListener mOnDismissListener;

    public NotifyContainerView(Context context) {
        this(context, null);
    }

    public NotifyContainerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotifyContainerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setCollapsible(boolean z) {
        this.mIsCollapsible = z;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x003c  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean dispatchTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.mIsCollapsible
            if (r0 != 0) goto L9
            boolean r5 = super.dispatchTouchEvent(r5)
            return r5
        L9:
            int r0 = r5.getAction()
            r1 = 0
            if (r0 == 0) goto L3f
            r2 = 1
            if (r0 == r2) goto L3c
            r3 = 2
            if (r0 == r3) goto L1a
            r2 = 3
            if (r0 == r2) goto L3c
            goto L47
        L1a:
            boolean r0 = r4.mIsConsumeTouchEvent
            if (r0 == 0) goto L1f
            goto L47
        L1f:
            float r0 = r5.getY()
            float r3 = r4.mLastY
            float r3 = r3 - r0
            r0 = 1092616192(0x41200000, float:10.0)
            int r0 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r0 <= 0) goto L47
            android.view.View r0 = r4.getChildAt(r1)
            if (r0 == 0) goto L47
            cn.yoozworld.watch.utils.notifi.OnDismissListener r5 = r4.mOnDismissListener
            if (r5 == 0) goto L39
            r5.onDismiss()
        L39:
            r4.mIsConsumeTouchEvent = r2
            return r2
        L3c:
            r4.mIsConsumeTouchEvent = r1
            goto L47
        L3f:
            float r0 = r5.getY()
            r4.mLastY = r0
            r4.mIsConsumeTouchEvent = r1
        L47:
            boolean r5 = super.dispatchTouchEvent(r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.yoozworld.watch.utils.notifi.NotifyContainerView.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    public Activity getActivity() {
        if (getContext() instanceof Activity) {
            return (Activity) getContext();
        }
        return null;
    }
}
