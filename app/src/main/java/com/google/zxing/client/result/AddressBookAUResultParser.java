package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharUtils;

/* JADX INFO: loaded from: classes2.dex */
public final class AddressBookAUResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        if (!massagedText.contains("MEMORY") || !massagedText.contains(IOUtils.LINE_SEPARATOR_WINDOWS)) {
            return null;
        }
        String strMatchSinglePrefixedField = matchSinglePrefixedField("NAME1:", massagedText, CharUtils.CR, true);
        String strMatchSinglePrefixedField2 = matchSinglePrefixedField("NAME2:", massagedText, CharUtils.CR, true);
        String[] strArrMatchMultipleValuePrefix = matchMultipleValuePrefix("TEL", massagedText);
        String[] strArrMatchMultipleValuePrefix2 = matchMultipleValuePrefix("MAIL", massagedText);
        String strMatchSinglePrefixedField3 = matchSinglePrefixedField("MEMORY:", massagedText, CharUtils.CR, false);
        String strMatchSinglePrefixedField4 = matchSinglePrefixedField("ADD:", massagedText, CharUtils.CR, true);
        return new AddressBookParsedResult(maybeWrap(strMatchSinglePrefixedField), null, strMatchSinglePrefixedField2, strArrMatchMultipleValuePrefix, null, strArrMatchMultipleValuePrefix2, null, null, strMatchSinglePrefixedField3, strMatchSinglePrefixedField4 != null ? new String[]{strMatchSinglePrefixedField4} : null, null, null, null, null, null, null);
    }

    private static String[] matchMultipleValuePrefix(String str, String str2) {
        String strMatchSinglePrefixedField;
        ArrayList arrayList = null;
        for (int i = 1; i <= 3 && (strMatchSinglePrefixedField = matchSinglePrefixedField(str + i + ':', str2, CharUtils.CR, true)) != null; i++) {
            if (arrayList == null) {
                arrayList = new ArrayList(3);
            }
            arrayList.add(strMatchSinglePrefixedField);
        }
        if (arrayList == null) {
            return null;
        }
        return (String[]) arrayList.toArray(EMPTY_STR_ARRAY);
    }
}
