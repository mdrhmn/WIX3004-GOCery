package com.example.gocery.grocerylist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gocery.R;

import com.example.gocery.grocerylist.adapter.CompletedGroceryTripAdapter;
import com.example.gocery.grocerylist.dao.DAOCompletedGroceryTrip;
import com.example.gocery.grocerylist.model.GroceryItem;
import com.example.gocery.grocerylist.model.GroceryTrip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GroceryHomeFragment extends Fragment {


    ListView listView;
    CompletedGroceryTripAdapter adapter;
    DAOCompletedGroceryTrip dao;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        CardView cardView = view.findViewById(R.id.cv_currentGroceryList);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.nav_viewCurrentGroceryList);
            }
        });

        dao = new DAOCompletedGroceryTrip();
        listView = view.findViewById(R.id.lv_completedGroceryTrip);
        ArrayList<GroceryTrip> groceryTrips = new ArrayList<>();
        adapter = new CompletedGroceryTripAdapter(getContext(), groceryTrips);
        listView.setAdapter(adapter);
        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroceryTrip groceryTrip = (GroceryTrip) adapter.getItem(position);

                // PASSING DATA TO THE NEXT FRAGMENT
                Bundle result = new Bundle();
                result.putString("TRIP_KEY", groceryTrip.getKey());
                getParentFragmentManager().setFragmentResult("viewGroceryTrip",result);
                Toast.makeText(getContext(), "SENT: "+groceryTrip.getKey(), Toast.LENGTH_SHORT).show();

                Navigation.findNavController(view).navigate(R.id.nav_viewCompletedGroceryTrip);
            }
        });

    }

    private void loadData() {
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<GroceryTrip> groceryTrips = new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    GroceryTrip groceryTrip = data.getValue(GroceryTrip.class);
                    groceryTrip.setKey(data.getKey());
                    groceryTrips.add(groceryTrip);
                }

                adapter.setCurrentGroceryTrips(groceryTrips);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grocery_home, container, false);
    }



}