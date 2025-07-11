package item;

public class Item {
    //Variables

    private String itemName;
    private double itemMarketCost;
    private int itemMinLevel;

    //Functions

    //Constructor
    public Item(String itemName,  double itemMarketCost, int itemMinLevel) {
        this.itemName = itemName;
        this.itemMarketCost = itemMarketCost; // Cost between 10–100
        this.itemMinLevel = itemMinLevel;      // Level between 1–5
    }

    //Getters * Setters
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemMarketCost() {
        return itemMarketCost;
    }

    public void setItemMarketCost(double itemMarketCost) {
        this.itemMarketCost = itemMarketCost;
    }

    public int getItemMinLevel() {
        return itemMinLevel;
    }

    public void setItemMinLevel(int itemMinLevel) {
        this.itemMinLevel = itemMinLevel;
    }
}