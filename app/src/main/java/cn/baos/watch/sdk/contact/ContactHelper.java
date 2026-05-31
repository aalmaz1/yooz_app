package cn.baos.watch.sdk.contact;

import android.app.Activity;
import cn.baos.watch.sdk.contact.ContactsLoaderCallback;
import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
public class ContactHelper {
    private static final String TAG = "ContactHelper";
    private ArrayList<PhoneContactsEntity> contacts;
    private Activity context;

    public ContactHelper(Activity activity) {
        this.context = activity;
    }

    public ArrayList<PhoneContactsEntity> queryContactList() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ContactsLoaderCallback contactsLoaderCallback = new ContactsLoaderCallback(this.context);
        contactsLoaderCallback.setQueryListener(new ContactsLoaderCallback.QueryListener() { // from class: cn.baos.watch.sdk.contact.ContactHelper.1
            @Override // cn.baos.watch.sdk.contact.ContactsLoaderCallback.QueryListener
            public void success(ArrayList<PhoneContactsEntity> arrayList) {
                ContactHelper.this.contacts = arrayList;
                countDownLatch.countDown();
            }
        });
        this.context.getLoaderManager().restartLoader(0, null, contactsLoaderCallback);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.contacts;
    }
}
