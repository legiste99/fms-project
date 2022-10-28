package za.ac.cput.fms.factory.info;

import za.ac.cput.fms.domain.info.TeamStats;
import za.ac.cput.fms.util.Helper;

public class TeamStatsFactory {
    public static TeamStats newTeamStats(String teamId, String teamName, String tournamentId,int points, int gamesPlayed, int goalsFor, int goalsAgainst, int goalDifference){
        String id = "ts-"+ Helper.generateId();
        return new TeamStats.Builder()
                .setId(id)
                .setTeamId(teamId)
                .setTeamName(teamName)
                .setTournamentId(tournamentId)
                .setPoints(points)
                .setGamesPlayed(gamesPlayed)
                .setGoalsFor(goalsFor)
                .setGoalsAgainst(goalsAgainst)
                .setGoalDifference(goalDifference)
                .build();
    }
}
