package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        final Button buttonWrite = findViewById(R.id.button_write);
        buttonWrite.setOnClickListener(view -> {
            final EditText firstNameField = findViewById(R.id.first_name_field);
            final EditText lastNameField = findViewById(R.id.last_name_field);
            final EditText emailAddressField = findViewById(R.id.email_address_field);

            final String firstName = firstNameField.getText().toString();
            final String lastName = lastNameField.getText().toString();
            final String emailAddress =  emailAddressField.getText().toString();

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

    private String capitalize(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
