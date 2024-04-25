package goldDigger.models.discoverer;

public class Archaeologist extends BaseDiscoverer{

    private static final double INITIAL_UNITS_OF_ENERGY= 40.00;
    public Archaeologist(String name) {
        super(name, INITIAL_UNITS_OF_ENERGY);
    }
}
