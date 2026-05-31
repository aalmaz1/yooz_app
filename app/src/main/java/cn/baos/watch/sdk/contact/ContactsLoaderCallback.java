package cn.baos.watch.sdk.contact;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ContactsLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "ContactsLoaderCallback";
    private Context context;
    private QueryListener listener;
    private JSONObject result;

    public interface QueryListener {
        void success(ArrayList<PhoneContactsEntity> arrayList);
    }

    @Override // android.app.LoaderManager.LoaderCallbacks
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public ContactsLoaderCallback(Context context) {
        this.context = context;
    }

    @Override // android.app.LoaderManager.LoaderCallbacks
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this.context, ContactsContract.Contacts.CONTENT_URI, new String[]{"_id", "display_name"}, null, null, null);
    }

    @Override // android.app.LoaderManager.LoaderCallbacks
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
        if (cursor.isClosed()) {
            return;
        }
        new Thread(new Runnable() { // from class: cn.baos.watch.sdk.contact.ContactsLoaderCallback.1
            @Override // java.lang.Runnable
            public void run() {
                ArrayList<PhoneContactsEntity> arrayList = new ArrayList<>();
                Cursor cursor2 = cursor;
                if (cursor2 != null && cursor2.moveToFirst()) {
                    do {
                        Cursor cursor3 = cursor;
                        String string = cursor3.getString(cursor3.getColumnIndex("display_name"));
                        Cursor cursor4 = cursor;
                        Cursor cursorQuery = ContactsLoaderCallback.this.context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"data1"}, "contact_id=" + cursor4.getInt(cursor4.getColumnIndex("_id")), null, null);
                        if (cursorQuery != null && cursorQuery.moveToFirst()) {
                            do {
                                String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("data1"));
                                PhoneContactsEntity phoneContactsEntity = new PhoneContactsEntity();
                                phoneContactsEntity.name = string;
                                phoneContactsEntity.phone = string2;
                                arrayList.add(phoneContactsEntity);
                            } while (cursorQuery.moveToNext());
                        }
                    } while (cursor.moveToNext());
                }
                if (ContactsLoaderCallback.this.listener != null) {
                    ContactsLoaderCallback.this.listener.success(arrayList);
                }
            }
        }).start();
    }

    public void setQueryListener(QueryListener queryListener) {
        this.listener = queryListener;
    }
}
