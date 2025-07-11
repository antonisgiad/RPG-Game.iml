package spell;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class Spell {
    //Variables
    String spellName;
    double spellCost, magicPowerRequired, minDamage, maxDamage;
    int spellMinLevel;
    double[] damageRange = {minDamage, maxDamage};

    //Functions
    //Constructor
    public Spell(String spellName) {
        this.spellName = spellName;
        this.spellCost = 50;            // Spell cost
        this.magicPowerRequired = 20;    // Magic power required
        this.spellMinLevel = 2;          // Minimum level to use
        this.damageRange = new double[] {10, 20};
    }

    //Remove magic power needed to cast the spell
    public boolean consumedMagicPowerAfterUse(Hero caster){
        if (caster.getMagicPower() >= magicPowerRequired) {
            caster.setMagicPower(caster.getMagicPower() - magicPowerRequired);
            System.out.println("Magic Power reduced by " + magicPowerRequired);
            return true;
        }
        else {
            System.out.println("There is not enough Magic Power for the Spell!");
            return false;
        }
    }

    //Getters & Setters
    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spell_name) {this.spellName = spellName;}

    public double getSpellCost() {
        return spellCost;
    }

    public void setSpellCost(double spellCost) {
        this.spellCost = spellCost;
    }

    public double getMagicPowerRequired() {
        return magicPowerRequired;
    }

    public void setMagicPowerRequired(double magicEnergyRequired) {this.magicPowerRequired = magicEnergyRequired;}

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

    public int getSpellMinLevel() {
        return spellMinLevel;
    }

    public void setSpellMinLevel(int spellMinLevel) {
        this.spellMinLevel = spellMinLevel;
    }

    public double[] getDamageRange() {
        return damageRange;
    }

    public void setDamageRange(double[] damageRange) {this.damageRange = damageRange;}
}