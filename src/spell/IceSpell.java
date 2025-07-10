package spell;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class IceSpell extends Spell {
    //Variables
    double rangeReductionPercentage, iceSpellDamageDealt;

    //Functions
    //Constructor
    public IceSpell(String spellName) {
        super(spellName);
        this.maxDamage = this.minDamage + RandomUtil.randomStat(10, 25);
        this.damageRange = new double[] { this.minDamage, this.maxDamage };
        this.rangeReductionPercentage = RandomUtil.randomStat(0.2, 0.5);        // 20%–50% reduction
        this.iceSpellDamageDealt = RandomUtil.randomStat(this.minDamage, this.maxDamage);
    }
    // Damage dealt by ice spell
    public void castIceSpell(Monster target, Hero user) {
        double totalDamage = this.iceSpellDamageDealt;
        double totalDefense = target.getDefense();
        double effectiveDamage = Math.max(0, totalDamage - totalDefense);

        target.setHealthPower(target.getHealthPower() - effectiveDamage);

        System.out.println(user.getLivingName() + " casts " + this.getSpellName() + " and causes " + effectiveDamage + " ice damage!");
    }
    // Ice Spell debuff for couple rounds (check monster class)
    public void reduceEnemyDamageRange(Monster enemy, int rounds) {
        // Save original damage range only if there is not already active effect
        if (enemy.getIceRoundsLeft() == 0) {
            double originalMinDamage = enemy.getOriginalMinDamage();
            double originalMaxDamage = enemy.getOriginalMaxDamage();
            originalMinDamage = enemy.getMinDamage();
            originalMaxDamage = enemy.getMaxDamage();
        }
        //Calculate new range
        double newMin = enemy.getMinDamage() * (1 - rangeReductionPercentage);
        double newMax = enemy.getMaxDamage() * (1 - rangeReductionPercentage);
        enemy.setMinDamage(newMin);
        enemy.setMaxDamage(newMax);
        int iceRoundsLeft = enemy.getIceRoundsLeft();
        iceRoundsLeft = rounds;

        System.out.println("IceSpell has reduced enemy's damage range by " + (int)(rangeReductionPercentage * 100) + "% for " + rounds + " rounds.");
    }
}