package za.ac.cput.fms.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import za.ac.cput.fms.model.venue.Venue;

public interface VenueApi {

    @GET("/fms/venue/all")
    Call<List<Venue>> getAllVenues();

    @POST("/fms/venue/save")
    Call<Venue> saveTournament(@Body Venue venue);

}
