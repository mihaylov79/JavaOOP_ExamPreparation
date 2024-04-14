package christmasPastryShop.repositories.interfaces;

import christmasPastryShop.entities.delicacies.interfaces.Delicacy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DelicacyRepositoryImpl implements DelicacyRepository<Delicacy> {

    private Collection<Delicacy> delicacies;
    @Override
    public Delicacy getByName(String name) {
        return delicacies.stream()
                        .filter(delicacy -> delicacy.getName().equals(name))
                        .findFirst()
                        .orElse(null);
    }

    @Override
    public Collection<Delicacy> getAll() {
        return Collections.unmodifiableCollection(delicacies);
    }

    @Override
    public void add(Delicacy delicacy) {
        this.delicacies.add(delicacy);

    }
}
