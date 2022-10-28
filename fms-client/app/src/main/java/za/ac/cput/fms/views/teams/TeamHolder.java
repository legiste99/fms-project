package za.ac.cput.fms.views.teams;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class TeamHolder extends RecyclerView.ViewHolder {

    TextView name;

    public TeamHolder(@NonNull View itemView, TeamAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        name = itemView.findViewById(R.id.team_item_name);

        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });
    }
}
