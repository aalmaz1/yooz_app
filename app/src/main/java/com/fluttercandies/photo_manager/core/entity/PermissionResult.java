package com.fluttercandies.photo_manager.core.entity;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: PermissionResult.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/PermissionResult;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "NotDetermined", "Denied", "Authorized", "Limited", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PermissionResult {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PermissionResult[] $VALUES;
    private final int value;
    public static final PermissionResult NotDetermined = new PermissionResult("NotDetermined", 0, 0);
    public static final PermissionResult Denied = new PermissionResult("Denied", 1, 2);
    public static final PermissionResult Authorized = new PermissionResult("Authorized", 2, 3);
    public static final PermissionResult Limited = new PermissionResult("Limited", 3, 4);

    private static final /* synthetic */ PermissionResult[] $values() {
        return new PermissionResult[]{NotDetermined, Denied, Authorized, Limited};
    }

    public static EnumEntries<PermissionResult> getEntries() {
        return $ENTRIES;
    }

    public static PermissionResult valueOf(String str) {
        return (PermissionResult) Enum.valueOf(PermissionResult.class, str);
    }

    public static PermissionResult[] values() {
        return (PermissionResult[]) $VALUES.clone();
    }

    private PermissionResult(String str, int i, int i2) {
        this.value = i2;
    }

    public final int getValue() {
        return this.value;
    }

    static {
        PermissionResult[] permissionResultArr$values = $values();
        $VALUES = permissionResultArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(permissionResultArr$values);
    }
}
