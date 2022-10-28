package za.ac.cput.fms.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;

import za.ac.cput.fms.R;
import za.ac.cput.fms.ui.user.LoginActivity;

public class Dashboard extends AppCompatActivity {

    private MaterialCardView tournaments, players, teams, venues, managers, referees;
    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tournaments = findViewById(R.id.tournament_card_button);
        tournaments.setOnClickListener(v->{
            Intent intent  = new Intent(this, TournamentsActivity.class);
            startActivity(intent);
        });


        players = findViewById(R.id.players_card_button);
        players.setOnClickListener(v->{
            Intent intent  = new Intent(this, PlayersActivity.class);
            startActivity(intent);
        });


        teams = findViewById(R.id.teams_card_button);
        teams.setOnClickListener(v->{
            Intent intent  = new Intent(this, TeamsActivity.class);
            startActivity(intent);
        });

        venues = findViewById(R.id.venue_card_button);
        venues.setOnClickListener(v->{
            Intent intent  = new Intent(this, VenuesActivity.class);
            startActivity(intent);
        });

        managers = findViewById(R.id.manager_card_button);
        managers.setOnClickListener(v->{
            Intent intent  = new Intent(this, ManagersActivity.class);
            startActivity(intent);
        });

        referees = findViewById(R.id.referee_card_button);
        referees.setOnClickListener(v->{
            Intent intent  = new Intent(this, RefereesActivity.class);
            startActivity(intent);
        });

        signOut = findViewById(R.id.sign_out_dashboard);
        signOut.setOnClickListener(v->{
            showSignOutDialog();
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
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}