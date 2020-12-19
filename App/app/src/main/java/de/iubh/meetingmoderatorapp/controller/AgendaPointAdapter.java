package de.iubh.meetingmoderatorapp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.model.AgendaPoint;

public class AgendaPointAdapter extends RecyclerView.Adapter<AgendaPointAdapter.AgendaPointViewHolder> {
    private ArrayList<AgendaPoint> agendapoints;

    public static class AgendaPointViewHolder extends RecyclerView.ViewHolder {
        public TextView apTitle;
        AgendaPointViewHolder(View itemView) {
            super(itemView);
            apTitle = itemView.findViewById(R.id.txtAPTitle);

        }
    }

    public AgendaPointAdapter(ArrayList<AgendaPoint> mAgendaPoints) {agendapoints = mAgendaPoints;}

    @NonNull
    @Override
    public AgendaPointAdapter.AgendaPointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_aptitle, parent, false);
        return new AgendaPointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaPointAdapter.AgendaPointViewHolder holder, int position) {
        AgendaPoint ap = agendapoints.get(position);
        holder.apTitle.setText(ap.getTitle());
    }

    @Override
    public int getItemCount() {
        return agendapoints.size();
    }
}
