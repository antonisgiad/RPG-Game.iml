public class FireSpell extends Spell {
    //Variables
    double firespell_damage_dealt, range_reduction_percentage;

    //Functions
    //Constructor
    public FireSpell(String living_name, int living_level, double healthPower, double magicPower, double strength,
                     double dexterity, double agility, double money, double experience, String spell_name, double spell_price,
                     double magic_energy_required, int spell_min_level, double minDamage, double maxDamage,
                     double[] damageRange, double firespell_damage_dealt, double range_reduction_percentage) {

        super(living_name, living_level, healthPower, magicPower, strength, dexterity, agility, money, experience, spell_name, spell_price, magic_energy_required, spell_min_level, minDamage, maxDamage, damageRange);
        this.firespell_damage_dealt = firespell_damage_dealt;
        this.range_reduction_percentage = range_reduction_percentage;
    }
    // Fire Spell debuff for couple rounds (check monster class)
    public void reduce_enemy_defense(Monster enemy, int rounds, double defenseReductionPercent) {
        // Save original defense only if there is not already active effect
        if (enemy.fireRoundsLeft == 0) {
            enemy.originalDefense = enemy.getDefense();
        }
        double newDefense = enemy.getDefense() * (1 - defenseReductionPercent);
        enemy.setDefense(newDefense);
        enemy.fireRoundsLeft = rounds;

        System.out.println("FireSpell has reduced enemy's defense by " + (int)(defenseReductionPercent * 100) + "% for " + rounds + " rounds.");
    }
}