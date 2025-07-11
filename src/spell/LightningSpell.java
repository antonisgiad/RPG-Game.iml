package spell;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class LightningSpell extends Spell {
    //Variables
    double lightningSpellDamageDealt, rangeReductionPercentage;

    //Functions
    //Constructor
    public LightningSpell(String spellName) {
        super(spellName);
        this.maxDamage = this.minDamage + RandomUtil.randomStat(10, 25);
        this.damageRange = new double[] {this.minDamage, this.maxDamage};
        this.lightningSpellDamageDealt = RandomUtil.randomStat(this.minDamage, this.maxDamage);
        this.rangeReductionPercentage = RandomUtil.randomStat(0.1, 0.4); // 10%–40% reduction
    }

    // Damage dealt by lightning spell
    public void castLightningSpell(Monster target, Hero user) {
        double totalDamage = this.lightningSpellDamageDealt;
        double totalDefense = target.getDefense();
        double effectiveDamage = Math.max(0, totalDamage - totalDefense);

        target.setHealthPower(target.getHealthPower() - effectiveDamage);

        System.out.println(user.getLivingName() + " casts " + this.getSpellName() + " and causes " + effectiveDamage + " lightning damage!");
    }

    // Lightning Spell debuff for couple rounds (check monster class)
    public void reduceEnemyDodgeChance(Monster enemy, int rounds, double dodgeReductionPercent) {
        // Save original dodge chance only if there is not already active effect
        if (enemy.getLightningRoundsLeft() == 0) {
            double originalDodgeChance = enemy.getDodgeChance();
            originalDodgeChance = enemy.getDodgeChance();
        }
        double newDodgeChance = enemy.getDodgeChance() * (1 - dodgeReductionPercent);
        enemy.setDodgeChance(newDodgeChance);
        int lightningRoundsLeft = enemy.getLightningRoundsLeft();
        lightningRoundsLeft = rounds;

        System.out.println("LightningSpell has reduced enemy's dodge chance by " + (int)(dodgeReductionPercent * 100) + "% for " + rounds + " rounds.");
    }
    // Lightning Spell debuff for couple rounds (check monster class)
    public void reducedDodgeChance(Monster enemy) {
        // Save original chance only if there is not already active effect
        if (enemy.getLightningRoundsLeft() == 0) {
            double originalDodgeChance = enemy.getDodgeChance();
            originalDodgeChance = enemy.getDefense();
        }
        // Calculate new chance
        double newChance = enemy.getDodgeChance() * (1 - rangeReductionPercentage);

        System.out.println("LightningSpell has reduced enemy's dodge chance by " + (int)(rangeReductionPercentage * 100) + "%.");
    }
}