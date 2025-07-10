package Game;

import item.Item;
import living.heroes.Hero;
import spell.Spell;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String playerName;
    private ArrayList<Hero> party;         // The player's team of heroes (at least 1 and max 3)
    private int currentRow, currentCol;    // Position on the grid

    // Constructor
    public Player(String playerName, int startRow, int startCol) {
        this.playerName = playerName;
        this.party = new ArrayList<>();
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
    public void addHero(Hero hero) {
        if (party.size() < 3) {
            party.add(hero);
        } else {
            System.out.println("You cannot have more than 3 heroes in your team!");
        }
    }
    public void removeHero(Hero hero) { party.remove(hero); }
    public ArrayList<Hero> getParty() { return party; }

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
        System.out.println("Location: (" + currentRow + ", " + currentCol + ")");
        System.out.println("Party:");

        for (Hero hero : party) {
            System.out.println("-------------------------------------------------");
            // Show hero type using getHeroType() or class name
            String heroType = hero.getClass().getSimpleName(); // Get hero type by class name

            System.out.println("Hero: " + hero.getLivingName() + " (" + heroType + ")");
            System.out.println("  Level: " + hero.getLivingLevel());
            System.out.println("  Health Power: " + hero.getHealthPower());
            System.out.println("  Magic Power: " + hero.getMagicPower());
            System.out.println("  Strength: " + hero.getStrength());
            System.out.println("  Dexterity: " + hero.getDexterity());
            System.out.println("  Agility: " + hero.getAgility());
            System.out.println("  Money: " + hero.getMoney());
            System.out.println("  Experience: " + hero.getExperience());

            // Display Items
            System.out.print("  Items: ");
            List<Item> items = hero.getInventory().getItems();
            if (items.isEmpty()) {
                System.out.print("None");
            } else {
                for (int i = 0; i < items.size(); i++) {
                    System.out.print(items.get(i).getItemName());
                    if (i < items.size() - 1) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println();

            // Display Spells
            System.out.print("  Spells: ");
            List<Spell> spells = hero.getInventory().getSpells();
            if (spells.isEmpty()) {
                System.out.print("None");
            } else {
                for (int i = 0; i < spells.size(); i++) {
                    System.out.print(spells.get(i).getSpellName());
                    if (i < spells.size() - 1) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("-------------------------------------------------");
    }


    //Getters & Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setParty(ArrayList<Hero> party) {
        this.party = party;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }

    public List<Hero> getHeroes() {
        return  party;
    }
}