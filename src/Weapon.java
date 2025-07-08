public class Weapon extends Item{
    //Variables
    double weapon_damage;
    int hands;

    //Functions
    //Constructor
    public Weapon(String item_name, double item_market_price, int item_min_level, double weapon_damage) {
        super(item_name, item_market_price, item_min_level);
        this.weapon_damage = weapon_damage;
    }

    //Getters & Setters
    public double getWeapon_damage() {
        return weapon_damage;
    }

    public void setWeapon_damage(double weapon_damage) {
        this.weapon_damage = weapon_damage;
    }

    public void setHands(int hands) {
        this.hands = hands;
    }

    //How many hands used
    public int hands_needed(){
        if (hands == 1) {
            System.out.println("This Weapon requires " + hands + " hand.");
        }
        else if (hands == 2) {
            System.out.println("This Weapon requires " + hands + " hands.");
        }
        return hands;
    }
}
