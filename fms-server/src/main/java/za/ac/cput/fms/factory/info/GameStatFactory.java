package za.ac.cput.fms.factory.info;

import za.ac.cput.fms.domain.info.GameStats;
import za.ac.cput.fms.util.Helper;

public class GameStatFactory {
    public static GameStats newGameStat(String teamId, String fixtureId,String teamName, int goalsScored, int goalsConceded, int corners, int yellowCards, int redCards, int freeKicks){
        String id = "gst-"+Helper.generateId();
        return new GameStats.Builder()
                .setId(id)
                .setFixtureId(fixtureId)
                .setTeamId(teamId)
                .setTeamName(teamName)
                .setGoalsScored(goalsScored)
                .setGoalsConceded(goalsConceded)
                .setCorners(corners)
                .setYellowCards(yellowCards)
                .setRedCards(redCards)
                .setFreeKicks(freeKicks)
                .build();
    }
}
