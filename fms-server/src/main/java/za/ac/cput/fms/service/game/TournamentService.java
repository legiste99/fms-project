package za.ac.cput.fms.service.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.game.Fixture;
import za.ac.cput.fms.domain.game.Team;
import za.ac.cput.fms.domain.game.Tournament;
import za.ac.cput.fms.domain.info.TeamStats;
import za.ac.cput.fms.factory.game.TournamentFactory;
import za.ac.cput.fms.factory.info.TeamStatsFactory;
import za.ac.cput.fms.repository.FixtureRepository;
import za.ac.cput.fms.repository.TeamRepository;
import za.ac.cput.fms.repository.TeamStatsRepository;
import za.ac.cput.fms.repository.TournamentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class TournamentService {

    @Autowired
    private TournamentRepository repository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private FixtureRepository fixtureRepository;

    @Autowired
    private TeamStatsRepository teamStatsRepository;

    public Tournament save(Tournament tournament){
        tournament.setNumberOfTeams(0);
        tournament.setStatus(0);
        return repository.save(tournament);
    }

    public List<Tournament> getAllTournaments(){
        List<Tournament> tournaments = new ArrayList<>();
        Streamable.of(repository.findAll())
                .forEach(tournaments::add);
        return tournaments;
    }

    public void deleteById(String id){
        repository.deleteById(id);
    }

    public List<Tournament> getTournamentById(String tournamentId) {
        if (null != tournamentId){
            return repository.findAllById(Collections.singleton(tournamentId));
        }
        else {
            return repository.findAll();
        }
    }

    public void createAndAddTeamStatsToTournament(String teamId, String tournamentId){

        String teamName = teamRepository.findById(teamId).get().getTeamName();

        TeamStats teamStats = TeamStatsFactory.newTeamStats(teamId, teamName, tournamentId,0,0,0,0,0);
        teamStatsRepository.save(teamStats);

        Set<TeamStats> teamStatsSet;
        Tournament tournament = repository.findById(tournamentId).get();

        String existingTeamId;

        teamStatsSet = tournament.getTournamentTeamStats();

        teamStatsSet.add(teamStats);

        //get last save from back up and try again.. do a test run with sample string data

    }

    public void updateNumberOfTeamsInTournament(String tournamentId){

        Tournament tournament = repository.findById(tournamentId).get();
        int numberOfTeams = tournament.getAssignedTeams().size();
        int maxNumber = tournament.getMaxNumberOfTeams();

        tournament.setNumberOfTeams(numberOfTeams);

        if (numberOfTeams >= maxNumber){
            tournament.setStatus(1);
        }

    }

    public Tournament assignTeamToTournament(String teamId, String tournamentId) {

        Set<Team> teamSet = null;

        Tournament tournament = repository.findById(tournamentId).get();

        int max = tournament.getMaxNumberOfTeams();
        int current = tournament.getNumberOfTeams();

        if (current >= max){
            return null;
        }

        else {

            Team team = teamRepository.findById(teamId).get();

            teamSet = tournament.getAssignedTeams();

            if (teamSet.contains(team)){
                return null;
            }

            else {

                teamSet.add(team);
                tournament.setAssignedTeams(teamSet);

                createAndAddTeamStatsToTournament(teamId, tournamentId);
                updateNumberOfTeamsInTournament(tournamentId);

                return repository.save(tournament);
            }
        }
    }

    public Tournament addFixtureToTournament(String tournamentId, String fixtureId){

        Set<Fixture> fixtures = null;

        Tournament tournament = repository.findById(tournamentId).get();
        Fixture fixture = fixtureRepository.findById(fixtureId).get();

        fixtures = tournament.getTournamentFixtures();
        fixtures.add(fixture);

        tournament.setTournamentFixtures(fixtures);
        return repository.save(tournament);
    }

    public Set<TeamStats> getTeamStatsByTournamentId(String tournamentId){
        Tournament tournament = repository.findById(tournamentId).get();

        Set<TeamStats> stats = tournament.getTournamentTeamStats();
        return stats;
    }

    // TODO: updateTournamentFixtureGameStat
    //get tournament by id
    //get fixture by id - get teams in that fixture
    //get the gameStat in that tournament(id) -> with a specific teamId
    //update accordingly. -> a method for each variable update.



}
