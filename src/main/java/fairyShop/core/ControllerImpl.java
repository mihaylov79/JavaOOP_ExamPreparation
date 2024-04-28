package fairyShop.core;

import fairyShop.common.ConstantMessages;
import fairyShop.common.ExceptionMessages;
import fairyShop.models.*;
import fairyShop.repositories.HelperRepository;
import fairyShop.repositories.PresentRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
HelperRepository helperRepository = new HelperRepository();
PresentRepository presentRepository = new PresentRepository();
private Shop shop = new ShopImpl();
private int count;



    @Override
    public String addHelper(String type, String helperName) {
        Helper helper;
        switch (type){

            case "Happy":
              helper = new Happy(helperName);
              break;
            case "Sleepy":
                helper = new Sleepy(helperName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.HELPER_TYPE_DOESNT_EXIST);
        }

        helperRepository.add(helper);
        return String.format(ConstantMessages.ADDED_HELPER,type,helperName);
    }

    @Override
    public String addInstrumentToHelper(String helperName, int power) {

        Helper helper = helperRepository.getModels().stream().
                filter(h -> h.getName().equals(helperName))
                .findFirst().orElse(null);

        if(helper == null){
            throw new IllegalArgumentException(ExceptionMessages.HELPER_DOESNT_EXIST);
        }

        Instrument instrument = new InstrumentImpl(power);

        helper.addInstrument(instrument);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_INSTRUMENT_TO_HELPER,power,helperName);
    }

    @Override
    public String addPresent(String presentName, int energyRequired) {
        Present present = new PresentImpl(presentName,energyRequired);

        presentRepository.add(present);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PRESENT, presentName);
    }

    @Override
    public String craftPresent(String presentName) {
        Collection<Helper>helpersList = helperRepository.getModels().stream().
                filter(helper -> helper.getEnergy() > 50)
                .collect(Collectors.toCollection(ArrayList::new));

        if(helpersList.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.NO_HELPER_READY);
        }

        count = 0;
        Present present = presentRepository.findByName(presentName);
        helpersList.forEach(helper -> {

            while (!present.isDone()){

            shop.craft(present,helper);
            long brokenCount = helper.getInstruments().stream()
                    .filter(Instrument::isBroken).count();
            count += (int) brokenCount;
            }

        });

        if (present.isDone()){
            return String.format(ConstantMessages.PRESENT_DONE,presentName, "done") +
                    String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS,count);
        }

        return String.format(ConstantMessages.PRESENT_DONE,presentName, "not done") +
                String.format(ConstantMessages.COUNT_BROKEN_INSTRUMENTS,count);
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();
        long presentCount = presentRepository.getModels().stream().filter(Present::isDone).count();
        sb.append(String.format("%d presents are done!",presentCount)).append(System.lineSeparator()).append("Helpers info:").append(System.lineSeparator());
        helperRepository.getModels().stream().map(h -> sb.append(String.format("Name: %s",h.getName()))
                .append(System.lineSeparator())
                .append(String.format("Energy: %d",h.getEnergy()))
                .append(System.lineSeparator())
                .append(String.format("Instruments: %s not broken left%n",h.getInstruments().stream().filter(instrument -> !instrument.isBroken()).count()))).collect(Collectors.joining(""));
        sb.append(System.lineSeparator());

        return sb.toString().trim();
    }
}
