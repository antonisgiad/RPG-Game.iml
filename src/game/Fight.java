package game;

import item.Armor;
import item.Item;
import item.Potion;
import item.Weapon;
import living.heroes.Hero;
import living.monster.Monster;
import spell.FireSpell;
import spell.IceSpell;
import spell.LightningSpell;
import spell.Spell;
import utils.RandomUtil;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Fight {
    // Variables
    private List<Hero> heroes;
    private List<Monster> monsters;

    //Functions

    // Constructor
    public Fight(List<Hero> heroes, int count) {
        this.heroes = heroes;
        this.monsters = generateMonstersForFight(count);
    }

    public List<Monster> generateMonstersForFight(int count) {
        List<Monster> monsters = new ArrayList<>();
        String[] monsterTypes = {"Dragon", "Spirit", "Exoskeleton"};
        for (int i = 0; i < count; i++) {
            int typeIndex = RandomUtil.randomLevel(0, monsterTypes.length - 1);
            String name = monsterTypes[typeIndex] + "_" + (i + 1);
            Monster monster = new Monster(name);
            monsters.add(monster);
        }
        return monsters;
    }

    public void startBattle() {
        System.out.println("Battle begins!");
        generateMonstersForFight(heroes.size());

        while (!allMonstersDefeated() || !allHeroesDefeated()) {
            // First hero's turn
            for (int i = 0; i < heroes.size(); i++) {
                Hero hero = heroes.get(i);
                if (!hero.passedOut()) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println(hero.getLivingName() + "'s turn!");
                    System.out.println("Choose action: 1) Attack 2) Cast Spell 3) Use Potion 4) Change Equipment (Weapon/Armor)");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            // Find and attack the first monster that is not passed out
                            for (int j = 0; j < monsters.size(); j++) {
                                Monster m = monsters.get(j);
                                if (!m.passedOut()) {
                                    attack(hero, m);
                                    break; // Only attack one monster per turn
                                }
                            }
                            break;
                        case 2:
                            // Cast Spell
                            // Check if hero has spells available
                            if (hero.getInventory().getSpells().isEmpty()) {
                                System.out.println("No spells available.");
                                break;
                            }
                            // List available spells
                            System.out.println("Available spells:");
                            List<Spell> heroSpells = hero.getInventory().getSpells();
                            for (int k = 0; k < heroSpells.size(); k++) {
                                Spell s = heroSpells.get(k);
                                System.out.println((k + 1) + ". " + s.getSpellName() + " (Cost: " + s.getSpellCost() + ")");
                            }
                            // Prompt user to select a spell
                            System.out.print("Select spell: ");
                            int spellChoice = Integer.parseInt(scanner.nextLine()) - 1;
                            // Validate spell choice
                            if (spellChoice < 0 || spellChoice >= heroSpells.size()) {
                                System.out.println("Invalid selection.");
                                break;
                            }

                            Spell selectedSpell = heroSpells.get(spellChoice);
                            // Check if hero has enough magic power to cast the spell
                            boolean magicPowerConsumed = selectedSpell.consumedMagicPowerAfterUse(hero);
                            if (!magicPowerConsumed) {
                                break;
                            }
                            // List available target monsters
                            System.out.println("Select target monster:");
                            for (int m = 0; m < monsters.size(); m++) {
                                Monster mon = monsters.get(m);
                                if (!mon.passedOut()) {
                                    System.out.println((m + 1) + ". " + mon.getLivingName());
                                }
                            }
                            // Prompt user to select a target monster
                            int monsterChoice = Integer.parseInt(scanner.nextLine()) - 1;
                            if (monsterChoice < 0 || monsterChoice >= monsters.size() || monsters.get(monsterChoice).passedOut()) {
                                System.out.println("Invalid target.");
                                break;
                            }
                            Monster target = monsters.get(monsterChoice);
                            // Applying spell effect based on spell type
                            if (selectedSpell.getClass().getSimpleName().equals("IceSpell")) {
                                ((IceSpell) selectedSpell).reduceEnemyDamageRange(target, 3);
                                ((IceSpell) selectedSpell).castIceSpell(target, hero);
                            }
                            else if (selectedSpell.getClass().getSimpleName().equals("LightningSpell")) {
                                ((LightningSpell) selectedSpell).reduceEnemyDodgeChance(target, 3);
                                ((LightningSpell) selectedSpell).castLightningSpell(target, hero);
                            }
                            else if (selectedSpell.getClass().getSimpleName().equals("FireSpell")) {
                                ((FireSpell) selectedSpell).reduceEnemyDefense(target, 3);
                                ((FireSpell) selectedSpell).castFireSpell(target, hero);
                            }
                            else {
                                System.out.println("Spell effect not implemented.");
                            }
                            break;
                         case 3:
                             // Use a potion
                             List<Item> heroItems = hero.getInventory().getItems();
                             List<Potion> potions = new ArrayList<>();
                             // Iterate through the hero's inventory and find potions
                             for (int l = 0; l < heroItems.size(); l++) {
                                 Item item = heroItems.get(l);
                                 if (item.getClass().getSimpleName().equals("Potion")) {
                                     potions.add((Potion) item);
                                 }
                             }
                             // Check if there are any potions to use
                             if (potions.isEmpty()) {
                                 System.out.println("No potions available to use.");
                                 break;
                             }
                             System.out.println("Available Potions:");
                             // Display available potions
                             for (int m = 0; m < potions.size(); m++) {
                                 System.out.println((m + 1) + ". " + potions.get(m).getItemName());
                             }

                             System.out.print("Select potion to use (or 0 to cancel): ");
                             int potionChoice = scanner.nextInt();
                             scanner.nextLine(); // consume newline
                             // Check if user wants to cancel
                             if (potionChoice == 0) {
                                 break;
                             }
                             // Validate selection
                             if (potionChoice < 1 || potionChoice > potions.size()) {
                                 System.out.println("Invalid selection.");
                                 break;
                             }
                             // Use the selected potion
                             Potion chosenPotion = potions.get(potionChoice - 1);
                             // Call the increaseRandomStatistic method on the potion
                             chosenPotion.increaseRandomStatistic(hero);
                             // Remove the potion from inventory after use
                             chosenPotion.destroyWhenUsed(hero.getInventory());
                             System.out.println("Potion used successfully.");
                             break;
                         case 4:
                             boolean equipMenuExit = false;
                             while (!equipMenuExit) {
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
                                 for (int l = 0; l < hero.getInventory().getItems().size(); l++) {
                                     Item item = hero.getInventory().getItems().get(l);
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
                                         for (int l = 0; l < weapons.size(); l++) {
                                             System.out.println((l + 1) + ". " + weapons.get(l).getItemName());
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
                                         boolean equippedW = hero.getInventory().equipWeapon(chosenWeapon, hero, chosenWeapon.getHands());
                                         System.out.println(equippedW ? "Equipped: " + chosenWeapon.getItemName() + "!" : "Cannot equip weapon: hands are full.");
                                         break;
                                     case 2: // Armor
                                         if (armors.isEmpty()) {
                                             System.out.println("No armors to equip.");
                                             break;
                                         }
                                         System.out.println("Armors:");
                                         for (int m = 0; m < armors.size(); m++) {
                                             System.out.println((m + 1) + ". " + armors.get(m).getItemName());
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
                                         boolean equippedA = hero.getInventory().equipArmor(chosenArmor, hero);
                                         System.out.println(equippedA ? "Equipped: " + chosenArmor.getItemName() + "!" : "Could not equip this armor.");
                                         break;
                                     default:
                                         System.out.println("Invalid type choice.");
                                         break;
                                 }
                                 equipMenuExit = true;
                             }
                             break;
                        default:
                            System.out.println("Invalid choice. Turn skipped.");
                    }
                }
            }
            // Update monsters debuffs
            for (Monster monster : monsters) {
                monster.updateIceEffect();
                monster.updateFireEffect();
                monster.updateLightningEffect();
            }

//            // Monsters Turn
//            for (Monster monster : monsters) {
//                if (monster.passedOut()) {
//                    Hero target = selectTarget(heroes);
//                    if (target != null) {
//                        monster.takeTurn(target); // attack, spell, κτλ.
//                    }
//                }
//            }
//
//            printBattleStatus();
        }

        if (allMonstersDefeated()) {
            System.out.println("Heroes win the battle!");
//            rewardHeroes();
        }
        else {
            System.out.println("Monsters win the battle!");
//            penalizeHeroes();
        }
    }

    public void attack(Hero caster, Monster target) {
        // Calculate base attack power
        double baseAttack = caster.getStrength();

        // Add weapon bonus if equipped
        if (caster.getInventory().getEquippedWeapon() != null) {
            baseAttack += caster.getInventory().getEquippedWeapon().getWeaponDamage();
        }

        // Get target's defense
        double defense = target.getDefense();

        // Calculate damage (ensure at least 1)
        double damage = Math.max(1, baseAttack - defense);
        damage = Math.round(damage * 100.0) / 100.0;

        // Apply damage
        target.setHealthPower(target.getHealthPower() - damage);

        // Print outcome
        System.out.println(caster.getLivingName() + " attacks " + target.getLivingName() +
                " for " + damage + " damage! (" + target.getLivingName() + " HP: " + target.getHealthPower() + ")");
    }

    //Check each monster if it's alive or dead
    private boolean allMonstersDefeated() {
        return monsters.stream().allMatch(Monster::passedOut);
    }

    //Check each monster if it's alive or dead
    private boolean allHeroesDefeated() {
        return heroes.stream().noneMatch(Hero::passedOut);
    }

        // Getters & Setters
    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
}
