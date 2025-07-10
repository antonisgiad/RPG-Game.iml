package Game;

import item.Item;
import spell.Spell;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    // Variables
    private List<Item> items;  // Items owned by the player
    private List<Spell> spells; // Spells owned by the player

    // Constructor
    public Inventory() {
        this.items = new ArrayList<>();
        this.spells = new ArrayList<>();
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
}