package com.baseflow.geolocator.permission;

/* JADX INFO: loaded from: classes.dex */
public enum LocationPermission {
    denied,
    deniedForever,
    whileInUse,
    always;

    /* JADX INFO: renamed from: com.baseflow.geolocator.permission.LocationPermission$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$baseflow$geolocator$permission$LocationPermission;

        static {
            int[] iArr = new int[LocationPermission.values().length];
            $SwitchMap$com$baseflow$geolocator$permission$LocationPermission = iArr;
            try {
                iArr[LocationPermission.denied.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$permission$LocationPermission[LocationPermission.deniedForever.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$permission$LocationPermission[LocationPermission.whileInUse.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$permission$LocationPermission[LocationPermission.always.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public int toInt() {
        int i = AnonymousClass1.$SwitchMap$com$baseflow$geolocator$permission$LocationPermission[ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if (i == 4) {
            return 3;
        }
        throw new IndexOutOfBoundsException();
    }
}
