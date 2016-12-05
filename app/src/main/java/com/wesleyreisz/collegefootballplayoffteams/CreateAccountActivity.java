package com.wesleyreisz.collegefootballplayoffteams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    EditText mTextEmail;
    EditText mTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setupAuthListener();

        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //validatation listener
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

        //validatation listener
        mTextPassword = (EditText)findViewById(R.id.textPassword);
        mTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (mTextPassword.length()<=6)
                    mTextPassword.setError("Password must be at least 6 characters");
            }
        });

        //button create account
        Button btnCreateAccount = (Button)findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressBar pb = (ProgressBar)findViewById(R.id.progress_bar);
                pb.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(mTextEmail.getText().toString(), mTextPassword.getText().toString())
                        .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(Constants.TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                pb.setVisibility(View.INVISIBLE);

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CreateAccountActivity.this, "Authentication Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }

    private void setupAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(Constants.TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(CreateAccountActivity.this,MainActivity.class);
                    intent.putExtra(Constants.AUTH_VAL,user.getEmail());
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(Constants.TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

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
