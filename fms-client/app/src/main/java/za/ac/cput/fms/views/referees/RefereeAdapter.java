package za.ac.cput.fms.views.referees;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.referee.Referee;

public class RefereeAdapter extends RecyclerView.Adapter<RefereeHolder> {

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public RefereeAdapter(List<Referee> refereeList) {
        this.refereeList = refereeList;
    }

    private List<Referee> refereeList;

    @NonNull
    @Override
    public RefereeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_referees, parent, false);
        return new RefereeHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RefereeHolder holder, int position) {
        Referee referee = refereeList.get(position);
        String name = referee.getFirstName()+" "+referee.getLastName();
        holder.name.setText(name);
        holder.experience.setText(String.valueOf(referee.getYearsOfExperience()));

    }

    @Override
    public int getItemCount() {
        return refereeList.size();
    }
}
