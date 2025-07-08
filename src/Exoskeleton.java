public class Exoskeleton extends Monster{
    //Variables
    double increased_defense;

    //Functions
    //Constructor
    public Exoskeleton(String living_name, int living_level, double healthPower, double defense, double dodgeChance,
                       double minDamage, double maxDamage, double[] damageRange, double increased_defense) {

        super(living_name, living_level, healthPower, defense, dodgeChance, minDamage, maxDamage, damageRange);
        this.increased_defense = increased_defense + defense; //Increased defense (Exoskeleton)
    }
}