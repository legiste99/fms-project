package za.ac.cput.fms.views.managers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;

public class ManagerHolder extends RecyclerView.ViewHolder {

    TextView name, experience;

    public ManagerHolder(@NonNull View itemView, ManagerAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        name = itemView.findViewById(R.id.manager_item_name);
        experience = itemView.findViewById(R.id.manager_item_experience);

        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });
    }
}
