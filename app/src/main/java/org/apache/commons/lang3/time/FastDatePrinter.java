package org.apache.commons.lang3.time;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.FieldPosition;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.exception.ExceptionUtils;

/* JADX INFO: loaded from: classes3.dex */
public class FastDatePrinter implements DatePrinter, Serializable {
    public static final int FULL = 0;
    public static final int LONG = 1;
    private static final int MAX_DIGITS = 10;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static final ConcurrentMap<TimeZoneDisplayKey, String> cTimeZoneDisplayCache = new ConcurrentHashMap(7);
    private static final long serialVersionUID = 1;
    private final Locale mLocale;
    private transient int mMaxLengthEstimate;
    private final String mPattern;
    private transient Rule[] mRules;
    private final TimeZone mTimeZone;

    private interface NumberRule extends Rule {
        void appendTo(Appendable appendable, int i) throws IOException;
    }

    private interface Rule {
        void appendTo(Appendable appendable, Calendar calendar) throws IOException;

        int estimateLength();
    }

    protected FastDatePrinter(String str, TimeZone timeZone, Locale locale) {
        this.mPattern = str;
        this.mTimeZone = timeZone;
        this.mLocale = locale;
        init();
    }

    private void init() {
        List<Rule> pattern = parsePattern();
        Rule[] ruleArr = (Rule[]) pattern.toArray(new Rule[pattern.size()]);
        this.mRules = ruleArr;
        int length = ruleArr.length;
        int iEstimateLength = 0;
        while (true) {
            length--;
            if (length >= 0) {
                iEstimateLength += this.mRules[length].estimateLength();
            } else {
                this.mMaxLengthEstimate = iEstimateLength;
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.util.List<org.apache.commons.lang3.time.FastDatePrinter.Rule> parsePattern() {
        /*
            Method dump skipped, instruction units count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.FastDatePrinter.parsePattern():java.util.List");
    }

    protected String parseToken(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        int i = iArr[0];
        int length = str.length();
        char cCharAt = str.charAt(i);
        if ((cCharAt >= 'A' && cCharAt <= 'Z') || (cCharAt >= 'a' && cCharAt <= 'z')) {
            sb.append(cCharAt);
            while (true) {
                int i2 = i + 1;
                if (i2 >= length || str.charAt(i2) != cCharAt) {
                    break;
                }
                sb.append(cCharAt);
                i = i2;
            }
        } else {
            sb.append('\'');
            boolean z = false;
            while (i < length) {
                char cCharAt2 = str.charAt(i);
                if (cCharAt2 != '\'') {
                    if (!z && ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || (cCharAt2 >= 'a' && cCharAt2 <= 'z'))) {
                        i--;
                        break;
                    }
                    sb.append(cCharAt2);
                } else {
                    int i3 = i + 1;
                    if (i3 >= length || str.charAt(i3) != '\'') {
                        z = !z;
                    } else {
                        sb.append(cCharAt2);
                        i = i3;
                    }
                }
                i++;
            }
        }
        iArr[0] = i;
        return sb.toString();
    }

    protected NumberRule selectNumberRule(int i, int i2) {
        if (i2 == 1) {
            return new UnpaddedNumberField(i);
        }
        if (i2 == 2) {
            return new TwoDigitNumberField(i);
        }
        return new PaddedNumberField(i, i2);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    @Deprecated
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof Date) {
            return format((Date) obj, stringBuffer);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, stringBuffer);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), stringBuffer);
        }
        throw new IllegalArgumentException("Unknown class: " + (obj == null ? "<null>" : obj.getClass().getName()));
    }

    String format(Object obj) {
        if (obj instanceof Date) {
            return format((Date) obj);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue());
        }
        throw new IllegalArgumentException("Unknown class: " + (obj == null ? "<null>" : obj.getClass().getName()));
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(long j) {
        Calendar calendarNewCalendar = newCalendar();
        calendarNewCalendar.setTimeInMillis(j);
        return applyRulesToString(calendarNewCalendar);
    }

    private String applyRulesToString(Calendar calendar) {
        return ((StringBuilder) applyRules(calendar, new StringBuilder(this.mMaxLengthEstimate))).toString();
    }

    private Calendar newCalendar() {
        return Calendar.getInstance(this.mTimeZone, this.mLocale);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Date date) {
        Calendar calendarNewCalendar = newCalendar();
        calendarNewCalendar.setTime(date);
        return applyRulesToString(calendarNewCalendar);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String format(Calendar calendar) {
        return ((StringBuilder) format(calendar, new StringBuilder(this.mMaxLengthEstimate))).toString();
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(long j, StringBuffer stringBuffer) {
        Calendar calendarNewCalendar = newCalendar();
        calendarNewCalendar.setTimeInMillis(j);
        return (StringBuffer) applyRules(calendarNewCalendar, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        Calendar calendarNewCalendar = newCalendar();
        calendarNewCalendar.setTime(date);
        return (StringBuffer) applyRules(calendarNewCalendar, stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        return format(calendar.getTime(), stringBuffer);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(long j, B b) {
        Calendar calendarNewCalendar = newCalendar();
        calendarNewCalendar.setTimeInMillis(j);
        return (B) applyRules(calendarNewCalendar, b);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Date date, B b) {
        Calendar calendarNewCalendar = newCalendar();
        calendarNewCalendar.setTime(date);
        return (B) applyRules(calendarNewCalendar, b);
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public <B extends Appendable> B format(Calendar calendar, B b) {
        if (!calendar.getTimeZone().equals(this.mTimeZone)) {
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }
        return (B) applyRules(calendar, b);
    }

    @Deprecated
    protected StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        return (StringBuffer) applyRules(calendar, stringBuffer);
    }

    private <B extends Appendable> B applyRules(Calendar calendar, B b) {
        try {
            for (Rule rule : this.mRules) {
                rule.appendTo(b, calendar);
            }
        } catch (IOException e) {
            ExceptionUtils.rethrow(e);
        }
        return b;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public String getPattern() {
        return this.mPattern;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    @Override // org.apache.commons.lang3.time.DatePrinter
    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDatePrinter)) {
            return false;
        }
        FastDatePrinter fastDatePrinter = (FastDatePrinter) obj;
        return this.mPattern.equals(fastDatePrinter.mPattern) && this.mTimeZone.equals(fastDatePrinter.mTimeZone) && this.mLocale.equals(fastDatePrinter.mLocale);
    }

    public int hashCode() {
        return this.mPattern.hashCode() + ((this.mTimeZone.hashCode() + (this.mLocale.hashCode() * 13)) * 13);
    }

    public String toString() {
        return "FastDatePrinter[" + this.mPattern + "," + this.mLocale + "," + this.mTimeZone.getID() + "]";
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendDigits(Appendable appendable, int i) throws IOException {
        appendable.append((char) ((i / 10) + 48));
        appendable.append((char) ((i % 10) + 48));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendFullDigits(Appendable appendable, int i, int i2) throws IOException {
        if (i < 10000) {
            int i3 = i < 1000 ? i < 100 ? i < 10 ? 1 : 2 : 3 : 4;
            for (int i4 = i2 - i3; i4 > 0; i4--) {
                appendable.append('0');
            }
            if (i3 != 1) {
                if (i3 != 2) {
                    if (i3 != 3) {
                        if (i3 != 4) {
                            return;
                        }
                        appendable.append((char) ((i / 1000) + 48));
                        i %= 1000;
                    }
                    if (i >= 100) {
                        appendable.append((char) ((i / 100) + 48));
                        i %= 100;
                    } else {
                        appendable.append('0');
                    }
                }
                if (i >= 10) {
                    appendable.append((char) ((i / 10) + 48));
                    i %= 10;
                } else {
                    appendable.append('0');
                }
            }
            appendable.append((char) (i + 48));
            return;
        }
        char[] cArr = new char[10];
        int i5 = 0;
        while (i != 0) {
            cArr[i5] = (char) ((i % 10) + 48);
            i /= 10;
            i5++;
        }
        while (i5 < i2) {
            appendable.append('0');
            i2--;
        }
        while (true) {
            i5--;
            if (i5 < 0) {
                return;
            } else {
                appendable.append(cArr[i5]);
            }
        }
    }

    private static class CharacterLiteral implements Rule {
        private final char mValue;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return 1;
        }

        CharacterLiteral(char c) {
            this.mValue = c;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValue);
        }
    }

    private static class StringLiteral implements Rule {
        private final String mValue;

        StringLiteral(String str) {
            this.mValue = str;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mValue.length();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValue);
        }
    }

    private static class TextField implements Rule {
        private final int mField;
        private final String[] mValues;

        TextField(int i, String[] strArr) {
            this.mField = i;
            this.mValues = strArr;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            int length = this.mValues.length;
            int i = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return i;
                }
                int length2 = this.mValues[length].length();
                if (length2 > i) {
                    i = length2;
                }
            }
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendable.append(this.mValues[calendar.get(this.mField)]);
        }
    }

    private static class UnpaddedNumberField implements NumberRule {
        private final int mField;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return 4;
        }

        UnpaddedNumberField(int i) {
            this.mField = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(this.mField));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i) throws IOException {
            if (i < 10) {
                appendable.append((char) (i + 48));
            } else if (i < 100) {
                FastDatePrinter.appendDigits(appendable, i);
            } else {
                FastDatePrinter.appendFullDigits(appendable, i, 1);
            }
        }
    }

    private static class UnpaddedMonthField implements NumberRule {
        static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        UnpaddedMonthField() {
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i) throws IOException {
            if (i >= 10) {
                FastDatePrinter.appendDigits(appendable, i);
            } else {
                appendable.append((char) (i + 48));
            }
        }
    }

    private static class PaddedNumberField implements NumberRule {
        private final int mField;
        private final int mSize;

        PaddedNumberField(int i, int i2) {
            if (i2 < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = i;
            this.mSize = i2;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mSize;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(this.mField));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i) throws IOException {
            FastDatePrinter.appendFullDigits(appendable, i, this.mSize);
        }
    }

    private static class TwoDigitNumberField implements NumberRule {
        private final int mField;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        TwoDigitNumberField(int i) {
            this.mField = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(this.mField));
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i) throws IOException {
            if (i < 100) {
                FastDatePrinter.appendDigits(appendable, i);
            } else {
                FastDatePrinter.appendFullDigits(appendable, i, 2);
            }
        }
    }

    private static class TwoDigitYearField implements NumberRule {
        static final TwoDigitYearField INSTANCE = new TwoDigitYearField();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        TwoDigitYearField() {
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(1) % 100);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i) throws IOException {
            FastDatePrinter.appendDigits(appendable, i);
        }
    }

    private static class TwoDigitMonthField implements NumberRule {
        static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return 2;
        }

        TwoDigitMonthField() {
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            appendTo(appendable, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public final void appendTo(Appendable appendable, int i) throws IOException {
            FastDatePrinter.appendDigits(appendable, i);
        }
    }

    private static class TwelveHourField implements NumberRule {
        private final NumberRule mRule;

        TwelveHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int leastMaximum = calendar.get(10);
            if (leastMaximum == 0) {
                leastMaximum = calendar.getLeastMaximum(10) + 1;
            }
            this.mRule.appendTo(appendable, leastMaximum);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i) throws IOException {
            this.mRule.appendTo(appendable, i);
        }
    }

    private static class TwentyFourHourField implements NumberRule {
        private final NumberRule mRule;

        TwentyFourHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int maximum = calendar.get(11);
            if (maximum == 0) {
                maximum = calendar.getMaximum(11) + 1;
            }
            this.mRule.appendTo(appendable, maximum);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i) throws IOException {
            this.mRule.appendTo(appendable, i);
        }
    }

    private static class DayInWeekField implements NumberRule {
        private final NumberRule mRule;

        DayInWeekField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(7);
            this.mRule.appendTo(appendable, i != 1 ? i - 1 : 7);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i) throws IOException {
            this.mRule.appendTo(appendable, i);
        }
    }

    private static class WeekYear implements NumberRule {
        private final NumberRule mRule;

        WeekYear(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            this.mRule.appendTo(appendable, calendar.getWeekYear());
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.NumberRule
        public void appendTo(Appendable appendable, int i) throws IOException {
            this.mRule.appendTo(appendable, i);
        }
    }

    static String getTimeZoneDisplay(TimeZone timeZone, boolean z, int i, Locale locale) {
        TimeZoneDisplayKey timeZoneDisplayKey = new TimeZoneDisplayKey(timeZone, z, i, locale);
        ConcurrentMap<TimeZoneDisplayKey, String> concurrentMap = cTimeZoneDisplayCache;
        String str = concurrentMap.get(timeZoneDisplayKey);
        if (str != null) {
            return str;
        }
        String displayName = timeZone.getDisplayName(z, i, locale);
        String strPutIfAbsent = concurrentMap.putIfAbsent(timeZoneDisplayKey, displayName);
        return strPutIfAbsent != null ? strPutIfAbsent : displayName;
    }

    private static class TimeZoneNameRule implements Rule {
        private final String mDaylight;
        private final Locale mLocale;
        private final String mStandard;
        private final int mStyle;

        TimeZoneNameRule(TimeZone timeZone, Locale locale, int i) {
            this.mLocale = locale;
            this.mStyle = i;
            this.mStandard = FastDatePrinter.getTimeZoneDisplay(timeZone, false, i, locale);
            this.mDaylight = FastDatePrinter.getTimeZoneDisplay(timeZone, true, i, locale);
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return Math.max(this.mStandard.length(), this.mDaylight.length());
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            TimeZone timeZone = calendar.getTimeZone();
            if (calendar.get(16) != 0) {
                appendable.append(FastDatePrinter.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
            } else {
                appendable.append(FastDatePrinter.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
            }
        }
    }

    private static class TimeZoneNumberRule implements Rule {
        static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
        static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        final boolean mColon;

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return 5;
        }

        TimeZoneNumberRule(boolean z) {
            this.mColon = z;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(15) + calendar.get(16);
            if (i < 0) {
                appendable.append('-');
                i = -i;
            } else {
                appendable.append('+');
            }
            int i2 = i / 3600000;
            FastDatePrinter.appendDigits(appendable, i2);
            if (this.mColon) {
                appendable.append(':');
            }
            FastDatePrinter.appendDigits(appendable, (i / CatagoryEnum.COMMANDBLEOTAFILEINFO) - (i2 * 60));
        }
    }

    private static class Iso8601_Rule implements Rule {
        final int length;
        static final Iso8601_Rule ISO8601_HOURS = new Iso8601_Rule(3);
        static final Iso8601_Rule ISO8601_HOURS_MINUTES = new Iso8601_Rule(5);
        static final Iso8601_Rule ISO8601_HOURS_COLON_MINUTES = new Iso8601_Rule(6);

        static Iso8601_Rule getRule(int i) {
            if (i == 1) {
                return ISO8601_HOURS;
            }
            if (i == 2) {
                return ISO8601_HOURS_MINUTES;
            }
            if (i == 3) {
                return ISO8601_HOURS_COLON_MINUTES;
            }
            throw new IllegalArgumentException("invalid number of X");
        }

        Iso8601_Rule(int i) {
            this.length = i;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public int estimateLength() {
            return this.length;
        }

        @Override // org.apache.commons.lang3.time.FastDatePrinter.Rule
        public void appendTo(Appendable appendable, Calendar calendar) throws IOException {
            int i = calendar.get(15) + calendar.get(16);
            if (i == 0) {
                appendable.append("Z");
                return;
            }
            if (i < 0) {
                appendable.append('-');
                i = -i;
            } else {
                appendable.append('+');
            }
            int i2 = i / 3600000;
            FastDatePrinter.appendDigits(appendable, i2);
            int i3 = this.length;
            if (i3 < 5) {
                return;
            }
            if (i3 == 6) {
                appendable.append(':');
            }
            FastDatePrinter.appendDigits(appendable, (i / CatagoryEnum.COMMANDBLEOTAFILEINFO) - (i2 * 60));
        }
    }

    private static class TimeZoneDisplayKey {
        private final Locale mLocale;
        private final int mStyle;
        private final TimeZone mTimeZone;

        TimeZoneDisplayKey(TimeZone timeZone, boolean z, int i, Locale locale) {
            this.mTimeZone = timeZone;
            if (z) {
                this.mStyle = Integer.MIN_VALUE | i;
            } else {
                this.mStyle = i;
            }
            this.mLocale = locale;
        }

        public int hashCode() {
            return (((this.mStyle * 31) + this.mLocale.hashCode()) * 31) + this.mTimeZone.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TimeZoneDisplayKey)) {
                return false;
            }
            TimeZoneDisplayKey timeZoneDisplayKey = (TimeZoneDisplayKey) obj;
            return this.mTimeZone.equals(timeZoneDisplayKey.mTimeZone) && this.mStyle == timeZoneDisplayKey.mStyle && this.mLocale.equals(timeZoneDisplayKey.mLocale);
        }
    }
}
