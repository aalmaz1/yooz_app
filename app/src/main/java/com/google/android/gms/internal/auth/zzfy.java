package com.google.android.gms.internal.auth;

import cn.baos.watch.sdk.entitiy.NotificationConstant;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import kotlin.text.Typography;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzfy {
    static String zza(zzfw zzfwVar, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zzd(zzfwVar, sb, 0);
        return sb.toString();
    }

    static final void zzb(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                zzb(sb, i, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zzb(sb, i, str, (Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(' ');
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"");
            sb.append(zzgw.zza(zzee.zzl((String) obj)));
            sb.append(Typography.quote);
            return;
        }
        if (obj instanceof zzee) {
            sb.append(": \"");
            sb.append(zzgw.zza((zzee) obj));
            sb.append(Typography.quote);
            return;
        }
        if (obj instanceof zzeu) {
            sb.append(" {");
            zzd((zzeu) obj, sb, i + 2);
            sb.append("\n");
            while (i2 < i) {
                sb.append(' ');
                i2++;
            }
            sb.append("}");
            return;
        }
        if (!(obj instanceof Map.Entry)) {
            sb.append(": ");
            sb.append(obj);
            return;
        }
        sb.append(" {");
        Map.Entry entry = (Map.Entry) obj;
        int i4 = i + 2;
        zzb(sb, i4, NotificationConstant.EXTRA_KEY, entry.getKey());
        zzb(sb, i4, "value", entry.getValue());
        sb.append("\n");
        while (i2 < i) {
            sb.append(' ');
            i2++;
        }
        sb.append("}");
    }

    private static final String zzc(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (Character.isUpperCase(cCharAt)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(cCharAt));
        }
        return sb.toString();
    }

    private static void zzd(zzfw zzfwVar, StringBuilder sb, int i) {
        boolean zEquals;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        TreeSet<String> treeSet = new TreeSet();
        for (Method method : zzfwVar.getClass().getDeclaredMethods()) {
            map2.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                map.put(method.getName(), method);
                if (method.getName().startsWith("get")) {
                    treeSet.add(method.getName());
                }
            }
        }
        for (String str : treeSet) {
            String strSubstring = str.startsWith("get") ? str.substring(3) : str;
            if (strSubstring.endsWith("List") && !strSubstring.endsWith("OrBuilderList") && !strSubstring.equals("List")) {
                String strConcat = String.valueOf(strSubstring.substring(0, 1).toLowerCase()).concat(String.valueOf(strSubstring.substring(1, strSubstring.length() - 4)));
                Method method2 = (Method) map.get(str);
                if (method2 != null && method2.getReturnType().equals(List.class)) {
                    zzb(sb, i, zzc(strConcat), zzeu.zze(method2, zzfwVar, new Object[0]));
                }
            }
            if (strSubstring.endsWith("Map") && !strSubstring.equals("Map")) {
                String strConcat2 = String.valueOf(strSubstring.substring(0, 1).toLowerCase()).concat(String.valueOf(strSubstring.substring(1, strSubstring.length() - 3)));
                Method method3 = (Method) map.get(str);
                if (method3 != null && method3.getReturnType().equals(Map.class) && !method3.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method3.getModifiers())) {
                    zzb(sb, i, zzc(strConcat2), zzeu.zze(method3, zzfwVar, new Object[0]));
                }
            }
            if (((Method) map2.get("set".concat(String.valueOf(strSubstring)))) != null && (!strSubstring.endsWith("Bytes") || !map.containsKey("get".concat(String.valueOf(strSubstring.substring(0, strSubstring.length() - 5)))))) {
                String strConcat3 = String.valueOf(strSubstring.substring(0, 1).toLowerCase()).concat(String.valueOf(strSubstring.substring(1)));
                Method method4 = (Method) map.get("get".concat(String.valueOf(strSubstring)));
                Method method5 = (Method) map.get("has".concat(String.valueOf(strSubstring)));
                if (method4 != null) {
                    Object objZze = zzeu.zze(method4, zzfwVar, new Object[0]);
                    if (method5 == null) {
                        if (objZze instanceof Boolean) {
                            if (((Boolean) objZze).booleanValue()) {
                                zzb(sb, i, zzc(strConcat3), objZze);
                            }
                        } else if (objZze instanceof Integer) {
                            if (((Integer) objZze).intValue() != 0) {
                                zzb(sb, i, zzc(strConcat3), objZze);
                            }
                        } else if (objZze instanceof Float) {
                            if (Float.floatToRawIntBits(((Float) objZze).floatValue()) != 0) {
                                zzb(sb, i, zzc(strConcat3), objZze);
                            }
                        } else if (!(objZze instanceof Double)) {
                            if (objZze instanceof String) {
                                zEquals = objZze.equals("");
                            } else if (objZze instanceof zzee) {
                                zEquals = objZze.equals(zzee.zzb);
                            } else if (objZze instanceof zzfw) {
                                if (objZze != ((zzfw) objZze).zzh()) {
                                    zzb(sb, i, zzc(strConcat3), objZze);
                                }
                            } else if (!(objZze instanceof Enum) || ((Enum) objZze).ordinal() != 0) {
                                zzb(sb, i, zzc(strConcat3), objZze);
                            }
                            if (!zEquals) {
                                zzb(sb, i, zzc(strConcat3), objZze);
                            }
                        } else if (Double.doubleToRawLongBits(((Double) objZze).doubleValue()) != 0) {
                            zzb(sb, i, zzc(strConcat3), objZze);
                        }
                    } else if (((Boolean) zzeu.zze(method5, zzfwVar, new Object[0])).booleanValue()) {
                        zzb(sb, i, zzc(strConcat3), objZze);
                    }
                }
            }
        }
        if (zzfwVar instanceof zzet) {
            zzep zzepVar = ((zzet) zzfwVar).zzb;
            throw null;
        }
        zzgz zzgzVar = ((zzeu) zzfwVar).zzc;
        if (zzgzVar != null) {
            zzgzVar.zze(sb, i);
        }
    }
}
