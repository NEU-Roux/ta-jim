package com.example.androidlifecycle;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * BadLifecycleActivity demonstrates an improper approach to managing an activity's lifecycle, particularly in handling video playback with a VideoView.
 * This activity lacks critical lifecycle methods such as onPause(), onResume(), and onStop(), leading to potential issues in resource management and user experience.
 *
 * Key Issues:
 * - Missing onPause() and onResume(): Essential for pausing and resuming video playback when the activity's visibility changes. Without these, the video continues playing in the background, wasting resources and potentially disrupting the user.
 * - Absence of onStop(): Failing to implement onStop() means the activity does not properly handle the scenario when it's no longer visible, potentially causing video playback to continue in the background.
 * - Over-reliance on onDestroy(): While onDestroy() is used to stop video playback, it's not always guaranteed to be called by the system. Solely relying on it for resource release can lead to memory leaks.
 * - Incomplete state restoration: The activity attempts to restore video playback state in onCreate(), but it lacks corresponding state saving in onSaveInstanceState(), leading to incomplete state management.
 *
 */
public class BadLifecycleActivity extends AppCompatActivity {


    private VideoView videoView;
    private int currentPosition = 0; // To save the current position of video playback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        videoView = findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.trailer;
        videoView.setVideoURI(Uri.parse(path));

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            currentPosition = savedInstanceState.getInt("currentPosition", 0);
        }
        videoView.seekTo(currentPosition);
    }

    @Override
    protected void onStart() {
        Log.d("Lifecycle", "BadLifeCycleActivity: onStart");
        super.onStart();
        videoView.start(); // Start the video when the activity starts
    }

    @Override
    protected void onDestroy() {
        Log.d("Lifecycle", "BadLifeCycleActivity: onDestroy");
        super.onDestroy();
        videoView.stopPlayback(); // Stop the video playback and release resources
    }

}