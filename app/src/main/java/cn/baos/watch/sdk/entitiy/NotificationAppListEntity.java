package cn.baos.watch.sdk.entitiy;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class NotificationAppListEntity implements Serializable {
    private String appName;
    private String appPackageName;
    private int crudState;
    private int id;
    private boolean isChecked;
    public String mac;
    private int position;
    private boolean isSynchronizeNetwork = false;
    private boolean isEnabled = true;

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean z) {
        this.isEnabled = z;
    }

    public NotificationAppListEntity() {
    }

    public NotificationAppListEntity(String str) {
        this.appName = str;
    }

    public NotificationAppListEntity(String str, String str2) {
        this.appPackageName = str;
        this.appName = str2;
    }

    public NotificationAppListEntity(String str, String str2, boolean z) {
        this.appPackageName = str;
        this.appName = str2;
        this.isChecked = z;
    }

    public int getCrudState() {
        return this.crudState;
    }

    public void setCrudState(int i) {
        this.crudState = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String getAppPackageName() {
        return this.appPackageName;
    }

    public void setAppPackageName(String str) {
        this.appPackageName = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public NotificationAppListEntity setAppName(String str) {
        this.appName = str;
        return this;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public boolean isSynchronizeNetwork() {
        return this.isSynchronizeNetwork;
    }

    public void setSynchronizeNetwork(boolean z) {
        this.isSynchronizeNetwork = z;
    }

    public String toString() {
        return "NotificationAppListEntity{crudState=" + this.crudState + ", id=" + this.id + ", position=" + this.position + ", appPackageName='" + this.appPackageName + "', appName='" + this.appName + "', isChecked=" + this.isChecked + ", isSynchronizeNetwork=" + this.isSynchronizeNetwork + ", isEnabled=" + this.isEnabled + '}';
    }
}
