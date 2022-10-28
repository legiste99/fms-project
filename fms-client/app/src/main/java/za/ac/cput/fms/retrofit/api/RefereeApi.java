package za.ac.cput.fms.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import za.ac.cput.fms.model.referee.Referee;

public interface RefereeApi {


    @GET("/fms/referee/all")
    Call<List<Referee>> getAllReferees();

    @POST("/fms/referee/save")
    Call<Referee> saveReferee(@Body Referee referee);

}
