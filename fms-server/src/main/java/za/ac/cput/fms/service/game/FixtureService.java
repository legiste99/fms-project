package za.ac.cput.fms.service.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.game.Fixture;
import za.ac.cput.fms.domain.game.Team;
import za.ac.cput.fms.domain.game.Venue;
import za.ac.cput.fms.domain.info.GameStats;
import za.ac.cput.fms.domain.info.PlayerStats;
import za.ac.cput.fms.domain.personal.Player;
import za.ac.cput.fms.domain.personal.Referee;
import za.ac.cput.fms.factory.info.GameStatFactory;
import za.ac.cput.fms.factory.info.PlayerStatFactory;
import za.ac.cput.fms.repository.*;
import za.ac.cput.fms.service.info.PlayerStatsService;

import java.util.*;

@Service
public class FixtureService {

    @Autowired
    private FixtureRepository repository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private RefereeRepository refereeRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
    @Autowired
    private GameStatsRepository gameStatsRepository;
    @Autowired
    PlayerStatsService playerStatsService = new PlayerStatsService();

    public Fixture save(Fixture fixture){

        return repository.save(fixture);
    }

    public List<Fixture> getFixtureById(String fixtureId){
         if (null != fixtureId){
             return repository.findAllById(Collections.singleton(fixtureId));
         }
         else {
             return repository.findAll();
         }
    }


    public void addPlayerStatToFixture(String fixtureId, String playerStatId){

        Set<PlayerStats> playerStatsSet = null;

        Fixture fixture = repository.findById(fixtureId).get();
        PlayerStats playerStats = playerStatsRepository.findById(playerStatId).get();

        playerStatsSet = fixture.getFixturePlayerStats();
        playerStatsSet.add(playerStats);

    }

    public void addPlayerStatToPlayerByTeam(String fixtureId ,String teamId){

        Team team = teamRepository.findById(teamId).get();

        Set<Player> players = team.getTeamPlayers();


        players.forEach(player -> {
            String id = player.getId();
            String playerName = player.getFirstName()+" "+player.getLastName();
            PlayerStats playerStats = PlayerStatFactory.newPlayerStat(id,playerName, fixtureId, teamId,  0,0,0);
            playerStatsService.save(playerStats);
            addPlayerStatToFixture(fixtureId, playerStats.getId());
            player.setPlayerPlayerStats(Collections.singleton(playerStats));
        });

    }

    public void addGameStatToTeam(String teamId, String fixtureId){

        Set<GameStats> gameStatsSet = null;
        String teamName = teamRepository.findById(teamId).get().getTeamName();

        GameStats gameStats = GameStatFactory.newGameStat(teamId, fixtureId, teamName,0,0,0,0,0,0);
        gameStatsRepository.save(gameStats);

        Fixture fixture = repository.findById(fixtureId).get();

        gameStatsSet = fixture.getFixtureGameStats();
        gameStatsSet.add(gameStats);

    }

    public Fixture assignVenueToFixture(String venueId, String fixtureId){
        List<Venue> venueList = null;

        Fixture fixture = repository.findById(fixtureId).get();
        Venue venue = venueRepository.findById(venueId).get();

        if (fixture.getFixtureVenue().contains(venue)){
            return null;
        }
        else if (fixture.getFixtureVenue().size() == 0){
            venueList = fixture.getFixtureVenue();
            venueList.add(venue);
            fixture.setFixtureVenue(venueList);
            return repository.save(fixture);
        }
        else {
            return null;
        }

    }

    public Fixture assignRefereeToFixture(String refereeId, String fixtureId){
        List<Referee> refereeList = null;

        Fixture fixture = repository.findById(fixtureId).get();
        Referee referee = refereeRepository.findById(refereeId).get();

        if (fixture.getFixtureReferee().contains(referee)){
            return null;
        }
        else if (fixture.getFixtureReferee().size() == 0){
            refereeList = fixture.getFixtureReferee();
            refereeList.add(referee);
            fixture.setFixtureReferee(refereeList);
            return repository.save(fixture);
        }
        else {
            return null;
        }
    }

    public Fixture addTeamToFixture(String fixtureId, String teamId){
        Set<Team> teamSet = null;

        Fixture fixture = repository.findById(fixtureId).get();
        Team team = teamRepository.findById(teamId).get();

        if (fixture.getFixtureTeams().contains(team)){
            return null;
        }
        else if(fixture.getFixtureTeams().size() ==0){

            teamSet = fixture.getFixtureTeams();
            teamSet.add(team);
            fixture.setFixtureTeams(teamSet);
            fixture.setTeamAId(team.getId());
            fixture.setTeamAName(team.getTeamName());

            addPlayerStatToPlayerByTeam(fixtureId ,teamId);
            addGameStatToTeam(teamId, fixtureId);

            fixture.setStatus(fixture.getFixtureTeams().size());

            return repository.save(fixture);
        }

        else if (fixture.getFixtureTeams().size() == 1){

            teamSet = fixture.getFixtureTeams();
            teamSet.add(team);
            fixture.setFixtureTeams(teamSet);
            fixture.setTeamBid(team.getId());
            fixture.setTeamBName(team.getTeamName());

            addPlayerStatToPlayerByTeam(fixtureId ,teamId);
            addGameStatToTeam(teamId, fixtureId);

            fixture.setStatus(fixture.getFixtureTeams().size());

            return repository.save(fixture);
        }
        else {
            return null;
        }

/**
        if (fixture.getFixtureTeams().size() >= 2){
            return null;
        }
        else {

            if (fixture.getFixtureTeams().contains(team)){
                return null;
            }
            else {
                teamSet = fixture.getFixtureTeams();
                teamSet.add(team);
                fixture.setFixtureTeams(teamSet);

                addPlayerStatToPlayerByTeam(fixtureId ,teamId);
                addGameStatToTeam(teamId, fixtureId);

                return repository.save(fixture);
            }
        }
 */
    }

    public List<Fixture> getAllFixtures() {
        List<Fixture> fixtures = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(fixtures::add);
        return fixtures;
    }

    public Set<Fixture> getFixturesByTournamentId(String tournamentId){

        return tournamentRepository.findById(tournamentId).get().getTournamentFixtures();
    }

    public Set<Venue> getVenuesByTeamIds(String fixtureId){

        Fixture fixture = repository.findById(fixtureId).get();
        Set<Venue> venues = new HashSet<>();

        Venue venue1 = teamRepository.findById(fixture.getTeamAId()).get().getTeamHomeVenue().iterator().next();
        Venue venue2 =  teamRepository.findById(fixture.getTeamBid()).get().getTeamHomeVenue().iterator().next();

        venues.add(venue1);
        venues.add(venue2);

        return venues;

    }


}
