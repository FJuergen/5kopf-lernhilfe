package com.kopf5.lernhilfe;


import android.content.SharedPreferences;

public class UserManager {
    public static UserManager manager = new UserManager();

    private int level;
    private float xp;
    private float levelThreshold;
    private long brainPoints;
    private final int maxLevel = 50;

    private UserManager(){
        // lade Werte; setze Standardwerte, falls noch nie addXp(float xp) aufgerufen wurde
        readSharedPreferences();
    }

    public int getLevel() {
        return level;
    }

    public float getXp() {
        return xp;
    }

    public float getLevelThreshold() { return levelThreshold; }

    public long getBrainPoints() { return brainPoints; }

    public void addXp(float xp) {
        SharedPreferences mySP = Lerntimer.getContextOfApplication().getSharedPreferences("UserInfo",0);
        SharedPreferences.Editor editor = mySP.edit();

        this.xp += xp;
        brainPoints += xp;

        //level-up
        if(this.xp > levelThreshold && this.level <= maxLevel){
            level++;
            this.xp=xp-levelThreshold;
            //neues level betraegt altes levelThreshold * 1.2
            levelThreshold*=1.2;
            editor.putInt("level",level);
            editor.putFloat("levelThreshold",levelThreshold);
        }
        editor.putFloat("xp",xp);
        editor.putLong("brainpoints",brainPoints);
        editor.commit();
    }

    //beim starten werden xp,level,levelThreshold und brainpoints geladen
    private void readSharedPreferences() {
        SharedPreferences mySP = Lerntimer.getContextOfApplication().getSharedPreferences("UserInfo",0);
        this.level = mySP.getInt("level",1);
        this.xp = mySP.getFloat("xp",0);
        this.levelThreshold = mySP.getFloat("levelThreshold", 100);
        this.brainPoints = mySP.getLong("brainpoints",0);
    }
}
