package cn.baos.watch.sdk.code.zplprint;

import android.content.Context;
import cn.baos.watch.sdk.util.FileUtils;
import cn.baos.watch.sdk.util.LogUtil;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.element.ZebraNativeZpl;

/* JADX INFO: loaded from: classes.dex */
public class ZplPrinterManager {
    private ZebraLabel zebraLabel = new ZebraLabel();

    public void saveSnCodeToLocal(Context context, String str) {
        String strMacAddressToLongValue = macAddressToLongValue(str);
        this.zebraLabel.addElement(new ZebraNativeZpl("^BY2,2,80\n"));
        this.zebraLabel.addElement(new ZebraNativeZpl("^FO55,0^BC^FD" + strMacAddressToLongValue + "^FS\n"));
        this.zebraLabel.addElement(new ZebraNativeZpl("^FO55,115^BC^FD" + strMacAddressToLongValue + "^FS\n"));
        this.zebraLabel.addElement(new ZebraNativeZpl("^FO55,240^BC^FD" + strMacAddressToLongValue + "^FS\n"));
        this.zebraLabel.addElement(new ZebraNativeZpl("^PQ1,0,1\n"));
        LogUtil.d("zpl:\n" + this.zebraLabel.getZplCode());
        FileUtils.writeZplValueToLocal(context, str + System.currentTimeMillis(), this.zebraLabel.getZplCode());
    }

    public String macAddressToLongValue(String str) {
        String str2 = "";
        for (String str3 : str.split(":")) {
            str2 = str2 + str3;
        }
        String strSubstring = str2.substring(2);
        LogUtil.d("zpl即将转换十进制条形码:" + strSubstring);
        return String.valueOf(FileUtils.decodeHEXLong(strSubstring));
    }
}
