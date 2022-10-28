package za.ac.cput.fms.views.playerstats;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class PlayerStatsHolder extends RecyclerView.ViewHolder {

    TextView playerName, playerGoals, playerAssists, playerSaves;

    public PlayerStatsHolder(@NonNull View itemView, PlayerStatsAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        playerName = itemView.findViewById(R.id.player_stat_player_item_name);
        playerGoals = itemView.findViewById(R.id.player_stat_goals);
        playerAssists = itemView.findViewById(R.id.player_stat_assists);
        playerSaves = itemView.findViewById(R.id.player_stat_saves);

        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });

    }
}
