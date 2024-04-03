package harpoonDiver.models.diver;

public class WreckDiver extends BaseDiver{

    private static final double DEFAULT_OXYGEN_INITIAL_UNITS = 150;
    public WreckDiver(String name) {
        super(name, DEFAULT_OXYGEN_INITIAL_UNITS);
    }
}
