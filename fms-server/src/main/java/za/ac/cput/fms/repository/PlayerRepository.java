package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.personal.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {
}