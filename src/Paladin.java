public class Paladin extends Hero{
    //Variables
    double increased_paladin_strength, increased_paladin_dexterity;

    //Functions
    //Constructor
    public Paladin(String living_name, double increased_paladin_strength, double increased_paladin_dexterity) {
        super(living_name);
        this.increased_paladin_strength = increased_paladin_strength + strength; //Increased strength (Paladin)
        this.increased_paladin_dexterity = increased_paladin_dexterity + dexterity; //Increased dexterity (Paladin)
    }
}
