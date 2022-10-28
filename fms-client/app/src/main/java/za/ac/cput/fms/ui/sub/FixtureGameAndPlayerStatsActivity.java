package za.ac.cput.fms.ui.sub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.gameStats.GameStats;
import za.ac.cput.fms.model.playerStats.PlayerStats;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.GameStatsApi;
import za.ac.cput.fms.retrofit.api.PlayerStatsApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.playerstats.PlayerStatsAdapter;

public class FixtureGameAndPlayerStatsActivity extends AppCompatActivity {

    private TextView teamName, teamGoals, teamYellows, teamReds;
    private RecyclerView playerStatsRecyclerView;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog operationsDialog;
    private Button close, scored, receivedYellow, receivedRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture_game_and_player_stats);

        teamName = findViewById(R.id.team_name);
        teamName.setText(getIntent().getStringExtra("team_name"));

        teamGoals = findViewById(R.id.team_goals);
        teamYellows = findViewById(R.id.team_yellows);
        teamReds = findViewById(R.id.team_reds);

        playerStatsRecyclerView = findViewById(R.id.fixture_player_stats_recycler_view);
        playerStatsRecyclerView.setLayoutManager(new GridLayoutManager(this ,2));

    }

    private void updateTeamOneView() {

        RetrofitService service = new RetrofitService();
        GameStatsApi api = service.getRetrofit().create(GameStatsApi.class);

        String fixtureId = getIntent().getStringExtra("fixture_id");
        String teamId = getIntent().getStringExtra("team_id");

        api.getGameStatByFixtureIdAndTeamId(fixtureId, teamId).enqueue(new Callback<GameStats>() {
            @Override
            public void onResponse(Call<GameStats> call, Response<GameStats> response) {
                String goals = String.valueOf(response.body().getGoalsScored());
                String yellows = String.valueOf(response.body().getYellowCards());
                String reds = String.valueOf(response.body().getRedCards());

                teamGoals.setText(goals);
                teamYellows.setText(yellows);
                teamReds.setText(reds);

            }

            @Override
            public void onFailure(Call<GameStats> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "update failed t1", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadPlayerStats(){

        RetrofitService service = new RetrofitService();
        PlayerStatsApi api = service.getRetrofit().create(PlayerStatsApi.class);

        String fixtureId = getIntent().getStringExtra("fixture_id");
        String teamId = getIntent().getStringExtra("team_id");

        api.getFixtureTeamPlayerStats(fixtureId, teamId).enqueue(new Callback<List<PlayerStats>>() {
            @Override
            public void onResponse(Call<List<PlayerStats>> call, Response<List<PlayerStats>> response) {
                populatePlayerStatsListView((List<PlayerStats>) response.body());
            }

            @Override
            public void onFailure(Call<List<PlayerStats>> call, Throwable t) {
                Toast.makeText(FixtureGameAndPlayerStatsActivity.this, "Failed to load player starts", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populatePlayerStatsListView(List<PlayerStats> playerStatsList){
        PlayerStatsAdapter adapter = new PlayerStatsAdapter(playerStatsList);
        playerStatsRecyclerView.setAdapter(adapter);

        adapter.OnRecyclerViewClickListener(position -> {
            String playerId = playerStatsList.get(position).getPlayerId();
            openOperationDialog(playerId);

        });
    }

    private void openOperationDialog(String playerId) {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_operations, null);

        scored = view.findViewById(R.id.player_scored);
        receivedYellow = view.findViewById(R.id.player_got_yellow);
        receivedRed = view.findViewById(R.id.player_got_red);
        close = view.findViewById(R.id.close_operations_button);

        dialogBuilder.setView(view);
        operationsDialog = dialogBuilder.create();
        operationsDialog.show();

        close.setOnClickListener(v->{
            operationsDialog.dismiss();
            onResume();
        });


        scored.setOnClickListener(v->{

            showConfirmActionMessage(playerId);

        });


    }

    public void showConfirmActionMessage(String playerId){
        new AlertDialog.Builder(this)
                .setTitle("Player Scored A Goal?")
                .setMessage("Action can not be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {

                    RetrofitService service = new RetrofitService();
                    GameStatsApi api = service.getRetrofit().create(GameStatsApi.class);

                    String tournamentId = getIntent().getStringExtra("tournament_id");
                    String fixtureId = getIntent().getStringExtra("fixture_id");
                    String teamId = getIntent().getStringExtra("team_id");

                    api.teamGoalScoredUpdate(tournamentId, fixtureId, teamId, playerId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(FixtureGameAndPlayerStatsActivity.this, "Scored success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(FixtureGameAndPlayerStatsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateTeamOneView();
        loadPlayerStats();
    }

}