package cn.baos.watch.sdk.utils;

import android.content.Context;
import android.provider.Settings;
import androidx.exifinterface.media.ExifInterface;
import cn.baos.watch.sdk.entitiy.ClockListEntity;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.NlpEntity;
import cn.baos.watch.sdk.entitiy.ReminderListEntity;
import cn.baos.watch.w100.messages.QueryAlarmResponse;
import cn.baos.watch.w100.messages.QueryReminderResponse;
import com.king.zxing.util.CodeUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes.dex */
public class TimeUtils {
    public static String nowTime() {
        String str = "" + System.currentTimeMillis();
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static int isOpenNetTimeSync(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), "auto_time");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return 3;
        }
    }

    public static long getNetWorkTime() {
        return System.currentTimeMillis();
    }

    public static int getCustomAlarmStampS(int i) {
        return (int) ((System.currentTimeMillis() / 1000) + ((long) i));
    }

    public static long getCustomAlarmStampMS(int i) {
        return System.currentTimeMillis() + ((long) (i * 1000));
    }

    public static int getXiaoAiAlarmStampS(String str) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(str));
            LogUtil.d("alarm value:" + calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public static long getXiaoAiAlarmStampMS(String str) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault()).parse(str));
            LogUtil.d("alarm value:" + calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static String getDateIsWhichWeekDay(String str, String str2) {
        Calendar calendar = Calendar.getInstance();
        String weekDayFromCalendar = "";
        try {
            calendar.setTime(new SimpleDateFormat(str2, Locale.getDefault()).parse(str));
            weekDayFromCalendar = getWeekDayFromCalendar(calendar);
            LogUtil.d("nlp 设置闹钟时间转周number:" + weekDayFromCalendar);
            return weekDayFromCalendar;
        } catch (ParseException e) {
            e.printStackTrace();
            return weekDayFromCalendar;
        }
    }

    private static String getWeekDayFromCalendar(Calendar calendar) {
        switch (calendar.get(7)) {
            case 1:
                return Constant.CYCLE_TYPE_SUNDAY_STR;
            case 2:
                return Constant.CYCLE_TYPE_MONDAY_STR;
            case 3:
                return Constant.CYCLE_TYPE_TUESDAY_STR;
            case 4:
                return Constant.CYCLE_TYPE_WEDNESDAY_STR;
            case 5:
                return Constant.CYCLE_TYPE_THURSDAY_STR;
            case 6:
                return Constant.CYCLE_TYPE_FRIDAY_STR;
            case 7:
                return Constant.CYCLE_TYPE_SATURDAY_STR;
            default:
                return "";
        }
    }

    public static String getXiaoAiAlarmWeeklyHasWhichWeekDay(String str) {
        LogUtil.d("circle_extra:" + ArrayUtils.toString(str.split("\\*")));
        String str2 = str.split("\\*")[1];
        String str3 = str2.contains("0") ? Constant.CYCLE_TYPE_SUNDAY_STR : "";
        if (str2.contains("1")) {
            str3 = str3 + Constant.CYCLE_TYPE_MONDAY_STR;
        }
        if (str2.contains(ExifInterface.GPS_MEASUREMENT_2D)) {
            str3 = str3 + Constant.CYCLE_TYPE_TUESDAY_STR;
        }
        if (str2.contains(ExifInterface.GPS_MEASUREMENT_3D)) {
            str3 = str3 + Constant.CYCLE_TYPE_WEDNESDAY_STR;
        }
        if (str2.contains("4")) {
            str3 = str3 + Constant.CYCLE_TYPE_THURSDAY_STR;
        }
        if (str2.contains("5")) {
            str3 = str3 + Constant.CYCLE_TYPE_FRIDAY_STR;
        }
        if (str2.contains("6")) {
            str3 = str3 + Constant.CYCLE_TYPE_SATURDAY_STR;
        }
        LogUtil.d("小爱周循环有周几:" + str3);
        return str3;
    }

    public static int getXiaoAiSetDownCounterValue(String str) {
        String[] strArrSplit = str.split(":");
        return (int) ((Long.parseLong(strArrSplit[0].replace("+", "")) * 3600) + (Long.parseLong(strArrSplit[1]) * 60) + Long.parseLong(strArrSplit[2]));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v38 */
    /* JADX WARN: Type inference failed for: r2v39 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v40 */
    /* JADX WARN: Type inference failed for: r2v41 */
    /* JADX WARN: Type inference failed for: r2v42 */
    /* JADX WARN: Type inference failed for: r2v43 */
    /* JADX WARN: Type inference failed for: r2v44 */
    /* JADX WARN: Type inference failed for: r2v45 */
    /* JADX WARN: Type inference failed for: r2v46 */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v8 */
    public static NlpEntity.CircleModel getCycleModel(String str, long j) {
        NlpEntity.CircleModel circleModel;
        circleModel = new NlpEntity.CircleModel();
        str.hashCode();
        switch (str) {
            case "周一至周五":
                LogUtil.d(Constant.CYCLE_TYPE_MONDAY_TO_FRIDAY_STR);
                circleModel.setCircleType(4);
                circleModel.setDayOfWeek(62);
                break;
            case "每周":
                LogUtil.d(Constant.CYCLE_TYPE_WEEKLY_STR);
                circleModel.setCircleType(4);
                circleModel.setDayOfWeek(1 << dayIndexInWeek(j));
                break;
            case "每天":
                LogUtil.d(Constant.CYCLE_TYPE_EVERYDAY_STR);
                circleModel.setCircleType(3);
                break;
            case "每年":
                LogUtil.d(Constant.CYCLE_TYPE_YEARLY_STR);
                circleModel.setCircleType(6);
                circleModel.setCircleExtra(0);
                circleModel.setMouthOfYear(1 << mouthIndexInYear(j));
                circleModel.setDayOfMouth(1 << dayIndexInMonth(j));
                break;
            case "每月":
                LogUtil.d(Constant.CYCLE_TYPE_MOUTH_STR);
                circleModel.setCircleType(5);
                circleModel.setCircleExtra(4);
                circleModel.setDayOfMouth(1 << dayIndexInMonth(j));
                break;
            case "仅一次":
                LogUtil.d(Constant.CYCLE_TYPE_ONCE_STR);
                circleModel.setCircleType(0);
                break;
        }
        if (!str.equals(Constant.CYCLE_TYPE_MONDAY_TO_FRIDAY_STR) && (str.contains(Constant.CYCLE_TYPE_SUNDAY_STR) || str.contains(Constant.CYCLE_TYPE_MONDAY_STR) || str.contains(Constant.CYCLE_TYPE_TUESDAY_STR) || str.contains(Constant.CYCLE_TYPE_WEDNESDAY_STR) || str.contains(Constant.CYCLE_TYPE_THURSDAY_STR) || str.contains(Constant.CYCLE_TYPE_FRIDAY_STR) || str.contains(Constant.CYCLE_TYPE_SATURDAY_STR))) {
            circleModel.setCircleType(4);
            boolean zContains = str.contains(Constant.CYCLE_TYPE_SUNDAY_STR);
            ?? r2 = zContains;
            if (str.contains(Constant.CYCLE_TYPE_MONDAY_STR)) {
                r2 = (zContains ? 1 : 0) | 2;
            }
            ?? r22 = r2;
            if (str.contains(Constant.CYCLE_TYPE_TUESDAY_STR)) {
                r22 = (r2 == true ? 1 : 0) | 4;
            }
            ?? r23 = r22;
            if (str.contains(Constant.CYCLE_TYPE_WEDNESDAY_STR)) {
                r23 = (r22 == true ? 1 : 0) | 8;
            }
            ?? r24 = r23;
            if (str.contains(Constant.CYCLE_TYPE_THURSDAY_STR)) {
                r24 = (r23 == true ? 1 : 0) | 16;
            }
            ?? r25 = r24;
            if (str.contains(Constant.CYCLE_TYPE_FRIDAY_STR)) {
                r25 = (r24 == true ? 1 : 0) | 32;
            }
            int i = r25;
            if (str.contains(Constant.CYCLE_TYPE_SATURDAY_STR)) {
                i = (r25 == true ? 1 : 0) | 64;
            }
            circleModel.setDayOfWeek(i);
        }
        return circleModel;
    }

    public static String getAlarmCycleTypeStr(QueryAlarmResponse.AlarmData alarmData) {
        String circleStringByCircleType = getCircleStringByCircleType(alarmData);
        LogUtil.d("闹钟管理:" + circleStringByCircleType);
        return circleStringByCircleType;
    }

    public static String getReminderCycleTypeStr(QueryReminderResponse.ReminderData reminderData) {
        String circleStringByCircleType = getCircleStringByCircleType(reminderData);
        LogUtil.d("日程提醒管理:" + circleStringByCircleType);
        return circleStringByCircleType;
    }

    private static String getCircleStringByCircleType(QueryReminderResponse.ReminderData reminderData) {
        int i = reminderData.circle_type;
        if (i == 0) {
            return Constant.CYCLE_TYPE_ONCE_STR;
        }
        if (i == 6) {
            return Constant.CYCLE_TYPE_YEARLY_STR;
        }
        if (i == 62) {
            return Constant.CYCLE_TYPE_MONDAY_TO_FRIDAY_STR;
        }
        if (i == 3) {
            return Constant.CYCLE_TYPE_EVERYDAY_STR;
        }
        if (i != 4) {
            return "";
        }
        String strConcat = (reminderData.mask_wday & 1) == 1 ? "周日 " : "";
        if ((reminderData.mask_wday & 2) == 2) {
            strConcat = strConcat.concat("周一 ");
        }
        if ((reminderData.mask_wday & 4) == 4) {
            strConcat = strConcat + "周二 ";
        }
        if ((reminderData.mask_wday & 8) == 8) {
            strConcat = strConcat + "周三 ";
        }
        if ((reminderData.mask_wday & 16) == 16) {
            strConcat = strConcat + "周四 ";
        }
        if ((reminderData.mask_wday & 32) == 32) {
            strConcat = strConcat + "周五 ";
        }
        return (reminderData.mask_wday & 64) == 64 ? strConcat + "周六 " : strConcat;
    }

    private static String getCircleStringByCircleType(QueryAlarmResponse.AlarmData alarmData) {
        int i = alarmData.circle_type;
        if (i == 0) {
            return Constant.CYCLE_TYPE_ONCE_STR;
        }
        if (i == 6) {
            return Constant.CYCLE_TYPE_YEARLY_STR;
        }
        if (i == 62) {
            return Constant.CYCLE_TYPE_MONDAY_TO_FRIDAY_STR;
        }
        if (i == 3) {
            return Constant.CYCLE_TYPE_EVERYDAY_STR;
        }
        if (i != 4) {
            return "";
        }
        String strConcat = (alarmData.mask_wday & 1) == 1 ? "周日 " : "";
        if ((alarmData.mask_wday & 2) == 2) {
            strConcat = strConcat.concat("周一 ");
        }
        if ((alarmData.mask_wday & 4) == 4) {
            strConcat = strConcat + "周二 ";
        }
        if ((alarmData.mask_wday & 8) == 8) {
            strConcat = strConcat + "周三 ";
        }
        if ((alarmData.mask_wday & 16) == 16) {
            strConcat = strConcat + "周四 ";
        }
        if ((alarmData.mask_wday & 32) == 32) {
            strConcat = strConcat + "周五 ";
        }
        return (alarmData.mask_wday & 64) == 64 ? strConcat + "周六 " : strConcat;
    }

    public static String getAlarmTime(long j) {
        LogUtil.d("闹钟管理 传入时间戳进行转换hh:mm:" + j);
        String str = new SimpleDateFormat("HH:mm").format(Long.valueOf(j));
        LogUtil.d("timeWhen:" + str);
        return str;
    }

    public static String getReminderTime(long j) {
        LogUtil.d("日程管理 传入时间戳进行转换MM月dd日 HH:mm:" + j);
        String str = new SimpleDateFormat("MM月dd日 HH:mm").format(Long.valueOf(j));
        LogUtil.d("日程提醒:" + str);
        return str;
    }

    public static String getTimeToHHmm24(long j) {
        String str = new SimpleDateFormat("HH:mm").format(Long.valueOf(j));
        LogUtil.d("timeWhen:" + str);
        return str;
    }

    public static String getTimeToMMDDHHmm24(long j) {
        String str = new SimpleDateFormat("MM月dd日 HH:mm").format(Long.valueOf(j));
        LogUtil.d("timeWhen:" + str);
        return str;
    }

    public static String getAlarmSlot(long j) {
        LogUtil.d("闹钟管理 传入时间戳进行转换a:" + j);
        String str = new SimpleDateFormat("a").format(Long.valueOf(j));
        LogUtil.d("timeSlot:" + str);
        return str;
    }

    public static long getClockManageAlarmTimeStamp(ClockListEntity clockListEntity) {
        LogUtil.d("handle ClockListEntity:" + clockListEntity.toString());
        Calendar clockCalendarHHmm = getClockCalendarHHmm(clockListEntity);
        LogUtil.d("闹钟当天时间戳:" + clockCalendarHHmm.getTimeInMillis() + " 此时的时间戳:" + System.currentTimeMillis());
        return clockCalendarHHmm.getTimeInMillis();
    }

    public static long getReminderManageAlarmTimeStamp(ReminderListEntity reminderListEntity) {
        long timeInMillis;
        LogUtil.d("日程管理 handle reminderListEntity:" + reminderListEntity.toString());
        String str = new SimpleDateFormat("yyyy").format(getTodayCalendar().getTime()) + "年" + reminderListEntity.getTime();
        LogUtil.d("日程管理 currentTime:" + str);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy年MM月dd日 HH:mm").parse(str));
            timeInMillis = calendar.getTimeInMillis();
        } catch (ParseException e) {
            LogUtil.d(e.getMessage());
            e.getMessage();
            timeInMillis = 0;
        }
        LogUtil.d("日程管理当天时间戳:" + timeInMillis);
        return timeInMillis;
    }

    private static Calendar getClockCalendarhhmma(ClockListEntity clockListEntity) {
        String str = new SimpleDateFormat("yyyy-MM-dd").format(getTodayCalendar().getTime()) + StringUtils.SPACE + clockListEntity.getTime() + StringUtils.SPACE + clockListEntity.getTimeSlot();
        LogUtil.d("currentTime:" + str);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(str));
        } catch (ParseException e) {
            LogUtil.d(e.getMessage());
        }
        LogUtil.d("闹钟时间完整上下午解析:" + calendar.getTimeInMillis());
        return calendar;
    }

    private static Calendar getClockCalendarHHmm(ClockListEntity clockListEntity) {
        String str = new SimpleDateFormat("yyyy-MM-dd").format(getTodayCalendar().getTime()) + StringUtils.SPACE + clockListEntity.getTime();
        LogUtil.d("闹钟管理 currentTime:" + str);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str));
        } catch (ParseException e) {
            LogUtil.d(e.getMessage());
        }
        LogUtil.d("闹钟管理 时间完整24小时解析:" + calendar.getTimeInMillis());
        return calendar;
    }

    private static Calendar getTodayCalendar() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String getDataToday() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String str = simpleDateFormat.format(calendar.getTime());
        LogUtil.d("今天日期:" + str);
        return str;
    }

    public static String getTimeZone() {
        return TimeZones.GMT_ID + String.valueOf(TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 3600000);
    }

    public static int getTimeZoneChange() {
        String str = TimeZones.GMT_ID + String.valueOf(TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 3600000);
        return CodeUtils.DEFAULT_REQ_WIDTH;
    }

    public static String getTimeZoneTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(Calendar.getInstance(TimeZone.getDefault()).getTime());
    }

    public static int getTimeZoneTimeInt() {
        new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").setTimeZone(TimeZone.getDefault());
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        LogUtil.d("时区：" + (calendar.getTime().getTimezoneOffset() / 60) + "时区2：" + TimeZone.getDefault());
        return (int) (calendar.getTimeInMillis() / 1000);
    }

    public static int getTimezoneDifferenceInSecond(String str) {
        Date date = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        TimeZone timeZone2 = TimeZone.getTimeZone(str);
        LogUtil.d("timeZone1:" + timeZone.getDisplayName() + " timeZone2:" + timeZone2.getDisplayName());
        return ((timeZone2.getRawOffset() + (timeZone2.inDaylightTime(date) ? timeZone2.getDSTSavings() : 0)) - (timeZone.getRawOffset() + (timeZone.inDaylightTime(date) ? timeZone.getDSTSavings() : 0))) / 1000;
    }

    public static int changeStamp(int i, int i2) {
        return i - (((((new Date(i).getTimezoneOffset() / 60) + i2) * 60) * 60) * 1000);
    }

    public static int dayIndexInWeek(long j) {
        Calendar.getInstance().setTime(new Date(j));
        return r0.get(7) - 1;
    }

    public static int mouthIndexInYear(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(j));
        return calendar.get(2);
    }

    public static int dayIndexInMonth(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(j));
        int i = calendar.get(5) - 1;
        LogUtil.d("日程管理 时间:" + j + " 每个月的第几天" + i);
        return i;
    }
}
