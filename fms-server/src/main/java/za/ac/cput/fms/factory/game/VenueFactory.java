package za.ac.cput.fms.factory.game;

import za.ac.cput.fms.domain.game.Venue;
import za.ac.cput.fms.util.Helper;

public class VenueFactory {
    public static Venue newVenue(String vName, int capacity){
        String id = "vn-"+Helper.generateId();

        return new Venue.Builder()
                .setId(id)
                .setVenueName(vName)
                .setCapacity(capacity)
                .build();
    }
}
