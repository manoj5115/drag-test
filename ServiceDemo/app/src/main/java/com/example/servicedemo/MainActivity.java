package com.example.servicedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Start the  service
    public void startNewService(View view) {

        startService(new Intent(this, MyService.class));
    }

    // Stop the  service
    public void stopNewService(View view) {

        stopService(new Intent(this, MyService.class));
    }
}