package cn.baos.watch.sdk.database.alldata;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseAllHandler {
    void close();

    void createDatabase();

    void delete(String str);

    ArrayList<String> getAlEntities();

    boolean hasSportRecordEntity(String str);

    void insert(String str);

    void open();

    String query(long j);

    ArrayList<String> queryArrayBetween(long j, long j2);

    void update(String str);
}
