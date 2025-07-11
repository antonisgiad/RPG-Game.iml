package living.monster;

import living.Living;
import utils.RandomUtil;

public class Monster extends Living {
    //Variables
    private double defense, dodgeChance, originalDefense, originalDodgeChance; //originals used in spell subclasses check there
    private double[] damageRange, originalDamageRange;
    private int iceRoundsLeft = 0, fireRoundsLeft = 0, lightningRoundsLeft = 0; //check spell subclasses

    //Functions
    //Constructor
    public Monster(String livingName) {
        super(livingName);
        this.defense = 20;
        this.dodgeChance = 0.1;
        this.damageRange = new double[]{100, 200};
    }

    //...........call updateIce/Fire/Lightning Effect() on each round on fight class!!!.............


    // Every round check Ice Spell debuff (check ice spell class):
    public void updateIceEffect() {
        if (iceRoundsLeft > 0) {
            iceRoundsLeft--;
            if (iceRoundsLeft == 0) {
                damageRange[0] = originalDamageRange[0]; // reset min damage
                damageRange[1] = originalDamageRange[1]; // reset max damage
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
    public double getDefense() {return defense;}

    public void setDefense(double defense) {this.defense = defense;}

    public double getDodgeChance() {return dodgeChance;}

    public void setDodgeChance(double dodgeChance) {this.dodgeChance = dodgeChance;}

    public double[] getDamageRange() {return damageRange;}

    public void setDamageRange(double[] damageRange) {this.damageRange = damageRange;}
    //For the min and max values of damage range
    public double getMinDamage() { return damageRange[0]; }

    public void setMinDamage(double minDamage) {this.damageRange[0] = minDamage;}

    public double getMaxDamage() { return damageRange[1]; }

    public void setMaxDamage(double maxDamage) {this.damageRange[1] = maxDamage;}

    public int getIceRoundsLeft() {return iceRoundsLeft;}

    public void setIceRoundsLeft(int iceRoundsLeft) {this.iceRoundsLeft = iceRoundsLeft;}

    public int getFireRoundsLeft() {return fireRoundsLeft;}

    public void setFireRoundsLeft(int fireRoundsLeft) {this.fireRoundsLeft = fireRoundsLeft;}

    public int getLightningRoundsLeft() {return lightningRoundsLeft;}

    public void setLightningRoundsLeft(int lightningRoundsLeft) {this.lightningRoundsLeft = lightningRoundsLeft;}
}