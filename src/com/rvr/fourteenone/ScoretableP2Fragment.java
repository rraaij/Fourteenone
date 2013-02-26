package com.rvr.fourteenone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ScoretableP2Fragment extends ScoreTable {
	
	public static int PLAYERNUMBER = 2;
	
	private LinearLayout table;
	private int turn = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
		return super.onCreateView(inflater, container, savedInstanceBundle, R.layout.scoretable_p2);		
	}

	@Override
    public void onStart() {
        Log.e("ScoreTableP2Fragment", "[ScoreTableP2Fragment] onStart");

        table = super.onStart(table, R.id.linearLayout_player2, PLAYERNUMBER);

        //initieel is player1 aan tafel
        //setPlayerAtTable(false);
	}

	public void addRow(int vRun, int vFoul) {
		super.addRow(table, turn, vRun, vFoul);
	}

    public int removeLastRow() {
        return super.removeLastRow(table);
    }

	public void setPlayerAtTable(boolean active) {
		super.setPlayerAtTable(table, active);
	}

}
