package com.example.tripexpense;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static Retrofit retrofit;
    //API Client
    //Run command terminal change of Mobile port to use Local PC Port
    // adb reverse tcp:61421 tcp:61421
    public static final String BASE_URL = "http://localhost:61421/";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
