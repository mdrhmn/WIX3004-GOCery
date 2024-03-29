package com.example.gocery.grocerylist.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gocery.R;
import com.example.gocery.grocerylist.model.GroceryItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FinalizeGroceryListAdapter extends BaseAdapter {

    List<GroceryItem> groceryItems;
    Context context;
    LayoutInflater inflater;
    StorageReference storageReference;

    public FinalizeGroceryListAdapter(Context context, List<GroceryItem> groceryItems) {
        this.context = context;
        this.groceryItems = groceryItems;
        inflater = LayoutInflater.from(context);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void setCurrentGroceryItems(ArrayList<GroceryItem> groceryItems) {
        this.groceryItems = groceryItems;
    }

    public List<GroceryItem> getList(){
        return this.groceryItems;
    }


    @Override
    public int getCount() {

        if(groceryItems.isEmpty()){
            return 0;
        }else{
            return groceryItems.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return groceryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GroceryItem gi = (GroceryItem) this.getItem(position);

        if(convertView == null){
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_finalize_grocery, parent, false);
        }

        TextView itemName = convertView.findViewById(R.id.tv_itemName);
        TextView itemDesc = convertView.findViewById(R.id.tv_itemDesc);
        TextView itemAmount = convertView.findViewById(R.id.tv_itemAmount);
        ImageView itemImage = convertView.findViewById(R.id.iv_imageItem);
        TextView locationName = convertView.findViewById(R.id.tv_locationName);
        ImageView locationIcon = convertView.findViewById(R.id.iv_locationIcon);
        TextView requesterName = convertView.findViewById(R.id.tv_requestedUser);


        // Set the items
        itemName.setText(gi.getName());
        itemAmount.setText(gi.getQuantity()+"x");
        requesterName.setText(gi.getCreatedBy());

        if(!gi.getDescription().equalsIgnoreCase("")){
            itemDesc.setText(gi.getDescription());
        }else {
            itemDesc.setVisibility(View.GONE);
        }

        if(gi.getLocationID()!=null){
            locationName.setVisibility(View.VISIBLE);
            locationIcon.setVisibility(View.VISIBLE);
            locationName.setText(gi.getLocationName());
        }else{
            locationName.setVisibility(View.GONE);
            locationIcon.setVisibility(View.GONE);
        }


        if(gi.getImage() == null){
            itemImage.setVisibility(View.GONE);
        }else {
            itemImage.setVisibility(View.VISIBLE);
            storageReference.child(""+gi.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context)
                            .load(uri)
                            .placeholder(R.drawable.spinning_loading)
                            .error(R.drawable.gocery_logo_only)
                            .into(itemImage);
                }
            });
        }

        return convertView;
    }
}
