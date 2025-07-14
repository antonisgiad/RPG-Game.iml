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
        this.availableItems = getItems();
        this.availableSpells = getSpells();
    }

    // Populate with items
    private List<Item> getItems() {
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

    // Populate with spells
    private List<Spell> getSpells() {
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
    public void openMarket(Player player) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Welcome to the Starting Market ---");

            List<Hero> heroes = player.getParty();
            // Select a hero
            System.out.println("Select a hero to shop for (or 0 to exit):");
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
                System.out.println("1. Buy items/spells");
                System.out.println("2. Equip an item or spell");
                System.out.println("3. View inventory");
                System.out.println("4. Back to hero selection");
                System.out.print("Enter choice: ");
                String menuInput = scanner.nextLine();

                label:
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
                            if (scanner.hasNextInt()) {
                                itemType = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                            } else {
                                System.out.println("Invalid input. Try again.");
                                scanner.nextLine();
                                continue;
                            }
                            if (itemType == 0) {
                                break;
                            }
                            if (itemType == 1) {
                                // Get all available items and filter for weapons
                                List<Item> items = getAvailableItems();
                                List<Weapon> availableWeapons = new ArrayList<>();
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).getClass().equals(Weapon.class)) {
                                        availableWeapons.add((Weapon) items.get(i));
                                    }
                                }
                                if (availableWeapons.isEmpty()) {
                                    System.out.println("No weapons available.");
                                } else {
                                    System.out.println("Available Weapons:");
                                    for (int i = 0; i < availableWeapons.size(); i++) {
                                        Weapon weapon = availableWeapons.get(i);
                                        System.out.println((i + 1) + ". " + weapon.getItemName() + " - Cost: " + weapon.getItemMarketCost());
                                    }
                                    System.out.print("Enter the number of the weapon you want to buy: ");
                                    int weaponChoice;
                                    if (scanner.hasNextInt()) {
                                        weaponChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    if (weaponChoice < 1 || weaponChoice > availableWeapons.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        Weapon selectedWeapon = availableWeapons.get(weaponChoice - 1);
                                        buyWeapon(selectedHero, selectedWeapon);
                                    }
                                }
                            }
                            if (itemType == 2) {
                                // Get all available items and filter for potions
                                List<Item> items = getAvailableItems();
                                List<Potion> availablePotions = new ArrayList<>();
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).getClass().equals(Potion.class)) {
                                        availablePotions.add((Potion) items.get(i));
                                    }
                                }
                                if (availablePotions.isEmpty()) {
                                    System.out.println("No potions available.");
                                } else {
                                    System.out.println("Available Potions:");
                                    for (int i = 0; i < availablePotions.size(); i++) {
                                        Potion potion = availablePotions.get(i);
                                        System.out.println((i + 1) + ". " + potion.getItemName() + " - Cost: " + potion.getItemMarketCost());
                                    }
                                    System.out.print("Enter the number of the potion you want to buy: ");
                                    int potionChoice;
                                    if (scanner.hasNextInt()) {
                                        potionChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    if (potionChoice < 1 || potionChoice > availablePotions.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        Potion selectedPotion = availablePotions.get(potionChoice - 1);
                                        buyPotion(selectedHero, selectedPotion);
                                    }
                                }
                            }
                            if (itemType == 3) {
                                // Get all available items and filter for armors
                                List<Item> items = getAvailableItems();
                                List<Armor> availableArmors = new ArrayList<>();
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).getClass().equals(Armor.class)) {
                                        availableArmors.add((Armor) items.get(i));
                                    }
                                }
                                if (availableArmors.isEmpty()) {
                                    System.out.println("No armors available.");
                                } else {
                                    System.out.println("Available Armors:");
                                    for (int i = 0; i < availableArmors.size(); i++) {
                                        Armor armor = availableArmors.get(i);
                                        System.out.println((i + 1) + ". " + armor.getItemName() + " - Cost: " + armor.getItemMarketCost());
                                    }
                                    System.out.print("Enter the number of the armor you want to buy: ");
                                    int armorChoice;
                                    if (scanner.hasNextInt()) {
                                        armorChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    if (armorChoice < 1 || armorChoice > availableArmors.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        Armor selectedArmor = availableArmors.get(armorChoice - 1);
                                        buyArmor(selectedHero, selectedArmor);
                                    }
                                }
                            }
                            break;
                        }
                        if (buyChoice == 2) {
                            System.out.println("Which type of spell would you like to buy?");
                            System.out.println("1. Ice spell");
                            System.out.println("2. Fire spell");
                            System.out.println("3. Lightning spell");
                            System.out.println("0. Back");
                            System.out.print("Enter your choice: ");
                            int itemType;
                            if (scanner.hasNextInt()) {
                                itemType = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                            } else {
                                System.out.println("Invalid input. Try again.");
                                scanner.nextLine();
                                continue;
                            }
                            if (itemType == 0) {
                                break;
                            }
                            if (itemType == 1) {
                                // Get all available spells and filter for IceSpell
                                List<Spell> spells = getAvailableSpells();
                                List<IceSpell> availableIceSpells = new ArrayList<>();
                                for (int i = 0; i < spells.size(); i++) {
                                    if (spells.get(i).getClass().equals(IceSpell.class)) {
                                        availableIceSpells.add((IceSpell) spells.get(i));
                                    }
                                }
                                if (availableIceSpells.isEmpty()) {
                                    System.out.println("No ice spells available.");
                                } else {
                                    System.out.println("Available Ice Spells:");
                                    for (int i = 0; i < availableIceSpells.size(); i++) {
                                        IceSpell iceSpell = availableIceSpells.get(i);
                                        System.out.println((i + 1) + ". " + iceSpell.getSpellName() + " - Cost: " + iceSpell.getSpellCost());
                                    }
                                    System.out.print("Enter the number of the ice spell you want to buy: ");
                                    int iceSpellChoice;
                                    if (scanner.hasNextInt()) {
                                        iceSpellChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    if (iceSpellChoice < 1 || iceSpellChoice > availableIceSpells.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        IceSpell selectedIceSpell = availableIceSpells.get(iceSpellChoice - 1);
                                        buyIceSpell(selectedHero, selectedIceSpell);
                                    }
                                }
                            }
                            if (itemType == 2) {
                                // Get all available spells and filter for FireSpell
                                List<Spell> spells = getAvailableSpells();
                                List<FireSpell> availableFireSpells = new ArrayList<>();
                                for (int i = 0; i < spells.size(); i++) {
                                    if (spells.get(i).getClass().equals(FireSpell.class)) {
                                        availableFireSpells.add((FireSpell) spells.get(i));
                                    }
                                }
                                if (availableFireSpells.isEmpty()) {
                                    System.out.println("No fire spells available.");
                                } else {
                                    System.out.println("Available Fire Spells:");
                                    for (int i = 0; i < availableFireSpells.size(); i++) {
                                        FireSpell fireSpell = availableFireSpells.get(i);
                                        System.out.println((i + 1) + ". " + fireSpell.getSpellName() + " - Cost: " + fireSpell.getSpellCost());
                                    }
                                    System.out.print("Enter the number of the fire spell you want to buy: ");
                                    int fireSpellChoice;
                                    if (scanner.hasNextInt()) {
                                        fireSpellChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    if (fireSpellChoice < 1 || fireSpellChoice > availableFireSpells.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        FireSpell selectedFireSpell = availableFireSpells.get(fireSpellChoice - 1);
                                        buyFireSpell(selectedHero, selectedFireSpell);
                                    }
                                }
                            }
                            if (itemType == 3) {
                                // Get all available spells and filter for LightningSpell
                                List<Spell> spells = getAvailableSpells();
                                List<LightningSpell> availableLightningSpells = new ArrayList<>();
                                for (int i = 0; i < spells.size(); i++) {
                                    if (spells.get(i).getClass().equals(LightningSpell.class)) {
                                        availableLightningSpells.add((LightningSpell) spells.get(i));
                                    }
                                }
                                if (availableLightningSpells.isEmpty()) {
                                    System.out.println("No lightning spells available.");
                                } else {
                                    System.out.println("Available Lightning Spells:");
                                    for (int i = 0; i < availableLightningSpells.size(); i++) {
                                        LightningSpell lightningSpell = availableLightningSpells.get(i);
                                        System.out.println((i + 1) + ". " + lightningSpell.getSpellName() + " - Cost: " + lightningSpell.getSpellCost());
                                    }
                                    System.out.print("Enter the number of the lightning spell you want to buy: ");
                                    int lightningSpellChoice;
                                    if (scanner.hasNextInt()) {
                                        lightningSpellChoice = scanner.nextInt();
                                        scanner.nextLine(); // consume newline
                                    } else {
                                        System.out.println("Invalid input, returning to menu.");
                                        scanner.nextLine();
                                        continue;
                                    }
                                    if (lightningSpellChoice < 1 || lightningSpellChoice > availableLightningSpells.size()) {
                                        System.out.println("Invalid selection, returning to menu.");
                                    } else {
                                        LightningSpell selectedLightningSpell = availableLightningSpells.get(lightningSpellChoice - 1);
                                        buyLightningSpell(selectedHero, selectedLightningSpell);
                                    }
                                }
                            }
                            break;
                        }
                        break;
                    case "2":
                        // Equip logic
                        // Ask for item type
                        System.out.println("Which type do you want to equip?");
                        System.out.println("1. Weapon");
                        System.out.println("2. Armor");
                        System.out.print("Enter choice (or 0 to cancel): ");

                        int typeChoice = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        // Check if user wants to cancel
                        if (typeChoice == 0) {
                            break;
                        }
                        // Create lists for each item type
                        List<Weapon> weapons = new ArrayList<>();
                        List<Armor> armors = new ArrayList<>();
                        // Iterate through the hero's inventory and categorize items
                        for (int i = 0; i < selectedHero.getInventory().getItems().size(); i++) {
                            Item item = selectedHero.getInventory().getItems().get(i); // Get the item
                            String type = item.getClass().getSimpleName(); // Get the class name of the item to determine its type (using getSimpleName())
                            if (type.equals("Weapon")) {
                                weapons.add((Weapon) item);
                            }
                            else if (type.equals("Armor")) {
                                armors.add((Armor) item);
                            }
                        }

                        switch (typeChoice) {
                            case 1: // Weapon
                                // Check if there are any weapons to equip
                                if (weapons.isEmpty()) {
                                    System.out.println("No weapons to equip.");
                                    break;
                                }

                                System.out.println("Weapons:");
                                // Display available weapons
                                for (int i = 0; i < weapons.size(); i++) {
                                    System.out.println((i + 1) + ". " + weapons.get(i).getItemName());
                                }

                                System.out.print("Select weapon (or 0 to cancel): ");
                                int wChoice = scanner.nextInt();
                                scanner.nextLine();
                                // Check if user wants to cancel
                                if (wChoice == 0) {
                                    break;
                                }
                                // Validate selection
                                if (wChoice < 1 || wChoice > weapons.size()) {
                                    System.out.println("Invalid selection.");
                                    break;
                                }
                                // Equip the selected weapon
                                boolean equippedW = selectedHero.getInventory().equipWeapon(weapons.get(wChoice - 1), selectedHero, weapons.get(wChoice - 1).getHands());
                                System.out.println(equippedW ? "Equipped: " + weapons.get(wChoice - 1).getItemName() + "!" : "Could not equip this weapon.");

                                break;
                            case 2: // Armor
                                if (armors.isEmpty()) {
                                    System.out.println("No armors to equip.");
                                    break;
                                }
                                System.out.println("Armors:");
                                for (int i = 0; i < armors.size(); i++)
                                    System.out.println((i + 1) + ". " + armors.get(i).getItemName());
                                System.out.print("Select armor (or 0 to cancel): ");
                                int aChoice = scanner.nextInt();
                                scanner.nextLine();
                                if (aChoice == 0) break;
                                if (aChoice < 1 || aChoice > armors.size()) {
                                    System.out.println("Invalid selection.");
                                    break;
                                }
                                boolean equippedA = selectedHero.getInventory().equipArmor(armors.get(aChoice - 1), selectedHero);
                                System.out.println(equippedA ? "Equipped: " + armors.get(aChoice - 1).getItemName() + "!" : "Could not equip this armor.");
                                break;
                            default:
                                System.out.println("Invalid type choice.");
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

    //Display Stats of available items and spells
    public void displayAvailableItemsAndSpells() {
        // Display available items
        System.out.println("---- Available Items ----");
        for (int i = 0; i < availableItems.size(); i++) {
            Item item = availableItems.get(i);
            String output = (i + 1) + ". Name: " + item.getItemName()
                    + ", Market Cost: " + item.getItemMarketCost()
                    + ", Minimum Level: " + item.getItemMinLevel()
                    + ", Type: " + item.getClass().getSimpleName();
            System.out.println(output);
        }
        // Display available spells
        System.out.println("\n---- Available Spells ----");
        for (int i = 0; i < availableSpells.size(); i++) {
            Spell spell = availableSpells.get(i);
            String output = (i + 1) + ". Name: " + spell.getSpellName()
                    + ", Market Cost: " + spell.getSpellCost()
                    + ", Minimum Level: " + spell.getSpellMinLevel()
                    + ", Damage Range: [" + spell.getMinDamage() + ", " + spell.getMaxDamage() + "]"
                    + ", Magic Power: " + spell.getMagicPowerRequired()
                    + ", Type: " + spell.getClass().getSimpleName();
            System.out.println(output);
        }
    }

    //Buy methods for items (weapons, armors, potions)
    public boolean buyWeapon(Hero hero, Weapon weapon) {
        // Check hero requirements: level and money
        if (hero.getLivingLevel() < weapon.getItemMinLevel() || hero.getMoney() < weapon.getItemMarketCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < weapon.getItemMinLevel() ? "level too low" : "not enough gold")
                    + " for this weapon.");
            return false;
        }
        // Check weapon-specific requirement: available hands
        int availableHands = weapon.getHands();
        if (weapon.getHands() > availableHands) {
            System.out.println("Failed buying attempt: not enough hands for this weapon.");
            return false;
        }
        // Complete purchase
        hero.setMoney(hero.getMoney() - weapon.getItemMarketCost());
        hero.getInventory().addItem(weapon);
        System.out.println("Bought the weapon: " + weapon.getItemName());
        return true;
    }

    public boolean buyArmor(Hero hero, Armor armor) {
        // Check hero requirements: level and money
        if (hero.getLivingLevel() < armor.getItemMinLevel() || hero.getMoney() < armor.getItemMarketCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < armor.getItemMinLevel() ? "level too low" : "not enough gold")
                    + " for this armor.");
            return false;
        }
        // Complete purchase
        hero.setMoney(hero.getMoney() - armor.getItemMarketCost());
        hero.getInventory().addItem(armor);
        System.out.println("Bought the armor: " + armor.getItemName());
        return true;
    }

    public boolean buyPotion(Hero hero, Potion potion) {
        // Check hero requirements: level and money
        if (hero.getLivingLevel() < potion.getItemMinLevel() || hero.getMoney() < potion.getItemMarketCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < potion.getItemMinLevel() ? "level too low" : "not enough gold")
                    + " for this potion.");
            return false;
        }
        // Complete purchase
        hero.setMoney(hero.getMoney() - potion.getItemMarketCost());
        hero.getInventory().addItem(potion);
        System.out.println("Bought the potion: " + potion.getItemName());
        return true;
    }
    // Buy methods for spells (ice, fire, lightning)
    public boolean buyIceSpell(Hero hero, IceSpell spell) {
        if (hero.getLivingLevel() < spell.getSpellMinLevel() || hero.getMoney() < spell.getSpellCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < spell.getSpellMinLevel() ? "level too low for this ice spell." : "not enough gold for this ice spell."));
            return false;
        }
        hero.setMoney(hero.getMoney() - spell.getSpellCost());
        hero.getInventory().addSpell(spell);
        System.out.println("Bought the ice spell: " + spell.getSpellName());
        return true;
    }

    public boolean buyFireSpell(Hero hero, FireSpell spell) {
        if (hero.getLivingLevel() < spell.getSpellMinLevel() || hero.getMoney() < spell.getSpellCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < spell.getSpellMinLevel() ? "level too low for this fire spell." : "not enough gold for this fire spell."));
            return false;
        }
        hero.setMoney(hero.getMoney() - spell.getSpellCost());
        hero.getInventory().addSpell(spell);
        System.out.println("Bought the fire spell: " + spell.getSpellName());
        return true;
    }

    public boolean buyLightningSpell(Hero hero, LightningSpell spell) {
        if (hero.getLivingLevel() < spell.getSpellMinLevel() || hero.getMoney() < spell.getSpellCost()) {
            System.out.println("Failed buying attempt: " +
                    (hero.getLivingLevel() < spell.getSpellMinLevel() ? "level too low for this lightning spell." : "not enough gold for this lightning spell."));
            return false;
        }
        hero.setMoney(hero.getMoney() - spell.getSpellCost());
        hero.getInventory().addSpell(spell);
        System.out.println("Bought the lightning spell: " + spell.getSpellName());
        return true;
    }

    //Sell methods for item and spell
    public boolean sellItem(Hero hero, Item item) {
        // Sell price is 50% of the market cost
        double sellPrice = item.getItemMarketCost() * 0.5;
        // Check if the item is in the hero's inventory
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