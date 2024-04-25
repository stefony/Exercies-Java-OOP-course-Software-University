package goldDigger.models.discoverer;

public class Geologist extends BaseDiscoverer{
    private static final double INITIAL_UNITS_OF_ENERGY= 100.00;
    public Geologist(String name) {

        super(name, INITIAL_UNITS_OF_ENERGY);
    }
}
