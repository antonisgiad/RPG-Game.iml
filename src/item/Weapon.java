package item;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

public class Weapon extends Item {
    //Variables
    double weaponDamage;
    int hands;

    //Functions
    //Constructor
    public Weapon(String item_name, int hands) {
        super(item_name);
        this.weaponDamage = RandomUtil.randomStat(15, 60); // Damage between 15–60
        this.hands = hands; // 1 or 2 hands
    }

    public void attack(Monster target, Hero user) {
        double totalDamage = this.weaponDamage + user.getStrength();
        double totalDefense = target.getDefense();
        double effectiveDamage = Math.max(0, totalDamage - totalDefense);
        double HP = target.getHealthPower();
        target.setHealthPower(HP - effectiveDamage);

        System.out.println(user.getLivingName() + " attacks with " + this.getItemName() + " and causes " + effectiveDamage + " damage!");
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
    //How many hands used
    public int handsNeeded(){
        if (hands == 1) {
            System.out.println("This Weapon requires " + hands + " hand.");
        }
        else if (hands == 2) {
            System.out.println("This Weapon requires " + hands + " hands.");
        }
        return hands;
    }
}