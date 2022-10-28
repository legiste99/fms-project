package za.ac.cput.fms.views.managers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.manager.Manager;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerHolder> {

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public ManagerAdapter(List<Manager> managerList) {
        this.managerList = managerList;
    }

    private List<Manager> managerList;

    @NonNull
    @Override
    public ManagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_managers, parent, false);
        return new ManagerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerHolder holder, int position) {
        Manager manager = managerList.get(position);
        String name = manager.getFirstName()+" "+manager.getLastName();
        holder.name.setText(name);
        holder.experience.setText(String.valueOf(manager.getYearsOfExperience()));
    }

    @Override
    public int getItemCount() {
        return managerList.size();
    }
}
