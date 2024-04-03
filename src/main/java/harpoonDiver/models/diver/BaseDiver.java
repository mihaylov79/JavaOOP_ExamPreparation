package harpoonDiver.models.diver;

import harpoonDiver.common.ConstantMessages;
import harpoonDiver.common.ExceptionMessages;
import harpoonDiver.models.seaCatch.BaseSeaCatch;
import harpoonDiver.models.seaCatch.SeaCatch;

public abstract class BaseDiver implements Diver {

    private String name;
    private double oxygen;
    private SeaCatch seaCatch;

    public BaseDiver(String name, double oxygen) {
        this.setName(name);
        this.setOxygen(oxygen);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getOxygen() {
        return this.oxygen;
    }

    @Override
    public boolean canDive() {
        return this.oxygen > 0;
    }

    @Override
    public SeaCatch getSeaCatch() {

        if (null == seaCatch){
            this.seaCatch = new BaseSeaCatch();
        }
        return this.seaCatch;
    }

    @Override
    public void shoot() {
        this.oxygen = Math.max(0,this.oxygen - 30);


    }

    private void setName(String name) {

        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.DIVER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setOxygen(double oxygen) {
        if (oxygen < 0){
            throw new IllegalArgumentException(ExceptionMessages.DIVER_OXYGEN_LESS_THAN_ZERO);
        }
        this.oxygen = oxygen;
    }

    @Override
    public String toString() {
        String format = ConstantMessages.FINAL_DIVER_NAME +
                System.lineSeparator() +
                ConstantMessages.FINAL_DIVER_OXYGEN +
                System.lineSeparator() +
                ConstantMessages.FINAL_DIVER_CATCH;
        return String.format(format,
                this.getName(),
                this.getOxygen(),
                this.getSeaCatch().getSeaCreatures().isEmpty() ? "None" :
                        String.join(ConstantMessages.FINAL_DIVER_CATCH_DELIMITER,
                                this.getSeaCatch().getSeaCreatures()));
    }
}
