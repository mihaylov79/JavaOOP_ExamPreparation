package robotService.repositories;

import robotService.entities.supplements.Supplement;

import java.util.Collection;

public class SupplementRepository implements Repository{

    private Collection<Supplement>supplements;
    @Override
    public void addSupplement(Supplement supplement) {
        this.supplements.add(supplement);

    }

    @Override
    public boolean removeSupplement(Supplement supplement) {
        return supplements.remove(supplement);
    }

    @Override
    public Supplement findFirst(String type) {
        return supplements.stream().
                filter(s-> s.getClass().getSimpleName().equals(type))
                .findFirst().orElse(null);
    }
}
