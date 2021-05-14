package com.kopf5.lernhilfe;

import android.os.CountDownTimer;

import static com.kopf5.lernhilfe.IsRunning.*;

enum IsRunning{
    RUNNING,
    PAUSED,
    STOPPED
}

public class LernManager {

    Lerntimer parent;
    CountDownTimer timer = null;
    public IsRunning isRunning = RUNNING;

    long pausedTimerTime = 0;


    public LernManager(Lerntimer parentActivity){
        parent = parentActivity;
    }

    public void stopTimer(){
        timer.cancel();
        timer = null;
        isRunning = STOPPED;
    }

    public void pauseTimer(){
        System.out.println("paused Timer");
        stopTimer();
        isRunning = PAUSED;
    }

    public void resumeTimer(){
        startTimer(pausedTimerTime);
    }

    public void startTimer(long length){
        if(timer==null) {
            timer = new CountDownTimer(length, 100) {

                @Override
                public void onTick(long millisUntilFinished) {
                    parent.updateTimerText(millisUntilFinished/60000 + " : " +  String.format("%02d",(millisUntilFinished/1000)%60));
                    pausedTimerTime = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    timer = null;
                }
            }.start();
            isRunning = RUNNING;
        }
    }
}
