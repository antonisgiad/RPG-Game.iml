package living.monster;

import utils.RandomUtil;

public class Spirit extends Monster {
    //Variables
    private double increasedDodgeChance;

    //Functions

    //Constructor
    public Spirit(String livingName, double increasedDodgeChance) {
        super(livingName);
        this.increasedDodgeChance = increasedDodgeChance + getDodgeChance();
    }

    //Getters & Setters
    public double getIncreasedDodgeChance() {
        return increasedDodgeChance;
    }

    public void setIncreasedDodgeChance(double increasedDodgeChance) {
        this.increasedDodgeChance = increasedDodgeChance;
    }
}