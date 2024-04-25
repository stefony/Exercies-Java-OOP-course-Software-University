package goldDigger.core;

import goldDigger.models.discoverer.Anthropologist;
import goldDigger.models.discoverer.Archaeologist;
import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.discoverer.Geologist;
import goldDigger.models.operation.Operation;
import goldDigger.models.operation.OperationImpl;
import goldDigger.models.spot.Spot;
import goldDigger.models.spot.SpotImpl;
import goldDigger.repositories.DiscovererRepository;
import goldDigger.repositories.SpotRepository;

import java.util.*;

import static goldDigger.common.ConstantMessages.*;
import static goldDigger.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private DiscovererRepository discovererRepository;
    private SpotRepository spotRepository;
    private Operation operation;

    public ControllerImpl() {
        discovererRepository = new DiscovererRepository();
        spotRepository = new SpotRepository();
        operation = new OperationImpl();
    }

    public String addDiscoverer(String kind, String name) {
        if (kind.equals("Archaeologist")) {
            Archaeologist archaeologist = new Archaeologist(name);
            discovererRepository.add(archaeologist);
            return String.format("Added Archaeologist: %s.", name);
        } else if (kind.equals("Anthropologist")) {
            Anthropologist anthropologist = new Anthropologist(name);
            discovererRepository.add(anthropologist);
            return String.format("Added Anthropologist: %s.", name);
        } else if (kind.equals("Geologist")) {
            Geologist geologist = new Geologist(name);
            discovererRepository.add(geologist);
            return String.format("Added Geologist: %s.", name);
        } else {
            throw new IllegalArgumentException("DISCOVERER_INVALID_KIND");
        }

    }

    public String addSpot(String spotName, String... exhibits) {
        SpotImpl spot = new SpotImpl(spotName);
        for (String exhibit : exhibits) {
            spot.getExhibits().add(exhibit);
        }
        spotRepository.add(spot);
        return String.format(SPOT_ADDED, spotName);
    }

    public String excludeDiscoverer(String name) {
        Discoverer discoverer = discovererRepository.byName(name);
        if (discoverer == null) {
            throw new IllegalArgumentException(String.format(DISCOVERER_DOES_NOT_EXIST,name));
        }
        discovererRepository.remove(discoverer);
        return String.format(DISCOVERER_EXCLUDE, name);
    }

    public String inspectSpot(String spotName) {
        Spot spot = spotRepository.byName(spotName);
        if (spot == null) {
            throw new IllegalArgumentException(String.format("Spot %s doesn't exist.", spotName));
        }

        List<Discoverer> suitableDiscoverers = new ArrayList<>();
        for (Discoverer discoverer : discovererRepository.getCollection()) {
            if (discoverer.canDig() && discoverer.getEnergy() > 45) {
                suitableDiscoverers.add(discoverer);
            }
        }

        if (suitableDiscoverers.isEmpty()) {
            throw new IllegalArgumentException(String.format(SPOT_DISCOVERERS_DOES_NOT_EXISTS ));
        }

        operation.startOperation(spot, suitableDiscoverers);

        int excludedDiscoverers = 0;
        for (Discoverer discoverer : suitableDiscoverers) {
            if (!discoverer.canDig()) {
                excludedDiscoverers++;
            }
        }

        return String.format(INSPECT_SPOT,spotName, excludedDiscoverers);
    }

    public String getStatistics() {
        int inspectedSpotCount = 0;
        StringBuilder builder = new StringBuilder();
        builder.append("Information for the discoverers:%n");
        for (Discoverer discoverer : discovererRepository.getCollection()) {
            builder.append(String.format("Name: %s%nEnergy: %.2f%nMuseum exhibits: ", discoverer.getName(), discoverer.getEnergy()));
            if (discoverer.getMuseum().getExhibits().isEmpty()) {
                builder.append("None\n");
            } else {
                builder.append(String.join(", ", discoverer.getMuseum().getExhibits())).append("%n");
            }
        }
        for (Spot spot : spotRepository.getCollection()) {
            if (spot.getExhibits().isEmpty()) {
                inspectedSpotCount++;
            }
        }
        return String.format("%d spots were inspected.%n%s", inspectedSpotCount, builder.toString());
    }
}
