package com.berry.androidwearstopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import com.berry.androidwearstopwatch.databinding.ActivityMainBinding;


public class MainActivity extends Activity {

    private ActivityMainBinding binding;

    private Handler handler;
    private boolean isRunning = false;
    private long startTime = 0L, elapsedTime = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // handler for timer
        handler = new Handler();

        //button click listeners
        binding.btnStart.setOnClickListener(v -> startStopwatch());

        binding.btnStop.setOnClickListener(v -> stopStopwatch());

        binding.btnReset.setOnClickListener(v -> resetStopwatch());


    }


    private void startStopwatch() {
        if (!isRunning) {
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
            isRunning = true;
            binding.btnStart.setEnabled(false);
            binding.btnStop.setEnabled(true);
            binding.btnReset.setEnabled(false);
        }
    }

    private void stopStopwatch() {
        if (isRunning) {
            elapsedTime += SystemClock.uptimeMillis() - startTime;
            handler.removeCallbacks(updateTimer);
            isRunning = false;
            binding.btnStart.setEnabled(true);
            binding.btnStop.setEnabled(false);
            binding.btnReset.setEnabled(true);
        }
    }

    private void resetStopwatch() {
        if (!isRunning) {
            elapsedTime = 0L;
            binding.btnStart.setEnabled(true);
            binding.btnStop.setEnabled(true);
            binding.btnReset.setEnabled(true);
            handler.removeCallbacks(updateTimer);
            binding.tvTimer.setText(getString(R.string._00_00_00));
        }
    }

    //runnable for timer.
    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long updatedTime = elapsedTime + SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (updatedTime % 1000);
            String formattedTime = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds / 10);
            binding.tvTimer.setText(formattedTime);
            handler.postDelayed(this, 0);
        }
    };

}

