package za.ac.cput.fms.retrofit.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import za.ac.cput.fms.model.teamStats.TeamStats;
import za.ac.cput.fms.model.tournament.Tournament;

public interface TournamentApi {

    @GET("/fms/tournament/all")
    Call<List<Tournament>> getAllTournaments();

    @GET("/fms/tournament/{tournamentId}")
    Call<List<Tournament>> getTournamentById(@Path("tournamentId") String tournamentId);

    @POST("/fms/tournament/save")
    Call<Tournament> saveTournament(@Body Tournament tournament);

    @GET("/fms/tournament/{tournamentId}/stats/")
    Call<List<TeamStats>> getTeamStatsByTournamentId(@Path("tournamentId") String tournamentId);

    @PUT("/fms/team/{teamId}/assign-to/tournament/{tournamentId}")
    Call<Tournament> assignTeamToTournament(@Path("teamId") String teamId, @Path("tournamentId") String tournamentId);

    @PUT("/fms/tournament/{tournamentId}/new-fixture/{fixtureId}")
    Call<Tournament> addFixtureToTournament(@Path("tournamentId") String tournamentId, @Path("fixtureId") String fixtureId);

}
