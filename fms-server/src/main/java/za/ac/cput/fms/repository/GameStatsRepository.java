package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.info.GameStats;
import za.ac.cput.fms.domain.info.TeamStats;

import java.util.List;
import java.util.Set;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, String> {
    GameStats findGameStatsByFixtureIdAndTeamId(String fixtureId, String teamId);
}