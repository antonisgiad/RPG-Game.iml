public class LightningSpell extends Spell {
    //Variables
    double lightningspell_damage_dealt, range_reduction_percentage;

    //Functions
    //Constructor
    public LightningSpell(String living_name, int living_level, double healthPower, double magicPower, double strength,
                          double dexterity, double agility, double money, double experience, String spell_name, double spell_price,
                          double magic_energy_required, int spell_min_level, double minDamage, double maxDamage, double[] damageRange,
                          double lightningspell_damage_dealt, double range_reduction_percentage) {

        super(living_name, living_level, healthPower, magicPower, strength, dexterity, agility, money, experience, spell_name, spell_price, magic_energy_required, spell_min_level, minDamage, maxDamage, damageRange);
        this.lightningspell_damage_dealt = lightningspell_damage_dealt;
        this.range_reduction_percentage = range_reduction_percentage;
    }
    // Lightning Spell debuff for couple rounds (check monster class)
    public void reduce_enemy_dodge_chance(Monster enemy, int rounds, double dodgeReductionPercent) {
        // Save original dodge chance only if there is not already active effect
        if (enemy.lightningRoundsLeft == 0) {
            enemy.originalDodgeChance = enemy.getDodgeChance();
        }
        double newDodgeChance = enemy.getDodgeChance() * (1 - dodgeReductionPercent);
        enemy.setDodgeChance(newDodgeChance);
        enemy.lightningRoundsLeft = rounds;

        System.out.println("LightningSpell has reduced enemy's dodge chance by " + (int)(dodgeReductionPercent * 100) + "% for " + rounds + " rounds.");
    }










    public void reduce_chance_of_missed_attack(Monster enemy) {
        // Calculate new chance
        double newChance = enemy.getDodgeChance() * (1 - range_reduction_percentage);
        System.out.println("LightningSpell has reduced enemy's chance of missed attack by " + (int)(range_reduction_percentage * 100) + "%.");
    }
}