import java.util.Random;

public class Grid {
    //Variables
    public enum CellType { COMMON, MARKET, FIGHT, NON_ACCESSIBLE }
    int rows;
    int cols;
    CellType[][] grid;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new CellType[rows][cols];
        generateRandomGrid();
    }

    private void generateRandomGrid() {
        Random rand = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int roll = rand.nextInt(100);
                if (roll < 10) {
                    grid[i][j] = CellType.MARKET; // 10% chance
                } else if (roll < 30) {
                    grid[i][j] = CellType.FIGHT; // 20% chance
                } else if (roll < 90) {
                    grid[i][j] = CellType.COMMON; // 60% chance
                } else {
                    grid[i][j] = CellType.NON_ACCESSIBLE; // 10% chance
                }
            }
        }
        // Guarantee at least one of each type for gameplay
        ensureAtLeastOneOfEachType();
    }

    private void ensureAtLeastOneOfEachType() {
        boolean foundMarket = false, foundFight = false, foundCommon = false, foundNonAccessible = false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (grid[i][j]) {
                    case MARKET:
                        foundMarket = true;
                        break;
                    case FIGHT:
                        foundFight = true;
                        break;
                    case COMMON:
                        foundCommon = true;
                        break;
                    case NON_ACCESSIBLE:
                        foundNonAccessible = true;
                        break;
                }
            }
        }

        Random rand = new Random();
        // If any type is missing, randomly assign it to a cell (may overwrite another type)
        if (!foundMarket) grid[rand.nextInt(rows)][rand.nextInt(cols)] = CellType.MARKET;
        if (!foundFight) grid[rand.nextInt(rows)][rand.nextInt(cols)] = CellType.FIGHT;
        if (!foundCommon) grid[rand.nextInt(rows)][rand.nextInt(cols)] = CellType.COMMON;
        if (!foundNonAccessible) grid[rand.nextInt(rows)][rand.nextInt(cols)] = CellType.NON_ACCESSIBLE;
    }

    public void displayGrid(Player player) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == player.getCurrentRow() && j == player.getCurrentCol()) {
                    System.out.print("P "); // Player's position
                } else {
                    switch (grid[i][j]) {
                        case MARKET: System.out.print("M "); break;
                        case FIGHT: System.out.print("F "); break;
                        case COMMON: System.out.print("C "); break;
                        case NON_ACCESSIBLE: System.out.print("X "); break;
                    }
                }
            }
            System.out.println();
        }
    }
}