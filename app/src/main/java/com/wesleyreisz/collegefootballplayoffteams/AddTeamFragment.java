package com.wesleyreisz.collegefootballplayoffteams;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTeamFragment extends Fragment {

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mFirebaseDatabase.getReference().child("teams");

    public AddTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_add_team, container, false);

        //get fields & validate
        final EditText editTextTeamName = (EditText)view.findViewById(R.id.editTextTeamName);
        editTextTeamName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editTextTeamName.length()<=0)
                        editTextTeamName.setError("Team Name is Required");
            }
        });

        final EditText editTextTeamMascot = (EditText)view.findViewById(R.id.editTextTeamMascot);
        editTextTeamMascot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editTextTeamName.length()<=0)
                    editTextTeamName.setError("Mascot is Required");
            }
        });

        final EditText editTextWins = (EditText)view.findViewById(R.id.editTextWins);
        final EditText editTextLoses = (EditText)view.findViewById(R.id.editTextLoses);
        final EditText editTextImage = (EditText)view.findViewById(R.id.editTextImage);

        Button btnAddTeam = (Button) view.findViewById(R.id.btnAddTeam);
        btnAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String key = mRef.push().getKey();
                Team team = new Team(
                    156,
                    Long.parseLong(editTextLoses.getText().toString()),
                    editTextTeamMascot.getText().toString(),
                    editTextTeamName.getText().toString(),
                    editTextImage.getText().toString(),
                    Long.parseLong(editTextWins.getText().toString())
                );
                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put(key,team);

                Log.d(Constants.TAG, "team:" + team.toString());
                mRef.updateChildren(updates);

                //load list fragment
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentMainContainer, new ListTeamsFragment());
                ft.commit();


            }
        });


        return view;
    }

}
