package living.monster;

import utils.RandomUtil;

public class Spirit extends Monster {
    //Variables
    double increasedDodgeChance;

    //Functions
    //Constructor
    public Spirit(String livingName) {
        super(livingName);
        // Spirits are known for high dodge and magical attacks
        this.increasedDodgeChance = RandomUtil.randomStat(0.15, 0.40) + dodgeChance; // Fully random
    }
}