package cn.yoozworld.watch.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import cn.baos.watch.sdk.util.LogUtil;
import cn.yoozworld.watch.R;
import com.tekartik.sqflite.Constant;

/* JADX INFO: loaded from: classes.dex */
public class LRActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        Uri data;
        super.onCreate(bundle);
        setContentView(R.layout.lr_view);
        Intent intent = getIntent();
        String action = intent.getAction();
        LogUtil.d("海外登录 LoginResultActivity action:" + action);
        if (!"android.intent.action.VIEW".equals(action) || (data = intent.getData()) == null) {
            return;
        }
        String queryParameter = data.getQueryParameter(Constant.PARAM_RESULT);
        LogUtil.d("海外登录 LoginResultActivity intent:" + queryParameter);
        Intent intent2 = new Intent(this, (Class<?>) HomeActivity.class);
        intent2.putExtra("loginResult", queryParameter);
        startActivity(intent2);
        finish();
    }
}
