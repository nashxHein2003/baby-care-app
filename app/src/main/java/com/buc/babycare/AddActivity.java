package com.buc.babycare;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText add_title, add_quantity, add_location;
    Button add_item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add_title = findViewById(R.id.add_title);
        add_quantity = findViewById(R.id.add_quantity);
        add_location = findViewById(R.id.add_location);
        add_item = findViewById(R.id.add_item);

        ItemDBHelper itemDBHelper = new ItemDBHelper(AddActivity.this);

        add_item.setOnClickListener(v -> {
            itemDBHelper.addItem(
                    add_title.getText().toString().trim(),
                    Integer.parseInt(add_quantity.getText().toString().trim()),
                    add_location.getText().toString().trim()
            );
        });
    }

}