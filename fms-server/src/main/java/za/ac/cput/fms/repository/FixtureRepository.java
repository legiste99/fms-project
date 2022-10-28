package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.game.Fixture;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, String> {
}