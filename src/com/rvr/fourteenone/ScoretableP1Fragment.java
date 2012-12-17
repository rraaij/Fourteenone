package com.rvr.fourteenone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

public class ScoretableP1Fragment extends ScoreTable {
	
	public static int PLAYERNUMBER = 1;
	
	private TableLayout table;
	private int turn = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
		return super.onCreateView(inflater, container, savedInstanceBundle, R.layout.scoretable_p1);		
	}

	@Override
    public void onStart() {
        table = super.onStart(table, R.id.tableLayout_player1, PLAYERNUMBER);
	}

	public void addRow(int vRun, int vFoul) {
		super.addRow(table, turn, vRun, vFoul);
	}

	public void setPlayerAtTable(boolean active) {
		super.setPlayerAtTable(table, active);
	}

}
