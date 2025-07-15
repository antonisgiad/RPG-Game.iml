package game;

import item.Armor;
import item.Potion;
import item.Weapon;
import living.heroes.Hero;
import living.heroes.Paladin;
import living.heroes.Sorcerer;
import living.heroes.Warrior;
import utils.RandomUtil;
import item.Item;
import java.util.ArrayList;
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

        // Initialize the grid
        grid = new Grid();

        // Initialize player and heroes
        initializePlayerAndHeroes();

        System.out.println("\n--- Your Team's Initial Status ---");
        this.player.displayHeroesStatus();
        grid.displayGrid(player);

        mainGameLoop();
    }


    private void initializePlayerAndHeroes() {
        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();

        // Starting position: set player in the first market cell.
        int[] marketPos = grid.getFirstMarketCell();
        player = new Player(playerName, marketPos[0], marketPos[1]);

        // Ask for number of heroes (1–3)
        int numHeroes;
        while (true) {
            System.out.print("How many heroes in your team? (1–3): ");
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                numHeroes = Integer.parseInt(input);
                break;
            }
            System.out.println("You must choose between 1 and 3 heroes. Try again.");
        }

        // Initialize heroes
        for (int i = 1; i <= numHeroes; i++) {
            // Choose hero name and type
            System.out.print("Enter name for Hero " + i + ": ");
            String heroName = scanner.nextLine();

            int type = 0;
            while (true) {
                System.out.print("Choose hero type for " + heroName + ": 1) Warrior 2) Sorcerer 3) Paladin: ");
                String input = scanner.nextLine();
                if (input.equals("1") || input.equals("2") || input.equals("3")) {
                    type = Integer.parseInt(input);
                    break;
                }
                System.out.println("Error: Invalid hero type. Please enter 1, 2, or 3.");
            }

            // Create the hero based on the chosen type
            Hero hero;
            if (type == 1) {
                hero = new Warrior(heroName);
            }
            else if (type == 2) {
                hero = new Sorcerer(heroName);
            }
            else if (type == 3) {
                hero = new Paladin(heroName);
            }
            else {
                hero = null;
            }

            // Add the hero to the player's party
            player.getParty().add(hero);
        }
    }

    private void mainGameLoop() {
        // --- Starting Market Phase ---
        Market market = new Market();
        market.openMarket(player); // Player immediately enters the market

        // Show the team's status after shopping
        System.out.println("\n--- Your Team's Status After Shopping ---");
        player.displayHeroesStatus();

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
                player.displayHeroesStatus();
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
                case MARKET:
                    System.out.println("You have entered a Market!");
                    market.openMarket(player);
                    break;

                case COMMON:
                    System.out.println("You are in a common area. What would you like to do?");
                    boolean inCommonArea = true;
                    while (inCommonArea) {
                        System.out.println("\n--- Common Area Actions ---");
                        System.out.println("1. View Heroes Inventories");
                        System.out.println("2. Equip a Weapon or an Armor");
                        System.out.println("3. Use a Potion");
                        System.out.println("4. View Heroes Status");
                        System.out.println("5. Show Map");
                        System.out.println("6. Keep Moving");
                        System.out.println("7. Quit Game");
                        System.out.print("Enter the number of your choice: ");

                        input = scanner.nextLine();
                        switch (input) {
                            case "1":
                                for (int i = 0; i < player.getParty().size(); i++) {
                                    Hero hero = player.getParty().get(i);
                                    System.out.println(hero.getLivingName() + "'s Inventory:");
                                    hero.getInventory().checkInventory();
                                }
                                break;
                            case "2":
                                boolean equipMenuExit = false;
                                while (!equipMenuExit) {
                                    List<Hero> heroes = player.getParty();
                                    // Select a hero
                                    System.out.println("Select a hero to equip for (or 0 to exit):");
                                    for (int i = 0; i < heroes.size(); i++) {
                                        Hero hero = heroes.get(i);
                                        System.out.println((i + 1) + ". " + hero.getLivingName() + " (Level: " + hero.getLivingLevel() + ")");
                                    }
                                    System.out.print("Choice: ");
                                    String heroInput = scanner.nextLine();
                                    if (!heroInput.matches("\\d+")) {
                                        System.out.println("Invalid input. Try again.");
                                        continue;
                                    }
                                    int heroChoice = Integer.parseInt(heroInput) - 1;
                                    if (heroChoice == -1) {
                                        break;
                                    }
                                    if (heroChoice < 0 || heroChoice >= heroes.size()) {
                                        System.out.println("Invalid hero selection. Try again.");
                                        continue;
                                    }
                                    Hero selectedHero = heroes.get(heroChoice);

                                    // Ask for item type
                                    System.out.println("Which type do you want to equip?");
                                    System.out.println("1. Weapon");
                                    System.out.println("2. Armor");
                                    System.out.print("Enter choice (or 0 to cancel): ");
                                    String typeInput = scanner.nextLine();
                                    if (!typeInput.matches("\\d+")) {
                                        System.out.println("Invalid input. Try again.");
                                        continue;
                                    }
                                    int typeChoice = Integer.parseInt(typeInput);
                                    if (typeChoice == 0) {
                                        break;
                                    }

                                    // Create lists for each item type
                                    List<Weapon> weapons = new ArrayList<>();
                                    List<Armor> armors = new ArrayList<>();
                                    for (int i = 0; i < selectedHero.getInventory().getItems().size(); i++) {
                                        Item item = selectedHero.getInventory().getItems().get(i);
                                        String type = item.getClass().getSimpleName();
                                        if (type.equals("Weapon")) {
                                            weapons.add((Weapon) item);
                                        }
                                        else if (type.equals("Armor")) {
                                            armors.add((Armor) item);
                                        }
                                    }

                                    switch (typeChoice) {
                                        case 1: // Weapon
                                            if (weapons.isEmpty()) {
                                                System.out.println("No weapons to equip.");
                                                break;
                                            }
                                            System.out.println("Weapons:");
                                            for (int i = 0; i < weapons.size(); i++) {
                                                System.out.println((i + 1) + ". " + weapons.get(i).getItemName());
                                            }
                                            System.out.print("Select weapon (or 0 to cancel): ");
                                            String wInput = scanner.nextLine();
                                            if (!wInput.matches("\\d+")) {
                                                System.out.println("Invalid input. Try again.");
                                                continue;
                                            }
                                            int wChoice = Integer.parseInt(wInput);
                                            if (wChoice == 0) {
                                                break;
                                            }
                                            if (wChoice < 1 || wChoice > weapons.size()) {
                                                System.out.println("Invalid selection.");
                                                break;
                                            }
                                            Weapon chosenWeapon = weapons.get(wChoice - 1);
                                            boolean equippedW = selectedHero.getInventory().equipWeapon(chosenWeapon, selectedHero, chosenWeapon.getHands());
                                            System.out.println(equippedW ? "Equipped: " + chosenWeapon.getItemName() + "!" : "Cannot equip weapon: hands are full.");
                                            break;
                                        case 2: // Armor
                                            if (armors.isEmpty()) {
                                                System.out.println("No armors to equip.");
                                                break;
                                            }
                                            System.out.println("Armors:");
                                            for (int i = 0; i < armors.size(); i++) {
                                                System.out.println((i + 1) + ". " + armors.get(i).getItemName());
                                            }
                                            System.out.print("Select armor (or 0 to cancel): ");
                                            String aInput = scanner.nextLine();
                                            if (!aInput.matches("\\d+")) {
                                                System.out.println("Invalid input. Try again.");
                                                continue;
                                            }
                                            int aChoice = Integer.parseInt(aInput);
                                            if (aChoice == 0) {
                                                break;
                                            }
                                            if (aChoice < 1 || aChoice > armors.size()) {
                                                System.out.println("Invalid selection.");
                                                break;
                                            }
                                            Armor chosenArmor = armors.get(aChoice - 1);
                                            boolean equippedA = selectedHero.getInventory().equipArmor(chosenArmor, selectedHero);
                                            System.out.println(equippedA ? "Equipped: " + chosenArmor.getItemName() + "!" : "Could not equip this armor.");
                                            break;
                                        default:
                                            System.out.println("Invalid type choice.");
                                            break;
                                    }
                                    equipMenuExit = true;
                                }
                                break;
                            case "3":
                                boolean potionMenuExit = false;
                                while (!potionMenuExit) {
                                    List<Hero> heroes = player.getParty();
                                    System.out.println("Select a hero to use a potion for (or 0 to exit):");
                                    for (int i = 0; i < heroes.size(); i++) {
                                        Hero hero = heroes.get(i);
                                        System.out.println((i + 1) + ". " + hero.getLivingName() + " (Level: " + hero.getLivingLevel() + ")");
                                    }
                                    System.out.print("Choice: ");
                                    String heroInput = scanner.nextLine();
                                    if (!heroInput.matches("\\d+")) {
                                        System.out.println("Invalid input. Try again.");
                                        continue;
                                    }
                                    int heroChoice = Integer.parseInt(heroInput) - 1;
                                    if (heroChoice == -1) {
                                        break;
                                    }
                                    if (heroChoice < 0 || heroChoice >= heroes.size()) {
                                        System.out.println("Invalid hero selection. Try again.");
                                        continue;
                                    }
                                    Hero selectedHero = heroes.get(heroChoice);

                                    // Filter potions from the hero's inventory
                                    List<Item> heroItems = selectedHero.getInventory().getItems();
                                    List<Potion> potions = new ArrayList<>();
                                    for (int i = 0; i < heroItems.size(); i++) {
                                        if (heroItems.get(i).getClass().getSimpleName().equals("Potion")) {
                                            potions.add((Potion) heroItems.get(i));
                                        }
                                    }

                                    if (potions.isEmpty()) {
                                        System.out.println("No potions available to use.");
                                        break;
                                    }
                                    System.out.println("Available Potions:");
                                    for (int i = 0; i < potions.size(); i++) {
                                        System.out.println((i + 1) + ". " + potions.get(i).getItemName());
                                    }

                                    System.out.print("Select potion to use (or 0 to cancel): ");
                                    String potionInput = scanner.nextLine();
                                    if (!potionInput.matches("\\d+")) {
                                        System.out.println("Invalid input. Try again.");
                                        continue;
                                    }
                                    int potionChoice = Integer.parseInt(potionInput);
                                    if (potionChoice == 0) {
                                        break;
                                    }
                                    if (potionChoice < 1 || potionChoice > potions.size()) {
                                        System.out.println("Invalid selection.");
                                        break;
                                    }
                                    Potion chosenPotion = potions.get(potionChoice - 1);
                                    // Apply potion effect and remove it from inventory
                                    chosenPotion.increaseRandomStatistic(selectedHero);
                                    chosenPotion.destroyWhenUsed(selectedHero.getInventory());
                                    System.out.println("Potion used successfully.");
                                    potionMenuExit = true;
                                }
                                break;
                            case "4":
                                player.displayHeroesStatus();
                                break;
                            case "5":
                                grid.displayGrid(player);
                                break;
                            case "6":
                                inCommonArea = false; // Exit the common area loop
                                break;
                            case "7":
                                System.out.println("Thank you for playing! Goodbye.");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    break;

                case FIGHT:
                    System.out.println("A wild monster appears! Prepare for battle!");
                    Fight fight = new Fight(player.getParty(), RandomUtil.randomLevel(1, 3));
                    fight.startBattle();
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
