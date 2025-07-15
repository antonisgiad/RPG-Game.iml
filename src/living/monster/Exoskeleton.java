package living.monster;

import utils.RandomUtil;

public class Exoskeleton extends Monster {
    //Variables
    private double increasedDefense;

    //Functions

    //Constructor
    public Exoskeleton(String livingName, double increasedDefense) {
        super(livingName);
        this.increasedDefense = increasedDefense + getDefense();
    }

    //Getters & Setters
    public double getIncreasedDefense() {
        return increasedDefense;
    }

    public void setIncreasedDefense(double increasedDefense) {
        this.increasedDefense = increasedDefense;
    }
}