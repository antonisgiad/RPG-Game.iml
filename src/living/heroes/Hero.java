package living.heroes;

import game.Inventory;
import living.Living;
import spell.Spell;

public class Hero extends Living {
    //Variables
    double magicPower, strength, dexterity, agility, money, experience;
    Inventory inventory;

    //Functions

    //Constructor
    public Hero(String livingName) {
        super(livingName);
        this.magicPower = 4;
        this.strength = 5;
        this.dexterity = 4;
        this.agility = 5;
        this.money = 100;
        this.experience = 0;
        this.inventory = new Inventory();
    }

    //TODO reminder that strength adds to total weapon damage!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (check weapon class)

    //dexterity affects spell casting ability
    public void increasedSpellcastingAbility(Spell spell) {
        // Define dexterity bonus
        int dexterityBonus = (int) (this.dexterity / 10);

        // Increase spell's damage range

        // Set new spell damage range (affected by dexterity)
        double newDamageRange = spell.getSpellDamageRange() + dexterityBonus;
        spell.setSpellDamageRange(newDamageRange);

        System.out.println("Spell's damage range has been increased by +" + dexterityBonus + "!");
    }

    //agility affects dodge chance
    public boolean dodgeMonsterAttackChance() {
        double chance = agility * 0.01;
        if (chance > 0.5) {
            chance = 0.5;
        }
        return Math.random() < chance;
    }

    //Level Up Hero
    public void levelUp() {
        // Check if hero has requiredExperience
        int livingLevel = getLivingLevel();
        int requiredExperience = livingLevel * 10;
        if (experience >= requiredExperience) {
            livingLevel++;
            experience -= requiredExperience;
            // Increase statistics
            strength += strength * 0.2;
            dexterity += dexterity * 0.2;
            agility += agility * 0.2;
            System.out.println(getLivingName() + " leveled up to " + livingLevel + "!");
            System.out.println("Strength increased to: " + strength);
            System.out.println("Dexterity increased to: " + dexterity);
            System.out.println("Agility increased to: " + agility);
        }
    }

    // Regenerate health after battle
    public void regenerateHealthAfterBattle() {
        if (this.getHealthPower() <= 0) {
            // Reset health to half of standard value (10)
            this.setHealthPower(5);
            System.out.println(this.getLivingName() + " has regenerated to 5 health points after battle.");
            this.setPassedOut(false); // Reset passed out status
        }
    }

    //Getters & Setters
    public double getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(double magicPower) {
        this.magicPower = magicPower;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getDexterity() {
        return dexterity;
    }

    public void setDexterity(double dexterity) {
        this.dexterity = dexterity;
    }

    public double getAgility() {
        return agility;
    }

    public void setAgility(double agility) {
        this.agility = agility;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getExperience() {return experience;}

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public void setInventory(Inventory inventory) {this.inventory = inventory;}

    public Inventory getInventory() {return inventory;}
}