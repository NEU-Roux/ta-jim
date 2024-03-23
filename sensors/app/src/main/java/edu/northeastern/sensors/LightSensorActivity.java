package edu.northeastern.sensors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

// The android sensor framework provides a light sensor that can be used to measure the ambient light level
// The light sensor is a hardware sensor that measures the amount of light in the environment
// This might be useful for applications that need to adjust the screen brightness based on the ambient light level
// In this activity we will be using the light sensor to adjust the screen brightness based on the ambient light level
public class LightSensorActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView tvLight, tvBrightnessLevel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        tvLight = findViewById(R.id.tvLight);
        tvBrightnessLevel = findViewById(R.id.tvBrightnessLevel);

        // 1.
        // Like the last activity in order to access the light sensor we need to get the sensor manager
        // then we can access the light sensor from the sensor manager. Please do so below:

        // complete part 1 here.

        // 2.
        // I've provided the onSensorChanged method for you to use to get the light sensor values
        // but I will want you to actually implement the adjustScreenBrightness method on your own
        // this method should adjust the screen brightness based on the ambient light level
        // think about what should happen to your screen brightness in a dark room vs a bright room
        // there are a few ways to do this, but here are a few hints:
        // You can use the WindowManager.LayoutParams to get the current screen brightness
        // You can use the WindowManager.LayoutParams to set the screen brightness

        // For your submission, make sure the current lux value is displayed on the screen
        // and the screen brightness level is displayed on the screen (as a percentage)
        // you can use the tvLight and tvBrightnessLevel TextViews for this

        // Remember to check if the light sensor is available on the device
        if (lightSensor == null) {
            tvLight.setText("Light sensor not available on this device");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void adjustScreenBrightness(float lux) {
        // complete part 2 here.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            String formattedLux = String.format("%.1f", lux);
            tvLight.setText("Ambient Light: " + formattedLux + " lx");
            adjustScreenBrightness(lux);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }


}
