package edu.northeastern.firebasedemo;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.Timestamp;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*


This is a pretty basic application that demonstrates how to use Firebase Authentication, Firestore, and Storage.
It's a social media app where:
- Users can register and login
- Users can create posts with a title, body, and an optional image.
- Users can view all posts in the app, sorted by the time they were created.

Firebase Authentication:
    Firebase authentication is a service that can authenticate users using only client-side code.
    It supports social login providers Facebook, GitHub, Twitter, and Google, as well as other providers such as email and password, and phone authentication.
    Today we will just be using basic email and password authentication, but you can easily extend this to use other providers.
    Aside from basic authentication it can help with password resets, email verification, and more.

Firebase Firestore:
    Firestore is a flexible, scalable database for mobile, web, and server development from Firebase and Google Cloud Platform.
    Cloud Firestore also offers seamless integration with other Firebase and Google Cloud Platform products, including Cloud Functions.
    It's a NoSQL database, which means it doesn't use tables and rows, but instead uses collections of documents.
    It's more similar to MongoDB, and in my opinion, it's even more simple to use than MongoDB, since it's not reliant on a separate server.

- Firebase Storage:
    Firebase Storage is a powerful, simple, and cost-effective object storage service built for Google scale.
    The Firebase SDKs for Cloud Storage add Google security to file uploads and downloads for your Firebase apps.
    You can use it to store images, audio, video, or other user-generated content.
    Firebase Storage is backed by Google Cloud Storage, a powerful, simple, and cost-effective object storage service.

In this app we use Firbase Authentication to handle basic email and password authentication, login, and registration.
We use Firestore to store the posts documents, and user documents.
Finally, we use Firebase Storage to store the images that are uploaded with the posts.
 */


public class MainActivity extends AppCompatActivity implements PostAdapter.OnPostListener {

    private PostAdapter adapter;
    private final List<Post> postList = new ArrayList<>();
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 420;

    private ImageView imageViewPostImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(this, postList, this);
        recyclerView.setAdapter(adapter);

