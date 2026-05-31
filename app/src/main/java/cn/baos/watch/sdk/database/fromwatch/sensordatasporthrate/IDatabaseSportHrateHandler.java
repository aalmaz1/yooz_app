package cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseSportHrateHandler {
    void close();

    void createDatabase();

    void delete(SportHrateEntity sportHrateEntity);

    void insert(SportHrateEntity sportHrateEntity);

    void open();

    SportHrateEntity query(int i);

    ArrayList<SportHrateEntity> queryArrayBetween(int i, int i2);

    void update(SportHrateEntity sportHrateEntity);
}
