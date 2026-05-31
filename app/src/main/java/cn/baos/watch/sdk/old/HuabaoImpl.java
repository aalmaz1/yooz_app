package cn.baos.watch.sdk.old;

import android.content.Context;
import cn.baos.watch.sdk.database.contacts.ContactsManager;
import cn.baos.watch.sdk.entitiy.WeatherEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActivePhoneManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr.DailyRhrManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo.DailySpoManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.gps.GpsModeManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.bp.BpManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.bs.BsManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.meto.MetoManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.rh.RhManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.temp.TempManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats.SleepStatsManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus.SleepStatusManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate.SportHeartManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord.SportRecordFromWatchManager;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.old.bleSdkWrapper.BleCallback;
import cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper;
import cn.baos.watch.sdk.old.bleSdkWrapper.DeviceCallback;
import cn.baos.watch.sdk.old.entity.ConParam;
import cn.baos.watch.sdk.old.entity.Fun;
import cn.baos.watch.sdk.old.entity.HrInterval;
import cn.baos.watch.sdk.old.entity.LocationInfo;
import cn.baos.watch.sdk.old.entity.LongSitBean;
import cn.baos.watch.sdk.old.entity.MsgState;
import cn.baos.watch.sdk.old.entity.ScreenLight;
import cn.baos.watch.sdk.old.entity.SportInfo;
import cn.baos.watch.sdk.old.entity.UserBean;
import cn.baos.watch.sdk.old.entity.WonmanHealth;
import java.io.File;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HuabaoImpl implements BleSdkWrapper {
    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void addDeviceCallback(DeviceCallback deviceCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getAllDayHr(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getAllDialplate(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getAutoSportState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getBindState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getBloodOxgen(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getConParams(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getContant(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getDeviceVersion(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getDialplate(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getDistanceUnit(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getFontColor(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getFun(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getHandLight(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getHrPressTestState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getHrinterval(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getId(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getLanguage(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getLimitHr(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getLocalDialplate(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getLocation(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getLongSit(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getMac(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getMaidian(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getMsgState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getNoDisturb(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getOTAState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getOneMsg(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getPower(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSN(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getScreenLight(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getShortCut(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSleepTime(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSlicentHr(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSportFun(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSportHrState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSportIdentify(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSportKm(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSportPause(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSportTarget(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSportTipsState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getStarState(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getStepLength(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSupportLanguage(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getSupportSportType(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getTarget(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getTime(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getTodayCustomHr(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getTodayPress(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getUser(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getWash(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getWater(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void getWomanHealth(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void removeDeviceCallback(DeviceCallback deviceCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void reset(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setAllDayHr(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setAutoSportState(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setConParams(ConParam conParam, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setContant(int i, String str, String str2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setControlCall(int i, String str, String str2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setControlFindDevice(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setControlFindPhone(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setControlSport(SportInfo sportInfo, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setControlSportWithGps(SportInfo sportInfo, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setDialplate(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setDialplateFontColor(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setFun(Fun fun, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setHandLight(int i, int i2, int i3, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setHardwareState(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setHrPressTestState(int i, int i2, int i3, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setHrTest(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setHrinterval(HrInterval hrInterval, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLanguage(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLimitHr(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLiveDistance(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLocalDialplate(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLocation(LocationInfo locationInfo, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLog(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLogDir(String str) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLogEnable(boolean z) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setLongSit(LongSitBean longSitBean, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setMessage(int i, String str, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setMsgState(MsgState msgState, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setMusicTitle(String str, int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setNoDisturb(int i, int i2, int i3, int i4, int i5, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setOneMsg(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setScreentLight(ScreenLight screenLight, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setShortCut(List<Integer> list, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSingleFun(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSleepTime(int i, int i2, int i3, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSlicentHr(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSportFun(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSportHrState(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSportIdentify(int i, int i2, int i3, int i4, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSportKm(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSportPause(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSportTarget(int i, int i2, int i3, int i4, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSportTipsState(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setStepLength(int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setSupportSportType(int[] iArr) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setTarget(int i, int i2, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setTime(int i, int i2, int i3, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setTime(BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setTime(Date date, int i, int i2, int i3, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setUserInfo(UserBean userBean, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setWash(int i, int i2, int i3, int i4, int i5, int i6, int i7, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setWater(int i, int i2, int i3, int i4, int i5, int i6, int i7, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setWomanHealth(WonmanHealth wonmanHealth, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void translateFile(File file, int i, BleCallback bleCallback) {
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void init(Context context) {
        SportRecordFromWatchManager.getInstance().setContext(context);
        SportModeManager.getInstance().setContext(context);
        SportHeartManager.getInstance().setContext(context);
        SleepStatsManager.getInstance().setContext(context);
        SleepStatusManager.getInstance().setContext(context);
        DailyActiveManager.getInstance().setContext(context);
        DailyActivePhoneManager.getInstance().setContext(context);
        DailyHrateManager.getInstance().setContext(context);
        DailySpoManager.getInstance().setContext(context);
        DailyRhrManager.getInstance().setContext(context);
        GpsModeManager.getInstance().setContext(context);
        ContactsManager.getInstance().setContext(context);
        BpManager.getInstance().setContext(context);
        BsManager.getInstance().setContext(context);
        MetoManager.getInstance().setContext(context);
        RhManager.getInstance().setContext(context);
        TempManager.getInstance().setContext(context);
    }

    @Override // cn.baos.watch.sdk.old.bleSdkWrapper.BleSdkWrapper
    public void setWeather(WeatherEntity weatherEntity, BleCallback bleCallback) {
        MessageManager.getInstance().sendWeatherInfoToWatch(weatherEntity);
        if (bleCallback != null) {
            bleCallback.complete(0, "success");
        }
    }
}
