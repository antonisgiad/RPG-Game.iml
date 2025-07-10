package living.heroes;

import living.Living;
import utils.RandomUtil;

public class Sorcerer extends Hero {
    //Variables
    double increasedSorcererDexterity, increasedSorcererAgility;

    //Functions
    //Constructor
    public Sorcerer(String livingName) {
        super(livingName);
        this.increasedSorcererAgility = 20 + agility; // Fully random
        this.increasedSorcererDexterity = 20 + dexterity; // Fully random
    }
}