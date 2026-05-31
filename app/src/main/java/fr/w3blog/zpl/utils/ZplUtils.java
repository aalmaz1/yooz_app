package fr.w3blog.zpl.utils;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.constant.ZebraPPP;

/* JADX INFO: loaded from: classes2.dex */
public class ZplUtils {
    private static String variableObjectToZplCode(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof Integer) {
            return Integer.toString(((Integer) obj).intValue());
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue() ? "Y" : "N";
        }
        return obj.toString();
    }

    public static StringBuilder zplCommand(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        sb.append(str);
        return sb;
    }

    public static StringBuilder zplCommandSautLigne(String str) {
        StringBuilder sbZplCommand = zplCommand(str);
        sbZplCommand.append("\n");
        return sbZplCommand;
    }

    public static StringBuilder zplCommand(String str, Object... objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("^");
        sb.append(str);
        if (objArr.length > 1) {
            sb.append(variableObjectToZplCode(objArr[0]));
            for (int i = 1; i < objArr.length; i++) {
                sb.append(",");
                sb.append(variableObjectToZplCode(objArr[i]));
            }
        } else if (objArr.length == 1) {
            sb.append(variableObjectToZplCode(objArr[0]));
        }
        return sb;
    }

    public static StringBuilder zplCommandSautLigne(String str, Object... objArr) {
        StringBuilder sbZplCommand = zplCommand(str, objArr);
        sbZplCommand.append("\n");
        return sbZplCommand;
    }

    public static Integer[] extractDotsFromFont(ZebraFont zebraFont, int i, ZebraPPP zebraPPP) {
        Integer[] numArr = new Integer[2];
        if (ZebraFont.ZEBRA_ZERO.equals(zebraFont) && ZebraPPP.DPI_300.equals(zebraPPP)) {
            float f = i;
            numArr[0] = Integer.valueOf(Math.round(4.16f * f));
            numArr[1] = Integer.valueOf(Math.round(f * 4.06f));
            return numArr;
        }
        throw new UnsupportedOperationException("This PPP and this font are not yet supported. Please use ZebraAFontElement.");
    }

    public static Integer convertPointInPixel(int i) {
        return Integer.valueOf(Math.round(i * 1.33f));
    }

    public static String convertAccentToZplAsciiHexa(String str) {
        return str != null ? str.replace("é", "\\82").replace("à", "\\85").replace("è", "\\8A") : str;
    }
}
