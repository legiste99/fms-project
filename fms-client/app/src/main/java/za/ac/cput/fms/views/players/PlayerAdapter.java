package za.ac.cput.fms.views.players;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.player.Player;
import za.ac.cput.fms.views.managers.ManagerAdapter;
import za.ac.cput.fms.views.managers.ManagerHolder;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerHolder> {

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public PlayerAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    private List<Player> playerList;

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_players, parent, false);
        return new PlayerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {
        Player player = playerList.get(position);
        String name = player.getFirstName()+" "+player.getLastName();
        holder.name.setText(name);
        holder.age.setText(String.valueOf(player.getAge()));

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }
}
