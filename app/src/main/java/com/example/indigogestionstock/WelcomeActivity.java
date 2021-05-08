package com.example.indigogestionstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
          timer=new Timer();
          timer.schedule(new TimerTask() {
              @Override
              public void run() {
                  Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                  startActivity(intent);
              }
          },3000);

    }
}