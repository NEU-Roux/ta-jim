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
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
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

    private void adjustScreenBrightness(float lux) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();

        float brightness = (float) (1 - (float)Math.log1p(lux) / Math.log1p(10000));
        brightness = Math.max(brightness, 0.1f);
        brightness = Math.min(brightness, 1.0f);

        layoutParams.screenBrightness = brightness;
        getWindow().setAttributes(layoutParams);

        tvBrightnessLevel.setText("Screen Brightness Level: " + Math.round(brightness * 100) + "%");
    }
}
