package living.heroes;

import utils.RandomUtil;

public class Warrior extends Hero {
    //Variables
    double increasedWarriorStrength, increasedWarriorAgility;

    //Functions
    //Constructor
    public Warrior(String livingName) {
        super(livingName);
        this.increasedWarriorStrength = 15 + strength; // Fully random
        this.increasedWarriorAgility = 15 + agility;  // Fully random
    }
    //Override levelUp from Hero
    @Override
    public void levelUp() {
        int livingLevel = getLivingLevel();
        int requiredExperience = livingLevel * 10;
        if (experience >= requiredExperience) {
            livingLevel++;
            experience -= requiredExperience;
            //Increase Statistics
            strength += increasedWarriorStrength + 12; //Increased strength on level up (Warrior)
            agility += increasedWarriorAgility + 7; //Increased agility on level up (Warrior)
            dexterity += dexterity + 5;
            System.out.println(getLivingName() + " leveled up to " + livingLevel + "!");
        }
    }
}