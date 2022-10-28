package za.ac.cput.fms.views.teams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.team.Team;

public class TeamAdapter extends RecyclerView.Adapter<TeamHolder> {

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    private List<Team> teamList;

    public TeamAdapter(List<Team> teamList) {
        this.teamList = teamList;
    }

    @NonNull
    @Override
    public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_teams, parent, false);
        return new TeamHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamHolder holder, int position) {
        Team team = teamList.get(position);
        holder.name.setText(team.getTeamName());
    }

    @Override
    public int getItemCount() {
        return teamList.size();
    }
}
