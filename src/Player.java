import java.util.ArrayList;

public class Player {
    private String playerName;
    private ArrayList<Hero> party;         // The player's team of heroes (at least 1 and max 3)
    private ArrayList<Item> inventory;     // Items owned by the player
    private int gold;                      // Player's money
    private int currentRow, currentCol;    // Position on the grid

    // Constructor
    public Player(String playerName, int startRow, int startCol) {
        this.playerName = playerName;
        this.party = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.gold = 0;
        this.currentRow = startRow;
        this.currentCol = startCol;
    }
    //Move player's team of heroes around the grid
    public boolean move(String direction, Grid grid) {
        int newRow = currentRow;
        int newCol = currentCol;

        switch (direction.toLowerCase()) {
            case "up":    newRow--; break;
            case "down":  newRow++; break;
            case "left":  newCol--; break;
            case "right": newCol++; break;
            default:
                System.out.println("Invalid direction! Use up, down, left, or right.");
                return false;
        }

        if (newRow < 0 || newRow >= grid.rows || newCol < 0 || newCol >= grid.cols) {
            System.out.println("Out of bounds! Cannot move there.");
            return false;
        }
        if (grid.grid[newRow][newCol] == Grid.CellType.NON_ACCESSIBLE) {
            System.out.println("Cell is non-accessible! Cannot move there.");
            return false;
        }

        moveTo(newRow, newCol);
        System.out.println("Moved to (" + newRow + ", " + newCol + ")");
        return true;
    }

    public void performCellAction(Grid grid) {
        Grid.CellType cellType = grid.grid[currentRow][currentCol];
        switch (cellType) {
            case MARKET:
                System.out.println("You have entered a Market! You can buy or sell items here.");
                // Call market logic here
                break;
            case FIGHT:
                System.out.println("A wild enemy appears! Prepare for battle.");
                // Call battle logic here
                break;
            case COMMON:
                System.out.println("This is a common area. You can rest or explore.");
                // Add random events or resting logic here
                break;
            case NON_ACCESSIBLE:
                System.out.println("This area is not accessible.");
                break;
        }
    }
    // Methods to manage heroes
    public void addHero(Hero hero) { party.add(hero); }
    public void removeHero(Hero hero) { party.remove(hero); }
    public ArrayList<Hero> getParty() { return party; }

    // Inventory management
    public void addItem(Item item) { inventory.add(item); }
    public void removeItem(Item item) { inventory.remove(item); }
    public ArrayList<Item> getInventory() { return inventory; }

    // Gold management
    public int getGold() { return gold; }
    public void addGold(int amount) { gold += amount; }
    public void spendGold(int amount) { gold -= amount; }

    // Grid position
    public int getCurrentRow() { return currentRow; }
    public int getCurrentCol() { return currentCol; }
    public void moveTo(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
    }

    // Display player status
    public void displayStatus() {
        System.out.println("Player: " + playerName);
        System.out.println("Gold: " + gold);
        System.out.println("Location: (" + currentRow + ", " + currentCol + ")");
        System.out.println("Party: " + party);
        System.out.println("Inventory: " + inventory);
    }
}