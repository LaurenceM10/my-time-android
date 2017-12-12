package com.example.developer.mytime.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.developer.mytime.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        //Call the necessary methods
        initFirebaseDatabase();
    }

    /**
     * To init Firebase Real Time Database
     */
    private void initFirebaseDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("task");

        myRef.setValue("Hacer comida");
    }
}
