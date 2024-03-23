package edu.northeastern.sensors;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

// This activity will display the accelerometer readings on the screen
// The accelerometer sensor measures the acceleration of the device
// It is a pretty simple sensor to use, it has three values, one for each axis (X, Y, Z)
// here we will examine how you can initialize SensorEventListener, SensorManager and Sensor to get the accelerometer readings
public class AccelerometerActivity extends Activity implements {

    // 1)
    // You might notice an error in the code,
    // In order for us to use the accelerometer sensor, we need to implement an interface
    // You can learn more about the interface that you will need here:
    // https://developer.android.com/reference/android/hardware/SensorEventListener
    // Once you've read through the documentation, implement the interface in the class declaration

    private SensorManager sensorManager; // The SensorManager is used to get the sensor service, it is the main entry point to the sensors
    private Sensor accelerometer; // The accelerometer sensor, this is the sensor we will be using in this activity
    private TextView tvAccelerometer; // This is the text view that will display the accelerometer readings

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        tvAccelerometer = findViewById(R.id.tvAccelerometer);

        // 2. Here we must assign the sensor service to the sensorManager.
        // The sensor service is a system service that allows you to access the sensors on the device
        // You can learn more about the sensor service here: https://developer.android.com/reference/android/hardware/SensorManager
        // Once you've read through the documentation, complete the code to assign the sensor service to the sensorManager
        // You will also need to get the accelerometer sensor from the sensorManager.

        // Hint: you might need the following methods: getSystemService, getDefaultSensor

        // 3. At the bottom of this file I have provided the code that you will need to get the accelerometer readings
        // Although we do not use the onAccuracyChanged method in this activity, you need to still implement it as a requirement of the interface.
        // Please read the documentation for the SensorEventListener interface to understand why you need to implement this method.
        // You can find the documentation here: https://developer.android.com/reference/android/hardware/SensorEventListener

        // 4. Finally, we need to handle the android lifecycle events onResume and onPause
        // think about what you should do with the sensorManager in these methods


        // Always check if the sensor is available on the device
        if (accelerometer == null) {
            tvAccelerometer.setText("Accelerometer not available on this device");
        }
    }

    // Only uncomment the below code once you've reached step 3.
    // These are the methods that you will need to complete in order to get the accelerometer readings
    /*
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            tvAccelerometer.setText("X: " + event.values[0] + "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    */

    @Override
    protected void onResume() {
        super.onResume();
        // what should you do with the sensorManager here?

    }

    @Override
    protected void onPause() {
        super.onPause();
        // what should you do with the sensorManager here?
    }

}
