package za.ac.cput.fms.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import za.ac.cput.fms.R;

public class SignUpActivity extends AppCompatActivity {

    private Button completeSignUpButton;
    private TextInputEditText firstNameInput, middleNameInput, lastNameInput
            , emailInput, passwordInput, confirmConfirmPassword, adminPasswordd;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        completeSignUpButton = findViewById(R.id.complete_sign_up_button);
        completeSignUpButton.setOnClickListener(v->{
            signUpUser();
            finish();
        });

    }

    private void signUpUser(){

       Boolean valid = true;






    }

}