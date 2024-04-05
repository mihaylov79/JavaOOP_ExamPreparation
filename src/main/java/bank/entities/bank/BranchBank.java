package bank.entities.bank;

public class BranchBank extends BaseBank{

    public static final int CLASS_DEFAULT_CAPACITY = 25;
    public BranchBank(String name) {
        super(name, CLASS_DEFAULT_CAPACITY);
    }
}
