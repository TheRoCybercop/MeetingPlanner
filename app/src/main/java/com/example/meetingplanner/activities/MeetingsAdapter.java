package com.example.meetingplanner.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingplanner.R;
import com.example.meetingplanner.models.Meeting;

import java.util.List;

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.ViewHolder> {
    private List<Meeting> meetings;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }

    public MeetingsAdapter(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.tvTitle.setText(meeting.getTitle());
        holder.tvDateTime.setText(meeting.getDate() + " " + meeting.getTime());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }
}