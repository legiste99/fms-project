package za.ac.cput.fms.views.fixtures;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class FixtureHolder extends RecyclerView.ViewHolder {

    TextView teamOne, teamTwo, refereeName, venueName;

    public FixtureHolder(@NonNull View itemView, FixtureAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        teamOne = itemView.findViewById(R.id.team_one_name);
        teamTwo = itemView.findViewById(R.id.team_two_name);
        refereeName = itemView.findViewById(R.id.fixture_referee_name);
        venueName = itemView.findViewById(R.id.fixture_venue_name);


        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });
    }
}
