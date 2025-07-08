public class Living {
    //Variables
    String living_name;
    int living_level;
    double healthPower;
    boolean isPassedOut;

    //Functions
    //Constructor
    public Living(String living_name){
        this.living_name = living_name;
        this.living_level = 1;
        this.healthPower = 100;
        this.isPassedOut = false;
    }
    //Check if Living has passed out
    public void passed_out(){
        if (healthPower <= 0){
            isPassedOut = true;
            System.out.println(living_name + " has passed out...");
        }
    }
}