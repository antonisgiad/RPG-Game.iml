import game.GameEngine;
import game.Grid;
import game.Player;
import item.*;
import living.heroes.*;
import living.monster.Monster;
import spell.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameTest {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getChoice();

            switch (choice) {
                case 1: testHeroCreation(); break;
                case 2: testInventorySystem(); break;
                case 3: testEquipmentSystem(); break;
                case 4: testWASDMovement(); break;
                case 5: testCombatSimulation(); break;
                case 6: testHeroRegenerationAfterBattle(); break;
                case 7: testMarketSystem(); break;
                case 8: testLevelUpMechanic(); break;
                case 0: running = false; break;
                default: System.out.println("Invalid choice, try again.");
            }

            if (running && choice >= 0 && choice <= 8) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        System.out.println("Test program terminated.");
    }

    private static void printMenu() {
        System.out.println("\n===== RPG GAME TEST SUITE =====");
        System.out.println("1. Test Hero Creation");
        System.out.println("2. Test Inventory System");
        System.out.println("3. Test Equipment System");
        System.out.println("4. Test WASD Movement");
        System.out.println("5. Test Combat Simulation");
        System.out.println("6. Test Hero Regeneration After Battle");
        System.out.println("7. Test Market System");
        System.out.println("8. Test Level Up Mechanic");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void testHeroCreation() {
        System.out.println("\n===== TESTING HERO CREATION =====");

        Hero warrior = new Warrior("TestWarrior");
        Hero paladin = new Paladin("TestPaladin");
        Hero sorcerer = new Sorcerer("TestSorcerer");

        System.out.println("\nWarrior Stats:");
        printHeroStats(warrior);

        System.out.println("\nPaladin Stats:");
        printHeroStats(paladin);

        System.out.println("\nSorcerer Stats:");
        printHeroStats(sorcerer);
    }

    private static void printHeroStats(Hero hero) {
        System.out.println("Name: " + hero.getLivingName());
        System.out.println("Level: " + hero.getLivingLevel());
        System.out.println("Health: " + hero.getHealthPower());
        System.out.println("Magic: " + hero.getMagicPower());
        System.out.println("Strength: " + hero.getStrength());
        System.out.println("Dexterity: " + hero.getDexterity());
        System.out.println("Agility: " + hero.getAgility());
        System.out.println("Money: " + hero.getMoney());
    }

    private static void testInventorySystem() {
        System.out.println("\n===== TESTING INVENTORY SYSTEM =====");

        Hero hero = new Warrior("InventoryTester");

        Weapon sword = new Weapon("Test Sword", 10.0, 1, 5.0, 1);
        Armor armor = new Armor("Test Armor", 15.0, 1, 3.0);
        Potion healthPotion = new Potion("Health Potion", 5.0, 1, 2.0);

        System.out.println("Adding items to inventory...");
        hero.getInventory().addItem(sword);
        hero.getInventory().addItem(armor);
        hero.getInventory().addItem(healthPotion);

        System.out.println("\nChecking inventory contents:");
        hero.getInventory().checkInventory();

        System.out.println("\nUsing health potion...");
        System.out.println("Hero health before: " + hero.getHealthPower());
        healthPotion.increaseRandomStatistic(hero);
        healthPotion.destroyWhenUsed(hero.getInventory());

        System.out.println("\nChecking inventory after using potion:");
        hero.getInventory().checkInventory();
    }

    private static void testEquipmentSystem() {
        System.out.println("\n===== TESTING EQUIPMENT SYSTEM =====");

        Hero hero = new Warrior("EquipmentTester");
        printHeroStats(hero);

        Weapon oneHandSword = new Weapon("One-Hand Sword", 10.0, 1, 5.0, 1);
        Weapon twoHandSword = new Weapon("Two-Hand Sword", 20.0, 1, 10.0, 2);
        Armor lightArmor = new Armor("Light Armor", 15.0, 1, 3.0);

        System.out.println("\nAdding equipment to inventory...");
        hero.getInventory().addItem(oneHandSword);
        hero.getInventory().addItem(twoHandSword);
        hero.getInventory().addItem(lightArmor);

        System.out.println("\nEquipping one-handed sword:");
        hero.getInventory().equipWeapon(oneHandSword, hero, 1);

        System.out.println("\nTrying to equip two-handed sword while one-handed is equipped:");
        hero.getInventory().equipWeapon(twoHandSword, hero, 0);

        System.out.println("\nEquipping two-handed sword:");
        hero.getInventory().equipWeapon(twoHandSword, hero, 0);

        System.out.println("\nEquipping armor:");
        hero.getInventory().equipArmor(lightArmor, hero);

        System.out.println("\nHero stats after equipment:");
        printHeroStats(hero);
    }

    private static void testWASDMovement() {
        System.out.println("\n===== TESTING WASD MOVEMENT =====");

        Grid grid = new Grid();
        Player player = new Player("MovementTester", 0, 0);

        System.out.println("Initial grid:");
        grid.displayGrid(player);

        System.out.println("\nTesting WASD movement controls:");
        System.out.println("Initial position: (" + player.getCurrentRow() + ", " + player.getCurrentCol() + ")");

        // Test D key (right)
        System.out.println("\nMoving right with 'd' key...");
        player.move("d", grid);
        System.out.println("New position: (" + player.getCurrentRow() + ", " + player.getCurrentCol() + ")");

        // Test S key (down)
        System.out.println("\nMoving down with 's' key...");
        player.move("s", grid);
        System.out.println("New position: (" + player.getCurrentRow() + ", " + player.getCurrentCol() + ")");

        // Test A key (left)
        System.out.println("\nMoving left with 'a' key...");
        player.move("a", grid);
        System.out.println("New position: (" + player.getCurrentRow() + ", " + player.getCurrentCol() + ")");

        // Test W key (up)
        System.out.println("\nMoving up with 'w' key...");
        player.move("w", grid);
        System.out.println("New position: (" + player.getCurrentRow() + ", " + player.getCurrentCol() + ")");

        // Test invalid movement
        System.out.println("\nTesting invalid movement (if at boundary)...");
        for (int i = 0; i < 10; i++) {
            player.move("a", grid); // Try to move left until hitting boundary
        }

        System.out.println("\nFinal grid state:");
        grid.displayGrid(player);
    }

    private static void testCombatSimulation() {
        System.out.println("\n===== TESTING COMBAT SIMULATION =====");

        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Warrior("WarriorHero"));
        heroes.add(new Paladin("PaladinHero"));

        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster("TestMonster1"));
        monsters.add(new Monster("TestMonster2"));

        System.out.println("Heroes:");
        for (Hero hero : heroes) {
            printHeroStats(hero);
        }

        System.out.println("\nMonsters:");
        for (Monster monster : monsters) {
            System.out.println("Name: " + monster.getLivingName());
            System.out.println("Level: " + monster.getLivingLevel());
            System.out.println("Health: " + monster.getHealthPower());
        }

        System.out.println("\nSimulating a few combat rounds...");
        // Simulate hero attacking monster
        System.out.println("\nHero attacks monster:");
        double damage = heroes.get(0).getStrength() * 0.5;
        System.out.println(heroes.get(0).getLivingName() + " deals " + damage + " damage to " + monsters.get(0).getLivingName());
        monsters.get(0).setHealthPower(monsters.get(0).getHealthPower() - damage);

        // Simulate monster attacking hero
        System.out.println("\nMonster attacks hero:");
        damage = monsters.get(0).getLivingLevel() * 1.5;
        System.out.println(monsters.get(0).getLivingName() + " deals " + damage + " damage to " + heroes.get(0).getLivingName());
        heroes.get(0).setHealthPower(heroes.get(0).getHealthPower() - damage);

        System.out.println("\nAfter combat round:");
        System.out.println("Hero health: " + heroes.get(0).getHealthPower());
        System.out.println("Monster health: " + monsters.get(0).getHealthPower());

        // Test regeneration
        System.out.println("\nTesting health regeneration:");
        double regenAmount = 0.5 + (heroes.get(0).getLivingLevel() * 0.1);
        regenAmount = Math.round(regenAmount * 100.0) / 100.0;
        heroes.get(0).setHealthPower(heroes.get(0).getHealthPower() + regenAmount);
        System.out.println(heroes.get(0).getLivingName() + " regenerated " + regenAmount + " HP.");
        System.out.println("New hero health: " + heroes.get(0).getHealthPower());
    }

    private static void testHeroRegenerationAfterBattle() {
        System.out.println("\n===== TESTING HERO REGENERATION AFTER BATTLE =====");

        Hero hero = new Warrior("RegenerationTester");
        System.out.println("Initial health: " + hero.getHealthPower());

        // Simulate hero being knocked out
        System.out.println("\nSimulating hero knocked out (setting health to 0)...");
        hero.setHealthPower(0);
        System.out.println("Health after knockout: " + hero.getHealthPower());

        // Test regeneration after battle
        System.out.println("\nTesting regeneration after battle...");
        if (hero.getHealthPower() <= 0) {
            hero.setHealthPower(5);
            hero.setPassedOut(false);
            System.out.println(hero.getLivingName() + " has regenerated to 5 health points after battle.");
        }

        System.out.println("Final health after regeneration: " + hero.getHealthPower());
        System.out.println("Passed out status: " + hero.isPassedOut());
    }

    private static void testMarketSystem() {
        System.out.println("\n===== TESTING MARKET SYSTEM =====");

        Hero hero = new Warrior("MarketTester");
        hero.setMoney(500);

        System.out.println("Hero initial money: " + hero.getMoney());

        // Simulate market items
        Weapon weapon = new Weapon("Market Sword", 50.0, 1, 8.0, 1);
        Armor armor = new Armor("Market Armor", 100.0, 2, 5.0);
        Potion potion = new Potion("Market Potion", 20.0, 1, 3.0);

        System.out.println("\nSimulating market purchase:");

        // Buy item
        System.out.println("Buying " + weapon.getItemName() + " for " + weapon.getItemMarketCost() + " gold.");
        if (hero.getMoney() >= weapon.getItemMarketCost()) {
            hero.setMoney(hero.getMoney() - weapon.getItemMarketCost());
            hero.getInventory().addItem(weapon);
            System.out.println("Purchase successful!");
        } else {
            System.out.println("Not enough money!");
        }

        System.out.println("Hero money after purchase: " + hero.getMoney());
        System.out.println("\nInventory after purchase:");
        hero.getInventory().checkInventory();
    }

    private static void testLevelUpMechanic() {
        System.out.println("\n===== TESTING LEVEL UP MECHANIC =====");

        Hero hero = new Warrior("LevelTester");

        System.out.println("Initial stats:");
        printHeroStats(hero);

        // Simulate gaining experience
        int expNeeded = hero.getLivingLevel() * 10;
        System.out.println("\nExperience needed for level up: " + expNeeded);

        System.out.println("Adding experience...");
        hero.setExperience(expNeeded);

        System.out.println("\nStats after level up:");
        printHeroStats(hero);
    }
}