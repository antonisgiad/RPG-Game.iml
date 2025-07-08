public class Armor extends Item{
    //Variables
    double reduced_damage;

    //Functions
    //Constructor
    public Armor(String item_name, double item_market_price, int item_min_level, double reduced_damage) {
        super(item_name, item_market_price, item_min_level);
        this.reduced_damage = reduced_damage;
    }

    public double reduce_monster_damage_taken(Monster monster) {
        double[] damageRange = monster.getDamageRange();
        // Choose random damage from damage range
        double incomingDamage = damageRange[0] + Math.random() * (damageRange[1] - damageRange[0]);
        double reducedDamage = incomingDamage - reduced_damage;
        if (reducedDamage < 0) {
            reducedDamage = 0;
        }
        return reducedDamage;
    }
}