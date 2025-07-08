public class Spirit extends Monster{
    //Variables
    double increased_dodgeChance;

    //Functions
    //Constructor
    public Spirit(String living_name, int living_level, double healthPower, double defense, double dodgeChance,
                  double minDamage, double maxDamage, double[] damageRange, double increased_dodgeChance) {

        super(living_name, living_level, healthPower, defense, dodgeChance, minDamage, maxDamage, damageRange);
        this.increased_dodgeChance = increased_dodgeChance + dodgeChance; //Increased dodge chance (Spirit)
    }
}