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
import za.ac.cput.fms.model.referee.Referee;
import za.ac.cput.fms.model.referee.RefereeFactory;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.RefereeApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.referees.RefereeAdapter;

public class RefereesActivity extends AppCompatActivity {

    private RecyclerView refereeRecyclerView;
    private Button registerNewReferee, back, signOut;
    private Button save, cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog newRefereeDialog;
    private TextInputEditText firstNameInput, middleNameInput, lastNameInput,
            ageInput, experienceInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referees);

        back = findViewById(R.id.referees_back_to_dashboard_button);
        back.setOnClickListener(v -> {
            finish();
        });

        signOut = findViewById(R.id.referees_sign_out_button);
        signOut.setOnClickListener(v -> {
            showSignOutDialog();
        });

        refereeRecyclerView = findViewById(R.id.referee_recycler_view);
        refereeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        registerNewReferee = findViewById(R.id.create_new_referee_button);
        registerNewReferee.setOnClickListener(v->{
            openNewManagerDialog();
        });

    }

    private void openNewManagerDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_new_referee, null);

        firstNameInput = view.findViewById(R.id.new_referee_first_name_input);
        middleNameInput = view.findViewById(R.id.new_referee_middle_name_input);
        lastNameInput = view.findViewById(R.id.new_referee_last_name_input);
        ageInput = view.findViewById(R.id.new_referee_age_input);
        experienceInput = view.findViewById(R.id.new_referee_xp_input);

        save = view.findViewById(R.id.save_new_referee);
        cancel = view.findViewById(R.id.cancel_new_referee);

        dialogBuilder.setView(view);
        newRefereeDialog = dialogBuilder.create();
        newRefereeDialog.show();

        RetrofitService service = new RetrofitService();
        RefereeApi api = service.getRetrofit().create(RefereeApi.class);

        save.setOnClickListener(v->{

            Boolean valid = true;

            String firstName = Objects.requireNonNull(firstNameInput.getText()).toString();
            String middleName = Objects.requireNonNull(middleNameInput.getText()).toString();
            String lastName = Objects.requireNonNull(lastNameInput.getText()).toString();
            String age = Objects.requireNonNull(ageInput.getText()).toString();
            String exp = Objects.requireNonNull(experienceInput.getText()).toString();

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

            if (TextUtils.isEmpty(exp)){
                experienceInput.setError("Experience is required");
                valid = false;
            }

            if (valid) {

                int intAge = Integer.parseInt(age);
                int intExp = Integer.parseInt(exp);

                Referee referee = RefereeFactory.newReferee(firstName, middleName, lastName, intAge, intExp);

                api.saveReferee(referee).enqueue(new Callback<Referee>() {
                    @Override
                    public void onResponse(Call<Referee> call, Response<Referee> response) {
                        Toast.makeText(getApplicationContext(), "Save Successfully", Toast.LENGTH_SHORT).show();
                        onResume();
                        newRefereeDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Referee> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                        newRefereeDialog.dismiss();
                    }
                });


            }
        });
        cancel.setOnClickListener(v->{
            newRefereeDialog.dismiss();
        });
    }

    public void loadReferees(){

        RetrofitService service = new RetrofitService();
        RefereeApi api = service.getRetrofit().create(RefereeApi.class);

        api.getAllReferees().enqueue(new Callback<List<Referee>>() {
            @Override
            public void onResponse(Call<List<Referee>> call, Response<List<Referee>> response) {
                populateListView(response.body());
            }

            @Override
            public void onFailure(Call<List<Referee>> call, Throwable t) {
                Toast.makeText(RefereesActivity.this, "Failed to load Referees", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void populateListView(List<Referee> refereeList) {

        RefereeAdapter adapter = new RefereeAdapter(refereeList);
        refereeRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+refereeList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {
            Toast.makeText(this, "viewing "+refereeList.get(position).getFirstName(), Toast.LENGTH_SHORT).show();
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
        loadReferees();
    }

}