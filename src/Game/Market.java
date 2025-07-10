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
        items.add(new Potion("Health Potion"));
        items.add(new Potion("Mana Potion"));
        items.add(new Potion("Strength Potion"));

        // Armors
        items.add(new Armor("Leather Armor"));
        items.add(new Armor("Chainmail Armor"));
        items.add(new Armor("Plate Armor"));

        // Weapons
        items.add(new Weapon("Short Sword", 1));
        items.add(new Weapon("Long Bow", 2));
        items.add(new Weapon("Battle Axe", 2));

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
                System.out.println((i + 1) + ". " + hero.getLivingName() + " (Gold: " + hero.getMoney() + ")");
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

            // Show available items and spells
            System.out.println("Available Items:");
            for (int i = 0; i < availableItems.size(); i++) {
                Item item = availableItems.get(i);
                System.out.println((i + 1) + ". " + item.getItemName() + " - " + item.getItemMarketCost() + " gold");
            }
            System.out.println("Available Spells:");
            for (int i = 0; i < availableSpells.size(); i++) {
                Spell spell = availableSpells.get(i);
                System.out.println((i + 1 + availableItems.size()) + ". " + spell.getSpellName() + " - " + spell.getSpellCost() + " gold");
            }
            System.out.println("0. Back to hero selection");

            System.out.print("Enter the number of the item/spell to buy, or 0 to go back: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }
            if (choice == 0) continue;

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
                if (selectedHero.getMoney() >= item.getItemMarketCost()) {
                    selectedHero.setMoney(selectedHero.getMoney() - item.getItemMarketCost());
                    selectedHero.getInventory().addItem(item);
                    System.out.println(item.getItemName() + " bought for " + selectedHero.getLivingName() + "!");
                } else {
                    System.out.println("Not enough gold!");
                }
            } else {
                Spell spell = availableSpells.get(index);
                if (selectedHero.getMoney() >= spell.getSpellCost()) {
                    selectedHero.setMoney(selectedHero.getMoney() - spell.getSpellCost());
                    selectedHero.getInventory().addSpell(spell);
                    System.out.println(spell.getSpellName() + " bought for " + selectedHero.getLivingName() + "!");
                } else {
                    System.out.println("Not enough gold!");
                }
            }
            System.out.println(selectedHero.getLivingName() + " gold remaining: " + selectedHero.getMoney());
        }

    }

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

    //Buy method
    public boolean buy(Hero hero, Object product) {
        if (product instanceof Item) {
            Item item = (Item) product;
            if (hero.getLivingLevel() >= item.getItemMinLevel() && hero.getMoney() >= item.getItemMarketCost()) {
                hero.setMoney(hero.getMoney() - item.getItemMarketCost());
                hero.getInventory().addItem(item);
                System.out.println("Buy the item: " + item.getItemName());
                return true;
            } else {
                System.out.println("Failed buying attempt: insufficient level or money.");
                return false;
            }
        } else if (product instanceof Spell) {
            Spell spell = (Spell) product;
            if (hero.getLivingLevel() >= spell.getSpellMinLevel() && hero.getMoney() >= spell.getSpellCost()) {
                hero.setMoney(hero.getMoney() - spell.getSpellCost());
                hero.getInventory().addSpell(spell);
                System.out.println("Bought the spell: " + spell.getSpellName());
                return true;
            } else {
                System.out.println("Failed buying attempt: insufficient level or money.");
                return false;
            }
        }
        return false;
    }

    //Sell method
    public boolean sell(Hero hero, Object product) {
        double sellPrice = 0;
        boolean removed = false;
        if (product instanceof Item) {
            Item item = (Item) product;
            sellPrice = item.getItemMarketCost() * 0.5; // π.χ. πουλάς στο 50% της τιμής αγοράς
            removed = hero.getInventory().removeItem(item);
            if (removed) {
                hero.setMoney(hero.getMoney() + sellPrice);
                System.out.println("Sold the item: " + item.getItemName() + " for " + sellPrice + " gold");
            }
        } else if (product instanceof Spell) {
            Spell spell = (Spell) product;
            sellPrice = spell.getSpellCost() * 0.5;
            removed = hero.getInventory().removeSpell(spell);
            if (removed) {
                hero.setMoney(hero.getMoney() + sellPrice);
                System.out.println("Sold the spell: " + spell.getSpellName() + " for " + sellPrice + " gold");
            }
        }
        if (!removed) {
            System.out.println("Item/Spell not found in inventory.");
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