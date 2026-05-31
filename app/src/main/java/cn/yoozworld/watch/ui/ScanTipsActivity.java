package cn.yoozworld.watch.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import cn.yoozworld.watch.R;
import com.bumptech.glide.Glide;

/* JADX INFO: loaded from: classes.dex */
public class ScanTipsActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_scan_tips);
        String stringExtra = getIntent().getStringExtra("scanTitle");
        String stringExtra2 = getIntent().getStringExtra("scanTips");
        String stringExtra3 = getIntent().getStringExtra("scanTab");
        TextView textView = (TextView) findViewById(R.id.title_s);
        TextView textView2 = (TextView) findViewById(R.id.scan_title);
        TextView textView3 = (TextView) findViewById(R.id.scan_tips);
        textView.setText(stringExtra3);
        textView2.setText(stringExtra);
        textView3.setText(stringExtra2);
        Glide.with((FragmentActivity) this).asGif().load(Integer.valueOf(R.raw.scantipimg)).into((ImageView) findViewById(R.id.scan_gif_image));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() { // from class: cn.yoozworld.watch.ui.ScanTipsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ScanTipsActivity.this.finish();
            }
        });
    }
}
