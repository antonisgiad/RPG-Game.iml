package living.monster;

public class Dragon extends Monster {
    //Variables
    private double increasedDamageRange;

    //Functions

    //Constructor
    public Dragon(String livingName, double increasedDamageRange) {
        super(livingName);
        this.increasedDamageRange = increasedDamageRange + getMonsterDamageRange();
    }

    //Getters & Setters
    public double getIncreasedDamageRange() {
        return increasedDamageRange;
    }

    public void setIncreasedDamageRange(double increasedDamageRange) {
        this.increasedDamageRange = increasedDamageRange;
    }
}