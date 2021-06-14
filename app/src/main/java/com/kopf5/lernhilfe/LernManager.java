package com.kopf5.lernhilfe;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import java.util.Locale;

import static com.kopf5.lernhilfe.IsRunning.*;

enum IsRunning{
    RUNNING,
    PAUSED,
    STOPPED
}

public class LernManager {

    Lerntimer parent;
    public static LernManager manager = new LernManager();
    CountDownTimer timer = null;
    CountDownTimer pauseTimer = null;
    public IsRunning isRunning = STOPPED;
    SharedPreferences spf = MainActivity.spf;
    long pausedTimerTime = 0;
    long xpCounter;
    long remainingPauses;
    long segmentLength;
    long pauseLength = 300000;


    private LernManager(){}

    public void stopTimer(){
        if(pauseTimer!=null) {
            pauseTimer.cancel();
        }
        timer.cancel();
        isRunning = STOPPED;
    }

    public void pauseTimer(){
        stopTimer();
        isRunning = PAUSED;
    }

    public void resumeTimer(){
        initTimer(pausedTimerTime, remainingPauses);
    }

    public void initTimer(long length, long pauses){
        if(isRunning == RUNNING) {
            stopTimer();
        }
        pauseLength = spf.getInt("pause_length",300000);
        remainingPauses = pauses;
        if(isRunning==PAUSED) {
            segmentLength = pausedTimerTime;
        }else{
            segmentLength = spf.getInt("phase_length", 1500000);
        }
        xpCounter = 0;
        startTimer();
    }

    private void startTimer(){
        isRunning = RUNNING;
        timer = new CountDownTimer(segmentLength, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                parent.updateTimerText("Lernphase:\n" + millisUntilFinished / 60000 + " : " + String.format(Locale.GERMANY, "%02d", (millisUntilFinished / 1000) % 60));
                pausedTimerTime = millisUntilFinished;
                xpCounter += 100;
                if (xpCounter > 60000) {
                    UserManager.manager.addXp(10);
                    xpCounter = 0;
                }
            }
            @Override
            public void onFinish() {
                if(remainingPauses>0) {
                    pauseTimer = new CountDownTimer(pauseLength, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            parent.updateTimerText("Pause:\n" + millisUntilFinished / 60000 + " : " + String.format(Locale.GERMANY, "%02d", (millisUntilFinished / 1000) % 60));
                            pausedTimerTime = millisUntilFinished;
                        }

                        @Override
                        public void onFinish() {
                            remainingPauses--;
                            startTimer();
                        }
                    }.start();
                }
                else{
                    isRunning = STOPPED;
                    parent.reset();
                }
            }
        }.start();
    }
}
