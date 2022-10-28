package za.ac.cput.fms.views.players;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class PlayerHolder extends RecyclerView.ViewHolder {

    TextView name, age;

    public PlayerHolder(@NonNull View itemView, PlayerAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        name = itemView.findViewById(R.id.player_item_name);
        age = itemView.findViewById(R.id.player_item_age);

        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });

    }
}
