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

        // Monster type name arrays
        String[] dragonNames = {"Flame-heart", "Shadow-fang", "Ember-claw", "Storm-bringer", "Frostbite"};
        String[] spiritNames = {"Wraith", "Phantom", "Specter", "Poltergeist", "Banshee"};
        String[] exoNames = {"Iron-shell", "Carapace", "Titan-plate", "Steel-crusher", "Stone-hide"};

        String[] monsterTypes = {"Dragon", "Spirit", "Exoskeleton"};
        // Generate monsters based on the count and types
        for (int i = 0; i < count; i++) {
            int typeIndex = RandomUtil.randomLevel(0, monsterTypes.length - 1);
            String type = monsterTypes[typeIndex];

            // Select name based on monster type
            String baseName = switch (type) {
                case "Dragon" -> dragonNames[RandomUtil.randomLevel(0, dragonNames.length - 1)];
                case "Spirit" -> spiritNames[RandomUtil.randomLevel(0, spiritNames.length - 1)];
                case "Exoskeleton" -> exoNames[RandomUtil.randomLevel(0, exoNames.length - 1)];
                default -> "Unknown";
            };

            // Create distinguished name with type in parentheses
            String name = baseName + " (" + type + ")";
            Monster monster = new Monster(name);
            monsters.add(monster);
        }
        return monsters;
    }

    public void startBattle() {
        System.out.println("\n\033[1;33m==== BATTLE BEGINS! ====\033[0m");

        // Display initial battle status
        displayBattleStatus();

        // Initialize state variables
        Scanner scanner = new Scanner(System.in);
        boolean battleContinues = true;
        int heroIndex = 0;

        // Track which entities have been announced as passed out
        List<String> announcedPassedOut = new ArrayList<>();

        // Initialize round counter before the main battle loop
        int roundCounter = 1;
        // Main battle loop
        while (battleContinues) {
            // Display round number
            System.out.println("\n\033[0;35m===== ROUND " + roundCounter + " =====\033[0m");

            // Only apply regeneration if this is NOT the first round
            if (roundCounter > 1) {
                System.out.println("\n\033[0;35m=== REGENERATION PHASE ===\033[0m");
                // Regenerate health and magic for heroes
                for (Hero hero : heroes) {
                    regenerateHealth(hero);
                    regenerateMagic(hero);
                }
                // Regenerate health for monsters
                for (Monster monster : monsters) {
                    regenerateHealth(monster);
                }
            }

            // Get lists of alive heroes and monsters
            List<Hero> aliveHeroes = new ArrayList<>();
            List<Monster> aliveMonsters = new ArrayList<>();

            // Filter out passed heroes
            for (Hero h : heroes) {
                if (!h.passedOut()) {
                    aliveHeroes.add(h);
                }
            }
            // Filter out passed monsters
            for (Monster m : monsters) {
                if (!m.passedOut()) {
                    aliveMonsters.add(m);
                }
            }

            // Check if battle is over
            if (aliveHeroes.isEmpty() || aliveMonsters.isEmpty()) {
                battleContinues = false;
                break;
            }

            // Reset hero index if needed
            if (heroIndex >= aliveHeroes.size()) {
                heroIndex = 0; // Reset to the first hero if all have taken their turn
            }

            // HERO TURN
            Hero currentHero = aliveHeroes.get(heroIndex);
            System.out.println("\n" + currentHero.getLivingName() + "'s turn!");
            System.out.println("Choose action: 1) Attack 2) Cast Spell 3) Use Potion 4) Change Equipment");

            String input = scanner.nextLine();
            int choice;
            // Validate input
            if (input.matches("\\d+")) {
                choice = Integer.parseInt(input);
            }
            else {
                System.out.println("Invalid input. Turn skipped.");
                choice = 0;
            }
            // Process hero's choice
            switch (choice) {
                // Attack
                case 1:
                    // Find and attack the first monster that is not passed out
                    for (Monster m : monsters) {
                        if (!m.passedOut()) {
                            heroAttack(currentHero, m);

                            // Check if monster passed out after the attack
                            if (m.passedOut() && !announcedPassedOut.contains(m.getLivingName())) {
                                System.out.println("\n\033[1;33m⚠ " + m.getLivingName() + " has been defeated! ⚠\033[0m");
                                announcedPassedOut.add(m.getLivingName());
                            }
                            break; // Only attack one monster per turn
                        }
                    }
                    break;
                // Cast Spell
                case 2:
                    // Check if hero has spells available
                    if (currentHero.getInventory().getSpells().isEmpty()) {
                        System.out.println("No spells available.");
                        break;
                    }
                    // List available spells
                    System.out.println("Available spells:");
                    List<Spell> heroSpells = currentHero.getInventory().getSpells();
                    for (int k = 0; k < heroSpells.size(); k++) {
                        Spell s = heroSpells.get(k);
                        System.out.println((k + 1) + ". " + s.getSpellName() + " (Cost: " + s.getSpellCost() + ")");
                    }
                    // Prompt user to select a spell
                    System.out.print("Select spell: ");
                    int spellChoice;
                    try {
                        spellChoice = Integer.parseInt(scanner.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    // Validate spell choice
                    if (spellChoice < 0 || spellChoice >= heroSpells.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    Spell selectedSpell = heroSpells.get(spellChoice);
                    // Check if hero has enough magic power to cast the spell
                    boolean magicPowerConsumed = selectedSpell.consumedMagicPowerAfterUse(currentHero);
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
                    int monsterChoice;
                    try {
                        monsterChoice = Integer.parseInt(scanner.nextLine()) - 1;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid target.");
                        break;
                    }

                    if (monsterChoice < 0 || monsterChoice >= monsters.size() || monsters.get(monsterChoice).passedOut()) {
                        System.out.println("Invalid target.");
                        break;
                    }
                    Monster target = monsters.get(monsterChoice);
                    // Applying spell effect based on spell type
                    switch (selectedSpell.getClass().getSimpleName()) {
                        case "IceSpell" -> {
                            ((IceSpell) selectedSpell).reduceEnemyDamageRange(target, 3);
                            ((IceSpell) selectedSpell).castIceSpell(target, currentHero);
                        }
                        case "LightningSpell" -> {
                            ((LightningSpell) selectedSpell).reduceEnemyDodgeChance(target, 3);
                            ((LightningSpell) selectedSpell).castLightningSpell(target, currentHero);
                        }
                        case "FireSpell" -> {
                            ((FireSpell) selectedSpell).reduceEnemyDefense(target, 3);
                            ((FireSpell) selectedSpell).castFireSpell(target, currentHero);
                        }
                        default -> System.out.println("Spell effect not implemented.");
                    }

                    // Check if monster passed out after the spell
                    if (target.passedOut() && !announcedPassedOut.contains(target.getLivingName())) {
                        System.out.println("\n\033[1;33m⚠ " + target.getLivingName() + " has been defeated! ⚠\033[0m");
                        announcedPassedOut.add(target.getLivingName());
                    }
                    break;
                //  Use Potion
                case 3:
                    List<Item> heroItems = currentHero.getInventory().getItems();
                    List<Potion> potions = new ArrayList<>();
                    // Iterate through the hero's inventory and find potions
                    for (Item item : heroItems) {
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
                    int potionChoice;
                    try {
                        potionChoice = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid selection.");
                        break;
                    }

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
                    chosenPotion.increaseRandomStatistic(currentHero);
                    // Remove the potion from inventory after use
                    chosenPotion.destroyWhenUsed(currentHero.getInventory());
                    System.out.println("Potion used successfully.");
                    break;
                // Change  Equipment
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
                        for (int i = 0; i < currentHero.getInventory().getItems().size(); i++) {
                            Item item = currentHero.getInventory().getItems().get(i);
                            String type = item.getClass().getSimpleName();
                            if (type.equals("Weapon")) {
                                weapons.add((Weapon) item);
                            }
                            else if (type.equals("Armor")) {
                                armors.add((Armor) item);
                            }
                        }
                        // Check the type choice and display corresponding items
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
                                boolean equippedW = currentHero.getInventory().equipWeapon(chosenWeapon, currentHero, chosenWeapon.getHands());
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
                                boolean equippedA = currentHero.getInventory().equipArmor(chosenArmor, currentHero);
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

            // Increment hero index for next turn
            heroIndex++;

            // Check battle status after hero's action
            if (aliveHeroes.isEmpty()) {
                battleContinues = false;
                break;
            }

            // Update monsters' debuffs after hero turn
            for (Monster monster : monsters) {
                monster.updateIceEffect();
                monster.updateFireEffect();
                monster.updateLightningEffect();
            }

            // MONSTER TURN
            // Pick a random monster to attack
            int monsterIndex = RandomUtil.randomLevel(0, aliveMonsters.size() - 1);
            Monster attackingMonster = aliveMonsters.get(monsterIndex);

            // Find a random alive hero to target
            List<Hero> targetableHeroes = new ArrayList<>();
            for (Hero h : heroes) {
                if (!h.passedOut()) {
                    targetableHeroes.add(h);
                }
            }

            if (!targetableHeroes.isEmpty()) {
                int targetIndex = RandomUtil.randomLevel(0, targetableHeroes.size() - 1);
                Hero targetHero = targetableHeroes.get(targetIndex);
                // Monster attacks the selected hero
                monsterAttack(attackingMonster, targetHero);

                // Check if hero passed out after the attack
                if (targetHero.passedOut() && !announcedPassedOut.contains(targetHero.getLivingName())) {
                    System.out.println("\n\033[1;33m⚠ " + targetHero.getLivingName() + " has been defeated! ⚠\033[0m");
                    announcedPassedOut.add(targetHero.getLivingName());
                }
            }

            // Display battle status after each round
            displayBattleStatus();

            // Check if battle is over after monster's turn
            if (allMonstersDefeated() || allHeroesDefeated()) {
                battleContinues = false;
            }
            roundCounter++; // Increment round counter after each full round
        }

        // Announce winner
        if (allMonstersDefeated()) {
            System.out.println("\n\033[1;32m🏆 HEROES WIN THE BATTLE! 🏆\033[0m");
            // Award experience and gold to heroes here
            for (Hero hero : heroes) {
                // Base rewards scaled by hero level and monster count
                double baseMoneyReward = 100;
                double baseExpReward = 20;

                // Calculate final rewards
                double moneyReward = baseMoneyReward * hero.getLivingLevel() * monsters.size() * 0.25;
                // Round to 2 decimal places
                moneyReward = Math.round(moneyReward * 100.0) / 100.0;

                double expReward = baseExpReward * hero.getLivingLevel() * monsters.size() * 0.5;
                // Round to 2 decimal places
                expReward = Math.round(expReward * 100.0) / 100.0;

                // Apply rewards
                hero.setMoney(hero.getMoney() + moneyReward);
                hero.setExperience(hero.getExperience() + expReward);

                // Announce rewards
                System.out.println("\033[1;33m" + hero.getLivingName() +
                        " gained " + moneyReward + " gold and " +
                        expReward + " experience!\033[0m");

                // Check if hero leveled up (assuming you have a method to check/handle level ups)
                hero.levelUp();
            }
        }
        else {
            System.out.println("\n\033[1;31m☠ MONSTERS WIN THE BATTLE! ☠\033[0m");
            // Heroes lose half their money
            for (Hero hero : heroes) {
                double oldMoney = hero.getMoney();
                double newMoney = oldMoney / 2;
                hero.setMoney(newMoney);

                System.out.println("\033[1;31m" + hero.getLivingName() + " lost " + (oldMoney - newMoney) + " gold!\033[0m");
            }
        }
    }

    // Display battle status
    private void displayBattleStatus() {
        System.out.println("\n--- Battle Status ---");

        // Only show living heroes
        System.out.println("Heroes:");
        int aliveHeroesCount = 0;
        for (Hero hero : heroes) {
            if (!hero.passedOut()) {
                System.out.println("- \033[1;36m" + hero.getLivingName() +
                        " \033[0;36m(" + hero.getClass().getSimpleName() + ")\033[0m" +
                        " [HP: " + hero.getHealthPower() + "]");
                aliveHeroesCount++;
            }
        }
        System.out.println("(\033[0;36m" + aliveHeroesCount + "/" + heroes.size() + " alive\033[0m)");

        // Only show living monsters
        System.out.println("Monsters:");
        int aliveMonsterCount = 0;
        for (Monster monster : monsters) {
            if (!monster.passedOut()) {
                System.out.println("- \033[1;31m" + monster.getLivingName() + "\033[0m" +
                        " [HP: " + monster.getHealthPower() + "]");
                aliveMonsterCount++;
            }
        }
        System.out.println("(\033[0;31m" + aliveMonsterCount + "/" + monsters.size() + " alive\033[0m)");

        System.out.println("-------------------\n");
    }

    // Hero attacks the monster
    public void heroAttack(Hero caster, Monster target) {
        // Calculate base attack power
        double baseAttack =
                caster.getStrength();

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
        System.out.println("\n\033[1;36m" + caster.getLivingName() + " \033[0;36m(" + caster.getClass().getSimpleName() + ")\033[0m" +
                " \033[1;33m⚔️ STRIKES\033[0m " +
                "\033[1;31m" + target.getLivingName() + "\033[0m" +
                " for \033[1;31m" + damage + " DAMAGE\033[0m!" +
                " [\033[0;31m" + target.getLivingName() + " HP: " + target.getHealthPower() + "\033[0m]");
    }
    // Monster attacks the hero
    public void monsterAttack(Monster attacker, Hero target) {
        // Calculate base attack power
        double damage = attacker.getMonsterDamageRange();

        // Check if hero has armor equipped
        double defense = 0;
        if (target.getInventory().getEquippedArmor() != null) {
            defense = target.getInventory().getEquippedArmor().getReducedDamage();
        }

        // Calculate final damage (minimum 1)
        double finalDamage = Math.max(1, damage - defense);
        finalDamage = Math.round(finalDamage * 100.0) / 100.0;

        // Apply damage to hero
        target.setHealthPower(target.getHealthPower() - finalDamage);

        // Print attack outcome
        System.out.println("\n\033[1;31m" + attacker.getLivingName() + "\033[0m" +
                " \033[1;33m🔥 ATTACKS\033[0m " +
                "\033[1;36m" + target.getLivingName() + " \033[0;36m(" + target.getClass().getSimpleName() + ")\033[0m" +
                " for \033[1;31m" + finalDamage + " DAMAGE\033[0m!" +
                " [\033[0;36m" + target.getLivingName() + " HP: " + target.getHealthPower() + "\033[0m]");
    }

    // Regenerate health for heroes
    private void regenerateHealth(Hero hero) {
        if (hero.getHealthPower() > 0) {  // Only regenerate if not passed out
            // Calculate regeneration amount based on level
            double regenAmount = 0.5 + (hero.getLivingLevel() * 0.5);
            // Round to 2 decimal places
            regenAmount = Math.round(regenAmount * 100.0) / 100.0;

            double newHealth = hero.getHealthPower() + regenAmount;
            // Round the new health value as well
            newHealth = Math.round(newHealth * 100.0) / 100.0;

            hero.setHealthPower(newHealth);
            System.out.println("\033[1;32m♥ " + hero.getLivingName() + " regenerated " +
                    newHealth + " HP.\033[0m");
        }
    }
    // Regenerate magic for heroes
    private void regenerateMagic(Hero hero) {
        if (hero.getHealthPower() > 0) {  // Only regenerate if not passed out
            // Calculate regeneration amount based on level
            double regenAmount = 1 + (hero.getLivingLevel() * 0.8);
            // Round to 2 decimal places
            regenAmount = Math.round(regenAmount * 100.0) / 100.0;

            double newMagic = hero.getMagicPower() + regenAmount;
            // Round the new magic value as well
            newMagic = Math.round(newMagic * 100.0) / 100.0;

            hero.setMagicPower(newMagic);
            System.out.println("\033[1;34m✨ " + hero.getLivingName() + " regenerated " +
                    newMagic + " MP.\033[0m");
        }
    }
    // Regenerate health for monsters
    private void regenerateHealth(Monster monster) {
        if (monster.getHealthPower() > 0) {  // Only regenerate if not passed out
            // Calculate regeneration amount based on level
            double regenAmount = 0.3 + (monster.getLivingLevel() * 0.3);
            // Round to 2 decimal places
            regenAmount = Math.round(regenAmount * 100.0) / 100.0;

            double newHealth = monster.getHealthPower() + regenAmount;
            // Round the new health value as well
            newHealth = Math.round(newHealth * 100.0) / 100.0;

            monster.setHealthPower(newHealth);
            System.out.println("\033[0;32m♥ " + monster.getLivingName() + " regenerated " +
                    newHealth + " HP.\033[0m");
        }
    }

    //Check each monster if it's alive or dead
    private boolean allMonstersDefeated() {
        return monsters.stream().allMatch(Monster::passedOut);
    }

    //Check each monster if it's alive or dead
    private boolean allHeroesDefeated() {
        return heroes.stream().allMatch(Hero::passedOut);
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
