package com.amazonaws.amplify.amplify_auth_cognito;

import android.net.Uri;
import com.amazonaws.amplify.amplify_auth_cognito.LegacyCredentialStoreData;
import com.amazonaws.amplify.amplify_auth_cognito.LegacyDeviceDetailsSecret;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AmplifyAuthCognitoPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\b\u001a\n\u0010\u0006\u001a\u00020\t*\u00020\n\"!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u000b"}, d2 = {"queryParameters", "", "", "Landroid/net/Uri;", "getQueryParameters", "(Landroid/net/Uri;)Ljava/util/Map;", "builder", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreDataBuilder;", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData$Companion;", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsBuilder;", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsSecret$Companion;", "amplify_auth_cognito_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AmplifyAuthCognitoPluginKt {
    public static final Map<String, String> getQueryParameters(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "<this>");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str : uri.getQueryParameterNames()) {
            Intrinsics.checkNotNull(str);
            String queryParameter = uri.getQueryParameter(str);
            if (queryParameter == null) {
                queryParameter = "";
            }
            linkedHashMap.put(str, queryParameter);
        }
        return linkedHashMap;
    }

    public static final LegacyCredentialStoreDataBuilder builder(LegacyCredentialStoreData.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return new LegacyCredentialStoreDataBuilder(null, null, null, null, null, null, null, null, 255, null);
    }

    public static final LegacyDeviceDetailsBuilder builder(LegacyDeviceDetailsSecret.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return new LegacyDeviceDetailsBuilder(null, null, null, null, 15, null);
    }
}
