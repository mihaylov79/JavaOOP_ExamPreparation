package robotService.entities.robot;

public class MaleRobot extends BaseRobot{

    public static final int INITIAL_KILOGRAMS = 9;
    public static final int CLASS_KILOGRAMS_INCREASING = 3;
    public MaleRobot(String name, String kind, double price) {
        super(name, kind, INITIAL_KILOGRAMS, price);
    }

    @Override
    public void eating() {

        this.setKilograms(this.getKilograms() + CLASS_KILOGRAMS_INCREASING);

    }
}
