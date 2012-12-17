package com.rvr.fourteenone;

import com.rvr.fourteenone.model.GameInfo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreTable extends Fragment {
	
	private GameInfo gameinfo = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle, int layout) {
		return inflater.inflate(layout, container, false);		
	}
	
	public TableLayout onStart(TableLayout table, int layout, int player) {
        super.onStart();
        
        Bundle bundle = getActivity().getIntent().getExtras();
        gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");
                
        table = (TableLayout) getActivity().findViewById(layout);
        
        table.setStretchAllColumns(true);  
        table.setShrinkAllColumns(true);  
        TableRow playername = new TableRow(getActivity());  
        playername.setGravity(Gravity.CENTER_HORIZONTAL);

        // title column/row  
        TextView name = new TextView(getActivity());  
        name.setWidth(160);
        if(player == 1) {
        	name.setText(gameinfo.getPlayer1());
        } else {
        	name.setText(gameinfo.getPlayer2());  
        }
        name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);  
        name.setGravity(Gravity.CENTER);  
        name.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);  
        playername.addView(name);  
        
        TableRow header = createTableRow("#", "Run", "Foul", "Score");
        
        table.addView(playername);
        table.addView(header);
        return table;
	}
	
	private TableRow createTableRow(String vNr, String vRun, String vFoul, String vScore) {
		TableRow row = new TableRow(getActivity());
        TextView nr = new TextView(getActivity());
        nr.setText(vNr);
        nr.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        nr.setGravity(Gravity.CENTER);
        nr.setTypeface(Typeface.DEFAULT_BOLD);
        TextView run = new TextView(getActivity());
        run.setText(vRun);
        run.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        run.setGravity(Gravity.CENTER);
        run.setTypeface(Typeface.DEFAULT_BOLD);
        TextView foul = new TextView(getActivity());
        foul.setText(vFoul);
        foul.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        foul.setGravity(Gravity.CENTER);
        foul.setTypeface(Typeface.DEFAULT_BOLD);
        TextView score = new TextView(getActivity());
        score.setText(vScore);
        score.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        score.setGravity(Gravity.CENTER);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        row.addView(nr);
        row.addView(run);
        row.addView(foul);
        row.addView(score);
        
        return row;
	}

	public void addRow(TableLayout table, int turn, int vRun, int vFoul) {
		int iScore;
		// get last score
		if (table.getChildCount() == 2 && turn == 0) {
			// it's the first score to be entered
			iScore = vRun-vFoul;
			table.addView(createTableRow(Integer.toString(turn+1), Integer.toString(vRun), Integer.toString(vFoul), Integer.toString(iScore)));
		} else {
			TableRow child = (TableRow) table.getChildAt(table.getChildCount()-1);
			int iNr = Integer.parseInt((String) ((TextView) child.getChildAt(0)).getText());
			iNr++;

			// opeenvolgende fouls
			int iFoul = Integer.parseInt((String) ((TextView) child.getChildAt(2)).getText());

			iScore = Integer.parseInt((String) ((TextView) child.getChildAt(3)).getText());
			iScore += vRun - vFoul;
			
			table.addView(createTableRow(Integer.toString(iNr), Integer.toString(vRun), Integer.toString(vFoul), Integer.toString(iScore)));
		}
		if (iScore >= gameinfo.getTarget()) {
			Toast.makeText(getActivity(), "END OF GAME", Toast.LENGTH_LONG).show();
		}
	}
	
	public void setPlayerAtTable(TableLayout table, boolean active) {
		if (active) {
			table.setBackgroundColor(Color.WHITE);
		} else {
			table.setBackgroundColor(Color.parseColor("#33b5e5"));			
		}
	}
}
