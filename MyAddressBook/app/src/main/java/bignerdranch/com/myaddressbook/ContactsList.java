package bignerdranch.com.myaddressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactsList {

    private final String TAG = "ContactsList";

    private static ContactsList sContactsList;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static ContactsList get(Context context){
        if (sContactsList == null){
            sContactsList = new ContactsList(context);
        }
        return  sContactsList;
    }

    private ContactsList(Context context){
        mContext = context;
        mDatabase = new ContactBaseHelper(context).getWritableDatabase();
    }

    // method queries database using ConctastCursorWrapper
    public Contact getContact(UUID contactId){

        ContactCursorWrapper cursor = queryContacts(
                ContactsDbSchema.ContactsTable.Cols.UUID + " = ?",
                new String[] {contactId.toString()}
        );

        try{
            // return null if no entries found
            if(cursor.getCount() == 0){
                return null;
            }
            // return first entry if more than one entry found
            cursor.moveToFirst();
            return cursor.getContact();

        } finally {
            cursor.close();
        }

    }

    public List<Contact> getContacts(){

        List<Contact> contacts = new ArrayList<>();

        // null, null as parameters will return all contacts
        ContactCursorWrapper cursor = queryContacts(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        Log.d(TAG, "getContacts: list size = " + contacts.size());

        return contacts;
    }

    // method removes contact from the database
    public void removeContact(Contact contact){

        String uuidString = contact.getId().toString();

        mDatabase.delete(ContactsDbSchema.ContactsTable.TABLE_NAME,
                ContactsDbSchema.ContactsTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }


    // method prepares contact to be written in the database
    private ContentValues getContentValues(Contact contact){

        ContentValues values = new ContentValues();
        values.put(ContactsDbSchema.ContactsTable.Cols.UUID, contact.getId().toString());
        values.put(ContactsDbSchema.ContactsTable.Cols.NAME, contact.getName());
        values.put(ContactsDbSchema.ContactsTable.Cols.PHONE, contact.getPhone());
        values.put(ContactsDbSchema.ContactsTable.Cols.EMAIL, contact.getEmail());
        values.put(ContactsDbSchema.ContactsTable.Cols.STREET, contact.getStreet());
        values.put(ContactsDbSchema.ContactsTable.Cols.CITY, contact.getCity());
        values.put(ContactsDbSchema.ContactsTable.Cols.STATE, contact.getState());
        values.put(ContactsDbSchema.ContactsTable.Cols.ZIP, contact.getZip());

        return values;
    }


    // method adds new contact to the database
    public void addContact(Contact contact){
        // uuid, name, phone, email, street, city, state, zip

        // prepare content values for inserting into the database
        ContentValues values = getContentValues(contact);

        mDatabase.insert(ContactsDbSchema.ContactsTable.TABLE_NAME, null, values);
    }


    // method updates the contact in the database
    public void updateContact(Contact contact){

        String uuidString = contact.getId().toString();



        ContentValues values = getContentValues(contact);

        int returnValue = mDatabase.update(
                ContactsDbSchema.ContactsTable.TABLE_NAME,
                values,
                ContactsDbSchema.ContactsTable.Cols.UUID + " = ?",
                new String[]{uuidString}
                );

        Log.d(TAG, "updateContact: " +
                "UUID = " + uuidString + "\n" +
                "new contact name = " + contact.getName() +
                "\nreturn value = " + returnValue
        );
    }

    // method makes a query into the database using whereClause and whereArgs passed as arguments
    private ContactCursorWrapper queryContacts(String whereClause, String[] whereArgs){

        Cursor cursor = mDatabase.query(
                ContactsDbSchema.ContactsTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ContactCursorWrapper(cursor);
    }
}
