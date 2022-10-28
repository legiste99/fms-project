package za.ac.cput.fms.service.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.game.Fixture;
import za.ac.cput.fms.domain.game.Team;
import za.ac.cput.fms.domain.game.Tournament;
import za.ac.cput.fms.domain.info.GameStats;
import za.ac.cput.fms.domain.info.PlayerStats;
import za.ac.cput.fms.domain.personal.Player;
import za.ac.cput.fms.repository.*;

import java.util.Set;

@Service
public class PlayerStatsService {

    @Autowired
    private PlayerStatsRepository repository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private FixtureRepository fixtureRepository;
    @Autowired
    private GameStatsRepository gameStatsRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;


    public PlayerStats save(PlayerStats playerStats){
        return repository.save(playerStats);
    }

    // TODO: Update playerStats during a Fixture and reflect(Update) to GameState and TeamStats
    /*
    Possible Steps to complete this:

    1. get playerStat by id,
    2. update a single value...
    3.

    * */

    public void updatePlayerStatGoalPlusOneById(String tour, String fix, String game, String teamId, String playerId, String playerStatId){

        Tournament tournament = tournamentRepository.findById(tour).get();
        Fixture fixture = fixtureRepository.findById(fix).get();
        GameStats gameStats = gameStatsRepository.findById(game).get();
        Team team = teamRepository.findById(teamId).get();
        Player player = playerRepository.findById(playerId).get();
        PlayerStats playerStat = playerStatsRepository.findById(playerId).get();

    }

    public Set<PlayerStats> getFixtureTeamPlayerStats(String fixtureId, String teamId){
        return repository.getPlayerStatsByFixtureIdAndTeamId(fixtureId, teamId);
    }

}
