package com.example.contactreader;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String phone = null;
                String email = null;
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    phone = getPhone(cr, cur, id);
                    email = getEmail(cr, id);
                }
                Log.d("ContactReader", "contact - " + name + " " + phone + " " + email);
            }
        }
    }

    private String getEmail(ContentResolver cr, String id) {
        String email = null;
        Cursor emailCur = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);
        while (emailCur.moveToNext()) {
            // This would allow you get several email addresses
            // if the email addresses were stored in an array
            email = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            String emailType = emailCur.getString(
                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
            Log.d("ContactReader", "contact - email" + email);
        }
        emailCur.close();
        return email;
    }

    private String getPhone(ContentResolver cr, Cursor cur, String id) {
        String phone = null;
        if (Integer.parseInt(cur.getString(
                cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
            Cursor pCur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                    new String[]{id}, null);
            while (pCur.moveToNext()) {
                int columnIndex = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                phone = pCur.getString(columnIndex);
                Log.d("ContactReader", "contact - phone" + phone);
            }
            pCur.close();
        }
        return phone;
    }
}