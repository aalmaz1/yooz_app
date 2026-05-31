package com.fluttercandies.photo_manager.core.entity.filter;

import java.util.ArrayList;
import kotlin.Metadata;

/* JADX INFO: compiled from: FilterOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J2\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0016\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\b0\fj\b\u0012\u0004\u0012\u00020\b`\r2\b\b\u0002\u0010\u000e\u001a\u00020\u0004H&J\n\u0010\u000f\u001a\u0004\u0018\u00010\bH&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", "", "()V", "containsPathModified", "", "getContainsPathModified", "()Z", "makeWhere", "", "requestType", "", "args", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "needAnd", "orderByCondString", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FilterOption {
    public abstract boolean getContainsPathModified();

    public abstract String makeWhere(int requestType, ArrayList<String> args, boolean needAnd);

    public abstract String orderByCondString();

    public static /* synthetic */ String makeWhere$default(FilterOption filterOption, int i, ArrayList arrayList, boolean z, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: makeWhere");
        }
        if ((i2 & 4) != 0) {
            z = true;
        }
        return filterOption.makeWhere(i, arrayList, z);
    }
}
