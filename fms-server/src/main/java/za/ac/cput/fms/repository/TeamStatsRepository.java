package za.ac.cput.fms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.fms.domain.info.TeamStats;

import java.util.List;
import java.util.Set;

@Repository
public interface TeamStatsRepository extends JpaRepository<TeamStats, String> {
    Set<TeamStats> findByTeamId(String teamId);
    TeamStats getTeamStatsByTeamIdAndTournamentId(String teamId, String tournamentId);
}