package za.ac.cput.fms.views.teamstats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.teamStats.TeamStats;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.TeamApi;

public class TeamStatsAdapter extends RecyclerView.Adapter<TeamStatsHolder> {

    private List<TeamStats> teamStatsList;

    public TeamStatsAdapter(List<TeamStats> teamStatsList) {
        this.teamStatsList = teamStatsList;
    }

    @NonNull
    @Override
    public TeamStatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_team_stats, parent, false);
        return new TeamStatsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamStatsHolder holder, int position) {

        TeamStats teamStats = teamStatsList.get(position);

        holder.teamName.setText(teamStats.getTeamName());
        holder.points.setText(String.valueOf(teamStats.getPoints()));
        holder.gamesPlayed.setText(String.valueOf(teamStats.getGamesPlayed()));
        holder.goalsFor.setText(String.valueOf(teamStats.getGoalsFor()));
        holder.goalsAgainst.setText(String.valueOf(teamStats.getGoalsAgainst()));
        holder.goalDifference.setText(String.valueOf(teamStats.getGoalDifference()));


    }

    @Override
    public int getItemCount() {
        return teamStatsList.size();
    }
}
