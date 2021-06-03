package com.kopf5.lernhilfe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Timer;
import java.util.TimerTask;

/*
 * Einstellungen und Interface für den Lerntimer
 */

public class Lerntimer extends Fragment {

    private static final String TAG = "Lerntimer";

    TextView clock;
    TextView level;
    LernManager manager = new LernManager(this);
    NumberPicker picker;
    AlertDialog dialog;
    Button newTimer;
    Button clockPause;
    Button clockResume;
    public BottomNavigationView bottomNavigationView;

    public Lerntimer(){
        super(R.layout.activity_lerntimer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_lerntimer,container, false);
        newTimer = (Button) view.findViewById(R.id.new_timer);
        newTimer.setOnClickListener(this::newTimer);
        clockPause = (Button) view.findViewById(R.id.clock_pause);
        clockPause.setOnClickListener(this::pauseTimer);
        clockResume = (Button) view.findViewById(R.id.clock_resume);
        clockResume.setOnClickListener(this::resumeTimer);
        clock = view.findViewById(R.id.clock);
        level = view.findViewById(R.id.level);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(view
                .getContext());
        builder.setTitle(R.string.dialog_title);
        builder.setView(getLayoutInflater().inflate(R.layout.clock_dialog,null))
                .setPositiveButton(R.string.start, (dialog, which) -> startTimer(null))
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());
        dialog = builder.create();
//
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                level.setText(String.format("Level : %d", UserManager.manager.getLevel()));
            }
        },0,100);


    }

    public void reset(){
        clockPause.setVisibility(View.INVISIBLE);
        clockResume.setVisibility(View.INVISIBLE);
    }

    public void newTimer(View view){

        dialog.show();
        if(picker==null) {
            picker = dialog.findViewById(R.id.clockPicker);
            picker.setMinValue(0);
            picker.setMaxValue(60);
        }
    }

    public void updateTimerText(String timerText,float deltaTime){
        clock.setText(timerText);
    }

    public void startTimer(View view) {
        manager.startTimer(picker.getValue()*60000);
        dialog.hide();
        clockPause.setVisibility(View.VISIBLE);
    }

    public void pauseTimer(View view){
        manager.pauseTimer();
        clockPause.setVisibility(View.INVISIBLE);
        clockResume.setVisibility(View.VISIBLE);

    }

    public void resumeTimer(View view){
        manager.resumeTimer();
        clockResume.setVisibility(View.INVISIBLE);
        clockPause.setVisibility(View.VISIBLE);

    }
}