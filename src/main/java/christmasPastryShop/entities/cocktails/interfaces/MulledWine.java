package christmasPastryShop.entities.cocktails.interfaces;

public class MulledWine extends BaseCocktail{

    public static final double INITIAL_COCKTAIL_PRICE = 3.50;

    public MulledWine(String name, int size, String brand) {
        super(name, INITIAL_COCKTAIL_PRICE, size, brand);
    }
}
