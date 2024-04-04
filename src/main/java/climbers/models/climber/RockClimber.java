package climbers.models.climber;

public class RockClimber extends BaseClimber{

    public static final double INITIAL_STRENGTH = 120;
    public static final double CLASS_STRENGTH_DECREASING = 60;

    public RockClimber(String name) {
        super(name, INITIAL_STRENGTH);
    }

    @Override
    public void climb() {
        this.setStrength(this.getStrength() - CLASS_STRENGTH_DECREASING );

        if (this.getStrength() < 0){
            this.setStrength(0);
        }

    }
}
