package com.example.gocery.card_voucher.dao;

import com.example.gocery.card_voucher.model.Voucher;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOVoucher {
    private DatabaseReference dbRef;
    private String currentUser;

    public DAOVoucher(){
        currentUser = "user_"+ FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference(Voucher.class.getSimpleName()).child(currentUser);
    }

    public Task<Void> addVoucher(Voucher voucher) {
        return dbRef.push().setValue(voucher);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) {
        return dbRef.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) {
        return dbRef.child(key).removeValue();
    }

    public Query get() {
        return dbRef;
    }

    public Query getSingle(String item_key) {
        return dbRef.child(item_key);
    }
}
