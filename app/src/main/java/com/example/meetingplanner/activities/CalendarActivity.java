package com.example.meetingplanner.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingplanner.R;
import com.example.meetingplanner.models.Meeting;
import com.google.gson.JsonObject;
import com.example.meetingplanner.models.Meeting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivity extends AppCompatActivity {
    private RecyclerView rvMeetings;
    private List<Meeting> meetingsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        rvMeetings = findViewById(R.id.rvMeetings);
        rvMeetings.setLayoutManager(new LinearLayoutManager(this));

    }
    private void saveMeetingToServer() {
        // Create a new Meeting object with the data
        Meeting meeting = new Meeting(
                "Meeting Title",  // Replace with actual title from your UI
                "Meeting Location", // Replace with actual location
                "2023-12-01",    // Replace with actual date
                "14:00"          // Replace with actual time
        );

        // Now you can use meeting.getTitle() etc.
        ApiClient.ApiInterface api = ApiClient.getClient().create(ApiClient.ApiInterface.class);
        Call<JsonObject> call = api.addMeeting(
                meeting.getTitle(),
                meeting.getLocation(),
                meeting.getDate(),
                meeting.getTime()
        );

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d("API_SUCCESS", "Meeting added: " + response.body());
                } else {
                    try {
                        Log.e("API_ERROR", "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("API_FAILURE", "Network error: " + t.getMessage());
            }
        });
    }

}