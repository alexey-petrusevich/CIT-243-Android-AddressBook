package bignerdranch.com.myaddressbook;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

public class ContactCursorWrapper extends CursorWrapper{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ContactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // method gets an entry from the database with columns specified by getString() method
    public Contact getContact(){
        // uuid, name, phone, email, street, city, state, zip
        String uuidString = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.UUID));
        String name = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.NAME));
        String phone = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.PHONE));
        String email = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.EMAIL));
        String street = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.STREET));
        String city = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.CITY));
        String state = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.STATE));
        String zip = getString(getColumnIndex(ContactsDbSchema.ContactsTable.Cols.ZIP));

        Contact contact = new Contact(name, phone, email, street, city, state, zip);
        contact.setId(UUID.fromString(uuidString));

        return contact;
    }
}
