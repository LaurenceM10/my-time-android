package com.example.developer.mytime.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.developer.mytime.R;
import com.example.developer.mytime.adapters.HomeAdapter;
import com.example.developer.mytime.models.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<Task> tasks;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    /**
     * onViewCreated It is the ideal method for any view configuration
     * or to bind view listeners
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Call the necessary methods
        initFirebaseDatabase();
        initViews(view);
        configureRecyclerView();
        initObjects();
        getTasks();
    }


    /**
     * To init and get a reference from Firebase Real Time Database
     */
    private void initFirebaseDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users/tasks/title");
    }


    /**
     * To init views
     */
    private void initViews(View view){
        recyclerView = view.findViewById(R.id.recycler_view);
    }


    /**
     * To configure the RecyclerView
     */
    private void configureRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    /**
     * To init objects
     */
    private void initObjects(){
        tasks = new ArrayList<>();
    }




    /**
     * FIXME: REPAIR THIS METHOD. THERE ARE MANY ERRORS.
     *
     *
     * This method get the task list from Firebase Real Time Database
     */
    private void getTasks(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //To save data from DataSnapshot in the object
                Task task = new Task();

                /*
                * To iterate the data list of the DataSnapshot and set each element
                * int the tasks ArrayList.
                * getChildren() is used to bring all the children of the node users/tasks
                * */
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    task.setTitle(data.getValue().toString());
                    tasks.add(task);
                }

                //To pass the data to the adapter
                HomeAdapter homeAdapter = new HomeAdapter(tasks, getContext());

                //To set adapter in the RecyclerView
                recyclerView.setAdapter(homeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: show a firebase message connection error message
            }
        });
    }


    /**
     * To
     */

}
