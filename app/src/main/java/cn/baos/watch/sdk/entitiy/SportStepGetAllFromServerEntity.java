package cn.baos.watch.sdk.entitiy;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SportStepGetAllFromServerEntity {
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
        private List<StepVOListBean> stepVOList;
        private String userId;

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public List<StepVOListBean> getStepVOList() {
            return this.stepVOList;
        }

        public void setStepVOList(List<StepVOListBean> list) {
            this.stepVOList = list;
        }

        public static class StepVOListBean {
            private String stepNumber;
            private String syncDate;

            public String getSyncDate() {
                return this.syncDate;
            }

            public void setSyncDate(String str) {
                this.syncDate = str;
            }

            public String getStepNumber() {
                return this.stepNumber;
            }

            public void setStepNumber(String str) {
                this.stepNumber = str;
            }

            public String toString() {
                return "StepVOListBean{syncDate='" + this.syncDate + "', stepNumber='" + this.stepNumber + "'}";
            }
        }

        public String toString() {
            return "DataBean{userId='" + this.userId + "', stepVOList=" + this.stepVOList + '}';
        }
    }

    public String toString() {
        return "SportStepGetAllFromServerEntity{code='" + this.code + "', msg='" + this.msg + "', data=" + this.data + '}';
    }
}
