package com.example.szenzorok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    public Ball ball = null;
    public Handler handler = new Handler();
    public Timer timer = null;
    public TimerTask timerTask = null;
    public int kepernyoSzelesseg, kepernyoMagassag;
    public android.graphics.PointF ballPos, ballSpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //teljeskepernyo,titlebar eltunik
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN); //teljeskepernyo

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FrameLayout frameLayout = findViewById(R.id.frameLayout);
        //kepernyo meret lekerese
        Display display = getWindowManager().getDefaultDisplay();
        kepernyoSzelesseg = display.getWidth();
        kepernyoMagassag = display.getHeight();
        ballPos = new android.graphics.PointF();
        ballSpd = new android.graphics.PointF();

        //labda pozicio es sebesseg beallitasa
        ballPos.x = kepernyoSzelesseg/2;
        ballPos.y = kepernyoMagassag/2;
        ballSpd.x = 0;
        ballSpd.y = 0;



        //labda elkeszitese
        ball = new Ball(MainActivity.this,ballPos.x,ballPos.y,25);

        frameLayout.addView(ball);//labda hozzaadasa a kepernyore
        frameLayout.invalidate(); // hivja az ondraw metodust a ball.java osztalyban!!!!!

        //sebessegmero hozzaadasa


        ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                //golyo sebesseg valtoztatas
                ballSpd.x = -sensorEvent.values[0];
                ballSpd.y = sensorEvent.values[1];

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                //semmit
            }
        },((SensorManager)getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),SensorManager.SENSOR_DELAY_NORMAL);


        //onTouch listener
        //onTouchListener

        frameLayout.setOnTouchListener(new android.view.View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ballPos.x = motionEvent.getX();
                ballPos.y = motionEvent.getY();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                ballPos.x += ballSpd.x;
                ballPos.y += ballSpd.y;
                //kiesik oldalrol akkor masik oldalrol jojjon elo
                if (ballPos.x >kepernyoSzelesseg) ballPos.x=0;
                if (ballPos.y >kepernyoMagassag) ballPos.y=0;

                if (ballPos.x <0 ) ballPos.x=kepernyoSzelesseg;
                if (ballPos.y <0 ) ballPos.y=kepernyoMagassag;



                ball.x = ballPos.x;
                ball.y = ballPos.y;

                ball.invalidate();

            }
        };
        timer.schedule(timerTask,10,10); //timer elinditasa
        super.onResume();
    }



    }

