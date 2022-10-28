package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.game.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, String> {
}