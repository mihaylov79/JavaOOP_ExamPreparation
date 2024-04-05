package bank.entities.client;

public class Adult extends BaseClient{

    public static final int INITIAL_CLASS_INTEREST = 4;
    public static final int CLASS_INTEREST_INCREASEMENT = 2;

    public Adult(String name, String ID, double income) {
        super(name, ID, INITIAL_CLASS_INTEREST, income);
    }

    @Override
    public void increase() {
        this.setInterest(this.getInterest() + CLASS_INTEREST_INCREASEMENT);

    }
}
