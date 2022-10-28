package za.ac.cput.fms.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import za.ac.cput.fms.model.fixture.Fixture;
import za.ac.cput.fms.model.venue.Venue;

public interface FixtureApi {

    @POST("/fms/fixture/save")
    Call<Fixture> saveFixture(@Body Fixture fixture);

    @GET("/fms/tournament/{tournamentId}/fixtures/all")
    Call<List<Fixture>> getFixturesByTournamentId(@Path("tournamentId") String tournamentId);

    @PUT("/fms/team/{teamId}/assign-to/fixture/{fixtureId}")
    Call<Fixture> addTeamToFixture(@Path("teamId") String teamId, @Path("fixtureId") String fixtureId);

    @PUT("/fms/referee/{refereeId}/assign-to/fixture/{fixtureId}")
    Call<Fixture> assignRefereeToFixture(@Path("refereeId")String refereeId, @Path("fixtureId") String fixtureId);

    @PUT("/fms/venue/{venueId}/assign-to/fixture/{fixtureId}")
    Call<Fixture> assignVenueToFixture(@Path("venueId") String venueId, @Path("fixtureId") String fixtureId);

    @GET("/fms/fixture/{fixtureId}/select-venue")
    Call<List<Venue>> getVenuesByFixtureId(@Path("fixtureId") String fixtureId);

}
