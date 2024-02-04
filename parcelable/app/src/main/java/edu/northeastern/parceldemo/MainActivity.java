package edu.northeastern.parceldemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity demonstrates how to collect user input, create a parcelable User object,
 * and pass it to OtherActivity using Intent.
 */
public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge, editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initializeComponents(); // Initialize the UI components (EditText fields and the submit Button).
    }

    private void initializeComponents() { initializeEditTextFields(); initializeSubmitButton(); }

    private void initializeEditTextFields() {
        editTextName = findViewById(R.id.editTextUserName);
        editTextAge = findViewById(R.id.editTextUserAge);
        editTextEmail = findViewById(R.id.editTextUserEmail);
    }

    private void initializeSubmitButton() {
        Button submitButton = findViewById(R.id.button_submit);
        submitButton.setOnClickListener(view -> launchOtherActivity());
    }

    /**
     * Here we are creating a new User object from the input provided in the EditText fields.
     * This User object implements Parcelable, which allows us to pass complex data through Intents.
     *
     * @return A new User object with details from the EditText fields.
     */
    private User createUser() {
        // Retrieve user input from EditText fields
        String name = editTextName.getText().toString();
        String ageInput = editTextAge.getText().toString();
        int age = ageInput.isEmpty() ? 0 : Integer.parseInt(ageInput);
        String email = editTextEmail.getText().toString();

        // Return a new User object
        return new User(name, age, email);
    }

    /**
     * Launches OtherActivity and passes the User object as parcelable data in the intent.
     * Parcelable is an Android-specific interface that allows for serialization
     * of objects so they can be passed in bundles or intents.
     */
    private void launchOtherActivity() {
        // Create the User object with the entered data
        User user = createUser();

        // Intent to start OtherActivity and pass the User object
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        intent.putExtra("user_data", user);
        startActivity(intent);
    }

}
