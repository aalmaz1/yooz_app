package cn.baos.watch.sdk.code;

import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import androidx.core.internal.view.SupportMenu;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class LV {
    public static boolean PRINT_TO_SYSTEM_OUT = false;
    private static final Handler sHandler = new LogHandler();
    private static WeakReference<TextView> sTextView;

    public static void setLogView(TextView textView) {
        sTextView = new WeakReference<>(textView);
    }

    public static class LogHandler extends Handler {
        private static final int MSG_ERROR = 2;
        private static final int MSG_LOG = 1;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            TextView textView = LV.sTextView == null ? null : (TextView) LV.sTextView.get();
            if (textView != null) {
                int i = message.what;
                if (i == 1) {
                    LV.logText(textView, (String) message.obj);
                } else {
                    if (i != 2) {
                        return;
                    }
                    LV.logError(textView, (String) message.obj);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logText(TextView textView, String str) {
        logText(textView, str, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logError(TextView textView, String str) {
        logText(textView, str, SupportMenu.CATEGORY_MASK);
    }

    private static void logText(TextView textView, String str, int i) {
        CharSequence text = textView.getText();
        if (text.length() > 8192) {
            textView.setText(text.subSequence(text.length() - 2048, text.length()));
        }
        if (TextUtils.isEmpty(str)) {
            str = "null\n";
        } else if (str.length() > 0 && str.charAt(str.length() - 1) != '\n') {
            str = str + "\n";
        }
        if (i != -1) {
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ForegroundColorSpan(i), 0, str.length(), 33);
            textView.append(spannableString);
            return;
        }
        textView.append(str);
    }

    public static void logText(String str) {
        if (PRINT_TO_SYSTEM_OUT) {
            System.out.println(str);
        } else {
            sHandler.obtainMessage(1, str).sendToTarget();
        }
    }

    public static void logError(String str) {
        try {
            if (PRINT_TO_SYSTEM_OUT) {
                System.out.println(str);
            } else {
                sHandler.obtainMessage(2, str).sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
