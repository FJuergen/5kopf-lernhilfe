package com.kopf5.lernhilfe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

/*
 * Einstellungen und Interface für den Lerntimer
 */

public class Lerntimer extends AppCompatActivity {


    TextView clock;
    LernManager manager = new LernManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lerntimer);
        clock = findViewById(R.id.clock);
    }

    public void updateTimerText(String timerText){
        clock.setText(timerText);
    }

    public void startTimer(View view) {
        manager.startTimer(1000);
    }
}