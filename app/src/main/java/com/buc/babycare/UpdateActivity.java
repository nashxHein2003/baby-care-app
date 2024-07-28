package com.buc.babycare;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText add_item, add_quantity, add_location;
    Button update_button, delete_button;

    String id, name, quantity, location;

    ItemDBHelper itemDBHelper;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        add_item = findViewById(R.id.add_title2);
        add_quantity = findViewById(R.id.add_quantity2);
        add_location = findViewById(R.id.add_location2);
        update_button = findViewById(R.id.update_item);
        delete_button = findViewById(R.id.delete_item);

        itemDBHelper = new ItemDBHelper(UpdateActivity.this);

        //First Call
        getAndSetIntentDasta();

        //Set appBar title
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(name);

        update_button.setOnClickListener(v -> {
            //And only then call
            ItemDBHelper itemDBHelper = new ItemDBHelper(UpdateActivity.this);
            name = add_item.getText().toString().trim();
            quantity = add_quantity.getText().toString().trim();
            location = add_location.getText().toString().trim();

            itemDBHelper.updateData(id, name, quantity, location);
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentDasta() {
        if(getIntent().hasExtra("name") && getIntent().hasExtra("quantity") && getIntent().hasExtra("location")) {
            //Getting data from intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            quantity = getIntent().getStringExtra("quantity");
            location = getIntent().getStringExtra("location");

            //Setting intent
            add_item.setText(name);
            add_quantity.setText(quantity);
            add_location.setText(location);
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + "?");
        builder.setMessage("Are you sure to delete " + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ItemDBHelper itemDBHelper = new ItemDBHelper(UpdateActivity.this);
                itemDBHelper.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

}