package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.game.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, String> {


}