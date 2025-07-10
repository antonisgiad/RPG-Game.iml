package living.monster;

import living.Living;
import utils.RandomUtil;

public class Monster extends Living {
    //Variables
    double defense, dodgeChance, originalMinDamage, originalMaxDamage, originalDefense, originalDodgeChance; //originals used in spell subclasses check there
    double[] damageRange;
    int iceRoundsLeft = 0, fireRoundsLeft = 0, lightningRoundsLeft = 0; //check spell subclasses

    //Functions
    //Constructor
    public Monster(String livingName) {
        super(livingName);

        // Use RandomUtil for all random stats
        this.defense = RandomUtil.randomStat(5, 20);           // Defense: 5–20
        this.dodgeChance = RandomUtil.randomStat(0.05, 0.25);  // Dodge chance: 0.05–0.25

        // Store original values for debuff logic
        this.originalDefense = this.defense;
        this.originalDodgeChance = this.dodgeChance;

        // Set damage range array
        this.damageRange = new double[]{100, 200};
    }
    //Calculate the damage taken from an attack considering defense factor
    public void receiveDamage(double incomingDamage) {
        double damageTaken = Math.max(0, incomingDamage - defense);
        setHealthPower(Math.max(0, getHealthPower() - damageTaken));
        System.out.println(getLivingName() + " suffered " + damageTaken + " damage!");
    }


    //...........call updateIce/Fire/Lightning Effect() on each round on fight class!!!.............


    // Every round check Ice Spell debuff (check ice spell class):
    public void updateIceEffect() {
        if (iceRoundsLeft > 0) {
            iceRoundsLeft--;
            if (iceRoundsLeft == 0) {
                setMinDamage(originalMinDamage);
                setMaxDamage(originalMaxDamage);
            }
        }
    }
    // Every round check Fire Spell debuff (check fire spell class):
    public void updateFireEffect() {
        if (fireRoundsLeft > 0) {
            fireRoundsLeft--;
            if (fireRoundsLeft == 0) {
                setDefense(originalDefense);
                System.out.println("FireSpell effect has worn off! Enemy's defense restored.");
            }
        }
    }
    // Every round check Lightning Spell debuff (check lightning spell class):
    public void updateLightningEffect() {
        if (lightningRoundsLeft > 0) {
            lightningRoundsLeft--;
            if (lightningRoundsLeft == 0) {
                setDodgeChance(originalDodgeChance);
                System.out.println("LightningSpell effect has worn off! Enemy's dodge chance restored.");
            }
        }
    }
    //Getters & Setters
    public double getMinDamage() {return minDamage;}

    public void setMinDamage(double minDamage) {this.minDamage = minDamage;}

    public double getMaxDamage() {return maxDamage;}

    public void setMaxDamage(double maxDamage) {this.maxDamage = maxDamage;}

    public double getDefense() {return defense;}

    public void setDefense(double defense) {this.defense = defense;}

    public double getDodgeChance() {return dodgeChance;}

    public void setDodgeChance(double dodgeChance) {this.dodgeChance = dodgeChance;}

    public double getOriginalMinDamage() {return originalMinDamage;}

    public void setOriginalMinDamage(double originalMinDamage) {this.originalMinDamage = originalMinDamage;}

    public double getOriginalMaxDamage() {return originalMaxDamage;}

    public void setOriginalMaxDamage(double originalMaxDamage) {this.originalMaxDamage = originalMaxDamage;}

    public double getOriginalDefense() {return originalDefense;}

    public void setOriginalDefense(double originalDefense) {this.originalDefense = originalDefense;}

    public double getOriginalDodgeChance() {return originalDodgeChance;}

    public void setOriginalDodgeChance(double originalDodgeChance) {this.originalDodgeChance = originalDodgeChance;}

    public double[] getDamageRange() {return damageRange;}

    public void setDamageRange(double[] damageRange) {this.damageRange = damageRange;}

    public int getIceRoundsLeft() {return iceRoundsLeft;}

    public void setIceRoundsLeft(int iceRoundsLeft) {this.iceRoundsLeft = iceRoundsLeft;}

    public int getFireRoundsLeft() {return fireRoundsLeft;}

    public void setFireRoundsLeft(int fireRoundsLeft) {this.fireRoundsLeft = fireRoundsLeft;}

    public int getLightningRoundsLeft() {return lightningRoundsLeft;}

    public void setLightningRoundsLeft(int lightningRoundsLeft) {this.lightningRoundsLeft = lightningRoundsLeft;}
}