package za.ac.cput.fms.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
        initialiseRetrofit();
    }

    private void initialiseRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.8.100:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
