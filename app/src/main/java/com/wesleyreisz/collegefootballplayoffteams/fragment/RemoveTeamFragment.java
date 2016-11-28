package com.wesleyreisz.collegefootballplayoffteams.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wesleyreisz.collegefootballplayoffteams.Constants;
import com.wesleyreisz.collegefootballplayoffteams.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveTeamFragment extends Fragment {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mTeamsReference = mRootRef.child("teams");

    private long teamid2Edit;

    public long getTeamid2Edit() {
        return teamid2Edit;
    }

    public void setTeamid2Edit(long teamid2Edit) {
        this.teamid2Edit = teamid2Edit;
    }

    public RemoveTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_remove_team, container, false);

        //how you perform a query where id=teamid2edit
        Query q = mTeamsReference
                .orderByChild("id")
                .equalTo(teamid2Edit);

        q.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                            Log.d(Constants.TAG,"Removing:" + childSnapshot.getKey());
                            childSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(Constants.TAG,"Error occured:" + databaseError);
                    }
                });

        TextView textView = (TextView)view.findViewById(R.id.txtViewMessage);
        textView.setText("Removed: " + teamid2Edit);

        return view;
    }

}
