public class Warrior extends Hero {
    //Variables
    double increased_warrior_strength, increased_warrior_agility;

    //Functions
    //Constructor
    public Warrior(String living_name, double increased_warrior_strength, double increased_warrior_agility) {
        super(living_name);
        this.increased_warrior_strength = 15 + strength; //Increased strength (Warrior)
        this.increased_warrior_agility = increased_warrior_agility + agility; //Increased agility (Warrior)
    }
    //Override levelUp from Hero
    @Override
    public void levelUp() {
        int requiredExperience = living_level * 10;
        if (experience >= requiredExperience) {
            living_level++;
            experience -= requiredExperience;
            //Increase Statistics
            strength += increased_warrior_strength * 1.5; //Increased strength (Warrior)
            agility += increased_warrior_agility * 1.5; //Increased agility (Warrior)
            dexterity += dexterity * 0.5;
            System.out.println(living_name + " leveled up to " + living_level + "!");
        }
    }
}