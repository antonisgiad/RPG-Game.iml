package living;

import utils.RandomUtil;

public class Living {
    //Variables
    String livingName;
    int livingLevel;
    double healthPower;
    boolean isPassedOut;

    //Functions
    //Constructor
    public Living(String livingName) {
        this.livingName = livingName;
        this.livingLevel = 1;
        this.healthPower = 10;
        this.isPassedOut = false;
    }
    //Check if Living has passed out
    public boolean passedOut() {
        if (healthPower <= 0) {
            isPassedOut = true;
            System.out.println(livingName + " has passed out...");
        } else {
            isPassedOut = false;
        }
        return isPassedOut;
    }
    //Getters & Setters
    public String getLivingName() {return livingName;}

    public void setLivingName(String livingName) {
        this.livingName = livingName;
    }

    public int getLivingLevel() {return livingLevel;}

    public void setLivingLevel(int livingLevel) {
        this.livingLevel = livingLevel;
    }

    public double getHealthPower() {
        return healthPower;
    }

    public void setHealthPower(double healthPower) {
        this.healthPower = healthPower;
    }

    public boolean isPassedOut() {
        return isPassedOut;
    }

    public void setPassedOut(boolean passedOut) {
        isPassedOut = passedOut;
    }
}