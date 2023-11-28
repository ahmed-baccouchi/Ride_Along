package com.example.projectdintegartion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectdintegartion.MyDatabaseHelper;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button resetButton;
    private MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        // Initialize views
        emailInput = findViewById(R.id.editTextEmail); // Replace with the actual ID in your layout
        resetButton = findViewById(R.id.buttonReset); // Replace with the actual ID in your layout

        // Initialize database helper
        databaseHelper = new MyDatabaseHelper(this);

        // Set click listener for the reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        // Get the entered email
        String email = emailInput.getText().toString().trim();

        // Validate email (you can add more comprehensive validation)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email exists in the database
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyDatabaseHelper.TABLE_USERS +
                " WHERE " + MyDatabaseHelper.COLUMN_EMAIL + "=?", new String[]{email});

        if (cursor.moveToFirst()) {
            // Email exists, implement your password reset logic here
            // For simplicity, let's display a message
            Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();
        } else {
            // Email does not exist in the database
            Toast.makeText(this, "Email not found. Please check your email address.", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }
}
