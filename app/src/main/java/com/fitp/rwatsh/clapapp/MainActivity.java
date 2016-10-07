package com.fitp.rwatsh.clapapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mediaPlayer = MediaPlayer.create(this, R.raw.clap);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                    mediaPlayer.setVolume(1.0f, 1.0f);
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }


    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mediaPlayer.isPlaying()) {
            float proximityVal = event.values[0];
            float volume = 0.0f;
            if (proximityVal > 0) {
                volume = 1.0f;
            }
            mediaPlayer.setVolume(volume, volume); // varies from 0.0f to 1.0f
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onClickButton(View view ) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        } else {
            mediaPlayer.start();
        }
    }


}
