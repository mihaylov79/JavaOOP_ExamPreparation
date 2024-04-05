package bank.entities.bank;

public class CentralBank extends BaseBank{

    public static final int CLASS_DEFAULT_CAPACITY = 50;
    public CentralBank(String name) {
        super(name, CLASS_DEFAULT_CAPACITY);
    }
}
