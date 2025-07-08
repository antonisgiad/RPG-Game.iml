public class Item {
    //Variables
    String item_name;
    double item_market_price;
    int item_min_level;

    //Functions
    //Constructor
    public Item(String item_name, double item_market_price, int item_min_level) {
        this.item_name = item_name;
        this.item_market_price = item_market_price;
        this.item_min_level = item_min_level;
    }

    //Getters
    public String getItem_name() {
        return item_name;
    }

    public double getItem_market_price() {
        return item_market_price;
    }

    public int getItem_min_level() {
        return item_min_level;
    }
}
