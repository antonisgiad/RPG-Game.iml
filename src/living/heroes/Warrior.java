package living.heroes;

public class Warrior extends Hero {
    //Variables

    //Functions

    //Constructor
    public Warrior(String livingName) {
        super(livingName);
        this.strength = 20; // Fully random
        this.agility = 15;  // Fully random
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
            strength += 12; //Increased strength on level up (Warrior)
            agility += 7; //Increased agility on level up (Warrior)
            dexterity += 5;
            System.out.println(getLivingName() + " leveled up to " + livingLevel + "!");
        }
    }
}