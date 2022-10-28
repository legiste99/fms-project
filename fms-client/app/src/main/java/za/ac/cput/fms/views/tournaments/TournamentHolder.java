package za.ac.cput.fms.views.tournaments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class TournamentHolder extends RecyclerView.ViewHolder {

    TextView name, teamsIn, maxTeams;

    public TournamentHolder(@NonNull View itemView, TournamentAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        name = itemView.findViewById(R.id.tournament_item_name);
        teamsIn = itemView.findViewById(R.id.tournament_item_teams_in_tour);
        maxTeams = itemView.findViewById(R.id.tournament_item_max_teams);

        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });

    }
}
