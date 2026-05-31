package cn.baos.watch.sdk.code;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.baos.watch.sdk.code.callcontroller.CallStateManager;
import cn.baos.watch.sdk.entitiy.CallInfoEntity;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.MusicControlRequest;

/* JADX INFO: loaded from: classes.dex */
public class MainHandler extends Handler {
    private static MainHandler instance;
    private boolean hasRead;
    private Activity mActivity;
    private CallInfoEntity mCallInfoEntity;
    private Context mContext;

    public static MainHandler getInstance() {
        if (instance == null) {
            synchronized (MainHandler.class) {
                if (instance == null) {
                    instance = new MainHandler();
                }
            }
        }
        return instance;
    }

    private MainHandler() {
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == 29) {
            Toast.makeText(this.mContext, (String) message.obj, 1).show();
            return;
        }
        if (i == 181) {
            MusicControlManager.getInstance().handleCommandFromWatchToControlMusic((MusicControlRequest) message.obj);
            return;
        }
        if (i != 183) {
            switch (i) {
                case 101:
                    CallInfoEntity callInfoEntity = (CallInfoEntity) message.obj;
                    this.mCallInfoEntity = callInfoEntity;
                    callInfoEntity.setPhoneState(0);
                    LogUtil.d("phone 响铃:CALL_STATE_RINGING:" + this.mCallInfoEntity.toString());
                    MessageManager.getInstance().sendAppSystemPhone(this.mCallInfoEntity);
                    break;
                case 102:
                    CallInfoEntity callInfoEntity2 = this.mCallInfoEntity;
                    if (callInfoEntity2 != null) {
                        callInfoEntity2.setPhoneState(2);
                        LogUtil.d("phone 接听电话:" + this.mCallInfoEntity.toString());
                        MessageManager.getInstance().sendAppSystemPhone(this.mCallInfoEntity);
                        break;
                    }
                    break;
                case 103:
                    CallInfoEntity callInfoEntity3 = this.mCallInfoEntity;
                    if (callInfoEntity3 != null) {
                        callInfoEntity3.setPhoneState(1);
                        MessageManager.getInstance().sendAppSystemPhone(this.mCallInfoEntity);
                        LogUtil.d("phone 挂断电话");
                        break;
                    }
                    break;
                case 104:
                    LogUtil.d("phone 手表端要求挂断电话");
                    CallStateManager.getInstance();
                    CallStateManager.endCall(this.mContext);
                    CallStateManager.getInstance().endCall();
                    break;
            }
            return;
        }
        MusicControlManager.getInstance().sendVolumeWhenChange();
    }
}
