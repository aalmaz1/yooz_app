package cn.baos.watch.sdk.bluetooth.utils;

import cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager;
import cn.baos.watch.sdk.util.LogUtil;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class TimeManager {
    private static TimeManager instance;
    public int mDefTime = 10;
    public int mFirstStatus = 0;
    public int mSecondStatus = 0;
    public int mThirdStatus = 0;

    public static TimeManager getInstance() {
        if (instance == null) {
            synchronized (TimeManager.class) {
                if (instance == null) {
                    instance = new TimeManager();
                }
            }
        }
        return instance;
    }

    public int getDefTime() {
        return this.mDefTime;
    }

    public void setDefTime(int i) {
        this.mDefTime = i;
    }

    public void initManager(long j) {
        if (TranslateManager.getInstance().isStopTransmission()) {
            return;
        }
        long fileSizeLength = TranslateManager.getInstance().getFileSizeLength();
        if (this.mFirstStatus == 0) {
            if (j <= (((fileSizeLength + 137) / 138.0d) + 2.0d) / ((double) this.mDefTime)) {
                this.mFirstStatus = 1;
                return;
            } else {
                this.mFirstStatus = -1;
                return;
            }
        }
        int i = this.mSecondStatus;
        if (i == 0) {
            if (j <= (((fileSizeLength + 137) / 138.0d) + 2.0d) / ((double) this.mDefTime)) {
                this.mSecondStatus = 1;
                return;
            } else {
                this.mSecondStatus = -1;
                return;
            }
        }
        int i2 = this.mThirdStatus;
        if (i2 != 0) {
            this.mFirstStatus = i;
            this.mSecondStatus = i2;
            if (j <= (((fileSizeLength + 137) / 138.0d) + 2.0d) / ((double) this.mDefTime)) {
                this.mThirdStatus = 1;
            } else {
                this.mThirdStatus = -1;
            }
            LogUtil.d("耗时测试: " + this.mFirstStatus + StringUtils.SPACE + this.mSecondStatus + StringUtils.SPACE + this.mThirdStatus);
        } else if (j <= (((fileSizeLength + 137) / 138.0d) + 2.0d) / ((double) this.mDefTime)) {
            this.mThirdStatus = 1;
        } else {
            this.mThirdStatus = -1;
        }
        int i3 = this.mFirstStatus;
        int i4 = this.mSecondStatus;
        int i5 = this.mThirdStatus;
        if (i3 + i4 + i5 == 3) {
            this.mDefTime--;
        } else if (i3 + i4 + i5 == -3) {
            this.mDefTime++;
        }
        int i6 = this.mDefTime;
        if (i6 < 7) {
            this.mDefTime = 7;
        } else if (i6 > 50) {
            this.mDefTime = 50;
        }
    }
}
