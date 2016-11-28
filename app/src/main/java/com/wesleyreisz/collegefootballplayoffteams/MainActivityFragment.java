package com.wesleyreisz.collegefootballplayoffteams;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRef = mRootRef.child("teams");

    ListView mListView;
    ListAdapter mListAdapter;

    HashMap<Integer,Long> teamMap = new HashMap<>();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        teamMap = new HashMap<>();

        mListView = (ListView)view.findViewById(R.id.listViewTeams);
        mListAdapter = new FirebaseListAdapter<Team>(getActivity(),Team.class,R.layout.team_layout, mRef) {

            @Override
            protected void populateView(View v, Team model, int position) {
                TextView teamNameTextView = (TextView)v.findViewById(R.id.teamName);
                teamNameTextView.setText(model.getName());
                teamMap.put(position,model.getId());
            }
        };
        mListView.setAdapter(mListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                TeamFragment teamFragment = new TeamFragment();
                teamFragment.setTeamId(teamMap.get(position));
                Log.d(Constants.TAG, "teamid: " + teamFragment.getTeamId());

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragmentContainer,teamFragment);
                ft.commit();

            }
        });

        return view;
    }

}
