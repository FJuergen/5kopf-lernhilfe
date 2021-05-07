package com.kopf5.lernhilfe;

import android.os.CountDownTimer;

public class LernManager {

    Lerntimer parent;
    CountDownTimer timer = null;

    public LernManager(Lerntimer parentActivity){
        parent = parentActivity;
    }
    public void stopTimer(){
        timer = null;
    }

    public void startTimer(long length){
        if(timer==null) {
            timer = new CountDownTimer(length, 100) {

                @Override
                public void onTick(long millisUntilFinished) {
                    parent.updateTimerText(millisUntilFinished + " ");
                }

                @Override
                public void onFinish() {
                    timer = null;
                }
            }.start();
        }
    }
}
