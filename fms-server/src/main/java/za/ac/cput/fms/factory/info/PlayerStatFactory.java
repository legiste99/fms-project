package za.ac.cput.fms.factory.info;

import za.ac.cput.fms.domain.info.PlayerStats;
import za.ac.cput.fms.util.Helper;

public class PlayerStatFactory {
    public static PlayerStats newPlayerStat(String playerId, String playerName, String fixtureId, String teamId, int goals, int assists, int saves){
        String id = "pls-"+Helper.generateId();
        return new PlayerStats.Builder()
                .setId(id)
                .setPlayerId(playerId)
                .setPlayerName(playerName)
                .setFixtureId(fixtureId)
                .setTeamId(teamId)
                .setGoalsScored(goals)
                .setAssistsMade(assists)
                .setGoalsSaved(saves)
                .build();
    }
}
