package christmasPastryShop.entities.cocktails.interfaces;

public class Hibernation extends BaseCocktail{

    public static final double INITIAL_COCKTAIL_PRICE = 3.50;

    public Hibernation(String name, int size, String brand) {
        super(name, INITIAL_COCKTAIL_PRICE, size, brand);
    }
}
