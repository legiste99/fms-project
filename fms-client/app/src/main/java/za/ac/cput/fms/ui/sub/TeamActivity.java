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
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.manager.Manager;
import za.ac.cput.fms.model.player.Player;
import za.ac.cput.fms.model.team.Team;
import za.ac.cput.fms.model.venue.Venue;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.ManagerApi;
import za.ac.cput.fms.retrofit.api.PlayerApi;
import za.ac.cput.fms.retrofit.api.TeamApi;
import za.ac.cput.fms.retrofit.api.VenueApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.ui.TournamentsActivity;
import za.ac.cput.fms.views.managers.ManagerAdapter;
import za.ac.cput.fms.views.players.PlayerAdapter;
import za.ac.cput.fms.views.venues.VenueAdapter;

public class TeamActivity extends AppCompatActivity {

    private MaterialTextView teamTitle, managerHeader, venueHeader;
    private RecyclerView teamPlayersRecyclerView, teamManagerRecyclerView, teamVenueRecyclerView;
    private Button registerPlayerToTeamButton, registerManagerToTeamButton, registerVenueToTeamButton;
    private Button cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog playerListDialog, managerListDialog, venueListDialog;
    private RecyclerView playerListRecyclerView, managerListRecyclerView, venueListRecyclerView;
    private Button backButton, signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity_team);


        backButton = findViewById(R.id.team_back_to_tournaments_button);
        backButton.setOnClickListener(v->{
            Intent intent = new Intent(this, TournamentsActivity.class);
            startActivity(intent);
            finish();
        });

        signOutButton = findViewById(R.id.team_sign_out_button);
        signOutButton.setOnClickListener(v->{
            showSignOutDialog();
        });

        teamTitle = findViewById(R.id.team_team_name);
        teamTitle.setText(getIntent().getStringExtra("team_name"));

        teamManagerRecyclerView = findViewById(R.id.team_manager_recycler_view);
        teamManagerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        teamVenueRecyclerView = findViewById(R.id.team_venue_recycler_view);
        teamVenueRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        teamPlayersRecyclerView = findViewById(R.id.team_players_recycler_view);
        teamPlayersRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        registerPlayerToTeamButton = findViewById(R.id.register_player_to_team_button);
        registerPlayerToTeamButton.setOnClickListener(v->{
            openPlayerListDialogToAddPlayerToTeam();
        });

        registerManagerToTeamButton = findViewById(R.id.register_manager_to_team_button);
        registerManagerToTeamButton.setOnClickListener(v->{
            openManagerListDialogToAddManagerToTeam();
        });

        registerVenueToTeamButton = findViewById(R.id.register_venue_to_team_button);
        registerVenueToTeamButton.setOnClickListener(v->{
            openVenueListDialogToAddVenueToTeam();
        });

    }

    private void openVenueListDialogToAddVenueToTeam() {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_venues_list, null);

        venueListRecyclerView = view.findViewById(R.id.venue_to_add_list_recycler_view);
        venueListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cancel = view.findViewById(R.id.cancel_select_venue_button);

        dialogBuilder.setView(view);
        venueListDialog = dialogBuilder.create();
        venueListDialog.show();

        loadVenuesToAddToTeam();

        cancel.setOnClickListener(v->{
            venueListDialog.dismiss();
        });

    }

    private void openManagerListDialogToAddManagerToTeam(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_managers_list, null);

        managerListRecyclerView = view.findViewById(R.id.manager_list_recycler_view);
        managerListRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cancel = view.findViewById(R.id.cancel_select_manager_button);

        dialogBuilder.setView(view);
        managerListDialog = dialogBuilder.create();
        managerListDialog.show();

        loadManagersToAddToTeam();

        cancel.setOnClickListener(v->{
            managerListDialog.dismiss();
        });
    }

    private void openPlayerListDialogToAddPlayerToTeam(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_player_list, null);

        playerListRecyclerView  = view.findViewById(R.id.player_list_recycler_view);
        playerListRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        cancel = view.findViewById(R.id.cancel_select_player_button);

        dialogBuilder.setView(view);
        playerListDialog = dialogBuilder.create();
        playerListDialog.show();

        loadPlayersToAddToTeam();

        cancel.setOnClickListener(v->{
            playerListDialog.dismiss();
        });


    }

    private void loadTeamPlayers() {

        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        api.getPlayersByTeamId(getIntent().getStringExtra("team_id")).enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                populateTeamPlayersListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Failed to load team players", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTeamVenue(){
        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        api.getVenueByTeamId(getIntent().getStringExtra("team_id")).enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                populateTeamVenueListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Failed to load team venue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void  loadTeamManager(){
        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        api.getManagerByTeamId(getIntent().getStringExtra("team_id")).enqueue(new Callback<List<Manager>>() {
            @Override
            public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
                populateTeamManagerListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Manager>> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Failed to load team manager", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateTeamPlayersListView(List<Player> playerList) {

        PlayerAdapter adapter = new PlayerAdapter(playerList);
        teamPlayersRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+playerList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {
            Toast.makeText(this, "viewing "+playerList.get(position).getFirstName(), Toast.LENGTH_SHORT).show();
        });

    }

    private void populateTeamVenueListView(List<Venue> venueList){
        VenueAdapter adapter = new VenueAdapter(venueList);
        teamVenueRecyclerView.setAdapter(adapter);

        adapter.OnRecycleViewClickListener(position -> {
            Toast.makeText(this, "viewing venue...", Toast.LENGTH_SHORT).show();
        });
    }

    private void populateTeamManagerListView(List<Manager> managerList){

        ManagerAdapter adapter = new ManagerAdapter(managerList);
        teamManagerRecyclerView.setAdapter(adapter);

        adapter.OnRecyclerViewClickListener(position -> {
            Toast.makeText(this, "viewing manager", Toast.LENGTH_SHORT).show();
        });

    }

    private void loadManagersToAddToTeam(){
        RetrofitService service = new RetrofitService();
        ManagerApi api = service.getRetrofit().create(ManagerApi.class);

        api.getAllManagers().enqueue(new Callback<List<Manager>>() {
            @Override
            public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
                populateManagerListViewDialogToAddManagerToTeam(response.body());
            }

            @Override
            public void onFailure(Call<List<Manager>> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Failed to load managers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadVenuesToAddToTeam(){

        RetrofitService service = new RetrofitService();
        VenueApi api = service.getRetrofit().create(VenueApi.class);

        api.getAllVenues().enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                populateVenueListViewDialogToAddVenueToTeam(response.body());
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Failed to load venues", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadPlayersToAddToTeam() {

        RetrofitService service = new RetrofitService();
        PlayerApi api = service.getRetrofit().create(PlayerApi.class);

        api.getAllPlayers().enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                populatePlayersListViewDialogToAddPlayerToTeam(response.body());
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Failed to load players", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatePlayersListViewDialogToAddPlayerToTeam(List<Player> playerList) {

        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        PlayerAdapter adapter = new PlayerAdapter(playerList);
        playerListRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+playerList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {

            api.assignPlayerToTeam(playerList.get(position).getId(), getIntent().getStringExtra("team_id"))
                    .enqueue(new Callback<Team>() {
                        @Override
                        public void onResponse(Call<Team> call, Response<Team> response) {
                            Toast.makeText(TeamActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            onResume();
                            playerListDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Team> call, Throwable t) {
                            Toast.makeText(TeamActivity.this, "Failed to add player", Toast.LENGTH_SHORT).show();
                            onResume();
                            playerListDialog.dismiss();
                        }
                    });
        });
    }

    private void populateVenueListViewDialogToAddVenueToTeam(List<Venue> venueList){

        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        VenueAdapter adapter = new VenueAdapter(venueList);
        venueListRecyclerView.setAdapter(adapter);

        adapter.OnRecycleViewClickListener(position -> {
            String venueId = venueList.get(position).getId();
            String teamId = getIntent().getStringExtra("team_id");
            api.assignVenueToTeam(venueId, teamId).enqueue(new Callback<Team>() {
                @Override
                public void onResponse(Call<Team> call, Response<Team> response) {
                    Toast.makeText(TeamActivity.this, "venue assigned", Toast.LENGTH_SHORT).show();
                    onResume();
                    venueListDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Team> call, Throwable t) {
                    Toast.makeText(TeamActivity.this, "failed to assign venue", Toast.LENGTH_SHORT).show();
                    onResume();
                    venueListDialog.dismiss();
                }
            });
        });

    }

    private void populateManagerListViewDialogToAddManagerToTeam(List<Manager> managerList){

        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        ManagerAdapter adapter = new ManagerAdapter(managerList);
        managerListRecyclerView.setAdapter(adapter);

        adapter.OnRecyclerViewClickListener(position -> {

            String managerId = managerList.get(position).getId();
            String teamId = getIntent().getStringExtra("team_id");

            api.assignManagerToTeam(managerId, teamId)
                    .enqueue(new Callback<Team>() {
                        @Override
                        public void onResponse(Call<Team> call, Response<Team> response) {
                            Toast.makeText(TeamActivity.this, "manager assigned", Toast.LENGTH_SHORT).show();
                            onResume();
                            managerListDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Team> call, Throwable t) {
                            Toast.makeText(TeamActivity.this, "Failed to add manager", Toast.LENGTH_SHORT).show();
                            onResume();
                            managerListDialog.dismiss();
                        }
                    });
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
        loadTeamPlayers();
        loadTeamManager();
        loadTeamVenue();
    }

}