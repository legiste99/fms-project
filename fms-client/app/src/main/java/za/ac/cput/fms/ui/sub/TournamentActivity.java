package za.ac.cput.fms.ui.sub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.team.Team;
import za.ac.cput.fms.model.teamStats.TeamStats;
import za.ac.cput.fms.model.tournament.Tournament;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.TeamApi;
import za.ac.cput.fms.retrofit.api.TournamentApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.ui.TournamentsActivity;
import za.ac.cput.fms.views.teams.TeamAdapter;
import za.ac.cput.fms.views.teamstats.TeamStatsAdapter;

public class TournamentActivity extends AppCompatActivity {

    private MaterialCardView standingsCardButton, teamsCardButton, fixturesCardButton;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog standingTableDialog, addTeamDialog;
    private MaterialTextView tournamentTitle;
    private Button okButton;
    private RecyclerView tableRecyclerView;
    private RecyclerView teamsToAddRecyclerView;
    private Button addTeamToTournamentButton, cancel, back, signOut;
    private TextView current, max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity_tournament);

        back = findViewById(R.id.tournament_back_to_tournaments_button);
        back.setOnClickListener(v->{
            finish();
        });

        signOut = findViewById(R.id.tournament_sign_out_button);
        signOut.setOnClickListener(v->{
            showSignOutDialog();
        });

        tournamentTitle = findViewById(R.id.tournament_name_text_view);
        tournamentTitle.setText(getIntent().getStringExtra("tournament_name"));

        standingsCardButton = findViewById(R.id.tournament_standings_card_button);
        standingsCardButton.setOnClickListener(v->{
            viewStandingTableDialog();
        });

        fixturesCardButton = findViewById(R.id.tournament_fixtures_card_button);
        fixturesCardButton.setOnClickListener(v->{
            Intent intent = new Intent(this, TournamentFixturesActivity.class);
            intent.putExtra("tournament_id", getIntent().getStringExtra("tournament_id"));
            intent.putExtra("tournament_name", getIntent().getStringExtra("tournament_name"));
            startActivity(intent);
        });

        addTeamToTournamentButton = findViewById(R.id.dash_add_team_to_tournament);
        addTeamToTournamentButton.setOnClickListener(v->{

            openSelectTeamToAddDialog();

        });


    }

    private void openSelectTeamToAddDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate( R.layout.dialog_teams_list, null);

        teamsToAddRecyclerView = view.findViewById(R.id.team_to_add_list_recycler_view);
        teamsToAddRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        cancel = view.findViewById(R.id.cancel_select_team_button);

        dialogBuilder.setView(view);
        addTeamDialog = dialogBuilder.create();
        addTeamDialog.show();

        loadTeams();

        cancel.setOnClickListener(v->{
            addTeamDialog.dismiss();
        });

    }

    private void loadTeams() {

        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        api.getAllTeams().enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                populateTeamsListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Toast.makeText(TournamentActivity.this, "Failed to load Teams", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populateTeamsListView(List<Team> teamList) {

        RetrofitService service = new RetrofitService();
        TournamentApi api = service.getRetrofit().create(TournamentApi.class);

        TeamAdapter adapter = new TeamAdapter(teamList);
        teamsToAddRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+teamList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {

            api.assignTeamToTournament(teamList.get(position).getId(), getIntent().getStringExtra("tournament_id"))
                    .enqueue(new Callback<Tournament>() {
                        @Override
                        public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                            Toast.makeText(TournamentActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            onResume();
                            addTeamDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Tournament> call, Throwable t) {
                            Toast.makeText(TournamentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            onResume();
                            addTeamDialog.dismiss();
                        }
                    });

        });
    }

    public void viewStandingTableDialog(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_team_standings_table, null);

        tableRecyclerView = view.findViewById(R.id.standings_table_recycler_view);
        tableRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dialogBuilder.setView(view);
        standingTableDialog = dialogBuilder.create();
        standingTableDialog.show();

        loadTables();

        okButton = view.findViewById(R.id.table_ok_button);
        okButton.setOnClickListener(v->{
            standingTableDialog.dismiss();
        });

    }

    private void loadTables() {

        RetrofitService service = new RetrofitService();
        TournamentApi api = service.getRetrofit().create(TournamentApi.class);

        api.getTeamStatsByTournamentId(getIntent().getStringExtra("tournament_id"))
                .enqueue(new Callback<List<TeamStats>>() {
                    @Override
                    public void onResponse(Call<List<TeamStats>> call, Response<List<TeamStats>> response) {
                        populateStandingsTableView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<TeamStats>> call, Throwable t) {
                        Toast.makeText(TournamentActivity.this, "Failed to load TeamStats", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void populateStandingsTableView(List<TeamStats> teamStatsList) {

        RetrofitService service = new RetrofitService();
        TournamentApi api = service.getRetrofit().create(TournamentApi.class);

        TeamStatsAdapter adapter = new TeamStatsAdapter(teamStatsList);
        tableRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+teamStatsList.size(), Toast.LENGTH_SHORT).show();

    }

    private void updateNumberOfTeams() {

        current = findViewById(R.id.tournament_current_teams);
        max = findViewById(R.id.tournament_max_teams);

        RetrofitService service = new RetrofitService();
        TournamentApi api = service.getRetrofit().create(TournamentApi.class);

        api.getTournamentById(getIntent().getStringExtra("tournament_id"))
                .enqueue(new Callback<List<Tournament>>() {
                    @Override
                    public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                        Toast.makeText(TournamentActivity.this, "updated", Toast.LENGTH_SHORT).show();
                        current.setText(String.valueOf(response.body().get(0).getNumberOfTeams()));
                        max.setText(String.valueOf(response.body().get(0).getMaxNumberOfTeams()));
                    }

                    @Override
                    public void onFailure(Call<List<Tournament>> call, Throwable t) {

                    }
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
    protected void onResume() {
        super.onResume();
        updateNumberOfTeams();
    }

}