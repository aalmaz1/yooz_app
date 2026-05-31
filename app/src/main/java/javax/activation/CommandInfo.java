package javax.activation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes3.dex */
public class CommandInfo {
    private String className;
    private String verb;

    public CommandInfo(String str, String str2) {
        this.verb = str;
        this.className = str2;
    }

    public String getCommandName() {
        return this.verb;
    }

    public String getCommandClass() {
        return this.className;
    }

    public Object getCommandObject(DataHandler dataHandler, ClassLoader classLoader) throws ClassNotFoundException, IOException {
        InputStream inputStream;
        Object objInstantiate = Beans.instantiate(classLoader, this.className);
        if (objInstantiate != null) {
            if (objInstantiate instanceof CommandObject) {
                ((CommandObject) objInstantiate).setCommandContext(this.verb, dataHandler);
            } else if ((objInstantiate instanceof Externalizable) && dataHandler != null && (inputStream = dataHandler.getInputStream()) != null) {
                ((Externalizable) objInstantiate).readExternal(new ObjectInputStream(inputStream));
            }
        }
        return objInstantiate;
    }

    private static final class Beans {
        static final Method instantiateMethod;

        private Beans() {
        }

        static {
            Method declaredMethod = null;
            try {
                declaredMethod = Class.forName("java.beans.Beans").getDeclaredMethod("instantiate", ClassLoader.class, String.class);
            } catch (ClassNotFoundException | NoSuchMethodException unused) {
            }
            instantiateMethod = declaredMethod;
        }

        static Object instantiate(ClassLoader classLoader, String str) throws ClassNotFoundException, IOException {
            int iLastIndexOf;
            Method method = instantiateMethod;
            if (method != null) {
                try {
                    return method.invoke(null, classLoader, str);
                } catch (IllegalAccessException | InvocationTargetException unused) {
                    return null;
                }
            }
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                String strReplace = str.replace(IOUtils.DIR_SEPARATOR_UNIX, '.');
                if (strReplace.startsWith("[") && (iLastIndexOf = strReplace.lastIndexOf(91) + 2) > 1 && iLastIndexOf < strReplace.length()) {
                    strReplace = strReplace.substring(iLastIndexOf);
                }
                int iLastIndexOf2 = strReplace.lastIndexOf(46);
                if (iLastIndexOf2 != -1) {
                    securityManager.checkPackageAccess(strReplace.substring(0, iLastIndexOf2));
                }
            }
            if (classLoader == null) {
                classLoader = (ClassLoader) AccessController.doPrivileged(new PrivilegedAction() { // from class: javax.activation.CommandInfo.Beans.1
                    @Override // java.security.PrivilegedAction
                    public Object run() {
                        try {
                            return ClassLoader.getSystemClassLoader();
                        } catch (SecurityException unused2) {
                            return null;
                        }
                    }
                });
            }
            Class<?> cls = Class.forName(str, true, classLoader);
            try {
                return cls.newInstance();
            } catch (Exception e) {
                throw new ClassNotFoundException(cls + ": " + e, e);
            }
        }
    }
}
