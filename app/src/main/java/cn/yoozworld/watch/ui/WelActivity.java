package cn.yoozworld.watch.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import cn.yoozworld.watch.R;
import com.bumptech.glide.Glide;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WelActivity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lcn/yoozworld/watch/ui/WelActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WelActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        View decorView = getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "getDecorView(...)");
        decorView.setSystemUiVisibility(1792);
        getWindow().setNavigationBarColor(0);
        getWindow().setStatusBarColor(0);
        View viewFindViewById = findViewById(R.id.gif_image);
        Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type android.widget.ImageView");
        Glide.with((FragmentActivity) this).load("file:///android_asset/keepHealth_logo.gif").into((ImageView) viewFindViewById);
        new Handler().postDelayed(new Runnable() { // from class: cn.yoozworld.watch.ui.WelActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WelActivity.onCreate$lambda$0(this.f$0);
            }
        }, 2500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(WelActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
        this$0.overridePendingTransition(0, 0);
    }
}
