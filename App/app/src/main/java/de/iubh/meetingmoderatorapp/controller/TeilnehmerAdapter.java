package de.iubh.meetingmoderatorapp.controller;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.iubh.meetingmoderatorapp.R;


public class TeilnehmerAdapter extends RecyclerView.Adapter<TeilnehmerAdapter.TeilnehmerViewHolder> {
    TextView surname, lastname;

    public class TeilnehmerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TeilnehmerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            surname = itemView.findViewById(R.id.txtWelcSur);
            lastname = itemView.findViewById(R.id.txtWelcLast);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @Override
    public TeilnehmerAdapter.TeilnehmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TeilnehmerAdapter.TeilnehmerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
