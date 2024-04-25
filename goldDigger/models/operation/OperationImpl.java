package goldDigger.models.operation;

import goldDigger.models.discoverer.Discoverer;
import goldDigger.models.spot.Spot;

import java.util.Collection;

public class OperationImpl implements Operation{

    @Override
    public void startOperation(Spot spot, Collection<Discoverer> discoverers) {


        Collection<String>exhibitsInSpot=spot.getExhibits();
        for (Discoverer discoverer: discoverers) {
            while (discoverer.canDig() && exhibitsInSpot.iterator().hasNext()){
                discoverer.dig();

                String currentExhibit = exhibitsInSpot.iterator().next();

                discoverer.getMuseum().getExhibits().add(currentExhibit);
            }
        }
    }
}
