package game;

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
    private final List<Item> availableItems;
    private final List<Spell> availableSpells;

    //Functions

    //Constructor
    public Market() {
        this.availableItems = getItems();
        this.availableSpells = getSpells();
    }

    // Populate with items
    private List<Item> getItems() {
        List<Item> items = new ArrayList<>();

        // Potions
        items.add(new Potion("Vitality Potion", 10.0, 1, 3.0));
        items.add(new Potion("Arcane Potion", 12.0, 1, 5.0));
        items.add(new Potion("Mystic Potion", 20.0, 2, 4.0));

        // Armors
        items.add(new Armor("Leather Armor", 25.0, 1, 2.0));
        items.add(new Armor("Chainmail Armor", 40.0, 2, 4.0));
        items.add(new Armor("Plate Armor", 60.0, 3, 8.0));

        // Weapons
        items.add(new Weapon("Short Sword", 15.0, 1, 2.0, 1));
        items.add(new Weapon("Long Bow", 30.0, 2, 4.0, 2));
        items.add(new Weapon("Battle Axe", 50.0, 3, 8.0, 2));

        return items;
    }

    // Populate with spells
    private List<Spell> getSpells() {
        List<Spell> spells = new ArrayList<>();

        // Ice Spells
        spells.add(new IceSpell("Frost Shard", 10, 5, 1, 0.2, 4));
        spells.add(new IceSpell("Blizzard", 12, 6, 1, 0.25, 2));
        spells.add(new IceSpell("Glacial Spike", 15, 7, 2, 0.3, 6));

        // Fire Spells
        spells.add(new FireSpell("Fireball", 11, 5, 1, 2, 0.2));
        spells.add(new FireSpell("Flame Burst", 13, 6, 1, 3, 0.25));
        spells.add(new FireSpell("Inferno", 16, 7, 2, 5, 0.3));

        // Lightning Spells
        spells.add(new LightningSpell("Spark", 9, 4, 1, 2.5, 0.15));
        spells.add(new LightningSpell("Thunderbolt", 14, 6, 1, 4, 0.2));
        spells.add(new LightningSpell("Storm Strike", 17, 8, 2, 1.5, 0.25));

        return spells;
    }

    //Start of the game
    public void openMarket(Player player) {
        Scanner scanner = new Scanner(System.in); // Scanner for user input
        while (true) {
            System.out.println("\n--- Welcome to the Market ---");

            List<Hero> heroes = player.getParty(); // Get the list of heroes from the player
            // Select a hero
            System.out.println("Select a hero to shop for (or 0 to exit):");
            // Display heroes with their levels and gold
            for (int i = 0; i < heroes.size(); i++) {
                Hero hero = heroes.get(i);
                System.out.println((i + 1) + ". " + hero.getLivingName() + " (Level: " + hero.getLivingLevel() + ", Gold: " + hero.getMoney() + ")");
            }

            System.out.print("Choice: ");
            int heroChoice;

            // Check if input is an integer
            if (scanner.hasNextInt()) {
                heroChoice = scanner.nextInt() - 1; // Convert input to index 0-based (in order to get the first hero as 1 from the array list)
                scanner.nextLine(); // consume the newline
            }
            else {
                System.out.println("Invalid input. Try again.");
                scanner.nextLine(); // consume the invalid input
                continue;
            }
            // Check if user wants to exit
            if (heroChoice == -1) {
                break;
            }
            // Check if heroChoice is valid
            if (heroChoice < 0 || heroChoice >= heroes.size()) {
                System.out.println("Invalid hero selection. Try again.");
                continue;
            }
            // Get the selected hero
            Hero selectedHero = heroes.get(heroChoice);
            // In hero market loop
            boolean inHeroMarket = true;
            while (inHeroMarket) {
                System.out.println("\nShopping for: " + selectedHero.getLivingName() + " (Level: " + selectedHero.getLivingLevel() + ", Gold: " + selectedHero.getMoney() + ")");
                System.out.println("1. Buy items or spells");
                System.out.println("2. Sell items or spells");
                System.out.println("3. Back to hero selection");
                System.out.print("Enter choice: ");
                String menuInput = scanner.nextLine();

                switch (menuInput) {
                    case "1":
                        // Ask if the user wants to buy an item or a spell
                        System.out.println("What would you like to buy?");
                        System.out.println("1. Item");
                        System.out.println("2. Spell");
                        System.out.println("0. Back");
                        System.out.print("Enter your choice: ");
                        int buyChoice;
                        if (scanner.hasNextInt()) {
                            buyChoice = scanner.nextInt();
                            scanner.nextLine(); // consume newline
                        } else {
                            System.out.println("Invalid input. Try again.");
                            scanner.nextLine();
                            continue;
                        }
                        // Go back if chosen
                        if (buyChoice == 0) {
                            break;
                        }

                        if (buyChoice == 1) { // Buy an item
                            System.out.println("Which type of item would you like to buy?");
                            System.out.println("1. Weapon");
                            System.out.println("2. Potion");
                            System.out.println("3. Armor");
                            System.out.println("0. Back");

                            System.out.print("Enter your choice: ");
                            int itemType;
                            // Check if input is an integer
                            if (scanner.hasNextInt()) {
                                itemType = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                            } else {
                                System.out.println("Invalid input. Try again.");
                                scanner.nextLine();
                                continue;
                            }
                            // Check if user wants to go back
                            if (itemType == 0) {
                                break;
                            }
                            // Check for weapon buying
                            if (itemType == 1) {
                                List<Item> items = getAvailableItems(); // Get all available items
                                List<Weapon> availableWeapons = new ArrayList<>(); // List to hold available weapons
                                // Filter items to find available weapons
                                for (Item item : items) {
                                    if (item.getClass().equals(Weapon.class)) {
                                        availableWeapons.add((Weapon) item);
                                    }
                                }
                                // Check if there are any available weapons
                                if (availableWeapons.isEmpty()) {
                                    System.out.println("No weapons available.");
                                } else {
                                    System.out.println("Available Weapons:");
                                    // Display available weapons
                                    for (int i = 0; i < availableWeapons.size(); i++) {
                                        Weapon weapon = availableWeapons.get(i);
                                        System.out.println((i + 1) + ". " + weapon.getItemName() + " - Cost: " + weapon.getItemMarketCost());
                                    }

                                    System.out.print("Enter the number of the weapon you want to buy: ");
                                    int weaponChoice;
                                    // Check if input is an integer
                                    if (scanner.hasNextInt()) {
                                        weaponChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    // Check if the weapon choice is valid
                                    if (weaponChoice < 1 || weaponChoice > availableWeapons.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        Weapon selectedWeapon = availableWeapons.get(weaponChoice - 1); // Get the selected weapon
                                        buyWeapon(selectedHero, selectedWeapon); // Call the buyWeapon method to attempt purchase
                                    }
                                }
                            }// End of Weapon buying
                            // Check for potion buying
                            if (itemType == 2) {
                                List<Item> items = getAvailableItems(); // Get all available items
                                List<Potion> availablePotions = new ArrayList<>(); // List to hold available potions
                                // Filter items to find available potions
                                for (Item item : items) {
                                    if (item.getClass().equals(Potion.class)) {
                                        availablePotions.add((Potion) item);
                                    }
                                }
                                // Check if there are any available potions
                                if (availablePotions.isEmpty()) {
                                    System.out.println("No potions available.");
                                } else {
                                    System.out.println("Available Potions:");
                                    // Display available potions
                                    for (int i = 0; i < availablePotions.size(); i++) {
                                        Potion potion = availablePotions.get(i);
                                        System.out.println((i + 1) + ". " + potion.getItemName() + " - Cost: " + potion.getItemMarketCost());
                                    }

                                    System.out.print("Enter the number of the potion you want to buy: ");
                                    int potionChoice;
                                    // Check if input is an integer
                                    if (scanner.hasNextInt()) {
                                        potionChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    // Check if the potion choice is valid
                                    if (potionChoice < 1 || potionChoice > availablePotions.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        Potion selectedPotion = availablePotions.get(potionChoice - 1); // Get the selected potion
                                        buyPotion(selectedHero, selectedPotion); // Call the buyPotion method to attempt purchase
                                    }
                                }
                            } // End of Potion buying
                            // Check for armor buying
                            if (itemType == 3) {
                                List<Item> items = getAvailableItems(); // Get all available items
                                List<Armor> availableArmors = new ArrayList<>(); // List to hold available armors
                                // Filter items to find available armors
                                for (Item item : items) {
                                    if (item.getClass().equals(Armor.class)) {
                                        availableArmors.add((Armor) item);
                                    }
                                }
                                // Check if there are any available armors
                                if (availableArmors.isEmpty()) {
                                    System.out.println("No armors available.");
                                } else {
                                    System.out.println("Available Armors:");
                                    // Display available armors
                                    for (int i = 0; i < availableArmors.size(); i++) {
                                        Armor armor = availableArmors.get(i);
                                        System.out.println((i + 1) + ". " + armor.getItemName() + " - Cost: " + armor.getItemMarketCost());
                                    }

                                    System.out.print("Enter the number of the armor you want to buy: ");
                                    int armorChoice;
                                    // Check if input is an integer
                                    if (scanner.hasNextInt()) {
                                        armorChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    // Check if the armor choice is valid
                                    if (armorChoice < 1 || armorChoice > availableArmors.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        Armor selectedArmor = availableArmors.get(armorChoice - 1); // Get the selected armor
                                        buyArmor(selectedHero, selectedArmor); // Call the buyArmor method to attempt purchase
                                    }
                                }
                            }// End of Armor buying
                            break;
                        } // End of Item buying
                        if (buyChoice == 2) {
                            System.out.println("Which type of spell would you like to buy?");
                            System.out.println("1. Ice spell");
                            System.out.println("2. Fire spell");
                            System.out.println("3. Lightning spell");
                            System.out.println("0. Back");

                            System.out.print("Enter your choice: ");
                            int itemType;
                            // Check if input is an integer
                            if (scanner.hasNextInt()) {
                                itemType = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                            } else {
                                System.out.println("Invalid input. Try again.");
                                scanner.nextLine();
                                continue;
                            }
                            // Check if user wants to go back
                            if (itemType == 0) {
                                break;
                            }
                            // Check for ice spell buying
                            if (itemType == 1) {
                                List<Spell> spells = getAvailableSpells(); // Get all available spells
                                List<IceSpell> availableIceSpells = new ArrayList<>(); // List to hold available ice spells
                                // Filter spells to find available ice spells
                                for (Spell spell : spells) {
                                    if (spell.getClass().equals(IceSpell.class)) {
                                        availableIceSpells.add((IceSpell) spell);
                                    }
                                }
                                // Check if there are any available ice spells
                                if (availableIceSpells.isEmpty()) {
                                    System.out.println("No ice spells available.");
                                } else {
                                    System.out.println("Available Ice Spells:");
                                    // Display available ice spells
                                    for (int i = 0; i < availableIceSpells.size(); i++) {
                                        IceSpell iceSpell = availableIceSpells.get(i);
                                        System.out.println((i + 1) + ". " + iceSpell.getSpellName() + " - Cost: " + iceSpell.getSpellCost());
                                    }

                                    System.out.print("Enter the number of the ice spell you want to buy: ");
                                    int iceSpellChoice;
                                    // Check if input is an integer
                                    if (scanner.hasNextInt()) {
                                        iceSpellChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    // Check if the ice spell choice is valid
                                    if (iceSpellChoice < 1 || iceSpellChoice > availableIceSpells.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        IceSpell selectedIceSpell = availableIceSpells.get(iceSpellChoice - 1); // Get the selected ice spell
                                        buyIceSpell(selectedHero, selectedIceSpell); // Call the buyIceSpell method to attempt purchase
                                    }
                                }
                            }// End of Ice Spell buying
                            // Check for fire spell buying
                            if (itemType == 2) {
                                List<Spell> spells = getAvailableSpells(); // Get all available spells
                                List<FireSpell> availableFireSpells = new ArrayList<>(); // List to hold available fire spells
                                // Filter spells to find available fire spells
                                for (Spell spell : spells) {
                                    if (spell.getClass().equals(FireSpell.class)) {
                                        availableFireSpells.add((FireSpell) spell);
                                    }
                                }
                                // Check if there are any available fire spells
                                if (availableFireSpells.isEmpty()) {
                                    System.out.println("No fire spells available.");
                                } else {
                                    System.out.println("Available Fire Spells:");
                                    // Display available fire spells
                                    for (int i = 0; i < availableFireSpells.size(); i++) {
                                        FireSpell fireSpell = availableFireSpells.get(i);
                                        System.out.println((i + 1) + ". " + fireSpell.getSpellName() + " - Cost: " + fireSpell.getSpellCost());
                                    }

                                    System.out.print("Enter the number of the fire spell you want to buy: ");
                                    int fireSpellChoice;
                                    // Check if input is an integer
                                    if (scanner.hasNextInt()) {
                                        fireSpellChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    // Check if the fire spell choice is valid
                                    if (fireSpellChoice < 1 || fireSpellChoice > availableFireSpells.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        FireSpell selectedFireSpell = availableFireSpells.get(fireSpellChoice - 1); // Get the selected fire spell
                                        buyFireSpell(selectedHero, selectedFireSpell); // Call the buyFireSpell method to attempt purchase
                                    }
                                }
                            } // End of Fire Spell buying
                            // Check for lightning spell buying
                            if (itemType == 3) {
                                List<Spell> spells = getAvailableSpells(); // Get all available spells
                                List<LightningSpell> availableLightningSpells = new ArrayList<>(); // List to hold available lightning spells
                                // Filter spells to find available lightning spells
                                for (Spell spell : spells) {
                                    if (spell.getClass().equals(LightningSpell.class)) {
                                        availableLightningSpells.add((LightningSpell) spell);
                                    }
                                }
                                // Check if there are any available lightning spells
                                if (availableLightningSpells.isEmpty()) {
                                    System.out.println("No lightning spells available.");
                                } else {
                                    System.out.println("Available Lightning Spells:");
                                    // Display available lightning spells
                                    for (int i = 0; i < availableLightningSpells.size(); i++) {
                                        LightningSpell lightningSpell = availableLightningSpells.get(i);
                                        System.out.println((i + 1) + ". " + lightningSpell.getSpellName() + " - Cost: " + lightningSpell.getSpellCost());
                                    }

                                    System.out.print("Enter the number of the lightning spell you want to buy: ");
                                    int lightningSpellChoice;
                                    // Check if input is an integer
                                    if (scanner.hasNextInt()) {
                                        lightningSpellChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    // Check if the lightning spell choice is valid
                                    if (lightningSpellChoice < 1 || lightningSpellChoice > availableLightningSpells.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        LightningSpell selectedLightningSpell = availableLightningSpells.get(lightningSpellChoice - 1); // Get the selected lightning spell
                                        buyLightningSpell(selectedHero, selectedLightningSpell); // Call the buyLightningSpell method to attempt purchase
                                    }
                                }
                            } // End of Lightning Spell buying
                            break;
                        }// End of Spell buying
                        break;
                    case "2":
                        System.out.println("What would you like to sell?");
                        System.out.println("1. Item");
                        System.out.println("2. Spell");
                        System.out.println("0. Back");

                        System.out.print("Enter your choice: ");
                        int sellChoice;
                        // Check if input is an integer
                        if (scanner.hasNextInt()) {
                            sellChoice = scanner.nextInt();
                            scanner.nextLine(); // consume newline
                        }
                        else {
                            System.out.println("Invalid input. Try again.");
                            scanner.nextLine();
                            break;
                        }
                        // Go back if chosen
                        if (sellChoice == 0) {
                            break;
                        }
                        // Check if sellChoice is valid
                        if (sellChoice == 1) { // Sell an item
                            List<Item> heroItems = selectedHero.getInventory().getItems(); // Get the hero's items
                            // Check if the hero has any items to sell
                            if (heroItems.isEmpty()) {
                                System.out.println("No items to sell.");
                            }
                            else {
                                System.out.println("Items available for selling:");
                                // Display the items with their sell prices
                                for (int i = 0; i < heroItems.size(); i++) {
                                    Item item = heroItems.get(i); // Get the item from the hero's inventory
                                    double sellPrice = item.getItemMarketCost() * 0.5; // Calculate the sell price (50% of market cost)
                                    System.out.println((i + 1) + ". " + item.getItemName() + " - Sell Price: " + sellPrice); // Display the item name and sell price
                                }

                                System.out.print("Enter the number of the item you want to sell: ");
                                int sellItemChoice;
                                // Check if input is an integer
                                if (scanner.hasNextInt()) {
                                    sellItemChoice = scanner.nextInt();
                                    scanner.nextLine(); // consume newline
                                }
                                else {
                                    System.out.println("Invalid input, returning to menu.");
                                    scanner.nextLine();
                                    break;
                                }
                                // Check if the sellItemChoice is valid
                                if (sellItemChoice < 1 || sellItemChoice > heroItems.size()) {
                                    System.out.println("Invalid selection, returning to menu.");
                                }
                                else {
                                    Item itemToSell = heroItems.get(sellItemChoice - 1); // Get the selected item from the hero's inventory
                                    // Call the sellItem method to process the sale
                                    sellItem(selectedHero, itemToSell);
                                }
                            }
                        } // End of Item selling
                        else if (sellChoice == 2) { // Sell a spell
                            List<Spell> heroSpells = selectedHero.getInventory().getSpells(); // Get the hero's spells
                            // Check if the hero has any spells to sell
                            if (heroSpells.isEmpty()) {
                                System.out.println("No spells to sell.");
                            }
                            else {
                                System.out.println("Spells available for selling:");
                                // Display the spells with their sell prices
                                for (int i = 0; i < heroSpells.size(); i++) {
                                    Spell spell = heroSpells.get(i);
                                    double sellPrice = spell.getSpellCost() * 0.5;
                                    System.out.println((i + 1) + ". " + spell.getSpellName() + " - Sell Price: " + sellPrice);
                                }

                                System.out.print("Enter the number of the spell you want to sell: ");
                                int sellSpellChoice;
                                // Check if input is an integer
                                if (scanner.hasNextInt()) {
                                    sellSpellChoice = scanner.nextInt();
                                    scanner.nextLine(); // consume newline
                                }
                                else {
                                    System.out.println("Invalid input, returning to menu.");
                                    scanner.nextLine();
                                    break;
                                }
                                // Check if the sellSpellChoice is valid
                                if (sellSpellChoice < 1 || sellSpellChoice > heroSpells.size()) {
                                    System.out.println("Invalid selection, returning to menu.");
                                }
                                else {
                                    Spell spellToSell = heroSpells.get(sellSpellChoice - 1); // Get the selected spell from the hero's inventory
                                    // Call the sellSpell method to process the sale
                                    sellSpell(selectedHero, spellToSell);
                                }
                            }
                        } // End of Spell selling
                        else {
                            System.out.println("Invalid sell choice.");
                        }
                        break;
                    case "3":
                        inHeroMarket = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }

    //Buy methods for items (weapons, armors, potions)
    public void buyWeapon(Hero hero, Weapon weapon) {
        // Check hero requirements: level and money
        if (hero.getLivingLevel() < weapon.getItemMinLevel() || hero.getMoney() < weapon.getItemMarketCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < weapon.getItemMinLevel() ? "level too low" : "not enough gold")
                    + " for this weapon.");
            return;
        }
        // Check weapon-specific requirement: available hands
        int availableHands = weapon.getHands();
        if (weapon.getHands() > availableHands) {
            System.out.println("Failed buying attempt: not enough hands for this weapon.");
            return;
        }
        // Complete purchase
        hero.setMoney(hero.getMoney() - weapon.getItemMarketCost());
        hero.getInventory().addItem(weapon);
        System.out.println("Bought the weapon: " + weapon.getItemName());
    }

    public void buyArmor(Hero hero, Armor armor) {
        // Check hero requirements: level and money
        if (hero.getLivingLevel() < armor.getItemMinLevel() || hero.getMoney() < armor.getItemMarketCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < armor.getItemMinLevel() ? "level too low" : "not enough gold")
                    + " for this armor.");
            return;
        }
        // Complete purchase
        hero.setMoney(hero.getMoney() - armor.getItemMarketCost());
        hero.getInventory().addItem(armor);
        System.out.println("Bought the armor: " + armor.getItemName());
    }

    public void buyPotion(Hero hero, Potion potion) {
        // Check hero requirements: level and money
        if (hero.getLivingLevel() < potion.getItemMinLevel() || hero.getMoney() < potion.getItemMarketCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < potion.getItemMinLevel() ? "level too low" : "not enough gold")
                    + " for this potion.");
            return;
        }
        // Complete purchase
        hero.setMoney(hero.getMoney() - potion.getItemMarketCost());
        hero.getInventory().addItem(potion);
        System.out.println("Bought the potion: " + potion.getItemName());
    }
    // Buy methods for spells (ice, fire, lightning)
    public void buyIceSpell(Hero hero, IceSpell spell) {
        if (hero.getLivingLevel() < spell.getSpellMinLevel() || hero.getMoney() < spell.getSpellCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < spell.getSpellMinLevel() ? "level too low for this ice spell." : "not enough gold for this ice spell."));
            return;
        }
        hero.setMoney(hero.getMoney() - spell.getSpellCost());
        hero.getInventory().addSpell(spell);
        System.out.println("Bought the ice spell: " + spell.getSpellName());
    }

    public void buyFireSpell(Hero hero, FireSpell spell) {
        if (hero.getLivingLevel() < spell.getSpellMinLevel() || hero.getMoney() < spell.getSpellCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < spell.getSpellMinLevel() ? "level too low for this fire spell." : "not enough gold for this fire spell."));
            return;
        }
        hero.setMoney(hero.getMoney() - spell.getSpellCost());
        hero.getInventory().addSpell(spell);
        System.out.println("Bought the fire spell: " + spell.getSpellName());
    }

    public void buyLightningSpell(Hero hero, LightningSpell spell) {
        if (hero.getLivingLevel() < spell.getSpellMinLevel() || hero.getMoney() < spell.getSpellCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < spell.getSpellMinLevel() ? "level too low for this lightning spell." : "not enough gold for this lightning spell."));
            return;
        }
        hero.setMoney(hero.getMoney() - spell.getSpellCost());
        hero.getInventory().addSpell(spell);
        System.out.println("Bought the lightning spell: " + spell.getSpellName());
    }

    //Sell methods for item and spell
    public void sellItem(Hero hero, Item item) {
        // Sell price is 50% of the market cost
        double sellPrice = item.getItemMarketCost() * 0.5;
        // Check if the item is in the hero's inventory
        boolean removed = hero.getInventory().removeItem(item);
        if (removed) {
            hero.setMoney(hero.getMoney() + sellPrice);
            System.out.println("Sold the item: " + item.getItemName() + " for " + sellPrice + " gold");
        }
        else {
            System.out.println("Item not found in inventory.");
        }
    }

    public void sellSpell(Hero hero, Spell spell) {
        // Sell price is 50% of the spell cost
        double sellPrice = spell.getSpellCost() * 0.5;
        // Check if the spell is in the hero's inventory
        boolean removed = hero.getInventory().removeSpell(spell);
        if (removed) {
            hero.setMoney(hero.getMoney() + sellPrice);
            System.out.println("Sold the spell: " + spell.getSpellName() + " for " + sellPrice + " gold");
        } else {
            System.out.println("Spell not found in inventory.");
        }
    }

    //Getters & Setters
    public List<Item> getAvailableItems() {
        return availableItems;
    }

    public List<Spell> getAvailableSpells() {
        return availableSpells;
    }
}