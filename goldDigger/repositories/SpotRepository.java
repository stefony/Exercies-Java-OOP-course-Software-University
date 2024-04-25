package goldDigger.repositories;

import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.spot.Spot;

import java.util.*;

public class SpotRepository implements Repository<Spot> {

    private  Map<String, Spot> spots;

    public SpotRepository() {
        this.spots = new LinkedHashMap<>();
    }


    @Override
    public void add(Spot spot) {
        this.spots.putIfAbsent(spot.getName(),spot);
    }
@Override
    public boolean remove(Spot spot) {
        return spots.remove(spot.getName()) != null;
    }
@Override
    public Spot byName(String name) {
        return this.spots.get(name);
    }
@Override
    public Collection<Spot> getCollection() {
        return Collections.unmodifiableCollection(spots.values());
    }


}
