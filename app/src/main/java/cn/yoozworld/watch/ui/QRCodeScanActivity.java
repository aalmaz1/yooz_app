package cn.yoozworld.watch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.yoozworld.watch.R;
import com.google.zxing.Result;
import com.king.camera.scan.AnalyzeResult;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.analyze.Analyzer;
import com.king.zxing.BarcodeCameraScanActivity;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import com.king.zxing.analyze.QRCodeAnalyzer;

/* JADX INFO: loaded from: classes.dex */
public class QRCodeScanActivity extends BarcodeCameraScanActivity {
    @Override // com.king.zxing.BarcodeCameraScanActivity, com.king.camera.scan.BaseCameraScanActivity
    public int getLayoutId() {
        return R.layout.activity_qrcode_scan;
    }

    @Override // com.king.camera.scan.BaseCameraScanActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String stringExtra = getIntent().getStringExtra("title");
        String stringExtra2 = getIntent().getStringExtra("tips");
        final String stringExtra3 = getIntent().getStringExtra("scanTitle");
        final String stringExtra4 = getIntent().getStringExtra("scanTips");
        final String stringExtra5 = getIntent().getStringExtra("scan_tab");
        TextView textView = (TextView) findViewById(R.id.tv_title);
        TextView textView2 = (TextView) findViewById(R.id.tv_tips);
        ((RelativeLayout) findViewById(R.id.img_error)).setOnClickListener(new View.OnClickListener() { // from class: cn.yoozworld.watch.ui.QRCodeScanActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeScanActivity.this, (Class<?>) ScanTipsActivity.class);
                intent.putExtra("scanTitle", stringExtra3);
                intent.putExtra("scanTips", stringExtra4);
                intent.putExtra("scanTab", stringExtra5);
                QRCodeScanActivity.this.startActivity(intent);
            }
        });
        textView.setText(stringExtra);
        textView2.setText(stringExtra2);
    }

    @Override // com.king.camera.scan.BaseCameraScanActivity
    public void initCameraScan(CameraScan<Result> cameraScan) {
        super.initCameraScan(cameraScan);
        cameraScan.setPlayBeep(true);
    }

    @Override // com.king.zxing.BarcodeCameraScanActivity, com.king.camera.scan.BaseCameraScanActivity
    public Analyzer<Result> createAnalyzer() {
        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig.setHints(DecodeFormatManager.QR_CODE_HINTS).setFullAreaScan(false).setAreaRectRatio(0.8f).setAreaRectVerticalOffset(0).setAreaRectHorizontalOffset(0);
        return new QRCodeAnalyzer(decodeConfig);
    }

    @Override // com.king.camera.scan.CameraScan.OnScanResultCallback
    public void onScanResultCallback(AnalyzeResult<Result> analyzeResult) {
        getCameraScan().setAnalyzeImage(false);
        Intent intent = new Intent();
        intent.putExtra(CameraScan.SCAN_RESULT, analyzeResult.getResult().getText());
        setResult(-1, intent);
        finish();
    }
}
