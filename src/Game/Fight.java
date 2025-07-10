package Game;

import living.heroes.Hero;
import living.monster.Monster;
import utils.RandomUtil;

import java.util.List;
import java.util.ArrayList;

public class Fight {
    private List<Hero> heroes;
    private List<Monster> monsters;

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

//        while (!allMonstersDefeated() || !allHeroesDefeated()) {
//            // First hero's turn
//            for (Hero hero : heroes) {
//                if (!hero.passedOut()) {
//                    Scanner scanner = new Scanner(System.in);
//                    System.out.println(hero.getLivingName() + "'s turn!");
//                    System.out.println("Choose action: 1) Attack 2) Cast Spell 3) Change Equipment");
//                    int choice = Integer.parseInt(scanner.nextLine());
//                    switch (choice) {
//                        case 1:
//                            // Find and attack the first monster that is not passed out
//                            for (Monster m : monsters) {
//                                if (!m.passedOut()) {
//                                    hero.attack(m);
//                                    break; // Only attack one monster per turn
//                                }
//                            }
//                            break;
//                        case 2:
//                            if (hero.hasSpells()) {
//                                Spell spell = hero.chooseSpell(scanner);
//                                Monster spellTarget = selectTarget(monsters);
//                                hero.castSpell(spell, spellTarget);
//                            } else {
//                                System.out.println("No spells available.");
//                            }
//                            break;
//                        case 3:
//                            hero.changeEquipment(scanner);
//                            break;
//                        default:
//                            System.out.println("Invalid choice. Turn skipped.");
//                    }
//                }
//            }
//            // Ενημέρωση debuffs στα τέρατα
//            for (Monster monster : monsters) {
//                monster.updateIceEffect();
//                monster.updateFireEffect();
//                monster.updateLightningEffect();
//            }
//
//            // Γύρος τεράτων
//            for (Monster monster : monsters) {
//                if (monster.passedOut()) {
//                    Hero target = selectTarget(heroes);
//                    if (target != null) {
//                        monster.takeTurn(target); // attack, spell, κτλ.
//                    }
//                }
//            }
//            // Ενημέρωση debuffs στους ήρωες (αν υπάρχουν)
//            for (Hero hero : heroes) {
//                hero.updateDebuffs();
//            }
//
//            printBattleStatus();
//        }
//
//        if (allMonstersDefeated()) {
//            System.out.println("Heroes win the battle!");
//            rewardHeroes();
//        } else {
//            System.out.println("Monsters win the battle!");
//            penalizeHeroes();
//        }
    }
    //Check each monster if it's alive or dead
    private boolean allMonstersDefeated() {
        return monsters.stream().allMatch(Monster::passedOut);
    }
    //Check each monster if it's alive or dead
    private boolean allHeroesDefeated() {
        return heroes.stream().noneMatch(Hero::passedOut);
    }
}
