package za.ac.cput.fms.controller.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import za.ac.cput.fms.domain.info.PlayerStats;
import za.ac.cput.fms.service.info.PlayerStatsService;

import java.util.Set;

@RestController
public class PlayerStatsController {

    @Autowired
    private PlayerStatsService service;


    @GetMapping("/fms/fixture/{fixtureId}/team/{teamId}/player-stats")
    public Set<PlayerStats> getFixtureTeamPlayerStats(@PathVariable String fixtureId, @PathVariable String teamId){
        return service.getFixtureTeamPlayerStats(fixtureId, teamId);
    }

}
