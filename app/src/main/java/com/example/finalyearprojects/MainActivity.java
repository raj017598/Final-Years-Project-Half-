package com.example.finalyearprojects;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static int NEXT_SCREEN = 2500;
    Animation top, bottom;
    private ImageView logo;
    private TextView kolhan, slogan;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE);
        int fir = sharedPreferences.getInt("first",0);

        top = AnimationUtils.loadAnimation(this,R.anim.to_to_down);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom_to_up);

        logo = (ImageView)findViewById(R.id.logo);
        kolhan = (TextView)findViewById(R.id.kolhan);
        slogan = (TextView)findViewById(R.id.slogan);

        if(fir != 0)
        {
            Intent i = new Intent(MainActivity.this,Home_Activity.class);
            startActivity(i);
        }
        else
        {
            logo.setAnimation(top);
            kolhan.setAnimation(bottom);
            slogan.setAnimation(bottom);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this,Signup_Activity.class);
                    startActivity(i);
                    finish();
                }
            },NEXT_SCREEN);
        }

    }
}
