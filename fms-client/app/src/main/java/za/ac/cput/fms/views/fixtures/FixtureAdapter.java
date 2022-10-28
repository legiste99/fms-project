package za.ac.cput.fms.views.fixtures;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import za.ac.cput.fms.R;
import za.ac.cput.fms.model.fixture.Fixture;
import za.ac.cput.fms.model.referee.Referee;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureHolder> {

    private FixtureAdapter.OnRecyclerViewClickListener listener;

    public FixtureAdapter(List<Fixture> fixtureList) {
        this.fixtureList = fixtureList;
    }

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(FixtureAdapter.OnRecyclerViewClickListener listener) {
        this.listener = listener;
    }

    private List<Fixture> fixtureList;

    @NonNull
    @Override
    public FixtureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_fixtures, parent, false );
        return new FixtureHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureHolder holder, int position) {
        Fixture fixture = fixtureList.get(position);
        if (fixture.getTeamAName() == null){
            holder.teamOne.setText("Pending..");
        }
        else {
            holder.teamOne.setText(fixture.getTeamAName());
        }
        if (fixture.getTeamBName() == null){
            holder.teamTwo.setText("Pending..");
        }
        else{
            holder.teamTwo.setText(fixture.getTeamBName());
        }
        if (fixture.getFixtureReferee().size() == 0){
            holder.refereeName.setText("Pending");
        }
        else{
            String fName = fixture.getFixtureReferee().get(0).getFirstName();
            String lName = fixture.getFixtureReferee().get(0).getLastName();
            String name = fName+" "+lName;
            holder.refereeName.setText(name);
        }
        if (fixture.getFixtureVenue().size() == 0){
            holder.venueName.setText("Pending");
        }
        else {
            String venueName = fixture.getFixtureVenue().get(0).getVenueName();
            holder.venueName.setText(venueName);
        }
    }

    @Override
    public int getItemCount() {
        return fixtureList.size();
    }
}
