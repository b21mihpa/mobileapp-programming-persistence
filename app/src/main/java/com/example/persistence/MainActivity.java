package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        final Button buttonRead = findViewById(R.id.button_read);
        buttonRead.setOnClickListener(view -> {
            List<Contact> contacts = getContacts();

            if (contacts.isEmpty()) {
                Toast.makeText(this, "No contacts available.", Toast.LENGTH_LONG).show();
                return;
            }

            final TextView contactsList = findViewById(R.id.contacts_list);
            contactsList.setText("");

            for (int i = 0; i < contacts.size(); i++) {
                contactsList.setText(contactsList.getText() + contacts.get(i).toString() + "\n");
            }
        });

        final Button buttonWrite = findViewById(R.id.button_write);
        buttonWrite.setOnClickListener(view -> {
            final EditText firstNameField = findViewById(R.id.first_name_field);
            final EditText lastNameField = findViewById(R.id.last_name_field);
            final EditText emailAddressField = findViewById(R.id.email_address_field);

            final String firstName = firstNameField.getText().toString();
            final String lastName = lastNameField.getText().toString();
            final String emailAddress = emailAddressField.getText().toString();

            if (firstName.isEmpty()) {
                Toast.makeText(this, "Please, provide a first name.", Toast.LENGTH_LONG).show();
                return;
            }

            if (lastName.isEmpty()) {
                Toast.makeText(this, "Please, provide a last name.", Toast.LENGTH_LONG).show();
                return;
            }

            if (emailAddress.isEmpty()) {
                Toast.makeText(this, "Please, provide an email address.", Toast.LENGTH_LONG).show();
                return;
            }

            addContact(capitalize(firstName), capitalize(lastName), emailAddress.toLowerCase());

            firstNameField.getText().clear();
            lastNameField.getText().clear();
            emailAddressField.getText().clear();

            Toast.makeText(this, "Added contact information for " + capitalize(firstName) + " " + capitalize(lastName) + ".", Toast.LENGTH_SHORT).show();
        });
    }

    private long addContact(String firstName, String lastName, String emailAddress) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseTables.Contact.COLUMN_NAME_FIRST_NAME, firstName);
        contentValues.put(DatabaseTables.Contact.COLUMN_NAME_LAST_NAME, lastName);
        contentValues.put(DatabaseTables.Contact.COLUMN_NAME_EMAIL_ADDRESS, emailAddress);
        return database.insert(DatabaseTables.Contact.TABLE_NAME, null, contentValues);
    }

    private List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        Cursor cursor = database.query(DatabaseTables.Contact.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            contacts.add(new Contact(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Contact.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Contact.COLUMN_NAME_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Contact.COLUMN_NAME_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Contact.COLUMN_NAME_EMAIL_ADDRESS))
            ));
        }

        cursor.close();
        return contacts;
    }

    private String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
