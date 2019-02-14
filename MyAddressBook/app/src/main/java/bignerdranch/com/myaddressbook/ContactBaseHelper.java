package bignerdranch.com.myaddressbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contactsBase.db";

    public ContactBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ContactsDbSchema.ContactsTable.TABLE_NAME + "(" +
                "_id integer primary key autoincrement, " +
                ContactsDbSchema.ContactsTable.Cols.UUID + ", " +
                ContactsDbSchema.ContactsTable.Cols.NAME + ", " +
                ContactsDbSchema.ContactsTable.Cols.PHONE + ", " +
                ContactsDbSchema.ContactsTable.Cols.EMAIL + ", " +
                ContactsDbSchema.ContactsTable.Cols.STREET + ", " +
                ContactsDbSchema.ContactsTable.Cols.CITY + ", " +
                ContactsDbSchema.ContactsTable.Cols.STATE + ", " +
                ContactsDbSchema.ContactsTable.Cols.ZIP + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
