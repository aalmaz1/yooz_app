package androidx.work;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class InputMerger {
    private static final String TAG = Logger.tagWithPrefix("InputMerger");

    public abstract Data merge(List<Data> inputs);

    public static InputMerger fromClassName(String className) {
        try {
            return (InputMerger) Class.forName(className).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            Logger.get().error(TAG, "Trouble instantiating + " + className, e);
            return null;
        }
    }
}
