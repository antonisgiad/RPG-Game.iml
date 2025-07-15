package spell;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class FireSpell extends Spell {
    //Variables
    private double fireSpellDamageDealt, defenseReductionPercentage;

    //Functions
    //Constructor
    public FireSpell(String spellName, double spellCost, double magicPowerRequired, int spellMinLevel,
                     double fireSpellDamageDealt, double defenseReductionPercentage) {
        super(spellName, spellCost, magicPowerRequired, spellMinLevel);
        this.fireSpellDamageDealt = fireSpellDamageDealt;
        this.defenseReductionPercentage = defenseReductionPercentage;
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
    public void reduceEnemyDefense(Monster enemy, int rounds) {
        // Save original defense only if there is not already active effect
        if (enemy.getFireRoundsLeft() == 0) {
            enemy.setDefense(enemy.getOriginalDefense()); // reset to original value
        }
        //Calculate new defense
        double newDefense = enemy.getDefense() * (1 - defenseReductionPercentage);
        enemy.setDefense(newDefense);
        enemy.setFireRoundsLeft(rounds);
        System.out.println("FireSpell has reduced enemy's defense by " + (int)(defenseReductionPercentage * 100) + "% for " + rounds + " rounds.");
    }

    // Getters & Setters
    public double getFireSpellDamageDealt() {
        return fireSpellDamageDealt;
    }

    public void setFireSpellDamageDealt(double fireSpellDamageDealt) {
        this.fireSpellDamageDealt = fireSpellDamageDealt;
    }

    public double getDefenseReductionPercentage() {
        return defenseReductionPercentage;
    }

    public void setDefenseReductionPercentage(double defenseReductionPercentage) {
        this.defenseReductionPercentage = defenseReductionPercentage;
    }
}