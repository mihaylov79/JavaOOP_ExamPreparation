package robotService.entities.robot;

public class FemaleRobot extends BaseRobot{

    public static final int INITIAL_KILOGRAMS = 7;
    public static final int CLASS_KILOGRAMS_INCREASING = 1;
    public FemaleRobot(String name, String kind, double price) {
        super(name, kind, INITIAL_KILOGRAMS, price);
    }

    @Override
    public void eating() {

        this.setKilograms(this.getKilograms() + CLASS_KILOGRAMS_INCREASING);

    }
}
