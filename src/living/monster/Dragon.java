package living.monster;

import utils.RandomUtil;

public class Dragon extends Monster {
    //Variables
    double increasedMinDamage, increasedMaxDamage;
    double[] increasedDamageRange = {increasedMinDamage, increasedMaxDamage};

    //Functions
    //Constructor
    public Dragon(String livingName) {
        super(livingName);
        // Randomly generate increased min/max damage for the Dragon
        this.increasedMinDamage = RandomUtil.randomStat(15, 35) + minDamage; // Fully random
        this.increasedMaxDamage = RandomUtil.randomStat(25, 50) +  maxDamage; // Fully random
        this.increasedDamageRange = new double[] {increasedMinDamage, increasedMaxDamage};
    }
}