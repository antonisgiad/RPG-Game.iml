public class Hero extends Living {
    //Variables
    double magicPower, strength, dexterity, agility, money, experience;

    //Functions
    //Constructor
    public Hero(String living_name) {
        super(living_name);
        this.magicPower = 100;
        this.strength = 10;
        this.dexterity = 10;
        this.agility = 10;
        this.money = 50;
        this.experience = 0;
    }

    //Getters & Setters
    public double getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(double magicPower) {
        this.magicPower = magicPower;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getDexterity() {
        return dexterity;
    }

    public void setDexterity(double dexterity) {
        this.dexterity = dexterity;
    }

    public double getAgility() {
        return agility;
    }

    public void setAgility(double agility) {
        this.agility = agility;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    //Level Up Hero
    public void levelUp() {
        // Check if hero has requiredExperience
        int requiredExperience = living_level * 10;
        if (experience >= requiredExperience) {
               living_level++;
               experience -= requiredExperience;
               // Increase statistics
               strength += strength * 0.5;
               dexterity += dexterity * 0.5;
               agility += agility * 0.5;
               System.out.println(living_name + " leveled up to " + living_level + "!");
        }
    }

    public boolean dodgeMonsterChance(){
        double chance = agility * 0.01;
        if (chance > 0.5){
            chance = 0.5;
        }
        return Math.random() < chance;
    }

    public void increased_spellcasting_ability(Spell spell) {
        // Define dexterity bonus
        int dexterityBonus = (int)(this.dexterity / 10);

        // Increase damage range
        spell.setMinDamage(spell.getMinDamage() + dexterityBonus);
        spell.setMaxDamage(spell.getMaxDamage() + dexterityBonus);

        // Set new damage range (affected by dexterity)
        double[] newDamageRange = {spell.getMinDamage(), spell.getMaxDamage()};
        spell.setDamageRange(newDamageRange);

        System.out.println("Spell's damage range has been increased by +" + dexterityBonus + "!");
    }

    public void add_strength_to_total_weapon_damage(Weapon weapon){
        double weapon_damage = weapon.getWeapon_damage();
        // Set new weapon damage (affected by strength)
        weapon.setWeapon_damage(weapon_damage + this.strength);
    }
}