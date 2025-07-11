package Game;

import item.Item;
import spell.Spell;
import java.util.ArrayList;
import java.util.List;
import item.Weapon;
import item.Armor;

public class Inventory {
    // Variables
    private List<Item> items;  // Items owned by the player
    private List<Spell> spells; // Spells owned by the player

    // Constructor
    public Inventory() {
        this.items = new ArrayList<>();
        this.spells = new ArrayList<>();
    }

    //Display heroes items and spells
    public void checkInventory() {
        System.out.println("=== Inventory ===");

        // Display Items
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
        } else {
            System.out.println("Items:");
            int count = 1;
            for (Item item : items) {
                System.out.println(count + ". " + item.getItemName() + " (Type: " + item.getClass().getSimpleName() + ")");
                count++;
            }
        }

        // Display Spells
        if (spells.isEmpty()) {
            System.out.println("No spells in inventory.");
        } else {
            System.out.println("Spells:");
            int count = 1;
            for (Spell spell : spells) {
                System.out.println(count + ". " + spell.getSpellName() + " (Type: " + spell.getClass().getSimpleName() + ")");
                count++;
            }
        }
        System.out.println("=================");
    }

//    public boolean equip(Item item) {
//        // Check if item is a weapon
//        if (item instanceof Weapon) {
//            this.equippedWeapon = (Weapon) item;
//            System.out.println("Εξοπλίστηκε το όπλο: " + item.getItemName());
//            return true;
//        }
//        // Έλεγχος αν το αντικείμενο είναι πανοπλία
//        else if (item instanceof Armor) {
//            this.equippedArmor = (Armor) item;
//            System.out.println("Εξοπλίστηκε η πανοπλία: " + item.getItemName());
//            return true;
//        }
//        System.out.println("Το αντικείμενο δεν μπορεί να εξοπλιστεί.");
//        return false;
//    }

    // Add an item
    public void addItem(Item item) {
        items.add(item);
    }

    // Remove an item
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    // List all items
    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    // Add a spell
    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    // Remove a spell
    public boolean removeSpell(Spell spell) {
        return spells.remove(spell);
    }

    // List all spells
    public List<Spell> getSpells() {
        return new ArrayList<>(spells);
    }
}