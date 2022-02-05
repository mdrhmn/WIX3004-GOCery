package com.example.gocery.manageprofiles.dao;

import com.example.gocery.manageprofiles.model.Household;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOHousehold {

    private DatabaseReference databaseReference;
    private String householdID;

    public DAOHousehold(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Household.class.getSimpleName());

    }

    public Task<Void> add(Household household, String userID){
        householdID = "household_"+userID;
        return databaseReference.child(householdID).push().setValue(household);
    }
}