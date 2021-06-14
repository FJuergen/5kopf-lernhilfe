package com.kopf5.lernhilfe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Einstellungen und Interface für den Lerntimer
 */

public class Lerntimer extends Fragment {

    private TextView clock;
    private TextView level;
    private final LernManager manager = LernManager.manager;
    private NumberPicker picker;
    private AlertDialog dialog,infoDialog,infoDialog2;
    private Button newTimer;
    private Button clockPause;
    private Button clockResume;
    private static Context contextOfApplication;
    SharedPreferences spf = MainActivity.spf;

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
        Button timerInfo = view.findViewById(R.id.timerInfo);
        timerInfo.setOnClickListener(this::timerInfo);
        clock = view.findViewById(R.id.clock);
        level = view.findViewById(R.id.level);
        manager.parent = this;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spf = view.getContext().getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        contextOfApplication = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        dialog = builder
                .setView(getLayoutInflater().inflate(R.layout.clock_dialog,null))
                .setPositiveButton(R.string.start, (dialog, which) -> startTimer())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .create();

        infoDialog = builder
                .setView(getLayoutInflater().inflate(R.layout.info_dialog_1,null))
                .setPositiveButton("Weiter", (dialog, which) -> timerInfo2())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .create();

        infoDialog2 = builder
                .setView(getLayoutInflater().inflate(R.layout.info_dialog_2,null))
                .setPositiveButton("Fertig", (dialog, which) -> infoDone())
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel())
                .create();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                level.setText(String.format(Locale.GERMANY,"Level : %d", spf.getInt("level",1)));
            }
        },0,100);
        if(LernManager.manager.isRunning==IsRunning.RUNNING){
            clockPause.setVisibility(View.VISIBLE);
        }
        if(LernManager.manager.isRunning==IsRunning.PAUSED){
            clockResume.setVisibility(View.VISIBLE);
        }

    }

    private void timerInfo(View view){
        infoDialog.show();
        NumberPicker picker = infoDialog.findViewById(R.id.phase_length_picker);
        assert picker!=null;
        picker.setMinValue(1);
        picker.setMaxValue(60);
        picker.setValue(spf.getInt("phase_length",25)/60000);
    }
    private void timerInfo2(){
        infoDialog2.show();
        NumberPicker picker = infoDialog2.findViewById(R.id.pausePicker);
        assert picker!=null;
        picker.setMinValue(1);
        picker.setMaxValue(15);
        picker.setValue(spf.getInt("pause_length",5)/60000);
    }
    private void infoDone(){
        spf.edit()
                .putInt("phase_length",((NumberPicker) Objects.requireNonNull(infoDialog.findViewById(R.id.phase_length_picker))).getValue()*60000)
                .putInt("pause_length",((NumberPicker) Objects.requireNonNull(infoDialog2.findViewById(R.id.pausePicker))).getValue()*60000)
                .apply();
    }

    public void reset(){
        clockPause.setVisibility(View.INVISIBLE);
        clockResume.setVisibility(View.INVISIBLE);
        clock.setText("");
    }

    public void newTimer(View view){
        dialog.show();
        if(picker==null) {
            picker = dialog.findViewById(R.id.clockPicker);
            assert picker != null;
            picker.setMinValue(1);
            picker.setMaxValue(10);
        }
    }

    public void updateTimerText(String timerText){
        clock.setText(timerText);
    }

    public void startTimer() {

        manager.initTimer(picker.getValue()*spf.getInt("phase_length",25),picker.getValue()-1);
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

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
}