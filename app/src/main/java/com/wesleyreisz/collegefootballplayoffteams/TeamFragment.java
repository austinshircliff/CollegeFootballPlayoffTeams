package com.wesleyreisz.collegefootballplayoffteams;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment {
    private long teamId;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRef = mRootRef.child("teams");

    public TeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_team, container, false);
        Query query = mRef.orderByChild("id").equalTo(teamId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Team team = null;
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                    team = childSnapshot.getValue(Team.class);
                }
                TextView textViewTeamName = (TextView)view.findViewById(R.id.textViewTeamName);
                textViewTeamName.setText(team.getName());

                TextView textViewMascot = (TextView)view.findViewById(R.id.textViewMascot);
                textViewMascot.setText(team.getMascot());

                Log.d(Constants.TAG,team.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }
}
