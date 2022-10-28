package za.ac.cput.fms.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.venue.Venue;
import za.ac.cput.fms.model.venue.VenueFactory;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.VenueApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.venues.VenueAdapter;

public class VenuesActivity extends AppCompatActivity {

    private Button backButton, signOutButton, newVenueButton;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog newVenueDialog;
    private TextInputEditText nameInput, capacityInput;
    private Button save, cancel;
    private RecyclerView venuesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);

        backButton = findViewById(R.id.venues_back_to_dashboard_button);
        backButton.setOnClickListener(v->{
            finish();
        });

        signOutButton = findViewById(R.id.venues_sign_out_button);
        signOutButton.setOnClickListener(v->{
            showSignOutDialog();
        });

        newVenueButton = findViewById(R.id.create_new_venue_button);
        newVenueButton.setOnClickListener(v->{

            createNewVenueDialog();
        });

        venuesRecyclerView = findViewById(R.id.venues_recycler_view);
        venuesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void loadVenues(){

        RetrofitService service = new RetrofitService();
        VenueApi api = service.getRetrofit().create(VenueApi.class);

        api.getAllVenues().enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {

            }
        });

    }

    private void populateListView(List<Venue> venueList) {

        VenueAdapter adapter = new VenueAdapter(venueList);
        venuesRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+venueList.size(), Toast.LENGTH_SHORT).show();


        adapter.OnRecycleViewClickListener(position -> {

            Toast.makeText(this, "viewing "+venueList.get(position).getVenueName(), Toast.LENGTH_SHORT).show();

        });

    }

    private void createNewVenueDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_new_venue, null);

        nameInput = view.findViewById(R.id.new_venue_name_input);
        capacityInput = view.findViewById(R.id.new_venue_capacity_input);

        save = view.findViewById(R.id.save_new_venue);
        cancel = view.findViewById(R.id.cancel_new_venue);

        dialogBuilder.setView(view);
        newVenueDialog = dialogBuilder.create();
        newVenueDialog.show();

        RetrofitService service = new RetrofitService();
        VenueApi api = service.getRetrofit().create(VenueApi.class);

        save.setOnClickListener(v->{

            Boolean valid = true;

            String name = Objects.requireNonNull(nameInput.getText()).toString();
            String capacity = Objects.requireNonNull(capacityInput.getText()).toString();

            if (TextUtils.isEmpty(name)){
                nameInput.setError("Venue name is required");
                valid = false;
            }

            if (TextUtils.isEmpty(capacity)){
                nameInput.setError("Venue capacity name is required");
                valid = false;
            }

            if (valid){

                int intCap = Integer.parseInt(capacity);

                Venue venue = VenueFactory.newVenue(name, intCap);

                api.saveTournament(venue)
                        .enqueue(new Callback<Venue>() {
                            @Override
                            public void onResponse(Call<Venue> call, Response<Venue> response) {
                                Toast.makeText(getApplicationContext(), "Save Successfully", Toast.LENGTH_SHORT).show();
                                onResume();
                                newVenueDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<Venue> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                                onResume();
                                newVenueDialog.dismiss();
                            }
                        });

            }

        });

        cancel.setOnClickListener(v->{
            newVenueDialog.dismiss();
        });

    }

    public void showSignOutDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Sign out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    protected void onResume(){
        super.onResume();
        loadVenues();
    }
}