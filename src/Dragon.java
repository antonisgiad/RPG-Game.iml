public class Dragon extends Monster{
    //Variables
    double increased_minDamage, increased_maxDamage;
    double[] increased_damageRange = {increased_minDamage, increased_maxDamage};

    //Functions
    //Constructor
    public Dragon(String living_name, int living_level, double healthPower, double defense, double dodgeChance,
                  double minDamage, double maxDamage, double[] damageRange, double increased_minDamage,
                  double increased_maxDamage, double[] increased_damageRange) {

        super(living_name, living_level, healthPower, defense, dodgeChance, minDamage, maxDamage, damageRange);
        this.increased_minDamage = increased_minDamage + minDamage; //Increased minDamage (Dragon)
        this.increased_maxDamage = increased_maxDamage + maxDamage; //Increased maxDamage (Dragon)
        this.increased_damageRange = new double[] {increased_minDamage, increased_maxDamage}; //Increased damage range (Dragon)
    }
}