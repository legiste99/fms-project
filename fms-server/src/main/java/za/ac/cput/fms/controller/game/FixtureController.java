package za.ac.cput.fms.controller.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.fms.domain.game.Fixture;
import za.ac.cput.fms.domain.game.Venue;
import za.ac.cput.fms.domain.info.GameStats;
import za.ac.cput.fms.service.game.FixtureService;

import java.util.List;
import java.util.Set;

@RestController
public class FixtureController {

    @Autowired
    FixtureService service;

    @PostMapping("/fms/fixture/save")
    public Fixture save(@RequestBody Fixture fixture){
        return service.save(fixture);
    }

    @GetMapping("/fms/fixture/all")
    public List<Fixture> getAllFixtures(){
        return service.getAllFixtures();
    }

    @GetMapping("/fms/tournament/{tournamentId}/fixtures/all")
    public Set<Fixture> getFixturesByTournamentId(@PathVariable String tournamentId){
        return service.getFixturesByTournamentId(tournamentId);
    }

    @PutMapping("/fms/team/{teamId}/assign-to/fixture/{fixtureId}")
    public Fixture addTeamToFixture(@PathVariable String fixtureId, @PathVariable String teamId){
        return service.addTeamToFixture(fixtureId, teamId);
    }

    @PutMapping("/fms/referee/{refereeId}/assign-to/fixture/{fixtureId}")
    public Fixture assignRefereeToFixture(@PathVariable String refereeId, @PathVariable String fixtureId){
        return service.assignRefereeToFixture(refereeId, fixtureId);
    }

    @PutMapping("/fms/venue/{venueId}/assign-to/fixture/{fixtureId}")
    public Fixture assignVenueToFixture(@PathVariable String venueId, @PathVariable String fixtureId){
        return service.assignVenueToFixture(venueId, fixtureId);
    }

    @GetMapping("/fms/fixture/{fixtureId}/select-venue")
    public Set<Venue> getVenuesByFixtureId(@PathVariable String fixtureId){
        return service.getVenuesByTeamIds(fixtureId);
    }



}
