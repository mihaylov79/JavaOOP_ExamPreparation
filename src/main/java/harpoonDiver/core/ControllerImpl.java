package harpoonDiver.core;

import harpoonDiver.common.ConstantMessages;
import harpoonDiver.common.ExceptionMessages;
import harpoonDiver.models.diver.DeepWaterDiver;
import harpoonDiver.models.diver.Diver;
import harpoonDiver.models.diver.OpenWaterDiver;
import harpoonDiver.models.diver.WreckDiver;
import harpoonDiver.models.diving.Diving;
import harpoonDiver.models.diving.DivingImpl;
import harpoonDiver.models.divingSite.DivingSite;
import harpoonDiver.models.divingSite.DivingSiteImpl;
import harpoonDiver.repositories.DiverRepository;
import harpoonDiver.repositories.DivingSiteRepository;
import harpoonDiver.repositories.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller{


    private Repository<Diver> diverRepository;
    private Repository<DivingSite> divingSiteRepository;
    private int count;

    public ControllerImpl() {
        this.diverRepository = new DiverRepository();
        this.divingSiteRepository = new DivingSiteRepository();
    }

    @Override
    public String addDiver(String kind, String diverName) {
        Diver diver;

        switch (kind){
            case "OpenWaterDiver":
                diver = new OpenWaterDiver(diverName);
                break;
            case "DeepWaterDiver":
                diver = new DeepWaterDiver(diverName);
                break;
            case "WreckDiver":
                diver = new WreckDiver(diverName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.DIVER_INVALID_KIND);
        }
        this.diverRepository.add(diver);
        return String.format(ConstantMessages.DIVER_ADDED, kind,diverName);
    }

    @Override
    public String addDivingSite(String siteName, String... seaCreatures) {
        DivingSite site = new DivingSiteImpl(siteName);
        site.getSeaCreatures().addAll(Arrays.asList(seaCreatures));
        this.divingSiteRepository.add(site);

        return String.format(ConstantMessages.DIVING_SITE_ADDED,siteName);
    }

    @Override
    public String removeDiver(String diverName) {
        Diver diver = diverRepository.byName(diverName);
        if (diver == null){
            throw new IllegalArgumentException (String.format(ExceptionMessages.DIVER_DOES_NOT_EXIST, diverName));
        }
        diverRepository.remove(diver);
        return String.format(ConstantMessages.DIVER_REMOVE, diverName);
    }

    @Override
    public String startDiving(String siteName) {
        List<Diver> divers= this.diverRepository.getCollection().stream()
                .filter((d-> d.getOxygen() > 30)).collect(Collectors.toList());

        if (divers.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.SITE_DIVERS_DOES_NOT_EXISTS);
        }
        DivingSite site = this.divingSiteRepository.byName(siteName);
        Diving diving = new DivingImpl();
        diving.searching(site,divers);
        long removed = divers.stream().filter(d-> d.getOxygen() == 0).count();
        this.count++;

        return String.format(ConstantMessages.SITE_DIVING,siteName,removed);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(ConstantMessages.FINAL_DIVING_SITES,count));
        sb.append(System.lineSeparator());
        sb.append(ConstantMessages.FINAL_DIVERS_STATISTICS);
        sb.append(System.lineSeparator());
//        this.diverRepository.getCollection()
//             .forEach(d ->
//             sb.append(String.format(ConstantMessages.FINAL_DIVER_NAME,d.getName()))
//             .append(System.lineSeparator())
//             .append(String.format(ConstantMessages.FINAL_DIVER_OXYGEN,d.getOxygen()))
//             .append(System.lineSeparator())
//              .append(String.format(ConstantMessages.FINAL_DIVER_CATCH,d.getSeaCatch().getSeaCreatures().isEmpty() ? "None": d.getSeaCatch().getSeaCreatures()))
//              .append(System.lineSeparator()));
        this.diverRepository.getCollection()
                .forEach(d -> sb.append(d).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
