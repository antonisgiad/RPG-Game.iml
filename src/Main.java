import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rows = 8;
        int cols = 8;

        Grid grid = new Grid(rows, cols);
        Player player = new Player("TestPlayer", 0, 0);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Generated Grid:");
        grid.displayGrid(player);

        while (true) {
            player.displayStatus();
            System.out.print("Enter move direction (up/down/left/right) or 'exit': ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            // This is the correct place for the action-triggering block:
            if (player.move(input, grid)) {
                grid.displayGrid(player);
                player.performCellAction(grid);
            }
        }

        scanner.close();
    }
}
