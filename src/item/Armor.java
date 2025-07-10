package item;

import living.monster.Monster;
import utils.RandomUtil;

public class Armor extends Item {
    //Variables
    double reducedDamage;

    //Functions
    //Constructor
    public Armor(String itemName) {
        super(itemName);
        this.reducedDamage = RandomUtil.randomStat(5, 30); // Reduces damage by 5–30
    }

    public double reduceMonsterDamageTaken(Monster monster) {
        double[] damageRange = monster.getDamageRange();
        // Choose random damage from damage range
        double incomingDamage = damageRange[0] + Math.random() * (damageRange[1] - damageRange[0]);
        double totalDamage = incomingDamage - reducedDamage;

        if (totalDamage < 0) {
            totalDamage = 0;
        }
        return totalDamage;
    }

    //Getters & Setters
    public double getReducedDamage() {return reducedDamage;}

    public void setReducedDamage(double reducedDamage) {this.reducedDamage = reducedDamage;}
}