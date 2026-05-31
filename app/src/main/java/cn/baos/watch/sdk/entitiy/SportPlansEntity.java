package cn.baos.watch.sdk.entitiy;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SportPlansEntity {
    public int enable;
    public List<Plans> plans;
    public int reserve1;
    public int reserve2;

    public class Plans {
        public int dayOfWeek;
        public int mode;
        public int reachValue;
        public int reserve;
        public int targetType;
        public int targetValue;

        public Plans() {
        }
    }
}
