package com.example.venom.a8_ballanimation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

/*
fade animations
https://www.tutorialspoint.com/android/android_animations.htm

using accelerometers
https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125

using spannableStrings to make the text appear effects
http://androidcocktail.blogspot.com/2014/03/android-spannablestring-example.html

Note: Handlers were replaced with a fade_in_text_even.xml with a startOffset
setting up a delay for a function using Handlers
http://stackoverflow.com/questions/4111905/how-do-you-have-the-code-pause-for-a-couple-of-seconds-in-android

 */
public class MainActivity extends AppCompatActivity implements SensorEventListener{

    //sensor variables instances to detect shaking
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 800;

    TextView mEvenTextView;
    TextView mOddTextView;
    private Handler mHandler = new Handler();
    private static final int ODD_TEXT_DELAY = 200;

    private static final boolean IS_EVEN = true;
    private static final boolean NOT_EVEN = false;

    //TODO (4) create a Contract class to populate this array from a database?
    private final String[] phrases = new String[]{"That's not gonna fuckin happen.", "Fuck if i know", "Shit happens", "Suck it up buttercup", "For fucks sake, yes!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        mEvenTextView = (TextView) findViewById(R.id.textViewCenterEven);
        mOddTextView = (TextView) findViewById(R.id.textViewCenterOdd);
        mEvenTextView.setVisibility(View.INVISIBLE);
        mOddTextView.setVisibility(View.INVISIBLE);
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //TODO (1) Abstract this function away. Have the imageView call another function that will call this.
    //Consider removing the imageView instance and pass it in from an earlier function
    //OnClick component set up in activity_main.xml
    public void fadeOutImage(View view){
        ImageView image = (ImageView) findViewById(R.id.imageViewCenterEight);
        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        image.startAnimation(animation);
        image.setVisibility(View.INVISIBLE);
        textAppear();
    }

    public void textAppear(){

        //TODO (3) create a function to return a string that will be used in this function
        //String fillText = "Try Again Later";

        Random random = new Random();
        int rand = random.nextInt(phrases.length);
        String fillText = phrases[rand];

        //Completed (2) this is where I need to do the spannable text stuff
        SpannableString evenString = new SpannableString(fillText);
        SpannableString oddString = new SpannableString(fillText);

        for(int i = 0; i < evenString.length(); i++){
            if(i%2 == 0) {
                //TODO (5) should the last value in this function be Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                evenString.setSpan(new ForegroundColorSpan(Color.argb(0, 0, 0, 0)), i, i+1, 0);
            }
        }

        for(int i = 0; i < oddString.length(); i++){
            if(i%2 == 0) {

            }else{
                oddString.setSpan(new ForegroundColorSpan(Color.argb(0, 0, 0, 0)), i, i+1, 0);
            }
        }

        mEvenTextView.setText(evenString);
        mOddTextView.setText(oddString);

        fadeInText(IS_EVEN);
        fadeInText(NOT_EVEN);
        mOddTextView.bringToFront();

        /*mHandler.postDelayed(new Runnable(){
            public void run(){
                fadeInText(NOT_EVEN);
            }
        }, ODD_TEXT_DELAY);*/

    }

    //OnClick component set up in activity_main.xml
    public void fadeInImage(View view){

        ImageView image = (ImageView) findViewById(R.id.imageViewCenterEight);
        image.setVisibility(View.VISIBLE);
        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        image.startAnimation(animation);

        mEvenTextView.setVisibility(View.INVISIBLE);
        mOddTextView.setVisibility(View.INVISIBLE);
    }

    public void fadeInText(boolean isEven){
        TextView tv;
        if(isEven == IS_EVEN){
            tv = mEvenTextView;
            tv.setVisibility(View.VISIBLE);
            Animation animation =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_text_even);
            tv.startAnimation(animation);
        }else if(isEven == NOT_EVEN){
            tv = mOddTextView;
            tv.setVisibility(View.VISIBLE);
            Animation animation =
                    AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
            tv.startAnimation(animation);
        }

        //tv.setAlpha((float) 0);

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

    public void startSpannableActivity(View view){
        Intent intent = new Intent(this, SpannableText.class);
        startActivity(intent);
    }
}
