package cn.baos.watch.sdk.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class ContactUtils {
    public static ArrayList<PhoneContactsEntity> getAllContacts(Context context) {
        ArrayList<PhoneContactsEntity> arrayList = new ArrayList<>();
        Cursor cursorQuery = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursorQuery.moveToNext()) {
            PhoneContactsEntity phoneContactsEntity = new PhoneContactsEntity();
            String string = cursorQuery.getString(cursorQuery.getColumnIndex("_id"));
            phoneContactsEntity.name = cursorQuery.getString(cursorQuery.getColumnIndex("display_name"));
            Cursor cursorQuery2 = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id=" + string, null, null);
            while (cursorQuery2.moveToNext()) {
                phoneContactsEntity.phone = cursorQuery2.getString(cursorQuery2.getColumnIndex("data1")).replace("-", "").replace(StringUtils.SPACE, "");
            }
            Cursor cursorQuery3 = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{"_id", "data1"}, "contact_id=? AND mimetype='vnd.android.cursor.item/nickname'", new String[]{string}, null);
            if (cursorQuery3.moveToFirst()) {
                do {
                    String string2 = cursorQuery3.getString(cursorQuery3.getColumnIndex("data1"));
                    phoneContactsEntity.note = string2;
                    Log.i("note:", string2);
                } while (cursorQuery3.moveToNext());
            }
            arrayList.add(phoneContactsEntity);
            cursorQuery2.close();
            cursorQuery3.close();
        }
        cursorQuery.close();
        return arrayList;
    }
}
