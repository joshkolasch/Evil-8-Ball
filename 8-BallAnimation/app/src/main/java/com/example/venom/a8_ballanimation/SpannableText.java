package com.example.venom.a8_ballanimation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SpannableText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_text);
    }

    public void textAnimation(View view){
        TextView tvEven = (TextView) findViewById(R.id.textViewEven);
        TextView tvOdd = (TextView) findViewById(R.id.textViewOdd);

        String fillString = "Try Again Later";

        //Complete TODO (2) this is where I need to do the spannable text stuff
        SpannableString evenString = new SpannableString(fillString);
        SpannableString oddString = new SpannableString(fillString);

        for(int i = 0; i < evenString.length(); i++){
            if(i%2 == 0) {
                evenString.setSpan(new ForegroundColorSpan(Color.argb(0, 0, 0, 0)), i, i+1, 0);
            }
        }

        for(int i = 0; i < oddString.length(); i++){
            if(i%2 == 1){
                oddString.setSpan(new ForegroundColorSpan(Color.argb(0, 0, 0, 0)), i, i, 0);
            }
        }

        tvEven.setText(evenString);
        animate(tvEven);

    }

    public void animate(View view){

        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        //view.startAnimation(animation);

        view.startAnimation(animation);
    }
}
