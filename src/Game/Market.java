package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import item.Armor;
import item.Item;
import item.Potion;
import item.Weapon;
import living.heroes.Hero;
import spell.FireSpell;
import spell.IceSpell;
import spell.LightningSpell;
import spell.Spell;

public class Market {
    //Variables
    private List<Item> availableItems;
    private List<Spell> availableSpells;

    //Functions
    //Constructor
    public Market() {
        this.availableItems = getStartingItems();
        this.availableSpells = getStartingSpells();
    }

    // Populate with starting items
    private List<Item> getStartingItems() {
        List<Item> items = new ArrayList<>();

        // Potions
        items.add(new Potion("Health Potion", 10.0, 1, 20.0));
        items.add(new Potion("Mana Potion", 12.0, 1, 15.0));
        items.add(new Potion("Strength Potion", 20.0, 2, 5.0));


        // Armors
        items.add(new Armor("Leather Armor", 25.0, 1, 5.0));
        items.add(new Armor("Chainmail Armor", 40.0, 2, 10.0));
        items.add(new Armor("Plate Armor", 60.0, 3, 15.0));

        // Weapons
        items.add(new Weapon("Short Sword", 15.0, 1, 8.0, 1));
        items.add(new Weapon("Long Bow", 30.0, 2, 12.0, 2));
        items.add(new Weapon("Battle Axe", 50.0, 3, 20.0, 2));

        return items;
    }

    // Populate with starting spells
    private List<Spell> getStartingSpells() {
        List<Spell> spells = new ArrayList<>();

        // Ice Spells
        spells.add(new IceSpell("Frost Shard"));
        spells.add(new IceSpell("Blizzard"));
        spells.add(new IceSpell("Glacial Spike"));

        // Fire Spells
        spells.add(new FireSpell("Fireball"));
        spells.add(new FireSpell("Flame Burst"));
        spells.add(new FireSpell("Inferno"));

        // Lightning Spells
        spells.add(new LightningSpell("Spark"));
        spells.add(new LightningSpell("Thunderbolt"));
        spells.add(new LightningSpell("Storm Strike"));

        return spells;
    }

    //Start of the game
    public void openStartingMarket(Player player) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Welcome to the Starting Market ---");

