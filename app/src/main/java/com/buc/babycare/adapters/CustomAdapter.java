package com.buc.babycare.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buc.babycare.models.Model;
import com.buc.babycare.R;
import com.buc.babycare.views.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Model> itemList;
    private Activity activity;

    public CustomAdapter(Activity activity, Context context, ArrayList<Model> itemList) {
        this.activity = activity;
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model item = itemList.get(position);
        holder.item_txt.setText(item.getName());
        holder.quantity_txt.setText(item.getQuantity());
        holder.location_txt.setText(item.getLocation());
        holder.item_checkbox.setChecked(item.isChecked());

        if (item.getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
            holder.item_image.setImageBitmap(bitmap);
        } else {
            holder.item_image.setImageResource(R.drawable.placeholder_image);
        }

        holder.item_checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            itemList.get(holder.getAdapterPosition()).setChecked(isChecked);
        });

        holder.mainItemLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("name", item.getName());
            intent.putExtra("quantity", item.getQuantity());
            intent.putExtra("location", item.getLocation());
            intent.putExtra("image", item.getImage());
            activity.startActivityForResult(intent, 1);
        });

        holder.send_sms_button.setOnClickListener(v -> {
            String message = "Item: " + item.getName() + "\nQuantity: " + item.getQuantity() + "\nLocation: " + item.getLocation();
            sendSms(message);
        });
    }

    private void sendSms(String message) {
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.putExtra("sms_body", message);
        context.startActivity(smsIntent);

//        if (smsIntent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(smsIntent);
//        } else {
//            Toast.makeText(context, "No SMS app found", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_txt, quantity_txt, location_txt;
        CheckBox item_checkbox;
        LinearLayout mainItemLayout;

        ImageView item_image;

        Button send_sms_button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_txt = itemView.findViewById(R.id.item_txt);
            quantity_txt = itemView.findViewById(R.id.quantity_txt);
            location_txt = itemView.findViewById(R.id.location_txt);
            item_image = itemView.findViewById(R.id.vector_image);
            item_checkbox = itemView.findViewById(R.id.done_check);
            mainItemLayout = itemView.findViewById(R.id.mainItemLayout);
            send_sms_button = itemView.findViewById(R.id.sms_button);
        }
    }

    public ArrayList<Model> getCheckedItems() {
        ArrayList<Model> checkedItems = new ArrayList<>();
        for (Model item : itemList) {
            if (item.isChecked()) {
                checkedItems.add(item);
            }
        }
        return checkedItems;
    }
}
