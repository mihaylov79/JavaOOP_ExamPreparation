package climbers.models.mountain;

import climbers.common.ExceptionMessages;

import java.util.ArrayList;
import java.util.Collection;

public class MountainImpl implements Mountain{

    private String name;
    private final Collection<String>peakList;

    public MountainImpl(String name) {
        this.setName(name);
        this.peakList = new ArrayList<>();
    }

    private void setName(String name) {
        if (null == name || name.trim().isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.MOUNTAIN_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<String> getPeaksList() {
        return this.peakList;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
