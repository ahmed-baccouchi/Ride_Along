package com.example.projectdintegartion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView RegisterButton;
    private MyDatabaseHelper databaseHelper;
    private TextView ForgotPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        emailInput = findViewById(R.id.editTextTextEmailAddress);
        passwordInput = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button);
        RegisterButton = findViewById(R.id.textView7);
        ForgotPasswordButton=findViewById(R.id.textView5);

        // Initialize database helper
        databaseHelper = new MyDatabaseHelper(this);

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Set click listener for the register button
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);
            }
        });
        ForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void loginUser() {
        // Get values from EditTexts
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Get a readable database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Define projection (columns) for the query
        String[] projection = {
                MyDatabaseHelper.COLUMN_FULL_NAME,
                MyDatabaseHelper.COLUMN_EMAIL,
                MyDatabaseHelper.COLUMN_PASSWORD
        };

        // Define selection (WHERE clause) and selection arguments
        String selection = MyDatabaseHelper.COLUMN_EMAIL + " = ? AND " +
                MyDatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        // Perform the query on the users table
        Cursor cursor = db.query(
                MyDatabaseHelper.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Check if a matching user is found
        if (cursor.moveToFirst()) {
            // User found, navigate to the next activity (e.g., home page)
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.COLUMN_FULL_NAME));
            // You can pass the user information to the next activity if needed
            Intent intent = new Intent(MainActivity.this, home.class);
            intent.putExtra("FULL_NAME", fullName);
            startActivity(intent);

            // Display success message
            Toast.makeText(this, "Login successful. Welcome, " + fullName + "!", Toast.LENGTH_SHORT).show();
        } else {
            // No matching user found, handle login failure
            // You can show a toast or set an error message
            Toast.makeText(this, "Login failed. Please check your credentials and try again.", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }



}
