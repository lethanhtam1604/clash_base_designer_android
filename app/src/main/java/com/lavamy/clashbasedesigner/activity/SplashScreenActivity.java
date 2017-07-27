package com.lavamy.clashbasedesigner.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.lavamy.clashbasedesigner.R;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 23) {
            ArrayList<String> requestList = new ArrayList<>();

            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                requestList.add(Manifest.permission.INTERNET);
            }

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestList.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }

            if (requestList.size() > 0) {
                String[] requestArr = new String[requestList.size()];
                requestArr = requestList.toArray(requestArr);
                ActivityCompat.requestPermissions(this, requestArr, 1);
            } else {
                begin();
            }
        } else {
            begin();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        boolean isGratedFully = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isGratedFully = false;
                break;
            }
        }

        if (isGratedFully) {
            begin();
        } else {
            this.finish();
        }
    }

    private void begin() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    init();
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {

                }

            }
        };
        timerThread.start();
    }

    private void init() {

    }
}
