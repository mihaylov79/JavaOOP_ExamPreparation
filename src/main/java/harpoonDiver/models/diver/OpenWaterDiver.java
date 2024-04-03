package harpoonDiver.models.diver;

public class OpenWaterDiver extends BaseDiver{
    private static final double DEFAULT_OXYGEN_INITIAL_UNITS = 30;
    public OpenWaterDiver(String name) {
        super(name, DEFAULT_OXYGEN_INITIAL_UNITS);
    }
}
