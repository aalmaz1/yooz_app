package com.fluttercandies.photo_manager.core.entity.filter;

import androidx.exifinterface.media.ExifInterface;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.AssetType;
import com.fluttercandies.photo_manager.core.utils.ConvertUtils;
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

/* JADX INFO: compiled from: CommonFilterOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J0\u0010\u0017\u001a\u00020\u00182\u0016\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u001aj\b\u0012\u0004\u0012\u00020\u0018`\u001b2\u0006\u0010\u001c\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u0018H\u0002J0\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u00002\u0016\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u001aj\b\u0012\u0004\u0012\u00020\u0018`\u001bH\u0002J(\u0010\"\u001a\u00020\u00182\u0016\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u001aj\b\u0012\u0004\u0012\u00020\u0018`\u001b2\u0006\u0010#\u001a\u00020\u0000H\u0002J0\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020 2\u0016\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u001aj\b\u0012\u0004\u0012\u00020\u0018`\u001b2\u0006\u0010&\u001a\u00020\bH\u0016J\n\u0010'\u001a\u0004\u0018\u00010\u0018H\u0016J\u001f\u0010(\u001a\u00020\u00182\b\u0010%\u001a\u0004\u0018\u00010 2\u0006\u0010#\u001a\u00020\u0000H\u0002¢\u0006\u0002\u0010)R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/CommonFilterOption;", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", "map", "", "(Ljava/util/Map;)V", "audioOption", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond;", "containsPathModified", "", "getContainsPathModified", "()Z", "createDateCond", "Lcom/fluttercandies/photo_manager/core/entity/filter/DateCond;", "imageOption", "orderByCond", "", "Lcom/fluttercandies/photo_manager/core/entity/filter/OrderByCond;", "typeUtils", "Lcom/fluttercandies/photo_manager/core/utils/RequestTypeUtils;", "getTypeUtils", "()Lcom/fluttercandies/photo_manager/core/utils/RequestTypeUtils;", "updateDateCond", "videoOption", "addDateCond", "", "args", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "dateCond", "dbKey", "getCondFromType", NotificationManager.BUNDLE_TYPE, "", "filterOption", "getDateCond", "option", "makeWhere", "requestType", "needAnd", "orderByCondString", "sizeWhere", "(Ljava/lang/Integer;Lcom/fluttercandies/photo_manager/core/entity/filter/CommonFilterOption;)Ljava/lang/String;", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonFilterOption extends FilterOption {
    private final FilterCond audioOption;
    private final boolean containsPathModified;
    private final DateCond createDateCond;
    private final FilterCond imageOption;
    private final List<OrderByCond> orderByCond;
    private final DateCond updateDateCond;
    private final FilterCond videoOption;

    public CommonFilterOption(Map<?, ?> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.videoOption = ConvertUtils.INSTANCE.getOptionFromType(map, AssetType.Video);
        this.imageOption = ConvertUtils.INSTANCE.getOptionFromType(map, AssetType.Image);
        this.audioOption = ConvertUtils.INSTANCE.getOptionFromType(map, AssetType.Audio);
        ConvertUtils convertUtils = ConvertUtils.INSTANCE;
        Object obj = map.get("createDate");
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        this.createDateCond = convertUtils.convertToDateCond((Map) obj);
        ConvertUtils convertUtils2 = ConvertUtils.INSTANCE;
        Object obj2 = map.get("updateDate");
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.Map<*, *>");
        this.updateDateCond = convertUtils2.convertToDateCond((Map) obj2);
        Object obj3 = map.get("containsPathModified");
        Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Boolean");
        this.containsPathModified = ((Boolean) obj3).booleanValue();
        ConvertUtils convertUtils3 = ConvertUtils.INSTANCE;
        Object obj4 = map.get("orders");
        Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.collections.List<*>");
        this.orderByCond = convertUtils3.convertToOrderByConds((List) obj4);
    }

    @Override // com.fluttercandies.photo_manager.core.entity.filter.FilterOption
    public boolean getContainsPathModified() {
        return this.containsPathModified;
    }

    @Override // com.fluttercandies.photo_manager.core.entity.filter.FilterOption
    public String makeWhere(int requestType, ArrayList<String> args, boolean needAnd) {
        Intrinsics.checkNotNullParameter(args, "args");
        String str = getCondFromType(requestType, this, args) + StringUtils.SPACE + getDateCond(args, this) + StringUtils.SPACE + sizeWhere(Integer.valueOf(requestType), this);
        if (StringsKt.trim((CharSequence) str).toString().length() == 0) {
            return "";
        }
        if (needAnd) {
            return " AND ( " + str + " )";
        }
        return " ( " + str + " ) ";
    }

    @Override // com.fluttercandies.photo_manager.core.entity.filter.FilterOption
    public String orderByCondString() {
        if (this.orderByCond.isEmpty()) {
            return null;
        }
        return CollectionsKt.joinToString$default(this.orderByCond, ",", null, null, 0, null, new Function1<OrderByCond, CharSequence>() { // from class: com.fluttercandies.photo_manager.core.entity.filter.CommonFilterOption.orderByCondString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(OrderByCond it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getOrder();
            }
        }, 30, null);
    }

    private final RequestTypeUtils getTypeUtils() {
        return RequestTypeUtils.INSTANCE;
    }

    private final String sizeWhere(Integer requestType, CommonFilterOption option) {
        if (option.imageOption.getSizeConstraint().getIgnoreSize() || requestType == null || !getTypeUtils().containsImage(requestType.intValue())) {
            return "";
        }
        String str = getTypeUtils().containsVideo(requestType.intValue()) ? "OR ( media_type = 3 )" : "";
        if (getTypeUtils().containsAudio(requestType.intValue())) {
            str = str + " OR ( media_type = 2 )";
        }
        return "AND (( media_type = 1 AND width > 0 AND height > 0 ) " + str + ")";
    }

    private final String getCondFromType(int type, CommonFilterOption filterOption, ArrayList<String> args) {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder();
        boolean zContainsImage = RequestTypeUtils.INSTANCE.containsImage(type);
        boolean zContainsVideo = RequestTypeUtils.INSTANCE.containsVideo(type);
        boolean zContainsAudio = RequestTypeUtils.INSTANCE.containsAudio(type);
        String str3 = "";
        if (zContainsImage) {
            FilterCond filterCond = filterOption.imageOption;
            args.add("1");
            if (filterCond.getSizeConstraint().getIgnoreSize()) {
                str = "media_type = ? ";
            } else {
                String strSizeCond = filterCond.sizeCond();
                str = "media_type = ?  AND " + strSizeCond;
                CollectionsKt.addAll(args, filterCond.sizeArgs());
            }
        } else {
            str = "";
        }
        if (zContainsVideo) {
            FilterCond filterCond2 = filterOption.videoOption;
            String strDurationCond = filterCond2.durationCond();
            String[] strArrDurationArgs = filterCond2.durationArgs();
            str2 = "media_type = ? AND " + strDurationCond;
            args.add(ExifInterface.GPS_MEASUREMENT_3D);
            CollectionsKt.addAll(args, strArrDurationArgs);
        } else {
            str2 = "";
        }
        if (zContainsAudio) {
            FilterCond filterCond3 = filterOption.audioOption;
            String strDurationCond2 = filterCond3.durationCond();
            String[] strArrDurationArgs2 = filterCond3.durationArgs();
            str3 = "media_type = ? AND " + strDurationCond2;
            args.add(ExifInterface.GPS_MEASUREMENT_2D);
            CollectionsKt.addAll(args, strArrDurationArgs2);
        }
        if (zContainsImage) {
            sb.append("( " + str + " )");
        }
        if (zContainsVideo) {
            if (sb.length() > 0) {
                sb.append("OR ");
            }
            sb.append("( " + str2 + " )");
        }
        if (zContainsAudio) {
            if (sb.length() > 0) {
                sb.append("OR ");
            }
            sb.append("( " + str3 + " )");
        }
        return "( " + ((Object) sb) + " )";
    }

    private final String getDateCond(ArrayList<String> args, CommonFilterOption option) {
        return addDateCond(args, option.createDateCond, "date_added") + StringUtils.SPACE + addDateCond(args, option.updateDateCond, "date_modified");
    }

    private final String addDateCond(ArrayList<String> args, DateCond dateCond, String dbKey) {
        if (dateCond.getIgnore()) {
            return "";
        }
        long minMs = dateCond.getMinMs();
        long maxMs = dateCond.getMaxMs();
        String str = "AND ( " + dbKey + " >= ? AND " + dbKey + " <= ? )";
        long j = 1000;
        args.add(String.valueOf(minMs / j));
        args.add(String.valueOf(maxMs / j));
        return str;
    }
}
