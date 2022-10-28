package za.ac.cput.fms.service.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.fms.domain.game.Tournament;
import za.ac.cput.fms.domain.info.TeamStats;
import za.ac.cput.fms.repository.TeamRepository;
import za.ac.cput.fms.repository.TeamStatsRepository;
import za.ac.cput.fms.repository.TournamentRepository;

import java.util.List;
import java.util.Set;

@Service
public class TeamStatsService {

    @Autowired
    private TeamStatsRepository repository;

    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private TeamRepository teamRepository;

    public TeamStats save(TeamStats teamStats){
        return repository.save(teamStats);
    }

    public Set<TeamStats> getTeamStatByTeamId(String tournamentId, String teamId){

        Tournament tournament = tournamentRepository.findById(tournamentId).get();
        Set<TeamStats> teamSet = tournament.getTournamentTeamStats();

        return repository.findByTeamId(teamId);

    }

}
