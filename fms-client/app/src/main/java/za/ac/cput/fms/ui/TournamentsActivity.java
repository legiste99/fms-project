package za.ac.cput.fms.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import za.ac.cput.fms.model.tournament.Tournament;
import za.ac.cput.fms.model.tournament.TournamentFactory;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.TournamentApi;
import za.ac.cput.fms.ui.sub.TournamentActivity;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.tournaments.TournamentAdapter;

public class TournamentsActivity extends AppCompatActivity {

    private Button backButton, signOutButton, newTournamentButton;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog newTournamentDialog;
    private TextInputEditText nameInput, maxInput;
    private Button save, cancel;
    private RecyclerView tournamentsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);

        backButton = findViewById(R.id.tournaments_back_to_dashboard_button);
        backButton.setOnClickListener(v->{
            finish();
        });

        signOutButton = findViewById(R.id.tournaments_sign_out_button);
        signOutButton.setOnClickListener(v->{
            showSignOutDialog();
        });

        newTournamentButton = findViewById(R.id.create_new_tournament_button);
        newTournamentButton.setOnClickListener(v->{

            createNewTournamentDialog();
        });

        tournamentsRecyclerView = findViewById(R.id.tournaments_recycler_view);
        tournamentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadTournaments(){
        RetrofitService service = new RetrofitService();
        TournamentApi api = service.getRetrofit().create(TournamentApi.class);

        api.getAllTournaments()
                .enqueue(new Callback<List<Tournament>>() {
                    @Override
                    public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                        populateListView(response.body());

                    }

                    @Override
                    public void onFailure(Call<List<Tournament>> call, Throwable t) {
                        Toast.makeText(TournamentsActivity.this, "Failed to load Tournaments", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Tournament> tournamentList) {
        TournamentAdapter adapter = new TournamentAdapter(tournamentList);
        tournamentsRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+tournamentList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {
            Intent intent = new Intent(this, TournamentActivity.class);
            intent.putExtra("tournament_id", tournamentList.get(position).getId());
            intent.putExtra("tournament_name", tournamentList.get(position).getTournamentName());
            startActivity(intent);
        });
    }

    public void createNewTournamentDialog(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_new_tournament, null);

        nameInput = view.findViewById(R.id.new_tournament_name_input);
        maxInput = view.findViewById(R.id.new_tournament_max_input);
        save = view.findViewById(R.id.save_new_tournament);
        cancel = view.findViewById(R.id.cancel_new_tournament);

        dialogBuilder.setView(view);
        newTournamentDialog = dialogBuilder.create();
        newTournamentDialog.show();

        RetrofitService service = new RetrofitService();
        TournamentApi api = service.getRetrofit().create(TournamentApi.class);

        save.setOnClickListener(v->{

            Boolean valid = true;

            String name = Objects.requireNonNull(nameInput.getText()).toString();
            String max = Objects.requireNonNull(maxInput.getText()).toString();

            if (TextUtils.isEmpty(name)){
                nameInput.setError("required");
                valid = false;
            }

            if (TextUtils.isEmpty(max)){
                maxInput.setError("required");
                valid = false;
            }

            if (valid){

                int intMax = Integer.parseInt(max);

                Tournament tournament = TournamentFactory.newTournament(
                        name, intMax
                );

                api.saveTournament(tournament).enqueue(new Callback<Tournament>() {
                    @Override
                    public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                        Toast.makeText(getApplicationContext(), "Save Successfully", Toast.LENGTH_SHORT).show();
                        onResume();
                        newTournamentDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Tournament> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

        cancel.setOnClickListener(v->{
            newTournamentDialog.dismiss();
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
        loadTournaments();
    }

}