package za.ac.cput.fms.views.venues;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.venue.Venue;

public class VenueAdapter extends RecyclerView.Adapter<VenueHolder> {

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecycleViewClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public VenueAdapter(OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    private List<Venue> venueList;

    public VenueAdapter(List<Venue> venueList) {
        this.venueList = venueList;
    }

    @NonNull
    @Override
    public VenueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_venues, parent, false);
        return new VenueHolder(view, listener);

    }

    @Override
    public void onBindViewHolder(@NonNull VenueHolder holder, int position) {
        Venue venue = venueList.get(position);
        holder.name.setText(venue.getVenueName());
        holder.capacity.setText(String.valueOf(venue.getCapacity()));
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }
}
