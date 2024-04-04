package climbers.models.climbing;

import climbers.models.climber.Climber;
import climbers.models.mountain.Mountain;

import java.util.Collection;

public class ClimbingImpl implements Climbing{
    @Override
    public void conqueringPeaks(Mountain mountain, Collection<Climber> climbers) {

        Collection<String>peakList = mountain.getPeaksList();

        climbers.forEach(climber -> {
            while (climber.canClimb() && peakList.iterator().hasNext()){
                climber.climb();

                String peak = peakList.iterator().next();
                climber.getRoster().getPeaks().add(peak);
                peakList.remove(peak);
            }
        });


    }
}
