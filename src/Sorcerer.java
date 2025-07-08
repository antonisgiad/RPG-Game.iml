public class Sorcerer extends Hero {
    //Variables
    double increased_sorcerer_dexterity, increased_sorcerer_agility;

    //Functions
    //Constructor
    public Sorcerer(String living_name, double increased_sorcerer_dexterity, double increased_sorcerer_agility) {
        super(living_name);
        this.increased_sorcerer_dexterity = increased_sorcerer_dexterity + dexterity; //Increased dexterity (Sorcerer)
        this.increased_sorcerer_agility = increased_sorcerer_agility + agility; //Increased agility (Sorcerer)
    }
}