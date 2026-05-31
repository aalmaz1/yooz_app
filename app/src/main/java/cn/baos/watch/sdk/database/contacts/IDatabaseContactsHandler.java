package cn.baos.watch.sdk.database.contacts;

import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseContactsHandler {
    void close();

    void createDatabase();

    void delete(PhoneContactsEntity phoneContactsEntity);

    void deleteAll();

    void insert(PhoneContactsEntity phoneContactsEntity);

    void open();

    PhoneContactsEntity query(int i);

    ArrayList<PhoneContactsEntity> queryArrayAll();

    ArrayList<PhoneContactsEntity> queryArrayBetween(int i, int i2);

    void update(PhoneContactsEntity phoneContactsEntity);
}
