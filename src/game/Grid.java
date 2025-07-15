package game;

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
    // Method to return the first market cell coordinates
    public int[] getFirstMarketCell() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == CellType.MARKET) {
                    return new int[]{i, j};
                }
            }
        }
        // Fallback to center if no market is found.
        return new int[]{rows / 2, cols / 2};
    }

    public void displayGrid(Player player) {
        // Print top border
        String horizontalBorder = "+";
        for (int j = 0; j < cols; j++) {
            horizontalBorder += "---+";
        }
        System.out.println(horizontalBorder);

        for (int i = 0; i < rows; i++) {
            StringBuilder row = new StringBuilder("|");
            for (int j = 0; j < cols; j++) {
                String cell;
                if (i == player.getCurrentRow() && j == player.getCurrentCol()) {
                    // ANSI escape for bright green text for player's position
                    cell = "\033[1;32mP\033[0m";
                } else {
                    switch (grid[i][j]) {
                        case MARKET:
                            cell = "M";
                            break;
                        case FIGHT:
                            cell = "F";
                            break;
                        case COMMON:
                            cell = "C";
                            break;
                        case NON_ACCESSIBLE:
                            cell = "X";
                            break;
                        default:
                            cell = " ";
                            break;
                    }
                }
                row.append(" " + cell + " |");
            }
            System.out.println(row);
            System.out.println(horizontalBorder);
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