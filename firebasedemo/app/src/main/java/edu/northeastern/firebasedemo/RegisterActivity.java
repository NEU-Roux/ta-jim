package edu.northeastern.firebasedemo;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

// This class represents the registration activity
// It allows the user to register a new account
// It uses Firebase Authentication to create a new user

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.editTextTextRegisterEmailAddress);
        passwordEditText = findViewById(R.id.editTextRegisterPassword);
        confirmPasswordEditText = findViewById(R.id.confirmRegisterTextPassword);
        Button registerButton = findViewById(R.id.submitRegistrationButton);
        mAuth = FirebaseAuth.getInstance(); // Get an instance of Firebase Authentication, which we use to register the user.

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // Some basic validation, but you should add more
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new user with the email and password when the user clicks the register button
                // See we are calling createUserWithEmailAndPassword on the FirebaseAuth instance
                // This is an asynchronous operation, so we need to add a listener to handle the result
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // notice we are not including the password in the user document
                        // this is because it is really bad practice to store passwords in the database
                        // even the hashed version of the password, if you can avoid it, you should.
                        // Firebase Authentication handles the password for us, so we don't need to store it in the database
                        createUserDocument(email); // Create a new user document in the Firestore database with the user's email
                        Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Create a new user document in the Firestore database
    // This is a simple document with the user's email
    // You might want to add more fields like name, profile picture, etc.
    // And you can see my explanation in the comments about why we don't store the password in the database.
    private void createUserDocument(String email) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid(); // Get the user's unique ID, which we use as the document ID in Firestore.
            User user = new User(uid, email);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(uid).set(user)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully written!"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error writing document", e));
        }
    }
}
