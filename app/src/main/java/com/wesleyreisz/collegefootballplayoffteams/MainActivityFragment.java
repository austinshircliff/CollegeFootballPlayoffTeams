package com.wesleyreisz.collegefootballplayoffteams;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRef = mFirebaseDatabase.getReference().child("teams");

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewTeams);
        ListAdapter listAdapter = new FirebaseListAdapter<Team>(getActivity(),Team.class,R.layout.team_layout,mRef) {
            @Override
            protected void populateView(View v, Team model, int position) {
                TextView textViewTeamName = (TextView)v.findViewById(R.id.teamName);
                textViewTeamName.setText(model.getName());
            }
        };
        listView.setAdapter(listAdapter);
        return view;
    }

}
