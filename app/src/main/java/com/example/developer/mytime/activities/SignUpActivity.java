package com.example.developer.mytime.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.developer.mytime.MainActivity;
import com.example.developer.mytime.R;
import com.example.developer.mytime.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText email;
    private EditText password;
    private Button signup;
    private TextView login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initFirebaseAuth();
        initFirebaseDatabase();
        initViews();
        initActions();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * To update the user interface if the user is authenticated
     *
     * @param currentUser
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            //To open MainActivity when the user is authenticated
            openMainActivity();
        }
    }


    /**
     * To instance and init Firebase Authentication
     */
    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * To init Firebase Real Time Database
     */
    private void initFirebaseDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("mytime-android-app");
    }


    /**
     * To init views
     */
    private void initViews() {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
    }


    /**
     * To init actions of some elements in the view
     */
    private void initActions() {
        //To login a user with email and password
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginUpActivity();
            }
        });

        //To create a new account an activity is opened
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmptyInputs()){
                    //Show progress dialog
                    showLoadingProgressDialog();

                    //To signup the user
                    createUser(email.getText().toString(), password.getText().toString());
                }
            }
        });
    }


    /**
     * To validate empty inputs
     *
     * @return
     */
    private boolean validateEmptyInputs(){
        if(email.getText().toString().isEmpty()){
            //Show a message is the email input is empty
            Toast.makeText(getApplicationContext(), R.string.signup_activity_message_empty_email_input, Toast.LENGTH_SHORT).show();
            return false;
        } else if(password.getText().toString().isEmpty()) {
            //Show a message is the password input is empty
            Toast.makeText(getApplicationContext(), R.string.signup_activity_message_empty_password_input, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    /**
     * To create an account Firebase
     *
     * @param email
     * @param password
     */
    private void createUser (final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Write the new user in the Firebase Real Time Database
                            assert user != null;
                            writeNewUser(user.getUid(), email, password);

                            updateUI(user);
                        } else {
                            //Dismiss the progres dialog
                            dismissLoadingProgressDialog();

                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), R.string.failed_signup_message,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


    /**
     * To open SignUp Activity
     */
    private void openLoginUpActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * To open Main Activity
     */
    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

        //To finish the activity
        finish();
    }


    /**
     * To show a login message progress dialog
     */
    private void showLoadingProgressDialog(){
        //To set a message and show a progress dialog
        progressDialog.setMessage(getString(R.string.signup_activity_message_loading_signup));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * To dismiss a login message progress dialog
     */
    private void dismissLoadingProgressDialog(){
        progressDialog.dismiss();
    }


    /**
     * Write a new user in the Firebase Real Time Database
     */
    private void writeNewUser(String userId, String email, String password) {
        User user = new User(email, password);

        databaseReference.child("users").child(userId).setValue(user);
        Log.i("Debug:", "User created");
    }
}
