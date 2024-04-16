package christmasPastryShop.core;

import christmasPastryShop.common.ExceptionMessages;
import christmasPastryShop.common.OutputMessages;
import christmasPastryShop.core.interfaces.Controller;
import christmasPastryShop.entities.booths.interfaces.OpenBooth;
import christmasPastryShop.entities.booths.interfaces.PrivateBooth;
import christmasPastryShop.entities.cocktails.interfaces.Hibernation;
import christmasPastryShop.entities.cocktails.interfaces.MulledWine;
import christmasPastryShop.entities.delicacies.interfaces.Delicacy;
import christmasPastryShop.entities.cocktails.interfaces.Cocktail;
import christmasPastryShop.entities.booths.interfaces.Booth;
import christmasPastryShop.entities.delicacies.interfaces.Gingerbread;
import christmasPastryShop.entities.delicacies.interfaces.Stolen;
import christmasPastryShop.repositories.interfaces.BoothRepository;
import christmasPastryShop.repositories.interfaces.CocktailRepository;
import christmasPastryShop.repositories.interfaces.DelicacyRepository;


public class ControllerImpl implements Controller {

    private DelicacyRepository<Delicacy> delicacyRepository;
    private CocktailRepository<Cocktail> cocktailRepository;
    private BoothRepository<Booth> boothRepository;
    private double totalIncome;

    public ControllerImpl(DelicacyRepository<Delicacy> delicacyRepository,
                          CocktailRepository<Cocktail> cocktailRepository,
                          BoothRepository<Booth> boothRepository) {
        this.delicacyRepository = delicacyRepository;
        this.cocktailRepository = cocktailRepository;
        this.boothRepository = boothRepository;
        this.totalIncome = 0;



    }


    @Override
    public String addDelicacy(String type, String name, double price) {
        delicacyRepository.getAll().forEach(d -> {
            if (d.getName().equals(name)){
                throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_OR_DRINK_EXIST,type,name));
            }
        } );

        Delicacy delicacy = null;
        switch (type){
            case "Gingerbread":
                delicacy = new Gingerbread(name,price);
                break;
            case "Stolen":
                delicacy = new Stolen(name , price);
                break;
        }


        delicacyRepository.add(delicacy);

        return String.format(OutputMessages.DELICACY_ADDED,name,type);
    }

    @Override
    public String addCocktail(String type, String name, int size, String brand) {
        cocktailRepository.getAll().forEach(c -> {
            if (c.getName().equals(name)){
                throw new IllegalArgumentException(String.format(ExceptionMessages.FOOD_OR_DRINK_EXIST,type,name));
            }
        });

        Cocktail cocktail = null;
        switch (type){
            case "Hibernation":
                cocktail = new Hibernation(name,size,brand);
                break;
            case "MulledWine":
                cocktail = new MulledWine(name, size, brand);
        }


        cocktailRepository.add(cocktail);

        return String.format(OutputMessages.COCKTAIL_ADDED,name,brand);
    }

    @Override
    public String addBooth(String type, int boothNumber, int capacity) {
        boothRepository.getAll().forEach(b -> {
            if (b.getBoothNumber() == boothNumber){
                throw  new IllegalArgumentException(String.format(ExceptionMessages.BOOTH_EXIST,boothNumber));
            }
        });

        Booth booth = null;

        switch (type){
            case "OpenBooth":
                booth = new OpenBooth(boothNumber,capacity);
                break;
            case "PrivateBooth":
                booth = new PrivateBooth(boothNumber,capacity);
                break;
        }



        boothRepository.add(booth);
        return String.format(OutputMessages.BOOTH_ADDED,boothNumber);
    }

    @Override
    public String reserveBooth(int numberOfPeople) {
        Booth freeBooth = boothRepository.getAll().stream()
                        .filter(booth -> booth.getCapacity() >= numberOfPeople
                        && !booth.isReserved())
                        .findFirst()
                        .orElse(null);
        if (freeBooth == null){
            return String.format(OutputMessages.RESERVATION_NOT_POSSIBLE,numberOfPeople);
        }
        freeBooth.reserve(numberOfPeople);
        return String.format(OutputMessages.BOOTH_RESERVED
                ,freeBooth.getBoothNumber(),numberOfPeople);
    }

    @Override
    public String leaveBooth(int boothNumber) {
        Booth booth = this.boothRepository.getByNumber(boothNumber);
        double bill = booth.getBill();
        this.totalIncome += bill;
        booth.clear();
        return String.format(OutputMessages.BILL,boothNumber,bill);
    }

    @Override
    public String getIncome() {

        return String.format(OutputMessages.TOTAL_INCOME,this.totalIncome);
    }
}
