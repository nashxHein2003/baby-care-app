package com.buc.babycare.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.buc.babycare.R;
import com.buc.babycare.db.ItemDBHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddActivity extends AppCompatActivity {

    EditText add_title, add_quantity, add_location;
    Button add_item;

    // Image
    ImageView add_image;
    Uri imageUri;
    byte[] imageBytes;

    private final ActivityResultLauncher<String[]> requestPermissionsLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean cameraPermission = result.getOrDefault(Manifest.permission.CAMERA, false);
                Boolean storagePermission = result.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
                openImageSourcePicker();
//                if (cameraPermission && storagePermission) {
//                    openImageSourcePicker();
//                } else {
//                    Toast.makeText(this, "Permissions required", Toast.LENGTH_SHORT).show();
//                }
            });

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null) {
                        imageUri = data.getData();
                        handleImage(imageUri);
                    }
                }
            });

    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getExtras() != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        if (bitmap != null) {
                            add_image.setImageBitmap(bitmap);
                            imageBytes = bitmapToByteArray(bitmap);
                        }
                    }
                }
            });

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

        add_image.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImageSourcePicker();
            } else {
                requestPermissionsLauncher.launch(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        });

        add_item.setOnClickListener(v -> {
            itemDBHelper.addItem(
                    add_title.getText().toString().trim(),
                    Integer.parseInt(add_quantity.getText().toString().trim()),
                    add_location.getText().toString().trim(),
                    imageBytes
            );
        });
    }

    private void openImageSourcePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image Source");
        builder.setItems(new String[]{"Camera", "Gallery"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Open camera
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureLauncher.launch(cameraIntent);
                    break;
                case 1:
                    // Open gallery
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickImageLauncher.launch(galleryIntent);
                    break;
            }
        });
        builder.show();
    }

    private void handleImage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            add_image.setImageBitmap(bitmap);
            imageBytes = bitmapToByteArray(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
