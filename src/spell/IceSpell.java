package spell;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class IceSpell extends Spell {
    //Variables
    private double rangeReductionPercentage, iceSpellDamageDealt;

    //Functions

    //Constructor
    public IceSpell(String spellName, double spellCost, double magicPowerRequired, int spellMinLevel,
                    double rangeReductionPercentage, double iceSpellDamageDealt) {
        super(spellName, spellCost, magicPowerRequired, spellMinLevel);
        this.rangeReductionPercentage = rangeReductionPercentage;
        this.iceSpellDamageDealt = iceSpellDamageDealt;
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
            enemy.setMonsterDamageRange(enemy.getOriginalDamageRange()); // reset to original value
        }
        // Calculate the new damage range
        double newDamageRange = enemy.getMonsterDamageRange() * (1 - rangeReductionPercentage);
        enemy.setMonsterDamageRange(newDamageRange);
        enemy.setIceRoundsLeft(rounds);
        System.out.println(this.getSpellName() + " reduced enemy damage range by " +
                (int)(rangeReductionPercentage * 100) + "% for " + rounds + " rounds.");
    }

    //Getters & Setters
    public double getRangeReductionPercentage() {
        return rangeReductionPercentage;
    }

    public void setRangeReductionPercentage(double rangeReductionPercentage) {
        this.rangeReductionPercentage = rangeReductionPercentage;
    }

    public double getIceSpellDamageDealt() {
        return iceSpellDamageDealt;
    }

    public void setIceSpellDamageDealt(double iceSpellDamageDealt) {
        this.iceSpellDamageDealt = iceSpellDamageDealt;
    }
}