package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.game.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {

}