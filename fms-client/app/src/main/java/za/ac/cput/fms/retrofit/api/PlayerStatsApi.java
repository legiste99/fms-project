package za.ac.cput.fms.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import za.ac.cput.fms.model.playerStats.PlayerStats;

public interface PlayerStatsApi {

    @GET("/fms/fixture/{fixtureId}/team/{teamId}/player-stats")
    Call<List<PlayerStats>> getFixtureTeamPlayerStats(@Path("fixtureId") String fixtureId, @Path("teamId") String teamId);

}
