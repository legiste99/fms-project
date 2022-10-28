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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.ac.cput.fms.R;
import za.ac.cput.fms.model.player.Player;
import za.ac.cput.fms.model.player.PlayerFactory;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.PlayerApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.players.PlayerAdapter;

public class PlayersActivity extends AppCompatActivity {

    private RecyclerView playerRecyclerView;
    private Button registerNewPlayerButton, back, signOut;
    private Button save, cancel, cancelViewing, editPlayer, deletePlayer;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog newPlayerDialog, viewPlayerDialog, editPlayerDialog;

    private TextInputEditText firstNameInput, middleNameInput, lastNameInput,
            ageInput, positionInput, positionNumberInput;

    private TextInputEditText editFirstNameInput, editMiddleNameInput, editLastNameInput,
            editAgeInput, editPositionInput, editPositionNumberInput;
    private TextView firstNameView, middleNameView, lastNameView, ageView, positionView, positionNumberView, goalsView, assistsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        back = findViewById(R.id.players_back_to_dashboard_button);
        back.setOnClickListener(v->{
            finish();
        });

        signOut = findViewById(R.id.players_sign_out_button);
        signOut.setOnClickListener(v->{
            showSignOutDialog();
        });

        playerRecyclerView = findViewById(R.id.players_recycler_view);
        playerRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        registerNewPlayerButton = findViewById(R.id.create_new_player_button);
        registerNewPlayerButton.setOnClickListener(v->{
            openNewPlayerDialog();
        });

    }

    private void loadPlayers() {

        RetrofitService service = new RetrofitService();
        PlayerApi api = service.getRetrofit().create(PlayerApi.class);

        api.getAllPlayers().enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Toast.makeText(PlayersActivity.this, "Failed to load Manager", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populateListView(List<Player> playerList) {

        PlayerAdapter adapter = new PlayerAdapter(playerList);
        playerRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+playerList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {
            Toast.makeText(this, "viewing "+playerList.get(position).getFirstName(), Toast.LENGTH_SHORT).show();

            openViewPlayerDialog(playerList.get(position).getId());

        });

    }

    private void openViewPlayerDialog(String playerId){

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_view_player, null);

        firstNameView = view.findViewById(R.id.player_first_name_text);
        middleNameView = view.findViewById(R.id.player_middle_name_text);
        lastNameView = view.findViewById(R.id.player_last_name_text);
        ageView = view.findViewById(R.id.player_age_text);
        positionView = view.findViewById(R.id.player_position_text);
        positionNumberView = view.findViewById(R.id.player_position_number_text);
        goalsView = view.findViewById(R.id.player_total_goals_text);
        assistsView = view.findViewById(R.id.player_total_assists_text);

        cancelViewing = view.findViewById(R.id.cancel_player_viewing);
        editPlayer = view.findViewById(R.id.edit_player_button);

        dialogBuilder.setView(view);
        viewPlayerDialog = dialogBuilder.create();
        viewPlayerDialog.show();

        editPlayer.setOnClickListener(v->{
            openEditPlayerDialog(playerId);
        });

        cancelViewing.setOnClickListener(v->{
            viewPlayerDialog.dismiss();
            onResume();
        });

        RetrofitService service = new RetrofitService();
        PlayerApi api = service.getRetrofit().create(PlayerApi.class);

        api.getPlayerById(playerId).enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                Player player = response.body().get(0);

                firstNameView.setText(player.getFirstName());
                middleNameView.setText(player.getMiddleName());
                lastNameView.setText(player.getLastName());
                ageView.setText(String.valueOf(player.getAge()));
                positionView.setText(player.getPosition());
                positionNumberView.setText(String.valueOf(player.getPositionNumber()));
                goalsView.setText(String.valueOf(player.getTotalGoalsScored()));
                assistsView.setText(String.valueOf(player.getTotalAssistsMade()));
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                firstNameView.setText("--");
                middleNameView.setText("--");
                lastNameView.setText("--");
                ageView.setText("--");
                positionView.setText("--");
                positionNumberView.setText("--");
                goalsView.setText("--");
                assistsView.setText("--");
                Toast.makeText(PlayersActivity.this, "Could not load player Information", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openEditPlayerDialog(String playerId){

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_edit_player, null);

        editFirstNameInput = view.findViewById(R.id.edit_player_first_name_input);
        editMiddleNameInput = view.findViewById(R.id.edit_player_middle_name_input);
        editLastNameInput = view.findViewById(R.id.edit_player_last_name_input);
        editAgeInput = view.findViewById(R.id.edit_player_age_input);
        editPositionInput = view.findViewById(R.id.edit_player_position_input);
        editPositionNumberInput = view.findViewById(R.id.edit_player_position_number_input);

        cancel = view.findViewById(R.id.cancel_player_editing);
        deletePlayer = view.findViewById(R.id.delete_player);
        save = view.findViewById(R.id.save_edited_player);

        dialogBuilder.setView(view);
        editPlayerDialog = dialogBuilder.create();
        editPlayerDialog.show();

        RetrofitService service = new RetrofitService();
        PlayerApi api = service.getRetrofit().create(PlayerApi.class);

        api.getPlayerById(playerId).enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                Player player = response.body().get(0);

                editFirstNameInput.setText(player.getFirstName());
                editMiddleNameInput.setText(player.getMiddleName());
                editLastNameInput.setText(player.getLastName());
                editAgeInput.setText(String.valueOf(player.getAge()));
                editPositionInput.setText(player.getPosition());
                editPositionNumberInput.setText(String.valueOf(player.getPositionNumber()));

            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {

            }
        });

        cancel.setOnClickListener(v->{
            viewPlayerDialog.dismiss();
            editPlayerDialog.dismiss();;
            onResume();
        });

        deletePlayer.setOnClickListener(v->{

            Toast.makeText(this, "implement delete function", Toast.LENGTH_SHORT).show();

        });


        save.setOnClickListener(v->{
            Toast.makeText(this, "implement save function", Toast.LENGTH_SHORT).show();
        });
        /*


        save.setOnClickListener(v->{

            Boolean valid = true;

            String firstName = Objects.requireNonNull(editFirstNameInput.getText()).toString();
            String middleName = Objects.requireNonNull(editMiddleNameInput.getText()).toString();
            String lastName = Objects.requireNonNull(editLastNameInput.getText()).toString();
            String age = Objects.requireNonNull(editAgeInput.getText()).toString();
            String position = Objects.requireNonNull(editPositionInput.getText()).toString();
            String positionNumber =Objects.requireNonNull(editPositionNumberInput.getText()).toString();

            if (TextUtils.isEmpty(firstName)){
                firstNameInput.setError("First name is required");
                valid = false;
            }

            if (TextUtils.isEmpty(lastName)){
                lastNameInput.setError("Last name is required");
                valid = false;
            }

            if (TextUtils.isEmpty(age)){
                ageInput.setError("Age is required");
                valid = false;
            }

            if (TextUtils.isEmpty(position)){
                positionInput.setError("Position is required");
                valid = false;
            }

            if (TextUtils.isEmpty(positionNumber)){
                positionNumberInput.setError("Position number is required");
                valid = false;
            }

            if (valid){

                RetrofitService service1 = new RetrofitService();
                PlayerApi api1 = service1.getRetrofit().create(PlayerApi.class);

                int intPositionNumber = Integer.parseInt(positionNumber);


                api1.updatePlayerDetails(playerId, firstName, middleName, lastName, position, intPositionNumber).enqueue(
                        new Callback<Player>() {
                            @Override
                            public void onResponse(Call<Player> call, Response<Player> response) {

                                editPlayerDialog.dismiss();
                                viewPlayerDialog.dismiss();
                                onResume();
                            }

                            @Override
                            public void onFailure(Call<Player> call, Throwable t) {
                                Toast.makeText(PlayersActivity.this, "failed to update player", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }


        });

*/
    }


    private void openNewPlayerDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_new_player, null);

        firstNameInput = view.findViewById(R.id.new_player_first_name_input);
        middleNameInput = view.findViewById(R.id.new_player_middle_name_input);
        lastNameInput = view.findViewById(R.id.new_player_last_name_input);
        ageInput = view.findViewById(R.id.new_player_age_input);
        positionInput = view.findViewById(R.id.new_player_position_input);
        positionNumberInput = view.findViewById(R.id.new_player_position_number_input);

        save = view.findViewById(R.id.save_new_player);
        cancel = view.findViewById(R.id.cancel_new_player);

        dialogBuilder.setView(view);
        newPlayerDialog = dialogBuilder.create();
        newPlayerDialog.show();

        RetrofitService service = new RetrofitService();
        PlayerApi api = service.getRetrofit().create(PlayerApi.class);

        save.setOnClickListener(v->{

            Boolean valid = true;

            String firstName = Objects.requireNonNull(firstNameInput.getText()).toString();
            String middleName = Objects.requireNonNull(middleNameInput.getText()).toString();
            String lastName = Objects.requireNonNull(lastNameInput.getText()).toString();
            String age = Objects.requireNonNull(ageInput.getText()).toString();
            String position = Objects.requireNonNull(positionInput.getText()).toString();
            String positionNumber =Objects.requireNonNull(positionNumberInput.getText()).toString();

            if (TextUtils.isEmpty(firstName)){
                firstNameInput.setError("First name is required");
                valid = false;
            }

            if (TextUtils.isEmpty(lastName)){
                lastNameInput.setError("Last name is required");
                valid = false;
            }

            if (TextUtils.isEmpty(age)){
                ageInput.setError("Age is required");
                valid = false;
            }

            if (TextUtils.isEmpty(position)){
                positionInput.setError("Position is required");
                valid = false;
            }

            if (TextUtils.isEmpty(positionNumber)){
                positionNumberInput.setError("Position number is required");
                valid = false;
            }

            if (valid){

                int intAge = Integer.parseInt(age);
                int intPositionNumber = Integer.parseInt(positionNumber);

                Player player = PlayerFactory.newPlayer(firstName, middleName, lastName, intAge, position, intPositionNumber);

                api.savePlayer(player).enqueue(new Callback<Player>() {
                    @Override
                    public void onResponse(Call<Player> call, Response<Player> response) {
                        Toast.makeText(getApplicationContext(), "Save Successfully", Toast.LENGTH_SHORT).show();
                        onResume();
                        newPlayerDialog.dismiss();                }

                    @Override
                    public void onFailure(Call<Player> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                        newPlayerDialog.dismiss();
                    }
                });

            }
        });

        cancel.setOnClickListener(v->{
            newPlayerDialog.dismiss();
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
        loadPlayers();
    }
}