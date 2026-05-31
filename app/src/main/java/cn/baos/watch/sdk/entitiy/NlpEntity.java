package cn.baos.watch.sdk.entitiy;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class NlpEntity implements Serializable {
    private int actionId;
    private CircleModel circleModel;
    private String event;
    private String reminder;
    private String textShow;
    private String type;
    private int value;

    public int getActionId() {
        return this.actionId;
    }

    public void setActionId(int i) {
        this.actionId = i;
    }

    public String getTextShow() {
        return this.textShow;
    }

    public void setTextShow(String str) {
        this.textShow = str;
    }

    public String getReminder() {
        return this.reminder;
    }

    public void setReminder(String str) {
        this.reminder = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int i) {
        this.value = i;
    }

    public CircleModel getCircleModel() {
        return this.circleModel;
    }

    public void setCircleModel(CircleModel circleModel) {
        this.circleModel = circleModel;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String str) {
        this.event = str;
    }

    public static class CircleModel {
        private int circleExtra;
        private int circleType;
        private int dayOfMouth;
        private int dayOfWeek;
        private int mouthOfYear;
        private int weekOfMouth;

        public int getCircleType() {
            return this.circleType;
        }

        public void setCircleType(int i) {
            this.circleType = i;
        }

        public int getCircleExtra() {
            return this.circleExtra;
        }

        public void setCircleExtra(int i) {
            this.circleExtra = i;
        }

        public int getDayOfWeek() {
            return this.dayOfWeek;
        }

        public void setDayOfWeek(int i) {
            this.dayOfWeek = i;
        }

        public int getDayOfMouth() {
            return this.dayOfMouth;
        }

        public void setDayOfMouth(int i) {
            this.dayOfMouth = i;
        }

        public int getWeekOfMouth() {
            return this.weekOfMouth;
        }

        public void setWeekOfMouth(int i) {
            this.weekOfMouth = i;
        }

        public int getMouthOfYear() {
            return this.mouthOfYear;
        }

        public void setMouthOfYear(int i) {
            this.mouthOfYear = i;
        }

        public String toString() {
            return "CircleModel{circleType=" + this.circleType + ", circleExtra=" + this.circleExtra + ", dayOfWeek=" + this.dayOfWeek + ", dayOfMouth=" + this.dayOfMouth + ", weekOfMouth=" + this.weekOfMouth + ", mouthOfYear=" + this.mouthOfYear + '}';
        }
    }

    public String toString() {
        return "NlpEntity{textShow='" + this.textShow + "', reminder='" + this.reminder + "', event='" + this.event + "', type='" + this.type + "', value=" + this.value + ", actionId=" + this.actionId + ", circleModel=" + this.circleModel + '}';
    }
}
