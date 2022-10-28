package za.ac.cput.fms.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.fms.domain.game.Tournament;
import za.ac.cput.fms.domain.info.TeamStats;
import za.ac.cput.fms.service.game.TournamentService;
import za.ac.cput.fms.service.info.TeamStatsService;

import java.util.List;
import java.util.Set;

@RestController
public class TournamentController {

    @Autowired
    private TournamentService service;

    @Autowired
    private TeamStatsService teamStatsService;

    @GetMapping("/fms/tournament/all")
    public List<Tournament> getAllTournaments(){
        return service.getAllTournaments();
    }

    @GetMapping("/fms/tournament/{tournamentId}")
    public List<Tournament> getTournamentById(@PathVariable(required = false) String tournamentId){
        return service.getTournamentById(tournamentId);
    }

    @PostMapping("/fms/tournament/save")
    /*public ResponseEntity save(@RequestBody Tournament tournament){
        service.save(tournament);
        return new ResponseEntity(HttpStatus.CREATED);
    }*/
    public Tournament save(@RequestBody Tournament tournament){
        return service.save(tournament);
    }

    @DeleteMapping("/fms/tournament/{tournamentId}/delete")
    public ResponseEntity deleteTournamentById(@PathVariable String tournamentId){
        service.deleteById(tournamentId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/fms/team/{teamId}/assign-to/tournament/{tournamentId}")
    public Tournament assignTeamToTournament(@PathVariable String teamId, @PathVariable String tournamentId){
        return service.assignTeamToTournament(teamId, tournamentId);
    }

    @PutMapping("/fms/tournament/{tournamentId}/new-fixture/{fixtureId}")
    public Tournament addNewFixture(@PathVariable String fixtureId, @PathVariable String tournamentId){
        return service.addFixtureToTournament(tournamentId, fixtureId);
    }

    @GetMapping("/fms/tournament/{tournamentId}/stats/")
    public Set<TeamStats> getTournamentTeamStats(@PathVariable String tournamentId){
        return service.getTeamStatsByTournamentId(tournamentId);
    }

}
