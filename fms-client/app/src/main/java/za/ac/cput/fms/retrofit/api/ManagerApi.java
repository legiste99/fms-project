package za.ac.cput.fms.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import za.ac.cput.fms.model.manager.Manager;

public interface ManagerApi {
    @GET("/fms/manager/all")
    Call<List<Manager>> getAllManagers();

    @POST("/fms/manager/save")
    Call<Manager> saveManager(@Body Manager manager);
}
