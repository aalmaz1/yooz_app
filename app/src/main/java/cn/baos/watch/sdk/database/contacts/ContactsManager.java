package cn.baos.watch.sdk.database.contacts;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class ContactsManager extends SyncDataBaseManager implements ContactsInterface {
    private static ContactsManager instance;
    private Context mContext;
    private DatabaseContactsHandler mDatabaseHandler;

    public static ContactsManager getInstance() {
        if (instance == null) {
            synchronized (ContactsManager.class) {
                if (instance == null) {
                    instance = new ContactsManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseContactsHandler databaseContactsHandler = new DatabaseContactsHandler(context);
        this.mDatabaseHandler = databaseContactsHandler;
        databaseContactsHandler.createDatabase();
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public DataBaseFartherHandler getDatabaseHandler() {
        return this.mDatabaseHandler;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public void open() {
        this.mDatabaseHandler.open();
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public void close() {
        this.mDatabaseHandler.close();
    }

    @Override // cn.baos.watch.sdk.database.contacts.ContactsInterface
    public void dlt() {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            this.mDatabaseHandler.deleteAll();
            close();
        }
    }

    @Override // cn.baos.watch.sdk.database.contacts.ContactsInterface
    public void saveContactsToDb(ArrayList<PhoneContactsEntity> arrayList) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            for (int i = 0; i < arrayList.size(); i++) {
                this.mDatabaseHandler.insert(arrayList.get(i));
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.database.contacts.ContactsInterface
    public ArrayList<PhoneContactsEntity> queryList() {
        ArrayList<PhoneContactsEntity> arrayListQueryArrayAll;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayAll = this.mDatabaseHandler.queryArrayAll();
            Collections.reverse(arrayListQueryArrayAll);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayAll));
            close();
        }
        return arrayListQueryArrayAll;
    }
}
