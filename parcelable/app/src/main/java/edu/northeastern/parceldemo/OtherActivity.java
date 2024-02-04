package edu.northeastern.parceldemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * So what have we done exactly?
 * <p>
 * 1) We created a User object in MainActivity
 * 2) We serialized a User object with writeToParcel in the MainActivity.
 * 3) The Parcel was passed via Intent -- intent.putExtra("user_data", user)
 * 4) In the current activity, we deserialized the User object passed from MainActivity
 *    -- getIntent().getParcelableExtra("user_data")
 */
public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        // Retrieve the parcelable User object passed from MainActivity.
        User user = getIntent().getParcelableExtra("user_data");

        // Use the User data to populate TextViews or other UI elements.
        if (user != null) {
            TextView userInfoTextView = findViewById(R.id.userInfoTextView);
            String userInfo = "Name: " + user.getName() + "\nAge: " + user.getAge() + "\nEmail: " + user.getEmail();
            userInfoTextView.setText(userInfo);
        }
    }

}
