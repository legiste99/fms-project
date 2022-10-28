package za.ac.cput.fms.views.playerstats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.playerStats.PlayerStats;
public class PlayerStatsAdapter extends RecyclerView.Adapter<PlayerStatsHolder> {

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public PlayerStatsAdapter(List<PlayerStats> playerStatsList) {
        this.playerStatsList = playerStatsList;
    }

    private List<PlayerStats> playerStatsList;

    @NonNull
    @Override
    public PlayerStatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_player_stats, parent,false);
        return new PlayerStatsHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerStatsHolder holder, int position) {

        PlayerStats playerStats = playerStatsList.get(position);

        holder.playerName.setText(playerStats.getPlayerName());
        holder.playerGoals.setText(String.valueOf(playerStats.getGoalsScored()));
        holder.playerAssists.setText(String.valueOf(playerStats.getAssistsMade()));
        holder.playerSaves.setText(String.valueOf(playerStats.getGoalsSaved()));

    }

    @Override
    public int getItemCount() {
        return playerStatsList.size();
    }
}