        // Add a divider between each item
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        initLogoutButton();
        initAddPostButton();
        updateWelcomeText();
        loadPosts();
    }

    // A method to initialize the logout button, which rests at the top right of the MainActivity.
    // When clicked, it logs the user out and redirects them to the LoginActivity.
    private void initLogoutButton() {
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // see here we sign out the user from Firebase using the FirebaseAuth instance we created.
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }

    // A method to initialize the add post button, which rests at the bottom right of the MainActivity.
    // When clicked, it opens a dialog that allows the user to create a new post.
    // The user can enter a title, body, and optionally upload an image.
    private void initAddPostButton() {
        FloatingActionButton addPostButton = findViewById(R.id.fabCreatePost);
        addPostButton.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View view = inflater.inflate(R.layout.dialog_add_post, null);

            EditText titleEditText = view.findViewById(R.id.editTextPostTitle);
            EditText bodyEditText = view.findViewById(R.id.editTextPostBody);
            Button buttonPickImage = view.findViewById(R.id.buttonPickImage);
            Button buttonSubmitPost = view.findViewById(R.id.buttonSubmitPost);
            imageViewPostImage = view.findViewById(R.id.imageViewPostImage);

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setView(view)
                    .create();

            // Action_GET_CONTENT is used to open the file picker, which allows the user to select an image from their device.
            // Often on Google devices this will open the Google Photos app, but it can vary by device.
            buttonPickImage.setOnClickListener(v2 -> {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            });

            // Here we call the submitPost method, which will handle the logic for creating a new post.
            buttonSubmitPost.setOnClickListener(v3 -> submitPost(titleEditText, bodyEditText, dialog));

            dialog.show();
        });
    }

    // A method to handle the logic for creating a new post.
    // It checks if the user has entered a title and body, and if an image has been uploaded.
    // If all the required fields are present, it calls the uploadImageToFirebaseStorage method.
    private void submitPost(EditText titleEditText, EditText bodyEditText, AlertDialog dialog) {
        String postTitle = titleEditText.getText().toString().trim();
        String postBody = bodyEditText.getText().toString().trim();

        // Here I want to make sure the user has entered a title and body, and if an image has been uploaded.
        // If all the required fields are present, I call the uploadImageToFirebaseStorage method.
        // If the user has not uploaded an image, I call the savePost method directly.

        // I did a lot of the work for you here, but you need to implement the logic for when the user has not uploaded an image.
        // Please examine all the code for uploading an image to Firebase Storage, and then implement the logic for when the user has not uploaded an image.
    }

    // A method to upload the image to Firebase Storage.
    // I have a folder in Firebase Storage called "posts" where I store all the images that are uploaded with posts.
    // They have an associate URL that I store in the Firestore collection for post documents. This URL is used to display the image in the app.
    // You will notice that in the Post document, I store the URL of the image, not the image itself.
    private void uploadImageToFirebaseStorage(String title, String body, AlertDialog dialog) {
        // Create a reference to the Firebase Storage "posts" folder
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("posts");
        // Create a reference to the file you want to upload
        // I use the current time in milliseconds as the file name, and the file extension from the imageUri.
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        // Upload the file and get the download URL
        fileReference.putFile(imageUri).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw Objects.requireNonNull(task.getException());
            }
            return fileReference.getDownloadUrl();

        }).addOnCompleteListener(task -> {
                // If the upload is successful, get the download URL and call the savePost method with the title, body, and imageUrl as arguments.
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String imageUrl = downloadUri.toString();
                    savePost(title, body, imageUrl, dialog);
                } else {
                    Toast.makeText(MainActivity.this, "Upload failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
        });
    }

    // This method gets the file extension from the imageUri, which is used to set the file name when uploading the image to Firebase Storage.
    // This is a common way to name files when uploading them to a server, as it ensures that the file name is unique.
    // I called this method from the uploadImageToFirebaseStorage method.
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // A method to save the post to Firestore.
    // It creates a new Post object with the title, body, imageUrl, and the current user's email.
    // It then adds the post to the "posts" collection in Firestore.
    // When the post is added, it calls the loadPosts method to refresh the list of posts in the app.
    // It also dismisses the dialog that was used to create the post.
    private void savePost(String title, String body, String imageUrl, AlertDialog dialog) {
        Post post = new Post();
        post.setTitle(title);
        post.setBodyText(body);
        post.setImageUrl(imageUrl);
        post.setPoster(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail()); // Get the current user's email and set it as the poster.
        post.setCreatedAt(new Timestamp(new Date())); // Timestamp the post with the current time, it's a useful Firebase feature.

        // Add the post to the "posts" collection in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // By now you've probably seen getInstance() a few times,
        // it's a common pattern in Firebase to get an instance of the database, storage, or authentication.
        // here's a link to the documentation if you want to learn more: https://firebase.google.com/docs/reference/android/com/google/firebase/firestore/FirebaseFirestore#getInstance()
        db.collection("posts").add(post)
                .addOnSuccessListener(documentReference -> {
                    String documentId = documentReference.getId();
                    // Update the post with the documentId
                    db.collection("posts").document(documentId)
                            .update("id", documentId)
                            .addOnSuccessListener(aVoid -> Log.d("MainActivity", "DocumentSnapshot successfully updated with ID"))
                            .addOnFailureListener(e -> Log.w("MainActivity", "Error updating document", e));

                    loadPosts(); // Refresh the list of posts in the app
                    dialog.dismiss();
                })
                .addOnFailureListener(e -> Log.w("MainActivity", "Error adding document", e));
    }

    // This method is called when the user selects an image from the file picker.
    // It loads the image into the imageViewPostImage in the dialog that is used to create a new post.
    // I used the Glide library to load the image, which is a popular library for loading images in Android.
    // It's a good idea to use a library like Glide or Picasso to load images, as it handles a lot of the complexity of loading images in Android.
    // You can learn more about Glide here: https://github.com/bumptech/glide
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // Update the ImageView in dialog
            if (imageViewPostImage != null) {
                Glide.with(this)
                        .load(imageUri)
                        .into(imageViewPostImage);
            }
        }
    }

    // Update the welcome text with the current user's email, if available.
    // If the user is not logged in, it sets the text to an empty string.
    // This is nice for the user, as it lets them know they are logged in, and it's also useful for debugging.
    private void updateWelcomeText() {
        TextView textUniqueUser = findViewById(R.id.textUniqueUser);
        // Finish the implementation of the updateWelcomeText method
        // to do so you will need to get the current user from FirebaseAuth and set the text of the textUniqueUser TextView to the user's email.
        // If the user is not logged in, set the text to an empty string.
        // although they should not be able to access this activity if they are not logged in.
        // See the below documentation for more information on how to get the current user.
        // https://firebase.google.com/docs/auth/android/manage-users


    }

    // Load posts from the Firestore database in order of their age (newest first).
    // This is a common pattern in social media apps, where you want to show the newest posts first.
    // You can always implement a different or even multiple sorting options, but this is a good default.
    @SuppressLint("NotifyDataSetChanged")
    private void loadPosts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                // Notice that I'm using the Query class to order the posts by their createdAt field in descending order.
                // Query class is a powerful feature of Firestore that allows you to filter and order documents in a collection.
                // Firebase provides a lot of flexibility in how you can query your data, and it's a good idea to familiarize yourself with the different query options.
                // Here's a link to the documentation if you want to learn more: https://firebase.google.com/docs/firestore/query-data/order-limit-data
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get() // This is an asynchronous method, so it returns a Task object.
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        postList.clear();
                        // Loop through the documents and add them to the postList, which is used to populate the RecyclerView.
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Post post = document.toObject(Post.class);
                            postList.add(post);
                        }
                        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed, so it can refresh the list of posts in the app.
                    } else {
                        Log.d("MainActivity", "Error getting documents: ", task.getException());
                    }
                });
    }

    // This method is called when the user clicks on a post in the RecyclerView.
    // It creates an intent to open the PostDetailsActivity, and passes the selected post as an extra.
    public void onPostClick(int position) {
        Post post = postList.get(position);
        Intent intent = new Intent(MainActivity.this, PostDetailsActivity.class);
        intent.putExtra("Post", post);
        startActivity(intent);
    }

    // Standard lifecycle methods for an Android activity.
    // In an app like this we want to make sure a user is logged in before we show them the main activity.
    // If they are not logged in, we redirect them to the LoginActivity.
    // We also want to make sure we clear the postList when the activity is paused, stopped, or destroyed, to avoid memory leaks.

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            updateWelcomeText();
            loadPosts();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            updateWelcomeText();
            loadPosts();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "onRestart");
        updateWelcomeText();
        loadPosts();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
        postList.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
        postList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
        postList.clear();
    }

}
