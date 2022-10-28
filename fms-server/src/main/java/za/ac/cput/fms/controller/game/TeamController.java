package za.ac.cput.fms.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.fms.domain.game.Team;
import za.ac.cput.fms.domain.game.Venue;
import za.ac.cput.fms.domain.personal.Manager;
import za.ac.cput.fms.domain.personal.Player;
import za.ac.cput.fms.service.game.TeamService;
import java.util.List;
import java.util.Set;

@RestController
public class TeamController {

    @Autowired
    private TeamService service;

    @GetMapping("/fms/team/all")
    public List<Team> getAllTeams(){
        return service.getAllTeams();
    }

    @GetMapping("/fms/team/{teamId}")
    public List<Team> getTeamById(@PathVariable(required = false) String teamId){
        return service.getTeamById(teamId);
    }

    @GetMapping("/fms/tournament/{tournamentId}/team")
    public List<Team> getTeamByTournamentId(@PathVariable String tournamentId){
        return service.getTeamByTournamentId(tournamentId);
    }

    @PostMapping("/fms/team/save")
    /*public ResponseEntity<Team> save(@RequestBody Team team){
        service.save(team);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/
    public Team save(@RequestBody Team team){
        return service.save(team);
    }

    @DeleteMapping("/fms/team/{teamId}/delete")
    public ResponseEntity deleteTeamById(@PathVariable String teamId){
        service.deleteTeamById(teamId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/fms/manager/{managerId}/assign-to/team/{teamId}")
    public Team assignManagerToTeam(@PathVariable String managerId, @PathVariable String teamId){
        return service.assignManagerToTeam(managerId, teamId);
    }

    @PutMapping("/fms/player/{playerId}/assign-to/team/{teamId}")
    public Team assignPlayerToTeam(@PathVariable String playerId, @PathVariable String teamId){
        return service.assignPlayerToTeam(playerId, teamId);
    }

    @GetMapping("/fms/team/{teamId}/player/all")
    public Set<Player> getPlayersByTeamId(@PathVariable String teamId){
        return service.getPlayersByTeamId(teamId);
    }

    @GetMapping("/fms/team/{teamId}/manager/get")
    public Set<Manager> getManagerByTeamId(@PathVariable String teamId){
        return service.getManagerTeamId(teamId);
    }

    @PutMapping("/fms/venue/{venueId}/assign-to/team/{teamId}")
    public Team assignVenueToTeam(@PathVariable String venueId, @PathVariable String teamId){
        return service.assignVenueToTeam(venueId, teamId);
    }

    @PatchMapping("/fms/team/{teamId}/update-name-to/{teamName}")
    public void updateTeamDetails(@PathVariable String teamId, @PathVariable String teamName){
        service.updateTeamNameById(teamId, teamName);
    }

    @GetMapping("/fms/team/{teamId}/venue/get")
    public Set<Venue> getVenueByTeamId(@PathVariable String teamId){
        return service.getVenueByTeamId(teamId);
    }

}