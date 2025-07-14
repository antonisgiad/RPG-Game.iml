package Game;

import item.Item;
import living.heroes.Hero;
import spell.Spell;
import java.util.ArrayList;
import java.util.List;
import item.Weapon;
import item.Armor;

public class Inventory {
    // Variables
    private List<Item> items;  // Items owned by the player
    private List<Spell> spells; // Spells owned by the player
    private Weapon equippedWeapon;
    private Armor equippedArmor;
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

    public boolean equipWeapon(Weapon weapon, Hero hero, int availableHands) {
        if (hero.getLivingLevel() < weapon.getItemMinLevel()) {
            System.out.println("Level too low to equip this weapon.");
            return false;
        }
        if (weapon.getHands() > availableHands) {
            System.out.println("Not enough hands to equip this weapon.");
            return false;
        }
        // Attempt to remove the weapon from the hero's inventory before equipping it.
        if (!this.removeItem(weapon)) {
            System.out.println("Failed to remove the weapon from inventory.");
            return false;
        }
        this.equippedWeapon = weapon;
        return true;
    }

    public boolean equipArmor(Armor armor, Hero hero) {
        if (hero.getLivingLevel() < armor.getItemMinLevel()) {
            System.out.println("Level too low to equip this weapon.");
            return false;
        }
        this.equippedArmor = armor;
        return true;
    }

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

    //Getters & Setters
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public void setEquippedArmor(Armor equippedArmor) {
        this.equippedArmor = equippedArmor;
    }
}