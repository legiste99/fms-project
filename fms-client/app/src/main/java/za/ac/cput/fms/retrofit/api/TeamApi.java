package za.ac.cput.fms.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import za.ac.cput.fms.model.manager.Manager;
import za.ac.cput.fms.model.player.Player;
import za.ac.cput.fms.model.team.Team;
import za.ac.cput.fms.model.venue.Venue;

public interface TeamApi {

    @GET("/fms/team/all")
    Call<List<Team>> getAllTeams();

    @POST("/fms/team/save")
    Call<Team> saveTeam(@Body Team team);

    @PUT("/fms/player/{playerId}/assign-to/team/{teamId}")
    Call<Team> assignPlayerToTeam(@Path("playerId") String playerId, @Path("teamId") String teamId);

    @GET("/fms/team/{teamId}/get-name")
    Call<String> getTeamNameById(@Path("teamId") String teamId);

    @GET("/fms/tournament/{tournamentId}/team")
    Call<List<Team>> getTeamsByTournamentId(@Path("tournamentId") String tournamentId);

    @PUT("/fms/manager/{managerId}/assign-to/team/{teamId}")
    Call<Team> assignManagerToTeam(@Path("managerId") String managerId, @Path("teamId") String teamId);

    @PUT("/fms/venue/{venueId}/assign-to/team/{teamId}")
    Call<Team> assignVenueToTeam(@Path("venueId") String venueId, @Path("teamId") String teamId);

    @GET("/fms/team/{teamId}/venue/get")
    Call<List<Venue>> getVenueByTeamId(@Path("teamId") String teamId);

    @GET("/fms/team/{teamId}/player/all")
    Call<List<Player>> getPlayersByTeamId(@Path("teamId") String teamId);

    @GET("/fms/team/{teamId}/manager/get")
    Call<List<Manager>> getManagerByTeamId(@Path("teamId") String teamId);

}
