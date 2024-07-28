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
    ImageView add_image;
    Uri imageUri;
    private ActivityResultLauncher<Intent> imagePickerLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add_title = findViewById(R.id.add_title);
        add_quantity = findViewById(R.id.add_quantity);
        add_location = findViewById(R.id.add_location);
        add_item = findViewById(R.id.add_item);
        add_image = findViewById(R.id.add_image);

        ItemDBHelper itemDBHelper = new ItemDBHelper(AddActivity.this);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        add_image.setImageURI(imageUri);
                    }
                }
        ); //Image

        add_image.setOnClickListener(v -> openFileChooser()); //Image

        add_item.setOnClickListener(v -> {
            itemDBHelper.addItem(
                    add_title.getText().toString().trim(),
                    Integer.parseInt(add_quantity.getText().toString().trim()),
                    add_location.getText().toString().trim(),
                    imageUri != null ? imageUri.toString() : null // Set image URI
            );
        });
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imagePickerLauncher.launch(intent);
    } //Image

}