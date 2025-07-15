package living.monster;

import living.Living;
import utils.RandomUtil;

public class Monster extends Living {
    //Variables
    private double defense, dodgeChance, monsterDamageRange, originalDamageRange, originalDefense, originalDodgeChance;
    private int iceRoundsLeft = 0, fireRoundsLeft = 0, lightningRoundsLeft = 0; //check spell subclasses

    //Functions

    //Constructor
    public Monster(String livingName) {
        super(livingName);
        this.defense = RandomUtil.randomStat(1, 5); // Random defense between 5 and 15
        this.dodgeChance = RandomUtil.randomStat(0.05, 0.1 ); // Random dodge chance between 5% and 10%
        this.monsterDamageRange = RandomUtil.randomStat(3, 5); // Random damage range between 3 and 5
    }

    // TODO call updateIce/Fire/Lightning Effect() on each round on fight class!!!

    // Every round check Ice Spell debuff (check ice spell class):
    public void updateIceEffect() {
        if (iceRoundsLeft > 0) {
            iceRoundsLeft--;
            if (iceRoundsLeft == 0) {
                monsterDamageRange = originalDamageRange; // reset  damageRange to original value
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
    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    public double getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(double dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public double getMonsterDamageRange() {
        return monsterDamageRange;
    }

    public void setMonsterDamageRange(double monsterDamageRange) {
        this.monsterDamageRange = monsterDamageRange;
    }

    public double getOriginalDefense() {
        return originalDefense;
    }

    public void setOriginalDefense(double originalDefense) {
        this.originalDefense = originalDefense;
    }

    public double getOriginalDodgeChance() {
        return originalDodgeChance;
    }

    public void setOriginalDodgeChance(double originalDodgeChance) {
        this.originalDodgeChance = originalDodgeChance;
    }

    public double getOriginalDamageRange() {
        return originalDamageRange;
    }

    public void setOriginalDamageRange(double originalDamageRange) {
        this.originalDamageRange = originalDamageRange;
    }

    public int getIceRoundsLeft() {
        return iceRoundsLeft;
    }

    public void setIceRoundsLeft(int iceRoundsLeft) {
        this.iceRoundsLeft = iceRoundsLeft;
    }

    public int getFireRoundsLeft() {
        return fireRoundsLeft;
    }

    public void setFireRoundsLeft(int fireRoundsLeft) {
        this.fireRoundsLeft = fireRoundsLeft;
    }

    public int getLightningRoundsLeft() {
        return lightningRoundsLeft;
    }

    public void setLightningRoundsLeft(int lightningRoundsLeft) {
        this.lightningRoundsLeft = lightningRoundsLeft;
    }
}