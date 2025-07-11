package item;

import utils.RandomUtil;

public class Weapon extends Item {
    //Variables

    private double weaponDamage;
    private int hands;

    //Functions

    //Constructor
    public Weapon(String itemName, double itemMarketCost, int itemMinLevel, double weaponDamage, int hands) {
        super(itemName, itemMarketCost, itemMinLevel);
        this.weaponDamage = weaponDamage;
        this.hands = hands;
    }

    //How many hands the weapon uses
    public int handsNeeded(){
        if (hands == 1) {
            System.out.println("This Weapon requires " + hands + " hand.");
        }
        else if (hands == 2) {
            System.out.println("This Weapon requires " + hands + " hands.");
        }
        return hands;
    }

    //Getters & Setters
    public double getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(double weaponDamage) {
        this.weaponDamage = weaponDamage;
    }

    public void setHands(int hands) {
        this.hands = hands;
    }

    public int getHands() { return hands; }
}