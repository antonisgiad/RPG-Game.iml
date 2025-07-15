package spell;

import living.heroes.Hero;

public class Spell {
    //Variables
    private String spellName;
    private double spellCost, magicPowerRequired, spellDamageRange;
    private int spellMinLevel;

    //Functions

    //Constructor
    public Spell(String spellName, double spellCost, double magicPowerRequired, int spellMinLevel) {
        this.spellName = spellName;
        this.spellCost = spellCost;
        this.magicPowerRequired = magicPowerRequired;
        this.spellMinLevel = spellMinLevel;
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

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public double getSpellCost() {
        return spellCost;
    }

    public void setSpellCost(double spellCost) {
        this.spellCost = spellCost;
    }

    public double getMagicPowerRequired() {
        return magicPowerRequired;
    }

    public void setMagicPowerRequired(double magicPowerRequired) {
        this.magicPowerRequired = magicPowerRequired;
    }

    public double getSpellDamageRange() {
        return spellDamageRange;
    }

    public void setSpellDamageRange(double spellDamageRange) {
        this.spellDamageRange = spellDamageRange;
    }

    public int getSpellMinLevel() {
        return spellMinLevel;
    }

    public void setSpellMinLevel(int spellMinLevel) {
        this.spellMinLevel = spellMinLevel;
    }
}