package cn.baos.watch.sdk.entitiy;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WeatherEntity {
    private String code;
    private DataBean data;
    private String msg;

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public static class DataBean {
        private List<FutureWeatherBean> futureWeather;
        private TodayWeatherBean todayWeather;

        public TodayWeatherBean getTodayWeather() {
            return this.todayWeather;
        }

        public void setTodayWeather(TodayWeatherBean todayWeatherBean) {
            this.todayWeather = todayWeatherBean;
        }

        public List<FutureWeatherBean> getFutureWeather() {
            return this.futureWeather;
        }

        public void setFutureWeather(List<FutureWeatherBean> list) {
            this.futureWeather = list;
        }

        public static class TodayWeatherBean {
            private String area;
            private int currentTemperature;
            private int maxTemperature;
            private int minTemperature;
            public int pressure;
            public int uvIndex;
            private int weatherType;

            public int getCurrentTemperature() {
                return this.currentTemperature;
            }

            public void setCurrentTemperature(int i) {
                this.currentTemperature = i;
            }

            public String getArea() {
                return this.area;
            }

            public void setArea(String str) {
                this.area = str;
            }

            public int getWeatherType() {
                return this.weatherType;
            }

            public void setWeatherType(int i) {
                this.weatherType = i;
            }

            public int getMinTemperature() {
                return this.minTemperature;
            }

            public void setMinTemperature(int i) {
                this.minTemperature = i;
            }

            public int getMaxTemperature() {
                return this.maxTemperature;
            }

            public void setMaxTemperature(int i) {
                this.maxTemperature = i;
            }

            public String toString() {
                return "TodayWeatherBean{currentTemperature='" + this.currentTemperature + "', area='" + this.area + "', weatherType='" + this.weatherType + "', minTemperature='" + this.minTemperature + "', maxTemperature='" + this.maxTemperature + "'}";
            }
        }

        public static class FutureWeatherBean {
            private String dateTime;
            private int maxTemperature;
            private int minTemperature;
            private int weatherType;

            public int getWeatherType() {
                return this.weatherType;
            }

            public void setWeatherType(int i) {
                this.weatherType = i;
            }

            public String getDateTime() {
                return this.dateTime;
            }

            public void setDateTime(String str) {
                this.dateTime = str;
            }

            public int getMinTemperature() {
                return this.minTemperature;
            }

            public void setMinTemperature(int i) {
                this.minTemperature = i;
            }

            public int getMaxTemperature() {
                return this.maxTemperature;
            }

            public void setMaxTemperature(int i) {
                this.maxTemperature = i;
            }

            public String toString() {
                return "FutureWeatherBean{weatherType='" + this.weatherType + "', dateTime='" + this.dateTime + "', minTemperature='" + this.minTemperature + "', maxTemperature='" + this.maxTemperature + "'}";
            }
        }

        public String toString() {
            return "DataBean{todayWeather=" + this.todayWeather + ", futureWeather=" + this.futureWeather + '}';
        }
    }

    public String toString() {
        return "WeatherReturnEntity{code='" + this.code + "', msg='" + this.msg + "', data=" + this.data + '}';
    }
}
