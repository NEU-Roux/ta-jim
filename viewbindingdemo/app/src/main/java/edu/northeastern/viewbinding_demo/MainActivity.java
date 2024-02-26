package edu.northeastern.viewbinding_demo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import edu.northeastern.viewbinding_demo.databinding.ActivityMainBinding;

// Mobile Application Development:
// ViewBinding Demo

// What is viewBinding?

// View Binding is a feature that allows you to more easily write code that interacts with views.
// Instead of using findViewById, you can use a view binding object to reference views directly.

// Why use viewBinding?

// 1. It is null-safe
// Meaning that it is not possible to get a null pointer exception when accessing the views
// This is because the binding object is generated only if the layout file is correct

// 2. It is type-safe
// Meaning that the views are accessed using the correct type
// This is because the binding object is generated with the correct type for each view

// 3. It is faster than findViewById, as it is generated at compile time and not at runtime


// To use viewBinding you must have the following in your build.gradle file:
/*
        android {
            ...
            viewBinding {
                enabled = true
            }
        }
*/


public class MainActivity extends AppCompatActivity {

    // Step 1: Create a binding object
    // We use this to access the views in the layout file without using findViewById method
    // The binding object is automatically generated from the layout file name

    // Notes:
    // This is a binding object for the activity_main.xml layout file
    // The name of the binding object is generated from the name of the layout file

    // If we were using another layout file, we would have another binding object with a different name
    // For example, if we had a layout file called activity_second.xml, we would have a binding object called ActivitySecondBinding
    // Binding implicitly refers to the layout file name, so it is important to name the layout file correctly

    private ActivityMainBinding binding;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 2: Inflate the layout using the binding object
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Step 3: Set the content view to the root view of the binding object
        // We need to use getRoot() method to get the root view of the layout file
        // This is because the binding object is a wrapper around the layout file
        setContentView(binding.getRoot());

        // Step 4: Access the views using the binding object
        // We can access the views using the binding object directly
        // Here we set the text of the TextView and the video path of the VideoView using the binding object
        binding.textView.setText("Hello there!");
        binding.videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hellothere);

        // Run the video in a loop
        binding.videoView.setOnCompletionListener(mp -> binding.videoView.start());
        if (currentPosition > 0) {
            binding.videoView.seekTo(currentPosition);
            binding.videoView.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Ensure the video starts only if not coming from a paused state
        if (currentPosition == 0) {
            binding.videoView.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume video from its paused position
        if (currentPosition > 0) {
            binding.videoView.seekTo(currentPosition);
            binding.videoView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause video playback and save current position
        binding.videoView.pause();
        currentPosition = binding.videoView.getCurrentPosition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.videoView.stopPlayback();
    }

    // Save the current video playback position
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPosition", binding.videoView.getCurrentPosition());
    }

    // Restore the video playback position
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPosition = savedInstanceState.getInt("currentPosition", 0);
        // This ensures that the video starts playing from the restored position
    }
}