            // List heroes and their gold
            List<Hero> heroes = player.getHeroes();
            System.out.println("Select a hero to shop for (or 0 to exit):");
            for (int i = 0; i < heroes.size(); i++) {
                Hero hero = heroes.get(i);
                System.out.println((i + 1) + ". " + hero.getLivingName() + " (Level: " + hero.getLivingLevel() + ", Gold: " + hero.getMoney() + ")");
            }
            System.out.print("Choice: ");
            int heroChoice;
            try {
                heroChoice = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            if (heroChoice == -1) break;
            if (heroChoice < 0 || heroChoice >= heroes.size()) {
                System.out.println("Invalid hero selection. Try again.");
                continue;
            }
            Hero selectedHero = heroes.get(heroChoice);

            boolean inHeroMarket = true;
            while (inHeroMarket) {
                System.out.println("\nShopping for: " + selectedHero.getLivingName() + " (Level: " + selectedHero.getLivingLevel() + ", Gold: " + selectedHero.getMoney() + ")");
                System.out.println("1. Buy items/spells");
                System.out.println("2. Equip an item or spell");
                System.out.println("3. View inventory");
                System.out.println("4. Back to hero selection");
                System.out.print("Enter choice: ");
                String menuInput = scanner.nextLine();

                switch (menuInput) {
                    case "1":
                        // Show available items and spells
                        System.out.println("Available Items:");
                        for (int i = 0; i < availableItems.size(); i++) {
                            Item item = availableItems.get(i);
                            System.out.println((i + 1) + ". " + item.getItemName() +
                                    " - Cost: " + item.getItemMarketCost() +
                                    " gold, Min Level: " + item.getItemMinLevel());
                        }
                        System.out.println("Available Spells:");
                        for (int i = 0; i < availableSpells.size(); i++) {
                            Spell spell = availableSpells.get(i);
                            System.out.println((i + 1 + availableItems.size()) + ". " + spell.getSpellName() +
                                    " - Cost: " + spell.getSpellCost() +
                                    " gold, Min Level: " + spell.getSpellMinLevel());
                        }
                        System.out.println("0. Back");

                        System.out.print("Enter the number of the item/spell to buy, or 0 to go back: ");
                        int choice;
                        try {
                            choice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Try again.");
                            continue;
                        }
                        if (choice == 0) break;

                        boolean isItem = choice <= availableItems.size();
                        int index = isItem ? choice - 1 : choice - 1 - availableItems.size();

                        // Validate selection
                        if ((isItem && (index < 0 || index >= availableItems.size())) ||
                                (!isItem && (index < 0 || index >= availableSpells.size()))) {
                            System.out.println("Invalid selection. Try again.");
                            continue;
                        }

                        // Attempt purchase for selected hero
                        if (isItem) {
                            Item item = availableItems.get(index);
                            if (selectedHero.getLivingLevel() < item.getItemMinLevel()) {
                                System.out.println("Level too low! Required: " + item.getItemMinLevel());
                            } else if (selectedHero.getMoney() < item.getItemMarketCost()) {
                                System.out.println("Not enough gold!");
                            } else {
                                selectedHero.setMoney(selectedHero.getMoney() - item.getItemMarketCost());
                                selectedHero.getInventory().addItem(item);
                                System.out.println(item.getItemName() + " bought for " + selectedHero.getLivingName() + "!");
                            }
                        } else {
                            Spell spell = availableSpells.get(index);
                            if (selectedHero.getLivingLevel() < spell.getSpellMinLevel()) {
                                System.out.println("Level too low! Required: " + spell.getSpellMinLevel());
                            } else if (selectedHero.getMoney() < spell.getSpellCost()) {
                                System.out.println("Not enough gold!");
                            } else {
                                selectedHero.setMoney(selectedHero.getMoney() - spell.getSpellCost());
                                selectedHero.getInventory().addSpell(spell);
                                System.out.println(spell.getSpellName() + " bought for " + selectedHero.getLivingName() + "!");
                            }
                        }
                        System.out.println(selectedHero.getLivingName() + " gold remaining: " + selectedHero.getMoney());
                        break;

                    // (case 2, 3, 4 as before...)

                    case "2":
                        // Equip logic
                        System.out.println("Do you want to equip an item or a spell?");
                        System.out.println("1. Item");
                        System.out.println("2. Spell");
                        System.out.print("Enter choice (or 0 to cancel): ");
                        String equipTypeInput = scanner.nextLine();

                        if (equipTypeInput.equals("0")) break;

                        if (equipTypeInput.equals("1")) {
                            // Equip Item
                            List<Item> heroItems = selectedHero.getInventory().getItems();
                            if (heroItems.isEmpty()) {
                                System.out.println("No items to equip.");
                                break;
                            }
                            System.out.println("Items:");
                            for (int i = 0; i < heroItems.size(); i++) {
                                System.out.println((i + 1) + ". " + heroItems.get(i).getItemName());
                            }
                            System.out.print("Enter the number of the item to equip (or 0 to cancel): ");
                            int equipChoice;
                            try {
                                equipChoice = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Returning to menu.");
                                break;
                            }
                            if (equipChoice == 0) break;
                            if (equipChoice < 1 || equipChoice > heroItems.size()) {
                                System.out.println("Invalid selection.");
                                break;
                            }
                            Item itemToEquip = heroItems.get(equipChoice - 1);
                            boolean equipped = false;
                            if (itemToEquip instanceof Weapon) {
                                equipped = selectedHero.getInventory().equipWeapon((Weapon) itemToEquip);
                                System.out.println(equipped ? "Equipped: " + itemToEquip.getItemName() + "!" : "Could not equip this weapon.");
                            } else if (itemToEquip instanceof Armor) {
                                equipped = selectedHero.getInventory().equipArmor((Armor) itemToEquip);
                                System.out.println(equipped ? "Equipped: " + itemToEquip.getItemName() + "!" : "Could not equip this armor.");
                            } else {
                                System.out.println("This item cannot be equipped.");
                            }
                        } else if (equipTypeInput.equals("2")) {
                            // Equip Spell
                            List<Spell> heroSpells = selectedHero.getInventory().getSpells();
                            if (heroSpells.isEmpty()) {
                                System.out.println("No spells to equip.");
                                break;
                            }
                            System.out.println("Spells:");
                            for (int i = 0; i < heroSpells.size(); i++) {
                                System.out.println((i + 1) + ". " + heroSpells.get(i).getSpellName());
                            }
                            System.out.print("Enter the number of the spell to equip (or 0 to cancel): ");
                            int equipChoice;
                            try {
                                equipChoice = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Returning to menu.");
                                break;
                            }
                            if (equipChoice == 0) break;
                            if (equipChoice < 1 || equipChoice > heroSpells.size()) {
                                System.out.println("Invalid selection.");
                                break;
                            }
//                            Spell spellToEquip = heroSpells.get(equipChoice - 1);
//                            // If you have an equipSpell method in Inventory, use it; otherwise, print a message
//                            if (selectedHero.getInventory().equipSpell != null) {
//                                boolean equipped = selectedHero.getInventory().equipSpell(spellToEquip);
//                                System.out.println(equipped ? "Equipped spell: " + spellToEquip.getSpellName() + "!" : "Could not equip this spell.");
//                            } else {
//                                System.out.println("Spells cannot be equipped, only used.");
//                            }
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    case "3":
                        selectedHero.getInventory().checkInventory();
                        break;
                    case "4":
                        inHeroMarket = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

//    public void openStartingMarket(Player player) {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("\n--- Welcome to the Starting Market ---");
//
//            // List heroes and their gold
//            List<Hero> heroes = player.getHeroes();
//            System.out.println("Select a hero to shop for (or 0 to exit):");
//            for (int i = 0; i < heroes.size(); i++) {
//                Hero hero = heroes.get(i);
//                System.out.println((i + 1) + ". " + hero.getLivingName() + " (Gold: " + hero.getMoney() + ")");
//            }
//            System.out.print("Choice: ");
//            int heroChoice;
//            try {
//                heroChoice = Integer.parseInt(scanner.nextLine()) - 1;
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Try again.");
//                continue;
//            }
//            if (heroChoice == -1) break;
//            if (heroChoice < 0 || heroChoice >= heroes.size()) {
//                System.out.println("Invalid hero selection. Try again.");
//                continue;
//            }
//            Hero selectedHero = heroes.get(heroChoice);
//
//            boolean inHeroMarket = true;
//            while (inHeroMarket) {
//                System.out.println("\nShopping for: " + selectedHero.getLivingName() + " (Gold: " + selectedHero.getMoney() + ")");
//                System.out.println("1. Buy items/spells");
//                System.out.println("2. Equip an item or spell");
//                System.out.println("3. View inventory");
//                System.out.println("4. Back to hero selection");
//                System.out.print("Enter choice: ");
//                String menuInput = scanner.nextLine();
//
//                switch (menuInput) {
//                    case "1":
//                        // Show available items and spells
//                        System.out.println("Available Items:");
//                        for (int i = 0; i < availableItems.size(); i++) {
//                            Item item = availableItems.get(i);
//                            System.out.println((i + 1) + ". " + item.getItemName() + " - " + item.getItemMarketCost() + " gold");
//                        }
//                        System.out.println("Available Spells:");
//                        for (int i = 0; i < availableSpells.size(); i++) {
//                            Spell spell = availableSpells.get(i);
//                            System.out.println((i + 1 + availableItems.size()) + ". " + spell.getSpellName() + " - " + spell.getSpellCost() + " gold");
//                        }
//                        System.out.println("0. Back");
//
//                        System.out.print("Enter the number of the item/spell to buy, or 0 to go back: ");
//                        int choice;
//                        try {
//                            choice = Integer.parseInt(scanner.nextLine());
//                        } catch (NumberFormatException e) {
//                            System.out.println("Invalid input. Try again.");
//                            continue;
//                        }
//                        if (choice == 0) break;
//
//                        boolean isItem = choice <= availableItems.size();
//                        int index = isItem ? choice - 1 : choice - 1 - availableItems.size();
//
//                        // Validate selection
//                        if ((isItem && (index < 0 || index >= availableItems.size())) ||
//                                (!isItem && (index < 0 || index >= availableSpells.size()))) {
//                            System.out.println("Invalid selection. Try again.");
//                            continue;
//                        }
//
//                        // Attempt purchase for selected hero
//                        if (isItem) {
//                            Item item = availableItems.get(index);
//                            if (selectedHero.getMoney() >= item.getItemMarketCost()) {
//                                selectedHero.setMoney(selectedHero.getMoney() - item.getItemMarketCost());
//                                selectedHero.getInventory().addItem(item);
//                                System.out.println(item.getItemName() + " bought for " + selectedHero.getLivingName() + "!");
//                            } else {
//                                System.out.println("Not enough gold!");
//                            }
//                        } else {
//                            Spell spell = availableSpells.get(index);
//                            if (selectedHero.getMoney() >= spell.getSpellCost()) {
//                                selectedHero.setMoney(selectedHero.getMoney() - spell.getSpellCost());
//                                selectedHero.getInventory().addSpell(spell);
//                                System.out.println(spell.getSpellName() + " bought for " + selectedHero.getLivingName() + "!");
//                            } else {
//                                System.out.println("Not enough gold!");
//                            }
//                        }
//                        System.out.println(selectedHero.getLivingName() + " gold remaining: " + selectedHero.getMoney());
//                        break;
//
////                    case "2":
////                        // Equip logic
////                        System.out.println("Inventory:");
////                        selectedHero.getInventory().checkInventory();
////                        System.out.print("Enter the number of the item/spell to equip (or 0 to cancel): ");
////                        int equipChoice;
////                        try {
////                            equipChoice = Integer.parseInt(scanner.nextLine());
////                        } catch (NumberFormatException e) {
////                            System.out.println("Invalid input. Returning to menu.");
////                            break;
////                        }
////                        if (equipChoice == 0) break;
////
////                        List<Item> heroItems = selectedHero.getInventory().getItems();
////                        List<Spell> heroSpells = selectedHero.getInventory().getSpells();
////
////                        if (equipChoice <= heroItems.size() && equipChoice > 0) {
////                            Item itemToEquip = heroItems.get(equipChoice - 1);
////                            boolean equipped = selectedHero.equip(itemToEquip);
////                            if (equipped) {
////                                System.out.println("Equipped " + itemToEquip.getItemName() + "!");
////                            } else {
////                                System.out.println("Could not equip this item.");
////                            }
////                        } else if (equipChoice > heroItems.size() && equipChoice <= heroItems.size() + heroSpells.size()) {
////                            Spell spellToEquip = heroSpells.get(equipChoice - 1 - heroItems.size());
////                            boolean equipped = selectedHero.equip(spellToEquip);
////                            if (equipped) {
////                                System.out.println("Equipped " + spellToEquip.getSpellName() + "!");
////                            } else {
////                                System.out.println("Could not equip this spell.");
////                            }
////                        } else {
////                            System.out.println("Invalid selection.");
////                        }
////                        break;
//
//                    case "3":
//                        selectedHero.getInventory().checkInventory();
//                        break;
//
//                    case "4":
//                        inHeroMarket = false;
//                        break;
//
//                    default:
//                        System.out.println("Invalid choice. Try again.");
//                }
//            }
//        }
//    }

//    public void openStartingMarket(Player player) {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("\n--- Welcome to the Starting Market ---");
//
//            // List heroes and their gold
//            List<Hero> heroes = player.getHeroes();
//            System.out.println("Select a hero to shop for (or 0 to exit):");
//            for (int i = 0; i < heroes.size(); i++) {
//                Hero hero = heroes.get(i);
//                System.out.println((i + 1) + ". " + hero.getLivingName() + " (Gold: " + hero.getMoney() + ")");
//            }
//            System.out.print("Choice: ");
//            int heroChoice;
//            try {
//                heroChoice = Integer.parseInt(scanner.nextLine()) - 1;
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Try again.");
//                continue;
//            }
//            if (heroChoice == -1) break;
//            if (heroChoice < 0 || heroChoice >= heroes.size()) {
//                System.out.println("Invalid hero selection. Try again.");
//                continue;
//            }
//            Hero selectedHero = heroes.get(heroChoice);
//
//            // Show available items and spells
//            System.out.println("Available Items:");
//            for (int i = 0; i < availableItems.size(); i++) {
//                Item item = availableItems.get(i);
//                System.out.println((i + 1) + ". " + item.getItemName() + " - " + item.getItemMarketCost() + " gold");
//            }
//            System.out.println("Available Spells:");
//            for (int i = 0; i < availableSpells.size(); i++) {
//                Spell spell = availableSpells.get(i);
//                System.out.println((i + 1 + availableItems.size()) + ". " + spell.getSpellName() + " - " + spell.getSpellCost() + " gold");
//            }
//            System.out.println("0. Back to hero selection");
//
//            System.out.print("Enter the number of the item/spell to buy, or 0 to go back: ");
//            int choice;
//            try {
//                choice = Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Try again.");
//                continue;
//            }
//            if (choice == 0) continue;
//
//            boolean isItem = choice <= availableItems.size();
//            int index = isItem ? choice - 1 : choice - 1 - availableItems.size();
//
//            // Validate selection
//            if ((isItem && (index < 0 || index >= availableItems.size())) ||
//                    (!isItem && (index < 0 || index >= availableSpells.size()))) {
//                System.out.println("Invalid selection. Try again.");
//                continue;
//            }
//
//            // Attempt purchase for selected hero
//            if (isItem) {
//                Item item = availableItems.get(index);
//                if (selectedHero.getMoney() >= item.getItemMarketCost()) {
//                    selectedHero.setMoney(selectedHero.getMoney() - item.getItemMarketCost());
//                    selectedHero.getInventory().addItem(item);
//                    System.out.println(item.getItemName() + " bought for " + selectedHero.getLivingName() + "!");
//                } else {
//                    System.out.println("Not enough gold!");
//                }
//            } else {
//                Spell spell = availableSpells.get(index);
//                if (selectedHero.getMoney() >= spell.getSpellCost()) {
//                    selectedHero.setMoney(selectedHero.getMoney() - spell.getSpellCost());
//                    selectedHero.getInventory().addSpell(spell);
//                    System.out.println(spell.getSpellName() + " bought for " + selectedHero.getLivingName() + "!");
//                } else {
//                    System.out.println("Not enough gold!");
//                }
//            }
//            System.out.println(selectedHero.getLivingName() + " gold remaining: " + selectedHero.getMoney());
//        }
//
//    }

    //Display Stats
    public void displayAvailableItemsAndSpells() {
        int count = 1;
        System.out.println("---- Available Items ----");
        for (Item item : availableItems) {
            String output = count + ". Name: " + item.getItemName()
                    + ", Market Cost: " + item.getItemMarketCost()
                    + ", Minimum Level: " + item.getItemMinLevel()
                    + ", Type: " + item.getClass().getSimpleName();
            // Add extra stats if needed...
            System.out.println(output);
            count++;
        }
        System.out.println("\n---- Available Spells ----");
        for (Spell spell : availableSpells) {
            String output = count + ". Name: " + spell.getSpellName()
                    + ", Market Cost: " + spell.getSpellCost()
                    + ", Minimum Level: " + spell.getSpellMinLevel()
                    + ", Damage Range: [" + spell.getMinDamage() + ", " + spell.getMaxDamage() + "]"
                    + ", Magic Power: " + spell.getMagicPowerRequired()
                    + ", Type: " + spell.getClass().getSimpleName();
            System.out.println(output);
            count++;
        }
    }

//Old display method
//    public void displayAvailableItemsAndSpells() {
//        System.out.println("---- Available Items ----");
//        for (Item item : availableItems) {
//            System.out.println("Name: " + item.getItemName() +
//                    ", Market Cost: " + item.getItemMarketCost() +
//                    ", Minimum Level: " + item.getItemMinLevel() +
//                    ", Type: " + item.getClass().getSimpleName());
//            // Ειδικά χαρακτηριστικά ανά τύπο
//            if (item instanceof Weapon) {
//                Weapon w = (Weapon) item;
//                System.out.println("   Damage: " + w.getWeaponDamage() + ", Hands: " + w.handsNeeded());
//            }
//            else if (item instanceof Armor) {
//                Armor a = (Armor) item;
//                System.out.println("   Damage Reduction: " + a.getReducedDamage());
//            }
//            else if (item instanceof Potion) {
//                Potion p = (Potion) item;
//                System.out.println("   Increase random statistic by : " + p.getIncreasedValue());
//            }
//        }
//        System.out.println("\n---- Available Spells ----");
//        for (Spell spell : availableSpells) {
//            System.out.println("Name: " + spell.getSpellName() +
//                    ", Market Cost: " + spell.getSpellCost() +
//                    ", Minimum Level: " + spell.getSpellMinLevel() +
//                    ", Damage Range: [" + spell.getMinDamage() + ", " + spell.getMaxDamage() + "]" +
//                    ", Magic Power: " + spell.getMagicPowerRequired() +
//                    ", Type: " + spell.getClass().getSimpleName());
//        }
//    }

    //Buy methods for item and spell
    public boolean buyItem(Hero hero, Item item) {
        if (hero.getLivingLevel() < item.getItemMinLevel()) {
            System.out.println("Failed buying attempt: level too low for this item.");
            return false;
        }
        if (hero.getMoney() < item.getItemMarketCost()) {
            System.out.println("Failed buying attempt: not enough gold for this item.");
            return false;
        }
        hero.setMoney(hero.getMoney() - item.getItemMarketCost());
        hero.getInventory().addItem(item);
        System.out.println("Bought the item: " + item.getItemName());
        return true;
    }

    public boolean buySpell(Hero hero, Spell spell) {
        if (hero.getLivingLevel() < spell.getSpellMinLevel()) {
            System.out.println("Failed buying attempt: level too low for this spell.");
            return false;
        }
        if (hero.getMoney() < spell.getSpellCost()) {
            System.out.println("Failed buying attempt: not enough gold for this spell.");
            return false;
        }
        hero.setMoney(hero.getMoney() - spell.getSpellCost());
        hero.getInventory().addSpell(spell);
        System.out.println("Bought the spell: " + spell.getSpellName());
        return true;
    }

    //Sell methods for item and spell
    public boolean sellItem(Hero hero, Item item) {
        double sellPrice = item.getItemMarketCost() * 0.5;
        boolean removed = hero.getInventory().removeItem(item);
        if (removed) {
            hero.setMoney(hero.getMoney() + sellPrice);
            System.out.println("Sold the item: " + item.getItemName() + " for " + sellPrice + " gold");
        } else {
            System.out.println("Item not found in inventory.");
        }
        return removed;
    }

    public boolean sellSpell(Hero hero, Spell spell) {
        double sellPrice = spell.getSpellCost() * 0.5;
        boolean removed = hero.getInventory().removeSpell(spell);
        if (removed) {
            hero.setMoney(hero.getMoney() + sellPrice);
            System.out.println("Sold the spell: " + spell.getSpellName() + " for " + sellPrice + " gold");
        } else {
            System.out.println("Spell not found in inventory.");
        }
        return removed;
    }

    //Getters & Setters
    public List<Item> getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(List<Item> availableItems) {
        this.availableItems = availableItems;
    }

    public List<Spell> getAvailableSpells() {
        return availableSpells;
    }

    public void setAvailableSpells(List<Spell> availableSpells) {
        this.availableSpells = availableSpells;
    }
}