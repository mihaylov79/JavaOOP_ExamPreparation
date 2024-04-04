package climbers.core;

import climbers.common.ConstantMessages;
import climbers.common.ExceptionMessages;
import climbers.models.climber.Climber;
import climbers.models.climber.RockClimber;
import climbers.models.climber.WallClimber;
import climbers.models.climbing.ClimbingImpl;
import climbers.models.mountain.Mountain;
import climbers.models.mountain.MountainImpl;
import climbers.repositories.ClimberRepository;
import climbers.repositories.MountainRepository;
import climbers.repositories.Repository;

import java.util.Collection;
import java.util.List;

public class ControllerImpl implements Controller{

    private final Repository<Climber> climberRepository;
    private final Repository<Mountain> mountainRepository;
    private int mountainsCount;

    public ControllerImpl() {
        this.climberRepository = new ClimberRepository();
        this.mountainRepository = new MountainRepository();
    }

    @Override
    public String addClimber(String type, String climberName) {

        Climber climber;
        switch (type){
            case "RockClimber":
                climber = new RockClimber(climberName);
                break;
            case "WallClimber":
                climber = new WallClimber(climberName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.CLIMBER_INVALID_TYPE);
        }
        climberRepository.add(climber);

        return String.format(ConstantMessages.CLIMBER_ADDED,type,climberName);
    }

    @Override
    public String addMountain(String mountainName, String... peaks) {
        MountainImpl mountain = new MountainImpl(mountainName);
        mountain.getPeaksList().addAll(List.of(peaks));
        mountainRepository.add(mountain);
        return String.format(ConstantMessages.MOUNTAIN_ADDED,mountainName);
    }

    @Override
    public String removeClimber(String climberName) {

        if (climberRepository.byName(climberName) == null){
            throw new IllegalArgumentException(
                    String.format(ExceptionMessages.CLIMBER_DOES_NOT_EXIST,climberName));
        }
        climberRepository.remove(climberRepository.byName(climberName));
        return String.format(ConstantMessages.CLIMBER_REMOVE,climberName);
    }

    @Override
    public String startClimbing(String mountainName) {
        Collection<Climber> climbers = climberRepository.getCollection();
        if (climbers == null){
            throw new IllegalArgumentException(ExceptionMessages.THERE_ARE_NO_CLIMBERS);
        }
        Mountain mountain = mountainRepository.byName(mountainName);
        ClimbingImpl climbing = new ClimbingImpl();
        climbing.conqueringPeaks(mountain,climbers);
        long removedClimbers = climbers.stream().filter(climber -> climber.getStrength() == 0).count();
        this.mountainsCount++;

        return String.format(ConstantMessages.PEAK_CLIMBING,mountainName,removedClimbers);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        Collection<Climber> climbers = climberRepository.getCollection();

        sb.append(String.format(ConstantMessages.FINAL_MOUNTAIN_COUNT,this.mountainsCount))
                .append(System.lineSeparator())
                .append(ConstantMessages.FINAL_CLIMBERS_STATISTICS)
                .append(System.lineSeparator());

        climbers.forEach(climber -> {

                    sb.append(String.format(ConstantMessages.FINAL_CLIMBER_NAME,climber.getName()))
                    .append(System.lineSeparator())
                    .append(String.format(ConstantMessages.FINAL_CLIMBER_STRENGTH,climber.getStrength()))
                    .append(System.lineSeparator());

            Collection<String>peaks = climber.getRoster().getPeaks();
            String conqueredPeaks = peaks.isEmpty() ? "None" : String.join(ConstantMessages.FINAL_CLIMBER_FINDINGS_DELIMITER,peaks);

            sb.append(String.format(ConstantMessages.FINAL_CLIMBER_PEAKS,conqueredPeaks)).append(System.lineSeparator());

        });
        return sb.toString().trim();
    }
}
