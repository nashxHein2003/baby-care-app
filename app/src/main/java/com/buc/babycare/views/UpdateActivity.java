package com.buc.babycare.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.buc.babycare.R;
import com.buc.babycare.db.ItemDBHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText add_item, add_quantity, add_location;
    Button update_button, delete_button;

    String id, name, quantity, location;
    byte[] imageBytes;

    ItemDBHelper itemDBHelper;

    ImageView update_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        add_item = findViewById(R.id.add_title2);
        add_quantity = findViewById(R.id.add_quantity2);
        add_location = findViewById(R.id.add_location2);
        update_button = findViewById(R.id.update_item);
        delete_button = findViewById(R.id.delete_item);
        update_image = findViewById(R.id.add_image2);

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

            itemDBHelper.updateData(id, name, quantity, location, imageBytes);
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
            imageBytes = getIntent().getByteArrayExtra("image");

            //Setting intent
            add_item.setText(name);
            add_quantity.setText(quantity);
            add_location.setText(location);

            if (imageBytes != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                update_image.setImageBitmap(bitmap);
            } else {
                update_image.setImageResource(R.drawable.placeholder_image); // Default placeholder
            }
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
                Intent intent = new Intent(UpdateActivity.this, ItemActivity.class);
                startActivity(intent);
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