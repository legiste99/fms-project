package za.ac.cput.fms.retrofit.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import za.ac.cput.fms.model.player.Player;

public interface PlayerApi {

    @GET("/fms/player/all")
    Call<List<Player>> getAllPlayers();

    @POST("/fms/player/save")
    Call<Player> savePlayer(@Body Player player);

    @GET("/fms/player/{playerId}")
    Call<List<Player>> getPlayerById(@Path("playerId") String playerId);

    @PATCH("/fms/player/{playerId}/update")
    Call <Player> updatePlayerDetails(@Path("playerId") String playerId, String firsName, String middleName, String lastName, String position, int positionNumber );


}
