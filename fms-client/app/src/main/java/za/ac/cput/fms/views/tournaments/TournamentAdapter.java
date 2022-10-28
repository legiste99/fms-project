package za.ac.cput.fms.views.tournaments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.tournament.Tournament;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentHolder> {

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    private List<Tournament> tournamentList;

    public TournamentAdapter(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @NonNull
    @Override
    public TournamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tournaments, parent, false);
        return new TournamentHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TournamentHolder holder, int position) {
        Tournament tournament = tournamentList.get(position);
        holder.name.setText(tournament.getTournamentName());
        holder.teamsIn.setText(String.valueOf( tournament.getNumberOfTeams()));
        holder.maxTeams.setText(String.valueOf(tournament.getMaxNumberOfTeams()));
    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }
}
