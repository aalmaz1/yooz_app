package com.baseflow.geolocator.location;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class AndroidIconResource {
    private final String defType;
    private final String name;

    public static AndroidIconResource parseArguments(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        return new AndroidIconResource((String) map.get("name"), (String) map.get("defType"));
    }

    private AndroidIconResource(String str, String str2) {
        this.name = str;
        this.defType = str2;
    }

    public String getName() {
        return this.name;
    }

    public String getDefType() {
        return this.defType;
    }
}
