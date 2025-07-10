package living.heroes;

import living.Living;
import utils.RandomUtil;

public class Paladin extends Hero {
    //Variables
    double increasedPaladinStrength, increasedPaladinDexterity;

    //Functions
    //Constructor
    public Paladin(String livingName) {
        super(livingName);
        this.increasedPaladinStrength = 10 + strength; // Fully random
        this.increasedPaladinDexterity = 10 + dexterity; // Fully random
    }
}