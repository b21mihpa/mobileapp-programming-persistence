package com.example.persistence;

public class DatabaseTables {
    static class Contact {
        static final String TABLE_NAME = "contact";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_FIRST_NAME = "first_name";
        static final String COLUMN_NAME_LAST_NAME = "last_name";
        static final String COLUMN_NAME_EMAIL_ADDRESS = "email_address";
    }

    static final String SQL_CREATE_TABLE_CONTACT =
            "CREATE TABLE " + Contact.TABLE_NAME + " (" +
                    Contact.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Contact.COLUMN_NAME_FIRST_NAME + " TEXT," +
                    Contact.COLUMN_NAME_LAST_NAME + " TEXT," +
                    Contact.COLUMN_NAME_EMAIL_ADDRESS + " TEXT)";

    static final String SQL_DELETE_TABLE_CONTACT = "DROP TABLE IF EXISTS " + Contact.TABLE_NAME;
}
