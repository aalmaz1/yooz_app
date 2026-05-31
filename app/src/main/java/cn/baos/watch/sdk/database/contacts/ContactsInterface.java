package cn.baos.watch.sdk.database.contacts;

import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface ContactsInterface {
    void dlt();

    ArrayList<PhoneContactsEntity> queryList();

    void saveContactsToDb(ArrayList<PhoneContactsEntity> arrayList);
}
