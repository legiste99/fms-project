package za.ac.cput.fms.views.referees;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class RefereeHolder extends RecyclerView.ViewHolder {

    TextView name, experience;

    public RefereeHolder(@NonNull View itemView, RefereeAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        name = itemView.findViewById(R.id.referee_item_name);
        experience = itemView.findViewById(R.id.referee_item_experience);

        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });
    }
}
