package game;

import living.heroes.Hero;

import java.util.ArrayList;
import java.util.List;

public class Player {
    // Variables
    private String playerName;
    private ArrayList<Hero> party;         // The player's team of heroes (at least 1 and max 3)
    private int currentRow, currentCol;    // Position on the grid

    // Functions

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
            case "w":    newRow--; break; // up
            case "s":    newRow++; break; // down
            case "a":    newCol--; break; // left
            case "d":    newCol++; break; // right
            default:
                System.out.println("Invalid direction! Use w (up), a (left), s (down), or d (right).");
                return false;
        }

        if (newRow < 0 || newRow >= grid.getRows() || newCol < 0 || newCol >= grid.getCols()) {
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

    // Methods to manage heroes
    public void removeHero(Hero hero) { party.remove(hero); }

    // Grid position
    public int getCurrentRow() { return currentRow; }
    public int getCurrentCol() { return currentCol; }
    public void moveTo(int row, int col) {
        this.currentRow = row;
        this.currentCol = col;
    }

    // Display player stats
    public void displayHeroesStatus() {
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
            System.out.println("-------------------------------------------------");
        }
    }

    //Getters & Setters
    public List<Hero> getParty() {
        return  party;
    }

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
}