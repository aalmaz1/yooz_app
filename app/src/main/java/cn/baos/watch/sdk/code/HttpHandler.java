package cn.baos.watch.sdk.code;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.baos.watch.sdk.constant.AccountConstant;
import cn.baos.watch.sdk.entitiy.SportStepGetAllFromServerEntity;
import cn.baos.watch.sdk.entitiy.WeatherEntity;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HttpHandler extends Handler {
    private static HttpHandler instance;
    private boolean hasRead;
    private Activity mActivity;
    private Context mContext;

    public static HttpHandler getInstance() {
        if (instance == null) {
            synchronized (HttpHandler.class) {
                if (instance == null) {
                    instance = new HttpHandler();
                }
            }
        }
        return instance;
    }

    private HttpHandler() {
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i != 113) {
            if (i == 118) {
                obtainMessage(127).sendToTarget();
                return;
            }
            if (i == 121) {
                obtainMessage(127).sendToTarget();
                return;
            }
            if (i == 123) {
                WeatherEntity weatherEntity = (WeatherEntity) message.obj;
                LogUtil.d("天气数据:" + weatherEntity.toString());
                if (weatherEntity.getCode() == null || !weatherEntity.getCode().equals("0")) {
                    return;
                }
                MessageManager.getInstance().sendWeatherInfoToWatch(weatherEntity);
                return;
            }
            if (i == 130) {
                LogUtil.d("保存花豹的token:" + message.obj);
                return;
            }
            if (i == 152) {
                List<SportStepGetAllFromServerEntity.DataBean.StepVOListBean> stepVOList = ((SportStepGetAllFromServerEntity) message.obj).getData().getStepVOList();
                for (int i2 = 0; i2 < stepVOList.size(); i2++) {
                    stepVOList.get(i2);
                }
                return;
            }
            if (i != 158) {
                if (i == 115) {
                    Toast.makeText(this.mContext, "小爱授权失败，请尝试重新登录", 1).show();
                    return;
                }
                if (i == 116) {
                    String str = (String) message.obj;
                    if (str != null && str.isEmpty()) {
                        Toast.makeText(this.mContext, (String) message.obj, 1).show();
                        return;
                    } else {
                        Toast.makeText(this.mContext, "验证码发送成功", 1).show();
                        return;
                    }
                }
                if (i != 127) {
                    if (i != 128) {
                        return;
                    }
                    LogUtil.d("花豹账号登陆成功");
                    return;
                }
                LogUtil.d("登陆花豹账号");
                String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, AccountConstant.LOGIN_TYPE);
                SharePreferenceUtils.queryStringByKey(this.mContext, AccountConstant.USER_ID_KEY_HUA_BAO);
                if (strQueryStringByKey.equals(AccountConstant.REGISTER_SOURCE_TYPE_XIAO_MI)) {
                    SharePreferenceUtils.queryStringByKey(this.mContext, AccountConstant.USER_ACCESS_TOKEN_KEY_XIAO_MI);
                    return;
                } else {
                    SharePreferenceUtils.queryStringByKey(this.mContext, AccountConstant.USER_ACCESS_TOKEN_KEY_WEI_XIN);
                    return;
                }
            }
        }
        obtainMessage(127).sendToTarget();
    }
}
