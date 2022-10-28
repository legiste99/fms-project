package za.ac.cput.fms.views.venues;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import za.ac.cput.fms.R;


public class VenueHolder extends RecyclerView.ViewHolder {

    TextView name, capacity;

    public VenueHolder(@NonNull View itemView, VenueAdapter.OnRecyclerViewClickListener listener) {
        super(itemView);

        name = itemView.findViewById(R.id.venue_item_name);
        capacity = itemView.findViewById(R.id.venue_capacity_value);

        itemView.setOnClickListener(v->{
            if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.OnItemClick(getAdapterPosition());
            }
        });


    }
}
