package com.example.developer.mytime;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.developer.mytime.activities.AccountActivity;
import com.example.developer.mytime.activities.AddTaskActivity;
import com.example.developer.mytime.activities.LoginActivity;
import com.example.developer.mytime.activities.SettingsActivity;
import com.example.developer.mytime.fragments.HomeFragment;
import com.example.developer.mytime.fragments.TodayFragment;
import com.example.developer.mytime.fragments.TomorrowFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initFloatingActionButton();
        initDraweLayout();
        initNavigationView();
        initViews();
        initFirebaseAuth();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * To start the toolbar and set the support
     */
    private void initToolbar(){
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * To init and show Floating Action Button
     */
    private void initFloatingActionButton(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                * The openActivity() method is called and the activity that
                * you want to open is passed as a parameter
                * */
                openActivity(AddTaskActivity.class);
            }
        });
    }


    /**
     * To init and show Drawer Layout
     */
    private void initDraweLayout(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    /**
     * To init and show the Navigation View
     */
    private void initNavigationView(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * To init views
     */
    private void initViews(){
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
    }


    /**
     * To instance and init Firebase Authentication
     */
    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
        } else if (id == R.id.nav_today) {
            fragmentClass = TodayFragment.class;
        } else if (id == R.id.nav_tomorrow) {
            fragmentClass = TomorrowFragment.class;
        } else if (id == R.id.nav_account) {
            /*
            * The openActivity() method is called and the activity that
            * you want to open is passed as a parameter
            * */
            openActivity(AccountActivity.class);
        } else if (id == R.id.nav_settings) {
            /*
            * The openActivity() method is called and the activity that
            * you want to open is passed as a parameter
            * */
            openActivity(SettingsActivity.class);
        } else if (id == R.id.nav_logout) {
            signOut();
        }


        /*
        * Call to the method in charge of replacing the fragment.
        * The second parameter allways is null, there's no problem
        */
        replaceFragment(fragmentClass, fragment);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * To replace and display the corresponding fragment
     *
     * @param fragmentClass
     * @param fragment
     */
    private void replaceFragment(Class fragmentClass, Fragment fragment){
        /**
         * Here we replace the fragments. These will be hosted in a FrameLayout
         * that is inside the content_main (layout).
         */
        try {
            //can be null
            assert fragmentClass != null;
            fragment = (Fragment) fragmentClass.newInstance();

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To update the user interface if the user is authenticated or not
     *
     * @param currentUser
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            //To open the login activity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

            //Finish the activity
            finish();
        }
    }

    /**
     * To signout
     */
    private void signOut(){
        //To close the session, just call firebase method signout()
        mAuth.signOut();

        //To show a progress dialog
        showLoadingProgressDialog();

        //To get the current user and update the UI if the current user is null
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    /**
     * To show a login message progress dialog
     */
    private void showLoadingProgressDialog(){
        //To set a message and show a progress dialog
        progressDialog.setMessage(getString(R.string.activity_main_message_logout));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * To open any activity
     *
     * @param activity
     */
    private void openActivity(Class activity){
        //The activity that is received as a parameter is opened
        Intent intent = new Intent(getApplicationContext(), activity);
        startActivity(intent);
    }
}
