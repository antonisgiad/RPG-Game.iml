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
        this.spellCost = RandomUtil.randomStat(30, 120);            // Spell cost
        this.magicPowerRequired = RandomUtil.randomStat(10, 40);    // Magic power required
        this.spellMinLevel = RandomUtil.randomLevel(1, 5);          // Minimum level to use
        this.minDamage = RandomUtil.randomStat(15, 30);               // Minimum spell damage
        this.maxDamage = this.minDamage + RandomUtil.randomStat(10, 25); // Maximum spell damage
        this.damageRange = new double[] {minDamage, maxDamage};
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

    public double calculateDamageDealt(Hero caster, Monster target) {
        double casterMagicPower = caster.getMagicPower();
        double targetMagicDefense = target.getDefense();
        double casterDexterity =  caster.getDexterity();

        double damage = (casterMagicPower * casterDexterity) - targetMagicDefense; // Dexterity affects damage

        if (damage < minDamage) {
            damage = minDamage;
        } else if (damage > maxDamage) {
            damage = maxDamage;
        }
        return damage;
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