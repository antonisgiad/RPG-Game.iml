Java RPG Game Simulation

Overview
This is a Java-based Role-Playing Game (RPG) simulation project featuring heroes, monsters, items, spells, turn-based combat, and a grid-based world with markets and interactive elements.

Features
Heroes: Team of 1–3, each as Warrior, Sorcerer, or Paladin (each with unique strengths).

Monsters: Dragons (high damage), Exoskeletons (strong defense), Spirits (high evasion).

Items:

Weapons: For damage (one or two hands required).

Armors: Reduce incoming damage.

Potions: Temporary stat boosts (single use).

Spells:

Ice Spell: Reduces enemy damage for a few rounds.

Fire Spell: Reduces enemy defense for a few rounds.

Lighting Spell: Reduces enemy's dodge chance for a few rounds.

Grid World:

Accessible and non-accessible tiles, markets, common tiles for random battles.

Optional: Rivers (require fatigue to cross) and caves (require a shovel from the market).

Gameplay
Turn-based combat: Heroes vs. Monsters, fighting continues until one side is defeated.

Hero actions: Attack, cast spell, use potion, or equip gear on each turn.

Earn gold and experience by defeating monsters; level up to improve stats.

Heroes level up to gain stat increases based on class.

Each hero manages their own inventory and uses a market to buy/sell at half price.

Explore the map: move up, down, left, right, display hero locations.

View stats or inventory at any time, or quit the game any time.

Optional Enhancements
Fatigue system: Heroes lose fatigue when performing certain actions or crossing terrain; can rest outside combat.

Additional map features: Rivers and caves for more variety and strategy.

How to Run
Clone the repository:

git clone <your-repository-url>
cd <repository-folder>

Compile:

javac *.java

Run:

java Main

Project Structure
File/Class : Description
Hero.java : Hero base class and subclasses (Warrior, Sorcerer, Paladin)
Monster.java : Monster base class and specializations (Dragon, Exoskeleton, Spirit)
Item.java : Weapons, armors, potions
Spell.java : Ice, fire, lighting spell definitions
Grid.java : Game world and map logic
Market.java : Buy/sell logic
Fight.java : Battle and combat mechanics
Main.java : Game loop and user interface

Controls
Movement: Move up, down, left, right on the grid.

Market: Enter market to buy/sell equipment.

Combat: Attack, cast spell, use potion, or equip during battle.

Inventory: View, equip, and use items.

Map: View grid and hero positions.

Stats: Display hero/monster stats.

Quit: Exit the game.

Have fun building and expanding your RPG game!
