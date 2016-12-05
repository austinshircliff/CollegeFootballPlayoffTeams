package com.wesleyreisz.collegefootballplayoffteams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    /*
    Steps for setting up auth:
     1.) Add Firebase Auth to gradle
     2.) Get an instance and create a listener
     3.) Validate input and call signInWithEmailAndPassword
     4.) Add Listeners for either sucess/failure or complete.
     5.) Start and stop listener when the activity is created and torn down
     */

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    EditText mTextEmail;
    EditText mTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //get auth instance
        mAuth = FirebaseAuth.getInstance();

        //create auth listener
        createAuthListener();

        //create account link
        TextView txtCreateAccount = (TextView)findViewById(R.id.txtCreateAccount);
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        //validation listener
        mTextEmail = (EditText)findViewById(R.id.textEmail);
        mTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mTextEmail.length()<=0)
                    mTextEmail.setError("Email is Required");
                if (!mTextEmail.getText().toString().contains("@"))
                    mTextEmail.setError("Valid Email is Required");
            }
        });

        //validation listener
        mTextPassword = (EditText)findViewById(R.id.textPassword);
        mTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mTextPassword.length()<=6)
                    mTextPassword.setError("Password must be at least 6 characters");
            }
        });

        //signin button
        Button btnSignin = (Button)findViewById(R.id.simple_sign_in_button);
        btnSignin.setOnClickListener(this);
    }

    //setup an authlistener for login/logout events for firebase
    private void createAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(Constants.TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    Intent intent = new Intent(SignInActivity.this,MainActivity.class);
                    intent.putExtra(Constants.AUTH_VAL,user.getEmail());
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(Constants.TAG, "onAuthStateChanged:signed_out");
                }
            }

        };
    }

    //actual method that signs into firebase
    private void signInUidPass(){
        final ProgressBar pb = (ProgressBar)findViewById(R.id.progress_bar);
        pb.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(mTextEmail.getText().toString(), mTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(Constants.TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            pb.setVisibility(View.INVISIBLE);
                            Log.w(Constants.TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignInActivity.this,"Authentication Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    //handle some events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple_sign_in_button:
                signInUidPass();
                break;
        }

    }

    //these just start and stop the listener... they could be done any number of ways
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
