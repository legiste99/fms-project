package za.ac.cput.fms.retrofit.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import za.ac.cput.fms.model.gameStats.GameStats;

public interface GameStatsApi {

    @GET("/fms/team/{fixtureId}/team/{teamId}")
    Call<GameStats> getGameStatByFixtureIdAndTeamId(@Path("fixtureId") String fixtureId, @Path("teamId") String teamId);

    @PATCH("/fms/tournament/{tournamentId}/fixture/{fixtureId}/team/{teamId}/game-stat/player/{playerId}")
    Call<Void> teamGoalScoredUpdate(@Path("tournamentId")String tournamentId, @Path("fixtureId") String fixtureId, @Path("teamId") String teamId, @Path("playerId") String playerId);

}
