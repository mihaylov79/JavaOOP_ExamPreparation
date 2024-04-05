package bank.entities.client;

import bank.common.ExceptionMessages;

public abstract class BaseClient implements Client{

    private String name;
    private String ID;
    private int interest;
    private double income;

    public BaseClient(String name, String ID, int interest, double income) {
        this.setName(name);
        this.setID(ID);
        this.interest = interest;
        this.setIncome(income);
    }

    protected void setInterest(int interest) {
        this.interest = interest;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        if (null == name || name.trim().isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.CLIENT_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setID(String ID) {
        if (null == ID || ID.trim().isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.CLIENT_ID_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.ID = ID;
    }

    private void setIncome(double income) {
        if(income <= 0){
            throw new IllegalArgumentException(ExceptionMessages.CLIENT_INCOME_CANNOT_BE_BELOW_OR_EQUAL_TO_ZERO);
        }
        this.income = income;
    }

    @Override
    public int getInterest() {
        return this.interest;
    }

    @Override
    public double getIncome() {
        return this.income;
    }


}
