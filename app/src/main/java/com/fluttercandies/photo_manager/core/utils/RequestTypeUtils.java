package com.fluttercandies.photo_manager.core.utils;

import cn.yoozworld.watch.utils.notifi.NotificationManager;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: RequestTypeUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\u0002J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/RequestTypeUtils;", "", "()V", "typeAudio", "", "typeImage", "typeVideo", "checkType", "", NotificationManager.BUNDLE_TYPE, "targetType", "containsAudio", "containsImage", "containsVideo", "toWhere", "", "requestType", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RequestTypeUtils {
    public static final RequestTypeUtils INSTANCE = new RequestTypeUtils();
    private static final int typeAudio = 4;
    private static final int typeImage = 1;
    private static final int typeVideo = 2;

    private final boolean checkType(int type, int targetType) {
        return (type & targetType) == targetType;
    }

    private RequestTypeUtils() {
    }

    public final boolean containsImage(int type) {
        return checkType(type, 1);
    }

    public final boolean containsVideo(int type) {
        return checkType(type, 2);
    }

    public final boolean containsAudio(int type) {
        return checkType(type, 4);
    }

    public final String toWhere(int requestType) {
        ArrayList arrayList = new ArrayList();
        if (containsImage(requestType)) {
            arrayList.add(1);
        }
        if (containsAudio(requestType)) {
            arrayList.add(2);
        }
        if (containsVideo(requestType)) {
            arrayList.add(3);
        }
        return "( " + CollectionsKt.joinToString$default(arrayList, " OR ", null, null, 0, null, new Function1<Integer, CharSequence>() { // from class: com.fluttercandies.photo_manager.core.utils.RequestTypeUtils$toWhere$where$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final CharSequence invoke(int i) {
                return "media_type = " + i;
            }
        }, 30, null) + " )";
    }
}
