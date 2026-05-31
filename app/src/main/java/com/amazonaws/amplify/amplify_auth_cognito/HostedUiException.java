package com.amazonaws.amplify.amplify_auth_cognito;

import com.tekartik.sqflite.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HostedUiException.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b&\u0018\u0000 \u00072\u00060\u0001j\u0002`\u0002:\u0004\u0006\u0007\b\tB\u0011\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005¨\u0006\n"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", Constant.PARAM_ERROR_MESSAGE, "", "(Ljava/lang/String;)V", "CANCELLED", "Companion", "NOBROWSER", "UNKNOWN", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class HostedUiException extends Exception {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public HostedUiException() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX INFO: compiled from: HostedUiException.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException$Companion;", "", "()V", "fromThrowable", "Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException;", "e", "", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final HostedUiException fromThrowable(Throwable e) {
            Intrinsics.checkNotNullParameter(e, "e");
            if (e instanceof HostedUiException) {
                return (HostedUiException) e;
            }
            return new UNKNOWN(e.getMessage());
        }
    }

    public HostedUiException(String str) {
        super(str);
    }

    public /* synthetic */ HostedUiException(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }

    /* JADX INFO: compiled from: HostedUiException.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException$NOBROWSER;", "Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException;", "()V", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NOBROWSER extends HostedUiException {
        public NOBROWSER() {
            super("No browser available for launching URL intent");
        }
    }

    /* JADX INFO: compiled from: HostedUiException.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0011\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException$UNKNOWN;", "Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException;", Constant.PARAM_ERROR_MESSAGE, "", "(Ljava/lang/String;)V", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UNKNOWN extends HostedUiException {
        public UNKNOWN() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public UNKNOWN(String str) {
            super(str);
        }

        public /* synthetic */ UNKNOWN(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : str);
        }
    }

    /* JADX INFO: compiled from: HostedUiException.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException$CANCELLED;", "Lcom/amazonaws/amplify/amplify_auth_cognito/HostedUiException;", "()V", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CANCELLED extends HostedUiException {
        public CANCELLED() {
            super(null, 1, 0 == true ? 1 : 0);
        }
    }
}
