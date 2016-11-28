package com.wesleyreisz.collegefootballplayoffteams.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wesleyreisz.collegefootballplayoffteams.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTeamFragment extends Fragment {
    private long teamid2Edit;

    public long getTeamid2Edit() {
        return teamid2Edit;
    }

    public void setTeamid2Edit(long teamid2Edit) {
        this.teamid2Edit = teamid2Edit;
    }

    public EditTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_team, container, false);
    }

}
