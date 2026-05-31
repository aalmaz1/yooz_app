package cn.baos.watch.sdk.code.bleSdkWrapper;

import android.content.Context;
import cn.baos.watch.sdk.code.entity.ConParam;
import cn.baos.watch.sdk.code.entity.Fun;
import cn.baos.watch.sdk.code.entity.HrInterval;
import cn.baos.watch.sdk.code.entity.LocationInfo;
import cn.baos.watch.sdk.code.entity.LongSitBean;
import cn.baos.watch.sdk.code.entity.MsgState;
import cn.baos.watch.sdk.code.entity.ScreenLight;
import cn.baos.watch.sdk.code.entity.SportInfo;
import cn.baos.watch.sdk.code.entity.UserBean;
import cn.baos.watch.sdk.code.entity.WonmanHealth;
import cn.baos.watch.sdk.entitiy.WeatherEntity;
import java.io.File;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface BleSdkWrapper {
    void addDeviceCallback(DeviceCallback deviceCallback);

    void getAllDayHr(BleCallback bleCallback);

    void getAllDialplate(BleCallback bleCallback);

    void getAutoSportState(BleCallback bleCallback);

    void getBindState(BleCallback bleCallback);

    void getBloodOxgen(BleCallback bleCallback);

    void getConParams(int i, BleCallback bleCallback);

    void getContant(BleCallback bleCallback);

    void getDeviceVersion(BleCallback bleCallback);

    void getDialplate(BleCallback bleCallback);

    void getDistanceUnit(BleCallback bleCallback);

    void getFontColor(BleCallback bleCallback);

    void getFun(BleCallback bleCallback);

    void getHandLight(BleCallback bleCallback);

    void getHrPressTestState(BleCallback bleCallback);

    void getHrinterval(BleCallback bleCallback);

    void getId(BleCallback bleCallback);

    void getLanguage(BleCallback bleCallback);

    void getLimitHr(BleCallback bleCallback);

    void getLocalDialplate(BleCallback bleCallback);

    void getLocation(BleCallback bleCallback);

    void getLongSit(BleCallback bleCallback);

    void getMac(BleCallback bleCallback);

    void getMaidian(BleCallback bleCallback);

    void getMsgState(BleCallback bleCallback);

    void getNoDisturb(BleCallback bleCallback);

    void getOTAState(BleCallback bleCallback);

    void getOneMsg(int i, BleCallback bleCallback);

    void getPower(BleCallback bleCallback);

    void getSN(BleCallback bleCallback);

    void getScreenLight(BleCallback bleCallback);

    void getShortCut(BleCallback bleCallback);

    void getSleepTime(BleCallback bleCallback);

    void getSlicentHr(BleCallback bleCallback);

    void getSportFun(BleCallback bleCallback);

    void getSportHrState(BleCallback bleCallback);

    void getSportIdentify(BleCallback bleCallback);

    void getSportKm(BleCallback bleCallback);

    void getSportPause(BleCallback bleCallback);

    void getSportTarget(BleCallback bleCallback);

    void getSportTipsState(BleCallback bleCallback);

    void getStarState(BleCallback bleCallback);

    void getStepLength(BleCallback bleCallback);

    void getSupportLanguage(BleCallback bleCallback);

    void getSupportSportType(BleCallback bleCallback);

    void getTarget(BleCallback bleCallback);

    void getTime(BleCallback bleCallback);

    void getTodayCustomHr(BleCallback bleCallback);

    void getTodayPress(BleCallback bleCallback);

    void getUser(BleCallback bleCallback);

    void getWash(BleCallback bleCallback);

    void getWater(BleCallback bleCallback);

    void getWomanHealth(BleCallback bleCallback);

    void init(Context context);

    void removeDeviceCallback(DeviceCallback deviceCallback);

    void reset(int i, BleCallback bleCallback);

    void setAllDayHr(int i, BleCallback bleCallback);

    void setAutoSportState(int i, int i2, BleCallback bleCallback);

    void setConParams(ConParam conParam, BleCallback bleCallback);

    void setContant(int i, String str, String str2, BleCallback bleCallback);

    void setControlCall(int i, String str, String str2, BleCallback bleCallback);

    void setControlFindDevice(int i, BleCallback bleCallback);

    void setControlFindPhone(int i, BleCallback bleCallback);

    void setControlSport(SportInfo sportInfo, BleCallback bleCallback);

    void setControlSportWithGps(SportInfo sportInfo, BleCallback bleCallback);

    void setDialplate(int i, BleCallback bleCallback);

    void setDialplateFontColor(int i, BleCallback bleCallback);

    void setFun(Fun fun, BleCallback bleCallback);

    void setHandLight(int i, int i2, int i3, BleCallback bleCallback);

    void setHardwareState(int i, int i2, BleCallback bleCallback);

    void setHrPressTestState(int i, int i2, int i3, BleCallback bleCallback);

    void setHrTest(int i, int i2, BleCallback bleCallback);

    void setHrinterval(HrInterval hrInterval, BleCallback bleCallback);

    void setLanguage(int i, BleCallback bleCallback);

    void setLimitHr(int i, BleCallback bleCallback);

    void setLiveDistance(int i, int i2, BleCallback bleCallback);

    void setLocalDialplate(int i, int i2, BleCallback bleCallback);

    void setLocation(LocationInfo locationInfo, BleCallback bleCallback);

    void setLog(int i, int i2, BleCallback bleCallback);

    void setLogDir(String str);

    void setLogEnable(boolean z);

    void setLongSit(LongSitBean longSitBean, BleCallback bleCallback);

    void setMessage(int i, String str, BleCallback bleCallback);

    void setMsgState(MsgState msgState, BleCallback bleCallback);

    void setMusicTitle(String str, int i, int i2, BleCallback bleCallback);

    void setNoDisturb(int i, int i2, int i3, int i4, int i5, BleCallback bleCallback);

    void setOneMsg(int i, int i2, BleCallback bleCallback);

    void setScreentLight(ScreenLight screenLight, BleCallback bleCallback);

    void setShortCut(List<Integer> list, BleCallback bleCallback);

    void setSingleFun(int i, int i2, BleCallback bleCallback);

    void setSleepTime(int i, int i2, int i3, BleCallback bleCallback);

    void setSlicentHr(int i, BleCallback bleCallback);

    void setSportFun(int i, BleCallback bleCallback);

    void setSportHrState(int i, BleCallback bleCallback);

    void setSportIdentify(int i, int i2, int i3, int i4, BleCallback bleCallback);

    void setSportKm(int i, int i2, BleCallback bleCallback);

    void setSportPause(int i, BleCallback bleCallback);

    void setSportTarget(int i, int i2, int i3, int i4, BleCallback bleCallback);

    void setSportTipsState(int i, BleCallback bleCallback);

    void setStepLength(int i, BleCallback bleCallback);

    void setSupportSportType(int[] iArr);

    void setTarget(int i, int i2, BleCallback bleCallback);

    void setTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, BleCallback bleCallback);

    void setTime(int i, int i2, int i3, BleCallback bleCallback);

    void setTime(BleCallback bleCallback);

    void setTime(Date date, int i, int i2, int i3, BleCallback bleCallback);

    void setUserInfo(UserBean userBean, BleCallback bleCallback);

    void setWash(int i, int i2, int i3, int i4, int i5, int i6, int i7, BleCallback bleCallback);

    void setWater(int i, int i2, int i3, int i4, int i5, int i6, int i7, BleCallback bleCallback);

    void setWeather(WeatherEntity weatherEntity, BleCallback bleCallback);

    void setWomanHealth(WonmanHealth wonmanHealth, BleCallback bleCallback);

    void translateFile(File file, int i, BleCallback bleCallback);
}
