package za.ac.cput.fms.service.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.game.Venue;
import za.ac.cput.fms.domain.personal.Manager;
import za.ac.cput.fms.domain.personal.Player;
import za.ac.cput.fms.domain.game.Team;
import za.ac.cput.fms.domain.game.Tournament;
import za.ac.cput.fms.repository.*;

import java.util.*;

@Service
public class TeamService {

    @Autowired
    private TeamRepository repository;
    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    VenueRepository venueRepository;

    public Team save(Team team){
        team.setTitlesWon(0);
        return repository.save(team);
    }

    public List<Team> getAllTeams(){
        List<Team> teams = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(teams::add);
        return teams;
    }

    public void deleteTeamById(String id){
        repository.deleteById(id);
    }

    public List<Team> getTeamByTournamentId(String tournamentId){

        Optional<Tournament> tournament = tournamentRepository.findById(tournamentId);

        List<Team> teams = new ArrayList<>();
        Streamable.of(tournament.get().getAssignedTeams())
                .forEach(teams::add);
        return teams;

    }

    public List<Team> getTeamById(String teamId) {
        if (null != teamId){
            return repository.findAllById(Collections.singleton(teamId));
        }
        else {
            return repository.findAll();
        }
    }

    public Team assignManagerToTeam(String managerId, String teamId){

        Set<Manager> managerSet = null;

        Team team = repository.findById(teamId).get();
        Manager manager = managerRepository.findById(managerId).get();


        // Prevents manager from managing two or more teams and the same team again.
        if (team.getTeamManager().size() == 0){

            if (manager.getTeamSet().size() == 0){

                managerSet = team.getTeamManager();
                managerSet.add(manager);
                team.setTeamManager(managerSet);

                return repository.save(team);
            }
            else return null;
        }

        else return null;

    }

    public Team assignPlayerToTeam(String playerId, String teamId){
        Set<Player> playerSet = null ;

        Team team = repository.findById(teamId).get();
        Player player = playerRepository.findById(playerId).get();


        // prevents player from being added to multiple teams and the same team again.
        if (player.getTeam().size() == 0){

            if (team.getTeamPlayers().size()<=team.getMaxPlayers()){

                playerSet = team.getTeamPlayers();
                playerSet.add(player);
                team.setTeamPlayers(playerSet);

                return repository.save(team);

            }

            else return null;

        }

        else return null;

    }

    public Team assignVenueToTeam(String venueId, String teamId){

        Set<Venue> venueSet = null;

        Team team = repository.findById(teamId).get();
        Venue venue = venueRepository.findById(venueId).get();


        // prevents Venue from being added to multiple teams and the same team again.
        if (team.getTeamHomeVenue().size() == 0){

            if (venue.getTeam().size() == 0){

                venueSet = team.getTeamHomeVenue();
                venueSet.add(venue);
                team.setTeamHomeVenue(venueSet);

                return repository.save(team);
            }
            else return null;
        }

        else return null;

    }

    public Set<Player> getPlayersByTeamId(String teamId){

        Team team = repository.findById(teamId).get();

        Set<Player> players = team.getTeamPlayers();

        return players;
    }

    public Set<Manager> getManagerTeamId(String teamId){

        Team team = repository.findById(teamId).get();
        Set<Manager> manager = team.getTeamManager();

        return manager;
    }

    public Set<Venue> getVenueByTeamId(String teamId){
        Team team = repository.findById(teamId).get();

        Set<Venue> venue = team.getTeamHomeVenue();

        return venue;

    }

    public void updateTeamNameById(String teamId, String teamName){
        Team team = repository.findById(teamId).get();
        team.setTeamName(teamName);
        repository.save(team);
    }


}
