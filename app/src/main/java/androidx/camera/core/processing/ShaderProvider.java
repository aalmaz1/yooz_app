package androidx.camera.core.processing;

/* JADX INFO: loaded from: classes.dex */
public interface ShaderProvider {
    public static final ShaderProvider DEFAULT = new ShaderProvider() { // from class: androidx.camera.core.processing.ShaderProvider.1
    };

    default String createFragmentShader(String str, String str2) {
        return null;
    }
}
