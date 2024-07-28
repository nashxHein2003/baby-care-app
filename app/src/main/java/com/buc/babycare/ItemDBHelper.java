package com.buc.babycare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ItemDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String db_name = "ItemDatabase.db";
    private static final int db_version = 1;

    private static final String db_table = "item";
    private static final String column_id = "_id";
    private static final String column_title = "name";
    private static final String column_quantity = "quantity";
    private static final String column_location = "location";
    private static final String column_image = "image"; //Image

    ItemDBHelper(@Nullable Context context) {
        super(context, db_name, null, db_version);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + db_table +
                " (" + column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                column_title + " TEXT, " +
                column_quantity + " INTEGER, " +
                column_location + " TEXT, " +
                column_image + " TEXT); "; //Image
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + db_table);
    }

    void addItem(String name, int quantity, String location, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(column_title, name);
        contentValues.put(column_quantity, quantity);
        contentValues.put(column_location, location);
        contentValues.put(column_image, imageUri); //Image

        System.out.println("Inserting: " + name + ", " + quantity + ", " + location + ", " + imageUri);
        try {
            long result = db.insert(db_table, null, contentValues);
            if(result == -1) {
                Toast.makeText(context, "Failed to add item", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    Cursor readAllData() {
        String query = "SELECT * FROM " + db_table;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void  updateData(String row_id, String name, String quantity, String location, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column_title, name);
        cv.put(column_quantity, quantity);
        cv.put(column_location, location);
        cv.put(column_image, imageUri); //Image

        long result = db.update(db_table, cv, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully update", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result =  db.delete(db_table, "_id=?", new String[]{row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + db_table);
    }

}
