package za.ac.cput.fms.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import za.ac.cput.fms.R;
import za.ac.cput.fms.ui.Dashboard;

public class LoginActivity extends AppCompatActivity {

    private Button forgotPasswordButton, signInButton, signUpButton;
    private TextInputLayout emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);


        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        forgotPasswordButton.setOnClickListener(v->{
            Toast.makeText(this, "Forgetting password...", Toast.LENGTH_SHORT).show();
        });

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            finish();
        });

        signUpButton = findViewById(R.id.to_sign_up_button);
        signUpButton.setOnClickListener(v->{
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

    }
}