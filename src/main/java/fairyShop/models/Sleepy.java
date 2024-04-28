package fairyShop.models;

public class Sleepy extends BaseHelper{

    public static int INITIAL_ENERGY = 50;

    public Sleepy(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void work() {
        if (this.getEnergy() - 15 < 0){
            setEnergy(0);
        }else {
            setEnergy(getEnergy() - 15);
        }




    }
}
