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
import za.ac.cput.fms.model.team.Team;
import za.ac.cput.fms.model.team.TeamFactory;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.TeamApi;
import za.ac.cput.fms.ui.sub.TeamActivity;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.teams.TeamAdapter;

public class TeamsActivity extends AppCompatActivity {

    private RecyclerView teamsRecyclerView;
    private TextInputEditText teamNameInput;
    private Button save, cancel;
    private Button registerNewTeamButton, back, signOut;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog newTeamDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        back = findViewById(R.id.teams_back_to_dashboard_button);
        back.setOnClickListener(v->{
            finish();
        });

        signOut = findViewById(R.id.teams_sign_out_button);
        signOut.setOnClickListener(v->{
            showSignOutDialog();
        });

        teamsRecyclerView = findViewById(R.id.teams_recycler_view);
        teamsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        registerNewTeamButton = findViewById(R.id.create_new_team_button);
        registerNewTeamButton.setOnClickListener(v->{
            openCreateNewTeamDialog();
        });

    }

    private void openCreateNewTeamDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_new_team, null);

        teamNameInput = view.findViewById(R.id.new_team_name_input);
        save = view.findViewById(R.id.save_new_team);
        cancel = view.findViewById(R.id.cancel_new_team);

        dialogBuilder.setView(view);
        newTeamDialog = dialogBuilder.create();
        newTeamDialog.show();

        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);



        save.setOnClickListener(v->{

            Boolean valid = true;

            String name = Objects.requireNonNull(teamNameInput.getText()).toString();

            if (TextUtils.isEmpty(name)){
                teamNameInput.setError("Team name is required");
                valid = false;
            }

            if(valid){

                Team team = TeamFactory.newTeam(name);

                api.saveTeam(team).enqueue(new Callback<Team>() {
                    @Override
                    public void onResponse(Call<Team> call, Response<Team> response) {
                        Toast.makeText(getApplicationContext(), "Save Successfully", Toast.LENGTH_SHORT).show();
                        onResume();
                        newTeamDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Team> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                        onResume();
                        newTeamDialog.dismiss();
                    }
                });

            }

        });

        cancel.setOnClickListener(v->{
            newTeamDialog.dismiss();
        });

    }

    private void loadTeams(){

        RetrofitService service = new RetrofitService();
        TeamApi api = service.getRetrofit().create(TeamApi.class);

        api.getAllTeams()
                .enqueue(new Callback<List<Team>>() {
                    @Override
                    public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Team>> call, Throwable t) {
                        Toast.makeText(TeamsActivity.this, "Failed to load Teams", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void populateListView(List<Team> teamList) {
        TeamAdapter adapter = new TeamAdapter(teamList);
        teamsRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved"+teamList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {
            Intent intent = new Intent(this, TeamActivity.class);
            intent.putExtra("team_id", teamList.get(position).getId());
            intent.putExtra("team_name", teamList.get(position).getTeamName());
            startActivity(intent);
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
        loadTeams();
    }

}