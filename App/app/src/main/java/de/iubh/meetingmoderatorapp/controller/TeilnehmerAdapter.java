package de.iubh.meetingmoderatorapp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.model.Participant;

public class TeilnehmerAdapter extends RecyclerView.Adapter<TeilnehmerAdapter.TeilnehmerViewHolder> {
    private final ArrayList<Participant> mParticipants;

    public static class TeilnehmerViewHolder extends RecyclerView.ViewHolder {
        public TextView surname, lastname;
        TeilnehmerViewHolder(View itemView) {
            super(itemView);
            surname = itemView.findViewById(R.id.txtWelcSur);
            lastname = itemView.findViewById(R.id.txtWelcLast);
        }
    }

    public TeilnehmerAdapter(ArrayList<Participant> participants) {
        mParticipants = participants;
    }

    @NonNull
    @Override
    public TeilnehmerAdapter.TeilnehmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_first_last_welcome, parent, false);
        return new TeilnehmerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeilnehmerAdapter.TeilnehmerViewHolder holder, int position) {
        Participant teilnehmer = mParticipants.get(position);
        holder.surname.setText(teilnehmer.getUser().getFirstname());
        holder.lastname.setText(teilnehmer.getUser().getLastname());
    }

    @Override
    public int getItemCount() {
        return mParticipants.size();
    }


}
