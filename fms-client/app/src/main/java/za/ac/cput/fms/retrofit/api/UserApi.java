package za.ac.cput.fms.retrofit.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import za.ac.cput.fms.model.user.User;

public interface UserApi {


    @GET("/fms/user/email")
    Call<User> getUserByEmail(User user);


    @PUT("/fms/user/sign-up")
    Call <User> signUpUser(User user);

}


