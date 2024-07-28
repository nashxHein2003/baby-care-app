package com.buc.babycare;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_image;
    TextView empty_data_txt;

    ItemDBHelper itemDBHelper;
    ArrayList<Model> itemList;
    CustomAdapter customAdapter;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        recyclerView = findViewById(R.id.recyclerView);
        empty_image = findViewById(R.id.empty_image);
        empty_data_txt = findViewById(R.id.empty_data_txt);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(v -> {
            Intent intent = new Intent(ItemActivity.this, AddActivity.class);
            startActivity(intent);
        });

        itemDBHelper = new ItemDBHelper(ItemActivity.this);
        itemList = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(ItemActivity.this, this, itemList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ItemActivity.this));
    }

    void storeDataInArrays() {
        Cursor cursor = itemDBHelper.readAllData();
        if (cursor.getCount() == 0) {
            empty_image.setVisibility(View.VISIBLE);
            empty_data_txt.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                itemList.add(new Model(
                        cursor.getString(0), // id
                        cursor.getString(1), // name
                        cursor.getString(2), // quantity
                        cursor.getString(3), // location
                        false // default isChecked value
                ));
            }
            empty_image.setVisibility(View.GONE);
            empty_data_txt.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure to delete all?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            ItemDBHelper itemDBHelper = new ItemDBHelper(ItemActivity.this);
            itemDBHelper.deleteAllData();
            //refresh activity
            Intent intent = new Intent(ItemActivity.this, ItemActivity.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            // Do nothing
        });
        builder.create().show();
    }
}
