package climbers.repositories;

import climbers.models.mountain.Mountain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MountainRepository implements Repository<Mountain> {

    private final Collection<Mountain> mountains;

    public MountainRepository() {
        this.mountains = new ArrayList<>();
    }

    @Override
    public Collection<Mountain> getCollection() {
        return Collections.unmodifiableCollection(this.mountains);
    }

    @Override
    public void add(Mountain entity) {
        mountains.add(entity);

    }

    @Override
    public boolean remove(Mountain entity) {
        return mountains.remove(entity);
    }

    @Override
    public Mountain byName(String name) {
        return mountains.stream()
                .filter(mountain -> mountain.getName().equals(name))
                .findFirst().orElse(null);
    }
}
