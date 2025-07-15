package spell;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class LightningSpell extends Spell {
    //Variables
    double lightningSpellDamageDealt, dodgeChanceReductionPercentage;

    //Functions

    //Constructor
    public LightningSpell(String spellName, double spellCost, double magicPowerRequired, int spellMinLevel,
                          double lightningSpellDamageDealt, double rangeReductionPercentage) {
        super(spellName, spellCost, magicPowerRequired, spellMinLevel);
        this.lightningSpellDamageDealt = lightningSpellDamageDealt;
        this.dodgeChanceReductionPercentage = rangeReductionPercentage;
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
    public void reduceEnemyDodgeChance(Monster enemy, int rounds) {
        // Save original dodge chance only if there is not already active effect
        if (enemy.getLightningRoundsLeft() == 0) {
            double originalDodgeChance = enemy.getDodgeChance();
            originalDodgeChance = enemy.getDodgeChance();
            enemy.setDodgeChance(enemy.getOriginalDodgeChance()); // reset to original value
        }
        // Calculate the new dodge chance
        double newDodgeChance = enemy.getDodgeChance() * (1 - dodgeChanceReductionPercentage);
        enemy.setDodgeChance(newDodgeChance);
        enemy.setLightningRoundsLeft(rounds);

        System.out.println("LightningSpell has reduced enemy's dodge chance by " + (int)(dodgeChanceReductionPercentage * 100) + "% for " + rounds + " rounds.");
    }

    //Getters & Setters
    public double getLightningSpellDamageDealt() {
        return lightningSpellDamageDealt;
    }

    public void setLightningSpellDamageDealt(double lightningSpellDamageDealt) {
        this.lightningSpellDamageDealt = lightningSpellDamageDealt;
    }

    public double getDodgeChanceReductionPercentage() {
        return dodgeChanceReductionPercentage;
    }

    public void setDodgeChanceReductionPercentage(double dodgeChanceReductionPercentage) {
        this.dodgeChanceReductionPercentage = dodgeChanceReductionPercentage;
    }
}