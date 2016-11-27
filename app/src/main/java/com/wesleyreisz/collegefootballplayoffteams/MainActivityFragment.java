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
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mTeamsReference = mRootRef.child("teams");

    ListView teamsListView;
    ListAdapter teamsListAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        teamsListView = (ListView)view.findViewById(R.id.listViewTeams);
        teamsListAdapter = new FirebaseListAdapter<Team>(getActivity(),Team.class,R.layout.team_layout,mTeamsReference) {
            @Override
            protected void populateView(View v, Team model, int position) {
                Log.d(Constants.TAG,model.toString());
                TextView teamName = (TextView)v.findViewById(R.id.teamName);
                teamName.setText(model.getName());
                TextView teamMascot = (TextView)v.findViewById(R.id.teamNickName);
                teamMascot.setText(model.getMascot());
                TextView teamRecord = (TextView)v.findViewById(R.id.teamRecord);
                teamRecord.setText("Record: " + model.getWins() + "-" + model.getLoses());
            }
        };
        teamsListView.setAdapter(teamsListAdapter);

        return view;
    }

}
