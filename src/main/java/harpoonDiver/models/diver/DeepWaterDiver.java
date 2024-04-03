package harpoonDiver.models.diver;

public class DeepWaterDiver extends BaseDiver{

    private static final double DEFAULT_OXYGEN_INITIAL_UNITS = 90;
    public DeepWaterDiver(String name) {
        super(name, DEFAULT_OXYGEN_INITIAL_UNITS);
    }
}
