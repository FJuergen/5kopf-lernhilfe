package com.kopf5.lernhilfe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Einstellungen und Interface für den Lerntimer
 */

public class Lerntimer extends Fragment {

    private TextView clock;
    private TextView level;
    private LernManager manager = new LernManager(this);
    private NumberPicker picker,pausePicker;
    private AlertDialog dialog;
    private Button newTimer;
    private Button clockPause;
    private Button clockResume;

    public Lerntimer(){
        super(R.layout.activity_lerntimer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_lerntimer,container, false);
        newTimer = view.findViewById(R.id.new_timer);
        newTimer.setOnClickListener(this::newTimer);
        clockPause = view.findViewById(R.id.clock_pause);
        clockPause.setOnClickListener(this::pauseTimer);
        clockResume =  view.findViewById(R.id.clock_resume);
        clockResume.setOnClickListener(this::resumeTimer);
        clock = view.findViewById(R.id.clock);
        level = view.findViewById(R.id.level);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        dialog = builder.setTitle(R.string.dialog_title)
                .setView(getLayoutInflater().inflate(R.layout.clock_dialog,null))
                .setPositiveButton(R.string.start, (dialog, which) -> startTimer())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .create();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                level.setText(String.format(Locale.GERMANY,"Level : %d", UserManager.manager.getLevel()));
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
            assert picker != null;
            picker.setMinValue(0);
            picker.setMaxValue(24);
            picker.setFormatter(value -> String.valueOf(value*5));
            pausePicker = dialog.findViewById(R.id.pausePicker);
            assert pausePicker != null;
            pausePicker.setMinValue(0);
            pausePicker.setMaxValue(5);
        }
    }

    public void updateTimerText(String timerText){
        clock.setText(timerText);
    }

    public void startTimer() {

        manager.startTimer(picker.getValue()*60000*5,pausePicker.getValue());
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