import java.util.Random;


public class Monster extends Living{
    //Variables
    double defense, dodgeChance, minDamage, maxDamage, originalMinDamage, originalMaxDamage, originalDefense, originalDodgeChance;
    double[] damageRange = {minDamage, maxDamage};
    int iceRoundsLeft = 0, fireRoundsLeft = 0, lightningRoundsLeft = 0;
    Random rand = new Random();
    int randomNum = rand.nextInt(11); //

    //Functions
    //Constructor
    public Monster(String living_name, int living_level, double healthPower, double defense, double dodgeChance, double minDamage, double maxDamage,
                   double[] damageRange) {

        super(living_name, living_level, healthPower);
        this.defense = defense;
        this.dodgeChance = dodgeChance;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.originalMinDamage = minDamage;
        this.originalMaxDamage = maxDamage;
        this.originalDefense = defense;
        this.originalDodgeChance = dodgeChance;
        this.damageRange = damageRange;
        this.iceRoundsLeft = 0;
        this.fireRoundsLeft = 0;
        this.lightningRoundsLeft = 0;
    }

    public double calculateDamageTaken(double incomingDamage) {
        double reducedDamage = incomingDamage - this.defense;
        if (reducedDamage < 0) {
            reducedDamage = 0;
        }
        return reducedDamage;
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

    public double getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(double minDamage) {
        this.minDamage = minDamage;
    }

    public double getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(double maxDamage) {
        this.maxDamage = maxDamage;
    }

    public double[] getDamageRange() {
        return damageRange;
    }

    public void setDamageRange(double[] damageRange) {
        this.damageRange = damageRange;
    }
}