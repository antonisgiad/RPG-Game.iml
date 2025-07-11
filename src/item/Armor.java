package item;

public class Armor extends Item {
    //Variables
    private double reducedDamage;

    //Functions

    //Constructor
    public Armor(String itemName, double itemMarketCost, int itemMinLevel, double reducedDamage) {
        super(itemName, itemMarketCost, itemMinLevel);
        this.reducedDamage = reducedDamage;
    }

    //Getters & Setters
    public double getReducedDamage() {return reducedDamage;}

    public void setReducedDamage(double reducedDamage) {this.reducedDamage = reducedDamage;}
}