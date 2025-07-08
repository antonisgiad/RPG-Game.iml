public class Spell extends Hero {
    //Variables
    String spell_name;
    double spell_price, magic_power_required, minDamage, maxDamage;
    int spell_min_level;
    double[] damageRange = {minDamage, maxDamage};

    //Functions
    //Constructor
    public Spell(String living_name, int living_level, double healthPower, double magicPower, double strength,
                 double dexterity, double agility, double money, double experience, String spell_name, double spell_price,
                 double magic_power_required, int spell_min_level, double minDamage, double maxDamage, double[] damageRange) {

        super(living_name, living_level, healthPower, magicPower, strength, dexterity, agility, money, experience);
        this.spell_name = spell_name;
        this.spell_price = spell_price;
        this.magic_power_required = magic_power_required;
        this.spell_min_level = spell_min_level;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.damageRange = damageRange;
    }

    //Remove magic energy needed to cast the spell
    public boolean consumed_magicPower_after_use(Hero caster){
        if (caster.getMagicPower() >= magic_power_required) {
            caster.setMagicPower(caster.getMagicPower() - magic_power_required);
            System.out.println("Magic Power reduced by " + magic_power_required);
            return true;
        }
        else {
            System.out.println("There is not enough Magic Power for the Spell!");
            return false;
        }
    }

    public double calculate_damage_dealt(Hero caster, Monster target) {
        double casterMagicPower = caster.getMagicPower();
        double targetMagicDefense = target.getDefense();
        double casterDexterity =  caster.getDexterity();

        double damage = (casterMagicPower * casterDexterity) - targetMagicDefense; // Dexterity affects damage

        if (damage < minDamage) {
            damage = minDamage;
        } else if (damage > maxDamage) {
            damage = maxDamage;
        }
        return damage;
    }

    //Getters & Setters
    public String getSpell_name() {
        return spell_name;
    }

    public void setSpell_name(String spell_name) {
        this.spell_name = spell_name;
    }

    public double getSpell_price() {
        return spell_price;
    }

    public void setSpell_price(double spell_price) {
        this.spell_price = spell_price;
    }

    public double getMagic_energy_required() {
        return magic_power_required;
    }

    public void setMagic_energy_required(double magic_energy_required) {
        this.magic_power_required = magic_energy_required;
    }

    public double getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(double minDamage) {
        this.minDamage = minDamage;
    }

    public double getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(double maxDamage) {
        this.maxDamage = maxDamage;
    }

    public int getSpell_min_level() {
        return spell_min_level;
    }

    public void setSpell_min_level(int spell_min_level) {
        this.spell_min_level = spell_min_level;
    }

    public double[] getDamageRange() {
        return damageRange;
    }

    public void setDamageRange(double[] damageRange) {
        this.damageRange = damageRange;
    }
}