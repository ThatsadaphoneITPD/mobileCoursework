package com.example.tripexpense;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    //Run command terminal to use Port
    // adb reverse tcp:61421 tcp:61421
    @FormUrlEncoded
    @POST("COMP1424CW")
    Call<UserUpload> getUserInformation(@Field("jsonpayload") JsonObject value);
}
