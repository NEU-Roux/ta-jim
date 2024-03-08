package edu.northeastern.firebasedemo;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import com.google.firebase.Timestamp;


public class PostDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ImageView postImage = findViewById(R.id.postImage);
        TextView postTitle = findViewById(R.id.postTitle);
        TextView postBody = findViewById(R.id.postBody);
        TextView postDate = findViewById(R.id.postDate); // Make sure you have this TextView in your layout
        TextView posterName = findViewById(R.id.posterName); // And this one

        // Retrieve the Post object
        Post post = getIntent().getParcelableExtra("Post");

        if (post != null) {
            postTitle.setText(post.getTitle());
            postBody.setText(post.getBodyText());

            // Format and display the createdAt timestamp
            Timestamp createdAt = post.getCreatedAt();
            postDate.setText(createdAt.toDate().toString());

            // Display the userId as the poster's name
            posterName.setText(post.getPoster());

            // Use Glide or another library to load the image
            Glide.with(this)
                    .load(post.getImageUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(postImage);
        }
    }
}

