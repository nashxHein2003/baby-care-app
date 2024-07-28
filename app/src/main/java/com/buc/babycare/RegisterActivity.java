package com.buc.babycare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.buc.babycare.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailLog.getText().toString();
                String username = binding.usernameLog.getText().toString();
                String password = binding.passwordLog.getText().toString();
                String confirmPassword = binding.confirmPasswordLog.getText().toString();

                if(email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(RegisterActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    if(password.equals(confirmPassword)) {
                        Boolean checkUserEmail = dbHelper.checkEmail(email);

                        if(!checkUserEmail) {
                            Boolean insert = dbHelper.insertData(email, username, password);

                            if(insert) {
                                Toast.makeText(RegisterActivity.this,"Register Successfully.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this,"Register Failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this,"User already exists. Please Login", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this,"Invalid Password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
