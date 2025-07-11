package Game;

import item.Item;
import living.heroes.Hero;
import living.heroes.Paladin;
import living.heroes.Sorcerer;
import living.heroes.Warrior;
import spell.Spell;

import java.util.List;
import java.util.Scanner;

public class GameEngine {
    private Grid grid;
    private Player player;
    private final Scanner scanner;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("===================================");
        System.out.println("    Welcome to Heroes & Monsters! ");
        System.out.println("===================================");
        System.out.println("Prepare to embark on an epic adventure with your team of heroes.");
        System.out.println("You will explore mysterious lands, battle fearsome monsters, and visit bustling markets.");
        System.out.println("May fortune and courage guide your journey!\n");

        initializeGrid();
        initializePlayerAndHeroes();

        System.out.println("\n--- Your Team's Initial Status ---");
        this.player.displayStats();
        grid.displayGrid(player);

        mainGameLoop();
    }

    private void initializeGrid() {
        System.out.print("Enter grid rows: ");
        int rows = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter grid columns: ");
        int cols = Integer.parseInt(scanner.nextLine());
        grid = new Grid(rows, cols);
    }

    private void initializePlayerAndHeroes() {
        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();
        int startRow = 0, startCol = 0;
        player = new Player(playerName, startRow, startCol);

        int numHeroes = 0;
        while (true) {
            System.out.print("How many heroes in your team? (1–3): ");
            try {
                numHeroes = Integer.parseInt(scanner.nextLine());
                if (numHeroes >= 1 && numHeroes <= 3) {
                    break;
                } else {
                    System.out.println("You must choose between 1 and 3 heroes. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
            }
        }

        for (int i = 1; i <= numHeroes; i++) {
            System.out.print("Enter name for Hero " + i + ": ");
            String heroName = scanner.nextLine();
            int type = 0;
            while (true) {
                System.out.println("Choose hero type for " + heroName + ": 1) Warrior 2) Sorcerer 3) Paladin");
                try {
                    type = Integer.parseInt(scanner.nextLine());
                    if (type >= 1 && type <= 3) {
                        break;
                    } else {
                        System.out.println("Error: Invalid hero type. Please enter 1, 2, or 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Please enter a valid number.");
                }
            }
            Hero hero;
            switch (type) {
                case 1:
                    hero = new Warrior(heroName);
                    break;
                case 2:
                    hero = new Sorcerer(heroName);
                    break;
                case 3:
                    hero = new Paladin(heroName);
                    break;
                default:
                    hero = new Warrior(heroName); // This default is unreachable
            }
            player.addHero(hero);
        }
    }

    private void mainGameLoop() {
        // --- Starting Market Phase ---
        Market market = new Market();
        market.openStartingMarket(player); // Player immediately enters the market

        // Show the team's status after shopping
        System.out.println("\n--- Your Team's Status After Shopping ---");
        player.displayStats();

        // Output a message to initiate the adventure
        System.out.println("\nYour heroes are ready! The adventure begins now. Explore the world, face challenges, and lead your team to victory!\n");
        grid.displayGrid(player);

        // --- Main Game Loop ---
        while (true) {
            System.out.print("Enter move (up/down/left/right), 'status', or 'exit': ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            if (input.equalsIgnoreCase("status")) {
                player.displayStats();
                continue;
            }

            if (player.move(input, grid)) {
                grid.displayGrid(player); // Show grid after every move
            }
            int row = player.getCurrentRow();
            int col = player.getCurrentCol();

            // Get the cell type at the player's current position
            Grid.CellType cellType = grid.getGrid(row, col);

            // Take action based on the cell type
            switch (cellType) {
                case FIGHT:
                    System.out.println("A wild monster appears! Prepare for battle!");
                    Fight fight = new Fight(player.getHeroes(), 3);
                    fight.startBattle();
                    // Optionally, after battle, you could restore some hero stats or give rewards
                    break;

                case MARKET:
                    System.out.println("You have entered a market. What would you like to do?");
                    boolean inMarket = true;
                    while (inMarket) {
                        System.out.println("1. Buy items/spells");
                        System.out.println("2. Sell items/spells");
                        System.out.println("3. View available items/spells");
                        System.out.println("4. Exit market");
                        System.out.print("Enter choice: ");
                        String choiceStr = scanner.nextLine();
                        int choice;
                        try {
                            choice = Integer.parseInt(choiceStr);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Try again.");
                            continue;
                        }
                        switch (choice) {
                            case 1:
                                // 1. Hero selection
                                List<Hero> heroes = player.getHeroes();
                                System.out.println("Choose a hero to buy for:");
                                for (int i = 0; i < heroes.size(); i++) {
                                    System.out.println((i + 1) + ". " + heroes.get(i).getLivingName() + " (Gold: " + heroes.get(i).getMoney() + ")");
                                }
                                int heroChoice;
                                try {
                                    heroChoice = Integer.parseInt(scanner.nextLine()) - 1;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Returning to market menu.");
                                    break;
                                }
                                if (heroChoice < 0 || heroChoice >= heroes.size()) {
                                    System.out.println("Invalid hero selection. Returning to market menu.");
                                    break;
                                }
                                Hero selectedHero = heroes.get(heroChoice);

                                // 2. Product selection
                                List<Item> items = market.getAvailableItems();
                                List<Spell> spells = market.getAvailableSpells();
                                System.out.println("Available Items and Spells:");
                                market.displayAvailableItemsAndSpells();
                                System.out.print("Enter the number of the item/spell to buy: ");
                                int productChoice;
                                try {
                                    productChoice = Integer.parseInt(scanner.nextLine()) - 1;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Returning to market menu.");
                                    break;
                                }
                                Object selectedProduct;
                                if (productChoice >= 0 && productChoice < items.size()) {
                                    selectedProduct = items.get(productChoice);
                                } else if (productChoice >= items.size() && productChoice < items.size() + spells.size()) {
                                    selectedProduct = spells.get(productChoice - items.size());
                                } else {
                                    System.out.println("Invalid selection. Returning to market menu.");
                                    break;
                                }

                                // 3. Attempt purchase
                                boolean success = market.buy(selectedHero, selectedProduct);
                                if (success) {
                                    System.out.println("Purchase successful!");
                                } else {
                                    System.out.println("Purchase failed: insufficient level or money.");
                                }
                                break;
                            case 2:
                                // 1. Hero selection
                                heroes = player.getHeroes();
                                System.out.println("Choose a hero to sell from:");
                                for (int i = 0; i < heroes.size(); i++) {
                                    System.out.println((i + 1) + ". " + heroes.get(i).getLivingName() + " (Gold: " + heroes.get(i).getMoney() + ")");
                                }
                                int heroChoiceSell;
                                try {
                                    heroChoiceSell = Integer.parseInt(scanner.nextLine()) - 1;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Returning to market menu.");
                                    break;
                                }
                                if (heroChoiceSell < 0 || heroChoiceSell >= heroes.size()) {
                                    System.out.println("Invalid hero selection. Returning to market menu.");
                                    break;
                                }
                                Hero selectedHeroSell = heroes.get(heroChoiceSell);

                                // 2. Product selection from hero's inventory
                                List<Item> heroItems = selectedHeroSell.getInventory().getItems();
                                List<Spell> heroSpells = selectedHeroSell.getInventory().getSpells();

                                if (heroItems.isEmpty() && heroSpells.isEmpty()) {
                                    System.out.println("This hero has no items or spells to sell.");
                                    break;
                                }

                                System.out.println("Items and Spells in Inventory:");
                                int count = 1;
                                for (Item item : heroItems) {
                                    System.out.println(count + ". " + item.getItemName() + " (Sell for: " + (item.getItemMarketCost() * 0.5) + " gold)");
                                    count++;
                                }
                                for (Spell spell : heroSpells) {
                                    System.out.println(count + ". " + spell.getSpellName() + " (Sell for: " + (spell.getSpellCost() * 0.5) + " gold)");
                                    count++;
                                }

                                System.out.print("Enter the number of the item/spell to sell: ");
                                int sellChoice;
                                try {
                                    sellChoice = Integer.parseInt(scanner.nextLine()) - 1;
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Returning to market menu.");
                                    break;
                                }
                                boolean sellResult = false;
                                if (sellChoice >= 0 && sellChoice < heroItems.size()) {
                                    Item itemToSell = heroItems.get(sellChoice);
                                    sellResult = market.sellItem(selectedHeroSell, itemToSell);
                                } else if (sellChoice >= heroItems.size() && sellChoice < heroItems.size() + heroSpells.size()) {
                                    Spell spellToSell = heroSpells.get(sellChoice - heroItems.size());
                                    sellResult = market.sellSpell(selectedHeroSell, spellToSell);
                                } else {
                                    System.out.println("Invalid selection. Returning to market menu.");
                                    break;
                                }

                                if (sellResult) {
                                    System.out.println("Sale successful!");
                                } else {
                                    System.out.println("Sale failed: item or spell not found in inventory.");
                                }
                                break;
                            case 3:
                                market.displayAvailableItemsAndSpells();
                                break;
                            case 4:
                                inMarket = false;
                                System.out.println("Leaving the market...");
                                grid.displayGrid(player);
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    break;


                case COMMON:
                    System.out.println("You are in a common area. What would you like to do?");
                    boolean inCommonArea = true;
                    while (inCommonArea) {
                        System.out.println("\n--- Common Area Actions ---");
                        System.out.println("1. View Heroes Inventories");
                        System.out.println("2. Equip or Use an Item/Spell");
                        System.out.println("3. Show Map");
                        System.out.println("4. View Heroes' Stats");
                        System.out.println("5. Keep Moving");
                        System.out.println("6. Quit Game");
                        System.out.print("Enter the number of your choice: ");

                        input = scanner.nextLine();
                        switch (input) {
                            case "1":
                                for (Hero hero : player.getHeroes()) {
                                    System.out.println(hero.getLivingName() + "'s Inventory:");
                                    hero.getInventory().checkInventory();
                                }
                                break;
//                          case "2":
//                                player.equipOrUseMenu(scanner);
//                                break;
                            case "3":
                                grid.displayGrid(player);
                                break;
                            case "4":
                                player.displayStats();
                                break;
                            case "5":
                                inCommonArea = false;
                                grid.displayGrid(player);
                                break;
                            case "6":
                                System.out.println("Thank you for playing! Goodbye.");
                                System.exit(0); // This will terminate the program
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    break;


                case NON_ACCESSIBLE:
                    System.out.println("You cannot move to a non-accessible cell! Please choose another direction.");
                    break;

                default:
                    System.out.println("Unknown cell type encountered.");
                    break;
            }
        }

        scanner.close();
        System.out.println("Game Over. Thanks for playing!");

    }
}
