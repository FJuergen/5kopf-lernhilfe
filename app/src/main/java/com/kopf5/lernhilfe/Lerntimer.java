package com.kopf5.lernhilfe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

/*
 * Einstellungen und Interface für den Lerntimer
 */

public class Lerntimer extends AppCompatActivity {

    TextView clock;
    LernManager manager = new LernManager(this);
    NumberPicker picker;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setView(getLayoutInflater().inflate(R.layout.clock_dialog,null))
        .setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startTimer(null);
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        setContentView(R.layout.activity_lerntimer);
        clock = findViewById(R.id.clock);
    }

    public void reset(){
        findViewById(R.id.clock_pause).setVisibility(View.INVISIBLE);
        findViewById(R.id.clock_resume).setVisibility(View.INVISIBLE);

    }

    public void newTimer(View view){
        dialog.show();
        if(picker==null) {
            picker = dialog.findViewById(R.id.clockPicker);
            picker.setMinValue(0);
            picker.setMaxValue(60);
        }
    }

    public void updateTimerText(String timerText){
        clock.setText(timerText);
    }

    public void startTimer(View view) {
        manager.startTimer(picker.getValue()*60000);
        dialog.hide();
        findViewById(R.id.clock_pause).setVisibility(View.VISIBLE);
    }

    public void pauseTimer(View view){
        manager.pauseTimer();
        findViewById(R.id.clock_pause).setVisibility(View.INVISIBLE);
        findViewById(R.id.clock_resume).setVisibility(View.VISIBLE);

    }

    public void resumeTimer(View view){
        manager.resumeTimer();
        findViewById(R.id.clock_resume).setVisibility(View.INVISIBLE);
        findViewById(R.id.clock_pause).setVisibility(View.VISIBLE);

    }
}