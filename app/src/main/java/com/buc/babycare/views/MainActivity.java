package com.buc.babycare.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.buc.babycare.databinding.ActivityMainBinding;
import com.buc.babycare.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DBHelper(this);
        binding.registerButton.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(i);
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.usernameLog.getText().toString();
                String password = binding.passwordLog.getText().toString();

                if(username.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredential = dbHelper.checkUsernamePassword(username, password);

                    if(checkCredential) {
                        Toast.makeText(MainActivity.this, "Login Successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credential.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
