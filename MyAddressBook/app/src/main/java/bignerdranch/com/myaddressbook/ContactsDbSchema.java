package bignerdranch.com.myaddressbook;

public class ContactsDbSchema {

    public static final class ContactsTable{

        public static final String TABLE_NAME = "contacts";

        public static final class Cols{
            // uuid, name, phone, email, street, city, state, zip
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String PHONE = "phone";
            public static final String EMAIL = "email";
            public static final String STREET = "street";
            public static final String CITY = "city";
            public static final String STATE = "state";
            public static final String ZIP = "zip";
        }
    }



}
