package com.rvr.fourteenone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ScoretableP1Fragment extends ScoreTable {
	
	public static int PLAYERNUMBER = 1;
	
	private LinearLayout scoretable;
	private int turn = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
		return super.onCreateView(inflater, container, savedInstanceBundle, R.layout.scoretable_p1);		
	}

	@Override
    public void onStart() {
        Log.e("ScoreTableP1Fragment", "[ScoreTableP1Fragment] onStart");

        scoretable = super.onStart(scoretable, R.id.linearLayout_player1, PLAYERNUMBER);

        //initieel is player1 aan tafel
        //setPlayerAtTable(true);
	}

	public void addRow(int vRun, int vFoul) {
		super.addRow(scoretable, turn, vRun, vFoul);
	}

	public void setPlayerAtTable(boolean active) {
		super.setPlayerAtTable(scoretable, active);
	}

}
