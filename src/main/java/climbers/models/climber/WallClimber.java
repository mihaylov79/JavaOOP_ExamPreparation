package climbers.models.climber;

public class WallClimber extends BaseClimber{

    public static final double INITIAL_STRENGTH = 90;
    public static final double CLASS_STRENGTH_DECREASING = 30;

    public WallClimber(String name) {
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
