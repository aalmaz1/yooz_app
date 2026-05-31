package cn.baos.watch.sdk.database.fromwatch.sensordatasportmode;

import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordDetailEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseSportModeHandler {
    void close();

    void createDatabase();

    void delete(SportModeEntity sportModeEntity);

    void insert(SportModeEntity sportModeEntity);

    void insertToPhone(SportPhoneRecordDetailEntity sportPhoneRecordDetailEntity);

    void open();

    SportModeEntity query(int i);

    ArrayList<SportModeEntity> queryArrayBetween(int i, int i2);

    ArrayList<SportPhoneRecordDetailEntity> queryArrayBetweenPhone(int i, int i2);

    void update(SportModeEntity sportModeEntity);
}
