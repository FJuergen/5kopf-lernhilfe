package com.kopf5.lernhilfe;


public class UserManager {
    public static UserManager manager = new UserManager();

    private int level = 1;
    private float xp;
    private float levelThreshold = 100;
    private long brainPoints;

    private UserManager(){}


    public int getLevel() {
        return level;
    }


    public float getXp() {
        return xp;
    }

    public void addXp(float xp) {
        this.xp += xp;
        brainPoints += xp;

        if(this.xp > levelThreshold){
            level++;
            this.xp=xp-levelThreshold;
            levelThreshold*=1.2;
        }
    }
}
