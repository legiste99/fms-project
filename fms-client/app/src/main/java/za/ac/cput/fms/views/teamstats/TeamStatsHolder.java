package za.ac.cput.fms.views.teamstats;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class TeamStatsHolder extends RecyclerView.ViewHolder {

    TextView teamName, points, gamesPlayed,
            goalsFor, goalsAgainst, goalDifference;

    public TeamStatsHolder(@NonNull View itemView) {
        super(itemView);

        teamName = itemView.findViewById(R.id.stat_team_name);
        points = itemView.findViewById(R.id.stat_team_points);
        gamesPlayed = itemView.findViewById(R.id.stat_team_games_played);
        goalsFor = itemView.findViewById(R.id.stat_team_goal_for);
        goalsAgainst = itemView.findViewById(R.id.stat_team_goals_against);
        goalDifference = itemView.findViewById(R.id.stat_team_goal_diff);

    }
}
