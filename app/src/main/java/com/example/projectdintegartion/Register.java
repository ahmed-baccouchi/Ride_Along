package com.example.projectdintegartion;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectdintegartion.MyDatabaseHelper;
import com.example.projectdintegartion.R;

public class Register extends AppCompatActivity {
    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // Initialize views
        fullNameEditText = findViewById(R.id.editTextText);
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
        passwordEditText = findViewById(R.id.editTextTextPassword2);
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword3);
        registerButton = findViewById(R.id.button);

        // Initialize database helper
        databaseHelper = new MyDatabaseHelper(this);

        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                Intent i=new Intent(Register.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void registerUser() {
        // Get values from EditTexts
        String fullName = fullNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            // Handle password mismatch
            return;
        }

        // Get a writable database
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Prepare the values to be inserted
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.COLUMN_FULL_NAME, fullName);
        values.put(MyDatabaseHelper.COLUMN_EMAIL, email);
        values.put(MyDatabaseHelper.COLUMN_PASSWORD, password);

        // Insert the values into the database
        long newRowId = db.insert(MyDatabaseHelper.TABLE_USERS, null, values);

        // Check if the insertion was successful
        if (newRowId != -1) {
            // Handle successful registration
        } else {
            // Handle registration failure
        }
    }
}
