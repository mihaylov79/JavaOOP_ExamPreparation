package christmasPastryShop.entities.booths.interfaces;

public class OpenBooth extends BaseBooth{

    private static final double INITIAL_PRICE_PER_PERSON = 2.50;
    public OpenBooth(int boothNumber, int capacity) {
        super(boothNumber, capacity, INITIAL_PRICE_PER_PERSON);
    }
}
