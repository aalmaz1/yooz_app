package com.fluttercandies.photo_manager.constant;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: AssetType.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lcom/fluttercandies/photo_manager/constant/AssetType;", "", "(Ljava/lang/String;I)V", "Image", "Video", "Audio", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AssetType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ AssetType[] $VALUES;
    public static final AssetType Image = new AssetType("Image", 0);
    public static final AssetType Video = new AssetType("Video", 1);
    public static final AssetType Audio = new AssetType("Audio", 2);

    private static final /* synthetic */ AssetType[] $values() {
        return new AssetType[]{Image, Video, Audio};
    }

    public static EnumEntries<AssetType> getEntries() {
        return $ENTRIES;
    }

    public static AssetType valueOf(String str) {
        return (AssetType) Enum.valueOf(AssetType.class, str);
    }

    public static AssetType[] values() {
        return (AssetType[]) $VALUES.clone();
    }

    static {
        AssetType[] assetTypeArr$values = $values();
        $VALUES = assetTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(assetTypeArr$values);
    }

    private AssetType(String str, int i) {
    }
}
