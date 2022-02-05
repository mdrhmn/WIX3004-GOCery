package com.example.gocery.manageprofiles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import com.example.gocery.R;
import com.example.gocery.manageprofiles.model.UserProfile;
import com.google.firebase.storage.StorageReference;

public class UserProfileAdapter extends BaseAdapter {
    List<UserProfile> userProfileList;
    Context context;
    LayoutInflater inflater;
    StorageReference storageReference;

    public UserProfileAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public UserProfileAdapter(Context context, List<UserProfile> userProfiles){
        this.context = context;
        userProfileList = userProfiles;
        inflater = LayoutInflater.from(context);
    }

    public void setUserProfileList(ArrayList<UserProfile> userProfiles){
        userProfileList = userProfiles;
    }

    public int getCount(){
        return userProfileList.size();
    }

    @Override
    public Object getItem(int position) {
        return userProfileList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserProfile userProfile = (UserProfile) this.getItem(position);

        if(convertView == null){
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.profile_card, parent, false);
        }

        TextView TVProfile = convertView.findViewById(R.id.TVProfile);
        ImageView IVProfile = convertView.findViewById(R.id.IVProfile);
        ImageView IVLock = convertView.findViewById(R.id.IVLock);

        TVProfile.setText(userProfile.getUsername());
        IVProfile.setImageResource(userProfile.getImgId());
        if(userProfile.getPassword() == null){
            IVLock.setVisibility(convertView.GONE);
        }else{
            IVLock.setVisibility(convertView.VISIBLE);
        }

        return convertView;
    }


}