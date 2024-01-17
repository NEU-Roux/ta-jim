package com.example.androidlifecycle;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * GoodLifecycleActivity demonstrates proper management of a VideoView within the activity lifecycle.
 * It handles the video playback in a way that respects the user's context and the system's resources.
 * It covers the essential callback methods: onCreate(), onStart(), onResume(), onPause(), onStop(), onDestroy()
 */
public class GoodLifecycleActivity extends AppCompatActivity {

    private VideoView videoView;
    private int currentPosition = 0; // To save the current position of video playback

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        Log.d("Lifecycle", "GoodLifecycleActivity: onCreate");

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
        super.onStart();
        Log.d("Lifecycle", "GoodLifecycleActivity: onStart");
        videoView.start(); // Start the video when the activity starts
    }

    @Override
    protected void onResume() {
        Log.d("Lifecycle", "GoodLifecycleActivity: onResume");
        super.onResume();
        videoView.seekTo(currentPosition); // Seek to the saved position
        videoView.start(); // Resume the video when the activity resumes
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "GoodLifecycleActivity: onPause");
        videoView.pause(); // Pause the video when the activity is paused
        currentPosition = videoView.getCurrentPosition(); // Save the current position
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "GoodLifecycleActivity: onStop");
        // If the video is still playing when the activity is no longer visible, pause it.
        if (videoView.isPlaying()) {
            videoView.pause();
            currentPosition = videoView.getCurrentPosition(); // Save the current position
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("Lifecycle", "GoodLifecycleActivity: onDestroy");
        super.onDestroy();
        videoView.stopPlayback(); // Stop the video playback and release resources
    }

    /**
     * The onSaveInstanceState method is called before placing the activity in a background state,
     * such as when another activity comes into the foreground. This method is used to save any dynamic
     * state in the activity into the given Bundle, typically to preserve the state across configuration changes
     * like screen rotations or when the activity is temporarily destroyed and recreated by the system.
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPosition", videoView.getCurrentPosition()); // Save the current position
    }

}


