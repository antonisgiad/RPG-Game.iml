package living.monster;

import utils.RandomUtil;

public class Exoskeleton extends Monster {
    //Variables
    double increasedDefense;

    //Functions
    //Constructor
    public Exoskeleton(String livingName) {
        super(livingName);
        this.increasedDefense = RandomUtil.randomStat(10, 25) + defense; // Fully random
    }
}