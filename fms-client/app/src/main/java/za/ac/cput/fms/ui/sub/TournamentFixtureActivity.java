package za.ac.cput.fms.ui.sub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.gameStats.GameStats;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.GameStatsApi;

public class TournamentFixtureActivity extends AppCompatActivity {

    private TextView tournamentName, teamAName, teamAGoals, teamAYellows, teamAReds;
    private TextView teamBName, teamBGoals, teamBYellows, teamBReds;
    private LinearLayout teamOneLayout, teamTwoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_fixture);

        teamOneLayout = findViewById(R.id.team_one_layout);
        teamOneLayout.setOnClickListener(v->{
            Intent intent = new Intent(this, FixtureGameAndPlayerStatsActivity.class);
            intent.putExtra("tournament_id", getIntent().getStringExtra("tournament_id"));
            intent.putExtra("fixture_id", getIntent().getStringExtra("fixture_id"));
            intent.putExtra("team_id", getIntent().getStringExtra("team_a_id"));
            intent.putExtra("team_name", getIntent().getStringExtra("team_a_name"));
            startActivity(intent);
        });

        teamTwoLayout = findViewById(R.id.team_two_layout);
        teamTwoLayout.setOnClickListener(v->{
            Intent intent = new Intent(this, FixtureGameAndPlayerStatsActivity.class);
            intent.putExtra("tournament_id", getIntent().getStringExtra("tournament_id"));
            intent.putExtra("fixture_id", getIntent().getStringExtra("fixture_id"));
            intent.putExtra("team_id", getIntent().getStringExtra("team_b_id"));
            intent.putExtra("team_name", getIntent().getStringExtra("team_b_name"));
            startActivity(intent);
        });

        tournamentName = findViewById(R.id.tour_name);
        tournamentName.setText(getIntent().getStringExtra("tournament_name"));

        teamAName = findViewById(R.id.game_stat_team_a_name);
        teamAName.setText(getIntent().getStringExtra("team_a_name"));

        teamBName = findViewById(R.id.game_stat_team_b_name);
        teamBName.setText(getIntent().getStringExtra("team_b_name"));

        teamAGoals = findViewById(R.id.game_stat_team_one_goals_scored);
        teamAYellows = findViewById(R.id.game_stat_team_one_yellows);
        teamAReds = findViewById(R.id.game_stat_team_one_reds);

        teamBGoals = findViewById(R.id.game_stat_team_two_goals_scored);
        teamBYellows = findViewById(R.id.game_stat_team_two_yellows);
        teamBReds = findViewById(R.id.game_stat_team_two_reds);
    }

    private void updateTeamOneView() {

        RetrofitService service = new RetrofitService();
        GameStatsApi api = service.getRetrofit().create(GameStatsApi.class);

        String fixtureId = getIntent().getStringExtra("fixture_id");
        String teamId = getIntent().getStringExtra("team_a_id");

        api.getGameStatByFixtureIdAndTeamId(fixtureId, teamId).enqueue(new Callback<GameStats>() {
            @Override
            public void onResponse(Call<GameStats> call, Response<GameStats> response) {
                String goals = String.valueOf(response.body().getGoalsScored());
                String yellows = String.valueOf(response.body().getYellowCards());
                String reds = String.valueOf(response.body().getRedCards());

                teamAGoals.setText(goals);
                teamAYellows.setText(yellows);
                teamAReds.setText(reds);

                Toast.makeText(TournamentFixtureActivity.this, "updated t1", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<GameStats> call, Throwable t) {
                Toast.makeText(TournamentFixtureActivity.this, "update failed t1", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateTeamTwoView() {

        RetrofitService service = new RetrofitService();
        GameStatsApi api = service.getRetrofit().create(GameStatsApi.class);

        String fixtureId = getIntent().getStringExtra("fixture_id");
        String teamId = getIntent().getStringExtra("team_b_id");

        api.getGameStatByFixtureIdAndTeamId(fixtureId, teamId).enqueue(new Callback<GameStats>() {
            @Override
            public void onResponse(Call<GameStats> call, Response<GameStats> response) {
                String goals = String.valueOf(response.body().getGoalsScored());
                String yellows = String.valueOf(response.body().getYellowCards());
                String reds = String.valueOf(response.body().getRedCards());

                teamBGoals.setText(goals);
                teamBYellows.setText(yellows);
                teamBReds.setText(reds);
                Toast.makeText(TournamentFixtureActivity.this, "updated t2", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<GameStats> call, Throwable t) {
                Toast.makeText(TournamentFixtureActivity.this, "update failed t2", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        updateTeamOneView();
        updateTeamTwoView();
    }

}