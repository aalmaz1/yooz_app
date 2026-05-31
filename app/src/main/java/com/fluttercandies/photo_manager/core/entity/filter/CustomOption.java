package com.fluttercandies.photo_manager.core.entity.filter;

import com.fluttercandies.photo_manager.core.utils.RequestTypeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: CustomOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J0\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\n0\u000ej\b\u0012\u0004\u0012\u00020\n`\u000f2\u0006\u0010\u0010\u001a\u00020\u0006H\u0016J\n\u0010\u0011\u001a\u0004\u0018\u00010\nH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/CustomOption;", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", "map", "", "(Ljava/util/Map;)V", "containsPathModified", "", "getContainsPathModified", "()Z", "makeWhere", "", "requestType", "", "args", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "needAnd", "orderByCondString", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CustomOption extends FilterOption {
    private final boolean containsPathModified;
    private final Map<?, ?> map;

    public CustomOption(Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
        Object obj = map.get("containsPathModified");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
        this.containsPathModified = ((Boolean) obj).booleanValue();
    }

    @Override // com.fluttercandies.photo_manager.core.entity.filter.FilterOption
    public boolean getContainsPathModified() {
        return this.containsPathModified;
    }

    @Override // com.fluttercandies.photo_manager.core.entity.filter.FilterOption
    public String orderByCondString() {
        Object obj = this.map.get("orderBy");
        List list = obj instanceof List ? (List) obj : null;
        List list2 = list;
        if (list2 == null || list2.isEmpty()) {
            return null;
        }
        return CollectionsKt.joinToString$default(list, ",", null, null, 0, null, new Function1<Object, CharSequence>() { // from class: com.fluttercandies.photo_manager.core.entity.filter.CustomOption.orderByCondString.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Object obj2) {
                Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
                Map map = (Map) obj2;
                Object obj3 = map.get("column");
                Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
                String str = (String) obj3;
                Object obj4 = map.get("isAsc");
                Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Boolean");
                return str + StringUtils.SPACE + (((Boolean) obj4).booleanValue() ? "ASC" : "DESC");
            }
        }, 30, null);
    }

    @Override // com.fluttercandies.photo_manager.core.entity.filter.FilterOption
    public String makeWhere(int requestType, ArrayList<String> args, boolean needAnd) {
        Intrinsics.checkNotNullParameter(args, "args");
        Object obj = this.map.get("where");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
        String str = (String) obj;
        String where = RequestTypeUtils.INSTANCE.toWhere(requestType);
        String str2 = str;
        if (StringsKt.trim((CharSequence) str2).toString().length() == 0) {
            return needAnd ? "AND " + where : where;
        }
        if (needAnd) {
            if (StringsKt.trim((CharSequence) str2).toString().length() > 0) {
                return "AND ( " + str + " )";
            }
        }
        return "( " + str + " )";
    }
}
