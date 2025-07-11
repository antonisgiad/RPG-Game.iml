package Game;

import utils.RandomUtil;

public class Grid {
    //Variables
    public enum CellType { COMMON, MARKET, FIGHT, NON_ACCESSIBLE }
    int rows;
    int cols;
    CellType[][] grid;

    //Functions
    //Constructor
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new CellType[rows][cols];
        generateRandomGrid();
    }
    //Generate grid
    private void generateRandomGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int roll = RandomUtil.randomLevel(0, 99); // 0-99 inclusive
                if (roll < 10) {
                    grid[i][j] = CellType.MARKET;
                }
                else if (roll < 90) {
                    grid[i][j] = CellType.COMMON;
                }
                else {
                    grid[i][j] = CellType.NON_ACCESSIBLE;
                }
            }
        }
        ensureAtLeastOneOfEachType();
    }

    private void ensureAtLeastOneOfEachType() {
        boolean foundMarket = false, foundFight = false, foundCommon = false, foundNonAccessible = false;

        // 1. Scan the grid to check which types exist
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                switch (grid[i][j]) {
                    case MARKET: foundMarket = true; break;
                    case FIGHT: foundFight = true; break;
                    case COMMON: foundCommon = true; break;
                    case NON_ACCESSIBLE: foundNonAccessible = true; break;
                }
            }
        }

        // 2. For each missing type, randomly place it on a cell that is not already that type
        placeMissingTypeIfNeeded(!foundMarket, CellType.MARKET);
        placeMissingTypeIfNeeded(!foundFight, CellType.FIGHT);
        placeMissingTypeIfNeeded(!foundCommon, CellType.COMMON);
        placeMissingTypeIfNeeded(!foundNonAccessible, CellType.NON_ACCESSIBLE);
    }

    // Helper method to place a missing type
    private void placeMissingTypeIfNeeded(boolean isMissing, CellType type) {
        if (!isMissing) return;
        int maxAttempts = 100;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int r = RandomUtil.randomLevel(0, rows - 1);
            int c = RandomUtil.randomLevel(0, cols - 1);
            if (grid[r][c] != type) {
                grid[r][c] = type;
                return;
            }
        }
    }

    public void displayGrid(Player player) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == player.getCurrentRow() && j == player.getCurrentCol()) {
                    System.out.print("P ");
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

    //Getters & Setters
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public CellType getGrid(int row, int col) {
        return grid[row][col];
    }

    public void setGrid(CellType[][] grid) {
        this.grid = grid;
    }
}