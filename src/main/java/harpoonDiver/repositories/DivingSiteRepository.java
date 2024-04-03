package harpoonDiver.repositories;

import harpoonDiver.models.divingSite.DivingSite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DivingSiteRepository implements Repository<DivingSite> {

    private Collection<DivingSite> sites;

    public DivingSiteRepository() {
        this.sites = new ArrayList<>();
    }

    @Override
    public Collection<DivingSite> getCollection() {
        return Collections.unmodifiableCollection(this.sites);
    }

    @Override
    public void add(DivingSite entity) {
        sites.add(entity);
    }

    @Override
    public boolean remove(DivingSite entity) {
        return sites.remove(entity);
    }

    @Override
    public DivingSite byName(String name) {
        return sites.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst().orElse(null);
    }
}
