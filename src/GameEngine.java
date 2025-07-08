import java.util.Scanner;

public class GameEngine {
    Grid grid;
    Player player;
    Scanner scanner;
    boolean isRunning;

    public GameEngine() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        initializeGame();
        isRunning = true;
        while (isRunning) {
            render();
            processInput();
            updateGameState();
        }
        endGame();
    }

    public void initializeGame() {
        scanner = new Scanner(System.in);

        // 1. Choose grid dimensions
        int rows = 8;
        int cols = 8;
        grid = new Grid(rows, cols);

        // 2. Choose player name
        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();

        // 3. Set player starting position (e.g., top-left corner)
        int startRow = 0, startCol = 0;

        // 4. Ensure starting cell is accessible
        while (grid.grid[startRow][startCol] == Grid.CellType.NON_ACCESSIBLE) {
            startRow = (startRow + 1) % rows;
            startCol = (startCol + 1) % cols;
        }

        // 5. Create player
        player = new Player(playerName, startRow, startCol);

        // 6. Initial display
        System.out.println("\nWelcome, " + playerName + "!");
        grid.displayGrid(player);
        player.displayStatus();
    }

    void render() {
        grid.displayGrid(player);
        player.displayStatus();
    }

    void processInput() {
        System.out.print("Enter move (up/down/left/right) or 'exit': ");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("exit")) {
            isRunning = false;
            return;
        }
        if (player.move(input, grid)) {
            handleCellAction();
        }
    }

    public void initializeHeroes(Player player) {
        Scanner scanner = new Scanner(System.in);
        int maxHeroes = 3;
        System.out.println("How many heroes do you want in your party? (1-" + maxHeroes + ")");
        int numHeroes = Integer.parseInt(scanner.nextLine());
        numHeroes = Math.max(1, Math.min(numHeroes, maxHeroes));

        for (int i = 1; i <= numHeroes; i++) {
            System.out.print("Enter name for Hero " + i + ": ");
            String heroName = scanner.nextLine();

            // Select type of hero
            System.out.println("Select hero type for " + heroName + ":");
            System.out.println("1. Warrior");
            System.out.println("2. Sorcerer");
            System.out.println("3. Paladin");
            int typeChoice = Integer.parseInt(scanner.nextLine());

            String living_name, int living_level, double healthPower, double magicPower,
            double strength, double dexterity, double agility, double money, double experience,
            double increased_warrior_strength, double increased_warrior_agility




            Hero hero;
            switch (typeChoice) {
                case 1:
                    hero = new Warrior(heroName, 15, 20);
                    break;
                case 2:
                    hero = new Sorcerer(heroName, 15 );
                    break;
                case 3:
                    hero = new Paladin(heroName);
                    break;
                default:
                    System.out.println("Invalid choice, defaulting to Warrior.");
                    hero = new Warrior(heroName);
            }
            player.addHero(hero);
        }

        System.out.println("Party initialized with " + numHeroes + " heroes!");
    }


    void handleCellAction() {
        player.performCellAction(grid);
    }

    void endGame() {
        System.out.println("Game Over. Thanks for playing!");
    }
}