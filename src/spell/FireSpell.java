package spell;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class FireSpell extends Spell {
    //Variables
    double fireSpellDamageDealt, rangeReductionPercentage;

    //Functions
    //Constructor
    public FireSpell(String spellName) {
        super(spellName);
        this.maxDamage = this.minDamage + RandomUtil.randomStat(10, 25);
        this.damageRange = new double[] { this.minDamage, this.maxDamage };
        this.fireSpellDamageDealt = RandomUtil.randomStat(this.minDamage, this.maxDamage);
        this.rangeReductionPercentage = RandomUtil.randomStat(0.1, 0.4); // 10%–40% reduction
    }

    // Damage dealt by fire spell
    public void castFireSpell(Monster target, Hero user) {
        double totalDamage = this.fireSpellDamageDealt;
        double totalDefense = target.getDefense();
        double effectiveDamage = Math.max(0, totalDamage - totalDefense);

        target.setHealthPower(target.getHealthPower() - effectiveDamage);

        System.out.println(user.getLivingName() + " casts " + this.getSpellName() + " and causes " + effectiveDamage + " fire damage!");
    }

    // Fire Spell debuff for couple rounds (check monster class)
    public void reduceEnemyDefense(Monster enemy, int rounds, double defenseReductionPercent) {
        // Save original defense only if there is not already active effect
        if (enemy.getFireRoundsLeft() == 0) {
            double originalDefense = enemy.getDefense();
            originalDefense = enemy.getDefense();
        }
        //Calculate new defense
        double newDefense = enemy.getDefense() * (1 - defenseReductionPercent);
        enemy.setDefense(newDefense);
        int fireRoundsLeft = enemy.getFireRoundsLeft();
        fireRoundsLeft = rounds;

        System.out.println("FireSpell has reduced enemy's defense by " + (int)(defenseReductionPercent * 100) + "% for " + rounds + " rounds.");
    }
}