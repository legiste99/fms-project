package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.info.PlayerStats;

import java.util.Set;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, String> {
    Set<PlayerStats> getPlayerStatsByFixtureIdAndTeamId(String fixtureId, String teamId);
    PlayerStats getPlayerStatsByFixtureIdAndPlayerId(String fixtureId, String playerId);
}