package com.fluttercandies.photo_manager.core.utils;

import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.AssetType;
import com.fluttercandies.photo_manager.core.PhotoManager;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.entity.AssetPathEntity;
import com.fluttercandies.photo_manager.core.entity.filter.CommonFilterOption;
import com.fluttercandies.photo_manager.core.entity.filter.CustomOption;
import com.fluttercandies.photo_manager.core.entity.filter.DateCond;
import com.fluttercandies.photo_manager.core.entity.filter.FilterCond;
import com.fluttercandies.photo_manager.core.entity.filter.FilterOption;
import com.fluttercandies.photo_manager.core.entity.filter.OrderByCond;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConvertUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\"\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\nJ \u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\f0\nJ\u0016\u0010\r\u001a\u00020\u000e2\u000e\u0010\u000f\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004J\u0016\u0010\u0010\u001a\u00020\u00112\u000e\u0010\u000f\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004J\u0018\u0010\u0012\u001a\u00020\u00132\u000e\u0010\u000f\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004H\u0002J\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\n2\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\nJ\u001e\u0010\u0017\u001a\u00020\u00132\u000e\u0010\u000f\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00042\u0006\u0010\u0018\u001a\u00020\u0019¨\u0006\u001a"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/ConvertUtils;", "", "()V", "convertAsset", "", "", "entity", "Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "convertAssets", "list", "", "convertPaths", "Lcom/fluttercandies/photo_manager/core/entity/AssetPathEntity;", "convertToDateCond", "Lcom/fluttercandies/photo_manager/core/entity/filter/DateCond;", "map", "convertToFilterOptions", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", "convertToOption", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond;", "convertToOrderByConds", "Lcom/fluttercandies/photo_manager/core/entity/filter/OrderByCond;", "orders", "getOptionFromType", NotificationManager.BUNDLE_TYPE, "Lcom/fluttercandies/photo_manager/constant/AssetType;", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConvertUtils {
    public static final ConvertUtils INSTANCE = new ConvertUtils();

    private ConvertUtils() {
    }

    public final Map<String, Object> convertPaths(List<AssetPathEntity> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        ArrayList arrayList = new ArrayList();
        for (AssetPathEntity assetPathEntity : list) {
            if (assetPathEntity.getAssetCount() != 0) {
                Map mapMutableMapOf = MapsKt.mutableMapOf(TuplesKt.to("id", assetPathEntity.getId()), TuplesKt.to("name", assetPathEntity.getName()), TuplesKt.to("assetCount", Integer.valueOf(assetPathEntity.getAssetCount())), TuplesKt.to(PhotoManager.ALL_ID, Boolean.valueOf(assetPathEntity.isAll())));
                if (assetPathEntity.getModifiedDate() != null) {
                    Long modifiedDate = assetPathEntity.getModifiedDate();
                    Intrinsics.checkNotNull(modifiedDate);
                    mapMutableMapOf.put("modified", modifiedDate);
                }
                arrayList.add(mapMutableMapOf);
            }
        }
        return MapsKt.mapOf(TuplesKt.to("data", arrayList));
    }

    public final Map<String, Object> convertAssets(List<AssetEntity> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        ArrayList arrayList = new ArrayList();
        Iterator<AssetEntity> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(convertAsset(it.next()));
        }
        return MapsKt.mapOf(TuplesKt.to("data", arrayList));
    }

    public final Map<String, Object> convertAsset(AssetEntity entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        HashMap mapHashMapOf = MapsKt.hashMapOf(TuplesKt.to("id", String.valueOf(entity.getId())), TuplesKt.to("duration", Long.valueOf(entity.getDuration() / ((long) 1000))), TuplesKt.to(NotificationManager.BUNDLE_TYPE, Integer.valueOf(entity.getType())), TuplesKt.to("createDt", Long.valueOf(entity.getCreateDt())), TuplesKt.to("width", Integer.valueOf(entity.getWidth())), TuplesKt.to("height", Integer.valueOf(entity.getHeight())), TuplesKt.to("orientation", Integer.valueOf(entity.getOrientation())), TuplesKt.to("modifiedDt", Long.valueOf(entity.getModifiedDate())), TuplesKt.to("lat", entity.getLat()), TuplesKt.to("lng", entity.getLng()), TuplesKt.to("title", entity.getDisplayName()), TuplesKt.to("relativePath", entity.getRelativePath()));
        if (entity.getMimeType() != null) {
            mapHashMapOf.put("mimeType", entity.getMimeType());
        }
        return mapHashMapOf;
    }

    public final FilterCond getOptionFromType(Map<?, ?> map, AssetType type) {
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(type, "type");
        String lowerCase = type.name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        if (map.containsKey(lowerCase)) {
            Object obj = map.get(lowerCase);
            if (obj instanceof Map) {
                return convertToOption((Map) obj);
            }
        }
        return new FilterCond();
    }

    private final FilterCond convertToOption(Map<?, ?> map) {
        FilterCond filterCond = new FilterCond();
        Object obj = map.get("title");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Boolean");
        filterCond.setShowTitle(((Boolean) obj).booleanValue());
        Object obj2 = map.get("size");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        Map map2 = (Map) obj2;
        FilterCond.SizeConstraint sizeConstraint = new FilterCond.SizeConstraint();
        Object obj3 = map2.get("minWidth");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
        sizeConstraint.setMinWidth(((Integer) obj3).intValue());
        Object obj4 = map2.get("maxWidth");
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Int");
        sizeConstraint.setMaxWidth(((Integer) obj4).intValue());
        Object obj5 = map2.get("minHeight");
        Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Int");
        sizeConstraint.setMinHeight(((Integer) obj5).intValue());
        Object obj6 = map2.get("maxHeight");
        Intrinsics.checkNotNull(obj6, "null cannot be cast to non-null type kotlin.Int");
        sizeConstraint.setMaxHeight(((Integer) obj6).intValue());
        Object obj7 = map2.get("ignoreSize");
        Intrinsics.checkNotNull(obj7, "null cannot be cast to non-null type kotlin.Boolean");
        sizeConstraint.setIgnoreSize(((Boolean) obj7).booleanValue());
        filterCond.setSizeConstraint(sizeConstraint);
        Object obj8 = map.get("duration");
        Intrinsics.checkNotNull(obj8, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        Map map3 = (Map) obj8;
        FilterCond.DurationConstraint durationConstraint = new FilterCond.DurationConstraint();
        Intrinsics.checkNotNull(map3.get("min"), "null cannot be cast to non-null type kotlin.Int");
        durationConstraint.setMin(((Integer) r3).intValue());
        Intrinsics.checkNotNull(map3.get("max"), "null cannot be cast to non-null type kotlin.Int");
        durationConstraint.setMax(((Integer) r3).intValue());
        Object obj9 = map3.get("allowNullable");
        Intrinsics.checkNotNull(obj9, "null cannot be cast to non-null type kotlin.Boolean");
        durationConstraint.setAllowNullable(((Boolean) obj9).booleanValue());
        filterCond.setDurationConstraint(durationConstraint);
        return filterCond;
    }

    public final DateCond convertToDateCond(Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return new DateCond(Long.parseLong(String.valueOf(map.get("min"))), Long.parseLong(String.valueOf(map.get("max"))), Boolean.parseBoolean(String.valueOf(map.get("ignore"))));
    }

    public final FilterOption convertToFilterOptions(Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        Object obj = map.get(NotificationManager.BUNDLE_TYPE);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        int iIntValue = ((Integer) obj).intValue();
        Object obj2 = map.get("child");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        Map map2 = (Map) obj2;
        if (iIntValue == 0) {
            return new CommonFilterOption(map2);
        }
        if (iIntValue == 1) {
            return new CustomOption(map2);
        }
        throw new IllegalStateException("Unknown type " + iIntValue + " for filter option.");
    }

    public final List<OrderByCond> convertToOrderByConds(List<?> orders) {
        Intrinsics.checkNotNullParameter(orders, "orders");
        ArrayList arrayList = new ArrayList();
        if (orders.isEmpty()) {
            return CollectionsKt.arrayListOf(new OrderByCond("_id", false));
        }
        for (Object obj : orders) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
            Map map = (Map) obj;
            Object obj2 = map.get(NotificationManager.BUNDLE_TYPE);
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) obj2).intValue();
            Object obj3 = map.get("asc");
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Boolean");
            boolean zBooleanValue = ((Boolean) obj3).booleanValue();
            String str = iIntValue != 0 ? iIntValue != 1 ? null : "date_modified" : "date_added";
            if (str != null) {
                arrayList.add(new OrderByCond(str, zBooleanValue));
            }
        }
        return arrayList;
    }
}
