public class IceSpell extends Spell {
    //Variables
    double range_reduction_percentage, icespell_damage_dealt;

    //Functions
    //Constructor
    public IceSpell(String living_name, int living_level, double healthPower, double magicPower, double strength, double dexterity,
                    double agility, double money, double experience, String spell_name, double spell_price,
                    double magic_energy_required, int spell_min_level, double minDamage, double maxDamage, double[] damageRange,
                    double range_reduction_percentage, double icespell_damage_dealt) {

        super(living_name, living_level, healthPower, magicPower, strength, dexterity, agility, money, experience, spell_name, spell_price, magic_energy_required, spell_min_level, minDamage, maxDamage, damageRange);
        this.range_reduction_percentage = range_reduction_percentage;
        this.icespell_damage_dealt = icespell_damage_dealt;
    }
    // Ice Spell debuff for couple rounds (check monster class)
    public void reduce_enemy_damage_range(Monster enemy, int rounds) {
        // Save original damage range only if there is not already active effect
        if (enemy.iceRoundsLeft == 0) {
            enemy.originalMinDamage = enemy.getMinDamage();
            enemy.originalMaxDamage = enemy.getMaxDamage();
        }
        double newMin = enemy.getMinDamage() * (1 - range_reduction_percentage);
        double newMax = enemy.getMaxDamage() * (1 - range_reduction_percentage);
        enemy.setMinDamage(newMin);
        enemy.setMaxDamage(newMax);
        enemy.iceRoundsLeft = rounds;

        System.out.println("IceSpell has reduced enemy's damage range by " + (int)(range_reduction_percentage * 100) + "% for " + rounds + " rounds.");
    }
}