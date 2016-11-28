package com.wesleyreisz.collegefootballplayoffteams.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wesleyreisz.collegefootballplayoffteams.Constants;
import com.wesleyreisz.collegefootballplayoffteams.MainActivity;
import com.wesleyreisz.collegefootballplayoffteams.R;
import com.wesleyreisz.collegefootballplayoffteams.behaviors.ShowFab;
import com.wesleyreisz.collegefootballplayoffteams.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShowTeamsListFragment extends Fragment implements ShowFab{
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mTeamsReference = mRootRef.child("teams");

    List<Long>listofIds = new ArrayList<Long>();

    ListView teamsListView;
    ListAdapter teamsListAdapter;

    public ShowTeamsListFragment() {
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

                listofIds.add(position,model.getId());
            }
        };
        teamsListView.setAdapter(teamsListAdapter);

        teamsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity mainActivity = (MainActivity) getActivity();

                mainActivity.updateFragment(new ShowTeamFragment());
            }
        });

        registerForContextMenu(teamsListView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,
                                    View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        Log.d(Constants.TAG, "id (position): " + listofIds.get(listPosition) +"(" + listPosition +")");

        MainActivity mainActivity = (MainActivity) getActivity();
        if (item.getTitle() == "Edit") {
            EditTeamFragment fragment = new EditTeamFragment();
            fragment.setTeamid2Edit(listofIds.get(listPosition));
            mainActivity.updateFragment(fragment);
        } else if (item.getTitle() == "Delete") {
            RemoveTeamFragment fragment = new RemoveTeamFragment();
            fragment.setTeamid2Edit(listofIds.get(listPosition));
            mainActivity.updateFragment(fragment);
        } else {
            return false;
        }
        return true;
    }

}
