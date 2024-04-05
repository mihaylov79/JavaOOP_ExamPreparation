package bank.entities.loan;

public class MortgageLoan extends BaseLoan{

    public static final int CLASS_INTEREST_RATE = 3;
    public static final int CLASS_AMOUNT = 50_000;
    public MortgageLoan() {
        super(CLASS_INTEREST_RATE, CLASS_AMOUNT);
    }
}
