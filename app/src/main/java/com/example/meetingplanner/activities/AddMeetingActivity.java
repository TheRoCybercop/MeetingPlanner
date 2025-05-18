package com.example.meetingplanner.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meetingplanner.R;
import com.example.meetingplanner.models.Meeting;
import com.google.gson.JsonObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddMeetingActivity extends AppCompatActivity {
    private EditText etTitle, etLocation;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        // Inițializare componente
        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnSave = findViewById(R.id.btnSave);

        // Configurare TimePicker să afișeze formatul 24h
        timePicker.setIs24HourView(true);

        btnSave.setOnClickListener(v -> saveMeeting());
    }

    private void saveMeeting() {
        // Validare câmpuri
        if (etTitle.getText().toString().isEmpty()) {
            etTitle.setError("Introdu titlul!");
            return;
        }

        // Obține datele din pickers
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1; // Month este indexat de la 0
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Formatează data și ora
        String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day);
        String time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

        // Creează un obiect Meeting (simplificat)
        Meeting meeting = new Meeting(
                etTitle.getText().toString(),
                etLocation.getText().toString(),
                date,
                time
        );

        // Trimite datele la server (folosind Retrofit)
        // 1. Create the API interface instance
        ApiClient.ApiInterface api = ApiClient.getClient().create(ApiClient.ApiInterface.class);

// 2. Prepare the data
        String title = etTitle.getText().toString();
        String location = etLocation.getText().toString();

// 3. Make the API call
        Call<JsonObject> call = api.addMeeting(title, location, date, time);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddMeetingActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddMeetingActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(AddMeetingActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (etLocation.getText().toString().isEmpty()) {
            etLocation.setError("Introdu locația!");
            return;
        }
    }
}