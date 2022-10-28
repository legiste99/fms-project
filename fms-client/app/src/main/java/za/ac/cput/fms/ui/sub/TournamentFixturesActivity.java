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

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.fixture.Fixture;
import za.ac.cput.fms.model.fixture.FixtureFactory;
import za.ac.cput.fms.model.referee.Referee;
import za.ac.cput.fms.model.team.Team;
import za.ac.cput.fms.model.tournament.Tournament;
import za.ac.cput.fms.model.venue.Venue;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.FixtureApi;
import za.ac.cput.fms.retrofit.api.RefereeApi;
import za.ac.cput.fms.retrofit.api.TeamApi;
import za.ac.cput.fms.retrofit.api.TournamentApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.fixtures.FixtureAdapter;
import za.ac.cput.fms.views.referees.RefereeAdapter;
import za.ac.cput.fms.views.teams.TeamAdapter;
import za.ac.cput.fms.views.venues.VenueAdapter;

public class TournamentFixturesActivity extends AppCompatActivity {

    private Button back, signOut, createNewFixtureButton;
    private RecyclerView fixturesRecyclerView;
    private TextView tourName;
    private Button cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog teamListDialog, refereeListDialog, venueListDialog;
    private RecyclerView teamListRecyclerView, refereeListRecyclerView, venueListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_fixtures);

        tourName = findViewById(R.id.tour_name);
        tourName.setText(getIntent().getStringExtra("tournament_name"));

        back = findViewById(R.id.tournament_fixtures_back_to_tournament_button);
        back.setOnClickListener(v->{
            finish();
        });

        signOut = findViewById(R.id.tournament_fixture_sign_out_button);
        signOut.setOnClickListener(v->{
            showSignOutDialog();
        });

        createNewFixtureButton = findViewById(R.id.create_new_fixture_button);
        createNewFixtureButton.setOnClickListener(v->{
            showConfirmMessage();
        });

        fixturesRecyclerView = findViewById(R.id.fixtures_recycler_view);
        fixturesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void showConfirmMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Create New Fixture")
                .setMessage("Are you sure you create a new Fixture?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    Fixture fixture = FixtureFactory.newFixture();

                    Retrofit service = new RetrofitService().getRetrofit();
                    FixtureApi api = service.create(FixtureApi.class);

                    api.saveFixture(fixture).enqueue(new Callback<Fixture>() {
                        @Override
                        public void onResponse(Call<Fixture> call, Response<Fixture> response) {
                            Toast.makeText(TournamentFixturesActivity.this, "Fixture Created", Toast.LENGTH_SHORT).show();

                            TournamentApi api = service.create(TournamentApi.class);
                            api.addFixtureToTournament(getIntent().getStringExtra("tournament_id"), fixture.getId())
                                    .enqueue(new Callback<Tournament>() {
                                        @Override
                                        public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                                            Toast.makeText(TournamentFixturesActivity.this, "Added to Tournament", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            onResume();
                                        }

                                        @Override
                                        public void onFailure(Call<Tournament> call, Throwable t) {
                                            Toast.makeText(TournamentFixturesActivity.this, "Failed to add Fixture to Tournament", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        @Override
                        public void onFailure(Call<Fixture> call, Throwable t) {
                            Toast.makeText(TournamentFixturesActivity.this, "Failed to create a fixture", Toast.LENGTH_SHORT).show();
                        }
                    });

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void loadFixtures() {
        RetrofitService service = new RetrofitService();
        FixtureApi api = service. getRetrofit().create(FixtureApi.class);

        api.getFixturesByTournamentId(getIntent().getStringExtra("tournament_id")).enqueue(new Callback<List<Fixture>>() {
            @Override
            public void onResponse(Call<List<Fixture>> call, Response<List<Fixture>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Fixture>> call, Throwable t) {
                Toast.makeText(TournamentFixturesActivity.this, "Failed to load fixtures", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateListView(List<Fixture> fixtureList) {
        FixtureAdapter adapter = new FixtureAdapter(fixtureList);
        fixturesRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+fixtureList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {

            Fixture fixture = fixtureList.get(position);

            if (fixture.getFixtureTeams().size() == 0){
                showConfirmMessageToAddTeam("Add Team One", fixture.getId());
            }
            else if (fixture.getFixtureTeams().size() == 1){
                showConfirmMessageToAddTeam("Add Team Two", fixture.getId());
            }
            else if(fixture.getFixtureReferee().size() == 0){
                showConfirmMessageToAddReferee(fixture.getId());
            }
            else if(fixture.getFixtureVenue().size() == 0){
                showConfirmMessageToAddVenue(fixture.getId());
            }
            else {
                Intent intent = new Intent(this, TournamentFixtureActivity.class);
                intent.putExtra("tournament_name", getIntent().getStringExtra("tournament_name"));
                intent.putExtra("tournament_id", getIntent().getStringExtra("tournament_id"));
                intent.putExtra("fixture_id", fixture.getId() );
                intent.putExtra( "team_a_id", fixture.getTeamAId());
                intent.putExtra("team_b_id", fixture.getTeamBid());
                intent.putExtra("team_a_name", fixture.getTeamAName());
                intent.putExtra("team_b_name", fixture.getTeamBName());
                startActivity(intent);
            }

        });

    }

/*    private void showConfirmMessageToAddVenue(String fixtureId) {
        new AlertDialog.Builder(this)
                .setTitle("Add Venue")
                .setMessage("Would you like to add a Venue to this Fixture?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    openVenueDialog(fixtureId);
                    onResume();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openVenueDialog(String fixtureId) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_venues_list, null);

        venueListRecyclerView = view.findViewById(R.id.venue_to_add_list_recycler_view);
        venueListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cancel = view.findViewById(R.id.cancel_select_venue_button);

        dialogBuilder.setView(view);
        venueListDialog = dialogBuilder.create();
        venueListDialog.show();

        loadVenues(fixtureId);

        cancel.setOnClickListener(v->{
            venueListDialog.dismiss();
        });
    }

    private void loadVenues(String fixtureId) {
        RetrofitService service = new RetrofitService();
        FixtureApi api = service.getRetrofit().create(FixtureApi.class);

        api.getVenuesByFixtureId(fixtureId).enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                populateVenueList(response.body(), fixtureId);
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                Toast.makeText(TournamentFixturesActivity.this, "Failed to load venues", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateVenueList(List<Venue> venueList, String fixtureId) {
        RetrofitService service = new RetrofitService();
        FixtureApi api = service.getRetrofit().create(FixtureApi.class);

        VenueAdapter adapter = new VenueAdapter(venueList);
        venueListRecyclerView.setAdapter(adapter);

        adapter.OnRecycleViewClickListener(position -> {
            api.assignVenueToFixture(venueList.get(position).getId(), fixtureId)
                    .enqueue(new Callback<Fixture>() {
                        @Override
                        public void onResponse(Call<Fixture> call, Response<Fixture> response) {
                            Toast.makeText(TournamentFixturesActivity.this, "Venue added", Toast.LENGTH_SHORT).show();
                            onResume();
                            venueListDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Fixture> call, Throwable t) {
                            Toast.makeText(TournamentFixturesActivity.this, "Failed to add assign Venue", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }*/

    private void showConfirmMessageToAddVenue(String fixtureId) {
        new AlertDialog.Builder(this)
                .setTitle("Add Venue")
                .setMessage("Would you like to add a Venue to this Fixture?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    openVenueDialog(fixtureId);
                    onResume();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openVenueDialog(String fixtureId) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_venues_list, null);

        venueListRecyclerView = view.findViewById(R.id.venue_to_add_list_recycler_view);
        venueListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cancel = view.findViewById(R.id.cancel_select_venue_button);

        dialogBuilder.setView(view);
        venueListDialog = dialogBuilder.create();
        venueListDialog.show();

        loadVenues(fixtureId);

        cancel.setOnClickListener(v->{
            venueListDialog.dismiss();
        });
    }

    private void loadVenues(String fixtureId) {
        RetrofitService service = new RetrofitService();
        FixtureApi api = service.getRetrofit().create(FixtureApi.class);

        api.getVenuesByFixtureId(fixtureId).enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {

                System.out.println("THis is The ERROR message");
                populateVenueList(response.body(), fixtureId);
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                Toast.makeText(TournamentFixturesActivity.this, "Failed to load venues", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateVenueList(List<Venue> venueList, String fixtureId) {

        RetrofitService service = new RetrofitService();
        FixtureApi api = service.getRetrofit().create(FixtureApi.class);

        VenueAdapter adapter = new VenueAdapter(venueList);
        venueListRecyclerView.setAdapter(adapter);

        adapter.OnRecycleViewClickListener(position -> {
            api.assignVenueToFixture(venueList.get(position).getId(), fixtureId)
                    .enqueue(new Callback<Fixture>() {
                        @Override
                        public void onResponse(Call<Fixture> call, Response<Fixture> response) {
                            Toast.makeText(TournamentFixturesActivity.this, "Venue added", Toast.LENGTH_SHORT).show();
                            onResume();
                            venueListDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Fixture> call, Throwable t) {
                            Toast.makeText(TournamentFixturesActivity.this, "Failed To add venue", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }

    private void showConfirmMessageToAddReferee(String fixtureId) {
        new AlertDialog.Builder(this)
                .setTitle("Add Referee")
                .setMessage("Would you like to add a Referee to this Fixture?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    openRefereeDialog(fixtureId);
                    onResume();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openRefereeDialog(String fixtureId) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_referees_list, null);

        refereeListRecyclerView = view.findViewById(R.id.referee_to_add_list_recycler_view);
        refereeListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cancel = view.findViewById(R.id.cancel_select_referee_button);

        dialogBuilder.setView(view);
        refereeListDialog = dialogBuilder.create();
        refereeListDialog.show();

        loadReferees(fixtureId);

        cancel.setOnClickListener(v->{
            refereeListDialog.dismiss();
        });
    }

    private void loadReferees(String fixtureId) {
        RetrofitService service = new RetrofitService();
        RefereeApi api = service.getRetrofit().create(RefereeApi.class);

        api.getAllReferees().enqueue(new Callback<List<Referee>>() {
            @Override
            public void onResponse(Call<List<Referee>> call, Response<List<Referee>> response) {
                populateRefereeList(response.body(), fixtureId);
            }

            @Override
            public void onFailure(Call<List<Referee>> call, Throwable t) {
                Toast.makeText(TournamentFixturesActivity.this, "Failed to load referees", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateRefereeList(List<Referee> refereeList, String fixtureId) {
        RetrofitService service = new RetrofitService();
        FixtureApi api = service.getRetrofit().create(FixtureApi.class);

        RefereeAdapter adapter = new RefereeAdapter(refereeList);
        refereeListRecyclerView.setAdapter(adapter);

        adapter.OnRecyclerViewClickListener(position -> {
            api.assignRefereeToFixture(refereeList.get(position).getId(), fixtureId)
                    .enqueue(new Callback<Fixture>() {
                        @Override
                        public void onResponse(Call<Fixture> call, Response<Fixture> response) {
                            Toast.makeText(TournamentFixturesActivity.this, "Referee added", Toast.LENGTH_SHORT).show();
                            onResume();
                            refereeListDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Fixture> call, Throwable t) {
                            Toast.makeText(TournamentFixturesActivity.this, "Failed To add Referee", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void showConfirmMessageToAddTeam(String title, String fixtureId) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Would you like to add a Team to this Fixture?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    openTeamListDialog(fixtureId);
                    onResume();
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void openTeamListDialog(String fixtureId) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_teams_list, null);

        teamListRecyclerView = view.findViewById(R.id.team_to_add_list_recycler_view);
        teamListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cancel = view.findViewById(R.id.cancel_select_team_button);

        dialogBuilder.setView(view);
        teamListDialog = dialogBuilder.create();
        teamListDialog.show();

        loadTeams(fixtureId);

        cancel.setOnClickListener(v->{
            teamListDialog.dismiss();
        });

    }

    private void loadTeams(String fixtureId) {
        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        api.getTeamsByTournamentId(getIntent().getStringExtra("tournament_id"))
                .enqueue(new Callback<List<Team>>() {
                    @Override
                    public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                        populateTeamList(response.body(), fixtureId);
                    }

                    @Override
                    public void onFailure(Call<List<Team>> call, Throwable t) {
                        Toast.makeText(TournamentFixturesActivity.this, "Failed To load teams", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateTeamList(List<Team> teamList, String fixtureId) {
        RetrofitService service = new RetrofitService();
        FixtureApi api = service.getRetrofit().create(FixtureApi.class);

        TeamAdapter adapter = new TeamAdapter(teamList);
        teamListRecyclerView.setAdapter(adapter);

        adapter.OnRecyclerViewClickListener(position -> {

            api.addTeamToFixture(teamList.get(position).getId(), fixtureId)
                    .enqueue(new Callback<Fixture>() {
                        @Override
                        public void onResponse(Call<Fixture> call, Response<Fixture> response) {
                            Toast.makeText(TournamentFixturesActivity.this, "Team added", Toast.LENGTH_SHORT).show();
                            onResume();
                            teamListDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Fixture> call, Throwable t) {
                            Toast.makeText(TournamentFixturesActivity.this, "failed to add team", Toast.LENGTH_SHORT).show();
                        }
                    });

        });
    }

    private void showSignOutDialog() {
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
        loadFixtures();

    }

}