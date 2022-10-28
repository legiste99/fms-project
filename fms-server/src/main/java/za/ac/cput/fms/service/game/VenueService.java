package za.ac.cput.fms.service.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.game.Venue;
import za.ac.cput.fms.repository.VenueRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository repository;

    public Venue save(Venue venue){
        return repository.save(venue);
    }

    public List<Venue> getAllVenues(){
        List<Venue> venues = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(venues::add);
        return venues;
    }

    public List<Venue> getVenueById(String venueId){
        if (null != venueId){
            return  repository.findAllById(Collections.singleton(venueId));
        }
        else {
            return repository.findAll();
        }
    }

    public Venue updateVenue(String venueId, String name, int capacity){

        Venue venue = repository.findById(venueId).get();
        venue.setVenueName(name);
        venue.setCapacity(capacity);

        return repository.save(venue);

    }

    public void deleteVenueById(String venueId){
        repository.deleteById(venueId);
    }

}
