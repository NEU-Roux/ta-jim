package com.example.androidlifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     * This callback is absolutely necessary. It will trigger when the system first creates the activity.
     * onCreate() performs the basic application startup logic that happens only once for the entire life of the activity.
     * You might use it to bind some data to lists, associate the activity with the ViewModel, and instantiate some class-scope variables.
     *
     * @param savedInstanceState - is the Bundle object containing the activities previously saved state. If the activity never existed before,
     *                             the value of the Bundle object is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Lifecycle", "MainActivity: onCreate()");

        initializeGoodLifecycleButton();
        initializeBadLifecycleButton();
        initializeUglyLifecycleButton();
    }

    /**
     * The following 3 methods are private methods that help initialize buttons for navigating to our three other activities.
     * We must set an OnClickListener that creates an Intent to start the select the corresponding activity depending on what button is clicked.
     */
    private void initializeGoodLifecycleButton() {
        Button goodLifecycleButton = findViewById(R.id.goodLifecycleButton);
        goodLifecycleButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GoodLifecycleActivity.class);
            startActivity(intent);
        });
    }

    private void initializeBadLifecycleButton() {
        Button badLifecycleButton = findViewById(R.id.badLifecycleButton);
        badLifecycleButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BadLifecycleActivity.class);
            startActivity(intent);
        });
    }

    private void initializeUglyLifecycleButton() {
        Button uglyLifecycleButton = findViewById(R.id.uglyLifecycleButton);
        uglyLifecycleButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, UglyLifecycleActivity.class);
            startActivity(intent);
        });
    }

    /**
     * This call makes the activity visible to the user and prepares for the activity to enter the foreground and become interactive.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "MainActivity: onStart()");
    }

    /**
     * When the activity enters the resumed state, it comes to the foreground, and the system invokes the onResume() callback.
     * This is the state in which the app interacts with the user. The app will stay in this state until something happens to take focus away from the app,
     * example: receiving a phone call, navigating to another activity, or the device screen turning off.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "MainActivity: onResume()");
    }

    /**
     * The system will call this method as the first indication that the user is leaving your activity, though it does not always mean the activity is being destroyed.
     * It indicates that the activity is no longer in the foreground, but will actually still be visible if the user is in a multi-window mode.
     *
     * there are several reasons why an activity might enter this state such as:
     * an event interrupts app execution (the app pauses)
     * the opening of a semi-transparent activity like a dialog pauses the activity it covers.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "MainActivity: onPause()");
    }

    /**
     * When an activity is no longer visible to the user, it enters the stopped state, and the system invokes onStop().
     * This will occur when the newly launched activity covers the whole screen.
     * The system will also call onStop() when the activity finishes running and is about to terminate.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "MainActivity: onStop()");
    }

    /**
     * onDestroy() is called before the activity is destroyed. It will invoke this callback for one of two reasons:
     * 1. The activity is finishing, due to the user completely dismissing the activity or due to finish() being called on the activtiy.
     * 2. The system is temporarily destroying the activity due to a configuration change, like device rotation or entering multi-window mode.
     *
     * Most importantly this method releases all resources not released by earlier callbacks like onStop().
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "MainActivity: onDestroy()");
    }

}