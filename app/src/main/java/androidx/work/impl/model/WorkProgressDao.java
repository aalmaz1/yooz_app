package androidx.work.impl.model;

import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import com.tekartik.sqflite.Constant;
import kotlin.Metadata;

/* JADX INFO: compiled from: WorkProgressDao.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H'J\b\u0010\u0006\u001a\u00020\u0003H'J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0004\u001a\u00020\u0005H'J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH'¨\u0006\f"}, d2 = {"Landroidx/work/impl/model/WorkProgressDao;", "", "delete", "", "workSpecId", "", "deleteAll", "getProgressForWorkSpecId", "Landroidx/work/Data;", Constant.METHOD_INSERT, NotificationCompat.CATEGORY_PROGRESS, "Landroidx/work/impl/model/WorkProgress;", "work-runtime_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
public interface WorkProgressDao {
    void delete(String workSpecId);

    void deleteAll();

    Data getProgressForWorkSpecId(String workSpecId);

    void insert(WorkProgress progress);
}
