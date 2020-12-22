package de.iubh.meetingmoderatorapp.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.iubh.meetingmoderatorapp.R;
import de.iubh.meetingmoderatorapp.model.Participant;

public class TeilnehmerAdapter extends RecyclerView.Adapter<TeilnehmerAdapter.TeilnehmerViewHolder> {
    private final ArrayList<Participant> participants;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TeilnehmerViewHolder extends RecyclerView.ViewHolder {

        public TextView surname, lastname;


        public TeilnehmerViewHolder(View itemView, final TeilnehmerAdapter.OnItemClickListener listener) {
            super(itemView);
            surname = itemView.findViewById(R.id.txtWelcSur);
            lastname = itemView.findViewById(R.id.txtWelcLast);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(pos);
                }
            });
        }
    }




    public TeilnehmerAdapter(ArrayList<Participant> mParticipants) {participants = mParticipants;}

    @NonNull
    @Override
    public TeilnehmerAdapter.TeilnehmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lay_first_last_welcome, parent, false);
        return new TeilnehmerViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TeilnehmerAdapter.TeilnehmerViewHolder holder, int position) {
        Participant teilnehmer = participants.get(position);
        holder.surname.setText(teilnehmer.getUser().getFirstname());
        holder.lastname.setText(teilnehmer.getUser().getLastname());
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

}
