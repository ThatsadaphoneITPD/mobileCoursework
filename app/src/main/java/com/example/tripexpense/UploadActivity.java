package com.example.tripexpense;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {
    private Button btnSendRequestUpload;
    TextView textResponse, textInput;
    JsonArray detailList = new JsonArray() ;
    String userId = "wm123";
    JsonObject mainObj = new JsonObject();
    JsonObject name1 = new JsonObject();
    JsonObject name2 = new JsonObject();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        //navigator

        BottomNav.setNavigateMenuId(this, R.id.uploadActivity);
        textResponse = findViewById(R.id.texRes);
        textInput = findViewById(R.id.textInputReq);
        btnSendRequestUpload = findViewById(R.id.btnSendPostRequest);
        btnSendRequestUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPost();
            }
        });
    }
    private void UploadPost() {

        mainObj.addProperty("userId", userId);
        name1.addProperty("name", "Android Conference");
        name2.addProperty("name", "Client Meeting");
        detailList.add(name1);
        detailList.add(name2);
        mainObj.add("detailList", detailList);
        textInput.setText("InputUpload: " + mainObj);
        ApiInterface apiInterface = RetrofitService.getRetrofitInstance().create(ApiInterface.class);
        Call<UserUpload> call = apiInterface.getUserInformation(mainObj);
        call.enqueue(new Callback<UserUpload>() {
            @Override
            public void onResponse(Call<UserUpload> call, Response<UserUpload> response) {
                if(response.isSuccessful())
                {

                    textResponse.setText("LocalHost Connect onResponse: " + response.code());
                    ResponseDialog("Upload @Post API Data to get Response:"
                            + "\n\n"+ "Id"+":\t"+ response.body().getUserId()
                            + "\n\n"+ "Name"+":\t"+ response.body().getNames()
                            + "\n\n"+ "Number"+":\t"+ response.body().getNumber()
                            + "\n\n"+ "Mgs"+":\t"+ response.body().getMessage()
                            + "\n\n"+ "Response"+":\t"+ response.body().getUploadResponseCode()
                    );
                }
                else
                {
                    Log.e(TAG, "onResponse: " + response.code());
                    textResponse.setText("onResponse: " + response.code());
                }


            }
            @Override
            public void onFailure(Call<UserUpload> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                textResponse.setText("onFailure: " + t.getMessage());
            }
        });
    }
    void ResponseDialog(String responseMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Response From API");
        builder.setMessage(responseMessage);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        //this will create Modal pop-up
        builder.create().show();
    }

}