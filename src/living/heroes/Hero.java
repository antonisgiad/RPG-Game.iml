package living.heroes;

import Game.Inventory;
import living.Living;
import living.monster.Monster;
import spell.Spell;
import utils.RandomUtil;

public class Hero extends Living {
    //Variables
    double magicPower, strength, dexterity, agility, money, experience;
    Inventory inventory;

    //Functions
    //Constructor
    public Hero(String living_name) {
        super(living_name);
        this.magicPower = 5;
        this.strength = 8;
        this.dexterity = 6;
        this.agility = 6;
        this.money = 12;
        this.experience = 0;
        this.inventory = new Inventory();
    }

    //TODO reminder that strength adds to total weapon damage!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! (check weapon class)

    //dexterity affects spell casting ability
    public void increasedSpellcastingAbility(Spell spell) {
        // Define dexterity bonus
        int dexterityBonus = (int) (this.dexterity / 10);

        // Increase spell's damage range
        spell.setMinDamage(spell.getMinDamage() + dexterityBonus);
        spell.setMaxDamage(spell.getMaxDamage() + dexterityBonus);

        // Set new spell damage range (affected by dexterity)
        double[] newDamageRange = {spell.getMinDamage(), spell.getMaxDamage()};
        spell.setDamageRange(newDamageRange);

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

//    public void attack(Monster target) {
//        if (target.passedOut()) {
//            System.out.println(target.getLivingName() + " is already defeated!");
//            return;
//        }
////        double damage = this.getweaponDam();
////        target.receiveDamage(damage);
////        System.out.println(this.getLivingName() + " attacks " + target.getLivingName() + " for " + damage + " damage!");
//    }
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