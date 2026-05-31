package cn.baos.watch.sdk.code;

import cn.baos.watch.sdk.code.bleSdkWrapper.BleSdkWrapper;

/* JADX INFO: loaded from: classes.dex */
public class HuabaoSdkFactory {

    public enum SdkType {
        SDK_HUABAO,
        SDK_XIAOJU
    }

    /* JADX INFO: renamed from: cn.baos.watch.sdk.code.HuabaoSdkFactory$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$baos$watch$sdk$code$HuabaoSdkFactory$SdkType;

        static {
            int[] iArr = new int[SdkType.values().length];
            $SwitchMap$cn$baos$watch$sdk$code$HuabaoSdkFactory$SdkType = iArr;
            try {
                iArr[SdkType.SDK_HUABAO.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$code$HuabaoSdkFactory$SdkType[SdkType.SDK_XIAOJU.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static BleSdkWrapper createMessage(SdkType sdkType) {
        int i = AnonymousClass1.$SwitchMap$cn$baos$watch$sdk$code$HuabaoSdkFactory$SdkType[sdkType.ordinal()];
        if (i == 1) {
            return new HuabaoImpl();
        }
        if (i == 2) {
            return new HuabaoImpl();
        }
        return new HuabaoImpl();
    }
}
