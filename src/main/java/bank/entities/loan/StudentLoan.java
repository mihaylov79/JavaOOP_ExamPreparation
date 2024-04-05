package bank.entities.loan;

public class StudentLoan extends BaseLoan{

    public static final int CLASS_INTEREST_RATE = 1;
    public static final int CLASS_AMOUNT = 10_000;
    public StudentLoan() {
        super(CLASS_INTEREST_RATE, CLASS_AMOUNT);
    }
}
