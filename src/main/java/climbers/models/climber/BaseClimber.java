package climbers.models.climber;

import climbers.common.ExceptionMessages;
import climbers.models.roster.Roster;
import climbers.models.roster.RosterImpl;

public abstract class BaseClimber implements Climber{

    private String name;
    private double strength;
    private final Roster roster;

    public BaseClimber(String name, double strength) {
        this.setName(name);
        this.setStrength(strength);
        this.roster = new RosterImpl();


    }

    private void setName(String name) {
        if (null == name || name.trim().isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.CLIMBER_NAME_NULL_OR_EMPTY);

        }
        this.name = name;
    }

    protected void setStrength(double strength) {
        if (strength < 0){
            throw new IllegalArgumentException(ExceptionMessages.CLIMBER_STRENGTH_LESS_THAN_ZERO);
        }
        this.strength = strength;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getStrength() {
        return this.strength;
    }

    @Override
    public boolean canClimb() {
        return strength > 0;
    }

    @Override
    public Roster getRoster() {
        return this.roster;
    }

}
