package com.sba.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView circlebg ,anchor;
    private Button startbtn , stopbtn, resetbtn;
    private Animation clockrotate;
    public TextView timeView;

    private int mseconds =0;
    private  boolean running =false;

    private boolean startstate=true,stopstate=false,reststate=false,animstate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUiView();

        if(savedInstanceState != null) {
            mseconds=savedInstanceState.getInt("mseconds");
            running=savedInstanceState.getBoolean("running");
            startstate=savedInstanceState.getBoolean("ss");
            stopstate=savedInstanceState.getBoolean("sts");
            reststate=savedInstanceState.getBoolean("rs");
            animstate=savedInstanceState.getBoolean("as");

            if(startstate)
                startbtn.setAlpha(1);
            else
                startbtn.setAlpha(0);
            if(reststate)
                resetbtn.setAlpha(1);
            else
                resetbtn.setAlpha(0);
            if(stopstate)
                stopbtn.setAlpha(1);
            else
                stopbtn.setAlpha(0);
            if(animstate)
                anchor.startAnimation(clockrotate);
            else
                anchor.clearAnimation();

        }


        runTimer();


    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("mseconds",mseconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("ss",startstate);
        savedInstanceState.putBoolean("sts",stopstate);
        savedInstanceState.putBoolean("rs",reststate);
        savedInstanceState.putBoolean("as",animstate);

    }

    private void setUiView() {
        circlebg =findViewById(R.id.circlebg);
        anchor= findViewById(R.id.anchor);
        startbtn =findViewById(R.id.startbtn);
        stopbtn =findViewById(R.id.stopbtn);
        resetbtn=findViewById(R.id.resetbtn);
        timeView=findViewById(R.id.time);
        clockrotate = AnimationUtils.loadAnimation(this,R.anim.clockrotate);

        stopbtn.setAlpha(0);
        resetbtn.setAlpha(0);
    }

    public void startTimer(View view) {
        running=true;
        startstate=false;
        stopstate=true;
        reststate=true;
        animstate=true;
        anchor.startAnimation(clockrotate);
        stopbtn.animate().alpha(1).translationY(-80).setDuration(300).start();
        resetbtn.animate().alpha(1).translationY(-80).setDuration(300).start();
        startbtn.animate().alpha(0).setDuration(300).start();
    }

    public void stopTimer(View view) {
        running=false;

        startstate=true;
        stopstate=false;
        animstate=false;

        anchor.clearAnimation();
        startbtn.animate().alpha(1).translationY(-80).setDuration(300).start();
        stopbtn.animate().alpha(0).setDuration(300).start();
    }

    public void resetTimer(View view) {
        running = false;
        mseconds=0;

        startstate=true;
        stopstate=false;
        reststate=false;
        animstate=false;

        anchor.clearAnimation();
        startbtn.animate().alpha(1).translationY(-80).setDuration(300).start();
        stopbtn.animate().alpha(0).setDuration(300).start();
        resetbtn.animate().alpha(0).setDuration(300).start();
    }

    public void runTimer() {
        final Handler handler =new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int     min=mseconds/3600,
                        sec=(mseconds%3600)/60;

                String time = String.format("%02d:%02d",min,sec);

                timeView.setText(time);

                if(running)
                    mseconds++;

                handler.postDelayed(this,1);
            }
        });
    }


}
