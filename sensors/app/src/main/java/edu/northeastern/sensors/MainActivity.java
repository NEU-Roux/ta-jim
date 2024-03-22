package edu.northeastern.sensors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnAccelerometer).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AccelerometerActivity.class));
        });

        findViewById(R.id.btnLightSensor).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, LightSensorActivity.class));
        });

        findViewById(R.id.btnMagnetometer).setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, CompassActivity.class));
        });
    }
}

