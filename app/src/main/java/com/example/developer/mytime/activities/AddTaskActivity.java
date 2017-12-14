package com.example.developer.mytime.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.developer.mytime.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTaskActivity extends AppCompatActivity {
    private EditText title;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        //Call the necessary methods
        initFirebaseDatabase();
        initViews();
    }


    /**
     * To init views in objects
     */
    private void initViews(){
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
    }


    /**
     * To init actions of some elements in the view
     */
    private void initActions(){

    }


    /**
     * To validate empty inputs title and description
     */
    private boolean validateEmptyInputs(){
        if(title.getText().toString().isEmpty()){
            //Show a message is the email input is empty
            Toast.makeText(getApplicationContext(), R.string.login_activity_message_empty_email_input, Toast.LENGTH_SHORT).show();
            return false;
        } else if(description.getText().toString().isEmpty()) {
            //Show a message is the password input is empty
            Toast.makeText(getApplicationContext(), R.string.login_activity_message_empty_password_input, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    /**
     * To init Firebase Real Time Database
     */
    private void initFirebaseDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
    }
}
