package com.example.meetingplanner.activities;

import com.example.meetingplanner.models.Meeting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2/meeting_planner/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    public interface ApiInterface {
        @FormUrlEncoded
        @POST("login.php")
        Call<JsonObject> loginUser(
                @Field("email") String email,
                @Field("password") String password
        );

        @FormUrlEncoded
        @POST("add_meeting.php")
        Call<JsonObject> addMeeting(
                @Field("title") String title,
                @Field("location") String location,
                @Field("date") String date,
                @Field("time") String time
        );
        public interface MeetingApi {
            @GET("api/meetings.php")
            Call<List<Meeting>> getMeetings();

            @POST("api/meetings.php")
            Call<JsonObject> createMeeting(@Body Meeting meeting);

            // Add other CRUD operations
        }


    }
}

    // Define API endpoints here

