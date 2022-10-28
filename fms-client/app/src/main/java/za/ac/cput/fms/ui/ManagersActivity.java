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
import za.ac.cput.fms.model.manager.Manager;
import za.ac.cput.fms.model.manager.ManagerFactory;
import za.ac.cput.fms.retrofit.RetrofitService;
import za.ac.cput.fms.retrofit.api.ManagerApi;
import za.ac.cput.fms.ui.user.LoginActivity;
import za.ac.cput.fms.views.managers.ManagerAdapter;

public class ManagersActivity extends AppCompatActivity {

    private RecyclerView managerRecyclerView;
    private Button registerNewManager, back, signOut;
    private Button save, cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog newManagerDialog;
    private TextInputEditText firstNameInput, middleNameInput, lastNameInput,
            ageInput, experienceInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managers);

        back = findViewById(R.id.managers_back_to_dashboard_button);
        back.setOnClickListener(v->{
            finish();
        });

        signOut = findViewById(R.id.managers_sign_out_button);
        signOut.setOnClickListener(v->{
            showSignOutDialog();
        });

        managerRecyclerView = findViewById(R.id.managers_recycler_view);
        managerRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        registerNewManager = findViewById(R.id.create_new_manager_button);
        registerNewManager.setOnClickListener(v->{
            openNewManagerDialog();
        });

    }

    public void openNewManagerDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_new_manager, null);

        firstNameInput = view.findViewById(R.id.new_manager_first_name_input);
        middleNameInput = view.findViewById(R.id.new_manager_middle_name_input);
        lastNameInput = view.findViewById(R.id.new_manager_last_name_input);
        ageInput = view.findViewById(R.id.new_manager_age_input);
        experienceInput = view.findViewById(R.id.new_manager_xp_input);

        save = view.findViewById(R.id.save_new_manager);
        cancel = view.findViewById(R.id.cancel_new_manager);

        dialogBuilder.setView(view);
        newManagerDialog = dialogBuilder.create();
        newManagerDialog.show();

        RetrofitService service = new RetrofitService();
        ManagerApi api = service.getRetrofit().create(ManagerApi.class);

        save.setOnClickListener(v->{

            Boolean valid = true;

            String firstName = Objects.requireNonNull(firstNameInput.getText()).toString();
            String middleName = middleNameInput.getText().toString();
            String lastName = lastNameInput.getText().toString();
            String age = ageInput.getText().toString();
            String exp = experienceInput.getText().toString();

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

            if (valid){

                int intAge = Integer.parseInt(age);
                int intExp = Integer.parseInt(exp);

                Manager manager = ManagerFactory.newManager(firstName, middleName, lastName, intAge, intExp);

                api.saveManager(manager).enqueue(new Callback<Manager>() {
                    @Override
                    public void onResponse(Call<Manager> call, Response<Manager> response) {
                        Toast.makeText(getApplicationContext(), "Save Successfully", Toast.LENGTH_SHORT).show();
                        onResume();
                        newManagerDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Manager> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed To Save", Toast.LENGTH_SHORT).show();
                        newManagerDialog.dismiss();
                    }
                });

            }

        });

        cancel.setOnClickListener(v->{
            newManagerDialog.dismiss();
        });

    }

    public void loadManagers() {

        RetrofitService service = new RetrofitService();
        ManagerApi api = service.getRetrofit().create(ManagerApi.class);

        api.getAllManagers()
                .enqueue(new Callback<List<Manager>>() {
                    @Override
                    public void onResponse(Call<List<Manager>> call, Response<List<Manager>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Manager>> call, Throwable t) {
                        Toast.makeText(ManagersActivity.this, "Failed to load Manager", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void populateListView(List<Manager> managerList) {
        ManagerAdapter adapter = new ManagerAdapter(managerList);
        managerRecyclerView.setAdapter(adapter);

        Toast.makeText(this, "Retrieved "+managerList.size(), Toast.LENGTH_SHORT).show();

        adapter.OnRecyclerViewClickListener(position -> {
            Toast.makeText(this, "viewing "+managerList.get(position).getFirstName(), Toast.LENGTH_SHORT).show();

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
        loadManagers();
    }

}