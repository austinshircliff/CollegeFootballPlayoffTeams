package com.wesleyreisz.collegefootballplayoffteams;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private String authenticatedUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check authentication on the activity
        checkAuth(getIntent());

        //build the toolbar form the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new AddTeamFragment());
            }
        });

        //load the correct fragment
        loadFragment(new ListTeamsFragment());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }else if (id == R.id.action_home){
            loadFragment(new ListTeamsFragment());
        }else if (id == R.id.action_logout){
            authenticatedUser = "";
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkAuth(Intent reqIntent) {
        authenticatedUser = reqIntent.getStringExtra(Constants.AUTH_VAL);
        if(TextUtils.isEmpty(authenticatedUser)){
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
        }
        Log.d(Constants.TAG,"Authenticated User: " + authenticatedUser);
    }

    private void loadFragment(Fragment fragment) {
        Log.d(Constants.TAG, "Loading fragment: " + fragment);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentMainContainer, fragment);
        ft.commit();
    }
}
