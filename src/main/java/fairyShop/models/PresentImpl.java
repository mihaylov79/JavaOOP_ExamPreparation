package fairyShop.models;

import fairyShop.common.ExceptionMessages;

public class PresentImpl implements Present{

    private String name;
    private int energyRequired;

    public PresentImpl(String name, int energyRequired) {
        this.setName(name);
        this.setEnergyRequired(energyRequired);
    }

    private void setName(String name) {

        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.PRESENT_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setEnergyRequired(int energyRequired) {
        if (energyRequired < 0){
            throw new IllegalArgumentException(ExceptionMessages.PRESENT_ENERGY_LESS_THAN_ZERO);
        }
        this.energyRequired = energyRequired;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getEnergyRequired() {
        return this.energyRequired;
    }

    @Override
    public boolean isDone() {
        return this.energyRequired == 0;
    }

    @Override
    public void getCrafted() {

        if (this.energyRequired  - 10 < 0 ){
            energyRequired = 0;
        }else {
            this.energyRequired -= 10;
        }

    }
}
