package item;

import utils.RandomUtil;

public class Item {
    //Variables
    String itemName;
    double itemMarketCost;
    int itemMinLevel;

    //Functions
    //Constructor
    public Item(String itemName) {
        this.itemName = itemName;
        this.itemMarketCost = RandomUtil.randomStat(10, 100); // Cost between 10–100
        this.itemMinLevel = RandomUtil.randomLevel(1, 5);      // Level between 1–5
    }

    //Getters
    public String getItemName() {
        return itemName;
    }

    public double getItemMarketCost() {
        return itemMarketCost;
    }

    public int getItemMinLevel() {
        return itemMinLevel;
    }
}