package item;

import game.Inventory;
import living.heroes.Hero;

public class Potion extends Item {
    //Variables

    double increasedValue; //Random starter value to increase statistics

    //Functions

    //Constructor
    public Potion(String itemName, double itemMarketCost, int itemMinLevel, double increasedValue) {
        super(itemName, itemMarketCost, itemMinLevel);
        this.increasedValue = increasedValue;
    }

    public void increaseRandomStatistic(Hero hero) {
        int stat = (int) (Math.random() * 6); // 0: strength, 1: dexterity, 2: agility, 3: magicPower, 4: Money, 5: Experience
        switch (stat) {
            case 0:
                hero.setStrength(hero.getStrength() + increasedValue);
                System.out.println("Strength increased!");
                break;
            case 1:
                hero.setDexterity(hero.getDexterity() + increasedValue);
                System.out.println("Dexterity increased!");
                break;
            case 2:
                hero.setAgility(hero.getAgility() + increasedValue);
                System.out.println("Agility increased!");
                break;
            case 3:
                hero.setMagicPower(hero.getMagicPower() + increasedValue);
                System.out.println("Magic Power increased!");
                break;
            case 4:
                hero.setMoney(hero.getMoney() + increasedValue);
                System.out.println("Money increased!");
                break;
            case 5:
                hero.setExperience(hero.getExperience() + increasedValue);
                System.out.println("Experience increased!");
                break;
        }
    }

    public void destroyWhenUsed(Inventory inventory) {
        boolean removed = inventory.removeItem(this);
        if (removed) {
            System.out.println("Potion " + this.getItemName() + " destroyed after use.");
        } else {
            System.out.println("Potion " + this.getItemName() + " was not found in inventory.");
        }
    }

    //Getters & Setters
    public double getIncreasedValue() {return increasedValue;}

    public void setIncreasedValue(double increasedValue) {this.increasedValue = increasedValue;}
}