package edu.northeastern.sensors;

import android.app.Activity;
import android.app.appsearch.StorageInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

// In this activity we will be combining the accelerometer and magnetometer sensors to create a compass
// The accelerometer will be used to determine the orientation of the device and the magnetometer will be used to determine the direction of the device
// Although the magnetometer alone would be fine for a simple compass, the accelerometer is used to make the compass more accurate
// think about it, if you are holding your phone flat, the magnetometer will not be able to determine the direction of the device
// but if you tilt your phone, the accelerometer will be able to tell the magnetometer the orientation of the device and the magnetometer will be able to determine the direction of the device
public class CompassActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer, magnetometer;
    private TextView tvDirection;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer);

        tvDirection = findViewById(R.id.tvDirection);

        // 1.
        // Like the previous activities, we need to get the sensor manager to access the sensors
        // this time we will need to access the accelerometer and magnetometer sensors
        // The sensor types are Sensor.TYPE_ACCELEROMETER and Sensor.TYPE_MAGNETIC_FIELD

        // complete part 1 here.
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    private String getCardinalDirection(float degrees) {
        String[] directions = { "N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW", "N" };
        return directions[(int) Math.round(((degrees % 360) / 22.5))];
    }

    // In this activity it will be called whenever the accelerometer or magnetometer values change
    // it is the doing the core work of the compass.
    // The accelerometer and magnetometer readings are stored in the accelerometerReading and magnetometerReading arrays respectively.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // here we make a copy of the accelerometer readings provided by the system so we can use them later
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // same thing here, accept we copy the magnetometer readings instead
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.length);
        }

        // Now I want you to do the following:
        // 1. Use the SensorManager.getRotationMatrix method to get the rotation matrix
        // 2. Use the SensorManager.getOrientation method to get the orientation angles
        // 3. Get the azimuth in degrees from the orientation angles
        // 4. Use the getCardinalDirection, which I have provided  method to get the cardinal direction from the azimuth in degrees

        // hints:
        // - the rotation matrix is a 3x3 matrix
        // - the orientation angles is a 3 element array
        // - the azimuth is the first element of the orientation angles array
        // - use the global variables rotationMatrix and orientationAngles to store the rotation matrix and orientation angles respectively

        // enter your code here

        tvDirection.setText("Direction: " + cardinalDirection);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}