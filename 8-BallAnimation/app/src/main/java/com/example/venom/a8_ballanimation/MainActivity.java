package com.example.venom.a8_ballanimation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
/*
fade animations
https://www.tutorialspoint.com/android/android_animations.htm

using accelerometers
https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125

 */
public class MainActivity extends AppCompatActivity implements SensorEventListener{

    //sensor variables instances to detect shaking
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //OnClick component set up in activity_main.xml
    public void fadeOutImage(View view){
        ImageView image = (ImageView) findViewById(R.id.imageViewCenterEight);
        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        image.startAnimation(animation);
        image.setVisibility(View.INVISIBLE);
    }

    //OnClick component set up in activity_main.xml
    public void fadeInImage(View view){

        ImageView image = (ImageView) findViewById(R.id.imageViewCenterEight);
        image.setVisibility(View.VISIBLE);
        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        image.startAnimation(animation);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if((curTime - lastUpdate) > 100){
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y +z - last_x - last_y - last_z)/diffTime * 10000;
                if(speed > SHAKE_THRESHOLD){
                    View view = (View) findViewById(R.id.imageViewCenterEight);
                    fadeOutImage(view);
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
