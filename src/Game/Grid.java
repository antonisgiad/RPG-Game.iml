package Game;

import utils.RandomUtil;

public class Grid {
    //Variables
    public enum CellType { COMMON, MARKET, FIGHT, NON_ACCESSIBLE }
    private int rows;
    private int cols;
    CellType[][] grid;

    //Functions

    //Constructor
    public Grid() {
        this.rows = 10;
        this.cols = 10;
        this.grid = new CellType[rows][cols];
        generateRandomGrid();
    }

    //Generate grid
    private void generateRandomGrid() {
        // Set all cells to COMMON
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = CellType.COMMON;
            }
        }

        // Add market areas
        int Markets = 5;
        int placedMarkets = 0;
        // Place markets randomly in the grid
        while (placedMarkets < Markets) {
            int r = RandomUtil.randomLevel(0, rows - 1);
            int c = RandomUtil.randomLevel(0, cols - 1);
            if (grid[r][c] == CellType.COMMON) {
                grid[r][c] = CellType.MARKET;
                placedMarkets++;
            }
        }

        // Add NON_ACCESSIBLE cells
        int NonAccessible = 10;
        int placedNonAccessible = 0;
        while (placedNonAccessible < NonAccessible) {
            int r = RandomUtil.randomLevel(0, rows - 1);
            int c = RandomUtil.randomLevel(0, cols - 1);
            if (grid[r][c] == CellType.COMMON) {
                grid[r][c] = CellType.NON_ACCESSIBLE;
                placedNonAccessible++;
            }
        }

        // Add FIGHT cells
        int Fights = 8;
        int placedFights = 0;
        while (placedFights < Fights) {
            int r = RandomUtil.randomLevel(0, rows - 1);
            int c = RandomUtil.randomLevel(0, cols - 1);
            if (grid[r][c] == CellType.COMMON) {
                grid[r][c] = CellType.FIGHT;
                placedFights++;
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